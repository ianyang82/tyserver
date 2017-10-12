package tv.huan.master.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;

import tv.huan.master.entity.SystemLog;
import tv.huan.master.entity.User;
import tv.huan.master.exception.AppException;
import tv.huan.master.service.ResourceService;
import tv.huan.master.service.SystemLogService;
import tv.huan.master.service.UserService;
import tv.huan.master.util.CommonUtil;
import tv.huan.master.util.IpUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2014/8/11
 * Time: 13:21
 * To change this template use File | Settings | File Templates
 */
public class MyInterceptor extends HandlerInterceptorAdapter {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    SystemLogService systemLogService;

    @Autowired
    ResourceService resourceService;
    @Autowired
    UserService userService;

    /**
     * 最后执行，可用于释放资源
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception ex) throws Exception {
        String requestRri = request.getRequestURI();
        if (ex != null) {
        	logger.error(ex.getMessage(),ex);
            showTraces(ex);
            // 判断是否 Ajax 请求
            if ((request.getHeader("accept").contains("application/json") ||
                (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").contains("XMLHttpRequest")))) {

            response.setContentType("text/html;charset=UTF-8");
            response.setStatus(500);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(ex.getMessage());
            response.getWriter().close();
        } else {
        	if(ex instanceof AppException)
        	{
        		response.setContentType("application/json;charset=UTF-8");
	            response.getWriter().print("{\"error\": {\"code\": \""+((AppException)ex).getCode()+"\",\"info\": \""+((AppException)ex).getInfo()+"\"}");
	            response.getWriter().close();
        	}else{
	            response.getWriter().print(ex.getMessage());
	            response.getWriter().close();
        	}
        }
    }
        if (StringUtils.endsWith(requestRri, "/save") || StringUtils.endsWith(requestRri, "/del") || ex != null) {
            User user = (User) request.getSession().getAttribute("user");
            if (user != null && user.getId() != null) {

                StringBuilder params = new StringBuilder();
                int index = 0;
                for (Object param : request.getParameterMap().keySet()) {
                    params.append((index++ == 0 ? "" : "&") + param + "=");
                    params.append(CommonUtil.abbr(StringUtils.endsWithIgnoreCase((String) param, "password")
                            ? "" : request.getParameter((String) param), 100));
                }

                SystemLog log = new SystemLog();
                log.setType(ex == null ? SystemLog.TYPE_ACCESS : SystemLog.TYPE_EXCEPTION);
                log.setUser(user);
                log.setCreateDate(new Date());
                log.setRemoteAddr(IpUtils.getIpAddr(request));
                log.setUserAgent(request.getHeader("user-agent"));
                log.setRequestUri(request.getRequestURI());
                log.setMethod(request.getMethod());
                log.setParams(params.toString());
                log.setException(ex != null ? ex.toString() : "");
                log.setModule(ex != null ? ex.getMessage() : "");
                logger.info(JSON.toJSONString(log) );
//                systemLogService.save(log);
            }
        }
    }

    /**
     * 显示视图前执行
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView view) throws Exception {
        String contextPath = request.getContextPath();
        if (view != null) {
            request.setAttribute("ctx", contextPath);
            String language = request.getAttribute("org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE").toString();
            request.setAttribute("easyui_lang", contextPath + "/static/jquery-easyui/locale/easyui-lang-" + language + ".js");
        }
    }

    /**
     * Controller之前执行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    private Throwable showTraces(Throwable t) {
        Throwable next = t.getCause();
        if (next == null) {
            return t;
        } else {
            return showTraces(next);
        }
    }
}
