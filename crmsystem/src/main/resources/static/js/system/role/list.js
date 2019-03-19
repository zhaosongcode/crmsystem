$(function () {
    initTable();
    $("#tableMessage").css("display","block");
})


function toDetail(userId) {
    layer.msg("暂无详情页内容!")
}

//条件查询
function selectByExample() {
    $("#seFrom").ajaxSubmit({
        url: "role/list",
        async: true,
        success: function (data) {
            $("#contentdiv").html(data)
        }
    });
}

//重置
function reSetSeacrch() {
    $("#roleName").val("");
}

//批量删除客户
function batchDelete() {
    var count = 0;
    var roleIds = $("#roleIds").val();
    $("input.checkboxes[name='selectedIds']:checkbox").each(function() {
        if ($(this).is(":checked")) {
            count++;
            if (roleIds != "" && typeof (roleIds) != "undefined") {
                roleIds += "," + $(this).val();
            } else {
                roleIds = $(this).val();
            }
        }
    })
    if(count!="0"){
        layer.confirm("是否删除？", {title: "删除确认"}, function (index) {
            layer.close(index);
            var url = "role/delete";
            var s = CommnUtil.ajax(url, {
                roleIds: roleIds
            }, "json");
            if (s.code='0') {
                layer.msg('删除成功');
                topage("role/list")
            }else{
                layer.msg("删除失败");
            }
        });
    }else{
        layer.msg("请选择操作项")
    }
}

//删除角色
function deleteRole(roleIds) {
    layer.confirm("是否删除？", {title: "删除确认"}, function (index) {
        layer.close(index);
        var url = "role/delete";
        var data = CommnUtil.ajax(url, {
            roleIds: roleIds
        }, "json");
        if (data.desc='success') {
            layer.msg('删除成功');
            topage("role/list")
        }else{
            layer.msg("删除失败");
        }
    });
}

function editorRole(roleId) {
    var index = -1;
    var url = "role/edit";
    $.ajax({
        type : "post",
        url : url,
        data : {
            roleId : roleId
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