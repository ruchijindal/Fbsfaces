 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smp.managedbean;

import com.smp.entity.FbsPlc;
import com.smp.session.FbsPlcFacade;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
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
@ManagedBean(name = "plcBean")
@ApplicationScoped
@Stateless
public class PlcBean {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
    @EJB
    FbsPlcFacade fbsPlcFacade;
    public FbsPlc fbsPlc = new FbsPlc();
    public static FbsPlc fbsdelPlc = new FbsPlc();
    public static List<FbsPlc> plcList = new ArrayList();
    String projid="";
    @PostConstruct
    public void populate() {
         FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
            HttpSession session = (HttpSession) externalContext.getSession(true);
            projid=(String) session.getAttribute("projId");
            if(projid!=null)
       plcList =  em.createNamedQuery("FbsPlc.findByFkProjId").setParameter("fkProjId", Integer.parseInt(projid)).getResultList();
    }

    public void addPlc() {
        fbsPlc.setFkProjId(Integer.parseInt(projid));
        fbsPlcFacade.create(fbsPlc);
        fbsPlc=new FbsPlc();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Congrates! PLC Successfully Added"));
        plcList= em.createNamedQuery("FbsPlc.findByFkProjId").setParameter("fkProjId", Integer.parseInt(projid)).getResultList();
    }

    public void editPlc(org.primefaces.event.RowEditEvent e) {
       fbsPlcFacade.edit((FbsPlc)e.getObject());
    }
    public void deletePlc() throws IOException {
        fbsPlcFacade.remove(fbsdelPlc);
        plcList = em.createNamedQuery("FbsPlc.findByFkProjId").setParameter("fkProjId", Integer.parseInt(projid)).getResultList();
    FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/ProjectSetting/setPlc.xhtml");
    }
    public void delPlc(FbsPlc fbsPlc) {
          fbsdelPlc=fbsPlc;
    }
    public FbsPlc getFbsPlc() {
        return fbsPlc;
    }
    public void setFbsPlc(FbsPlc fbsPlc) {
        this.fbsPlc = fbsPlc;
    }
    public void setPlcList(ArrayList<FbsPlc> plcList) {
        this.plcList = plcList;
    }
    public List<FbsPlc> getPlcList() {
        return plcList;
    }
}

