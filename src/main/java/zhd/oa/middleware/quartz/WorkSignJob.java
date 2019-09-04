package zhd.oa.middleware.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.com.weaver.services.webservices.WorkflowServicePortTypeProxy;
import weaver.workflow.webservices.WorkflowBaseInfo;
import weaver.workflow.webservices.WorkflowMainTableInfo;
import weaver.workflow.webservices.WorkflowRequestInfo;
import weaver.workflow.webservices.WorkflowRequestTableField;
import weaver.workflow.webservices.WorkflowRequestTableRecord;
import zhd.oa.middleware.model.CronJob;
import zhd.oa.middleware.service.CronJobService;

public class WorkSignJob implements Job{
	// prod
	private final static String url = "http://oa.xingyun361.com/services/WorkflowService";

	private CronJobService cronJobService = new CronJobService();
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		System.out.println("WorkSignJob is coming...");
		try{
			List<CronJob> cronMsg = cronJobService.getCronJobData();
			
			for (int i = 0; i < cronMsg.size(); i++) {
				
				System.out.println("---------"+cronMsg.get(i).toString());
				
				WorkflowRequestTableField[] wrti = new WorkflowRequestTableField[4];
				
				wrti[0] = new WorkflowRequestTableField();         
				wrti[0].setFieldName("applicant");//        
				wrti[0].setFieldValue("1");//        
				wrti[0].setView(true);//字段是否可见        
				wrti[0].setEdit(true);//字段是否可编辑
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String datei = sdf.format(new Date());
				
				wrti[1] = new WorkflowRequestTableField();         
				wrti[1].setFieldName("notice_date");//        
				wrti[1].setFieldValue(datei);//        
				wrti[1].setView(true);//字段是否可见        
				wrti[1].setEdit(true);//字段是否可编辑
				
				wrti[2] = new WorkflowRequestTableField();         
				wrti[2].setFieldName("notice_dt_html");//        
				wrti[2].setFieldValue(cronMsg.get(i).getMsg());//        
				wrti[2].setView(true);//字段是否可见        
				wrti[2].setEdit(true);//字段是否可编辑
				
				wrti[3] = new WorkflowRequestTableField();         
				wrti[3].setFieldName("notice_emps");//        
				wrti[3].setFieldValue(cronMsg.get(i).getUserid()+"");//        
				wrti[3].setView(true);//字段是否可见        
				wrti[3].setEdit(true);//字段是否可编辑
				
				WorkflowRequestTableRecord[] wrtri = new WorkflowRequestTableRecord[1];//主字段只有一行数据       
				wrtri[0] = new WorkflowRequestTableRecord();        
				wrtri[0].setWorkflowRequestTableFields(wrti);          
				WorkflowMainTableInfo wmi = new WorkflowMainTableInfo();        
				wmi.setRequestRecords(wrtri);
				
				WorkflowBaseInfo wbi = new WorkflowBaseInfo();        
				wbi.setWorkflowId("4301");//workflowid 流程接口演示流程2016==38        
				WorkflowRequestInfo wri = new WorkflowRequestInfo();//流程基本信息            
				wri.setCreatorId("1");//创建人id        
				wri.setRequestLevel("0");//0 正常，1重要，2紧急       
				wri.setRequestName("考勤异常提醒");//流程标题       
				wri.setWorkflowMainTableInfo(wmi);//添加主字段数据        
				wri.setWorkflowBaseInfo(wbi);        
				WorkflowServicePortTypeProxy WorkflowServicePortTypeProxy = new WorkflowServicePortTypeProxy(url);        
				String requestid = WorkflowServicePortTypeProxy.doCreateWorkflowRequest(wri, 1);        
				System.out.println("requestid:"+requestid);
				
			}
			System.out.println("once over!");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
