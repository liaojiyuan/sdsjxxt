package com.hebut.sdsjxxt.service.impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hebut.sdsjxxt.dvo.JsjxzyJxnrCourseVO;
import com.hebut.sdsjxxt.dvo.JxzyTjVO;
import com.hebut.sdsjxxt.mapper.JsjxzyMapper;
import com.hebut.sdsjxxt.pojo.Jsjxzy;
import com.hebut.sdsjxxt.service.JsjxzyService;
import com.hebut.sdsjxxt.service.UserService;

@Service
public class JsjxzyServiceImpl extends ServiceImpl<JsjxzyMapper, Jsjxzy> implements JsjxzyService {

	@Autowired
	private JsjxzyMapper jsjxzyMapper;
	
	@Autowired
	private UserService userService;
	@Override
	public List<Jsjxzy> getJsjxzyListByJxnrId(Long jxnrId) {
		List<Jsjxzy> list = jsjxzyMapper.selectList(new EntityWrapper<Jsjxzy>().eq("jxnr_id", jxnrId));
		return list;
	}

	@Override
	public List<JsjxzyJxnrCourseVO> selectPage(RowBounds rowBounds, Wrapper<JsjxzyJxnrCourseVO> ew) {
		List<JsjxzyJxnrCourseVO> list = jsjxzyMapper.getJsjxzyJxnrCourseVOPageByCondition(rowBounds, ew);
		return list;
	}

	@Override
	public List<JsjxzyJxnrCourseVO> selectJsjxzyJxnrCourseVOList(Wrapper<JsjxzyJxnrCourseVO> ew) {
		return jsjxzyMapper.getJsjxzyJxnrCourseVOByCondition(ew);
	}

	/* 
	 */
	@Override
	public boolean updateJxzyStatusBatchById(List<Jsjxzy> jsjxzyList) {
		int count = jsjxzyMapper.updateJxzyStatusBatchById(jsjxzyList);
		return count == jsjxzyList.size();
	}

	/* 
	 */
	@Override
	public boolean updateJxzyStatusById(Long id, Integer status) {
		int count = jsjxzyMapper.updateJxzyStatusById(id, status);
		return count == 1;
	}

	/* 
	 */
	@Override
	public boolean updateJxzyStatus(Jsjxzy jxzy) {
		int count = jsjxzyMapper.updateJxzyStatusById(jxzy.getId(), jxzy.getStatus());
		return count == 1;
	}

	@Override
	public void setJxzyId(HttpSession session, Long id) {
		String xuehao = userService.getCurrentUserXuehao(session);
		String token=new StringBuilder().append("jxzy").append("_").append(xuehao).toString();
		session.setAttribute(token, id);
	}

	@Override
	public Long getJxzyId(HttpSession session) {
		//教师工号
		String xuehao = userService.getCurrentUserXuehao(session);
		String token=new StringBuilder().append("jxzy").append("_").append(xuehao).toString();
		Long id=(Long) session.getAttribute(token);
		return id;
	}
	
	@Override
	public String getBanjiIdsByJsjxzyId(Long id) {
		return jsjxzyMapper.getBanjiIdsByJsjxzyId(id);
	}

	@Override
	public List<JxzyTjVO> selectJxzyTjVOPage(RowBounds rowBounds, Wrapper<JxzyTjVO> ew) {
		return jsjxzyMapper.getJxzyTjVOPageByCondition(rowBounds, ew);
	}

	
}
