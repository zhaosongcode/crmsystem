$(function () {
    initTable()
    $("#tableMessage").css("display","block");
    $("#clientType").select2({
        placeholder:'请选择客户类型',
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
        url: "client/list",
        async: true,
        success: function (data) {
            $("#contentdiv").html(data)
        }
    });
}

//重置
function reSetSeacrch() {
    $("#clientName").val("");
    $("#clientType").val(null).trigger("change");
}

//批量删除客户
function batchDelete() {
    var count = 0;
    var clientIds = $("#clientIds").val();
    $("input.checkboxes[name='selectedIds']:checkbox").each(function() {
        if ($(this).is(":checked")) {
            count++;
            if (clientIds != "" && typeof (clientIds) != "undefined") {
                clientIds += "," + $(this).val();
            } else {
                clientIds = $(this).val();
            }
        }
    })
    if(count!="0"){
        layer.confirm("是否删除？", {title: "删除确认"}, function (index) {
            layer.close(index);
            var url = "client/deleteClient";
            var s = CommnUtil.ajax(url, {
                clientIds: clientIds
            }, "json");
            if ("success"== s) {
                layer.msg('删除成功');
                topage("client/list")
            }else{
                layer.msg("删除失败");
            }
        });
    }else{
        layer.msg("请选择操作项")
    }
}

//删除客户
function deleteClientById(clientIds) {
    layer.confirm("是否删除？", {title: "删除确认"}, function (index) {
        layer.close(index);
        var url = "client/deleteClient";
        var s = CommnUtil.ajax(url, {
            clientIds: clientIds
        }, "json");
        if ("success"== s) {
            layer.msg('删除成功');
            topage("client/list")
        }else{
            layer.msg("删除失败");
        }
    });
}

//前往编辑客户页面
function editorClient(clientId) {
    var data = {clientId:clientId};
    toedit(data)
}
function toedit(data) {
    var index = -1;
    var url = "client/editClient";
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