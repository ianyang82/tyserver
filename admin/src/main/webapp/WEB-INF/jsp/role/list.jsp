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
              var url = "<c:url value='/role/list'/>";
            var data_opt = {queryParams: {"search.delFlag_eq": "0"}};
            baseGrid(url, data_opt);
        }
    </script>
</head>
<body>
<div id="search_form">
    <form style="margin:10px;">
        <a href="#" onclick="addrow('<c:url value='/role/create'/>');" class="easyui-linkbutton" iconCls="icon-add"><spring:message code="create"/></a>
        <a href="#" onclick="updaterow('<c:url value='/role/update'/>');" class="easyui-linkbutton" iconCls="icon-edit"><spring:message code="update"/></a>
        <a href="#" onclick="deleterows('<c:url value='/role/del'/>');" class="easyui-linkbutton" iconCls="icon-remove"><spring:message code="delete"/></a>
        <spring:message code="name"/>：<input name="search_name_like" style="width: 200px">
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
            <th data-options="field:'name',width:'10%'"><spring:message code="name"/></th>
            <th data-options="field:'createDate',width:'20%',align:'right'"><spring:message code="createTime"/></th>
            <th data-options="field:'updateDate',width:'20%',align:'right'"><spring:message code="updateTime"/></th>
        </tr>
        </thead>
    </table>
</div>
</body>
</html>


