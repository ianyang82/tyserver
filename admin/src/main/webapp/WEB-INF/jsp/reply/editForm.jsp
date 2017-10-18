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
<%@ include file="../include.jspf" %>
<script type="text/javascript" language="javascript">
    $(function () {
    	$("#editForm").form("load",{id:'${m.id}',parentId:'${m.parentId}',name:'${m.name}',url:'${m.url}',type:'${m.type}',isShowMenu:'${m.isShowMenu}'});
        loadwxmenuList();
    });

    //获取所有资源，显示出来
    function loadwxmenuList() {
        $("#wxmenuTree").tree({
            url: "<c:url value='/wxmenu/resList'/>",
            animate: true,
            checkbox: true,
            cascadeCheck: false,
            // 实现单选
            onSelect: function (node) {
                var cknodes = $('#wxmenuTree').tree("getChecked");
                for (var i = 0; i < cknodes.length; i++) {
                    if (cknodes[i].id != node.id) {
                        $('#wxmenuTree').tree("uncheck", cknodes[i].target);
                    }
                }
                if (node.checked) {
                    $('#wxmenuTree').tree('uncheck', node.target);

                } else {
                    $('#wxmenuTree').tree('check', node.target);
                }

            },
            onLoadSuccess: function (node, data) {
                $(this).find('span.tree-checkbox').unbind().click(function () {
                    $('#wxmenuTree').tree('select', $(this).parent());
                    return false;
                });
                var node = $("#wxmenuTree").tree('find', '${m.parentId}');
                $("#wxmenuTree").tree('check', node.target);
            }
        });
    }
    function closeWindow() {
    	parent.closetabs();
    }
    // 保存
    function save(){
        var nodes = $('#wxmenuTree').tree('getChecked');
        if(nodes.length != 0){
            $("#parentId").val(nodes[0].id);
        }
        saveobj('<c:url value='/wxmenu/save'/>');
    }
</script>
<div class="panel">
    <div class="easyui-layout" style="width: 100%;height: 100%">
        <div region="north" style="height: 80%;border: none" >
        <div class="easyui-layout"  style="width: 100%;height: 100%">
            <div region="west" title="请选择父资源" split="true" style="width: 30%;height: 100%">
            <div id="wxmenuTree"></div>
        	</div>
        	
        	<div  region="east" title="请选择父资源" split="true" style="width:70%;height: 100%">
        	<form id="editForm" method="post" data-options="validate:true">
                <input id="selectedId" name="id" type="hidden"/>
                <input id="parentId" name="parentId" type="hidden" data-options="required:true"/>
                <table>
                    <tr>
                        <td><spring:message code="name"/></td>
                        <td><input name="name" class="easyui-textbox" data-options="required:true" /></td>
                    </tr>
                    <tr>
                        <td>type</td>
                        <td><select name="type" style="width: 200px" class="easyui-combobox"><option></option>
                        <option>click</option><option>view</option><option>media_id</option><option>view_limited</option><option>scancode_push</option>
                        <option>scancode_waitmsg</option><option>pic_sysphoto</option><option>pic_photo_or_album</option>
                        <option>pic_weixin</option><option>location_select</option>
                        </select></td>
                    </tr>
                    <tr>
                    <td>URL</td>
                    <td> <input name="url" style="width: 300px;" class="easyui-textbox"/></td>
                    </tr>
                    <tr>
                        <td><spring:message code="ishow"/></td>
                        <td><select name="isShowMenu">
                            <option value="1"><spring:message code="yes"/></option>
                            <option value="0"><spring:message code="no"/></option>
                        </select></td>
                    </tr>
                </table>
               
            </form>
        	</div>
        	</div>
        </div>
        
        <div style="text-align:center;padding:5px; border: none" region="south">
            <button type="button" class="easyui-linkbutton" onclick="save()"><spring:message code="submit"/></button>
            <button type="button" class="easyui-linkbutton" onclick="closeWindow()"><spring:message code="back"/></button>
        </div>
    </div>
</div>


