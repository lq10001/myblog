package com.ly.blog.action;

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
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.CacheManager;


import com.ly.blog.vo.Myinfo;
import com.ly.blog.service.MyinfoService;


@IocBean
@At("/myinfo")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class MyinfoAction {

	private static final Log log = Logs.getLog(MyinfoAction.class);
	
	@Inject
	private MyinfoService myinfoService;

    @At("/")
    @Ok("beetl:/WEB-INF/blog/myinfo_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Myinfo myinfo,
                      HttpServletRequest request){

        Cnd c = new ParseObj(myinfo).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(myinfoService.listCount(c));
            request.setAttribute("list_obj", myinfoService.queryCache(c,p));
        }else{
            p.setRecordCount(myinfoService.count(c));
            request.setAttribute("list_obj", myinfoService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("myinfo", myinfo);
    }

    @At
    @Ok("beetl:/WEB-INF/blog/myinfo.html")
    public void edit(@Param("id")long id,
                      HttpServletRequest request){
        if(id == 0){
            request.setAttribute("myinfo", null);
        }else{
            request.setAttribute("myinfo", myinfoService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Myinfo myinfo){
        Object rtnObject;
        if (myinfo.getId() == null || myinfo.getId() == 0) {
            myinfo.setAdddate(new Date());
            rtnObject = myinfoService.dao().insert(myinfo);
        }else {
            myinfo.setEditdate(new Date());
            rtnObject = myinfoService.dao().updateIgnoreNull(myinfo);
        }
        CacheManager.getInstance().getCache(MyinfoService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_myinfo", true);
    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")long id)
    {
        int num =  myinfoService.delete(id);
        CacheManager.getInstance().getCache(MyinfoService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_myinfo",false);
    }

}
