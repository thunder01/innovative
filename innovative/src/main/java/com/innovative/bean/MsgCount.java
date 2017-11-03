package com.innovative.bean;

import java.io.Serializable;

public class MsgCount implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userid;//用户id
	private int message_count;//最新的消息总数
	private int notice_count;//最新的通知消息数
	private int finish_count;//已办数
	private int unfinish_count;//代办数
	private int old_message_count;//上一次消息总数
	private int old_notice_count;//上一次通知消息数
	private int old_finish_count;//上一次已办数
	private int old_unfinish_count;//上一次代办数
	
	public MsgCount() {
		super();
	}
	public MsgCount(String userid, int message_count, int notice_count, int finish_count, int unfinish_count,
			int old_message_count, int old_notice_count, int old_finish_count, int old_unfinish_count) {
		super();
		this.userid = userid;
		this.message_count = message_count;
		this.notice_count = notice_count;
		this.finish_count = finish_count;
		this.unfinish_count = unfinish_count;
		this.old_message_count = old_message_count;
		this.old_notice_count = old_notice_count;
		this.old_finish_count = old_finish_count;
		this.old_unfinish_count = old_unfinish_count;
	}
	@Override
	public String toString() {
		return "MsgCount [userid=" + userid + ", message_count=" + message_count + ", notice_count=" + notice_count
				+ ", finish_count=" + finish_count + ", unfinish_count=" + unfinish_count + ", old_message_count="
				+ old_message_count + ", old_notice_count=" + old_notice_count + ", old_finish_count="
				+ old_finish_count + ", old_unfinish_count=" + old_unfinish_count + "]";
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getMessage_count() {
		return notice_count+finish_count+unfinish_count;
	}
	public void setMessage_count(int message_count) {
		this.message_count = message_count;
	}
	public int getNotice_count() {
		return notice_count;
	}
	public void setNotice_count(int notice_count) {
		this.notice_count = notice_count;
	}
	public int getFinish_count() {
		return finish_count;
	}
	public void setFinish_count(int finish_count) {
		this.finish_count = finish_count;
	}
	public int getUnfinish_count() {
		return unfinish_count;
	}
	public void setUnfinish_count(int unfinish_count) {
		this.unfinish_count = unfinish_count;
	}
	public int getOld_message_count() {
		return old_notice_count+old_finish_count+old_unfinish_count;
	}
	public void setOld_message_count(int old_message_count) {
		this.old_message_count = old_message_count;
	}
	public int getOld_notice_count() {
		return old_notice_count;
	}
	public void setOld_notice_count(int old_notice_count) {
		this.old_notice_count = old_notice_count;
	}
	public int getOld_finish_count() {
		return old_finish_count;
	}
	public void setOld_finish_count(int old_finish_count) {
		this.old_finish_count = old_finish_count;
	}
	public int getOld_unfinish_count() {
		return old_unfinish_count;
	}
	public void setOld_unfinish_count(int old_unfinish_count) {
		this.old_unfinish_count = old_unfinish_count;
	}
}
