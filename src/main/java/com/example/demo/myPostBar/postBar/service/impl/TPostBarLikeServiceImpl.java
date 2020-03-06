package com.example.demo.myPostBar.postBar.service.impl;

import com.example.demo.config.common.BaseContant;
import com.example.demo.myPostBar.postBar.entity.TPostBar;
import com.example.demo.myPostBar.postBar.entity.TPostBarLike;
import com.example.demo.myPostBar.postBar.mapper.TPostBarLikeMapper;
import com.example.demo.myPostBar.postBar.mapper.TPostBarMapper;
import com.example.demo.myPostBar.postBar.service.ITPostBarLikeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 帖子——点赞表 服务实现类
 * </p>
 *
 * @author youkehai
 * @since 2020-01-15
 */
@Service
public class TPostBarLikeServiceImpl extends ServiceImpl<TPostBarLikeMapper, TPostBarLike> implements ITPostBarLikeService {

	@Autowired
	private TPostBarMapper tPostBarMapper;
	/***
	 * 点赞
	 * @param postLike
	 * @param postBar
	 * @return
	 */
	@Transactional
	public int insertAndUpdate(TPostBarLike postLike, TPostBar postBar) {
		save(postLike);
		TPostBar entity=new TPostBar();
		entity.setId(postBar.getId());
		entity.setLikeNum(postBar.getLikeNum().add(BaseContant.UPDATE_ADD_NUM));
		return tPostBarMapper.updateById(entity);
	}
	/**
	 * 取消点赞
	 * @param query
	 * @param postBar 
	 */
	@Transactional
	public void deleteAndUpdate(QueryWrapper<TPostBarLike> query, TPostBar postBar) {
		remove(query);
		TPostBar entity=new TPostBar();
		entity.setId(postBar.getId());
		entity.setLikeNum(postBar.getLikeNum().subtract(BaseContant.UPDATE_ADD_NUM));
		tPostBarMapper.updateById(entity);
	}

}
