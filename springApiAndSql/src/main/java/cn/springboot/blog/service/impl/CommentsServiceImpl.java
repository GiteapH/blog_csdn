package cn.springboot.blog.service.impl;

import cn.springboot.blog.dao.CommentsMapper;
import cn.springboot.blog.entity.CommentAndUser;
import cn.springboot.blog.entity.Comments;
import cn.springboot.blog.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    CommentsMapper commentsMapper;

    @Override
    public List<CommentAndUser> getComments(@RequestParam Integer uid,@RequestParam Integer aid) {
        List<CommentAndUser> commentAndUsers;
        try{
            commentAndUsers = commentsMapper.selectAllByAidAndUid(uid, aid);
            System.out.println(commentAndUsers);
        }catch (Exception e){
            e.printStackTrace();
           return null;
        }
        return commentAndUsers;
    }

    @Override
    public int insertComments(Comments comments) {
        return commentsMapper.insertSelective(comments);
    }

    @Override
    public List<CommentAndUser> getDetailComments(@RequestParam Integer uid,@RequestParam Integer aid,@RequestParam Integer rootUid,@RequestParam Integer answerUid,@RequestParam Integer cid) {
        return commentsMapper.selectAllByPrimaryKeyAnd(uid,aid,rootUid,answerUid, cid);
    }
}
