package com.example.demo.myPostBar.postBar.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 帖子——评论-回复表
 * </p>
 *
 * @author youkehai
 * @since 2020-01-15
 */
@TableName("t_post_bar_comment_reply")
public class TPostBarCommentReply implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 帖子ID
     */
    @TableField("post_id")
    private String postId;

    /**
     * 评论ID
     */
    @TableField("comment_id")
    private String commentId;

    /**
     * 回复内容
     */
    @TableField("content")
    private String content;

    /**
     * 回复时间
     */
    @TableField("create_date")
    private LocalDateTime createDate;

    /**
     * 回复人
     */
    @TableField("create_id")
    private String createId;

    /**
     * 回复人名称
     */
    @TableField("create_name")
    private String createName;

    /**
     * 回复人头像
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
    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        return "TPostBarCommentReply{" +
            "id=" + id +
            ", postId=" + postId +
            ", commentId=" + commentId +
            ", content=" + content +
            ", createDate=" + createDate +
            ", createId=" + createId +
            ", createName=" + createName +
            ", createAvatar=" + createAvatar +
        "}";
    }
}
