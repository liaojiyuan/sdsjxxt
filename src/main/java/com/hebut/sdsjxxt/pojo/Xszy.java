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
 * @date 2019年5月15日 下午3:52:23
 * @description 学生作业内容大表
 */
@Data
@NoArgsConstructor // 必须有这个注解，否则springmvc后台controller绑定pojo对象无法实现
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Xszy implements Serializable {

	private static final long serialVersionUID = 205307150533085897L;
	@TableId(type = IdType.AUTO)
	private Long id;// id自增

	private Long jsjxzyId;// 教师教学作业表id
	
	private Long jxnrId;// 教学内容id

	private String userId;// 学生用户id，存放学号 哪个学生的作业

	private String teacherId;// 教师用户id，存放学号 ，提交作业给哪位老师批改

	private Long courseId;// 课程id

	private String answer;// 答案

	private Double grade;// 作业权值分数，学生掌握程度

	private Double stuScore; // 分数，学生作业得分情况

	private Integer stage;// 作业阶段 1：未提交 2 已提交，未截止   3已截止，未批改  4 已批改

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date homeworkDeadline;// 作业提交截止时间

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date submitTime;// 作业提交时间

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date submitLastTime;// 作业最后提交时间

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;// 创建时间

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;// 作业最后提交时间
}
