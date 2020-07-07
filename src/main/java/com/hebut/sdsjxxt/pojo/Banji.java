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
 * @date 2019年5月15日 下午3:35:00
 * @description JsonInclude注解不返回null值
 */
@Data
@NoArgsConstructor // 必须有这个注解，否则springmvc后台controller绑定pojo对象无法实现
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Banji implements Serializable {
	private static final long serialVersionUID = 7903483937950045928L;
	@TableId(type = IdType.AUTO)
	private Long id; // id自增

	private String name;// 班级名称
	
	private Integer totalCount;//班级总人数

	private String description;// 描述

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

}
