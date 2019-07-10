$(function() {
  var tableColumn = [];
  var excelType = 0;
  var uid = $("#uid")
    .html()
    .trim();
  // 初始化数据格式
  var columns_yx = [
    "年份",
    "月度/季度/年度",
    "姓名",
    "高达销量",
    "销量变化量",
    "其他吨位",
    "实际销量",
    "销量任务量",
    "销量得分",
    "高达高卖",
    "高卖变化量",
    "应扣费用",
    "实际高卖",
    "高卖任务量",
    "高卖得分",
    "加分",
    "加分原因",
    "减分",
    "减分原因",
    "总得分"
  ];
  var columns_xy = [
    "年份",
    "月度/季度/年度",
    "姓名",
    "加分",
    "加分原因",
    "减分",
    "减分原因",
    "总得分"
  ];
  var columns_cc = [
    "年份",
    "月度/季度/年度",
    "姓名",
    "库区",
    "加分",
    "加分原因",
    "减分",
    "减分原因",
    "年绩效得分基数",
    "附加分",
    "附加分原因",
    "总得分"
  ];
  var columns_cg = [
    "年份",
    "月度/季度/年度",
    "姓名",
    "高达销量",
    "销量变化量",
    "其他吨位",
    "实际销量",
    "销量任务量",
    "销量得分",
    "本月平均价格",
    "本月考核价格",
    "本月平均库存",
    "库存范围",
    "考核吨位",
    "库存得分",
    "加分",
    "加分原因",
    "减分",
    "减分原因",
    "总得分"
  ];
  var columns_jtfz = [
    "年份",
    "月度/季度/年度",
    "姓名",
    "高达销量",
    "销量变化量",
    "其他吨位",
    "实际销量",
    "销量任务量",
    "销量得分",
    "高达高卖",
    "高卖变化量",
    "应扣费用",
    "实际高卖",
    "高卖任务量",
    "高卖得分",
    "加分",
    "加分原因",
    "减分",
    "减分原因",
    "总得分"
  ];
  var columns_cpzj = [
    "年份",
    "月度/季度/年度",
    "姓名",
    "高达销量",
    "销量变化量",
    "其他吨位",
    "实际销量",
    "销量任务量",
    "销量得分",
    "高达高卖",
    "高卖变化量",
    "应扣费用",
    "实际高卖",
    "高卖任务量",
    "高卖得分",
    "加分",
    "加分原因",
    "减分",
    "减分原因",
    "总得分"
  ];
  var columns_yxfz = [
    "年份",
    "月度/季度/年度",
    "姓名",
    "高达销量",
    "销量变化量",
    "其他吨位",
    "实际销量",
    "销量任务量",
    "销量得分",
    "高达高卖",
    "高卖变化量",
    "应扣费用",
    "实际高卖",
    "高卖任务量",
    "高卖得分",
    "加分",
    "加分原因",
    "减分",
    "减分原因",
    "总得分"
  ];

  //初始化上传控件的样式
  var upload = $("#upload_file");
  upload.fileinput({
    language: "zh", //设置语言
    uploadUrl: "/OaUpload", //上传的地址
    allowedFileExtensions: ["xlsx", "xls"], //接收的文件后缀
    showUpload: true,
    showRemove: false,
    showClose: false,
    uploadExtraData: function() {
      return {
        excelType: excelType,
        uid: uid,
        filename: $('.file-caption-name').attr('title')
      };
    },
    layoutTemplates: {
      actionDelete: ""
    },
    browseClass: "btn btn-primary"
  });
  // 选择文件后的处理
  upload.on("filebatchselected", function(event, previewId, index) {
    if (setData()) {
      createTableThead();
    } else {
      alert("请选择数据类型");
      location.reload();
    }
  });

  // 文件移除
  upload.on("fileremoved", function(event) {
    console.log("file remove");
    console.log(event);
  });

  // 点击上传后的处理
  upload.on("fileuploaded", function(event, data, previewId, index) {
    if (data.response.returnCode === 0) {
      // 处理数据,生成table
      var bodyHtm = "";
      $.each(data.response.list, function(idx, item) {
        bodyHtm += "<tr style='text-align: center'>";
        $.each(item, function(i, v) {
          var tmpName = "row-" + idx + "-" + i;
          bodyHtm +=
            "<td><input type='text' class='text-center' id='" +
            tmpName +
            "' name='" +
            tmpName +
            "' value='" +
            v +
            "'></td>";
        });
        bodyHtm += "</tr>";
      });
      $("#table-body table tbody").html(bodyHtm); //覆盖tbody部分
    }
    $("#btn_check").removeAttr("disabled");
  });

  $("#btn_clear").click(function() {
    location.reload();
  });

  $("#btn_check").click(function() {
    var res = "";
    $(".table tbody tr").each(function(trIdx, trItem) {
      //遍历每一行
      res += "$";
      $(trItem)
        .find("input")
        .each(function(tdIdx, tdItem) {
          // debugger
          var tdValue = $(tdItem).val();
          if (tdValue === undefined || tdValue == null) {
            res += "";
          } else {
            res += tdValue;
          }
          res += "|";
        });
      res = res.substring(0, res.length - 1);
    });
    checkData(res.substring(1, res.length));
  });

  function checkData(reqData) {
    $.ajax({
      url: "/doCheckKpi",
      type: "POST",
      data: {
        type: excelType,
        uid: uid,
        data: reqData
      },
      dataType: "json",
      success: function(data) {
        var code = data.success;
        if (code == "0") {
          alert("校验成功，相关流程已经在OA自动创建");
          location.reload();
        } else {
          alert(data.msg);
        }
      }
    });
  }

  function setData() {
    var selectDept = $("#select_dept").val();
    if (selectDept.indexOf("请选择数据类型") > 0) {
      return false;
    } else {
      switch (selectDept) {
        case "营销中心":
          excelType = 0;
          tableColumn = columns_yx;
          break;
        case "型云等公司":
          excelType = 1;
          tableColumn = columns_xy;
          break;
        case "仓储部门":
          excelType = 2;
          tableColumn = columns_cc;
          break;
        case "采购部门":
          excelType = 3;
          tableColumn = columns_cg;
          break;
        case "集团常务副总":
          excelType = 4;
          tableColumn = columns_jtfz;
          break;
        case "产品资源中心总监":
          excelType = 5;
          tableColumn = columns_cpzj;
          break;
        case "营销中心副总监":
          excelType = 6;
          tableColumn = columns_yxfz;
          break;
        default:
          excelType = 0;
          tableColumn = columns_yx;
      }
      return true;
    }
  }

  function createTableThead() {
    var theadHtm = "";
    theadHtm += "<tr class='thead'>";
    $.each(tableColumn, function(idx, item) {
      theadHtm += "<th class='text-center'>" + item + "</th>";
    });
    theadHtm += "</tr>";
    $("#table-body table thead").html(theadHtm); //覆盖thead部分
  }
});
