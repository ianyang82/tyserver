<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: warriorr
  Mail: warriorr@163.com
  QQ:283173481
  Date: 2010-6-7
  Time: 11:41:23
  To change this template use File | Settings | File Templates
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title><spring:message code="title"/></title>
</head>
<link href="<c:url value='/static/css/css.css'/>" rel="stylesheet" type="text/css">
<body style=" background-repeat: no-repeat;background-color: #9CDCF9;background-position: 0 0;" onload="loadTopWindow()">
<form method="post" action="<c:url value='/main'/>">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <table border="0" align="center" cellpadding="0" cellspacing="0" style="width:681px;margin-top:120px">
        <tr>
            <td width="353" height="259" align="center" valign="bottom" style="background:url('<c:url value='/static/image/login_1.gif'/>')">
                <span style="color:#05B8E4">Power by <a href="http://www.tcl.com" target="_blank">TCL</a> Copyright 2017</span>
            </td>
            <td width="195" style="background:url('<c:url value='/static/image/login_2.gif'/>')">
                <table width="190" border="0" align="center" cellpadding="2" cellspacing="0">
                    <tr>
                        <td height="50" colspan="2" align="center" style="color: red">
                            <c:if test="${error!=null}">
                                <spring:message code="${error}"/>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td width="60" height="30" align="left" style="color: #007AB5"><spring:message code="user.username"/></td>
                        <td><input name="username" type="text"
                                   style="background:url('<c:url value='/static/image/login_6.gif'/>') repeat-x; border:solid 1px #27B3FE; height:20px; background-color:#FFFFFF"
                                   size="14"></td>
                    </tr>
                    <tr>
                        <td height="30" align="left" style="color: #007AB5"><spring:message code="user.password"/></td>
                        <td><input name="passwd" TYPE="password"
                                   style="background:url('<c:url value='/static/image/login_6.gif'/>') repeat-x; border:solid 1px #27B3FE; height:20px; background-color:#FFFFFF"
                                   size="14"></td>
                    </tr>
                    <tr>
                        <td height="40" colspan="2" align="center" style="color: red"><img src="<c:url value='/static/image/tip.gif'/>" width="16" height="16">
                            <spring:message code="login.hint"/>
                        </td>
                    <tr>
                        <td colspan="2" align="center">
                            <input type="submit" style="width: 70px;height: 25px;  border: 0;cursor: pointer;background: url('<c:url value='/static/image/login_5.gif'/>') no-repeat;color: #007AB5;"
                                   value="<spring:message code="login.login"/>">
                            <input type="reset" style="width: 70px;height: 25px;  border: 0;cursor: pointer;background: url('<c:url value='/static/image/login_5.gif'/>') no-repeat;color: #007AB5;"
                                   value="<spring:message code="cancel"/>"></td>
                    <tr>
                        <td height="5" colspan="2"></td>
                </table>
            </td>
            <td width="133" style="background:url('<c:url value='/static/image/login_3.gif'/>')">&nbsp;</td>
        </tr>
        <tr>
            <td height="161" colspan="3" style="background:url('<c:url value='/static/image/login_4.gif'/>')">
                <div style="width: 600px;margin-left: auto">
                    Languages:
                    <a href="language?type=en">English</a>
                    <a href="language?type=zh_CN">Chinese (Simplified)</a>
                </div>
            </td>
        </tr>
    </table>
</form>
</body>
<script type="text/javascript" language="javascript">
    document.onkeydown = function (event) {
        var e = event || window.event;
        var keyCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
        if (keyCode == 0x0D) {
            return logging();
        }
    };
    function loadTopWindow(){
        if (window.top!=null && window.top.document.URL!=document.URL){
            window.top.location= document.URL; //这样就可以让登陆窗口显示在整个窗口了
        }
    }
</script>
</html>