/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.managedbean;

import com.smp.entity.FbsPlanname;
import com.smp.session.FbsPlannameFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author smp
 */
@ManagedBean(name = "planBean")
@ApplicationScoped
@Stateful
public class PlanBean implements Serializable{

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
    @EJB

    FbsPlannameFacade fbsPlannameFacade;
    public FbsPlanname fbsPlanname = new FbsPlanname();
    public static FbsPlanname fbsdelPlanname = new FbsPlanname();
    public List<FbsPlanname> plannameList = new ArrayList();
    public String[] plannames;
    private String projid;

    @PostConstruct
    public void populate() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);
        projid = (String) session.getAttribute("projId");
        if(projid!=null)
        {
        plannameList = em.createNamedQuery("FbsPlanname.findByFkProjId").setParameter("fkProjId", Integer.parseInt(projid)).getResultList();
        plannames=new String[plannameList.size()];
        for(int i=0;i<plannames.length;i++)
        {
            plannames[i]=plannameList.get(i).getPlanName();
        }
        }
    }

    public void addPlanname() {
        fbsPlanname.setFkProjId(Integer.parseInt(projid));
        fbsPlannameFacade.create(fbsPlanname);
        fbsPlanname=new FbsPlanname();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Congrates! Plan Name Successfully Added"));
        populate();
    }

    public void editPlanname(org.primefaces.event.RowEditEvent e) {
        fbsPlannameFacade.edit((FbsPlanname) e.getObject());
        populate();
    }

    public void deletePlanname() throws IOException {
        fbsPlannameFacade.remove(fbsdelPlanname);
        populate();
FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/ProjectSetting/setPlanname.xhtml");
    }
    public void delPlanname(FbsPlanname fbsPlanname) {
            fbsdelPlanname=fbsPlanname;

    }

    public FbsPlanname getFbsPlanname() {
        return fbsPlanname;
    }

    public void setFbsPlanname(FbsPlanname fbsPlanname) {
        this.fbsPlanname = fbsPlanname;
    }

    public List<FbsPlanname> getPlannameList() {
        return plannameList;
    }

    public void setPlannameList(List<FbsPlanname> plannameList) {
        this.plannameList = plannameList;
    }

    public String[] getPlannames() {
        return plannames;
    }

    public void setPlannames(String[] plannames) {
        this.plannames = plannames;
    }
    
}
