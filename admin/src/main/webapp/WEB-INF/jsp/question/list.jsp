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

            var url = "<c:url value='/question/list'/>";
            var data_opt = {queryParams: {"search.delFlag_eq": "0"}};//只显示没有被删掉的问题
            baseGrid(url, data_opt);
        }

    </script>
</head>
<body>
<div id="search_form">
    <form style="margin:10px;">
        <security:authorize url="/question/create">
            <a href="#" onclick="addrow('<c:url value='/question/create'/>');" class="easyui-linkbutton" iconCls="icon-add"><spring:message code="create"/></a>
        </security:authorize>
        <security:authorize url="/question/save">
            <a href="#" onclick="updaterow2('<c:url value='/question/update'/>');" class="easyui-linkbutton" iconCls="icon-edit"><spring:message code="update"/></a>
        </security:authorize>
        <a href="#" onclick="deleterows('<c:url value='/question/del'/>');" class="easyui-linkbutton" iconCls="icon-remove"><spring:message code="delete"/></a>
       	标题：<input name="search_title_like" style="width: 200px">
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
            <th data-options="field:'ck',checkbox:true"></th>
            <th data-options="field:'id',width:'10%',hidden:true">id</th>
            <th data-options="field:'title',width:'10%'">标题</th>
            <th data-options="field:'readcount',width:'10%'">阅读量</th>
            <th data-options="field:'answercount',width:'20%',align:'right'">回复量</th>
            <th data-options="field:'createDate',width:'20%',align:'right'"><spring:message code="createTime"/></th>
            <th data-options="field:'updateDate',width:'20%',align:'right'"><spring:message code="updateTime"/></th>
        </tr>
        </thead>
    </table>
</div>
</body>
</html>


