package com.hebut.sdsjxxt.dvo;

import java.io.Serializable;

import com.hebut.sdsjxxt.pojo.Xszy;

/**
* @author gaochaochao
* @date 2019年5月26日 下午5:09:20
* @description
*/
public class XszyJxzyVO extends Xszy implements Serializable{

	private static final long serialVersionUID = 4717201734004307642L;
	
	private String banjiName; //来自user的banji_name
	
	private String courseName; //来自jxnr的course_name
	
	private String courseContent; //来自jxnr的course_content
	
	private Integer jxzyStage; //来自jxzy的stage 1,2,3,4
	
	private String jxzyStageName; //教学作业阶段对应中文名 来自jsjxzy的stage 中文课前、中、后
	
	private String homework; //作业内容来自 jsjxzy.homework
	
	private Double totalScore;//作业总分值 来自 jsjxzy.total_score
	
	private String xszyStageName;//学生作业状态对应中文
	
	private Double jxzyWeight;//教学作业权重
	
	private String teacherName;

	public String getBanjiName() {
		return banjiName;
	}

	public void setBanjiName(String banjiName) {
		this.banjiName = banjiName;
	}

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

	public String getJxzyStageName() {
		return jxzyStageName;
	}

	public void setJxzyStageName(String jxzyStageName) {
		this.jxzyStageName = jxzyStageName;
	}

	public String getHomework() {
		return homework;
	}

	public void setHomework(String homework) {
		this.homework = homework;
	}

	public Double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Double totalScore) {
		this.totalScore = totalScore;
	}

	public String getXszyStageName() {
		return xszyStageName;
	}

	public void setXszyStageName(String xszyStageName) {
		this.xszyStageName = xszyStageName;
	}

	public Double getJxzyWeight() {
		return jxzyWeight;
	}

	public void setJxzyWeight(Double jxzyWeight) {
		this.jxzyWeight = jxzyWeight;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	
	
}
