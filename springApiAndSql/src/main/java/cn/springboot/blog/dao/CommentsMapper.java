package cn.springboot.blog.dao;

import cn.springboot.blog.entity.CommentAndUser;
import cn.springboot.blog.entity.Comments;
import org.apache.ibatis.annotations.Param;

import java.util.*;

public interface CommentsMapper {
    int deleteByPrimaryKey(Integer cid);

    int insert(Comments row);

    int insertSelective(Comments row);

    Comments selectByPrimaryKey(Integer cid);

    int updateByPrimaryKeySelective(Comments row);

    int updateByPrimaryKeyWithBLOBs(Comments row);

    int updateByPrimaryKey(Comments row);

    List<CommentAndUser> selectAllByAidAndUid(@Param("uid") Integer uid, @Param("aid") Integer aid);

    List<CommentAndUser> selectAllByPrimaryKeyAnd(@Param("uid") Integer uid, @Param("aid") Integer aid,@Param("rootUid") Integer rootUid, @Param("answerUid") Integer answerUid, @Param("cid") Integer cid);
}