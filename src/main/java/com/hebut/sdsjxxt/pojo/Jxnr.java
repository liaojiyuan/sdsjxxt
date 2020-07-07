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
 * @date 2019年5月15日 下午3:43:39
 * @description 教学内容大表
 */
@Data
@NoArgsConstructor // 必须有这个注解，否则springmvc后台controller绑定pojo对象无法实现
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Jxnr implements Serializable {

	private static final long serialVersionUID = 5928456825467105372L;

	@TableId(type = IdType.AUTO)
	private Long id;// id自增

	private Long courseId; // 课程id

	private String courseName;// 课程名称

	private String courseAim;// 课程目标

	private String courseContent;// 课程内容

	private Double grade;// 分数，教学内容对应阶段分数=权重*课程平时分 插入时自动计算

	private Double weight;// 权重，教学内容对应阶段权重比值

	// 剩余权重，课前，课中，课后阶段权重相加为1，每次设置完一次作业，剩余权重减去该次作业权重，自动计算更新，在前端作为提示信息
	private Double leave;

	private String createUserId;// 教学内容创建者id，存放学号
	
	private Integer sfFinished;//教学内容是否结束
	
	private Integer sfTj;//教学内容是否已经统计，即生成一条xsjxnrcj记录

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;// 创建时间

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime; // 最后更新时间

	private Long crc64CourseAim;// 课程目标

	private Long crc64CourseContent;// 课程内容
}
