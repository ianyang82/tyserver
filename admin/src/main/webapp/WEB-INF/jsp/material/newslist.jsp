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
    height:50px;
    }
    .news_ul{
 	list-style: none;   
 	padding-top: 0px;
    }
    .news_ul li{
    border-bottom: 1px solid #eee;
    }
    </style>
    <script type="text/javascript" language="javascript">
    function shownews(val,row){
    	var json=eval('('+val+')'); 
    	var s="<ul class='news_ul'>"
    	for(var d in json.news_item)
    	{
    		s=s+"<li>"+json.news_item[d].title
    		s=s+"<img class='wximg' imgid='"+json.news_item[d].thumb_media_id+"'/>"
    		s=s+"</li>"
    	}
    	return s+"</ul>";
    }
    
        $(function () {
            loadGrid();
        });
        function loadGrid() {
            var url = "<c:url value='/material/list'/>";
            var data_opt = {queryParams: {"search.type_eq": "news"},onLoadSuccess:function(){
            	$(".wximg").each(function(){ var d=this;
            		$.get("image/"+$(d).attr("imgid"),function(data){$(d).attr("src",data)});
            		
            	})
            	
            }};//只显示没有被删掉的问题
            baseGrid(url, data_opt);
        }
		function syncserver(){
			$.get("snycserver?type=news",function(data){
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
            <th data-options="field:'content',width:'40%',formatter:shownews">内容</th>
             <th data-options="field:'media_id',width:'30%'">media_id</th>
        </tr>
        </thead>
    </table>
</div>
</body>
</html>


