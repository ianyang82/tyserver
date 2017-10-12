package tv.huan.master.controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import tv.huan.master.common.model.MyResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2017/3/23 0023
 * Time: 16:33
 * To change this template use File | Settings | File Templates
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public void exception(Exception exception, HttpServletRequest request,
                          HttpServletResponse response) {
        logger.error(exception.getMessage());
        MyResponse myResponse = new MyResponse();
        myResponse.setError(1);
        myResponse.setMsg(exception.getMessage());
        response.setCharacterEncoding("UTF-8");
        try {
            response.setContentType("application/json");
            response.getWriter().print(JSON.toJSONString(myResponse));
            response.getWriter().close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}