$(function () {
    $("#feedbackUserId").val($("#currentUserId").val())
    $("#feedbackType").select2({
        placeholder:'请选择反馈的类型',
        allowClear:true
    });
    $("#feedbackType").val(null).trigger("change");
})

function sendFeedback() {
    if(!check()){
        var dt = JSONHelper.GetObjectFromJson(jsonstring);
        return false;
    }
    sendFeedbackDo();
}

function sendFeedbackDo() {
    $("#feedbackForm").ajaxSubmit({
        url: "other/send",
        datatype: "json",
        async: true,
        success: function (data) {
            if (data.obj=="success") {
                $("#feedbackType").val(null).trigger("change");
                $("#feedbackSubject").val("");
                $("#feedbackDesc").val("");
                feedbackDescEntity.execCommand('cleardoc')
                setTimeout("window.location.href=\"index\"",1000)
                layer.msg("发送成功，我们将尽快处理!");
            } else {
                layer.msg("发送失败！");
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            layer.msg("发送失败！");
            console.log("textStatus=" + textStatus);
            console.log("jqXHR.status=" + jqXHR.status);
            console.log("jqXHR.readyState=" + jqXHR.readyState);
        }
    });
}

function check() {
    var feedbackSubject = $("#feedbackSubject").val();
    var feddbackType = $("#feedbackType").val();
    $("#feedbackDesc").val(feedbackDescEntity.getPlainTxt());
    var feedbackDesc = $("#feedbackDesc").val();
    if(CommnUtil.notNull(feedbackSubject)){
        if(feedbackSubject.trim() != ""){
            if(CommnUtil.notNull(feddbackType)){
                if(CommnUtil.notNull(feedbackDesc)){
                    if(feedbackDesc.trim() != ""){
                        return true;
                    }else{
                        layer.msg("请正确输入反馈描述!");
                        return false;
                    }
                }else{
                    layer.msg("请输入反馈描述!");
                    return false;
                }
            }else{
                layer.msg("请选择反馈的类型!");
                return false;
            }
        }else{
            layer.msg("请准确输入反馈标题!");
            $("#feedbackSubject").val();
            $("#feedbackSubject").focus();
            return false;
        }
    }else{
        layer.msg("请输入反馈标题!");
        $("#feedbackSubject").focus();
        return false;
    }
}
