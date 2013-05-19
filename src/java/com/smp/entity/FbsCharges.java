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
@Table(name = "fbs_charges")
@NamedQueries({
    @NamedQuery(name = "FbsCharges.findAll", query = "SELECT f FROM FbsCharges f"),
    @NamedQuery(name = "FbsCharges.findByChargeId", query = "SELECT f FROM FbsCharges f WHERE f.chargeId = :chargeId"),
    @NamedQuery(name = "FbsCharges.findByCoveredParking", query = "SELECT f FROM FbsCharges f WHERE f.coveredParking = :coveredParking"),
    @NamedQuery(name = "FbsCharges.findByOpenParking", query = "SELECT f FROM FbsCharges f WHERE f.openParking = :openParking"),
    @NamedQuery(name = "FbsCharges.findByLeaseRent", query = "SELECT f FROM FbsCharges f WHERE f.leaseRent = :leaseRent"),
    @NamedQuery(name = "FbsCharges.findByPowerBackup", query = "SELECT f FROM FbsCharges f WHERE f.powerBackup = :powerBackup"),
    @NamedQuery(name = "FbsCharges.findByEec", query = "SELECT f FROM FbsCharges f WHERE f.eec = :eec"),
    @NamedQuery(name = "FbsCharges.findByFfc", query = "SELECT f FROM FbsCharges f WHERE f.ffc = :ffc"),
    @NamedQuery(name = "FbsCharges.findByClubCharge", query = "SELECT f FROM FbsCharges f WHERE f.clubCharge = :clubCharge"),
    @NamedQuery(name = "FbsCharges.findByMaintenance", query = "SELECT f FROM FbsCharges f WHERE f.maintenance = :maintenance"),
    @NamedQuery(name = "FbsCharges.findByAddCovered", query = "SELECT f FROM FbsCharges f WHERE f.addCovered = :addCovered"),
    @NamedQuery(name = "FbsCharges.findByOtherCharges", query = "SELECT f FROM FbsCharges f WHERE f.otherCharges = :otherCharges"),
    @NamedQuery(name = "FbsCharges.findByFkProjId", query = "SELECT f FROM FbsCharges f WHERE f.fkProjId = :fkProjId"),
    @NamedQuery(name = "FbsCharges.findByAddOpen", query = "SELECT f FROM FbsCharges f WHERE f.addOpen = :addOpen")})
public class FbsCharges implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CHARGE_ID")
    private Integer chargeId;
    @Column(name = "COVERED_PARKING")
    private Long coveredParking;
    @Column(name = "OPEN_PARKING")
    private Long openParking;
    @Column(name = "LEASE_RENT")
    private Long leaseRent;
    @Column(name = "POWER_BACKUP")
    private Long powerBackup;

    @Column(name = "EEC")
    private long eec;

    @Column(name = "FFC")
    private long ffc;
    @Column(name = "CLUB_CHARGE")
    private Long clubCharge;
    @Basic(optional = false)
    @Column(name = "MAINTENANCE")
    private long maintenance;
    @Column(name = "ADD_COVERED")
    private Long addCovered;
    @Column(name = "OTHER_CHARGES")
    private Long otherCharges;
    
    @Column(name = "FK_PROJ_ID")
    private long fkProjId;
    @Column(name = "ADD_OPEN")
    private Long addOpen;

    public FbsCharges() {
    }

    public FbsCharges(Integer chargeId) {
        this.chargeId = chargeId;
    }

    public FbsCharges(Integer chargeId, long eec, long ffc, long maintenance, long fkProjId) {
        this.chargeId = chargeId;
        this.eec = eec;
        this.ffc = ffc;
        this.maintenance = maintenance;
        this.fkProjId = fkProjId;
    }

    public Integer getChargeId() {
        return chargeId;
    }

    public void setChargeId(Integer chargeId) {
        this.chargeId = chargeId;
    }

    public Long getCoveredParking() {
        return coveredParking;
    }

    public void setCoveredParking(Long coveredParking) {
        this.coveredParking = coveredParking;
    }

    public Long getOpenParking() {
        return openParking;
    }

    public void setOpenParking(Long openParking) {
        this.openParking = openParking;
    }

    public Long getLeaseRent() {
        return leaseRent;
    }

    public void setLeaseRent(Long leaseRent) {
        this.leaseRent = leaseRent;
    }

    public Long getPowerBackup() {
        return powerBackup;
    }

    public void setPowerBackup(Long powerBackup) {
        this.powerBackup = powerBackup;
    }

    public long getEec() {
        return eec;
    }

    public void setEec(long eec) {
        this.eec = eec;
    }

    public long getFfc() {
        return ffc;
    }

    public void setFfc(long ffc) {
        this.ffc = ffc;
    }

    public Long getClubCharge() {
        return clubCharge;
    }

    public void setClubCharge(Long clubCharge) {
        this.clubCharge = clubCharge;
    }

    public long getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(long maintenance) {
        this.maintenance = maintenance;
    }

    public Long getAddCovered() {
        return addCovered;
    }

    public void setAddCovered(Long addCovered) {
        this.addCovered = addCovered;
    }

    public Long getOtherCharges() {
        return otherCharges;
    }

    public void setOtherCharges(Long otherCharges) {
        this.otherCharges = otherCharges;
    }

    public long getFkProjId() {
        return fkProjId;
    }

    public void setFkProjId(long fkProjId) {
        this.fkProjId = fkProjId;
    }

    public Long getAddOpen() {
        return addOpen;
    }

    public void setAddOpen(Long addOpen) {
        this.addOpen = addOpen;
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
        if (!(object instanceof FbsCharges)) {
            return false;
        }
        FbsCharges other = (FbsCharges) object;
        if ((this.chargeId == null && other.chargeId != null) || (this.chargeId != null && !this.chargeId.equals(other.chargeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smp.entity.FbsCharges[chargeId=" + chargeId + "]";
    }

}
