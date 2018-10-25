package com.bys.crm.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.bys.crm.app.dto.ChartDto;

public class ChartUtil {

	public static List<ChartDto> buildChartByMonth(DateTime startDate, DateTime endDate, Map<DateTime, Long> counting){
		List<ChartDto> dtos = new ArrayList<>();
		startDate = DateTimeUtil.toDateTimeAtStartOfDay(startDate.getMillis()).withDayOfMonth(1);
		endDate = DateTimeUtil.toDateTimeAtStartOfDay(endDate.getMillis()).withDayOfMonth(1);
		while (startDate.isBefore(endDate) || startDate.isEqual(endDate)) {
			if(counting.get(startDate) == null){
				dtos.add(new ChartDto(startDate,Float.valueOf(0)));
			} else{
				dtos.add(new ChartDto(startDate,counting.get(startDate).floatValue()));
			}
			startDate = startDate.plusMonths(1);
		}
		return dtos;
	}
	
	public static List<ChartDto> buildChartByYear(DateTime startDate, DateTime endDate, Map<DateTime, Long> counting){
		List<ChartDto> dtos = new ArrayList<>();
		startDate = DateTimeUtil.toDateTimeAtStartOfDay(startDate.getMillis()).withDayOfMonth(1).withMonthOfYear(1);
		endDate = DateTimeUtil.toDateTimeAtStartOfDay(endDate.getMillis()).withDayOfMonth(1).withMonthOfYear(1);
		while (startDate.isBefore(endDate) || startDate.isEqual(endDate)) {
			if(counting.get(startDate) == null){
				dtos.add(new ChartDto(startDate,Float.valueOf(0)));
			} else{
				dtos.add(new ChartDto(startDate,counting.get(startDate).floatValue()));
			}
			startDate = startDate.plusYears(1);
		}
		return dtos;
	}
}
