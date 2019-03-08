$(function () {

    loadIndex();

    var chi = $(".menuChild");
    $("[nav-n]").each(function () {
        $(this).bind("click",function () {
            if($(this).parent().find(chi).css("display")=="none"){
                $(".menuParten").find(chi).slideUp("fast");
                $(".menuParten").find(".fa-angle-down").prop("class","fa fa-angle-left");
                $(this).parent().find(chi).slideDown("fast");
                $(this).parent().find(".fa-angle-left").prop("class","fa fa-angle-down");
            }else{
                $(this).parent().find(chi).slideUp("fast");
                $(this).parent().find(".fa-angle-down").prop("class","fa fa-angle-left");
            }
        })
    })

    websocket = createWebScoket();

    //连接成功建立的回调方法
    websocket.onopen = function(event){
        timingMessage();
        unMessCss();
    }

    //定时查询未读消息
    function timingMessage(){
        var userId = $("#currentUserId").val();

        //顶部提示消息刷新
        $.ajax({
            url : "other/getunreadmessage?userId="+userId,
            type : "get",
            success : function (data) {
                var data = $.parseJSON(data);
                if(data=="havemessage"){
                    $("#messageDiv").css("display","block");
                }else if(data=="unmessage"){
                    $("#messageDiv").css("display","none");
                }
            }
        })
        //定时循环执行
        setTimeout(timingMessage,10000);
    }

    //接收到消息的回调方法
    websocket.onmessage = function(event){
        var messageMain = $("#messageMain");
        var meCo = event.data;
        var split = meCo.split(",");
        var fromUserId = split[1];
        if($("#messageMain").attr("class")==fromUserId){
            //正打开当前聊天对象窗口
            //也要存数据库
            $.ajax({
                url : "other/saveMessages",
                type : "post",
                data : {
                    messages : meCo,
                    toUserId : user.userId
                },
                success : function (data) {
                    data = $.parseJSON(data);
                    var split = data.split(",");
                    var messa = split[0];
                    var userName = split[1];
                    var messCont = "<div style=\"margin-left: 2%;width: 50%;clear: both\">\n" +
                        "\t<span style=\"display: block\">"+userName+"：</span>\n" +
                        "\t<label style=\"word-break: break-all\">"+messa+"</label>\n" +
                        "</div>";
                    messageMain.append(messCont);
                    scoBot();
                },
                error : function () {
                    layer.alert("接收消息失败,后台异常!")
                }
            })
        }else{
            //不是当前窗口则后天存入数据库未读消息
            var mess = split[0];
            var toUserId = user.userId;
            var messages = mess + "," + toUserId + "," + fromUserId;
            $.ajax({
                url : "other/saveUnRMessages",
                type : "post",
                data : {
                    messages : messages
                },
                success :function (data) {
                    return true;
                },
                error : function () {
                    return false;
                }
            })
        }
    }

    //连接关闭的回调方法
   /* websocket.onclose = function(){

    }*/

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function(){
        websocket.close();
    }
    //关闭连接
    function closeWebSocket(){
        websocket.close();
    }

    //发送消息
    /*function send(toUserId){
        var message = $("#messageContent").val();
        message = message + "," + toUserId;
        websocket.send(message);
    }*/


});

//刷新联系人列表
function unMessCss() {
    //unReadMessages未读消息列表,根据列表来处理未读样式问题
    $.ajax({
        url : "other/refSess?userId="+user.userId,
        type : "get",
        success : function (unReadMessages) {
            //进入页面后刷新列表未读样式
            if(unReadMessages != null && unReadMessages != undefined){
                for(var i=0;i<unReadMessages.length;i++){
                    $("#"+unReadMessages[i].apartmentId).css("display","");
                    $("#"+unReadMessages[i].fromUserid).css("display","");
                }
            }
        },
        error : function () {

        }
    })
    setTimeout(unMessCss,10000);
}

function createWebScoket(){
    //创建scoket对象
    var websocket = null;
    //判断当前浏览器是否支持WebSocket
    if('WebSocket' in window){
        var userId = $("#currentUserId").val();
        //服务器地址
        //websocket = new WebSocket("ws://39.96.47.197:8080/crmsystem/websocket?userId="+userId);
        //本地测试地址
        websocket = new WebSocket("ws://localhost:8080/crmsystem/websocket?userId="+userId);
        //局域网测试
        //websocket = new WebSocket("ws://172.20.10.5:8080/crmsystem/websocket?userId="+userId);
    }
    else{
        alert('不支持webscorket!')
    }
    return websocket;
}

//未读消息单击事件
$("#messageA").on('click',function () {
    topage("other/onlineconsultant");
})

function loadIndex() {
    var index = -1;
    $.ajax({
        type : "post",
        url : "../overview/index",
        data : {
            userId : $("#currentUserId").val()
        },
        beforeSend : function(){
            index = layer.load(2, {time: 10*1000}); //设定最长等待10秒
            return true;
        },
        success : function (data) {
            layer.close(index);
            $("#contentdiv").html(data);
        },
        error : function () {
            layer.close(index);
            layer.msg("加载失败");
        }
    })
}

function topage(url) {
    var index = -1;
    $.ajax({
        type : "post",
        url : url,
        data : {
            userId : $("#currentUserId").val()
        },
        beforeSend : function(){
            index = layer.load(2, { //icon支持传入0-2
                content: '加载中...',
                success: function (layero) {
                    layero.find('.layui-layer-content').css({
                        'padding-top': '39px',
                        'width': '60px'
                    });
                }
            }); //设定最长等待10秒
            return true;
        },
        success : function (data) {
            layer.close(index);
            $("#contentdiv").html(data);
        },
        error : function () {
            layer.close(index);
            layer.msg("加载失败");
        }
    })
}

//滚动条保持底部
function scoBot() {
    var div = $("#chatMainIndex");
    div[0].scrollTop = div[0].scrollHeight;
}
