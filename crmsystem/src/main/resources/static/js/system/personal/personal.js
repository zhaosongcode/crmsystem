function upDataPersonPassword() {
    var oldPassword = $("#oldPassword").val();
    var newPassword = $("#newPassword").val();
    var affirmPassword = $("#affirmPassword").val();
    if(oldPassword==""){
        $("#oldPassword").val("");
        $("#oldPassword").focus();
        layer.msg("请输入旧密码");
    }else if(newPassword==""){
        $("#newPassword").val("");
        $("#newPassword").focus();
        layer.msg("请输入新密码");
    }else if(affirmPassword==""){
        $("#affirmPassword").val("");
        $("#affirmPassword").focus();
        layer.msg("请确认新密码");
    }else if(newPassword==affirmPassword) {
        var datas = {
            newPassword: newPassword,
            oldPassword: oldPassword,
            affirmPassword: affirmPassword,
            userId: $("#currentUserId").val()
        }
        var data = CommnUtil.ajax("../personal/updataPerPass", datas, "json");
        if(data.code==0){
            window.location.href="../logout"
        }else{
            layer.msg(data.desc);
        }
    }
}