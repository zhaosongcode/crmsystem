$(function () {
    initDate();
    $("#isClose").select2({
        placeholder:'请选择是否关闭',
        allowClear:true
    });
    $("#isClose").val(null).trigger("change");
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

function addSub() {
    var url = "route/tip/addDo";
    var surl = "route/tip/list";
    var str = "tipAddForm";
    ajaSub(str,url,function (data) {
        var split = data.split(",");
        if(split.length>1){
            var mess = split[0];
            var content = split[1];
            var tis = split[2];
            var tipId = split[3];
            if(mess=="success"){
                layer.msg("添加成功");
                var ti = "setTime"+tipId;
                window[ti] = setTimeout(function(){
                    layer.alert(content);
                },tis);
                /*var aaa = eval("setTime"+tipId);
                clearTimeout(aaa);*/
                topage(surl);
            }else{
                layer.msg("添加失败");
            }
        }else{
            if(data=="success"){
                layer.msg("添加成功");
                topage(surl);
            }else{
                layer.msg("添加失败");
            }
        }
    });
}
