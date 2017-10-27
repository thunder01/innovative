package com.innovative.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;


//信息推特
public class Informationpush implements Serializable {


	private static final long serialVersionUID = 7867013951701463970L;
	private String id;//主键
	private String pid; //父id
	private Timestamp comentAt;   //评论时间
	private String comentBy;      //评论人 
	private String cotent;    //内容
	private String title;    //内容
	private int approuverNum; //赞的数量
	private int todayIsAprouver; //今天是否点赞 0代表没有点赞 1代表点赞
	private int transmettreNum;    //转发数量
	private int todayIsTransmettre;    //今天是否转发 0代表没有转发 1代表转发
	private int colletionNum;    //收藏数量
	private int todayIsColletion;    //今天是否收藏 0代表没有收藏 1代表已收藏
	private int commenterNum;    //评论数量
	private String path;    //234//345
	private String type;    //一条推特信息标志
	private int depth;    //深度，有几级
	List<FileBean> filelist ;//文件
	List<Informationpush> commentlist ;//评论
	
	public Informationpush(){
		
	}

	public Informationpush(String id, String pid, Timestamp comentAt, String comentBy, String cotent, String title,
			int approuverNum, int todayIsAprouver, int transmettreNum, int todayIsTransmettre, int colletionNum,
			int todayIsColletion, int commenterNum, String path, String type, int depth, List<FileBean> filelist,
			List<Informationpush> commentlist) {
		this.id = id;
		this.pid = pid;
		this.comentAt = comentAt;
		this.comentBy = comentBy;
		this.cotent = cotent;
		this.title = title;
		this.approuverNum = approuverNum;
		this.todayIsAprouver = todayIsAprouver;
		this.transmettreNum = transmettreNum;
		this.todayIsTransmettre = todayIsTransmettre;
		this.colletionNum = colletionNum;
		this.todayIsColletion = todayIsColletion;
		this.commenterNum = commenterNum;
		this.path = path;
		this.type = type;
		this.depth = depth;
		this.filelist = filelist;
		this.commentlist = commentlist;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getApprouverNum() {
		return approuverNum;
	}

	public void setApprouverNum(int approuverNum) {
		this.approuverNum = approuverNum;
	}

	public int getTodayIsAprouver() {
		return todayIsAprouver;
	}

	public void setTodayIsAprouver(int todayIsAprouver) {
		this.todayIsAprouver = todayIsAprouver;
	}

	public int getTransmettreNum() {
		return transmettreNum;
	}

	public void setTransmettreNum(int transmettreNum) {
		this.transmettreNum = transmettreNum;
	}

	public int getTodayIsTransmettre() {
		return todayIsTransmettre;
	}

	public void setTodayIsTransmettre(int todayIsTransmettre) {
		this.todayIsTransmettre = todayIsTransmettre;
	}

	public int getColletionNum() {
		return colletionNum;
	}

	public void setColletionNum(int colletionNum) {
		this.colletionNum = colletionNum;
	}

	public int getTodayIsColletion() {
		return todayIsColletion;
	}

	public void setTodayIsColletion(int todayIsColletion) {
		this.todayIsColletion = todayIsColletion;
	}

	public int getCommenterNum() {
		return commenterNum;
	}

	public void setCommenterNum(int commenterNum) {
		this.commenterNum = commenterNum;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public List<FileBean> getFilelist() {
		return filelist;
	}

	public void setFilelist(List<FileBean> filelist) {
		this.filelist = filelist;
	}

	public List<Informationpush> getCommentlist() {
		return commentlist;
	}

	public void setCommentlist(List<Informationpush> commentlist) {
		this.commentlist = commentlist;
	}
	




}