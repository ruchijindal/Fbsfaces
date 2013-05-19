/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.managedbean;

import com.smp.entity.FbsCompany;
import com.smp.entity.FbsLogin;
import com.smp.entity.FbsUser;
import com.smp.session.FbsCompanyFacade;
import com.smp.session.FbsLoginFacade;
import com.smp.session.FbsUserFacade;
import java.io.IOException;
import java.io.StringReader;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.primefaces.context.RequestContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@ManagedBean(name = "loginBean")
@ApplicationScoped
@Stateful
public class LoginBean {

    private boolean renderProjectSetting;
    private boolean renderBooking;
    private boolean renderBookingAuthorize;
    private boolean renderPayment;
    private boolean renderPaymentAuthorize;
    private boolean renderComplaints;
    private boolean renderResolveComplaints;
    private boolean renderCompanySettings;
    private boolean renderConsumerReport;
    private boolean renderCollectionReport;
    private String username;
    public String userRole;
    private String password;
    private String companyName;
    private int companyId;
    private String website;
    public static FbsLogin fbsLogin = new FbsLogin();
    public static FbsUser fbsUser = new FbsUser();
    @EJB
    FbsLoginFacade fbsLoginFacade;
    @EJB
    FbsCompanyFacade fbsCompanyFacade;
    @EJB
    FbsUserFacade fbsUserFacade;
    public FbsCompany fbscompany = new FbsCompany();
    int tab = 0;
    int tab1=0;

    public void tabSet(int i) {
        System.out.println("inside tabSet");
        tab = i;
    }

    public int getTab() {
        return tab;
    }

    public void setTab(int tab) {
        tab1=0;
        this.tab = tab;
    }
    public void tabSet1(int i) {
        tab1 = i;
    }

    public int getTab1() {
        return tab1;
    }

    public void setTab1(int tab1) {
        tab=0;
        this.tab1= tab1;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public FbsLogin getFbsLogin() {
        return fbsLogin;
    }

    public void setFbsLogin(FbsLogin fbsLogin) {
        this.fbsLogin = fbsLogin;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWebsite() {
        return website;
    }

    public boolean isRenderBookingAuthorize() {
        return renderBookingAuthorize;
    }

    public void setRenderBookingAuthorize(boolean renderBookingAuthorize) {
        this.renderBookingAuthorize = renderBookingAuthorize;
    }

    public boolean isRenderCollectionReport() {
        return renderCollectionReport;
    }

    public void setRenderCollectionReport(boolean renderCollectionReport) {
        this.renderCollectionReport = renderCollectionReport;
    }

    public boolean isRenderCompanySettings() {
        return renderCompanySettings;
    }

    public void setRenderCompanySettings(boolean renderCompanySettings) {
        this.renderCompanySettings = renderCompanySettings;
    }

    public boolean isRenderComplaints() {
        return renderComplaints;
    }

    public void setRenderComplaints(boolean renderComplaints) {
        this.renderComplaints = renderComplaints;
    }

    public boolean isRenderConsumerReport() {
        return renderConsumerReport;
    }

    public void setRenderConsumerReport(boolean renderConsumerReport) {
        this.renderConsumerReport = renderConsumerReport;
    }

    public boolean isRenderPaymentAuthorize() {
        return renderPaymentAuthorize;
    }

    public void setRenderPaymentAuthorize(boolean renderPaymentAuthorize) {
        this.renderPaymentAuthorize = renderPaymentAuthorize;
    }

    public boolean isRenderResolveComplaints() {
        return renderResolveComplaints;
    }

    public void setRenderResolveComplaints(boolean renderResolveComplaints) {
        this.renderResolveComplaints = renderResolveComplaints;
    }

    public boolean isRenderBooking() {
        return renderBooking;
    }

    public void setRenderBooking(boolean renderBooking) {
        this.renderBooking = renderBooking;
    }

    public boolean isRenderPayment() {
        return renderPayment;
    }

    public void setRenderPayment(boolean renderPayment) {
        this.renderPayment = renderPayment;
    }

    public boolean isRenderProjectSetting() {
        return renderProjectSetting;
    }

    public void setRenderProjectSetting(boolean renderProjectSetting) {
        this.renderProjectSetting = renderProjectSetting;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public void validateUser() throws IOException, ParserConfigurationException, SAXException {
        System.out.println("inside login bean...........");
        int flag = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;

        for (int i = 0; i < fbsLoginFacade.findAll().size(); i++) {
            if (fbsLoginFacade.findAll().get(i).getUserId().trim().equals(fbsLogin.getUserId().trim()) && fbsLoginFacade.findAll().get(i).getPassword().equals(fbsLogin.getPassword())) {
                flag = 1;
                FbsLogin fbslogin = fbsLoginFacade.findAll().get(i);
                fbsLogin = fbslogin;
                break;
            }
        }
        System.out.println(flag + "hello");
        if (flag == 1) {
            renderProjectSetting = false;
            renderBooking = false;
            renderBookingAuthorize = false;
            renderPayment = false;
            renderPaymentAuthorize = false;
            renderComplaints = false;
            renderResolveComplaints = false;
            renderCompanySettings = false;
            renderConsumerReport = false;
            renderCollectionReport = false;
            userRole = "[" + fbsUserFacade.find(Integer.parseInt(fbsLogin.getUserRole())).getRoleName() + "]";
            System.out.println("user role is " + userRole);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", fbsLogin.getUserName());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            companyId = fbsLogin.getCompanyId();
            //session.setAttribute("userole", fbsLogin.getUserRole());
            fbsUser = fbsUserFacade.find(Integer.parseInt(fbsLogin.getUserRole()));
            String xmlFile = fbsUser.getXmlFile();
            if(xmlFile == null)
            {              
               xmlFile= "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"+"<role_authorize>" + "\n"+"</role_authorize>";
            }
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(xmlFile)));
            doc.getDocumentElement().normalize();
            Node right = doc.getFirstChild();
            NodeList rights = right.getChildNodes();
            for (int i = 0; i < rights.getLength(); i++) {
                Node rightchilds = rights.item(i);
                // System.out.println("node Name "+rightchilds.getNodeName());
                if (rightchilds.getNodeType() == Node.ELEMENT_NODE) {
                    Element rightElement = (Element) rightchilds;
                    System.out.println("elemet " + rightElement.getTagName());
                    System.out.println("   " + rightElement.getTextContent());
                    String righttemp = rightElement.getTextContent().trim();
                    if (righttemp.equalsIgnoreCase("Booking")) {
                        renderBooking = true;
                    }
                    if (righttemp.equalsIgnoreCase("Booking Authorize")) {
                        renderBookingAuthorize = true;
                    }
                    if (righttemp.equalsIgnoreCase("Collection Report")) {
                        renderCollectionReport = true;
                    }
                    if (righttemp.equalsIgnoreCase("Company Settings")) {
                        renderCompanySettings = true;
                    }

                    if (righttemp.equalsIgnoreCase("Complaints")) {
                        renderComplaints = true;
                    }
                    if (righttemp.equalsIgnoreCase("Consumer Report")) {
                        renderConsumerReport = true;
                    }
                    if (righttemp.equalsIgnoreCase("Payment")) {
                        renderPayment = true;
                    }
                    if (righttemp.equalsIgnoreCase("Payment Authorize")) {
                        renderPaymentAuthorize = true;
                    }
                    if (righttemp.equalsIgnoreCase("Project Setting")) {
                        System.out.println("project is true");
                        renderProjectSetting = true;
                    }
                    if (righttemp.equalsIgnoreCase("Resolve Complaints")) {
                        renderResolveComplaints = true;
                    }



                }
            }
            System.out.println("Proj Set. " + renderProjectSetting + " Book " + renderBooking + " Book Auth " + renderBookingAuthorize + " Payment" + renderPayment + " Pay Auth " + renderPaymentAuthorize + " Comp " + renderComplaints + " Res Comp " + renderResolveComplaints + "Comp Set" + renderCompanySettings + " Cons. " + renderConsumerReport + " Colle " + renderCollectionReport);
            /*   if (fbsLogin.getUserRole().equals("9")) {
            renderProjectSetting = true;
            renderBooking = true;
            renderPayment = true;
            renderPaymentAuthorize = true;
            renderBookingAuthorize = true;
            renderCompanySettings = true;
            renderComplaints = true;
            renderConsumerReport = true;
            renderResolveComplaints = true;
            renderPayment = true;
            }
            else if (fbsLogin.getUserRole().equals("26")) {
            renderProjectSetting = true;
            renderBooking = true;
            renderPayment = true;
            renderPaymentAuthorize = true;
            renderBookingAuthorize = true;
            renderCompanySettings = true;
            renderComplaints = true;
            renderConsumerReport = true;
            renderResolveComplaints = true;
            renderPayment = true;
            } else if (fbsLogin.getUserRole().equals("21")) {
            renderProjectSetting = true;
            renderBooking = true;
            renderPayment = true;
            renderPaymentAuthorize = true;
            renderBookingAuthorize = true;
            renderCompanySettings = false;
            renderComplaints = true;
            renderConsumerReport = true;
            renderResolveComplaints = true;
            } else if (fbsLogin.getUserRole().equals("5")) {
            renderProjectSetting = false;
            renderBooking = true;
            renderPayment = true;
            renderPaymentAuthorize = false;
            renderBookingAuthorize = true;
            renderCompanySettings = false;
            renderComplaints = true;
            renderConsumerReport = false;
            renderResolveComplaints = false;
            } else if (fbsLogin.getUserRole().equals("6")) {
            renderProjectSetting = false;
            renderBooking = true;
            renderPayment = true;
            renderPaymentAuthorize = false;
            renderBookingAuthorize = false;
            renderCompanySettings = false;
            renderComplaints = true;
            renderConsumerReport = false;
            renderResolveComplaints = false;
            } else if (fbsLogin.getUserRole().equals("7")) {
            renderProjectSetting = false;
            renderBooking = true;
            renderPayment = true;
            renderPaymentAuthorize = true;
            renderBookingAuthorize = false;
            renderCompanySettings = false;
            renderComplaints = true;
            renderConsumerReport = true;
            renderResolveComplaints = false;
            } else if (fbsLogin.getUserRole().equals("8")) {
            renderProjectSetting = false;
            renderBooking = false;
            renderPayment = true;
            renderPaymentAuthorize = false;
            renderBookingAuthorize = false;
            renderCompanySettings = false;
            renderComplaints = true;
            renderConsumerReport = false;
            renderResolveComplaints = false;
            } else if (fbsLogin.getUserRole().equals("11")) {
            renderProjectSetting = false;
            renderBooking = false;
            renderPayment = false;
            renderPaymentAuthorize = false;
            renderBookingAuthorize = false;
            renderCompanySettings = false;
            renderComplaints = true;
            renderConsumerReport = false;
            renderResolveComplaints = true;
            }*/
            //session.setAttribute("companyId", companyId);
            companyName = fbsCompanyFacade.find(companyId).getCompanyName();
            website = fbsCompanyFacade.find(companyId).getWebsite();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/Dashboard/companyDashboard.xhtml");

        } else {

            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Invalid credentials");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }


    }

    public void logout() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(false);
        session.removeAttribute(username);
        fbsLogin = new FbsLogin();
        session.invalidate();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/index.xhtml");
    }

    public void redirectWebsite(String website) throws IOException
    {
         FacesContext.getCurrentInstance().getExternalContext().redirect("http://"+website);
    }
}
