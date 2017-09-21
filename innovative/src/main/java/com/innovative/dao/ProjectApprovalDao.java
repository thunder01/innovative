package com.innovative.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.innovative.bean.ProjectApproval;


/**
 * @author jacks
 *
 */
public interface ProjectApprovalDao {
	/**
	 * 根据寻源工程师的id查询相应的立项信息
	 * @param userid 寻源工程师id
	 * @return
	 * */
	public List<ProjectApproval> selectApprovalListByUserId(@Param("userid") String userid,@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);
	
	/**
	 * 根据订单id查询所有立项表单信息
	 * @param
	 * @return
	 * */
	public List<ProjectApproval> getApprovalListByOrderid(Integer id);
	
	/**
	 * 添加立项表单信息
	 * @param pApproval 立项信息
	 * @return
	 * */
	public int addProjectApproval(ProjectApproval pApproval);
	
	/**
	 * 发布立项至需求广场
	 * @param id 立项id
	 * @return
	 */
	public int postApproval(Integer id);
	
	/**
	 * 需求广场(寻源需求)
	 * @return
	 */
	public List<ProjectApproval> selectSquare(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

	/**
	 * 查询所有已发布的立项条数
	 * @return
	 */
	public int getTotalCount();
	
	/**
	 * 寻源工程师接单数
	 * @param source_id
	 * @return
	 */
	public int getSourceCount(String source_id);
	
	/**
	 * 根据id查询立项表单详情
	 * @param id
	 * @return
	 */
	public ProjectApproval findApprovalById(Integer id);
	
	/**
	 * 通过立项表单的id来查询确认用户id 
	 * @param id
	 * @return
	 */
	public String findUserIdByApp_id(Integer id);
	
	/**
	 * 寻源工程师下单
	 * @param id
	 * @return
	 */
	public int updateProjectApprovalReceive(Integer id);
	
	/**
	 * 根据表单id查询接单状态
	 * @param id 立项表单id
	 * @return
	 */
	public int findSource_statusById(Integer id);
	
	/**
	 * 通过订单的id查询寻源工程师的id数组 
	 * @param order_id
	 * @return
	 */
	public String[] findSource_idByOrder_id(Integer order_id);
}
