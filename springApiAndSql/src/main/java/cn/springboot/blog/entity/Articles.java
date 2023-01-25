package cn.springboot.blog.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class Articles implements Serializable {
    /**
     * 上传者id
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
     * 发布日期
     *
     * @mbg.generated
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    /**
     * 点赞数
     *
     * @mbg.generated
     */
    private Integer like;

    /**
     * 评论数
     *
     * @mbg.generated
     */
    private Integer comments;

    /**
     * 观看数
     *
     * @mbg.generated
     */
    private Integer watched;

    /**
     * 收藏数
     *
     * @mbg.generated
     */
    private Integer collected;

    /**
     * 文章标题
     *
     * @mbg.generated
     */
    private String title;

    /**
     * 文章内容
     *
     * @mbg.generated
     */
    private String articles;

    /**
     * 文章类别，用户自定义
     *
     * @mbg.generated
     */
    private String classper;

    /**
     * 文章类别，系统定义
     *
     * @mbg.generated
     */
    private String classsys;

    /**
     * 简介
     *
     * @mbg.generated
     */
    private String tinymes;

    private static final long serialVersionUID = 1L;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getWatched() {
        return watched;
    }

    public void setWatched(Integer watched) {
        this.watched = watched;
    }

    public Integer getCollected() {
        return collected;
    }

    public void setCollected(Integer collected) {
        this.collected = collected;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticles() {
        return articles;
    }

    public void setArticles(String articles) {
        this.articles = articles;
    }

    public String getClassper() {
        return classper;
    }

    public void setClassper(String classper) {
        this.classper = classper;
    }

    public String getClasssys() {
        return classsys;
    }

    public void setClasssys(String classsys) {
        this.classsys = classsys;
    }

    public String getTinymes() {
        return tinymes;
    }

    public void setTinymes(String tinymes) {
        this.tinymes = tinymes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", uid=").append(uid);
        sb.append(", aid=").append(aid);
        sb.append(", date=").append(date);
        sb.append(", like=").append(like);
        sb.append(", comments=").append(comments);
        sb.append(", watched=").append(watched);
        sb.append(", collected=").append(collected);
        sb.append(", title=").append(title);
        sb.append(", articles=").append(articles);
        sb.append(", classper=").append(classper);
        sb.append(", classsys=").append(classsys);
        sb.append(", tinymes=").append(tinymes);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}