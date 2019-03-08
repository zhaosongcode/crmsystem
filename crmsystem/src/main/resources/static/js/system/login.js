$(function () {
    setTimeout(changeCode,1000);
    $("#codeImg").on("click",changeCode);

});


//键盘回车事件，执行登录
$(document).keyup(function(event) {
    if (event.keyCode == 13) {
        $("#submit").trigger("click");
    }
});

/**
 * 验证码改变
 */
function changeCode() {
    $("#codeImg").attr("src","code/generate?t=" + getCurrenTime());
}

/**
 * 验证码框提示信息
 */
function remval() {
    if($("#code").val()=="请输入验证码"){
        $("#code").val("");
    }
}

function showval() {
    if($("#code").val()==""){
        $("#code").val("请输入验证码");
    }
}

/**
 * 获取当前时间
 * @returns {number}
 */
function getCurrenTime() {
    var time = new Date();
    return time.getTime();
}

/**
 * 提交
 */
function submit() {
    /*客户端判定*/
        var username = $("#username").val();
        var password = $("#password").val();
        var code = $("#code").val();
        var datas = username+",boke,"+password+",boke,"+code
        if(username.trim()==""){
            $("#username").val("");
            $("#username").focus();
            $("#username").tips({
                side : 1,
                msg : '账号不得为空',
                bg : '#AE81FF',
                time : 2
            });
        }else if(password.trim()==""){
            $("#password").val("");
            $("#password").focus();
            $("#password").tips({
                side : 1,
                msg : '密码不得为空',
                bg : '#AE81FF',
                time : 2
            });
        }else if(code.trim()=="" || code=="请输入验证码"){
            $("#code").tips({
                side : 1,
                msg : '验证码不得为空',
                bg : '#AE81FF',
                time : 2
            });
        }else{
            /*服务端判定*/
            /*var index = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });*/
            var index = -1;
            $.ajax({
               type : "post",
                url : "login_login",
                data : {
                    datas : datas
                },
                beforeSend : function () {
                    index = layer.load(2, {time: 10*1000}); //设定最长等待10秒
                    return true;
                },
                success : function (data) {
                   layer.close(index);
                    if(data.result=="success"){
                        loadIndex(data.userId);
                    }else if(data.result=="验证码不能为空"){
                        $("#code").tips({
                            side : 1,
                            msg : data.result,
                            bg : '#AE81FF',
                            time : 2
                        });
                    }else if(data.result=="登录异常，请联系管理员"){
                        layer.alert(data.result, {icon: 2});
                    }else if(data.result=="验证码错误"){
                        $("#code").tips({
                            side : 1,
                            msg : data.result,
                            bg : '#AE81FF',
                            time : 2
                        });
                    }else if(data.result=="输入不得为空"){
                        layer.alert(data.result, {icon: 2});
                    }else{
                        layer.alert(data.result, {icon: 2});
                    }
                },
                error : function () {
                    layer.close(index);
                    layer.alert("后台处理异常", {icon: 2});
                }
            });
        }
}

function loadIndex(userId) {
    var index = -1;
    $.ajax({
        type : "post",
        url : "main/load",
        data : {
            userId : userId
        },
        beforeSend : function(){
            index = layer.load(2, {time: 10*1000}); //设定最长等待10秒
            return true;
        },
        success : function (data) {
            layer.close(index);
            if(data.desc="success"){
                window.location.href="main/index";
            }else{
                layer.msg(data.desc);
            }
        },
        error : function () {
            layer.close(index);
            layer.msg(data.desc);
        }
    })
}
