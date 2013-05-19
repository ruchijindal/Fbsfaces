/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.managedbean;

import com.smp.entity.FbsParkingType;
import com.smp.session.FbsParkingTypeFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "parkingBean")
@RequestScoped
public class ParkingBean implements Serializable {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
    @EJB
    FbsParkingTypeFacade fbsParkingTypeFacade;
    private String projId;
    public FbsParkingType fbsParkingType = new FbsParkingType();
    public static FbsParkingType fbsdelParkingType = new FbsParkingType();
    public List<FbsParkingType> fbsParkingTypeList = new ArrayList<FbsParkingType>();

    /** Creates a new instance of ParkingBean */
    public ParkingBean() {
    }

    @PostConstruct
    public void populateParking() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);
        projId = (String) session.getAttribute("projId");
        if (projId != null) {
            fbsParkingTypeList = em.createNamedQuery("FbsParkingType.findByFkProjId").setParameter("fkProjId", Integer.parseInt(projId)).getResultList();
        }
    }

    public void addParking() throws IOException {

        fbsParkingType.setFkProjId(Integer.valueOf(projId));
        fbsParkingTypeFacade.create(fbsParkingType);
        fbsParkingType = new FbsParkingType();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Congrates! Parking Type Successfully Added"));
        fbsParkingTypeList = em.createNamedQuery("FbsParkingType.findByFkProjId").setParameter("fkProjId", Integer.parseInt(projId)).getResultList();
    }

    public void deleteRow() throws IOException {
        fbsParkingTypeFacade.remove(fbsdelParkingType);
        fbsParkingTypeList = em.createNamedQuery("FbsParkingType.findByFkProjId").setParameter("fkProjId", Integer.parseInt(projId)).getResultList();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/ProjectSetting/setParking.xhtml");
    }

    public void delRow(FbsParkingType fbsParkingType) {
        fbsdelParkingType = fbsParkingType;
    }

    public void editParking(org.primefaces.event.RowEditEvent e) {
        fbsParkingTypeFacade.edit((FbsParkingType) e.getObject());
    }

    public List<FbsParkingType> getFbsParkingTypeList() {
        return fbsParkingTypeList;
    }

    public void setFbsParkingTypeList(List<FbsParkingType> fbsParkingTypeList) {
        this.fbsParkingTypeList = fbsParkingTypeList;
    }

    public FbsParkingType getFbsParkingType() {
        return fbsParkingType;
    }

    public void setFbsParkingType(FbsParkingType fbsParkingType) {
        this.fbsParkingType = fbsParkingType;
    }
}
