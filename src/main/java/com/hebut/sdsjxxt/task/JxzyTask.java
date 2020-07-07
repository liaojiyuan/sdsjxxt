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
import com.hebut.sdsjxxt.pojo.Jsjxzy;
import com.hebut.sdsjxxt.pojo.Xszy;
import com.hebut.sdsjxxt.service.CourseService;
import com.hebut.sdsjxxt.service.JsjxzyService;

import com.hebut.sdsjxxt.service.XszyService;

/**
* @author gaochaochao
* @date 2019年6月1日 下午5:53:21
* @description 设置教学作业状态是否完成
*/
@Service
public class JxzyTask implements IJxzyTask {

	Logger log = Logger.getLogger(JxzyTask.class);
	@Autowired
	private JsjxzyService jsjxzyService;
	
	@Autowired
	private CourseService courseService;

	

	@Autowired
	private XszyService xszyService;
	
	/**
	 * 查询教学作业是否已经全部完成，设置jxzy.sf_finished=1
	 * 查询条件  jxzy.status=3(已截止) and jxzy.sf_finished=0 (未设置完成教学哦作业标志)
	 * 依据 jxzy.id取xszy查询 xszy.stage=4(已批改)的总数 ，是否等于参与课程的总人数
	 */
	@Scheduled(cron="0 0/1 * * * ? ") //每10分钟执行一次
	@Override
	public void showJxzyAnalysisBtn() {
		log.info(log.getClass().getName() + ":" + "开始执行定时任务showJxzyAnalysisBtn------------------------------------------------");
		//查询教学作业已结束且 sf_finished为0
		List<Jsjxzy> jxzyList=jsjxzyService.selectList(new EntityWrapper<Jsjxzy>().eq("status",Const.JSJXZY_STATUS_EXPIRE).eq("sf_finished", Const.SF_F));
		List<Jsjxzy> list=new ArrayList<>();
		if(CollectionUtils.isNotEmpty(jxzyList)) {
			for (Jsjxzy jxzy : jxzyList) {
				Course course=courseService.selectById(jxzy.getCourseId());
				//查询该教学作业下的学生作业状态为4（已批改）的总数
				int count=xszyService.selectCount(new EntityWrapper<Xszy>().eq("jsjxzy_id",jxzy.getId()).eq("stage", Const.XSZY_STAGE_YPG));
				if(count==course.getTotalCount()) {
					jxzy.setSfFinished(Const.SF_S);
					jxzy.setUpdateTime(new Date());
					list.add(jxzy);
				}
			}
			if(CollectionUtils.isNotEmpty(list)) {
				jsjxzyService.updateBatchById(list);
				log.info(log.getClass().getName() + ":" + "定时任务 showJxzyAnalysisBtn执行完成，共检测到" +  list.size() + "条教师教学作业需要更新完成字段"
				+ "------------------------------------------------");
				return ;
			}
		}
		log.info(log.getClass().getName() + ":" + "定时任务 showJxzyAnalysisBtn执行完成，没有检测到教师教学作业需要更新完成字段！"+
					 "------------------------------------------------");
	}

}
