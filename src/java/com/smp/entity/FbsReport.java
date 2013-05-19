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
@Table(name = "fbs_report")
@NamedQueries({
    @NamedQuery(name = "FbsReport.findAll", query = "SELECT f FROM FbsReport f"),
    @NamedQuery(name = "FbsReport.findById", query = "SELECT f FROM FbsReport f WHERE f.id = :id"),
    @NamedQuery(name = "FbsReport.findByRegNumber", query = "SELECT f FROM FbsReport f WHERE f.regNumber = :regNumber"),
    @NamedQuery(name = "FbsReport.findByDate", query = "SELECT f FROM FbsReport f WHERE f.date = :date"),
    @NamedQuery(name = "FbsReport.findByRecievedAmt", query = "SELECT f FROM FbsReport f WHERE f.recievedAmt = :recievedAmt"),
    @NamedQuery(name = "FbsReport.findByCurInstallment", query = "SELECT f FROM FbsReport f WHERE f.curInstallment = :curInstallment"),
    @NamedQuery(name = "FbsReport.findByOutCredit", query = "SELECT f FROM FbsReport f WHERE f.outCredit = :outCredit"),
    @NamedQuery(name = "FbsReport.findByAmountPayable", query = "SELECT f FROM FbsReport f WHERE f.amountPayable = :amountPayable"),
    @NamedQuery(name = "FbsReport.findByServiceTax", query = "SELECT f FROM FbsReport f WHERE f.serviceTax = :serviceTax"),
    @NamedQuery(name = "FbsReport.findByTotalCost", query = "SELECT f FROM FbsReport f WHERE f.totalCost = :totalCost"),
    @NamedQuery(name = "FbsReport.findByRemainingAmt", query = "SELECT f FROM FbsReport f WHERE f.remainingAmt = :remainingAmt")})
public class FbsReport implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "REG_NUMBER")
    private Integer regNumber;
    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "RECIEVED_AMT")
    private Float recievedAmt;
    @Column(name = "CUR_INSTALLMENT")
    private Float curInstallment;
    @Column(name = "OUT_CREDIT")
    private Float outCredit;
    @Column(name = "AMOUNT_PAYABLE")
    private Float amountPayable;
    @Column(name = "SERVICE_TAX")
    private Float serviceTax;
    @Column(name = "TOTAL_COST")
    private Float totalCost;
    @Column(name = "REMAINING_AMT")
    private Float remainingAmt;

    public FbsReport() {
    }

    public FbsReport(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(Integer regNumber) {
        this.regNumber = regNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getRecievedAmt() {
        return recievedAmt;
    }

    public void setRecievedAmt(Float recievedAmt) {
        this.recievedAmt = recievedAmt;
    }

    public Float getCurInstallment() {
        return curInstallment;
    }

    public void setCurInstallment(Float curInstallment) {
        this.curInstallment = curInstallment;
    }

    public Float getOutCredit() {
        return outCredit;
    }

    public void setOutCredit(Float outCredit) {
        this.outCredit = outCredit;
    }

    public Float getAmountPayable() {
        return amountPayable;
    }

    public void setAmountPayable(Float amountPayable) {
        this.amountPayable = amountPayable;
    }

    public Float getServiceTax() {
        return serviceTax;
    }

    public void setServiceTax(Float serviceTax) {
        this.serviceTax = serviceTax;
    }

    public Float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Float totalCost) {
        this.totalCost = totalCost;
    }

    public Float getRemainingAmt() {
        return remainingAmt;
    }

    public void setRemainingAmt(Float remainingAmt) {
        this.remainingAmt = remainingAmt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbsReport)) {
            return false;
        }
        FbsReport other = (FbsReport) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smp.entity.FbsReport[id=" + id + "]";
    }

}
