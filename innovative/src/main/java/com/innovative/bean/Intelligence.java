package com.innovative.bean;

import java.io.Serializable;

public class Intelligence implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  int id;//uuid
    private  String name;//项目名称
    private  String demand;//需求方
    private  String tell;//联系方式
    private  String status;//状态
    private  String number;//定制次数
    private  String frequency;//定制频率
    private  String startdate;//日期起
    private  String enddate;//日期止
    private  String content;//需求描述
    private  String createDate;//创建时间
    private  String createBy;//创建人
    private  String []userName;//自定用户名
    private  String userid;//接单人ID
    private  String ddmj;//订单密集
    private  String checkname;//审核人
    private  String projectId;//项目编号

    public Intelligence() {
    }

    public Intelligence(int id, String name, String demand, String tell, String status, String number, String frequency, String startdate, String enddate, String content, String createDate, String createBy, String[] userName, String userid, String ddmj, String checkname,String projectId) {
        this.id = id;
        this.name = name;
        this.demand = demand;
        this.tell = tell;
        this.status = status;
        this.number = number;
        this.frequency = frequency;
        this.startdate = startdate;
        this.enddate = enddate;
        this.content = content;
        this.createDate = createDate;
        this.createBy = createBy;
        this.userName = userName;
        this.userid = userid;
        this.ddmj = ddmj;
        this.checkname = checkname;
        this.projectId=projectId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public String getTell() {
        return tell;
    }

    public void setTell(String tell) {
        this.tell = tell;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String[] getUserName() {
        return userName;
    }

    public void setUserName(String[] userName) {
        this.userName = userName;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDdmj() {
        return ddmj;
    }

    public void setDdmj(String ddmj) {
        this.ddmj = ddmj;
    }

    public String getCheckname() {
        return checkname;
    }

    public void setCheckname(String checkname) {
        this.checkname = checkname;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
