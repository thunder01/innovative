package com.innovative.bean;

public class TechnologyDz {
    private int id;
    private int project;
    private String create_date;
    private String create_by;

    public TechnologyDz(int id, int project, String create_date, String create_by) {
        this.id = id;
        this.project = project;
        this.create_date = create_date;
        this.create_by = create_by;
    }

    public TechnologyDz() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProject() {
        return project;
    }

    public void setProject(int project) {
        this.project = project;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }
}
