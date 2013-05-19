/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.managedbean;

import com.smp.entity.FbsBank;
import com.smp.session.FbsBankFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author smp
 */
@ManagedBean(name = "bankBean")
@ApplicationScoped
@Stateless
public class BankBean implements Serializable {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
    @EJB
    FbsBankFacade fbsBankFacade;
    public FbsBank fbsBank = new FbsBank();
    public static FbsBank delBank = new FbsBank();
    public List<FbsBank> bankList = new ArrayList();
    public String[] bank;
    String companyid = "";
    String viewStatus;

    @PostConstruct
    public void populate() {
        bankList.clear();
        bankList = em.createNamedQuery("FbsBank.findAll").getResultList();
        if (bankList.size() <= 5) {
            viewStatus = "View";

        } else {
            viewStatus = "View More";

        }

    }

    public void addBank() {
        fbsBank.setCompanyId(LoginBean.fbsLogin.getCompanyId());
        fbsBankFacade.create(fbsBank);
        fbsBank = new FbsBank();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Congrates! Bank Successfully Added"));
        populate();
    }

    public void editBank(org.primefaces.event.RowEditEvent e) {
        fbsBankFacade.edit((FbsBank) e.getObject());
        bankList = em.createNamedQuery("FbsBank.findAll").getResultList();

    }

    public void deleteBank() throws IOException {
        System.out.println("delete" + delBank.getBankName());
        fbsBankFacade.remove(delBank);
        bankList.clear();
        bankList = em.createNamedQuery("FbsBank.findAll").getResultList();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/Company/addBank.xhtml");
    }

    public void bank(FbsBank fbsBank) {
        //setFbsBank(fbsBank);
        delBank = fbsBank;
        System.out.println("bank" + fbsBank.getBankName());
    }

    public void setFbsBank(FbsBank fbsBank) {
        this.fbsBank = fbsBank;
    }

    public FbsBank getFbsBank() {
        return fbsBank;
    }

    public String[] getBank() {
        return bank;
    }

    public void setBank(String[] bank) {
        this.bank = bank;
    }

    public void setBankList(List<FbsBank> bankList) {
        this.bankList = bankList;
    }

    public List<FbsBank> getBankList() {
        return bankList;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getCompanyid() {
        return companyid;
    }

    public static FbsBank getDelBank() {
        return delBank;
    }

    public static void setDelBank(FbsBank delBank) {
        BankBean.delBank = delBank;
    }

    public String getViewStatus() {
        return viewStatus;
    }

    public void setViewStatus(String viewStatus) {
        this.viewStatus = viewStatus;
    }
}
