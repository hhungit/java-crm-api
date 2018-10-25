package com.bys.crm.domain.erp.constant;

public enum AAStatus {
	Alive("Alive"),
	ALive("ALive"),
	Dummy("Dummy"),
	Delete("Delete");
	
	private String dBCode;

	private AAStatus(String dBCode) {
		this.dBCode = dBCode;
	}

	public String value() {
		return this.dBCode;
	}

	public static AAStatus fromValue(String value) {
		for (AAStatus status : AAStatus.values()) {
			if (status.value().equalsIgnoreCase(value)) {
				return status;
			}
		}

		return null;
	}
}
