package com.hebut.sdsjxxt.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.hebut.sdsjxxt.dvo.XszyCjcxVO;
import com.hebut.sdsjxxt.dvo.XszyJxzyVO;
import com.hebut.sdsjxxt.pojo.Xszy;

/**
 * @author gaochaochao
 * @date 2019年5月15日 下午8:57:39
 * @description
 */
public interface XszyService extends IService<Xszy> {
	// 获取学生作业
	List<Xszy> getXszyListByCourseId(Long courseId);

	boolean updateXszyStageBatchById(List<Xszy> xszyList);

	boolean updateXszyStageById(Long id, Integer stage);

	boolean updateXszyStage(Xszy xszy);
	
	List<XszyJxzyVO> selectPage(RowBounds rowBounds, Wrapper<XszyJxzyVO> ew);
	
	XszyJxzyVO getXszyJxzyVOById(Long id);

	List<XszyCjcxVO> selectXszyCjcxVOPage(RowBounds rowBounds, Wrapper<XszyCjcxVO> ew);

}
