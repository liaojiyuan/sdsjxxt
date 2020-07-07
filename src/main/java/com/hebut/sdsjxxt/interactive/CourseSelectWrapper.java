package com.hebut.sdsjxxt.interactive;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 课程筛选条件
 */
public class CourseSelectWrapper implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;// id自增

	private String name;// 课程名称

	private String teacherId;// 授课教师id,存放学号

	private String teacherName;// 授课教师姓名

	private String banjiIds;// 授课班级id

	private Integer startYear;// 开课年份
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate1;// 开课日期起始
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate2;// 开课日期结束
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDate1;// 结课日期起始
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDate2;// 结课日期结束

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
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
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the teacherName
	 */
	public String getTeacherName() {
		return teacherName;
	}

	/**
	 * @param teacherName
	 *            the teacherName to set
	 */
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	/**
	 * @return the banjiId
	 */
	public String getBanjiIds() {
		return banjiIds;
	}

	/**
	 * @param banjiId
	 *            the banjiId to set
	 */
	public void setBanjiIds(String banjiId) {
		this.banjiIds = banjiId;
	}

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

}
