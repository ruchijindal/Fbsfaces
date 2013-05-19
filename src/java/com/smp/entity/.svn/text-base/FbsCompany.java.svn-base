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
@Table(name = "fbs_company")
@NamedQueries({
    @NamedQuery(name = "FbsCompany.findAll", query = "SELECT f FROM FbsCompany f"),
    @NamedQuery(name = "FbsCompany.findByCompanyId", query = "SELECT f FROM FbsCompany f WHERE f.companyId = :companyId"),
    @NamedQuery(name = "FbsCompany.findByCompanyName", query = "SELECT f FROM FbsCompany f WHERE f.companyName = :companyName"),
    @NamedQuery(name = "FbsCompany.findByCompanyAbrv", query = "SELECT f FROM FbsCompany f WHERE f.companyAbrv = :companyAbrv"),
    @NamedQuery(name = "FbsCompany.findByAddress", query = "SELECT f FROM FbsCompany f WHERE f.address = :address"),
    @NamedQuery(name = "FbsCompany.findByTelNumber", query = "SELECT f FROM FbsCompany f WHERE f.telNumber = :telNumber"),
    @NamedQuery(name = "FbsCompany.findByMobile", query = "SELECT f FROM FbsCompany f WHERE f.mobile = :mobile"),
    @NamedQuery(name = "FbsCompany.findByEmail", query = "SELECT f FROM FbsCompany f WHERE f.email = :email"),
    @NamedQuery(name = "FbsCompany.findByWebsite", query = "SELECT f FROM FbsCompany f WHERE f.website = :website")})
public class FbsCompany implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "COMPANY_ID")
    private Integer companyId;
    @Basic(optional = false)
    @Column(name = "COMPANY_NAME")
    private String companyName;
    @Basic(optional = false)
    @Column(name = "COMPANY_ABRV")
    private String companyAbrv;
    @Basic(optional = false)
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "TEL_NUMBER")
    private String telNumber;
    @Column(name = "MOBILE")
    private Long mobile;
    @Basic(optional = false)
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "WEBSITE")
    private String website;

    public FbsCompany() {
    }

    public FbsCompany(Integer companyId) {
        this.companyId = companyId;
    }

    public FbsCompany(Integer companyId, String companyName, String companyAbrv, String address, String email) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.companyAbrv = companyAbrv;
        this.address = address;
        this.email = email;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAbrv() {
        return companyAbrv;
    }

    public void setCompanyAbrv(String companyAbrv) {
        this.companyAbrv = companyAbrv;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (companyId != null ? companyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbsCompany)) {
            return false;
        }
        FbsCompany other = (FbsCompany) object;
        if ((this.companyId == null && other.companyId != null) || (this.companyId != null && !this.companyId.equals(other.companyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smp.entity.FbsCompany[companyId=" + companyId + "]";
    }

}
