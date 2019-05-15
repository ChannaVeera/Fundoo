package com.BridgeIt.FundooApp.user.Dto;

public class ForgetPasswordDto {
	private String password;

	private String confirmPassword;
public ForgetPasswordDto() {
		
	}
public ForgetPasswordDto(String password, String confirmpassword) {
	super();
	this.password = password;
	confirmPassword = confirmpassword;
}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmpassword() {
		return confirmPassword;
	}
	public void setConfirmpassword(String confirmpassword) {
		confirmPassword = confirmpassword;
	}
	@Override
	public String toString() {
		return "ForgetPasswordDto [password=" + password + ", confirmPassword=" + confirmPassword + "]";
	}
	


}
