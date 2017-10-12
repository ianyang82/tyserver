package tv.huan.master.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2017/3/23 0023
 * Time: 16:33
 * To change this template use File | Settings | File Templates
 */
//@ControllerAdvice
public class ExceptionHandlerAdvice {
    private Logger logger = LoggerFactory.getLogger(getClass());

//    @ExceptionHandler(value = Exception.class)
    public void exception(Exception exception, HttpServletRequest request,
                          HttpServletResponse response) {
        try {
            // 判断是否 Ajax 请求
            if ((request.getHeader("accept").contains("application/json") ||
                    (request.getHeader("X-Requested-With") != null &&request.getHeader("X-Requested-With").contains("XMLHttpRequest")))) {

                response.setContentType("text/html;charset=UTF-8");
                response.setStatus(500);
                response.setCharacterEncoding("UTF-8");
                response.getWriter().print(exception.getMessage());
                response.getWriter().close();
            } else {
                response.getWriter().print(exception.getMessage());
                response.getWriter().close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }
}