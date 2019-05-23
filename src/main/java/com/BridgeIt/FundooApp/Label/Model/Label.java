package com.BridgeIt.FundooApp.Label.Model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.BridgeIt.FundooApp.Note.Model.Note;

@Document
public class Label {
	@Id
	private String labelId;
	private String lableName;
	private String userId;
	private String createTime;
	private String updateTime;
	private List<Note> notes;
	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	public String getLableName() {
		return lableName;
	}

	public void setLableName(String lableName) {
		this.lableName = lableName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "Label [labelId=" + labelId + ", lableName=" + lableName + ", userId=" + userId + ", createTime="
				+ createTime + ", updateTime=" + updateTime + "]";
	}

	/**
	 * 
	 */
	public Label() {
	}

	/**
	 * @param labelId
	 * @param lableName
	 * @param userId
	 * @param createTime
	 * @param updateTime
	 * @param notes
	 */
	public Label(String labelId, String lableName, String userId, String createTime, String updateTime,
			List<Note> notes) {
		super();
		this.labelId = labelId;
		this.lableName = lableName;
		this.userId = userId;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.notes = notes;
	}

	

}
