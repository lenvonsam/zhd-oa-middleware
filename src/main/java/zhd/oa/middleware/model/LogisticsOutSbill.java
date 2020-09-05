package zhd.oa.middleware.model;

public class LogisticsOutSbill {

    private String deliveryBillcode;
    private String sbillBillcode;
    private String deliveryDate;
    private String datasCustomer;
    private String datasCustomername;
    private String datasCarnum;
    private String carCode;
    private String driverCarId;
    private String datasDriver;
    private String driverCode;
    private double goodsNum;
    private double goodsWeight;
    private String employeeCode;
    private String orgCode;
    private String deptCode;
    private String partsnameName;
    private String goodsMaterial;
    private String goodsSpec;
    private String productareaName;
    private String zlfw = "";
    private String gcfw = "";

    public String getDriverCarId() {
        return driverCarId;
    }

    public void setDriverCarId(String driverCarId) {
        this.driverCarId = driverCarId;
    }

    public String getDeliveryBillcode() {
        return deliveryBillcode;
    }

    public void setDeliveryBillcode(String deliveryBillcode) {
        this.deliveryBillcode = deliveryBillcode;
    }

    public String getSbillBillcode() {
        return sbillBillcode;
    }

    public void setSbillBillcode(String sbillBillcode) {
        this.sbillBillcode = sbillBillcode;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDatasCustomer() {
        return datasCustomer;
    }

    public void setDatasCustomer(String datasCustomer) {
        this.datasCustomer = datasCustomer;
    }

    public String getDatasCustomername() {
        return datasCustomername;
    }

    public void setDatasCustomername(String datasCustomername) {
        this.datasCustomername = datasCustomername;
    }

    public String getDatasCarnum() {
        return datasCarnum;
    }

    public void setDatasCarnum(String datasCarnum) {
        this.datasCarnum = datasCarnum;
    }

    public String getCarCode() {
        return carCode;
    }

    public void setCarCode(String carCode) {
        this.carCode = carCode;
    }

    public String getDatasDriver() {
        return datasDriver;
    }

    public void setDatasDriver(String datasDriver) {
        this.datasDriver = datasDriver;
    }

    public String getDriverCode() {
        return driverCode;
    }

    public void setDriverCode(String driverCode) {
        this.driverCode = driverCode;
    }

    public double getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(double goodsNum) {
        this.goodsNum = goodsNum;
    }

    public double getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsWeight(double goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getPartsnameName() {
        return partsnameName;
    }

    public void setPartsnameName(String partsnameName) {
        this.partsnameName = partsnameName;
    }

    public String getGoodsMaterial() {
        return goodsMaterial;
    }

    public void setGoodsMaterial(String goodsMaterial) {
        this.goodsMaterial = goodsMaterial;
    }

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    public String getProductareaName() {
        return productareaName;
    }

    public void setProductareaName(String productareaName) {
        this.productareaName = productareaName;
    }

    public String getZlfw() {
        return zlfw;
    }

    public void setZlfw(String zlfw) {
        this.zlfw = zlfw;
    }

    public String getGcfw() {
        return gcfw;
    }

    public void setGcfw(String gcfw) {
        this.gcfw = gcfw;
    }

    @Override
    public String toString() {
        return "LogisticsOutSbill{" +
                "deliveryBillcode='" + deliveryBillcode + '\'' +
                ", sbillBillcode='" + sbillBillcode + '\'' +
                ", deliveryDate='" + deliveryDate + '\'' +
                ", datasCustomer='" + datasCustomer + '\'' +
                ", datasCustomername='" + datasCustomername + '\'' +
                ", datasCarnum='" + datasCarnum + '\'' +
                ", carCode='" + carCode + '\'' +
                ", driverCarId='" + driverCarId + '\'' +
                ", datasDriver='" + datasDriver + '\'' +
                ", driverCode='" + driverCode + '\'' +
                ", goodsNum=" + goodsNum +
                ", goodsWeight=" + goodsWeight +
                ", employeeCode='" + employeeCode + '\'' +
                ", orgCode='" + orgCode + '\'' +
                ", deptCode='" + deptCode + '\'' +
                ", partsnameName='" + partsnameName + '\'' +
                ", goodsMaterial='" + goodsMaterial + '\'' +
                ", goodsSpec='" + goodsSpec + '\'' +
                ", productareaName='" + productareaName + '\'' +
                ", zlfw='" + zlfw + '\'' +
                ", gcfw='" + gcfw + '\'' +
                '}';
    }
}
