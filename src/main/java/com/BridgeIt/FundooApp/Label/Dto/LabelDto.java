package com.BridgeIt.FundooApp.Label.Dto;

public class LabelDto {
	private String lableName;

	public String getLableName() {
		return lableName;
	}

	public void setLableName(String lableName) {
		this.lableName = lableName;
	}

	
	@Override
	public String toString() {
		return "LabelDto [lableName=" + lableName + "]";
	}

	/**
	 * 
	 */
	public LabelDto() {

	}

	/**
	 * @param lableName
	 */
	public LabelDto(String lableName) {

		this.lableName = lableName;
	}

}
