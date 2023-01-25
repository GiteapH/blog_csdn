package cn.springboot.blog.service.impl;

import cn.springboot.blog.dao.ArticlesMapper;
import cn.springboot.blog.entity.Articles;
import cn.springboot.blog.entity.UserAndArticle;
import cn.springboot.blog.service.ArticleService;
import cn.springboot.blog.util.PageQueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticlesMapper articlesMapper;

    @Override
    public long getArticleLen(Integer uid){
        return articlesMapper.getTotalArticles(uid);
    }

    @Override
    public long getCurDaysOOArticlesCount(Integer type,String tag) {
        return articlesMapper.getTotalCurDays(type,tag);
    }

    @Override
    public List<Articles> getArticleTags(){
        return articlesMapper.getClasssys();
    }

    public List<UserAndArticle> getUserAndArticle(PageQueryUtil pageQueryUtil) {
        return articlesMapper.getUser2Article(pageQueryUtil);
    }

    public UserAndArticle getArticle(int uid, int aid){
        return articlesMapper.selectByPrimaryKey(uid,aid);
    }

    @Override
    public List<UserAndArticle> getAroundCurDayArticles(PageQueryUtil pageQueryUtil) {
        return articlesMapper.getUser2ArticleNearCurDay(pageQueryUtil);
    }

    @Override
    public int insertArticle(Articles articles) {
        return articlesMapper.insertSelective(articles);
    }

    @Override
    public int updateIntParams(String oldParamName, String newParamName, Integer aid) {
        return articlesMapper.updateIntParam(oldParamName,newParamName,aid);
    }

    @Override
    public int updateArticleParams(Articles articles) {
        return articlesMapper.updateByPrimaryKeySelective(articles);
    }

    @Override
    public List<Articles> getUserArticle(PageQueryUtil pageQueryUtil) {
        return  articlesMapper.getArticleByUid(pageQueryUtil);
    }

    @Override
    public int deleteArticle(Integer aid) {
        return articlesMapper.deleteByPrimaryKey(aid);
    }
}
