

package com.smp.fbs;

import java.util.Date;


public class FbsBookingDetail {
    private Integer regNo;
    private Date bookingdt;
    private String projectName;
    private long projId;
    private Integer blockId;
    private Integer flatTypeId;
    private String blockNo;
    private String floorNo;
    private int  flatNo;
    private String flatId;
    private String paymentPlanName;
    private String discountType;
    private String applicantName;
    private int applicantId;
    private int coApplicantId;
    private String paybalAmount;
    private String paidAmount;
    
    public Date getBookingdt() {
        return bookingdt;
    }

    public String getFlatId() {
        return flatId;
    }

    public void setFlatId(String flatId) {
        this.flatId = flatId;
    }
    

    public String getPaymentPlanName() {
        return paymentPlanName;
    }

    public String getDiscountType() {
        return discountType;
    }

    public String getBlockNo() {
        return blockNo;
    }

    public Integer getRegNo() {
        return regNo;
    }

    public int getFlatNo() {
        return flatNo;
    }  

    public String getApplicantName() {
        return applicantName;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getPaybalAmount() {
        return paybalAmount;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public String getFloorNo() {
        return floorNo;
    }



    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setPaybalAmount(String paybalAmount) {
        this.paybalAmount = paybalAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public void setFloorNo(String floorNo) {
        this.floorNo = floorNo;
    }

    public void setFlatNo(int flatNo) {
        this.flatNo = flatNo;
    }

    public void setBookingdt(Date bookingdt) {
        this.bookingdt = bookingdt;
    }

    public void setBlockNo(String blockNo) {
        this.blockNo = blockNo;
    }

    public void setRegNo(Integer regNo) {
        this.regNo = regNo;
    }

    public void setPaymentPlanName(String paymentPlanName) {
        this.paymentPlanName = paymentPlanName;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }



    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public int getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(int applicantId) {
        this.applicantId = applicantId;
    }

    public int getCoApplicantId() {
        return coApplicantId;
    }

    public void setCoApplicantId(int coApplicantId) {
        this.coApplicantId = coApplicantId;
    }

    public Integer getBlockId() {
        return blockId;
    }

    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }

    public Integer getFlatTypeId() {
        return flatTypeId;
    }

    public void setFlatTypeId(Integer flatTypeId) {
        this.flatTypeId = flatTypeId;
    }

    public long getProjId() {
        return projId;
    }

    public void setProjId(long projId) {
        this.projId = projId;
    }

    

}
