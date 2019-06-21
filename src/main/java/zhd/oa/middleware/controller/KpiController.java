package zhd.oa.middleware.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import zhd.oa.middleware.utils.ReadExcelUtil;

import javax.servlet.MultipartConfigElement;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;

public class KpiController extends BaseController {

    @Override
    public void router() {
        get("/tes", (req, res) -> {

            //String[][] result = ReadExcelUtil.shareInstance().getData(file, 1);

            return "";
        });

        post("/OaUpload", (req, res) -> {
            String msg = "";
            int type = 0;
            String excelType = req.queryParams("excelType");
            if (excelType == null) {
                msg = "excel 类型不能为空...";
            } else {
                type = Integer.valueOf(excelType);
            }
            String callback = req.queryParams("jsoncallback");
            System.out.println(req.queryParams("aaa") + "===" + callback);

            req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
            try (InputStream is = req.raw().getPart("uploaded_file").getInputStream()) {
                BufferedInputStream in = new BufferedInputStream(is);
                List<String[]> result = ReadExcelUtil.shareInstance().getData(null, in, 1);

                log.info(">>>{}", JSON.toJSONString(result));
            }
            HashMap<String, Object> result = new HashMap<>();
            result.put("returnCode", 0);
            return JSONObject.toJSON(result);
        });

    }

}
