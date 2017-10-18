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
    body{
    -webkit-user-select: none;user-select: none;
    }
    .list-header,.list-content{
    	display: inline-block;padding-left: 20px;
    }
    .l-btn-plain:hover{
    background:none !important;
    border:none !important;
   	padding: 1px !important;}
     .list-footer{
    	background: url("${ctx}/static/image/heart114.png") no-repeat;
    	background-size: 30px 30px;
    	background-position:10px 5px;
    	padding-left:50px;
    	min-width: 100px;
    }
    .list-footer img{
    	width: 30px;border-radius:15px;
    	height: 30px;float: left;padding-right: 3px;
    }
    #listen-btn{
    background: url("${ctx}/static/image/play86.png") no-repeat ;
  	background-size:90px 90px;
    border: none;
    height:90px;
    width: 90px;
    outline:none;
    }
    .lstop-btn{
    background: url("${ctx}/static/image/stop39.png") no-repeat !important;
  	background-size:90px 90px !important;
    }
	.hide{
    display: none;
    }
    .d-comment{padding:0 5px;background-color:#eee;margin: 5px;}
    .c-list{padding: 0;}
    .c-list li{padding: 5px;}
    .q-content{
    font-size:16px;line-height:24px;border: none;width: 100%;resize: none;outline:none}
    footer .l-btn{
    font-size: 14px;
    padding: 10px 0px;
    width: 100%
    }
    .a-headurl{
   		margin-top: 20px; 
        width:50px; 
        height:50px; 
        border-radius:50px; 
    }
    .a-date{
    float: right;
    }
    .play-btn{
  	background: url("${ctx}/static/image/play86.png") no-repeat ;
  	background-size:30px 30px;
    border: none;
    height:30px;
    width: 30px;
    outline:none;
  }
  
  .list-content{
    width: 80%;
  }
  .run-content{
    background: url("${ctx}/static/image/timg.gif") no-repeat;
    background-position: 50px -225px;
    }
   .run-content .play-btn{
  	background: url("${ctx}/static/image/stop39.png") no-repeat ;
  	background-size:30px 30px;
  }
    </style>
    <script type="text/javascript">
    jQuery.fn.extend({
        autoHeight: function(){
            return this.each(function(){
                var $this = jQuery(this);
                if( !$this.attr('_initAdjustHeight') ){
                    $this.attr('_initAdjustHeight', $this.outerHeight());
                }
                _adjustH(this).on('input', function(){
                    _adjustH(this);
                });
            });
            /**
             * 重置高度 
             * @param {Object} elem
             */
            function _adjustH(elem){
                var $obj = jQuery(elem);
                return $obj.css({height: $obj.attr('_initAdjustHeight'), 'overflow-y': 'hidden'})
                        .height( elem.scrollHeight );
            }
        }
    });
    var au=document.createElement("AUDIO");
    wx.config({
        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId: '${appid}', // 必填，公众号的唯一标识
        timestamp: '${timestamp}', // 必填，生成签名的时间戳
        nonceStr: '${noncestr}', // 必填，生成签名的随机串
        signature: '${signature}',// 必填，签名，见附录1
        jsApiList: ['checkJsApi','startRecord','stopRecord','onVoiceRecordEnd','uploadVoice','translateVoice','pauseVoice','playVoice'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
    });
    var f=0,cls,localId,id='${param.id}',scf=true,tm;
    $(function(){
    	loadques();
    	$(".easyui-navpanel").scroll(function(){
    		if(scf && $(".easyui-navpanel").scrollTop()+$('.easyui-navpanel').height()>=$('.m-data').height())
    			{scf=false;loadans();
    			setTimeout(function() {
    				scf=true;
				}, 3000);
    			}
    	});
    	$('#a-form').form({
			success:function(data){
				console.debug(data);
               	 $("#vfile").empty();
               	appendanswer(eval('(' + data + ')'),false);
			},
			type:"json",
			error: function() {
                alert("上传失败，请检查网络后重试");
            }
		});
    	$("#vfile").change(function(){
    		if($(this).val()!="")
    		{
    			$('#a-form').submit();
    		}
    	})
    	$("#vrecord").click(function(){
    		if('1'==$(this).attr('pstatus'))
    		{
    			console.debug(tm);
    			clearInterval(tm);
    			$(this).attr('pstatus','0');
    			wx.stopRecord({
        		    success: function (res) {
        		    	$('#dlg2').dialog('open').dialog('center');
        		    	localId= res.localId;
        		    }
        		});
    			$(this).html("点击开始录音");
    		}else{
    			console.debug(tm);
    			clearInterval(tm);
    			$(".run-content").removeClass("run-content")
    			au.pause();
    			$(this).html("60秒录音中，点击结束录音");
    			var i=60;
    			tm=setInterval(function(){
    				console.debug(i);
					i--;
    				if(i>0&&i<30)
    				{
    					$("#vrecord").html(i+"秒后将自动结束录音");
    				}else if(i<0)
    					return;
    			},1000);
    			$(this).attr('pstatus','1');
    			wx.startRecord();
    		}
    	})
    	$("#listen-btn").click(function(){
    		if($(this).hasClass("lstop-btn"))
    		{
    			wx.pauseVoice({
    			    localId: localId
    			});
    		}else{
    		$(this).addClass("lstop-btn");
    		wx.playVoice({
    		    localId: localId
    		});
    		}
    		
    	});
        wx.onVoiceRecordEnd({
            // 录音时间超过一分钟没有停止的时候会执行 complete 回调
            complete: function (res) {
                localId = res.localId; 
                console.debug('complete.....');
                $("#vrecord").attr('pstatus','0');
                $("#vrecord").html("点击开始录音");
                $('#dlg2').dialog('open').dialog('center');
            }
        });
    });
    function wxserverid()
    {
    	if(localId==null)
    		$('#dlg2').dialog('close');
    	else
    	wx.uploadVoice({
		    localId: localId, // 需要上传的音频的本地ID，由stopRecord接口获得
		    isShowProgressTips: 1, // 默认为1，显示进度提示
		        success: function (res) {
		        	$('#dlg2').dialog('close');
		        	downwx(res.serverId)
		    }
		});
    }
    function downwx(serverId)
    {
    	$.getJSON("${ctx}/answer/downwx?code=${param.code}&qid="+id+"&serverid="+serverId,function(data){
    		appendanswer(data,false);
        })
    }
    function loadques()
    {
    	$.ajax({url:"${ctx}/question/next",dataType: "json",data: {'id':id},success:function(data){
    		console.debug(data);
    		$("#a-form [name='qid']").val(data.id);
    		$(".q-title").html(data.title);
    		$(".q-content").html(data.content);
    		id=data.id;
    		$(".a-li").not(".hide").remove();
    		 $('textarea').autoHeight();
   			loadans();
    	},error:function(){
    		$.messager.alert("提示","已经到达结尾");
    		id=null;
    	}});
    }
    function loadans()
    {
    	 var data_opt = {"qid":id,"f":f};
    	$.getJSON("${ctx}/answer/pagelist",data_opt,function(data){
    		for(var d in data)
    			appendanswer(data[d],true);
    		f=f+data.length;
    	})
    }
    function appendanswer(q,flag){
    	var ali=$(".a-li:first").clone();
    	$(ali).removeClass("hide");
		$(ali).find('.a-headurl').attr("src",q.headurl);
		$(ali).find('.a-nick').html(q.usernickname);
		$(ali).find('.play-btn').click(function(){
    		if($(this).parent().hasClass("run-content"))
    		{
    			au.pause();
    			$(".run-content").removeClass("run-content");
    		}
    		else
    		{
    			au.src=q.vurl;
    			au.play();$(".run-content").removeClass("run-content");
    			$(this).parent().addClass("run-content");
    			var bc=$(this);
    			$(au).bind('ended',function () {
    				$(bc).parent().removeClass("run-content");
    				});
    			$(this).parent().addClass("run-content");
    		}
    	});
		$(ali).find('.a-date').html(q.createDate);
		var us=q.likeusers;
		$(ali).find(".easyui-menubutton:eq(0)").attr('lk','1');
		if(q.userid=='${user.id}')
		{
			$(ali).find(".easyui-menubutton:eq(1)").show();
			$(ali).find(".easyui-menubutton:eq(1)").click(function(){
				if(confirm("是否确认删除"))
					$.ajax({
			            url: "<c:url value='/answer/remove/'/>?code=${param.code}",
			            data: {id:q.id},
			            success: function (result) {
			            	$(ali).remove();
			            },
			            error: function (XMLHttpRequest) {
			                console.error(XMLHttpRequest);
			                $.messager.alert("系统错误", XMLHttpRequest.responseText);
			            }
			        }); 
			});
		}
		for(var u in us)
		{
			$(ali).find('.list-footer').css("min-height","30px");
			$(ali).find('.list-footer').append("<img src='"+us[u].headurl+"'>")
			if(us[u].id=='${user.id}')
				$(ali).find(".easyui-menubutton:eq(0)").attr('lk','0');
		}
		$(ali).find(".easyui-menubutton:eq(0)").click(function(){
			cls=this;
			console.debug(cls)
			$("#c-form [name='answerid']").val(q.id);
			if($(this).attr('lk')=='0') 
				$('#like_b div').html("取消赞")
			else
				$('#like_b div').html("赞")
			$('#mm1').menu('show', {
				left: $(this).offset().left,
				top: $(this).offset().top + $(this).height()
			});
		});
		var cl=$(ali).find('.c-list');
		var cs=q.comments;
		for(var c in cs)
		{
			$(cl).append("<li><span class='c-nick'></span>"+cs[c].usernickname+":<span class='c-content'>"+cs[c].content+"</span></li>");
		}
		if(flag)
			$(".m-list").append(ali);
		else
			$(".m-list").prepend(ali);
    }
    function like()
    {
    	$.ajax({
            url: "<c:url value='/answer/like/'/>"+$(cls).attr("lk")+"?code=${param.code}",
            data: {id:$("#c-form [name='answerid']").val()},
            dataType: "json",
            success: function (result) {
            	var us=result.likeusers;
            	if($(cls).attr('lk')=='1')
            		$(cls).attr('lk','0');
            	else
            		$(cls).attr('lk','1');
            	$(cls).parent().next().find('.list-footer').html("");
            	$(cls).parent().next().find('.list-footer').css("min-height","0");
        		for(var u in us)
        		{
        			$(cls).parent().next().find('.list-footer').css("min-height","30px");
        			$(cls).parent().next().find('.list-footer').append("<img src='"+us[u].headurl+"'>");
        		}
            },
            error: function (XMLHttpRequest) {
                console.error(XMLHttpRequest);
                $.messager.alert("系统错误", XMLHttpRequest.responseText);
            }
        }); 
    }
    function sendcomment()
    {
    	 $('#dlg1').dialog('close');
    	 $.ajax({
             url: "<c:url value='/comment/save'/>?code=${param.code}",
             type: 'POST',
             data: $("#c-form").serialize(),
             dataType: "json",
             success: function (result) {
             	console.debug(result)
                 if (result.error != 0) {
                     var errors = result.data;
                     $.messager.alert(messageStrings.hint, result.msg);
                 } else {
                	 $(cls).parent().next().children(".c-list").prepend("<li><span class='c-nick'></span>我:<span class='c-content'>"+$("#c-form [name='content']").val()+"</span></li>");
                	 $('#c-form input').val('');
                 }
             },
             error: function (XMLHttpRequest) {
                 console.error(XMLHttpRequest);
                 $.messager.alert("系统错误", XMLHttpRequest.responseText);
             }
         }); 
    }
    </script>
</head>
<body>

	<div class="easyui-navpanel" style="-webkit-overflow-scrolling: touch">
        <header>
            <div class="m-toolbar">
                <div class="m-title">童言朗读者</div>
                <div class="m-right">
                    <a href="question?code=${param.code}" class="easyui-linkbutton" iconCls="icon-search" plain="true" outline="true">往期</a>
                </div>
            </div>
        </header>
        <div style="padding:20px 10px" class="m-data">
        <div class="m-title q-title" style="font-size:18px;font-weight:800;padding-bottom:20px">标题</div>
        <textarea class="q-content" readonly="readonly">内容</textarea>
        
		<ul id="list" class="m-list">
		<li class="a-li hide"><div class="a-date"></div>
		<div class='list-header'><img class='list-image a-headurl' src='${ctx}/static/jquery-easyui/demo-mobile/images/scanner.png'/><div class="a-nick">ggyy</div></div>
		<div class='list-content'><button class="play-btn" vurl="../../../snow.mp3"></button></div>
		<div style="height: 30px;float: right;"">
		<a href="javascript:void(0)" class="easyui-menubutton" data-options="hasDownArrow:false,iconCls:'icon-edit'" style="width:30px;"></a>
		<a href="javascript:void(0)" class="easyui-menubutton" data-options="hasDownArrow:false,iconCls:'icon-trash'" style="width:30px;display: none;"></a></div>
		<div class="d-comment"><div class="list-footer"></div>
		<ul class="c-list"></ul>
		</div></li>
		</ul>
		</div>
        <footer style="padding:2px 3px">
        <form id="a-form" action="${ctx}/answer/upload?code=${param.code}" enctype="multipart/form-data" method="post">
        <input name="qid" type="hidden"><input name="vfile" style="display: none;" type="file" id="vfile" accept="audio/*" >
        <!-- <button class="l-btn" onclick="$('#vfile').click();" type="button">直接发送</button> -->
        <button id="vrecord" class="l-btn" type="button">录音并发送</button></form>
        </footer>
	</div>
	
	<div id="mm1" class="easyui-menu">  
        <div id="like_b" onclick="like();">赞</div>  
        <div onclick="$('#dlg1').dialog('open').dialog('center');$('#dlg1 .textbox-text').focus();">回复</div>
    </div>
    <div id="dlg1" class="easyui-dialog" style="padding:20px 6px;width:80%;" data-options="inline:true,modal:true,closed:true,title:false">
    <form id="c-form">
			<div style="margin-bottom:10px">
			<input name="answerid" type="hidden">
			<input class="easyui-textbox" name="content" prompt="评论" style="width:100%;" multiline="true">
			</div>
			<div class="dialog-button">
				<a href="javascript:void(0)" class="easyui-linkbutton" style="width:45%;height:30px;float: left;" onclick="sendcomment()">发送</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" style="width:45%;height:30px;float: right;" onclick="$('#dlg1').dialog('close')">取消</a>
			</div>
			</form>
		</div>
	<div id="dlg2" class="easyui-dialog" style="padding:20px 6px;width:80%;" data-options="inline:true,modal:true,closed:true,title:false">
	<div class="dialog-button">
	<p style="text-align: center;"><button id="listen-btn"></button></p>
	<a href="javascript:void(0)" class="easyui-linkbutton" style="width:45%;height:30px;float: left;" onclick="wxserverid();">上传</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" style="width:45%;height:30px;float: right;" onclick="$('#dlg2').dialog('close')">取消</a>
	</div>
	</div>
</body>
</html>