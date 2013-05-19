/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smp.managedbean;

import com.smp.entity.FbsComplaint;
import com.smp.session.FbsComplaintFacade;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author smp7
 */
@ManagedBean(name = "complaintBean")
@ApplicationScoped
@Stateless
public class ComplaintBean {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
    @EJB
    FbsComplaintFacade fbsComplaintFacade;
    public FbsComplaint fbsComplaint=new FbsComplaint();
    public static FbsComplaint fbsdelComplaint=new FbsComplaint();
    public FbsComplaint fbsComplaint1=new FbsComplaint();
   private String complaints="";
         String viewStatus;
    public static List<FbsComplaint> complaintList=new ArrayList();

    @PostConstruct
    public void populate() {
       complaintList =  em.createNamedQuery("FbsComplaint.findAll").getResultList();
        if(    complaintList.size() <=5)
        {
            viewStatus="View";
        }
        else
        {
            viewStatus="View More";
        }
    }

    public void addComplaint() {
        fbsComplaintFacade.create(fbsComplaint);
        fbsComplaint=new FbsComplaint();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Congrates! Complaint Successfully Added"));
         complaintList =  em.createNamedQuery("FbsComplaint.findAll").getResultList();
    }

    public void editComplaint(org.primefaces.event.RowEditEvent e) {

       fbsComplaintFacade.edit((FbsComplaint) e.getObject());
    }

     public void edit( )
    {

         fbsComplaint1.setComplaint(complaints);
        fbsComplaintFacade.edit((fbsComplaint1));
        fbsComplaint1=new FbsComplaint();
         FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Congrates! Complaint Successfully Updated"));
        populate();

    }

    public void deleteComplaint() throws IOException {
        fbsComplaintFacade.remove(fbsdelComplaint);
          complaintList =  em.createNamedQuery("FbsComplaint.findAll").getResultList();
FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/Complaint/complaint.xhtml");
    }
    public void delComplaint(FbsComplaint fbsComplaint) {
                  fbsdelComplaint=fbsComplaint;
    }

     public void SetObject(FbsComplaint fbsComplaint) {
        complaints= fbsComplaint.getComplaint();
        fbsComplaint1=fbsComplaint;
        System.out.println("****complaint"+complaints);
       
    }
    
    public  FbsComplaint getFbsComplaint() {
        return fbsComplaint;
    }
    public void setFbsComplaint(FbsComplaint fbsComplaint) {
        this.fbsComplaint = fbsComplaint;
    }
    public void setComplaintList(ArrayList<FbsComplaint> complaintList) {
        this.complaintList = complaintList;
    }
    public List<FbsComplaint> getComplaintList() {
        return complaintList;
    }

    public String getComplaints() {
        return complaints;
    }

    public void setComplaints(String complaints) {
        this.complaints = complaints;
    }

    public FbsComplaint getFbsComplaint1() {
        return fbsComplaint1;
    }

    public void setFbsComplaint1(FbsComplaint fbsComplaint1) {
        this.fbsComplaint1 = fbsComplaint1;
    }

    public String getViewStatus() {
        return viewStatus;
    }

    public void setViewStatus(String viewStatus) {
        this.viewStatus = viewStatus;
    }
    
}


