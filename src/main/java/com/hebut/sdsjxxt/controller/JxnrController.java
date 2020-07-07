package com.hebut.sdsjxxt.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
import com.hebut.sdsjxxt.dvo.JxnrCourseVO;
import com.hebut.sdsjxxt.interactive.JxnrCourseVOSelectWrapper;
import com.hebut.sdsjxxt.interactive.Msg;
import com.hebut.sdsjxxt.pojo.Course;
import com.hebut.sdsjxxt.pojo.Jsjxzy;
import com.hebut.sdsjxxt.pojo.Jxnr;
import com.hebut.sdsjxxt.service.CourseService;
import com.hebut.sdsjxxt.service.JsjxzyService;
import com.hebut.sdsjxxt.service.JxnrService;
import com.hebut.sdsjxxt.service.UserService;
import com.hebut.sdsjxxt.util.CRC64;

/**
 * 教学内容Controller
 */
@RestController
public class JxnrController {

	Logger log = Logger.getLogger(JxnrController.class);

	@Autowired
	private JxnrService jxnrService;

	@Autowired
	private JsjxzyService jsjxzyService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private UserService userService;

	@Autowired
	private GlobalFunction globalFunction;

	/**
	 * 获取当前用户参与的课程的教学内容列表，以课程分组,学生来说，关联班级---课程----教学内容；教师来说 教学内容（create_user_id）
	 * 
	 * @description
	 * @param @param
	 *            rows
	 * @param @param
	 *            page
	 * @param @param
	 *            order
	 * @param @return
	 * @return Map
	 */
	@GetMapping("/jxnr/tableList")
	public Map<String,Object> getJxnrTableList(Integer rows, Integer page, Long courseId, JxnrCourseVOSelectWrapper esw,
			@RequestParam(defaultValue = "start_date") String order, HttpSession session) {
		// 得到筛选条件
		EntityWrapper<JxnrCourseVO> wrapper = globalFunction.getJxnrCourseVOWrapper(esw);
		Map<String, Object> map = new HashMap<>(16);
		// 构造分页类，记录
		Page<JxnrCourseVO> selectPages = new Page<JxnrCourseVO>(page, rows);
		List<Course> courseList = courseService.getUserCourseList(session);
		// 当前用户没有参与课程，则没有教学内容信息显示
		if (CollectionUtils.isEmpty(courseList)) {
			map.put("total", 0);
			map.put("rows", new ArrayList<Jxnr>());
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
			wrapper.in("course_id", idArray);
		}

		// 要按照课程分组，课程下的教学内容全部显示， 排序按照 教学内容创建时间降序，课程开课日期降序
		wrapper.groupBy("course_id,crc64_course_content");
		wrapper.orderBy("create_time", false);
		wrapper.orderBy("start_date", false);
		List<JxnrCourseVO> records = jxnrService.selectJxnrCourseVOPage(selectPages, wrapper);
		List<Map<String,Object>> listMap = jxnrService.selectJxnrJxzyCountList( );
		fillRecord(records,listMap);
		jxnrService.handleBanjiIds(records);
		map.put("total", selectPages.getTotal());
		map.put("rows", records);
		return map;
	}

	private void fillRecord(List<JxnrCourseVO> records,List<Map<String,Object>> listMap) {
		for(JxnrCourseVO vo:records) {
			long jxnrid=vo.getId();
			//{jxzyCount=3, id=39}  
			for(Map<String,Object> map:listMap) {
				long id=((Long) map.get("id")).longValue();
				if(jxnrid==id) {
					int jxzyCount=(int)(long) map.get("jxzyCount");
					vo.setJxzyCount(Integer.valueOf(jxzyCount));
					break;
				}
			}
			if(ObjectUtils.isEmpty(vo.getJxzyCount())) {
				vo.setJxzyCount(0);
			}
		}
		
	}

	/**
	 * 添加教学内容
	 * 
	 * @description
	 * @param @param
	 *            jxnr
	 * @param @return
	 * @return Msg
	 */
	@PostMapping(value = "/jxnr/add", produces = "application/json;charset=utf-8")
	public Msg saveJxnr(@RequestBody Jxnr jxnr, HttpSession session) {
		System.out.println(jxnr);

		Course course = courseService.selectById(jxnr.getCourseId());
		if (ObjectUtils.isEmpty(course)) {
			return Msg.error("保存教学内容出错，其对应课程查询为空");
		}
		boolean unique = jxnrService.checkJxnrUnique(jxnr);
		if (!unique) {
			return Msg.error("该教学内容在《" + course.getName() + "》这门课程已经创建过了");
		}
		double leave = course.getLeave();
		double weight = jxnr.getWeight();
		if ((leave - weight) < 0) {
			return Msg.error("教学内容的权重值设置不正确，请仔细查看权重值设置提示信息");
		}
		;
		jxnr.setGrade(weight * course.getGrade()); // 设置分数
		jxnr.setCourseName(course.getName()); // 设置课程名
		jxnr.setCrc64CourseAim(CRC64.crc64Long(jxnr.getCourseAim()));
		jxnr.setCrc64CourseContent(CRC64.crc64Long(jxnr.getCourseContent()));
		String xuehao = userService.getCurrentUserXuehao(session);
		if (StringUtils.isEmpty(xuehao)) {
			return Msg.error("当前用户未登陆，不能操作");
		}
		jxnr.setCreateUserId(xuehao);
		boolean success = jxnrService.insert(jxnr);
		if (success) { //更新课程剩余权值，jxnrCount+1
			course.setJxnrCount(course.getJxnrCount()+1);
			courseService.updateLeave(course, weight, true);
			return Msg.ok("保存成功");
		} else {
			return Msg.error("保存失败");
		}
	}

	/**
	 * 根据id查询教学内容
	 * 
	 * @description
	 * @param @param
	 *            id
	 * @param @return
	 * @return Msg
	 */
	@GetMapping({ "/jxnr/display/{id}", "/jxnr/edit/{id}" })
	public Msg displayJxnr(@PathVariable("id") Long id) {
		JxnrCourseVO jxnrCourseVO = jxnrService.getJxnrCourseVO(id);
		if (ObjectUtils.isEmpty(jxnrCourseVO)) {
			return Msg.error("获取该条教学内容记录失败");
		}
		return Msg.ok(null, jxnrCourseVO);
	}

	/**
	 * 
	 * @description 修改教学内容，重点放在 权重值的准确性验证上 修改教学内容 只能修改  教学内容,权值 两个字段
	 * @param @param
	 *            jxnr
	 * @param @return
	 * @return Msg
	 */
	@PostMapping("/jxnr/toedit")
	public Msg modifyJxnr(@RequestBody Jxnr jxnr) {
		System.out.println(jxnr);
		boolean isModifiedWeight = false;
		long id = jxnr.getId();
		Jxnr oldJxnr = jxnrService.selectById(id);
		Course course = courseService.selectById(jxnr.getCourseId());
		double oldWeight = oldJxnr.getWeight();
		double newWeight = jxnr.getWeight();
		if (newWeight != oldWeight) {
			isModifiedWeight = true;
		}
		double leaveWeight = course.getLeave();
		if (leaveWeight + oldWeight - newWeight < 0) {
			return Msg.error("权重值修改后的值不正确，请仔细计算和查看提示信息");
		}
		boolean unique = jxnrService.checkJxnrUnique(jxnr);
		if (!unique && !isModifiedWeight) { // 如果权值没改，并且教学目标和内容在库中存在过，则盼为重复
			return Msg.error("该教学内容在《" + jxnr.getCourseName() + "》这门课程已经创建过了");
		}
		jxnr.setGrade(jxnr.getWeight() * course.getGrade());// 修改分数
		jxnr.setCrc64CourseAim(CRC64.crc64Long(jxnr.getCourseAim()));
		jxnr.setCrc64CourseContent(CRC64.crc64Long(jxnr.getCourseContent()));
		boolean success = jxnrService.updateById(jxnr);
		if (success) {
			if (isModifiedWeight) {
				// 修改课程剩余权重值
				courseService.updateLeave(course, newWeight - oldWeight, true);
			}
			return Msg.ok("保存成功");
		}
		return Msg.error("保存修改信息出错");
	}

	/**
	 * 修改教学内容 另存为新的教学内容（与前一个修改区分开）必须有的字段（前台hidden传递过来）course_id,create_user_id
	 * 
	 * @description
	 * @param @param
	 *            jxnr
	 * @param @return
	 * @return Msg
	 */
	@PostMapping("/jxnr/toeditNew")
	public Msg modifyToNewJxnr(@RequestBody Jxnr jxnr) {
		System.out.println(jxnr);
		boolean unique = jxnrService.checkJxnrUnique(jxnr);
		if (!unique) {
			return Msg.error("该教学内容在《" + jxnr.getCourseName() + "》这门课程已经创建过了");
		}
		Course course = courseService.selectById(jxnr.getCourseId());
		double weight = jxnr.getWeight();
		if (weight - course.getLeave() > 0) {
			return Msg.error("教学内容的权重值设置不正确，请仔细查看权重值设置提示信息");
		}
		jxnr.setId(null); // 设置自增的id，要把之前的id清空
		jxnr.setGrade(weight * course.getGrade()); // 设置分数
		jxnr.setCrc64CourseAim(CRC64.crc64Long(jxnr.getCourseAim()));
		jxnr.setCrc64CourseContent(CRC64.crc64Long(jxnr.getCourseContent()));
		boolean success = jxnrService.insert(jxnr);
		if (success) {
			course.setJxnrCount(course.getJxnrCount()+1);
			courseService.updateLeave(course, weight, true);
			return Msg.ok("保存成功");
		}
		return Msg.error("保存失败");
	}

	/**
	 * 删除教学内容 ，如果存在相关联的教学作业，则不能删除，删除同时，将课程对应的剩余权重leave相应修改
	 * 
	 * @description
	 * @param @param
	 *            id
	 * @param @return
	 * @return Msg
	 */
	@GetMapping("/jxnr/delete/{id}")
	public Msg removeJxnr(@PathVariable Long id) {
		List<Jsjxzy> jxzyList = jsjxzyService.getJsjxzyListByJxnrId(id);
		boolean result = false;
		if (CollectionUtils.isEmpty(jxzyList)) {
			Jxnr jxnr = jxnrService.selectById(id);
			double weight = jxnr.getWeight();
			result = jxnrService.deleteById(id);
			if (result) {// 删除成功
				Course course = courseService.selectById(jxnr.getCourseId());
				course.setJxnrCount(course.getJxnrCount()-1);
				courseService.updateLeave(course, weight, false);
				return Msg.ok("删除成功");
			}
			return Msg.error("删除出错");
		} else {
			return Msg.error("该教学内容已经发布相关联的作业任务，不能被删除");
		}
	}

	/**
	 * 动态获取教学内容剩余权值
	 * 
	 * @description
	 * @param @param
	 *            jxnrId
	 * @param @return
	 * @return Msg
	 */
	@GetMapping("/jxnr/leaveWeight/{jxnrId}")
	public Msg getJxnrLeave(@PathVariable("jxnrId") Long jxnrId) {
		Jxnr jxnr = jxnrService.selectById(jxnrId);
		return Msg.ok(null, jxnr);
	}
	
	/**
	 * 在跳转到教学内容统计页面之前，先把教学内容id存在session中
	 * @param id
	 * @param session
	 * @return
	 */
	@GetMapping("/jxnr/tjfx/{id}")
	public Msg showJxzyTjfx(@PathVariable Long id ,HttpSession session) {
		jxnrService.setJxnrId(session, id);
		return Msg.ok(null,"/tjfx/jxnrtj");
	}
	
}
