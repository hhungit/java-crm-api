package com.bys.crm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.bys.crm.app.dto.BranchSummaryDto;
import com.bys.crm.app.dto.CustomerResourcesDto;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.app.exception.MandatoryException;
import com.bys.crm.domain.erp.constant.ADObjectType;
import com.bys.crm.domain.erp.constant.ActivityItem;
import com.bys.crm.domain.erp.constant.ContactItem;
import com.bys.crm.domain.erp.constant.CustomerItem;
import com.bys.crm.domain.erp.constant.ProspectItem;

public class FileUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);
	private static final String MANDATORY_SYMBOL = "*";

	private FileUtil() {
		super();
	}

	@SuppressWarnings("deprecation")
	public static <T> List<T> convertExcelToObject(String path, Class<T> type, String objectType) {
		List<T> result = new ArrayList<>();
		FileInputStream fis = null;
		XSSFWorkbook workbook = null;
		String columnNm = null;
		String cellNm = null;
		String itemName = null;
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Get iterator to all the rows in current sheet
			Iterator<Row> iterator = sheet.iterator();
			// First row is header to identify DB column name
			Row first = iterator.next();
			List<String> columnNames = new ArrayList<>();
			Iterator<Cell> cellIterator = first.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				columnNames.add(cell.getStringCellValue());
			}

			while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				cellIterator = nextRow.cellIterator();
				T object = type.newInstance();
				boolean mandatoryItemFlg = false;
				for (String columnName : columnNames) {
					if (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						columnNm = columnName;
						cellNm = cell.getAddress().toString();

						// Get item name
						if (ADObjectType.Prospect.name().equals(objectType)) {
							itemName = ProspectItem.fromValue(columnName).name();
						} else if (ADObjectType.Customer.name().equals(objectType)) {
							itemName = CustomerItem.fromValue(columnName).name();
						} else if (ADObjectType.Contact.name().equals(objectType)) {
							itemName = ContactItem.fromValue(columnName).name();
						} else if (ADObjectType.Activity.name().equals(objectType)) {
							itemName = ActivityItem.fromValue(columnName).name();
						}

						for (Field field : type.getDeclaredFields()) {
							if (DomainEntityUtil.getDatabaseColumnName(field).equals(itemName)) {
								boolean accessible = field.isAccessible();
								field.setAccessible(true);
								// Check mandatory item is empty or not
								if (columnName.contains(MANDATORY_SYMBOL) && StringUtil.isEmpty(cell.toString())) {
									mandatoryItemFlg = true;
								}
								switch (cell.getCellType()) {
								case Cell.CELL_TYPE_STRING:
									// Get cell value and set to dto
									if (field.getType().getName().contains("DateTime")
											&& StringUtil.isNotEmpty(cell.getStringCellValue())) {
										field.set(object,
												DateTime.parse(
														cell.getStringCellValue().length() == 10
																? cell.getStringCellValue().concat(" 00:00:00")
																: cell.getStringCellValue(),
														DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss")));
									} else {
										field.set(object, cell.getStringCellValue());
									}
									break;
								case Cell.CELL_TYPE_BOOLEAN:
									// Get cell value and set to dto
									field.set(object, cell.getBooleanCellValue());
									break;
								case Cell.CELL_TYPE_NUMERIC:
									// Get cell value and set to dto
									if (StringUtil.isNotEmpty(cell.toString())) {
										if (field.getType().getName().contains("BigDecimal")) {
											field.set(object,
													new BigDecimal(Double.valueOf(cell.getNumericCellValue())));
										} else if (field.getType().getName().contains("BranchSummaryDto")) {
											field.set(object, new BranchSummaryDto(
													Double.valueOf(cell.getNumericCellValue()).intValue()));
										} else if (field.getType().getName().contains("CustomerResourcesDto")) {
											field.set(object, new CustomerResourcesDto(
													Double.valueOf(cell.getNumericCellValue()).longValue()));
										}
										else {
											field.set(object, Double.valueOf(cell.getNumericCellValue()).longValue());
										}
									}

									break;
								}
								field.setAccessible(accessible);
							}
						}
					}
				}

				if (!isAllElementNull(object, objectType)) {
					if (mandatoryItemFlg) {
						throw new MandatoryException("Exist one or more a mandatory item is empty");
					}
					result.add(object);
					System.out.println();
				}
			}
			return result;
		} catch (MandatoryException e) {
			throw new InvalidException(e.getMessage(), ErrorCodeEnum.INVALID_VALUE);
		} catch (Exception e) {
			throw new InvalidException(
					columnNm + "," + cellNm,
					ErrorCodeEnum.INVALID_VALUE);
		} finally {
			try {
				workbook.close();
				fis.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	public static String toString(File file) {
		try {
			return new String(Files.readAllBytes(file.toPath()));
		} catch (IOException e) {
			LOGGER.error("Cannot read file:: {}", file.getPath(), e);
			return null;
		}
	}

	public static InputStream getInputStream(String path) {
		try {
			return FileUtil.class.getClassLoader().getResourceAsStream(path);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Path getUploadPath(MultipartFile file) {
		// Validation file
		if (file == null) {
			throw new InvalidException("File is required.", ErrorCodeEnum.INVALID_REQUEST);
		}

		Path uploadPath = null;
		try {
			// Get the file and save it to path
			byte[] bytes = file.getBytes();
			String resourcePath = Thread.currentThread().getContextClassLoader().getResource("upload").getPath();
			uploadPath = Paths
					.get(resourcePath.substring(1, resourcePath.length()).replace("%20", " ").replace("//", "/")
							+ file.getOriginalFilename());
			Files.write(uploadPath, bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return uploadPath;
	}

	public static <T> boolean isAllElementNull(T object, String objectType) {
		try {
			for (Field field : object.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				if (field.get(object) != null && field.get(object).toString() != "false") {
					return false;
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return true;
	}
}
