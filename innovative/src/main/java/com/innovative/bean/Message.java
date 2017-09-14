package com.innovative.bean;

public class Message {
    private  int  id; //主键
    private  int  projectId;//对应的项目ID
    private  String type;//类型
    private  String createDate;//创建时间


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    public Message() {
    }

    public Message(int id, int projectId, String type, String createDate) {
        this.id = id;
        this.projectId = projectId;
        this.type = type;
        this.createDate = createDate;
    }
}
