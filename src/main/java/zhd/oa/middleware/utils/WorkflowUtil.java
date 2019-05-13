package zhd.oa.middleware.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.apache.ibatis.javassist.tools.rmi.RemoteException;

import cn.com.weaver.services.webservices.WorkflowServicePortTypeProxy;
import weaver.workflow.webservices.WorkflowBaseInfo;
import weaver.workflow.webservices.WorkflowDetailTableInfo;
import weaver.workflow.webservices.WorkflowMainTableInfo;
import weaver.workflow.webservices.WorkflowRequestInfo;
import weaver.workflow.webservices.WorkflowRequestTableField;
import weaver.workflow.webservices.WorkflowRequestTableRecord;

public class WorkflowUtil {
	private final static String url = "http://172.16.120.30:8899/services/WorkflowService";
	private static WorkflowUtil instance = null;

	private WorkflowUtil() {
	}

	public static WorkflowUtil shareInstance() {
		if (instance == null)
			instance = new WorkflowUtil();
		return instance;
	}

	/**
	 * 流程转发
	 * 
	 * @param requestid
	 * @param forwardids
	 *            被转发人的id 以,隔开
	 * @param uid
	 *            转发发出人员，只能有操作权限的人员才可转发，即待办事项里面包含
	 * @param remk
	 *            转发的签字意见
	 * @return
	 * @throws Exception
	 */
	public String forwardRequest(int requestid, String forwardids, int uid, String remk) throws Exception {

		WorkflowServicePortTypeProxy wsptp = new WorkflowServicePortTypeProxy(url);

		String res = null;

		List<String> toDoList = getToDoList(uid, null);

		if (toDoList.contains(requestid + "")) {

			res = "success";

			wsptp.forwardWorkflowRequest(requestid, forwardids, remk + new Date(), uid, "172.16.120.238");

		} else {

			res = "failed";

		}

		return res;
	}

	/**
	 * 流程提交或退回 注：该提交为不编辑表单提交；该退回走流程图设定的退回路线！！！！
	 * 
	 * @param requestid
	 * @param uid
	 *            只能有操作权限的人员才可转发，即待办事项里面包含
	 * @param type
	 *            "submit" 为提交 "reject" 为退回
	 * @return
	 * @throws Exception
	 */

	public String operateRequest(int requestid, int uid, String type) throws Exception {

		WorkflowServicePortTypeProxy wsptp = new WorkflowServicePortTypeProxy(url);

		String res = null;

		List<String> toDoList = getToDoList(uid, null);

		if (toDoList.contains(requestid + "")) {

			res = "success";

			WorkflowRequestInfo wri = wsptp.getWorkflowRequest(requestid, uid, 0);

			wsptp.submitWorkflowRequest(wri, requestid, uid, type,"系统定时提交");

		} else {

			res = "failed";

		}

		return res;
	}

	/**
	 * 创建流程 主表明细表数据需按规则传入，否则会失败
	 * 
	 * @param param
	 *            forExample "sqr:286,bm:30,sqrq:2019-03-21,bz:测试创建流程" 字段名要和oa数据库名一样
	 * @param workflowId
	 *            流程id
	 * @param creatorId
	 *            流程创建人
	 * @param flowTitle
	 *            流程标题
	 * @return
	 * @throws RemoteException
	 */
	public String createWorkflow(String workflowId, int creatorId, String flowTitle, String mainParam,
			String detailParam) throws Exception {

		List<TreeMap<String, String>> flowMainField = new ArrayList<TreeMap<String, String>>();
		String[] paramsList = mainParam.split("<->");
		System.out.println(paramsList);
		for (int i = 0; i < paramsList.length; i++) {
			String[] paramsMap = paramsList[i].split(":");
			TreeMap<String, String> paramsTreeMap = new TreeMap<String, String>();
			System.out.println(paramsList[i]);
			paramsTreeMap.put(paramsMap[0], paramsMap[1]);
			flowMainField.add(i, paramsTreeMap);
		}
		WorkflowRequestTableField[] workflowRequestTableField = new WorkflowRequestTableField[flowMainField.size()];
		for (int i = 0; i < workflowRequestTableField.length; i++) {
			workflowRequestTableField[i] = new WorkflowRequestTableField();
			TreeMap<String, String> listMap = flowMainField.get(i);
			workflowRequestTableField[i].setFieldName(listMap.firstKey()); // 字段key
			workflowRequestTableField[i].setFieldValue(listMap.get(listMap.firstKey()));// 字段值
			workflowRequestTableField[i].setView(true);// 字段是否可见
			workflowRequestTableField[i].setEdit(true);// 字段是否可编辑
		}

		WorkflowRequestTableRecord[] workflowRequestTableRecord = new WorkflowRequestTableRecord[1];
		workflowRequestTableRecord[0] = new WorkflowRequestTableRecord();
		workflowRequestTableRecord[0].setWorkflowRequestTableFields(workflowRequestTableField);

		WorkflowMainTableInfo workflowMainTableInfo = new WorkflowMainTableInfo();
		workflowMainTableInfo.setRequestRecords(workflowRequestTableRecord);// 加入主表字段数据
		// -------------加入明细表数据------------------
		if (null == detailParam) {
			detailParam = "";
		}
		String[] detailParams = detailParam.split("<=>");
		List<String> detailList = new ArrayList<String>();
		for (int i = 0; i < detailParams.length; i++) {
			detailList.add(detailParams[i]);
		}

		workflowRequestTableRecord = new WorkflowRequestTableRecord[detailList.size()];

		for (int i = 0; i < detailList.size(); i++) {
			String[] detailParamsList = detailList.get(i).split("<->");
			workflowRequestTableField = new WorkflowRequestTableField[detailParamsList.length]; // 明细字段数
																								// detailParamsList.length
			System.out.println(detailList.get(i)); /// ==--------------
			for (int j = 0; j < detailParamsList.length; j++) {
				String[] arr = detailParamsList[j].split(":");
				workflowRequestTableField[j] = new WorkflowRequestTableField();
				workflowRequestTableField[j].setFieldName(arr[0]);
				workflowRequestTableField[j].setFieldValue(arr[1]);
				workflowRequestTableField[j].setView(true);
				workflowRequestTableField[j].setEdit(true);

				System.out.println("Line182====" + arr[0] + "<====>" + arr[1]);
			}
			workflowRequestTableRecord[i] = new WorkflowRequestTableRecord();
			workflowRequestTableRecord[i].setWorkflowRequestTableFields(workflowRequestTableField);
		}

		WorkflowDetailTableInfo WorkflowDetailTableInfo[] = new WorkflowDetailTableInfo[1];
		WorkflowDetailTableInfo[0] = new WorkflowDetailTableInfo();
		WorkflowDetailTableInfo[0].setWorkflowRequestTableRecords(workflowRequestTableRecord);

		WorkflowBaseInfo workflowBaseInfo = new WorkflowBaseInfo();
		workflowBaseInfo.setWorkflowId(workflowId);

		WorkflowRequestInfo workflowRequestInfo = new WorkflowRequestInfo();
		workflowRequestInfo.setCreatorId(creatorId + "");// 创建人
		workflowRequestInfo.setRequestLevel("0");// 0 正常，1重要，2紧急
		workflowRequestInfo.setRequestName(flowTitle + "");// 流程表单标题
		workflowRequestInfo.setWorkflowMainTableInfo(workflowMainTableInfo);// 主表数据
		workflowRequestInfo.setWorkflowDetailTableInfos(WorkflowDetailTableInfo);// 明细字段
		workflowRequestInfo.setWorkflowBaseInfo(workflowBaseInfo);// 添加流程id信息

		WorkflowServicePortTypeProxy workflowServicePortTypeProxy = new WorkflowServicePortTypeProxy(url);
		return workflowServicePortTypeProxy.doCreateWorkflowRequest(workflowRequestInfo, creatorId);
	}

	/**
	 * 根据uid查询流程待办流程
	 * 
	 * @param uid
	 *            操作者id
	 * @param conditions
	 *            查询条件
	 * @return
	 * @throws Exception
	 */
	public static List<String> getToDoList(int uid, String[] conditions) throws Exception {

		WorkflowServicePortTypeProxy workflowServicePortTypeProxy = new WorkflowServicePortTypeProxy(url);

		WorkflowRequestInfo[] workflowRequestInfo = workflowServicePortTypeProxy.getToDoWorkflowRequestList(1, 15, 100,
				uid, conditions);

		List<String> toDoList = new ArrayList<>();

		for (int i = 0; i < workflowRequestInfo.length; i++) {

			String toDoRequestid = workflowRequestInfo[i].getRequestId();

			toDoList.add(toDoRequestid);

		}

		return toDoList;

	}
}
