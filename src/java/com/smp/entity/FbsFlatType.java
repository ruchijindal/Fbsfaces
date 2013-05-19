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
@Table(name = "fbs_flat_type")
@NamedQueries({
    @NamedQuery(name = "FbsFlatType.findAll", query = "SELECT f FROM FbsFlatType f"),
    @NamedQuery(name = "FbsFlatType.findByFlatTypeId", query = "SELECT f FROM FbsFlatType f WHERE f.flatTypeId = :flatTypeId"),
    @NamedQuery(name = "FbsFlatType.findByFlatType", query = "SELECT f FROM FbsFlatType f WHERE f.flatType = :flatType"),
    @NamedQuery(name = "FbsFlatType.findByFlatSpecification", query = "SELECT f FROM FbsFlatType f WHERE f.flatSpecification = :flatSpecification"),
    @NamedQuery(name = "FbsFlatType.findByFlatSba", query = "SELECT f FROM FbsFlatType f WHERE f.flatSba = :flatSba"),
    @NamedQuery(name = "FbsFlatType.findByFlatBa", query = "SELECT f FROM FbsFlatType f WHERE f.flatBa = :flatBa"),
    @NamedQuery(name = "FbsFlatType.findByFlatCa", query = "SELECT f FROM FbsFlatType f WHERE f.flatCa = :flatCa"),
    @NamedQuery(name = "FbsFlatType.findByFlatBsp", query = "SELECT f FROM FbsFlatType f WHERE f.flatBsp = :flatBsp"),
    @NamedQuery(name = "FbsFlatType.findByFkProjId", query = "SELECT f FROM FbsFlatType f WHERE f.fkProjId = :fkProjId")})
public class FbsFlatType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "FLAT_TYPE_ID")
    private Integer flatTypeId;
    @Basic(optional = false)
    @Column(name = "FLAT_TYPE")
    private String flatType;
    @Column(name = "FLAT_SPECIFICATION")
    private String flatSpecification;
    @Column(name = "FLAT_SBA")
    private Integer flatSba;
    @Column(name = "FLAT_BA")
    private Integer flatBa;
    @Column(name = "FLAT_CA")
    private Integer flatCa;
    @Column(name = "FLAT_BSP")
    private Integer flatBsp;
    @Column(name = "FK_PROJ_ID")
    private Integer fkProjId;

    public FbsFlatType() {
    }

    public FbsFlatType(Integer flatTypeId) {
        this.flatTypeId = flatTypeId;
    }

    public FbsFlatType(Integer flatTypeId, String flatType) {
        this.flatTypeId = flatTypeId;
        this.flatType = flatType;
    }

    public Integer getFlatTypeId() {
        return flatTypeId;
    }

    public void setFlatTypeId(Integer flatTypeId) {
        this.flatTypeId = flatTypeId;
    }

    public String getFlatType() {
        return flatType;
    }

    public void setFlatType(String flatType) {
        this.flatType = flatType;
    }

    public String getFlatSpecification() {
        return flatSpecification;
    }

    public void setFlatSpecification(String flatSpecification) {
        this.flatSpecification = flatSpecification;
    }

    public Integer getFlatSba() {
        return flatSba;
    }

    public void setFlatSba(Integer flatSba) {
        this.flatSba = flatSba;
    }

    public Integer getFlatBa() {
        return flatBa;
    }

    public void setFlatBa(Integer flatBa) {
        this.flatBa = flatBa;
    }

    public Integer getFlatCa() {
        return flatCa;
    }

    public void setFlatCa(Integer flatCa) {
        this.flatCa = flatCa;
    }

    public Integer getFlatBsp() {
        return flatBsp;
    }

    public void setFlatBsp(Integer flatBsp) {
        this.flatBsp = flatBsp;
    }

    public Integer getFkProjId() {
        return fkProjId;
    }

    public void setFkProjId(Integer fkProjId) {
        this.fkProjId = fkProjId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (flatTypeId != null ? flatTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbsFlatType)) {
            return false;
        }
        FbsFlatType other = (FbsFlatType) object;
        if ((this.flatTypeId == null && other.flatTypeId != null) || (this.flatTypeId != null && !this.flatTypeId.equals(other.flatTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smp.entity.FbsFlatType[flatTypeId=" + flatTypeId + "]";
    }

}
