$(function () {
    //初始化下拉框start
    $("#clientName").select2({
        placeholder:'请选择客户',
        allowClear:true
    });
    $("#clientName").val(null).trigger("change");

    $("#orderType").select2({
        placeholder:'请选择订单的类型',
        allowClear:true
    });
    $("#orderType").val(null).trigger("change");

    $("#orderStatus").select2({
        placeholder:'请选择订单的状态',
        allowClear:true
    });
    $("#orderStatus").val(null).trigger("change");
    //初始化下拉框end
});
function addSub(){
    if(!check()){
        return false;
    }

$("#addForm").ajaxSubmit({
    url : "order/addDo",
    type : "post",
    dataType : "json",
    success : function (data) {
        if(data == "success"){
            layer.msg("添加成功");
            topage("order/list");
        }else{
            layer.msg("添加失败");
        }
    },
    error : function () {
        layer.msg("后台系统异常");
    }
});
}

function check() {
    var clientName = $("#clientName");
    var orderType = $("#orderType");
    var orderStatus = $("#orderStatus");
    if(clientName.val()==null || clientName.val()==undefined || clientName.val().trim()==""){
        layer.msg("请选择客户");
        return false;
    }else if(orderType.val()==null || orderType.val()==undefined || orderType.val().trim()==""){
        layer.msg("请选择订单类型");
        return false;
    }else if(orderStatus.val()==null || orderStatus.val()==undefined || orderStatus.val().trim()==""){
        layer.msg("请选择订单状态");
        return false;
    }else{
        return true;
    }
}