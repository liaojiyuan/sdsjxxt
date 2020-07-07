package com.hebut.sdsjxxt.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.baomidou.mybatisplus.service.IService;
import com.hebut.sdsjxxt.pojo.User;

/**
 * @author gaochaochao
 * @date 2019年5月15日 下午1:35:44
 * @description
 */
public interface UserService extends IService<User> {

	boolean login(String xuehao, String password, HttpSession session);

	// 注册时验证学号唯一不重复
	boolean checkXuehao(String xuehao);

	String getCurrentUserXuehao(HttpSession session);

	// 注册新用户
	Map<Integer, String> register(User user);

	List<User> getTeacherListByBanjiId(Long banjiId);
	
	User getCurrentUserInfo(HttpSession session);
}
