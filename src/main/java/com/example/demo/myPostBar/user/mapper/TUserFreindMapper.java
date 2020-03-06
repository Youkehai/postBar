package com.example.demo.myPostBar.user.mapper;

import com.example.demo.myPostBar.postBar.entity.TPostBar;
import com.example.demo.myPostBar.user.entity.TUserFreind;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;

/**
 * <p>
 * 用户-好友表 Mapper 接口
 * </p>
 *
 * @author youkehai
 * @since 2020-01-15
 */
public interface TUserFreindMapper extends BaseMapper<TUserFreind> {
	List<TUserFreind> selectFriendInfo(@Param(Constants.WRAPPER) QueryWrapper<TUserFreind> queryWrapper);
}
