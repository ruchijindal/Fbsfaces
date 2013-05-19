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
 * @author smp7
 */
@Entity
@Table(name = "fbs_charge")
@NamedQueries({
    @NamedQuery(name = "FbsCharge.findAll", query = "SELECT f FROM FbsCharge f"),
    @NamedQuery(name = "FbsCharge.findByChargeId", query = "SELECT f FROM FbsCharge f WHERE f.chargeId = :chargeId"),
    @NamedQuery(name = "FbsCharge.findByChargeName", query = "SELECT f FROM FbsCharge f WHERE f.chargeName = :chargeName"),
    @NamedQuery(name = "FbsCharge.findByAmount", query = "SELECT f FROM FbsCharge f WHERE f.amount = :amount"),
    @NamedQuery(name = "FbsCharge.findByFkProjId", query = "SELECT f FROM FbsCharge f WHERE f.fkProjId = :fkProjId")})
public class FbsCharge implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CHARGE_ID")
    private Integer chargeId;
    @Column(name = "CHARGE_NAME")
    private String chargeName;
    @Column(name = "AMOUNT")
    private Integer amount;
    @Column(name = "FK_PROJ_ID")
    private String fkProjId;

    public FbsCharge() {
    }

    public FbsCharge(Integer chargeId) {
        this.chargeId = chargeId;
    }

    public Integer getChargeId() {
        return chargeId;
    }

    public void setChargeId(Integer chargeId) {
        this.chargeId = chargeId;
    }

    public String getChargeName() {
        return chargeName;
    }

    public void setChargeName(String chargeName) {
        this.chargeName = chargeName;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getFkProjId() {
        return fkProjId;
    }

    public void setFkProjId(String fkProjId) {
        this.fkProjId = fkProjId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (chargeId != null ? chargeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbsCharge)) {
            return false;
        }
        FbsCharge other = (FbsCharge) object;
        if ((this.chargeId == null && other.chargeId != null) || (this.chargeId != null && !this.chargeId.equals(other.chargeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smp.entity.FbsCharge[chargeId=" + chargeId + "]";
    }

}
