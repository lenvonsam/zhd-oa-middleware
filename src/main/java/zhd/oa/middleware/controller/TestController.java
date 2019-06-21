package zhd.oa.middleware.controller;

import static spark.Spark.get;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import spark.ModelAndView;
import spark.template.jade.JadeTemplateEngine;
import zhd.oa.middleware.innotation.Autowired;
import zhd.oa.middleware.service.ExcelService;
import zhd.oa.middleware.utils.ReadExcelUtil;

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
			String[][] result = ReadExcelUtil.shareInstance().getData(file,null, 1);
			  
			int rowLength = result.length;
			int count = 0;
			List<String> noSuccess = new ArrayList<String>();
					       
		       for(int i=0;i<rowLength;i++) {
//		    	   String outdate = result[i][3];
//		    	   String datee = result[i][4];
//		    	   String acceptNo = result[i][1];
		    	   
//		    	   System.out.println(acceptNo);
//		    	   System.out.println(datee);
//		    	   System.out.println(outdate);
		    	   
		    	   
		    	   //EXCEL 导入数据库功能
//		    	   int doRes = excelService.excelInsert(datee, outdate, acceptNo);
//		    	   System.out.println(doRes);
//		    	   count = count + doRes;
//		    	   if(0==doRes){
//		    		   noSuccess.add(acceptNo);
//		    	   }
		    	   
		    	   for(int j=0;j<result[i].length;j++) {
			              //System.out.print(result[i][j]+"\t\t");
			           }
		    	   System.out.println();
		       }
		       System.out.println("一共"+rowLength+"条,"+"成功"+count+"条,失败==>"+noSuccess );
			
			return "SUCCESS";
		});
		
		
		
	}

}
