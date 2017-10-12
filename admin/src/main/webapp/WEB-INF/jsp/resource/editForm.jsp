<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
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

    //获取所有资源，显示出来
    function loadResourceList() {
        $("#resourceTree").tree({
            url: "<c:url value='/resource/resList'/>",
            animate: true,
            checkbox: true,
            cascadeCheck: false,
            // 实现单选
            onSelect: function (node) {
                var cknodes = $('#resourceTree').tree("getChecked");
                for (var i = 0; i < cknodes.length; i++) {
                    if (cknodes[i].id != node.id) {
                        $('#resourceTree').tree("uncheck", cknodes[i].target);
                    }
                }
                if (node.checked) {
                    $('#resourceTree').tree('uncheck', node.target);

                } else {
                    $('#resourceTree').tree('check', node.target);

                }

            },
            onLoadSuccess: function (node, data) {
                $(this).find('span.tree-checkbox').unbind().click(function () {
                    $('#resourceTree').tree('select', $(this).parent());
                    return false;
                });
                getSelectId();
            }
        });
    }
    // 获取所选择的资源
    function getSelectId() {
        if ("${op}" == "update") {
            var id = $("#selectedId").val();
            setResourceList(id);
        }
    }
    //把该资源对应的父资源回显出来
    function setResourceList(id) {
        $.ajax({
            url: "<c:url value='/resource/findResourceById'/>",
            type: 'POST',
            data: {id: id},
            dataType:"json",
            success: function (data) {
                var node = $("#resourceTree").tree('find', data.parentId);
                $("#resourceTree").tree('check', node.target);
            },
            error: function (XMLHttpRequest) {
                $.messager.alert("系统错误", XMLHttpRequest.responseText);
            }
        });
    }

    // 保存
    function save(){
        var nodes = $('#resourceTree').tree('getChecked');
        if(nodes.length != 1){
            $.messager.alert("<spring:message code="hint"/>","请选择父级资源","info");
            return;
        }
        $("#parentId").val(nodes[0].id);
        saveobj('<c:url value='/resource/save'/>');
    }
</script>
<div class="panel">
    <div class="easyui-layout" style="width: 600px; height: 500px;">
        <div region="north" split="true" style="height: 100px;">
            <form id="editForm" method="post" data-options="validate:true">
                <input id="selectedId" name="id" type="hidden"/>
                <input id="parentId" name="parentId" type="hidden" data-options="required:true"/>
                <table>
                    <tr>
                        <td><spring:message code="name"/></td>
                        <td><input name="name" class="easyui-textbox" data-options="required:true"/></td>
                    </tr>
                    <tr>
                        <td>url</td>
                        <td><input name="url" style="width: 200px" class="easyui-textbox" data-options="required:true"/></td>
                    </tr>
                    <tr>
                        <td><spring:message code="ishow"/></td>
                        <td><select name="isShowMenu">
                            <option value="1"><spring:message code="yes"/></option>
                            <option value="0"><spring:message code="no"/></option>
                        </select></td>
                    </tr>
                    <tr>
                        <td>en</td>
                        <td><input id="en" name="en"/></td>
                    </tr>
                </table>
            </form>
        </div>
        <div region="center" title="请选择父资源" split="true" style="height: 350px;">
            <div id="resourceTree"></div>
        </div>
        <div style="text-align:center;padding:5px" region="south">
            <button type="button" class="easyui-linkbutton" onclick="save()"><spring:message code="submit"/></button>
            <button type="button" class="easyui-linkbutton" onclick="closeWindow()"><spring:message code="back"/></button>
        </div>
    </div>
</div>


