/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.managedbean;

import com.smp.entity.FbsProject;
import com.smp.session.FbsProjectFacade;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
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
@ManagedBean(name = "commonBean")
@ApplicationScoped
@Stateful
public class CommonBean {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
    @EJB
    FbsProjectFacade fbsProjectFacade;
    @EJB
    BlockDetailBean blockDetail;
    FbsProject fbsProject = new FbsProject();
    List<FbsProject> fbsProjectList = new ArrayList();
    String[] projectNameList;
    public static String projectName = "Projects"; //for the project menu

    @PostConstruct
    public void populateProject() {
        fbsProjectList = fbsProjectFacade.findAll();
        projectNameList = new String[fbsProjectList.size()];
        for (int i = 0; i < projectNameList.length; i++) {
            projectNameList[i] = fbsProjectList.get(i).getProjName();
        }

    }

    public void projId(String projId) throws IOException {
        //System.out.println("set project id");
        fbsProject = fbsProjectFacade.find(Integer.parseInt(projId));
        projectName = fbsProject.getProjName();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);
        session.setAttribute("projId", projId);
        blockDetail.PopulateBlo(projId);
        blockDetail.bookingmonthChart(projId);
        blockDetail.Populate();
        System.out.println("Commaon Bean is calessssssssssssssssssss");
    }

    public void forward() throws IOException {
        projectName = "Projects";
        populateProject();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/Dashboard/companyDashboard.xhtml");
    }

    public void project() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/Project/setProject.xhtml");
    }

    public void user() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/User/setUser.xhtml");
    }

    public void broker() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/Broker/setBroker.xhtml");
    }

    public void bank() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/Company/addBank.xhtml");
    }

    public void block() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/ProjectSetting/setBlock.xhtml");
    }

    public void booking() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/Booking/bookingList.xhtml");
    }

    public void payment() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/Payment/paymentList.xhtml");
    }

    public void complaint() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/Complaint/complaintList.xhtml");
    }

    public FbsProject getFbsProject() {
        return fbsProject;
    }

    public void setFbsProject(FbsProject fbsProject) {
        this.fbsProject = fbsProject;
    }

    public List<FbsProject> getFbsProjectList() {
        return fbsProjectList;
    }

    public void setFbsProjectList(List<FbsProject> fbsProjectList) {
        this.fbsProjectList = fbsProjectList;
    }

    public String[] getProjectNameList() {
        return projectNameList;
    }

    public void setProjectNameList(String[] projectNameList) {
        this.projectNameList = projectNameList;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        CommonBean.projectName = projectName;
    }
}
