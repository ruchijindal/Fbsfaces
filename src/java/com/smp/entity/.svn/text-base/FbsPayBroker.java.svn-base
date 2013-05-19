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
@Table(name = "fbs_pay_broker")
@NamedQueries({
    @NamedQuery(name = "FbsPayBroker.findAll", query = "SELECT f FROM FbsPayBroker f"),
    @NamedQuery(name = "FbsPayBroker.findByPaymentId", query = "SELECT f FROM FbsPayBroker f WHERE f.paymentId = :paymentId"),
    @NamedQuery(name = "FbsPayBroker.findByBrokerId", query = "SELECT f FROM FbsPayBroker f WHERE f.brokerId = :brokerId"),
    @NamedQuery(name = "FbsPayBroker.findByBookingDate", query = "SELECT f FROM FbsPayBroker f WHERE f.bookingDate = :bookingDate"),
    @NamedQuery(name = "FbsPayBroker.findByPaymentDate", query = "SELECT f FROM FbsPayBroker f WHERE f.paymentDate = :paymentDate"),
    @NamedQuery(name = "FbsPayBroker.findByPaymentMode", query = "SELECT f FROM FbsPayBroker f WHERE f.paymentMode = :paymentMode"),
    @NamedQuery(name = "FbsPayBroker.findByAmount", query = "SELECT f FROM FbsPayBroker f WHERE f.amount = :amount"),
    @NamedQuery(name = "FbsPayBroker.findByChequeDate", query = "SELECT f FROM FbsPayBroker f WHERE f.chequeDate = :chequeDate"),
    @NamedQuery(name = "FbsPayBroker.findByChequeNo", query = "SELECT f FROM FbsPayBroker f WHERE f.chequeNo = :chequeNo"),
    @NamedQuery(name = "FbsPayBroker.findByDrawnOn", query = "SELECT f FROM FbsPayBroker f WHERE f.drawnOn = :drawnOn"),
    @NamedQuery(name = "FbsPayBroker.findByStatus", query = "SELECT f FROM FbsPayBroker f WHERE f.status = :status"),
    @NamedQuery(name = "FbsPayBroker.findByFkFlatId", query = "SELECT f FROM FbsPayBroker f WHERE f.fkFlatId = :fkFlatId")})
public class FbsPayBroker implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PAYMENT_ID")
    private Integer paymentId;
    @Column(name = "BROKER_ID")
    private Integer brokerId;
    @Column(name = "BOOKING_DATE")
    @Temporal(TemporalType.DATE)
    private Date bookingDate;
    @Column(name = "PAYMENT_DATE")
    @Temporal(TemporalType.DATE)
    private Date paymentDate;
    @Column(name = "PAYMENT_MODE")
    private String paymentMode;
    @Column(name = "AMOUNT")
    private Integer amount;
    @Column(name = "CHEQUE_DATE")
    @Temporal(TemporalType.DATE)
    private Date chequeDate;
    @Column(name = "CHEQUE_NO")
    private Integer chequeNo;
    @Column(name = "DRAWN_ON")
    private String drawnOn;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "FK_FLAT_ID")
    private Integer fkFlatId;

    public FbsPayBroker() {
    }

    public FbsPayBroker(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(Integer brokerId) {
        this.brokerId = brokerId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getChequeDate() {
        return chequeDate;
    }

    public void setChequeDate(Date chequeDate) {
        this.chequeDate = chequeDate;
    }

    public Integer getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(Integer chequeNo) {
        this.chequeNo = chequeNo;
    }

    public String getDrawnOn() {
        return drawnOn;
    }

    public void setDrawnOn(String drawnOn) {
        this.drawnOn = drawnOn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        hash += (paymentId != null ? paymentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbsPayBroker)) {
            return false;
        }
        FbsPayBroker other = (FbsPayBroker) object;
        if ((this.paymentId == null && other.paymentId != null) || (this.paymentId != null && !this.paymentId.equals(other.paymentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smp.entity.FbsPayBroker[paymentId=" + paymentId + "]";
    }

}
