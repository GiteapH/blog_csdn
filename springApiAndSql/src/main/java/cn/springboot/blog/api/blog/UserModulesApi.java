package cn.springboot.blog.api.blog;

import cn.springboot.blog.service.UserModuleService;
import cn.springboot.blog.util.Result;
import cn.springboot.blog.util.ResultGenerator;
import cn.springboot.blog.util.SystemUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.*;

@RestController
@Api(value = "v1", tags = "用户模块操作接口")
@RequestMapping("/api/v1")
public class UserModulesApi {

    @Autowired
    UserModuleService userModuleService;

    @GetMapping("/modules/getUMs")
    public Result getUerModules(@RequestParam Integer uid){
        try {
            Set<String> modules = new HashSet<>(userModuleService.getModulesById(uid));
            String[] moduleNames = SystemUtil.getModuleNames();
            Map<String,Object> resMap =  new HashMap<>();
            for(int i=0;i<moduleNames.length;i++){
                Map<String, Object> name_checked = new HashMap<>();
                name_checked.put("name",moduleNames[i]);
                name_checked.put("checked",modules.contains(moduleNames[i]));
                name_checked.put("key",i);
                resMap.put(moduleNames[i],name_checked);
            }
            return ResultGenerator.genSuccessResult(resMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genErrorResult(500,e.getMessage());
        }
    }
}
