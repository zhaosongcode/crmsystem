$(function () {
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

//前端对数据验证
function checkUser() {
    var userRealName = $("#userRealName").val();
    var userName = $("#userName").val();
    var phone = $("#phone").val();
    var phoneRegular = /^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\d{8}$/;
    var email = $("#email").val();
    var emailRegular = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
    var userDepartment = $("#userDepartment").val();
    var userTypeOfWork = $("#userTypeOfWork").val();
    if(CommnUtil.notNull(userRealName)){
        if(userRealName.trim()!=''){
            if(!CommnUtil.isSpecial(userRealName)){
                if(CommnUtil.notNull(userName)){
                    if(userName.trim()!=''){
                        if(!CommnUtil.isSpecial(userName)){
                            if(CommnUtil.notNull(phone)){
                                if(phoneRegular.test(phone)){
                                    if(CommnUtil.notNull(email)){
                                        if(emailRegular.test(email)){
                                            if(CommnUtil.notNull(userDepartment)){
                                                if(CommnUtil.notNull(userTypeOfWork)){
                                                    return true;
                                                }else{
                                                    layer.msg("请选择用户的职位!")
                                                    return false;
                                                }
                                            }else{
                                                layer.msg("请选择用户的部门!");
                                                return false;
                                            }
                                        }else{
                                            layer.msg("请正确输入邮箱!")
                                            $("#email").val("");
                                            $("#email").focus();
                                            return false;
                                        }
                                    }else{
                                        layer.msg("请输入邮箱!")
                                        $("#email").focus();
                                        return false;
                                    }
                                }else{
                                    layer.msg("请正确输入用户手机号!")
                                    $("#phone").val("");
                                    $("#phone").focus();
                                    return false;
                                }
                            }else{
                                layer.msg("请输入用户手机号!")
                                $("#phone").focus();
                                return false;
                            }
                        }else{
                            layer.msg("用户账号不能包含特殊字符!")
                            $("#username").val("");
                            $("#username").focus();
                            return false;
                        }
                    }else{
                        layer.msg("请正确输入用户账号!")
                        $("#username").val("");
                        $("#username").focus();
                        return false;
                    }
                }else{
                    layer.msg("请输入用户账号名!")
                    $("#username").focus();
                    return false;
                }
            }else{
                layer.msg("用户姓名不能包含特殊字符!")
                $("#userRealName").val("");
                $("#userRealName").focus();
                return false;
            }
        }else{
            layer.msg("请正确输入用户姓名!")
            $("#userRealName").val("");
            $("#userRealName").focus();
            return false;
        }
    }else{
        layer.msg("用户姓名不能为空")
        $("#userRealName").focus();
        return false;
    }
}

$("#saveNewUser").on('click',function () {
    if(!checkUser()){
        return false;
    }
    $("#addForm").ajaxSubmit({
        url: "user/editUserDo",
        datatype: "json",
        async: true,
        success: function (data) {
            if(data.desc='success'){
                layer.msg("修改成功");
                topage("user/list")
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