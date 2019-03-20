$(function () {
    $("#bankType").select2({
        placeholder:'请选择银行类型',
        allowClear:true
    });
    $("#bankType").val(null).trigger("change");
})

//重置按钮
function reset() {
    $("#bankNum").val("");
    $("#bankType").val(null).trigger("change");
    $("#moneyNum").val("");
}

//充值按钮
function money() {
    //前端数据校验
    if(!check()){
        return false;
    }
    //后台逻辑
    $("#moneySubmitForm").ajaxSubmit({
        url : "other/moneydo",
        type : "post",
        dataType : "json",
        success : function (data) {
            if(data == "success"){
                window.location.href="index";
            }
        },
        error : function () {
            layer,msg("后台系统异常，无法充值!");
        }
    })
}

//前端数据校验
function check() {
    var bankNum = $("#bankNum");
    var bankType = $("#bankType");
    var moneyNum = $("#moneyNum");
    if(bankNum.val()==null || bankNum.val()==undefined || bankNum.val().trim()==""){
        layer.msg("请正确输入银行卡号!");
        bankNum.val("");
        return false;
    }else if(bankType,val()==null || bankType.val()==undefined || bankType.val().trim()==""){
        layer,msg("请选择银行类型!");
        return false;
    }else if(moneyNum.val()==null || moneyNum.val()==undefined || moneyNum.val().trim()==""){
        layer.msg("请正确输入充值金额!");
        moneyNum.val("");
        return false;
    }else{
        return true;
    }
}