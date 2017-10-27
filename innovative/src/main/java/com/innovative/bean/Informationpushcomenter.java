package com.innovative.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;


//信息推特评论
public class Informationpushcomenter implements Serializable {


	private static final long serialVersionUID = 7867013951701463970L;
	private String id;//主键
	private String pid; //父id
	private Timestamp comentAt;   //评论时间
	private String comentBy;      //评论人 id
	private String cotent;    //内容
	private String pushId;    //推特信息id
	private String path;    //234//345
	private String comentByC;    //评论人姓名
	private String type;    //0评论 1回复
	private List<Informationpushcomenter> Informationpushcomenters;   //此评论的所有的回复
	
	public Informationpushcomenter(){
		
	}

	public Informationpushcomenter(String id, String pid, Timestamp comentAt, String comentBy, String cotent,
			String pushId, String path, String comentByC, String type,
			List<Informationpushcomenter> informationpushcomenters) {
		this.id = id;
		this.pid = pid;
		this.comentAt = comentAt;
		this.comentBy = comentBy;
		this.cotent = cotent;
		this.pushId = pushId;
		this.path = path;
		this.comentByC = comentByC;
		this.type = type;
		Informationpushcomenters = informationpushcomenters;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Timestamp getComentAt() {
		return comentAt;
	}

	public void setComentAt(Timestamp comentAt) {
		this.comentAt = comentAt;
	}

	public String getComentBy() {
		return comentBy;
	}

	public void setComentBy(String comentBy) {
		this.comentBy = comentBy;
	}

	public String getCotent() {
		return cotent;
	}

	public void setCotent(String cotent) {
		this.cotent = cotent;
	}

	public String getPushId() {
		return pushId;
	}

	public void setPushId(String pushId) {
		this.pushId = pushId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getComentByC() {
		return comentByC;
	}

	public void setComentByC(String comentByC) {
		this.comentByC = comentByC;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Informationpushcomenter> getInformationpushcomenters() {
		return Informationpushcomenters;
	}

	public void setInformationpushcomenters(List<Informationpushcomenter> informationpushcomenters) {
		Informationpushcomenters = informationpushcomenters;
	}

	

}
