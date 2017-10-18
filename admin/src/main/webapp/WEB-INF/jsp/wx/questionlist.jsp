<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<title>童言朗读者</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/metro/easyui.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/mobile.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="${ctx}/static/jquery-easyui/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/jquery-easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/jquery-easyui/jquery.easyui.mobile.js"></script>
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
    <style type="text/css">
    .m-data{
    padding: 10px 5px;
    }
    .list-header{
    font-size: 15px;font-weight: 800;padding: 3px 0;
    }
    .list-footer{
    padding:5px 0;
    }
    .list-content{
    font-size: 14px;
    word-wrap: break-word;
    text-overflow: ellipsis;
    overflow: hidden;
    }
    .content{
    text-indent:25px;
    line-height: 18px;
    font-size: 14px;
    }
    .list-footer li{
    padding:0;
     font-size: 9px;color: gray;
     display: inline;
     border: none;
    }
    #dlg2 img{
    text-align: center;
    	height: 100px;
    	border-radius:50px;
    }
    #dlg2 p{
    text-align: left;
    margin: 3px 0;
    }
    .anum{
    float: left;
    }
    .cdate{
    float:right;
    }
    .t_inf ul{padding:0px;margin-top: 3px}
    .t_inf ul li{ display: inline-block; padding: 2px;}
    .t_inf img{
    	height: 50px;
    	border-radius:25px;
    }
    .q-content{
    font-size:16px;line-height:24px}
    footer .l-btn{
    font-size: 14px;
    padding: 10px 0px;
    width: 50%
    }
    .q-li:FIRST-CHILD {
	display: none;
}
    </style>
    <script type="text/javascript">
    var f=0;scf=true;
    $(function(){
    	loadq();
    	$(".easyui-navpanel").scroll(function(){
    		if(scf&&$(".easyui-navpanel").scrollTop()+$('.easyui-navpanel').height()>=$('.m-data').height())
    			scf=false;
    		loadq();
    		setTimeout(function() {
				scf=true;
			}, 3000);
    	});
    	$(".window-mask").click(function(){
    		$('#dlg2').dialog('close');
    	})
    	$(".t_inf img").click(function(){
    		$("#dlg2").html("<img src='"+$(this).attr("src")+"'/>"+$("#"+$(this).attr("info")).html());
    		$('#dlg2').dialog('open').dialog('center');
    	})
    });
    function loadq()
    {
    	 var data_opt = {"f":f};
    	$.getJSON("${ctx}/question/pagelist",data_opt,function(data){
    		for(var d in data)
    			appendquestion(data[d]);
    		f++;
    	})
    }
    function appendquestion(q)
    {
    	var qli=$(".q-li:first").clone();
    	$(qli).attr("qid",q.id);
    	$(qli).find(".list-header").html(q.title);
    	$(qli).find(".list-content").html(q.content);
    	$(qli).find(".anum").html(q.answercount+"回/"+q.readcount+"看");
    	$(qli).find(".cdate").html(q.createDate);
    	$(qli).click(function(){
    		window.location.href="questionview?code=${param.code}&id="+$(qli).prev().attr("qid");
    	})
    	$("#list").append(qli)
    }
    </script>
</head>
<body>

	<div class="easyui-navpanel">
        <header>
            <div class="m-toolbar">
                <div class="m-title">童言朗诵训练</div>
            </div>
        </header>
        <div class="m-data">
        <h3 style="margin: 3px 0;">欢迎您来到童言朗读练习</h3>
		<div class="content">朗诵训练与提高，是一个循序渐进的过程，应当由易到难，由浅入深，万万不可急于成。这里有专业的老师实时点评。</div>
		<div class="t_inf">
		<h4 style="margin: 10px 0 0 0;">点评老师：</h4>
		<ul><li><img src="${ctx}/static/image/tz.jpg" info="info1"></li><li><img src="${ctx}/static/image/tf.jpg"  info="info2"></li>
		<img src="${ctx}/static/image/tw.jpg"  info="info5"></li>
		<li><img src="${ctx}/static/image/tl.jpg" info="info4"></li><li><img src="${ctx}/static/image/ts.jpg"  info="info3"></li></ul>
		</div>
		<ul id="list" class="m-list">
		<li class="q-li" qid=""><div class='list-header'>标题</div>
		<div class='list-content'>内容</div>
		<ul class='list-footer'><li class="anum">回复数</li><li class="cdate"></li></div>
		</li>
		</ul>
		</div>
  <div id="dlg2" class="easyui-dialog" style="padding:20px 8px;width:80%;text-align: center;" data-options="inline:true,modal:true,closed:true,title:false"></div>
  <div id="info1">
<h3>朱芳</h3>
<p>童言艺术创始人</p>
<p>深圳著名主持人演员</p>
<p>从事少儿语言教育近十年</p>
<p>主持各种大型活动逾千场</p>
<p>参与广告拍摄、电影电视拍摄上百次</p>
  </div>
   <div id="info2">
<h3>方强</h3>
<p>广州电视台City Tv   制片人/主持人</p>
<p>栏目《新鲜天气》/《新鲜亚运》</p>
<p>广州电视台亚运特别节目《一起来更精彩》</p>
<p>CCTV-12暖春行动《圆你一个梦想》</p>
<p>主演央视器官捐献微电影《生生不息》</p>
<p>主演公益微电影《我的父亲》</p>
  </div>
   <div id="info3">
<h3>舒雅</h3>
<p>深圳广电集团主持人</p>
<p>青年影视演员</p>
<p>进修于中央戏剧学院表演系</p>
<p>参演过上百部电影电视：</p>
<p>《美人鱼》《一代宗师》《暗黑者》《日光宝盒》《整形那些事》等</p>
  </div>
   <div id="info4">
<h3>李筱婉</h3>
<p>惠州广电传媒集团综艺节目当家主持</p>
<p>少儿综艺节目《非常3Q》</p>
<p>户外闯关节目《快乐大挑战》</p>
<p>知识竞答节目《谁是赢家》</p>
<p>各界精英pk节目《技艺超群》</p>
  </div>
   <div id="info5">
<h3>文新</h3>
<p>长沙市主流大型活动特约主持人</p>
<p>长沙晚报报业集团主持人</p>
<p>湖南广电配音</p>
<p>湖南车展开幕式主持人</p>
  </div>
</body>
</html>