package com.example.demo.myPostBar.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author youkehai
 * @since 2020-01-15
 */
@TableName("t_user")
public class TUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**验证码*/
    @TableField(exist=false)
    @NotNull
    private String code;
    
    /**等级名称*/
    @TableField(exist=false)
    private String grade;
    
    /**距离下一等级积分*/
    @TableField(exist=false)
    private String scoreFar;
    /**帖子ID或者好友关系表ID*/
    @TableField(exist=false)
    private String postId;
    
    
    
    public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getScoreFar() {
		return scoreFar;
	}

	public void setScoreFar(String scoreFar) {
		this.scoreFar = scoreFar;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
     * id
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 账号
     */
    @TableField("username")
    private Long username;

    /**
     * 邮箱
     */
    @TableField("email")
    @NotNull
    @Email
    private String email;

    /**
     * 等级积分
     */
    @TableField("score")
    private String score;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 性别
     */
    @TableField("sex")
    private String sex;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 注册时间
     */
    @TableField("create_date")
    private LocalDateTime createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Long getUsername() {
        return username;
    }

    public void setUsername(Long username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "TUser{" +
            "id=" + id +
            ", name=" + name +
            ", username=" + username +
            ", email=" + email +
            ", score=" + score +
            ", password=" + password +
            ", sex=" + sex +
            ", avatar=" + avatar +
            ", createDate=" + createDate +
        "}";
    }
}
