$(function () {
    initTable()
    $("#tableMessage").css("display","block");
    $("#orderType").select2({
        placeholder:'请选择订单类型',
        allowClear:true
    });
    $("#orderStatus").select2({
        placeholder:'请选择订单状态',
        allowClear:true
    });
})

//双击进入详情页
function toDetail(id){
    layer.msg("暂无详情页内容")
}

//初始化表格
function initTable() {
    var table = $("#table1")
    table.DataTable({
        "language": {
            "aria": {
                "sortAscending": ": activate to sort column ascending",
                "sortDescending": ": activate to sort column descending"
            },
            "emptyTable": "没有相关记录！",
            "info": "显示 _START_ 到 _END_ 总共 _TOTAL_ 条记录",
            "infoEmpty": "无记录！",
            "infoFiltered": "(过滤1 from _MAX_ 条记录)",
            "lengthMenu": "展示条数  _MENU_",
            "search": "字典名称:",
            "zeroRecords": "无记录！",
            "paginate": {
                "previous": "上一页",
                "next": "下一页",
                "last": "Last",
                "first": "First"
            }
        },

        "ordering": false, //禁用排序
        "searching":false, //禁用查询
        "bStateSave": true, // save datatable state(pagination, sort, etc) in cookie.

        "lengthMenu": [
            [5,10, 15, 20, -1],
            [5,10, 15, 20, "All"] // change per page values here
        ],
        // set the initial value
        "pageLength": 5    ,
        /*"pagingType": "bootstrap_full_number",*/
        "columnDefs": [
            {
                "searchable": false,
                "targets": [0]
            }
        ]
    });
    table.find('.group-checkable').change(function () {
        var set = jQuery(this).attr("data-set");
        var checked = jQuery(this).is(":checked");
        jQuery(set).each(function () {
            if (checked) {
                $(this).prop("checked", true);
                $(this).parents('tr').addClass("active");
            } else {
                $(this).prop("checked", false);
                $(this).parents('tr').removeClass("active");
            }
        });
    });

    table.on('change', 'tbody tr .checkboxes', function () {
        $(this).parents('tr').toggleClass("active");
    });

    table.on('dblclick', 'tbody tr', function () {
        var clientId = $(this).find("input.checkboxes[name='selectedIds']").val();
        toDetail(clientId);
    });
}

//条件查询
function selectByExample() {
    $("#seFrom").ajaxSubmit({
        url: "order/list",
        async: true,
        success: function (data) {
            $("#contentdiv").html(data)
        }
    });
}

//重置
function reSetSeacrch() {
    $("#clientName").val("");
    $("#orderType").val(null).trigger("change");
    $("#orderStatus").val(null).trigger("change");
}

//批量删除客户
function batchDelete() {
    var count = 0;
    var orderIds = $("#orderIds").val();
    $("input.checkboxes[name='selectedIds']:checkbox").each(function() {
        if ($(this).is(":checked")) {
            count++;
            if (orderIds != "" && typeof (orderIds) != "undefined") {
                orderIds += "," + $(this).val();
            } else {
                orderIds = $(this).val();
            }
        }
    })
    if(count!="0"){
        layer.confirm("是否删除？", {title: "删除确认"}, function (index) {
            layer.close(index);
            var url = "order/delete";
            var s = CommnUtil.ajax(url, {
                orderIds: orderIds
            }, "json");
            if ("success"== s) {
                layer.msg('删除成功');
                topage("order/list")
            }else if("statusError"==s){
                layer.msg("订单状态无法删除!")
            }else{
                layer.msg("删除失败");
            }
        });
    }else{
        layer.msg("请选择操作项")
    }
}

//删除客户
function deleteOrderById(orderId) {
    layer.confirm("是否删除？", {title: "删除确认"}, function (index) {
        layer.close(index);
        var url = "order/delete";
        var s = CommnUtil.ajax(url, {
            orderIds: orderId
        }, "json");
        if ("success"== s) {
            layer.msg('删除成功');
            topage("order/list")
        }else if("statusError"==s){
            layer.msg("订单状态无法删除!")
        }else{
            layer.msg("删除失败");
        }
    });
}

//前往编辑客户页面
function editorOrder(orderId) {
    var data = {orderId:orderId,userId:$("#currentUserId").val()};
    toedit(data)
}
function toedit(data) {
    var index = -1;
    var url = "order/edit";
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