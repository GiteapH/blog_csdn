package cn.springboot.blog.api.blog;

import cn.springboot.blog.api.blog.param.GoodPointsParam;
import cn.springboot.blog.entity.GoodPoints;
import cn.springboot.blog.service.ArticleService;
import cn.springboot.blog.service.GoodPointsService;
import cn.springboot.blog.util.Result;
import cn.springboot.blog.util.ResultGenerator;
import cn.springboot.blog.util.SystemUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@Api(value = "v1", tags = "点赞操作相关接口")
@RequestMapping("/api/v1")

public class GoodPointsApi {
    @Autowired
    GoodPointsService goodPointsService;
    @Autowired
    ArticleService articleService;

    @DeleteMapping("/GoodPointsParam/delByKeys")
    public Result delByKeys(@RequestBody GoodPointsParam goodPointsParam){
        int effectRow = goodPointsService.delByKeys(goodPointsParam.getUid(),goodPointsParam.getType(),goodPointsParam.getCid(),goodPointsParam.getAid());
        Result result = null;
        if(effectRow==0){
            result = ResultGenerator.genFailResult("修改失败");
        }else {
            result = ResultGenerator.genSuccessResult(effectRow);
        }
        return result;
    }

    @PutMapping("/GoodPointsParam/updateByKeys")
    public Result updateByKeys(@RequestBody GoodPointsParam goodPointsParam){
        GoodPoints goodPoints = new GoodPoints();
        goodPoints.setGoodUid(goodPointsParam.getUid());
        goodPoints.setGoodCid(goodPointsParam.getCid());
        goodPoints.setGoodAid(goodPointsParam.getAid());
        goodPoints.setType(goodPointsParam.getType());
        goodPoints.setNtype(goodPointsParam.getNtype());
        int effectRow = -1;

        Result RES;
        try {
            effectRow = goodPointsService.updateByKeys(goodPoints);
        }catch (Exception e){
            e.printStackTrace();
            RES = ResultGenerator.genErrorResult(500,e.getMessage());
            return RES;
        }


        if (effectRow==0){
            RES = ResultGenerator.genFailResult("更新失败");
        }
        else RES = ResultGenerator.genSuccessResult(effectRow);

        return RES;
    }

    @PutMapping("/GoodPointsParam/onLikeActive")
    public Result onLikeActive(@RequestBody GoodPointsParam goodPointsParam){
        GoodPoints goodPoints = new GoodPoints();
        goodPoints.setGoodUid(goodPointsParam.getUid());
        goodPoints.setGoodCid(goodPointsParam.getCid());
        goodPoints.setGoodAid(goodPointsParam.getAid());
        goodPoints.setTime(new Date());
        GoodPoints contain = goodPointsService.checkContain(goodPointsParam.getUid(),goodPointsParam.getCid(),goodPointsParam.getAid());
        int effectRow = -1,intParams = -1;
        Result RES;
        if (contain != null) {
            try {
                goodPoints.setType(contain.getType());
                goodPoints.setNtype(goodPointsParam.getType());
                effectRow = goodPointsService.updateByKeys(goodPoints);
                if(goodPointsParam.getType()!=0&&goodPointsParam.getType()!=1)
                    intParams = articleService.updateIntParams(SystemUtil.getParamName(contain.getType()-2),SystemUtil.getParamName(goodPointsParam.getType()-2),goodPointsParam.getAid());
            }catch (Exception e){
                e.printStackTrace();
                RES = ResultGenerator.genErrorResult(500,e.getMessage());
                return RES;
            }
        }else{
            try {
                goodPoints.setType(goodPointsParam.getType());
                effectRow = goodPointsService.onLikeActive(goodPoints);
                if(goodPointsParam.getType()!=0&&goodPointsParam.getType()!=1)
                    intParams = articleService.updateIntParams(null,SystemUtil.getParamName(goodPointsParam.getType()-2),goodPointsParam.getAid());
            }catch (Exception e){
                e.printStackTrace();
                RES = ResultGenerator.genErrorResult(500,e.getMessage());
                return RES;
            }

        }
        if (effectRow==0){
            RES = ResultGenerator.genFailResult("更新失败");
        }
        else if(intParams==0){
            RES = ResultGenerator.genFailResult("关联表更新失败");
        }
        else RES = ResultGenerator.genSuccessResult(effectRow);

        return RES;
    }

    @DeleteMapping("/GoodPointsParam/deleteLike")
    public Result deleteLike(@RequestBody GoodPointsParam goodPointsParam){
        int effectRows,intParams;
        try {
            effectRows = goodPointsService.delByKeys(goodPointsParam.getUid(), goodPointsParam.getType(), goodPointsParam.getCid(),goodPointsParam.getAid());
            intParams = articleService.updateIntParams(SystemUtil.getParamName(goodPointsParam.getType()),null,goodPointsParam.getAid());
        }catch (Exception e){
            return ResultGenerator.genErrorResult(500, e.getMessage());
        }
        if(effectRows==0){
            return ResultGenerator.genFailResult("删除失败");
        }
        else if(intParams==0){
            return ResultGenerator.genFailResult("关联表更新失败");
        }
        return ResultGenerator.genSuccessResult(effectRows);


    }
}
