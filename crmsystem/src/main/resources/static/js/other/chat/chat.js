$(function () {

    var chi = $(".menuChild");
    $(".menpeo").each(function () {
        $(this).bind("click",function () {
            if($(this).parent().find(chi).css("display")=="none"){
                $(".menuPartens").find(chi).slideUp("fast");
                $(".menuPartens").find(".fa-angle-down").prop("class","fa fa-angle-left");
                $(this).parent().find(chi).slideDown("fast");
                $(this).parent().find(".fa-angle-left").prop("class","fa fa-angle-down");
            }else{
                $(this).parent().find(chi).slideUp("fast");
                $(this).parent().find(".fa-angle-down").prop("class","fa fa-angle-left");
            }
        })
    })

    unMessCss1();
})

//刷新联系人列表
function unMessCss1() {
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
}


//点击用户进入聊天主页面
function changeUser(fromUserId, userName,apartmentId) {

    $("#userNameaa").val(userName);
    var userId = user.userId;
    $(".messClass").css("display","block");
    $("#messageMain").html("<input type=\"hidden\" id=\"messageCount\" value=\"10\">");
    $("#messageMain").attr("class",fromUserId);
    $("#useNam").text("您正在和"+userName+"聊天");
    $("#useNam").attr("toUserId",fromUserId);
    //点击某一用户的时候清除未读标志
    $("#"+fromUserId).css("display","none");
    //指针计数
    var blockCount = 0;
    $("."+apartmentId).prev().each(function () {
        if($(this).css("display")=="none"){
            return true;
        }else{
            blockCount++;
        }
    })
    if(blockCount<1){
        $("#"+apartmentId).css("display","none");
    }
    //清楚顶部提示字样
    //部门指针计数
    var apaCount = 0;
    $(".apaeach").each(function () {
        if($(this).css("display")=="none"){
            return true;
        }else{
            apaCount++;
        }
    })
    if(apaCount<1){
        $("#messageDiv").css("display","none");
    }
    //实际后台业务
    //做聊天记录回显
    $.ajax({
        url : "other/getRecord",
        type : "post",
        data : {
            toUserId : userId,
            fromUserId : fromUserId,
            messageCount : $("#messageCount").val()
        },
        success : function (data) {
            if(data != null && data != undefined && data.length>0){
                var mmcc = data[0].messageCount;
                $("#messageCount").val(mmcc);
                for(var i=0;i<data.length;i++){
                    if(data[i].toUserid != userId){
                        //自己一方的消息
                        var mescon = "<div style=\"margin-right: 2%;width: 50%;float: right;clear: both\">\n" +
                            "\t<span style=\"float: right\">我：</span>\n" +
                            "\t<label style=\"word-break: break-all;float: right;clear: both\">"+data[i].messageContent+"</label>\n" +
                            "</div>";
                        $("#messageMain").append(mescon);
                    }else{
                        var mescon = "<div style=\"margin-left: 2%;width: 50%;clear: both\">\n" +
                            "\t<span style=\"display: block\">"+userName+"：</span>\n" +
                            "\t<label style=\"word-break: break-all\">"+data[i].messageContent+"</label>\n" +
                            "</div";
                        $("#messageMain").append(mescon);
                    }
                }
                scoBot();
            }
        },
        error : function () {
            layer.alert("记录查询失败!")
        }
    })
    //改变消息状态
   $.ajax({
       url : "other/getMessage",
       type : "post",
       data : {
         userId : userId,
         fromUserId : fromUserId
       },
       beforeSend : function(){
           index = layer.load(2, {time: 10*1000}); //设定最长等待10秒
           return true;
       },
       success : function (data) {
           layer.close(index);
           var message =  data.obj;
           for(var i=0;i<message.length;i++){
               var content = "<div style=\"margin-left: 2%;width: 50%;clear: both\">\n" +
                   "            <span style=\"display: block\">"+userName+"：</span>\n" +
                   "            <label style=\"word-break: break-all\">"+message[i].messageContent+"</label>\n" +
                   "        </div>";
               $("#messageMain").append(content);
           }
           scoBot();
       },
       error : function () {
           layer.close(index);
           layer.alert("后台系统故障，请稍后再试!")
       }
   })
}

//发送消息
function sendMessage() {
    var message = $("#messageContent").val();
    if(message==""){
        layer.alert("请输入内容!");
        return false;
    }
    //首先处理页面效果
    var messCon = $("#messageContent").val();
    var content = "<div style=\"margin-right: 2%;width: 50%;float: right;clear: both\">\n" +
        "            <span style=\"float: right\">我：</span>\n" +
        "            <label style=\"word-break: break-all;float: right;clear: both\">"+messCon+"</label>\n" +
        "        </div>";
    $("#messageMain").append(content);
    $("#messageContent").val("");
    $("#messageContent").focus();
    //后台实际业务处理
    var toUserId = $("#useNam").attr("toUserId");
    message = message + "," + toUserId;
    websocket.send(message);
    scoBot();
}

function getMoreRecord() {
    var userId = user.userId;
    var userName = $("#userNameaa").val();
    var fromUserId = $("#useNam").attr("toUserId");
    var toUserId = user.userId;
    var messageCount = $("#messageCount").val();
    $.ajax({
        url : "other/getRecord",
        type : "post",
        data : {
            toUserId : toUserId,
            fromUserId : fromUserId,
            messageCount : messageCount
        },
        success : function (data) {
            if(data != null && data != undefined && data.length>0){
                $("#messageMain").html("<input type=\"hidden\" id=\"messageCount\" value=\""+data[0].messageCount+"\">");
                for(var i=0;i<data.length;i++){
                    if(data[i].toUserid != userId){
                        //自己一方的消息
                        var mescon = "<div style=\"margin-right: 2%;width: 50%;float: right;clear: both\">\n" +
                            "\t<span style=\"float: right\">我：</span>\n" +
                            "\t<label style=\"word-break: break-all;float: right;clear: both\">"+data[i].messageContent+"</label>\n" +
                            "</div>";
                        $("#messageMain").append(mescon);
                    }else{
                        var mescon = "<div style=\"margin-left: 2%;width: 50%;clear: both\">\n" +
                            "\t<span style=\"display: block\">"+userName+"：</span>\n" +
                            "\t<label style=\"word-break: break-all\">"+data[i].messageContent+"</label>\n" +
                            "</div";
                        $("#messageMain").append(mescon);
                    }}}
        },
        error : function () {
            layer.alert("后台系统异常!")
        }
    })
}

//滚动条保持底部
function scoBot() {
    var div = $("#chatMainIndex");
    div[0].scrollTop = div[0].scrollHeight;
}

//键盘回车事件，执行登录
$(document).keyup(function(event) {
    if (event.keyCode == 13) {
        $("#sendBtn").trigger("click");
    }
});
