$(function () {
    //初始化下拉框start
    $("#clientName").select2({
        placeholder:'请选择客户',
        allowClear:true
    });

    $("#orderType").select2({
        placeholder:'请选择订单的类型',
        allowClear:true
    });

    $("#orderStatus").select2({
        placeholder:'请选择订单的状态',
        allowClear:true
    });
    //初始化下拉框end
})

function toEdit(orderId) {
    var userId = $("#currentUserId").val();
    var index = -1;
    var url = "order/edit";
    var data = {
        orderId : orderId,
        userId : userId
    }
    $.ajax({
        type : "post",
        url : url,
        data : data,
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

//数据验证
function checkOrder(){
    var clientId = $("#clientName").val();
    var orderType = $("#orderType").val();
    var orderStatus = $("#orderStatus").val();
    if(CommnUtil.notNull(clientId)){
        if(CommnUtil.notNull(orderType)){
            if(CommnUtil.notNull(orderStatus)){
                return true;
            }else{
                layer.msg("请选择订单状态!")
                return false;
            }
        }else{
            layer.msg("请选择订单类型!")
            return false;
        }
    }else{
        layer.msg("请选择客户!")
        return false;
    }
}

$("#saveNewUser").on('click',function () {
    if(!checkOrder()){
        return false;
    }
    $("#addForm").ajaxSubmit({
        url: "order/editdo",
        datatype: "json",
        async: true,
        success: function (data) {
            if(data.obj='success'){
                layer.msg("修改成功");
                topage("order/list")
            }else{
                layer.msg("修改失败!");
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            layer.msg("修改失败！");
            console.log("textStatus=" + textStatus);
            console.log("jqXHR.status=" + jqXHR.status);
            console.log("jqXHR.readyState=" + jqXHR.readyState);
        }
    });
})