package com.hebut.sdsjxxt.interactive;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
* @author gaochaochao
* @date 2019年5月26日 下午5:01:30
* @description
*/
public class XszySelectWrapper implements Serializable{

	private static final long serialVersionUID = -5572712073500560582L;
	
	private Integer xszyStage;
	
	private Integer jxzyStage;
	
	private Long courseId;
	
	private String banjiIds;
	
	private String teacherId;
	
	private String xuehao;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime1;// 作业开始时间起始
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime2;// 作业开始时间结束
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime1;// 作业截止时间起始
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime2;// 作业截止时间结束
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date submitTime1;// 学生提交作业开始时间起始
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date submitTime2;// 学生提交作业开始时间结束
	public Integer getXszyStage() {
		return xszyStage;
	}
	public void setXszyStage(Integer xszyStage) {
		this.xszyStage = xszyStage;
	}
	public Integer getJxzyStage() {
		return jxzyStage;
	}
	public void setJxzyStage(Integer jxzyStage) {
		this.jxzyStage = jxzyStage;
	}
	public Long getCourseId() {
		return courseId;
	}
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	public String getBanjiIds() {
		return banjiIds;
	}
	public void setBanjiIds(String banjiIds) {
		this.banjiIds = banjiIds;
	}
	public String getXuehao() {
		return xuehao;
	}
	public void setXuehao(String xuehao) {
		this.xuehao = xuehao;
	}
	public Date getStartTime1() {
		return startTime1;
	}
	public void setStartTime1(Date startTime1) {
		this.startTime1 = startTime1;
	}
	public Date getStartTime2() {
		return startTime2;
	}
	public void setStartTime2(Date startTime2) {
		this.startTime2 = startTime2;
	}
	public Date getEndTime1() {
		return endTime1;
	}
	public void setEndTime1(Date endTime1) {
		this.endTime1 = endTime1;
	}
	public Date getEndTime2() {
		return endTime2;
	}
	public void setEndTime2(Date endTime2) {
		this.endTime2 = endTime2;
	}
	public Date getSubmitTime1() {
		return submitTime1;
	}
	public void setSubmitTime1(Date submitTime1) {
		this.submitTime1 = submitTime1;
	}
	public Date getSubmitTime2() {
		return submitTime2;
	}
	public void setSubmitTime2(Date submitTime2) {
		this.submitTime2 = submitTime2;
	}
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	
	
	

	
	
}
