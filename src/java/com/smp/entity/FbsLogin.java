/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smp.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author smp
 */
@Entity
@Table(name = "fbs_login")
@NamedQueries({
    @NamedQuery(name = "FbsLogin.findAll", query = "SELECT f FROM FbsLogin f"),
    @NamedQuery(name = "FbsLogin.findByUserId", query = "SELECT f FROM FbsLogin f WHERE f.userId = :userId"),
    @NamedQuery(name = "FbsLogin.findByUserName", query = "SELECT f FROM FbsLogin f WHERE f.userName = :userName"),
    @NamedQuery(name = "FbsLogin.findByUserRole", query = "SELECT f FROM FbsLogin f WHERE f.userRole = :userRole"),
    @NamedQuery(name = "FbsLogin.findByCreatedBy", query = "SELECT f FROM FbsLogin f WHERE f.createdBy = :createdBy"),
    @NamedQuery(name = "FbsLogin.findByTelPhone", query = "SELECT f FROM FbsLogin f WHERE f.telPhone = :telPhone"),
    @NamedQuery(name = "FbsLogin.findByMobile", query = "SELECT f FROM FbsLogin f WHERE f.mobile = :mobile"),
    @NamedQuery(name = "FbsLogin.findByAddress", query = "SELECT f FROM FbsLogin f WHERE f.address = :address"),
    @NamedQuery(name = "FbsLogin.findByCity", query = "SELECT f FROM FbsLogin f WHERE f.city = :city"),
    @NamedQuery(name = "FbsLogin.findByState", query = "SELECT f FROM FbsLogin f WHERE f.state = :state"),
    @NamedQuery(name = "FbsLogin.findByEmail", query = "SELECT f FROM FbsLogin f WHERE f.email = :email"),
    @NamedQuery(name = "FbsLogin.findByWebsite", query = "SELECT f FROM FbsLogin f WHERE f.website = :website"),
    @NamedQuery(name = "FbsLogin.findByPassword", query = "SELECT f FROM FbsLogin f WHERE f.password = :password"),
    @NamedQuery(name = "FbsLogin.findByCompanyId", query = "SELECT f FROM FbsLogin f WHERE f.companyId = :companyId")})
public class FbsLogin implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "USER_ID")
    private String userId;
    @Basic(optional = false)
    @Column(name = "USER_NAME")
    private String userName;
    @Basic(optional = false)
    @Column(name = "USER_ROLE")
    private String userRole;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "TEL_PHONE")
    private String telPhone;
    @Column(name = "MOBILE")
    private Long mobile;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "CITY")
    private String city;
    @Column(name = "STATE")
    private String state;
    @Basic(optional = false)
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "WEBSITE")
    private String website;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "COMPANY_ID")
    private Integer companyId;

    public FbsLogin() {
    }

    public FbsLogin(String userId) {
        this.userId = userId;
    }

    public FbsLogin(String userId, String userName, String userRole, String email) {
        this.userId = userId;
        this.userName = userName;
        this.userRole = userRole;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbsLogin)) {
            return false;
        }
        FbsLogin other = (FbsLogin) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smp.entity.FbsLogin[userId=" + userId + "]";
    }

}
