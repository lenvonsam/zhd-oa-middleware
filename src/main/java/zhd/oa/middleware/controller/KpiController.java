package zhd.oa.middleware.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import zhd.oa.middleware.innotation.Autowired;
import zhd.oa.middleware.model.EmpOA;
import zhd.oa.middleware.service.KpiService;
import zhd.oa.middleware.utils.FormaChecktUtil;
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
        	log.info(">>>type:{},data:{},uid:{}", type, data,uid);
        	
        	Map<String,String> map = new HashMap<String,String>();
        	
        	int checkRes = FormaChecktUtil.shareInstance().checkDatas(data, type);
        	log.info(checkRes+"<===checkRes");
        	if(checkRes==0){
        		String dept = kpiService.getDeptIdByUid(uid)+"";
        		map = kpiService.compareKpi(type, data , uid,dept);
        		log.info("map.toString()"+map.toString());
        		String mainid = kpiService.getMainid(map.get("requestid"));
        		log.info("mainid"+mainid);
        		if("0".equals(map.get("success"))){
        			String[] datas = data.split("\\$");
        			
        			for (int i = 0; i < datas.length; i++) {
        				String[] kpis = datas[i].split("\\|");
        				List<EmpOA> list = kpiService.getEmpsOA(kpis[2]);
        				kpis[2] = list.get(0).getUid()+"";
        				kpiService.insertAndUpdateWorkflowDt(mainid, kpis, type);
        			}
        		}
        		
        	}else{
        		map.put("success", "1");
				map.put("msg", "数据格式不正确！调整后请重新上传");
        	}
        	
        	log.info("map.toString()==>"+map.toString());
        	return JSONObject.toJSON(map);
        	
        });
    }
    
}

