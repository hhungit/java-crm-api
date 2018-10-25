package com.bys.crm.app.dto;

public class FengShuisDto {

	private FengShuisGenaralDto fengShuisGenaralDto;

	private FengShuisDirectionDto fengShuisDirectionDto;

	private FengShuisColorDto fengShuisColorDto;

	public FengShuisGenaralDto getFengShuisGenaralDto() {
		return fengShuisGenaralDto;
	}

	public void setFengShuisGenaralDto(FengShuisGenaralDto fengShuisGenaralDto) {
		this.fengShuisGenaralDto = fengShuisGenaralDto;
	}

	public FengShuisDirectionDto getFengShuisDirectionDto() {
		return fengShuisDirectionDto;
	}

	public void setFengShuisDirectionDto(FengShuisDirectionDto fengShuisDirectionDto) {
		this.fengShuisDirectionDto = fengShuisDirectionDto;
	}

	public FengShuisColorDto getFengShuisColorDto() {
		return fengShuisColorDto;
	}

	public void setFengShuisColorDto(FengShuisColorDto fengShuisColorDto) {
		this.fengShuisColorDto = fengShuisColorDto;
	}

	public FengShuisDto(FengShuisGenaralDto fengShuisGenaralDto, FengShuisDirectionDto fengShuisDirectionDto,
			FengShuisColorDto fengShuisColorDto) {
		super();
		this.fengShuisGenaralDto = fengShuisGenaralDto;
		this.fengShuisDirectionDto = fengShuisDirectionDto;
		this.fengShuisColorDto = fengShuisColorDto;
	}

}
