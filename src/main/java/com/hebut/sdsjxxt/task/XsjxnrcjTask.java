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
import com.hebut.sdsjxxt.pojo.Jxnr;
import com.hebut.sdsjxxt.pojo.Xsjxnrcj;
import com.hebut.sdsjxxt.service.JxnrService;
import com.hebut.sdsjxxt.service.XsjxnrcjService;

/**
* @author gaochaochao
* @date 2019年6月5日 上午8:17:22
* @description 生成学生教学内容成绩记录
*/
@Service
public class XsjxnrcjTask implements IXsjxnrcjTask {

	Logger log = Logger.getLogger(XsjxnrcjTask.class);
	
	@Autowired
	private XsjxnrcjService xsjxnrcjService;
	@Autowired
	private JxnrService jxnrService;
	
	
	@Scheduled(cron="0 0/1 * * * ?") //每隔10分钟检查一次
	@Override
	public void insertXsjxnrcj() {
		log.info(log.getClass().getName() + ":" + "开始执行定时任务insertXsjxnrcj------------------------------------------------");
		
		List<Jxnr> jxnrList = jxnrService.selectList(
				new EntityWrapper<Jxnr>().eq("sf_finished",Const.SF_S).eq("sf_tj",Const.SF_F));
		if(CollectionUtils.isNotEmpty(jxnrList)) {
			List<Xsjxnrcj> xsjxnrcjList = new ArrayList<>();
			for(Jxnr jxnr:jxnrList) {
				List<Xsjxnrcj> list=xsjxnrcjService.getXsjxnrcjListByJxnrId(jxnr.getId());
				list.forEach(xsjxnrcj->{
					xsjxnrcj.setCreateTime(new Date());
					xsjxnrcj.setUpdateTime(new Date());
					xsjxnrcjList.add(xsjxnrcj);
				});
			}
			xsjxnrcjService.insertBatch(xsjxnrcjList);
			jxnrList.forEach(jxnr->{
				jxnr.setSfTj(Const.SF_S);
				jxnr.setUpdateTime(new Date());
			});
			jxnrService.updateBatchById(jxnrList);
			log.info(log.getClass().getName() + ":" + "定时任务 insertXsjxnrcj执行完成，共检测到"  + jxnrList.size() 
					+ "条教学内容记录需要生成学生教学内容记录，本次插入 学生教学内容成绩记录共"+xsjxnrcjList.size()+"条记录"
					+ "------------------------------------------------");
		}else {
			log.info(log.getClass().getName() + ":" + "定时任务 insertXsjxnrcj执行完成，没有共检测到教学内容记录需要生成学生教学内容记录"  
			+ "------------------------------------------------");
		}
	}

}
