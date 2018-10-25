package com.bys.crm.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bys.crm.app.facade.DownloadFileFacade;

@RestController
public class DownloadFileController extends BaseController {
	@Autowired
	private DownloadFileFacade facade;

	private static final String PROSPECT = "prospect";
	private static final String CUSTOMER = "customer";
	private static final String CONTACT = "contact";
	private static final String ACTIVITY = "activity";

	@RequestMapping(method = RequestMethod.GET, value = RestURL.DOWNLOAD_FILE)
	public ResponseEntity<InputStreamResource> importCustomerFromExcel(
			@PathVariable(value = "employeeId") int employeeId, @PathVariable("objectType") String objectType) {
		String fileName = null;
		switch (objectType) {
		case PROSPECT:
			fileName = "prospect_template.xlsm";
			break;
		case CUSTOMER:
			fileName = "customer_template.xlsm";
			break;
		case CONTACT:
			fileName = "contact_template.xlsm";
			break;
		case ACTIVITY:
			fileName = "activity_template.xlsm";
			break;
		}
		return ResponseEntity.ok()
				// Content-Disposition
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel.sheet.macroEnabled.12"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
				.body(facade.downloadFile(employeeId, objectType, fileName));
	}
}
