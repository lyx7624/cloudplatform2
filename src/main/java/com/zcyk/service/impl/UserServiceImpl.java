package com.zcyk.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zcyk.dto.LoginUser;
import com.zcyk.dto.LoginUserMap;
import com.zcyk.dto.ResultData;
import com.zcyk.entity.User;
import com.zcyk.dao.UserDao;
import com.zcyk.myenum.ResultCode;
import com.zcyk.service.UserService;
import com.zcyk.util.JwtUtil;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 用户信息表
(User)表服务实现类
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    static Long EXPIRE_SECONDS = 10000000000l;



    /**
     * 功能描述：判断用户状态
     * 开发人员： lyx
     * 创建时间： 2019/7/22 17:54
     * 参数： [user]
     * 返回值： com.zcyk.dto.ResultData
     */
    public Integer judgeUser(User user){
        User nowUser = getByAccount(user.getAccount());
        if (nowUser != null) {//不考虑已被邀请就退出企业（状态为0且没有企业），那样只能自己进入企业再退出

            if (nowUser.getStatus() != 0) {//判断是是否加入了企业
                return  204;//已注册
            } else {//(nowUser.getStatus() == 0)
                return 201;//账号已注销
            }

        }
        return  404;//没有注册

    }

    /**
     * 功能描述：超管登录
     * 开发人员：Wujiefeng
     * 创建时间：2020/5/25 14:08
     * 参数：[ * @param null]
     * 返回值：
    */
    public ResultData supperManagerLogin(User user,HttpServletResponse response){
        User nowUser = getByAccount(user.getAccount());
        if(nowUser!=null){
            if(user.getPassword().equals(nowUser.getPassword())){
                LoginUser loginUser =(LoginUser) new LoginUser()
                        .setExpireTime(System.currentTimeMillis() + EXPIRE_SECONDS)
                        .setId(nowUser.getId());
                String jwtToken = JwtUtil.createJWTToken(loginUser);
                response.setHeader("token",jwtToken);
//                        //放到内存中 用于异地登录判断
                LoginUserMap.setLoginUsers(nowUser.getId(), jwtToken);

                return ResultData.WRITE(200,"登录成功",nowUser.getId());
            }else {
                return ResultData.WRITE(400,"密码错误");
            }
        }else {
            return ResultData.WRITE(400,"该账号不存在");
        }
    }

    /**
     * 功能描述：判断用户状态
     * 开发人员： lyx
     * 创建时间： 2019/7/22 17:54
     * 参数： [user]
     * 返回值： com.zcyk.dto.ResultData
     */
//    public Integer judgeUser(User user){
//        User nowUser = getByAccount(user.getAccount());
//        UserCompany userCompany;
//        if (nowUser != null) {//不考虑已被邀请就退出企业（状态为0且没有企业），那样只能自己进入企业再退出
//            userCompany = userCompanyDao.selectByUserId(nowUser.getId());
//            if (nowUser.getStatus() == 1 && null == userCompany) {//判断是是否加入了企业
//                return  204;//已注册未加入企业
//            } else if (nowUser.getStatus() == 0&& null != userCompany) {
//                return  201;//已被邀请未激活
//            }else if (nowUser.getStatus() == 0&& null == userCompany) {
//                return  203;//已被邀未激活请就退出企业
//            }else if (nowUser.getStatus() == 1&& null != userCompany) {
//                return  202;//已注册且加入了企业
//            }
//        }
//        return  404;//没有注册或被邀请
//
//    }

    public User getByAccount(String account){
           return userDao.selectByAccount(account);
    }

    /*根据账号更新用户信息*/
    public Integer updateUser(User user){
        //根据用户账号
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("account",user.getAccount());

        return userDao.updateByExampleSelective(user, example);
    }




    @Override
    public User getNowUser(HttpServletRequest request) {
//        return new User().setId("1");
        String user_token = request.getHeader("token");
        Map<String, Object> mapFromJWT = JwtUtil.getMapFromJWT(user_token);
        return userDao.selectByPrimaryKey(MapUtils.getString(mapFromJWT,"LOGIN_USER_KEY"));

    }

    @Override
    public ResultData loginByPwd(User user, HttpServletResponse response) {
        User nowUser = getByAccount(user.getAccount());
        if(nowUser==null){
            return ResultData.WRITE(400,"没有注册");
        }
        if (nowUser.getStatus()==0){
            return ResultData.WRITE(400,"该账户已注销");
        }
        if (nowUser.getStatus()==3){
            return ResultData.WRITE(400,"该账户已被冻结");
        }

//        if(user.getCode().equals(request.getSession().getAttribute("code"))){//判断验证码是否正确
        if(nowUser.getPassword().equals(user.getPassword())){//已经注册且激活，判断密码是否正确
            //保存sessionId到map中
            //放入map中
            LoginUser loginUser =(LoginUser) new LoginUser()
                    .setExpireTime(System.currentTimeMillis() + EXPIRE_SECONDS)
                    .setId(nowUser.getId());
            String jwtToken = JwtUtil.createJWTToken(loginUser);
            response.setHeader("token",jwtToken);
//                        //放到内存中 用于异地登录判断
            LoginUserMap.setLoginUsers(nowUser.getId(), jwtToken);

            return ResultData.SUCCESS(nowUser.getId());

        } else {
            return ResultData.WRITE(400, "密码错误");
        }

    }
//    @Override
//    public ResultData loginByPwd(User user, HttpServletRequest request, HttpServletResponse response) {
///*
//        Integer userStatus = judgeUser(user);
//        if (userStatus == 404) {
//            return ResultData.FAILED("没有注册");
//        }
//        User nowUser = getByAccount(user.getAccount());
//
//        if (user.getCode().equals(request.getSession().getAttribute("code"))) {//判断验证码是否正确
//                switch (userStatus) {
//                    case 404:
//                        return ResultData.FAILED("没有注册或被邀请");
//                    case 203:
//                    case 204:
//                    case 201:
//                        if (nowUser.getPassword().equals(user.getPassword())) {//登录
//                            //未注册或者未激活的状态
//                            LoginUser loginUser = (LoginUser) new LoginUser()
//                                    .setExpireTime(System.currentTimeMillis() + EXPIRE_SECONDS)
//                                    .setId(nowUser.getId());
//                            String jwtToken = JwtUtil.createJWTToken(loginUser);
//                            response.setHeader("user_token", jwtToken);
//                            //放到内存中 用于异地登录判断
//                            LoginUserMap.setLoginUsers(nowUser.getId(), jwtToken);
//
//                        }  else {
//                            return ResultData.WRITE(400, "密码错误");
//                        }
//                        return ResultData.SUCCESS(nowUser.getId());
//                    case 202:
//                        if (nowUser.getPassword().equals(user.getPassword())) {//已经注册且激活，判断密码是否正确
//                            //保存sessionId到map中
//                            //放入map中
//                            LoginUser loginUser = (LoginUser) new LoginUser()
//                                    .setExpireTime(System.currentTimeMillis() + EXPIRE_SECONDS)
//                                    .setId(nowUser.getId());
//                            String jwtToken = JwtUtil.createJWTToken(loginUser);
//                            response.setHeader("user_token", jwtToken);
////                        //放到内存中 用于异地登录判断
//                            LoginUserMap.setLoginUsers(nowUser.getId(), jwtToken);
//
//                            return ResultData.SUCCESS(nowUser.getId());
//                        } else {
//                            return ResultData.WRITE(400, "密码错误");
//                        }
//
//                }
//
//
//            } else {
//                return ResultData.WRITE(400, "验证码错误");
//            }
//*/
//
//            return ResultData.FAILED();
//
//
//    }

    @Override
    public ResultData register(User user, String code) {
        Integer userStatus = judgeUser(user);
        if(code!=null){
            if(code.equals(user.getCode())){//验证码正确
                switch (userStatus){
                    //注销账号直接恢复？？？？？
                    case 201:
                        User byAccount = getByAccount(user.getAccount());
                        if(0<userDao.updateByPrimaryKeySelective(byAccount.setPassword(user.getPassword()).setStatus(2))){
                            return  ResultData.WRITE(200,"该账号已注册，密码已重置请直接登录");
                        }
                        return ResultData.FAILED();
                    case 204:
                        return ResultData.WRITE(400,"该电话已注册,并在使用中");
                    //未注册未被邀请 ，产生新的用户
                    case 404:
                        /*手动设置用户的相关信息*/
                        // 加密
                        //user.setUser_password(new BCryptPasswordEncoder().encode(user.getUser_password().trim()));
                        user.setId(UUID.randomUUID().toString())
                                .setStatus(2)//信息未认证的状态
                                .setCreate_time(new Date());

                        if(userDao.insertSelective(user)!=0){
                            return ResultData.WRITE(200,"注册成功",userDao.selectByAccount(user.getAccount()).getId());

                        }else{
                            return ResultData.WRITE(ResultCode.ERROR);
                        }
                }
            }else{
                return ResultData.WRITE(400,"验证码错误");
            }
        }else{
            return ResultData.WRITE(400,"验证码过期");
        }
        return ResultData.FAILED();
    }


//    public ResultData register(User user) {
//        if(userDao.selectByAccount(user.getAccount())!=null){
//         return ResultData.WRITE(400,"改电话号码已注册");
//        }
//        user.setId(UUID.randomUUID().toString())
//                .setCreate_time(new Date()).setStatus(1);
//        userDao.insertSelective(user);
//
//        return ResultData.SUCCESS();
//    }


    /**
     * 功能描述：用户忘记密码，修改密码
     * 开发人员： lyx
     * 创建时间： 2019/7/24 17:04
     * 参数： [user, code]修改的用户，服务器保存的验证码
     * 返回值： com.zcyk.dto.ResultData
     */
    @Override
    public ResultData updatePwd(User user, String code){
        Integer usersStatus = judgeUser(user);//判断用户状态
        if("404".equals(usersStatus)){//未注册
            return ResultData.WRITE(400,"账号未注册");
        }else{
            if(code!=null){
                if(code.equals(user.getCode())){//验证码验证
                    if(updateUser(user)!=0){
                        return ResultData.WRITE(200,"修改成功");
                    }else {
                        return ResultData.WRITE(400,"修改失败");

                    }
                }else{
                    return ResultData.WRITE(400,"验证码错误");

                }
            }else {
                return ResultData.WRITE(400,"验证码过期");
            }
        }
    }

    @Override
    public boolean update(User user) throws Exception {
        return userDao.updateByPrimaryKeySelective(user)>0;
    }

    @Override
    public User getById(String id) {
        return userDao.selectById(id);
    }

    @Override
    public PageInfo<User> getCompanyUsers(String userId, Integer pageNum, Integer pageSize, String level, String type) {
//        UserCompany userCompany = userCompanyDao.selectByUserId(userId);
        if(null!=pageNum && null!=pageSize){
            PageHelper.startPage(pageNum,pageSize);
        }
        List<User> users = userDao.getCompanyUserOfCredential(null,level,type);
        return  new PageInfo<>(users);
    }

    @Override
    public List<User> getUnCompanyUsers() {
        return  userDao.getUnCompanyUserOfCredential();
    }

    public PageInfo<User> getDistrictUser(String area_code,int status,String search,Integer pageNum,Integer pageSize){
        if (pageNum!=null&&pageSize!=null){
            PageHelper.startPage(pageNum,pageSize);
        }
        return new PageInfo<>(userDao.getDistrictUser(area_code,status,search));
    }
}