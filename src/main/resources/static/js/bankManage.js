var basicPath = $("#base_").val();
console.log("base path:>>>" + basicPath);
var pageSize = 8;
var page = 1;
var pageNum = 5;

getPageOfMemo(page);
// 分页函数
function getPageOfMemo(page) {
    var content = $('#search_content').val();
    var startDate = $('#start_date').val();
    var endDate = $('#end_date').val();
    var contentStr = "";
    var startDateStr = "";
    var endDateStr = "";
    if (content != null && content != "") contentStr = String(content);
    if (startDate != null && startDate != "") startDateStr = String(startDate);
    if (endDate != null && endDate != "") endDateStr = String(endDate);
    console.log('入参：{'+contentStr+','+startDateStr+','+endDateStr+'}');
    $.ajax({
        url : basicPath + "/queryBkLog",
        type : "POST",
        data:{
            "currentPage":page,
            "pageSize":pageSize,
            "content":contentStr,
            "startDate":startDateStr,
            "endDate":endDateStr
        },
        dataType : "json",
        success : function(data) {
            console.log("queryBkLog success")
            var total;
            if (data != null) {
                total = data.count;
                var htm = "";
                $.each(data.list, function(i, item) {
                    htm += "<tr>";
                    htm += "<td width='8%'>"+item.id+"</td>";
                    htm += "<td width='10%'>"+item.insert_date+"</td>";
                    htm += "<td id='td_content'>"+item.trans_content+"</td>";
                    htm += "<td width='10%'><button class='btn btn-primary' id='btn_resend' onclick='onClick()'>重发</button></td>";
                    htm += "</tr>";
                });
                $('#myTable tbody').html(htm); // 覆盖tbody部分
                var pages = Math.ceil(total/pageSize); // 计算总页数
                console.log("总数:"+total+",总页数:"+pages);
                if (pages != 0) {
                    var element = $('#pageButton');
                    var options = {
                        bootstrapMajorVersion : 3,
                        currentPage : page, // 当前页数
                        numberOfPages : pageNum, // 显示按钮的数量
                        totalPages : pages, // 总页数
                        itemTexts : function(type, page, current) {
                            switch (type) {
                            case "first":
                                return "首页";
                            case "prev":
                                return "上一页";
                            case "next":
                                return "下一页";
                            case "last":
                                return "末页";
                            case "page":
                                return page;
                            }
                        },
                        // 点击事件，用于通过Ajax来刷新整个list列表
                        onPageClicked : function(event, originalEvent, type, page) {
                            getPageOfMemo(page);
                        }
                    };
                    element.bootstrapPaginator(options);
                }
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            console.log(errorThrown);
        }
    });
};

$(function(){
    $("#btn_query").click(function(){
        getPageOfMemo(page);
    });
    $('#start_date').datetimepicker({
        language:"zh-CN", // 汉化
        todayBtn : "true",  // 显示今天按钮
        autoclose : true,   // 选择日期后自动关闭日期选择框
        todayHighlight : true,   // 当天高亮显示
        minView: 0,
        format: 'yyyy-mm-dd hh:ii',
        pickerPosition: "bottom-left"
    }).on('click',function(e){
        $("#start_date").datetimepicker("setEndDate", $("#end_date").val());
    });
    $('#end_date').datetimepicker({
        language:"zh-CN",
        todayBtn : "true",
        autoclose : true,
        todayHighlight : true,
        minView: 0,
        format: 'yyyy-mm-dd hh:ii',
        pickerPosition: "bottom-left"
    }).on('click',function(e){
        $("#end_date").datetimepicker("setStartDate", $("#start_date").val());
    });
    toastr.options.positionClass = 'toast-top-right';
    toastr.options.timeOut = '1000';
});

function onKeyDown(event){
    var e = event || window.event || arguments.callee.caller.arguments[0];
    if(e && e.keyCode==13){ // enter 键
        getPageOfMemo(page);
    }
};

function onClick(event){
    var e = e || window.event;
    var target = e.target || e.srcElement;
    if (target.parentNode.tagName.toLowerCase() == "td") {
        rowIndex = target.parentNode.parentNode.rowIndex;
        var content = document.getElementById("myTable").rows[rowIndex].cells[2].innerHTML
        var id = document.getElementById("myTable").rows[rowIndex].cells[0].innerHTML
        console.log("重发数据Id为："+ id);
        var contentStr = "";
        if (content != null && content != "") contentStr = String(content);
        resend(contentStr);
    }
};

function resend(content){
    $.ajax({
        url : basicPath + "/reSend",
        type : "POST",
        data:{
            "content":content
        },
        dataType : "json",
        success : function(data) {
            console.log("reSend success");
            if (data != null) {
                var returnCode = data.returnCode;
                var msg = data.msg;
                if(returnCode == 0) toastr.success(msg);
                if(returnCode == -1) toastr.error(msg);
            }
        },
         error: function(XMLHttpRequest, textStatus, errorThrown){
            console.log(errorThrown);
        }
    });
}