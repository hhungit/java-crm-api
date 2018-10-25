package com.bys.crm.app.facade;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.domain.erp.constant.AAStatus;
import com.bys.crm.domain.erp.model.HREmployees;
import com.bys.crm.domain.erp.repository.HREmployeesRepository;

@Service
public class DownloadFileFacade {
	@Autowired
	private HREmployeesRepository employeesRepository;

	// Import customer from excel file
	@Transactional
	public InputStreamResource downloadFile(Integer employeeId, String objectType, String fileName) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		String resourcePath = Thread.currentThread().getContextClassLoader().getResource("upload").getPath();
		Path downloadPath = Paths
				.get(resourcePath.substring(1, resourcePath.length()).replace("%20", " ").replace("//", "/"));
		File file = new File(downloadPath + "/" + fileName);
		InputStreamResource resource = null;
		try {
			resource = new InputStreamResource(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return resource;
	}

}
