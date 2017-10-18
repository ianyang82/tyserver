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
$().load(showdata());

    function showdata() {
    	var url="<c:url value='/comment/list'/>";
        var data_opt = {rownumbers: false, pagination: false, queryParams: {"search.delFlag_eq": "0","search.answerid_eq":"${m.id}"}};
        baseGrid(url, data_opt);
    }
    function save() {
        var form = $('#ceditForm');
        if (!form.form('validate')) {
            return;
        }
        $.ajax({
            url: "<c:url value='/comment/save'/>",
            type: 'POST',
            data: form.serialize(),
            dataType: "json",
            success: function (result) {
            	console.debug(result)
                if (result.error != 0) {
                    var errors = result.data;
                    for (var o in result.data) {
                        $("[name='" + errors[o].field + "']").parent().after('<span style="color: red">' + errors[o].defaultMessage + '</span>');
                    }
                    $.messager.alert(messageStrings.hint, result.msg);
                    
                } else {
                	showdata();
                }
            },
            error: function (XMLHttpRequest) {
                console.error(XMLHttpRequest);
                $.messager.alert("系统错误", XMLHttpRequest.responseText);
            }
        });
    }
</script>
<div class="panel">
    <div class="easyui-layout" style="width: 600px; height: 500px;">
        <div region="north" style="height:200px;">
                <table style="margin: 0 auto; width: 400px;">
                    <tr>
                    	<td>昵称</td>
                        <td> ${m.usernickname}</td>
                    </tr>
                    <tr>
                        <td>回答</td>
                        <td><input name="content" class="easyui-textbox"  value="${m.content}"
                        			multiline="true" data-options="required:true" readonly="readonly" style="width:300px;height:60px"/></td>
                    </tr>
                    <tr>
                        <td>语音</td>
                        <td><audio src="${m.vurl}" controls="controls">浏览器不支持播放音频.</audio></td>
                    </tr>
                </table>
                <form id="ceditForm" method="post" data-options="validate:true"><table style="margin: 0 auto; width: 400px;">
                <input name="answerid" type="hidden" value="${m.id}">
                 <tr>
                        <td>点评</td>
                        <td>
                        <input name="content" class="easyui-textbox" multiline="true" style="width:300px;height:60px"/></td>
                    </tr></table>
                    </form>
           
        </div>
		<div region="south" title=""  style="height:50px;">
            <div style="text-align: center; padding: 5px">
                <button type="button" class="easyui-linkbutton" onclick="save()"><spring:message code="submit"/></button>
                <button type="button" class="easyui-linkbutton" onclick="closeWindow()"><spring:message code="back"/></button>
            </div>
        </div>

         <div region="center" title="评论"  style="height:250px;">
 <a href="#" onclick="deleterows('<c:url value='/comment/del'/>');" class="easyui-linkbutton" iconCls="icon-remove"><spring:message code="delete"/></a>
            <table id="dg">
                <thead>
                <tr>
                    <th data-options="field:'ck',checkbox:true,width:'15%'"></th>
                    <th data-options="field:'usernickname',width:'15%'">昵称</th>
                    <th data-options="field:'content',width:'50%',align:'right'">内容</th>
                    <th data-options="field:'updateDate',width:'30%',align:'right'">发表时间</th>
                </tr>
                </thead>
            </table>
        </div> 

        
    </div>
</div>

