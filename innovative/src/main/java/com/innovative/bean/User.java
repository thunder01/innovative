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
import java.util.List;

import javax.jws.WebService;
@WebService
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4148405984845574540L;
	private String userId ;//用户id
	private String userName ;//名字
	private String pernr ;//pernr(对应员工号)主键(在这里我们的这个字段用处不大)
	private String itcode ;//ITOCDE
	private String usrid_u ;//用户名拼音
	private String nachn ;//用户姓
	private String vorna ;//用户名
	private String gesch ;//性别
	private String teleno ;//办公电话
	private String mobile ;//移动电话
	private String mail ;//电子邮件
	private String werks ;//人事范围
	private String pbtxt ;//电子邮件
	private String plans ;//职位代码
	private String stext ;//职位名称
	private String orgeh ;//部门编号
	private String dstext ;//部门名称
	private String stat2 ;//是否离职（0代表离职，3代表在职）
	private String createAt ;//创建时间
	private String operaction ;//操作操作类型
	private String glstdm ;//管理实体代码
	private String glst ;//管理实体
	private String lzrq ;//离职日期
	private String updateAt ;//更新时间
	private String createBy ;//更新时间
	private String updateBy ;//更新时间
	private String roleId ;//更新时间
	private String sys_pass_flag ;//目前没用
	private List<String> roleIds;//状态
	public User() {
			
		}
	
	public User(String userId, String userName, String pernr, String itcode, String usrid_u, String nachn, String vorna,
			String gesch, String teleno, String mobile, String mail, String werks, String pbtxt, String plans,
			String stext, String orgeh, String dstext, String stat2, String createAt, String operaction, String glstdm,
			String glst, String lzrq, String updateAt, String createBy, String updateBy, String roleId,
			String sys_pass_flag, List<String> roleIds) {
		this.userId = userId;
		this.userName = userName;
		this.pernr = pernr;
		this.itcode = itcode;
		this.usrid_u = usrid_u;
		this.nachn = nachn;
		this.vorna = vorna;
		this.gesch = gesch;
		this.teleno = teleno;
		this.mobile = mobile;
		this.mail = mail;
		this.werks = werks;
		this.pbtxt = pbtxt;
		this.plans = plans;
		this.stext = stext;
		this.orgeh = orgeh;
		this.dstext = dstext;
		this.stat2 = stat2;
		this.createAt = createAt;
		this.operaction = operaction;
		this.glstdm = glstdm;
		this.glst = glst;
		this.lzrq = lzrq;
		this.updateAt = updateAt;
		this.createBy = createBy;
		this.updateBy = updateBy;
		this.roleId = roleId;
		this.sys_pass_flag = sys_pass_flag;
		this.roleIds = roleIds;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return nachn+vorna;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPernr() {
		return pernr;
	}
	public void setPernr(String pernr) {
		this.pernr = pernr;
	}
	public String getItcode() {
		return itcode;
	}
	public void setItcode(String itcode) {
		this.itcode = itcode;
	}
	public String getUsrid_u() {
		return usrid_u;
	}
	public void setUsrid_u(String usrid_u) {
		this.usrid_u = usrid_u;
	}
	public String getNachn() {
		return nachn;
	}
	public void setNachn(String nachn) {
		this.nachn = nachn;
	}
	public String getVorna() {
		return vorna;
	}
	public void setVorna(String vorna) {
		this.vorna = vorna;
	}
	public String getGesch() {
		return gesch;
	}
	public void setGesch(String gesch) {
		this.gesch = gesch;
	}
	public String getTeleno() {
		return teleno;
	}
	public void setTeleno(String teleno) {
		this.teleno = teleno;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getWerks() {
		return werks;
	}
	public void setWerks(String werks) {
		this.werks = werks;
	}
	public String getPbtxt() {
		return pbtxt;
	}
	public void setPbtxt(String pbtxt) {
		this.pbtxt = pbtxt;
	}
	public String getPlans() {
		return plans;
	}
	public void setPlans(String plans) {
		this.plans = plans;
	}
	public String getStext() {
		return stext;
	}
	public void setStext(String stext) {
		this.stext = stext;
	}
	public String getOrgeh() {
		return orgeh;
	}
	public void setOrgeh(String orgeh) {
		this.orgeh = orgeh;
	}
	public String getDstext() {
		return dstext;
	}
	public void setDstext(String dstext) {
		this.dstext = dstext;
	}
	public String getStat2() {
		return stat2;
	}
	public void setStat2(String stat2) {
		this.stat2 = stat2;
	}
	public String getCreateAt() {
		return createAt;
	}
	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}
	public String getOperaction() {
		return operaction;
	}
	public void setOperaction(String operaction) {
		this.operaction = operaction;
	}
	public String getGlstdm() {
		return glstdm;
	}
	public void setGlstdm(String glstdm) {
		this.glstdm = glstdm;
	}
	public String getGlst() {
		return glst;
	}
	public void setGlst(String glst) {
		this.glst = glst;
	}
	public String getLzrq() {
		return lzrq;
	}
	public void setLzrq(String lzrq) {
		this.lzrq = lzrq;
	}
	public String getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(String updateAt) {
		this.updateAt = updateAt;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getSys_pass_flag() {
		return sys_pass_flag;
	}
	public void setSys_pass_flag(String sys_pass_flag) {
		this.sys_pass_flag = sys_pass_flag;
	}
	public List<String> getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}
	
	
	
}
