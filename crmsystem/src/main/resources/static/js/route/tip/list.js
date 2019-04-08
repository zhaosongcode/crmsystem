$(function () {
   initTable();
});

function deleteTipById(tipId) {
    layer.confirm("是否删除？", {title: "删除确认"}, function (index) {
        layer.close(index);
        aja("route/tip/delete","get",{tipId : tipId},function (data) {
            var split = data.split("-");
            if(split.length>1){
                var mess = split[0];
                var da = split[1];
                if(mess=="success"){
                   if(da != null && da.trim() != "" && da != undefined){
                       var aa = eval("setTime"+da);
                       clearTimeout(aa);
                   }
                    layer.msg("删除成功");
                    topage("route/tip/list");
                }else{
                    layer.msg("删除失败");
                }
            }else{
               if(split=="success"){
                   layer.msg("删除成功");
                   topage("route/tip/list");
               }else{
                   layer.msg("删除失败");
               }
            }
        })
    });
}

function batchDelete() {
    var count = 0;
    var tipId = "";
    $("input.checkboxes[name='selectedIds']:checkbox").each(function() {
        if ($(this).is(":checked")) {
            count++;
            if (tipId != "" && typeof (tipId) != "undefined") {
                tipId += "," + $(this).val();
            } else {
                tipId = $(this).val();
            }
        }
    })
    if(count!="0"){
        layer.confirm("是否删除？", {title: "删除确认"}, function (index) {
            layer.close(index);
            var url = "route/tip/delete";
            var s = CommnUtil.ajax(url, {
                tipId: tipId
            }, "json");
            var split = s.split("-");
            if(split.length>1){
                var mess = split[0];
                var data = split[1];
                if (mess=="success") {
                   if(strings != null && strings.trim()!="" && strings != null){
                       var strings = data.split(",");
                       for(var i=0;i<strings.length;i++){
                           var aaaa = eval("setTime"+strings[i]);
                           if(aaaa != undefined && aaaa != null){
                               clearTimeout(aaaa);
                           }
                       }
                   }
                    layer.msg('删除成功');
                    topage("route/tip/list")
                }else{
                    layer.msg("删除失败");
                }
            }else{
                if(split=="success"){
                    layer.msg("删除成功");
                    topage("route/tip/list");
                }else{
                    layer.msg("删除失败");
                }
            }
        });
    }else{
        layer.msg("请选择操作项")
    }
}