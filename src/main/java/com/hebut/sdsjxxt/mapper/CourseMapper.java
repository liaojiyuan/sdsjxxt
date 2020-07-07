package com.hebut.sdsjxxt.mapper;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hebut.sdsjxxt.pojo.Course;

/**
 * @author gaochaochao
 * @date 2019年5月15日 下午4:16:44
 * @description
 */

@Repository("courseMapper")
public interface CourseMapper extends BaseMapper<Course> {

	/*
	 * List<Course> getCourseList();
	 * 
	 * Course selectByPrimaryKey(Long id);
	 * 
	 * int updateByPrimaryKey(Course course);
	 * 
	 * int deleteByPrimaryKey(Long id);
	 */
}
