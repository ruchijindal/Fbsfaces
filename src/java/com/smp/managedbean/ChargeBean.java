/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smp.managedbean;

import com.smp.entity.FbsCharge;
import com.smp.session.FbsChargeFacade;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author smp7
 */
@ManagedBean(name = "chargeBean")
@ApplicationScoped
@Stateful
public class ChargeBean {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
    @EJB
    FbsChargeFacade fbsChargeFacade;
    public FbsCharge fbsCharge;
   public static FbsCharge fbsdelCharge;
    public static List<FbsCharge> chargeList;
    public SelectItem[] list;
    String projid="";

    public ChargeBean() {
        fbsCharge = new FbsCharge();
        chargeList = new ArrayList();
        fbsdelCharge=new FbsCharge();
    }


    @PostConstruct
    public void populate() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
            HttpSession session = (HttpSession) externalContext.getSession(true);
            projid=(String) session.getAttribute("projId");
            if(projid!=null)
       chargeList =  em.createNamedQuery("FbsCharge.findByFkProjId").setParameter("fkProjId", projid).getResultList();
    }

    public void addCharge() {
        fbsCharge.setFkProjId(projid);
        fbsChargeFacade.create(fbsCharge);
        fbsCharge=new FbsCharge();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Congrates! Charge Successfully Added"));
        populate();
    }

    public void editCharge(org.primefaces.event.RowEditEvent e) {
       fbsChargeFacade.edit((FbsCharge)e.getObject());
    }
    public void deleteCharge() throws IOException {
        fbsChargeFacade.remove(fbsdelCharge);
        populate();
FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/ProjectSetting/setCharges.xhtml");
    }
    public void delCharge(FbsCharge fbsCharge){
   fbsdelCharge=fbsCharge;

    }
    public  FbsCharge getFbsCharge() {
        return fbsCharge;
    }
    public void setFbsCharge(FbsCharge fbsCharge) {
        this.fbsCharge = fbsCharge;
    }
    public void setChargeList(ArrayList<FbsCharge> chargeList) {
        this.chargeList = chargeList;
    }
    public List<FbsCharge> getChargeList() {
        return chargeList;
    }
     public void setList(SelectItem[] list) {
        this.list = list;
    }
    public SelectItem[] getList() {
        return list;
    }
     
}


