package com.hebut.sdsjxxt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hebut.sdsjxxt.dvo.JxnrCourseVO;
import com.hebut.sdsjxxt.dvo.JxnrTjVO;
import com.hebut.sdsjxxt.pojo.Jxnr;

/**
 * @author gaochaochao
 * @date 2019年5月15日 下午4:17:10
 * @description
 */
@Repository("jxnrMapper")
public interface JxnrMapper extends BaseMapper<Jxnr> {
	/**
	 * 教学内容与课程关联查询
	 * 
	 * @description
	 * @param @param
	 *            id 教学内容主键
	 * @param @return
	 * @return JxnrCourseVO
	 */

	JxnrCourseVO getJxnrCourseVOByJxnrId(@Param("id") Long id);

	/**
	 * 
	 * @description
	 * @param @param
	 *            rowBounds 分页对象 直接传入page即可
	 * @param @param
	 *            ew 条件构造器
	 * @param @return
	 * @return List<JxnrCourseVO>
	 */
	List<JxnrCourseVO> getJxnrCourseVOPageByCourseIds(RowBounds rowBounds, @Param("ew") Wrapper<JxnrCourseVO> ew);

	List<Map<String,Object>> getJxnrJxzyCount();
	/**
	 * 获取教学内容关联的学生作业成绩统计表
	 * @param rowBounds
	 * @param ew
	 * @return
	 */
	List<JxnrTjVO> getJxnrTjVOPageByCondition(RowBounds rowBounds, @Param("ew") Wrapper<JxnrTjVO> wr);

	String getBanjiIdsByJxnrId(Long jxnrId);
}
