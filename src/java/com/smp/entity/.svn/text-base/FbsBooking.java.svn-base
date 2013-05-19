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
 * @author smp7
 */
@Entity
@Table(name = "fbs_booking")
@NamedQueries({
    @NamedQuery(name = "FbsBooking.findAll", query = "SELECT f FROM FbsBooking f"),
    @NamedQuery(name = "FbsBooking.findByRegNumber", query = "SELECT f FROM FbsBooking f WHERE f.regNumber = :regNumber"),
    @NamedQuery(name = "FbsBooking.findByFlatId", query = "SELECT f FROM FbsBooking f WHERE f.flatId = :flatId"),
    @NamedQuery(name = "FbsBooking.findByBlockId", query = "SELECT f FROM FbsBooking f WHERE f.blockId = :blockId"),
    @NamedQuery(name = "FbsBooking.findByPlcId", query = "SELECT f FROM FbsBooking f WHERE f.plcId = :plcId"),
    @NamedQuery(name = "FbsBooking.findByPlanId", query = "SELECT f FROM FbsBooking f WHERE f.planId = :planId"),
    @NamedQuery(name = "FbsBooking.findByDiscountId", query = "SELECT f FROM FbsBooking f WHERE f.discountId = :discountId"),
    @NamedQuery(name = "FbsBooking.findByBookingDt", query = "SELECT f FROM FbsBooking f WHERE f.bookingDt = :bookingDt"),
    @NamedQuery(name = "FbsBooking.findByStatus", query = "SELECT f FROM FbsBooking f WHERE f.status = :status"),
    @NamedQuery(name = "FbsBooking.findByUserId", query = "SELECT f FROM FbsBooking f WHERE f.userId = :userId"),
    @NamedQuery(name = "FbsBooking.findByBlockIdAndFlatId", query = "SELECT f FROM FbsBooking f WHERE f.flatId = :flatId AND f.blockId = :blockId"),
    @NamedQuery(name = "FbsBooking.findByBrokerId", query = "SELECT f FROM FbsBooking f WHERE f.brokerId = :brokerId"),
    @NamedQuery(name = "FbsBooking.distinctBrokerId", query = "SELECT DISTINCT f.brokerId from FbsBooking f"),
    @NamedQuery(name = "FbsBooking.flatId&brokerId", query = "SELECT  f from FbsBooking f WHERE f.brokerId = :brokerId AND f.flatId = :flatId")
})
public class FbsBooking implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "REG_NUMBER")
    private Integer regNumber;
    @Column(name = "FLAT_ID")
    private Integer flatId;
    @Column(name = "BLOCK_ID")
    private Integer blockId;
    @Column(name = "PLC_ID")
    private Integer plcId;
    @Column(name = "PLAN_ID")
    private Integer planId;
    @Column(name = "DISCOUNT_ID")
    private Integer discountId;
    @Column(name = "BOOKING_DT")
    @Temporal(TemporalType.DATE)
    private Date bookingDt;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "BROKER_ID")
    private Integer brokerId;

    public FbsBooking() {
    }

    public FbsBooking(Integer regNumber) {
        this.regNumber = regNumber;
    }

    public Integer getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(Integer regNumber) {
        this.regNumber = regNumber;
    }

    public Integer getFlatId() {
        return flatId;
    }

    public void setFlatId(Integer flatId) {
        this.flatId = flatId;
    }

    public Integer getBlockId() {
        return blockId;
    }

    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }

    public Integer getPlcId() {
        return plcId;
    }

    public void setPlcId(Integer plcId) {
        this.plcId = plcId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }

    public Date getBookingDt() {
        return bookingDt;
    }

    public void setBookingDt(Date bookingDt) {
        this.bookingDt = bookingDt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(Integer brokerId) {
        this.brokerId = brokerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (regNumber != null ? regNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbsBooking)) {
            return false;
        }
        FbsBooking other = (FbsBooking) object;
        if ((this.regNumber == null && other.regNumber != null) || (this.regNumber != null && !this.regNumber.equals(other.regNumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smp.entity.FbsBooking[regNumber=" + regNumber + "]";
    }
}