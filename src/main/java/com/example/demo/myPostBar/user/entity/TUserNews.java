package com.example.demo.myPostBar.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 用户-消息表
 * </p>
 *
 * @author youkehai
 * @since 2020-01-15
 */
@TableName("t_user_news")
public class TUserNews implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     *我的头像
     */
    @TableField(exist = false)
    private String myAvatar;
    /**
     *我的名称
     */
    @TableField(exist = false)
    private String myName;
    /**
     *朋友名称
     */
    @TableField(exist = false)
    private String name;
    /**
     *朋友头像
     */
    @TableField(exist = false)
    private String avatar;
    
    
    public String getMyAvatar() {
		return myAvatar;
	}

	public void setMyAvatar(String myAvatar) {
		this.myAvatar = myAvatar;
	}

	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
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
     * 发送者ID
     */
    @TableField("send_id")
    private String sendId;
    
    /**
     *消息类型（chat(聊天),message(通知)）
     */
    @TableField("type")
    private String type;
    
    /**
     * 存放其他内容，可放入帖子ID(post)，评论ID(comment)等
     */
    @TableField("body")
    private String body;

    /**
     * 消息内容
     */
    @NotNull
    @TableField("content")
    private String content;

    /**
     * 发送时间
     */
    @TableField("create_date")
    private LocalDateTime createDate;

    /**
     * 状态(0已读,1未读)
     */
    @TableField("status")
    private String status;

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
    public String getSendId() {
        return sendId;
    }

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setSendId(String sendId) {
        this.sendId = sendId;
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
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TUserNews{" +
            "id=" + id +
            ", userId=" + userId +
            ", sendId=" + sendId +
            ", content=" + content +
            ", createDate=" + createDate +
            ", status=" + status +
        "}";
    }
}
