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
	private final static String url = "http://oaapp-test.xingyun361.com:88/services/WorkflowService";
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
