package com.hebut.sdsjxxt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hebut.sdsjxxt.common.Const;
import com.hebut.sdsjxxt.dto.CourseDto;
import com.hebut.sdsjxxt.interactive.CourseSelectWrapper;
import com.hebut.sdsjxxt.interactive.Msg;
import com.hebut.sdsjxxt.mapper.CourseMapper;
import com.hebut.sdsjxxt.pojo.Course;
import com.hebut.sdsjxxt.pojo.User;
import com.hebut.sdsjxxt.pojo.Xszy;
import com.hebut.sdsjxxt.service.CourseService;
import com.hebut.sdsjxxt.service.UserService;
import com.hebut.sdsjxxt.service.XszyService;
import com.hebut.sdsjxxt.util.CRC64;

/**
 * 课程信息Controller
 */
@RestController
public class CourseController {

	Logger log = Logger.getLogger(CourseController.class);

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private CourseService courseService;
	
	@Autowired
	private XszyService xszyService;

	@Autowired
	private UserService userService;

	@Autowired
	private GlobalFunction globalFunction;
	
	/**
	 * 获取课程列表 通过当前用户过滤 
	 * @param session
	 * @return
	 */
	@GetMapping("/course/userList")
	public Msg getCourseList(HttpSession session ) {
		List<Course> list = courseService.getUserCourseList(session);
		return Msg.ok(null, list);
	}

	@GetMapping("/course/tableList")
	public Map<String,Object> getCourseTableList(Integer rows, Integer page, CourseSelectWrapper esw,
			HttpSession session) {
		// 得到筛选条件
		EntityWrapper<Course> courseWrapper = globalFunction.getCourseWrapper(esw,session);
		courseWrapper.orderBy("create_time", false);
		Page<Course> selectPage = courseService.selectPage(new Page<>(page, rows), courseWrapper);
		List<CourseDto> list = courseService.course2dto(selectPage.getRecords());
		Map<String, Object> map = new HashMap<>(16);
		map.put("total", selectPage.getTotal());
		map.put("rows", list);
		return map;
	}

	@GetMapping("/course/display/{id}")
	public Msg displayCourse(@PathVariable Long id) {
		Course course = courseService.selectById(id);
		if (null == course) {
			return Msg.error("获取课程信息出错");
		}
		CourseDto dto = courseService.course2dto(course);
		return Msg.ok(null, dto);
	}

	/**
	 * 
	 * @description 课程的修改操作 1 判断是否被允许修改 是否被允许 允许前提 xszy没有关联course_id 2
	 *              如果允许，在进行查找数据回填
	 * @param @return
	 * @return Msg
	 */
	@GetMapping("/course/edit/{id}")
	public Msg operationEditAllow(@PathVariable Long id) {
		List<Xszy> xszyList = xszyService.getXszyListByCourseId(id);
		Course course = null;
		if (CollectionUtils.isEmpty(xszyList)) {
			course = courseService.selectById(id);
			if (null == course) {
				return Msg.error("获取课程信息出错");
			}
			return Msg.ok(null, course);
		} else {
			return Msg.error("该课程记录关联相关的教学作业，已发布，不能被修改");
		}
	}

	/**
	 * 
	 * @description 将修改的内容更新到库里面
	 * @param @param
	 *            course
	 * @param @return
	 * @return Msg
	 */
	@PostMapping(value = "/course/toedit", produces = "application/json;charset=utf-8")
	public Msg modifyCourse(@RequestBody Course course) {
		System.out.println(course);
		//设置课程总学生人数
		course.setTotalCount(courseService.getCourseStudentTotal(course.getBanjiIds()));
		int count = courseMapper.updateById(course);
		if (count == 0) {
			return Msg.error("更新课程信息出错");
		}

		return Msg.ok("更新成功");
	}

	/**
	 * 
	 * @description 课程的删除操作删除前先校验是否能够删除，如果可以返回success，进行删除操作，否则，给出提示信息。  允许前提 jxnr没有关联course_id
	 * @param @return
	 * @return Msg
	 */
	@GetMapping("/course/delete/{id}")
	public Msg operationDeleteAllow(@PathVariable Long id) {
		Course course = courseService.selectById(id);
		if(course.getJxnrCount().longValue()!=0) {
			return Msg.error("该课程已经关联相关的教学内容，不能被删除");
		}
		return Msg.ok();
	}
	

	/**
	 * 
	 * @description 课程的删除操作,已经验证过可以删除了。
	 * @param @return
	 * @return Msg
	 */
	@GetMapping("/course/todelete/{id}")
	public Msg removeCourse(@PathVariable Long id) {
		int result = 0;
		result = courseMapper.deleteById(id);
		if (result == 0) {
			return Msg.error("删除课程出错");
		}
		return Msg.ok("删除成功");
	}

	/**
	 * 
	 * @description 课程的删除操作是否被允许 允许前提 jxnr没有关联course_id
	 * @param @return
	 * @return Msg
	 */
	@PostMapping(value = "/course/add", produces = "application/json;charset=utf-8")
	public Msg saveCourse(@RequestBody Course course,HttpSession session) {
		System.out.println(course);
		boolean unique = courseService.checkCourseNameUnique(course);
		if (!unique) {
			return Msg.error("该课程（课程名+开课年份）已存在，请换一个");
		}
		User user = userService.getCurrentUserInfo(session);
		if(ObjectUtils.isEmpty(user)) {
			return Msg.error("当前用户身份已过期，请重新登录");
		}
		//设置课程总学生人数
		course.setTotalCount(courseService.getCourseStudentTotal(course.getBanjiIds()));
		//教师用户添加课程 ，前端没有筛选教师用户下拉框，后台程序需要添加当前教师用户为本课程授课教师
		//管理员添加课程，前端实现teacherId和teacherName联动修改，后台直接接收到pojo里面
		if(Const.ROLETYPE_TEACHER==user.getRole().intValue()) {
			course.setTeacherId(user.getXuehao());
			course.setTeacherName(user.getName());
		}
		course.setCrc64Name(CRC64.crc64Long(course.getName()));
		boolean success = courseService.insert(course);
		if (success) {
			return Msg.ok("添加成功");
		} else {
			return Msg.error("添加失败，请联系管理员询问情况");
		}
	}

	@GetMapping("/course/uniqueName")
	public Msg checkCourseNameUnique(@RequestParam String name) {
		int count = courseService.selectCount(new EntityWrapper<Course>().eq("name", name));
		if (count != 0) {
			return Msg.error("该课程名已存在");
		}
		return Msg.ok();
	}

	@GetMapping("/course/leaveWeight/{id}")
	public Msg getLeaveWeight(@PathVariable Integer id) {
		Course course = courseService.selectById(id);
		if (ObjectUtils.isEmpty(course)) {
			return Msg.error("获取失败");
		}
		return Msg.ok(null, course);
	}
	
	
	
	
	/**
	 * 在跳转到课程统计页面之前，先把课程id存在session中
	 * @param id
	 * @param session
	 * @return
	 */
	@GetMapping("/course/tjfx/{id}")
	public Msg showCourseTjfx(@PathVariable Long id ,HttpSession session) {
		courseService.setCourseId(session, id);
		return Msg.ok(null,"/tjfx/kctj");
	}
	
	
}
