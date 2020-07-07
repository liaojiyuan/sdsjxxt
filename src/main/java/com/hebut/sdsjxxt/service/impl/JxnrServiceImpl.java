package com.hebut.sdsjxxt.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hebut.sdsjxxt.dto.CourseDto;
import com.hebut.sdsjxxt.dvo.JxnrCourseVO;
import com.hebut.sdsjxxt.dvo.JxnrTjVO;
import com.hebut.sdsjxxt.mapper.JxnrMapper;
import com.hebut.sdsjxxt.pojo.Jxnr;
import com.hebut.sdsjxxt.service.CourseService;
import com.hebut.sdsjxxt.service.JxnrService;
import com.hebut.sdsjxxt.service.UserService;
import com.hebut.sdsjxxt.util.CRC64;

@Service
public class JxnrServiceImpl extends ServiceImpl<JxnrMapper, Jxnr> implements JxnrService {

	@Autowired
	private JxnrService jxnrService;

	@Autowired
	private JxnrMapper jxnrMapper;

	@Autowired
	private CourseService courseService;
	
	@Autowired
	private UserService userService;

	/* 
	 */
	@Override
	public List<Jxnr> getJxnrListByCourseId(Long courseId) {

		List<Jxnr> list = jxnrService.selectList(new EntityWrapper<Jxnr>().eq("course_id", courseId));
		if (!CollectionUtils.isEmpty(list)) {
			return list;
		}
		return null;
	}

	/* 
	 */
	@Override
	public boolean checkJxnrUnique(Jxnr jxnr) {
		int count = jxnrMapper.selectCount(new EntityWrapper<Jxnr>().eq("course_id", jxnr.getCourseId())
				.eq("crc64_course_aim", CRC64.crc64Long(jxnr.getCourseAim()))
				.eq("crc64_course_content", CRC64.crc64Long(jxnr.getCourseContent())));
		return count == 0 ? true : false;
	}

	/* 
	 */
	@Override
	public JxnrCourseVO getJxnrCourseVO(Long id) {
		JxnrCourseVO jxnrCourseVO = jxnrMapper.getJxnrCourseVOByJxnrId(id);
		return jxnrCourseVO;
	}

	/* 
	 */
	@Override
	public List<JxnrCourseVO> selectJxnrCourseVOPage(RowBounds rowBounds, Wrapper<JxnrCourseVO> ew) {
		return jxnrMapper.getJxnrCourseVOPageByCourseIds(rowBounds, ew);
	}

	/* 
	 */
	@Override
	public List<JxnrCourseVO> handleBanjiIds(List<JxnrCourseVO> records) {
		if (CollectionUtils.isNotEmpty(records)) {
			records.forEach(jxnrCourseVO -> {
				CourseDto dto = new CourseDto();
				dto.setBanjiIds(jxnrCourseVO.getBanjiIds());
				courseService.handleBanjiNames(dto);
				jxnrCourseVO.setBanjiNames(dto.getBanjiNames());
			});

		}
		return records;
	}

	/* 
	 */
	@Override
	public int updateLeave(Jxnr jxnr, double weight, boolean flag) {
		Jxnr jxnr2 = new Jxnr();
		jxnr2.setId(jxnr.getId());
		if (flag) { // 教学作业的增加，编辑都会导致教学内容的剩余权值的“-”操作
			jxnr2.setLeave(jxnr.getLeave() - weight);
		} else {// 删除 增加教学内容的剩余权值
			jxnr2.setLeave(jxnr.getLeave() + weight);
		}
		// 仅更新course的leave字段
		return jxnrMapper.update(jxnr2, new EntityWrapper<Jxnr>().eq("id", jxnr.getId()));
	}

	@Override
	public List<Map<String,Object>> selectJxnrJxzyCountList() {
		return jxnrMapper.getJxnrJxzyCount();
	}

	@Override
	public void setJxnrId(HttpSession session, Long id) {
		String xuehao = userService.getCurrentUserXuehao(session);
		String token=new StringBuilder().append("jxnr").append("_").append(xuehao).toString();
		session.setAttribute(token, id);
	}

	@Override
	public Long getJxnrId(HttpSession session) {
		String xuehao = userService.getCurrentUserXuehao(session);
		String token=new StringBuilder().append("jxnr").append("_").append(xuehao).toString();
		Long id=(Long) session.getAttribute(token);
		return id;
	}

	@Override
	public List<JxnrTjVO> selectJxnrTjVOPage(RowBounds rowBounds,Wrapper<JxnrTjVO> wr) {
		
		return jxnrMapper.getJxnrTjVOPageByCondition(rowBounds, wr);
	}

	@Override
	public String getBanjiIdsByJxnrId(Long jxnrId) {
		return jxnrMapper.getBanjiIdsByJxnrId(jxnrId);
	}

	

}
