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
@Table(name = "fbs_facility")
@NamedQueries({
    @NamedQuery(name = "FbsFacility.findAll", query = "SELECT f FROM FbsFacility f"),
    @NamedQuery(name = "FbsFacility.findByFacilityId", query = "SELECT f FROM FbsFacility f WHERE f.facilityId = :facilityId"),
    @NamedQuery(name = "FbsFacility.findByFacilityName", query = "SELECT f FROM FbsFacility f WHERE f.facilityName = :facilityName"),
    @NamedQuery(name = "FbsFacility.findBySpecification", query = "SELECT f FROM FbsFacility f WHERE f.specification = :specification"),
    @NamedQuery(name = "FbsFacility.findByImagePath", query = "SELECT f FROM FbsFacility f WHERE f.imagePath = :imagePath"),
    @NamedQuery(name = "FbsFacility.findByFkProjId", query = "SELECT f FROM FbsFacility f WHERE f.fkProjId = :fkProjId")})
public class FbsFacility implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "FACILITY_ID")
    private Integer facilityId;
    @Basic(optional = false)
    @Column(name = "FACILITY_NAME")
    private String facilityName;
    @Column(name = "SPECIFICATION")
    private String specification;
    @Column(name = "IMAGE_PATH")
    private String imagePath;
    @Basic(optional = false)
    @Column(name = "FK_PROJ_ID")
    private long fkProjId;

    public FbsFacility() {
    }

    public FbsFacility(Integer facilityId) {
        this.facilityId = facilityId;
    }

    public FbsFacility(Integer facilityId, String facilityName, long fkProjId) {
        this.facilityId = facilityId;
        this.facilityName = facilityName;
        this.fkProjId = fkProjId;
    }

    public Integer getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Integer facilityId) {
        this.facilityId = facilityId;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public long getFkProjId() {
        return fkProjId;
    }

    public void setFkProjId(long fkProjId) {
        this.fkProjId = fkProjId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (facilityId != null ? facilityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbsFacility)) {
            return false;
        }
        FbsFacility other = (FbsFacility) object;
        if ((this.facilityId == null && other.facilityId != null) || (this.facilityId != null && !this.facilityId.equals(other.facilityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smp.entity.FbsFacility[facilityId=" + facilityId + "]";
    }

}
