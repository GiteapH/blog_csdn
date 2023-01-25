package cn.springboot.blog.service;

import cn.springboot.blog.entity.Articles;
import cn.springboot.blog.entity.UserAndArticle;
import cn.springboot.blog.util.PageQueryUtil;

import  java.util.*;

public interface ArticleService {
    long getArticleLen(Integer uid);

    long getCurDaysOOArticlesCount(Integer type,String tag);

    List<Articles> getArticleTags();

    List<UserAndArticle> getUserAndArticle(PageQueryUtil pageQueryUtil);

    UserAndArticle getArticle(int uid, int aid);

    List<UserAndArticle> getAroundCurDayArticles(PageQueryUtil pageQueryUtil);


    int insertArticle(Articles articles);

    int updateIntParams(String oldParamName,String newParamName,Integer aid);

    int updateArticleParams(Articles articles);


    List<Articles> getUserArticle(PageQueryUtil pageQueryUtil);

    int deleteArticle(Integer aid);
}
