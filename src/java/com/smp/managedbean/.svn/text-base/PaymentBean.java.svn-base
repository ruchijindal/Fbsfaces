/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.managedbean;

import com.smp.entity.FbsBank;
import com.smp.entity.FbsBooking;
import com.smp.entity.FbsPayment;
import com.smp.report.PaymentReceipt;
import com.smp.session.FbsBankFacade;
import com.smp.session.FbsBookingFacade;
import com.smp.session.FbsPaymentFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.primefaces.event.SelectEvent;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.smp.entity.FbsApplicant;
import com.smp.entity.FbsBlock;
import com.smp.entity.FbsCharge;
import com.smp.entity.FbsDiscount;
import com.smp.entity.FbsFlat;
import com.smp.entity.FbsFlatType;
import com.smp.entity.FbsParking;
import com.smp.entity.FbsParkingAllot;
import com.smp.entity.FbsParkingType;
import com.smp.entity.FbsPlanname;
import com.smp.entity.FbsPlc;
import com.smp.entity.FbsProject;
import com.smp.entity.FbsServicetax;
import com.smp.session.FbsApplicantFacade;
import com.smp.session.FbsBlockFacade;
import com.smp.session.FbsDiscountFacade;
import com.smp.session.FbsFlatTypeFacade;
import com.smp.session.FbsParkingTypeFacade;
import com.smp.session.FbsPlannameFacade;
import com.smp.session.FbsPlcFacade;
import com.smp.session.FbsProjectFacade;
import com.smp.session.FbsServicetaxFacade;
import java.io.IOException;

import java.util.Date;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author smp7
 */
@ManagedBean(name = "paymentBean")
@Stateful
@SessionScoped
public class PaymentBean implements Serializable {

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
    FbsProjectFacade fbsprojectFacade;
    @EJB
    FlatMasterBean flatMasterBean;
    @EJB
    FbsServicetaxFacade fbsServicetaxFacade;
    @EJB
    FbsFlatTypeFacade fbsFlatTypeFacade;
    @EJB
    FbsDiscountFacade fbsDiscountFacade;
    @EJB
    FbsPlannameFacade fbsPlannameFacade;
    @EJB
    FbsPlcFacade fbsPlcFacade;
    @EJB
    FbsParkingTypeFacade fbsParkingTypeFacade;
    @EJB
    FbsApplicantFacade fbsApplicantFacade;
    public FbsBlock fbsBlock = new FbsBlock();
    public FbsProject fbsProject = new FbsProject();
    public FbsPayment fbsPayment = new FbsPayment();
    public FbsApplicant fbsApplicant = new FbsApplicant();
    public FbsApplicant fbsCoApplicant = new FbsApplicant();
    public static List<FbsPayment> paymentList = new ArrayList();
    public static List<FbsBank> bankList = new ArrayList();
    public FbsBooking fbsBooking = new FbsBooking();
    public ArrayList bankNames;
    private String bankName;
    private String paymentMode;
    public boolean render = false;
    public boolean render1 = false;
    private String status;
    private Date clearingDate;
    public FbsPayment obj;
    private String clearingBankId;
    private String[] bookingFlatList;
    private String[] bookingFlatList1;
    private String chequeNo;
    private Date chequeDate;
    private int chequeAmt;
    private String drawnOn;
    private String transactionId;
    String projId = "";
    String companyId;
    private List<FbsBooking> fbsBookings = new ArrayList<FbsBooking>();
    private List<FbsBlock> fbsBlockList = new ArrayList<FbsBlock>();
    private List<FbsBlock> fbsBlockList1 = new ArrayList<FbsBlock>();
    private List<FbsProject> fbsProjectList = new ArrayList<FbsProject>();
    public PaymentReceipt paymentReceipt = new PaymentReceipt();
    private static Font catFont = new Font(Font.TIMES_ROMAN, 18, Font.BOLD);
    private static Font redFont = new Font(Font.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
    private static Font blackFont = new Font(Font.TIMES_ROMAN, 8, Font.NORMAL, BaseColor.BLACK);
    private static Font subFont = new Font(Font.TIMES_ROMAN, 16, Font.BOLD);
    private static Font smallBold = new Font(Font.TIMES_ROMAN, 8, Font.BOLD);
    private static Font blackSmallFont = new Font(Font.TIMES_ROMAN, 6, Font.NORMAL, BaseColor.BLACK);
    private ArrayList blockNameList = new ArrayList();
    private ArrayList blockNameList1 = new ArrayList();
    private ArrayList unitNameList = new ArrayList();
    private ArrayList projectNameList = new ArrayList();
    public String remark;
    String viewStatus;
    public FbsPayment fbsPayment1 = new FbsPayment();
    public FbsPayment paymentDetail = new FbsPayment();
    public FbsServicetax fbsServiceTax = new FbsServicetax();
    public List<FbsServicetax> servicetaxList = new ArrayList<FbsServicetax>();
    Double totalpayoutstanding = 0.0;
    public FbsBooking fbsBooking1 = new FbsBooking();
    private List<FbsFlat> fbsFlatList = new ArrayList<FbsFlat>();
    String xmlFile = "";
    List<FbsFlat> refFlatList = new ArrayList<FbsFlat>();
    public FbsFlatType fbsFlatType = new FbsFlatType();
    Double totalPaidAmount = 0.0;
    float totalPayableAmount = 0;
    float totalAmount = 0;
    int discountPercantadge = 0;
    int discountId = 0;
    public FbsDiscount fbsDiscount = new FbsDiscount();
    public List<FbsPayment> fbsPaymentList = new ArrayList<FbsPayment>();
    public FbsServicetax fbsServicetax = new FbsServicetax();
    DecimalFormat format = new DecimalFormat("0.00");
    public FbsPlanname fbsPlanname = new FbsPlanname();
    List<String> plcidlist = new ArrayList<String>();
    static  FbsPayment fbsPaymentdel=new FbsPayment();
 
    @PostConstruct
    public void populate() {
fbsPayment.setPaymentMode("Cash");
        paymentList = em.createNamedQuery("FbsPayment.findAll").getResultList();
        if (paymentList.size() <= 5) {
            viewStatus = "View";

        } else {
            viewStatus = "View More";

        }
        bankList = fbsBankFacade.findAll();
        bankNames = new ArrayList();
        for (int i = 0; i < bankList.size(); i++) {
            bankNames.add(new SelectItem(bankList.get(i).getBankId(), bankList.get(i).getBankName()));
        }


    }

    public PaymentBean() {
    }

    public void payment() {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);
        projId = (String) session.getAttribute("projId");
//fbsPayment.setPaymentMode("Cash");
//fbsPayment1.setPaymentMode("Cash");
        if (projId != null) {
            fbsBlockList.clear();
            blockNameList.clear();
            fbsBlockList = em.createNamedQuery("FbsBlock.findByFkProjId").setParameter("fkProjId", Long.parseLong(projId)).getResultList();
            for (int i = 0; i < fbsBlockList.size(); i++) {
                blockNameList.add(new SelectItem(fbsBlockList.get(i).getBlockId(), fbsBlockList.get(i).getBlockName()));
            }
        }
    }

    public void project() {
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        ExternalContext externalContext = facesContext.getExternalContext();
//        HttpSession session = (HttpSession) externalContext.getSession(false);
//        companyId = session.getAttribute("companyId").toString();
        companyId = LoginBean.fbsLogin.getCompanyId().toString();
        fbsProjectList.clear();
        projectNameList.clear();
        System.out.println("company id++++++ " + companyId);
        fbsProjectList = em.createNamedQuery("FbsProject.findByCompanyId").setParameter("companyId", Integer.parseInt(companyId)).getResultList();
        for (int a = 0; a < fbsProjectList.size(); a++) {
            projectNameList.add(new SelectItem(fbsProjectList.get(a).getProjId(), fbsProjectList.get(a).getProjName()));
        }
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

    public ArrayList getProjectNameList() {
        return projectNameList;
    }

    public void setProjectNameList(ArrayList projectNameList) {
        this.projectNameList = projectNameList;
    }

    public void populateBlocks() {
        fbsBlockList1.clear();
        blockNameList1.clear();
        fbsBlockList1 = em.createNamedQuery("FbsBlock.findByFkProjId").setParameter("fkProjId", fbsProject.getProjId()).getResultList();
        for (int i = 0; i < fbsBlockList1.size(); i++) {
            blockNameList1.add(new SelectItem(fbsBlockList1.get(i).getBlockId(), fbsBlockList1.get(i).getBlockName()));
        }
    }

    public void populateFlats() {
        fbsBookings.clear();
        unitNameList.clear();
        fbsBookings = em.createNamedQuery("FbsBooking.findByBlockId").setParameter("blockId", fbsBlock.getBlockId()).getResultList();

        for (int i = 0; i < fbsBookings.size(); i++) {
            unitNameList.add(new SelectItem(fbsBookings.get(i).getFlatId()));
        }
    }

    public void showAmount() {
        System.out.println("in show Amount method....>" + fbsPayment.getUnitCode());
        totalpayoutstanding = findAmount(fbsPayment.getUnitCode());
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Payable amount should be " + format.format(totalpayoutstanding) + " .(excluding servicetax)"));
    }

    public double findAmount(String flatId) {
        plcidlist.clear();

        System.out.println("in find Amount method....>" + fbsPayment.getUnitCode());
        fbsBooking1 = (FbsBooking) em.createNamedQuery("FbsBooking.findByFlatId").setParameter("flatId", Integer.parseInt(flatId)).getResultList().get(0);
        try {

            fbsBlock = fbsBlockFacade.find(fbsBooking1.getBlockId());
            fbsPlanname = fbsPlannameFacade.find(fbsBooking1.getPlanId());
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
                        if (flatElement.getAttribute("flat_id").equals(flatId)) {

                            NodeList plcList = flatElement.getElementsByTagName("plc");
                            //System.out.println("hello smp"+ plcList.getLength());
                            if (plcList.getLength() != 0) {
                                Node plcElement = (Node) plcList.item(0);

                                NodeList plcChilds = plcElement.getChildNodes();
                                //  System.out.println("hello smp"+ plcChilds.getLength());
                                for (int a = 0; a < plcChilds.getLength(); a++) {
                                    Node plcchildnode = plcChilds.item(a);
                                    if (plcchildnode.getNodeType() == Node.ELEMENT_NODE) {
                                        org.w3c.dom.Element plcelement = (org.w3c.dom.Element) plcchildnode;
                                        //   System.out.println("plc child "+plcelement.getTextContent());
                                        //   System.out.println("plc child11111 "+plcchildnode.getNodeName().toString());
                                        plcidlist.add(plcelement.getTextContent());
                                        // System.out.println(a+"listing......"+plcelement.getTextContent());
                                    }
                                }
                            }
                        }
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
                    //  System.out.println("inside if====>");

                    fbsFlatType = fbsFlatTypeFacade.find(Integer.parseInt(fbsFlatList.get(b).getFlatType()));

                    //   fbsBrPaymentlist = em.createNamedQuery("FbsBrPayment.findByFkFlatId").setParameter("fkFlatId", fbsBookingList.getFlatId()).getResultList();
                    totalPaidAmount = 0.0;
//                    for (int i = 0; i < fbsBrPaymentlist.size(); i++) {
//                        totalPaidAmount = (float) totalPaidAmount + fbsBrPaymentlist.get(i).getAmount();
//                    }

//                   fbsBooking1 = (FbsBooking) em.createNamedQuery("FbsBooking.findByFlatId").setParameter("flatId", fbsBookingList.getFlatId()).getResultList().get(0);

                    discountId = fbsBooking1.getDiscountId();
                    fbsDiscount = fbsDiscountFacade.find(discountId);
                    discountPercantadge = fbsDiscount.getPercentage();
                    int planDiscount = 0;
                    planDiscount = fbsPlanname.getDiscount();

                    int br = fbsFlatType.getFlatBsp();
                    int sba = fbsFlatType.getFlatSba();

                    long charge = 0;
                    for (int i = 0; i < plcidlist.size(); i++) {
                        //   System.out.println("plcidlist...>" + plcidlist.size());
                        FbsPlc fbsPlc = fbsPlcFacade.find(Integer.parseInt(plcidlist.get(i).trim()));
                        charge = charge + fbsPlc.getPlcCharge() * sba;

                    }
                    long otherCharge = 0;
                    List<FbsCharge> fbsCharge = em.createNamedQuery("FbsCharge.findByFkProjId").setParameter("fkProjId", String.valueOf(fbsBlock.getFkProjId())).getResultList();
                    for (int i = 0; i < fbsCharge.size(); i++) {
                        otherCharge = otherCharge + fbsCharge.get(i).getAmount() * sba;
                    }
                    long parkingCharge = 0;
                    List<FbsParkingAllot> fbsParkingAllots = em.createNamedQuery("FbsParkingAllot.findByFkFlatId").setParameter("fkFlatId", Integer.parseInt(flatId)).getResultList();
                    for (int i = 0; i < fbsParkingAllots.size(); i++) {
                        FbsParkingType fbsParkingType = fbsParkingTypeFacade.find(fbsParkingAllots.get(i).getParkingTypeId());
                        parkingCharge = parkingCharge + fbsParkingType.getParkingCharge();
                    }
                    //  System.out.println("amt to    b paid....>>>>>" + parkingCharge);
                    //  System.out.println("amt to    b paid....>>>>>" + charge);
                    //  System.out.println("amt to    b paid....>>>>>" + otherCharge);

                    int bsp;
//                    int br_commisson;
                    if (br != 0 && sba != 0) {
                        bsp = sba * br;
                        //   System.out.println("amt tob paid...."+sba * br);
                        bsp = bsp - ((discountPercantadge * bsp) / 100) - ((planDiscount * bsp) / 100);
                        //   System.out.println("amt to  b paid...>>>>>>>."+bsp);


                    } else {
                        bsp = 0;
                    }


//                    br_commisson = (bsp * commission) / 100;
                    // System.out.println("bsp===>" + bsp );

                    fbsPaymentList = em.createNamedQuery("FbsPayment.findByUnitCode").setParameter("unitCode", fbsFlatList.get(b).getFlatId().toString()).getResultList();

                    totalAmount = 0;
                    totalPaidAmount = 0.0;
                    for (int j = 0; j < fbsPaymentList.size(); j++) {

                        totalAmount = fbsPaymentList.get(j).getPaidAmount();
                        //    System.out.println("servicetaxID.............."+fbsPaymentList.get(j).getServiceTax());
                        fbsServiceTax = fbsServicetaxFacade.find(fbsPaymentList.get(j).getServiceTax());
                        //    System.out.println("servicetax..?????????????/"+fbsServiceTax.getServicetax());
                        // totalAmount=totalPaidAmount+(totalPaidAmount*fbsServicetax.getServicetax()/100);
                        Double temp = 1 + ((double) fbsServiceTax.getServicetax()) / 100;
                        //   System.out.println("total Amount......."+totalAmount);
                        //   System.out.println("temp value........"+temp);
                        totalPaidAmount = totalPaidAmount + totalAmount / temp;
                        //   System.out.println("total amount====>" + totalPaidAmount);
//                         format.format(totalPaidAmount);

                    }

                    //   System.out.println("total amount====>" + totalPaidAmount);


                    totalpayoutstanding = ((int) bsp + parkingCharge + charge + otherCharge - totalPaidAmount);
//                     format.format(totalpayoutstanding);
                    break;
                }

            }

        } catch (Exception ex) {
            Logger.getLogger(QuickBookingBean.class.getName()).log(Level.SEVERE, null, ex);
        }


        return totalpayoutstanding;


    }

    public List<FbsBooking> getFbsBookings() {
        return fbsBookings;
    }

    public void setFbsBookings(List<FbsBooking> fbsBookings) {
        this.fbsBookings = fbsBookings;
    }

    public ArrayList getUnitNameList() {
        return unitNameList;
    }

    public void setUnitNameList(ArrayList unitNameList) {
        this.unitNameList = unitNameList;
    }

    public String getBankName() {
        return this.bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String findBankName(String bankId) {

        bankName = "----";
        if (!bankId.equals("")) {
            bankList = em.createNamedQuery("FbsBank.findByBankId").setParameter("bankId", Integer.parseInt(bankId)).getResultList();
            bankName = bankList.get(0).getBankName();
            return bankName;
        }
        return bankName;
    }

    public Date getNow() {
        return (new Date());
    }

    public void addPayment() {
        fbsPayment.setChequeStatus("Pending");
        servicetaxList = fbsServicetaxFacade.findAll();
        for (int i = 0; i < servicetaxList.size(); i++) {
//            System.out.println("fbspayment paymentdate"+this.fbsPayment.getPaymentDate());
//             System.out.println("servicetax before if"+servicetaxList.get(i).getStDate());
            if (this.fbsPayment.getPaymentDate().equals(servicetaxList.get(i).getStDate()) || this.fbsPayment.getPaymentDate().after(servicetaxList.get(i).getStDate()) && this.fbsPayment.getPaymentDate().before(servicetaxList.get(i).getEndDate())) {
                fbsPayment.setServiceTax(servicetaxList.get(i).getStId());
//            System.out.println("servicetax"+servicetaxList.get(i).getStId());
                break;
            } else {
                fbsPayment.setServiceTax(null);
            }
        }
        //   System.out.println("+++++++-->"+this.fbsPayment.getPaymentMode());

        if (this.fbsPayment.getPaymentMode().equals("Cheque")) {
            fbsPayment.setChequeAmt(Long.valueOf(chequeAmt));
            fbsPayment.setChequeDate(chequeDate);
            fbsPayment.setChequeNo(chequeNo);
            fbsPayment.setDrawnOn(drawnOn);
        }
        if (this.fbsPayment.getPaymentMode().equals("NEFT")) {

            fbsPayment.setTransactionId(transactionId);
            fbsPayment.setDrawnOn(drawnOn);
        }
        fbsPayment.setUserId(LoginBean.fbsLogin.getUserId());
        fbsPayment.setBlockId(fbsBlock.getBlockId());
        fbsPaymentFacade.create(fbsPayment);
        fbsPayment = new FbsPayment();
        fbsPayment.setPaymentMode("Cash");
        blockNameList.clear();
        projectNameList.clear();
        blockNameList1.clear();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Congrates! Payment Successfully Added"));
        paymentList = em.createNamedQuery("FbsPayment.findAll").getResultList();
        this.chequeAmt = 0;
        this.chequeDate = null;
        this.chequeNo = "";
        this.drawnOn = "";
        fbsPayment.setPaidAmount(null);
        fbsPayment.setRemark("");
        fbsPayment.setPaymentDate(null);
        this.transactionId = null;

        fbsPayment.setUnitCode("");
        fbsPayment.setPaymentMode("Cash");

    }

    public void addDirectPayment(int blockId, String unitCode) throws IOException {
        fbsPayment.setChequeStatus("Pending");
        System.out.println("+++++++-->" + this.fbsPayment.getPaymentMode());
        servicetaxList = fbsServicetaxFacade.findAll();
        for (int i = 0; i < servicetaxList.size(); i++) {
//            System.out.println("fbspayment paymentdate"+this.fbsPayment.getPaymentDate());
//             System.out.println("servicetax before if"+servicetaxList.get(i).getStDate());
            if (this.fbsPayment.getPaymentDate().equals(servicetaxList.get(i).getStDate()) || this.fbsPayment.getPaymentDate().after(servicetaxList.get(i).getStDate()) && this.fbsPayment.getPaymentDate().before(servicetaxList.get(i).getEndDate())) {
                fbsPayment.setServiceTax(servicetaxList.get(i).getStId());
//            System.out.println("servicetax"+servicetaxList.get(i).getStId());
                break;
            } else {
                fbsPayment.setServiceTax(null);
            }
        }
        if (this.fbsPayment.getPaymentMode().equals("Cheque")) {
            fbsPayment.setChequeAmt(Long.valueOf(chequeAmt));
            fbsPayment.setChequeDate(chequeDate);
            fbsPayment.setChequeNo(chequeNo);
            fbsPayment.setDrawnOn(drawnOn);
        }
        if (this.fbsPayment.getPaymentMode().equals("NEFT")) {

            fbsPayment.setTransactionId(transactionId);
            fbsPayment.setDrawnOn(drawnOn);
        }
        fbsPayment.setUserId(LoginBean.fbsLogin.getUserId());
        fbsPayment.setBlockId(blockId);
        fbsPayment.setUnitCode(unitCode);
        fbsPaymentFacade.create(fbsPayment);
        fbsPayment = new FbsPayment();
        blockNameList.clear();
        projectNameList.clear();
        blockNameList1.clear();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Congrates! Payment Successfully Added"));
        paymentList = em.createNamedQuery("FbsPayment.findAll").getResultList();
        this.chequeAmt = 0;
        this.chequeDate = null;
        this.chequeNo = "";
        this.drawnOn = "";
        fbsPayment.setPaidAmount(null);
        fbsPayment.setRemark("");
        fbsPayment.setPaymentDate(null);
        this.transactionId = null;

        fbsPayment.setUnitCode("");

        flatMasterBean.find(unitCode);
    }

    public void editInformation(FbsApplicant fbsApplicant) throws IOException {
        String ApplicantId=fbsApplicant.getApplicantId().toString();

    //fbsApplicant=fbsApplicantFacade.find(Integer.parseInt(applicantId));
    System.out.println("in edit info"+ApplicantId);

        fbsApplicantFacade.edit(fbsApplicant);

        fbsApplicant = new FbsApplicant();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Congrates! Information Sucessfully Updated"));

    }

    public void editInformation1(FbsApplicant fbsCoApplicant) throws IOException {
        String ApplicantId=fbsApplicant.getApplicantId().toString();

    //fbsApplicant=fbsApplicantFacade.find(Integer.parseInt(applicantId));
    System.out.println("in edit info"+ApplicantId);

        fbsApplicantFacade.edit(fbsCoApplicant);

        fbsApplicant = new FbsApplicant();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Congrates! Information Sucessfully Edit"));

    }

public String managePaymentDate(Date date1)
    {
    String date="";

    SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
    if(date1==null)
    {
            date="----";
    }else{

         date=sdf.format(date1);
    }
    return date;
}
    public void editPayment(org.primefaces.event.RowEditEvent e) {

        fbsPaymentFacade.edit((FbsPayment) e.getObject());
    }
    public void delPayment(FbsPayment fbsPayment)
    {
        fbsPaymentdel=fbsPayment;
    }

    public void deletePayment(String requestType) throws IOException {
        fbsPaymentFacade.remove(fbsPaymentdel);
        paymentList.clear();
        paymentList = em.createNamedQuery("FbsPayment.findAll").getResultList();
        if(requestType.equals("company"))
        {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/Payment/companyQuickPayment.xhtml");
        }
 else{
            if(requestType.equals("project"))
       FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/Payment/QuickPayment.xhtml");
 }

    }

    public void renderChequeDetails() {
        if (this.fbsPayment.getPaymentMode().equals("Cheque")) {
            this.render = true;
            this.render1 = false;
        } else if (this.fbsPayment.getPaymentMode().equals("Cash")) {
            this.render = false;
            this.render1 = false;
        } else if (this.fbsPayment.getPaymentMode().equals("NEFT")) {
            this.render = false;
            this.render1 = true;

        }

    }

    public void putStatus() {

        String userId = LoginBean.fbsLogin.getUserId();

        obj.setChequeStatus(this.status);
        obj.setClearingDt(this.clearingDate);
        if (this.status.equals("Cleared")) {
            obj.setClearingBankId(Integer.parseInt(this.clearingBankId));

            obj.setAuthorizedBy(userId);
        }
        fbsPaymentFacade.edit(obj);
        populate();
    }

    public void SetObject(FbsPayment fbsPayment) {
        obj = fbsPayment;
        clearingDate = new Date();
    }

//    public void populateUnitName() {
//        fbsBookings = fbsBookingFacade.findAll();
//        bookingFlatList = new String[fbsBookings.size()];
//        for (int i = 0; i < fbsBookings.size(); i++) {
//            bookingFlatList[i] = fbsBookings.get(i).getFlatId().toString();
//        }
//    }
    public void genrateReceipt(FbsPayment fbsPayment) throws DocumentException, IOException {
        //fbsPayment = (FbsPayment) event.getObject();
        Integer paymentId = fbsPayment.getPaymentId();
        String unitCode = fbsPayment.getUnitCode();
        Integer companyId1 = LoginBean.fbsLogin.getCompanyId();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/paymentReceipt?paymentId=" + paymentId + "&unitCode=" + unitCode + "&companyId=" + companyId1 + "");
    }

    public boolean setDisableAuthrize(String paymentMode, String chequeStatus) {
        if (paymentMode.equals("cash") || chequeStatus.equals("Cleared")) {
            return true;
        }
        return false;
    }

    public void SetObj(FbsPayment fbsPayment) {
        remark = fbsPayment.getRemark();
        fbsPayment1 = fbsPayment;
        System.out.println("****remark***" + remark);

    }

    public void edit() {
        fbsPayment1.setRemark(remark);
        fbsPaymentFacade.edit((fbsPayment1));
        fbsPayment1 = new FbsPayment();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Congrates! Complaint Successfully Updated"));
        populate();

    }

    public void onRowSelect(SelectEvent event) {
        paymentDetail = (FbsPayment) event.getObject();
    }

    public FbsPayment getFbsPayment1() {
        return fbsPayment1;
    }

    public void setFbsPayment1(FbsPayment fbsPayment1) {
        this.fbsPayment1 = fbsPayment1;
    }

    public FbsPayment getFbsPayment() {
        return fbsPayment;
    }

    public void setFbsPayment(FbsPayment fbsPayment) {
        this.fbsPayment = fbsPayment;
    }

    public void setPaymentList(ArrayList<FbsPayment> paymentList) {
        this.paymentList = paymentList;
    }

    public List<FbsPayment> getPaymentList() {
        for(int i=0;i<paymentList.size();i++)
        {
           if(paymentList.get(i).getChequeNo()==null||paymentList.get(i).getChequeNo().equals(""))
                paymentList.get(i).setChequeNo("----");
           if(paymentList.get(i).getDrawnOn()==null||paymentList.get(i).getDrawnOn().equals(""))
                paymentList.get(i).setDrawnOn("----");
           if(paymentList.get(i).getTransactionId()==null||paymentList.get(i).getTransactionId().equals(""))
                paymentList.get(i).setTransactionId("----");            
         if(paymentList.get(i).getAuthorizedBy()==null||paymentList.get(i).getAuthorizedBy().equals(""))
                paymentList.get(i).setAuthorizedBy("----");
          
        }
        return paymentList;
    }

    public void SetPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
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

    public void setObj(FbsPayment obj) {
        this.obj = obj;
    }

    public FbsPayment getObj() {
        return obj;
    }

    public void setClearingDate(Date clearingDate) {
        this.clearingDate = clearingDate;
    }

    public Date getClearingDate() {
        return clearingDate;
    }

    public String getClearingBankId() {
        return clearingBankId;
    }

    public void setClearingBankId(String clearingBankId) {
        this.clearingBankId = clearingBankId;
    }

    public ArrayList getBankNames() {
        return bankNames;
    }

    public void setBankNames(ArrayList bankNames) {
        this.bankNames = bankNames;
    }

    public String[] getBookingFlatList() {
        return bookingFlatList;
    }

    public void setBookingFlatList(String[] bookingFlatList) {
        this.bookingFlatList = bookingFlatList;
    }

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

    public int getChequeAmt() {
        return chequeAmt;
    }

    public void setChequeAmt(int chequeAmt) {
        this.chequeAmt = chequeAmt;
    }

    public void setFbsBlockList(List<FbsBlock> fbsBlockList) {
        this.fbsBlockList = fbsBlockList;
    }

    public List<FbsBlock> getFbsBlockList() {
        return fbsBlockList;
    }

    public ArrayList getBlockNameList() {
        return blockNameList;
    }

    public void setBlockNameList(ArrayList blockNameList) {
        this.blockNameList = blockNameList;
    }

    public FbsBlock getFbsBlock() {
        return fbsBlock;
    }

    public void setFbsBlock(FbsBlock fbsBlock) {
        this.fbsBlock = fbsBlock;
    }

    public ArrayList getBlockNameList1() {
        return blockNameList1;
    }

    public void setBlockNameList1(ArrayList blockNameList1) {
        this.blockNameList1 = blockNameList1;
    }

    public List<FbsBlock> getFbsBlockList1() {
        return fbsBlockList1;
    }

    public void setFbsBlockList1(List<FbsBlock> fbsBlockList1) {
        this.fbsBlockList1 = fbsBlockList1;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public FbsPayment getPaymentDetail() {
        return paymentDetail;
    }

    public void setPaymentDetail(FbsPayment paymentDetail) {
        this.paymentDetail = paymentDetail;
    }

    public List<FbsServicetax> getServicetaxList() {
        return servicetaxList;
    }

    public void setServicetaxList(List<FbsServicetax> servicetaxList) {
        this.servicetaxList = servicetaxList;
    }

    public void setTotalpayoutstanding(Double totalpayoutstanding) {
        this.totalpayoutstanding = totalpayoutstanding;
    }

    public Double getTotalpayoutstanding() {
        return totalpayoutstanding;
    }
}
