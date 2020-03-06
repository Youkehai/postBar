package com.example.demo.myPostBar.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 用户-好友表
 * </p>
 *
 * @author youkehai
 * @since 2020-01-15
 */
@TableName("t_user_freind")
public class TUserFreind implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 我的ID
     */
    @TableField("my_id")
    private String myId;

    /**
     * 朋友ID
     */
    @TableField("freind_id")
    private String freindId;

    /**
     * 状态(0好友，1申请中，2已拒绝)
     */
    @TableField("status")
    private String status;

    /**
     * 申请时间
     */
    @TableField("create_date")
    private LocalDateTime createDate;

    /**
     * 拒绝（成为好友）时间
     */
    @TableField("update_date")
    private LocalDateTime updateDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getMyId() {
        return myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }
    public String getFreindId() {
        return freindId;
    }

    public void setFreindId(String freindId) {
        this.freindId = freindId;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "TUserFreind{" +
            "id=" + id +
            ", myId=" + myId +
            ", freindId=" + freindId +
            ", status=" + status +
            ", createDate=" + createDate +
            ", updateDate=" + updateDate +
        "}";
    }
}
