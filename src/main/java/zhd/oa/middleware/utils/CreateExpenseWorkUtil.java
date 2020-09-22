package zhd.oa.middleware.utils;

import cn.com.weaver.services.webservices.WorkflowServicePortTypeProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weaver.workflow.webservices.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import static zhd.oa.middleware.utils.HttpUtil.OAPROXYURL;

public class CreateExpenseWorkUtil {

    private static Logger log = LoggerFactory.getLogger(CreateExpenseWorkUtil.class);
    private static CreateExpenseWorkUtil obj = null;

    public static CreateExpenseWorkUtil shareInstance(){
        if(null == obj){
            obj = new CreateExpenseWorkUtil();
        }
        return obj;
    }

    /**
     * 创建费用报销主流程
     * @param creatorid 流程创建人 --费用报销的申请人
     * @param applyDept 申请部门
     * @param belongDept 归属部门
     * @param paymethod 支付方式
     * @param receiptBank 收款行
     * @param receiptAccount 收款账号
     * @param receiptCompany 收款单位
     * @param money 报销总金额
     * @param remark 备注
     * @param payerCompany 付款单位
     * @return
     */
    public String createExpenseWork(String creatorid,String applyDept,String belongDept,
                                   String paymethod ,String receiptBank,String receiptAccount,String receiptCompany,
                                   String money ,String remark ,String payerCompany,String flowid) throws Exception {

        String requestid = "";
        WorkflowRequestTableField[] properties = new WorkflowRequestTableField[13];

        properties[0] = new WorkflowRequestTableField();
        properties[0].setFieldName("applicant");//
        properties[0].setFieldValue(creatorid);//
        properties[0].setView(true);//字段是否可见
        properties[0].setEdit(true);//字段是否可编辑

        properties[1] = new WorkflowRequestTableField();
        properties[1].setFieldName("apply_dept");//
        properties[1].setFieldValue(applyDept);//
        properties[1].setView(true);//字段是否可见
        properties[1].setEdit(true);//字段是否可编辑

        properties[2] = new WorkflowRequestTableField();
        properties[2].setFieldName("apply_date");//
        properties[2].setFieldValue(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));//
        properties[2].setView(true);//字段是否可见
        properties[2].setEdit(true);//字段是否可编辑

        properties[3] = new WorkflowRequestTableField();
        properties[3].setFieldName("belong_to_dept");//
        properties[3].setFieldValue(belongDept);//
        properties[3].setView(true);//字段是否可见
        properties[3].setEdit(true);//字段是否可编辑

        properties[4] = new WorkflowRequestTableField();
        properties[4].setFieldName("apply_pay_method");//
        properties[4].setFieldValue(paymethod);//
        properties[4].setView(true);//字段是否可见
        properties[4].setEdit(true);//字段是否可编辑

        properties[5] = new WorkflowRequestTableField();
        properties[5].setFieldName("receipt_bank");//
        properties[5].setFieldValue(receiptBank);//
        properties[5].setView(true);//字段是否可见
        properties[5].setEdit(true);//字段是否可编辑

        properties[6] = new WorkflowRequestTableField();
        properties[6].setFieldName("account");//
        properties[6].setFieldValue(receiptAccount);//
        properties[6].setView(true);//字段是否可见
        properties[6].setEdit(true);//字段是否可编辑

        properties[7] = new WorkflowRequestTableField();
        properties[7].setFieldName("receipt_name");//
        properties[7].setFieldValue(receiptCompany);//
        properties[7].setView(true);//字段是否可见
        properties[7].setEdit(true);//字段是否可编辑

        properties[8] = new WorkflowRequestTableField();
        properties[8].setFieldName("apply_total");//
        properties[8].setFieldValue(money);//
        properties[8].setView(true);//字段是否可见
        properties[8].setEdit(true);//字段是否可编辑

        properties[9] = new WorkflowRequestTableField();
        properties[9].setFieldName("apply_remark");//
        properties[9].setFieldValue(remark);//
        properties[9].setView(true);//字段是否可见
        properties[9].setEdit(true);//字段是否可编辑

        properties[10] = new WorkflowRequestTableField();
        properties[10].setFieldName("payer_org");//
        properties[10].setFieldValue(payerCompany);//
        properties[10].setView(true);//字段是否可见
        properties[10].setEdit(true);//字段是否可编辑

        properties[11] = new WorkflowRequestTableField();
        properties[11].setFieldName("do_type");//
        properties[11].setFieldValue("0");// 必填项 0是日常报销  1是办公用品  2是仓储备件
        properties[11].setView(true);//字段是否可见
        properties[11].setEdit(true);//字段是否可编辑

        properties[12] = new WorkflowRequestTableField();
        properties[12].setFieldName("reqid");//来源流程
        properties[12].setFieldValue(flowid);//
        properties[12].setView(true);//字段是否可见
        properties[12].setEdit(true);//字段是否可编辑

        WorkflowRequestTableRecord[] workflowRequestTableRecord = new WorkflowRequestTableRecord[1];//主字段只有一行数据
        workflowRequestTableRecord[0] = new WorkflowRequestTableRecord();
        workflowRequestTableRecord[0].setWorkflowRequestTableFields(properties);
        WorkflowMainTableInfo workflowMainTableInfo = new WorkflowMainTableInfo();
        workflowMainTableInfo.setRequestRecords(workflowRequestTableRecord);


        WorkflowBaseInfo wbi = new WorkflowBaseInfo();
        wbi.setWorkflowId("4521");//workflowid

        WorkflowRequestInfo wri = new WorkflowRequestInfo();//流程基本信息
        wri.setCreatorId(creatorid);//创建人id
        wri.setRequestLevel("0");//0 正常，1重要，2紧急
        wri.setRequestName("5.18费用报销流程");//流程标题
        wri.setWorkflowMainTableInfo(workflowMainTableInfo);//添加主字段数据

        wri.setWorkflowBaseInfo(wbi);
        WorkflowServicePortTypeProxy WorkflowServicePortTypeProxy = new WorkflowServicePortTypeProxy(OAPROXYURL+"/services/WorkflowService");
        requestid = WorkflowServicePortTypeProxy.doCreateWorkflowRequest(wri, Integer.parseInt(creatorid));
        log.info("创建流程结果====>"+requestid);

        return requestid;


    }

}
