package com.BridgeIt.FundooApp.user.Dto;

public class LoginDto {
	private String emailId;
	private String password;
	
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "LoginDto [emailId=" + emailId + ", password=" + password + "]";
	}
	/**
	 * 
	 */
	public LoginDto() {

	}
	/**
	 * @param emailId
	 * @param password
	 */
	public LoginDto(String emailId, String password) {
		super();
		this.emailId = emailId;
		this.password = password;
	}
	

}
