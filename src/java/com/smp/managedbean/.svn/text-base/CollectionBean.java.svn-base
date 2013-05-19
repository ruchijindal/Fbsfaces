/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.managedbean;

import com.itextpdf.text.DocumentException;
import com.smp.entity.FbsPayment;
import com.smp.fbs.CollectionInfo;
import com.smp.session.FbsPaymentFacade;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author smp
 */
@ManagedBean(name = "collectionBean")
@SessionScoped
@Stateless
public class CollectionBean {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
    @EJB
    FbsPaymentFacade fbsPaymentFacade;
    //FbsPayment fbsPayment;
    private List<FbsPayment> payment;
    private List<FbsPayment> paymentTemp;
    List<FbsPayment> fbsPaymentList;
    List<CollectionInfo> collectionInfoList;
    List<FbsPayment> FbsPaymentList;
    List<FbsPayment> refPaymentList;
    List<FbsPayment> paymentList;
    List<FbsPayment> tempList1;
    FbsPayment fbsPayment;
    public FbsPayment obj;
    String[] StatusOption = {"Pending", "Cleared"};
    Date opStartDate;
    Date opEndDate;
    Date opStartDate1;
    Date opEndDate1;
    String opStatus = "";
    String opUnitCode = "";
    String opAmmount = "";
    Long paidAmount;
    int totalAmount = 0;
    int chequeAmount = 0;
    int totalClearedAmount = 0;
    int totalPendingAmount = 0;
    boolean unit;
    boolean stats;
    boolean amt;
    boolean dat;
    private String dateMode;
    boolean[] payOption = new boolean[10];
    private Date startDate;
    private Date endDate;
    boolean flOption;
    long totalCollection = 0;
    long totalPendingAmount1 = 0;
    long totalClearedAmount1 = 0;
    CollectionInfo collectionInfo;
    boolean withInterest = false;
    // CollectionBean collectioonBean1=new CollectionBean();

    public CollectionBean() {
        paymentList = new ArrayList<FbsPayment>();
        fbsPaymentList = new ArrayList<FbsPayment>();
        tempList1 = new ArrayList<FbsPayment>();
        unit = false;
        stats = false;
        amt = false;
        dat = false;
        withInterest = false;
        for (int i = 0; i < 10; i++) {
            payOption[i] = false;
        }
        paymentList = new ArrayList<FbsPayment>();

    }

    @PostConstruct
    public void populatePaymentDetail() {
        paymentList = new ArrayList<FbsPayment>();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);
        String projId1 = (String) session.getAttribute("projId");

        FbsPaymentList = fbsPaymentFacade.findAll();

        //this loop is used to show the total at footer on view of total payment list
        for (int j = 0; j < FbsPaymentList.size(); j++) {
            totalCollection = totalCollection + FbsPaymentList.get(j).getPaidAmount();
            if (FbsPaymentList.get(j).getChequeStatus().equals("Cleared")) {
                totalClearedAmount1 = totalClearedAmount1 + FbsPaymentList.get(j).getPaidAmount();
            } else {
                totalPendingAmount1 = totalPendingAmount1 + FbsPaymentList.get(j).getPaidAmount();
            }

        }

        int l = 0;
        for (int i = 0; i < FbsPaymentList.size(); i++) {

            //this.paymentList.add(l, this.fbsPaymentList.get(i));
            this.paymentList.add(i, this.FbsPaymentList.get(i));

        }
        System.out.println("in post construct->>" + paymentList.size());
        refPaymentList = paymentList;

    }

    public void populateFilterByDate() {
        System.out.println("size of reflist++++++++" + paymentList.size());
        refPaymentList = searchByDate(paymentList, opStartDate1, opEndDate1);

        totalClearedAmount1 = 0;
        totalPendingAmount1 = 0;
        totalCollection = 0;
        for (int i = 0; i < refPaymentList.size(); i++) {
            totalCollection = totalCollection + refPaymentList.get(i).getPaidAmount();
            if (refPaymentList.get(i).getChequeStatus().equals("Cleared")) {
                totalClearedAmount1 += refPaymentList.get(i).getPaidAmount();
            } else {
                totalPendingAmount1 += refPaymentList.get(i).getPaidAmount();
            }
        }
    }

    public List<FbsPayment> searchByDate(List<FbsPayment> paymentTemp, Date opStartDate1, Date opEndDate1) {

        int k = 0;
        List<FbsPayment> paymentTemp1 = new ArrayList<FbsPayment>();
        if ((opStartDate1 == null) || (opEndDate1 == null)) {
//            System.out.println("Start date & End Date is Null");
            resetDateOption();
            return paymentTemp1;

        }
        if (opStartDate1.after(opEndDate1)) {
            Date temp = opEndDate1;
            opEndDate1 = opStartDate1;
            opStartDate1 = temp;
            this.opStartDate1 = opStartDate1;
            this.opEndDate1 = opEndDate1;
        }
        //System.out.println("Payment Date");
        for (int i = 0; i < paymentTemp.size(); i++) {

            if (!(paymentTemp.get(i).getPaymentDate() == null)) {
                if (((paymentTemp.get(i).getPaymentDate().after(opStartDate1)) || (paymentTemp.get(i).getPaymentDate().equals(opStartDate1))) && ((paymentTemp.get(i).getPaymentDate().before(opEndDate1))) || (paymentTemp.get(i).getPaymentDate().equals(opEndDate1))) {

                    paymentTemp1.add(k, paymentTemp.get(i));
                    k++;
                }
            }

        }
        return paymentTemp1;
    }

    public void genrateReceiptforCollection() throws DocumentException, IOException {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);
        session.setAttribute("startDate", this.opStartDate1);
        session.setAttribute("endDate", this.opEndDate1);

        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/CollectionReports?companyId=" + LoginBean.fbsLogin.getCompanyId() + "");
    }

    public void withinterest() throws DocumentException, IOException {
        withInterest = true;
        generateReceipt();

    }

    public void generateReceipt() throws DocumentException, IOException {
        //fbsPayment = (FbsPayment) event.getObject();
//        Integer paymentId = fbsPayment.getPaymentId();
//        String unitCode = fbsPayment.getUnitCode();
        Integer companyId1 = LoginBean.fbsLogin.getCompanyId();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);
        String applicantId = (String) session.getAttribute("applicantId");
        String projId = (String) session.getAttribute("projId");
        //String regNo = (String) session.getAttribute("regNo");
        String flattypeId = (String) session.getAttribute("flatTypeId");
        String unitCode = (String) session.getAttribute("flatId");
        String flatId = (String) session.getAttribute("flatId");


        if (withInterest == false) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/DueLetter?companyId=" + companyId1 + "&applicantId=" + applicantId + "&projId=" + projId);
        } else {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/DueLetter?companyId=" + companyId1 + "&applicantId=" + applicantId + "&projId=" + projId + "&interest=" + withInterest);
        }
        // FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/DueLetter?companyId=" + companyId1);

        //  FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/Letter?companyId=" + companyId1 + "&applicantId=" + applicantId+"&projId="+projId+"&flattypeId="+flattypeId+"&unitCode="+unitCode+"&flatId="+flatId);




        //FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/DueLetter?companyId=" + companyId1 + "&applicantId=" + applicantId+"&projId="+projId);
        // FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/DueLetter?companyId=" + companyId1);

        //  FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/Letter?companyId=" + companyId1 + "&applicantId=" + applicantId+"&projId="+projId+"&flattypeId="+flattypeId+"&unitCode="+unitCode+"&flatId="+flatId);




        //  FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/Letter?companyId=" + companyId1 + "&applicantId=" + applicantId+"&projId="+projId);
        // FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/DueLetter?companyId=" + companyId1);

        /*  FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/Letter?companyId=" + companyId1 + "&applicantId=" + applicantId+"&projId="+projId+"&flattypeId="+flattypeId+"&unitCode="+unitCode+"&flatId="+flatId);*/


    }

//      public void populatePaymentByFilter() {
//        paymentTemp = new ArrayList<FbsPayment>();
//        paymentTemp.addAll(this.payment);
////        System.out.println("Flat List-->" + flatTemp.size());
////        System.out.println("NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN ");
////        System.out.println("Select Block-->" + this.opBlockName);
////        System.out.println("Select FlatSpecification-->" + this.opFlatSpecification);
////        System.out.println("Select Floor-->" + this.opFloor);
////        System.out.println("Select Status-->" + this.opStatus);
////        System.out.println("Select ApplicantName-->" + this.opApplicantName);
////        System.out.println("Select StartDate-->" + this.opStartDate);
////        System.out.println("Select EndDate-->" + this.opEndDate);
////        System.out.println("+++++++++++++++++++++++++++++++++++++++ ");
////        System.out.println("blok-->" + this.blok);
////        System.out.println("fltSpeci-->" + this.fltSpeci);
////        System.out.println("flor-->" + this.flor);
////        System.out.println("stats-->" + this.Stats);
////        System.out.println("dat-->" + this.dat);
//
//       // int l = 0;
//
//
//            if (this.dat) {
//                flOption = this.dat;
//            }
//
//            if (flOption) {
//                paymentTemp = filterByDate(paymentTemp, opStartDate, opEndDate);
////                System.out.println("Flat List 6-->" + flatTemp.size());
//            }
//        }
    public void callServlet() throws IOException {                                    // callServlet add by ashish
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/CollectionReports");

    }

//
    public void resetDateOption() {
        this.opStartDate = null;
        this.opEndDate = null;
        payOption[7] = false;
    }

    public void resetStatusOption() {
        this.opStatus = "";
        this.stats = false;
        this.payOption[0] = false;
    }

    public void resetUnitCodeOption() {
        this.opUnitCode = "";
        this.unit = false;
        this.payOption[3] = false;
    }

    public void resetAmmountOption() {
        this.opAmmount = "";
        this.amt = false;
        this.payOption[5] = false;
    }

    public String populateProjectId(String flatId) {
        return flatId.substring(0, 2);
    }

    public List<FbsPayment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<FbsPayment> paymentList) {
        this.paymentList = paymentList;
    }

    public void setOpStartDate(Date opStartDate) {
        this.opStartDate = opStartDate;
        this.dat = false;
    }

    public void setOpEndDate(Date opEndDate) {
        this.opEndDate = opEndDate;
        this.dat = true;
    }

    public Date getOpStartDate() {
        return opStartDate;
    }

    public Date getOpEndDate() {
        return opEndDate;
    }

    public void setRefPaymentList(List<FbsPayment> refPaymentList) {
        this.refPaymentList = refPaymentList;
    }

    public List<FbsPayment> getRefPaymentList() {
        return refPaymentList;
    }

    public void setFbsPaymentList(List<FbsPayment> fbsPaymentList) {
        this.fbsPaymentList = fbsPaymentList;
    }

    public List<FbsPayment> getFbsPaymentList() {
        return fbsPaymentList;
    }

    public String[] getStatusOption() {
        return StatusOption;
    }

    public void setStatusOption(String[] StatusOption) {
        this.StatusOption = StatusOption;
    }

    public long getTotalClearedAmount1() {
        return totalClearedAmount1;
    }

    public void setTotalClearedAmount1(long totalClearedAmount1) {
        this.totalClearedAmount1 = totalClearedAmount1;
    }

    public long getTotalCollection() {
        return totalCollection;
    }

    public void setTotalCollection(long totalCollection) {
        this.totalCollection = totalCollection;
    }

    public long getTotalPendingAmount1() {
        return totalPendingAmount1;
    }

    public void setTotalPendingAmount1(long totalPendingAmount1) {
        this.totalPendingAmount1 = totalPendingAmount1;
    }

    public void setOpStatus(String opStatus) {
        this.opStatus = opStatus;
        if ((!(opStatus.equals(""))) && (!(opStatus.equals("Select Status")))) {
            this.stats = true;
        } else {
            resetStatusOption();
        }
    }

    public String getOpStatus() {
        return opStatus;
    }

    public void setOpUnitCode(String opUnitCode) {
        this.opUnitCode = opUnitCode;
        if (!(opUnitCode.equals("")) && !(opUnitCode.equals("Unite Code/Flat No"))) {
            this.unit = true;

        } else {
            resetUnitCodeOption();
        }
    }

    public void setOpAmmount(String opAmmount) {
        this.opAmmount = opAmmount;
        if (!(opAmmount.equals("")) && !(opAmmount.equals("Ammount"))) {
            this.amt = true;

        } else {
            resetAmmountOption();
        }
    }

    public void setDateMode(String dateMode) {
        this.dateMode = dateMode;
    }

    public String getOpUnitCode() {
        return opUnitCode;
    }

    public String getOpAmmount() {
        return opAmmount;
    }

    public String getDateMode() {
        return dateMode;
    }

    public Date getOpStartDate1() {
        return opStartDate1;
    }

    public Date getOpEndDate1() {
        return opEndDate1;
    }

    public void setOpStartDate1(Date opStartDate1) {
        this.opStartDate1 = opStartDate1;
    }

    public void setOpEndDate1(Date opEndDate1) {
        this.opEndDate1 = opEndDate1;
    }

    public boolean isWithInterest() {
        return withInterest;
    }

    public void setWithInterest(boolean withInterest) {
        this.withInterest = withInterest;
    }
}
