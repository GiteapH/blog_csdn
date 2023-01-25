package cn.springboot.blog.dao;

import cn.springboot.blog.entity.Users;
import cn.springboot.blog.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;
import java.util.*;
public interface UsersMapper {
    int deleteByPrimaryKey(@Param("id") Long id, @Param("email") String email);

    int insert(Users row);

    int insertSelective(Users row);

    Users selectByPrimaryKey(@Param("id") Long id, @Param("email") String email);

    int updateByPrimaryKeySelective(Users row);

    Users selectByLoginNameAndPasswd(String email,String password);

    Users selectByEmail(String email);



    Users selectById(Long id);
    List<Users> findMallUserList(PageQueryUtil pageUtil);

    int getTotalMallUsers(PageQueryUtil pageUtil);

    int updateByPrimaryKeyWithBLOBs(Users row);

    int updateByPrimaryKey(Users row);
}