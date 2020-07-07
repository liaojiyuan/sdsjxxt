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
import com.hebut.sdsjxxt.pojo.Jsjxzy;
import com.hebut.sdsjxxt.pojo.Jxnr;
import com.hebut.sdsjxxt.service.JsjxzyService;
import com.hebut.sdsjxxt.service.JxnrService;

/**
* @author gaochaochao
* @date 2019年6月1日 下午5:35:19
* @description 教学内容定时器  检查教学内容是否已经完成，完成之后可以显示学生统计表
*/

@Service
public class JxnrTask implements IJxnrTask {

	Logger log = Logger.getLogger(JxnrTask.class);
	@Autowired
	private JsjxzyService jsjxzyService;
	@Autowired
	private JxnrService jxnrService;
	
	
	/**
	 * 查询教学作业，如果关联的三条教学作业记录的字段sf_finished都为1，则该教学内容的字段sf_finished=1，即可以查看学生统计表
	 */
	
	@Scheduled(cron="0 0/1 * * * ?") // 每10分钟执行一次
	@Override
	public void showJxnrAnalysisBtn() {
		log.info(log.getClass().getName() + ":" + "开始执行定时任务showJxnrAnalysisBtn------------------------------------------------");
		//查询 jsjxzy.sf_finished=1 ,按照jxnr_id分组，分组之后条数等于3条
		List<Jsjxzy> jxzyList = jsjxzyService.selectList(new EntityWrapper<Jsjxzy>().
				eq("sf_finished", Const.SF_S).
				groupBy("jxnr_id").
				having("count(*)={0}", 3));
		if(CollectionUtils.isNotEmpty(jxzyList)) {
			List<Jxnr> jxnrList=new ArrayList<>();
			for (Jsjxzy jxzy : jxzyList) {
				Jxnr jxnr=jxnrService.selectById(jxzy.getJxnrId());
				if(Const.SF_S==jxnr.getSfFinished().intValue()) {
					continue;
				}
				jxnr.setSfFinished(Const.SF_S);
				jxnr.setUpdateTime(new Date());
				jxnrList.add(jxnr);
			}
			if(CollectionUtils.isNotEmpty(jxnrList)) {
				jxnrService.updateBatchById(jxnrList);
				log.info(log.getClass().getName() + ":" + "定时任务 showJxnrAnalysisBtn执行完成，共检测到"  + jxnrList.size() + "条教学内容记录需要设置为完成状态"
				+ "------------------------------------------------");
			}else {
				log.info(log.getClass().getName() + ":" + "定时任务 showJxnrAnalysisBtn执行完成，没有检测到教师教学内容需要更新完成字段！"+
						 "------------------------------------------------");
			}
			
		}else {
			log.info(log.getClass().getName() + ":" + "定时任务 showJxnrAnalysisBtn执行完成，没有检测到教师教学内容需要更新完成字段！"+
					 "------------------------------------------------");
		}

	}

}
