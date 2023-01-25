
package cn.springboot.blog.api.blog;

import cn.springboot.blog.api.blog.param.MallUserLoginParam;
import cn.springboot.blog.api.blog.param.MallUserRegisterParam;
import cn.springboot.blog.api.blog.param.MallUserUpdateParam;
import cn.springboot.blog.api.blog.param.UserCheckParam;
import cn.springboot.blog.api.blog.vo.LoginResult;
import cn.springboot.blog.api.blog.vo.MallUserVO;
import cn.springboot.blog.common.Constants;
import cn.springboot.blog.config.annotation.TokenToMallUser;
import cn.springboot.blog.dao.UsersMapper;
import cn.springboot.blog.entity.UserFan;
import cn.springboot.blog.entity.Users;
import cn.springboot.blog.service.MallUserService;
import cn.springboot.blog.service.UserFansService;
import cn.springboot.blog.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import javax.annotation.Resource;
import javax.validation.Valid;


@RestController
@Api(value = "v1", tags = "用户操作相关接口")
@RequestMapping("/api/v1")
public class MallPersonalAPI {

    @Resource
    private MallUserService mallUserService;
    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    UserFansService userFansService;
    private static final Logger logger = LoggerFactory.getLogger(MallPersonalAPI.class);

    @PostMapping("/user/login")
    @ApiOperation(value = "登录接口", notes = "返回token+用户信息")
    public Result<String> login(@RequestBody @Valid MallUserLoginParam mallUserLoginParam) {
        System.out.println(mallUserLoginParam.getPasswordMd5());
        String loginResult = mallUserService.login(mallUserLoginParam.getLoginName(), mallUserLoginParam.getPasswordMd5());
        logger.info("login api,loginName={},loginResult={}", mallUserLoginParam.getLoginName(), loginResult);

        //登录成功
        if (!StringUtils.isEmpty(loginResult) && loginResult.length() == Constants.TOKEN_LENGTH) {
            Result result = ResultGenerator.genSuccessResult();
            Users user = usersMapper.selectByLoginNameAndPasswd(mallUserLoginParam.getLoginName(), mallUserLoginParam.getPasswordMd5());
            LoginResult res = new LoginResult();
            res.setInfo(user);
            res.setToken(loginResult);
            result.setData(res);
            return result;
        }
        //登录失败
        return ResultGenerator.genFailResult(loginResult);
    }


    @GetMapping("/user/login2up")
    @ApiOperation(value = "用户与up的关联修仙")
    public Result login2up(Integer upUid,Integer loginUid){
//        关注信息
        UserFan userFan;
        try {
            userFan = userFansService.checkLoginUserIsFan(upUid, loginUid);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult(e.getMessage());
        }
        Map<String,Object> map = new HashMap<>();
        map.put("fanRelation",userFan);
        return ResultGenerator.genSuccessResult(map);

    }

    @PostMapping("/user/register")
    @ApiOperation(value = "注册接口")
    public Result<String> register(@RequestBody @Valid MallUserRegisterParam mallUserLoginParam) {
        Users users = usersMapper.selectByEmail(mallUserLoginParam.getEmail());
        String loginResult = "";
        if(users!=null){
            logger.info("login api,loginName={},loginResult={}", mallUserLoginParam.getLoginName(), loginResult);
            return ResultGenerator.genFailResult("该邮箱已存在");
        }
        logger.info("login api,loginName={},loginResult={}", mallUserLoginParam.getLoginName(), loginResult);

        Users newUser = new Users();
        newUser.setPassword(mallUserLoginParam.getPassword());
        newUser.setUsername(mallUserLoginParam.getLoginName());
        newUser.setEmail(mallUserLoginParam.getEmail());
        newUser.setDescribe(mallUserLoginParam.getDescribe());
        newUser.setCardimg(mallUserLoginParam.getCardImg());
        newUser.setHead(mallUserLoginParam.getHead());
        int insert = usersMapper.insertSelective(newUser);

        if(insert!=0){
            return ResultGenerator.genSuccessResult("注册成功");
        }
        //登录失败
        return ResultGenerator.genFailResult("注册失败");
    }
    @PostMapping("/user/checkContain")
    @ApiOperation(value = "检查接口")
    public Result<String> checkContain(@RequestBody UserCheckParam userCheckParam) {
        System.out.println(userCheckParam.getLoginName());
        Users users = usersMapper.selectByEmail(userCheckParam.getLoginName());
        String loginResult = "";
        if(users!=null){
            logger.info("login api,loginName={},loginResult={}", userCheckParam.getLoginName(), loginResult);
            return ResultGenerator.genSuccessResult(users);
        }

        else{
            return ResultGenerator.genFailResult("不存在");
        }

    }


    @PostMapping("/user/logout")
    @ApiOperation(value = "登出接口", notes = "清除token")
    public Result<String> logout(@TokenToMallUser Users loginMallUser) {
        Boolean logoutResult = mallUserService.logout(loginMallUser.getId());

        logger.info("logout api,loginMallUser={}", loginMallUser.getId());

        //登出成功
        if (logoutResult) {
            return ResultGenerator.genSuccessResult();
        }
        //登出失败
        return ResultGenerator.genFailResult("logout error");
    }




    @PutMapping("/user/info")
    @ApiOperation(value = "修改用户信息", notes = "")
    public Result updateInfo(@RequestBody @ApiParam("用户信息") MallUserUpdateParam mallUserUpdateParam) {
        Boolean flag = mallUserService.updateUserInfo(mallUserUpdateParam, mallUserUpdateParam.getEmail());
        Result result;
        if (flag) {
            //返回成功
            result = ResultGenerator.genSuccessResult();
        } else {
            //返回失败
            result = ResultGenerator.genFailResult("修改失败");
        }
        return result;
    }

    @GetMapping("/user/info")
    @ApiOperation(value = "获取用户信息", notes = "")
    public Result<MallUserVO> getUserDetail(@TokenToMallUser Users loginMallUser) {
        //已登录则直接返回
        System.out.println(loginMallUser);
        return ResultGenerator.genSuccessResult(loginMallUser);
    }
    @GetMapping("/user/infoBIs")
    @ApiOperation(value = "获取用户信息", notes = "")
    public Result getUserDetailById(@RequestParam String uids,@RequestParam(defaultValue = "15") Integer limit,@RequestParam(defaultValue = "1") Integer start) {
        Map<String,Object> map = new HashMap<>();

        map.put("uids",uids.split(","));
        map.put("limit",limit);
        map.put("page",start);
        PageResult mallUsersPage = null;
        try {
            mallUsersPage = mallUserService.getMallUsersPage(new PageQueryUtil(map));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genErrorResult(500,e.getMessage());
        }
        return ResultGenerator.genSuccessResult(mallUsersPage);


    }
}
