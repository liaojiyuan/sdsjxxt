package com.hebut.sdsjxxt.dvo;

import java.io.Serializable;

import com.hebut.sdsjxxt.pojo.Xszy;

/**
* @author gaochaochao
* @date 2019年6月5日 下午3:33:27
* @description 学生作业成绩查询vo
*/
public class XszyCjcxVO extends Xszy implements Serializable{

	private static final long serialVersionUID = -6005092829822114448L;
	
	private String courseName;
	
	private String courseContent;
	
	private Integer jxzyStage;
	
	private Double xszyGrade;
	
	private Double jxzyGrade;
	
	public Double getXszyGrade() {
		return xszyGrade;
	}

	public void setXszyGrade(Double xszyGrade) {
		this.xszyGrade = xszyGrade;
	}

	private Double totalScore;
	
	private String teacherName;

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseContent() {
		return courseContent;
	}

	public void setCourseContent(String courseContent) {
		this.courseContent = courseContent;
	}

	public Integer getJxzyStage() {
		return jxzyStage;
	}

	public void setJxzyStage(Integer jxzyStage) {
		this.jxzyStage = jxzyStage;
	}

	public Double getJxzyGrade() {
		return jxzyGrade;
	}

	public void setJxzyGrade(Double jxzyGrade) {
		this.jxzyGrade = jxzyGrade;
	}

	public Double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Double totalScore) {
		this.totalScore = totalScore;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	

}
