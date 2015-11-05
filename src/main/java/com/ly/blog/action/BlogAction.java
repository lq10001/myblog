package com.ly.blog.action;

import com.ly.blog.service.BlogclassService;
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
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.CacheManager;


import com.ly.blog.vo.Blog;
import com.ly.blog.service.BlogService;


@IocBean
@At("/blog")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class BlogAction {

	private static final Log log = Logs.getLog(BlogAction.class);
	
	@Inject
	private BlogService blogService;

    @Inject
    private BlogclassService blogclassService;

    @At("/")
    @Ok("beetl:/WEB-INF/blog/blog_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Blog blog,
                      HttpServletRequest request){

        Cnd c = new ParseObj(blog).getCnd();
        if (c == null || c.equals(""))
        {
            c = Cnd.NEW();
            p.setRecordCount(blogService.listCount(c));
            request.setAttribute("list_obj", blogService.queryCache(c.desc("id"),p));
        }else{
            p.setRecordCount(blogService.count(c));
            request.setAttribute("list_obj", blogService.query(c.desc("id"),p));
        }

        request.setAttribute("blogclassList",blogclassService.queryCache(null,new Page()));
        request.setAttribute("page", p);
        request.setAttribute("blog", blog);
    }

    @At
    @Ok("beetl:/WEB-INF/blog/blog.html")
    public void edit(@Param("id")long id,
                      HttpServletRequest request){
        if(id == 0){
            request.setAttribute("blog", null);
        }else{
            request.setAttribute("blog", blogService.fetch(id));
        }
        Blog blog = blogService.fetch(id);
        request.setAttribute("blogclassList",blogclassService.queryCache(null,new Page()));
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Blog blog,
                                    HttpSession session){
        Object rtnObject;
        if (blog.getId() == null || blog.getId() == 0) {
            blog.setAdddate(new Date());
            rtnObject = blogService.dao().insert(blog);
        }else{
            blog.setEditdate(new Date());
            rtnObject = blogService.dao().updateIgnoreNull(blog);
        }
        blog.setUserid(Long.parseLong(session.getAttribute("userid").toString()));
        CacheManager.getInstance().getCache(BlogService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_blog", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  blogService.delete(id);
        CacheManager.getInstance().getCache(BlogService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_blog",false);
    }

}
