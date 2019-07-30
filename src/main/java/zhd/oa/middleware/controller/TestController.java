package zhd.oa.middleware.controller;

import spark.ModelAndView;
import spark.template.jade.JadeTemplateEngine;
import zhd.oa.middleware.innotation.Autowired;
import zhd.oa.middleware.model.TestOne;
import zhd.oa.middleware.service.TestOneService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import static spark.Spark.get;

public class TestController extends BaseController {
    @Autowired
    private TestOneService testOneService;

    public void router() {
        get("/hello", (req, res) -> {
            throw new NullPointerException();
//			return "hello world 1";
        });

        get("/testView", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new JadeTemplateEngine().render(new ModelAndView(model, "index"));
        });
        
        get("/testone",(req, res) -> {
        	String sbills = req.queryParams("sbills");
        	String callback = req.queryParams("jsoncallback");
        	System.out.println(sbills);
        	System.out.println(callback);
        	List<TestOne> listTestOne = testOneService.getTestOne();
        	
        	String testOne = JSONObject.toJSON(listTestOne)+"";
        	System.out.println(testOne);
        	
        	return callback + "("+JSONObject.toJSON(listTestOne)+")";
        });
        
        
    }

}
