package com.innovative.bean;

import java.util.List;

public class Demand {
    private  int id;//uuid
    private  String name;//项目名称
    private  String number;//项目编号
    private  String iphone;//联系方式
    private  String xqf;//需求方
    private  String createDate;//创建时间
    private  String cteateBy;//创建人
    private  String lateDate;//修改时间
    private  String lateBy;//修改人
    private  String bgd;//背景描述
    private  String demandd;//需求的具体描述
    private  String interestpg;//可能解决方案
    private  String ninterestpg;//不感兴趣的方案
    private  String []gjc;//关键词
    private  String hzms;//合作模式
    private  String hzrequest;//对合作方要求
    private  String productm;//项目资金预算
    private  String productDate;//项目完成时间
    private  String bz;//备注
    private  String checkName;//审核人姓名
    private  String ddmj;//订单密级
    private  String option;//审批意见
    private  String status;//状态
    private  String path;//文件路径
    private  String []userName;//自定用户名
    private List<FileBean> list;


    public Demand(int id, String name, String number, String iphone, String createDate, String cteateBy, String lateDate, String lateBy, String bgd, String demandd, String interestpg, String ninterestpg, String []gjc, String hzms, String hzrequest, String productm, String productDate, String bz, String checkName, String ddmj, String option, String status,String path,String xqf,String [] userName) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.iphone = iphone;
        this.createDate = createDate;
        this.cteateBy = cteateBy;
        this.lateDate = lateDate;
        this.lateBy = lateBy;
        this.bgd = bgd;
        this.demandd = demandd;
        this.interestpg = interestpg;
        this.ninterestpg = ninterestpg;
        this.gjc = gjc;
        this.hzms = hzms;
        this.hzrequest = hzrequest;
        this.productm = productm;
        this.productDate = productDate;
        this.bz = bz;
        this.checkName = checkName;
        this.ddmj = ddmj;
        this.option = option;
        this.status = status;
        this.path=path;
        this.xqf=xqf;
        this.userName=userName;
    }

    public Demand() {

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIphone() {
        return iphone;
    }

    public void setIphone(String iphone) {
        this.iphone = iphone;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCteateBy() {
        return cteateBy;
    }

    public void setCteateBy(String cteateBy) {
        this.cteateBy = cteateBy;
    }

    public String getLateDate() {
        return lateDate;
    }

    public void setLateDate(String lateDate) {
        this.lateDate = lateDate;
    }

    public String getLateBy() {
        return lateBy;
    }

    public void setLateBy(String lateBy) {
        this.lateBy = lateBy;
    }

    public String getBgd() {
        return bgd;
    }

    public void setBgd(String bgd) {
        this.bgd = bgd;
    }

    public String getDemandd() {
        return demandd;
    }

    public void setDemandd(String demandd) {
        this.demandd = demandd;
    }

    public String getInterestpg() {
        return interestpg;
    }

    public void setInterestpg(String interestpg) {
        this.interestpg = interestpg;
    }

    public String getNinterestpg() {
        return ninterestpg;
    }

    public void setNinterestpg(String ninterestpg) {
        this.ninterestpg = ninterestpg;
    }

    public String []getGjc() {
        return gjc;
    }

    public void setGjc(String []gjc) {
        this.gjc = gjc;
    }

    public String getHzms() {
        return hzms;
    }

    public void setHzms(String hzms) {
        this.hzms = hzms;
    }

    public String getHzrequest() {
        return hzrequest;
    }

    public void setHzrequest(String hzrequest) {
        this.hzrequest = hzrequest;
    }

    public String getProductm() {
        return productm;
    }

    public void setProductm(String productm) {
        this.productm = productm;
    }

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public String getDdmj() {
        return ddmj;
    }

    public void setDdmj(String ddmj) {
        this.ddmj = ddmj;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getXqf() {
        return xqf;
    }

    public void setXqf(String xqf) {
        this.xqf = xqf;
    }

    public String[] getUserName() {
        return userName;
    }

    public void setUserName(String[] userName) {
        this.userName = userName;
    }

    public List<FileBean> getList() {
        return list;
    }

    public void setList(List<FileBean> list) {
        this.list = list;
    }
}
