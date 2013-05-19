/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.managedbean;

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
import com.smp.fbs.FbsBookingDetail;
import com.smp.fbs.FlatInfo;
import com.smp.fbs.ParkingInfo;
import com.smp.session.FbsApplicantFacade;
import com.smp.session.FbsBlockFacade;
import com.smp.session.FbsBookingFacade;
import com.smp.session.FbsBrokerFacade;
import com.smp.session.FbsFlatTypeFacade;
import com.smp.session.FbsParkingAllotFacade;
import com.smp.session.FbsPaymentFacade;
import com.smp.session.FbsPlcFacade;
import com.smp.session.FbsProjectFacade;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author smp7
 */
@ManagedBean(name = "graphicalSearchBean")
@ApplicationScoped
@Stateful
public class GraphicalSearchBean {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
    @EJB
    FbsProjectFacade fbsProjectFacade;
    @EJB
    FbsBlockFacade fbsBlockFacade;
    @EJB
    FbsBookingFacade fbsBookingFacade;
    @EJB
    FlatMasterBean flatMasterBean;
    public FbsBooking fbsBooking = new FbsBooking();
    public FbsProject fbsProject = new FbsProject();
    public FbsApplicant fbsApplicant = new FbsApplicant();
    public FbsApplicant fbsApplicant1 = new FbsApplicant();
    public static List<FbsProject> projectList = new ArrayList();
    private FbsBlock fbsBlock = new FbsBlock();
    private List<FbsBlock> fbsBlockList = new ArrayList<FbsBlock>();
    private FbsFlat fbsFlat = new FbsFlat();
    private List<FbsFlat> fbsFlatList = new ArrayList<FbsFlat>();
    List<FbsFlat> refFlatList = new ArrayList<FbsFlat>();
    public FbsFlatType fbsFlatType = new FbsFlatType();
    public FbsFlatType fbsFlatType1 = new FbsFlatType();
    private List<FbsApplicant> fbsApplicantList = new ArrayList<FbsApplicant>();
    private List<FbsPayment> fbsPaymentList = new ArrayList<FbsPayment>();
    private List<FbsPayment> fbsPaymentList1 = new ArrayList<FbsPayment>();
    public ArrayList floorList;
    //public ArrayList flatNoList=new ArrayList();
    //public String[] floorList;
    public FlatInfo flatInfo = new FlatInfo();
    public FlatInfo flatInfo1;
    public FlatInfo[] flatNoList;
    public String floorName;
    String xmlFile = "";
    public int brokercom = 0;
    int high = 0;
    @EJB
    FbsFlatTypeFacade fbsFlatTypeFacade;
    @EJB
    FbsPaymentFacade fbsPaymentFacade;
    private FbsApplicant coFbsApplicant = new FbsApplicant();
    private FbsApplicant coFbsApplicant1;
    private List<FbsCharge> fbsChargeList;
    private List<FbsPlc> fbsPlcList;
    ArrayList plcList = new ArrayList();
    private FbsPlc fbsPlc = new FbsPlc();
    private List<FbsBroker> fbsBrokerList = new ArrayList<FbsBroker>();
    private ArrayList brokerList = new ArrayList();
    private FbsBroker fbsBroker = new FbsBroker();
    public int parkingType1 = 0;
    public int parkingType2 = 0;
    public int parkingType3 = 0;
    public int parkingType4 = 0;
    public int parkingType5 = 0;
    @EJB
    private FbsBrokerFacade fbsBrokerFacade;
    private List<FbsPlanname> fbsPlannameList = new ArrayList<FbsPlanname>();
    private ArrayList plannameList = new ArrayList();
    private List<FbsDiscount> fbsDiscountList = new ArrayList<FbsDiscount>();
    private String[] discountList;
    private FbsDiscount fbsDiscount = new FbsDiscount();
    private int payableAmount;
    private int brokercomision;
    public boolean booked = false;
    public boolean unbooked = false;
    private boolean render1;
    private boolean render2;
    private boolean render3;
    private boolean render4;
    private boolean render5;
    private int noOfParking = 0;
    public String flatNo;
    public String floorNo;
    public String flatTypeId;
    private List<FbsParkingType> fbsParkingTypes = new ArrayList<FbsParkingType>();
    private ArrayList parkingTypeList = new ArrayList();
    private List<ParkingInfo> parkingInfoList = new ArrayList<ParkingInfo>();
    private List<FbsParkingAllot> fbsParkingAllotList = new ArrayList<FbsParkingAllot>();
    @EJB
    private FbsParkingAllotFacade fbsParkingAllotFacade;
    @EJB
    private FbsApplicantFacade fbsApplicantFacade;
    private FbsPlanname fbsPlanname = new FbsPlanname();
    FbsBookingDetail bookingDetail = new FbsBookingDetail();
    List<FbsPlc> fbsPlcinfo = new ArrayList<FbsPlc>();
    @EJB
    FbsPlcFacade fbsPlcFacade;

    public GraphicalSearchBean() {
        fbsBooking.setBookingDt(new Date());
        fbsDiscount.setPercentage(0);
        fbsPlc.setPlcCharge(0);
    }

    @PostConstruct
    public void populateProjects() {
        System.out.println("inside populateProject........");
        projectList.clear();
        projectList = fbsProjectFacade.findAll();
    }

    public void populate() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);
        String projId = (String) session.getAttribute("projId");
        System.out.println("project id in graphical search bean populate method " + projId);
        fbsBlockList.clear();
        if (projId != null) {
            fbsProject = fbsProjectFacade.find(Integer.parseInt(projId));
            Query query = em.createNamedQuery("FbsBlock.findByProjId&Status");
            query.setParameter("status", "lock");
            query.setParameter("fkProjId", fbsProject.getProjId());
            fbsBlockList = query.getResultList();
        }
    }

    public void poupulateBlocks(FbsProject fbsProject) throws IOException {
        fbsBlockList.clear();
        Query query = em.createNamedQuery("FbsBlock.findByProjId&Status");
        query.setParameter("status", "lock");
        query.setParameter("fkProjId", fbsProject.getProjId());
        fbsBlockList = query.getResultList();
        this.fbsProject = fbsProject;
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/Project/blockList.xhtml");
    }

    public void poupulateCompanyBlocks(FbsProject fbsProject) throws IOException {
        fbsBlockList.clear();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(false);
        String projId = fbsProject.getProjId().toString();
        session.setAttribute("projId", projId);

        Query query = em.createNamedQuery("FbsBlock.findByProjId&Status");
        query.setParameter("status", "lock");
        query.setParameter("fkProjId", fbsProject.getProjId());
        fbsBlockList = query.getResultList();
        this.fbsProject = fbsProject;

        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/Project/companyBlockList.xhtml");
    }

    public void populateFloors(FbsBlock fbsBlock) throws IOException {
        // fbsBlock = (FbsBlock) em.createNamedQuery("FbsBlock.findByBlockName").setParameter("blockName", fbsBlock.getBlockName()).getResultList().get(0);
        this.fbsBlock = fbsBlock;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);

        try {
            fbsFlatList = new ArrayList();
            String blockId = fbsBlock.getBlockId().toString();
            session.setAttribute("blockId", blockId);
            int l = 0;
            FbsBlock list = fbsBlockFacade.find(Integer.parseInt(blockId));
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
            high = 0;
            for (int a = 0; a < fbsFlatList.size(); a++) {
                if (high < Integer.parseInt(fbsFlatList.get(a).getFloorNo())) {
                    high = Integer.parseInt(fbsFlatList.get(a).getFloorNo());
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(QuickBookingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        populateFlats();
    }

    public void callServlet() {                                      // callServlet add by ashish
        // System.out.print("hnjdhxj");
        try {

            FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/Report");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void populateFlats() throws IOException {
        int l = 0;
        floorList = new ArrayList();
        for (int k = high; k >= 0; k--) {
            l = 0;
            fbsFlatList = new ArrayList<FbsFlat>();
            for (int i = 0; i < this.refFlatList.size(); i++) {
                if (this.refFlatList.get(i).getFloorNo().equals(String.valueOf(k))) {
                    this.fbsFlatList.add(l, this.refFlatList.get(i));
                    l++;
                }
            }
            flatNoList = new FlatInfo[fbsFlatList.size()];
            for (int j = 0; j < fbsFlatList.size(); j++) {
                flatInfo = new FlatInfo();
                flatInfo.setFlatId(fbsFlatList.get(j).getFlatId().toString());
                flatInfo.setFlatNo(fbsFlatList.get(j).getFlatNo());
                flatInfo.setFlatTypeId(fbsFlatList.get(j).getFlatType());
                for (int s = 0; s < fbsBookingFacade.findAll().size(); s++) {
                    if (fbsBookingFacade.findAll().get(s).getFlatId().toString().equals(fbsFlatList.get(j).getFlatId().toString())) {
                        flatInfo.setStatus("b");
                        break;
                    } else {
                        flatInfo.setStatus("u");
                    }

                }
                //flatInfo.setStatus("u");
                flatInfo.setFloorNo(fbsFlatList.get(j).getFloorNo().toString());
                flatNoList[j] = flatInfo;
            }
            floorList.add(flatNoList);
        }
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);
        String projid = (String) session.getAttribute("projId");
        fbsProject = (FbsProject) em.createNamedQuery("FbsProject.findByProjId").setParameter("projId", Integer.parseInt(projid)).getResultList().get(0);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/Project/flatList.xhtml");
    }

    public void populateCompanyFloors(FbsBlock fbsBlock) throws IOException {

        this.fbsBlock = fbsBlock;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);

        try {
            fbsFlatList = new ArrayList();
            String blockId = fbsBlock.getBlockId().toString();
            session.setAttribute("blockId", blockId);

            int l = 0;
            FbsBlock list = fbsBlockFacade.find(Integer.parseInt(blockId));
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
            high = 0;
            for (int a = 0; a < fbsFlatList.size(); a++) {
                if (high < Integer.parseInt(fbsFlatList.get(a).getFloorNo())) {
                    high = Integer.parseInt(fbsFlatList.get(a).getFloorNo());
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(QuickBookingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        populateCompanyFlats();
    }

    public void populateCompanyFlats() throws IOException {
        int l = 0;
        floorList = new ArrayList();
        for (int k = high; k >= 0; k--) {
            l = 0;
            fbsFlatList = new ArrayList<FbsFlat>();
            for (int i = 0; i < this.refFlatList.size(); i++) {
                if (this.refFlatList.get(i).getFloorNo().equals(String.valueOf(k))) {
                    this.fbsFlatList.add(l, this.refFlatList.get(i));
                    l++;
                }
            }
            flatNoList = new FlatInfo[fbsFlatList.size()];
            for (int j = 0; j < fbsFlatList.size(); j++) {
                flatInfo = new FlatInfo();
                flatInfo.setFlatId(fbsFlatList.get(j).getFlatId().toString());
                flatInfo.setFlatNo(fbsFlatList.get(j).getFlatNo());
                flatInfo.setFlatTypeId(fbsFlatList.get(j).getFlatType());
                for (int s = 0; s < fbsBookingFacade.findAll().size(); s++) {
                    if (fbsBookingFacade.findAll().get(s).getFlatId().toString().equals(fbsFlatList.get(j).getFlatId().toString())) {
                        flatInfo.setStatus("b");
                        break;
                    } else {
                        flatInfo.setStatus("u");
                    }

                }
                //flatInfo.setStatus("u");
                flatInfo.setFloorNo(fbsFlatList.get(j).getFloorNo().toString());
                flatNoList[j] = flatInfo;
            }
            floorList.add(flatNoList);
        }
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/Project/companyFlatList.xhtml");
    }

    /****************************Transfer Booking************************************/
    public void transferBooking(FbsBookingDetail fbsBookingDetail) throws IOException {
     bookingDetail=fbsBookingDetail;
//     FbsBooking booking=new FbsBooking();
//     booking=fbsBookingFacade.find(fbsBookingDetail.getRegNo());
//     FbsApplicant applicant=new FbsApplicant();
//     FbsApplicant coApplicant=new FbsApplicant();
//     fbsProject =fbsProjectFacade.find(Integer.valueOf(String.valueOf(fbsBookingDetail.getProjId())));
//     List<FbsParkingAllot> fbsParkingAllots=em.createNamedQuery("FbsParkingAllot.findByFkFlatId").setParameter("fkFlatId", Integer.valueOf(fbsBookingDetail.getFlatId())).getResultList();
//    for(int i=0;i<fbsParkingAllots.size();i++)
//    {
//       fbsParkingAllotFacade.remove(fbsParkingAllots.get(i));
//    }
//     applicant=fbsApplicantFacade.find(fbsBookingDetail.getApplicantId());
//     coApplicant=fbsApplicantFacade.find(fbsBookingDetail.getCoApplicantId());
//    // applicant.setStatus("t");
//    // coApplicant.setStatus("t");
//    // booking.setStatus("t");
//     fbsApplicantFacade.edit(applicant);
//     fbsApplicantFacade.edit(coApplicant);
//     fbsBookingFacade.edit(booking);
//     fbsFlatType=fbsFlatTypeFacade.find(fbsBookingDetail.getFlatTypeId());
        populateCharges();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/Booking/transferCompanyBooking.xhtml");
    }
  public void addViewBooking() throws IOException {
        System.out.println("flat id is+++++++++++++++=" + flatInfo.getFlatId());
//
//        fbsBooking.setFlatId(Integer.parseInt(bookingDetail.getFlatId()));
//
//
//
//
//        fbsBooking.setBlockId(fbsBlock.getBlockId());
//        fbsBooking.setBrokerId(fbsBroker.getBrokerId());
//        fbsBooking.setDiscountId(fbsDiscount.getDiscountId());
//        fbsBooking.setPlcId(fbsPlc.getPlcId());
//        fbsBooking.setStatus("t");
//        fbsBooking.setUserId(LoginBean.fbsLogin.getUserId());
//        fbsBookingFacade.edit(fbsBooking);
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
      // FbsApplicant applicant1=new FbsApplicant();
     //  FbsApplicant applicant2=new FbsApplicant();
       //applicant1=fbsApplicantFacade.find(bookingDetail.getApplicantId());
       //applicant2=fbsApplicantFacade.find(bookingDetail.getCoApplicantId());
        FbsBooking booking=fbsBookingFacade.find(bookingDetail.getRegNo());
        booking.setStatus("t");
        fbsBookingFacade.edit(booking);
        fbsApplicant.setFlatId(booking.getFlatId());
        coFbsApplicant.setFlatId(booking.getFlatId());
        fbsApplicant.setApplicantFlag(Short.parseShort("1"));
        coFbsApplicant.setApplicantFlag(Short.parseShort("2"));
        fbsApplicant.setStatus(String.valueOf(bookingDetail.getApplicantId()));
        fbsApplicant.setTransferDate(new Date());
        coFbsApplicant.setStatus(String.valueOf(bookingDetail.getCoApplicantId()));
        coFbsApplicant.setTransferDate(new Date());
        fbsApplicantFacade.create(fbsApplicant);
        fbsApplicantFacade.create(coFbsApplicant);
        fbsProject = new FbsProject();
        fbsBlock = new FbsBlock();
        fbsApplicant = new FbsApplicant();
        coFbsApplicant = new FbsApplicant();
        fbsFlatType = new FbsFlatType();
        fbsPlc = new FbsPlc();
        fbsPlanname = new FbsPlanname();
        // fbsBooking = new FbsBooking();
        fbsDiscount = new FbsDiscount();
        //fbsBooking.setBookingDt(new Date());
        fbsBroker = new FbsBroker();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Congrates! Flat Booked Successfully"));
        flatMasterBean.findFlatMaster(booking);
    }
    /*************************************************************************************/
    /******************************************************************************/
    public void populateCharges() {
        try {
            fbsChargeList = new ArrayList<FbsCharge>();
            fbsChargeList = (List<FbsCharge>) em.createNamedQuery("FbsCharge.findByFkProjId").setParameter("fkProjId", fbsProject.getProjId().toString()).getResultList();
            populateBroker();
            this.noOfParking = 0;
            renderParking();
            populatePlc();
            populatePayplan();
            populateDiscount();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void populatePlcInformation(HttpSession session) {
        FbsBlock fbsBlock2 = fbsBlockFacade.find(Integer.parseInt((String) session.getAttribute("blockId")));
        System.out.println("block id in session " + session.getAttribute("blockId").toString());
        String xmlFile1 = fbsBlock2.getXmlFile();
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(xmlFile1)));
            doc.getDocumentElement().normalize();
            NodeList block = doc.getElementsByTagName("block");
            NodeList floorList1 = block.item(0).getChildNodes();
            System.out.println("Floor list size " + floorList1.getLength());
            for (int i = 0; i < floorList1.getLength(); i++) {
                Node floor = floorList1.item(i);
                if (floor.getNodeType() == Node.ELEMENT_NODE) {
                    Element floorElement = (Element) floor;
                    String floorId1 = floorElement.getAttribute("floor_id").trim();
                    System.out.println("floor id is -> " + floorId1);
                    NodeList flatList = floorElement.getElementsByTagName("flat");
                    System.out.println("flat LIst size " + flatList.getLength());
                    for (int j = 0; j < flatList.getLength(); j++) {
                        Node flatvalue = flatList.item(j);
                        if (flatvalue.getNodeType() == Node.ELEMENT_NODE) {
                            Element flatelement = (Element) flatvalue;
                            System.out.println(" dfsdf" + flatelement.getAttribute("flat_id"));
                            if (flatelement.getAttribute("flat_id").trim().equals(session.getAttribute("flatId").toString().trim())) {
                                NodeList plcNodes = flatelement.getElementsByTagName("plc");
                                System.out.println("plc node Sixe " + plcNodes.getLength());
                                System.out.println("plc size ");

                                if (plcNodes.getLength() > 0) {
                                    for (int k = 0; k < plcNodes.getLength(); k++) {

                                        Node plcNode = plcNodes.item(k);
                                        if (plcNode.getNodeType() == Node.ELEMENT_NODE) {
                                            Element plcElement = (Element) plcNode;
                                            System.out.println("k " + k);
                                            NodeList list = plcElement.getElementsByTagName("plc_id");
                                            for (int l = 0; l < list.getLength(); l++) {
                                                String val = list.item(l).getTextContent();
                                                System.out.println("Flat List value " + val + " hj " + plcNode.getTextContent() + " ");
                                                fbsPlcinfo.add(fbsPlcFacade.find(Integer.parseInt(val)));
                                            }



                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void populatePlc() {
        fbsPlcList = new ArrayList<FbsPlc>();
        fbsPlcList = (List<FbsPlc>) em.createNamedQuery("FbsPlc.findByFkProjId").setParameter("fkProjId", fbsProject.getProjId()).getResultList();
        plcList.clear();
        for (int i = 0; i < fbsPlcList.size(); i++) {
            plcList.add(new SelectItem(fbsPlcList.get(i).getPlcId(), fbsPlcList.get(i).getPlcName()));
        }
    }

    public void poupulatePlcCharge() {
        for (int i = 0; i < fbsPlcList.size(); i++) {
            if (fbsPlc.getPlcId().toString().equals(fbsPlcList.get(i).getPlcId().toString())) {
                fbsPlc = fbsPlcList.get(i);
            }
        }
        calculateTotal();
    }

    public void populateBroker() {
        fbsBrokerList.clear();
        fbsBrokerList = (List<FbsBroker>) em.createNamedQuery("FbsBroker.findByCompanyId").setParameter("companyId", LoginBean.fbsLogin.getCompanyId()).getResultList();
        brokerList = new ArrayList();
        for (int i = 0; i < fbsBrokerList.size(); i++) {
            brokerList.add(new SelectItem(fbsBrokerList.get(i).getBrokerId(), fbsBrokerList.get(i).getBrokerId().toString()));
        }

    }

    public int commission() {
        System.out.println("in commission");

        //   calculateTotal();
        brokercom = this.brokercomision;
        return brokercom;
    }

    public void populateBrokerName() {
        fbsBroker = fbsBrokerFacade.find(this.fbsBroker.getBrokerId());
        calculateTotal();
    }

    public void populatePayplan() {
        fbsPlannameList = em.createNamedQuery("FbsPlanname.findByFkProjId").setParameter("fkProjId", fbsProject.getProjId()).getResultList();
        plannameList.clear();
        for (int i = 0; i < fbsPlannameList.size(); i++) {
            plannameList.add(new SelectItem(fbsPlannameList.get(i).getPlanId(), fbsPlannameList.get(i).getPlanName()));
        }
    }

    public void populateDiscount() {

        fbsDiscountList = em.createNamedQuery("FbsDiscount.findByCompanyId").setParameter("companyId", LoginBean.fbsLogin.getCompanyId()).getResultList();
        discountList = new String[fbsDiscountList.size()];
        for (int i = 0; i < discountList.length; i++) {
            discountList[i] = fbsDiscountList.get(i).getDiscountType()+"("+fbsDiscountList.get(i).getPercentage().toString()+"%)";
        }
    }

    public void setDiscount() {
        for (int i = 0; i < fbsDiscountList.size(); i++) {
            if (fbsDiscountList.get(i).getDiscountType().equals(fbsDiscount.getDiscountType())) {
                fbsDiscount = fbsDiscountList.get(i);
            }
        }
        calculateTotal();
    }

    public void calculateTotal() {
        payableAmount = 0;

        payableAmount = (int) (payableAmount + fbsFlatType.getFlatBsp() * fbsFlatType.getFlatSba());
        for (int i = 0; i < fbsChargeList.size(); i++) {
            payableAmount = payableAmount + fbsChargeList.get(i).getAmount();
        }
        // if(fbsPlc.getPlcCharge()!=null)
        //  payableAmount = (int) (payableAmount + fbsPlc.getPlcCharge());
        // payableAmount = payableAmount + (noOfCoveredParking * coveredParking);
        //payableAmount = payableAmount + (noOfUnCoveredParking * unCoveredParking);
        if (fbsPlcinfo.size() > 0) {
            for (int i = 0; i < fbsPlcinfo.size(); i++) {
                FbsPlc plccharge = fbsPlcinfo.get(i);
                payableAmount = (int) (payableAmount + plccharge.getPlcCharge() * fbsFlatType.getFlatSba());
            }

        }
        payableAmount = payableAmount - (fbsDiscount.getPercentage() * payableAmount / 100);
        if (fbsProject.getBrCommission() != null) {
            brokercomision = fbsProject.getBrCommission() * payableAmount / 100;
        }
        System.out.println("calculate broker commission ++++++++++++++" + brokercomision);

    }

//    public void calculateTotal() {
//        payableAmount = 0;
//        payableAmount = (int) (payableAmount + fbsFlatType.getFlatBsp());
//        for (int i = 0; i < fbsChargeList.size(); i++) {
//            payableAmount = payableAmount + fbsChargeList.get(i).getAmount();
//        }
//        payableAmount = (int) (payableAmount + fbsPlc.getPlcCharge());
//        // payableAmount = payableAmount + (noOfCoveredParking * coveredParking);
//        //payableAmount = payableAmount + (noOfUnCoveredParking * unCoveredParking);
//        payableAmount = payableAmount - (fbsDiscount.getPercentage() * payableAmount / 100);
//        System.out.println("broker commesion********************  "+fbsProject.getBrCommission());
//        if(fbsProject.getBrCommission()==null)
//           System.out.println("in if block");
//        else{
//                 System.out.println("in else block");
//        brokercomision = fbsProject.getBrCommission() * payableAmount / 100;
//        }
//
//   }
    public void renderParking() {
        render1 = false;
        render2 = false;
        render3 = false;
        render4 = false;
        render5 = false;
        for (int j = 1; j <= this.noOfParking; j++) {
            if (j == 1) {
                this.render1 = !this.render1;
            } else if (j == 2) {
                this.render2 = !this.render2;
            } else if (j == 3) {
                this.render3 = !this.render3;
            } else if (j == 4) {
                this.render4 = !this.render4;
            } else if (j == 5) {
                this.render5 = !this.render5;
            }
        }
        fbsParkingTypes.clear();
        parkingTypeList.clear();
        fbsParkingTypes = em.createNamedQuery("FbsParkingType.findByFkProjId").setParameter("fkProjId", fbsProject.getProjId()).getResultList();
        for (int j = 0; j < fbsParkingTypes.size(); j++) {
            parkingTypeList.add(new SelectItem(fbsParkingTypes.get(j).getParkingTypeId(), fbsParkingTypes.get(j).getParkingType()));
        }
        calculateParking();
    }

    public void calculateParking() {
        ParkingInfo parkingInfo;
        parkingInfoList.clear();
        for (int i = 0; i < fbsParkingTypes.size(); i++) {
            fbsParkingAllotList = em.createNamedQuery("FbsParkingAllot.findByParkingTypeId").setParameter("parkingTypeId", fbsParkingTypes.get(i).getParkingTypeId()).getResultList();
            parkingInfo = new ParkingInfo();
            parkingInfo.setParkingTypeId(fbsParkingTypes.get(i).getParkingTypeId());
            parkingInfo.setParkingType(fbsParkingTypes.get(i).getParkingType());
            parkingInfo.setAvailParking(fbsParkingTypes.get(i).getNoOfParking() - fbsParkingAllotList.size());
            parkingInfoList.add(parkingInfo);
        }
    }

    /******************************************************************************/
    public void forwardBooking() throws IOException {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);
        //   String flatId1 = (String) session.getAttribute("flatId1");
        flatNo = (String) session.getAttribute("flatNo");
        floorNo = (String) session.getAttribute("floorNo");
        flatTypeId = (String) session.getAttribute("flatTypeId");
        fbsFlatType = fbsFlatTypeFacade.find(Integer.parseInt(flatTypeId));
        fbsPlcinfo.clear();
        populatePlcInformation(session);
        populateCharges();
        for (int i = 0; i < fbsPlcinfo.size(); i++);
        System.out.println("Size of PLC info " + fbsPlcinfo.size());

        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/Booking/booking.xhtml");
    }

    public void addBooking() throws IOException {
        fbsBooking.setFlatId(Integer.parseInt(flatInfo.getFlatId()));
        fbsBooking.setBlockId(fbsBlock.getBlockId());
        fbsBooking.setBrokerId(fbsBroker.getBrokerId());
        fbsBooking.setDiscountId(fbsDiscount.getDiscountId());
        fbsBooking.setPlcId(fbsPlc.getPlcId());
        fbsBooking.setStatus("b");
        fbsBooking.setUserId(LoginBean.fbsLogin.getUserId());
        fbsBookingFacade.create(fbsBooking);
        FbsParkingAllot fbsParkingAllot;
        for (int i = 0; i < this.noOfParking; i++) {
            fbsParkingAllot = new FbsParkingAllot();
            fbsParkingAllot.setFkFlatId(fbsBooking.getFlatId());
            if (i == 0) {
                fbsParkingAllot.setParkingTypeId(parkingType1);
                fbsParkingAllotFacade.create(fbsParkingAllot);
            } else if (i == 1) {

                fbsParkingAllot.setParkingTypeId(parkingType2);
                fbsParkingAllotFacade.create(fbsParkingAllot);
            } else if (i == 2) {

                fbsParkingAllot.setParkingTypeId(parkingType3);
                fbsParkingAllotFacade.create(fbsParkingAllot);
            } else if (i == 3) {

                fbsParkingAllot.setParkingTypeId(parkingType4);
                fbsParkingAllotFacade.create(fbsParkingAllot);
            } else if (i == 4) {

                fbsParkingAllot.setParkingTypeId(parkingType5);
                fbsParkingAllotFacade.create(fbsParkingAllot);
            }

        }
        fbsChargeList.clear();
        parkingInfoList.clear();
        fbsParkingTypes.clear();
        parkingTypeList.clear();
        fbsParkingTypes = em.createNamedQuery("FbsParkingType.findByFkProjId").setParameter("fkProjId", fbsProject.getProjId()).getResultList();
        for (int j = 0; j < fbsParkingTypes.size(); j++) {
            parkingTypeList.add(new SelectItem(fbsParkingTypes.get(j).getParkingTypeId(), fbsParkingTypes.get(j).getParkingType()));
        }
        fbsApplicant.setFlatId(fbsBooking.getFlatId());
        coFbsApplicant.setFlatId(fbsBooking.getFlatId());
        fbsApplicant.setApplicantFlag(Short.parseShort("1"));
        coFbsApplicant.setApplicantFlag(Short.parseShort("2"));
        fbsApplicantFacade.create(fbsApplicant);
        fbsApplicantFacade.create(coFbsApplicant);

        fbsProject = new FbsProject();
        fbsBlock = new FbsBlock();
        fbsApplicant = new FbsApplicant();
        coFbsApplicant = new FbsApplicant();
        fbsFlatType = new FbsFlatType();
        fbsPlc = new FbsPlc();
        fbsPlanname = new FbsPlanname();
        //  fbsBooking = new FbsBooking();
        fbsDiscount = new FbsDiscount();
        //  fbsBooking.setBookingDt(new Date());
        fbsBroker = new FbsBroker();


        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Congrates! Flat Booked Successfully"));
        flatMasterBean.findFlatMaster(fbsBooking);
    }



    public void addProjectBooking() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);
        String flatId = (String) session.getAttribute("flatId");
        fbsBooking.setFlatId(Integer.parseInt(flatId));
        fbsBooking.setBlockId(fbsBlock.getBlockId());
        fbsBooking.setBrokerId(fbsBroker.getBrokerId());
        fbsBooking.setDiscountId(fbsDiscount.getDiscountId());
        fbsBooking.setPlcId(fbsPlc.getPlcId());
        fbsBooking.setStatus("b");
        fbsBooking.setUserId(LoginBean.fbsLogin.getUserId());
        fbsBookingFacade.create(fbsBooking);
        FbsParkingAllot fbsParkingAllot;
        for (int i = 0; i < this.noOfParking; i++) {
            fbsParkingAllot = new FbsParkingAllot();
            fbsParkingAllot.setFkFlatId(fbsBooking.getFlatId());
            if (i == 0) {
                fbsParkingAllot.setParkingTypeId(parkingType1);
                fbsParkingAllotFacade.create(fbsParkingAllot);
            } else if (i == 1) {

                fbsParkingAllot.setParkingTypeId(parkingType2);
                fbsParkingAllotFacade.create(fbsParkingAllot);
            } else if (i == 2) {

                fbsParkingAllot.setParkingTypeId(parkingType3);
                fbsParkingAllotFacade.create(fbsParkingAllot);
            } else if (i == 3) {

                fbsParkingAllot.setParkingTypeId(parkingType4);
                fbsParkingAllotFacade.create(fbsParkingAllot);
            } else if (i == 4) {

                fbsParkingAllot.setParkingTypeId(parkingType5);
                fbsParkingAllotFacade.create(fbsParkingAllot);
            }

        }
        fbsChargeList.clear();
        parkingInfoList.clear();
        fbsParkingTypes.clear();
        parkingTypeList.clear();
        fbsParkingTypes = em.createNamedQuery("FbsParkingType.findByFkProjId").setParameter("fkProjId", fbsProject.getProjId()).getResultList();
        for (int j = 0; j < fbsParkingTypes.size(); j++) {
            parkingTypeList.add(new SelectItem(fbsParkingTypes.get(j).getParkingTypeId(), fbsParkingTypes.get(j).getParkingType()));
        }
        fbsApplicant.setFlatId(fbsBooking.getFlatId());
        coFbsApplicant.setFlatId(fbsBooking.getFlatId());
        fbsApplicant.setApplicantFlag(Short.parseShort("1"));
        coFbsApplicant.setApplicantFlag(Short.parseShort("2"));
        fbsApplicantFacade.create(fbsApplicant);
        fbsApplicantFacade.create(coFbsApplicant);
        fbsProject = new FbsProject();
        fbsBlock = new FbsBlock();
        fbsApplicant = new FbsApplicant();
        coFbsApplicant = new FbsApplicant();
        fbsFlatType = new FbsFlatType();
        fbsPlc = new FbsPlc();
        fbsPlanname = new FbsPlanname();
        // fbsBooking = new FbsBooking();
        fbsDiscount = new FbsDiscount();
        //  fbsBooking.setBookingDt(new Date());
        fbsBroker = new FbsBroker();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Congrates! Flat Booked Successfully"));
        flatMasterBean.findFlatMaster(fbsBooking);
    }

    public void setFlatInfo1(FlatInfo flatInfo1) {
        this.flatInfo1 = flatInfo1;
    }

    public FlatInfo getFlatInfo1() {
        return flatInfo1;
    }

    public void setCoFbsApplicant1(FbsApplicant coFbsApplicant1) {
        this.coFbsApplicant1 = coFbsApplicant1;
    }

    public FbsApplicant getCoFbsApplicant1() {
        return coFbsApplicant1;
    }

    public FbsApplicant getFbsApplicant1() {
        return fbsApplicant1;
    }

    public void setFbsApplicant1(FbsApplicant fbsApplicant1) {
        this.fbsApplicant1 = fbsApplicant1;
    }

    public List<FbsPayment> getFbsPaymentList1() {
        return fbsPaymentList1;
    }

    public void setFbsPaymentList1(List<FbsPayment> fbsPaymentList1) {
        this.fbsPaymentList1 = fbsPaymentList1;
    }

    public FbsFlatType getFbsFlatType1() {
        return fbsFlatType1;
    }

    public void setFbsFlatType1(FbsFlatType fbsFlatType1) {
        this.fbsFlatType1 = fbsFlatType1;
    }

    public FbsApplicant getCoFbsApplicant() {
        return coFbsApplicant;
    }

    public void setCoFbsApplicant(FbsApplicant coFbsApplicant) {
        this.coFbsApplicant = coFbsApplicant;
    }

    public FbsApplicant getFbsApplicant() {
        return fbsApplicant;
    }

    public void setFbsApplicant(FbsApplicant fbsApplicant) {
        this.fbsApplicant = fbsApplicant;
    }

    public FbsProject getFbsProject() {
        return fbsProject;
    }

    public void setFbsProject(FbsProject fbsProject) {
        this.fbsProject = fbsProject;
    }

    public void setProjectList(ArrayList<FbsProject> projectList) {
        GraphicalSearchBean.projectList = projectList;
    }

    public List<FbsProject> getProjectList() {
        return projectList;
    }

    public FbsBlock getFbsBlock() {
        return fbsBlock;
    }

    public void setFbsBlock(FbsBlock fbsBlock) {
        this.fbsBlock = fbsBlock;
    }

    public List<FbsBlock> getFbsBlockList() {
        return fbsBlockList;
    }

    public void setFbsBlockList(List<FbsBlock> fbsBlockList) {
        this.fbsBlockList = fbsBlockList;
    }

    public FbsFlat getFbsFlat() {
        return fbsFlat;
    }

    public void setFbsFlat(FbsFlat fbsFlat) {
        this.fbsFlat = fbsFlat;
    }

    public List<FbsFlat> getFbsFlatList() {
        return fbsFlatList;
    }

    public void setFbsFlatList(List<FbsFlat> fbsFlatList) {
        this.fbsFlatList = fbsFlatList;
    }

    public List<FbsFlat> getRefFlatList() {
        return refFlatList;
    }

    public void setRefFlatList(List<FbsFlat> refFlatList) {
        this.refFlatList = refFlatList;
    }

    public FlatInfo[] getFlatNoList() {
        return flatNoList;
    }

    public void setFlatNoList(FlatInfo[] flatNoList) {
        this.flatNoList = flatNoList;
    }

    public ArrayList getFloorList() {
        return floorList;
    }

    public void setFloorList(ArrayList floorList) {
        this.floorList = floorList;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public FlatInfo getFlatInfo() {
        return flatInfo;
    }

    public void setFlatInfo(FlatInfo flatInfo) {
        this.flatInfo = flatInfo;
    }

    public FbsFlatType getFbsFlatType() {
        return fbsFlatType;
    }

    public void setFbsFlatType(FbsFlatType fbsFlatType) {
        this.fbsFlatType = fbsFlatType;
    }

    public List<FbsApplicant> getFbsApplicantList() {
        return fbsApplicantList;
    }

    public void setFbsApplicantList(List<FbsApplicant> fbsApplicantList) {
        this.fbsApplicantList = fbsApplicantList;
    }

    public List<FbsPayment> getFbsPaymentList() {
        return fbsPaymentList;
    }

    public void setFbsPaymentList(List<FbsPayment> fbsPaymentList) {
        this.fbsPaymentList = fbsPaymentList;
    }

    public ArrayList getBrokerList() {
        return brokerList;
    }

    public void setBrokerList(ArrayList brokerList) {
        this.brokerList = brokerList;
    }

    public int getBrokercomision() {
        return brokercomision;
    }

    public void setBrokercomision(int brokercomision) {
        this.brokercomision = brokercomision;
    }

    public String[] getDiscountList() {
        return discountList;
    }

    public void setDiscountList(String[] discountList) {
        this.discountList = discountList;
    }

    public FbsBroker getFbsBroker() {
        return fbsBroker;
    }

    public void setFbsBroker(FbsBroker fbsBroker) {
        this.fbsBroker = fbsBroker;
    }

    public List<FbsBroker> getFbsBrokerList() {
        return fbsBrokerList;
    }

    public void setFbsBrokerList(List<FbsBroker> fbsBrokerList) {
        this.fbsBrokerList = fbsBrokerList;
    }

    public List<FbsCharge> getFbsChargeList() {
        return fbsChargeList;
    }

    public void setFbsChargeList(List<FbsCharge> fbsChargeList) {
        this.fbsChargeList = fbsChargeList;
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

    public List<FbsParkingAllot> getFbsParkingAllotList() {
        return fbsParkingAllotList;
    }

    public void setFbsParkingAllotList(List<FbsParkingAllot> fbsParkingAllotList) {
        this.fbsParkingAllotList = fbsParkingAllotList;
    }

    public List<FbsParkingType> getFbsParkingTypes() {
        return fbsParkingTypes;
    }

    public void setFbsParkingTypes(List<FbsParkingType> fbsParkingTypes) {
        this.fbsParkingTypes = fbsParkingTypes;
    }

    public List<FbsPlanname> getFbsPlannameList() {
        return fbsPlannameList;
    }

    public void setFbsPlannameList(List<FbsPlanname> fbsPlannameList) {
        this.fbsPlannameList = fbsPlannameList;
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

    public int getNoOfParking() {
        return noOfParking;
    }

    public void setNoOfParking(int noOfParking) {
        this.noOfParking = noOfParking;
    }

    public List<ParkingInfo> getParkingInfoList() {
        return parkingInfoList;
    }

    public void setParkingInfoList(List<ParkingInfo> parkingInfoList) {
        this.parkingInfoList = parkingInfoList;
    }

    public ArrayList getParkingTypeList() {
        return parkingTypeList;
    }

    public void setParkingTypeList(ArrayList parkingTypeList) {
        this.parkingTypeList = parkingTypeList;
    }

    public int getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(int payableAmount) {
        this.payableAmount = payableAmount;
    }

    public ArrayList getPlannameList() {
        return plannameList;
    }

    public void setPlannameList(ArrayList plannameList) {
        this.plannameList = plannameList;
    }

    public ArrayList getPlcList() {
        return plcList;
    }

    public void setPlcList(ArrayList plcList) {
        this.plcList = plcList;
    }

    public boolean isRender1() {
        return render1;
    }

    public void setRender1(boolean render1) {
        this.render1 = render1;
    }

    public boolean isRender2() {
        return render2;
    }

    public void setRender2(boolean render2) {
        this.render2 = render2;
    }

    public boolean isRender3() {
        return render3;
    }

    public void setRender3(boolean render3) {
        this.render3 = render3;
    }

    public boolean isRender4() {
        return render4;
    }

    public void setRender4(boolean render4) {
        this.render4 = render4;
    }

    public boolean isRender5() {
        return render5;
    }

    public void setRender5(boolean render5) {
        this.render5 = render5;
    }

    public FbsBooking getFbsBooking() {
        return fbsBooking;
    }

    public void setFbsBooking(FbsBooking fbsBooking) {
        this.fbsBooking = fbsBooking;
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

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public boolean isUnbooked() {
        return unbooked;
    }

    public void setUnbooked(boolean unbooked) {
        this.unbooked = unbooked;
    }

    public FbsBookingDetail getBookingDetail() {
        return bookingDetail;
    }

    public void setBookingDetail(FbsBookingDetail bookingDetail) {
        this.bookingDetail = bookingDetail;
    }

    public int getBrokercom() {
        return brokercom;
    }

    public void setBrokercom(int brokercom) {
        this.brokercom = brokercom;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public String getFlatTypeId() {
        return flatTypeId;
    }

    public void setFlatTypeId(String flatTypeId) {
        this.flatTypeId = flatTypeId;
    }

    public String getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(String floorNo) {
        this.floorNo = floorNo;
    }

    public List<FbsPlc> getFbsPlcinfo() {
        return fbsPlcinfo;
    }

    public void setFbsPlcinfo(List<FbsPlc> fbsPlcinfo) {
        this.fbsPlcinfo = fbsPlcinfo;
    }
}
