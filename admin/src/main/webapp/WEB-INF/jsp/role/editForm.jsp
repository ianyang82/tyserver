<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%-- Role
  Created by IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
  Date: 2014/8/25
  Time: 10:34
  To change this template use File | Settings | File Templates.
--%>
<script type="text/javascript" language="javascript">
    $(function () {
        loadResourceList();
    });


    function getSelectId() {
        if ("${op}" == "update") {
            var id = $("#selectedId").val();
            setResourceList(id);
        }
    }

    //把角色已有的权限显示出来
    function setResourceList(id) {
        $.ajax({
            url: "<c:url value='/role/getResourceList'/>",
            type: 'POST',
            data: {id: id},
            dataType:"json",
            success: function (rsp) {
                $.each(rsp, function (i, e) {
                    var node = $("#resourceTree").tree('find', e.id);
                    $("#resourceTree").tree('check', node.target);
                });
            },
            error: function (XMLHttpRequest) {
                $.messager.alert("系统错误", XMLHttpRequest.responseText);
            }
        });
    }

    //获取所有权限，显示出来
    function loadResourceList() {
        $("#resourceTree").tree({
            url: "<c:url value='/resource/resList'/>",
            animate: true,
            checkbox: true,
            cascadeCheck: true,
            onLoadSuccess: function () {
                getSelectId();
            }
        });
    }

    function save() {
        getSelected();
        saveobj("<c:url value='/role/save'/>");
    }

    //为新建的角色添加权限
    function getSelected() {
        var nodes = $('#resourceTree').tree('getChecked');
        var pnodes = $('#resourceTree').tree('getChecked', 'indeterminate');
        var input;
        for (var i = 0; i < nodes.length; i++) {
            input = $('<input/>');
            input.attr("name", "resourceList[" + i + "].id");
            input.attr("value", nodes[i].id);
            input.attr("type", "hidden");
            $('#editForm').append(input);
        }
        for (var j = 0; j < pnodes.length; j++) {
            input = $('<input/>');
            input.attr("name", "resourceList[" + (parseInt(nodes.length) + parseInt(j)) + "].id");
            input.attr("value", pnodes[j].id);
            input.attr("type", "hidden");
            $('#editForm').append(input);
        }
    }
</script>
<div class="panel">
    <div class="easyui-layout" style="width: 600px; height: 500px;">
        <div region="north" split="true" style="height: 100px;">
            <form id="editForm" method="post" data-options="validate:true">
                <input id="selectedId" name="id" type="hidden">
                <table style="margin: 0 auto; width: 300px;">
                    <tr>
                        <td><spring:message code="name"/></td>
                        <td><input name="name" class="easyui-textbox"
                                   data-options="required:true"/></td>
                    </tr>
                </table>
            </form>
        </div>

        <div region="center" title="<spring:message code="role.hint"/>" split="true" style="height: 350px;">
            <div id="resourceTree"></div>
        </div>

        <div region="south" title="" split="true" style="height: 50px;">
            <div style="text-align: center; padding: 5px">
                <button type="button" class="easyui-linkbutton" onclick="save()"><spring:message code="submit"/></button>
                <button type="button" class="easyui-linkbutton"
                        onclick="closeWindow()"><spring:message code="back"/>
                </button>
            </div>
        </div>
    </div>
</div>

