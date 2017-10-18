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
               var url = "<c:url value='/wxmenu/list'/>";
            var data_opt = {queryParams: {"search.delFlag_eq": "0"}};
            baseGrid(url, data_opt);
        }
        function synctoserver()
        {
        	$.getJSON("synctoserver",function(data){
        		console.debug(data);
        	})
        }
      //更新
        function updatemenu(href, grid_id) {
            if (grid_id == null) {
                grid_id = "dg";
            }
            var rows = $("#" + grid_id).datagrid('getSelections');
//            console.debug(rows)
            if (rows.length == 0) {
                $.messager.alert(messageStrings.hint, messageStrings.update_hint_check, 'info');
                return;
            }
            if (rows.length > 1) {
                $.messager.alert(messageStrings.hint, messageStrings.update_hint_check_one, 'info');
                return;
            }
            var title="修改微信菜单";
            parent.setMain(title,href+"?id="+rows[0].id);
        }

      
    </script>
</head>
<body>
<div id="search_form">
    <form style="margin:10px;">
        <a href="#" onclick="addrow('<c:url value='/wxmenu/create'/>');" class="easyui-linkbutton" iconCls="icon-add"><spring:message code="create"/></a>
        <a href="#" onclick="updatemenu('/wxmenu/update');" class="easyui-linkbutton" iconCls="icon-edit"><spring:message code="update"/></a>
        <a href="#" onclick="deleterows('<c:url value='/wxmenu/del'/>');" class="easyui-linkbutton" iconCls="icon-remove"><spring:message code="delete"/></a>
        <a href="#" onclick="synctoserver();" class="easyui-linkbutton" iconCls="icon-ok">更新微信</a>
        <spring:message code="state"/>：
        <select name="search.delFlag_eq" style="width:190px;">
            <option value="0"><spring:message code="normal"/></option>
            <option value="1"><spring:message code="deled"/></option>
        </select>
        <a href="#" onclick="searchData(this);" class="easyui-linkbutton" iconCls="icon-search"><spring:message code="search"/></a>
    </form>
</div>
<div style="height: 100%;">
    <table id="dg" title="<spring:message code="list"/>">
        <thead>
        <tr>
            <th data-options="field:'ck',checkbox:true"></th>
            <th data-options="field:'id',width:'10%'">id</th>
            <th data-options="field:'name',width:'10%'"><spring:message code="name"/></th>
            <th data-options="field:'en',width:'10%'">en</th>
            <th data-options="field:'url',width:'10%'">url</th>
            <th data-options="field:'parentId',width:'5%',align:'right'">parent</th>
            <th data-options="field:'createDate',width:'20%',align:'right'"><spring:message code="createTime"/></th>
            <th data-options="field:'updateDate',width:'20%',align:'right'"><spring:message code="updateTime"/></th>
        </tr>
        </thead>
    </table>
</div>
</body>
</html>


