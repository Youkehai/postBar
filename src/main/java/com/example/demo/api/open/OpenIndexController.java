package com.example.demo.api.open;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.config.common.BaseController;
import com.example.demo.config.common.WebResult;
import com.example.demo.myPostBar.postBar.entity.TPostBar;
import com.example.demo.myPostBar.postBar.entity.TPostBarComment;
import com.example.demo.myPostBar.postBar.entity.TPostBarCommentReply;
import com.example.demo.myPostBar.postBar.entity.TPostBarLike;
import com.example.demo.myPostBar.postBar.entity.TPostBarType;
import com.example.demo.myPostBar.postBar.mapper.TPostBarCommentMapper;
import com.example.demo.myPostBar.postBar.mapper.TPostBarCommentReplyMapper;
import com.example.demo.myPostBar.postBar.mapper.TPostBarLikeMapper;
import com.example.demo.myPostBar.postBar.mapper.TPostBarMapper;
import com.example.demo.myPostBar.postBar.mapper.TPostBarTypeMapper;
import com.example.demo.myPostBar.postBar.service.impl.TPostBarServiceImpl;
import com.example.demo.myPostBar.user.entity.TScoreGrade;
import com.example.demo.myPostBar.user.mapper.TScoreGradeMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value=BaseController.OPEN_URL+"/index")
@Api(description = "首页接口 ",tags="首页接口  ")
public class OpenIndexController extends BaseController{

	@Autowired
	private TPostBarMapper tPostBarMapper;
	@Autowired
	private TPostBarLikeMapper tPostBarLikeMapper;
	@Autowired
	private TPostBarCommentMapper tPostBarCommentMapper;
	@Autowired
	private TPostBarCommentReplyMapper tPostBarCommentReplyMapper;
	@Autowired
	private TPostBarServiceImpl tPostBarServiceImpl;
	@Autowired
	private TPostBarTypeMapper tPostBarTypeMapper;
	@Autowired
	private TScoreGradeMapper tScoreGradeMapper;
	
	@ApiOperation(value = "首页帖子列表", notes = "传入userId就是登陆后可以看到自己是否点过赞,如果点过赞，则like字段为1，可根据这个字段展示点赞状态和控制取消点赞事件,可根据类型ID和内容进行搜索")
	@GetMapping("/postBar")
	public WebResult getPostBar(Page<TPostBar> page,String userId,TPostBar postBar) {
		QueryWrapper<TPostBar> query1=new QueryWrapper<TPostBar>();
		if(StrUtil.isNotBlank(postBar.getContent())) {
			query1.eq("pb.content", postBar.getContent());
		}
		if(StrUtil.isNotBlank(postBar.getTypeId())) {
			query1.eq("pb.type_id", postBar.getTypeId());
		}
		Page<TPostBar> pageList=tPostBarMapper.selectTypeNamePage(page, query1);
		if(pageList.getRecords()!=null && !pageList.getRecords().isEmpty()) {
			//拼装发帖者的等级
			List<String> idList=Lists.newArrayList();
			List<TScoreGrade> typeList=tScoreGradeMapper.selectList(null);
			//弄成一个分数为key,等级名称为value的map
			Map<String, String> scoreMap=typeList.stream()
					.collect(Collectors.toMap(TScoreGrade :: getScore, TScoreGrade::getName));
			for (TPostBar tPostBar : pageList.getRecords()) {
				for (Entry<String, String> score:scoreMap.entrySet()) {
					if(Integer.parseInt(tPostBar.getScore())>=Integer.parseInt(score.getKey())) {
						tPostBar.setScoreName(score.getValue());
					}
				}
				idList.add(tPostBar.getId());
			}
			if(StrUtil.isNotBlank(userId)) {//如果传了UserId，去查询点赞表看是否点赞
				Map<String, String> likeMap=Maps.newHashMap();
				QueryWrapper<TPostBarLike> query=new QueryWrapper<TPostBarLike>();
				query.in("post_id", idList).eq("create_id", userId);
				List<TPostBarLike> likeList=tPostBarLikeMapper.selectList(query);
				if(likeList!=null && !likeList.isEmpty()) {
					for (TPostBarLike tPostBarLike : likeList) {
						likeMap.put(tPostBarLike.getPostId(), tPostBarLike.getPostId());
					}
					for (TPostBar tPostBar : pageList.getRecords()) {
						if(StrUtil.isNotBlank(likeMap.get(tPostBar.getId()))) {
							tPostBar.setLike("1");
						}
					}
				}
			}
			return selectSuccess(pageList);
		}
		return selectNotFound();
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "帖子详情", notes = "")
	@GetMapping("/postBar/{id}")
	public WebResult getPostBar(@PathVariable("id") String id,String userId) {
		QueryWrapper<TPostBar> query3=new QueryWrapper<TPostBar>();
		query3.eq("pb.id",id);
		TPostBar postBar=tPostBarMapper.selectTypeNamePage(query3);
		//查询帖子所有评论
		if(postBar!=null) {
			List<TScoreGrade> typeList=tScoreGradeMapper.selectList(null);
			//弄成一个分数为key,等级名称为value的map
			Map<String, String> scoreMap=typeList.stream()
					.collect(Collectors.toMap(TScoreGrade :: getScore, TScoreGrade::getName));
				for (Entry<String, String> score:scoreMap.entrySet()) {
					if(Integer.parseInt(postBar.getScore())>=Integer.parseInt(score.getKey())) {
						postBar.setScoreName(score.getValue());
					}
				}
			if(StrUtil.isNotBlank(userId)) {
				QueryWrapper<TPostBarLike> query2=new QueryWrapper<TPostBarLike>();
				query2.eq("post_id", postBar.getId()).eq("create_id",userId);
				TPostBarLike like=tPostBarLikeMapper.selectOne(query2);
				if(like!=null) {
					postBar.setLike("1");
				}
			}
			//增加浏览数，浏览记录等
			tPostBarServiceImpl.updateNum(postBar,userId);
			@SuppressWarnings("rawtypes")
			QueryWrapper query=new QueryWrapper();
			query.eq("post_id", id);
			List<TPostBarComment> commentList=tPostBarCommentMapper.selectList(query);
			List<TPostBarCommentReply> replyList=tPostBarCommentReplyMapper.selectList(query);
			if(commentList!=null && !commentList.isEmpty()) {
				if(replyList!=null && !replyList.isEmpty()) {
					Map<String, List<TPostBarCommentReply>> replyMap=replyList.stream()
							.collect(Collectors.groupingBy(TPostBarCommentReply :: getCommentId));
					for (TPostBarComment tPostBarComment : commentList) {
						tPostBarComment.setReplyList(replyMap.get(tPostBarComment.getId()));
					}
				}
				postBar.setCommentList(commentList);
			}
			return selectSuccess(postBar);
		}
		return selectNotFound();
	}
	
	@ApiOperation(value = "查询帖子类型", notes = "")
	@GetMapping("/postBarType")
	public WebResult getPostBarType() {
		List<TPostBarType> typeList=tPostBarTypeMapper.selectList(null);
		if(typeList!=null && !typeList.isEmpty()) {
			return selectSuccess(typeList);
		}
		return selectNotFound();
	}
	
	@ApiOperation(value = "查询所有等级", notes = "")
	@GetMapping("/grade")
	public WebResult getAllGrade() {
		List<TScoreGrade> typeList=tScoreGradeMapper.selectList(null);
		if(typeList!=null && !typeList.isEmpty()) {
			return selectSuccess(typeList);
		}
		return selectNotFound();
	}
	
	@ApiOperation(value = "查询热门帖子", notes = "")
	@GetMapping("/hotPost")
	public WebResult getHotPostBar(){
		//查询所有帖子评论
		List<TPostBarComment> commentList=tPostBarCommentMapper.selectList(null);
		if(commentList!=null && !commentList.isEmpty()) {
			//然后将每个帖子评论分组
			Map<String, List<TPostBarComment>> commentMap=commentList.stream()
					.collect(Collectors.groupingBy(TPostBarComment ::getPostId));
			//循环分组拿到每个帖子的所有评论
			List<String> postIdList=Lists.newArrayList();
			Map<String, Integer> postSizeMap=Maps.newHashMap();
			for(Entry<String, List<TPostBarComment>> comment:commentMap.entrySet()) {
				//将评论放到map,去除重复IP，然后将map大小存入List
				List<TPostBarComment> gropuComment=comment.getValue();
				//组成IP-评论的实体类的map
				Map<String, TPostBarComment> normalComment=Maps.newHashMap();
				for (TPostBarComment tPostBarComment : gropuComment) {
					normalComment.put(tPostBarComment.getIpAddress(), tPostBarComment);
				}
				if(postIdList.size()<10) {
					postSizeMap.put(comment.getKey(),normalComment.entrySet().size());
					postIdList.add(comment.getKey());
				}
			}
			List<TPostBar> postBarList=tPostBarMapper.selectBatchIds(postIdList);
			for (TPostBar tPostBar : postBarList) {
				tPostBar.setIpCommentNum(postSizeMap.get(tPostBar.getId()));
			}
			//对list进行排序获得前十的热帖
			List<TPostBar> sortList=postBarList.stream().
			sorted(Comparator.comparing(TPostBar :: getIpCommentNum).reversed())
			.collect(Collectors.toList());
			return selectSuccess(sortList);
		}
		return selectNotFound();
	}

}
