<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  wxuser: warriorr
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
            var url = "<c:url value='/wxuser/list'/>";
            var data_opt = {queryParams: {"search.delFlag_eq": "0"}};//只显示没有被删掉的问题
            baseGrid(url, data_opt);
        }
	function showhead(d,r)
	{
		return "<img class='head_img' src='"+d+"'>";
	}
	function showtype(d,r)
	{
		if(d==0)return "普通用户";else if(d==1) return "老师"; else return "管理员";		
	}
    </script>
</head>
<body>
<div id="search_form">
    <form style="margin:10px;">
        <security:authorize url="/wxuser/save">
            <a href="#" onclick="updaterow2('<c:url value='/wxuser/update'/>');" class="easyui-linkbutton" iconCls="icon-edit"><spring:message code="view"/></a>
        </security:authorize>
        <a href="#" onclick="deleterows('<c:url value='/wxuser/del'/>');" class="easyui-linkbutton" iconCls="icon-remove"><spring:message code="delete"/></a>
       	昵称：<input name="search_usernickname_like" style="width: 200px">
       	姓名：<input name="search_name_like" style="width: 200px">
        <input type="hidden" name="search.delFlag_eq" value="0">
        <spring:message code="type"/>：
        <select name="search.type_eq" style="width:190px;">
            <option value="0">普通用户</option>
            <option value="1">老师</option><option value="9">管理员</option>
        </select>
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
            <th data-options="field:'nickname',width:'10%'">昵称</th>
            <th data-options="field:'headurl',width:'10%',formatter:showhead">头像</th>
             <th data-options="field:'type',width:'20%',formatter:showtype">类型</th>
            <th data-options="field:'name',width:'20%',align:'right'">姓名</th>
            <th data-options="field:'loginTime',width:'20%',align:'right'">最后登录时间</th>
        </tr>
        </thead>
    </table>
</div>
</body>
</html>


