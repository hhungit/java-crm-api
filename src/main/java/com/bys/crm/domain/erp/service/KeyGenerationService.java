package com.bys.crm.domain.erp.service;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicLong;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bys.crm.domain.erp.constant.ADObjectType;
import com.bys.crm.domain.erp.model.GENumbering;
import com.bys.crm.domain.erp.repository.ADUsersRepository;
import com.bys.crm.domain.erp.repository.ADUserDevicesRepository;
import com.bys.crm.domain.erp.repository.ARCustomersRepository;
import com.bys.crm.domain.erp.repository.BaseRepository;
import com.bys.crm.domain.erp.repository.GENumberingRepository;

@Service
public class KeyGenerationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(KeyGenerationService.class);
	
	@Autowired
	private ARCustomersRepository arCustomersRepository;
	@Autowired
	private ADUsersRepository adPortalUsersRepository;
	@Autowired
	private ADUserDevicesRepository adUserDevicesRepository;
	@Autowired
	private GENumberingRepository geNumberingRepository;
	
	public Long getARCustomerId() {
		Long max = arCustomersRepository.findMaxId();
		max = (max == null) ? Long.valueOf(1) : Long.valueOf(max + 1);
		return max;
	}

	public Long getADPortalUserId() {
		Long max = adPortalUsersRepository.findMaxId();
		max = (max == null) ? Long.valueOf(1) : Long.valueOf(max + 1);
		return max;
	}
	
	public Long getADUserDeviceId() {
		Long max = adUserDevicesRepository.findMaxId();
		max = (max == null) ? Long.valueOf(1) : Long.valueOf(max + 1);
		return max;
	}
	
	public AtomicLong findMaxId(BaseRepository baseRepository) {
		Long max = baseRepository.findMaxId();
		return (max == null) ? new AtomicLong(0) : new AtomicLong(max);
	}

	@Transactional
	public synchronized String generateCustomerNumber() {
		GENumbering numbering = geNumberingRepository.findByNameAndBranchId(ADObjectType.Customer.name(), 1);
		int start = numbering.getStart();
		String prefix = numbering.getPrefix();

		numbering.setStart(start + 1);
		geNumberingRepository.save(numbering);

		int current = Calendar.getInstance().get(Calendar.YEAR);

		String year = (numbering.isHaveYear() != null && numbering.isHaveYear())
				? String.valueOf(current).substring(2, 4)
				: "";

		return prefix + year + "." + String.format("%06d", start);
	}
	
	@Transactional
	public synchronized String generateNumberNo(String name) {
		LOGGER.info("generateNumberNo "+name);
		GENumbering numbering = geNumberingRepository.findByNameAndBranchId(name, 1);
		int start = numbering.getStart();
		int length = numbering.getLength();
		String format = "%0" +length+"d";
		LOGGER.info("format "+format);
		String prefix = numbering.getPrefix();

		numbering.setStart(start + 1);
		geNumberingRepository.save(numbering);

		int current = Calendar.getInstance().get(Calendar.YEAR);

		String year = (numbering.isHaveYear() != null && numbering.isHaveYear())
				? String.valueOf(current).substring(2, 4)
				: "";
		return prefix + year + "." + String.format(format, start);
	}
	
	@Transactional
	public synchronized String generateEmployeeNumber() {
		GENumbering numbering = geNumberingRepository.findByNameAndBranchId(ADObjectType.Employee.name(), 1);
		int start = numbering.getStart();
		String prefix = numbering.getPrefix();

		numbering.setStart(start + 1);
		geNumberingRepository.save(numbering);

		int current = Calendar.getInstance().get(Calendar.YEAR);

		String year = (numbering.isHaveYear() != null && numbering.isHaveYear())
				? String.valueOf(current).substring(2, 4)
				: "";

		return prefix + year + "." + String.format("%06d", start);
	}
	
}
