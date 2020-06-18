package com.zcyk.filter;


import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {

    /**
     * 创建session时候的动作
     * @param event
     */
    @Override
    public void sessionCreated(HttpSessionEvent event) {

    }
 
    /**
     * 销毁session时候的动作
     * @param event
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
//        HttpSession session = event.getSession();
//        String sessionId = session.getId();
        //移除loginUsers中已经被销毁的session
//        LoginUserMap.removeUser(sessionId);
//        System.out.println(userMapper.selectUserById((String)session.getAttribute("user_id")).getUser_name() +"退出登录");
        }
}
