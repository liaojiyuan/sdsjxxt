package com.hebut.sdsjxxt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hebut.sdsjxxt.common.Const;
import com.hebut.sdsjxxt.dvo.JsjxzyJxnrCourseVO;
import com.hebut.sdsjxxt.dvo.JxnrTjVO;
import com.hebut.sdsjxxt.dvo.JxzyTjVO;
import com.hebut.sdsjxxt.dvo.XsjxnrcjTjVO;
import com.hebut.sdsjxxt.dvo.XskccjTjVO;
import com.hebut.sdsjxxt.dvo.XszyCjcxVO;
import com.hebut.sdsjxxt.interactive.JxnrTjSelectWrapper;
import com.hebut.sdsjxxt.interactive.JxzyTjSelectWrapper;
import com.hebut.sdsjxxt.interactive.Msg;
import com.hebut.sdsjxxt.interactive.StucjcxSelectWrapper;
import com.hebut.sdsjxxt.interactive.XskccjTjSelectWrapper;
import com.hebut.sdsjxxt.pojo.Course;
import com.hebut.sdsjxxt.pojo.Jxnr;
import com.hebut.sdsjxxt.service.CourseService;
import com.hebut.sdsjxxt.service.JsjxzyService;
import com.hebut.sdsjxxt.service.JxnrService;
import com.hebut.sdsjxxt.service.UserService;
import com.hebut.sdsjxxt.service.XsjxnrcjService;
import com.hebut.sdsjxxt.service.XskccjService;
import com.hebut.sdsjxxt.service.XszyService;

@Controller
public class TjfxController{
	@Autowired
	private JsjxzyService jxzyService;
	
	@Autowired
	private XszyService xszyService;
	
	@Autowired
	private JxnrService jxnrService;
	
	@Autowired
	private XsjxnrcjService xsjxnrcjService;
	
	@Autowired
	private XskccjService xskccjService;
	
	@Autowired
	private GlobalFunction globalFunction;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/jxzytj/tableList")
	@ResponseBody
	public Map<String,Object> getJxzyTjTable(Integer rows, Integer page, JxzyTjSelectWrapper esw, HttpSession session) {
		Long jxzyId=jxzyService.getJxzyId(session);
		System.out.println(jxzyId);
		Map<String, Object> map = new HashMap<>(16);
		Page<JxzyTjVO> selectPages = new Page<>(page, rows);
		EntityWrapper<JxzyTjVO> wrapper=globalFunction.getJxzyTjVOWrapper(esw);
		wrapper.eq("jsjxzy.id", jxzyId).eq("jsjxzy.sf_finished", Const.SF_S);
		List<JxzyTjVO> list = jxzyService.selectJxzyTjVOPage(selectPages, wrapper);
		map.put("total",selectPages.getTotal());
		map.put("rows",list);
		return map;
	}
	
	
	@GetMapping("/jxzytj/title")
	@ResponseBody
	public Msg getJxzyTjTitle(HttpSession session) {
		Long jxzyId=jxzyService.getJxzyId(session);
		List<JsjxzyJxnrCourseVO> list = jxzyService.selectJsjxzyJxnrCourseVOList(new EntityWrapper<JsjxzyJxnrCourseVO>().eq("jsjxzy.id",jxzyId));
		if(CollectionUtils.isNotEmpty(list)) {
			return Msg.ok(null,list.get(0));
		}
		return Msg.error("无法找到记录");
		
	}
	
	@GetMapping("/jxnrtj/title")
	@ResponseBody
	public Msg getJxnrTjTitle(HttpSession session) {
		Long jxnrId=jxnrService.getJxnrId(session);
		 Jxnr jxnr = jxnrService.selectById(jxnrId);
		if(!ObjectUtils.isEmpty(jxnr)) {
			return Msg.ok(null,jxnr);
		}
		return Msg.error("无法找到记录");
		
	}
	
	@GetMapping("/jxnrtj/tableList")
	@ResponseBody
	public Map<String,Object> getJxnrTjTable(Integer rows, Integer page, JxnrTjSelectWrapper esw, HttpSession session) {
		Long jxnrId=jxnrService.getJxnrId(session);
		System.out.println(jxnrId);
		Map<String, Object> map = new HashMap<>(16);
		Page<JxzyTjVO> selectPages = new Page<>(page, rows);
		EntityWrapper<JxnrTjVO> wrapper=globalFunction.getJxnrTjVOWrapper(esw);
		wrapper.eq("jxnr.id", jxnrId).eq("jxnr.sf_finished", Const.SF_S);
		wrapper.groupBy("jsjxzy.id,jsjxzy.stage,xszy.id");
		wrapper.orderBy("jsjxzy.stage", true);
		wrapper.orderBy("user.xuehao", true);//学号升序排序
		List<JxnrTjVO> list = jxnrService.selectJxnrTjVOPage(selectPages, wrapper);
		map.put("total",selectPages.getTotal());
		map.put("rows",list);
		return map;
	}
	
	@GetMapping("/xsjxnrcjtj/tableList")
	@ResponseBody
	public Map<String,Object> getXsjxnrcjTjTable(Integer rows, Integer page, JxnrTjSelectWrapper esw, HttpSession session) {
		Long jxnrId=jxnrService.getJxnrId(session);
		System.out.println(jxnrId);
		Map<String, Object> map = new HashMap<>(16);
		Page<XsjxnrcjTjVO> selectPages = new Page<>(page, rows);
		EntityWrapper<XsjxnrcjTjVO> wrapper=globalFunction.getXsjxnrcjTjVOWrapper(esw);
		wrapper.eq("xsjxnrcj.jxnr_id", jxnrId);
		wrapper.groupBy("xsjxnrcj.jxnr_id ,xsjxnrcj.user_id");
		wrapper.orderBy("xsjxnrcj.user_id", true);//学号升序排序
		List<XsjxnrcjTjVO> list = xsjxnrcjService.selectXsjxnrcjTjVOPage(selectPages, wrapper);
		map.put("total",selectPages.getTotal());
		map.put("rows",list);
		return map;
	}
	
	/**
	 * 
	 * @param session
	 * @return
	 */
	@GetMapping("/coursetj/title")
	@ResponseBody
	public Msg getCourseTjTitle(HttpSession session) {
		Long id = courseService.getCourseId(session);
		Course course = courseService.selectById(id);
		if(!ObjectUtils.isEmpty(course)) {
			return Msg.ok(null,course);
		}
		return Msg.error("获取记录出错");
	}
	
	
	@GetMapping("/kctj/tableList")
	@ResponseBody
	public Map<String,Object> getKctjTable(Integer rows, Integer page, JxnrTjSelectWrapper esw, HttpSession session){
		Long courseId=courseService.getCourseId(session);
		System.out.println(courseId);
		Map<String, Object> map = new HashMap<>(16);
		Page<XsjxnrcjTjVO> selectPages = new Page<>(page, rows);
		EntityWrapper<XsjxnrcjTjVO> wrapper=globalFunction.getXsjxnrcjTjVOWrapper(esw);
		wrapper.eq("xsjxnrcj.course_id", courseId);
		wrapper.groupBy("xsjxnrcj.jxnr_id ,xsjxnrcj.user_id");
		wrapper.orderBy("xsjxnrcj.user_id", true);//学号升序排序
		List<XsjxnrcjTjVO> list = xsjxnrcjService.selectXsjxnrcjTjVOPage(selectPages, wrapper);
		map.put("total",selectPages.getTotal());
		map.put("rows",list);
		return map;
	}
	
	@GetMapping("/xskccjtj/tableList")
	@ResponseBody
	public Map<String,Object> getXskccjTjTable(Integer rows, Integer page, XskccjTjSelectWrapper esw, HttpSession session){
		Long courseId=courseService.getCourseId(session);
		System.out.println(courseId);
		Map<String, Object> map = new HashMap<>(16);
		Page<XsjxnrcjTjVO> selectPages = new Page<>(page, rows);
		EntityWrapper< XskccjTjVO> wrapper=globalFunction.getXskccjTjVOWrapper(esw);
		wrapper.eq("xskccj.course_id", courseId);
		wrapper.groupBy("user.banji_id ,xskccj.user_id");
		wrapper.orderBy("xskccj.user_id", true);//学号升序排序
		List<XskccjTjVO> list = xskccjService.selectXskccjTjVOPage(selectPages, wrapper);
		map.put("total",selectPages.getTotal());
		map.put("rows",list);
		return map;
	}
	
	
	@GetMapping("/stucjcx/tableList")
	@ResponseBody
	public Map<String,Object> getStucjcxTable(Integer rows, Integer page, StucjcxSelectWrapper esw, HttpSession session){
		Map<String, Object> map = new HashMap<>(16);
		Page<XszyCjcxVO> selectPages = new Page<>(page, rows);
		EntityWrapper< XszyCjcxVO> wrapper=globalFunction.getXszyCjcxVOWrapper(esw);
		String xuehao = userService.getCurrentUserXuehao(session);
		wrapper.eq("xszy.user_id", xuehao);
		wrapper.groupBy("xszy.course_id,xszy.jxnr_id,xszy.jsjxzy_id,xszy.user_id");
		wrapper.orderBy("xszy.create_time", false);
		List<XszyCjcxVO> list =xszyService.selectXszyCjcxVOPage(selectPages, wrapper);
		map.put("total",selectPages.getTotal());
		map.put("rows",list);
		return map;
	}
}
