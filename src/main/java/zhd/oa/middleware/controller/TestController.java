package zhd.oa.middleware.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import spark.ModelAndView;
import spark.template.jade.JadeTemplateEngine;
import zhd.oa.middleware.innotation.Autowired;
import zhd.oa.middleware.innotation.Value;
import zhd.oa.middleware.model.ErpTransRecord;
import zhd.oa.middleware.service.ErpTransRecordService;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class TestController extends BaseController {

	@Autowired
	private ErpTransRecordService transService;

	@Value(value = "zhd.erp.proxyhotel")
	private String xx;

	public void router() {
		get("/hello", (req, res) -> {
			Map<String, String> s = new HashMap<String, String>();
			try {
				ErpTransRecord r = transService.findOne(25);
				s.put("returnCode", r.getFlow_name());
				s.put("xx", xx);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return s;
		});

		get("/testView", (req, res) -> {
			System.out.println("test controller ");
			Map<String, Object> model = new HashMap<String, Object>();
			return new JadeTemplateEngine().render(new ModelAndView(model, "index"));
		});

        get("/testSend",(req,res) -> transService.send2Erp());

        //获取银行页面
        get("/bankView", (req, res) -> {
            String context = req.contextPath();
            System.out.println("contextPath:" + context);
            Map<String, Object> model = new HashMap<String, Object>();
            return new JadeTemplateEngine().render(new ModelAndView(model, "bankManage"));
        });

        //查询银行数据
        post("/queryBkLog","application/json",(req,res) -> {
            HashMap<String, Object> result = new HashMap<>();
            String currentPage = req.queryParams("currentPage");
            String pageSize = req.queryParams("pageSize");
            String content = req.queryParams("content");
            String startDate = req.queryParams("startDate");
            String endDate = req.queryParams("endDate");
            if (content.isEmpty()) content = null;
            if (startDate.isEmpty()) startDate = null;
            if (endDate.isEmpty()) endDate = null;
            //从第几条数据开始
            Integer firstIndex = (Integer.valueOf(currentPage) - 1) * Integer.valueOf(pageSize);
            //到第几条数据结束
            Integer lastIndex = Integer.valueOf(currentPage) * Integer.valueOf(pageSize) + 1;
            transService.findRecordList(result,firstIndex,lastIndex,content,startDate,endDate);
            result.put("returnCode", 0);
            return JSONObject.toJSON(result);
        });

        //重发银行数据
        post("/reSend",(req,res) -> {
            HashMap<String, Object> result = new HashMap<>();
            String content = req.queryParams("content");
            Integer returnCode = 0;
            String msg = "";
            if (content.isEmpty()) {
                returnCode = -1;
                msg = "重发内容为空";
            } else {
                String json = transService.send2Erp(content);
                Map map = JSON.parseObject(json, Map.class);
                returnCode = Integer.valueOf(map.get("success").toString());
                msg = map.get("msg").toString();
            }
            result.put("returnCode", returnCode);
            result.put("msg", msg);
            return JSONObject.toJSON(result);
        });
	}

}
