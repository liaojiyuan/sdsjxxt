package com.hebut.sdsjxxt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hebut.sdsjxxt.dvo.XskccjTjVO;
import com.hebut.sdsjxxt.pojo.Xskccj;

/**
* @author gaochaochao
* @date 2019年6月5日 下午12:45:35
* @description
*/
@Repository("xskccjMapper")
public interface XskccjMapper extends BaseMapper<Xskccj>{

	List<Xskccj> getXskccjListByCourseId(@Param("id")Long id);

	List<XskccjTjVO> getXskccjTjVOPageByCondition(RowBounds rowBounds, @Param("ew") Wrapper<XskccjTjVO> wr);

}
