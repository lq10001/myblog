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
import java.util.List;
import java.util.Map;

import net.sf.ehcache.CacheManager;


import com.ly.blog.vo.Blogclass;
import com.ly.blog.service.BlogclassService;


@IocBean
@At("/blogclass")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class BlogclassAction {

	private static final Log log = Logs.getLog(BlogclassAction.class);
	
	@Inject
	private BlogclassService blogclassService;

    @At("/")
    @Ok("beetl:/WEB-INF/blog/blogclass_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Blogclass blogclass,
                      HttpServletRequest request){

        Cnd c = new ParseObj(blogclass).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(blogclassService.listCount(c));
            request.setAttribute("list_obj", blogclassService.queryCache(c,p));
        }else{
            p.setRecordCount(blogclassService.count(c));
            request.setAttribute("list_obj", blogclassService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("blogclass", blogclass);
    }

    @At
    @Ok("beetl:/WEB-INF/blog/blogclass.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("blogclass", null);
        }else{
            request.setAttribute("blogclass", blogclassService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Blogclass blogclass){
        Object rtnObject;
        if (blogclass.getId() == null || blogclass.getId() == 0) {
            rtnObject = blogclassService.dao().insert(blogclass);
        }else{
            rtnObject = blogclassService.dao().updateIgnoreNull(blogclass);
        }
        CacheManager.getInstance().getCache(BlogclassService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_blogclass", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  blogclassService.delete(id);
        CacheManager.getInstance().getCache(BlogclassService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_blogclass",false);
    }

}
