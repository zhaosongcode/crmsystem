$(function () {
    initTable()
    $("#tableMessage").css("display","block");
    $("#orderType").select2({
        placeholder:'请选择订单类型',
        allowClear:true
    });
    $("#orderStatus").select2({
        placeholder:'请选择订单状态',
        allowClear:true
    });
})

//双击进入详情页
function toDetail(id){
    layer.msg("暂无详情页内容")
}

//条件查询
function selectByExample() {
    $("#seFrom").ajaxSubmit({
        url: "order/list",
        async: true,
        success: function (data) {
            $("#contentdiv").html(data)
        }
    });
}

//重置
function reSetSeacrch() {
    $("#clientName").val("");
    $("#orderType").val(null).trigger("change");
    $("#orderStatus").val(null).trigger("change");
}

//批量删除客户
function batchDelete() {
    var count = 0;
    var orderIds = $("#orderIds").val();
    $("input.checkboxes[name='selectedIds']:checkbox").each(function() {
        if ($(this).is(":checked")) {
            count++;
            if (orderIds != "" && typeof (orderIds) != "undefined") {
                orderIds += "," + $(this).val();
            } else {
                orderIds = $(this).val();
            }
        }
    })
    if(count!="0"){
        layer.confirm("是否删除？", {title: "删除确认"}, function (index) {
            layer.close(index);
            var url = "order/delete";
            var s = CommnUtil.ajax(url, {
                orderIds: orderIds
            }, "json");
            if ("success"== s) {
                layer.msg('删除成功');
                topage("order/list")
            }else if("statusError"==s){
                layer.msg("订单状态无法删除!")
            }else{
                layer.msg("删除失败");
            }
        });
    }else{
        layer.msg("请选择操作项")
    }
}

//删除客户
function deleteOrderById(orderId) {
    layer.confirm("是否删除？", {title: "删除确认"}, function (index) {
        layer.close(index);
        var url = "order/delete";
        var s = CommnUtil.ajax(url, {
            orderIds: orderId
        }, "json");
        if ("success"== s) {
            layer.msg('删除成功');
            topage("order/list")
        }else if("statusError"==s){
            layer.msg("订单状态无法删除!")
        }else{
            layer.msg("删除失败");
        }
    });
}

//前往编辑客户页面
function editorOrder(orderId) {
    var data = {orderId:orderId,userId:$("#currentUserId").val()};
    toedit(data)
}
function toedit(data) {
    var index = -1;
    var url = "order/edit";
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