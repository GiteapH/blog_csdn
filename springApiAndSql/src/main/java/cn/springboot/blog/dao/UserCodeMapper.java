package cn.springboot.blog.dao;

import cn.springboot.blog.entity.UserCode;

public interface UserCodeMapper {
    int deleteByPrimaryKey(Integer uid);

    int insert(UserCode row);

    int insertSelective(UserCode row);

    UserCode selectByPrimaryKey(Integer uid);

    int updateByPrimaryKeySelective(UserCode row);

    int updateByPrimaryKey(UserCode row);
}