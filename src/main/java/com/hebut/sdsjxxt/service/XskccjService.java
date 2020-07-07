package com.hebut.sdsjxxt.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.hebut.sdsjxxt.dvo.XskccjTjVO;
import com.hebut.sdsjxxt.pojo.Xskccj;

/**
* @author gaochaochao
* @date 2019年6月5日 下午12:47:15
* @description
*/
public interface XskccjService extends IService<Xskccj>{

	List<Xskccj> getXskccjListByCourseId(Long id);

	List<XskccjTjVO> selectXskccjTjVOPage(RowBounds rowBounds, Wrapper<XskccjTjVO> wrapper);

}
