package zhd.oa.middleware.controller;

import spark.ModelAndView;
import spark.template.jade.JadeTemplateEngine;
import zhd.oa.middleware.innotation.Autowired;
import zhd.oa.middleware.service.ExcelService;
import zhd.oa.middleware.utils.ReadExcelUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;

public class TestController extends BaseController {
    @Autowired
    private ExcelService excelService;

    public void router() {
        get("/hello", (req, res) -> {
            throw new NullPointerException();
//			return "hello world 1";
        });

        get("/testView", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new JadeTemplateEngine().render(new ModelAndView(model, "index"));
        });

        get("/testView1", (req, res) -> {

            String path = req.queryParams("filePath");
            System.out.println(path);

//			File file = new File("C:\\Users\\conqueror\\Desktop\\Office\\Excel.xls");
            File file = new File(path);
            List<String[]> result = ReadExcelUtil.shareInstance().getData(file, null, 1);

            int rowLength = result.size();
            int count = 0;
            List<String> noSuccess = new ArrayList<>();

            for (int i = 0; i < rowLength; i++) {
                String[] rowData = result.get(i);
                for (int j = 0; j < rowData.length; j++) {
                    //System.out.print(result[i][j]+"\t\t");
                }
                System.out.println();
            }
            System.out.println("一共" + rowLength + "条," + "成功" + count + "条,失败==>" + noSuccess);

            return "SUCCESS";
        });


    }

}
