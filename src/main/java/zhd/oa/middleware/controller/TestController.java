package zhd.oa.middleware.controller;

import spark.ModelAndView;
import spark.template.jade.JadeTemplateEngine;
import zhd.oa.middleware.innotation.Autowired;
import zhd.oa.middleware.service.ExcelService;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;

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
    }

}
