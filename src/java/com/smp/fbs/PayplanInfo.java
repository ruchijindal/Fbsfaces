/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.fbs;

import java.util.Date;

/**
 *
 * @author smp
 */
public class PayplanInfo {

    private Integer planId;
    private String planName;
    private Integer payplanId;
    private Short percentage;
    private Integer fkPlanId;
    private String planDesc;
    private Integer serialNo;
    private Integer fkProjId;
    private Date dueDate;

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getFkPlanId() {
        return fkPlanId;
    }

    public void setFkPlanId(Integer fkPlanId) {
        this.fkPlanId = fkPlanId;
    }

    public Integer getFkProjId() {
        return fkProjId;
    }

    public void setFkProjId(Integer fkProjId) {
        this.fkProjId = fkProjId;
    }

    public Integer getPayplanId() {
        return payplanId;
    }

    public void setPayplanId(Integer payplanId) {
        this.payplanId = payplanId;
    }

    public Short getPercentage() {
        return percentage;
    }

    public void setPercentage(Short percentage) {
        this.percentage = percentage;
    }

    public String getPlanDesc() {
        return planDesc;
    }

    public void setPlanDesc(String planDesc) {
        this.planDesc = planDesc;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }
}
