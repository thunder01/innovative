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
    private Timestamp time;
    //行为：上传 下载 编辑 删除 点赞 收藏
    private String action;
    //分类: {专家,技术报告,方案,合作机构,行业协会,仪器设备,科技资讯,科技专栏,
    private String type;
    //相关的id,比如下载文件,就记录文件id,需求接单就记录需求id
    private String relateid;
    //相关联的名称
    private String  relatename;

    public Integer getId() {
        return id;
    }

    public LoggerUser(String userid, String action, String type, String relateid, String relatename) {
        this.userid = userid;
        this.action = action;
        this.type = type;
        this.relateid = relateid;
        this.relatename = relatename;
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

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
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
                '}';
    }
}
