/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.managedbean;

import com.itextpdf.text.ExceptionConverter;
import com.smp.entity.FbsCompany;
import com.smp.entity.FbsProject;
import com.smp.session.FbsProjectFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author smp
 */
@ManagedBean
@RequestScoped
@Stateless
public class BulkManagedBean implements Serializable {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
    FbsCompany fbsCompany = new FbsCompany();
    String companyId;
    FbsProject fbsProject = new FbsProject();
    List<FbsProject> fbsProjectList = new ArrayList<FbsProject>();
    @EJB
    FbsProjectFacade FbsProjectFacade;
    String[] projects;
    String projName = "";
    boolean status;
   String reporttype="false";

    /** Creates a new instance of BulkManagedBean */
    @PostConstruct
    public void populate() {
        status = false;
        reporttype="false";
        companyId = LoginBean.fbsLogin.getCompanyId().toString();
        // companyId="46";

        fbsCompany = (FbsCompany) em.createNamedQuery("FbsCompany.findByCompanyId").setParameter("companyId", Integer.parseInt(companyId)).getResultList().get(0);
        System.out.println("Company is" + fbsCompany.getCompanyName() + " " + fbsCompany.getCompanyId());
        fbsProjectList = FbsProjectFacade.findAll();
        projects = new String[fbsProjectList.size()];
        for (int i = 0; i < fbsProjectList.size(); i++) {
            projects[i] = fbsProjectList.get(i).getProjName();
        }

    }

    public void dueReport() throws IOException {
        System.out.println("project anme is ->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + projName);
        System.out.println("report type is "+reporttype);
        fbsProject = (FbsProject) em.createNamedQuery("FbsProject.findByProjName").setParameter("projName", projName).getSingleResult();
        System.out.println("projetc nmae is ->" + projName + "Propject" + fbsProject);
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.setAttribute("status", "false");
        System.out.println("Project id is->" + fbsProject.getProjId().toString());
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/BulkReport?companyId=" + companyId + "&projId=" + fbsProject.getProjId().toString()+"&type="+reporttype.trim());
        reporttype="false";
    }

    public void checkstatus() {

        HttpSession ses = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String statusvalue = (String) ses.getAttribute("status");
        System.out.println("status value is --------------->" + statusvalue);
//        if(statusvalue.trim().equals("true"))
//            status=true;
//        else
//            status=false;
        System.out.println("status value is --------------->" + status);

    }

    public BulkManagedBean() {
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public FbsCompany getFbsCompany() {
        return fbsCompany;
    }

    public void setFbsCompany(FbsCompany fbsCompany) {
        this.fbsCompany = fbsCompany;
    }

    public FbsProject getfbsProject() {
        return fbsProject;
    }

    public void setfbsProject(FbsProject fbsProject) {
        this.fbsProject = fbsProject;
    }

    public List<FbsProject> getFbsProjectList() {
        return fbsProjectList;
    }

    public void setFbsProjectList(List<FbsProject> fbsProjectList) {
        this.fbsProjectList = fbsProjectList;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String[] getProjects() {
        return projects;
    }

    public void setProjects(String[] projects) {
        this.projects = projects;
    }

    public boolean isStatus() {

        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getReporttype() {
        return reporttype;
    }

    public void setReporttype(String reporttype) {
        this.reporttype = reporttype;
    }

   
}
