package com.hebut.sdsjxxt.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.hebut.sdsjxxt.dvo.JsjxzyJxnrCourseVO;
import com.hebut.sdsjxxt.dvo.JxzyTjVO;
import com.hebut.sdsjxxt.pojo.Jsjxzy;

/**
 * @author gaochaochao
 * @date 2019年5月15日 下午8:57:39
 * @description
 */
public interface JsjxzyService extends IService<Jsjxzy> {
	// 获取教学内容的教学作业
	List<Jsjxzy> getJsjxzyListByJxnrId(Long jxnrId);

	List<JsjxzyJxnrCourseVO> selectPage(RowBounds rowBounds, Wrapper<JsjxzyJxnrCourseVO> ew);

	List<JsjxzyJxnrCourseVO> selectJsjxzyJxnrCourseVOList(Wrapper<JsjxzyJxnrCourseVO> ew);

	boolean updateJxzyStatusBatchById(List<Jsjxzy> jsjxzyList);

	boolean updateJxzyStatusById(Long id, Integer status);

	boolean updateJxzyStatus(Jsjxzy jxzy);
	
	void setJxzyId(HttpSession session,Long id);
	
	Long getJxzyId(HttpSession session);
	
	//通过 jsjxzy表的course_id关联course表，并找到banji_ids字段
	String getBanjiIdsByJsjxzyId(Long id);
	
	List<JxzyTjVO> selectJxzyTjVOPage(RowBounds rowBounds, Wrapper<JxzyTjVO> ew);

}
