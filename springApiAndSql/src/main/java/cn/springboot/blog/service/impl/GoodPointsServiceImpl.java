package cn.springboot.blog.service.impl;

import cn.springboot.blog.dao.GoodPointsMapper;
import cn.springboot.blog.entity.ArticlePointsCount;
import cn.springboot.blog.entity.GoodPoints;
import cn.springboot.blog.service.GoodPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodPointsServiceImpl implements GoodPointsService {
    @Autowired
    GoodPointsMapper goodPointsMapper;
    @Override
    public int delByKeys(Integer uid, Integer type, Integer cid,Integer aid) {
        return goodPointsMapper.deleteByGoodCid(uid,type,cid,aid);
    }


    @Override
    public int updateByKeys(GoodPoints goodPoints) {
        return goodPointsMapper.updateByPrimaryKeySelective(goodPoints);
    }

    @Override
    public GoodPoints checkContain(Integer uid, Integer cid,Integer aid){
        return goodPointsMapper.selectByPrimaryKey(uid, cid,aid);
    }

    @Override
    public List<ArticlePointsCount> countType(Integer cid, Integer aid) {
        return goodPointsMapper.countByGoodAidOrGoodCid(aid,cid);
    }

    @Override
    public List<Integer> getTypeUids(Integer aid, Integer type) {
        return goodPointsMapper.getGoodUidListByType(type,aid);
    }

    @Override
    public int onLikeActive(GoodPoints goodPoints) {
        return goodPointsMapper.insertSelective(goodPoints);
    }
}
