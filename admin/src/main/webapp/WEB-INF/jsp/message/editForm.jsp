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

    function loadGrid() {
    	var url="<c:url value='/answer/list'/>";
        var data_opt = {rownumbers: false, pagination: false, queryParams: {"search.delFlag_eq": "0","search.questionid_eq":"${m.id}"}};
        baseGrid(url, data_opt);
    }

    function save() {
        saveobj("<c:url value='/question/save'/>");
    }
</script>
<div class="panel">
    <div class="easyui-layout" style="width: 600px; height: 500px;">
        <div region="north" style="height:200px;">
            <form id="editForm" method="post" data-options="validate:true">
                <input id="selectedId" name="id" value="${m.id}" type="hidden">
                <table style="margin: 0 auto; width: 300px;">
                    <tr>
                        <td>标题</td>
                        <td><input name="title" value="${m.title}" class="easyui-textbox"
                                   data-options="required:true"/></td>
                    </tr>
                    <tr>
                        <td>内容</td>
                        <td><input name="content" class="easyui-textbox" value="${m.content}"
                        			multiline="true" data-options="required:true" style="width:300px;height:100px"/></td>
                    </tr>
                    <tr>
                        <td>标注</td>
                        <td><input name="note" multiline="true"  value="${m.note}" class="easyui-textbox"/></td>
                    </tr>
                </table>
            </form>
        </div>
<div region="south" title=""  style="height:50px;">
            <div style="text-align: center; padding: 5px">
                <button type="button" class="easyui-linkbutton" onclick="save()"><spring:message code="submit"/></button>
                <button type="button" class="easyui-linkbutton" onclick="closeWindow()"><spring:message code="back"/></button>
            </div>
        </div>

        <div region="center" title="回答"  style="height:250px;">
 <a href="#" onclick="deleterows('<c:url value='/answer/del'/>');" class="easyui-linkbutton" iconCls="icon-remove"><spring:message code="delete"/></a>
            <table id="dg">
                <thead>
                <tr>
                    <th data-options="field:'ck',checkbox:true"></th>
                    <th data-options="field:'id',width:'10%'"><spring:message code="code"/></th>
                    <th data-options="field:'usernickname',width:'20%'">回复人</th>
                    <th data-options="field:'createDate',width:'30%',align:'right'"><spring:message code="createTime"/></th>
                    <th data-options="field:'updateDate',width:'30%',align:'right'"><spring:message code="updateTime"/></th>
                </tr>
                </thead>
            </table>
        </div>

        
    </div>
</div>

