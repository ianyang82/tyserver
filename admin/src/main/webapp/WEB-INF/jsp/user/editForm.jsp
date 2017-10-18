<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%-- User
  Created by IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
  Date: 2014/8/25
  Time: 10:34
  To change this template use File | Settings | File Templates.
--%>
<script type="text/javascript" language="javascript">
    $(function () {
        loadGrid();
    });

    function getSelectId() {
        var id = $("#selectedId").val();
        if (id != null) {
            setRoleList(id);
        }
    }

    function setRoleList(id) {
        $.post("<c:url value='/user/getRoleList'/>",
            {id: id},
            function (rsp) {
                $.each(rsp, function (i, e) {
                    $('#dg').datagrid('selectRecord', e.id);
                    var index = $('#dg').datagrid('getRowIndex', e.id);
                    $('#dg').datagrid('checkRow', index);
                });
            }, "JSON")
    }

    function loadGrid() {
        var url = "<c:url value='/role/list'/>";
        var data_opt = {rownumbers: false, pagination: false, queryParams: {"search.delFlag_eq": "0"}, onLoadSuccess: getSelectId};
        baseGrid(url, data_opt);
    }

    function save() {
        getSelected();
        saveobj("<c:url value='/user/save'/>");
    }

    //为新建的用户添加角色
    function getSelected() {
        var rows = $('#dg').datagrid('getSelections');
        for (var i = 0; i < rows.length; i++) {
            var input = $('<input/>');
            input.attr("name", "roleList[" + i + "].id");
            input.attr("value", rows[i].id);
            input.attr("type", "hidden");
            $('#editForm').append(input);
        }
    }
</script>
<div class="panel">
    <div class="easyui-layout" style="width: 600px; height: 500px;">
        <div region="north" style="height:200px;">
            <form id="editForm" method="post" data-options="validate:true" action="<c:url value='/user/save'/>">
                <input id="selectedId" name="id" type="hidden">
                <table style="margin: 0 auto; width: 300px;">
                    <tr>
                        <td><spring:message code="user.username"/></td>
                        <td><input name="loginName" class="easyui-textbox"
                                   data-options="required:true"/></td>
                    </tr>
                    <tr>
                        <td><spring:message code="user.password"/></td>
                        <td><input name="password" type="password" class="easyui-textbox"
                                   data-options="required:true,validType:'length[6,20]'"/></td>
                    </tr>
                    <tr>
                        <td><spring:message code="user.realName"/></td>
                        <td><input name="realName" class="easyui-textbox"
                                   data-options="required:true"/></td>
                    </tr>
                    <tr>
                        <td><spring:message code="phone"/></td>
                        <td><input name="phone" class="easyui-textbox"/></td>
                    </tr>
                    <tr>
                        <td><spring:message code="mail"/></td>
                        <td><input name="email" class="easyui-textbox"
                                   data-options="validType:'email'"/></td>
                    </tr>
                    <tr>
                        <td><spring:message code="address"/></td>
                        <td><input name="address" class="easyui-textbox"/></td>
                    </tr>
                </table>
            </form>
        </div>


        <div region="center" title="<spring:message code="user.hint"/>"  style="height:250px;">
            <table id="dg">
                <thead>
                <tr>
                    <th data-options="field:'ck',checkbox:true"></th>
                    <th data-options="field:'id',width:'10%'"><spring:message code="code"/></th>
                    <th data-options="field:'name',width:'20%'"><spring:message code="name"/></th>
                    <th data-options="field:'createDate',width:'30%',align:'right'"><spring:message code="createTime"/></th>
                    <th data-options="field:'updateDate',width:'30%',align:'right'"><spring:message code="updateTime"/></th>
                </tr>
                </thead>
            </table>
        </div>

        <div region="south" title=""  style="height:50px;">
            <div style="text-align: center; padding: 5px">
                <button type="button" class="easyui-linkbutton" onclick="save()"><spring:message code="submit"/></button>
                <button type="button" class="easyui-linkbutton" onclick="closeWindow()"><spring:message code="back"/></button>
            </div>
        </div>
    </div>
</div>

