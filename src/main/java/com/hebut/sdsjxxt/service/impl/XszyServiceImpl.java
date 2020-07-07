package com.hebut.sdsjxxt.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hebut.sdsjxxt.dvo.XszyCjcxVO;
import com.hebut.sdsjxxt.dvo.XszyJxzyVO;
import com.hebut.sdsjxxt.mapper.XszyMapper;
import com.hebut.sdsjxxt.pojo.Xszy;
import com.hebut.sdsjxxt.service.XszyService;

@Service
public class XszyServiceImpl extends ServiceImpl<XszyMapper, Xszy> implements XszyService {

	@Autowired
	private XszyMapper xszyMapper;

	/* 
	 */
	@Override
	public List<Xszy> getXszyListByCourseId(Long courseId) {
		return xszyMapper.selectList(new EntityWrapper<Xszy>().eq("course_id", courseId));
	}

	/* 
	 */
	@Override
	public boolean updateXszyStageBatchById(List<Xszy> xszyList) {
		int count = xszyMapper.updateXszyStageBatchById(xszyList);
		return xszyList.size() == count;
	}

	/* 
	 */
	@Override
	public boolean updateXszyStageById(Long id, Integer stage) {
		int count = xszyMapper.updateXszyStageById(id, stage);
		return count == 1;
	}

	/* 
	 */
	@Override
	public boolean updateXszyStage(Xszy xszy) {
		int count = xszyMapper.updateXszyStageById(xszy.getId(), xszy.getStage());
		return count == 1;
	}

	@Override
	public List<XszyJxzyVO> selectPage(RowBounds rowBounds, Wrapper<XszyJxzyVO> ew) {
		return xszyMapper.getXszyJxzyVOPageByCondition(rowBounds,ew);
	}

	@Override
	public XszyJxzyVO getXszyJxzyVOById(Long id) {
		XszyJxzyVO xszyvo=xszyMapper.selectXszyJxzyVOById(id);
		return xszyvo;
	}

	@Override
	public List<XszyCjcxVO> selectXszyCjcxVOPage(RowBounds rowBounds, Wrapper<XszyCjcxVO> ew) {
		return xszyMapper.getXszyCjcxVOPageByCondition(rowBounds,ew);
	}

}
