/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.managedbean;

import com.smp.entity.FbsApplicant;
import com.smp.entity.FbsBlock;
import com.smp.entity.FbsBooking;
import com.smp.entity.FbsFlatType;
import com.smp.fbs.FbsFlat;
import com.smp.session.FbsApplicantFacade;
import com.smp.session.FbsBlockFacade;
import com.smp.session.FbsBookingFacade;
import com.smp.session.FbsFlatTypeFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

@ManagedBean(name = "searchManagedBean")
@SessionScoped
@Stateless
public class SearchManagedBean implements Serializable {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
    @EJB
    FbsBlockFacade fbsBlockFacade;
    @EJB
    FbsFlatTypeFacade fbsFlatTypeFacade;
    @EJB
    FbsApplicantFacade fbsApplicantFacade;
    @EJB
    FbsBookingFacade fbsBookingFacade;
    private List<FbsFlat> flat;

    private List<FbsFlat> flatTemp;
    private List<FbsFlat> flatByDate;
    private List<FbsFlat> refFlat;
    private List<FbsFlat> tempRefFlat;
    List<FbsFlat> refFlatList;
    List<FbsFlat> fbsFlatList;
    private String[] blockNames;
    private String[] floorNames;
    private String[] floorListing;
    private String[] status = {"Booked", "Unbooked"};
    private String[] flatTypes;
    public SelectItem[] blockNameOption;
    public SelectItem[] floorOption;
    public SelectItem[] statusOption;
    public SelectItem[] flatTypeOption;
    public SelectItem[] floorOptionByBlock;
    private Date startDate;
    private Date endDate;
    public String[] floorList;
    String xmlFile = "";
    String opBlockName;
    String opFlatSpecification;
    String opFloor;
    String opStatus;
    String opApplicantName;
    Date opStartDate;
    Date opEndDate;
    boolean blok;
    boolean flor;
    boolean fltSpeci;
    boolean nam;
    boolean namTemp;
    boolean dat;
    boolean Stats;
    boolean[] flOption = new boolean[6];
    private FbsBlock fbsBlock;

    public SearchManagedBean() {
        floorList = null;
        opBlockName = "";
        opFlatSpecification = "";
        opFloor = "";
        opStatus = "";
        opApplicantName = "";
        opStartDate = null;
        opEndDate = null;
        blok = false;
        flor = false;
        fltSpeci = false;
        nam = false;
        dat = false;
        Stats = false;
        flOption[0] = false;
        flOption[1] = false;
        flOption[2] = false;
        flOption[3] = false;
        flOption[4] = false;
        flOption[5] = false;

    }

    @PostConstruct
    public void PopulateFlatsDetail() //Set ALL DETAIL OF EACH FLAT IN flat ARRAYLIST
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);
        String projId = (String) session.getAttribute("projId");
        int a = 0, b = 0, length = 0;
        int f = 0;
        this.endDate = null;
        this.startDate = null;
        flat = new ArrayList<FbsFlat>();
if(projId!=null)
{
        List<FbsBlock> fbsBlock = em.createNamedQuery("FbsBlock.findByFkProjId").setParameter("fkProjId", Integer.parseInt(projId)).getResultList();

        int siz = fbsBlock.size();
        blockNames = new String[siz];

        for (int n = 0; n < siz; n++) {
            blockNames[n] = fbsBlock.get(n).getBlockName();
        }
        blockNameOption = createFilterOption(blockNames);
        statusOption = createFilterOption(status);
        try {
            int l = 0;
            List<FbsApplicant> fbsApplicant = fbsApplicantFacade.findAll();
            List<FbsBooking> fbsBooking = fbsBookingFacade.findAll();
            List<FbsFlatType> flatType = em.createNamedQuery("FbsFlatType.findByFkProjId").setParameter("fkProjId", Integer.parseInt(projId)).getResultList();
            flatTypes = new String[flatType.size()];
            for (int i = 0; i < flatType.size(); i++) {
                flatTypes[i] = flatType.get(i).getFlatSpecification();
            }
            floorListing = new String[siz];
            for (int n = 0; n < siz; n++) {
                int blockId = fbsBlock.get(n).getBlockId();

                  xmlFile = "";
                FbsBlock list = fbsBlockFacade.find(blockId);
                xmlFile = list.getXmlFile();
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(new InputSource(new StringReader(xmlFile)));
                doc.getDocumentElement().normalize();
                NodeList block = doc.getElementsByTagName("block");
                NodeList floorList = block.item(0).getChildNodes();
                floorListing[n] = String.valueOf(floorList.getLength());
                for (int i = 0; i < floorList.getLength(); i++) {
                    Node floor = floorList.item(i);
                    if (floor.getNodeType() == Node.ELEMENT_NODE) {
                        Element floorElement = (Element) floor;
                        String floorId1 = floorElement.getAttribute("floor_id").trim();
                        NodeList floorNoList = floorElement.getElementsByTagName("floor_number");
                        Node fnoElement = (Node) floorNoList.item(0);
                        String floorNo = fnoElement.getTextContent();
                        NodeList flatList = floorElement.getElementsByTagName("flat");
                        for (int j = 0; j < flatList.getLength(); j++) {
                            FbsFlat flat1 = new FbsFlat();
                            Element flatElement = (Element) flatList.item(j);
                            String flatId = flatElement.getAttribute("flat_id").trim();
                            flat1.setFlatId(Long.parseLong(flatId));
                            NodeList flatTypeList = flatElement.getElementsByTagName("flattype");
                            Element typeElement = (Element) flatTypeList.item(0);
                            flat1.setFlatType(typeElement.getAttribute("flatTypeId").trim());
                            String flatTypeId = flat1.getFlatType();
                            for (int k = 0; k < flatType.size(); k++) {
                                String flatTypeID = flatType.get(k).getFlatTypeId().toString();
                                if (flatTypeId.equals(flatTypeID)) {
                                    flat1.setFlatTypeSpecification(flatType.get(k).getFlatSpecification());
                                }
                            }
                            NodeList flatNoList = flatElement.getElementsByTagName("flatno");
                            Element noElement = (Element) flatNoList.item(0);
                            flat1.setFlatNo(noElement.getAttribute("flatNo").trim());
                            flat1.setfkFloorId(Long.parseLong(floorId1));
                            flat1.setFloorNo(floorNo);
                            if (Integer.parseInt(flat1.getFloorNo()) > f) {
                                f = Integer.parseInt(flat1.getFloorNo());

                            }
                            flat1.setProjId(Integer.parseInt(projId.trim()));
                            flat1.setBlockId(blockId);
                            FbsBlock fbsBlock1 = fbsBlockFacade.find(blockId);
                            flat1.setBlockName(fbsBlock1.getBlockName());


                            for (int k = 0; k < fbsApplicant.size(); k++) {
                                String flat3 = flat1.getFlatId() + "";
                                Integer app = fbsApplicant.get(k).getFlatId();
                                if (flat3.trim().equals(app.toString())) {
                                    flat1.setApplicantName(fbsApplicant.get(k).getApplicantName());
                                    break;
                                } else {
                                    flat1.setApplicantName("N/A");
                                }
                            }
                            if (fbsApplicant.size() < 1) {
                                flat1.setApplicantName("N/A");
                            }

                            for (int k = 0; k < fbsBooking.size(); k++) {

                                String flat3 = flat1.getFlatId() + "";
                                String app = fbsBooking.get(k).getFlatId() + "";
                                if (flat3.trim().equals(app.trim())) {
                                    String status = fbsBooking.get(k).getStatus();
                                    if (status.equals("b") || status.equals("t")) {

                                        flat1.setBookDate1(fbsBooking.get(k).getBookingDt().toString());
                                        flat1.setBookDate(fbsBooking.get(k).getBookingDt());
                                        flat1.setStatus("Booked");
                                        break;
                                    } else {
                                        flat1.setBookDate1("");
                                        flat1.setBookDate(fbsBooking.get(k).getBookingDt());
                                        flat1.setStatus("Unbooked");
                                    }
                                } else {
                                    flat1.setBookDate1("");
                                    flat1.setBookDate(fbsBooking.get(k).getBookingDt());
                                    flat1.setStatus("Unbooked");
                                }
                            }
                            if (fbsBooking.size() < 1) {
                                flat1.setStatus("Unbooked");
                                flat1.setBookDate1("");
                                flat1.setBookDate(new Date());
                            }

                            flat.add(l, flat1);
                            l++;
                        }

                    }

                }

            }
            floorNames = new String[f + 1];
            for (int n = 0; n < floorNames.length; n++) {
                floorNames[n] = String.valueOf(n);
            }
            flatTypeOption = createFilterOption(flatTypes);
            floorOption = createFilterOption(floorNames);
            floorOptionByBlock = createFilterOption(floorListing);
            this.refFlat = this.flat;

        } catch (Exception e) {
            System.out.println(e + e.toString());
        }
    }
    }
    public void populateFlatByFilter() {
        flatTemp = new ArrayList<FbsFlat>();
        flatTemp.addAll(this.flat);
//        System.out.println("Flat List-->" + flatTemp.size());
//        System.out.println("NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN ");
//        System.out.println("Select Block-->" + this.opBlockName);
//        System.out.println("Select FlatSpecification-->" + this.opFlatSpecification);
//        System.out.println("Select Floor-->" + this.opFloor);
//        System.out.println("Select Status-->" + this.opStatus);
//        System.out.println("Select ApplicantName-->" + this.opApplicantName);
//        System.out.println("Select StartDate-->" + this.opStartDate);
//        System.out.println("Select EndDate-->" + this.opEndDate);
//        System.out.println("+++++++++++++++++++++++++++++++++++++++ ");
//        System.out.println("blok-->" + this.blok);
//        System.out.println("fltSpeci-->" + this.fltSpeci);
//        System.out.println("flor-->" + this.flor);
//        System.out.println("stats-->" + this.Stats);
//        System.out.println("dat-->" + this.dat);

        int l = 0;

        if ((opBlockName.equals("Select Block")) && (opFloor.equals("Select Floor")) && (opFlatSpecification.equals("Select Specification")) && (opApplicantName.equals("")) && (opStartDate == null || opEndDate == null)) {
            l = 0;
//            System.out.println("All are in select mode");

        } else if ((opBlockName.equals("Select Block")) && (!(this.dat))) {
//            System.out.println("All are in  ");
            reset();

        } else {

            if (this.blok) {
                flOption[0] = this.blok;
            }
            if (this.fltSpeci) {
                flOption[1] = this.fltSpeci;
            }
            if (this.flor) {
                flOption[2] = this.flor;
            }
            if (this.Stats) {
                flOption[3] = this.Stats;
            }

            if (this.nam) {
                flOption[4] = this.nam;
            }
            if (this.dat) {
                flOption[5] = this.dat;
            }
            if (flOption[0]) {
                flatTemp = filterByBlock(flatTemp, this.opBlockName);
//                System.out.println("Flat List 1-->" + flatTemp.size());
            }
            if (flOption[1]) {
                flatTemp = filterByFlatSpecification(flatTemp, opFlatSpecification);
//                System.out.println("Flat List 2-->" + flatTemp.size());
            }
            if (flOption[2]) {
                flatTemp = filterByFloor(flatTemp, opFloor);
//                System.out.println("Flat List 3-->" + flatTemp.size());
            }
            if (flOption[3]) {
                flatTemp = filterByStatus(flatTemp, opStatus);
//                System.out.println("Flat List 4-->" + flatTemp.size());
            }
            if (flOption[4]) {
                flatTemp = filterByApplicantName(flatTemp, opApplicantName);
//                System.out.println("Flat List 5-->" + flatTemp.size());
            }
            if (flOption[5]) {
                flatTemp = filterByDate(flatTemp, opStartDate, opEndDate);
//                System.out.println("Flat List 6-->" + flatTemp.size());
            }
        }
        this.refFlat = this.flatTemp;
        this.blok = false;
        this.flor = false;
        this.fltSpeci = false;
        this.nam = false;

    }

    public List<FbsFlat> filterByBlock(List<FbsFlat> flatTemps, String opBlockName) {
        int k = 0;
        List<FbsFlat> flatTemps1 = new ArrayList<FbsFlat>();
        for (int i = 0; i < flatTemps.size(); i++) {
            if (flatTemps.get(i).getBlockName().equals(opBlockName)) {
                flatTemps1.add(k, flatTemps.get(i));
                k++;
            }
        }
        return flatTemps1;
    }

    public List<FbsFlat> filterByFlatSpecification(List<FbsFlat> flatTemps, String opFlatSpecification) {
        int k = 0;
        List<FbsFlat> flatTemps1 = new ArrayList<FbsFlat>();
        for (int i = 0; i < flatTemps.size(); i++) {
            if (flatTemps.get(i).getFlatTypeSpecification().equals(opFlatSpecification)) {
                flatTemps1.add(k, flatTemps.get(i));
                k++;
            }
        }
        return flatTemps1;
    }

    public List<FbsFlat> filterByFloor(List<FbsFlat> flatTemps, String opFloor) {
        int k = 0;
        List<FbsFlat> flatTemps1 = new ArrayList<FbsFlat>();
        for (int i = 0; i < flatTemps.size(); i++) {
            if (flatTemps.get(i).getFloorNo().equals(opFloor)) {
                flatTemps1.add(k, flatTemps.get(i));
                k++;
            }
        }
        return flatTemps1;
    }

    public List<FbsFlat> filterByStatus(List<FbsFlat> flatTemps, String opStatus) {
        int k = 0;
        List<FbsFlat> flatTemps1 = new ArrayList<FbsFlat>();
        for (int i = 0; i < flatTemps.size(); i++) {
            if (flatTemps.get(i).getStatus().equals(opStatus)) {
                flatTemps1.add(k, flatTemps.get(i));
                k++;
            }
        }
        return flatTemps1;
    }

    public List<FbsFlat> filterByApplicantName(List<FbsFlat> flatTemps, String name) {
        int k = 0;
        boolean nameStatus = false;
        List<FbsFlat> flatTemps1 = new ArrayList<FbsFlat>();
        for (int i = 0; i < flatTemps.size(); i++) {
            nameStatus = flatTemps.get(i).getApplicantName().toUpperCase().contains(name.toUpperCase());
            if (nameStatus) {

                flatTemps1.add(k, flatTemps.get(i));

                k++;
            }
        }
        return flatTemps1;

    }

    public List<FbsFlat> filterByDate(List<FbsFlat> flatTemps, Date opStartDate, Date opEndDate) {
        int k = 0;
        List<FbsFlat> flatTemps1 = new ArrayList<FbsFlat>();
        if ((opStartDate == null) || (opEndDate == null)) {
//            System.out.println("Start date & End Date is Null");
             resetDateOption();
            return flatTemps;

        }
        if (opStartDate.after(opEndDate)) {
            Date temp = opEndDate;
            opEndDate = opStartDate;
            opStartDate = temp;
            this.opStartDate = opStartDate;
            this.opEndDate = opEndDate;
        }
        for (int i = 0; i < flatTemps.size(); i++) {
            if (flatTemps.get(i).getBookDate1().equals("")) {
                continue;
            } else {
                if (((flatTemps.get(i).getBookDate().after(opStartDate)) || (flatTemps.get(i).getBookDate().equals(opStartDate))) && ((flatTemps.get(i).getBookDate().before(opEndDate))) || (flatTemps.get(i).getBookDate().equals(opEndDate))) {

                    flatTemps1.add(k, flatTemps.get(i));
                    k++;
                }
            }
        }
        return flatTemps1;

    }

    public void populateFloor() {
        if (!(this.opBlockName.equals("Select Block")) && (!(this.opBlockName.equals("")))) {

            fbsBlock = (FbsBlock) em.createNamedQuery("FbsBlock.findByBlockName").setParameter("blockName", this.opBlockName).getResultList().get(0);
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
                        l++;
                    }
                }
//                System.out.println("Floor List Value-->" + floorList1.getLength());
//                System.out.println("L-->" + l);
                if (floorList1.getLength() == 0) {
                    this.floorList = null;

                } else {
                    floorList = new String[l];
                    for (int i = 0; i < l; i++) {
                        floorList[i] = String.valueOf(i);
                    }
                }
            } catch (Exception ex) {
            }
        } else {
            this.floorList = null;
             
        }

        populateFlatByFilter();
    }

    public void reset() {

        resetDateOption();
        resetNameOption();
        resetblockOption();
        resetFloorOption();
        resetStatusOption();
        resetFlatSpecificationOption();
        resetArrayOfOption();
        refFlat = this.flat;
    }

    public void resetFlatSpecificationOption() {
        this.opFlatSpecification = "";
        this.fltSpeci = false;
        this.flOption[1] = false;
    }

    public void resetFlatSpecificationOptionOnSelect() {
        this.opFlatSpecification = "";
        this.fltSpeci = false;
        this.flOption[1] = false;
    }

    public void resetFloorOption() {
        this.opFloor = "";
        this.flor = false;
        this.floorListing = null;
        this.flOption[2] = false;
    }

    public void resetFloorOptionOnSelect() {
        this.opFloor = "";
        this.flor = false;
        this.flOption[2] = false;
    }

    public void resetStatusOption() {
        this.opStatus = "";
        this.Stats = false;
        this.flOption[3] = false;
    }

    public void resetblockOption() {
        this.opBlockName = "";
        this.blok = false;
        this.flOption[0] = false;
    }

    public void resetNameOption() {
        this.opApplicantName = "";
        this.nam = false;
        this.flOption[4] = false;
    }

    public void resetDateOption() {
        this.opStartDate = null;
        this.opEndDate = null;
        flOption[5] = false;
    }

    public void resetArrayOfOption() {
        flOption[0] = false;
        flOption[1] = false;
        flOption[2] = false;
        flOption[3] = false;
        flOption[4] = false;
        flOption[5] = false;

    }

    public SelectItem[] createFilterOption(String[] data) {
        SelectItem[] option = new SelectItem[data.length + 1];
        option[0] = new SelectItem("", "select");
        for (int i = 0; i < data.length; i++) {
            option[i + 1] = new SelectItem(data[i], data[i]);
        }
        return option;
    }

    public SelectItem[] createFilterOptionByBlock() {
        String[] data = new String[2];
        return createFilterOption(data);
    }

    public SelectItem[] getBlockNameOption() {
        return this.blockNameOption;
    }

    public SelectItem[] getFloorOption() {
        return this.floorOption;
    }

    public SelectItem[] getFloorOptionByBlock() {
        return this.floorOptionByBlock;
    }

    public SelectItem[] getStatusOption() {
        return this.statusOption;
    }

    public SelectItem[] getFlatTypeOption() {

        return this.flatTypeOption;
    }

    public void setFlat(FbsFlat fbsFlat) {
        this.flat = (List<FbsFlat>) fbsFlat;
    }

    public List<FbsFlat> getFlat() {
        return this.flat;
    }

    public void setFlatByDate(FbsFlat fbsFlat) {
        this.flatByDate = (List<FbsFlat>) fbsFlat;
    }

    public List<FbsFlat> getFlatByDate() {
        return this.flatByDate;
    }

    public void setRefFlat(FbsFlat fbsFlat) {
        this.refFlat = (List<FbsFlat>) fbsFlat;
    }

    public List<FbsFlat> getRefFlat() {
        return this.refFlat;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;

    }

    public Date getStartDate() {

        return this.startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;

    }

    public Date getEndDate() {

        return this.endDate;
    }

    public void setTempRefFlat(List<FbsFlat> tempRefFlat) {
        this.tempRefFlat = tempRefFlat;
    }

    public List<FbsFlat> getTempRefFlat() {
        return tempRefFlat;
    }

    public String[] getBlockNames() {
        return blockNames;
    }

    public void setBlockNames(String[] blockNames) {
        this.blockNames = blockNames;
    }

    public void setFlatTypes(String[] flatTypes) {
        this.flatTypes = flatTypes;
    }

    public String[] getFlatTypes() {
        return flatTypes;
    }

    public void setOpApplicantName(String opApplicantName) {
        this.opApplicantName = opApplicantName;
        if (!(opApplicantName.equals("")) && !(opApplicantName.equals("Applicant Name"))) {
            this.nam = true;

        } else {
            resetNameOption();
        }
    }

    public void setOpBlockName(String opBlockName) {
        this.opBlockName = opBlockName;
        if ((!(opBlockName.equals(""))) && (!(opBlockName.equals("Select Block")))) {
            this.blok = true;
        } else {
            this.blok = false;
        }
    }

    public void setOpFlatSpecification(String opFlatSpecification) {
        this.opFlatSpecification = opFlatSpecification;
        if ((!(opFlatSpecification.equals(""))) && (!(opFlatSpecification.equals("Select Specification")))) {
            this.fltSpeci = true;
        } else {
            resetFlatSpecificationOptionOnSelect();
        }
    }

    public void setOpFloor(String opFloor) {
        this.opFloor = opFloor;
        if ((!(opFloor.equals(""))) && (!(opFloor.equals("Select Floor")))) {
            this.flor = true;
        } else {
            resetFloorOptionOnSelect();
        }
    }

    public void setOpStatus(String opStatus) {
        this.opStatus = opStatus;
        if ((!(opStatus.equals(""))) && (!(opStatus.equals("Select Status")))) {
            this.Stats = true;
        } else {
            resetStatusOption();
        }
    }

    public void setOpStartDate(Date opStartDate) {
        this.opStartDate = opStartDate;
        this.dat = false;
    }

    public Date getOpStartDate() {
        return opStartDate;
    }

    public void setOpEndDate(Date opEndDate) {
        this.opEndDate = opEndDate;
        this.dat = true;
    }

    public Date getOpEndDate() {
        return opEndDate;
    }

    public String getOpApplicantName() {
        return opApplicantName;
    }

    public String getOpBlockName() {
        return opBlockName;
    }

    public String getOpFlatSpecification() {
        return opFlatSpecification;
    }

    public String getOpFloor() {
        return opFloor;
    }

    public String getOpStatus() {
        return opStatus;
    }

    public String[] getStatus() {
        return status;
    }

    public void setStatus(String[] status) {
        this.status = status;
    }

    public String[] getFloorListing() {
        return floorListing;
    }

    public void setFloorListing(String[] floorListing) {
        this.floorListing = floorListing;
    }

    public void setFlatTemp(List<FbsFlat> flatTemp) {
        this.flatTemp = flatTemp;
    }

    public List<FbsFlat> getFlatTemp() {
        return flatTemp;
    }

    public void setFloorList(String[] floorList) {
        this.floorList = floorList;
    }

    public String[] getFloorList() {
        return floorList;
    }
 
}
