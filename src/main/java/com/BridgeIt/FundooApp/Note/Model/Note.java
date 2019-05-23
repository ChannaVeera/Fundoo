package com.BridgeIt.FundooApp.Note.Model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.BridgeIt.FundooApp.Label.Model.Label;

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
	
	@DBRef
	private List<Label>labels;
	
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
	
	public List<Label> getLabels() {
		return labels;
	}
	public void setLabels(List<Label> labels) {
		this.labels = labels;
	}
	public void setPin(boolean isPin) {
		this.isPin = isPin;
	}
	
	@Override
	public String toString() {
		return "Note [noteId=" + noteId + ", userId=" + userId + ", title=" + title + ", description=" + description
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", trash=" + trash + ", archive="
				+ archive + ", isPin=" + isPin + ", labels=" + labels + "]";
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
	 * @param labels
	 */
	public Note(String noteId, String userId, String title, String description, String createTime, String updateTime,
			boolean trash, boolean archive, boolean isPin, List<Label> labels) {
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
		this.labels = labels;
	}
	
	
	

}
