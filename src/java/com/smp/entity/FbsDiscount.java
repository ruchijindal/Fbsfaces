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
@Table(name = "fbs_discount")
@NamedQueries({
    @NamedQuery(name = "FbsDiscount.findAll", query = "SELECT f FROM FbsDiscount f"),
    @NamedQuery(name = "FbsDiscount.findByDiscountId", query = "SELECT f FROM FbsDiscount f WHERE f.discountId = :discountId"),
    @NamedQuery(name = "FbsDiscount.findByDiscountType", query = "SELECT f FROM FbsDiscount f WHERE f.discountType = :discountType"),
    @NamedQuery(name = "FbsDiscount.findByPercentage", query = "SELECT f FROM FbsDiscount f WHERE f.percentage = :percentage"),
    @NamedQuery(name = "FbsDiscount.findByCompanyId", query = "SELECT f FROM FbsDiscount f WHERE f.companyId = :companyId")})
public class FbsDiscount implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "DISCOUNT_ID")
    private Integer discountId;
    @Column(name = "DISCOUNT_TYPE")
    private String discountType;
    @Column(name = "PERCENTAGE")
    private Integer percentage;
    @Column(name = "COMPANY_ID")
    private Integer companyId;

    public FbsDiscount() {
    }

    public FbsDiscount(Integer discountId) {
        this.discountId = discountId;
    }

    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
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
        hash += (discountId != null ? discountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbsDiscount)) {
            return false;
        }
        FbsDiscount other = (FbsDiscount) object;
        if ((this.discountId == null && other.discountId != null) || (this.discountId != null && !this.discountId.equals(other.discountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smp.entity.FbsDiscount[discountId=" + discountId + "]";
    }

}
