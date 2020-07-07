package com.hebut.sdsjxxt.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hebut.sdsjxxt.common.Const;
import com.hebut.sdsjxxt.dto.CourseDto;
import com.hebut.sdsjxxt.mapper.CourseMapper;
import com.hebut.sdsjxxt.pojo.Banji;
import com.hebut.sdsjxxt.pojo.Course;
import com.hebut.sdsjxxt.pojo.User;
import com.hebut.sdsjxxt.service.BanjiService;
import com.hebut.sdsjxxt.service.CourseService;
import com.hebut.sdsjxxt.service.UserService;
import com.hebut.sdsjxxt.util.CRC64;

/**
 * @author gaochaochao
 * @date 2019年5月15日 下午8:58:26
 * @description
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

	@Autowired
	private BanjiService banjiService;
	
	@Autowired
	CourseMapper courseMapper;

	@Autowired
	private UserService userService;

	/*
	 * 获取courseDto列表
	 */
	@Override
	public List<CourseDto> course2dto(List<Course> courseList) {
		List<CourseDto> list = new ArrayList<>();
		if (!CollectionUtils.isEmpty(courseList)) {
			courseList.forEach(course -> {
				CourseDto dto = new CourseDto();
				BeanUtils.copyProperties(course, dto);
				handleBanjiNames(dto);
				list.add(dto);
			});
		}
		return list;
	}

	/**
	 * @description 处理课程班级号和班级映射
	 * @param @param
	 *            list
	 * @return void
	 */
	@Override
	public CourseDto handleBanjiNames(CourseDto coursedto) {
		Map<Integer, String> banjiMap = banjiService.getBanjiReflection();
		String ids = coursedto.getBanjiIds();
		if (StringUtils.isNotBlank(ids)) {
			int[] idArray = Arrays.stream(ids.split(",")).mapToInt(Integer::valueOf).toArray();
			StringBuilder sb = new StringBuilder();
			Arrays.stream(idArray).forEach(id -> sb.append(banjiMap.get(id)).append(" "));
			coursedto.setBanjiNames(sb.toString());
		}
		return coursedto;
	}

	/*
	 * 获取courseDto列表
	 */
	@Override
	public CourseDto course2dto(Course course) {
		CourseDto dto = new CourseDto();
		BeanUtils.copyProperties(course, dto);
		handleBanjiNames(dto);
		return dto;
	}

	/*
	 * 验证添加的课程名称唯一 验证方式  课程名称+开课年份
	 */
	@Override
	public boolean checkCourseNameUnique(Course course) {
		int count = courseMapper
				.selectCount(new EntityWrapper<Course>()
						.eq("crc64_name", CRC64.crc64Long(course.getName()))
						.eq("start_year",course.getStartYear()));

		return count == 0 ? true : false;
	}

	/* 
	 */
	@Override
	public int updateLeave(Course course, double weight, boolean flag) {
		Course course2 = new Course();
		if (flag) { // 教学内容的添加，编辑操作，都会导致课程剩余权值的 “-”操作
			course2.setLeave(course.getLeave() - weight);
		} else {// 教学内容删除操作会导致课程剩余权值的“+”操作
			course2.setLeave(course.getLeave() + weight);
		}

		course2.setId(course.getId());
		course2.setJxnrCount(course.getJxnrCount());
		// 仅更新course的leave字段
		return courseMapper.update(course2, new EntityWrapper<Course>().eq("id", course.getId()));
	}

	/**
	 * 获取用户课程列表
	 * 学生   班级id-->课程id
	 * 教师  课程
	 * 管理员 加载全部课程
	 */
	@Override
	public List<Course> getUserCourseList(HttpSession session) {
		EntityWrapper<Course> wrapper = new EntityWrapper<Course>();
		User user = userService.getCurrentUserInfo(session);
		if(!ObjectUtils.isEmpty(user)) {
			if(Const.ROLETYPE_TEACHER==user.getRole().intValue()) {
				wrapper.eq("teacher_id", user.getXuehao());
			}else if(Const.ROLETYPE_STUDENT==user.getRole().intValue()){
				wrapper.like("banji_ids", user.getBanjiId().toString());
			}
		}
		List<Course> list = courseMapper.selectList(wrapper);
		return list;
	}
	
	/**
	 * 获取课程参与的总人数  授课班级人数之和
	 */
	@Override
	public int getCourseStudentTotal(String ids) {
		int count=0;
		int[] idArray = Arrays.stream(ids.split(",")).mapToInt(Integer::valueOf).toArray();
		Banji banji=null;
		for (int i : idArray) {
			banji=banjiService.selectById(i);
			count+=banji.getTotalCount();
		}
		return  count;
	}

	@Override
	public void setCourseId(HttpSession session, Long id) {
		String xuehao = userService.getCurrentUserXuehao(session);
		String token=new StringBuilder().append("course").append("_").append(xuehao).toString();
		session.setAttribute(token, id);
		
	}

	@Override
	public Long getCourseId(HttpSession session) {
		String xuehao = userService.getCurrentUserXuehao(session);
		String token=new StringBuilder().append("course").append("_").append(xuehao).toString();
		Long id=(Long) session.getAttribute(token);
		return id;
	}

}
