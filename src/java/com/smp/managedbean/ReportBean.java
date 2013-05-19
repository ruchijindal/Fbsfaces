/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.managedbean;

import com.itextpdf.text.DocumentException;
import com.smp.entity.FbsApplicant;
import com.smp.entity.FbsBlock;
import com.smp.entity.FbsBooking;
import com.smp.entity.FbsBroker;
import com.smp.entity.FbsCharge;
import com.smp.entity.FbsDiscount;
import com.smp.entity.FbsFlat;
import com.smp.entity.FbsFlatType;
import com.smp.entity.FbsParkingAllot;
import com.smp.entity.FbsParkingType;
import com.smp.entity.FbsPayment;
import com.smp.entity.FbsPlanname;
import com.smp.entity.FbsPlc;
import com.smp.entity.FbsProject;
import com.smp.fbs.FlatInfo;
import com.smp.fbs.ParkingInfo;
import com.smp.session.FbsApplicantFacade;
import com.smp.session.FbsBlockFacade;
import com.smp.session.FbsBookingFacade;
import com.smp.session.FbsProjectFacade;
import com.smp.session.FbsBrokerFacade;
import com.smp.session.FbsFlatTypeFacade;
import com.smp.session.FbsParkingAllotFacade;
import com.smp.session.FbsParkingTypeFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

/**
 *
 * @author smp7
 */
@ManagedBean(name = "reportBean")
@SessionScoped
@Stateless
public class ReportBean implements Serializable {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
//    @EJB
//    FlatMasterBean flatMaster;
    @EJB
    FbsFlatTypeFacade fbsFlatTypeFacade;
    @EJB
    FbsProjectFacade fbsProjectFacade;
    @EJB
    FbsBlockFacade fbsBlockFacade;
    @EJB
    FbsApplicantFacade fbsApplicantFacade;
    @EJB
    FbsBrokerFacade fbsBrokerFacade;
    @EJB
    FbsBookingFacade fbsBookingFacade;
    @EJB
    FlatMasterBean flatMasterBean;
    @EJB
    FbsParkingAllotFacade fbsParkingAllotFacade;
    @EJB
    FbsParkingTypeFacade fbsParkingTypeFacade;
    FbsProject fbsProject;
    FbsBlock fbsBlock;
    FbsApplicant fbsApplicant;
    FbsApplicant coFbsApplicant;
    FbsFlatType fbsFlatType;
    FbsPlc fbsPlc;
    FbsPlanname fbsPlanname;
    FbsBooking fbsBooking;
    FbsDiscount fbsDiscount;
    FbsBroker fbsBroker;
    boolean withInterest = false;
    FbsParkingType fbsParkingType = new FbsParkingType();
    List<FbsParkingAllot> fbsParkingAllotList = new ArrayList<FbsParkingAllot>();
    List<ParkingInfo> parkingInfoList = new ArrayList<ParkingInfo>();
    public int brokercomision = 0;
    public int brokercom = 0;
    public String projectcode = null;
    List<FbsCharge> fbsChargeList = new ArrayList<FbsCharge>();
    List<FbsFlat> fbsFlatList;
    List<FbsPlc> fbsPlcList = new ArrayList<FbsPlc>();
    List<FbsPlanname> fbsPlannameList = new ArrayList<FbsPlanname>();
    List<FbsFlat> refFlatList = new ArrayList<FbsFlat>();
    List<FbsDiscount> fbsDiscountList = new ArrayList<FbsDiscount>();
    List<FbsBroker> fbsBrokerList = new ArrayList<FbsBroker>();
    List<FbsParkingType> fbsParkingTypes = new ArrayList<FbsParkingType>();
    List<FbsBooking> fbsBookingList = new ArrayList<FbsBooking>();
    List<FbsBlock> fbsBlockList = new ArrayList<FbsBlock>();
    public ArrayList projectList;
    public ArrayList blockList = new ArrayList();
    public String[] floorList;
    public ArrayList flatNoList = new ArrayList();
    public ArrayList plcList = new ArrayList();
    public ArrayList plannameList = new ArrayList();
    public String[] discountList;
    public ArrayList brokerList;
    public String floorName;
    private DashboardModel model;
    public int payableAmount = 0;
    String xmlFile = "";
    public int noOfParking = 0;
    boolean render1 = false;
    boolean render2 = false;
    boolean render3 = false;
    boolean render4 = false;
    boolean render5 = false;
    public int parkingType1 = 0;
    public int parkingType2 = 0;
    public int parkingType3 = 0;
    public int parkingType4 = 0;
    public int parkingType5 = 0;
    Integer projIds;
    Integer blockIds;
    Integer flatIds;
    Integer fbsProjId;
    Integer fbsBlockId;
    Integer fbsFlatId;
    String reporttype="false";
    public ArrayList parkingTypeList = new ArrayList();
    public FlatInfo flatInfo;
    private List<FbsPayment> fbsPaymentList = new ArrayList<FbsPayment>();

    public ReportBean() {
        fbsProject = new FbsProject();
        fbsBlock = new FbsBlock();
        fbsApplicant = new FbsApplicant();
        coFbsApplicant = new FbsApplicant();
        fbsFlatType = new FbsFlatType();
        fbsPlc = new FbsPlc();
        fbsPlc.setPlcCharge(Integer.parseInt("0"));
        fbsPlanname = new FbsPlanname();
        fbsBooking = new FbsBooking();
        fbsDiscount = new FbsDiscount();
        fbsDiscount.setPercentage(Integer.parseInt("0"));
        fbsBooking.setBookingDt(new Date());
        fbsBroker = new FbsBroker();

        model = new DefaultDashboardModel();
        DashboardColumn column1 = new DefaultDashboardColumn();
        DashboardColumn column2 = new DefaultDashboardColumn();
        DashboardColumn column3 = new DefaultDashboardColumn();

        column1.addWidget("applicantfile");
        column1.addWidget("brokerwisereport");

        column2.addWidget("panel");


        model.addColumn(column1);
        model.addColumn(column2);
    }

    public void handleReorder(DashboardReorderEvent event) {
        FacesMessage message = new FacesMessage();
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        message.setSummary("Reordered: " + event.getWidgetId());
        message.setDetail("Item index: " + event.getItemIndex() + ", Column index: " + event.getColumnIndex() + ", Sender index: " + event.getSenderColumnIndex());

        addMessage(message);
    }

    @PostConstruct
    public void populate() {
        projectList = new ArrayList();
        for (int i = 0; i < fbsProjectFacade.findAll().size(); i++) {
            projectList.add(new SelectItem(fbsProjectFacade.findAll().get(i).getProjId(), fbsProjectFacade.findAll().get(i).getProjName()));
        }
        brokerList = new ArrayList();
        for (int i = 0; i < fbsBrokerFacade.findAll().size(); i++) {

            brokerList.add(new SelectItem(fbsBrokerFacade.findAll().get(i).getBrokerId(), fbsBrokerFacade.findAll().get(i).getBrName()));
        }
    }

    public void populateBlocks() {
        if (fbsProjId == null) {
            fbsProject = (FbsProject) em.createNamedQuery("FbsProject.findByProjId").setParameter("projId", projIds).getResultList().get(0);
            fbsProjId = null;
            fbsBlockId = null;
            fbsFlatId = null;
        } else {
            fbsProject = (FbsProject) em.createNamedQuery("FbsProject.findByProjId").setParameter("projId", fbsProjId).getResultList().get(0);
            flatIds=null;
            projIds = null;
            blockIds = null;

        }
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(false);
        String projId = fbsProject.getProjId().toString();
        session.setAttribute("projId", projId);
        //int size = em.createNamedQuery("FbsBlock.findByFkProjId").setParameter("fkProjId", fbsProject.getProjId()).getResultList().size();
        fbsBlockList.clear();
        blockList.clear();
        Query query = em.createNamedQuery("FbsBlock.findByProjId&Status");
        query.setParameter("status", "lock");
        query.setParameter("fkProjId", fbsProject.getProjId());
        fbsBlockList = query.getResultList();
        for (int i = 0; i < fbsBlockList.size(); i++) {
            blockList.add(new SelectItem(fbsBlockList.get(i).getBlockId(), fbsBlockList.get(i).getBlockName()));
        }
    }

    public void populateFlats() {
        if (fbsBlockId != null) {
            fbsBlock = (FbsBlock) em.createNamedQuery("FbsBlock.findByBlockId").setParameter("blockId", fbsBlockId).getResultList().get(0);
            blockIds = null;
        } else {
            fbsBlock = (FbsBlock) em.createNamedQuery("FbsBlock.findByBlockId").setParameter("blockId", blockIds).getResultList().get(0);
            fbsBlockId = null;
        }
        try {
            fbsFlatList = new ArrayList();
            int blockId = fbsBlock.getBlockId();
            int l = 0;
            FbsBlock list = fbsBlockFacade.find(blockId);
            xmlFile = list.getXmlFile();
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
                        String flatId = flatElement.getAttribute("flat_id").trim();
                        fbsFlat.setFlatId(Long.parseLong(flatId));
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

        } catch (Exception ex) {
            Logger.getLogger(ReportBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        fbsBookingList = fbsBookingFacade.findAll();
        flatNoList = new ArrayList();
        for (int j = 0; j < fbsFlatList.size(); j++) {
            for (int k = 0; k < fbsBookingList.size(); k++) {
                if (fbsBookingList.get(k).getFlatId().toString().equals(fbsFlatList.get(j).getFlatId().toString())) {
                    flatNoList.add(new SelectItem(fbsFlatList.get(j).getFlatId(), fbsFlatList.get(j).getFlatNo()));
                }


            }
        }
    }

    public void reportServlet() {

        if (fbsFlatId != null) {
            fbsBooking = (FbsBooking) em.createNamedQuery("FbsBooking.findByFlatId").setParameter("flatId", fbsFlatId).getResultList().get(0);
            flatIds = null;
        } else {
            fbsBooking = (FbsBooking) em.createNamedQuery("FbsBooking.findByFlatId").setParameter("flatId", flatIds).getResultList().get(0);
            fbsFlatId = null;
        }
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(false);

        try {
            fbsFlatList = new ArrayList();
            int blockId = fbsBooking.getBlockId();

            int l = 0;
            FbsBlock list = fbsBlockFacade.find(blockId);
            session.setAttribute("blockName", list.getBlockName());
            fbsFlatList = this.refFlatList;

            flatInfo = new FlatInfo();
            for (int a = 0; a < fbsFlatList.size(); a++) {
                if (fbsBooking.getFlatId().toString().equals(fbsFlatList.get(a).getFlatId().toString())) {
                    flatInfo.setFlatId(fbsFlatList.get(a).getFlatId().toString());
                    flatInfo.setFlatNo(fbsFlatList.get(a).getFlatNo());
                    flatInfo.setFlatTypeId(fbsFlatList.get(a).getFlatType());
                    flatInfo.setFloorNo(fbsFlatList.get(a).getFloorNo().toString());
                    session.setAttribute("flatTypeId", flatInfo.getFlatTypeId());
                    session.setAttribute("flatId", flatInfo.getFlatId());
                    session.setAttribute("flatNo", flatInfo.getFlatNo());
                    break;
                }
            }

            FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/Report");
            fbsProjId = null;
            projIds = null;
        } catch (Exception ex) {
            Logger.getLogger(ReportBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void dueReport() {
        fbsBooking = (FbsBooking) em.createNamedQuery("FbsBooking.findByFlatId").setParameter("flatId", flatIds).getResultList().get(0);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(false);

        try {
            fbsFlatList = new ArrayList();
            int blockId = fbsBooking.getBlockId();

            int l = 0;
            FbsBlock list = fbsBlockFacade.find(blockId);
            session.setAttribute("blockName", list.getBlockName());
            fbsFlatList = this.refFlatList;

            flatInfo = new FlatInfo();
            for (int a = 0; a < fbsFlatList.size(); a++) {
                if (fbsBooking.getFlatId().toString().equals(fbsFlatList.get(a).getFlatId().toString())) {
                    flatInfo.setFlatId(fbsFlatList.get(a).getFlatId().toString());
                    flatInfo.setFlatNo(fbsFlatList.get(a).getFlatNo());
                    flatInfo.setFlatTypeId(fbsFlatList.get(a).getFlatType());
                    flatInfo.setFloorNo(fbsFlatList.get(a).getFloorNo().toString());
                    session.setAttribute("flatTypeId", flatInfo.getFlatTypeId());
                    session.setAttribute("flatId", flatInfo.getFlatId());
                    session.setAttribute("flatNo", flatInfo.getFlatNo());
                    session.setAttribute("floorNo", flatInfo.getFloorNo());
                    break;
                }
            }
            session.setAttribute("projId", String.valueOf(fbsProject.getProjId()));
            List<FbsApplicant> fbsApplicants = em.createNamedQuery("FbsApplicant.findByFlatId").setParameter("flatId", fbsBooking.getFlatId()).getResultList();
            Integer companyId1 = LoginBean.fbsLogin.getCompanyId();
            System.out.println("company id is " + companyId1);
               System.out.println("the value of report type is "+reporttype);
            session.setAttribute("applicantId", fbsApplicants.get(0).getApplicantId().toString());

            FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/DueLetter?companyId=" + companyId1+"&interest="+reporttype.trim());
            fbsProjId = null;
            projIds = null;
            reporttype="false";
        } catch (Exception ex) {
            Logger.getLogger(ReportBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void genrateReceipt1() throws IOException {
        System.out.println("broker id is+++++++" + fbsBroker.getBrokerId());
        System.out.println("proj id is+++++++" + fbsProject.getProjId());
        System.out.println("block id is+++++++" + fbsBlock.getBlockId());
        System.out.println("unit code is+++++++" + fbsBooking.getFlatId());
     
        try {
            Integer brokerId = fbsBroker.getBrokerId();
            Integer projId = fbsProject.getProjId();
            Integer blockId = fbsBlock.getBlockId();
            //  flatInfo = this.flatInfo;
            Integer unitCode = fbsBooking.getFlatId();
            // String unitCode = flatInfo.getFlatId();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/masterBrokerReceipt?brokerId=" + brokerId + "&companyId=" + LoginBean.fbsLogin.getCompanyId()
                    + "&projId=" + projId + "&blockId=" + blockId + "&unitCode=" + unitCode + "");
        } catch (Exception ex) {
            Logger.getLogger(ReportBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
//    public void populateCharges() {
//        try {
//            fbsChargeList = (List<FbsCharge>) em.createNamedQuery("FbsCharge.findByFkProjId").setParameter("fkProjId", fbsProject.getProjId().toString()).getResultList();
//            populatePlc();
//            populatePayplan();
//            populateDiscount();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void populatePlc() {
//        fbsPlcList = (List<FbsPlc>) em.createNamedQuery("FbsPlc.findByFkProjId").setParameter("fkProjId", fbsProject.getProjId()).getResultList();
//        plcList.clear();
//        for (int i = 0; i < fbsPlcList.size(); i++) {
//            plcList.add(new SelectItem(fbsPlcList.get(i).getPlcId(), fbsPlcList.get(i).getPlcName()));
//        }
//    }
//
//    public void poupulatePlcCharge() {
//        for (int i = 0; i < fbsPlcList.size(); i++) {
//            if (fbsPlc.getPlcId().toString().equals(fbsPlcList.get(i).getPlcId().toString())) {
//                fbsPlc = fbsPlcList.get(i);
//            }
//        }
//        calculateTotal();
//    }

    public void populateBroker() {
        fbsBrokerList.clear();
        fbsBrokerList = (List<FbsBroker>) em.createNamedQuery("FbsBroker.findByCompanyId").setParameter("companyId", LoginBean.fbsLogin.getCompanyId()).getResultList();
        brokerList = new ArrayList();
        for (int i = 0; i < fbsBrokerList.size(); i++) {
            brokerList.add(new SelectItem(fbsBrokerList.get(i).getBrokerId(), fbsBrokerList.get(i).getBrokerId().toString()));
        }

    }

    public void populateBrokerName() {
        fbsBroker = fbsBrokerFacade.find(this.fbsBroker.getBrokerId());
        //calculateTotal();
    }

    public int commission() {
        System.out.println("in commission");

        //   calculateTotal();
        brokercom = this.brokercomision;
        return brokercom;
    }

//    public String projectCode() {
//        projectcode = this.fbsProject.getProjCode();
//        return projectcode;
//    }
//
//    public void populatePayplan() {
//        fbsPlannameList = em.createNamedQuery("FbsPlanname.findByFkProjId").setParameter("fkProjId", fbsProject.getProjId()).getResultList();
//        plannameList.clear();
//        for (int i = 0; i < fbsPlannameList.size(); i++) {
//            plannameList.add(new SelectItem(fbsPlannameList.get(i).getPlanId(), fbsPlannameList.get(i).getPlanName()));
//        }
//    }
//
//    public void populateDiscount() {
//
//        fbsDiscountList = em.createNamedQuery("FbsDiscount.findByCompanyId").setParameter("companyId", LoginBean.fbsLogin.getCompanyId()).getResultList();
//        discountList = new String[fbsDiscountList.size()];
//        for (int i = 0; i < discountList.length; i++) {
//            discountList[i] = fbsDiscountList.get(i).getDiscountType();
//        }
//    }
//
//    public void setDiscount() {
//        for (int i = 0; i < fbsDiscountList.size(); i++) {
//            if (fbsDiscountList.get(i).getDiscountType().equals(fbsDiscount.getDiscountType())) {
//                fbsDiscount = fbsDiscountList.get(i);
//            }
//        }
//        calculateTotal();
//    }
//
//    public void calculateTotal() {
//        payableAmount = 0;
//
//        payableAmount = (int) (payableAmount + fbsFlatType.getFlatBsp() * fbsFlatType.getFlatSba());
//        for (int i = 0; i < fbsChargeList.size(); i++) {
//            payableAmount = payableAmount + fbsChargeList.get(i).getAmount();
//        }
//        if (fbsPlc.getPlcCharge() != null) {
//            payableAmount = (int) (payableAmount + fbsPlc.getPlcCharge());
//        }
//        // payableAmount = payableAmount + (noOfCoveredParking * coveredParking);
//        //payableAmount = payableAmount + (noOfUnCoveredParking * unCoveredParking);
//        payableAmount = payableAmount - (fbsDiscount.getPercentage() * payableAmount / 100);
//        if (fbsProject.getBrCommission() != null) {
//            brokercomision = fbsProject.getBrCommission() * payableAmount / 100;
//        }
//        System.out.println("calculate broker commission" + brokercomision);
//
//    }
//
//    public void renderParking() {
//        render1 = false;
//        render2 = false;
//        render3 = false;
//        render4 = false;
//        render5 = false;
//        for (int j = 1; j <= this.noOfParking; j++) {
//            if (j == 1) {
//                this.render1 = !this.render1;
//            } else if (j == 2) {
//                this.render2 = !this.render2;
//            } else if (j == 3) {
//                this.render3 = !this.render3;
//            } else if (j == 4) {
//                this.render4 = !this.render4;
//            } else if (j == 5) {
//                this.render5 = !this.render5;
//            }
//        }
//        fbsParkingTypes.clear();
//        parkingTypeList.clear();
//        fbsParkingTypes = em.createNamedQuery("FbsParkingType.findByFkProjId").setParameter("fkProjId", fbsProject.getProjId()).getResultList();
//        for (int j = 0; j < fbsParkingTypes.size(); j++) {
//            parkingTypeList.add(new SelectItem(fbsParkingTypes.get(j).getParkingTypeId(), fbsParkingTypes.get(j).getParkingType()));
//        }
//        calculateParking();
//    }
//
//    public void calculateParking() {
//        ParkingInfo parkingInfo;
//        parkingInfoList.clear();
//        for (int i = 0; i < fbsParkingTypes.size(); i++) {
//            fbsParkingAllotList = em.createNamedQuery("FbsParkingAllot.findByParkingTypeId").setParameter("parkingTypeId", fbsParkingTypes.get(i).getParkingTypeId()).getResultList();
//            parkingInfo = new ParkingInfo();
//            parkingInfo.setParkingTypeId(fbsParkingTypes.get(i).getParkingTypeId());
//            parkingInfo.setParkingType(fbsParkingTypes.get(i).getParkingType());
//            parkingInfo.setAvailParking(fbsParkingTypes.get(i).getNoOfParking() - fbsParkingAllotList.size());
//            parkingInfoList.add(parkingInfo);
//        }
//    }
//
//    /*******************************Upload Image*****************/
//    public void handleFileUpload(FileUploadEvent event) {
//        System.out.println(event.getFile().getFileName());
//    }
//
//    /**************************************************************/
//    /*******************************Booking****************************/
//    public void addBooking() throws IOException {
//
//        fbsBooking.setBlockId(fbsBlock.getBlockId());
//        fbsBooking.setBrokerId(fbsBroker.getBrokerId());
//        fbsBooking.setDiscountId(fbsDiscount.getDiscountId());
//        fbsBooking.setPlcId(fbsPlc.getPlcId());
//        fbsBooking.setStatus("b");
//        fbsBooking.setUserId(LoginBean.fbsLogin.getUserId());
//        fbsBookingFacade.create(fbsBooking);
//        FbsParkingAllot fbsParkingAllot;
//        for (int i = 0; i < this.noOfParking; i++) {
//            fbsParkingAllot = new FbsParkingAllot();
//            fbsParkingAllot.setFkFlatId(fbsBooking.getFlatId());
//            if (i == 0) {
//                fbsParkingAllot.setParkingTypeId(parkingType1);
//                fbsParkingAllotFacade.create(fbsParkingAllot);
//            } else if (i == 1) {
//
//                fbsParkingAllot.setParkingTypeId(parkingType2);
//                fbsParkingAllotFacade.create(fbsParkingAllot);
//            } else if (i == 2) {
//
//                fbsParkingAllot.setParkingTypeId(parkingType3);
//                fbsParkingAllotFacade.create(fbsParkingAllot);
//            } else if (i == 3) {
//
//                fbsParkingAllot.setParkingTypeId(parkingType4);
//                fbsParkingAllotFacade.create(fbsParkingAllot);
//            } else if (i == 4) {
//
//                fbsParkingAllot.setParkingTypeId(parkingType5);
//                fbsParkingAllotFacade.create(fbsParkingAllot);
//            }
//
//        }
//        fbsChargeList.clear();
//        parkingInfoList.clear();
//        fbsParkingTypes.clear();
//        parkingTypeList.clear();
//        fbsParkingTypes = em.createNamedQuery("FbsParkingType.findByFkProjId").setParameter("fkProjId", fbsProject.getProjId()).getResultList();
//        for (int j = 0; j < fbsParkingTypes.size(); j++) {
//            parkingTypeList.add(new SelectItem(fbsParkingTypes.get(j).getParkingTypeId(), fbsParkingTypes.get(j).getParkingType()));
//        }
//        fbsApplicant.setFlatId(fbsBooking.getFlatId());
//        coFbsApplicant.setFlatId(fbsBooking.getFlatId());
//        fbsApplicant.setApplicantFlag(Short.parseShort("1"));
//        coFbsApplicant.setApplicantFlag(Short.parseShort("2"));
//        fbsApplicantFacade.create(fbsApplicant);
//        fbsApplicantFacade.create(coFbsApplicant);
//
//        fbsProject = new FbsProject();
//        fbsBlock = new FbsBlock();
//        fbsApplicant = new FbsApplicant();
//        coFbsApplicant = new FbsApplicant();
//        fbsFlatType = new FbsFlatType();
//        fbsPlc = new FbsPlc();
//        fbsPlanname = new FbsPlanname();
//        //fbsBooking = new FbsBooking();
//        fbsDiscount = new FbsDiscount();
//        //fbsBooking.setBookingDt(new Date());
//        fbsBroker = new FbsBroker();
//
//
//        FacesContext context = FacesContext.getCurrentInstance();
//        context.addMessage(null, new FacesMessage("Congrates! Flat Booked Successfully"));
//
//        flatMasterBean.findFlatMaster(fbsBooking);
//    }
//
    /******************************************************************/
    public FbsProject getFbsProject() {
        return fbsProject;
    }

    public FbsBlock getFbsBlock() {
        return fbsBlock;
    }

    public void setFbsBlock(FbsBlock fbsBlock) {
        this.fbsBlock = fbsBlock;
    }

    public FbsFlatType getFbsFlatType() {
        return this.fbsFlatType;
    }

    public void setFbsFlatType(FbsFlatType fbsFlatType) {
        this.fbsFlatType = fbsFlatType;
    }

    public FbsApplicant getFbsApplicant() {
        return this.fbsApplicant;
    }

    public void setFbsApplicant(FbsApplicant fbsApplicant) {
        this.fbsApplicant = fbsApplicant;
    }

    public FbsApplicant getCoFbsApplicant() {
        return this.coFbsApplicant;
    }

    public void setCoFbsApplicant(FbsApplicant coFbsApplicant) {
        this.coFbsApplicant = coFbsApplicant;
    }

    public List<FbsCharge> getFbsChargeList() {
        return this.fbsChargeList;
    }

    public void setFbsChargeList(List<FbsCharge> fbsChargeList) {
        this.fbsChargeList = fbsChargeList;
    }

    public ArrayList getProjectList() {
        return projectList;
    }

    public void setProjectList(ArrayList projectList) {
        this.projectList = projectList;
    }

    public ArrayList getBlockList() {
        return blockList;
    }

    public void setBlockList(ArrayList blockList) {
        this.blockList = blockList;
    }

    public List<FbsPayment> getFbsPaymentList() {
        return fbsPaymentList;
    }

    public void setFbsPaymentList(List<FbsPayment> fbsPaymentList) {
        this.fbsPaymentList = fbsPaymentList;
    }

    public FlatInfo getFlatInfo() {
        return flatInfo;
    }

    public void setFlatInfo(FlatInfo flatInfo) {
        this.flatInfo = flatInfo;
    }

    public void setFloorList(String[] floorList) {
        this.floorList = floorList;
    }

    public String[] getFloorList() {
        return floorList;
    }

    public ArrayList getFlatNoList() {
        return flatNoList;
    }

    public void setFlatNoList(ArrayList flatNoList) {
        this.flatNoList = flatNoList;
    }

    public void setFbsFlatList(List<FbsFlat> fbsFlatList) {
        this.fbsFlatList = fbsFlatList;
    }

    public List<FbsFlat> getFbsFlatList() {
        return fbsFlatList;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getFloorName() {
        return this.floorName;
    }

    public FbsBooking getFbsBooking() {
        return fbsBooking;
    }

    public void setFbsBooking(FbsBooking fbsBooking) {
        this.fbsBooking = fbsBooking;
    }

    public int getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(int payableAmount) {
        this.payableAmount = payableAmount;
    }

    public ArrayList getPlcList() {
        return plcList;
    }

    public void setPlcList(ArrayList plcList) {
        this.plcList = plcList;
    }

    public FbsPlc getFbsPlc() {
        return fbsPlc;
    }

    public void setFbsPlc(FbsPlc fbsPlc) {
        this.fbsPlc = fbsPlc;
    }

    public List<FbsPlc> getFbsPlcList() {
        return fbsPlcList;
    }

    public void setFbsPlcList(List<FbsPlc> fbsPlcList) {
        this.fbsPlcList = fbsPlcList;
    }

    public FbsPlanname getFbsPlanname() {
        return fbsPlanname;
    }

    public void setFbsPlanname(FbsPlanname fbsPlanname) {
        this.fbsPlanname = fbsPlanname;
    }

    public List<FbsPlanname> getFbsPlannameList() {
        return fbsPlannameList;
    }

    public void setFbsPlannameList(List<FbsPlanname> fbsPlannameList) {
        this.fbsPlannameList = fbsPlannameList;
    }

    public ArrayList getPlannameList() {
        return plannameList;
    }

    public void setPlannameList(ArrayList plannameList) {
        this.plannameList = plannameList;
    }

    public int getNoOfParking() {
        return noOfParking;
    }

    public void setNoOfParking(int noOfParking) {
        this.noOfParking = noOfParking;
    }

    public FbsDiscount getFbsDiscount() {
        return fbsDiscount;
    }

    public void setFbsDiscount(FbsDiscount fbsDiscount) {
        this.fbsDiscount = fbsDiscount;
    }

    public List<FbsDiscount> getFbsDiscountList() {
        return fbsDiscountList;
    }

    public void setFbsDiscountList(List<FbsDiscount> fbsDiscountList) {
        this.fbsDiscountList = fbsDiscountList;
    }

    public String[] getDiscountList() {
        return discountList;
    }

    public void setDiscountList(String[] discountList) {
        this.discountList = discountList;
    }

    public ArrayList getBrokerList() {
        return brokerList;
    }

    public void setBrokerList(ArrayList brokerList) {
        this.brokerList = brokerList;
    }

    public void setFbsBroker(FbsBroker fbsBroker) {
        this.fbsBroker = fbsBroker;
    }

    public FbsBroker getFbsBroker() {
        return fbsBroker;
    }

    public void setRender1(boolean render1) {
        this.render1 = render1;
    }

    public boolean getRender1() {
        return this.render1;
    }

    public void setRender2(boolean render2) {
        this.render2 = render2;
    }

    public boolean getRender2() {
        return this.render2;
    }

    public void setRender3(boolean render3) {
        this.render3 = render3;
    }

    public boolean getRender3() {
        return this.render3;
    }

    public void setRender4(boolean render4) {
        this.render4 = render4;
    }

    public boolean getRender4() {
        return this.render4;
    }

    public void setRender5(boolean render5) {
        this.render5 = render5;
    }

    public boolean getRender5() {
        return this.render5;
    }

    public int getParkingType1() {
        return parkingType1;
    }

    public void setParkingType1(int parkingType1) {
        this.parkingType1 = parkingType1;
    }

    public int getParkingType2() {
        return parkingType2;
    }

    public void setParkingType2(int parkingType2) {
        this.parkingType2 = parkingType2;
    }

    public int getParkingType3() {
        return parkingType3;
    }

    public void setParkingType3(int parkingType3) {
        this.parkingType3 = parkingType3;
    }

    public int getParkingType4() {
        return parkingType4;
    }

    public void setParkingType4(int parkingType4) {
        this.parkingType4 = parkingType4;
    }

    public int getParkingType5() {
        return parkingType5;
    }

    public void setParkingType5(int parkingType5) {
        this.parkingType5 = parkingType5;
    }

    public void setParkingTypeList(ArrayList parkingTypeList) {
        this.parkingTypeList = parkingTypeList;
    }

    public ArrayList getParkingTypeList() {
        return parkingTypeList;
    }

    public void setBrokercomision(int brokercomision) {
        this.brokercomision = brokercomision;
    }

    public int getBrokercomision() {
        return brokercomision;
    }

    public List<ParkingInfo> getParkingInfoList() {
        return parkingInfoList;
    }

    public void setParkingInfoList(List<ParkingInfo> parkingInfoList) {
        this.parkingInfoList = parkingInfoList;
    }

    public int getBrokercom() {
        return brokercom;
    }

    public void setBrokercom(int brokercom) {
        this.brokercom = brokercom;
    }

    public String getProjectcode() {
        return projectcode;
    }

    public void setProjectcode(String projectcode) {
        this.projectcode = projectcode;
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public DashboardModel getModel() {
        return model;
    }

    public void setModel(DashboardModel model) {
        this.model = model;
    }

    public Integer getBlockIds() {
        return blockIds;
    }

    public void setBlockIds(Integer blockIds) {
        this.blockIds = blockIds;
    }

    public Integer getFlatIds() {
        return flatIds;
    }

    public void setFlatIds(Integer flatIds) {
        this.flatIds = flatIds;
    }

    public Integer getProjIds() {
        return projIds;
    }

    public void setProjIds(Integer projIds) {
        this.projIds = projIds;
    }

    public Integer getFbsBlockId() {
        return fbsBlockId;
    }

    public void setFbsBlockId(Integer fbsBlockId) {
        this.fbsBlockId = fbsBlockId;
    }

    public Integer getFbsFlatId() {
        return fbsFlatId;
    }

    public void setFbsFlatId(Integer fbsFlatId) {
        this.fbsFlatId = fbsFlatId;
    }

    public Integer getFbsProjId() {
        return fbsProjId;
    }

    public void setFbsProjId(Integer fbsProjId) {
        this.fbsProjId = fbsProjId;
    }

    public String getReporttype() {
        return reporttype;
    }

    public void setReporttype(String reporttype) {
        this.reporttype = reporttype;
    }
    
}
