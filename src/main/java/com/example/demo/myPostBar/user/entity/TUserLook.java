package com.example.demo.myPostBar.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 用户浏览记录表
 * </p>
 *
 * @author youkehai
 * @since 2020-01-15
 */
@TableName("t_user_look")
public class TUserLook implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 帖子ID
     */
    @TableField("post_id")
    private String postId;

    /**
     * 帖子标题
     */
    @TableField("post_title")
    private String postTitle;

    /**
     * 浏览时间
     */
    @TableField("create_date")
    private LocalDateTime createDate;

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
    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "TUserLook{" +
            "id=" + id +
            ", userId=" + userId +
            ", postId=" + postId +
            ", postTitle=" + postTitle +
            ", createDate=" + createDate +
        "}";
    }
}
