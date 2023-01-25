package cn.springboot.blog.service;


import cn.springboot.blog.entity.ArticlePointsCount;
import cn.springboot.blog.entity.GoodPoints;
import  java.util.*;

public interface GoodPointsService {
    int delByKeys(Integer uid,Integer type, Integer cid,Integer aid);
    int updateByKeys(GoodPoints goodPoints);

//    点赞插入
    int onLikeActive(GoodPoints goodPoints);

    GoodPoints checkContain(Integer uid,Integer cid, Integer aid);

    List<ArticlePointsCount> countType(Integer cid,Integer aid);

    List<Integer> getTypeUids(Integer aid, Integer type);
}
