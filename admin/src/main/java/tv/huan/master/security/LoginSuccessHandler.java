package tv.huan.master.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import tv.huan.master.common.Constants;
import tv.huan.master.common.model.EasyUiMenuModel;
import tv.huan.master.entity.Resource;
import tv.huan.master.entity.User;
import tv.huan.master.service.ResourceService;
import tv.huan.master.service.UserService;
import tv.huan.master.util.IpUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2017/3/16 0016
 * Time: 17:43
 * To change this template use File | Settings | File Templates
 */
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    UserService userService;
    @Autowired
    ResourceService resourceService;
    private String defaultTargetUrl;

    private boolean forwardToDestination = false;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws IOException,
            ServletException {
        //获得授权后可得到用户信息   可使用SUserService进行数据库操作
        User user = (User) authentication.getPrincipal();
        List<Resource> resources = resourceService.findResourceList(user);
        List<EasyUiMenuModel> menuList = new ArrayList<EasyUiMenuModel>();
        for (Resource resource : resources) {
            if (resource.getParentId() == 0) {
                EasyUiMenuModel easyUiMenuModel = new EasyUiMenuModel();
                easyUiMenuModel.setText(resource.getName());
                easyUiMenuModel.setUrl(resource.getUrl());
                List<EasyUiMenuModel> childList = new ArrayList<EasyUiMenuModel>();
                for (Resource resource1 : resources) {
                    EasyUiMenuModel menuChildModel = new EasyUiMenuModel();
                    if (resource1.getParentId() == resource.getId() && resource1.getIsShowMenu()==1) {
                        menuChildModel.setText(resource1.getName());
                        menuChildModel.setUrl(resource1.getUrl());
                        childList.add(menuChildModel);
                    }
                }
                easyUiMenuModel.setChild(childList);
                menuList.add(easyUiMenuModel);
            }
        }
        List<String> resourceList = new ArrayList<String>();
        for (Resource resource : resources) {
            resourceList.add(resource.getUrl());
        }
        HttpSession session = request.getSession();
        session.setAttribute(Constants.SESSION_USER, user);
        session.setAttribute(Constants.SESSION_MENUS, menuList);
        session.setAttribute(Constants.SESSION_RESOURCES, resourceList);
        user.setLoginIp(IpUtils.getIpAddr(request));
        user.setLoginDate(new Date());
        userService.save(user);
        if (this.forwardToDestination) {
            logger.info("Login success,Forwarding to " + this.defaultTargetUrl);

            request.getRequestDispatcher(this.defaultTargetUrl).forward(request, response);
        } else {
            logger.info("Login success,Redirecting to " + this.defaultTargetUrl);

            this.redirectStrategy.sendRedirect(request, response, this.defaultTargetUrl);
        }
    }

    public void setDefaultTargetUrl(String defaultTargetUrl) {
        this.defaultTargetUrl = defaultTargetUrl;
    }

    public void setForwardToDestination(boolean forwardToDestination) {
        this.forwardToDestination = forwardToDestination;
    }
}
