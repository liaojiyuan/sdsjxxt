package com.hebut.sdsjxxt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hebut.sdsjxxt.dvo.XszyCjcxVO;
import com.hebut.sdsjxxt.dvo.XszyJxzyVO;
import com.hebut.sdsjxxt.pojo.Xszy;

/**
 * @author gaochaochao
 * @date 2019年5月15日 下午4:17:35
 * @description
 */
@Repository("xszyMapper")
public interface XszyMapper extends BaseMapper<Xszy> {
	/**
	 * 批量更新学生作业阶段
	 * 
	 * @description
	 * @param @param
	 *            xszyList
	 * @param @return
	 * @return int
	 */
	int updateXszyStageBatchById(@Param("xszyList") List<Xszy> xszyList);

	/**
	 * 更新学生作业状态通过主键
	 * 
	 * @description
	 * @param @param
	 *            id xszy主键
	 * @param @param
	 *            stage 新阶段
	 * @param @return
	 * @return int
	 */
	int updateXszyStageById(@Param("id") Long id, @Param("stage") Integer stage);

	int updateXszyStage(@Param("xszy") Xszy xszy);
	
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
	List<XszyJxzyVO> getXszyJxzyVOPageByCondition(RowBounds rowBounds,
			@Param("ew") Wrapper<XszyJxzyVO> ew);

	XszyJxzyVO selectXszyJxzyVOById(@Param("id")Long id);

	List<XszyCjcxVO> getXszyCjcxVOPageByCondition(RowBounds rowBounds,@Param("ew") Wrapper<XszyCjcxVO> ew);
	
	
}
