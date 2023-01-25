package cn.springboot.blog.service;

import cn.springboot.blog.entity.CommentAndUser;
import cn.springboot.blog.entity.Comments;

import java.util.*;

public interface CommentsService {
    List<CommentAndUser> getComments(Integer uid,Integer aid);
    int insertComments(Comments comments);

    List<CommentAndUser> getDetailComments(Integer uid, Integer aid, Integer rootUid, Integer answerUid, Integer cid);
}
