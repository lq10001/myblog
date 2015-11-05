package com.ly;

import com.ly.blog.service.BlogService;
import com.ly.blog.service.BlogclassService;
import com.ly.blog.service.MyinfoService;
import com.ly.blog.vo.Blog;
import com.ly.comm.Page;
import com.ly.comm.ParseObj;
import com.ly.sys.service.InfoService;
import com.ly.sys.service.MenuService;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.sql.Criteria;
import org.nutz.dao.util.cri.SimpleCriteria;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CheckSession;

import javax.servlet.http.HttpServletRequest;

@IocBean
@At("/")
@Fail("json")
public class DataAction {
	
	private static final Log log = Logs.getLog(DataAction.class);

    @Inject
    private InfoService infoService;

    @Inject
    private BlogService blogService;

    @Inject
    private BlogclassService blogclassService;

    @Inject
    private MyinfoService myinfoService;


    @At("")
    @Ok("beetl:/WEB-INF/web/index.html")
    public void index(@Param("..")Page p,
                      HttpServletRequest request)
    {
        Condition c = Cnd.NEW().desc("id");
        p.setRecordCount(blogService.listCount(c));
        request.setAttribute("blogList", blogService.queryCache(c,p));
        request.setAttribute("blogclassList",blogclassService.queryCache(null,new Page()));
        request.setAttribute("page", p);

        request.setAttribute("menuIndex", 0);
    }

    @At
    @Ok("beetl:/WEB-INF/web/blog.html")
    public void blogInfo(@Param("id")long id,
                      HttpServletRequest request)
    {
        request.setAttribute("blog", blogService.fetch(id));
        request.setAttribute("blogclassList",blogclassService.queryCache(null,new Page()));

        request.setAttribute("menuIndex", 1);

    }

    @At
    @Ok("beetl:/WEB-INF/web/blog_list.html")
    public void blogList(@Param("..")Page p,
                         @Param("..")Blog blog,
                         HttpServletRequest request)
    {
        Cnd cnd = new ParseObj(blog).getCnd();
        if (cnd == null || cnd.equals(""))
        {
            Condition c = Cnd.NEW().desc("id");

            p.setRecordCount(blogService.listCount(c));
            request.setAttribute("blogList", blogService.queryCache(c,p));
        }else{
            Condition c = cnd.desc("id");
            p.setRecordCount(blogService.listCount(c));
            request.setAttribute("blogList", blogService.query(c,p));
        }

        request.setAttribute("blogclassList",blogclassService.queryCache(null, new Page()));
        request.setAttribute("page", p);
        request.setAttribute("menuIndex", 1);
    }

    @At
    @Ok("beetl:/WEB-INF/web/myinfo.html")
    public void works(HttpServletRequest request)
    {
        request.setAttribute("myinfo", myinfoService.fetch(1));
        request.setAttribute("blogclassList",blogclassService.queryCache(null,new Page()));
        request.setAttribute("menuIndex", 2);
    }

    @At
    @Ok("beetl:/WEB-INF/web/myinfo.html")
    public void experience(HttpServletRequest request)
    {
        request.setAttribute("myinfo", myinfoService.fetch(2));
        request.setAttribute("blogclassList",blogclassService.queryCache(null,new Page()));
        request.setAttribute("menuIndex", 3);
    }

    @At
    @Ok("beetl:/WEB-INF/web/myinfo.html")
    public void ability(HttpServletRequest request)
    {
        request.setAttribute("myinfo", myinfoService.fetch(3));
        request.setAttribute("blogclassList",blogclassService.queryCache(null,new Page()));
        request.setAttribute("menuIndex", 4);
    }

    @At
    @Ok("beetl:/WEB-INF/web/myinfo.html")
    public void aboutme(HttpServletRequest request)
    {
        request.setAttribute("myinfo", myinfoService.fetch(4));
        request.setAttribute("blogclassList",blogclassService.queryCache(null,new Page()));
        request.setAttribute("menuIndex", 5);
    }

}
