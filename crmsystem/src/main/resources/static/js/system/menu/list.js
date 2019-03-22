$(function () {
    initTable();
});

function toDetail(userId) {
    layer.msg("暂无详情页内容!")
}

//条件查询
function selectByExample() {
    $("#menuFrom").ajaxSubmit({
        url: "menu/list",
        async: true,
        success: function (data) {
            $("#contentdiv").html(data)
        }
    });
}

//重置
function reSetSeacrch() {
    $("#menuName").val("");
}

//批量删除客户
function batchDelete() {
    var count = 0;
    var menuIds = "";
    $("input.checkboxes[name='selectedIds']:checkbox").each(function() {
        if ($(this).is(":checked")) {
            count++;
            if (menuIds != "" && typeof (menuIds) != "undefined") {
                menuIds += "," + $(this).val();
            } else {
                menuIds = $(this).val();
            }
        }
    })
    if(count!="0"){
        layer.confirm("是否删除？", {title: "删除确认"}, function (index) {
            layer.close(index);
            var url = "menu/delete";
            var s = CommnUtil.ajax(url, {
                menuIds: menuIds
            }, "json");
            if (s=="success") {
                layer.msg('删除成功');
                topage("menu/list")
            }else{
                layer.msg("删除失败");
            }
        });
    }else{
        layer.msg("请选择操作项")
    }
}

//删除
function deleteRole(menuId) {
    layer.confirm("是否删除？", {title: "删除确认"}, function (index) {
        layer.close(index);
        var url = "menu/delete";
        var data = CommnUtil.ajax(url, {
            menuIds: menuId
        }, "json");
        if (data=="success") {
            layer.msg('删除成功');
            topage("menu/list")
        }else{
            layer.msg("删除失败");
        }
    });
}

function editorRole(menuId) {
    var index = -1;
    var url = "menu/edit";
    $.ajax({
        type : "post",
        url : url,
        data : {
            menuId : menuId
        },
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