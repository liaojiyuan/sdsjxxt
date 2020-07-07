package com.hebut.sdsjxxt.dvo;

import java.io.Serializable;

import com.hebut.sdsjxxt.pojo.Jsjxzy;

public class JsjxzyJxnrCourseVO extends Jsjxzy implements Serializable {

	private static final long serialVersionUID = 8430356444168990588L;

	private String courseName;

	private Double jxnrLeave;

	private Double jxnrGrade;

	private String banjiIds;

	private String banjiNames;

	private String courseContent;

	private String stageName; // 作业阶段名称 课前，课中，课后

	private String statusName;// 作业状态 1 刚创建,未发布 2 已发布，未结束 3 已结束

	/**
	 * @return the courseName
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * @param courseName
	 *            the courseName to set
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * @return the jxnrLeave
	 */
	public Double getJxnrLeave() {
		return jxnrLeave;
	}

	/**
	 * @param jxnrLeave
	 *            the jxnrLeave to set
	 */
	public void setJxnrLeave(Double jxnrLeave) {
		this.jxnrLeave = jxnrLeave;
	}

	/**
	 * @return the courseContent
	 */
	public String getCourseContent() {
		return courseContent;
	}

	/**
	 * @param courseContent
	 *            the courseContent to set
	 */
	public void setCourseContent(String courseContent) {
		this.courseContent = courseContent;
	}

	/**
	 * @return the jxnrGrade
	 */
	public Double getJxnrGrade() {
		return jxnrGrade;
	}

	/**
	 * @param jxnrGrade
	 *            the jxnrGrade to set
	 */
	public void setJxnrGrade(Double jxnrGrade) {
		this.jxnrGrade = jxnrGrade;
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
	 * @return the banjiNames
	 */
	public String getBanjiNames() {
		return banjiNames;
	}

	/**
	 * @param banjiNames
	 *            the banjiNames to set
	 */
	public void setBanjiNames(String banjiNames) {
		this.banjiNames = banjiNames;
	}

	/**
	 * @return the stageName
	 */
	public String getStageName() {
		return stageName;
	}

	/**
	 * @param stageName
	 *            the stageName to set
	 */
	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	/**
	 * @return the statusName
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * @param statusName
	 *            the statusName to set
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}
