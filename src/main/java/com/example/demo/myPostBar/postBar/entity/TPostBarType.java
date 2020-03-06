package com.example.demo.myPostBar.postBar.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 帖子类型表
 * </p>
 *
 * @author youkehai
 * @since 2020-01-15
 */
@TableName("t_post_bar_type")
public class TPostBarType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 类型名称
     */
    @TableField("name")
    private String name;

    /**
     * 类型图标
     */
    @TableField("image")
    private String image;

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
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "TPostBarType{" +
            "id=" + id +
            ", name=" + name +
            ", image=" + image +
        "}";
    }
}
