package com.hebut.sdsjxxt.dvo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hebut.sdsjxxt.pojo.Jxnr;

public class JxnrCourseVO extends Jxnr implements Serializable {

	private static final long serialVersionUID = -6637904036619082031L;

	private Integer grade2;// 课程平时分 分数

	private String teacherName; // 课程对应的授课教师

	private String banjiIds;// 课程对应的授课班级ids

	private Double courseLeave;// 课程对应的剩余权值

	private String banjiNames;// 课程对应的授课班级names

	private Integer startYear; // 开课年份
	
	private Integer jxzyCount;//教学内容关联的教学作业记录总数 ，3条之后，操作列不在显示 编辑，删除 功能

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate; // 开课日期

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDate; // 结课日期

	public Integer getStartYear() {
		return startYear;
	}

	public void setStartYear(Integer startYear) {
		this.startYear = startYear;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getGrade2() {
		return grade2;
	}

	public void setGrade2(Integer grade2) {
		this.grade2 = grade2;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getBanjiIds() {
		return banjiIds;
	}

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
	 * @return the courseLeave
	 */
	public Double getCourseLeave() {
		return courseLeave;
	}

	/**
	 * @param courseLeave
	 *            the courseLeave to set
	 */
	public void setCourseLeave(Double courseLeave) {
		this.courseLeave = courseLeave;
	}

	public Integer getJxzyCount() {
		return jxzyCount;
	}

	public void setJxzyCount(Integer jxzyCount) {
		this.jxzyCount = jxzyCount;
	}

}
