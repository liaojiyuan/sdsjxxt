package com.hebut.sdsjxxt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hebut.sdsjxxt.pojo.User;

/**
 * @author gaochaochao
 * @date 2019年5月15日 下午4:14:54
 * @description 用户表信息
 */
@Repository("userMapper")
public interface UserMapper extends BaseMapper<User> {

	List<User> getTeacherListByBanjiId(@Param("id")Long banjiId);
}
