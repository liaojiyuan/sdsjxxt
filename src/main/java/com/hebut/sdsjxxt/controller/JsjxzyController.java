package com.hebut.sdsjxxt.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hebut.sdsjxxt.common.Const;
import com.hebut.sdsjxxt.dvo.JsjxzyJxnrCourseVO;
import com.hebut.sdsjxxt.interactive.JsjxzyJxnrCourseVOSelectWrapper;
import com.hebut.sdsjxxt.interactive.Msg;
import com.hebut.sdsjxxt.pojo.Course;
import com.hebut.sdsjxxt.pojo.Jsjxzy;
import com.hebut.sdsjxxt.pojo.Jxnr;
import com.hebut.sdsjxxt.pojo.Xszy;
import com.hebut.sdsjxxt.service.CourseService;
import com.hebut.sdsjxxt.service.JsjxzyService;
import com.hebut.sdsjxxt.service.JxnrService;
import com.hebut.sdsjxxt.service.UserService;
import com.hebut.sdsjxxt.service.XszyService;

/**
 * 教师教学作业controller
 */
@RestController
public class JsjxzyController {

	Logger log = Logger.getLogger(getClass());

	@Autowired
	private JsjxzyService jsjxzyService;

	@Autowired
	private JxnrService jxnrService;

	@Autowired
	private XszyService xszyService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private GlobalFunction globalFunction;

	@Autowired
	private UserService userService;

	@GetMapping("/jxzy/tableList")
	public Map<String,Object> getJxzyTableList(Integer rows, Integer page, JsjxzyJxnrCourseVOSelectWrapper esw, HttpSession session) {
		Map<String, Object> map = new HashMap<>(16);
		Page<JsjxzyJxnrCourseVO> selectPages = new Page<>(page, rows);
		EntityWrapper<JsjxzyJxnrCourseVO> wrapper = globalFunction.getJsjxzyJxnrCourseVOWrapper(esw);
		List<Course> courseList = courseService.getUserCourseList(session);
		// 当前用户没有参与课程，则没有教学作业信息显示
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
		wrapper.groupBy("jsjxzy.course_id,jsjxzy.jxnr_id,jsjxzy.id");
		wrapper.orderBy("jsjxzy.create_time", false);
		wrapper.orderBy("jsjxzy.start_time", false);
		wrapper.orderBy("jsjxzy.end_time", false);
		wrapper.orderBy("jsjxzy.stage", true); //作业阶段前中后升序排序
		wrapper.orderBy("jsjxzy.status", true); //作业状态 1,2,3升序排序
		wrapper.orderBy("jsjxzy.update_time", false);
		List<JsjxzyJxnrCourseVO> records = jsjxzyService.selectPage(selectPages, wrapper);
		map.put("total", selectPages.getTotal());
		map.put("rows", records);
		return map;
	}

	/**
	 * 在教学内容记录下布置教学作业时，先统计一下该教学内容相关的作业次数是否已打上限3次
	 * 
	 * @description
	 * @param @param
	 *            jxnrId
	 * @param @return
	 * @return Msg
	 */
	@GetMapping("/jsjxzy/beforeAdd/{jxnrId}")
	public Msg beforeAddJsjxzy(@PathVariable Long jxnrId) {
		int count = jsjxzyService.selectCount(new EntityWrapper<Jsjxzy>().eq("jxnr_id", jxnrId));
		if (count >= 3) {
			return Msg.error("该教学内容课前，课中，课后三次作业已经布置完成，无法在继续发布作业任务");
		}
		return Msg.ok(null, count);
	}

	/**
	 * 检查教师教学作业是否存在该阶段的教学作业
	 * 
	 * @description
	 * @param @param
	 *            stage
	 * @param @return
	 * @return Msg
	 */
	@GetMapping("/jsjxzy/queryStage")
	public Msg checkStageTask(@RequestParam("stage") Integer stage, @RequestParam("jxnrId") Long jxnrId) {
		int count = jsjxzyService.selectCount(new EntityWrapper<Jsjxzy>().eq("jxnr_id", jxnrId).eq("stage", stage));
		if (count != 0) {
			return Msg.error("该教学内容下已经发布过" + Const.stageJxjd.get(stage) + "阶段的教学作业了，请选择其他阶段作业");
		}
		return Msg.ok();
	}

	/**
	 * 教学作业添加 添加成功要把教学内容剩余权值修改更新一下
	 * 
	 * @description
	 * @param @param
	 *            jsjxzy
	 * @param @return
	 * @return Msg
	 */
	@PostMapping("/jsjxzy/add")
	public Msg saveJsjxzy(@RequestBody Jsjxzy jsjxzy, HttpSession session) {
		Jxnr jxnr = jxnrService.selectById(jsjxzy.getJxnrId());
		jsjxzy.setGrade(jsjxzy.getWeight() * jxnr.getGrade()); // 权重总分数，设置作业总分数=教学内容分数*权重
		jsjxzy.setStatus(Const.JSJXZY_STATUS_CREATE);
		String xuehao = userService.getCurrentUserXuehao(session);
		if (StringUtils.isEmpty(xuehao)) {
			return Msg.error("当前用户未登陆，不能操作");
		}
		jsjxzy.setCreateUserId(xuehao);
		boolean success = jsjxzyService.insert(jsjxzy);
		if (success) {
			// 更新教学内容的剩余权重值
			jxnrService.updateLeave(jxnr, jsjxzy.getWeight(), true);
			return Msg.ok("保存成功");
		}
		return Msg.error("保存出错");
	}

	/**
	 * 显示教学作业详细信息
	 * 
	 * @description
	 * @param @param
	 *            id
	 * @param @return
	 * @return Msg
	 */
	@GetMapping("/jxzy/display/{id}")
	public Msg showJxzyInfo(@PathVariable Long id) {
		if (org.springframework.util.ObjectUtils.isEmpty(id)) {
			return Msg.error("后台获取url参数失败，请调试检查原因");
		}
		List<JsjxzyJxnrCourseVO> jxzyVO = jsjxzyService
				.selectJsjxzyJxnrCourseVOList(new EntityWrapper<JsjxzyJxnrCourseVO>().eq("jsjxzy.id", id));

		if (CollectionUtils.isNotEmpty(jxzyVO)) {
			return Msg.ok(null, jxzyVO.get(0));
		}
		return Msg.error("获取教学作业信息失败");
	}

	/**
	 * 编辑教学作业之前，查看是否能够进行编辑操作 学生作业没有状态为2（已提交状态即可编辑）
	 * 
	 * @description
	 * @param @param
	 *            id
	 * @param @return
	 * @return Msg
	 */
	@GetMapping("/jxzy/edit/{id}")
	@ResponseBody
	public Msg checkEditJxzyValid(@PathVariable Long id) {
		if (org.springframework.util.ObjectUtils.isEmpty(id)) {
			return Msg.error("后台获取url参数失败，请调试检查原因");
		}
		int count = xszyService.selectCount(new EntityWrapper<Xszy>().eq("jsjxzy_id", id).eq("stage", 2));
		if (count > 0) {
			return Msg.error("已经有学生提交该教学作业，不能在进行编辑，前往作业管理中心可查看学生提交作业详细情况");
		}
		List<JsjxzyJxnrCourseVO> jxzyVO = jsjxzyService
				.selectJsjxzyJxnrCourseVOList(new EntityWrapper<JsjxzyJxnrCourseVO>().eq("jsjxzy.id", id));

		if (CollectionUtils.isNotEmpty(jxzyVO)) {
			return Msg.ok(null, jxzyVO.get(0));
		}
		return Msg.error("获取教学作业信息失败");

	}

	/**
	 * 修改教学作业 1 对权值合法性计算验证，同步更新教学内容的剩余权值
	 * 
	 * @description
	 * @param @param
	 *            jsjxzy
	 * @param @return
	 * @return Msg
	 */
	@PostMapping("/jxzy/toedit")
	public Msg modifyJxzy(@RequestBody Jsjxzy jsjxzy) {
		System.out.println(jsjxzy);
		Jxnr jxnr = jxnrService.selectById(jsjxzy.getJxnrId());
		double jxnrLeave = jxnr.getLeave();
		double weight = jsjxzy.getWeight();
		Jsjxzy oldJxzy = jsjxzyService.selectById(jsjxzy.getId());
		double oldWeight = oldJxzy.getWeight();
		if (oldWeight != weight) {
			if (jxnrLeave + oldWeight - weight < 0.0) {
				return Msg.error("权重值修改后的值不正确，请仔细计算和查看提示信息");
			}
		}
		jsjxzy.setGrade(weight * jxnr.getGrade());
		boolean success = jsjxzyService.updateById(jsjxzy);
		if (success) {
			// 更新教学内容剩余权值
			jxnrService.updateLeave(jxnr, weight - oldWeight, true);
			return Msg.ok("保存成功");
		}
		return Msg.error("保存失败");
	}

	/**
	 * 更新教学作业另存为新教学作业
	 * 
	 * @description
	 * @param @param
	 *            jsjxzy
	 * @param @return
	 * @return Msg
	 */
	@PostMapping("/jxzy/toeditNew")
	public Msg modifyToNewJxzy(@RequestBody Jsjxzy jsjxzy) {
		System.out.println(jsjxzy);
		Jxnr jxnr = jxnrService.selectById(jsjxzy.getJxnrId());
		if (jxnr.getLeave() - jsjxzy.getWeight() < 0.0) {
			return Msg.error("教学作业的权重值设置不正确，请仔细查看权重值设置提示信息");
		}
		jsjxzy.setId(null);
		jsjxzy.setGrade(jsjxzy.getWeight() * jxnr.getGrade());
		jsjxzy.setStatus(Const.JSJXZY_STATUS_CREATE);
		boolean success = jsjxzyService.insert(jsjxzy);
		if (success) {
			jxnrService.updateLeave(jxnr, jsjxzy.getWeight(), true);
			return Msg.ok("保存成功");
		}
		return Msg.error("保存失败");
	}

	/**
	 * 删除教学作业， 前提条件是 学生作业没有提交2（已提交状态），并且删除时，会把学生作业也删除掉。同时把教学内容的剩余权值也更新了
	 * 
	 * @description
	 * @param @param
	 *            id
	 * @param @return
	 * @return Msg
	 */
	@GetMapping("/jxzy/delete/{id}")
	public Msg removeJxzy(@PathVariable Long id) {
		int count = xszyService
				.selectCount(new EntityWrapper<Xszy>().eq("jsjxzy_id", id).eq("stage", Const.XSZY_STAGE_SUBMIT));
		if (count > 0) {
			return Msg.error("已经有学生提交该教学作业，不能删除，前往作业管理中心可查看学生提交作业详细情况");
		} else {
			List<Xszy> xszyList = xszyService
					.selectList(new EntityWrapper<Xszy>().eq("jsjxzy_id", id).eq("stage", Const.XSZY_STAGE_CREATE));
			// 将相关学生作业删除
			if (CollectionUtils.isNotEmpty(xszyList)) {
				boolean success = xszyService
						.deleteBatchIds(xszyList.stream().map(Xszy::getId).collect(Collectors.toList()));
				if (!success) {
					return Msg.error("删除失败");
				}
			}
			// 将教师教学作业记录删除
			Jsjxzy jxzy = jsjxzyService.selectById(id);
			boolean delete = jsjxzyService.deleteById(id);
			if (delete) {
				// 将作业权值还给教学内容
				Jxnr jxnr = jxnrService.selectById(jxzy.getJxnrId());
				jxnrService.updateLeave(jxnr, jxzy.getWeight(), false);
				return Msg.ok("删除成功");
			} else {
				return Msg.error("删除失败");
			}
		}

	}
	/**
	 * 在跳转到教学作业统计页面之前，先把教学作业id存在session中
	 * @param id
	 * @param session
	 * @return
	 */
	@GetMapping("/jxzy/tjfx/{id}")
	public Msg showJxzyTjfx(@PathVariable Long id ,HttpSession session) {
		jsjxzyService.setJxzyId(session, id);
		return Msg.ok(null,"/tjfx/jxzytj");
	}

}
