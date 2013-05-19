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
@Table(name = "fbs_servicetax")
@NamedQueries({
    @NamedQuery(name = "FbsServicetax.findAll", query = "SELECT f FROM FbsServicetax f"),
    @NamedQuery(name = "FbsServicetax.findAllOrderbyDueDate", query = "SELECT f FROM FbsServicetax f ORDER BY f.stDate"),
    @NamedQuery(name = "FbsServicetax.findByStId", query = "SELECT f FROM FbsServicetax f WHERE f.stId = :stId"),
    @NamedQuery(name = "FbsServicetax.findByServicetax", query = "SELECT f FROM FbsServicetax f WHERE f.servicetax = :servicetax"),
    @NamedQuery(name = "FbsServicetax.findByStDate", query = "SELECT f FROM FbsServicetax f WHERE f.stDate = :stDate"),
    @NamedQuery(name = "FbsServicetax.findByEndDate", query = "SELECT f FROM FbsServicetax f WHERE f.endDate = :endDate")})
public class FbsServicetax implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ST_ID")
    private Integer stId;
    @Column(name = "SERVICETAX")
    private Integer servicetax;
    @Column(name = "ST_DATE")
    @Temporal(TemporalType.DATE)
    private Date stDate;
    @Column(name = "END_DATE")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    public FbsServicetax() {
    }

    public FbsServicetax(Integer stId) {
        this.stId = stId;
    }

    public Integer getStId() {
        return stId;
    }

    public void setStId(Integer stId) {
        this.stId = stId;
    }

    public Integer getServicetax() {
        return servicetax;
    }

    public void setServicetax(Integer servicetax) {
        this.servicetax = servicetax;
    }

    public Date getStDate() {
        return stDate;
    }

    public void setStDate(Date stDate) {
        this.stDate = stDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stId != null ? stId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbsServicetax)) {
            return false;
        }
        FbsServicetax other = (FbsServicetax) object;
        if ((this.stId == null && other.stId != null) || (this.stId != null && !this.stId.equals(other.stId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smp.entity.FbsServicetax[stId=" + stId + "]";
    }

}
