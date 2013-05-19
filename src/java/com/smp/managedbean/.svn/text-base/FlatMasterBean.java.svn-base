/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.managedbean;

import com.smp.entity.FbsApplicant;
import com.smp.entity.FbsBlock;
import com.smp.entity.FbsBooking;
import com.smp.entity.FbsDocs;
import com.smp.entity.FbsFlatType;
import com.smp.entity.FbsLoan;
import com.smp.entity.FbsPayment;
import com.smp.entity.FbsPayplan;
import com.smp.entity.FbsProject;
import com.smp.entity.FbsReport;
import com.smp.fbs.FbsBookingDetail;
import com.smp.fbs.FbsFlat;
import com.smp.fbs.FlatInfo;
import com.smp.session.FbsApplicantFacade;
import com.smp.session.FbsBlockFacade;
import com.smp.session.FbsBookingFacade;
import com.smp.session.FbsDocsFacade;
import com.smp.session.FbsFlatTypeFacade;
import com.smp.session.FbsLoanFacade;
import com.smp.session.FbsProjectFacade;
import com.smp.session.FbsReportFacade;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import java.io.StringReader;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author smp11
 */
@ManagedBean(name = "flatMasterBean")
@ApplicationScoped
@Stateful
public class FlatMasterBean implements Serializable {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
    @EJB
    FbsBlockFacade fbsBlockFacade;
    @EJB
    FbsBookingFacade fbsBookingFacade;
    @EJB
    FbsProjectFacade fbsProjectFacade;
    @EJB
    FbsApplicantFacade fbsApplicantFacade;
    @EJB
    FbsReportFacade fbsReportFacade;
    @EJB
    FbsLoanFacade fbsLoanFacade;
    @EJB
    FbsDocsFacade fbsDocsFacade;
    public FbsBooking fbsBooking = new FbsBooking();
    private FbsBlock fbsBlock = new FbsBlock();
    public static FbsBlock fbsBlock1 = new FbsBlock();
    public static FbsApplicant fbsApplicant = new FbsApplicant();
    public static FbsApplicant coFbsApplicant = new FbsApplicant();
    private List<FbsFlat> fbsFlatList = new ArrayList<FbsFlat>();
    public static List<FbsPayment> fbsPaymentList = new ArrayList<FbsPayment>();
    public static List<FbsDocs> fbsDocList = new ArrayList<FbsDocs>();
    public static FbsDocs fbsDocs = new FbsDocs();
    List<FbsFlat> refFlatList = new ArrayList<FbsFlat>();
    List<FbsBooking> fbsBookingList = new ArrayList<FbsBooking>();
    List<FbsReport> fbsReportList = new ArrayList<FbsReport>();
    public ArrayList floorList;
    public static FbsFlatType fbsFlatType = new FbsFlatType();
    @EJB
    FbsFlatTypeFacade FbsFlatTypeFacade;
    public static FlatInfo flatInfo;
    public FlatInfo[] flatNoList;
    public String floorName;
    public String flatNo;
    public String floorNo;
    public String flatTypeId;
    String xmlFile = "";
    int high = 0;
    boolean editdetail = false;
    boolean label = true;
    boolean colabel = true;
    boolean coeditdetail;
    public static boolean uploading = false;
    public static String appId = "";
    boolean loaninfo = false;
    public static boolean booked = false;
    //   public boolean unbooked = false;
    public static FbsProject fbsProject = new FbsProject();
    FbsLoan fbsLoan;
    File upfile;
    String docName;
    String description;
    //byte image[];
    String ApplicantId = "";
    StreamedContent streamedContent;
    boolean disable = false;

    public FlatMasterBean() {
        fbsLoan = new FbsLoan();
        coeditdetail = false;
    }

    public void Populate(SelectEvent event) throws IOException {
        FbsPayment fbsPayment = (FbsPayment) event.getObject();
        String flatId1 = fbsPayment.getUnitCode();
        find(flatId1);
        uploadImage();
        disable = false;
    }

    public void PopulateBlockwise(FbsBookingDetail fbsBookingDetail) {
        //FbsBookingDetail fbsBookingDetail = (FbsBookingDetail) event.getObject();
        String flatId1 = fbsBookingDetail.getFlatId().toString();
        find(flatId1);
    }

    public void editDetails() {
        editdetail = true;
        uploading = true;
        label = false;
        System.out.println("edit detalis method in flat master bean is called ");
    }

    public void editInformation(FbsApplicant fbsApplicant) throws IOException {

        ApplicantId = fbsApplicant.getApplicantId().toString();
        appId = fbsApplicant.getApplicantId().toString();
        //fbsApplicant=fbsApplicantFacade.find(Integer.parseInt(applicantId));
        System.out.println("in edit info" + ApplicantId + "  " + appId);

        System.out.println("I am in flat master bean " + fbsApplicant.getApplicantName());
        if (isEditdetail()) {
            FbsApplicant applicant = fbsApplicantFacade.find(fbsApplicant.getApplicantId());
            byte[] img = applicant.getImage1Path();

            fbsApplicant.setImage1Path(img);
            fbsApplicantFacade.edit(fbsApplicant);
        }
        if (isCoeditdetail()) {
            FbsApplicant applicant = fbsApplicantFacade.find(fbsApplicant.getApplicantId());
            byte[] img = applicant.getImage1Path();
            fbsApplicant.setImage1Path(img);
            fbsApplicantFacade.edit(fbsApplicant);
        }


        System.out.println("applicant name " + fbsApplicant.getApplicantName() + "      " + this.fbsApplicant.getApplicantName());
        fbsApplicant = new FbsApplicant();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Congrates! Information Sucessfully Updated"));
        editdetail = false;
        label = true;
        colabel = true;
        coeditdetail = false;
    }

    public void putFlagtwo() {
        if (isEditdetail()) {
            appId = ApplicantId = fbsApplicant.getApplicantId().toString();
        } else if (isCoeditdetail()) {
            appId = ApplicantId = coFbsApplicant.getApplicantId().toString();
        }
    }

    public String uploadImage() throws IOException {

        Object o = coFbsApplicant.getImage1Path();

        byte[] image = (byte[]) o;
        if (image != null) {
//    InputStreamReader ir=new InputStreamReader;

            // streamedContent=
            // System.out.println("cofbsApplicant Id "+coFbsApplicant.getApplicantId()+" size is "+image.length);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            String outputfile = externalContext.getRealPath("/") + "resources/images/" + coFbsApplicant.getApplicantId().toString() + "sks";  // get path on the server
            System.out.println("path+++++++++++++++" + outputfile);

            File file = new File(outputfile);
            file.delete();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(image);

//System.out.println("out put file "+outputfile);
            return ("/resources/images/" + coFbsApplicant.getApplicantId().toString() + "sks");
        } else {

            return ("/resources/images/pic.jpg");
        }
    }

    public String uploadImageFirst() throws IOException {

        String image_path = "";
        Object o1 = fbsApplicant.getImage1Path();

        byte[] image1 = (byte[]) o1;
        if (image1 != null) {
//    InputStreamReader ir=new InputStreamReader;

            // streamedContent=
            // System.out.println("cofbsApplicant Id "+coFbsApplicant.getApplicantId()+" size is "+image.length);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            String outputfile = externalContext.getRealPath("/") + "resources/images/" + fbsApplicant.getApplicantId().toString() + "sks";  // get path on the server
            System.out.println("path+++++++++++++++" + outputfile);

            File file = new File(outputfile);
            file.delete();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(image1);

//System.out.println("out put file "+outputfile);
            image_path = "/resources/images/" + fbsApplicant.getApplicantId().toString() + "sks";
        } else {
            image_path = "/resources/images/pic.jpg";
        }
        return image_path;
    }

    public void handleFileUpload(FbsDocs doc) throws IOException {
        Object o = doc.getFile();
        byte[] a = (byte[]) o;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        // externalContext.setResponseHeader("Content-Type",);
        externalContext.setResponseHeader("Content-Length", String.valueOf(a.length));
        externalContext.setResponseHeader("Content-Disposition", "attachment;filename=\"" + doc.getDocName() + "\"");
        externalContext.getResponseOutputStream().write(a);
        facesContext.responseComplete();


    }

    public void coeditDetails() throws IOException {
        colabel = false;
        coeditdetail = true;
        uploading = true;
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/Project/directFlatMaster.xhtml");
    }

    public void PopulateSearch(SelectEvent event) throws IOException {
        FbsBookingDetail fbsBookingDetail = (FbsBookingDetail) event.getObject();
        String flatId1 = fbsBookingDetail.getFlatId().toString();
        find(flatId1);
    }

    public void PopulateBooking(FbsBooking fbsBooking) {
        String flatId1 = fbsBooking.getFlatId().toString();
        find(flatId1);
    }

    public void forward(FlatInfo flatInfo, String requestFlat) throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);
        String projId = (String) session.getAttribute("projId");
        String blockId = (String) session.getAttribute("blockId");
        FlatMasterBean.flatInfo = flatInfo;
        String flatId1 = flatInfo.getFlatId();
        System.out.println("yahoooo forward called");
        session.setAttribute("flatId", flatInfo.getFlatId());
        if (flatInfo.getStatus().equals("b")) {

            this.booked = true;
            //  this.unbooked =false;
            System.out.println("flat id in if condition " + flatId1);
            find(flatId1);

        } else {
            this.booked = false;
            //   this.unbooked = true;
            // session.setAttribute("flatId",flatId1);
            session.setAttribute("flatNo", flatInfo.getFlatNo());
            session.setAttribute("floorNo", flatInfo.getFloorNo());
            session.setAttribute("flatTypeId", flatInfo.getFlatTypeId());
            fbsFlatType = FbsFlatTypeFacade.find(Integer.parseInt(flatInfo.getFlatTypeId()));
            fbsBlock1 = fbsBlockFacade.find(Integer.parseInt(blockId));
            fbsProject = fbsProjectFacade.find(Integer.parseInt(projId));
            if (requestFlat.equals("company")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/Project/directFlatMaster1.xhtml");
            } else {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/Project/directFlatMaster.xhtml");
            }


        }

    }

    public void BookingList(String flatId1) {
        fbsBooking = (FbsBooking) em.createNamedQuery("FbsBooking.findByFlatId").setParameter("flatId", Integer.parseInt(flatId1)).getResultList().get(0);

        this.booked = true;

        find(flatId1);
    }

    public void searchFlat(String flatId1) throws IOException {
        fbsBookingList = fbsBookingFacade.findAll();
        this.booked = false;
        for (int i = 0; i < fbsBookingList.size(); i++) {
            if (fbsBookingList.get(i).getFlatId().toString().equals(flatId1)) {
                this.booked = true;
                break;
            }
        }
        System.out.println(this.booked);
        if (this.booked == true) {

            find(flatId1);

        } else {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/Project/directFlatMaster.xhtml");

        }

    }

    public List<FbsDocs> findDoc(String flatId) throws IOException {
        fbsDocList.clear();
        System.out.println("flatId in find doc method......." + flatId);
        fbsDocList = em.createNamedQuery("FbsDocs.findByUnitCode").setParameter("unitCode", Integer.parseInt(flatId.trim())).getResultList();
        //List<FbsPayplan> fbsPayplanList = em.createNamedQuery("FbsPayplan.findByFkPlanId").setParameter("fkPlanId", fbsBooking.getPlanId()).getResultList();

        return fbsDocList;
    }

    public void find(String flatId1) {
        System.out.println("++++++++" + flatId1);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(false);
        try {
            System.out.println("++++++++" + flatId1);
            fbsBooking = (FbsBooking) em.createNamedQuery("FbsBooking.findByFlatId").setParameter("flatId", Integer.parseInt(flatId1)).getResultList().get(0);
            int blockId = fbsBooking.getBlockId();
            int regNo = fbsBooking.getRegNumber();
            fbsReportList = em.createNamedQuery("FbsReport.findByRegNumber").setParameter("regNumber", regNo).getResultList();
            fbsBlock1 = fbsBlockFacade.find(blockId);
            String projId = String.valueOf(fbsBlock1.getFkProjId());
            session.setAttribute("projId", projId);
            fbsProject = fbsProjectFacade.find(Integer.parseInt(projId));
            String blockName = fbsBlock1.getBlockName();
            session.setAttribute("blockName", blockName);
            //   fbsBlock = (FbsBlock) em.createNamedQuery("FbsBlock.findByBlockId").setParameter("blockId", blockId).getResultList().get(0);

            fbsFlatList = new ArrayList();
            int l = 0;
            xmlFile = fbsBlock1.getXmlFile();
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
            flatInfo = new FlatInfo();
            for (int b = 0; b < fbsFlatList.size(); b++) {
                if (fbsFlatList.get(b).getFlatId().toString().equals(flatId1)) {
                    flatInfo.setFlatId(fbsFlatList.get(b).getFlatId().toString());
                    flatInfo.setFlatNo(fbsFlatList.get(b).getFlatNo());
                    flatInfo.setFlatTypeId(fbsFlatList.get(b).getFlatType());
                    flatInfo.setFloorNo(fbsFlatList.get(b).getFloorNo().toString());
//                   for (int s = 0; s < fbsBookingFacade.findAll().size(); s++) {
//                    if (fbsBookingFacade.findAll().get(s).getFlatId().toString().equals(fbsFlatList.get(b).getFlatId().toString())) {
//                        flatInfo.setStatus("b");
//                        break;
//                    } else {
//                        flatInfo.setStatus("u");
//                    }
//
//                }
//                    System.out.println("status"+flatInfo.getStatus());
                    session.setAttribute("flatTypeId", flatInfo.getFlatTypeId());
                    session.setAttribute("flatId", flatInfo.getFlatId());
                    session.setAttribute("flatNo", flatInfo.getFlatNo());
                    session.setAttribute("floorNo", flatInfo.getFloorNo());
                    break;
                }
            }
            fbsFlatType = FbsFlatTypeFacade.find(Integer.parseInt(flatInfo.getFlatTypeId()));
            List<FbsApplicant> fbsAplicantList = em.createNamedQuery("FbsApplicant.findByFlatId").setParameter("flatId", Integer.parseInt(flatId1)).getResultList();

            fbsApplicant = fbsAplicantList.get(fbsAplicantList.size() - 2);
            session.setAttribute("applicantId", fbsApplicant.getApplicantId().toString());
            coFbsApplicant = (FbsApplicant) fbsAplicantList.get(fbsAplicantList.size() - 1);
            fbsPaymentList.clear();
            fbsPaymentList = em.createNamedQuery("FbsPayment.findByUnitCode").setParameter("unitCode", (flatInfo.getFlatId())).getResultList();
            fbsDocList.clear();
            fbsDocList = em.createNamedQuery("FbsDocs.findByUnitCode").setParameter("unitCode", Integer.parseInt(flatInfo.getFlatId())).getResultList();
            //List<FbsPayplan> fbsPayplanList = em.createNamedQuery("FbsPayplan.findByFkPlanId").setParameter("fkPlanId", fbsBooking.getPlanId()).getResultList();

            externalContext.redirect("/FbsFaces/faces/jsfpages/Project/directFlatMaster.xhtml");
            //  externalContext.redirect("/FbsFaces/faces/jsfpages/Booking/booking.xhtml");
        } catch (Exception ex) {
            Logger.getLogger(QuickBookingBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void findFlatMaster(FbsBooking fbsBooking) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);
        try {
            //  System.out.println("++++++++"+flatId1);
            //fbsBooking = (FbsBooking) em.createNamedQuery("FbsBooking.findByFlatId").setParameter("flatId", Integer.valueOf("3247102")).getResultList().get(0);
            String flatId1 = fbsBooking.getFlatId().toString();
            int blockId = fbsBooking.getBlockId();

            System.out.println("++++++++" + flatId1);
            System.out.println("++++++++" + blockId);


            //fbsBlock = fbsBlockFacade.find(blockId);
            fbsBlock1 = (FbsBlock) em.createNamedQuery("FbsBlock.findByBlockId").setParameter("blockId", blockId).getResultList().get(0);
            String blockName = fbsBlock1.getBlockName();
            String projId = String.valueOf(fbsBlock1.getFkProjId());
            session.setAttribute("projId", projId);
            session.setAttribute("blockName", blockName);

            fbsFlatList = new ArrayList();
            int l = 0;
            xmlFile = fbsBlock1.getXmlFile();
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
            flatInfo = new FlatInfo();
            for (int b = 0; b < fbsFlatList.size(); b++) {
                if (fbsFlatList.get(b).getFlatId().toString().equals(flatId1)) {
                    flatInfo.setFlatId(fbsFlatList.get(b).getFlatId().toString());
                    flatInfo.setFlatNo(fbsFlatList.get(b).getFlatNo());
                    flatInfo.setFlatTypeId(fbsFlatList.get(b).getFlatType());
                    flatInfo.setFloorNo(fbsFlatList.get(b).getFloorNo().toString());
                    session.setAttribute("flatTypeId", flatInfo.getFlatTypeId());
                    session.setAttribute("flatId", flatInfo.getFlatId());
                    session.setAttribute("flatNo", flatInfo.getFlatNo());
                    break;
                }
            }

            fbsFlatType = FbsFlatTypeFacade.find(Integer.parseInt(flatInfo.getFlatTypeId()));
            System.out.println("+++" + fbsFlatType.getFlatSpecification());
            if (fbsBooking.getStatus().equals("b")) {
                System.out.println("+status++" + fbsBooking.getStatus());
                this.booked = true;
                //   this.unbooked =false;
                fbsApplicant = (FbsApplicant) em.createNamedQuery("FbsApplicant.findByFlatId").setParameter("flatId", Integer.parseInt(flatId1)).getResultList().get(0);
                session.setAttribute("applicantId", fbsApplicant.getApplicantId().toString());
                coFbsApplicant = (FbsApplicant) em.createNamedQuery("FbsApplicant.findByFlatId").setParameter("flatId", Integer.parseInt(flatId1)).getResultList().get(1);
                fbsPaymentList.clear();
                fbsPaymentList = em.createNamedQuery("FbsPayment.findByUnitCode").setParameter("unitCode", (flatInfo.getFlatId())).getResultList();

                fbsDocList.clear();
                fbsDocList = em.createNamedQuery("FbsDocs.findByUnitCode").setParameter("unitCode", Integer.parseInt(flatInfo.getFlatId())).getResultList();
                System.out.println("booked status-->" + this.booked);

            } else if (fbsBooking.getStatus().equals("t")) {
                this.booked = true;
                List<FbsApplicant> applicants = em.createNamedQuery("FbsApplicant.findByFlatId").setParameter("flatId", Integer.parseInt(flatId1)).getResultList();

                fbsApplicant = applicants.get(applicants.size() - 2);
                coFbsApplicant = applicants.get(applicants.size() - 1);
                session.setAttribute("applicantId", fbsApplicant.getApplicantId().toString());
                fbsPaymentList.clear();
                fbsPaymentList = em.createNamedQuery("FbsPayment.findByUnitCode").setParameter("unitCode", (flatInfo.getFlatId())).getResultList();

                fbsDocList.clear();
                fbsDocList = em.createNamedQuery("FbsDocs.findByUnitCode").setParameter("unitCode", Integer.parseInt(flatInfo.getFlatId())).getResultList();
            } else {
                this.booked = false;
                /// this.unbooked = true;
            }

            FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/Project/directFlatMaster.xhtml");

        } catch (Exception ex) {
            Logger.getLogger(FlatMasterBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void generateLoan() {
        fbsLoan.setUnitCode(Integer.parseInt(flatInfo.getFlatId()));
        List fbsLoanList = em.createNamedQuery("FbsLoan.findByUnitCode").setParameter("unitCode", Integer.parseInt(flatInfo.getFlatId())).getResultList();
        FbsLoan fbsloantemp = new FbsLoan();
        if (fbsLoanList.size() > 0) {
            fbsloantemp = (FbsLoan) fbsLoanList.get(0);
            fbsLoanFacade.edit(fbsLoan);
            fbsLoanFacade.edit(fbsLoan);
        } else {
            fbsLoanFacade.create(fbsLoan);
        }
        fbsLoan = new FbsLoan();
        loaninfo = true;
    }

    public void addDocument() {



//        docName = fbsDocs.getDocName();
//        description = fbsDocs.getDescription();
//        //image=fbsDocs.getFile();
//        fbsDocs.setDocName(docName);
//        fbsDocs.setDescription(description);
        System.out.println("document name=" + fbsDocs.getDocName());
        fbsDocsFacade.create(fbsDocs);
        fbsDocs = new FbsDocs();
        //fbsDocs.setFile(image);

        FacesContext context = FacesContext.getCurrentInstance();

//        fbsPayment.setUnitCode("");
//
//        flatMasterBean.find(unitCode);
    }

    public void deleteDocument(FbsDocs fbsDocs) {
        fbsDocsFacade.remove(fbsDocs);
        fbsDocList = em.createNamedQuery("FbsDocs.findAll").getResultList();
    }

    public void editLoanInfo() {
        loaninfo = false;
    }

    public void loanStatus() {
        fbsLoan = new FbsLoan();
        System.out.println("Loan Status is called ");
        List fbsLoanList = em.createNamedQuery("FbsLoan.findByUnitCode").setParameter("unitCode", Integer.parseInt(flatInfo.getFlatId())).getResultList();
        FbsLoan fbsloantemp = new FbsLoan();
        if (fbsLoanList.size() > 0) {
            fbsloantemp = (FbsLoan) fbsLoanList.get(0);
            loaninfo = true;
            fbsLoan = fbsloantemp;
        } else {
            loaninfo = false;
        }
    }

    public void enableDisable() {
        disable = true;
        System.out.println("enable disable is   " + disable);
    }

    public void disDisable() {
        disable = false;
        System.out.println("diable disable is   " + disable);
    }

    public List<FbsPayment> getFbsPaymentList() {
        return fbsPaymentList;
    }

    public void setFbsPaymentList(List<FbsPayment> fbsPaymentList) {
        this.fbsPaymentList = fbsPaymentList;
    }

    public FbsApplicant getCoFbsApplicant() {
        return coFbsApplicant;
    }

    public void setCoFbsApplicant(FbsApplicant coFbsApplicant) {
        FlatMasterBean.coFbsApplicant = coFbsApplicant;
    }

    public FbsBlock getFbsBlock1() {
        return fbsBlock1;
    }

    public void setFbsBlock1(FbsBlock fbsBlock1) {
        FlatMasterBean.fbsBlock1 = fbsBlock1;
    }

    public FbsBlock getFbsBlock() {
        return fbsBlock;
    }

    public void setFbsBlock(FbsBlock fbsBlock) {
        this.fbsBlock = fbsBlock;
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
        FlatMasterBean.flatInfo = flatInfo;
    }

    public FbsFlatType getFbsFlatType() {
        return fbsFlatType;
    }

    public void setFbsFlatType(FbsFlatType fbsFlatType) {
        FlatMasterBean.fbsFlatType = fbsFlatType;
    }

    public FbsApplicant getFbsApplicant() {
        return fbsApplicant;
    }

    public void setFbsApplicant(FbsApplicant fbsApplicant) {
        FlatMasterBean.fbsApplicant = fbsApplicant;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        FlatMasterBean.booked = booked;
    }

    public FbsProject getFbsProject() {
        return fbsProject;
    }

    public void setFbsProject(FbsProject fbsProject) {
        FlatMasterBean.fbsProject = fbsProject;
    }

    public boolean isEditdetail() {
        return editdetail;
    }

    public void setEditdetail(boolean editdetail) {
        this.editdetail = editdetail;
    }

    public boolean isLabel() {
        return label;
    }

    public void setLabel(boolean label) {
        this.label = label;
    }

    public boolean isCoeditdetail() {
        return coeditdetail;
    }

    public void setCoeditdetail(boolean coeditdetail) {
        this.coeditdetail = coeditdetail;
    }

    public boolean isColabel() {
        return colabel;
    }

    public void setColabel(boolean colabel) {
        this.colabel = colabel;
    }

    public FbsLoan getFbsLoan() {
        return fbsLoan;
    }

    public void setFbsLoan(FbsLoan fbsLoan) {
        this.fbsLoan = fbsLoan;
    }

    public boolean isLoaninfo() {
        return loaninfo;
    }

    public void setLoaninfo(boolean loaninfo) {
        this.loaninfo = loaninfo;
    }

    public File getUpfile() {
        return upfile;
    }

    public void setUpfile(File upfile) {
        this.upfile = upfile;
    }

    public List<FbsDocs> getFbsDocList() {
        return fbsDocList;
    }

    public void setFbsDocList(List<FbsDocs> fbsDocList) {
        this.fbsDocList = fbsDocList;
    }

    public FbsDocs getFbsDocs() {
        return fbsDocs;
    }

    public void setFbsDocs(FbsDocs fbsDocs) {
        this.fbsDocs = fbsDocs;
    }

    public String getApplicantId() {
        if (coeditdetail) {
            putFlagtwo();
        }

        return ApplicantId;
    }

    public void setApplicantId(String ApplicantId) {
        this.ApplicantId = ApplicantId;
    }

    public List<FbsReport> getFbsReportList() {
        return fbsReportList;
    }

    public void setFbsReportList(List<FbsReport> fbsReportList) {
        this.fbsReportList = fbsReportList;
    }

    public boolean isDisable() {
        System.out.println("getter disable " + disable);
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }
}
