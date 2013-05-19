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
@Table(name = "fbs_project")
@NamedQueries({
    @NamedQuery(name = "FbsProject.findAll", query = "SELECT f FROM FbsProject f"),
    @NamedQuery(name = "FbsProject.findByProjId", query = "SELECT f FROM FbsProject f WHERE f.projId = :projId"),
    @NamedQuery(name = "FbsProject.findByProjCode", query = "SELECT f FROM FbsProject f WHERE f.projCode = :projCode"),
    @NamedQuery(name = "FbsProject.findByProjName", query = "SELECT f FROM FbsProject f WHERE f.projName = :projName"),
    @NamedQuery(name = "FbsProject.findByProjType", query = "SELECT f FROM FbsProject f WHERE f.projType = :projType"),
    @NamedQuery(name = "FbsProject.findByAddress", query = "SELECT f FROM FbsProject f WHERE f.address = :address"),
    @NamedQuery(name = "FbsProject.findByState", query = "SELECT f FROM FbsProject f WHERE f.state = :state"),
    @NamedQuery(name = "FbsProject.findByCity", query = "SELECT f FROM FbsProject f WHERE f.city = :city"),
    @NamedQuery(name = "FbsProject.findByStartDt", query = "SELECT f FROM FbsProject f WHERE f.startDt = :startDt"),
    @NamedQuery(name = "FbsProject.findByEndDt", query = "SELECT f FROM FbsProject f WHERE f.endDt = :endDt"),
    @NamedQuery(name = "FbsProject.findByCompanyId", query = "SELECT f FROM FbsProject f WHERE f.companyId = :companyId"),
    @NamedQuery(name = "FbsProject.findByImagePath", query = "SELECT f FROM FbsProject f WHERE f.imagePath = :imagePath"),
    @NamedQuery(name = "FbsProject.findByBrCommission", query = "SELECT f FROM FbsProject f WHERE f.brCommission = :brCommission")})
public class FbsProject implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PROJ_ID")
    private Integer projId;
    @Basic(optional = false)
    @Column(name = "PROJ_CODE")
    private String projCode;
    @Basic(optional = false)
    @Column(name = "PROJ_NAME")
    private String projName;
    @Basic(optional = false)
    @Column(name = "PROJ_TYPE")
    private String projType;
    @Column(name = "ADDRESS")
    private String address;
    @Basic(optional = false)
    @Column(name = "STATE")
    private String state;
    @Basic(optional = false)
    @Column(name = "CITY")
    private String city;
    @Basic(optional = false)
    @Column(name = "START_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDt;
    @Column(name = "END_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDt;
    @Column(name = "COMPANY_ID")
    private Integer companyId;
    @Column(name = "IMAGE_PATH")
    private String imagePath;
    @Column(name = "BR_COMMISSION")
    private Integer brCommission;

    public FbsProject() {
    }

    public FbsProject(Integer projId) {
        this.projId = projId;
    }

    public FbsProject(Integer projId, String projCode, String projName, String projType, String state, String city, Date startDt) {
        this.projId = projId;
        this.projCode = projCode;
        this.projName = projName;
        this.projType = projType;
        this.state = state;
        this.city = city;
        this.startDt = startDt;
    }

    public Integer getProjId() {
        return projId;
    }

    public void setProjId(Integer projId) {
        this.projId = projId;
    }

    public String getProjCode() {
        return projCode;
    }

    public void setProjCode(String projCode) {
        this.projCode = projCode;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getProjType() {
        return projType;
    }

    public void setProjType(String projType) {
        this.projType = projType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getStartDt() {
        return startDt;
    }

    public void setStartDt(Date startDt) {
        this.startDt = startDt;
    }

    public Date getEndDt() {
        return endDt;
    }

    public void setEndDt(Date endDt) {
        this.endDt = endDt;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getBrCommission() {
        return brCommission;
    }

    public void setBrCommission(Integer brCommission) {
        this.brCommission = brCommission;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projId != null ? projId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbsProject)) {
            return false;
        }
        FbsProject other = (FbsProject) object;
        if ((this.projId == null && other.projId != null) || (this.projId != null && !this.projId.equals(other.projId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smp.entity.FbsProject[projId=" + projId + "]";
    }

}
