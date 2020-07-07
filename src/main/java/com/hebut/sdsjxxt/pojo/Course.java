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
 * @date 2019年5月15日 下午3:37:23
 * @description
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Course implements Serializable {

	private static final long serialVersionUID = -8242508925964630246L;

	@TableId(type = IdType.AUTO)
	private Long id;// id自增

	private String name;// 课程名称

	private String teacherId;// 授课教师id,存放学号

	private String teacherName;// 授课教师姓名

	private String banjiIds;// 授课班级列表id多个用;分割开

	private Integer grade;// 课程平时分成绩，整数，默认30

	private Double leave;// 剩余权重，每次添加教学内容权重时都相应减去,初始默认为1

	private Integer sfTj;// 是否统计
	private Integer startYear;// 开课年份

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date startDate;// 开课日期

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date endDate;// 结课日期

	private String description;// 描述
	
	private Integer totalCount;//课程参与总人数
	
	private Integer jxnrCount;//该课程下发布的教学内容总次数统计

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;// 创建时间

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;// 最后更新时间

	private Long crc64Name;// 课程名称的crc64码

}
