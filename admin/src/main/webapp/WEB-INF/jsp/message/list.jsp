<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  question: warriorr
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

            var url = "<c:url value='/message/list'/>";
            var data_opt = {queryParams: {"search.delFlag_eq": "0"}};//只显示没有被删掉的问题
            baseGrid(url, data_opt);
        }

    </script>
</head>
<body>
<div id="search_form">
    <form style="margin:10px;">
       	回复状态：<select name="search.status_eq" style="width:190px;">
            <option value="0">待回复</option>
            <option value="1">已回复</option>
            <option value="2">已发送</option>
        </select>
        <spring:message code="state"/>：
        <select name="search.delFlag_eq" style="width:190px;">
            <option value="0"><spring:message code="normal"/></option>
            <option value="1"><spring:message code="deled"/></option>
        </select>
        <a href="#" onclick="clearForm(this);" class="easyui-linkbutton" iconCls="icon-reload"><spring:message code="clear"/></a>
        <a href="#" onclick="searchData(this);" class="easyui-linkbutton" iconCls="icon-search"><spring:message code="search"/></a>
    </form>
</div>
<div style="height: 100%;">
    <table id="dg" title="<spring:message code="list"/>">
        <thead>
        <tr>
            <th data-options="field:'id',width:'10%',hidden:true">id</th>
            <th data-options="field:'type',width:'10%'">类型</th>
            <th data-options="field:'content',width:'40%'">内容</th>
            <th data-options="field:'reply',width:'30%'">回复</th>
            <th data-options="field:'createDate',width:'20%',align:'right'"><spring:message code="createTime"/></th>
        </tr>
        </thead>
    </table>
</div>
</body>
</html>


