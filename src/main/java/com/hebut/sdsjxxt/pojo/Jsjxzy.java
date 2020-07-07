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
public class Jsjxzy implements Serializable {

	private static final long serialVersionUID = 1514610754704725660L;

	@TableId(type = IdType.AUTO)
	private Long id;// id自增

	private Long jxnrId; // 教学内容id

	private Long courseId; // 课程id

	private String createUserId;// 教学内容创建者id，存放学号

	private Integer stage;// 作业阶段，1：课前，2：课中，3：课后

	private String homework;// 作业内容

	private Double totalScore;// 作业总分值多少

	private Double grade;// 总分数，设置作业总分数=教学内容分数*权重，自动计算保存

	private Double weight;// 权重，设置该次作业对应所占教学内容权重比值 ，课前，中后加起来等于1
	
	private Integer sfFinished;//是否完成教学作业，即教师把学生作业都批改完了

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;// 作业开始时间

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime; // 作业结束时间

	private Integer status; // 教师教学作业状态 1 刚创建，未发布 2 已创建关联学生作业 3 已过期

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;// 创建时间

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime; // 最后更新时间

}
