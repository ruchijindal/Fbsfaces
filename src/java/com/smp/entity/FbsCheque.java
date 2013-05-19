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
@Table(name = "fbs_cheque")
@NamedQueries({
    @NamedQuery(name = "FbsCheque.findAll", query = "SELECT f FROM FbsCheque f"),
    @NamedQuery(name = "FbsCheque.findByChequeId", query = "SELECT f FROM FbsCheque f WHERE f.chequeId = :chequeId"),
    @NamedQuery(name = "FbsCheque.findByChequeNo", query = "SELECT f FROM FbsCheque f WHERE f.chequeNo = :chequeNo"),
    @NamedQuery(name = "FbsCheque.findByChequeDate", query = "SELECT f FROM FbsCheque f WHERE f.chequeDate = :chequeDate"),
    @NamedQuery(name = "FbsCheque.findByChequeAmt", query = "SELECT f FROM FbsCheque f WHERE f.chequeAmt = :chequeAmt"),
    @NamedQuery(name = "FbsCheque.findByDrawnOn", query = "SELECT f FROM FbsCheque f WHERE f.drawnOn = :drawnOn"),
    @NamedQuery(name = "FbsCheque.findByStatus", query = "SELECT f FROM FbsCheque f WHERE f.status = :status"),
    @NamedQuery(name = "FbsCheque.findByFkRegNumber", query = "SELECT f FROM FbsCheque f WHERE f.fkRegNumber = :fkRegNumber")})
public class FbsCheque implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CHEQUE_ID")
    private Integer chequeId;
    @Column(name = "CHEQUE_NO")
    private String chequeNo;
    @Column(name = "CHEQUE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date chequeDate;
    @Column(name = "CHEQUE_AMT")
    private Long chequeAmt;
    @Column(name = "DRAWN_ON")
    private String drawnOn;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "FK_REG_NUMBER")
    private Long fkRegNumber;

    public FbsCheque() {
    }

    public FbsCheque(Integer chequeId) {
        this.chequeId = chequeId;
    }

    public Integer getChequeId() {
        return chequeId;
    }

    public void setChequeId(Integer chequeId) {
        this.chequeId = chequeId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getFkRegNumber() {
        return fkRegNumber;
    }

    public void setFkRegNumber(Long fkRegNumber) {
        this.fkRegNumber = fkRegNumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (chequeId != null ? chequeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbsCheque)) {
            return false;
        }
        FbsCheque other = (FbsCheque) object;
        if ((this.chequeId == null && other.chequeId != null) || (this.chequeId != null && !this.chequeId.equals(other.chequeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smp.entity.FbsCheque[chequeId=" + chequeId + "]";
    }

}
