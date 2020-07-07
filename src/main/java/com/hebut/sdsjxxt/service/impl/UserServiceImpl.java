package com.hebut.sdsjxxt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hebut.sdsjxxt.common.Const;
import com.hebut.sdsjxxt.mapper.BanjiMapper;
import com.hebut.sdsjxxt.mapper.UserMapper;
import com.hebut.sdsjxxt.pojo.Banji;
import com.hebut.sdsjxxt.pojo.User;
import com.hebut.sdsjxxt.service.UserService;

/**
 * @author gaochaochao
 * @date 2019年5月15日 下午1:35:57
 * @description
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	@Autowired
	private BanjiMapper banjiMapper;

	@Autowired
	private UserMapper userMapper;

	// 登录校验
	@Override
	public boolean login(String xuehao, String password, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		map.put("xuehao", xuehao);
		map.put("password", password);
		List<User> list = userMapper.selectByMap(map);
		if (CollectionUtils.isEmpty(list)) {
			return false;
		}
		User user = list.get(0);

		// 将用户信息存放在session中
		session.setAttribute(Const.CURRENT_USER, user);
		return true;
	}

	/**
	 * 校验学号不重复
	 */
	@Override
	public boolean checkXuehao(String xuehao) {
		int count = userMapper.selectCount(new EntityWrapper<User>().eq("xuehao", xuehao));
		if (count > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 用户注册 1 验证学号唯一 2 验证注册类型，学生类型必须填写班级信息 ,注册成功，班级人数加1
	 * 
	 * @return 返回{ 0, 注册成功}或者{1,,注册失败信息}
	 */
	@Override
	public Map<Integer, String> register(User user) {
		Map<Integer, String> result = new HashMap<>();
		// 对学号进行验证
		boolean uinque = (boolean) this.checkXuehao(user.getXuehao());
		StringBuilder sb = new StringBuilder();
		if (!uinque) {
			sb.append("该学号已经注册过了，请换一个<br/>");
		}
		//
		int role = user.getRole().intValue();
		Long banjiId = user.getBanjiId();
		if (role == 1 && null == banjiId) {
			sb.append("学生用户注册时，班级信息必须填写<br/>");
		}
		if (StringUtils.isEmpty(sb.toString())) {
			int count = saveUser(user);
			if (count == 1) {
				result.put(0, "注册成功");
				Banji banji=banjiMapper.selectById(user.getBanjiId());
				banji.setTotalCount(banji.getTotalCount()+1);
				banjiMapper.updateById(banji);
			} else {
				result.put(1, "注册失败，保存用户信息出错");
			}
		} else {
			result.put(1, sb.toString());
		}
		return result;
	}

	/**
	 * @description 保存用户信息
	 * @param @param
	 *            user
	 * @return void
	 */
	private int saveUser(User user) {
		int role = user.getRole().intValue();
		if (role == 1) {
			// 设置班级名称
			Banji banji = banjiMapper.selectById(user.getBanjiId());
			user.setBanjiName(banji.getName());
		} else {
			// 教师，管理员注册，班级信息不需要
			user.setBanjiId(null);
			user.setBanjiName(null);
		}
		// 存到用户表
		return userMapper.insert(user);

	}

	/*
	 * 返回教师用户列表
	 * 学生用户 banjiId不为null，通过班级--->课程--->授课教师
	 * 管理员查询  banjiId为null ，全部查询 
	 */
	@Override
	public List<User> getTeacherListByBanjiId(Long banjiId) {
		if(ObjectUtils.isEmpty(banjiId)) { //管理员返回全部教师用户列表
			return userMapper.selectList(new EntityWrapper<User>().eq("role",2));
		}
		return  userMapper.getTeacherListByBanjiId(banjiId);
	}

	/*
	 * 获取当前用户学号
	 */
	@Override
	public String getCurrentUserXuehao(HttpSession session) {
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (org.springframework.util.ObjectUtils.isEmpty(user)) {
			return null;
		}
		return user.getXuehao();
	}
	
	/*
	 * 获取当前用户信息
	 */
	@Override
	public User getCurrentUserInfo(HttpSession session) {
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (org.springframework.util.ObjectUtils.isEmpty(user)) {
			return null;
		}
		return user;
	}

}
