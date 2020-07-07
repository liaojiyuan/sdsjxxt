package com.hebut.sdsjxxt.dvo;

import java.io.Serializable;

/**
* @author gaochaochao
* @date 2019年6月4日 下午3:20:06
* @description
*/
public class JxnrTjVO implements Serializable{

	
	private static final long serialVersionUID = -8698967518208471365L;
	
	private String xuehao ;
	
	private String name;
	
	private String banjiName;
	
	private Long banjiId;
	
	private Integer stage;//教学作业阶段  课前，中，后
	
	private Double jxnrGrade; //教学内容平时分
	
	private double totalScore; //教学作业总分
	
	private double stuScore; //学生作业得分
	
	private double jxzyGrade; //教学作业平时分
	
	private double xszyGrade; //学生作业平时分
	
	private Long jxnrId;
	
	private Long xszyId;
	
	private Long JsjxzyId;

	public String getXuehao() {
		return xuehao;
	}

	public void setXuehao(String xuehao) {
		this.xuehao = xuehao;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBanjiName() {
		return banjiName;
	}

	public void setBanjiName(String banjiName) {
		this.banjiName = banjiName;
	}

	public Long getBanjiId() {
		return banjiId;
	}

	public void setBanjiId(Long banjiId) {
		this.banjiId = banjiId;
	}

	public Double getJxnrGrade() {
		return jxnrGrade;
	}

	public void setJxnrGrade(Double jxnrGrade) {
		this.jxnrGrade = jxnrGrade;
	}

	public double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(double totalScore) {
		this.totalScore = totalScore;
	}

	public double getStuScore() {
		return stuScore;
	}

	public void setStuScore(double stuScore) {
		this.stuScore = stuScore;
	}

	public double getJxzyGrade() {
		return jxzyGrade;
	}

	public void setJxzyGrade(double jxzyGrade) {
		this.jxzyGrade = jxzyGrade;
	}

	public double getXszyGrade() {
		return xszyGrade;
	}

	public void setXszyGrade(double xszyGrade) {
		this.xszyGrade = xszyGrade;
	}

	public Long getXszyId() {
		return xszyId;
	}

	public void setXszyId(Long xszyId) {
		this.xszyId = xszyId;
	}

	public Integer getStage() {
		return stage;
	}

	public void setStage(Integer stage) {
		this.stage = stage;
	}

	public Long getJxnrId() {
		return jxnrId;
	}

	public void setJxnrId(Long jxnrId) {
		this.jxnrId = jxnrId;
	}

	public Long getJsjxzyId() {
		return JsjxzyId;
	}

	public void setJsjxzyId(Long jsjxzyId) {
		JsjxzyId = jsjxzyId;
	}
	
	

}
