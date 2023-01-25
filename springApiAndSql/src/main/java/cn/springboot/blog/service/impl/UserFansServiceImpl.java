package cn.springboot.blog.service.impl;

import cn.springboot.blog.dao.UserFanMapper;
import cn.springboot.blog.entity.UserFan;
import cn.springboot.blog.service.UserFansService;
import cn.springboot.blog.util.Result;
import cn.springboot.blog.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserFansServiceImpl implements UserFansService {
    @Autowired
    UserFanMapper userFanMapper;

    @Override
    public Result addFan(Integer upUid, Integer loginUid, Byte type) {
        int effectRow = -1;
        UserFan userFan = userFanMapper.selectByFTouidAndFUid(upUid, loginUid);
//      该用户尚未关注
        if(userFan==null){
            UserFan addFan = new UserFan();
            addFan.setfDate(new Date());
            addFan.setfTouid(upUid);
            addFan.setfUid(loginUid);
            addFan.setfType(type);
            effectRow = userFanMapper.insertSelective(addFan);
        }

        switch (effectRow){
            case -1:return ResultGenerator.genFailResult("该用户已被您关注");
            case 0:return ResultGenerator.genErrorResult(500,"发生未知错误");
            default:return ResultGenerator.genSuccessResult(effectRow);
        }
    }

    @Override
    public UserFan checkLoginUserIsFan(Integer upUid, Integer loginUid) {
        UserFan userFan = userFanMapper.selectByFTouidAndFUid(upUid, loginUid);
        System.out.println(userFan);
        if(userFan==null){
            return null;
        }else{
            return userFan;
        }
    }

    @Override
    public int delFan(Integer fid) {
        return userFanMapper.deleteByPrimaryKey(fid);
    }
}
