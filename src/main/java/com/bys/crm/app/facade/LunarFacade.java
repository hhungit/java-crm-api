package com.bys.crm.app.facade;

import org.springframework.stereotype.Service;

import com.bys.crm.app.dto.Lunar;
import com.bys.crm.app.dto.Solar;
import com.bys.crm.util.LunarSolarConverterUtil;

@Service
public class LunarFacade {

	public String getLunarYear(String solarYear) {
		Solar solar = new Solar();

		solar.setSolarDay(Integer.parseInt(solarYear.substring(0, 2)));
		solar.setSolarMonth(Integer.parseInt(solarYear.substring(2, 4)));
		solar.setSolarYear(Integer.parseInt(solarYear.substring(4, 8)));

		Lunar lunar = LunarSolarConverterUtil.SolarToLunar(solar);
		return LunarSolarConverterUtil.lunarYearToGanZhi(lunar.getLunarYear());
	}

}
