package cn.springboot.blog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class CommentAndUser implements Serializable {
    private Long id;

    private String email;

    private String username;

    private String password;

    private String head;

    private String cardimg;

    private Byte lockedflag;

    private Byte isdeleted;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    private String describe;

    /**
     * 评论id
     *
     * @mbg.generated
     */
    private Integer cid;

    /**
     * 文章发布者id
     *
     * @mbg.generated
     */
    private Integer uid;

    /**
     * 文章id
     *
     * @mbg.generated
     */
    private Integer aid;

    /**
     * 评论者id
     *
     * @mbg.generated
     */
    private Integer fromUid;

    /**
     * 目标评论者id
     *
     * @mbg.generated
     */
    private Integer toUid;

    /**
     * 评论时间
     *
     * @mbg.generated
     */
    private Date date;

    /**
     * 上层评论id
     *
     * @mbg.generated
     */
    private Integer precid;

    /**
     * 点赞数
     *
     * @mbg.generated
     */
    private Integer like;

    /**
     * 评论内容
     *
     * @mbg.generated
     */
    private String content;

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
    private Date time;

    /**
     * 点赞类型（评论，文章）
     *
     * @mbg.generated
     */
    private Integer type;

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


    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getFromUid() {
        return fromUid;
    }

    public void setFromUid(Integer fromUid) {
        this.fromUid = fromUid;
    }

    public Integer getToUid() {
        return toUid;
    }

    public void setToUid(Integer toUid) {
        this.toUid = toUid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getPrecid() {
        return precid;
    }

    public void setPrecid(Integer precid) {
        this.precid = precid;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getCardimg() {
        return cardimg;
    }

    public void setCardimg(String cardimg) {
        this.cardimg = cardimg;
    }

    public Byte getLockedflag() {
        return lockedflag;
    }

    public void setLockedflag(Byte lockedflag) {
        this.lockedflag = lockedflag;
    }

    public Byte getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Byte isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", cid=").append(cid);
        sb.append(", uid=").append(uid);
        sb.append(", aid=").append(aid);
        sb.append(", fromUid=").append(fromUid);
        sb.append(", toUid=").append(toUid);
        sb.append(", date=").append(date);
        sb.append(", precid=").append(precid);
        sb.append(", like=").append(like);
        sb.append(", content=").append(content);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", email=").append(email);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", head=").append(head);
        sb.append(", cardimg=").append(cardimg);
        sb.append(", lockedflag=").append(lockedflag);
        sb.append(", isdeleted=").append(isdeleted);
        sb.append(", createtime=").append(createtime);
        sb.append(", describe=").append(describe);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}