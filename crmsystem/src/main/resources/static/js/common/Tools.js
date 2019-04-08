function isNull(str) {
    if(str == null || str.trim() == "" || str == undefined){
        return true;
    }else{
        return false;
    }
}

function ajaSub(str,url,success) {
    $("#"+str).ajaxSubmit({
        url : url,
        type : "post",
        dataType : "json",
        success : success,
        error : function () {
            layer.msg("添加失败")
        }
    })
}

function aja(url,type,data,success) {
    if(type==null || type.trim()==""||type==undefined){
        type = "post";
    }
    $.ajax({
        url : url,
        type : type,
        dataType : "json",
        data : data,
        success : success,
        error : function () {
            layer.msg("后台系统异常")
        }
    })
}