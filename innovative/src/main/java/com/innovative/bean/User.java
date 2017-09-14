package com.innovative.bean;

import java.io.Serializable;

/**
 * userId 用户id
 * userName 用户姓名
 * userIntegration 用户积分 （这个参数为以后的用户积分做准备）
 * userPost 用户职位
 * userSex 性别 
 * userAge 年龄
 *  * jobNumber 工号
 */
import java.sql.Timestamp;

import javax.jws.WebService;
@WebService
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4148405984845574540L;
	private String userId ;//用户id
	private String userName ;//姓名
	private Integer userIntegration ;//积分
	private String userPost ;//用户职位
	private Timestamp createAt ;//创建时间
	private String userSex ;//性别
	private int userAge ;//年龄
	private String jobNumber ;//工号
	private String updateBy;//更改人
	private Timestamp updateAt;//更新时间
	private String userState;//状态
	public User() {
			
		}



	public User(String userId, String userName, Integer userIntegration, String userPost, Timestamp createAt,
			String userSex, int userAge, String jobNumber, String updateBy, Timestamp updateAt, String userState) {
		this.userId = userId;
		this.userName = userName;
		this.userIntegration = userIntegration;
		this.userPost = userPost;
		this.createAt = createAt;
		this.userSex = userSex;
		this.userAge = userAge;
		this.jobNumber = jobNumber;
		this.updateBy = updateBy;
		this.updateAt = updateAt;
		this.userState = userState;
	}



	public String getUserState() {
		return userState;
	}



	public void setUserState(String userState) {
		this.userState = userState;
	}



	public String getJobNumber() {
		return jobNumber;
	}



	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}



	public String getUpdateBy() {
		return updateBy;
	}



	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}



	public Timestamp getUpdateAt() {
		return updateAt;
	}



	public void setUpdateAt(Timestamp updateAt) {
		this.updateAt = updateAt;
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
