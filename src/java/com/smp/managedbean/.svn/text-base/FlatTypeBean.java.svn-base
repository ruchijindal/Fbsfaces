/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.managedbean;

import com.smp.entity.FbsFlatType;
import com.smp.session.FbsFlatTypeFacade;
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
@ManagedBean(name = "flatTypeBean")
@ApplicationScoped
@Stateful
public class FlatTypeBean implements Serializable {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
    @EJB
    FbsFlatTypeFacade fbsFlatTypeFacade;
    public FbsFlatType fbsFlatType = new FbsFlatType();
    public static FbsFlatType fbsdelFlatType = new FbsFlatType();
    public static List<FbsFlatType> flatTypeList = new ArrayList();
    String projid = "";

    @PostConstruct
    public void populate() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);
        projid = (String) session.getAttribute("projId");
        if (projid != null) {
            flatTypeList = em.createNamedQuery("FbsFlatType.findByFkProjId").setParameter("fkProjId", Integer.parseInt(projid)).getResultList();
        }
    }

    public void addFlatType() {
        fbsFlatType.setFkProjId(Integer.parseInt(projid));
        fbsFlatTypeFacade.create(fbsFlatType);
        fbsFlatType = new FbsFlatType();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Congrates! FlatType Successfully Added"));
        flatTypeList = em.createNamedQuery("FbsFlatType.findByFkProjId").setParameter("fkProjId", Integer.parseInt(projid)).getResultList();
    }

    public void editFlatType(org.primefaces.event.RowEditEvent e) {
        fbsFlatTypeFacade.edit((FbsFlatType) e.getObject());
    }

    public void deleteFlatType() throws IOException {
        fbsFlatTypeFacade.remove(fbsdelFlatType);
        flatTypeList.clear();
        flatTypeList = em.createNamedQuery("FbsFlatType.findByFkProjId").setParameter("fkProjId", Integer.parseInt(projid)).getResultList();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/ProjectSetting/setFlatType.xhtml");
    }

    public void delFlatType(FbsFlatType fbsFlatType) {
        fbsdelFlatType = fbsFlatType;
    }

    public List<FbsFlatType> FlatTypeId(String projId) {
        return (em.createNamedQuery("FbsFlatType.findByFkProjId").setParameter("fkProjId", Integer.parseInt(projId)).getResultList());
    }

    public FbsFlatType getFbsFlatType() {
        return fbsFlatType;
    }

    public void setFbsFlatType(FbsFlatType fbsFlatType) {
        this.fbsFlatType = fbsFlatType;
    }

    public void setFlatTypeList(ArrayList<FbsFlatType> flatTypeList) {
        this.flatTypeList = flatTypeList;
    }

    public List<FbsFlatType> getFlatTypeList() {
        return flatTypeList;
    }
}
