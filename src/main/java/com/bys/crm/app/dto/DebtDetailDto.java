package com.bys.crm.app.dto;

import java.math.BigDecimal;
import java.util.List;

public class DebtDetailDto {

	private BigDecimal startBalance;
	
	private BigDecimal endBalance;
	
	private List<DocumentEntryDto> documentEntrys;

	public BigDecimal getStartBalance() {
		return startBalance;
	}

	public void setStartBalance(BigDecimal startBalance) {
		this.startBalance = startBalance;
	}

	public BigDecimal getEndBalance() {
		return endBalance;
	}

	public void setEndBalance(BigDecimal endBalance) {
		this.endBalance = endBalance;
	}

	public List<DocumentEntryDto> getDocumentEntrys() {
		return documentEntrys;
	}

	public void setDocumentEntrys(List<DocumentEntryDto> documentEntrys) {
		this.documentEntrys = documentEntrys;
	}

	public DebtDetailDto(BigDecimal startBalance, BigDecimal endBalance, List<DocumentEntryDto> documentEntrys) {
		super();
		this.startBalance = startBalance;
		this.endBalance = endBalance;
		this.documentEntrys = documentEntrys;
	}

//	private DateTime documentDate;
//
//	private String documentNo;
//
//	private String documentDesc;
//
//	private Float documentTotalAmount;
//
//	private Float documentExchangeAmount;
//
//	private Long documentTypeId;
//
//	private Long entryTypeId;
//
//	private Long debitAccountId;
//
//	private Long creditAccountId;
//
//	private String documentEntryDesc;
//
//	public DateTime getDocumentDate() {
//		return documentDate;
//	}
//
//	public DebtDetailDto(DateTime documentDate, String documentNo, String documentDesc, Float documentTotalAmount,
//			Float documentExchangeAmount, Long documentTypeId, Long entryTypeId, Long debitAccountId,
//			Long creditAccountId, String documentEntryDesc) {
//		super();
//		this.documentDate = documentDate;
//		this.documentNo = documentNo;
//		this.documentDesc = documentDesc;
//		this.documentTotalAmount = documentTotalAmount;
//		this.documentExchangeAmount = documentExchangeAmount;
//		this.documentTypeId = documentTypeId;
//		this.entryTypeId = entryTypeId;
//		this.debitAccountId = debitAccountId;
//		this.creditAccountId = creditAccountId;
//		this.documentEntryDesc = documentEntryDesc;
//	}

}