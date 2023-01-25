
package cn.springboot.blog.service;

import cn.springboot.blog.api.blog.param.MallUserUpdateParam;
import cn.springboot.blog.util.PageQueryUtil;
import cn.springboot.blog.util.PageResult;

public interface MallUserService {

    /**
     * 登录
     *
     * @param loginName
     * @param passwordMD5
     * @return
     */
    String login(String loginName, String passwordMD5);

    /**
     * 用户信息修改
     *
     * @param mallUser
     * @return
     */
    Boolean updateUserInfo(MallUserUpdateParam mallUser,String email);

    /**
     * 登出接口
     * @param userId
     * @return
     */
    Boolean logout(Long userId);



    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getMallUsersPage(PageQueryUtil pageUtil);
}
