$(function () {
    //初始化table
    initTable()
    $("#tableMessage").css("display","block");
    //初始化下拉框start
    $("#userDepartment").select2({
        placeholder:'请选择员工部门',
        allowClear:true
    });

    $("#userTypeOfWork").select2({
        placeholder:'请选择员工职位',
        allowClear:true
    });
    //初始化下拉框end
})

function toDetail(userId) {
    layer.msg("暂无详情页内容!")
}

//条件查询
function selectByExample() {
    $("#seFrom").ajaxSubmit({
        url: "user/list",
        async: true,
        success: function (data) {
            $("#contentdiv").html(data)
        }
    });
}

//重置
function reSetSeacrch() {
    $("#userDealName").val("");
    $("#userCode").val("");
    $("#userDepartment").val(null).trigger("change");
    $("#userTypeOfWork").val(null).trigger("change");
}

//批量删除客户
function batchDelete() {
    var count = 0;
    var userIds = $("#userIds").val();
    $("input.checkboxes[name='selectedIds']:checkbox").each(function() {
        if ($(this).is(":checked")) {
            count++;
            if (userIds != "" && typeof (userIds) != "undefined") {
                userIds += "," + $(this).val();
            } else {
                userIds = $(this).val();
            }
        }
    })
    if(count!="0"){
        layer.confirm("是否删除？", {title: "删除确认"}, function (index) {
            layer.close(index);
            var url = "user/deleteUser";
            var s = CommnUtil.ajax(url, {
                userId: userIds
            }, "json");
            if (s.code='0') {
                layer.msg('删除成功');
                topage("user/list")
            }else{
                layer.msg("删除失败");
            }
        });
    }else{
        layer.msg("请选择操作项")
    }
}

//删除客户
function deleteUserById(userIds) {
    layer.confirm("是否删除？", {title: "删除确认"}, function (index) {
        layer.close(index);
        var url = "user/deleteUser";
        var data = CommnUtil.ajax(url, {
            userId: userIds
        }, "json");
        if (data.code='0') {
            layer.msg('删除成功');
            topage("user/list")
        }else{
            layer.msg("删除失败");
        }
    });
}

//前往编辑客户页面
function editorUser(userId) {
    var data = {userId:userId};
    toedit(data)
}
function toedit(data) {
    var index = -1;
    var url = "user/editUser";
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