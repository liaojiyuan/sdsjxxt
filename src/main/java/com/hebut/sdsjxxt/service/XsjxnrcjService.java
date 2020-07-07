package com.hebut.sdsjxxt.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.hebut.sdsjxxt.dvo.XsjxnrcjTjVO;
import com.hebut.sdsjxxt.pojo.Xsjxnrcj;

/**
* @author gaochaochao
* @date 2019年6月5日 上午8:23:13
* @description
*/
public interface XsjxnrcjService  extends IService<Xsjxnrcj>{
	
	List<Xsjxnrcj> getXsjxnrcjListByJxnrId(Long jxnrId);
	
	List<XsjxnrcjTjVO> selectXsjxnrcjTjVOPage(RowBounds rowBounds ,Wrapper<XsjxnrcjTjVO> wr);
}
