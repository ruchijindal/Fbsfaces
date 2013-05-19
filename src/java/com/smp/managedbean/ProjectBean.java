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
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author smp7
 */
@ManagedBean(name = "projectBean")
@SessionScoped
@Stateless
public class ProjectBean {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
    @EJB
    FbsProjectFacade fbsProjectFacade;
    public FbsProject fbsProject = new FbsProject();
      public static FbsProject fbsdelProject = new FbsProject();
    public static List<FbsProject> projectList = new ArrayList();
    String companyid="";
     String viewStatus;
 
    @PostConstruct
    public void populate() {
        projectList =  fbsProjectFacade.findAll();
        if( projectList.size() <=5)
        {
            viewStatus="View";

        }
        else
        {
            viewStatus="View More";

        }
    }

    public void addProject() {
        fbsProject.setCompanyId(LoginBean.fbsLogin.getCompanyId());
       // fbsProject.setProjCode("PROJ");
        fbsProjectFacade.create(fbsProject);
        fbsProject = new FbsProject();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Congrates! Project Successfully Added"));
          populate();
    }

    public void editProject(org.primefaces.event.RowEditEvent e) {
        fbsProjectFacade.edit((FbsProject) e.getObject());
    }
    public void deleteProject() throws IOException {
       fbsProjectFacade.remove(fbsdelProject);
        populate();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/Project/setProject.xhtml");
    }
 public void delProject(FbsProject fbsProject)
    {
     fbsdelProject=fbsProject;
 }

    public FbsProject getFbsProject() {
        return fbsProject;
    }
    public void setFbsProject(FbsProject fbsProject) {
        this.fbsProject = fbsProject;
    }
    public void setProjectList(ArrayList<FbsProject> projectList) {
        this.projectList = projectList;
    }
    public List<FbsProject> getProjectList() {
        return projectList;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getCompanyid() {
        return companyid;
    }

    public String getViewStatus() {
        return viewStatus;
    }

    public void setViewStatus(String viewStatus) {
        this.viewStatus = viewStatus;
    }

    
    
}

