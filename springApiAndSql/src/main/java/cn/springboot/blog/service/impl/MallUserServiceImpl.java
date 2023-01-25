package cn.springboot.blog.service.impl;
import cn.springboot.blog.api.blog.param.MallUserUpdateParam;
import cn.springboot.blog.common.MallException;
import cn.springboot.blog.common.ServiceResultEnum;
import cn.springboot.blog.dao.UserTokenMapper;
import cn.springboot.blog.dao.UsersMapper;
import cn.springboot.blog.entity.UserToken;
import cn.springboot.blog.entity.Users;
import cn.springboot.blog.service.MallUserService;
import cn.springboot.blog.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class MallUserServiceImpl implements MallUserService {

    @Autowired
    private UsersMapper mallUserMapper;
    @Autowired
    private UserTokenMapper newBeeMallUserTokenMapper;


    @Override
    public String login(String loginName, String passwordMD5) {
        Users user = mallUserMapper.selectByLoginNameAndPasswd(loginName, passwordMD5);
        if (user != null) {
            if (user.getLockedflag() == 1) {
                return ServiceResultEnum.LOGIN_USER_LOCKED_ERROR.getResult();
            }
            //登录后即执行修改token的操作
            String token = getNewToken(System.currentTimeMillis() + "", user.getId());
            UserToken userToken = newBeeMallUserTokenMapper.selectByPrimaryKey(user.getId());
            //当前时间
            Date now = new Date();
            //过期时间
            Date expireTime = new Date(now.getTime() + 2 * 24 * 3600 * 1000);//过期时间 48 小时
            if (userToken == null) {
                userToken = new UserToken();
                userToken.setUserId(user.getId());
                userToken.setToken(token);
                userToken.setUpdateTime(now);
                userToken.setExpireTime(expireTime);
                //新增一条token数据
                if (newBeeMallUserTokenMapper.insertSelective(userToken) > 0) {
                    //新增成功后返回
                    return token;
                }
            } else {
                userToken.setToken(token);
                userToken.setUpdateTime(now);
                userToken.setExpireTime(expireTime);
                //更新
                if (newBeeMallUserTokenMapper.updateByPrimaryKeySelective(userToken) > 0) {
                    //修改成功后返回
                    return token;
                }
            }

        }
        return ServiceResultEnum.LOGIN_ERROR.getResult();
    }

    /**
     * 获取token值
     *
     * @param timeStr
     * @param userId
     * @return
     */

    private String getNewToken(String timeStr, Long userId) {
        String src = timeStr + userId + NumberUtil.genRandomNum(4);
        return SystemUtil.genToken(src);
    }



    @Override
    public Boolean updateUserInfo(MallUserUpdateParam mallUser, String email) {
        Users user = mallUserMapper.selectByEmail(email);
        if (user == null) {
            MallException.fail(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        BeanUtil.copyProperties(mallUser,user);
        //user.setPasswordMd5(mallUser.getPasswordMd5());
        //若密码为空字符，则表明用户不打算修改密码，使用原密码保存
        if (!MD5Util.MD5Encode("", "UTF-8").equals(mallUser.getPasswordMd5())){
            user.setPassword(mallUser.getPasswordMd5());
        }
        user.setDescribe(mallUser.getDescribe());
        if (mallUserMapper.updateByPrimaryKeySelective(user) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean logout(Long userId) {
        return newBeeMallUserTokenMapper.deleteByPrimaryKey(userId) > 0;
    }

    @Override
    public PageResult getMallUsersPage(PageQueryUtil pageUtil) {
        List<Users> mallUsers = mallUserMapper.findMallUserList(pageUtil);
        int total = mallUserMapper.getTotalMallUsers(pageUtil);
        PageResult pageResult = new PageResult(mallUsers, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

}
