package com.hebut.sdsjxxt.common;

import java.util.HashMap;
import java.util.Map;

public class Const {

	public static final String CURRENT_USER = "current_user";

	public static final String PHONE = "phone";

	public static final String USERNAME = "username";

	public static final String SUCCESS = "success";

	public static final String TOKEN_PREFIX = "Token_";

	public static final String CURRENT_CORRECT = "current_correct";

	public static final int ROLETYPE_STUDENT = 1;
	public static final int ROLETYPE_TEACHER = 2;
	public static final int ROLETYPE_MANAGER = 3;

	public static final int STAGE_JXJD_KQ = 1;// 教学阶段-课前
	public static final int STAGE_JXJD_KZ = 2;// 教学阶段-课中
	public static final int STAGE_JXJD_KH = 3;// 教学阶段-课后

	public static final int JSJXZY_STATUS_CREATE = 1;// 教师教学作业阶段，刚创建，未发布
	public static final int JSJXZY_STATUS_PUBLISH = 2;// 教师教学作业阶段，发布中
	public static final int JSJXZY_STATUS_EXPIRE = 3;// 教师教学作业阶段，已过期

	public static final int XSZY_STAGE_CREATE = 1;// 学生作业刚创建，未提交
	public static final int XSZY_STAGE_SUBMIT = 2;// 学生作业以提交，未到教师作业截止时间
	public static final int XSZY_STAGE_DEADLINE = 3;// 学生作业已截止，不管是否提交，到时间就自动将状态改为deadline
	public static final int XSZY_STAGE_YPG = 4;// 教师批改作业，并给出了分数

	public static final int SF_S=1;
	public static final int SF_F=0;
	
	public static final Map<Integer, String> stageJxjd = new HashMap<Integer, String>() {
		private static final long serialVersionUID = 1L;

		{
			put(1, "课前");
			put(2, "课中");
			put(3, "课后");
		}
	};

	public interface OrderBy {
		String PRICE_ASC = "price_asc";
		String PRICE_DESC = "price_desc";
	}

	public interface Role {
		String Role_STUDENT = "1";// 学生
		String ROLE_TEACHER = "2";// 教师
	}
}
