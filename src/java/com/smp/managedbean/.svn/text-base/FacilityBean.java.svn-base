 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smp.managedbean;

import com.smp.entity.FbsFacility;
import com.smp.session.FbsFacilityFacade;
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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author smp7
 */
@ManagedBean(name = "facilityBean")
@ApplicationScoped
@Stateful
public class FacilityBean implements Serializable {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
    @EJB
    FbsFacilityFacade fbsFacilityFacade;
    public FbsFacility fbsFacility = new FbsFacility();
    public static FbsFacility fbsdelFacility = new FbsFacility();
    public static List<FbsFacility> facilityList = new ArrayList();
    String projid="26";
    @PostConstruct
    public void populate() {
         FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
            HttpSession session = (HttpSession) externalContext.getSession(true);
            projid=(String) session.getAttribute("projId");
            if(projid!=null)
       facilityList =  em.createNamedQuery("FbsFacility.findByFkProjId").setParameter("fkProjId", Integer.parseInt(projid)).getResultList();
    }

    public void addFacility() {
        fbsFacility.setFkProjId(Integer.parseInt(projid));
        fbsFacilityFacade.create(fbsFacility);
        fbsFacility=new FbsFacility();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Congrates! Facility Successfully Added"));
        facilityList= em.createNamedQuery("FbsFacility.findByFkProjId").setParameter("fkProjId", Integer.parseInt(projid)).getResultList();
    }

    public void editFacility(org.primefaces.event.RowEditEvent e) {
       fbsFacilityFacade.edit((FbsFacility)e.getObject());
    }
    public void deleteFacility() throws IOException {
       fbsFacilityFacade.remove(fbsdelFacility);
        facilityList = em.createNamedQuery("FbsFacility.findByFkProjId").setParameter("fkProjId", Integer.parseInt(projid)).getResultList();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/ProjectSetting/setFacility.xhtml");
    }
    public void delFacility(FbsFacility fbsFacility) {
     fbsdelFacility=fbsFacility;
    }
    public FbsFacility getFbsFacility() {
        return fbsFacility;
    }
    public void setFbsFacility(FbsFacility fbsFacility) {
        this.fbsFacility = fbsFacility;
    }
    public void setFacilityList(ArrayList<FbsFacility> facilityList) {
        this.facilityList= facilityList;
    }
    public List<FbsFacility> getFacilityList() {
        return facilityList;
    }
}

