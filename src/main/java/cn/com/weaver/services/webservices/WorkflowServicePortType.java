/**
 * WorkflowServicePortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.com.weaver.services.webservices;

public interface WorkflowServicePortType extends java.rmi.Remote {
    public java.lang.String forward2WorkflowRequest(int in0, java.lang.String in1, java.lang.String in2, int in3, java.lang.String in4) throws java.rmi.RemoteException;
    public weaver.workflow.webservices.WorkflowRequestInfo[] getAllWorkflowRequestList(int in0, int in1, int in2, int in3, java.lang.String[] in4) throws java.rmi.RemoteException;
    public weaver.workflow.webservices.WorkflowRequestInfo getWorkflowRequest(int in0, int in1, int in2) throws java.rmi.RemoteException;
    public weaver.workflow.webservices.WorkflowRequestInfo[] getHendledWorkflowRequestList(int in0, int in1, int in2, int in3, java.lang.String[] in4) throws java.rmi.RemoteException;
    public weaver.workflow.webservices.WorkflowRequestInfo[] getToDoWorkflowRequestList(int in0, int in1, int in2, int in3, java.lang.String[] in4) throws java.rmi.RemoteException;
    public weaver.workflow.webservices.WorkflowRequestInfo getWorkflowRequest4Split(int in0, int in1, int in2, int in3) throws java.rmi.RemoteException;
    public java.lang.String submitWorkflowRequest(weaver.workflow.webservices.WorkflowRequestInfo in0, int in1, int in2, java.lang.String in3, java.lang.String in4) throws java.rmi.RemoteException;
    public int getHendledWorkflowRequestCount(int in0, java.lang.String[] in1) throws java.rmi.RemoteException;
    public java.lang.String getLeaveDays(java.lang.String in0, java.lang.String in1, java.lang.String in2, java.lang.String in3, java.lang.String in4) throws java.rmi.RemoteException;
    public weaver.workflow.webservices.WorkflowBaseInfo[] getCreateWorkflowList(int in0, int in1, int in2, int in3, int in4, java.lang.String[] in5) throws java.rmi.RemoteException;
    public int getCreateWorkflowCount(int in0, int in1, java.lang.String[] in2) throws java.rmi.RemoteException;
    public int getProcessedWorkflowRequestCount(int in0, java.lang.String[] in1) throws java.rmi.RemoteException;
    public java.lang.String forwardWorkflowRequest(int in0, java.lang.String in1, java.lang.String in2, int in3, java.lang.String in4) throws java.rmi.RemoteException;
    public java.lang.String doCreateWorkflowRequest(weaver.workflow.webservices.WorkflowRequestInfo in0, int in1) throws java.rmi.RemoteException;
    public java.lang.String doForceOver(int in0, int in1) throws java.rmi.RemoteException;
    public int getCCWorkflowRequestCount(int in0, java.lang.String[] in1) throws java.rmi.RemoteException;
    public weaver.workflow.webservices.WorkflowRequestInfo[] getProcessedWorkflowRequestList(int in0, int in1, int in2, int in3, java.lang.String[] in4) throws java.rmi.RemoteException;
    public int getAllWorkflowRequestCount(int in0, java.lang.String[] in1) throws java.rmi.RemoteException;
    public weaver.workflow.webservices.WorkflowRequestInfo getCreateWorkflowRequestInfo(int in0, int in1) throws java.rmi.RemoteException;
    public weaver.workflow.webservices.WorkflowRequestInfo[] getMyWorkflowRequestList(int in0, int in1, int in2, int in3, java.lang.String[] in4) throws java.rmi.RemoteException;
    public weaver.workflow.webservices.WorkflowBaseInfo[] getCreateWorkflowTypeList(int in0, int in1, int in2, int in3, java.lang.String[] in4) throws java.rmi.RemoteException;
    public int getMyWorkflowRequestCount(int in0, java.lang.String[] in1) throws java.rmi.RemoteException;
    public java.lang.String[] getWorkflowNewFlag(java.lang.String[] in0, java.lang.String in1) throws java.rmi.RemoteException;
    public void writeWorkflowReadFlag(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;
    public int getToDoWorkflowRequestCount(int in0, java.lang.String[] in1) throws java.rmi.RemoteException;
    public java.lang.String givingOpinions(int in0, int in1, java.lang.String in2) throws java.rmi.RemoteException;
    public int getCreateWorkflowTypeCount(int in0, java.lang.String[] in1) throws java.rmi.RemoteException;
    public weaver.workflow.webservices.WorkflowRequestLog[] getWorkflowRequestLogs(java.lang.String in0, java.lang.String in1, int in2, int in3, int in4) throws java.rmi.RemoteException;
    public boolean deleteRequest(int in0, int in1) throws java.rmi.RemoteException;
    public weaver.workflow.webservices.WorkflowRequestInfo[] getCCWorkflowRequestList(int in0, int in1, int in2, int in3, java.lang.String[] in4) throws java.rmi.RemoteException;
    public java.lang.String getUserId(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;
}
