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
@Table(name = "fbs_user")
@NamedQueries({
    @NamedQuery(name = "FbsUser.findAll", query = "SELECT f FROM FbsUser f"),
    @NamedQuery(name = "FbsUser.findByRollId", query = "SELECT f FROM FbsUser f WHERE f.rollId = :rollId"),
    @NamedQuery(name = "FbsUser.findByRoleName", query = "SELECT f FROM FbsUser f WHERE f.roleName = :roleName"),
    @NamedQuery(name = "FbsUser.findByRoleArv", query = "SELECT f FROM FbsUser f WHERE f.roleArv = :roleArv"),
    @NamedQuery(name = "FbsUser.findByCompanyId", query = "SELECT f FROM FbsUser f WHERE f.companyId = :companyId"),
    @NamedQuery(name = "FbsUser.findByXmlFile", query = "SELECT f FROM FbsUser f WHERE f.xmlFile = :xmlFile")})
public class FbsUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ROLL_ID")
    private Integer rollId;
    @Column(name = "ROLE_NAME")
    private String roleName;
    @Column(name = "ROLE_ARV")
    private String roleArv;
    @Column(name = "COMPANY_ID")
    private Integer companyId;
    @Column(name = "XML_FILE")
    private String xmlFile;

    public FbsUser() {
    }

    public FbsUser(Integer rollId) {
        this.rollId = rollId;
    }

    public Integer getRollId() {
        return rollId;
    }

    public void setRollId(Integer rollId) {
        this.rollId = rollId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleArv() {
        return roleArv;
    }

    public void setRoleArv(String roleArv) {
        this.roleArv = roleArv;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getXmlFile() {
        return xmlFile;
    }

    public void setXmlFile(String xmlFile) {
        this.xmlFile = xmlFile;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rollId != null ? rollId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbsUser)) {
            return false;
        }
        FbsUser other = (FbsUser) object;
        if ((this.rollId == null && other.rollId != null) || (this.rollId != null && !this.rollId.equals(other.rollId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smp.entity.FbsUser[rollId=" + rollId + "]";
    }

}
