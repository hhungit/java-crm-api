package com.bys.crm.app.facade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bys.crm.app.dto.CustomerInforDto;
import com.bys.crm.app.dto.DebtDetailDto;
import com.bys.crm.app.dto.DocumentEntryDto;
import com.bys.crm.app.dto.InvoiceItemDto;
import com.bys.crm.app.dto.ProductCustomerPriceDto;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.app.mapping.GenericMapper;
import com.bys.crm.domain.PageableResult;
import com.bys.crm.domain.erp.constant.AAStatus;
import com.bys.crm.domain.erp.constant.ARCustomerType;
import com.bys.crm.domain.erp.model.ACDocumentEntrys;
import com.bys.crm.domain.erp.model.ARInvoiceItems;
import com.bys.crm.domain.erp.model.HREmployees;
import com.bys.crm.domain.erp.model.ICProductCustomerPrices;
import com.bys.crm.domain.erp.repository.ACDocumentEntrysRepository;
import com.bys.crm.domain.erp.repository.ARInvoiceItemsRepository;
import com.bys.crm.domain.erp.repository.HREmployeesRepository;
import com.bys.crm.domain.erp.repository.ICProductCustomerPricesRepository;
import com.bys.crm.domain.erp.service.TestCallStoreProdecureService;
import com.bys.crm.util.StringUtil;

@Service
public class AnonymousFacade {

	@Autowired
	private HREmployeesRepository employeesRepository;

	@Autowired
	private ICProductCustomerPricesRepository productCustomerPricesRepository;

	@Autowired
	private ARInvoiceItemsRepository invoiceItemsRepository;

	@Autowired
	private ACDocumentEntrysRepository documentEntrysRepository;

	@Autowired
	private GenericMapper mapper;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private TestCallStoreProdecureService callStoreProdecureService;
	
	// get Customer Infor
	public CustomerInforDto getCustomerInfor(Integer employeeId, Long customerId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		List<ProductCustomerPriceDto> productCustomerPriceDtos = getProductCustomerPrices(customerId);

		InvoiceItemDto invoiceItemDto = getLastestInvoice(customerId);

		List<InvoiceItemDto> invoicesOfMonth = getInvoicesOfMonth(employeeId, customerId);
		
		String accountNo = "131";
		String objectType = ARCustomerType.Customer.name();
//		DateTime fromDt = new DateTime(-2211753600000L);
		DateTime toDt = new DateTime(4068144000000L);
		
		BigDecimal customerBalance = getEndBalance(customerId, accountNo, objectType, toDt);

		CustomerInforDto customerInforDto = new CustomerInforDto(productCustomerPriceDtos, invoiceItemDto,
				invoicesOfMonth, customerBalance);

		return customerInforDto;
	}

	private List<ProductCustomerPriceDto> getProductCustomerPrices(Long customerId) {
		List<ICProductCustomerPrices> productPrices = productCustomerPricesRepository
				.findByCustomerIdAndPriceTo(customerId, DateTime.now(), AAStatus.Alive.name(), AAStatus.Alive.name());

		List<ProductCustomerPriceDto> dtos = new ArrayList<>();

		productPrices.forEach(item -> {
			dtos.add(new ProductCustomerPriceDto(item.getId(), item.getProduct().getProductName(),
					item.getProduct().getProductNo(), item.getProductUnitPrice()));
		});

		return dtos;
	}

	private InvoiceItemDto getLastestInvoice(Long customerId) {
		List<ARInvoiceItems> invoiceItems = invoiceItemsRepository
				.findByInvoiceCustomerIdAndInvoiceInvoiceStatusAndStatusOrderByInvoiceInvoiceDateDesc(customerId,
						"Complete", AAStatus.Alive.name());

		if (!invoiceItems.isEmpty()) {
			return mapper.buildObject(invoiceItems.get(0), InvoiceItemDto.class);
		}

		return null;
	}

	public PageableResult<InvoiceItemDto> getInvoices(Integer employeeId, Long customerId, Integer pageNumber,
			Integer pageSize, String productNo, String invoiceStatus, String invoiceNo) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		Pageable pageRequest = new PageRequest(pageNumber, pageSize);

		Page<ARInvoiceItems> invoiceItems = invoiceItemsRepository
				.findByInvoiceCustomerIdAndInvoiceStatusAndStatusOrderByInvoiceInvoiceDateDesc(customerId,
						StringUtil.convertSearchKey(productNo), StringUtil.convertSearchKey(invoiceStatus),
						AAStatus.Alive.name(), AAStatus.Alive.name(), StringUtil.convertSearchKeyLike(invoiceNo),
						pageRequest);

		List<InvoiceItemDto> dtos = null;

		if (invoiceItems != null && invoiceItems.getContent() != null && !invoiceItems.getContent().isEmpty()) {
			dtos = invoiceItems.getContent().stream().map(item -> mapper.buildObject(item, InvoiceItemDto.class))
					.collect(Collectors.toList());
		}

		return new PageableResult<>(pageNumber, invoiceItems.getTotalPages(), invoiceItems.getTotalElements(), dtos);
	}

	private List<InvoiceItemDto> getInvoicesOfMonth(Integer employeeId, Long customerId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		DateTime fromDate = DateTime.now().withDayOfMonth(1).withTimeAtStartOfDay();
		
		DateTime toDate = fromDate.plusMonths(1).minusDays(1);

		List<Object[]> invoiceItems = invoiceItemsRepository.getInvoiceProductQtyOfMonth(customerId, fromDate, toDate,
				AAStatus.Alive.name(), AAStatus.Alive.name());

		return invoiceItems.stream().map(item -> buildInvoiceItem(item)).collect(Collectors.toList());
	}

	private InvoiceItemDto buildInvoiceItem(Object[] object) {
		InvoiceItemDto dto = new InvoiceItemDto();
		dto.setProductName(object[0].toString());
		dto.setProductQty(Float.valueOf(object[1].toString()));

		return dto;
	}

	public PageableResult<DebtDetailDto> getDebtDetails(Integer employeeId, Long customerId, Long fromDate,
			Long toDate, Integer pageNumber, Integer pageSize) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		Pageable pageRequest = new PageRequest(pageNumber, pageSize);
		DateTime fromDt = (fromDate == null ? new DateTime(-2211753600000L) : new DateTime(fromDate));
		DateTime toDt = toDate == null ? new DateTime(4068144000000L) : new DateTime(toDate);
		String accountNo = "131";
		String objectType = ARCustomerType.Customer.name();

		Page<ACDocumentEntrys> documentEntrys = documentEntrysRepository.getDebtDetails(customerId, fromDt, toDt,
				AAStatus.Alive.name(), AAStatus.Alive.name(), pageRequest);

		List<DocumentEntryDto> dtos = null;

		if (documentEntrys != null && documentEntrys.getContent() != null && !documentEntrys.getContent().isEmpty()) {
			dtos = documentEntrys.getContent().stream().map(item -> mapper.buildObject(item, DocumentEntryDto.class))
					.collect(Collectors.toList());
		}

		//BigDecimal startBalance = getStartBalance(customerId, accountNo, objectType, fromDt.minusDays(1));
		//fix bug so du dau ky
		BigDecimal startBalance = getEndBalance(customerId, accountNo, objectType, fromDt.minusDays(1));
		
		BigDecimal endBalance = getEndBalance(customerId, accountNo, objectType, toDt);

		DebtDetailDto debtDetail = new DebtDetailDto(startBalance, endBalance, dtos);
		
		List<DebtDetailDto> debtDetails = new ArrayList<>();
		debtDetails.add(debtDetail);

		return new PageableResult<>(pageNumber, documentEntrys.getTotalPages(), documentEntrys.getTotalElements(),
				debtDetails);
	}

	// private DebtDetailDto buildDebtDetail(Object[] object) {
	// DebtDetailDto dto = new DebtDetailDto(new DateTime(object[0]),
	// object[1].toString(), object[2].toString(),
	// Float.valueOf(object[3].toString()), Float.valueOf(object[4].toString()),
	// Long.valueOf(object[5].toString()), Long.valueOf(object[6].toString()),
	// Long.valueOf(object[7].toString()), Long.valueOf(object[8].toString()),
	// object[9].toString());
	//
	// return dto;
	// }

	private BigDecimal getStartBalance(Long customerId, String accountNo, String objectType, DateTime fromDate) {

		return callStoreProdecureService.getStartBalance(customerId, accountNo, objectType,
				fromDate.toString("yyyy-MM-dd"));
	}
	
	private BigDecimal getEndBalance(Long customerId, String accountNo, String objectType, DateTime toDate) {

		return callStoreProdecureService.getEndBalance(customerId, accountNo, objectType,
				toDate.toString("yyyy-MM-dd"));
	}

}
