package com.example.demo.myPostBar.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 评分等级表
 * </p>
 *
 * @author youkehai
 * @since 2020-01-15
 */
@TableName("t_score_grade")
public class TScoreGrade implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 分值
     */
    @TableField("score")
    private String score;

    /**
     * 名称(达到分值即可设定为某个等级，还可以查看差多少分能达到某个等级)
     */
    @TableField("name")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TScoreGrade{" +
            "id=" + id +
            ", score=" + score +
            ", name=" + name +
        "}";
    }
}
