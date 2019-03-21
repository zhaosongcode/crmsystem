$(function () {
    $("#addUserId").val($("#currentUserId").val())
    $("#clientGender").select2({
        placeholder:'请选择客户性别',
        allowClear:true
    });
    $("#clientGender").val(null).trigger("change");
    $("#clientProvince").select2({
        placeholder:'请选择客户所在省份',
        allowClear:true
    });
    $("#clientProvince").val(null).trigger("change");
    $("#clientCity").select2({
        language: {
            noResults: function (params) {
                return "请先选择省份";
            }
        },
        placeholder:'请选择客户所在城市',
        allowClear:true
    });
    $("#clientCity").val(null).trigger("change");
    $("#clientCountry").select2({
        language: {
            noResults: function (params) {
                return "请先选择城市";
            }
        },
        placeholder:'请选择客户所在县市',
        allowClear:true
    });
    $("#clientCountry").val(null).trigger("change");
    $("#clientType").select2({
        placeholder:'请选择客户类型',
        allowClear:true
    });
    $("#clientType").val(null).trigger("change");
})

$("#clientProvince").on('change',function () {
   var provinceCode = $(this).val();
   if(provinceCode != null){
       $("#clientCity").empty();
        for(var i = 0;i<address.length;i++){
            var proCode = address[i].provinceCode;
            if(provinceCode==proCode){
                $("#clientProvinceName").val(address[i].provinceName);
                var citys = address[i].cities;
                var str = "";
                for(var i = 0;i<citys.length;i++){
                    str+="<option value='"+citys[i].cityCode+"'>"+citys[i].cityName+"</option>"
                }
                $("#clientCity").append(str);
                $("#clientCity").val(null).trigger("change");
            }
        }
   }else{
       $("#clientProvinceName").val("");
       $("#clientCityName").val("");
       $("#clientCountryName").val("");
       $("#clientCity").val(null).trigger("change");
       $("#clientCity").empty();
       $("#clientCountry").val(null).trigger("change");
       $("#clientCountry").empty();
   }
});
$("#clientCity").on('change',function () {
    var cityssCode = $(this).val();
    if(cityssCode != null){
        $("#clientCountry").empty();
        for(var i = 0;i<address.length;i++){
            var citys = address[i].cities;
            for(var j = 0;j<citys.length;j++){
                var ct = citys[j];
                var cCode = ct.cityCode;
                if(cCode==cityssCode){
                    $("#clientCityName").val(ct.cityName);
                    var countrys = ct.countries
                    var sstr = "";
                    for(var k = 0;k<countrys.length;k++){
                        sstr+="<option value='"+countrys[k].countryCode+"'>"+countrys[k].countryName+"</option>"
                    }
                    $("#clientCountry").append(sstr);
                    $("#clientCountry").val(null).trigger("change");
                }
            }
        }
    }else{
        $("#clientCityName").val("");
        $("#clientCountryName").val("");
        $("#clientCountry").val(null).trigger("change");
        $("#clientCountry").empty();
    }
});

$("#clientCountry").on('change',function () {
    var countrCode = $(this).val();
    if(countrCode!=null){
        for(var i = 0;i<address.length;i++){
            var citys = address[i].cities;
            for(var j = 0;j<citys.length;j++){
                var countrys = citys[j].countries;
                for(var k = 0;k<countrys.length;k++){
                    var counCode = countrys[k].countryCode;
                    if(countrCode==counCode){
                        var countrName = countrys[k].countryName;
                        $("#clientCountryName").val(countrName);
                    }
                }
            }
        }
    }else{
        $("#clientCountryName").val("");
    }
})
$("#seledIcon").on("change", function () {
    if ($('#seledIcon').val().length == 0) {
        return;
    }
    // 前端判断选择的文件是否符合要求
    if (!checkFile()) {
        // 重置file input
        $('#seledIcon').replaceWith($('#seledIcon').val('').clone(true));
        return;
    }
    // 上传图片到项目临时目录下
    var url = "client/upLoadImage";
    var icon = $('#seledIcon')[0].files[0];
    var fd = new FormData();
    fd.append('icon', icon);
    $.ajax({
        url: url,
        type: "post",
        //Form数据
        data: fd,
        cache: false,
        async: true,
        contentType: false,
        processData: false,
        success: function (data) {
            if (data.code == "0") {
                // 上传成功data为图片路径
                var clientIcon = data.obj;
                //本地测试
                //var imgClientIcon = 'http://localhost:8001/crmsystem/upload/' + clientIcon;
                //服务器
                var imgClientIcon = 'http://106.13.115.228:8001/crmsystem/upload/' + clientIcon;
                $('#imgClientIcon').attr('src', imgClientIcon);
                $("#seledIconName").val(clientIcon)
            } else if (data.obj == "fileIsEmpty") {
                layer.msg("图片内容为空！");
            } else if (data.obj == "formatError") {
                layer.msg("请检查图片格式是否为jpg！");
            } else if (data.obj == "sizeTooBig") {
                layer.msg("图片大小超出512KB！");
            } else if (data.obj == "sizeError") {
                layer.msg("图片尺寸必须为60*60！");
            } else {
                layer.msg("上传图片失败");
            }
        },
        error: function () {
            layer.msg("上传图片失败，后台系统故障");
        }
    });
    // 重置file input
    $('#seledIcon').replaceWith($('#seledIcon').val('').clone(true));
});
$("#imgClientIcon").on("click", function () {
    $("#seledIcon").trigger("click");
});

//前台判断图片
function checkFile() {
    var maxsize = 512 * 1024;
    var errMsg = "图片大小超出512KB！！！";
    var browserCfg = {};
    var ua = window.navigator.userAgent;
    if (ua.indexOf("MSIE") >= 1) {
        browserCfg.ie = true;
    } else if (ua.indexOf("Firefox") >= 1) {
        browserCfg.firefox = true;
    } else if (ua.indexOf("Chrome") >= 1) {
        browserCfg.chrome = true;
    }

    var obj_file = $('#seledIcon')[0];
    var fileSize = 0;
    var fileType;
    if (browserCfg.firefox || browserCfg.chrome) {
        fileSize = obj_file.files[0].size;
        if (fileSize > maxsize) {
            layer.msg(errMsg);
            return false;
        }
        var fileName = obj_file.files[0].name;
        fileType = fileName.slice(fileName.lastIndexOf(".") + 1).toLowerCase();
        if (fileType != "jpg") {
            layer.msg("请检查图片格式是否为jpg！");
            return false;
        }
    } else if (browserCfg.ie) {
        // ie浏览器，获取不到filesize，暂时通过前台验证，转到后台处理
    } else {
        // 其它类型的浏览器，暂时通过前台验证，转到后台处理
    }
    return true;
}

//保存用户
$("#saveNewClient").on('click',function () {
    if(!checkClient()){
        return false;
    }
    $("#addForm").ajaxSubmit({
        url: "client/addClient",
        datatype: "json",
        async: true,
        success: function (data) {
            data = JSON.parse(data);
            if (data.code=="0") {
                layer.msg("添加成功!");
                topage("client/list")
            }
            else {
                layer.msg("添加失败！");
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
//前端对数据验证
function checkClient() {
    var clientName = $("#clientName").val();
    var clientGender = $("#clientGender").val();
    var clientMobelPhone = $("#clientMobelPhone").val();
    var clientMobelPhoneRegular = /^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\d{8}$/;
    var clientPhone = $("#clientPhone").val();
    var clientEnePhone = $("#clientEnePhone").val();
    var clientEnePhoneRegular = /^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\d{8}$/;
    var clientBirthday = $("#clientBirthday").val();
    var clientBirthdayRegular = /^(19|20)\d{2}-(1[0-2]|0?[1-9])-(0?[1-9]|[1-2][0-9]|3[0-1])$/;
    var clientEmail = $("#clientEmail").val();
    var clientEmailRegular = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
    var clientCredit = $("#clientCredit").val();
    var clientProvinceName = $("#clientProvinceName").val();
    var clientCityName = $("#clientCityName").val();
    var clientCountryName = $("#clientCountryName").val();
    var clientDetail = $("#clientDetail").val();
    var clientType = $("#clientType").val();
    var seledIconName = $("#seledIconName").val();
    var clientAge = $("#clientAge").val();
    if(CommnUtil.notNull(clientName)){
        if(CommnUtil.isSpecial(clientName)){
            layer.msg("用户姓名不能包含特殊字符！");
            $("#clientName").val("");
            $("#clientName").focus();
            return false;
        }
        if(CommnUtil.notNull(clientGender)){
            if(CommnUtil.notNull(clientMobelPhone)){
                if(!clientMobelPhoneRegular.test(clientMobelPhone)){
                    layer.msg("请正确输入手机号！");
                    return false;
                }
                if(CommnUtil.notNull(clientEmail)&&clientEmailRegular.test(clientEmail)){
                    if(!clientEmailRegular.test(clientEmail)){
                        layer.msg("请正确输入邮箱！");
                        return false;
                    }
                    if(CommnUtil.notNull(clientProvinceName)&&CommnUtil.notNull(clientCityName)&&CommnUtil.notNull(clientCountryName)&&CommnUtil.notNull(clientDetail)){
                        if(CommnUtil.notNull(clientType)){
                            if(CommnUtil.notNull(clientAge)){
                                var ageRe = /^([1-9]\d*|[0]{1,1})$/;
                                if(!ageRe.test(clientAge)){
                                    layer.msg("请正确输入用户年龄信息!")
                                    return false;
                                }
                                if(CommnUtil.notNull(seledIconName)){
                                    return true;
                                }else{
                                    layer.msg("请选择上传图标")
                                    return false;
                                }
                            }else{
                                layer.msg("请输入年龄信息!")
                            }
                        }else{
                            layer.msg("请选择客户类型！")
                            return false;
                        }
                    }else{
                        layer.msg("请完善客户地址信息！")
                        return false;
                    }
                }else{
                    layer.msg("请填写客户邮箱！")
                    $("#clientEmail").val("");
                    $("#clientEmail").focus();
                    return false;
                }
            }else{
                layer.msg("请填写客户手机！")
                $("#clientMobelPhone").val("");
                $("#clientMobelPhone").focus();
                return false;
            }
        }else{
            layer.msg("请选择客户性别！")
            return false;
        }
    }else{
        layer.msg("请填写客户姓名！");
        $("#clientName").val("");
        $("#clientName").focus();
        return false;
    }
}