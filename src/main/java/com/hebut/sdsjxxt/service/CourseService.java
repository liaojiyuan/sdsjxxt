package com.hebut.sdsjxxt.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.baomidou.mybatisplus.service.IService;
import com.hebut.sdsjxxt.dto.CourseDto;
import com.hebut.sdsjxxt.pojo.Course;

/**
 * @author gaochaochao
 * @date 2019年5月15日 下午8:57:39
 * @description
 */
public interface CourseService extends IService<Course> {

	/**
	 * @description 获取courseDto列表
	 * @param @return
	 * @return List<Course>
	 */
	List<CourseDto> course2dto(List<Course> courseList);

	CourseDto handleBanjiNames(CourseDto coursedto);

	CourseDto course2dto(Course course);

	boolean checkCourseNameUnique(Course course);

	int updateLeave(Course course, double weight, boolean flag);

	// 获取用户课程列表
	List<Course> getUserCourseList(HttpSession session);
	
	//获取课程参与的学生总人数
	int getCourseStudentTotal(String banjiIds);
	
	void setCourseId(HttpSession session,Long id);
	
	Long getCourseId(HttpSession session);
}
