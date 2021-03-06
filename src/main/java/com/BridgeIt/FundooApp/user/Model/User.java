package com.BridgeIt.FundooApp.user.Model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


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

	private String image;

	// ==================constructer==================\\
	public User() {
		super();
	}
	// =======\\

	/**
	 * @param Id
	 * @param name
	 * @param emailId
	 * @param phoneNumber
	 * @param password
	 * @param registerStamp
	 * @param updateStamp
	 * @param isVarified
	 * @param token
	 * @param notes
	 */
	public User(String Id, String name, String emailId, String phoneNumber, String password, String registerStamp,
			String updateStamp, boolean isVarified) {
		super();
		this.userId = Id;
		this.name = name;
		this.emailId = emailId;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.registerStamp = registerStamp;
		this.updateStamp = updateStamp;
		this.isVarified = isVarified;

	}

	// =====================getters&setters====================\\
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", emailId=" + emailId + ", phoneNumber=" + phoneNumber
				+ ", password=" + password + ", registerStamp=" + registerStamp + ", updateStamp=" + updateStamp
				+ ", isVarified=" + isVarified +"]";
	}

}
