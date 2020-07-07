package com.hebut.sdsjxxt.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.hebut.sdsjxxt.dvo.JxnrCourseVO;
import com.hebut.sdsjxxt.dvo.JxnrTjVO;
import com.hebut.sdsjxxt.pojo.Jxnr;

/**
 * @author gaochaochao
 * @date 2019年5月15日 下午8:57:39
 * @description
 */
public interface JxnrService extends IService<Jxnr> {
	// 获取班级列表
	List<Jxnr> getJxnrListByCourseId(Long courseId);

	boolean checkJxnrUnique(Jxnr jxnr);

	JxnrCourseVO getJxnrCourseVO(Long id);

	List<JxnrCourseVO> selectJxnrCourseVOPage(RowBounds rowBounds, Wrapper<JxnrCourseVO> ew);

	/**
	 * @description
	 * @param @param
	 *            records
	 * @return void
	 */
	List<JxnrCourseVO> handleBanjiIds(List<JxnrCourseVO> records);

	int updateLeave(Jxnr jsnr, double weight, boolean flag);

	List<Map<String,Object>> selectJxnrJxzyCountList();
	
    void setJxnrId(HttpSession session,Long id);
	
	Long getJxnrId(HttpSession session);
	
	List<JxnrTjVO> selectJxnrTjVOPage(RowBounds rowBounds ,Wrapper<JxnrTjVO> wr);

	String getBanjiIdsByJxnrId(Long jxnrId);


}
