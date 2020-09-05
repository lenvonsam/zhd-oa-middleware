package zhd.oa.middleware.controller;

import static spark.Spark.get;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

import zhd.oa.middleware.innotation.Autowired;
import zhd.oa.middleware.model.OutSbills;
import zhd.oa.middleware.model.PropertyBasic;
import zhd.oa.middleware.service.*;

public class WorkflowController extends BaseController{
	@Autowired
	private OutSbillsService outSbillsService;
	@Autowired
	private PropertyService propertyService; 
	@Autowired
	private ExpenseReimburseService expenseReimburseService;
	@Autowired
	private LogisticsSbillInfoService logisticsSbillInfoService;
	@Autowired
	private LogisticsOutSbillService logisticsOutSbillService;
	
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

        get("/getBasicProperty",(req,res) -> {
        	String callback = req.queryParams("jsoncallback");
        	String nos = req.queryParams("nos");
        	
        	System.out.println(nos);

        	List<PropertyBasic> propertyBasics = propertyService.getPropertyBasic(nos);
//        	List<PropertyBasic> propertyBasics = null;
        	System.out.println(propertyBasics);
        	System.out.println("size"+propertyBasics.size());
        	
        	return callback + "("+JSONObject.toJSON(propertyBasics)+")";
        });
        
        get("/createExpenseReimburse",(req,res) -> {
        	
        	String callback = req.queryParams("jsoncallback");
        	String requestid = req.queryParams("dt");
        	
        	System.out.println(requestid);

        	expenseReimburseService.createExpenseReimburse(requestid);
        	
        	//return callback + "("+JSONObject.toJSON(propertyBasics)+")";
        	return "SUCCESS === " + callback ;
        	
        });
        
        get("/createTest",(req,res) -> {
        	
        	String callback = req.queryParams("jsoncallback");
        	String dt = req.queryParams("dt");
        	
        	System.out.println(dt);

        	//return callback + "("+JSONObject.toJSON(propertyBasics)+")";
        	return "SUCCESS === " + callback ;
        	
        });
		/**
		 * 调车流程查询明细表1数据
		 */
        get("/logisticsSbillInfo",(request, response) -> {

			String callback = request.queryParams("jsoncallback");
			String sbillCodes = request.queryParams("sbillCodes");
			System.out.println("callback-->"+callback+"sbillCode-->"+sbillCodes);
			return callback + "(" + JSONObject.toJSON(logisticsSbillInfoService.getListLogisticsSbillInfo(sbillCodes)) + ")";
		});

		/**
		 * 调车流程查询明细表2出库数据
		 */
		get("/logisticsOutSbill",(request, response) -> {
			String callback = request.queryParams("jsoncallback");
			String codes = request.queryParams("codes");
			System.out.println("callback-->"+callback+"codes-->"+codes);
			return callback + "(" + JSONObject.toJSON(logisticsOutSbillService.getListLogisticsOutSbill(codes)) + ")";
		});
        
        
        
	}

}
