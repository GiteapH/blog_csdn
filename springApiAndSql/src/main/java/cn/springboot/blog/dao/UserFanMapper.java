package cn.springboot.blog.dao;

import cn.springboot.blog.entity.UserFan;

public interface UserFanMapper {
    int deleteByPrimaryKey(Integer fid);

    int insert(UserFan row);

    int insertSelective(UserFan row);

    UserFan selectByPrimaryKey(Integer fid);

    UserFan selectByFTouidAndFUid(Integer upUid,Integer loginUid);

    int updateByPrimaryKeySelective(UserFan row);

    int updateByPrimaryKey(UserFan row);
}