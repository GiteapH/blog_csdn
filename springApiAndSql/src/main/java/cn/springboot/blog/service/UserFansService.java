package cn.springboot.blog.service;

import cn.springboot.blog.entity.UserFan;
import cn.springboot.blog.util.Result;

public interface UserFansService {
    Result addFan(Integer upUid, Integer loginUid, Byte type);

//    返回关注类型
    UserFan checkLoginUserIsFan(Integer upUid, Integer loginUid);

    int delFan(Integer fid);
}
