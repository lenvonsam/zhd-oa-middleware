package zhd.oa.middleware.model;

public class LogisticsSbillInfo {

    /**
     * 单据编号（包含销售提单、加工需求单、销售需求单）
     */
    private String sbillCode;//单据号
    /**
     * 单据明细编号
     */
    private String sbillDtCode;//码单
    private String goodsName;
    private String goodsMaterial;
    private String goodsSpec;
    private String goodsArea;
    private String goodsZLFW;
    private String goodsGCFW;
    private int goodsNum;
    private double goodsWeight;
    /**
     * 购货单位编号
     */
    private String buyerComCode;
    /**
     * 购货单位名称
     */
    private String buyerComName;
    /**
     * 物资机构编号
     */
    private String goodsOrgCode;
    /**
     * 物资机构名称
     */
    private String goodsOrgName;

    public String getSbillCode() {
        return sbillCode;
    }

    public void setSbillCode(String sbillCode) {
        this.sbillCode = sbillCode;
    }

    public String getSbillDtCode() {
        return sbillDtCode;
    }

    public void setSbillDtCode(String sbillDtCode) {
        this.sbillDtCode = sbillDtCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
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

    public String getGoodsArea() {
        return goodsArea;
    }

    public void setGoodsArea(String goodsArea) {
        this.goodsArea = goodsArea;
    }

    public String getGoodsZLFW() {
        return goodsZLFW;
    }

    public void setGoodsZLFW(String goodsZLFW) {
        this.goodsZLFW = goodsZLFW;
    }

    public String getGoodsGCFW() {
        return goodsGCFW;
    }

    public void setGoodsGCFW(String goodsGCFW) {
        this.goodsGCFW = goodsGCFW;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public double getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsWeight(double goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public String getBuyerComCode() {
        return buyerComCode;
    }

    public void setBuyerComCode(String buyerComCode) {
        this.buyerComCode = buyerComCode;
    }

    public String getBuyerComName() {
        return buyerComName;
    }

    public void setBuyerComName(String buyerComName) {
        this.buyerComName = buyerComName;
    }

    public String getGoodsOrgCode() {
        return goodsOrgCode;
    }

    public void setGoodsOrgCode(String goodsOrgCode) {
        this.goodsOrgCode = goodsOrgCode;
    }

    public String getGoodsOrgName() {
        return goodsOrgName;
    }

    public void setGoodsOrgName(String goodsOrgName) {
        this.goodsOrgName = goodsOrgName;
    }

    @Override
    public String toString() {
        return "LogisticsSbillInfo{" +
                "sbillCode='" + sbillCode + '\'' +
                ", sbillDtCode='" + sbillDtCode + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsMaterial='" + goodsMaterial + '\'' +
                ", goodsSpec='" + goodsSpec + '\'' +
                ", goodsArea='" + goodsArea + '\'' +
                ", goodsZLFW='" + goodsZLFW + '\'' +
                ", goodsGCFW='" + goodsGCFW + '\'' +
                ", goodsNum='" + goodsNum + '\'' +
                ", goodsWeight='" + goodsWeight + '\'' +
                ", buyerComCode='" + buyerComCode + '\'' +
                ", buyerComName='" + buyerComName + '\'' +
                ", goodsOrgCode='" + goodsOrgCode + '\'' +
                ", goodsOrgName='" + goodsOrgName + '\'' +
                '}';
    }
}
