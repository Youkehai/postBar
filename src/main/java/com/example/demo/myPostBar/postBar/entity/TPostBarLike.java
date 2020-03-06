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
 * 帖子——点赞表
 * </p>
 *
 * @author youkehai
 * @since 2020-01-15
 */
@TableName("t_post_bar_like")
public class TPostBarLike implements Serializable {

    private static final long serialVersionUID = 1L;
    /***
     * 点赞状态
     */
    @NotNull
    @TableField(exist = false)
    private String status;
    
    
    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    /**
     * 帖子标题
     */
    @TableField("post_title")
    private String postTitle;
	
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
     * 点赞时间
     */
    @TableField("create_date")
    private LocalDateTime createDate;

    /**
     * 点赞人
     */
    @TableField("create_id")
    private String createId;

    /**
     * 点赞人名称
     */
    @TableField("create_name")
    private String createName;

    /**
     * 点赞人头像
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
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
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
        return "TPostBarLike{" +
            "id=" + id +
            ", postId=" + postId +
            ", createDate=" + createDate +
            ", createId=" + createId +
            ", createName=" + createName +
            ", createAvatar=" + createAvatar +
        "}";
    }
}
