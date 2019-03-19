/**
 * WorkflowService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.com.weaver.services.webservices;

public interface WorkflowService extends javax.xml.rpc.Service {
    public java.lang.String getWorkflowServiceHttpPortAddress();

    public cn.com.weaver.services.webservices.WorkflowServicePortType getWorkflowServiceHttpPort() throws javax.xml.rpc.ServiceException;

    public cn.com.weaver.services.webservices.WorkflowServicePortType getWorkflowServiceHttpPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
