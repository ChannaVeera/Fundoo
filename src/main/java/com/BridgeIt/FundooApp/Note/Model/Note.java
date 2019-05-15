package com.BridgeIt.FundooApp.Note.Model;

import org.springframework.data.annotation.Id;

public class Note {
	@Id
	private String noteId;
	private String userId;
	private String title;
	private String description;
	private String createTime;
	private String updateTime;
	private boolean trash;
	private boolean archive;
	private boolean isPin;
	
	public String getNoteId() {
		return noteId;
	}
	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public boolean isTrash() {
		return trash;
	}
	public void setTrash(boolean trash) {
		this.trash = trash;
	}
	public boolean isArchive() {
		return archive;
	}
	public void setArchive(boolean archive) {
		this.archive = archive;
	}
	public boolean isPin() {
		return isPin;
	}
	public void setPin(boolean isPin) {
		this.isPin = isPin;
	}
	/**
	 * 
	 */
	public Note() {
		
	}
	/**
	 * @param noteId
	 * @param userId
	 * @param title
	 * @param description
	 * @param createTime
	 * @param updateTime
	 * @param trash
	 * @param archive
	 * @param isPin
	 */
	public Note(String noteId, String userId, String title, String description, String createTime, String updateTime,
			boolean trash, boolean archive, boolean isPin) {
		super();
		this.noteId = noteId;
		this.userId = userId;
		this.title = title;
		this.description = description;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.trash = trash;
		this.archive = archive;
		this.isPin = isPin;
	}
	
	

}
