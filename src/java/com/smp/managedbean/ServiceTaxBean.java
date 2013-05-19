/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smp.managedbean;

import com.smp.entity.FbsDiscount;
import com.smp.entity.FbsServicetax;
import com.smp.session.FbsServicetaxFacade;
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


@ManagedBean(name="serviceBean")
@RequestScoped
public class ServiceTaxBean  implements Serializable{
    @PersistenceContext(unitName = "FlatBookingSystemPU")
    private EntityManager em;
    @EJB
    FbsServicetaxFacade fbsServicetaxFacade;
    public FbsServicetax fbsServicetax=new FbsServicetax();
    public static FbsServicetax fbsdelServicetax=new FbsServicetax();
    public List<FbsServicetax> serviceList=new ArrayList<FbsServicetax>();

    /** Creates a new instance of ServiceTaxBean */
    public ServiceTaxBean() {
    }
    public void editServicetax(org.primefaces.event.RowEditEvent e) {
        fbsServicetaxFacade.edit((FbsServicetax) e.getObject());
    }
    @PostConstruct
    public void populate() {
       serviceList = fbsServicetaxFacade.findAll();
    }
    public void addRow() {
        fbsServicetaxFacade.create(fbsServicetax);
        fbsServicetax=new FbsServicetax();
          FacesContext context = FacesContext.getCurrentInstance();
         context.addMessage(null, new FacesMessage("Congrates! ServiceTax Successfully Added"));
      populate();
    }
    public void deleteRow() throws IOException {
       fbsServicetaxFacade.remove(fbsdelServicetax);
        populate();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/Company/serviceTax.xhtml");
    }
    public void delRow(FbsServicetax fbsServicetax)
    {
        fbsdelServicetax=fbsServicetax;
    }

    public FbsServicetax getFbsServicetax() {
        return fbsServicetax;
    }

    public List<FbsServicetax> getServiceList() {
        return serviceList;
    }

    public void setFbsServicetax(FbsServicetax fbsServicetax) {
        this.fbsServicetax = fbsServicetax;
    }

    public void setServiceList(List<FbsServicetax> serviceList) {
        this.serviceList = serviceList;
    }

     


}
