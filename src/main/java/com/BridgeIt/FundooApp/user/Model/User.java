package com.BridgeIt.FundooApp.user.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;
@Document
public class User {
	@Id
	private String userId;
	private String name;
	private String emailId;
	private String phoneNumber;
	private String password;
	private String registerStamp;
	private String updateStamp;
	private boolean isVarified;
	@NonNull
	private String token;
	
	
	
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	//==================constructer==================\\
	public User() {
		super();
	}
	//=======\\
	/**
	 * @param userId
	 * @param name
	 * @param emailId
	 * @param password
	 * @param registerStamp
	 * @param updateStamp
	 * @param isVarified
	 */
	public User(String userId, String name, String emailId, String password, String registerStamp, String updateStamp,
			boolean isVarified) {
		super();
		this.userId = userId;
		this.name = name;
		this.emailId = emailId;
		this.password = password;
		this.registerStamp = registerStamp;
		this.updateStamp = updateStamp;
		this.isVarified = isVarified;
	}

	//=====================getters&setters====================\\
	public String getUserId() {
		return userId;
	}
	
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRegisterStamp() {
		return registerStamp;
	}
	public void setRegisterStamp(String registerStamp) {
		this.registerStamp = registerStamp;
	}
	public String getUpdateStamp() {
		return updateStamp;
	}
	public void setUpdateStamp(String updateStamp) {
		this.updateStamp = updateStamp;
	}
	public boolean isVarified() {
		return isVarified;
	}
	public void setVarified(boolean isVarified) {
		this.isVarified = isVarified;
	}

}
