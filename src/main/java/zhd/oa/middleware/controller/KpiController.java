package zhd.oa.middleware.controller;

import static spark.Spark.*;

import java.io.BufferedInputStream;
import java.io.InputStream;


import javax.servlet.MultipartConfigElement;

import zhd.oa.middleware.utils.ReadExcelUtil;

public class KpiController extends BaseController {

	@Override
	public void router() {
		get("/tes", (req, res) -> {
			
			//String[][] result = ReadExcelUtil.shareInstance().getData(file, 1);
			
			return "";
		});
		
		post("/yourUploadPath", (req, res) -> {
			
			String callback = req.queryParams("jsoncallback");
			System.out.println(req.queryParams("aaa")+"==="+callback);
			
		    req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
		    try (InputStream is = req.raw().getPart("uploaded_file").getInputStream()) {
		    	BufferedInputStream in = new BufferedInputStream(is);
		    	String[][] result = ReadExcelUtil.shareInstance().getData(null,in, 1);
		    	
		    	int rowLength = result.length;
						       
			       for(int i=0;i<rowLength;i++) {
			    	   String res1 = result[i][1];
			    	   String res2 = result[i][2];
			    	   String res3 = result[i][3];
			    	   System.out.println(res1+"---------"+res2+"-------------"+res3);
			    	   for(int j=0;j<result[i].length;j++) {
				              //System.out.print(result[i][j]+"\t\t");
				           }
			    	   System.out.println();
			       }
			       System.out.println();
		    }
		    
		    return "File uploaded";
		});
		
	}

}
