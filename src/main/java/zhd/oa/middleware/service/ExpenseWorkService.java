package zhd.oa.middleware.service;

import com.alibaba.fastjson.JSONObject;
import zhd.oa.middleware.mapper.ExpenseWorkMapper;
import zhd.oa.middleware.utils.CreateExpenseWorkUtil;

import java.util.HashMap;
import java.util.Map;

public class ExpenseWorkService extends BaseService {

    private ExpenseWorkMapper expenseWorkMapper;

    public Map createExpenseWork(JSONObject jsonObject){

        Map resMap = new HashMap();
        int code = 0; //操作结果0是失败  1是成功
        String msg = "";

        try {
            //读取私车公用流程创建费用报销流程所要的数据
            String creatorid = jsonObject.getString("creatorid");//创建人
            String applyDept = jsonObject.getString("applyDept");//申请部门
            String belongDept = jsonObject.getString("belongDept");//归属部门
            String paymethod = jsonObject.getString("paymethod");//付款方式
            String totalMoney = jsonObject.getString("totalMoney");//报销总金额
            String receiptBank = jsonObject.getString("receiptBank");//收款开户行
            String receiptAccount = jsonObject.getString("receiptAccount");//收款账号
            String receiptCompany = jsonObject.getString("receiptCompany");// 收款单位
            String remark = jsonObject.getString("remark");// 备注
            String payerCompany = "";//付款单位  暂时为空

            String dateFrom = jsonObject.getString("dateFrom");//出发日期
            String placeStart = jsonObject.getString("placeStart");//出发地
            String placeEnd = jsonObject.getString("placeEnd");//目的地
            String placeTo = jsonObject.getString("placeTo");//出发途径
            String placeBack = jsonObject.getString("placeBack");//回来途径
            String mileage = jsonObject.getString("mileage");//行驶里程数
            String parkingFee = jsonObject.getString("parkingFee");//停车费
            String toll = jsonObject.getString("toll");//过路费
            String fuelFee = jsonObject.getString("fuelFee");//燃油费
            String visitCustomer = jsonObject.getString("visitCustomer");//拜访客户
            String flowid = jsonObject.getString("flowid");//私车流程id

            //创建费用报销流程
            String requestid = CreateExpenseWorkUtil.shareInstance().createExpenseWork(creatorid, applyDept, belongDept,
                    paymethod, receiptBank, receiptAccount, receiptCompany,
                    totalMoney, "【私车公用流程系统触发报销】" + remark, payerCompany);

            if(Integer.parseInt(requestid) > 0){
                session = openSession();
                expenseWorkMapper = session.getMapper(ExpenseWorkMapper.class);
                String mainid = expenseWorkMapper.getMainid(requestid);

                String expenseType = "82_145"; //该处油费固定为车补
                String expenseMoney = fuelFee;
                String expenseDate = dateFrom;
                String expenseDetail = placeStart + " --> " + placeTo + " --> " + placeEnd + " --> " + placeBack + " 行驶里程数 " + mileage;
                String expenseForCompany = visitCustomer;

                boolean detail1 = expenseWorkMapper.insertExpenseFeeDetail(mainid,expenseType,expenseMoney,expenseDate,expenseDetail,expenseForCompany,flowid);
                log.info("私车公用流程系统创建费用报销接口：插入明细车补执行结果--->"+detail1);
                if(!"".equals(parkingFee)){
                    boolean detail2 = expenseWorkMapper.insertExpenseFeeDetail(mainid,"82_156",parkingFee,expenseDate,expenseDetail,expenseForCompany,flowid);
                    log.info("私车公用流程系统创建费用报销接口：插入明细停车费执行结果--->"+detail2);
                }
                if(!"".equals(toll)){
                    boolean detail3 = expenseWorkMapper.insertExpenseFeeDetail(mainid,"82_155",toll,expenseDate,expenseDetail,expenseForCompany,flowid);
                    log.info("私车公用流程系统创建费用报销接口：插入明细过路费执行结果--->"+detail3);
                }
                code = 1;
                msg = "私车公用流程系统创建费用报销接成功!";
            }else{
                msg = "系统创建流程异常!";
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "接口代码异常!";
        } finally {
            closeSession();
        }

        resMap.put("success",code);
        resMap.put("msg",msg);

        return resMap;
    }




}
