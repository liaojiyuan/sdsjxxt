package com.hebut.sdsjxxt.task;

public interface IHomeworkTask {

	/**
	 * 检查教师教学作业表
	 * 
	 * @description
	 * @param
	 * @return void
	 */
	void insertXszy();

	/**
	 * 查看学生作业截止时间，如果截止，改变作业状态
	 * 
	 * @description
	 * @param
	 * @return void
	 */
	void finishXszy();
}
