package com.smp.managedbean;

import com.smp.entity.FbsBank;
import com.smp.entity.FbsPayment;
import com.smp.session.FbsBankFacade;
import com.smp.session.FbsBlockFacade;
import com.smp.session.FbsPaymentFacade;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author smp11
 */
@ManagedBean(name = "paymentDetailManagedBean")
@ApplicationScoped
@Stateless
public class PaymentDetailManagedBean {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
    @EJB
    FbsPaymentFacade fbsPaymentFacade;
    @EJB
    FbsBankFacade fbsBankFacade;
    @EJB
    FbsBlockFacade fbsBlockFacade;
    List<FbsPayment> fbsPaymentList;
    List<FbsPayment> FbsPaymentList;
    List<FbsPayment> paymentListByDate;
    List<FbsPayment> refPaymentList;
    List<FbsPayment> paymentList2;
    List<FbsPayment> paymentList;
    List<FbsPayment> paymentListTemp;
    StringBuffer st = new StringBuffer();
    FbsPayment fbsPayment;
    FbsPayment fbsPaymentDetail;
    public FbsPayment obj;
    String[] paymentMode = {"Cash", "Cheque", "NEFT"};
    String[] StatusOption = {"Pending", "Cleared"};
    String[] clrBankOption;
    int[] clrBankOptionId;
    public SelectItem[] paymentModeOption;
    boolean[] payOption = new boolean[10];
    public ArrayList projectList;
    Date opStartDate;
    Date opEndDate;
    String opStatus = "";
    String opPayMode = "";
    String opClrBank = "";
    String opUnitCode = "";
    String opTrascId = "";
    String opAmmount = "";
    String opBankName = "";
    String opAuthorizeBy = "";
    String opRecievedBy = "";
    boolean stats;
    boolean mod;
    boolean clr;
    boolean unit;
    boolean trans;
    boolean amt;
    boolean bank;
    boolean auth;
    boolean recv;
    boolean dat;
    private String bankName;
    private String dateMode;
    public static List<FbsBank> bankList = new ArrayList();
    private ArrayList clrBankOption1;

    public PaymentDetailManagedBean() {
        paymentList = new ArrayList<FbsPayment>();
        fbsPaymentList = new ArrayList<FbsPayment>();
        stats = false;
        mod = false;
        clr = false;
        unit = false;
        trans = false;
        amt = false;
        bank = false;
        auth = false;
        recv = false;
        dat = false;
        for (int i = 0; i < 10; i++) {
            payOption[i] = false;
        }
        paymentList = new ArrayList<FbsPayment>();
    }

    @PostConstruct
    public void populatePaymentDetail() {
        System.out.println("hellooooooooooooooooo");

        paymentList = new ArrayList<FbsPayment>();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);
        String projId1 = (String) session.getAttribute("projId");
        if (projId1 != null) {
            paymentModeOption = createFilterOption(paymentMode);
            FbsPaymentList = fbsPaymentFacade.findAll();

            int l = 0;
            for (int i = 0; i < FbsPaymentList.size(); i++) {
                Long projId2 = populateProjectId(FbsPaymentList.get(i).getBlockId());
                System.out.println("proj1===>" + projId1);
                System.out.println("proj2===>" + projId2);


                if (projId1.equals(projId2.toString())) {
                    //this.paymentList.add(l, this.fbsPaymentList.get(i));
                    this.paymentList.add(l, this.FbsPaymentList.get(i));

                    l++;
                }
            }

            refPaymentList = paymentList;
            System.out.println("size of refPaymentList+++++++++++++" + refPaymentList.size());
            populateClrBankOption();
        } else {
            paymentModeOption = createFilterOption(paymentMode);
            FbsPaymentList = fbsPaymentFacade.findAll();

            int l = 0;
            for (int i = 0; i < FbsPaymentList.size(); i++) {


                //this.paymentList.add(l, this.fbsPaymentList.get(i));
                this.paymentList.add(l, this.FbsPaymentList.get(i));

                l++;

            }

            refPaymentList = paymentList;
            System.out.println("size of refPaymentList+++++++++++++" + refPaymentList.size());
            populateClrBankOption();
        }
    }

    public void populatePaymentListByFilter() {
        paymentListTemp = new ArrayList<FbsPayment>();
        paymentListTemp.addAll(this.paymentList);
//        System.out.println("Flat List-->" + paymentListTemp.size());
//        System.out.println("NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN ");
//        System.out.println("Select Status-->" + this.opStatus);
//        System.out.println("Select PayMode-->" + this.opPayMode);
//        System.out.println("Select Clearing Bank-->" + this.opClrBank);
//        System.out.println("Select Unit Code-->" + this.opUnitCode);
//
//        System.out.println("Select Transac Id-->" + this.opTrascId);
//        System.out.println("Select Ammount-->" + this.opAmmount);
//        System.out.println("Select Bank Name-->" + this.opBankName);
//        System.out.println("Select Authorize By-->" + this.opAuthorizeBy);
//        System.out.println("Select Recieved By-->" + this.opRecievedBy);
//        System.out.println("Start Date-->" + this.opStartDate);
//        System.out.println("End Date-->" + this.opEndDate);
//
//        System.out.println("+++++++++++++++++++++++++++++++++++++++ ");
//        System.out.println("stats-->" + this.stats);
//        System.out.println("mod-->" + this.mod);
//        System.out.println("clr-->" + this.clr);
//        System.out.println("unit-->" + this.unit);
//        System.out.println("trans-->" + this.trans);
//        System.out.println("amt-->" + this.amt);
//        System.out.println("bank-->" + this.bank);
//        System.out.println("auth-->" + this.auth);
//        System.out.println("recv-->" + this.recv);
//        System.out.println("dat-->" + this.dat);



        int l = 0;

        if ((opStatus.equals("Select Status")) && (opPayMode.equals("Payment Mode")) && (opClrBank.equals("Clearing Bank")) && (opUnitCode.equals("")) && (opTrascId.equals("")) && (opAmmount.equals("")) && (opBankName.equals("")) && (opAuthorizeBy.equals("")) && (opRecievedBy.equals(""))) {
            l = 0;
            //System.out.println("All are in select mode");

//        } else if ((opComplaintId.equals("")) && (!(this.dat))) {
//            System.out.println("All are in  ");
//            reset();

        } else {
            if (this.stats) {
                payOption[0] = this.stats;
            }
            if (this.mod) {
                payOption[1] = this.mod;
            }

            if (this.clr) {
                payOption[2] = this.clr;
            }
            if (this.unit) {
                payOption[3] = this.unit;
            }

            if (this.trans) {
                payOption[4] = this.trans;
            }
            if (this.amt) {
                payOption[5] = this.amt;
            }
            if (this.bank) {
                payOption[6] = this.bank;
            }
            if (this.auth) {
                payOption[7] = this.auth;
            }
            if (this.recv) {
                payOption[8] = this.recv;
            }
            if (this.dat) {
                payOption[9] = this.dat;
            }
            if (payOption[0]) {
                paymentListTemp = filterByStatus(paymentListTemp, this.opStatus);
                //System.out.println("Flat List 0-->" + paymentListTemp.size());
            }
            if (payOption[1]) {
                paymentListTemp = filterByPaymentMode(paymentListTemp, this.opPayMode);
                // System.out.println("Flat List 1-->" + paymentListTemp.size());
            }
            if (payOption[2]) {
                paymentListTemp = filterByClearingBank(paymentListTemp, this.opClrBank);
                // System.out.println("Flat List 2-->" + paymentListTemp.size());
            }
            if (payOption[3]) {
                paymentListTemp = filterByUnitCode(paymentListTemp, opUnitCode);
                // System.out.println("Flat List 3-->" + paymentListTemp.size());
            }
            if (payOption[4]) {
                paymentListTemp = filterByTransactionId(paymentListTemp, opTrascId);
                // System.out.println("Flat List 4-->" + paymentListTemp.size());
            }
            if (payOption[5]) {
                paymentListTemp = filterByAmmount(paymentListTemp, opAmmount);
                //System.out.println("Flat List 5-->" + paymentListTemp.size());
            }
            if (payOption[6]) {
                paymentListTemp = filterByBankName(paymentListTemp, opBankName);
                //System.out.println("Flat List 6-->" + paymentListTemp.size());
            }
            if (payOption[7]) {
                paymentListTemp = filterByAuthrizedBy(paymentListTemp, opAuthorizeBy);
                // System.out.println("Flat List 6-->" + paymentListTemp.size());
            }
            if (payOption[8]) {
                paymentListTemp = filterByRecievedBy(paymentListTemp, opRecievedBy);
                // System.out.println("Flat List 6-->" + paymentListTemp.size());
            }
            if (payOption[9]) {
                paymentListTemp = filterByDate(paymentListTemp, opStartDate, opEndDate, dateMode);
                //System.out.println("Flat List 6-->" + paymentListTemp.size());
            }
        }
        this.refPaymentList = this.paymentListTemp;
        resetBooleanOption();

    }

    public List<FbsPayment> filterByStatus(List<FbsPayment> paymentTemp, String opStatus) {
        int k = 0;
        List<FbsPayment> paymentTemp1 = new ArrayList<FbsPayment>();
        for (int i = 0; i < paymentTemp.size(); i++) {
            //System.out.println("Cheque Status:::::"+paymentTemp.get(i).getChequeStatus());
            if (paymentTemp.get(i).getChequeStatus().equals(opStatus)) {
                paymentTemp1.add(k, paymentTemp.get(i));
                k++;
            }
        }
        return paymentTemp1;
    }

    public List<FbsPayment> filterByPaymentMode(List<FbsPayment> paymentTemp, String opPaymentMode) {
        int k = 0;
        List<FbsPayment> paymentTemp1 = new ArrayList<FbsPayment>();
        for (int i = 0; i < paymentTemp.size(); i++) {
            if (paymentTemp.get(i).getPaymentMode().equals(opPaymentMode)) {
                paymentTemp1.add(k, paymentTemp.get(i));
                k++;
            }
        }
        return paymentTemp1;
    }

    public List<FbsPayment> filterByClearingBank(List<FbsPayment> paymentTemp, String opClrBank) {
        int k = 0;
        List<FbsPayment> paymentTemp1 = new ArrayList<FbsPayment>();
        for (int i = 0; i < paymentTemp.size(); i++) {
            //String banknam=findBankName(opClrBank);
            // System.out.println("ClrBankId-->"+paymentTemp.get(i).getClearingBankId());
            if (!(paymentTemp.get(i).getClearingBankId() == null)) {
                if (opClrBank.equals(paymentTemp.get(i).getClearingBankId().toString())) {
                    paymentTemp1.add(k, paymentTemp.get(i));
                    k++;
                }
            }

        }
        return paymentTemp1;
    }

    public List<FbsPayment> filterByUnitCode(List<FbsPayment> paymentTemp, String name) {
        int k = 0;
        boolean nameStatus = false;
        List<FbsPayment> paymentTemp1 = new ArrayList<FbsPayment>();
        for (int i = 0; i < paymentTemp.size(); i++) {
            nameStatus = paymentTemp.get(i).getUnitCode().toUpperCase().contains(name.toUpperCase());
            if (nameStatus) {

                paymentTemp1.add(k, paymentTemp.get(i));

                k++;
            }
        }
        return paymentTemp1;

    }

    public List<FbsPayment> filterByTransactionId(List<FbsPayment> paymentTemp, String name) {
        int k = 0;
        boolean nameStatus = false;
        List<FbsPayment> paymentTemp1 = new ArrayList<FbsPayment>();
        for (int i = 0; i < paymentTemp.size(); i++) {
            if (!(paymentTemp.get(i).getTransactionId() == null)) {
                nameStatus = paymentTemp.get(i).getTransactionId().toUpperCase().contains(name.toUpperCase());
                if (nameStatus) {

                    paymentTemp1.add(k, paymentTemp.get(i));

                    k++;
                }
            }
        }
        return paymentTemp1;

    }

    public List<FbsPayment> filterByAmmount(List<FbsPayment> paymentTemp, String name) {
        int k = 0;
        boolean nameStatus = false;
        List<FbsPayment> paymentTemp1 = new ArrayList<FbsPayment>();
        for (int i = 0; i < paymentTemp.size(); i++) {
            nameStatus = paymentTemp.get(i).getPaidAmount().toString().toUpperCase().contains(name.toUpperCase());
            if (nameStatus) {

                paymentTemp1.add(k, paymentTemp.get(i));

                k++;
            }
        }
        return paymentTemp1;

    }

    public List<FbsPayment> filterByBankName(List<FbsPayment> paymentTemp, String name) {
        int k = 0;
        boolean nameStatus = false;
        List<FbsPayment> paymentTemp1 = new ArrayList<FbsPayment>();
        for (int i = 0; i < paymentTemp.size(); i++) {
            if (!(paymentTemp.get(i).getDrawnOn() == null)) {
                nameStatus = paymentTemp.get(i).getDrawnOn().toUpperCase().contains(name.toUpperCase());
                if (nameStatus) {

                    paymentTemp1.add(k, paymentTemp.get(i));

                    k++;
                }
            }
        }
        return paymentTemp1;

    }

    public List<FbsPayment> filterByAuthrizedBy(List<FbsPayment> paymentTemp, String name) {
        int k = 0;
        boolean nameStatus = false;
        List<FbsPayment> paymentTemp1 = new ArrayList<FbsPayment>();
        for (int i = 0; i < paymentTemp.size(); i++) {
            if (!(paymentTemp.get(i).getAuthorizedBy() == null)) {
                nameStatus = paymentTemp.get(i).getAuthorizedBy().toUpperCase().contains(name.toUpperCase());
                if (nameStatus) {

                    paymentTemp1.add(k, paymentTemp.get(i));

                    k++;
                }
            }
        }
        return paymentTemp1;

    }

    public List<FbsPayment> filterByRecievedBy(List<FbsPayment> paymentTemp, String name) {
        int k = 0;
        boolean nameStatus = false;
        List<FbsPayment> paymentTemp1 = new ArrayList<FbsPayment>();
        for (int i = 0; i < paymentTemp.size(); i++) {
            if (!(paymentTemp.get(i).getUserId() == null)) {
                nameStatus = paymentTemp.get(i).getUserId().toUpperCase().contains(name.toUpperCase());
                if (nameStatus) {

                    paymentTemp1.add(k, paymentTemp.get(i));

                    k++;
                }
            }
        }
        return paymentTemp1;

    }

    public List<FbsPayment> filterByDate(List<FbsPayment> paymentTemp, Date opStartDate, Date opEndDate, String dateMode) {
        int k = 0;
        List<FbsPayment> paymentTemp1 = new ArrayList<FbsPayment>();
        if ((opStartDate == null) || (opEndDate == null)) {
//            System.out.println("Start date & End Date is Null");
            resetDateOption();
            return paymentTemp;

        }
        if (opStartDate.after(opEndDate)) {
            Date temp = opEndDate;
            opEndDate = opStartDate;
            opStartDate = temp;
            this.opStartDate = opStartDate;
            this.opEndDate = opEndDate;
        }
        if (dateMode.equals("paydate")) {
            //System.out.println("Payment Date");
            for (int i = 0; i < paymentTemp.size(); i++) {

                if (!(paymentTemp.get(i).getPaymentDate() == null)) {
                    if (((paymentTemp.get(i).getPaymentDate().after(opStartDate)) || (paymentTemp.get(i).getPaymentDate().equals(opStartDate))) && ((paymentTemp.get(i).getPaymentDate().before(opEndDate))) || (paymentTemp.get(i).getPaymentDate().equals(opEndDate))) {

                        paymentTemp1.add(k, paymentTemp.get(i));
                        k++;
                    }
                }
            }
        } else if (dateMode.equals("chqdate")) {
            // System.out.println("Cheque Date");
            for (int i = 0; i < paymentTemp.size(); i++) {

                if (!(paymentTemp.get(i).getChequeDate() == null)) {
                    if (((paymentTemp.get(i).getChequeDate().after(opStartDate)) || (paymentTemp.get(i).getChequeDate().equals(opStartDate))) && ((paymentTemp.get(i).getChequeDate().before(opEndDate))) || (paymentTemp.get(i).getChequeDate().equals(opEndDate))) {

                        paymentTemp1.add(k, paymentTemp.get(i));
                        k++;
                    }
                }
            }
        } else if (dateMode.equals("clrdate")) {
            // System.out.println("Clearing Date");
            for (int i = 0; i < paymentTemp.size(); i++) {

                if (!(paymentTemp.get(i).getClearingDt() == null)) {
                    if (((paymentTemp.get(i).getClearingDt().after(opStartDate)) || (paymentTemp.get(i).getClearingDt().equals(opStartDate))) && ((paymentTemp.get(i).getClearingDt().before(opEndDate))) || (paymentTemp.get(i).getClearingDt().equals(opEndDate))) {

                        paymentTemp1.add(k, paymentTemp.get(i));
                        k++;
                    }
                }
            }
        } else {
            // System.out.println("No date option");
            return paymentTemp;
        }
        return paymentTemp1;
    }

    public void clearingBankNameOption() {
        List<FbsBank> fbsBank = fbsBankFacade.findAll();
        clrBankOption = new String[fbsBank.size()];
        clrBankOptionId = new int[fbsBank.size()];
        for (int i = 0; i < fbsBank.size(); i++) {
            clrBankOption[i] = fbsBank.get(i).getBankName();
            clrBankOptionId[i] = fbsBank.get(i).getBankId();
        }
    }

    public void populateClrBankOption() {
        clrBankOption1 = new ArrayList();
        for (int i = 0; i < fbsBankFacade.findAll().size(); i++) {
            clrBankOption1.add(new SelectItem(fbsBankFacade.findAll().get(i).getBankId(), fbsBankFacade.findAll().get(i).getBankName()));
        }
    }

    public void resetBooleanOption() {

        stats = false;
        mod = false;
        clr = false;
        unit = false;
        trans = false;
        amt = false;
        bank = false;
        auth = false;
        recv = false;
        dat = false;
    }

    public void reset() {

        resetDateOption();
        resetAmmountOption();
        resetPayModeOption();
        resetStatusOption();
        resetClearingBankOption();
        resetUnitCodeOption();
        resetTransactionIdOption();
        resetRecivedByOption();
        resetBankOption();
        resetAuthorizeByOption();
        refPaymentList = this.paymentList;
    }

    public void resetDateOption() {
        this.opStartDate = null;
        this.opEndDate = null;
        payOption[7] = false;
    }

    public void resetAmmountOption() {
        this.opAmmount = "";
        this.amt = false;
        this.payOption[5] = false;
    }

    public void resetPayModeOption() {
        this.opPayMode = "";
        this.mod = false;
        this.payOption[1] = false;
    }

    public void resetStatusOption() {
        this.opStatus = "";
        this.stats = false;
        this.payOption[0] = false;
    }

    public void resetClearingBankOption() {
        this.opClrBank = "";
        this.clr = false;
        this.payOption[2] = false;
    }

    public void resetUnitCodeOption() {
        this.opUnitCode = "";
        this.unit = false;
        this.payOption[3] = false;
    }

    public void resetTransactionIdOption() {
        this.opTrascId = "";
        this.trans = false;
        this.payOption[4] = false;
    }

    public void resetRecivedByOption() {
        this.opRecievedBy = "";
        this.recv = false;
        this.payOption[9] = false;
    }

    public void resetBankOption() {
        this.opBankName = "";
        this.bank = false;
        this.payOption[6] = false;
    }

    public void resetAuthorizeByOption() {
        this.opAuthorizeBy = "";
        this.auth = false;
        this.payOption[7] = false;
    }

    public Long populateProjectId(int blockId) {
        return (fbsBlockFacade.find(blockId).getFkProjId());

    }

    public SelectItem[] createFilterOption(String[] data) {
        SelectItem[] option = new SelectItem[data.length + 1];
        option[0] = new SelectItem("", "select");
        for (int i = 0; i < data.length; i++) {
            option[i + 1] = new SelectItem(data[i], data[i]);
        }
        return option;
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

    public SelectItem[] getPaymentModeOption() {
        return paymentModeOption;
    }

    public void setPaymentModeOption(SelectItem[] paymentModeOption) {
        this.paymentModeOption = paymentModeOption;
    }

    public void setPaymentMode(String[] paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String[] getPaymentMode() {
        return paymentMode;
    }

    public String[] getStatusOption() {
        return StatusOption;
    }

    public void setStatusOption(String[] StatusOption) {
        this.StatusOption = StatusOption;
    }

    public void setOpStatus(String opStatus) {
        this.opStatus = opStatus;
        if ((!(opStatus.equals(""))) && (!(opStatus.equals("Select Status")))) {
            this.stats = true;
        } else {
            resetStatusOption();
        }
    }

    public void setOpPayMode(String opPayMode) {
        this.opPayMode = opPayMode;
        if ((!(opPayMode.equals(""))) && (!(opPayMode.equals("Payment Mode")))) {
            this.mod = true;
        } else {
            resetPayModeOption();
        }
    }

    public String getOpPayMode() {
        return opPayMode;
    }

    public String getOpStatus() {
        return opStatus;
    }

    public String[] getClrBankOption() {
        return clrBankOption;
    }

    public void setClrBankOption(String[] clrBankOption) {
        this.clrBankOption = clrBankOption;
    }

    public String getOpClrBank() {
        return opClrBank;


    }

    public void setOpClrBank(String opClrBank) {
        this.opClrBank = opClrBank;
        if ((!(opClrBank.equals(""))) && (!(opClrBank.equals("Clearing Bank")))) {
            this.clr = true;
        } else {
            resetClearingBankOption();
        }
    }

    public void setOpUnitCode(String opUnitCode) {
        this.opUnitCode = opUnitCode;
        if (!(opUnitCode.equals("")) && !(opUnitCode.equals("Unite Code/Flat No"))) {
            this.unit = true;

        } else {
            resetUnitCodeOption();
        }
    }

    public void setOpTrascId(String opTrascId) {
        this.opTrascId = opTrascId;
        if (!(opTrascId.equals("")) && !(opTrascId.equals("Cheque No/Transaction Id"))) {
            this.trans = true;

        } else {
            resetTransactionIdOption();
        }
    }

    public void setOpRecievedBy(String opRecievedBy) {
        this.opRecievedBy = opRecievedBy;
        if (!(opRecievedBy.equals("")) && !(opRecievedBy.equals("Recieved By"))) {
            this.recv = true;

        } else {
            resetRecivedByOption();
        }
    }

    public void setOpBankName(String opBankName) {
        this.opBankName = opBankName;
        if (!(opBankName.equals("")) && !(opBankName.equals("Bank Name"))) {
            this.bank = true;

        } else {
            resetBankOption();
        }
    }

    public void setOpAuthorizeBy(String opAuthorizeBy) {
        this.opAuthorizeBy = opAuthorizeBy;
        if (!(opAuthorizeBy.equals("")) && !(opAuthorizeBy.equals("Authrized By"))) {
            this.auth = true;

        } else {
            resetAuthorizeByOption();
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

    public String getOpTrascId() {
        return opTrascId;
    }

    public String getOpRecievedBy() {
        return opRecievedBy;
    }

    public String getOpBankName() {
        return opBankName;
    }

    public String getOpAuthorizeBy() {
        return opAuthorizeBy;
    }

    public String getOpAmmount() {
        return opAmmount;
    }

    public String getDateMode() {
        return dateMode;
    }

    public void setClrBankOption1(ArrayList clrBankOption1) {
        this.clrBankOption1 = clrBankOption1;
    }

    public ArrayList getClrBankOption1() {
        return clrBankOption1;
    }

    public String findBankName(String bankId) {

        bankName = "";
        if (!bankId.equals("")) {
            bankList = em.createNamedQuery("FbsBank.findByBankId").setParameter("bankId", Integer.parseInt(bankId)).getResultList();
            bankName = bankList.get(0).getBankName();
            return bankName;
        }
        return bankName;
    }
}
