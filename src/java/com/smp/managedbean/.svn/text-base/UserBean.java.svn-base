/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smp.managedbean;

import com.smp.entity.FbsLogin;
import com.smp.entity.FbsUser;
import com.smp.session.FbsLoginFacade;
import com.smp.session.FbsUserFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.primefaces.event.ToggleEvent;

/**
 *
 * @author smp7
 */
@ManagedBean(name = "userBean")
@ApplicationScoped
@Stateless
public class UserBean implements Serializable{

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
    @EJB
    FbsLoginFacade fbsLoginFacade;
    @EJB
    FbsUserFacade fbsUserFacade;
    
    public FbsLogin fbsLogin = new FbsLogin();
    public FbsUser fbsUser=new FbsUser();
    public static FbsLogin fbsdelLogin = new FbsLogin();
    public static List<FbsLogin> userList = new ArrayList();
     public List<FbsUser> userRoleList = new ArrayList();
    String projid="26";
    String confirmPassword="";
    String confirmEmail="";
    String companyid="";
    String viewStatus;
    private ArrayList roleName=new ArrayList();
    public List<FbsLogin> roleNameList = new ArrayList();

      public List<FbsUser> userRoleNameList = new ArrayList();
    
    @PostConstruct
    public void populate() {
        userList =  fbsLoginFacade.findAll();
        if(userList.size() <=5)
        {
            viewStatus="View";
        }
        else
        {
            viewStatus="View More";
        }
        createUser();
    }
   

    public void createUser(){
        userRoleList.clear();
        roleName.clear();

        userRoleList=em.createNamedQuery("FbsUser.findAll").getResultList();

        for(int i=0;i<userRoleList.size();i++){
            roleName.add(new SelectItem(userRoleList.get(i).getRollId(),userRoleList.get(i).getRoleName()));
        
        }

    }
    public String findUserAbb(int rollId)
    {
        return fbsUserFacade.find(rollId).getRoleArv();

    }

    public void addUser() {

        fbsLogin.setCompanyId(LoginBean.fbsLogin.getCompanyId());
         FacesContext context = FacesContext.getCurrentInstance();
        if(fbsLogin.getPassword().equals(this.confirmPassword))
        {
        fbsLoginFacade.create(fbsLogin);
        fbsLogin=new FbsLogin();
         
         context.addMessage(null, new FacesMessage("Congrates!  User Successfully Added"));
          populate();
        }
        else{
         context.addMessage(null, new FacesMessage("Password Missmatch!  Password & Confirm Password should be same"));
        }
    }
    public void validateEmail(FacesContext context,
            UIComponent toValidate,
            Object value) throws ValidatorException {
        String emailStr = (String) value;
        if (-1 == emailStr.indexOf("@")) {
            FacesMessage message = new FacesMessage("Invalid email address");
            throw new ValidatorException(message);
        }
    }
    public void editUser(org.primefaces.event.RowEditEvent e) {
        fbsLoginFacade.edit((FbsLogin) e.getObject());
    }
    public void deleteUser() throws IOException {
       fbsLoginFacade.remove(fbsdelLogin);
        populate();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/User/setUser.xhtml");
    }
    public void user(FbsLogin fbsLogin)
    {
        fbsdelLogin=fbsLogin;
    }
   
  public void handleToggle(ToggleEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, event.getComponent().getId() + " toggled", "Status:" + event.getVisibility().name());
        FacesContext.getCurrentInstance().addMessage(null,message);
     }
    public FbsLogin getFbsLogin() {
        return fbsLogin;
    }
    public void setFbsLogin(FbsLogin fbsLogin) {
        this.fbsLogin = fbsLogin;
    }
    public void setUserList(ArrayList<FbsLogin> userList) {
       this.userList = userList;
    }
    public List<FbsLogin> getUserList() {
        return userList;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public ArrayList getRoleName() {
        return roleName;
    }

    public void setRoleName(ArrayList roleName) {
       this.roleName = roleName;
    }

    public List<FbsUser> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<FbsUser> userRoleList) {
        this.userRoleList = userRoleList;
    }

    public FbsUser getFbsUser() {
        return fbsUser;
    }

    public void setFbsUser(FbsUser fbsUser) {
        this.fbsUser = fbsUser;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public static FbsLogin getFbsdelLogin() {
        return fbsdelLogin;
    }

    public static void setFbsdelLogin(FbsLogin fbsdelLogin) {
        UserBean.fbsdelLogin = fbsdelLogin;
    }

    public String getViewStatus() {
        return viewStatus;
    }

    public void setViewStatus(String viewStatus) {
        this.viewStatus = viewStatus;
    }



    

    
}

