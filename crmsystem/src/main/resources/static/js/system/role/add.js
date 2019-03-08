$(function () {
    $.ajax({
        // 数据传送方式
        type: 'GET',
        contentType: "application/json; charset=utf-8",
        // 数据处理文件
        datatype:"json",
        url: "permission/menu",
        // @msg: 数据返回值
        async: false,
        success: function (data) {
            var getJsonTree=function(data,parentId){
                var itemArr=[];
                for(var i=0;i<data.length;i++){
                    var node=data[i];
                    var chilList = node.childPermission;
                    var childArr = [];
                    for(var j = 0;j<chilList.length;j++){
                        var chilNode = chilList[j];
                        var newChilNode = {id:chilNode.permissionId,text:chilNode.permissionName,pid:chilNode.permissionParentId,icon:chilNode.permissionIcon};
                        childArr.push(newChilNode);
                    }
                        var newNode={id:node.permissionId,text:node.permissionName,pid:node.permissionParentId,children:childArr,icon:node.permissionIcon};
                        itemArr.push(newNode);
                }
                return itemArr;
            };
            $('#res_tree').jstree({
                'core' : {
                    'data' :getJsonTree(data,0),
                    'multiple': true
                },
                'force_text': true,
                'plugins': ["checkbox", "types"],
                'checkbox': {
                    'keep_selected_style': false,//是否默认选中
                    'three_state': true,//父子级别级联选择
                },
                "types" : {
                    "default" : {
                        "icon" : "fa fa-folder icon-state-warning icon-lg"
                    },
                    "file" : {
                        "icon" : "fa fa-file icon-state-warning icon-lg"
                    }
                }
            }).on('loaded.jstree', function() {
                $('#res_tree').jstree('open_all');
            }).on("activate_node.jstree", function (obj, e) {
                // 处理代码
                // 获取当前节点
                var currentNode = e.node;
                var parent = currentNode.parent;
                var seled = currentNode.state.selected;
                var curVal = $("#resIds").val();
                if(parent == "#"){//一级菜单
                    var child = currentNode.children;
                    if(seled){//一级菜单被选中
                        if(CommnUtil.notNull(curVal)){
                            for(var j = 0; j < child.length; j++){
                                if(curVal.indexOf(child[j]) < 0){
                                    curVal += child[j] + ',';
                                }
                            }
                        }else{
                            for(var j = 0; j < child.length; j++){
                                curVal +=  child[j] + ',';
                            }
                        }
                    } else { //一级菜单取消选中
                        var temp = "";
                        var curValArr = curVal.split(',');
                        for(var i = 0; i < curValArr.length; i++){
                            if(CommnUtil.notNull(curValArr[i])){
                                var flag = true;
                                for(var j = 0; j < child.length; j++){
                                    if(curValArr[i] == child[j]){
                                        flag = false;
                                    }
                                }
                                if(flag){
                                    temp += curValArr[i] + ',';
                                }
                            }
                        }
                        curVal = temp;
                    }
                } else {
                    var sedId = currentNode.id;
                    if(seled){//选中
                        if(CommnUtil.notNull(curVal)){
                            curVal = curVal + sedId + ",";
                        }else{
                            curVal = sedId + ',';
                        }
                    } else { //取消选中
                        var sedIdArr = "";
                        if(curVal.startsWith(sedId)){
                            sedIdArr = curVal.split(sedId + ',');
                        }else{
                            sedIdArr = curVal.split(',' + sedId + ',');
                        }
                        if(CommnUtil.notNull(sedIdArr[0]) && CommnUtil.notNull(sedIdArr[1])){
                            curVal = sedIdArr[0] + "," +sedIdArr[1];
                        }else if(CommnUtil.notNull(sedIdArr[0]) && !CommnUtil.notNull(sedIdArr[1])){
                            curVal = sedIdArr[0] + ',';
                        }else if(!CommnUtil.notNull(sedIdArr[0]) && CommnUtil.notNull(sedIdArr[1])){
                            curVal = sedIdArr[1];
                        }else{
                            curVal = '';
                        }
                    }
                }
                $("#resIds").val(curVal);
            });
        }
    });
})

$("#saveNewRole").on('click',function () {
    var roleName = $("#roleName").val();
    if(!CommnUtil.notNull(roleName)){
        layer.msg("请输入角色名称!");
        $("#roleName").focus();
        return false;
    }
    if(roleName.trim()==""){
        layer.msg("请正确输入角色名称!");
        $("#roleName").val("");
        $("#roleName").focus();
        return false;
    }
    $("#addForm").ajaxSubmit({
        url: "role/addDo",
        datatype: "json",
        async: true,
        success: function (data) {
            if(data.obj=="success"){
                layer.msg("保存成功");
                topage("role/list")
            }else{
                layer.msg("保存失败!");
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            layer.msg("添加失败！");
            console.log("textStatus=" + textStatus);
            console.log("jqXHR.status=" + jqXHR.status);
            console.log("jqXHR.readyState=" + jqXHR.readyState);
        }
    });
})