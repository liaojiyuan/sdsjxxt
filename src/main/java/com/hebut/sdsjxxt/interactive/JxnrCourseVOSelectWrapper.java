package com.hebut.sdsjxxt.interactive;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class JxnrCourseVOSelectWrapper implements Serializable {

	private static final long serialVersionUID = -6618954776324258237L;

	private Integer startYear; // 开课年份
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate1;// 开课日期起始
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate2;// 开课日期结束
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDate1;// 结课日期起始
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDate2;// 结课日期结束

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
	 * @return the startDate1
	 */
	public Date getStartDate1() {
		return startDate1;
	}

	/**
	 * @param startDate1
	 *            the startDate1 to set
	 */
	public void setStartDate1(Date startDate1) {
		this.startDate1 = startDate1;
	}

	/**
	 * @return the startDate2
	 */
	public Date getStartDate2() {
		return startDate2;
	}

	/**
	 * @param startDate2
	 *            the startDate2 to set
	 */
	public void setStartDate2(Date startDate2) {
		this.startDate2 = startDate2;
	}

	/**
	 * @return the endDate1
	 */
	public Date getEndDate1() {
		return endDate1;
	}

	/**
	 * @param endDate1
	 *            the endDate1 to set
	 */
	public void setEndDate1(Date endDate1) {
		this.endDate1 = endDate1;
	}

	/**
	 * @return the endDate2
	 */
	public Date getEndDate2() {
		return endDate2;
	}

	/**
	 * @param endDate2
	 *            the endDate2 to set
	 */
	public void setEndDate2(Date endDate2) {
		this.endDate2 = endDate2;
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

}
