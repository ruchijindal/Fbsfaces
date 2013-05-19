/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.managedbean;

import com.smp.entity.FbsInterest;
import com.smp.session.FbsInterestFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author smp
 */
@ManagedBean(name = "setInterestBean")
@RequestScoped
public class IntersetBean implements Serializable {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    private EntityManager em;
    @EJB
    FbsInterestFacade fbsInterestFacade;
    FbsInterest fbsInterest = new FbsInterest();
    public static FbsInterest fbsdelInterest = new FbsInterest();
    public List<FbsInterest> interestList = new ArrayList<FbsInterest>();
    String companyid="";
    public IntersetBean() {
    }

    @PostConstruct
    public void populate() {
        interestList = fbsInterestFacade.findAll();
    }

    public void addRow() {
        fbsInterest.setCompanyId(LoginBean.fbsLogin.getCompanyId());
        fbsInterestFacade.create(fbsInterest);
        fbsInterest = new FbsInterest();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Congrates! ServiceTax Successfully Added"));
        populate();
    }

    public void deleteRow() throws IOException {
        fbsInterestFacade.remove(fbsdelInterest);
        populate();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/Company/interest.xhtml");
    }

    public void delRow(FbsInterest fbsInterest) {
        fbsdelInterest = fbsInterest;
    }

    public void editInterest(org.primefaces.event.RowEditEvent e) {

        fbsInterestFacade.edit((FbsInterest) e.getObject());
    }

    public FbsInterest getFbsInterest() {
        return fbsInterest;
    }

    public void setFbsInterest(FbsInterest fbsInterest) {
        this.fbsInterest = fbsInterest;
    }

    public List<FbsInterest> getInterestList() {
        return interestList;
    }

    public void setInterestList(List<FbsInterest> interestList) {
        this.interestList = interestList;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getCompanyid() {
        return companyid;
    }

    
}
