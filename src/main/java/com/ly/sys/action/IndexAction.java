package com.ly.sys.action;

import com.ly.sys.service.InfoService;
import com.ly.sys.service.MenuService;
import org.nutz.dao.Cnd;
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
@Filters(@By(type=CheckSession.class, args={"userid", "/"}))
public class IndexAction {
	
	private static final Log log = Logs.getLog(IndexAction.class);

    @Inject
    private InfoService infoService;

    @Inject
    private MenuService menuService;

    @At("ok")
    @Ok("beetl:/WEB-INF/sys/index.html")
    public void ok(HttpServletRequest request)
    {
        request.setAttribute("pmenus",menuService.query(Cnd.wrap("pname = '0' and state = 1 order by ordernum"),null));
        request.setAttribute("menus",menuService.query(Cnd.wrap("pname != '0' and state = 1 order by ordernum"),null));
        request.setAttribute("info", infoService.fetch(1L));
    }

}
