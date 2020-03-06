package com.example.demo.myPostBar.postBar.entity;

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
 * 帖子——评论表
 * </p>
 *
 * @author youkehai
 * @since 2020-01-15
 */
@TableName("t_post_bar_comment")
public class TPostBarComment implements Serializable {

    private static final long serialVersionUID = 1L;

    
    /***
     * 回复List
     */
    @TableField(exist = false)
    private List<TPostBarCommentReply> replyList;
    
    
    public List<TPostBarCommentReply> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<TPostBarCommentReply> replyList) {
		this.replyList = replyList;
	}

	/**
     * id
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 帖子ID
     */
    @NotNull
    @TableField("post_id")
    private String postId;

    /**
     * 评论内容
     */
    @NotNull
    @TableField("content")
    private String content;

    /**
     * 评论图片
     */
    @TableField("images")
    private String images;

    /**
     * IP地址
     */
    @TableField("ip_address")
    private String ipAddress;

    /**
     * 评论时间
     */
    @TableField("create_date")
    private LocalDateTime createDate;

    /**
     * 评论人
     */
    @TableField("create_id")
    private String createId;

    /**
     * 评论人名称
     */
    @TableField("create_name")
    private String createName;

    /**
     * 评论人头像
     */
    @TableField("create_avatar")
    private String createAvatar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
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
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
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
        return "TPostBarComment{" +
            "id=" + id +
            ", postId=" + postId +
            ", content=" + content +
            ", images=" + images +
            ", ipAddress=" + ipAddress +
            ", createDate=" + createDate +
            ", createId=" + createId +
            ", createName=" + createName +
            ", createAvatar=" + createAvatar +
        "}";
    }
}
