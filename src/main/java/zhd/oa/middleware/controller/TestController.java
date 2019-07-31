package zhd.oa.middleware.controller;

import static spark.Spark.get;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.template.jade.JadeTemplateEngine;

public class TestController extends BaseController {

	public void router() {
		get("/hello", (req, res) -> {
			// throw new NullPointerException();
			return "hello world 1";
		});

		get("/testView", (req, res) -> {
			Map<String, Object> model = new HashMap<String, Object>();
			return new JadeTemplateEngine().render(new ModelAndView(model, "index"));
		});

		get("/testone", (req, res) -> {
			String sbills = req.queryParams("sbills");
			String callback = req.queryParams("jsoncallback");
			System.out.println(sbills);
			System.out.println(callback);
			return callback + "()";
		});

	}

}
