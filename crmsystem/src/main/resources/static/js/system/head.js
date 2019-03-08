function toPersonalSet() {
    var userId = $("#currentUserId").val();
    var index = -1;
    $.ajax({
        type : "post",
        url : "../personal/personalSet",
        data : {
            userId : userId
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

function logout() {
    layer.confirm('您确定要退出吗？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        window.location.href="../logout";
    });
};

function savePersonalMessage() {
    var phone = $("#personalPhone").val();
    var email = $("#personalEmail").val();
    var emailverify = /^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/;
    if(phone.trim()==""){
        layer.msg("请正确输入手机号");
        $("#personalPhone").val("");
        $("#personalPhone").focus();
    }else if(email.trim()==""){
        layer.msg("请正确输入邮箱");
        $("#personalEmail").val("");
        $("#personalEmail").focus();
    }else if(emailverify.test(email)){
        var index = -1;
        $.ajax({
            type : "post",
            url : "../personal/updataPerMess",
            data : {
                phone : phone,
                email : email,
                userId : $("#currentUserId").val()
            },
            beforeSend : function(){
                index = layer.load(2, {time: 10*1000}); //设定最长等待10秒
                return true;
            },
            success : function (data) {
                layer.close(index);
                if(data.desc=="success"){
                    layer.msg("修改成功");
                }else{
                    layer.msg(data.desc);
                }
            },
            error : function () {
                layer.close(index);
                layer.msg(data.desc);
            }
        })
    }else{
        layer.msg("请输入正确格式的邮箱");
    }
}