/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smp.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author smp
 */
@Entity
@Table(name = "fbs_planname")
@NamedQueries({
    @NamedQuery(name = "FbsPlanname.findAll", query = "SELECT f FROM FbsPlanname f"),
    @NamedQuery(name = "FbsPlanname.findByPlanId", query = "SELECT f FROM FbsPlanname f WHERE f.planId = :planId"),
    @NamedQuery(name = "FbsPlanname.findByPlanName", query = "SELECT f FROM FbsPlanname f WHERE f.planName = :planName"),
    @NamedQuery(name = "FbsPlanname.findByFkProjId", query = "SELECT f FROM FbsPlanname f WHERE f.fkProjId = :fkProjId"),
    @NamedQuery(name = "FbsPlanname.findByFkProjIdAndPlanName", query = "SELECT f FROM FbsPlanname f WHERE f.fkProjId = :fkProjId and f.planName = :planName"),
    @NamedQuery(name = "FbsPlanname.findByDiscount", query = "SELECT f FROM FbsPlanname f WHERE f.discount = :discount")})
public class FbsPlanname implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PLAN_ID")
    private Integer planId;
    @Column(name = "PLAN_NAME")
    private String planName;
    @Column(name = "FK_PROJ_ID")
    private Integer fkProjId;
    @Column(name = "DISCOUNT")
    private Integer discount;
    @Column(name = "FULL_NAME")
    private String fullName;

    public FbsPlanname() {
    }

    public FbsPlanname(Integer planId) {
        this.planId = planId;
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

    public Integer getFkProjId() {
        return fkProjId;
    }

    public void setFkProjId(Integer fkProjId) {
        this.fkProjId = fkProjId;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planId != null ? planId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbsPlanname)) {
            return false;
        }
        FbsPlanname other = (FbsPlanname) object;
        if ((this.planId == null && other.planId != null) || (this.planId != null && !this.planId.equals(other.planId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smp.entity.FbsPlanname[planId=" + planId + "]";
    }

}
