package com.hebut.sdsjxxt.interactive;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class JsjxzyJxnrCourseVOSelectWrapper implements Serializable {

	private static final long serialVersionUID = -6618954776324258237L;

	private Integer startYear; // 开课年份
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime1;// 作业开始时间起始
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime2;// 作业开始时间结束
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime1;// 作业截止时间起始
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime2;// 作业截止时间结束

	private Integer status; // 教学作业状态 1 刚创建，未发布 2 已发布，未结束 3 已结束
	
	private Integer stage;// 教学作业阶段
	private Long courseId;

	private String teacherId; // 教师学号

	private String banjiIds; // 单选只能选一个 班级的主键Long类型，但是转换成String类型
								// 在课程表的banji_ids字段里，like即可以模糊匹配到

	/**
	 * @return the startYear
	 */
	public Integer getStartYear() {
		return startYear;
	}

	/**
	 * @param startYear
	 *            the startYear to set
	 */
	public void setStartYear(Integer startYear) {
		this.startYear = startYear;
	}

	/**
	 * @return the startTime1
	 */
	public Date getStartTime1() {
		return startTime1;
	}

	/**
	 * @param startTime1
	 *            the startTime1 to set
	 */
	public void setStartTime1(Date startTime1) {
		this.startTime1 = startTime1;
	}

	/**
	 * @return the startTime2
	 */
	public Date getStartTime2() {
		return startTime2;
	}

	/**
	 * @param startTime2
	 *            the startTime2 to set
	 */
	public void setStartTime2(Date startTime2) {
		this.startTime2 = startTime2;
	}

	/**
	 * @return the endTime1
	 */
	public Date getEndTime1() {
		return endTime1;
	}

	/**
	 * @param endTime1
	 *            the endTime1 to set
	 */
	public void setEndTime1(Date endTime1) {
		this.endTime1 = endTime1;
	}

	/**
	 * @return the endTime2
	 */
	public Date getEndTime2() {
		return endTime2;
	}

	/**
	 * @param endTime2
	 *            the endTime2 to set
	 */
	public void setEndTime2(Date endTime2) {
		this.endTime2 = endTime2;
	}

	/**
	 * @return the courseId
	 */
	public Long getCourseId() {
		return courseId;
	}

	/**
	 * @param courseId
	 *            the courseId to set
	 */
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	/**
	 * @return the teacherId
	 */
	public String getTeacherId() {
		return teacherId;
	}

	/**
	 * @param teacherId
	 *            the teacherId to set
	 */
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	/**
	 * @return the banjiIds
	 */
	public String getBanjiIds() {
		return banjiIds;
	}

	/**
	 * @param banjiIds
	 *            the banjiIds to set
	 */
	public void setBanjiIds(String banjiIds) {
		this.banjiIds = banjiIds;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStage() {
		return stage;
	}

	public void setStage(Integer stage) {
		this.stage = stage;
	}
}
