<%@ page import="tv.huan.master.entity.User" %>
<%--
  Created by IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
  Date: 2015/1/21
  Time: 17:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><spring:message code="title"/></title>
    <%@ include file="include.jspf" %>
    <%--<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/demo.css"/>--%>
    <script type="text/javascript" src="${ctx}/static/js/datetime.js"></script>
    <script type="text/javascript">
        function setMain(title, href) {
            href = href.substring(1);
            href = "<c:url value='/'/>" + href;//添加跟路径
            var tt = $('#tabs');
            if (tt.tabs('exists', title)) {//如果tab已经存在,则选中并刷新该tab
                tt.tabs('select', title);
                refreshTab({tabTitle: title, url: href});
            } else {
                tt.tabs('add', {
                    title: title,
                    closable: true,
                    content: '<iframe scrolling="no" frameborder="0"  src="' + href + '" style="width:100%;height:99%;"></iframe>'
                });
            }
        }
	function closetabs()
	{
		$('#tabs').tabs("close",$('#tabs').tabs('getTabIndex',$('#tabs').tabs('getSelected')));
	}
        function refreshTab(cfg) {
            var refresh_tab = cfg.tabTitle ? $('#tabs').tabs('getTab', cfg.tabTitle) : $('#tabs').tabs('getSelected');
            if (refresh_tab && refresh_tab.find('iframe').length > 0) {
                var _refresh_ifram = refresh_tab.find('iframe')[0];
                _refresh_ifram.contentWindow.location.href = cfg.url ? cfg.url : _refresh_ifram.src;
            }
        }
        $(function () {
            initMenu();
            ShowDate("clock");
        });
        function initMenu() {
            var $ma = $("#menuList");
            $ma.accordion({animate: true, fit: true, border: false});
            $.post("<c:url value='/resource/findMenuList'/>", function (rsp) {
                $.each(rsp, function (i, e) {
                    var menulist = "<div class=\"well well-small\">";
                    if (e.child && e.child.length > 0) {
                        $.each(e.child, function (ci, ce) {
                            menulist += "<a href=\"javascript:void(0);\" class=\"easyui-linkbutton\" data-options=\"plain:true\" onclick=\"setMain('" + ce.text + "','" + ce.url + "');\">" + ce.text + "</a><br/>";
                        });
                    }
                    menulist += "</div>";
                    $ma.accordion('add', {
                        title: e.text,
                        content: menulist,
                        border: false,
                        iconCls: e.iconCls,
                        selected: false
                    });
                });
            }, "JSON").error(function () {
                $.messager.alert("<spring:message code="hint"/>", "<spring:message code="main.menu.error"/>", "info", function () {
                    window.top.location.href = "<c:url value='/login'/>";
                });
            });
        }

        function logout() {
            $.messager.alert("<spring:message code="hint"/>", "<spring:message code="main.logout.confirm"/>", "info", function () {
                window.top.location.href = "<c:url value='/login'/>";
            });
        }
    </script>

    <style type="text/css">
        #menuList a.l-btn span span.l-btn-text {
            display: inline-block;
            height: 14px;
            line-height: 14px;
            margin: 5px 0px 5px 0px;
            vertical-align: baseline;
            width: 130px;
        }

        #menuList a.l-btn span span.l-btn-icon-left {
            background-position: left center;
            padding: 0px 0px 0px 0px;
        }

        #menuList .panel-body {
            padding: 5px;
        }

        #menuList span:focus {
            outline: none;
        }
    </style>
</head>
<body class="easyui-layout">
<div data-options="region:'north'">
    <div style="width: 10%;height:58px;float: left;background: url('<c:url value='/static/image/top_logo.jpg'/>');"></div>
    <%--<div style="width:90%; height:58px;float: right;background: url('<c:url value='/static/image/top_bg.jpg'/>');background-size:cover;">--%>
    <div style="width:90%; height:58px;float: right;">
        <img src="<c:url value='/static/image/top_bg.jpg'/>" style="height:100%;width:100%;position:absolute;">

        <div style="width: 90%;float:left;text-align: center;">
            <marquee id="a" onmouseover="a.stop()" onmouseout="a.start()" scrollAmount=2 direction=left width="300">
                <div align="center" style="font-size: 20px">欢迎<span
                        style="color:#ffffff;font-size: 20px "><security:authentication property="name"/></span>登陆终端管理系统<br></div>
            </marquee>
            <div id="clock" style="text-align: center;color:#ffffff;font-size: 20px;position: relative;"></div>
        </div>
        <div style="width:10%;height:100%;float:right;text-align: center;">
            <a href="#" onclick="logout();" style="height:100%;color:#ffffff;" class="easyui-linkbutton" iconCls="icon-ok"><spring:message code="main.logout"/></a>
        </div>
    </div>
</div>
<div data-options="region:'west',split:true" title="<spring:message code="main.navigation"/>" style="width:190px;">
    <div id="menuList"></div>
</div>
<div data-options="region:'center',iconCls:'icon-ok'">
    <div id="tabs" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
    </div>
</div>
<%--<div data-options="region:'east',split:true" title="East" style="width:80px;">--%>
<%--</div>--%>
<div id="southId" data-options="region:'south',split:true" style="height:30px;text-align: center">欢网科技</div>
<div id="win" modal="true" shadow="false" minimizable="false" cache="false" maximizable="false"
     collapsible="false" resizable="false" style="margin: 0px;padding: 0px;overflow: auto;"></div>

<!--      用于显示实时操作结果的页面 -->
<div id="realtimeCtrlWin" modal="false" shadow="false" minimizable="false" cache="false" maximizable="false"
     collapsible="false" resizable="false" style="margin: 0px;padding: 0px;overflow: auto;"></div>
</body>