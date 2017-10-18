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
$(function(){
	$("#ceditForm").form('load', {id:'${m.id}',name:'${m.name}',type:'${m.type}'});
});
    function save() {
    	saveobj("<c:url value='/wxuser/save'/>","ceditForm");
    }
</script>
<div class="panel">
    <div class="easyui-layout" style="width: 600px; height: 500px;">
        <div region="north" style="height:400px;">
                <form id="ceditForm" method="post" data-options="validate:true">
                <input name="id">
                <table style="margin: 0 auto; width: 400px;">
                    <tr>
                    	<td>昵称</td>
                        <td> ${m.nickname}</td>
                    </tr>
                    <tr>
                        <td>头像</td>
                        <td><img src="${m.headurl}"/></td>
                    </tr>
                    <tr>
                        <td>最近登录时间</td>
                        <td>${m.loginTime}</td>
                    </tr>
                    <tr><td>姓名</td><td><input name="name"/></td></tr>
                 <tr>
                        <td>类型</td>
                        <td><select name="type"><option value="0">普通用户</option>
            <option value="1">老师</option><option value="9">管理员</option></select></td>
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

    </div>
</div>

