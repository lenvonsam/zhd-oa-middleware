package zhd.oa.middleware.controller;

import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;

public class OaController extends BaseController{

    @Override
    public void router() {
        // 获取绩效页面
        get("/oaPerf", (req, res) -> {
            String uid = req.queryParams("uid");
            Map<String, Object> model = new HashMap<>();
            model.put("uid", uid == null ? "" : uid);
            return new VelocityTemplateEngine().render(new ModelAndView(model, "templates/oaPerf1.vm"));
        });

        // 测试freemarker
        get("/testFtl", (req, res) -> {
            String context = req.contextPath();
            Map<String, Object> model = new HashMap<>();
            model.put("projectPath", context == null ? "213" : context);
            Configuration cfg = new Configuration();
            cfg.setClassForTemplateLoading(this.getClass(), "/templates/");
            return new FreeMarkerEngine(cfg).render(new ModelAndView(model, "test.ftl"));
        });
    }
}
