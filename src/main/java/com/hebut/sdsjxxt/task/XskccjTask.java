package com.hebut.sdsjxxt.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hebut.sdsjxxt.common.Const;
import com.hebut.sdsjxxt.pojo.Course;
import com.hebut.sdsjxxt.pojo.Xskccj;
import com.hebut.sdsjxxt.service.CourseService;
import com.hebut.sdsjxxt.service.XskccjService;

/**
* @author gaochaochao
* @date 2019年6月5日 下午12:56:06
* @description
*/
@Service
public class XskccjTask implements IXskccjTask {

	Logger log = Logger.getLogger(IXskccjTask.class);
	
	@Autowired
	private CourseService courseService;

	@Autowired
	private XskccjService xskccjService;

	
	@Scheduled(cron="0 0/1 * * * ?")
	@Override
	public void insertXskccj() {
		List<Course> courseList = courseService.selectList(new EntityWrapper<Course>().eq("sf_tj", Const.SF_F).lt("end_date", new Date()));
		if(CollectionUtils.isNotEmpty(courseList)) {
			List<Xskccj> xskccjList=new ArrayList<>();
			for(Course course:courseList) {
				List<Xskccj> list=xskccjService.getXskccjListByCourseId(course.getId());
				list.forEach(xskccj->{
					xskccj.setCreateTime(new Date());
					xskccj.setUpdateTime(new Date());
					xskccjList.add(xskccj);
				});
			}
			if(CollectionUtils.isNotEmpty(xskccjList)) {
				xskccjService.insertBatch(xskccjList);
				courseList.forEach(course->{
					course.setSfTj(Const.SF_S);
					course.setUpdateTime(new Date());
				});
				courseService.updateBatchById(courseList);
				log.info(log.getClass().getName() + ":" + "定时任务 insertXskccj执行完成，共检测到"  + courseList.size() 
				+ "条课程记录需要生成学生课程成绩记录，本次插入 学生课程成绩记录共"+xskccjList.size()+"条记录"
				+ "------------------------------------------------");
				return ;
			}
			
		}
		log.info(log.getClass().getName() + ":" + "定时任务 insertXskccj执行完成，没有共检测到课程记录需要生成学生课程成绩记录"  
					+ "------------------------------------------------");
	}

}
