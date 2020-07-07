package com.hebut.sdsjxxt.dvo;

import java.io.Serializable;

/**
* @author gaochaochao
* @date 2019年6月4日 下午3:20:06
* @description
*/
public class JxzyTjVO implements Serializable{

	
	private static final long serialVersionUID = -8698967518208471365L;
	
	private String xuehao ;
	
	private String name;
	
	private String banjiName;
	
	private Long banjiId;
	
	private double totalScore;
	
	private double stuScore;
	
	private double jxzyGrade;
	
	private double xszyGrade;
	
	private Long jxzyId;
	
	private Long xszyId;

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

	public Long getJxzyId() {
		return jxzyId;
	}

	public void setJxzyId(Long jxzyId) {
		this.jxzyId = jxzyId;
	}

	public Long getXszyId() {
		return xszyId;
	}

	public void setXszyId(Long xszyId) {
		this.xszyId = xszyId;
	}
	
	

}
