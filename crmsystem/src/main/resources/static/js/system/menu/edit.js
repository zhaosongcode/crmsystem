$(function () {
    $("#paMenu").select2({
        placeholder:'请选择父菜单',
        allowClear:true
    });
    if(menu != null && menu != ""){
        var paMenu = menu.paMenu;
        if(paMenu != null && paMenu != ""){
            $("#paMenu").val(paMenu).trigger("change");
        }else{
            $("#paMenu").val(null).trigger("change");
        }
    }else{
        $("#paMenu").val(null).trigger("change");
    }
});

function saveMenu() {
    if(!check()){
        return false;
    }
    $("#addForm").ajaxSubmit({
        url : "menu/editDo",
        type : "post",
        dataType : "json",
        success : function (data) {
            if(data == "success"){
                topage("menu/list");
                layer.msg("修改成功");
            }else{
                layer.msg("添加失败");
            }
        },
        error : function () {
            layer.msg("后台系统异常!");
        }
    })
}

//前端校验
function check() {
    var menuName = $("#menuName");
    var menuUrl = $("#menuUrl");
    var paMenu = $("#paMenu");
    var menuIcon = $("#menuIcon");
    if(!isNull(paMenu.val())){
        if(isNull(menuUrl.val())){
            layer.msg("请正确输入菜单路径!");
            return false;
        }
    }
    if(isNull(menuName.val())){
        layer.msg("请正确输入菜单名称!");
        return false;
    }else if(isNull(menuIcon.val())){
        layer.msg("请正确输入菜单图标!");
        return false;
    }else{
        return true;
    }
}