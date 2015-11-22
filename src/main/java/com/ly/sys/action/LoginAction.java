package com.ly.sys.action;

import com.ly.sys.service.InfoService;
import com.ly.sys.service.MenuService;
import com.ly.sys.service.UserService;
import com.ly.sys.vo.User;
import com.ly.comm.EnDeCode;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@IocBean
@At("/sys")
@Fail("json")
public class LoginAction {

	private static final Log log = Logs.getLog(LoginAction.class);

	@Inject
	private MenuService menuService;

    @Inject
    private UserService userService;

    @Inject
    private InfoService infoService;


    @At("/login")
    @Ok("beetl:/WEB-INF/sys/${req_attr.page}")
    public void login(HttpServletRequest request,
                      HttpSession session,
                      @Param("username")String username,
                      @Param("password")String password)
    {
        /*
        String kaptchaExpected = (String)request.getSession().getAttribute (com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        if (!yz.trim().equals(kaptchaExpected)) {
            request.setAttribute("err", "验证码错误！请从新输入！");
            return new JspView("/WEB-INF/login.jsp");
        }
        */
        EnDeCode en = new EnDeCode();
        User user = userService.fetch(Cnd.where("name", "=", username).and("password","=",en.getMD5Str(password.trim())));

        if (user == null) {
            request.setAttribute("err", "用户名或密码错误！请从新输入！");
            request.setAttribute("page","login.html");
            log.info("用户名和密码错误");
            return;
        }
        session.setAttribute("userid",user.getId());
        session.setAttribute("username", username);

        request.setAttribute("page","index.html");

        request.setAttribute("pmenus",menuService.query(Cnd.wrap("pname = '0' and state = 1 order by ordernum"),null));
        request.setAttribute("menus",menuService.query(Cnd.wrap("pname != '0' and state = 1 order by ordernum"),null));
        request.setAttribute("info", infoService.fetch(1L));

    }
}
