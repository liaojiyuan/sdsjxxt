package com.hebut.sdsjxxt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 页面跳转Controller
 * 
 * @author jitwxs
 * @date 2018/4/23 10:39
 */
@Controller
public class PageController {

	@GetMapping("/")
	public String showIndex() {
		return "login";
	}

	// 登陆
	@GetMapping("/login")
	public String showLogin() {
		return "login";
	}

	// 注册
	@GetMapping("/register")
	public String showRegister() {
		return "register";
	}

	// 学生主页
	@GetMapping("/stu/index")
	public String showStuIndex() {
		return "stu/index";
	}

	@GetMapping("/stu/jxnrcenter")
	public String showStudentJxnrcenter() {
		return "stu/jxnrcenter";
	}
	
	@GetMapping("/stu/jxzycenter")
	public String showStudentJxzycenter() {
		return "stu/jxzycenter";
	}
	
	@GetMapping("/stu/xszyglzx")
	public String showStudentXszyglzx() {
		return "stu/xszyglzx";
	}
	
	// 教师主页
	@GetMapping("/teacher/index")
	public String showTeacherIndex() {
		return "teacher/index";
	}

	@GetMapping("/teacher/jxnrcenter")
	public String showTeacherJxnrcenter() {
		return "teacher/jxnrcenter";
	}

	@GetMapping("/teacher/jxzycenter")
	public String showTeacherJxzycenter() {
		return "teacher/jxzycenter";
	}


	@GetMapping("/teacher/xszyglzx")
	public String showTeacherXszyglzx() {
		return "teacher/xszyglzx";
	}
	
	// 管理员主页
	@GetMapping("/manager/index")
	public String showManagerIndex() {
		return "manager/index";
	}
	//教学作业学生成绩统计
	@GetMapping("/tjfx/jxzytj")
	public String showJxzytj() {
		return "tjfx/jxzytj";
	}
	//教学内容 学生成绩统计
	@GetMapping("/tjfx/jxnrtj")
	public String showJxnrtj() {
		return "tjfx/jxnrtj";
	}
	
	//学生教学内容成绩统计
	@GetMapping("/tjfx/xsjxnrcjtj")
	public String showXsjxnrcjtj() {
		return "tjfx/xsjxnrcjtj";
	}
	
	//学生教学内容成绩统计
	@GetMapping("/tjfx/kctj")
	public String showKctj() {
		return "tjfx/kctj";
	}
	
	//学生教学内容成绩统计
	@GetMapping("/tjfx/xskccjtj")
	public String showxskccjtj() {
		return "tjfx/xskccjtj";
	}
	
	
	
	//学生成绩查询
	@GetMapping("/tjfx/stucjcx")
	public String showStucjcx() {
		return "tjfx/stucjcx";
	}
	
	@GetMapping("/manager/banjicenter")
	public String showBanjiCenter() {
		return "manager/banjicenter";
	}
	
	@GetMapping("/manager/studentcenter")
	public String showStudentCenter() {
		return "manager/studentcenter";
	}
	
	@GetMapping("/manager/teachercenter")
	public String showTeacherCenter() {
		return "manager/teachercenter";
	}

}