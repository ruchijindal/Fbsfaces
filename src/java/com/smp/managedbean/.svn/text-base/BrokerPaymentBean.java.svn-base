/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.managedbean;

import com.smp.report.PaymentReceipt;
import com.smp.entity.FbsBank;
import com.smp.session.FbsBookingFacade;
import com.smp.session.FbsBrPaymentFacade;
import com.smp.session.FbsPaymentFacade;
import com.smp.session.FbsBankFacade;
import java.util.Date;
import com.itextpdf.text.DocumentException;
import com.smp.entity.FbsBlock;
import com.smp.entity.FbsBooking;
import com.smp.entity.FbsBrPayment;
import com.smp.entity.FbsBroker;
import com.smp.entity.FbsBrokerCat;
import com.smp.entity.FbsDiscount;
import com.smp.entity.FbsFlat;
import com.smp.entity.FbsFlatType;
import com.smp.entity.FbsPayment;
import com.smp.entity.FbsPlanname;
import com.smp.entity.FbsProject;
import com.smp.entity.FbsServicetax;
import com.smp.session.FbsBlockFacade;
import com.smp.session.FbsBrokerCatFacade;
import com.smp.session.FbsBrokerFacade;
import com.smp.session.FbsDiscountFacade;
import com.smp.session.FbsFlatTypeFacade;
import com.smp.session.FbsPlannameFacade;
import com.smp.session.FbsProjectFacade;
import com.smp.session.FbsServicetaxFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.faces.model.SelectItem;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.SessionScoped;
import javax.persistence.Query;
import javax.faces.context.FacesContext;

/**
 *
 * @author smp
 */
@ManagedBean(name = "brokerPaymentBean")
@SessionScoped
@Stateful
public class BrokerPaymentBean implements Serializable {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
    @EJB
    FbsPaymentFacade fbsPaymentFacade;
    @EJB
    FbsBankFacade fbsBankFacade;
    @EJB
    FbsBookingFacade fbsBookingFacade;
    @EJB
    FbsBlockFacade fbsBlockFacade;
    @EJB
    FbsBrokerFacade fbsBrokerFacade;
    @EJB
    FbsBrPaymentFacade fbsBrPaymentFacade;
    @EJB
    FbsProjectFacade fbsProjectFacade;
    @EJB
    FbsFlatTypeFacade FbsFlatTypeFacade;
    @EJB
    FbsDiscountFacade fbsDiscountFacade;
    @EJB
    FbsBrokerFacade fbsBrokerFacadeNew;
    @EJB
    FbsBrokerCatFacade fbsBrokerCatFacade;
    @EJB
    FbsPlannameFacade fbsPlannameFacade;
    @EJB
    FbsServicetaxFacade fbsServicetaxFacade;
    public FbsBlock fbsBlock = new FbsBlock();
    public FbsPayment fbsPayment = new FbsPayment();
    public static List<FbsBrPayment> paymentList = new ArrayList();
    public static List<FbsBank> bankList = new ArrayList();
    public FbsBooking fbsBooking = new FbsBooking();
    public ArrayList bankNames;
    private String paymentMode;
    public boolean render = false;
    public boolean render1 = false;
    private String status;
    private Date clearingDate;
    public FbsBrPayment obj;
    private String clearingBank;
    private String chequeNo;
    private Date chequeDate;
    private String chequeAmt;
    private String drawnOn;
    private String transactionId;
    Integer brokerId = 0;
    int flatId = 0;
    String projId = "";
    String projid = "";
    String companyId;
    private List<FbsBooking> fbsBookings = new ArrayList<FbsBooking>();
    public PaymentReceipt paymentReceipt = new PaymentReceipt();
    private ArrayList unitNameList = new ArrayList();
    public FbsBroker fbsBroker = new FbsBroker();
    public FbsBrPayment fbsBrPayment = new FbsBrPayment();
    public static FbsBrPayment fbsdelBrPayment = new FbsBrPayment();
    public List<FbsBroker> brokerList = new ArrayList();
    private ArrayList brokerNameList = new ArrayList();
    String remark;
    public FbsBrPayment fbsBrPayment1 = new FbsBrPayment();
    private FbsBrokerCat fbsBrokerCat = new FbsBrokerCat();
    public FbsBooking fbsBookingList = new FbsBooking();
    FbsBlock fbsBlockName = new FbsBlock();
    FbsProject fbsProjectName = new FbsProject();
    private List<FbsFlat> fbsFlatList = new ArrayList<FbsFlat>();
    String xmlFile = "";
    List<FbsFlat> refFlatList = new ArrayList<FbsFlat>();
    public ArrayList categoryList;
    public FbsFlatType fbsFlatType = new FbsFlatType();
    List<FbsBrPayment> fbsBrPaymentlist = new ArrayList<FbsBrPayment>();
    public List<FbsPayment> fbsPaymentList = new ArrayList<FbsPayment>();
    public FbsDiscount fbsDiscount = new FbsDiscount();
    List<FbsBrPayment> brokerPaymentList = new ArrayList<FbsBrPayment>();
    float totalPaidAmount = 0;
    float totalPayableAmount = 0;
    float totalAmount = 0;
    int discountPercantadge = 0;
    int discountId = 0;
    public FbsBooking fbsBooking1 = new FbsBooking();
    float totalCommission = 0;
    int total;
    float totalPayable;
    float totalPaid;
    int totalpayoutstanding = 0;
    float totalOutstanding = 0;
    float brokerpaymentAmount = 0;
    float clearedAmount = 0;
    float pendingAmount = 0;
     public FbsPlanname fbsPlanname = new FbsPlanname();
     public FbsServicetax fbsServicetax=new FbsServicetax();

    @PostConstruct
    public void populate() {
        System.out.println("inside populate--------------");
        //FacesContext facesContext = FacesContext.getCurrentInstance();
        //ExternalContext externalContext = facesContext.getExternalContext();
        //HttpSession session = (HttpSession) externalContext.getSession(true);
        //projid = (String) session.getAttribute("projId");
       // fbsBrPayment.setPaymentMode("Cash");
        brokerList.clear();
        brokerNameList.clear();
        brokerId = new Integer(0);
        fbsBrPayment.setPaymentMode("Cash");
        System.out.println("brokerId======>" + brokerId);
        brokerList = fbsBrokerFacade.findAll();
        for (int i = 0; i < brokerList.size(); i++) {
            brokerNameList.add(new SelectItem(brokerList.get(i).getBrokerId(), brokerList.get(i).getBrName()));
        }

        paymentList = em.createNamedQuery("FbsBrPayment.findAll").getResultList();
        bankList = fbsBankFacade.findAll();
        bankNames = new ArrayList();
        for (int i = 0; i < bankList.size(); i++) {
            bankNames.add(new SelectItem(bankList.get(i).getBankId(), bankList.get(i).getBankName()));
        }
    }

    public void populateFlats() {

        System.out.println("inside++++++++++++");
        fbsBookings.clear();
        unitNameList.clear();
        flatId = 0;
        if (this.brokerId != 0) {
            System.out.println("brokerId===> " + this.brokerId);
            fbsBookings = em.createNamedQuery("FbsBooking.findByBrokerId").setParameter("brokerId", this.brokerId).getResultList();
            System.out.println("size of  fbsBookings  ::::::" + fbsBookings.size());
            for (int i = 0; i < fbsBookings.size(); i++) {
                unitNameList.add(new SelectItem(fbsBookings.get(i).getFlatId()));
            }
        }
    }

    public int findBroker(Integer brokerId, Integer flatId) throws IOException {
        System.out.println("flat Id=====>" + flatId);
        fbsBroker = fbsBrokerFacadeNew.find(brokerId);
        String categoryId = fbsBroker.getCategoryId().toString();
        fbsBrokerCat = fbsBrokerCatFacade.find(Integer.parseInt(categoryId));
        int commission = fbsBrokerCat.getCommission();
        //  @NamedQuery(name = "FbsBooking.flatId&brokerId", query = "SELECT  f from FbsBooking f WHERE f.brokerId = :brokerId AND f.flatId = :flatId")
        totalpayoutstanding = 0;
        totalAmount = 0;
        totalPaidAmount = 0;
        totalPayableAmount = 0;

        Query query = em.createNamedQuery("FbsBooking.flatId&brokerId");
        query.setParameter("brokerId", brokerId);
        query.setParameter("flatId", flatId);
        fbsBookingList = (FbsBooking) query.getResultList().get(0);
  fbsPlanname = fbsPlannameFacade.find(fbsBookingList.getPlanId());

        try {

            fbsBlock = fbsBlockFacade.find(fbsBookingList.getBlockId());
            fbsFlatList = new ArrayList();
            int l = 0;
            xmlFile = fbsBlock.getXmlFile();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(xmlFile)));
            doc.getDocumentElement().normalize();
            NodeList block = doc.getElementsByTagName("block");
            NodeList floorList1 = block.item(0).getChildNodes();
            for (int i = 0; i < floorList1.getLength(); i++) {
                Node floor = floorList1.item(i);
                if (floor.getNodeType() == Node.ELEMENT_NODE) {
                    Element floorElement = (Element) floor;
                    String floorId1 = floorElement.getAttribute("floor_id").trim();
                    NodeList floorNoList = floorElement.getElementsByTagName("floor_number");
                    Node fnoElement = (Node) floorNoList.item(0);
                    NodeList flatList = floorElement.getElementsByTagName("flat");
                    for (int j = 0; j < flatList.getLength(); j++) {
                        FbsFlat fbsFlat = new FbsFlat();
                        fbsFlat.setFloorNo(fnoElement.getTextContent());
                        Element flatElement = (Element) flatList.item(j);
                        String flatId1 = flatElement.getAttribute("flat_id").trim();
                        fbsFlat.setFlatId(Long.parseLong(flatId1));
                        NodeList flatTypeList = flatElement.getElementsByTagName("flattype");
                        Element typeElement = (Element) flatTypeList.item(0);
                        fbsFlat.setFlatType(typeElement.getAttribute("flatTypeId").trim());
                        NodeList flatNoList1 = flatElement.getElementsByTagName("flatno");
                        Element noElement = (Element) flatNoList1.item(0);
                        fbsFlat.setFlatNo(noElement.getAttribute("flatNo").trim());
                        fbsFlatList.add(l, fbsFlat);
                        l++;
                        refFlatList = fbsFlatList;
                    }
                }
            }

            for (int b = 0; b < fbsFlatList.size(); b++) {
                if (fbsFlatList.get(b).getFlatId().toString().equals(flatId.toString())) {
                    System.out.println("inside if====>");

                    fbsFlatType = FbsFlatTypeFacade.find(Integer.parseInt(fbsFlatList.get(b).getFlatType()));

                    fbsBrPaymentlist = em.createNamedQuery("FbsBrPayment.findByFkFlatId").setParameter("fkFlatId", fbsBookingList.getFlatId()).getResultList();
                    totalPaidAmount = 0;
                    for (int i = 0; i < fbsBrPaymentlist.size(); i++) {
                        totalPaidAmount = (float) totalPaidAmount + fbsBrPaymentlist.get(i).getAmount();
                    }

                    fbsBooking1 = (FbsBooking) em.createNamedQuery("FbsBooking.findByFlatId").setParameter("flatId", fbsBookingList.getFlatId()).getResultList().get(0);

                    discountId = fbsBooking1.getDiscountId();
                    fbsDiscount = fbsDiscountFacade.find(discountId);
                    discountPercantadge = fbsDiscount.getPercentage();
                    int planDiscount = 0;
                    planDiscount = fbsPlanname.getDiscount();

                    int br = fbsFlatType.getFlatBsp();
                    int sba = fbsFlatType.getFlatSba();
                    int bsp;
                    int br_commisson;
                    if (br != 0 && sba != 0) {
                        bsp = sba * br;
                        bsp = bsp - ((discountPercantadge * bsp) / 100) - ((planDiscount * bsp) / 100);

                    } else {
                        bsp = 0;
                    }


                    br_commisson = (bsp * commission) / 100;
                    System.out.println("bsp===>" + bsp + "brcommission==>" + br_commisson);

                    fbsPaymentList = em.createNamedQuery("FbsPayment.findByUnitCode").setParameter("unitCode", fbsFlatList.get(b).getFlatId().toString()).getResultList();

                    totalAmount = 0;
                    double total_Amount=0.0;
                    for (int j = 0; j < fbsPaymentList.size(); j++) {

                        totalAmount = totalAmount + fbsPaymentList.get(j).getPaidAmount();
                          fbsServicetax = fbsServicetaxFacade.find(fbsPaymentList.get(j).getServiceTax());
                        //    System.out.println("servicetax..?????????????/"+fbsServiceTax.getServicetax());
                        // totalAmount=totalPaidAmount+(totalPaidAmount*fbsServicetax.getServicetax()/100);
                        Double temp = 1 + ((double) fbsServicetax.getServicetax()) / 100;
                        //   System.out.println("total Amount......."+totalAmount);
                        //   System.out.println("temp value........"+temp);
                        total_Amount =  totalAmount / temp;
                    }

                    System.out.println("total amount====>" + totalAmount);
                    System.out.println("total amount====>" + total_Amount);


                    int bspPercantage = fbsBrokerCat.getBspPercent();
                    int brokerPercantage = fbsBrokerCat.getBrokerPercent();

                    int installment = (int) 100 / brokerPercantage;
                    int rem = 100 % brokerPercantage;
                    if (rem > 0) {
                        installment++;
                    }

                    float bspAmount = 0;

                    int count = 0;

                    bspAmount = 0;
                    for (int i1 = 1; i1 <= installment; i1++) {

                        bspAmount = (float) (bspPercantage * i1 * bsp) / 100;

                        if (total_Amount >= bspAmount) {
                            count++;
                        }

                    }
                    totalPayableAmount = 0;

                    if (count < installment) {
                        totalPayableAmount = (float) (brokerPercantage * count * br_commisson) / 100;
                    } else {

                        totalPayableAmount = br_commisson;
                    }

                    totalpayoutstanding = ((int) totalPayableAmount - (int) totalPaidAmount);

                    break;
                }
                System.out.println("totalPayableAmount==>" + totalPayableAmount);
                System.out.println("totalPaidAmount==>" + totalPaidAmount);
            }

        } catch (Exception ex) {
            Logger.getLogger(QuickBookingBean.class.getName()).log(Level.SEVERE, null, ex);
        }


        return totalpayoutstanding;


    }

    public void paymentvalidate(float amount) throws IOException {

        System.out.println("brokerId******** " + this.brokerId);
        System.out.println("flatId****************" + this.flatId);
        System.out.println("amount**************" + amount);
        totalpayoutstanding = findBroker(this.brokerId, this.flatId);

        if (totalpayoutstanding < amount) {
            FacesContext context = FacesContext.getCurrentInstance();
            //context.addMessage(null, new FacesMessage("Entered amount should be less than " + totalpayoutstanding));
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Entered amount should be less than " + totalpayoutstanding, ""));
            System.out.println("valid amount is " + totalpayoutstanding);

        } else {
            addBrokerPayment();

        }

    }

    public void showAmount() throws IOException {


        totalpayoutstanding = findBroker(this.brokerId, this.flatId);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Brokerage amount should be " + totalpayoutstanding));
    }

    public void addBrokerPayment() {
        fbsBrPayment.setStatus("Pending");
        //System.out.println("+++++++-->"+chequeAmt);

        if (this.fbsBrPayment.getPaymentMode().equals("Cheque")) {
            //    fbsBrPayment.setChequeAmt(chequeAmt);
            fbsBrPayment.setChequeDate(chequeDate);
            fbsBrPayment.setChequeNo(Integer.parseInt(chequeNo));
            fbsBrPayment.setDrawnOn(drawnOn);
        }
        if (this.fbsBrPayment.getPaymentMode().equals("NEFT")) {

            fbsBrPayment.setTransactionId(transactionId);
            fbsBrPayment.setDrawnOn(drawnOn);
        }
        fbsBrPayment.setUserId(LoginBean.fbsLogin.getUserId());
        fbsBrPayment.setBrokerId(this.brokerId);
        fbsBrPayment.setFkFlatId(this.flatId);
        fbsBrPaymentFacade.create(fbsBrPayment);
        fbsBrPayment = new FbsBrPayment();
        brokerNameList.clear();
        unitNameList.clear();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Congrates! Broker Payment Successfully Added"));
        paymentList = em.createNamedQuery("FbsBrPayment.findAll").getResultList();
        this.chequeAmt = "";
        this.chequeDate = null;
        this.chequeNo = "";
        this.drawnOn = "";
        fbsBrPayment.setAmount(null);
        fbsBrPayment.setRemark("");
        fbsBrPayment.setPaymentDate(null);
        this.transactionId = null;

        fbsBrPayment.setFkFlatId(null);
        populate();

    }

    public void editBrokerPayment(org.primefaces.event.RowEditEvent e) {

        fbsBrPaymentFacade.edit((FbsBrPayment) e.getObject());
    }

    public void deleteBrokerPayment() throws IOException {
        fbsBrPaymentFacade.remove(fbsdelBrPayment);
        paymentList.clear();
        paymentList = em.createNamedQuery("FbsBrPayment.findAll").getResultList();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/Broker/brokerPayment.xhtml");

    }

    public void payment(FbsBrPayment fbsBrPayment) {
        fbsdelBrPayment = fbsBrPayment;
    }

    public void renderChequeDetails() {
        if (this.fbsBrPayment.getPaymentMode().equals("Cheque")) {
            this.render = true;
            this.render1 = false;
        } else if (this.fbsBrPayment.getPaymentMode().equals("Cash")) {
            this.render = false;
            this.render1 = false;
        } else if (this.fbsBrPayment.getPaymentMode().equals("NEFT")) {
            this.render = false;
            this.render1 = true;

        }

    }

    public void putStatus() {

        String userId = LoginBean.fbsLogin.getUserId();

        obj.setStatus(this.status);
        obj.setClearingDt(this.clearingDate);
        obj.setClearingBank(this.clearingBank);


        if (this.status.equals("Cleared")) {
            //  obj.setClearingBank(fbsBrPayment.getClearingBank());


            obj.setAuthorizedBy(userId);
        }
        fbsBrPaymentFacade.edit(obj);
        populate();
    }

    public void SetObject(FbsBrPayment fbsBrPayment) {
        obj = fbsBrPayment;
        clearingDate = new Date();
    }

//    public void populateUnitName() {
//        fbsBookings = fbsBookingFacade.findAll();
//        bookingFlatList = new String[fbsBookings.size()];
//        for (int i = 0; i < fbsBookings.size(); i++) {
//            bookingFlatList[i] = fbsBookings.get(i).getFlatId().toString();
//        }
//    }
    public void genrateReceipt(FbsBrPayment fbsBrPayment) throws DocumentException, IOException {
        // fbsBrPayment = (FbsBrPayment) event.getObject();
        Integer paymentId = fbsBrPayment.getPaymentId();
        String unitCode = fbsBrPayment.getFkFlatId().toString();
        Integer companyId = LoginBean.fbsLogin.getCompanyId();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/brokerPaymentReceipt?paymentId=" + paymentId + "&unitCode=" + unitCode + "&companyId=" + companyId + "");
    }

    public boolean setDisableAuthrize(String paymentMode, String chequeStatus) {
        if (paymentMode.equals("cash") || chequeStatus.equals("Cleared")) {
            return true;
        }
        return false;
    }

    public void edit() {
        fbsBrPayment1.setRemark(remark);
        fbsBrPaymentFacade.edit((fbsBrPayment1));
        fbsBrPayment1 = new FbsBrPayment();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Congrates! Complaint Successfully Updated"));
        populate();

    }

    public void SetObj(FbsBrPayment fbsBrPayment) {
        remark = fbsBrPayment.getRemark();
        fbsBrPayment1 = fbsBrPayment;
        System.out.println("****remark***" + remark);

    }

    public void reset() throws IOException {
        brokerId = new Integer(0);
        flatId = 0;
        System.out.println("brokerId=====>" + brokerId);
        unitNameList.clear();
        bankNames.clear();
        fbsBookings.clear();
        fbsBrPayment = new FbsBrPayment();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/Broker/brokerPayment.xhtml");

    }

    public String findBrokerName(int brokerId) {
        return (fbsBrokerFacade.find(brokerId).getBrName());
    }

    public String getClearingBank() {
        return clearingBank;
    }

    public void setClearingBank(String clearingBank) {
        this.clearingBank = clearingBank;
    }

    public List<FbsBrPayment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<FbsBrPayment> paymentList) {
        this.paymentList = paymentList;
    }

    public void SetPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setRender(boolean render) {
        this.render = render;
    }

    public boolean getRender() {
        return this.render;
    }

    public void setRender1(boolean render1) {
        this.render1 = render1;
    }

    public boolean getRender1() {
        return this.render1;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setObj(FbsBrPayment obj) {
        this.obj = obj;
    }

    public FbsBrPayment getObj() {
        return obj;
    }

    public void setClearingDate(Date clearingDate) {
        this.clearingDate = clearingDate;
    }

    public Date getClearingDate() {
        return clearingDate;
    }

//    public String getClearingBankId() {
//        return clearingBankId;
//    }
//
//    public void setClearingBankId(String clearingBankId) {
//        this.clearingBankId = clearingBankId;
//    }
//
//    public ArrayList getBankNames() {
//        return bankNames;
//    }
//
//    public void setBankNames(ArrayList bankNames) {
//        this.bankNames = bankNames;
//    }
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setDrawnOn(String drawnOn) {
        this.drawnOn = drawnOn;
    }

    public String getDrawnOn() {
        return drawnOn;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeDate(Date chequeDate) {
        this.chequeDate = chequeDate;
    }

    public Date getChequeDate() {
        return chequeDate;
    }

    public String getChequeAmt() {
        return chequeAmt;
    }

    public void setChequeAmt(String chequeAmt) {
        this.chequeAmt = chequeAmt;
    }

    public void setFbsBroker(FbsBroker fbsBroker) {
        this.fbsBroker = fbsBroker;
    }

    public FbsBroker getFbsBroker() {
        return fbsBroker;
    }

    public void setBrokerList(List<FbsBroker> brokerList) {
        this.brokerList = brokerList;
    }

    public List<FbsBroker> getBrokerList() {
        return brokerList;
    }

    public ArrayList getBrokerNameList() {
        return brokerNameList;
    }

    public void setBrokerNameList(ArrayList brokerNameList) {
        this.brokerNameList = brokerNameList;
    }

    public FbsBrPayment getFbsBrPayment() {
        return fbsBrPayment;
    }

    public void setFbsBrPayment(FbsBrPayment fbsBrPayment) {
        this.fbsBrPayment = fbsBrPayment;
    }

    public ArrayList getUnitNameList() {
        return unitNameList;
    }

    public void setUnitNameList(ArrayList unitNameList) {
        this.unitNameList = unitNameList;
    }

    public FbsBrPayment getFbsBrPayment1() {
        return fbsBrPayment1;
    }

    public void setFbsBrPayment1(FbsBrPayment fbsBrPayment1) {
        this.fbsBrPayment1 = fbsBrPayment1;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(int brokerId) {
        this.brokerId = brokerId;
    }

    public int getFlatId() {
        return flatId;
    }

    public void setFlatId(int flatId) {
        this.flatId = flatId;
    }

    public int getTotalpayoutstanding() {
        return totalpayoutstanding;
    }

    public void setTotalpayoutstanding(int totalpayoutstanding) {
        this.totalpayoutstanding = totalpayoutstanding;
    }
}
