package tv.huan.master.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import tv.huan.master.service.ResourceService;
import tv.huan.master.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2014/8/12
 * Time: 14:50
 * To change this template use File | Settings | File Templates
 */
@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    CookieLocaleResolver resolver;
    @Autowired
    UserService userService;
    @Autowired
    ResourceService resourceService;

    @RequestMapping(value = "login", method = {RequestMethod.GET})
    public String login(@RequestParam(value = "error", required = false) String error, HttpServletRequest request, HttpSession session) {
//    重登陆时销毁session
        Enumeration em = session.getAttributeNames();
        while (em.hasMoreElements()) {
            session.removeAttribute(em.nextElement().toString());
        }
        if (error != null) {
            request.setAttribute("error", error);
        }
        return "login";
    }

    @RequestMapping(value = "main", method = {RequestMethod.GET, RequestMethod.POST})
    public String main() {
        return "main";
    }

    /**
     * 语言切换
     */
    @RequestMapping("language")
    public ModelAndView language(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam(value = "type", defaultValue = "zh_CN") String type) {

        switch (type) {
            case "zh_CN":
                //request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
                resolver.setLocale(request, response, Locale.CHINA);
                break;
            case "en":
                //request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
                resolver.setLocale(request, response, Locale.ENGLISH);
                break;
            default:
                //request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,LocaleContextHolder.getLocale());
                resolver.setLocale(request, response, Locale.CHINA);
                break;
        }
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/timeout")
    public void sessionTimeout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getHeader("x-requested-with") != null
                && request.getHeader("x-requested-with").equalsIgnoreCase(
                "XMLHttpRequest")) { // ajax 超时处理
            response.setStatus(500);
            response.getWriter().print("timeout");  //设置超时标识
            response.getWriter().close();
        } else {
            response.sendRedirect("login?error=login.timeout");
            response.getWriter().close();
        }
    }
}
