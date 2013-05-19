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
@Table(name = "fbs_parking_type")
@NamedQueries({
    @NamedQuery(name = "FbsParkingType.findAll", query = "SELECT f FROM FbsParkingType f"),
    @NamedQuery(name = "FbsParkingType.findByParkingTypeId", query = "SELECT f FROM FbsParkingType f WHERE f.parkingTypeId = :parkingTypeId"),
    @NamedQuery(name = "FbsParkingType.findByParkingType", query = "SELECT f FROM FbsParkingType f WHERE f.parkingType = :parkingType"),
    @NamedQuery(name = "FbsParkingType.findByParkingCharge", query = "SELECT f FROM FbsParkingType f WHERE f.parkingCharge = :parkingCharge"),
    @NamedQuery(name = "FbsParkingType.findByNoOfParking", query = "SELECT f FROM FbsParkingType f WHERE f.noOfParking = :noOfParking"),
    @NamedQuery(name = "FbsParkingType.findByFkProjId", query = "SELECT f FROM FbsParkingType f WHERE f.fkProjId = :fkProjId")})
public class FbsParkingType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PARKING_TYPE_ID")
    private Integer parkingTypeId;
    @Column(name = "PARKING_TYPE")
    private String parkingType;
    @Column(name = "PARKING_CHARGE")
    private Integer parkingCharge;
    @Column(name = "NO_OF_PARKING")
    private Integer noOfParking;
    @Column(name = "FK_PROJ_ID")
    private Integer fkProjId;

    public FbsParkingType() {
    }

    public FbsParkingType(Integer parkingTypeId) {
        this.parkingTypeId = parkingTypeId;
    }

    public Integer getParkingTypeId() {
        return parkingTypeId;
    }

    public void setParkingTypeId(Integer parkingTypeId) {
        this.parkingTypeId = parkingTypeId;
    }

    public String getParkingType() {
        return parkingType;
    }

    public void setParkingType(String parkingType) {
        this.parkingType = parkingType;
    }

    public Integer getParkingCharge() {
        return parkingCharge;
    }

    public void setParkingCharge(Integer parkingCharge) {
        this.parkingCharge = parkingCharge;
    }

    public Integer getNoOfParking() {
        return noOfParking;
    }

    public void setNoOfParking(Integer noOfParking) {
        this.noOfParking = noOfParking;
    }

    public Integer getFkProjId() {
        return fkProjId;
    }

    public void setFkProjId(Integer fkProjId) {
        this.fkProjId = fkProjId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (parkingTypeId != null ? parkingTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbsParkingType)) {
            return false;
        }
        FbsParkingType other = (FbsParkingType) object;
        if ((this.parkingTypeId == null && other.parkingTypeId != null) || (this.parkingTypeId != null && !this.parkingTypeId.equals(other.parkingTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smp.entity.FbsParkingType[parkingTypeId=" + parkingTypeId + "]";
    }

}
