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
import javax.persistence.Lob;
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
@Table(name = "fbs_payment")
@NamedQueries({
    @NamedQuery(name = "FbsPayment.findAll", query = "SELECT f FROM FbsPayment f"),
    @NamedQuery(name = "FbsPayment.findByPaymentId", query = "SELECT f FROM FbsPayment f WHERE f.paymentId = :paymentId"),
    @NamedQuery(name = "FbsPayment.findByPaidAmount", query = "SELECT f FROM FbsPayment f WHERE f.paidAmount = :paidAmount"),
    @NamedQuery(name = "FbsPayment.findByServiceTax", query = "SELECT f FROM FbsPayment f WHERE f.serviceTax = :serviceTax"),
    @NamedQuery(name = "FbsPayment.findByChequeDate", query = "SELECT f FROM FbsPayment f WHERE f.chequeDate = :chequeDate"),
    @NamedQuery(name = "FbsPayment.findByChequeAmt", query = "SELECT f FROM FbsPayment f WHERE f.chequeAmt = :chequeAmt"),
    @NamedQuery(name = "FbsPayment.findByPaymentMode", query = "SELECT f FROM FbsPayment f WHERE f.paymentMode = :paymentMode"),
    @NamedQuery(name = "FbsPayment.findByPaymentDate", query = "SELECT f FROM FbsPayment f WHERE f.paymentDate = :paymentDate"),
    //@NamedQuery(name = "FbsPayment.FindBystartdateAndUnitCode", query = "Select f from FbsPayment f Where  f.unitCode = :unitCode and f.paymentDate > :startDate"),
    //@NamedQuery(name = "FbsPayment.FindBystartdate1AndUnitCode", query = "Select f from FbsPayment f Where  f.unitCode = :unitCode and f.paymentDate =:startDate and f.paymentMode ='cash"),
    //@NamedQuery(name = "FbsPayment.FindBystartdateAndUnitCode", query = "Select f from FbsPayment f Where  f.unitCode = :unitCode and f.paymentDate > :startDate "),
    //@NamedQuery(name = "FbsPayment.FindBystartdate1AndUnitCode", query = "Select f from FbsPayment f Where  f.unitCode = :unitCode and f.paymentDate =:startDate and f.paymentMode ='cash'"),
    //@NamedQuery(name = "FbsPayment.FindByDueDateAndUnitCodeandCheque", query = "Select f from FbsPayment f Where  f.unitCode = :unitCode and f.paymentDate between :startDate AND :endDate and f.paymentMode ='cheque' and f.chequeStatus='c'"),
    //@NamedQuery(name = "FbsPayment.FindBystartdateAndUnitCodeandCheque", query = "Select f from FbsPayment f Where  f.unitCode = :unitCode and f.paymentDate > :startDate and f.paymentMode ='cheque' and f.chequeStatus='c'"),
    //@NamedQuery(name = "FbsPayment.FindBystartdate1AndUnitCodeandCheque", query = "Select f from FbsPayment f Where  f.unitCode = :unitCode and f.paymentDate =:startDate and f.paymentMode ='cheque' and f.chequeStatus='c'"),
    //@NamedQuery(name = "FbsPayment.findByTransactionBank", query = "SELECT f FROM FbsPayment f WHERE f.transactionBank = :transactionBank"),
    //@NamedQuery(name = "FbsPayment.FindByDueDateAndUnitCode",query="Select f from FbsPayment f Where  f.unitCode = :unitCode and f.paymentDate between :startDate AND :endDate and f.paymentMode ='cash'"),
//    @NamedQuery(name = "FbsPayment.FindByDueDateAndUnitCode",query="Select f from FbsPayment f Where  f.unitCode = :unitCode and f.paymentDate between :startDate AND :endDate and f.paymentMode ='cash'"),
    @NamedQuery(name = "FbsPayment.FindBystartdateAndUnitCode", query = "Select f from FbsPayment f Where  f.unitCode = :unitCode and f.paymentDate > :startDate and f.chequeStatus='Cleared'"),
    @NamedQuery(name = "FbsPayment.FindBystartdate1AndUnitCode", query = "Select f from FbsPayment f Where  f.unitCode = :unitCode and f.paymentDate =:startDate and f.paymentMode ='cash'"),
    //@NamedQuery(name=  "FbsPayment.FindBystartdateAndFirstDate",query="Select f from FbsPayment f where f.startdate=:startdate < f.FirstDate=:FirstDate"),
    //@NamedQuery(name=  "FbsPayment.FindByFirstDateAndendDate",query="Select f from FbsPayment f where f.FirstDate=:FirstDate < f.endDate=:endDate"),
@NamedQuery(name="FbsPayment.FindByDueDateAndUnitCode",query="Select f from FbsPayment f Where  f.unitCode = :unitCode and f.paymentDate between :startDate AND :endDate and f.paymentMode ='cash'"),
    @NamedQuery(name = "FbsPayment.FindByDueDateAndUnitCodeandCheque", query = "Select f from FbsPayment f Where  f.unitCode = :unitCode and f.paymentDate between :startDate AND :endDate and f.paymentMode ='cheque' and f.chequeStatus='c'"),
    @NamedQuery(name = "FbsPayment.FindBystartdateAndUnitCodeandCheque", query = "Select f from FbsPayment f Where  f.unitCode = :unitCode and f.paymentDate > :startDate and f.paymentMode ='cheque' and f.chequeStatus='c'"),
    @NamedQuery(name = "FbsPayment.FindBystartdate1AndUnitCodeandCheque", query = "Select f from FbsPayment f Where  f.unitCode = :unitCode and f.paymentDate =:startDate and f.paymentMode ='cheque' and f.chequeStatus='c'"),
    
    @NamedQuery(name = "FbsPayment.findByClearingDt", query = "SELECT f FROM FbsPayment f WHERE f.clearingDt = :clearingDt"),
    @NamedQuery(name = "FbsPayment.findByClearingBankId", query = "SELECT f FROM FbsPayment f WHERE f.clearingBankId = :clearingBankId"),
    @NamedQuery(name = "FbsPayment.findByAuthorize", query = "SELECT f FROM FbsPayment f WHERE f.authorize = :authorize"),
    @NamedQuery(name = "FbsPayment.findByAuthorizedBy", query = "SELECT f FROM FbsPayment f WHERE f.authorizedBy = :authorizedBy"),
    @NamedQuery(name = "FbsPayment.findByRemark", query = "SELECT f FROM FbsPayment f WHERE f.remark = :remark"),
    @NamedQuery(name = "FbsPayment.findByUserId", query = "SELECT f FROM FbsPayment f WHERE f.userId = :userId"),
    @NamedQuery(name = "FbsPayment.findByBlockId", query = "SELECT f FROM FbsPayment f WHERE f.blockId = :blockId"),
    @NamedQuery(name = "FbsPayment.findByUnitCode", query = "SELECT f FROM FbsPayment f WHERE f.unitCode = :unitCode"),
    @NamedQuery(name = "FbsPayment.findByUnitCodeAndStatus", query = "SELECT f FROM FbsPayment f WHERE f.unitCode = :unitCode and f.chequeStatus='cleared'"),
    @NamedQuery(name = "FbsPayment.findByTransactionId", query = "SELECT f FROM FbsPayment f WHERE f.transactionId = :transactionId"),
    @NamedQuery(name = "FbsPayment.findByTransactionBank", query = "SELECT f FROM FbsPayment f WHERE f.transactionBank = :transactionBank")})
public class FbsPayment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PAYMENT_ID")
    private Integer paymentId;
    @Column(name = "PAID_AMOUNT")
    private Long paidAmount;
    @Column(name = "SERVICE_TAX")
    private Integer serviceTax;
    @Lob
    @Column(name = "CHEQUE_NO")
    private String chequeNo;
    @Column(name = "CHEQUE_DATE")
    @Temporal(TemporalType.DATE)
    private Date chequeDate;
    @Column(name = "CHEQUE_AMT")
    private Long chequeAmt;
    @Lob
    @Column(name = "DRAWN_ON")
    private String drawnOn;
    @Lob
    @Column(name = "CHEQUE_STATUS")
    private String chequeStatus;
    @Column(name = "PAYMENT_MODE")
    private String paymentMode;
    @Column(name = "PAYMENT_DATE")
    @Temporal(TemporalType.DATE)
    private Date paymentDate;
    @Column(name = "CLEARING_DT")
    @Temporal(TemporalType.DATE)
    private Date clearingDt;
    @Column(name = "CLEARING_BANK_ID")
    private Integer clearingBankId;
    @Column(name = "AUTHORIZE")
    private String authorize;
    @Column(name = "AUTHORIZED_BY")
    private String authorizedBy;
    @Column(name = "REMARK")
    private String remark;
    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "BLOCK_ID")
    private Integer blockId;
    @Column(name = "UNIT_CODE")
    private String unitCode;
    @Column(name = "TRANSACTION_ID")
    private String transactionId;
    @Column(name = "TRANSACTION_BANK")
    private String transactionBank;

    public FbsPayment() {
    }

    public FbsPayment(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Long getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Long paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Integer getServiceTax() {
        return serviceTax;
    }

    public void setServiceTax(Integer serviceTax) {
        this.serviceTax = serviceTax;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public Date getChequeDate() {
        return chequeDate;
    }

    public void setChequeDate(Date chequeDate) {
        this.chequeDate = chequeDate;
    }

    public Long getChequeAmt() {
        return chequeAmt;
    }

    public void setChequeAmt(Long chequeAmt) {
        this.chequeAmt = chequeAmt;
    }

    public String getDrawnOn() {
        return drawnOn;
    }

    public void setDrawnOn(String drawnOn) {
        this.drawnOn = drawnOn;
    }

    public String getChequeStatus() {
        return chequeStatus;
    }

    public void setChequeStatus(String chequeStatus) {
        this.chequeStatus = chequeStatus;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Date getClearingDt() {
        return clearingDt;
    }

    public void setClearingDt(Date clearingDt) {
        this.clearingDt = clearingDt;
    }

    public Integer getClearingBankId() {
        return clearingBankId;
    }

    public void setClearingBankId(Integer clearingBankId) {
        this.clearingBankId = clearingBankId;
    }

    public String getAuthorize() {
        return authorize;
    }

    public void setAuthorize(String authorize) {
        this.authorize = authorize;
    }

    public String getAuthorizedBy() {
        return authorizedBy;
    }

    public void setAuthorizedBy(String authorizedBy) {
        this.authorizedBy = authorizedBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getBlockId() {
        return blockId;
    }

    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionBank() {
        return transactionBank;
    }

    public void setTransactionBank(String transactionBank) {
        this.transactionBank = transactionBank;
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
        if (!(object instanceof FbsPayment)) {
            return false;
        }
        FbsPayment other = (FbsPayment) object;
        if ((this.paymentId == null && other.paymentId != null) || (this.paymentId != null && !this.paymentId.equals(other.paymentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smp.entity.FbsPayment[paymentId=" + paymentId + "]";
    }

}
