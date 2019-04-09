$(function () {
    initDate();
    $("#isClose").select2({
        placeholder:'请选择是否关闭',
        allowClear:true
    });
});

//初始化时间框
function initDate() {
    //购买日期
    $('#tipTime').focus(function(){
        $(this).blur();//不可输入状态
    })
    $("#tipTime").datetimepicker({
        language : 'zh-CN',
        step:10,
        minuteStep : 1,
        minView : 0,
        maxView : 4,
        format:'yyyy-mm-dd hh:ii:ss',
        clearBtn:true,
        autoclose:true
    });
}

//跳转编辑页面
function toEdit(tipId) {
    $.ajax({
        url : "route/tip/edit",
        data : {
            tipId : tipId
        },
        success : function (data) {
            $("#contentdiv").html(data);
        },
        error : function () {
            layer.msg("后台系统异常");
        }
    })
}

//保存
function addSub() {
    if(!check()){
        return false;
    }
    ajaSub("tipAddForm","route/tip/editDo",function (data) {
        var split = data.split(",");
        if(split.length>1){
            var mess = split[0];
            if(mess == "success"){
                layer.msg("修改成功");
                topage("route/tip/list");
            }else{
                layer.msg("修改失败")
            }
        }else{
            if(data=="success"){
                layer.msg("修改成功");
                topage("route/tip/list");
            }else{
                layer.msg("修改失败")
            }
        }
    })
}

function check() {
    var tipContent = $("#tipContent");
    var tipTime = $("#tipTime");
    var isClose = $("#isClose");
    if(!CommnUtil.notNull(tipContent.val())){
        layer.msg("请选择事项内容");
        return false;
    }else if(!CommnUtil.notNull(tipTime.val())){
        layer.msg("请选择提醒时间")
        return false;
    }else if(!CommnUtil.notNull(isClose.val())){
        layer.msg("请选择是否关闭");
        return false;
    }else {
        return true;
    }
}