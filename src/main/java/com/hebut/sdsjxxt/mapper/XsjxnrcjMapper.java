package com.hebut.sdsjxxt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hebut.sdsjxxt.dvo.XsjxnrcjTjVO;
import com.hebut.sdsjxxt.pojo.Xsjxnrcj;

/**
* @author gaochaochao
* @date 2019年6月5日 上午8:20:50
* @description
*/
@Repository("xsjxnrcjMapper")
public interface XsjxnrcjMapper extends BaseMapper<Xsjxnrcj>{
	//获取 学生教学内容成绩表
	List<Xsjxnrcj> getXsjxnrcjListByJxnrId(@Param("id")Long jxnrId);

	List<XsjxnrcjTjVO> getXsjxnrcjTjVOPageByCondition(RowBounds rowBounds, @Param("ew")Wrapper<XsjxnrcjTjVO> wr);
}
