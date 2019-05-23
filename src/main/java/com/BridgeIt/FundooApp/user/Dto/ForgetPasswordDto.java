package com.BridgeIt.FundooApp.user.Dto;

public class ForgetPasswordDto {
	private String password;

public ForgetPasswordDto() {
		
	}
public ForgetPasswordDto(String password, String confirmpassword) {
	super();
	this.password = password;
}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "ForgetPasswordDto [password=" + password +  "]";
	}
	


}
