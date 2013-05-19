/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.managedbean;

import com.smp.entity.FbsApplicant;
import com.smp.entity.FbsBlock;
import com.smp.entity.FbsBooking;
import com.smp.entity.FbsDiscount;
import com.smp.entity.FbsFlat;
import com.smp.entity.FbsFlatType;
import com.smp.entity.FbsProject;
import com.smp.fbs.FbsBookingDetail;
import com.smp.session.FbsApplicantFacade;
import com.smp.session.FbsBlockFacade;
import com.smp.session.FbsBookingFacade;
import com.smp.session.FbsDiscountFacade;
import com.smp.session.FbsFlatTypeFacade;
import com.smp.session.FbsParkingAllotFacade;
import com.smp.session.FbsProjectFacade;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author smp11
 */
@ManagedBean(name = "bookingListBean")
@SessionScoped
@Stateful
public class BookingListManagedBean implements Serializable {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
    @EJB
    FbsBookingFacade fbsBookingFacade;
    @EJB
    FbsBlockFacade fbsBlockFacade;
    @EJB
    FbsProjectFacade fbsProjectFacade;
    @EJB
    FbsApplicantFacade fbsApplicantFacade;
    @EJB
    FbsDiscountFacade fbsDiscountFacade;
    @EJB
    FbsParkingAllotFacade fbsParkingAllotFacade;
    @EJB
    FbsFlatTypeFacade fbsFlatTypeFacade;
    List<FbsBooking> fbsBookingList;
    List<FbsBookingDetail> bookingList;
    List<FbsBookingDetail> bookingTemps;
    List<FbsBookingDetail> refBookingList;
    List<FbsApplicant> fbsApplicantList = new ArrayList<FbsApplicant>();
    FbsBookingDetail fbsBookingDetail;
    FbsBlock fbsBlock;
    FbsProject fbsProject;
    List<FbsProject> fbsProjectList;
    List<FbsFlat> fbsFlatList;
    FbsApplicant fbsApplicant;
    FbsApplicant coFbsApplicant;
    FbsDiscount fbsDiscount;
    FbsFlatType fbsFlatType = new FbsFlatType();
    private String[] projectOption;
    private String[] blockNameOption;
    private int[] projId;
    private String projName;
    private String blockName;
    private int projID;
    boolean status;
    boolean proj;
    boolean blok;
    boolean flor;
    boolean flt;
    boolean nam;
    boolean namTemp;
    boolean dat;
    boolean[] flOption = new boolean[6];
    Date startDate;
    Date endDate;
    String floorNO;
    String flatNO;
    String viewStatus;
    String name;
    private String xmlFile;
    private List<FbsFlat> refFlatList;
    private String[] floorList;
    public ArrayList flatNoList = new ArrayList();

    public BookingListManagedBean() {

        fbsBookingList = new ArrayList<FbsBooking>();
        bookingList = new ArrayList<FbsBookingDetail>();

        for (int i = 0; i < 6; i++) {
            flOption[i] = false;
        }
        proj = false;
        blok = false;
        flor = false;
        flt = false;
        nam = false;
        namTemp = false;
        dat = false;
        this.projName = "";
        this.blockName = "";
        this.floorNO = "";
        this.flatNO = "";
        this.name = "";
        this.startDate = null;
        this.endDate = null;

    }

    @PostConstruct
    public void populateBookingDetail() {
        bookingList.clear();
        fbsBookingList = fbsBookingFacade.findAll();
        for (int i = 0; i < fbsBookingList.size(); i++) {
            fbsBookingDetail = new FbsBookingDetail();
            fbsBookingDetail.setRegNo(fbsBookingList.get(i).getRegNumber());
            fbsBookingDetail.setBookingdt(fbsBookingList.get(i).getBookingDt());
            int blockId = fbsBookingList.get(i).getBlockId();
            fbsBlock = fbsBlockFacade.find(blockId);
            long projId = fbsBlock.getFkProjId();
            fbsProject = fbsProjectFacade.find(Integer.parseInt(String.valueOf(projId)));
            fbsBookingDetail.setProjectName(fbsProject.getProjName());
            fbsBookingDetail.setBlockNo(fbsBlock.getBlockName());
            int regNo = fbsBookingList.get(i).getRegNumber();
            List<FbsApplicant> applicants = em.createNamedQuery("FbsApplicant.findByFlatId").setParameter("flatId", fbsBookingList.get(i).getFlatId()).getResultList();

            fbsApplicant = applicants.get(applicants.size() - 2);
            coFbsApplicant = applicants.get(applicants.size() - 1);
            // fbsApplicant = (FbsApplicant) em.createNamedQuery("FbsApplicant.findByFlatId").setParameter("flatId", fbsBookingList.get(i).getFlatId()).getResultList().get(0);
            //   coFbsApplicant = (FbsApplicant) em.createNamedQuery("FbsApplicant.findByFlatId").setParameter("flatId", fbsBookingList.get(i).getFlatId()).getResultList().get(1);
            String app = fbsApplicant.getApplicantName().toString();
            fbsBookingDetail.setApplicantName(fbsApplicant.getApplicantName());
            fbsBookingDetail.setApplicantId(fbsApplicant.getApplicantId());
            fbsBookingDetail.setCoApplicantId(coFbsApplicant.getApplicantId());
            // fbsBookingDetail.setFlatNo(fbsBookingList.get(i).getFlatId());
            fbsBookingDetail.setFlatId(fbsBookingList.get(i).getFlatId().toString());
            Integer DiscountId = fbsBookingList.get(i).getDiscountId();
            fbsDiscount = fbsDiscountFacade.find(Integer.parseInt(DiscountId.toString()));
            fbsBookingDetail.setDiscountType(fbsDiscount.getDiscountType());
            // fbsBookingDetail.setDiscountType(fbsDiscount.getDiscountType());
            fbsBookingDetail.setBlockId(fbsBookingList.get(i).getBlockId());
            fbsBookingDetail.setProjId(projId);
            /************************************************************/
            try {
                String xmlFile = fbsBlock.getXmlFile();
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(new InputSource(new StringReader(xmlFile)));
                doc.getDocumentElement().normalize();
                NodeList block = doc.getElementsByTagName("block");
                NodeList floorList1 = block.item(0).getChildNodes();
                for (int k = 0; k < floorList1.getLength(); k++) {
                    Node floor = floorList1.item(k);
                    if (floor.getNodeType() == Node.ELEMENT_NODE) {
                        Element floorElement = (Element) floor;
                        String floorId1 = floorElement.getAttribute("floor_id").trim();
                        NodeList floorNoList = floorElement.getElementsByTagName("floor_number");
                        Node fnoElement = (Node) floorNoList.item(0);
                        NodeList flatList = floorElement.getElementsByTagName("flat");
                        for (int j = 0; j < flatList.getLength(); j++) {


                            Element flatElement = (Element) flatList.item(j);
                            String flatId = flatElement.getAttribute("flat_id").trim();
                            if (flatId.equals(fbsBookingDetail.getFlatId())) {
                                fbsBookingDetail.setFloorNo(fnoElement.getTextContent());
                                NodeList flatTypeList = flatElement.getElementsByTagName("flattype");
                                Element typeElement = (Element) flatTypeList.item(0);
                                fbsBookingDetail.setFlatTypeId(Integer.parseInt((typeElement.getAttribute("flatTypeId"))));
                                NodeList flatNoList1 = flatElement.getElementsByTagName("flatno");
                                Element noElement = (Element) flatNoList1.item(0);
                                fbsBookingDetail.setFlatNo(Integer.parseInt(noElement.getAttribute("flatNo").trim()));
                            }
                        }
                    }
                }

            } catch (Exception ex) {
            }
            bookingList.add(i, fbsBookingDetail);
        }
        if (bookingList.size() <= 5) {
            viewStatus = "View";
        } else {
            viewStatus = "View More";
        }
        fbsProjectList = fbsProjectFacade.findAll();
        projectOption = new String[fbsProjectList.size()];
        projId = new int[fbsProjectList.size()];
        for (int i = 0; i < fbsProjectList.size(); i++) {
            projectOption[i] = fbsProjectList.get(i).getProjName();
            projId[i] = fbsProjectList.get(i).getProjId();
        }
        refBookingList = bookingList;

    }

    public void populateBookingDetailByFilter() {

        bookingTemps = new ArrayList<FbsBookingDetail>();
        bookingTemps.addAll(bookingList);
//        System.out.println("Flat List-->" + bookingTemps.size());
//        System.out.println("NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN ");
//        System.out.println("Select Project-->" + this.projName);
//        System.out.println("Select block-->" + this.blockName);
//        System.out.println("Select FloorNo-->" + this.floorNO);
//        System.out.println("Select FlatNo-->" + this.flatNO);
//        System.out.println("Select Applicant Name-->" + this.name);
//        System.out.println("Select StartDate-->" + this.startDate);
//        System.out.println("Select EndDate-->" + this.endDate);
//        System.out.println("+++++++++++++++++++++++++++++++++++++++ ");
//        System.out.println("proj-->" + this.proj);
//        System.out.println("blok-->" + this.blok);
//        System.out.println("flor-->" + this.flor);
//        System.out.println("flt-->" + this.flt);
//        System.out.println("nam-->" + this.nam);
//        System.out.println("dat-->" + this.dat);
        int l = 0;
        if ((projName.equals("Select Project")) && (blockName.equals("Select Block")) && (floorNO.equals("Select Floor")) && (flatNO.equals("Select Flat")) && (name.equals("")) && (startDate == null || endDate == null)) {
            l = 0;
            // bookingTemps = this.bookingList;
        } else if ((projName.equals("Select Project")) && (!(this.dat))) {
            reset();
        } else {
            if (this.proj) {
                flOption[0] = this.proj;

            }
            if (this.blok) {
                flOption[1] = this.blok;

            }
            if (this.flor) {
                flOption[2] = this.flor;

            }
            if (this.flt) {
                flOption[3] = this.flt;
            }
            if (this.nam) {
                flOption[4] = this.nam;

            }
            if (this.dat) {
                flOption[5] = this.dat;

            }
            if (flOption[0]) {
                bookingTemps = filterByProject(bookingTemps, this.projName);
                // System.out.println("Book List 0-->" + bookingTemps.size());
            }
            if (flOption[1]) {
                bookingTemps = filterByBlock(bookingTemps, this.blockName);
                // System.out.println("Book List 1-->" + bookingTemps.size());
            }
            if (flOption[2]) {
                bookingTemps = filterByFloor(bookingTemps, floorNO);
                // System.out.println("Book List 2-->" + bookingTemps.size());
            }
            if (flOption[3]) {
                bookingTemps = filterByFlat(bookingTemps, flatNO);
                // System.out.println("Book List 3-->" + bookingTemps.size());
            }
            if (flOption[4]) {
                bookingTemps = filterByName(bookingTemps, name);
                //System.out.println("Book List 4-->" + bookingTemps.size());
            }
            if (flOption[5]) {
                bookingTemps = populateBookingDetailByDateFilter(bookingTemps, startDate, endDate);
                // System.out.println("Book List 5-->" + bookingTemps.size());
            }
        }
        refBookingList = bookingTemps;
        this.blok = false;
        this.flor = false;
        this.flt = false;
        this.nam = false;

    }

    public List<FbsBookingDetail> filterByProject(List<FbsBookingDetail> fbsBookingTemps, String projName) {

        int k = 0;
        List<FbsBookingDetail> bookingTemps1 = new ArrayList<FbsBookingDetail>();
        for (int i = 0; i < fbsBookingTemps.size(); i++) {
            if (fbsBookingTemps.get(i).getProjectName().equals(projName)) {
                bookingTemps1.add(k, fbsBookingTemps.get(i));
                k++;
            }
        }
        return bookingTemps1;
    }

    public List<FbsBookingDetail> filterByBlock(List<FbsBookingDetail> fbsBookingTemps, String blockName) {
        int k = 0;
        List<FbsBookingDetail> bookingTemps1 = new ArrayList<FbsBookingDetail>();
        for (int i = 0; i < fbsBookingTemps.size(); i++) {
            if (fbsBookingTemps.get(i).getBlockNo().equals(blockName)) {
                bookingTemps1.add(k, fbsBookingTemps.get(i));
                k++;
            }
        }
        return bookingTemps1;
    }

    public List<FbsBookingDetail> filterByFloor(List<FbsBookingDetail> fbsBookingTemps, String floorNo) {
        int k = 0;
        List<FbsBookingDetail> bookingTemps1 = new ArrayList<FbsBookingDetail>();
        for (int i = 0; i < fbsBookingTemps.size(); i++) {
            if (fbsBookingTemps.get(i).getFloorNo().equals(floorNo)) {
                bookingTemps1.add(k, fbsBookingTemps.get(i));
                k++;
            }
        }
        return bookingTemps1;
    }

    public List<FbsBookingDetail> filterByFlat(List<FbsBookingDetail> fbsBookingTemps, String flatNo) {
        int k = 0;
        List<FbsBookingDetail> bookingTemps1 = new ArrayList<FbsBookingDetail>();
        for (int i = 0; i < fbsBookingTemps.size(); i++) {

            if (String.valueOf(fbsBookingTemps.get(i).getFlatId()).equals(flatNo)) {

                bookingTemps1.add(k, fbsBookingTemps.get(i));

                k++;
            }
        }
        return bookingTemps1;
    }

    public List<FbsBookingDetail> filterByName(List<FbsBookingDetail> fbsBookingTemps, String name) {
        int k = 0;
        boolean nameStatus = false;
        List<FbsBookingDetail> bookingTemps1 = new ArrayList<FbsBookingDetail>();
        for (int i = 0; i < fbsBookingTemps.size(); i++) {
            nameStatus = fbsBookingTemps.get(i).getApplicantName().toUpperCase().contains(name.toUpperCase());

            if (nameStatus) {

                bookingTemps1.add(k, fbsBookingTemps.get(i));

                k++;
            }
        }
        return bookingTemps1;

    }

    public List<FbsBookingDetail> populateBookingDetailByDateFilter(List<FbsBookingDetail> fbsBookingTemps, Date startDate, Date endDate) {
        int k = 0;
        List<FbsBookingDetail> bookingTemps1 = new ArrayList<FbsBookingDetail>();
        if ((startDate == null) || (endDate == null)) {
            resetDateOption();
            return fbsBookingTemps;
        }
        if (startDate.after(endDate)) {
            Date temp = endDate;
            endDate = startDate;
            startDate = temp;
            this.startDate = startDate;
            this.endDate = endDate;
        }
        for (int i = 0; i < fbsBookingTemps.size(); i++) {

            if (((fbsBookingTemps.get(i).getBookingdt().after(startDate)) || (fbsBookingTemps.get(i).getBookingdt().equals(startDate))) && ((fbsBookingTemps.get(i).getBookingdt().before(endDate))) || (fbsBookingTemps.get(i).getBookingdt().equals(endDate))) {
                bookingTemps1.add(k, fbsBookingTemps.get(i));
                k++;
            }
        }
        return bookingTemps1;
    }

    public void populateProjectList() {

        fbsProjectList = fbsProjectFacade.findAll();
        projectOption = new String[fbsProjectList.size()];
        projId = new int[fbsProjectList.size()];
        for (int i = 0; i < fbsProjectList.size(); i++) {
            projectOption[i] = fbsProjectList.get(i).getProjName();
            projId[i] = fbsProjectList.get(i).getProjId();
        }
    }

    public void populateBlock() {

        resetNameOption();
        resetFlatOption();


        resetFloorOption();
        resetblockOption();

        projID = 0;
        for (int i = 0; i < this.projectOption.length; i++) {
            if (projectOption[i].equals(projName)) {
                projID = projId[i];
                // System.out.println("prij Id-->" + projID);
                break;
            }
        }
        List<FbsBlock> fbsBlock = em.createNamedQuery("FbsBlock.findByFkProjId").setParameter("fkProjId", projID).getResultList();
        int siz = fbsBlock.size();
        blockNameOption = new String[siz];
        for (int n = 0; n < siz; n++) {
            blockNameOption[n] = fbsBlock.get(n).getBlockName();
        }
        resetFloorOption();

        populateBookingDetailByFilter();
    }

    public void populateFloor() {
        if (!(this.blockName.equals("Select Block")) && (!(this.blockName.equals("")))) {

            fbsBlock = (FbsBlock) em.createNamedQuery("FbsBlock.findByBlockName").setParameter("blockName", this.blockName).getResultList().get(0);
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
            }
        } else {
            this.floorList = null;
            //System.out.println("hi");
        }
        resetFlatOption();
        populateBookingDetailByFilter();
    }

    public void populateFlats() {
        if (!(this.floorNO.equals("Select Floor")) && (!(this.floorNO.equals("")))) {
            int l = 0;
            int f = 0;
            fbsFlatList = new ArrayList<FbsFlat>();
            for (int i = 0; i < this.refFlatList.size(); i++) {
                if (this.refFlatList.get(i).getFloorNo().equals(this.floorNO.trim())) {
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
                if (f == 1) {
                    flatNoList.add(new SelectItem(fbsFlatList.get(j).getFlatId(), fbsFlatList.get(j).getFlatId().toString()));
                }
            }


        } else {
            resetFloorOptionOnSelect();
            this.flatNoList = null;
        }
        populateBookingDetailByFilter();
    }

    public void reset() {

        resetDateOption();
        resetNameOption();
        resetFlatOption();
        resetArrayOfOption();
        resetProjctOption();
        resetFloorOption();
        resetblockOption();
        this.refBookingList = this.bookingList;
        //System.out.println("ref-->"+refBookingList.size());
        //System.out.println("bookinglist-->"+bookingList.size());
    }

    public void resetFlatOption() {
        this.flatNO = "";
        this.flt = false;
        this.flatNoList = null;
        this.flOption[3] = false;
    }

    public void resetFlatOptionOnSelect() {
        this.flatNO = "";
        this.flt = false;
        this.flOption[3] = false;
    }

    public void resetFloorOption() {
        this.floorNO = "";
        this.flor = false;
        this.floorList = null;
        this.flOption[2] = false;
        resetFlatOption();
    }

    public void resetFloorOptionOnSelect() {
        this.floorNO = "";
        this.flor = false;
        this.flOption[2] = false;
    }

    public void resetblockOption() {
        this.blockName = "";
        this.blok = false;
        this.blockNameOption = null;
        this.flOption[1] = false;

    }

    public void resetblockOptionOnSelect() {
        this.blockName = "";
        this.blok = false;
        this.flOption[1] = false;
    }

    public void resetNameOption() {
        this.name = "";
        this.nam = false;
        this.flOption[4] = false;
        populateProjectList();
    }

    public void resetDateOption() {
        this.startDate = null;
        this.endDate = null;
    }

    public void resetProjctOption() {
        this.projName = "";
        this.proj = false;
        this.flOption[0] = false;

    }

    public void resetArrayOfOption() {
        flOption[0] = false;
        flOption[1] = false;
        flOption[2] = false;
        flOption[3] = false;
        flOption[4] = false;
        flOption[5] = false;

    }

    public List<FbsBookingDetail> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<FbsBookingDetail> bookingList) {
        this.bookingList = bookingList;
    }

    public FbsFlatType getFbsFlatType() {
        return fbsFlatType;
    }

    public void setFbsFlatType(FbsFlatType fbsFlatType) {
        this.fbsFlatType = fbsFlatType;
    }

    public String[] getProjectOption() {
        return projectOption;
    }

    public void setProjectOption(String[] projectOption) {
        this.projectOption = projectOption;
    }

    public void setProjName(String projName) {
        this.projName = projName;
        if ((!(projName.equals(""))) && (!(projName.equals("Select Project")))) {
            this.proj = true;
        } else {

            reset();
        }
    }

    public String getProjName() {
        return projName;
    }

    public int getProjID() {
        return projID;
    }

    public void setProjID(int projID) {
        this.projID = projID;

    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
        if ((!(blockName.equals(""))) && (!(blockName.equals("Select Block")))) {
            this.blok = true;
        } else {

            resetblockOptionOnSelect();
        }
    }

    public String getBlockName() {
        return blockName;
    }

    public void setFloorNO(String floorNO) {
        this.floorNO = floorNO;
        if ((!(floorNO.equals(""))) && (!(floorNO.equals("Select Floor")))) {
            this.flor = true;
        } else {
            resetFloorOptionOnSelect();
        }
    }

    public String getFloorNO() {
        return floorNO;
    }

    public void setFlatNO(String flatNO) {
        this.flatNO = flatNO;
        if ((!(flatNO.equals(""))) && (!(flatNO.equals("Select Flat")))) {
            this.flt = true;
        } else {
            resetFlatOptionOnSelect();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

        if (!(name.equals("")) && !(name.equals("Applicant Name"))) {
            this.nam = true;
            this.namTemp = false;
        } else {
            this.nam = false;
        }
    }

    public String getFlatNO() {
        return flatNO;
    }

    public void setBlockNameOption(String[] blockNameOption) {
        this.blockNameOption = blockNameOption;
    }

    public String[] getBlockNameOption() {
        return blockNameOption;
    }

    public void setFlatNoList(ArrayList flatNoList) {
        this.flatNoList = flatNoList;
    }

    public ArrayList getFlatNoList() {
        return flatNoList;
    }

    public void setRefBookingList(List<FbsBookingDetail> refBookingList) {
        this.refBookingList = refBookingList;
    }

    public List<FbsBookingDetail> getRefBookingList() {
        populateBookingDetail();
        return refBookingList;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
        this.dat = false;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
        this.dat = true;
    }

    public Date getStartDate() {
        return startDate;
    }

    public List<FbsBookingDetail> getBookingTemps() {
        return bookingTemps;
    }

    public String[] getFloorList() {
        return floorList;
    }

    public void setFloorList(String[] floorList) {
        this.floorList = floorList;
    }

    public String getViewStatus() {
        return viewStatus;
    }

    public void setViewStatus(String viewStatus) {
        this.viewStatus = viewStatus;
    }
}
