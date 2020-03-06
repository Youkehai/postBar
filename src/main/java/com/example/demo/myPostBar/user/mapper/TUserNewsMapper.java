package com.example.demo.myPostBar.user.mapper;

import com.example.demo.myPostBar.user.entity.TUserFreind;
import com.example.demo.myPostBar.user.entity.TUserNews;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;

/**
 * <p>
 * 用户-消息表 Mapper 接口
 * </p>
 *
 * @author youkehai
 * @since 2020-01-15
 */
public interface TUserNewsMapper extends BaseMapper<TUserNews> {
	/**
	 * 查询聊天记录和头像名称
	 * @param queryWrapper
	 * @return
	 */
	List<TUserNews> selectNewsUserInfo(@Param(Constants.WRAPPER) QueryWrapper<TUserNews> queryWrapper);
}
