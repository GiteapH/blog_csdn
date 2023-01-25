package cn.springboot.blog.dao;
import cn.springboot.blog.entity.ArticlePointsCount;
import cn.springboot.blog.entity.GoodPoints;

import java.util.*;
public interface GoodPointsMapper {
    int deleteByPrimaryKey(Integer goodUid,Integer goodCid,Integer goodAid);

    int insert(GoodPoints row);

    int insertSelective(GoodPoints row);

    GoodPoints selectByPrimaryKey(Integer goodUid,Integer cid,Integer aid);

    int updateByPrimaryKeySelective(GoodPoints row);

    int updateByPrimaryKey(GoodPoints row);

    int deleteByGoodCid(Integer uid,Integer type, Integer cid, Integer aid);

    List<ArticlePointsCount> countByGoodAidOrGoodCid(Integer aid, Integer cid);

    List<Integer> getGoodUidListByType(Integer type,Integer aid);
}