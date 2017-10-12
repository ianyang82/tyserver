<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: warriorr
  Mail: warriorr@163.com
  QQ:283173481
  Date: 11-12-20
  Time: 上午10:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="../include.jspf" %>
    <script type="text/javascript" language="javascript">
        $(function () {
            loadGrid();
        });
        function loadGrid() {
               var url = "<c:url value='/systemlog/list'/>";
            var data_opt = {};
            baseGrid(url, data_opt);
        }
        function getType(value) {
            if (value == "1") {
                return "<spring:message code="log.action"/>";
            } else {
                return "<spring:message code="log.error"/>";
            }
        }
    </script>
</head>
<body>
<div id="search_form">
    <form style="margin:10px;">
        <spring:message code="type"/>：<select name="search_type_eq">
        <option value=""><spring:message code="all"/></option>
        <option value="1"><spring:message code="log.action"/></option>
        <option value="2"><spring:message code="log.error"/></option>
    </select>
        <spring:message code="user.username"/>：<input name="search_user.loginName_like" style="width: 200px">
        <a href="#" onclick="clearForm(this);" class="easyui-linkbutton" iconCls="icon-reload"><spring:message code="clear"/></a>
        <a href="#" onclick="searchData(this);" class="easyui-linkbutton" iconCls="icon-search"><spring:message code="search"/></a>
    </form>
</div>
<div style="height: 100%;">
    <table id="dg" title="<spring:message code="list"/>">
        <thead>
        <tr>
            <th data-options="field:'ck',checkbox:true"></th>
            <th data-options="field:'id',width:'10%',hidden:true">id</th>
            <th data-options="field:'type',width:'10%'" formatter="getType"><spring:message code="type"/></th>
            <th data-options="field:'user',width:'10%'" formatter="getUserName"><spring:message code="user.username"/></th>
            <th data-options="field:'remoteAddr',width:'10%',align:'right'">IP</th>
            <th data-options="field:'requestUri',width:'20%',align:'right'"><spring:message code="object"/></th>
            <th data-options="field:'module',width:'20%',align:'right'"><spring:message code="desc"/></th>
            <th data-options="field:'createDate',width:'10%',align:'right'"><spring:message code="createTime"/></th>
        </tr>
        </thead>
    </table>
</div>
</body>
</html>


