package zhd.oa.middleware.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import zhd.oa.middleware.innotation.Autowired;
import zhd.oa.middleware.service.KpiService;
import zhd.oa.middleware.utils.ReadExcelUtil;

import javax.servlet.MultipartConfigElement;
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
        	
        	String type = req.queryParams("type");
        	String data = req.queryParams("data");
        	String uid = req.queryParams("uid");
        	log.info(">>>type:{},data:{},uid:{}", type, data,uid);
        	
        	Map<String,String> map = kpiService.compareKpi(type, data , uid);
        	
        	
        	
        	return JSONObject.toJSON(map);
        	
        });
    }
    
}

