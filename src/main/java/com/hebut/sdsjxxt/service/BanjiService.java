package com.hebut.sdsjxxt.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.baomidou.mybatisplus.service.IService;
import com.hebut.sdsjxxt.pojo.Banji;

/**
 * @author gaochaochao
 * @date 2019年5月15日 下午8:57:39
 * @description
 */
public interface BanjiService extends IService<Banji> {
	// 获取班级列表 通过当前用户参与的课程
	List<Banji> getBanjiList(HttpSession session);
	//获取所有的班级id和name的map
	Map<Integer, String> getBanjiReflection();
	//获取banjiIds字符串包含的key的name
	List<Banji> getBanjiReflection(String banjiIds);
	boolean checkBanjiNameUnique(Banji banji);
}
