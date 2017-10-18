<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  material: warriorr
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
    <style type="text/css">
    .wximg{
    max-height: 70px;
    max-width: 200px;
    }
    </style>
    <script type="text/javascript" language="javascript">
    function showimg(val,row){
    	return "<img class='wximg' src='"+val+"'/>"
    }
        $(function () {
            loadGrid();
        });
        function loadGrid() {
            var url = "<c:url value='/material/list'/>";
            var data_opt = {queryParams: {"search.type_eq": "${param.type}"}};//只显示没有被删掉的问题
            baseGrid(url, data_opt);
        }
		function syncserver(){
			$.get("snycserver?type=${param.type}",function(data){
				console.debug(data);
				});
		}
    </script>
</head>
<body>
<div id="search_form">
    <form style="margin:10px;">
        <a href="#" onclick="deleterows('<c:url value='/material/del'/>');" class="easyui-linkbutton" iconCls="icon-remove"><spring:message code="delete"/></a>
	<a href="#" onclick="syncserver();" class="easyui-linkbutton" iconCls="icon-remove">微信同步</a>
       	名称：<input name="search_name_like" style="width: 200px">
        
        <a href="#" onclick="clearForm(this);" class="easyui-linkbutton" iconCls="icon-reload"><spring:message code="clear"/></a>
        <a href="#" onclick="searchData(this);" class="easyui-linkbutton" iconCls="icon-search"><spring:message code="search"/></a>
    </form>
</div>
<div style="height: 100%;">
    <table id="dg" title="<spring:message code="list"/>">
        <thead>
        <tr>
            <th data-options="field:'ck',width:'10%',checkbox:true"></th>
            <th data-options="field:'id',hidden:true">id</th>
            <th data-options="field:'name',width:'20%'">名称</th>
            <c:if test="${param.type=='image'}"><th data-options="field:'url',width:'40%',formatter:showimg">图片</th></c:if>
            <c:if test="${param.type!='image'}"><th data-options="field:'url',width:'40%'">链接地址</th></c:if>
             <th data-options="field:'media_id',width:'30%',align:'right'">media_id</th>
        </tr>
        </thead>
    </table>
</div>
</body>
</html>


