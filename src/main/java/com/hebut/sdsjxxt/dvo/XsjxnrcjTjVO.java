package com.hebut.sdsjxxt.dvo;

import java.io.Serializable;

import com.hebut.sdsjxxt.pojo.Xsjxnrcj;

/**
* @author gaochaochao
* @date 2019年6月4日 下午3:20:06
* @description
*/
public class XsjxnrcjTjVO extends Xsjxnrcj implements Serializable{

	
	private static final long serialVersionUID = -8698967518208471365L;
	
	private String name; //学生姓名
	
	private String banjiName; //学生所在班级名称
	
	private Long banjiId; //班级id
	
	private Double jxnrGrade; //教学内容平时分

	private String courseContent; //教学内容
	
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

	public String getCourseContent() {
		return courseContent;
	}

	public void setCourseContent(String courseContent) {
		this.courseContent = courseContent;
	}

	

}
