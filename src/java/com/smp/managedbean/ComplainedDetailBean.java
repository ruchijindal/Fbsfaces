/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.managedbean;

import com.smp.entity.FbsBlock;
import com.smp.entity.FbsComplaint;
import com.smp.session.FbsComplaintFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author smp11
 */
@ManagedBean(name = "complainedDetailBean")
@SessionScoped
public class ComplainedDetailBean implements Serializable {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
    @EJB
    FbsComplaintFacade fbsComplaintFacade;
    List<FbsComplaint> complaintList;
    List<FbsComplaint> refComplaintList;
    private String[] blockNames;
    private String[] status = {"Pending", "Resolved"};
    private String opComplaintId;
    private String opName;
    private String opAddress;
    private String opEmail;
    private String opSubject;
    private String opStatus;
    private String opRegiterBy;
    private Date opStartDate;
    private Date opEndDate;
    boolean complnId;
    boolean stats;
    boolean nam;
    boolean addr;
    boolean mail;
    boolean sub;
    boolean reg;
    boolean dat;
    boolean[] payOption = new boolean[8];
    private List<FbsComplaint> complaintTemp;

    public ComplainedDetailBean() {
        complnId = false;
        stats = false;
        nam = false;
        addr = false;
        mail = false;
        sub = false;
        reg = false;
        dat = false;
        for (int i = 0; i < 8; i++) {
            payOption[i] = false;
        }
        complaintList = new ArrayList<FbsComplaint>();
    }

    @PostConstruct
    public void populateComplaintList() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);
        String projId1 = (String) session.getAttribute("projId");
        List<FbsBlock> fbsBlock = em.createNamedQuery("FbsBlock.findByFkProjId").setParameter("fkProjId", Integer.parseInt(projId1)).getResultList();
        int siz = fbsBlock.size();
        blockNames = new String[siz];
        for (int n = 0; n < siz; n++) {
            blockNames[n] = fbsBlock.get(n).getBlockName();
        }
        complaintList = fbsComplaintFacade.findAll();
        refComplaintList = complaintList;
    }

    public void populateComplaintListByFilter() {
        complaintTemp = new ArrayList<FbsComplaint>();
        complaintTemp.addAll(this.complaintList);
        System.out.println("Flat List-->" + complaintTemp.size());
        System.out.println("NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN ");
        System.out.println("Select Status-->" + this.opStatus);
        System.out.println("Select complnId-->" + this.opComplaintId);
        System.out.println("Select Applicant Name-->" + this.opName);
        System.out.println("Select Address-->" + this.opAddress);
        
        System.out.println("Select Email-->" + this.opEmail);
        System.out.println("Select Subject-->" + this.opSubject);
        System.out.println("Select Register By-->" + this.opRegiterBy);
        System.out.println("Select StartDate-->" + this.opStartDate);
        System.out.println("Select EndDate-->" + this.opEndDate);
        System.out.println("+++++++++++++++++++++++++++++++++++++++ ");
        System.out.println("stats-->" + this.stats);
         System.out.println("copmlnId-->" + this.complnId);
        System.out.println("nam-->" + this.nam);
        System.out.println("addr-->" + this.addr);
        System.out.println("mail-->" + this.mail);
        System.out.println("sub-->" + this.sub);
        System.out.println("reg-->" + this.reg);
        System.out.println("dat-->" + this.dat);

        int l = 0;

        if ((opComplaintId.equals("")) && (opStatus.equals("Select Status")) && (opName.equals("")) && (opAddress.equals("")) && (opEmail.equals("")) && (opSubject.equals("")) && (opRegiterBy.equals("")) && (opStartDate == null || opEndDate == null)) {
            l = 0;
            System.out.println("All are in select mode");

        } else if ((opComplaintId.equals("")) && (!(this.dat))) {
            System.out.println("All are in  ");
            reset();

        } else {
            if (this.stats) {
                payOption[0] = this.stats;
            }
            if (this.complnId) {
                payOption[1] = this.complnId;
            }

            if (this.nam) {
                payOption[2] = this.nam;
            }
            if (this.addr) {
                payOption[3] = this.addr;
            }

            if (this.mail) {
                payOption[4] = this.mail;
            }
            if (this.sub) {
                payOption[5] = this.sub;
            }
            if (this.reg) {
                payOption[6] = this.reg;
            }
            if (this.dat) {
                payOption[7] = this.dat;
            }
             if (payOption[0]) {
                complaintTemp = filterByStatus(complaintTemp, this.opStatus);
                System.out.println("Flat List 0-->" + complaintTemp.size());
            }
            if (payOption[1]) {
                complaintTemp = filterByComplaintId(complaintTemp, this.opComplaintId);
                System.out.println("Flat List 1-->" + complaintTemp.size());
            }
            if (payOption[2]) {
                complaintTemp = filterByName(complaintTemp, opName);
                System.out.println("Flat List 2-->" + complaintTemp.size());
            }
            if (payOption[3]) {
                complaintTemp = filterByAddress(complaintTemp, opAddress);
                System.out.println("Flat List 3-->" + complaintTemp.size());
            }
            if (payOption[4]) {
                complaintTemp = filterByEmail(complaintTemp, opEmail);
                System.out.println("Flat List 4-->" + complaintTemp.size());
            }
            if (payOption[5]) {
                complaintTemp = filterBySubject(complaintTemp, opSubject);
                System.out.println("Flat List 5-->" + complaintTemp.size());
            }
            if (payOption[6]) {
                complaintTemp = filterByRegister(complaintTemp, opRegiterBy);
                System.out.println("Flat List 6-->" + complaintTemp.size());
            }
            if (payOption[7]) {
                complaintTemp = filterByDate(complaintTemp, opStartDate, opEndDate);
               System.out.println("Flat List 6-->" + complaintTemp.size());
            }
        }
        this.refComplaintList = this.complaintTemp;
        resetBooleanOption();

    }

    public List<FbsComplaint> filterByStatus(List<FbsComplaint> complaintTemp, String opStatus) {
        int k = 0;
        List<FbsComplaint> complaintTemp1 = new ArrayList<FbsComplaint>();
        for (int i = 0; i < complaintTemp.size(); i++) {
            if (complaintTemp.get(i).getStatus().equals(opStatus)) {
                complaintTemp1.add(k, complaintTemp.get(i));
                k++;
            }
        }
        return complaintTemp1;
    }

    public List<FbsComplaint> filterByComplaintId(List<FbsComplaint> complaintTemp, String complaintId) {
        int k = 0;
        boolean nameStatus = false;
        List<FbsComplaint> complaintTemp1 = new ArrayList<FbsComplaint>();
        for (int i = 0; i < complaintTemp.size(); i++) {
            nameStatus = complaintTemp.get(i).getComplaintId().toString().toUpperCase().contains(complaintId.toUpperCase());
            if (nameStatus) {

                complaintTemp1.add(k, complaintTemp.get(i));

                k++;
            }
        }
        return complaintTemp1;

    }
    public List<FbsComplaint> filterByName(List<FbsComplaint> complaintTemp, String name) {
        int k = 0;
        boolean nameStatus = false;
        List<FbsComplaint> complaintTemp1 = new ArrayList<FbsComplaint>();
        for (int i = 0; i < complaintTemp.size(); i++) {
            nameStatus = complaintTemp.get(i).getName().toUpperCase().contains(name.toUpperCase());
            if (nameStatus) {

                complaintTemp1.add(k, complaintTemp.get(i));

                k++;
            }
        }
        return complaintTemp1;

    }

    public List<FbsComplaint> filterByAddress(List<FbsComplaint> complaintTemp, String address) {
        int k = 0;
        boolean nameStatus = false;
        List<FbsComplaint> complaintTemp1 = new ArrayList<FbsComplaint>();
        for (int i = 0; i < complaintTemp.size(); i++) {
            nameStatus = complaintTemp.get(i).getAddress().toUpperCase().contains(address.toUpperCase());
            if (nameStatus) {

                complaintTemp1.add(k, complaintTemp.get(i));

                k++;
            }
        }
        return complaintTemp1;

    }

    public List<FbsComplaint> filterByEmail(List<FbsComplaint> complaintTemp, String email) {
        int k = 0;
        boolean nameStatus = false;
        List<FbsComplaint> complaintTemp1 = new ArrayList<FbsComplaint>();
        for (int i = 0; i < complaintTemp.size(); i++) {
            nameStatus = complaintTemp.get(i).getEmail().toUpperCase().contains(email.toUpperCase());
            if (nameStatus) {

                complaintTemp1.add(k, complaintTemp.get(i));

                k++;
            }
        }
        return complaintTemp1;

    }

    public List<FbsComplaint> filterBySubject(List<FbsComplaint> complaintTemp, String subject) {
        int k = 0;
        boolean nameStatus = false;
        List<FbsComplaint> complaintTemp1 = new ArrayList<FbsComplaint>();
        for (int i = 0; i < complaintTemp.size(); i++) {
            nameStatus = complaintTemp.get(i).getSubject().toUpperCase().contains(subject.toUpperCase());
            if (nameStatus) {

                complaintTemp1.add(k, complaintTemp.get(i));

                k++;
            }
        }
        return complaintTemp1;

    }
     public List<FbsComplaint> filterByRegister(List<FbsComplaint> complaintTemp, String register) {
        int k = 0;
        boolean nameStatus = false;
        List<FbsComplaint> complaintTemp1 = new ArrayList<FbsComplaint>();
        for (int i = 0; i < complaintTemp.size(); i++) {
            nameStatus = complaintTemp.get(i).getRegisteredBy().toUpperCase().contains(register.toUpperCase());
            if (nameStatus) {

                complaintTemp1.add(k, complaintTemp.get(i));

                k++;
            }
        }
        return complaintTemp1;

    }
     public List<FbsComplaint> filterByDate(List<FbsComplaint> complaintTemp, Date opStartDate, Date opEndDate) {
        int k = 0;
        List<FbsComplaint> complaintTemp1 = new ArrayList<FbsComplaint>();
        if ((opStartDate == null) || (opEndDate == null)) {
//            System.out.println("Start date & End Date is Null");
             resetDateOption();
            return complaintTemp;

        }
        if (opStartDate.after(opEndDate)) {
            Date temp = opEndDate;
            opEndDate = opStartDate;
            opStartDate = temp;
            this.opStartDate = opStartDate;
            this.opEndDate = opEndDate;
        }
         for (int i = 0; i < complaintTemp.size(); i++) {

            if (((complaintTemp.get(i).getComplaintDt().after(opStartDate)) || (complaintTemp.get(i).getComplaintDt().equals(opStartDate))) && ((complaintTemp.get(i).getComplaintDt().before(opEndDate))) || (complaintTemp.get(i).getComplaintDt().equals(opEndDate))) {

                complaintTemp1.add(k, complaintTemp.get(i));
                k++;
            }
        }
        return complaintTemp1;
    }



    public void resetComplaintIdOption() {
        this.opComplaintId = "";
        this.complnId = false;
        this.payOption[1] = false;
    }

    public void resetStatusOption() {
        this.opStatus = "";
        this.stats = false;
        this.payOption[0] = false;
    }

    public void resetNameOption() {
        this.opName = "";
        this.nam = false;
        this.payOption[2] = false;
    }

    public void resetAddressOption() {
        this.opAddress = "";
        this.addr = false;
        this.payOption[3] = false;
    }

    public void resetEmailOption() {
        this.opEmail = "";
        this.mail = false;
        this.payOption[4] = false;
    }

    public void resetSubjectOption() {
        this.opSubject = "";
        this.sub = false;
        this.payOption[5] = false;
    }

    public void resetRegisterByOption() {
        this.opRegiterBy = "";
        this.reg = false;
        this.payOption[6] = false;
    }

    public void resetDateOption() {
        this.opStartDate = null;
        this.opEndDate = null;
        payOption[7] = false;
    }

    public void resetArrayOfOption() {
        payOption[0] = false;
        payOption[1] = false;
        payOption[2] = false;
        payOption[3] = false;
        payOption[4] = false;
        payOption[5] = false;
        payOption[6] = false;
        payOption[7] = false;

    }
    public void resetBooleanOption()
    {
        complnId = false;
        stats = false;
        nam = false;
        addr = false;
        mail = false;
        sub = false;
        reg = false;

    }

    public void reset() {

        resetDateOption();
        resetNameOption();
        resetComplaintIdOption();
        resetStatusOption();
        resetAddressOption();
        resetEmailOption();
        resetSubjectOption();
        resetRegisterByOption();
        resetArrayOfOption();
        refComplaintList = this.complaintList;
    }

    public List<FbsComplaint> getComplaintList() {
        return complaintList;
    }

    public void setComplaintList(List<FbsComplaint> complaintList) {
        this.complaintList = complaintList;
    }

    public String[] getStatus() {
        return status;
    }

    public void setStatus(String[] Status) {
        this.status = Status;
    }

    public void setOpComplaintId(String opComplaintId) {
        this.opComplaintId = opComplaintId;
        if (!(opComplaintId.equals("")) && !(opComplaintId.equals("Complaint Id"))) {
            this.complnId = true;

        } else {
            resetComplaintIdOption();
        }
    }

    public String getOpComplaintId() {
        return opComplaintId;
    }

    public void setOpEndDate(Date opEndDate) {
       this.opEndDate = opEndDate;
        this.dat = true;
    }

   

    public void setOpSubject(String opSubject) {
        this.opSubject = opSubject;
        if (!(opSubject.equals("")) && !(opSubject.equals("Subject"))) {
            this.sub = true;

        } else {
            resetSubjectOption();
        }
    }

    public void setOpStatus(String opStatus) {
        this.opStatus = opStatus;
        if ((!(opStatus.equals(""))) && (!(opStatus.equals("Select Status")))) {
            this.stats = true;
        } else {
            resetStatusOption();
        }
    }

    public void setOpStartDate(Date opStartDate) {
        this.opStartDate = opStartDate;
        this.dat = false;
    }

    

    public void setOpRegiterBy(String opRegiterBy) {
        this.opRegiterBy = opRegiterBy;
        if (!(opRegiterBy.equals("")) && !(opRegiterBy.equals("Register By"))) {
            this.reg = true;

        } else {
            resetRegisterByOption();
        }
    }

    public void setOpName(String opName) {
        this.opName = opName;
        if (!(opName.equals("")) && !(opName.equals("Applicant Name"))) {
            this.nam = true;

        } else {
            resetNameOption();
        }
    }

    public void setOpEmail(String opEmail) {
        this.opEmail = opEmail;
        if (!(opEmail.equals("")) && !(opEmail.equals("Email"))) {
            this.mail = true;

        } else {
            resetEmailOption();
        }
    }

    public void setOpAddress(String opAddress) {
        this.opAddress = opAddress;
        if (!(opAddress.equals("")) && !(opAddress.equals("Address"))) {
            this.addr = true;

        } else {
            resetAddressOption();
        }
    }

    public void setRefComplaintList(List<FbsComplaint> refComplaintList) {
        this.refComplaintList = refComplaintList;
    }


    public String getOpSubject() {
        return opSubject;
    }

    public String getOpStatus() {
        return opStatus;
    }

    public Date getOpStartDate() {
        return opStartDate;
    }

    
    public String getOpRegiterBy() {
        return opRegiterBy;
    }

    public String getOpName() {
        return opName;
    }

    public Date getOpEndDate() {
        return opEndDate;
    }

   

    public String getOpEmail() {
        return opEmail;
    }

    public String getOpAddress() {
        return opAddress;
    }

    public List<FbsComplaint> getRefComplaintList() {
        return refComplaintList;
    }

}
