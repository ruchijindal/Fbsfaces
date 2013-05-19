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
@Table(name = "fbs_parking_allot")
@NamedQueries({
    @NamedQuery(name = "FbsParkingAllot.findAll", query = "SELECT f FROM FbsParkingAllot f"),
    @NamedQuery(name = "FbsParkingAllot.findByAllotId", query = "SELECT f FROM FbsParkingAllot f WHERE f.allotId = :allotId"),
    @NamedQuery(name = "FbsParkingAllot.findByParkingTypeId", query = "SELECT f FROM FbsParkingAllot f WHERE f.parkingTypeId = :parkingTypeId"),
    @NamedQuery(name = "FbsParkingAllot.findByFkFlatId", query = "SELECT f FROM FbsParkingAllot f WHERE f.fkFlatId = :fkFlatId")})
public class FbsParkingAllot implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ALLOT_ID")
    private Integer allotId;
    @Column(name = "PARKING_TYPE_ID")
    private Integer parkingTypeId;
    @Column(name = "FK_FLAT_ID")
    private Integer fkFlatId;

    public FbsParkingAllot() {
    }

    public FbsParkingAllot(Integer allotId) {
        this.allotId = allotId;
    }

    public Integer getAllotId() {
        return allotId;
    }

    public void setAllotId(Integer allotId) {
        this.allotId = allotId;
    }

    public Integer getParkingTypeId() {
        return parkingTypeId;
    }

    public void setParkingTypeId(Integer parkingTypeId) {
        this.parkingTypeId = parkingTypeId;
    }

    public Integer getFkFlatId() {
        return fkFlatId;
    }

    public void setFkFlatId(Integer fkFlatId) {
        this.fkFlatId = fkFlatId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (allotId != null ? allotId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbsParkingAllot)) {
            return false;
        }
        FbsParkingAllot other = (FbsParkingAllot) object;
        if ((this.allotId == null && other.allotId != null) || (this.allotId != null && !this.allotId.equals(other.allotId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smp.entity.FbsParkingAllot[allotId=" + allotId + "]";
    }

}
