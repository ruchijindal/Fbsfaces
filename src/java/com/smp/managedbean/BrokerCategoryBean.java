/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.managedbean;

import com.smp.entity.FbsBrokerCat;
import com.smp.entity.FbsProject;
import com.smp.session.FbsBrokerCatFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author smp
 */
@ManagedBean(name = "brokerCategoryBean")
@SessionScoped
@Stateless
public class BrokerCategoryBean implements Serializable {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
    @EJB

    FbsBrokerCatFacade fbsBrokerCatFacade;
    public FbsBrokerCat fbsBrokerCat = new FbsBrokerCat();
    public static FbsBrokerCat fbsdelBrokerCat = new FbsBrokerCat();
    public FbsProject fbsProject = new FbsProject();
    public List<FbsBrokerCat> brokerCatList = new ArrayList();
    String companyid = "";

    @PostConstruct
    public void populate() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);
        companyid = (String) session.getAttribute(companyid);
        brokerCatList = fbsBrokerCatFacade.findAll();
    }

    public void addBrokerCategory() {
        fbsBrokerCat.setCompanyId(LoginBean.fbsLogin.getCompanyId());
        fbsBrokerCatFacade.create(fbsBrokerCat);
        fbsBrokerCat = new FbsBrokerCat();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Congrates! Broker Category Successfully Added"));
        populate();
    }

    public void editBrokerCategory(org.primefaces.event.RowEditEvent e) {
        fbsBrokerCatFacade.edit((FbsBrokerCat) e.getObject());
        populate();
    }

    public void deleteBrokerCategory(FbsBrokerCat fbsBrokerCat) throws IOException {
        fbsBrokerCatFacade.remove(fbsdelBrokerCat);
        populate();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/Broker/setBrokerCategory.xhtml");

    }
    public void delBrokerCategory(FbsBrokerCat fbsBrokerCat) {
           fbsdelBrokerCat=fbsBrokerCat;
    }

    public void setFbsBrokerCat(FbsBrokerCat fbsBrokerCat) {
        this.fbsBrokerCat = fbsBrokerCat;
    }

    public FbsBrokerCat getFbsBrokerCat() {
        return fbsBrokerCat;
    }

    public void setBrokerCatList(List<FbsBrokerCat> brokerCatList) {
        this.brokerCatList = brokerCatList;
    }

    public List<FbsBrokerCat> getBrokerCatList() {
        return brokerCatList;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getCompanyid() {
        return companyid;
    }
}
