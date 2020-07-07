package com.hebut.sdsjxxt.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.hebut.sdsjxxt.common.Const;
import com.hebut.sdsjxxt.dvo.JsjxzyJxnrCourseVO;
import com.hebut.sdsjxxt.dvo.JxnrCourseVO;
import com.hebut.sdsjxxt.dvo.JxnrTjVO;
import com.hebut.sdsjxxt.dvo.JxzyTjVO;
import com.hebut.sdsjxxt.dvo.XsjxnrcjTjVO;
import com.hebut.sdsjxxt.dvo.XskccjTjVO;
import com.hebut.sdsjxxt.dvo.XszyCjcxVO;
import com.hebut.sdsjxxt.dvo.XszyJxzyVO;
import com.hebut.sdsjxxt.interactive.CourseSelectWrapper;
import com.hebut.sdsjxxt.interactive.JsjxzyJxnrCourseVOSelectWrapper;
import com.hebut.sdsjxxt.interactive.JxnrCourseVOSelectWrapper;
import com.hebut.sdsjxxt.interactive.JxnrTjSelectWrapper;
import com.hebut.sdsjxxt.interactive.JxzyTjSelectWrapper;
import com.hebut.sdsjxxt.interactive.StucjcxSelectWrapper;
import com.hebut.sdsjxxt.interactive.UserSelectWrapper;
import com.hebut.sdsjxxt.interactive.XskccjTjSelectWrapper;
import com.hebut.sdsjxxt.interactive.XszySelectWrapper;
import com.hebut.sdsjxxt.pojo.Course;
import com.hebut.sdsjxxt.pojo.User;
import com.hebut.sdsjxxt.service.UserService;

/**
 * 全局方法
 * 
 */
@Component
public class GlobalFunction {

	@Autowired
	private UserService userService;
	/**
	 * 构造课程表筛选条件
	 * 
	 * @description
	 * @param @param
	 *            csw
	 * @param @return
	 * @return EntityWrapper<Course>
	 */
	public EntityWrapper<Course> getCourseWrapper(CourseSelectWrapper csw,HttpSession session) {
		EntityWrapper<Course> entityWrapper = new EntityWrapper<>();
		if (StringUtils.isNotEmpty(csw.getId())) {
			entityWrapper.eq("id", csw.getId());
		}
		if (StringUtils.isNotEmpty(csw.getName())) {
			entityWrapper.eq("name", csw.getName());
		}
		if (StringUtils.isNotEmpty(csw.getTeacherId())) {
			entityWrapper.eq("teacher_id", csw.getTeacherId());
		}
		if (StringUtils.isNotEmpty(csw.getBanjiIds())) {
			entityWrapper.like("banji_ids", csw.getBanjiIds());
		}
		if (null != csw.getStartYear()) {
			entityWrapper.eq("start_year", csw.getStartYear());
		}
		if (null != csw.getStartDate1() && null != csw.getStartDate2()) {
			entityWrapper.between("start_date", csw.getStartDate1(), csw.getStartDate2());
		} else {
			if (null != csw.getStartDate1()) {
				entityWrapper.ge("start_date", csw.getStartDate1());
			}
			if (null != csw.getStartDate2()) {
				entityWrapper.le("start_date", csw.getStartDate2());
			}
		}
		if (null != csw.getEndDate1() && null != csw.getEndDate2()) {
			entityWrapper.between("end_date", csw.getEndDate1(), csw.getEndDate2());
		} else {
			if (null != csw.getEndDate1()) {
				entityWrapper.ge("end_date", csw.getEndDate1());
			}
			if (null != csw.getEndDate2()) {
				entityWrapper.le("end_date", csw.getEndDate2());
			}
		}
		
		//筛选当前用户的课程列表
		User user=userService.getCurrentUserInfo(session);
		if(!ObjectUtils.isEmpty(user)) {
			if(Const.ROLETYPE_TEACHER==user.getRole().intValue()) {
				entityWrapper.eq("teacher_id", user.getXuehao());
			}else if(Const.ROLETYPE_STUDENT==user.getRole().intValue()){
				entityWrapper.like("banji_ids", user.getBanjiId().toString());
			}
		}
		return entityWrapper;
	}

	
	
	/**
	 * 构造教学内容表课程表筛选条件
	 * 
	 * @description
	 * @param @param
	 *            csw
	 * @param @return
	 * @return EntityWrapper<Course>
	 */
	public EntityWrapper<JxnrCourseVO> getJxnrCourseVOWrapper(JxnrCourseVOSelectWrapper csw) {
		EntityWrapper<JxnrCourseVO> entityWrapper = new EntityWrapper<>();
		if (!ObjectUtils.isEmpty(csw.getCourseId())) {
			entityWrapper.eq("course_id", csw.getCourseId());
		}
		if (StringUtils.isNotEmpty(csw.getTeacherId())) {
			entityWrapper.eq("create_user_id", csw.getTeacherId());
		}
		if (StringUtils.isNotEmpty(csw.getBanjiIds())) {
			entityWrapper.like("banji_ids", csw.getBanjiIds());
		}
		if (null != csw.getStartYear()) {
			entityWrapper.eq("start_year", csw.getStartYear());
		}
		if (null != csw.getStartDate1() && null != csw.getStartDate2()) {
			entityWrapper.between("start_date", csw.getStartDate1(), csw.getStartDate2());
		} else {
			if (null != csw.getStartDate1()) {
				entityWrapper.ge("start_date", csw.getStartDate1());
			}
			if (null != csw.getStartDate2()) {
				entityWrapper.le("start_date", csw.getStartDate2());
			}
		}
		if (null != csw.getEndDate1() && null != csw.getEndDate2()) {
			entityWrapper.between("end_date", csw.getEndDate1(), csw.getEndDate2());
		} else {
			if (null != csw.getEndDate1()) {
				entityWrapper.ge("end_date", csw.getEndDate1());
			}
			if (null != csw.getEndDate2()) {
				entityWrapper.le("end_date", csw.getEndDate2());
			}
		}

		return entityWrapper;
	}

	
	/**
	 * 构造教师教学作业表，教学内容表，课程表筛选条件
	 * 
	 * @description
	 * @param @param
	 *            csw
	 * @param @return
	 * @return EntityWrapper<Course>
	 */
	public EntityWrapper<JsjxzyJxnrCourseVO> getJsjxzyJxnrCourseVOWrapper(JsjxzyJxnrCourseVOSelectWrapper csw) {
		EntityWrapper<JsjxzyJxnrCourseVO> entityWrapper = new EntityWrapper<>();
		if (!ObjectUtils.isEmpty(csw.getStatus())) {
			entityWrapper.eq("jsjxzy.status", csw.getStatus());
		}
		if (!ObjectUtils.isEmpty(csw.getStage())) {
			entityWrapper.eq("jsjxzy.stage", csw.getStage());
		}
		if (!ObjectUtils.isEmpty(csw.getCourseId())) {
			entityWrapper.eq("jsjxzy.course_id", csw.getCourseId());
		}
		if (StringUtils.isNotEmpty(csw.getTeacherId())) {
			entityWrapper.eq("jsjxzy.create_user_id", csw.getTeacherId());
		}
		if (StringUtils.isNotEmpty(csw.getBanjiIds())) {
			entityWrapper.like("course.banji_ids", csw.getBanjiIds());
		}
		if (null != csw.getStartYear()) {
			entityWrapper.eq("course.start_year", csw.getStartYear());
		}
		if (null != csw.getStartTime1() && null != csw.getStartTime2()) {
			entityWrapper.between("jsjxzy.start_time", csw.getStartTime1(), csw.getStartTime2());
		} else {
			if (null != csw.getStartTime1()) {
				entityWrapper.ge("jsjxzy.start_time", csw.getStartTime1());
			}
			if (null != csw.getStartTime2()) {
				entityWrapper.le("jsjxzy.start_time", csw.getStartTime2());
			}
		}
		if (null != csw.getEndTime1() && null != csw.getEndTime2()) {
			entityWrapper.between("jsjxzy.end_time", csw.getEndTime1(), csw.getEndTime2());
		} else {
			if (null != csw.getEndTime1()) {
				entityWrapper.ge("jsjxzy.end_time", csw.getEndTime1());
			}
			if (null != csw.getEndTime2()) {
				entityWrapper.le("jsjxzy.end_time", csw.getEndTime2());
			}
		}

		return entityWrapper;
	}
	
	/**
	 * 构造学生作业，教学作业 条件查询表
	 * @param csw
	 * @return
	 */
	public EntityWrapper<XszyJxzyVO> getXszyJxzyVOWrapper(XszySelectWrapper csw,HttpSession session){
		EntityWrapper<XszyJxzyVO> entityWrapper = new EntityWrapper<>();
		if (!ObjectUtils.isEmpty(csw.getXszyStage())) {
			entityWrapper.eq("xszy.stage", csw.getXszyStage());
		}
		if (!ObjectUtils.isEmpty(csw.getJxzyStage())) {
			entityWrapper.eq("jsjxzy.stage", csw.getJxzyStage());
		}
		if (!ObjectUtils.isEmpty(csw.getCourseId())) {
			entityWrapper.eq("xszy.course_id", csw.getCourseId());
		}
		if (StringUtils.isNotEmpty(csw.getTeacherId())) {
			entityWrapper.eq("xszy.teacher_id", csw.getTeacherId());
		}
		if (StringUtils.isNotEmpty(csw.getBanjiIds())) {
			entityWrapper.eq("user.banji_id", csw.getBanjiIds());
		}
		if (StringUtils.isNotEmpty(csw.getXuehao())) { 
			User user=(User) session.getAttribute(Const.CURRENT_USER);
			if(null!=user) {
				if("1".equals(csw.getXuehao())) {
					entityWrapper.eq("xszy.user_id", user.getXuehao());
				}else {
					entityWrapper.eq("xszy.user_id", csw.getXuehao());
				}
			}
		}
		if (null != csw.getStartTime1() && null != csw.getStartTime2()) {
			entityWrapper.between("jsjxzy.start_time", csw.getStartTime1(), csw.getStartTime2());
		} else {
			if (null != csw.getStartTime1()) {
				entityWrapper.ge("jsjxzy.start_time", csw.getStartTime1());
			}
			if (null != csw.getStartTime2()) {
				entityWrapper.le("jsjxzy.start_time", csw.getStartTime2());
			}
		}
		if (null != csw.getEndTime1() && null != csw.getEndTime2()) {
			entityWrapper.between("jsjxzy.end_time", csw.getEndTime1(), csw.getEndTime2());
		} else {
			if (null != csw.getEndTime1()) {
				entityWrapper.ge("jsjxzy.end_time", csw.getEndTime1());
			}
			if (null != csw.getEndTime2()) {
				entityWrapper.le("jsjxzy.end_time", csw.getEndTime2());
			}
		}
		if (null != csw.getSubmitTime1() && null != csw.getSubmitTime2()) {
			entityWrapper.between("xszy.submit_time", csw.getSubmitTime1(), csw.getSubmitTime2());
		} else {
			if (null != csw.getSubmitTime1()) {
				entityWrapper.ge("xszy.submit_time", csw.getSubmitTime1());
			}
			if (null != csw.getSubmitTime2()) {
				entityWrapper.le("xszy.submit_time", csw.getSubmitTime2());
			}
		}

		return entityWrapper;
	}

	
	public EntityWrapper<JxzyTjVO> getJxzyTjVOWrapper(JxzyTjSelectWrapper csw){
		EntityWrapper<JxzyTjVO> entityWrapper=new EntityWrapper<>();
		if(StringUtils.isNotEmpty(csw.getXuehao())) {
			entityWrapper.eq("user.xuehao", csw.getXuehao());
		}
		if(!ObjectUtils.isEmpty(csw.getBanjiIds())) {
			entityWrapper.eq("user.banji_id", csw.getBanjiIds());
		}
		return entityWrapper;
		
	}

	public EntityWrapper<JxnrTjVO> getJxnrTjVOWrapper(JxnrTjSelectWrapper csw) {
		EntityWrapper<JxnrTjVO> entityWrapper=new EntityWrapper<>();
		if(StringUtils.isNotEmpty(csw.getXuehao())) {
			entityWrapper.eq("user.xuehao", csw.getXuehao());
		}
		if(!ObjectUtils.isEmpty(csw.getBanjiIds())) {
			entityWrapper.eq("user.banji_id", csw.getBanjiIds());
		}
		if(!ObjectUtils.isEmpty(csw.getStage())) {
			entityWrapper.eq("jsjxzy.stage", csw.getStage());
		}
		return entityWrapper;
	}
	
	public EntityWrapper<XsjxnrcjTjVO> getXsjxnrcjTjVOWrapper(JxnrTjSelectWrapper csw) {
		EntityWrapper<XsjxnrcjTjVO> entityWrapper=new EntityWrapper<>();
		if(StringUtils.isNotEmpty(csw.getXuehao())) {
			entityWrapper.eq("xsjxnrcj.user_id", csw.getXuehao());
		}
		if(!ObjectUtils.isEmpty(csw.getBanjiIds())) {
			entityWrapper.eq("user.banji_id", csw.getBanjiIds());
		}
		return entityWrapper;
	}



	public EntityWrapper<XskccjTjVO> getXskccjTjVOWrapper(XskccjTjSelectWrapper csw) {
		EntityWrapper<XskccjTjVO> entityWrapper=new EntityWrapper<>();
		if(StringUtils.isNotEmpty(csw.getXuehao())) {
			entityWrapper.eq("xskccj.user_id", csw.getXuehao());
		}
		if(!ObjectUtils.isEmpty(csw.getBanjiIds())) {
			entityWrapper.eq("user.banji_id", csw.getBanjiIds());
		}
		return entityWrapper;
	}



	public EntityWrapper<XszyCjcxVO> getXszyCjcxVOWrapper(StucjcxSelectWrapper csw) {
		EntityWrapper<XszyCjcxVO> entityWrapper=new EntityWrapper<>();
		if(!ObjectUtils.isEmpty(csw.getCourseId())) {
			entityWrapper.eq("xszy.course_id", csw.getCourseId());
		}
		if(!ObjectUtils.isEmpty(csw.getTeacherId())) {
			entityWrapper.eq("xszy.teacher_id", csw.getTeacherId());
		}
		if(!ObjectUtils.isEmpty(csw.getJxzyStage())) {
			entityWrapper.eq("jsjxzy.stage", csw.getJxzyStage());
		}
		return entityWrapper;
	}



	public EntityWrapper<User> getUserWrapper(UserSelectWrapper userSelectWrapper) {
		EntityWrapper<User> wrapper=new EntityWrapper<>();
		if(!ObjectUtils.isEmpty(userSelectWrapper.getBanjiId())) {
			wrapper.eq("banji_id", userSelectWrapper.getBanjiId());
		}
		if(StringUtils.isNotEmpty(userSelectWrapper.getXuehao())) {
			wrapper.eq("xuehao", userSelectWrapper.getXuehao());
		}
		if(StringUtils.isNotEmpty(userSelectWrapper.getName())) {
			wrapper.like("name",userSelectWrapper.getName());
		}
		return wrapper;
	}

}
