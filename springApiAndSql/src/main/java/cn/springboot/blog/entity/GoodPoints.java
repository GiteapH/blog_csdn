package cn.springboot.blog.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class GoodPoints implements Serializable {
    /**
     * 用户id
     *
     * @mbg.generated
     */
    private Integer goodUid;

    /**
     * 时间
     *
     * @mbg.generated
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date time;

    /**
     * 点赞类型（评论，文章）
     *
     * @mbg.generated
     */
    private Integer type;
    private Integer ntype;

    /**
     * 评论id
     *
     * @mbg.generated
     */
    private Integer goodCid;

    /**
     * 文章id
     *
     * @mbg.generated
     */
    private Integer goodAid;

    private static final long serialVersionUID = 1L;

    public Integer getGoodUid() {
        return goodUid;
    }

    public void setGoodUid(Integer goodUid) {
        this.goodUid = goodUid;
    }
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getGoodCid() {
        return goodCid;
    }

    public void setGoodCid(Integer goodCid) {
        this.goodCid = goodCid;
    }

    public Integer getGoodAid() {
        return goodAid;
    }

    public void setGoodAid(Integer goodAid) {
        this.goodAid = goodAid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", goodUid=").append(goodUid);
        sb.append(", time=").append(time);
        sb.append(", type=").append(type);
        sb.append(", goodCid=").append(goodCid);
        sb.append(", goodAid=").append(goodAid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}