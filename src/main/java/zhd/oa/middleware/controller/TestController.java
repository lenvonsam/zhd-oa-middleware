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
	}

}
