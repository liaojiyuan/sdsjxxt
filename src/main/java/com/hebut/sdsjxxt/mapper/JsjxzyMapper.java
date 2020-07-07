package com.hebut.sdsjxxt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hebut.sdsjxxt.dvo.JsjxzyJxnrCourseVO;
import com.hebut.sdsjxxt.dvo.JxzyTjVO;
import com.hebut.sdsjxxt.pojo.Jsjxzy;

/**
 * @author gaochaochao
 * @date 2019年5月15日 下午4:17:10
 * @description
 */
@Repository("jsjxzyMapper")
public interface JsjxzyMapper extends BaseMapper<Jsjxzy> {

	/**
	 * 教学作业、教学内容、课程相关联
	 * 
	 * @description
	 * @param @param
	 *            ew entityWrapper 条件构造器
	 * @param @return
	 * @return JsjxzyJxnrCourseVO
	 */
	List<JsjxzyJxnrCourseVO> getJsjxzyJxnrCourseVOByCondition(@Param("ew") Wrapper<JsjxzyJxnrCourseVO> ew);

	/**
	 * 
	 * @description 进行分页查询
	 * @param @param
	 *            rowBounds 分页对象 直接传入page即可
	 * @param @param
	 *            ew 条件构造器
	 * @param @return
	 * @return List<JsjxzyJxnrCourseVO>
	 */
	List<JsjxzyJxnrCourseVO> getJsjxzyJxnrCourseVOPageByCondition(RowBounds rowBounds,
			@Param("ew") Wrapper<JsjxzyJxnrCourseVO> ew);

	/**
	 * 批量更新教学作业状态
	 * 
	 * @description
	 * @param @param
	 *            jsjxzyList
	 * @param @return
	 * @return int
	 */
	int updateJxzyStatusBatchById(@Param("jxzyList") List<Jsjxzy> jsjxzyList);

	/**
	 * 根据 教学作业主键更新作业状态，传入两个参数
	 * 
	 * @description
	 * @param @param
	 *            id jxzy主键
	 * @param @param
	 *            status 新状态
	 * @param @return
	 * @return int
	 */
	int updateJxzyStatusById(@Param("id") Long id, @Param("status") Integer status);

	/**
	 * 更新教学作业状态
	 * 
	 * @description
	 * @param @param
	 *            jxzy 教学作业记录
	 * @param @return
	 * @return int
	 */
	int updateJxzyStatus(@Param("jxzy") Jsjxzy jxzy);

	/**
	 *  借助教学作业的id关联 获取课程的班级号
	 * @param id
	 * @return
	 */
	String getBanjiIdsByJsjxzyId(@Param("id")Long id);
	/**
	 * 获取教学作业关联的学生作业成绩统计表
	 * @param rowBounds
	 * @param ew
	 * @return
	 */
	List<JxzyTjVO> getJxzyTjVOPageByCondition(RowBounds rowBounds,
			@Param("ew") Wrapper<JxzyTjVO> ew);
}
