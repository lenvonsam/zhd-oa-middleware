package zhd.oa.middleware.utils;

import cn.com.weaver.services.webservices.WorkflowServicePortTypeProxy;
import weaver.workflow.webservices.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import static zhd.oa.middleware.utils.HttpUtil.OAPROXYURL;

public class CreateNoticeWorkflow {
    private static CreateNoticeWorkflow obj = null;

    public static CreateNoticeWorkflow shareInstance(){
        if(null == obj){
            obj = new CreateNoticeWorkflow();
        }
        return obj;
    }


    /**
     * 系统提醒流程
     * @param flowName
     * @param creatorid
     * @param emp
     * @param noticeDt
     * @return
     * @throws Exception
     */
    public String notice(String flowName, String creatorid, String emp,String noticeDt) throws Exception {

        String requestid = "";
        WorkflowRequestTableField[] properties = new WorkflowRequestTableField[4];

        properties[0] = new WorkflowRequestTableField();
        properties[0].setFieldName("applicant");//
        properties[0].setFieldValue(creatorid);//
        properties[0].setView(true);//字段是否可见
        properties[0].setEdit(true);//字段是否可编辑

        properties[1] = new WorkflowRequestTableField();
        properties[1].setFieldName("notice_date");//
        properties[1].setFieldValue(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));//
        properties[1].setView(true);//字段是否可见
        properties[1].setEdit(true);//字段是否可编辑

        properties[2] = new WorkflowRequestTableField();
        properties[2].setFieldName("notice_dt_html");//
        properties[2].setFieldValue(noticeDt);//
        properties[2].setView(true);//字段是否可见
        properties[2].setEdit(true);//字段是否可编辑

        properties[3] = new WorkflowRequestTableField();
        properties[3].setFieldName("notice_emps");//
        properties[3].setFieldValue(emp);//
        properties[3].setView(true);//字段是否可见
        properties[3].setEdit(true);//字段是否可编辑

        WorkflowRequestTableRecord[] workflowRequestTableRecord = new WorkflowRequestTableRecord[1];//主字段只有一行数据
        workflowRequestTableRecord[0] = new WorkflowRequestTableRecord();
        workflowRequestTableRecord[0].setWorkflowRequestTableFields(properties);
        WorkflowMainTableInfo workflowMainTableInfo = new WorkflowMainTableInfo();
        workflowMainTableInfo.setRequestRecords(workflowRequestTableRecord);


        WorkflowBaseInfo wbi = new WorkflowBaseInfo();
        wbi.setWorkflowId("4301");//workflowid

        WorkflowRequestInfo wri = new WorkflowRequestInfo();//流程基本信息
        wri.setCreatorId("1");//创建人id
        wri.setRequestLevel("0");//0 正常，1重要，2紧急
        wri.setRequestName(flowName);//流程标题
        wri.setWorkflowMainTableInfo(workflowMainTableInfo);//添加主字段数据

        wri.setWorkflowBaseInfo(wbi);
        WorkflowServicePortTypeProxy WorkflowServicePortTypeProxy = new WorkflowServicePortTypeProxy(OAPROXYURL+"/services/WorkflowService");
        requestid = WorkflowServicePortTypeProxy.doCreateWorkflowRequest(wri, Integer.parseInt(creatorid));

        return requestid;
    }

    /**
     * 创建提醒流程  由系统通知
     * @param emp 要通知的人员
     * @param noticeDt 要通知的内容
     * @return
     */
    public String notice(String emp,String noticeDt) throws Exception {
        return notice("系统提醒流程","1",emp, noticeDt);
    }

    /**
     * 创建提醒流程 自定义流程标题
     * @param flowName 自定义流程标题
     * @param emp 要通知的人员
     * @param noticeDt 要通知的内容
     * @return
     */
    public String notice(String flowName, String emp,String noticeDt) throws Exception {
        return notice(flowName,"1",emp, noticeDt);
    }

}
