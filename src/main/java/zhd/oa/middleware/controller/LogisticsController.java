package zhd.oa.middleware.controller;

import com.alibaba.fastjson.JSONObject;
import zhd.oa.middleware.innotation.Autowired;
import zhd.oa.middleware.service.LogisticsWorkService;

import java.util.Map;

import static spark.Spark.post;

public class LogisticsController extends BaseController{

    @Autowired
    private LogisticsWorkService logisticsWorkService;
    @Override
    public void router() {

        /**
         * 简易版4.3调车流程
         */
        post("/subLogisticsWork",(req,res) -> {
            log.info(req.body());
            JSONObject dataJSONObject= JSONObject.parseObject(req.body());

            /**
             *不提交流程的情况
             */
//            Map resultMap = logisticsWorkService.insetLogisticsWorkDetail(JSONObject.parseObject(req.body()));

            /**
             * 需要提交流程的情况
             */
            Map resultMap = logisticsWorkService.subLogisticsWork(dataJSONObject);
            log.info(JSONObject.toJSONString(resultMap));
            return JSONObject.toJSON(resultMap);
        });


    }


}
