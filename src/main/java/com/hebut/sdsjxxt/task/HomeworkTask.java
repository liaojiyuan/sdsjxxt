package com.hebut.sdsjxxt.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hebut.sdsjxxt.common.Const;
import com.hebut.sdsjxxt.pojo.Course;
import com.hebut.sdsjxxt.pojo.Jsjxzy;
import com.hebut.sdsjxxt.pojo.User;
import com.hebut.sdsjxxt.pojo.Xszy;
import com.hebut.sdsjxxt.service.CourseService;
import com.hebut.sdsjxxt.service.JsjxzyService;
import com.hebut.sdsjxxt.service.UserService;
import com.hebut.sdsjxxt.service.XszyService;

@Service
public class HomeworkTask implements IHomeworkTask {

	Logger log = Logger.getLogger(HomeworkTask.class);
	@Autowired
	private JsjxzyService jsjxzyService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private UserService userService;

	@Autowired
	private XszyService xszyService;

	/*
	 * 
	 * 查询数据库表 jsjxzy，如果字段status=1 并且start_time小于当前日期，则批量生成学生作业 status=1 and
	 * start_time <=now() 并且将jxzy的status设置为2 即对学生开放，已开始作答
	 * 
	 */
	@Scheduled(cron = "0 0/1 * * * ? ") // 每1分钟执行一次
	@Override
	public void insertXszy() {
		log.info(log.getClass().getName() + ":" + "开始执行定时任务insertXszy------------------------------------------------");
		List<Jsjxzy> jsjxzyList = jsjxzyService.selectList(
				new EntityWrapper<Jsjxzy>().eq("status", Const.JSJXZY_STATUS_CREATE).le("start_time", new Date()));
		if (CollectionUtils.isNotEmpty(jsjxzyList)) {
			List<Xszy> xszyList = new ArrayList<>();
			for (Jsjxzy jsjxzy : jsjxzyList) {
				Course course = courseService.selectById(jsjxzy.getCourseId());
				String banjiIds = course.getBanjiIds();
				String[] idArr = banjiIds.split(",");
				for (String id : idArr) {
					List<User> studentList = userService
							.selectList(new EntityWrapper<User>().eq("banji_id", Long.valueOf(id)));
					if (CollectionUtils.isNotEmpty(studentList)) {
						for (User stu : studentList) {
							Xszy xszy = new Xszy();
							xszy.setUserId(stu.getXuehao());
							xszy.setTeacherId(course.getTeacherId());
							xszy.setJsjxzyId(jsjxzy.getId());
							xszy.setJxnrId(jsjxzy.getJxnrId());
							xszy.setCourseId(course.getId());
							xszy.setStage(Const.XSZY_STAGE_CREATE);
							xszy.setHomeworkDeadline(jsjxzy.getEndTime());
							xszy.setCreateTime(new Date());
							xszy.setUpdateTime(new Date());
							xszyList.add(xszy);
						}
					}
				}
			}
			// 批量生成学生作业
			if(CollectionUtils.isNotEmpty(xszyList)) {
				xszyService.insertBatch(xszyList);
				// 更新教学作业状态status
				jsjxzyList.forEach(jxzy -> {
					jxzy.setStatus(Const.JSJXZY_STATUS_PUBLISH);
					jxzy.setUpdateTime(new Date());
				});
				jsjxzyService.updateJxzyStatusBatchById(jsjxzyList);
				log.info(log.getClass().getName() + ":" + "定时任务 insertXszy执行完成，共检测到" + jsjxzyList.size()
				+ "个教学作业需要发布，本次插入学生作业" + xszyList.size() + "条记录"
				+ "------------------------------------------------");
			}else {
				log.info(log.getClass().getName() + ":"
						+ "定时任务insertXszy执行完成，没有检测到教学作业需要发布！------------------------------------------------");
			}
		} else {
			log.info(log.getClass().getName() + ":"
					+ "定时任务insertXszy执行完成，没有检测到教学作业需要发布！------------------------------------------------");
		}

	}

	/*
	 * 查询数据库表 jsjxzy，如果作业截止时间过了，则变更状态为3 提交截止 end_time<=now then status=3
	 * 并且把jsjxzy状态变更为3 即已结束
	 */
	@Scheduled(cron = "0 0/1 *  * * ? ") // 每1分钟执行一次
	@Override
	public void finishXszy() {
		log.info(
				log.getClass().getName() + ":" + "开始执行定时任务 finishXszy------------------------------------------------");

		Integer[] stages = { Const.XSZY_STAGE_CREATE, Const.XSZY_STAGE_SUBMIT };
		List<Jsjxzy> jxzyList = jsjxzyService.selectList(
				new EntityWrapper<Jsjxzy>().eq("status", Const.JSJXZY_STATUS_PUBLISH).le("end_time", new Date()));
		if (CollectionUtils.isNotEmpty(jxzyList)) {
			List<Xszy> xszyList = new ArrayList<>();
			List<Long> idList = jxzyList.stream().map(Jsjxzy::getId).collect(Collectors.toList());
			xszyList = xszyService.selectList(new EntityWrapper<Xszy>().in("jsjxzy_id", idList).in("stage", stages));
			xszyList.forEach(xszy -> {
				xszy.setStage(Const.XSZY_STAGE_DEADLINE);
				xszy.setUpdateTime(new Date());
			});
			// 更新学生作业阶段为3 截止作业时间
			xszyService.updateXszyStageBatchById(xszyList);
			// 更新教学作业状态为3 已结束
			jxzyList.forEach(jxzy -> {
				jxzy.setStatus(Const.JSJXZY_STATUS_EXPIRE);
				jxzy.setUpdateTime(new Date());
			});
			jsjxzyService.updateJxzyStatusBatchById(jxzyList);
			log.info(log.getClass().getName() + ":" + "定时任务 finishXszy执行完成，共检测到" + jxzyList.size()
					+ "个教学作业需要j结束提交事件，本次截止学生作业提交共" + xszyList.size() + "条记录"
					+ "------------------------------------------------");

		} else {
			log.info(log.getClass().getName() + ":"
					+ "定时任务 finishXszy执行完成，没有检测到教学作业需要截止提交时间！------------------------------------------------");
		}
	}

}
