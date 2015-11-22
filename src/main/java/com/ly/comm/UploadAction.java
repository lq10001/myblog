package com.ly.comm;

import net.sf.ehcache.CacheManager;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CheckSession;
import org.nutz.mvc.upload.UploadAdaptor;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@IocBean
@At("/upload")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class UploadAction {

	private static final Log log = Logs.getLog(UploadAction.class);

    @At("/")
    @Ok("json")
    @AdaptBy(type = UploadAdaptor.class, args = { "ioc:uploadFile" })
    public Map<String,String> save( @Param("file1") File f1,
                                    HttpServletRequest request
    ) throws IOException {

        String webPath =  request.getServletContext().getRealPath("/");
        String appPath = webPath.substring(0,webPath.length() - 1) + "upload/";

        String[] names = appPath.split("/");
        String webname = names[names.length  - 1];


        log.debug(webPath + " ------   " +request.getServletContext().getServletContextName() +" ====  " + webname);

        String fileName = System.currentTimeMillis()+f1.getName();
        boolean isOk = false;
        if (f1 != null)
        {
            Files.copyFile(f1, new File(appPath + fileName));
            isOk = true;
        }

        Map<String, String> map = new HashMap<String, String>();
        map.put("statusCode", isOk ? "200":"300");
        map.put("message", isOk ? "上传成功!":"上传失败!");
        map.put("message", isOk ? webname + "/" + fileName : "");

        return map;
    }

}
