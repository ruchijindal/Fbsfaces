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
@Table(name = "fbs_parking")
@NamedQueries({
    @NamedQuery(name = "FbsParking.findAll", query = "SELECT f FROM FbsParking f"),
    @NamedQuery(name = "FbsParking.findByParkingId", query = "SELECT f FROM FbsParking f WHERE f.parkingId = :parkingId"),
    @NamedQuery(name = "FbsParking.findByCoveredCharge", query = "SELECT f FROM FbsParking f WHERE f.coveredCharge = :coveredCharge"),
    @NamedQuery(name = "FbsParking.findByUncoveredCharge", query = "SELECT f FROM FbsParking f WHERE f.uncoveredCharge = :uncoveredCharge"),
    @NamedQuery(name = "FbsParking.findByCoveredNo", query = "SELECT f FROM FbsParking f WHERE f.coveredNo = :coveredNo"),
    @NamedQuery(name = "FbsParking.findByUncoveredNo", query = "SELECT f FROM FbsParking f WHERE f.uncoveredNo = :uncoveredNo"),
    @NamedQuery(name = "FbsParking.findByFkProjId", query = "SELECT f FROM FbsParking f WHERE f.fkProjId = :fkProjId")})
public class FbsParking implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PARKING_ID")
    private Integer parkingId;
    @Column(name = "COVERED_CHARGE")
    private Integer coveredCharge;
    @Column(name = "UNCOVERED_CHARGE")
    private Integer uncoveredCharge;
    @Column(name = "COVERED_NO")
    private Integer coveredNo;
    @Column(name = "UNCOVERED_NO")
    private Integer uncoveredNo;
    @Column(name = "FK_PROJ_ID")
    private String fkProjId;

    public FbsParking() {
    }

    public FbsParking(Integer parkingId) {
        this.parkingId = parkingId;
    }

    public Integer getParkingId() {
        return parkingId;
    }

    public void setParkingId(Integer parkingId) {
        this.parkingId = parkingId;
    }

    public Integer getCoveredCharge() {
        return coveredCharge;
    }

    public void setCoveredCharge(Integer coveredCharge) {
        this.coveredCharge = coveredCharge;
    }

    public Integer getUncoveredCharge() {
        return uncoveredCharge;
    }

    public void setUncoveredCharge(Integer uncoveredCharge) {
        this.uncoveredCharge = uncoveredCharge;
    }

    public Integer getCoveredNo() {
        return coveredNo;
    }

    public void setCoveredNo(Integer coveredNo) {
        this.coveredNo = coveredNo;
    }

    public Integer getUncoveredNo() {
        return uncoveredNo;
    }

    public void setUncoveredNo(Integer uncoveredNo) {
        this.uncoveredNo = uncoveredNo;
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
        hash += (parkingId != null ? parkingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbsParking)) {
            return false;
        }
        FbsParking other = (FbsParking) object;
        if ((this.parkingId == null && other.parkingId != null) || (this.parkingId != null && !this.parkingId.equals(other.parkingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smp.entity.FbsParking[parkingId=" + parkingId + "]";
    }

}
