package com.bys.crm.app.dto;

import org.joda.time.DateTime;

public class ChartDto {
	private DateTime paymentDate;
	private Float amount;
			
	public ChartDto() {
		super();
	}
		
	public ChartDto(DateTime paymentDate, Float amount) {
		super();
		this.paymentDate = paymentDate;
		this.amount = amount;
	}

	public DateTime getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(DateTime paymentDate) {
		this.paymentDate = paymentDate;
	}
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
}
