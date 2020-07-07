package com.hebut.sdsjxxt.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hebut.sdsjxxt.common.Const;
import com.hebut.sdsjxxt.dvo.JsjxzyJxnrCourseVO;
import com.hebut.sdsjxxt.dvo.XszyJxzyVO;
import com.hebut.sdsjxxt.interactive.Msg;
import com.hebut.sdsjxxt.interactive.XszySelectWrapper;
import com.hebut.sdsjxxt.pojo.Course;
import com.hebut.sdsjxxt.pojo.Jsjxzy;
import com.hebut.sdsjxxt.pojo.User;
import com.hebut.sdsjxxt.pojo.Xszy;
import com.hebut.sdsjxxt.service.CourseService;
import com.hebut.sdsjxxt.service.JsjxzyService;
import com.hebut.sdsjxxt.service.UserService;
import com.hebut.sdsjxxt.service.XszyService;

/**
 * 学生作业controller
 */
@RestController
public class XszyController {
	
	@Autowired
	private JsjxzyService jsjxzyService;

	@Autowired
	private XszyService xszyService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private GlobalFunction globalFunction;

	@Autowired
	private UserService userService;

	@GetMapping("/xszy/tableList")
	public Map<String,Object> getXszyTableList(Integer rows, Integer page, XszySelectWrapper esw, HttpSession session) {
		Map<String, Object> map = new HashMap<>(16);
		Page<JsjxzyJxnrCourseVO> selectPages = new Page<>(page, rows);
		EntityWrapper<XszyJxzyVO> wrapper = globalFunction.getXszyJxzyVOWrapper(esw,session);
		List<Course> courseList = courseService.getUserCourseList(session);
		// 当前用户没有参与课程，则没有学生作业信息显示
		if (CollectionUtils.isEmpty(courseList)) {
			map.put("total", 0);
			map.put("rows", new ArrayList<JsjxzyJxnrCourseVO>());
			return map;
		}
		// 当前用户有参与课程
		// 筛选条件esw没有获取特定的课程id，将当前用户的所有courseid满足都罗列出来,添加到esw条件查询构造器中
		// 筛选条件如果有courseId了，则前面esw在globalFunction类里面已经包装进去查询了，不用再包装过滤
		if (esw.getCourseId() == null) {
			List<Long> courseIdList = new ArrayList<>();
			courseList.forEach(course -> courseIdList.add(course.getId()));
			Long[] idArray = new Long[courseIdList.size()];
			courseIdList.toArray(idArray);
			wrapper.in("course.id", idArray);
		}
		// group by jsjxzy.course_id ,jsjxzy.jxnr_id,jsjxzy.id
		// order by 
		wrapper.groupBy("xszy.course_id,jsjxzy.jxnr_id,xszy.id");
		wrapper.orderBy("xszy.create_time", false);
		wrapper.orderBy("xszy.submit_time", false);
		wrapper.orderBy("xszy.submit_last_time", false);
		wrapper.orderBy("xszy.stage", true); 
		wrapper.orderBy("xszy.update_time", false);
		List<XszyJxzyVO> records = xszyService.selectPage(selectPages, wrapper);
		handlerTeacherName(records);
		map.put("total", selectPages.getTotal());
		map.put("rows", records);
		return map;
	}
	/**
	 * 设置教师姓名
	 * @param records
	 */
	private void handlerTeacherName(List<XszyJxzyVO> records) {
		if(CollectionUtils.isNotEmpty(records)) {
			User user=userService.selectOne(new EntityWrapper<User>().eq("xuehao",records.get(0).getTeacherId()));
			if(!ObjectUtils.isEmpty(user)) {
				records.forEach(vo->vo.setTeacherName(user.getName()));
			}
		}
		
	}

	@GetMapping("/xszy/edit/{id}")
	public Msg getXszyInfo(@PathVariable("id")Long id) {
		XszyJxzyVO xszyvo = xszyService.getXszyJxzyVOById(id);
		if(org.springframework.util.ObjectUtils.isEmpty(xszyvo)) {
			return Msg.error("未找到学生作业信息");
		}
		return Msg.ok(null,xszyvo);
	}
	/**
	 * 保存学生作业得分，并将状态变成已批改 并将grade设置  分数，学生权重得分x  jsjxzy.weight:x=jsjxzy.total_score:stu:score
	 * @param id  
	 * @param stuScore
	 * @return
	 */
	@GetMapping("/xszy/toeditscore")
	public Msg updateXszyScore(@RequestParam("id")Long id,@RequestParam("stuScore")Double stuScore) {
		Xszy xszy=xszyService.selectById(id);
		if(org.springframework.util.ObjectUtils.isEmpty(xszy)) {
			return Msg.error("保存失败");
		}
		Jsjxzy jxzy=jsjxzyService.selectById(xszy.getJsjxzyId());
		if(org.springframework.util.ObjectUtils.isEmpty(jxzy)) {
			return Msg.error("保存失败");
		}
		Double totalScore=jxzy.getTotalScore();
		Double weight=jxzy.getGrade();
		Double grade=stuScore/totalScore*weight;
		xszy.setStuScore(stuScore);
		xszy.setStage(Const.XSZY_STAGE_YPG);
		xszy.setGrade(grade);
		xszyService.updateById(xszy);
		return Msg.ok();
	}
	
	/**
	 * 保存学生作业作答情况，并将作业阶段改为2 已提交作答
	 * @param id
	 * @param stuScore
	 * @return
	 */
	@GetMapping("/xszy/toeditanswer")
	public Msg updateXszyAnswer(@RequestParam("id")Long id,@RequestParam("answer")String answer) {
		Xszy xszy=xszyService.selectById(id);
		if(org.springframework.util.ObjectUtils.isEmpty(xszy)) {
			return Msg.error("保存失败");
		}
		if(StringUtils.isEmpty(xszy.getAnswer())) {
			xszy.setSubmitTime(new Date());
		}
		xszy.setAnswer(answer);
		xszy.setSubmitLastTime(new Date());
		xszy.setStage(Const.XSZY_STAGE_SUBMIT);
		xszyService.updateById(xszy);
		return Msg.ok();
	}
	
}
