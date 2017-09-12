package com.innovative.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId ;//用户id
	private String userName ;//姓名
	private Integer userIntegration ;//积分
	private String userPost ;//用户职位
	private Timestamp createAt ;//创建时间
	private String userSex ;//性别
	private int userAge ;//年龄
	public User() {
			
		}
	
		
	


	public User(String userId, String userName, Integer userIntegration, String userPost, Timestamp createAt,
			String userSex, int userAge) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userIntegration = userIntegration;
		this.userPost = userPost;
		this.createAt = createAt;
		this.userSex = userSex;
		this.userAge = userAge;
	}





	public Timestamp getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Timestamp createAt) {
		this.createAt = createAt;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getUserIntegration() {
		return userIntegration;
	}
	public void setUserIntegration(Integer userIntegration) {
		this.userIntegration = userIntegration;
	}
	public String getUserPost() {
		return userPost;
	}
	public void setUserPost(String userPost) {
		this.userPost = userPost;
	}
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}





	public int getUserAge() {
		return userAge;
	}





	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}

	
	
	
	
}
