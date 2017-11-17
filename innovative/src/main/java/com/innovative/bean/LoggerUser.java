package com.innovative.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 记录用户在系统中的行为，如增删改操作等
 * Created by jacks on 2017/11/16.
 */
public class LoggerUser implements Serializable{
    //日志id，由数据库序列生成
    private Integer id;
    //用户id
    private String userid;
    //日志记录的时间
    private String time;
    //行为：上传 下载 编辑 删除 点赞 收藏
    private String action;
    //分类: {专家,技术报告,方案,合作机构,行业协会,仪器设备,科技资讯,科技专栏,
    private String type;
    //相关的id,比如下载文件,就记录文件id,需求接单就记录需求id
    private String relateid;
    //相关联的名称
    private String  relatename;
    //用户姓名
    private String username;
    //用户角色
    private String rolename;
    //日期 年-月-日
    private String riqi;
    //时间 时：分：秒
    private String shijian;

    public LoggerUser() {
    }

    public LoggerUser(String userid, String action, String type, String relateid, String relatename) {
        this.userid = userid;
        this.action = action;
        this.type = type;
        this.relateid = relateid;
        this.relatename = relatename;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRelateid() {
        return relateid;
    }

    public void setRelateid(String relateid) {
        this.relateid = relateid;
    }

    public String getRelatename() {
        return relatename;
    }

    public void setRelatename(String relatename) {
        this.relatename = relatename;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRiqi() {
        return this.time.substring(0,10);
    }

    public void setRiqi(String riqi) {
        this.riqi = this.time.substring(0,10);
    }

    public String getShijian() {
        return this.time.substring(11,19);
    }

    public void setShijian(String shijian) {
        this.shijian = this.time.substring(11,18);
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    @Override
    public String toString() {
        return "LoggerUser{" +
                "id=" + id +
                ", userid='" + userid + '\'' +
                ", time=" + time +
                ", action='" + action + '\'' +
                ", type='" + type + '\'' +
                ", relateid='" + relateid + '\'' +
                ", relatename='" + relatename + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
