package com.hebut.sdsjxxt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hebut.sdsjxxt.common.Const;
import com.hebut.sdsjxxt.mapper.BanjiMapper;
import com.hebut.sdsjxxt.pojo.Banji;
import com.hebut.sdsjxxt.pojo.User;
import com.hebut.sdsjxxt.service.BanjiService;
import com.hebut.sdsjxxt.service.UserService;

/**
 * @author gaochaochao
 * @date 2019年5月15日 下午8:58:26
 * @description
 */
@Service
public class BanjiServiceImpl extends ServiceImpl<BanjiMapper, Banji> implements BanjiService {

	@Autowired
	private BanjiMapper banjiMapper;
	
	@Autowired
	private UserService userService;
	@Override  
	public List<Banji> getBanjiList(HttpSession session) {
		List<Banji> list=null;
		User user = userService.getCurrentUserInfo(session);
		if(!ObjectUtils.isEmpty(user)) {
			//通过 教师--->课程---->班级映射筛选班级列表 
			if(Const.ROLETYPE_TEACHER==user.getRole().intValue()) {
				list=banjiMapper.getBanjiListByTeacherId(user.getXuehao());
			}else {
				list = banjiMapper.selectList(new EntityWrapper<Banji>());
			}
		}
		return list;
	}

	/*
	 * 获取班级id和班级名称之间的映射map
	 */
	@Override
	public Map<Integer, String> getBanjiReflection() {
		List<Banji> list = banjiMapper.selectList(new EntityWrapper<Banji>());
		Map<Integer, String> map = new HashMap<>();
		if (CollectionUtils.isNotEmpty(list)) {
			list.forEach(banji -> map.put(banji.getId().intValue(), banji.getName()));
		}
		return map;
	}

	@Override
	public List<Banji> getBanjiReflection(String banjiIds) {
		return banjiMapper.selectList(new EntityWrapper<Banji>().in("id",banjiIds));
	}

	@Override
	public boolean checkBanjiNameUnique(Banji banji) {
		int count = banjiMapper.selectCount(new EntityWrapper<Banji>().eq("name", banji.getName()));
		return count==0?true:false;
	}

}
