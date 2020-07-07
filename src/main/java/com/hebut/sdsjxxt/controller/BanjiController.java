package com.hebut.sdsjxxt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hebut.sdsjxxt.interactive.Msg;
import com.hebut.sdsjxxt.pojo.Banji;
import com.hebut.sdsjxxt.pojo.Course;
import com.hebut.sdsjxxt.service.BanjiService;
import com.hebut.sdsjxxt.service.CourseService;
import com.hebut.sdsjxxt.service.JsjxzyService;
import com.hebut.sdsjxxt.service.JxnrService;

@RestController
public class BanjiController {

	@Autowired
	private BanjiService banjiService;
	
	@Autowired
	private JsjxzyService jxzyService;

	@Autowired
	private JxnrService jxnrService;
	
	@Autowired
	private CourseService courseService;
	/**
	 * 获取班级列表 通过当前用户过滤
	 * 
	 * @description
	 * @param @return
	 * @return Msg
	 */
	@GetMapping("/banji/userList")
	public Msg getBanjiList(HttpSession session) { 
		List<Banji> list = banjiService.getBanjiList(session);
		if (CollectionUtils.isEmpty(list)) {
			return Msg.error("获取班级信息出错");
		}
		return Msg.ok(null, list);
	}
	/**
	 * 获取所有班级列表  教师添加，编辑课程时从班级表里面筛选班级
	 * @return
	 */
	@GetMapping("/banji/list")
	public Msg getBanjiList() {
		List<Banji> list=banjiService.selectList(new EntityWrapper<Banji>());
		return Msg.ok(null,list);
	}
	
	
	/**
	 * 统计分析模块 查询教学作业对应课程的班级下拉列表内容
	 * @param session
	 * @return
	 */
	@GetMapping("/banji/listByJxzyId")
	public Msg getBanjiListByJxzyId(HttpSession session) {
		Long jxzyId=jxzyService.getJxzyId(session);
		String banjiIds=jxzyService.getBanjiIdsByJsjxzyId(jxzyId);
		List<Banji> list =banjiService.getBanjiReflection(banjiIds);
		if (CollectionUtils.isEmpty(list)) {
			return Msg.error("获取课程的授课班级列表出错");
		}
		return Msg.ok(null, list);
	}
	
	/**
	 * 统计分析模块 查询教学内容对应课程的班级下拉列表内容
	 * @param session
	 * @return
	 */
	@GetMapping("/banji/listByJxnrId")
	public Msg getBanjiListByJxnrId(HttpSession session) {
		Long jxnrId=jxnrService.getJxnrId(session);
		String banjiIds=jxnrService.getBanjiIdsByJxnrId(jxnrId);
		
		List<Banji> list =banjiService.getBanjiReflection(banjiIds);
		if (CollectionUtils.isEmpty(list)) {
			return Msg.error("获取课程的授课班级列表出错");
		}
		return Msg.ok(null, list);
	}
	
	/**
	 * 统计分析模块 查询课程的班级下拉列表内容
	 * @param session
	 * @return
	 */
	@GetMapping("/banji/listByCourseId")
	public Msg getBanjiListByCourseId(HttpSession session) {
		Long courseId=courseService.getCourseId(session);
		Course course=courseService.selectById(courseId);
		List<Banji> list =banjiService.getBanjiReflection(course.getBanjiIds());
		if (CollectionUtils.isEmpty(list)) {
			return Msg.error("获取课程的授课班级列表出错");
		}
		return Msg.ok(null, list);
	}
	
	@PostMapping("/banji/add")
	public Msg saveBanji(@RequestBody Banji banji) {
		boolean unique = banjiService.checkBanjiNameUnique(banji);
		if (!unique) {
			return Msg.error("该班级已存在，请换一个");
		}
		banjiService.insert(banji);
		return Msg.ok("添加成功");
	}
	
	@GetMapping("/banji/tableList")
	public Map<String,Object> getBanjiTableList(Integer rows, Integer page, Integer id,
			HttpSession session) {
		// 得到筛选条件
		EntityWrapper<Banji> wrapper = new EntityWrapper<>();
		if(!ObjectUtils.isEmpty(id)) {
			wrapper.eq("id", id);
		}
		wrapper.orderBy("create_time", false);
		Page<Banji> selectPage = banjiService.selectPage(new Page<>(page, rows), wrapper);
		Map<String, Object> map = new HashMap<>(16);
		map.put("total", selectPage.getTotal());
		map.put("rows", selectPage.getRecords());
		return map;
	}
	
	@GetMapping("banji/delete/{id}")
	public Msg deleteBanji(@PathVariable("id")Long id) {
		boolean success = banjiService.deleteById(id);
		if(success) {
			return Msg.ok();
		}
		return Msg.error("删除失败");
	}

}
