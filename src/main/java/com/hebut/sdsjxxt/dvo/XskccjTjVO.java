package com.hebut.sdsjxxt.dvo;

import java.io.Serializable;

import com.hebut.sdsjxxt.pojo.Xskccj;

/**
* @author gaochaochao
* @date 2019年6月5日 下午2:07:56
* @description
*/
public class XskccjTjVO extends Xskccj implements Serializable{

	private static final long serialVersionUID = 9223052004001914061L;
	
	private String name; //学生姓名
	
	private String banjiName; //学生所在班级名称
	
	private Long banjiId; //班级id
	
	private Double courseGrade; //课程内容平时分

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

	public Double getCourseGrade() {
		return courseGrade;
	}

	public void setCourseGrade(Double courseGrade) {
		this.courseGrade = courseGrade;
	}
	
	
	
}
