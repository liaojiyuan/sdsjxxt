package com.hebut.sdsjxxt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hebut.sdsjxxt.pojo.Banji;

/**
 * @author gaochaochao
 * @date 2019年5月15日 下午4:16:17
 * @description
 */
@Repository("banjiMapper")
public interface BanjiMapper extends BaseMapper<Banji> {

	List<Banji> getBanjiListByTeacherId(@Param("xuehao")String xuehao);

}
