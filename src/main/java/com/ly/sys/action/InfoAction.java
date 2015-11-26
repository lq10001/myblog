package com.ly.sys.action;

import com.ly.comm.Dwz;
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

import com.ly.sys.vo.Info;
import com.ly.sys.service.InfoService;


@IocBean
@At("/info")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class InfoAction {

	private static final Log log = Logs.getLog(InfoAction.class);
	
	@Inject
	private InfoService infoService;

    @At("/")
    @Ok("beetl:/WEB-INF/sys/info.html")
    public void index(HttpServletRequest request){
        request.setAttribute("info", infoService.fetch(1L));
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Info info){
        Object rtnObject;
        if (info.getId() == null || info.getId() == 0) {
            rtnObject = infoService.dao().insert(info);
        }else{
            rtnObject = infoService.dao().updateIgnoreNull(info);
        }
        return Dwz.rtnMap((rtnObject == null) ? false : true,"info", "");
    }

}
