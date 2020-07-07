package com.hebut.sdsjxxt.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @author gaochaochao
* @date 2019年6月5日 上午8:11:45
* @description 学生教学内容成绩
*/
@Data
@NoArgsConstructor // 必须有这个注解，否则springmvc后台controller绑定pojo对象无法实现
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Xskccj implements Serializable{

	private static final long serialVersionUID = 5601252199245410134L;
	
	@TableId(type = IdType.AUTO)
	private Long id;// id自增
	
	private Long courseId;// 课程id

	private String userId;// 学生用户id，存放学号 

	private Double grade;// 学生课程的所有教学内容加起来平时分得分
	
	private Integer total ;//该课程下发布的教学内容次数
	
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;// 创建时间

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;// 作业最后提交时间

}
