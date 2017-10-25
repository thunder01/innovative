package com.innovative.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量处理类
 */
public class Config {


    //文件上传路径(测试)
    public static final  String FILE_URL = "/data/images/";
   //二期的文件上传路径
    public static final String FILE2_URL = "/data/wwwroot/default/attachments/file/";

    //文件上传路径(正式)
    //public static final  String FILE_URL = "/data/file/";
    
   
    
    //立项申请模块SID
    //健康研究院-立项申请模块
    public static final String jkyjy_lxsq = "732A2632-6B94-4888-AA75-1D34907CDB4C";
    //研发项目管理系统（产业层）
    public static final String cyc_lxsq = "73D49E04-09B2-4C0F-9B7A-E47878760236";
    //研发项目管理系统（成员公司）-立项申请模块
    public static final String cygs_lxsq = "D9DE6DBA-9093-4995-A020-35E3A068DBAD";
    //石墨烯（成员公司）-立项申请模块
    public static final String smx_lxsq = "756296A6-AC08-4D68-ADFE-8D1F48F43A5E";
    
    //验收申请模块SID
    //健康研究院
    public static final String jkyjy_yssq = "91CCAE6E-58C5-4143-B7AB-F3F1734536B8";
    //研发项目管理系统（产业层）
    public static final String cyc_yssq = "BE9B9A3F-9429-49D1-96B8-A1C5F2AE5025";
    //研发项目管理系统（成员公司）-立项申请模块
    public static final String cygs_yssq = "0C27E3C0-37C0-498C-B52F-F74F5EDC4193";
    //石墨烯（成员公司）-立项申请模块
    public static final String smx_yssq = "D2B2B827-7CAA-4352-8238-5464604A195B";
    
    //风险登记册模块SID
    //健康研究院
    public static final String jkyjy_fxdj = "70D89E1E-6AE5-47A3-B35E-93F6E332C7C9";
    //研发项目管理系统（产业层）
    public static final String cyc_fxdj = "C10DA63C-0910-4C67-AB14-A6C7F9280EAC";
    //研发项目管理系统（成员公司）-立项申请模块
    public static final String cygs_fxdj = "6FA5707A-B92C-4CBF-9C4F-54213CD5067C";
    //石墨烯（成员公司）-立项申请模块
    public static final String smx_fxdj = "3383F032-1E4D-44E0-9C81-2E5D0CA7A099";
    
    
    //有效创意模块SID
    //健康研究院
    public static final String jkyjy_yxcy = "A033C3A4-BE81-45A3-AFF6-D0FDE14D8339";
    //研发项目管理系统（产业层）
    public static final String cyc_yxcy = "A189F2B5-850C-4BD9-B4D7-77B8684C730A";
    //研发项目管理系统（成员公司）-立项申请模块
    public static final String cygs_yxcy = "B966B657-9CBA-4F7C-81BB-1FC69809DF32";
    //石墨烯（成员公司）-立项申请模块
    public static final String smx_yxcy = "5CDF7DAC-8567-4FE1-909D-EE41434F6A4E";
    
   //健康研究院
    public static final Map<String,String> jkyjymap = new HashMap<String,String>();
    //研发项目管理系统（产业层）
    public static final Map<String,String> cycmap = new HashMap<String,String>();
    //研发项目管理系统（成员公司）
    public static final Map<String,String> cygsmap = new HashMap<String,String>();
    
  
    public static final Map<String,String> smxmap = new HashMap<String,String>();
    static{
    	////研发项目管理系统（成员公司）
    	smxmap.put("lxsq", smx_lxsq);
    	smxmap.put("yssq", smx_yssq);
    	smxmap.put("fxdj", smx_fxdj);
    	smxmap.put("yxcy", smx_yxcy);
    	
    	//健康研究院
    	jkyjymap.put("lxsq", jkyjy_lxsq);
    	jkyjymap.put("yssq", jkyjy_yssq);
    	jkyjymap.put("fxdj", jkyjy_fxdj);
    	jkyjymap.put("yxcy", jkyjy_yxcy);
    	
    	 //研发项目管理系统（产业层）
    	cycmap.put("lxsq", cyc_lxsq);
    	cycmap.put("yssq", cyc_yssq);
    	cycmap.put("fxdj", cyc_fxdj);
    	cycmap.put("yxcy", cyc_yxcy);
    	//研发项目管理系统（成员公司）
    	cygsmap.put("lxsq", cygs_lxsq);
    	cygsmap.put("yssq", cygs_yssq);
    	cygsmap.put("fxdj", cygs_fxdj);
    	cygsmap.put("yxcy", cygs_yxcy);
     }
    //关于收藏数量的配置
    public static final  int COLLCT_NUM = 5;
    public static final  int COLLCT_STARTINDEX = 0;
    //信息推特文件标志 
    public static final  String INFORMATIONPUSHFILE = "informationpushFile";
    
}
