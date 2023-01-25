package cn.springboot.blog.api.blog;

import cn.springboot.blog.api.blog.param.FanParams;
import cn.springboot.blog.service.UserFansService;
import cn.springboot.blog.util.Result;
import cn.springboot.blog.util.ResultGenerator;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "v1", tags = "关注系统接口文档")
@RequestMapping("/api/v1")
public class UserFansApi {
    @Autowired
    UserFansService userFansService;
    @PutMapping("/fan/putFan")
    public Result addFan(@RequestBody FanParams fanParams){
        return userFansService.addFan(fanParams.getToUid(), fanParams.getUid(), fanParams.getType());
    }

    @DeleteMapping("/fan/delFan")
    public Result delFan(@RequestParam Integer fid){
        try {
            int i = userFansService.delFan(fid);
            return ResultGenerator.genSuccessResult(i);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }
}
