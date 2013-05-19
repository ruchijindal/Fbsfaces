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
 * @author SMP Technologies
 */
@Entity
@Table(name = "fbs_interest")
@NamedQueries({
    @NamedQuery(name = "FbsInterest.findAll", query = "SELECT f FROM FbsInterest f"),
    @NamedQuery(name = "FbsInterest.findByInterestId", query = "SELECT f FROM FbsInterest f WHERE f.interestId = :interestId"),
    @NamedQuery(name = "FbsInterest.findByRate", query = "SELECT f FROM FbsInterest f WHERE f.rate = :rate"),
    @NamedQuery(name = "FbsInterest.findByStDate", query = "SELECT f FROM FbsInterest f WHERE f.stDate = :stDate"),
    @NamedQuery(name = "FbsInterest.findByEndDate", query = "SELECT f FROM FbsInterest f WHERE f.endDate = :endDate"),
    @NamedQuery(name = "FbsInterest.findByCompanyId", query = "SELECT f FROM FbsInterest f WHERE f.companyId = :companyId")})
public class FbsInterest implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "INTEREST_ID")
    private Integer interestId;
    @Column(name = "RATE")
    private Float rate;
    @Column(name = "ST_DATE")
    @Temporal(TemporalType.DATE)
    private Date stDate;
    @Column(name = "END_DATE")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(name = "COMPANY_ID")
    private Integer companyId;

    public FbsInterest() {
    }

    public FbsInterest(Integer interestId) {
        this.interestId = interestId;
    }

    public Integer getInterestId() {
        return interestId;
    }

    public void setInterestId(Integer interestId) {
        this.interestId = interestId;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (interestId != null ? interestId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbsInterest)) {
            return false;
        }
        FbsInterest other = (FbsInterest) object;
        if ((this.interestId == null && other.interestId != null) || (this.interestId != null && !this.interestId.equals(other.interestId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smp.entity.FbsInterest[interestId=" + interestId + "]";
    }

}
