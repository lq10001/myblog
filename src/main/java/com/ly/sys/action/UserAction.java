package com.ly.sys.action;

import com.ly.comm.Bjui;
import com.ly.comm.Page;
import com.ly.comm.ParseObj;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CheckSession;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.CacheManager;


import com.ly.sys.vo.User;
import com.ly.sys.service.UserService;


@IocBean
@At("/user")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class UserAction {

	private static final Log log = Logs.getLog(UserAction.class);
	
	@Inject
	private UserService userService;

    @At("/")
    @Ok("beetl:/WEB-INF/sys/user_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")User user,
                      HttpServletRequest request){

        Cnd c = new ParseObj(user).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(userService.listCount(c));
            request.setAttribute("list_obj", userService.queryCache(c,p));
        }else{
            p.setRecordCount(userService.count(c));
            request.setAttribute("list_obj", userService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("user", user);
    }

    @At
    @Ok("beetl:/WEB-INF/sys/user.html")
    public void edit(@Param("action")int action,
                     @Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("user", null);
        }else{

            User user = userService.fetch(id);
            request.setAttribute("user", user);
        }
        request.setAttribute("action", action);
    }

    @At
    @Ok("json")
    public Map<String,String> save(@Param("action")int action,
                                @Param("..")User user){
        Object rtnObject;
        if (user.getId() == null || user.getId() == 0) {
            rtnObject = userService.dao().insert(user);
        }else{
            if (action == 3) {
                user.setId(null);
                rtnObject = userService.dao().insert(user);
            }else{
                rtnObject = userService.dao().updateIgnoreNull(user);
            }
        }
        CacheManager.getInstance().getCache(UserService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_user", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  userService.delete(id);
        CacheManager.getInstance().getCache(UserService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_user",false);
    }

}
