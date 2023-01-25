package cn.springboot.blog.api.blog;

import cn.springboot.blog.entity.CommentAndUser;
import cn.springboot.blog.entity.Comments;
import cn.springboot.blog.service.ArticleService;
import cn.springboot.blog.service.CommentsService;
import cn.springboot.blog.util.Result;
import cn.springboot.blog.util.ResultGenerator;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "v1", tags = "评论操作相关接口")
@RequestMapping("/api/v1")
public class CommentsApi {
    @Autowired
    CommentsService commentsService;
    @Autowired
    ArticleService articleService;
    @GetMapping("/comment/getComments")
    public Result getComments(@RequestParam Integer uid,@RequestParam Integer aid){
        List<CommentAndUser> comments = commentsService.getComments(uid, aid);
        if(comments==null){
            return ResultGenerator.genErrorResult(500,"查找失败");
        }
        return ResultGenerator.genSuccessResult(comments);
    }

    @PostMapping("/comment/insertComment")
    public Result insertComment(@RequestBody Comments comments){
        int i = commentsService.insertComments(comments);
        if(i!=0){

            int intParams = articleService.updateIntParams(null, "comments", comments.getAid());

            if(intParams!=0){
                return ResultGenerator.genSuccessResult();
            }
            return ResultGenerator.genErrorResult(501,"文章评论条数更新失败");
        }else{
            return  ResultGenerator.genFailResult("插入失败");
        }
    }

    @GetMapping("/comment/getDetailComments")
    public Result getDetailComments(@RequestParam Integer uid,@RequestParam Integer aid,@RequestParam Integer rootUid,@RequestParam Integer answerUid, @RequestParam Integer cid){
        List<CommentAndUser> comments = commentsService.getDetailComments(uid,aid,rootUid,answerUid, cid);
        if(comments==null){
            return ResultGenerator.genErrorResult(500,"查找失败");
        }
        return ResultGenerator.genSuccessResult(comments);
    }
}
