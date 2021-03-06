package zhd.oa.middleware.controller;

import static spark.Spark.get;
import static spark.Spark.post;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.MultipartConfigElement;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import zhd.oa.middleware.innotation.Autowired;
import zhd.oa.middleware.service.KpiService;
import zhd.oa.middleware.utils.ReadExcelUtil;

public class KpiController extends BaseController {
	@Autowired
	private KpiService kpiService;

	@Override
	public void router() {
		get("/tes", (req, res) -> {

			// String[][] result = ReadExcelUtil.shareInstance().getData(file, 1);
			return "";
		});

		post("/OaUpload", (req, res) -> {
			
			HashMap<String, Object> result = new HashMap<>();
			req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
			// multipart/form-data请求获取其他参数需要二进制流转string
			
			String filename = req.queryParams("filename");
			try (InputStream is = req.raw().getPart("file_data").getInputStream() ) {
				BufferedInputStream in = new BufferedInputStream(is);
				List<String[]> resultData = ReadExcelUtil.shareInstance().getData(null, in, 1 , filename);
				log.info(">>>{}", JSON.toJSONString(resultData));
				result.put("list", resultData);
			}
			result.put("returnCode", 0);
			return JSONObject.toJSON(result);
		});

        post("/doCheckKpi", (req, res) -> {
            String uid = req.queryParams("uid");
        	String type = kpiService.getTypeByUid(uid);
        	String data = req.queryParams("data");
        	log.info(">>>type:{},data:{},uid:{}", type, data,uid);
        	
        	
        	Map<String,String> map = new HashMap<String,String>();
        	
//        	int checkRes = FormaChecktUtil.shareInstance().checkDatas(data, type);
//        	//checkRes ==0 未正常   大于0是异常的条数
//        	if(checkRes==0&&null!=uid&&!"1".equals(uid)){
//        		String dept = kpiService.getDeptIdByUid(uid)+"";
//        		map = kpiService.compareKpi(type, data , uid,dept);
//        		String mainid = kpiService.getMainid(map.get("requestid"));
//        		//map success是0时正常  进行插入数据
//        		if("0".equals(map.get("success"))){
//        			String[] datas = data.split("\\$");
//        			
//        			for (int i = 0; i < datas.length; i++) {
//        				String[] kpis = datas[i].split("\\|");
////        				List<EmpOA> list = kpiService.getEmpsOA(kpis[2]);
////        				kpis[2] = list.get(0).getUid()+"";
//        				kpiService.insertAndUpdateWorkflowDt(mainid, kpis, type);
//        			}
//        		}
//        		
//        	}else if(null==uid||"1".equals(uid)){
//        		map.put("success", "1");
//				map.put("msg", "用户不可用");
//        	}else{
//        		map.put("success", "1");
//				map.put("msg", "数据格式不正确！请上传正确的模板数据");
//			}
//			log.info("map.toString()==>" + map.toString());
        	map = kpiService.compareKpi(uid, data);
        	
        	if("0".equals(map.get("success"))){
        		kpiService.insertAndUpdateWorkflowDt(map, data, type);
        	}
        	
			return JSONObject.toJSON(map);
		});
	}

}
