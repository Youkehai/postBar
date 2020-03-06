package com.example.demo.myPostBar.postBar.mapper;

import com.example.demo.myPostBar.postBar.entity.TPostBar;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 * 帖子表 Mapper 接口
 * </p>
 *
 * @author youkehai
 * @since 2020-01-15
 */
public interface TPostBarMapper extends BaseMapper<TPostBar> {
	
	Page<TPostBar> selectTypeNamePage(Page<TPostBar> page,@Param(Constants.WRAPPER) QueryWrapper<TPostBar> queryWrapper);
	
	TPostBar selectTypeNamePage(@Param(Constants.WRAPPER) QueryWrapper<TPostBar> queryWrapper);
}
