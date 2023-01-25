package cn.springboot.blog.service.impl;

import cn.springboot.blog.dao.UserModulesMapper;
import cn.springboot.blog.entity.UserModules;
import cn.springboot.blog.service.UserModuleService;
import cn.springboot.blog.util.SystemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserModuleServiceImpl implements UserModuleService {

    @Autowired
    UserModulesMapper userModulesMapper;

    @Override
    public List<String> getModulesById(Integer uid) {
        UserModules userModules = userModulesMapper.selectByPrimaryKey(uid);
        List<String> res = new ArrayList<>();
        if(userModules==null){
            return res;
        }
        for(String s:userModules.getOpenModules().split(",")){
            res.add(SystemUtil.getModuleName(Integer.parseInt(s)));
        }
        return res;
    }
}
