/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.managedbean;

import com.smp.entity.FbsDiscount;
import com.smp.session.FbsDiscountFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author smp11
 */
@ManagedBean(name = "discountBean")
@ApplicationScoped
@Stateful
public class DiscountBean implements Serializable {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
    @EJB
    FbsDiscountFacade fbsDiscountFacade;
    public FbsDiscount fbsDiscount = new FbsDiscount();
    public static FbsDiscount fbsdelDiscount = new FbsDiscount();
    public List<FbsDiscount> discountList = new ArrayList();
    String companyid="";

    public DiscountBean() {
    }

    public void editDiscount(org.primefaces.event.RowEditEvent e) {
        fbsDiscountFacade.edit((FbsDiscount) e.getObject());
    }

    @PostConstruct
    public void populate() {
        discountList = fbsDiscountFacade.findAll();
    }

    

    public void addRow() {
        
        fbsDiscount.setCompanyId(LoginBean.fbsLogin.getCompanyId());
        fbsDiscountFacade.create(fbsDiscount);
        fbsDiscount=new FbsDiscount();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Congrates! Discount Type Successfully Added"));
        populate();
    }

    public void deleteRow() throws IOException {
        fbsDiscountFacade.remove(fbsdelDiscount);
        populate();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/Company/discountType.xhtml");
    }
  public void delRow(FbsDiscount fbsDiscount)
    {
      fbsdelDiscount=fbsDiscount;
  }
    public void setDiscountList(List<FbsDiscount> discountList) {
        this.discountList = discountList;
    }

    public List<FbsDiscount> getDiscountList() {
        return this.discountList;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setFbsDiscount(FbsDiscount fbsDiscount) {
        this.fbsDiscount = fbsDiscount;
    }

    public FbsDiscount getFbsDiscount() {
        return fbsDiscount;
    }
    


}
