/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.managedbean;

import com.smp.entity.FbsLogin;
import com.smp.entity.FbsUser;
import com.smp.session.FbsUserFacade;
import java.io.IOException;
import java.io.Serializable;
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
 * @author smp
 */
@ManagedBean(name = "userRoleBean")
@ApplicationScoped
@Stateless
public class UserRoleBean implements Serializable {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
    @EJB
    FbsUserFacade fbsUserFacade;
    public FbsUser fbsUser = new FbsUser();
    public List<FbsUser> userRoleList = new ArrayList();
    public String companyid = "";
    List<String> commandlist = new ArrayList<String>();
    public static FbsUser fbsUserRole = new FbsUser();

    @PostConstruct
    public void populate() {
        commandlist.clear();
        userRoleList.clear();
        userRoleList = fbsUserFacade.findAll();
    }

    public void addUserRole() {
        fbsUser.setCompanyId(LoginBean.fbsLogin.getCompanyId());
        String xml_file = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?> ";
        xml_file += "<role_authorize>" + "\n";
        //System.out.println("project settings"+isRenderProjectSetting()+"  "+projectsetting);
        for (int i = 0; i < commandlist.size(); i++) {
            System.out.println(commandlist.get(i));
            xml_file += "<right>"+commandlist.get(i)+"</right>" + "\n";
        }
        xml_file += "</role_authorize>";
        fbsUser.setXmlFile(xml_file);
        System.out.println(xml_file);
        fbsUserFacade.create(fbsUser);
        fbsUser = new FbsUser();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Congrates!  UserRole Successfully Added"));
        populate();
    }

    public void editUserRole(org.primefaces.event.RowEditEvent e) {
        fbsUserFacade.edit((FbsUser) e.getObject());
    }
    public void deleteRole(FbsUser fbsUser)
    {
        fbsUserRole=fbsUser;

    }

    public void deleteUserRole() throws IOException {
        fbsUserFacade.remove(fbsUserRole);
        commandlist.clear();
        userRoleList.clear();
        userRoleList = fbsUserFacade.findAll();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/User/setUserRole.xhtml");

    }

    public List<FbsUser> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<FbsUser> userRoleList) {
        this.userRoleList = userRoleList;
    }

    public void setFbsUser(FbsUser fbsUser) {
        this.fbsUser = fbsUser;
    }

    public FbsUser getFbsUser() {
        return fbsUser;
    }

    public void setFbsUserFacade(FbsUserFacade fbsUserFacade) {
        this.fbsUserFacade = fbsUserFacade;
    }

    public FbsUserFacade getFbsUserFacade() {
        return fbsUserFacade;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public List<String> getCommandlist() {
        return commandlist;
    }

    public void setCommandlist(List<String> commandlist) {
        this.commandlist = commandlist;
    }
}
