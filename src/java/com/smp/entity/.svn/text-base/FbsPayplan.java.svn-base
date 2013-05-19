/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smp.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author smp
 */
@Entity
@Table(name = "fbs_payplan")
@NamedQueries({
    @NamedQuery(name = "FbsPayplan.findByPlanIdAndProjectId", query = "SELECT f FROM FbsPayplan f WHERE f.fkProjId = :fkProjId AND f.fkPlanId = :fkPlanId  ORDER BY f.serialNo"),
    @NamedQuery(name = "FbsPayplan.findByPlanIdAndProjectIdAndDueDate", query = "SELECT f FROM FbsPayplan f WHERE f.fkProjId = :fkProjId AND f.fkPlanId = :fkPlanId And f.dueDate is not null  ORDER BY f.serialNo"),
    @NamedQuery(name = "FbsPayplan.findAll", query = "SELECT f FROM FbsPayplan f"),
    @NamedQuery(name = "FbsPayplan.findByPayplanId", query = "SELECT f FROM FbsPayplan f WHERE f.payplanId = :payplanId"),
    @NamedQuery(name = "FbsPayplan.findByPercentage", query = "SELECT f FROM FbsPayplan f WHERE f.percentage = :percentage"),
    @NamedQuery(name = "FbsPayplan.findByFkPlanId", query = "SELECT f FROM FbsPayplan f WHERE f.fkPlanId = :fkPlanId"),
    @NamedQuery(name = "FbsPayplan.findByPlanDesc", query = "SELECT f FROM FbsPayplan f WHERE f.planDesc = :planDesc"),
    @NamedQuery(name = "FbsPayplan.findBySerialNo", query = "SELECT f FROM FbsPayplan f WHERE f.serialNo = :serialNo"),
    @NamedQuery(name = "FbsPayplan.findByFkProjId", query = "SELECT f FROM FbsPayplan f WHERE f.fkProjId = :fkProjId"),
    @NamedQuery(name = "FbsPayplan.findByDueDate", query = "SELECT f FROM FbsPayplan f WHERE f.dueDate = :dueDate")})
public class FbsPayplan implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PAYPLAN_ID")
    private Integer payplanId;
    @Column(name = "PERCENTAGE")
    private Short percentage;
    @Column(name = "FK_PLAN_ID")
    private Integer fkPlanId;
    @Column(name = "PLAN_DESC")
    private String planDesc;
    @Column(name = "SERIAL_NO")
    private Integer serialNo;
    @Column(name = "FK_PROJ_ID")
    private Integer fkProjId;
    @Column(name = "DUE_DATE")
    @Temporal(TemporalType.DATE)
    private Date dueDate;

    public FbsPayplan() {
    }

    public FbsPayplan(Integer payplanId) {
        this.payplanId = payplanId;
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

    public Integer getFkPlanId() {
        return fkPlanId;
    }

    public void setFkPlanId(Integer fkPlanId) {
        this.fkPlanId = fkPlanId;
    }

    public String getPlanDesc() {
        return planDesc;
    }

    public void setPlanDesc(String planDesc) {
        this.planDesc = planDesc;
    }

    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    public Integer getFkProjId() {
        return fkProjId;
    }

    public void setFkProjId(Integer fkProjId) {
        this.fkProjId = fkProjId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (payplanId != null ? payplanId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbsPayplan)) {
            return false;
        }
        FbsPayplan other = (FbsPayplan) object;
        if ((this.payplanId == null && other.payplanId != null) || (this.payplanId != null && !this.payplanId.equals(other.payplanId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smp.entity.FbsPayplan[payplanId=" + payplanId + "]";
    }

}
