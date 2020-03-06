package com.example.demo.myPostBar.postBar.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 帖子表
 * </p>
 *
 * @author youkehai
 * @since 2020-01-15
 */
@TableName("t_post_bar")
public class TPostBar implements Serializable {

    private static final long serialVersionUID = 1L;

    /***
     * 类型名称
     */
    @TableField(exist = false)
    private String typeName;
    /***
     * 发帖人等级积分
     */
    @TableField(exist = false)
    private String score;
    /***
     * 独立IP评论数
     */
    @TableField(exist = false)
    private Integer ipCommentNum;
    
    
    /***
     * 发帖人等级名称
     */
    @TableField(exist = false)
    private String scoreName;
    
    public String getScoreName() {
		return scoreName;
	}

	public Integer getIpCommentNum() {
		return ipCommentNum;
	}

	public void setIpCommentNum(Integer ipCommentNum) {
		this.ipCommentNum = ipCommentNum;
	}

	public void setScoreName(String scoreName) {
		this.scoreName = scoreName;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/***
     * 评论
     */
    @TableField(exist = false)
    private List<TPostBarComment> commentList;
    
    public List<TPostBarComment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<TPostBarComment> commentList) {
		this.commentList = commentList;
	}

	/***
     * 是否点赞
     */
    @TableField(exist = false)
    private String like;
    
    public String getLike() {
		return like;
	}

	public void setLike(String like) {
		this.like = like;
	}

	/**
     * id
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 帖子标题
     */
    @NotNull
    @TableField("title")
    private String title;

    /**
     * 帖子版块ID
     */
    @NotNull
    @TableField("type_id")
    private String typeId;

    /**
     * 帖子内容
     */
    @NotNull
    @TableField("content")
    private String content;

    /**
     * 帖子图片
     */
    @TableField("images")
    private String images;

    /**
     * 浏览数
     */
    @TableField("look_num")
    private BigDecimal lookNum;

    /**
     * 点赞数
     */
    @TableField("like_num")
    private BigDecimal likeNum;

    /**
     * 评论数
     */
    @TableField("comment_num")
    private BigDecimal commentNum;

    /**
     * 发帖时间
     */
    @TableField("create_date")
    private LocalDateTime createDate;

    /**
     * 发帖人
     */
    @TableField("create_id")
    private String createId;

    /**
     * 发帖人名称
     */
    @TableField("create_name")
    private String createName;

    /**
     * 发帖人头像
     */
    @TableField("create_avatar")
    private String createAvatar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
    public BigDecimal getLookNum() {
        return lookNum;
    }

    public void setLookNum(BigDecimal lookNum) {
        this.lookNum = lookNum;
    }
    public BigDecimal getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(BigDecimal likeNum) {
        this.likeNum = likeNum;
    }
    public BigDecimal getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(BigDecimal commentNum) {
        this.commentNum = commentNum;
    }
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }
    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }
    public String getCreateAvatar() {
        return createAvatar;
    }

    public void setCreateAvatar(String createAvatar) {
        this.createAvatar = createAvatar;
    }

    @Override
    public String toString() {
        return "TPostBar{" +
            "id=" + id +
            ", userId=" + userId +
            ", title=" + title +
            ", typeId=" + typeId +
            ", content=" + content +
            ", images=" + images +
            ", lookNum=" + lookNum +
            ", likeNum=" + likeNum +
            ", commentNum=" + commentNum +
            ", createDate=" + createDate +
            ", createId=" + createId +
            ", createName=" + createName +
            ", createAvatar=" + createAvatar +
        "}";
    }
}
