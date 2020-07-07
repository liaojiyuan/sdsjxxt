package com.hebut.sdsjxxt.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hebut.sdsjxxt.common.Const;
import com.hebut.sdsjxxt.interactive.Msg;
import com.hebut.sdsjxxt.interactive.UserSelectWrapper;
import com.hebut.sdsjxxt.pojo.Banji;
import com.hebut.sdsjxxt.pojo.User;
import com.hebut.sdsjxxt.service.BanjiService;
import com.hebut.sdsjxxt.service.UserService;

/**
 * 系统用户信息管理
 */
@RestController
public class SystemController {

	@Autowired
	@Qualifier(value = "userService")
	private UserService userService;
	
	@Autowired
	BanjiService banjiService;
	
	@Autowired
	private GlobalFunction globalFunction;
	
	/**
	 * 
	 * @description 验证码校验
	 * @param @param
	 *            data
	 * @param @param
	 *            request
	 * @param @return
	 * @return Msg
	 */
	@PostMapping("/checkVerifyCode")
	public Msg checkVerifyCode(String data, HttpServletRequest request) {
		String validateCode;
		try {
			validateCode = ((String) request.getSession().getAttribute("validateCode")).toLowerCase();
		} catch (NullPointerException e) {
			return Msg.error("验证码初始化错误，请刷新页面重试");
		}

		String value = data.toLowerCase();

		if (!validateCode.equals(value)) {
			return Msg.error("验证码输入错误");
		} else {
			return Msg.ok();
		}
	}

	/**
	 * 
	 * @description 登陆
	 * @param @param
	 *            user
	 * @param @param
	 *            session
	 * @param @return
	 * @return Msg
	 */
	@PostMapping("/login")
	public Msg login(User user, HttpSession session) {
		boolean success = false;
		try {
			success = userService.login(user.getXuehao(), user.getPassword(), session);
		} catch (Exception e) {
			return Msg.error("用户名或密码错误");
		}

		// 所有用户均重定向对应的展示配送页面
		if (success) {
			User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
			if (Const.ROLETYPE_STUDENT == currentUser.getRole().intValue()) {
				return Msg.ok(null, "/stu/index");
			} else if (Const.ROLETYPE_TEACHER == currentUser.getRole().intValue()) {
				return Msg.ok(null, "/teacher/index");
			} else if (Const.ROLETYPE_MANAGER == currentUser.getRole().intValue()) {
				return Msg.ok(null, "/manager/index");
			}
		}
		return Msg.error("用户名或密码错误");
	}

	/**
	 * 
	 * @description 注册
	 * @param @param
	 *            user
	 * @param @return
	 * @return Msg
	 */
	@PostMapping("/register")
	public Msg register(User user) {
		Map<Integer, String> result = userService.register(user);
		String successMsg = result.get(0);
		String errorMsg = result.get(1);
		if (!StringUtils.isEmpty(successMsg)) {
			return Msg.ok(successMsg, "/login");
		}
		if (!StringUtils.isEmpty(errorMsg)) {
			return Msg.error(errorMsg, null);
		}
		return Msg.ok();
	}

	/**
	 * 
	 * @description 登出
	 * @param @param
	 *            session
	 * @param @return
	 * @return String
	 */
	@RequestMapping(value = "/logout")
	public Msg logout(HttpSession session) {
		session.removeAttribute(Const.CURRENT_USER);
		return Msg.ok(null, "/");
	}

	/**
	 * 
	 * @description 重置密码
	 * @param @param
	 *            oldPassword
	 * @param @param
	 *            newPassword
	 * @param @param
	 *            session
	 * @param @return
	 * @return Msg
	 */
	@PostMapping("/password")
	public Msg resetPassword(@RequestParam String oldPassword, @RequestParam String newPassword, HttpSession session) {
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (!user.getPassword().equals(oldPassword)) {
			return Msg.error(null, "原始密码错误");
		} else {
			user.setPassword(newPassword);
			user.setUpdateTime(new Date());
			boolean result = userService.insertOrUpdate(user);
			if (result) {
				session.removeAttribute(Const.CURRENT_USER);
				session.setAttribute(Const.CURRENT_USER, user);
				return Msg.ok();
			} else {
				return Msg.error(null, "更新密码操作出错");
			}
		}
		// 密码加密存储
		/*
		 * if (!PasswordUtils.validatePassword(oldPassword, user.getPassword()))
		 * { return Msg.error("原始密码错误"); } else {
		 * user.setPassword(PasswordUtils.entryptPassword(newPassword));
		 * userService.updateById(user); return Msg.ok(); }
		 */
	}

	/**
	 * 
	 * @description 获取当前用户信息
	 * @param @param
	 *            session
	 * @param @return
	 * @return Msg
	 */
	@GetMapping("/currentUserInfo")
	public Msg getCurrentUserInfo(HttpSession session) {
		User user = userService.getCurrentUserInfo(session);
		if (user == null) {
			return Msg.error("用户未登陆", "/login");
		} else {
			return Msg.ok(null, user);
		}
	}

	/**
	 * 
	 * @description 获取教师用户列表通过当前学生用户参与的课程的授课教师列表，如果是管理员直接返回全部教师列表
	 * @param @return
	 * @return Msg
	 */
	@GetMapping("/user/teacherList")
	public Msg getCourseList(HttpSession session) {
		User user=(User) session.getAttribute(Const.CURRENT_USER);
		if(!ObjectUtils.isEmpty(user)) {
			Long banjiId=user.getBanjiId();
			List<User> list=userService.getTeacherListByBanjiId(banjiId);
			return Msg.ok(null, list);
		}else {
			return Msg.error("当前用户未登陆");
		}
	}
	
	@GetMapping("/user/studentTableList")
	public Map<String,Object> getStudentTableList(Integer rows, Integer pageSize,UserSelectWrapper userSelectWrapper){
		EntityWrapper<User> wrapper=globalFunction.getUserWrapper(userSelectWrapper);
		wrapper.eq("role",Const.ROLETYPE_STUDENT);
		Page<User> page = userService.selectPage(new Page<>(pageSize,rows), wrapper);
		Map<String,Object> map=new HashMap<>(16);
		map.put("total", page.getTotal());
		map.put("rows", page.getRecords());
		return map;
	}
	
	@GetMapping("/user/xuehaoUnique")
	public Msg checkXuehaoUnique(@RequestParam("xuehao")String xuehao) {
		boolean flag=userService.checkXuehao(xuehao);
		if(!flag) {
			return Msg.error("该学号已经注册过了，请重新输入新的学号");
		}
		return Msg.ok();
	}
	
	@PostMapping("/user/addStudent")
	public Msg saveStudent(@RequestBody User user) {
		System.out.println(user);
		user.setRole(Const.ROLETYPE_STUDENT);
		boolean success = userService.insert(user);
		if(success) {
			//学生所在班级人数加1
			Banji banji=banjiService.selectById(user.getBanjiId());
			banji.setTotalCount(banji.getTotalCount()+1);
			banjiService.updateById(banji);
			return Msg.ok();
		}else {
			return Msg.error("保存学生用户出错");
		}
	}
	
	@GetMapping("/user/teacherTableList")
	public Map<String,Object> getTeacherTableList(Integer rows, Integer pageSize,UserSelectWrapper userSelectWrapper){
		EntityWrapper<User> wrapper=globalFunction.getUserWrapper(userSelectWrapper);
		wrapper.eq("role",Const.ROLETYPE_TEACHER);
		Page<User> page = userService.selectPage(new Page<>(pageSize,rows), wrapper);
		Map<String,Object> map=new HashMap<>(16);
		map.put("total", page.getTotal());
		map.put("rows", page.getRecords());
		return map;
	}
	
	@PostMapping("/user/addTeacher")
	public Msg saveTeacher(@RequestBody User user) {
		System.out.println(user);
		user.setRole(Const.ROLETYPE_TEACHER);
		boolean success = userService.insert(user);
		if(success) {
			return Msg.ok();
		}else {
			return Msg.error("保存教师用户出错");
		}
	}
}
