/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smp.managedbean;

import com.smp.entity.FbsBank;
import com.smp.entity.FbsCompany;
import com.smp.session.FbsCompanyFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;


/**
 *
 * @author smp
 */
@ManagedBean( name = "companyBean")
@ApplicationScoped
@Stateless

public class CompanyBean implements Serializable {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
    @EJB

    FbsCompanyFacade fbsCompanyFacade;
    public FbsCompany fbsCompany = new FbsCompany();
    public List<FbsCompany> companyList = new ArrayList();
    public String[] Company;
    private String companyId;
   
    @PostConstruct
    public void populate()
    {
      
            companyId= LoginBean.fbsLogin.getCompanyId().toString();
       // companyId="46";

        fbsCompany = (FbsCompany)em.createNamedQuery("FbsCompany.findByCompanyId").setParameter("companyId",Integer.parseInt(companyId)).getResultList().get(0);
    }

  
    public void editCompany()
    {
        fbsCompanyFacade.edit((fbsCompany));
        fbsCompany=new FbsCompany();
         FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Congrates! Company Successfully Updated"));
        populate();
        
    }

    public void setCompany(String[] Company) {
        this.Company = Company;
    }

    public String[] getCompany() {
        return Company;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyList(List<FbsCompany> companyList) {
        this.companyList = companyList;
    }

    public List<FbsCompany> getCompanyList() {
        return companyList;
    }

    public void setFbsCompany(FbsCompany fbsCompany) {
        this.fbsCompany = fbsCompany;
    }

    public FbsCompany getFbsCompany() {
        return fbsCompany;
    }
    
}
