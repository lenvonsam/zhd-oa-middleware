package zhd.oa.middleware.controller;

import static spark.Spark.get;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

import zhd.oa.middleware.innotation.Autowired;
import zhd.oa.middleware.model.OutSbills;
import zhd.oa.middleware.service.OutSbillsService;

public class WorkflowController extends BaseController{
	@Autowired
	private OutSbillsService outSbillsService;
	
	@Override
	public void router() {

        get("/outSbills",(req, res) -> {
        	String sbills = req.queryParams("sbills");
        	String bm = req.queryParams("bm");
        	String callback = req.queryParams("jsoncallback");
        	System.out.println(sbills+"---"+bm);
        	System.out.println(callback);
        	
        	List<OutSbills> listOutSbills = null;
        	if("31".equals(bm)||"201".equals(bm)){
        		listOutSbills = outSbillsService.getOutSbillsCode(sbills);
        	}else{
        		listOutSbills = outSbillsService.getOutSbills(sbills);
        	}
        	
        	String listOutSbill = JSONObject.toJSON(listOutSbills)+"";
        	System.out.println(listOutSbill);
        	System.out.println(listOutSbills.size()+"---size");
        	
        	return callback + "("+JSONObject.toJSON(listOutSbills)+")";
        });
		
	}

}
