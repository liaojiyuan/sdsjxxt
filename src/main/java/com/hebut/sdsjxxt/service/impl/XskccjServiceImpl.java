package com.hebut.sdsjxxt.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hebut.sdsjxxt.dvo.XskccjTjVO;
import com.hebut.sdsjxxt.mapper.XskccjMapper;
import com.hebut.sdsjxxt.pojo.Xskccj;
import com.hebut.sdsjxxt.service.XskccjService;

/**
* @author gaochaochao
* @date 2019年6月5日 下午12:48:08
* @description
*/
@Service
public class XskccjServiceImpl extends ServiceImpl<XskccjMapper, Xskccj> implements XskccjService {
	
	@Autowired
	private XskccjMapper xskccjMapper;

	@Override
	public List<Xskccj> getXskccjListByCourseId(Long id) {
		return xskccjMapper.getXskccjListByCourseId(id);
	}

	@Override
	public List<XskccjTjVO> selectXskccjTjVOPage(RowBounds rowBounds, Wrapper<XskccjTjVO> wr) {
		return xskccjMapper.getXskccjTjVOPageByCondition(rowBounds,wr);
	}	
}
