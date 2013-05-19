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
import com.smp.entity.FbsLoan;
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
import com.smp.session.FbsBrokerCatFacade;
import com.smp.session.FbsProjectFacade;
import com.smp.session.FbsBrokerFacade;
import com.smp.session.FbsFlatTypeFacade;
import com.smp.session.FbsLoanFacade;
import com.smp.session.FbsParkingAllotFacade;
import com.smp.session.FbsParkingTypeFacade;
import com.smp.session.FbsPlannameFacade;
import com.smp.session.FbsPlcFacade;
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
import javax.xml.parsers.ParserConfigurationException;
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
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.Query;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.xml.sax.SAXException;

/**
 *
 * @author smp7
 */
@ManagedBean(name = "quickBookingBean")
@SessionScoped
@Stateless
public class QuickBookingBean implements Serializable {

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
    @EJB
    FbsPlcFacade fbsPlcFacade;
    @EJB
    FbsLoanFacade fbsLoanFacade;
    @EJB
    FbsPlannameFacade fbsPlannameFacade;
    @EJB
    FbsBrokerCatFacade fbsBrokerCatFacade;
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
    FbsLoan fbsLoan;
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
    public long loan_Amount = 0;
    public String islooan = "";
    public Double installment_Amount = 0.0;
    public int number_of_years = 0;
    public float rate_of_interest;
    public String roi = "";
    public int loanyear = 0;
    public boolean renderloan = false;
    public ArrayList parkingTypeList = new ArrayList();
    public FlatInfo flatInfo;
    private List<FbsPayment> fbsPaymentList = new ArrayList<FbsPayment>();
    List<FbsPlc> fbsPlcinfo = new ArrayList<FbsPlc>();

    public QuickBookingBean() {
        fbsLoan = new FbsLoan();
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
    }

    @PostConstruct
    public void populate() {
        projectList = new ArrayList();
        for (int i = 0; i < fbsProjectFacade.findAll().size(); i++) {
            projectList.add(new SelectItem(fbsProjectFacade.findAll().get(i).getProjId(), fbsProjectFacade.findAll().get(i).getProjName()));
        }
        islooan = "No";
    }

    public void populateProjectCode() {
        fbsFlatType = new FbsFlatType();
        fbsProject = (FbsProject) em.createNamedQuery("FbsProject.findByProjId").setParameter("projId", fbsProject.getProjId()).getResultList().get(0);
        populateBlocks();
        floorList = null;
        flatNoList.clear();
        //  populateBroker();
        plannameList.clear();
        discountList = null;
        fbsDiscount = new FbsDiscount();
        floorName = "";

        this.noOfParking = 0;
        renderParking();
        fbsPlcinfo.clear();
    }

    public void populateBlocks() {
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
        fbsPlcinfo.clear();
    }

    public void populateFloors() {
        fbsPlcinfo.clear();
        fbsBlock = (FbsBlock) em.createNamedQuery("FbsBlock.findByBlockId").setParameter("blockId", fbsBlock.getBlockId()).getResultList().get(0);
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
            int high = 0;
            for (int a = 0; a < fbsFlatList.size(); a++) {
                if (high < Integer.parseInt(fbsFlatList.get(a).getFloorNo())) {
                    high = Integer.parseInt(fbsFlatList.get(a).getFloorNo());
                }
            }
            high++;
            floorList = new String[high];
            for (int i = 0; i < floorList.length; i++) {
                floorList[i] = String.valueOf(i);
            }

        } catch (Exception ex) {
            Logger.getLogger(QuickBookingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void populateFlats() {
        int l = 0;
        int f = 0;
        fbsFlatList = new ArrayList<FbsFlat>();
        for (int i = 0; i < this.refFlatList.size(); i++) {
            if (this.refFlatList.get(i).getFloorNo().equals(this.floorName.trim())) {
                this.fbsFlatList.add(l, this.refFlatList.get(i));
                l++;
            }
        }
        fbsBookingList = fbsBookingFacade.findAll();
        flatNoList = new ArrayList();
        for (int j = 0; j < fbsFlatList.size(); j++) {
            f = 0;
            for (int k = 0; k < fbsBookingList.size(); k++) {
                if (fbsBookingList.get(k).getFlatId().toString().equals(fbsFlatList.get(j).getFlatId().toString())) {
                    f = 1;
                    break;
                }
            }
            if (f == 0) {
                flatNoList.add(new SelectItem(fbsFlatList.get(j).getFlatId(), fbsFlatList.get(j).getFlatNo()));
            }
        }



    }

    public void populateFlatSpecification() {
        String flatTypeId = "0";
        System.out.println("Flat id on populate flats pecification " + fbsBooking.getFlatId() + "   " + fbsFlatList.size());
        for (int i = 0; i < fbsFlatList.size(); i++) {
            if (fbsBooking.getFlatId().toString().equals(fbsFlatList.get(i).getFlatId().toString())) {
                flatTypeId = fbsFlatList.get(i).getFlatType();
            }
        }
        fbsFlatType = (FbsFlatType) em.createNamedQuery("FbsFlatType.findByFlatTypeId").setParameter("flatTypeId", Integer.parseInt(flatTypeId)).getResultList().get(0);

        fbsPlcinfo.clear();
        populateCharges();
        populatePlcInformation();
        //   calculateTotal();
    }

    public void populatePlcInformation() {
        FbsBlock fbsBlock2 = fbsBlockFacade.find(fbsBlock.getBlockId());
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
                            if (flatelement.getAttribute("flat_id").trim().equals(String.valueOf(fbsBooking.getFlatId()))) {
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

    public void populateCharges() {
        try {
            fbsChargeList = (List<FbsCharge>) em.createNamedQuery("FbsCharge.findByFkProjId").setParameter("fkProjId", fbsProject.getProjId().toString()).getResultList();
            populatePlc();
            populatePayplan();
            populateDiscount();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void populatePlc() {
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
        // calculateTotal();
    }

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
        commission();
    }

    public int commission() {
        if ((fbsFlatType != null) && (fbsDiscount != null)) {
            long payableAmount = (fbsFlatType.getFlatBsp() * fbsFlatType.getFlatSba());
            payableAmount -= Math.round(payableAmount - payableAmount * fbsPlannameFacade.find(fbsBooking.getPlanId()).getDiscount() / 100 - payableAmount * fbsDiscount.getPercentage() / 100);

            System.out.println("in commission");

            //   calculateTotal();
            brokercom = Math.round(payableAmount * fbsBrokerCatFacade.find(fbsBroker.getCategoryId()).getBrokerPercent() / 100);
        }
        return brokercom;
    }

    public String projectCode() {
        projectcode = this.fbsProject.getProjCode();
        return projectcode;
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
            discountList[i] = fbsDiscountList.get(i).getDiscountType() + "(" + fbsDiscountList.get(i).getPercentage().toString() + "%)";
        }
    }

    public void setDiscount() {
        //    System.out.println("discount percentage "+fbsDiscount.getDiscountType());
        for (int i = 0; i < fbsDiscountList.size(); i++) {
            if (fbsDiscount.getDiscountType().startsWith(fbsDiscountList.get(i).getDiscountType())) {
                String name = fbsDiscount.getDiscountType();
                fbsDiscount = fbsDiscountList.get(i);
                fbsDiscount.setDiscountType(name);
            }
        }
        //  System.out.println("discount percentage "+fbsDiscount.getPercentage());
        calculateTotal();
        populateDiscount();
        populateBroker();
    }

    public long calculateTotal() {
        payableAmount = 0;      
        if(!(fbsProject.getProjId() == null) && !(fbsBlock.getBlockId() == null) && !(floorName == null) && !(fbsBooking.getFlatId() == null) && !(fbsBooking.getBookingDt() == null) && !(fbsBooking.getPlanId() == null) && !(fbsDiscount.getDiscountType() == null)){
        if (fbsFlatType != null && fbsFlatType.getFlatBsp() != null && fbsFlatType.getFlatSba() != null) {
            payableAmount = (int) (payableAmount + fbsFlatType.getFlatBsp() * fbsFlatType.getFlatSba());
        }
        for (int i = 0; i < fbsChargeList.size(); i++) {
            payableAmount = payableAmount + fbsChargeList.get(i).getAmount();
        }
        //if (fbsPlc.getPlcCharge() != null) {
        // payableAmount = (int) (payableAmount + fbsPlc.getPlcCharge());
        // }
        if (fbsPlcinfo.size() > 0) {
            for (int i = 0; i < fbsPlcinfo.size(); i++) {
                FbsPlc plccharge = fbsPlcinfo.get(i);
                payableAmount = (int) (payableAmount + plccharge.getPlcCharge() * fbsFlatType.getFlatSba());
            }

        }
        // payableAmount = payableAmount + (noOfCoveredParking * coveredParking);
        //payableAmount = payableAmount + (noOfUnCoveredParking * unCoveredParking);
        try {
            if (fbsDiscount != null) {
                System.out.println("  vgvvv  " + fbsDiscount.getPercentage());
                payableAmount = payableAmount - (fbsDiscount.getPercentage() * payableAmount / 100);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        if (fbsProject.getBrCommission() != null) {
            brokercomision = fbsProject.getBrCommission() * payableAmount / 100;
        }
        System.out.println("calculate broker commission" + brokercomision);
        }
        return payableAmount;

    }

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

    /*******************************Upload Image*****************/
    public void handleFileUpload(FileUploadEvent event) {
        UploadedFile file = event.getFile();

        System.out.println(" file name" + file.getFileName() + " " + file.getSize());

        System.out.println("sdsdsdsdsd" + event.getFile().getFileName());
    }

    /**************************************************************/
    /*******************************Booking****************************/
    public void addBooking() throws IOException {

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
        //fbsBooking = new FbsBooking();
        fbsDiscount = new FbsDiscount();
        //fbsBooking.setBookingDt(new Date());
        fbsBroker = new FbsBroker();


        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Congrates! Flat Booked Successfully"));

        flatMasterBean.findFlatMaster(fbsBooking);
    }

    public void checkRender() {
        System.out.println("yahoooooooooooooooooooooooooooooooooooo" + islooan);
        if ((fbsBooking.getFlatId() != null) && (islooan.trim().toUpperCase().equals("yes".toUpperCase()))) {
            this.renderloan = !this.renderloan;
        } else {
            // loan_Amount=0;
            //  islooan="";
            //installment_Amount=0.0;
            // number_of_years=0;
            // rate_of_interest=(float) 0.0;
            // roi="";
            //  loanyear=0;
            renderloan = false;
        }
        System.out.println("renderloan is" + renderloan);
    }

    public void generateLoan() {
        //  double temp= (rate_of_interest / 12);
        // temp=temp/100;
        //double temp1=Math.pow((1+temp),12);
//installment_Amount=(loan_Amount*temp*temp1)/(temp1-1);

//System.out.println("the loan installment is "+Math.round(installment_Amount));
//System.out.println("the loan installment is "+(installment_Amount));
        if (fbsBooking.getFlatId() != null) {
            fbsLoanFacade.create(fbsLoan);
            fbsLoan = new FbsLoan();
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Please Select a Flat First !"));
        }
        renderloan = false;
    }

    public void cancelLoan() {
        System.out.println("render is going to be false " + renderloan);
        renderloan = false;
        islooan = "No";
        System.out.println("render is going to be false " + renderloan);
    }

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

    public List<FbsPlc> getFbsPlcinfo() {
        return fbsPlcinfo;
    }

    public void setFbsPlcinfo(List<FbsPlc> fbsPlcinfo) {
        this.fbsPlcinfo = fbsPlcinfo;
    }

    public double getInstallment_Amount() {
        return installment_Amount;
    }

    public void setInstallment_Amount(double installment_Amount) {
        this.installment_Amount = installment_Amount;
    }

    public long getLoan_Amount() {
        return loan_Amount;
    }

    public void setLoan_Amount(long loan_Amount) {
        this.loan_Amount = loan_Amount;
    }

    public float getRate_of_interest() {
        return rate_of_interest;
    }

    public void setRate_of_interest(float rate_of_interest) {
        this.rate_of_interest = rate_of_interest;
    }

    public int getNumber_of_years() {
        return number_of_years;
    }

    public void setNumber_of_years(int number_of_years) {
        this.number_of_years = number_of_years;
    }

    public String getRoi() {
        roi = String.valueOf(rate_of_interest);
        return roi;
    }

    public void setRoi(String roi) {

        this.roi = roi;
        rate_of_interest = Float.parseFloat(roi);
    }

    public String getIslooan() {
        System.out.println("isloan is " + islooan);
        return islooan;
    }

    public boolean isRenderloan() {
        return renderloan;
    }

    public void setRenderloan(boolean renderloan) {
        this.renderloan = renderloan;
    }

    public void setIslooan(String islooan) {
        this.islooan = islooan;

    }

    public int getLoanyear() {
        return loanyear;
    }

    public void setLoanyear(int loanyear) {
        this.loanyear = loanyear;
    }

    public FbsLoan getFbsLoan() {
        return fbsLoan;
    }

    public void setFbsLoan(FbsLoan fbsLoan) {
        this.fbsLoan = fbsLoan;
    }
}
