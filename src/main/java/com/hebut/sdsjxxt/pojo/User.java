package com.hebut.sdsjxxt.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaochaochao
 * @date 2019年5月15日 下午3:19:53
 * @description
 */
@Data
@NoArgsConstructor // 必须有这个注解，否则springmvc后台controller绑定pojo对象无法实现
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable {

	private static final long serialVersionUID = 4531637950486736780L;

	@TableId(type = IdType.AUTO)
	private Long id; // 用户自增id

	private String xuehao; // 学号

	private String password; // 密码

	private String name; // 真实姓名

	@TableField("banji_id")
	private Long banjiId; // 班级id

	@TableField("banji_name")
	private String banjiName; // 班级名称

	private Integer role; // 角色 1 学生 2 教师

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime; // 创建时间

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime; // 最后更新时间

}
