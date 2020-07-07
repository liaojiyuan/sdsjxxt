package com.hebut.sdsjxxt.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hebut.sdsjxxt.dvo.XsjxnrcjTjVO;
import com.hebut.sdsjxxt.mapper.XsjxnrcjMapper;
import com.hebut.sdsjxxt.pojo.Xsjxnrcj;
import com.hebut.sdsjxxt.service.XsjxnrcjService;

/**
* @author gaochaochao
* @date 2019年6月5日 上午8:23:56
* @description
*/
@Service
public class XsjxnrcjServiceImpl extends ServiceImpl<XsjxnrcjMapper, Xsjxnrcj> implements XsjxnrcjService{
	@Autowired
	private XsjxnrcjMapper xsjxnrcjMapper;	
	
	@Override
	public List<Xsjxnrcj> getXsjxnrcjListByJxnrId(Long jxnrId) {
		return xsjxnrcjMapper.getXsjxnrcjListByJxnrId(jxnrId);
	}
	
	@Override
	public List<XsjxnrcjTjVO> selectXsjxnrcjTjVOPage(RowBounds rowBounds, Wrapper<XsjxnrcjTjVO> wr) {
		return xsjxnrcjMapper.getXsjxnrcjTjVOPageByCondition(rowBounds,wr);
	}
}
