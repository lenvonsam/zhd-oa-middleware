package zhd.oa.middleware.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import zhd.oa.middleware.innotation.Autowired;
import zhd.oa.middleware.service.KpiService;
import zhd.oa.middleware.utils.ReadExcelUtil;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class KpiController extends BaseController {
    private static String staticData = "";
    @Autowired
    private KpiService kpiService;
    
    @Override
    public void router() {
        get("/tes", (req, res) -> {

            //String[][] result = ReadExcelUtil.shareInstance().getData(file, 1);

            return "";
        });

        post("/OaUpload", (req, res) -> {
            HashMap<String, Object> result = new HashMap<>();
            req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
            //multipart/form-data请求获取其他参数需要二进制流转string
            Part uidPart = req.raw().getPart("uid");
            String uid = IOUtils.toString(uidPart.getInputStream());
            try (InputStream is = req.raw().getPart("file_data").getInputStream()) {
                BufferedInputStream in = new BufferedInputStream(is);
                List<String[]> resultData = ReadExcelUtil.shareInstance().getData(null, in, 1);
                log.info(">>>{}", JSON.toJSONString(resultData));
                result.put("list", resultData);
            }
            result.put("returnCode", 0);
            return JSONObject.toJSON(result);
        });

        post("/doCheckKpi", (req, res) -> {
            String uid = req.queryParams("uid");
        	String type = req.queryParams("type");
        	String data = req.queryParams("data");
        	String uid = req.queryParams("uid");
        	log.info(">>>type:{},data:{},uid:{}", type, data,uid);
        	
        	Map<String,String> map = kpiService.compareKpi(type, data , uid);
        	
        	String mainid = kpiService.getMainid(map.get("requestid"));
        	
        	String[] datas = data.split("\\$");
        	
        	for (int i = 0; i < datas.length; i++) {
				String[] kpis = datas[i].split("\\|");
				System.out.println("kpis.length==>"+kpis.length);
				kpiService.insertAndUpdateWorkflowDt(mainid, kpis, type);
			}
        	
        	return JSONObject.toJSON(map);
        	
        });
    }
    
}
