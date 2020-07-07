#创建数据库
DROP DATABASE IF EXISTS `sdsjxxt`;
CREATE DATABASE `sdsjxxt` CHARACTER SET UTF8;

#切换数据库
USE sdsjxxt;

#创建用户表 学号唯一且不为空
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`(
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID自增',
  `xuehao` VARCHAR(50)  NOT NULL COMMENT '学号', 
  `password` VARCHAR(50) NOT NULL COMMENT '用户密码，存放加密后的MD5',
  `name` VARCHAR(50) NOT NULL COMMENT '真实姓名',
  `banji_id` BIGINT default NULL  comment '班级id',
  `banji_name` varchar(50) null  comment '班级名称',
  `role`  INT(4) not null COMMENT '用户角色,1:学生，2：教师',
  `create_time` DATETIME NULL COMMENT '创建时间',
  `update_time` DATETIME NULL COMMENT '最后一次更新时间'
)ENGINE =InnoDB  DEFAULT CHARSET = UTF8;

#给xuehao字段添加unique索引，否则其他表不能将学号作为关联外键
create unique index user_xuehao on user(xuehao);
#添加banji_id外键索引
alter table user add constraint fk_user_banji_id foreign key(banji_id) references banji(id);


#创建班级表
DROP TABLE IF EXISTS `banji`;
CREATE TABLE `banji`(
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'ID自增',
  `name` VARCHAR(50) NOT NULL COMMENT '班级名称', 
  `description` text null  comment '班级描述',
  `create_time` DATETIME NULL COMMENT '创建时间',
  `update_time` DATETIME NULL COMMENT '最后一次更新时间'
)ENGINE =InnoDB DEFAULT CHARSET = UTF8;

insert into banji(name,description) values('软件工程151班',''),('软件工程152班',''),('软件工程153班','');

#创建课程表
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`(
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'ID自增',
  `name` VARCHAR(50) NOT NULL COMMENT '课程名称', 
  `teacher_id`  VARCHAR(50) not null COMMENT '授课教师id 学号',
  `teacher_name` VARCHAR(50) not null COMMENT '授课教师姓名',
  `banji_ids` VARCHAR(50) not null  comment '授课班级id，多个，用;分割开',
  `grade` int(4) default 30  comment '该课程平时分成绩，整数',
  `leave`  decimal(5,2) default 1.00 comment'剩余权重，每次添加教学内容权重时都相应减去',
  `start_year` int default NULL COMMENT '开课年份',
  `start_date` date default NULL COMMENT '开课日期',
  `end_date` date default NULL COMMENT '结课日期',
  `description` text default NULL COMMENT '描述',
  `create_time` DATETIME NULL COMMENT '创建时间',
  `update_time` DATETIME NULL COMMENT '最后一次更新时间',
  `crc64_name` bigint NOT NULL COMMENT '课程名称crc64码' 
)ENGINE =InnoDB  DEFAULT CHARSET = UTF8;
#给课程表添加teacher_id外键索引，指向user的xuehao
alter table course add constraint fk_course_teacher_id foreign key(teacher_id) references user(xuehao);


#创建教学内容大表
DROP TABLE IF EXISTS `jxnr`;
CREATE TABLE `jxnr`(
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'ID自增',
  `course_id` bigint NOT NULL COMMENT '课程id', 
  `course_name` varchar(50) not null COMMENT '课程名称', 
  `course_aim` text NOT NULL COMMENT '课程目标',
  `course_content` text NOT NULL COMMENT '课程内容',
  `grade`  decimal(5,2)  null comment'权重分数，教学内容对应阶段分数=权重*课程平时分 ',
  `weight`  decimal(5,2) not null comment'权重，教学内容对应阶段权重比值',
  `leave`  decimal(5,2) default 1.00 comment'剩余权重，每次添加教学内容权重时都相应减去',
  `create_user_id` varchar(50)  null COMMENT '教学内容创建者id，学号' ,
  `create_time` DATETIME NULL COMMENT '创建时间',
  `update_time` DATETIME NULL COMMENT '最后一次更新时间',
  `crc64_course_aim` bigint  NULL COMMENT '课程目标的crc64码',
  `crc64_course_content` bigint NOT NULL COMMENT '课程内容的crc64码' 
)ENGINE =InnoDB  DEFAULT CHARSET = UTF8;

#添加 course_id外键约束
alter table jxnr add constraint fk_jxnr_course_id foreign key(course_id) references course(id);
#添加 create_user_id外键约束，指向user的xuehao
alter table jxnr add constraint fk_jxnr_create_user_id foreign key(create_user_id) references user(xuehao);
 
#创建教师教学作业表
DROP TABLE IF EXISTS `jsjxzy`;
CREATE TABLE `jsjxzy`(
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'ID自增',
  `jxnr_id` bigint NOT NULL COMMENT '教学内容id', 
  `course_id` bigint NOT NULL COMMENT '课程id 方便根据课程名称展示教学',  
  `create_user_id` varchar(50)  null COMMENT '作业创建者id，学号' ,
  `stage` int(4) not null  comment '阶段，1：课前，2：课中，3：课后',
  `homework`  text not null COMMENT '作业内容',
  `grade`  decimal(5,2) null comment'权重总分数，设置作业总分数=教学内容分数*权重，自动计算保存',
  `weight`  decimal(5,2) not null comment'权重，设置该次作业对应所占教学内容权重比值 ，课前，中后加起来等于1',
  'total_score'  decimal(5,2) null comment '作业总分数值',
  `start_time` DATETIME  not NULL COMMENT '作业开始日期',
  `end_time` DATETIME not NULL COMMENT '作业结束日期',
  `status` int(4) not NULL COMMENT '教师教学作业状态  1 刚创建，未发布  2 已创建关联学生作业  3 已过期',
  `create_time` DATETIME not NULL COMMENT '创建时间',
  `update_time` DATETIME NULL COMMENT '最后一次更新时间' 
)ENGINE =InnoDB  DEFAULT CHARSET = UTF8;

#添加一个字段，设置教师作业的总分数多少  
#alter table jsjxzy add column 'total_score'  decimal(5,2) null comment '作业总分数值';
# 添加jxnr_id 外键
alter table jsjxzy add constraint fk_jsjxzy_jxnr_id foreign key(jxnr_id) references jxnr(id);
#添加course_id 外键
alter table jsjxzy add constraint fk_jsjxzy_course_id foreign key(course_id) references course(id);
#添加user的学号外键
alter table jsjxzy add constraint fk_jsjxzy_create_user_id foreign key(create_user_id) references user(xuehao);
 

#创建学生作业大表
DROP TABLE IF EXISTS `xszy`;
CREATE TABLE `xszy`(
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'ID自增',
  `jsjxzy_id`  BIGINT NOT NULL COMMENT '教师教学作业id', 
  `user_id` varchar(50) NOT NULL COMMENT '用户id，存放学号，表明哪个学生的作业',
  `teacher_id` varchar(50) NOT NULL COMMENT '用户id，存放学号，给哪位老师批改',
  `course_id` BIGINT NOT NULL COMMENT '课程id',
  `answer` text default NULL COMMENT '作业答案',
  `grade`  decimal(5,2) null comment'分数，学生权重得分',
  'stu_score'  decimal(5,2) null comment '学生作业得分分数值',
  `stage`  int(4) null comment'学生作业阶段 1：刚创建，2：已提交未批改，3：已截止   4：已批改',
  `homework_deadline` DATETIME   NULL COMMENT '学生作业截止时间，教师教学作业的结束时间', 
  `submit_time` DATETIME   NULL COMMENT '作业提交日期',
  `submit_last_time` DATETIME   NULL COMMENT '作业最后提交时间',
  `create_time` DATETIME NULL COMMENT '创建时间',
  `update_time` DATETIME NULL COMMENT '最后一次更新时间'
)ENGINE =InnoDB   DEFAULT CHARSET = UTF8;

#添加一个字段，设置学生作业的得分
#alter table xszy add column 'stu_score'  decimal(5,2) null comment '学生作业得分分数值';
#添加jsjxzy_id外键
alter table xszy add constraint fk_xszy_jsjxzy_id foreign key(jsjxzy_id) references jsjxzy(id);
#添加course_id外键
alter table xszy add constraint fk_xszy_course_id foreign key(course_id) references course(id);
#添加user_id外键xuehao ,标识哪个学生的作业
alter table xszy add constraint fk_xszy_user_id foreign key(user_id) references user(xuehao);
#添加teacher_id外键xuehao ,标识哪位老师来批改作业
alter table xszy add constraint fk_xszy_teacher_id foreign key(teacher_id) references user(xuehao);  