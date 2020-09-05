package zhd.oa.middleware.service;

import java.util.ArrayList;
import java.util.List;

import cn.com.weaver.services.webservices.WorkflowServicePortTypeProxy;
import weaver.workflow.webservices.WorkflowBaseInfo;
import weaver.workflow.webservices.WorkflowDetailTableInfo;
import weaver.workflow.webservices.WorkflowMainTableInfo;
import weaver.workflow.webservices.WorkflowRequestInfo;
import weaver.workflow.webservices.WorkflowRequestTableField;
import weaver.workflow.webservices.WorkflowRequestTableRecord;
import zhd.oa.middleware.mapper.ExpenseReimburseMapper;
import zhd.oa.middleware.model.ExpenseReimburseDetail;
import zhd.oa.middleware.model.ExpenseReimburseMain;

import static zhd.oa.middleware.utils.HttpUtil.OAPROXYURL;

public class ExpenseReimburseService extends BaseService{
	
	private ExpenseReimburseMapper expenseReimburseMapper = null;
	
	public List<ExpenseReimburseMain> getExpenseReimburseMain(String requestid){
		
		List<ExpenseReimburseMain> listExpenseReimburseMain = new ArrayList<ExpenseReimburseMain>();
		
		try {
			session = openSession();
			expenseReimburseMapper = session.getMapper(ExpenseReimburseMapper.class);
			listExpenseReimburseMain = expenseReimburseMapper.getExpenseReimburseMain(requestid);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession();
		}
		
		return listExpenseReimburseMain;
	}
	
	public List<ExpenseReimburseDetail> getExpenseReimburseDetail(String requestid, 
			int applicant,String applydate,int belongdept,int payment,String remk ){
		
		List<ExpenseReimburseDetail> listExpenseReimburseDetail = new ArrayList<ExpenseReimburseDetail>();
		
		try {
			session = openSession();
			expenseReimburseMapper = session.getMapper(ExpenseReimburseMapper.class);
			listExpenseReimburseDetail = expenseReimburseMapper.getExpenseReimburseDetail(requestid, applicant, applydate, belongdept, payment, remk);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession();
		}
		
		return listExpenseReimburseDetail;
	}
	
	public void createExpenseReimburse(String requestid){
		
		log.info("开始创建费用报销流程！！！");
		
		try {
			session = openSession();
			expenseReimburseMapper = session.getMapper(ExpenseReimburseMapper.class);
			List<ExpenseReimburseMain> listExpenseReimburseMain = expenseReimburseMapper.getExpenseReimburseMain(requestid);
			
			log.info("需要创建的流程有--->" + listExpenseReimburseMain);
			
			for (int i = 0; i < listExpenseReimburseMain.size(); i++) {
				
				WorkflowRequestTableField[] workflowRequestTableField = new WorkflowRequestTableField[6];
				
				ExpenseReimburseMain expenseReimburseMain = listExpenseReimburseMain.get(i);
				
				workflowRequestTableField[0] = new WorkflowRequestTableField();        
				workflowRequestTableField[0].setFieldName("applicant");//  申请人      
				workflowRequestTableField[0].setFieldValue(expenseReimburseMain.getUserid()+"");      
				workflowRequestTableField[0].setView(true);//字段是否可见    
				workflowRequestTableField[0].setEdit(true);//字段是否可编辑
				
				workflowRequestTableField[1] = new WorkflowRequestTableField();        
				workflowRequestTableField[1].setFieldName("belong_to_dept");//    申请部门   
				workflowRequestTableField[1].setFieldValue(expenseReimburseMain.getBelongDeptId()+"");       
				workflowRequestTableField[1].setView(true);//字段是否可见    
				workflowRequestTableField[1].setEdit(true);//字段是否可编辑
				
				workflowRequestTableField[2] = new WorkflowRequestTableField();        
				workflowRequestTableField[2].setFieldName("apply_date");//   申请日期     
				workflowRequestTableField[2].setFieldValue(expenseReimburseMain.getApplyDate());        
				workflowRequestTableField[2].setView(true);//字段是否可见    
				workflowRequestTableField[2].setEdit(true);//字段是否可编辑
				
				workflowRequestTableField[3] = new WorkflowRequestTableField();        
				workflowRequestTableField[3].setFieldName("apply_total");//   报销总额   
				workflowRequestTableField[3].setFieldValue(expenseReimburseMain.getDoMoney()+"");        
				workflowRequestTableField[3].setView(true);//字段是否可见    
				workflowRequestTableField[3].setEdit(true);//字段是否可编辑
				
				workflowRequestTableField[4] = new WorkflowRequestTableField();        
				workflowRequestTableField[4].setFieldName("apply_pay_method");//   支付方式     
				workflowRequestTableField[4].setFieldValue(expenseReimburseMain.getPaymethodId()+"");         
				workflowRequestTableField[4].setView(true);//字段是否可见    
				workflowRequestTableField[4].setEdit(true);//字段是否可编辑
				
				workflowRequestTableField[5] = new WorkflowRequestTableField();        
				workflowRequestTableField[5].setFieldName("apply_remark");//     备注   
				workflowRequestTableField[5].setFieldValue(expenseReimburseMain.getRemk()+"");        
				workflowRequestTableField[5].setView(true);//字段是否可见    
				workflowRequestTableField[5].setEdit(true);//字段是否可编辑
				
				WorkflowRequestTableRecord[] workflowRequestTableRecord = new WorkflowRequestTableRecord[1];
				workflowRequestTableRecord[0] = new WorkflowRequestTableRecord();        
				workflowRequestTableRecord[0].setWorkflowRequestTableFields(workflowRequestTableField);           
				WorkflowMainTableInfo workflowMainTableInfo = new WorkflowMainTableInfo();        
				workflowMainTableInfo.setRequestRecords(workflowRequestTableRecord);
				
				List<ExpenseReimburseDetail> listExpenseReimburseDetail = expenseReimburseMapper.getExpenseReimburseDetail(requestid, 
						expenseReimburseMain.getUserid(), expenseReimburseMain.getApplyDate(), expenseReimburseMain.getBelongDeptId(), 
						expenseReimburseMain.getPaymethodId(), expenseReimburseMain.getRemk());
				
				log.info("需要添加的明细有--->" + listExpenseReimburseDetail);
				
				int detailrows = listExpenseReimburseDetail.size() ;//添加指定条数明细
				
				workflowRequestTableRecord = new WorkflowRequestTableRecord[detailrows];
				
				for (int j = 0; j < detailrows; j++) {
					
					workflowRequestTableField = new WorkflowRequestTableField[4]; //字段信息
					
					ExpenseReimburseDetail expenseReimburseDetail = listExpenseReimburseDetail.get(j);
					
					workflowRequestTableField[0] = new WorkflowRequestTableField();             
					workflowRequestTableField[0].setFieldName("expense_type");//报销项目              
					workflowRequestTableField[0].setFieldValue(expenseReimburseDetail.getExpenseType());            
					workflowRequestTableField[0].setView(true);//字段是否可见              
					workflowRequestTableField[0].setEdit(true);//字段是否可编辑
					
					workflowRequestTableField[1] = new WorkflowRequestTableField();             
					workflowRequestTableField[1].setFieldName("expense_money");//报销金额             
					workflowRequestTableField[1].setFieldValue(expenseReimburseDetail.getExpenseMoney()+"");            
					workflowRequestTableField[1].setView(true);//字段是否可见              
					workflowRequestTableField[1].setEdit(true);//字段是否可编辑
					
					workflowRequestTableField[2] = new WorkflowRequestTableField();             
					workflowRequestTableField[2].setFieldName("expense_date");//费用日期            
					workflowRequestTableField[2].setFieldValue(expenseReimburseDetail.getExpenseDate());            
					workflowRequestTableField[2].setView(true);//字段是否可见              
					workflowRequestTableField[2].setEdit(true);//字段是否可编辑
					
					workflowRequestTableField[3] = new WorkflowRequestTableField();             
					workflowRequestTableField[3].setFieldName("expense_dt1");//报销内容         
					workflowRequestTableField[3].setFieldValue(expenseReimburseDetail.getExpenseDt());            
					workflowRequestTableField[3].setView(true);//字段是否可见              
					workflowRequestTableField[3].setEdit(true);//字段是否可编辑
					
					workflowRequestTableRecord[j] = new WorkflowRequestTableRecord();  
					workflowRequestTableRecord[j].setWorkflowRequestTableFields(workflowRequestTableField);
					
				}
				
				WorkflowDetailTableInfo WorkflowDetailTableInfo[] = new WorkflowDetailTableInfo[1];//指定明细表的个数，多个明细表指定多个，顺序按照明细的顺序       
				WorkflowDetailTableInfo[0] = new WorkflowDetailTableInfo();        
				WorkflowDetailTableInfo[0].setWorkflowRequestTableRecords(workflowRequestTableRecord);
				
				WorkflowBaseInfo workflowBaseInfo = new WorkflowBaseInfo();
				
				workflowBaseInfo.setWorkflowId("4801");//workflowid 
				 
				WorkflowRequestInfo workflowRequestInfo = new WorkflowRequestInfo();//流程基本信息           
				workflowRequestInfo.setCreatorId(expenseReimburseMain.getUserid()+"");//创建人id        
				workflowRequestInfo.setRequestLevel("0");//0 正常，1重要，2紧急       
				workflowRequestInfo.setRequestName("5.18费用报销流程");//流程标题        
				workflowRequestInfo.setWorkflowMainTableInfo(workflowMainTableInfo);//添加主字段数据
				workflowRequestInfo.setWorkflowDetailTableInfos(WorkflowDetailTableInfo);//添加明细数据
				
				workflowRequestInfo.setWorkflowBaseInfo(workflowBaseInfo);        
				WorkflowServicePortTypeProxy WorkflowServicePortTypeProxy = new WorkflowServicePortTypeProxy(OAPROXYURL + "/services/WorkflowService");
				String returnRequestid = WorkflowServicePortTypeProxy.doCreateWorkflowRequest(workflowRequestInfo, expenseReimburseMain.getUserid());
				log.info("流程创建成功---requestid--->" + returnRequestid);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession();
		}
	}
	
}
