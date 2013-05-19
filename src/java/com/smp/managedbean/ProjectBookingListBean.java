/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.managedbean;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import com.smp.entity.FbsApplicant;
import com.smp.entity.FbsBlock;
import com.smp.entity.FbsBooking;
import com.smp.entity.FbsBookingTemp;
import com.smp.entity.FbsDiscount;
import com.smp.entity.FbsFlat;
import com.smp.entity.FbsProject;
import com.smp.fbs.FbsBookingDetail;
import com.smp.session.FbsApplicantFacade;
import com.smp.session.FbsBlockFacade;
import com.smp.session.FbsBookingFacade;
import com.smp.session.FbsBookingTempFacade;
import com.smp.session.FbsDiscountFacade;
import com.smp.session.FbsProjectFacade;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author Mohit SMP 0010
 */
@ManagedBean(name = "projectBookingListBean")
@SessionScoped
@Stateless
public class ProjectBookingListBean implements Serializable {

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
    FbsBookingTempFacade fbsBookingTempFacade;
    List<FbsBooking> fbsBookingList;
    List<FbsBookingTemp> bookingList;
    List refBookingList;
    List<FbsBookingTemp> bookingListByDate;
    List<FbsBookingTemp> bookingList2;
    List<FbsApplicant> fbsApplicantList = new ArrayList<FbsApplicant>();
    List<FbsBookingTemp> fbsBookingDetails;
    List<FbsBooking> bookings;
    List<FbsBookingDetail> fbsBookingT;
    List<FbsFlat> fbsFlatList;
    TreeSet<String> treeSet = new TreeSet<String>();
    TreeSet<String> treeSet1 = new TreeSet<String>();
    List<FbsFlat> refFlatList = new ArrayList<FbsFlat>();
    TreeSet<FbsBookingTemp> treeSet2 = new TreeSet<FbsBookingTemp>();
    public ArrayList flatNoList = new ArrayList();
    List<FbsBookingTemp> bookingTemps;
    FbsBookingTemp fbsBookingDetail;
    FbsBlock fbsBlock;
    FbsProject fbsProject;
    FbsApplicant fbsApplicant;
    FbsDiscount fbsDiscount;
    Date startDate;
    Date endDate;
    String blockName;
    String floorNO;
    String flatNO;
    String name;
    String xmlFile = "";
    public Boolean startDtStatus;
    public Boolean endDtStatus;
    public SelectItem[] blockNameOption;
    private String[] blockNames;
    public List<TreeSet> floorNoOption;
    public List<TreeSet> flatNoOption;
    private String[] floorNos;
    private String[] flats;
    public String[] floorList;
    boolean status;
    boolean blok;
    boolean flor;
    boolean flt;
    boolean nam;
    boolean namTemp;
    boolean dat;
    boolean[] flOption = new boolean[5];

    public ProjectBookingListBean() {
        this.blockName = "";
        this.floorNO = "";
        this.flatNO = "";
        this.name = "";
        this.startDate = null;
        this.endDate = null;
        status = false;
        namTemp = true;
        flOption[0] = false;
        flOption[1] = false;
        flOption[2] = false;
        flOption[3] = false;
        flOption[4] = false;
    }

    @PostConstruct
    public void populateBookingDetail() {

        int l = 0;
        bookingList = new ArrayList<FbsBookingTemp>();
        fbsBookingList = new ArrayList<FbsBooking>();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);
        String projId1 = (String) session.getAttribute("projId");
        if(projId1!=null)
        {
        fbsBookingList = fbsBookingFacade.findAll();
        fbsBookingDetails = new ArrayList<FbsBookingTemp>();
        floorNoOption = new ArrayList<TreeSet>();
        for (int i = 0; i < fbsBookingList.size(); i++) {
            int blockId = fbsBookingList.get(i).getBlockId();
            fbsBlock = fbsBlockFacade.find(blockId);
            long projId2 = (long) fbsBlock.getFkProjId();
            if (projId1.equals(String.valueOf(projId2))) {
                fbsBookingDetail = new FbsBookingTemp();
                fbsBookingDetail.setRegNumber(fbsBookingList.get(i).getRegNumber());
                fbsBookingDetail.setBookingDt(fbsBookingList.get(i).getBookingDt());
                fbsBlock = fbsBlockFacade.find(blockId);
                fbsBookingDetail.setBlockNo(fbsBlock.getBlockName());
                int regNo = fbsBookingList.get(i).getRegNumber();
                List<FbsApplicant> applicants=em.createNamedQuery("FbsApplicant.findByFlatId").setParameter("flatId", fbsBookingList.get(i).getFlatId()).getResultList();

                 fbsApplicant=applicants.get(applicants.size()-2);
             //    coFbsApplicant=applicants.get(applicants.size()-1);
             //   fbsApplicant = (FbsApplicant) em.createNamedQuery("FbsApplicant.findByFlatId").setParameter("flatId", fbsBookingList.get(i).getFlatId()).getResultList().get(0);
                fbsBookingDetail.setApplicantName(fbsApplicant.getApplicantName());
                fbsBookingDetail.setFlatNo(fbsBookingList.get(i).getFlatId().toString());
                treeSet1.add(String.valueOf(fbsBookingDetail.getFlatNo()));
                fbsBookingDetail.setFloorNo(populateFloorNo(String.valueOf(fbsBookingDetail.getFlatNo())));
                treeSet.add(fbsBookingDetail.getFloorNo().toString().trim());
                fbsBookingDetail.setFlatId(fbsBookingList.get(i).getFlatId().toString());
                bookingList.add(l, fbsBookingDetail);

                l++;
            }
            floorNoOption.add(treeSet);
        }
        List<FbsBlock> fbsBlock = em.createNamedQuery("FbsBlock.findByFkProjId").setParameter("fkProjId", Integer.parseInt(projId1)).getResultList();
        int siz = fbsBlock.size();
        blockNames = new String[siz];
        for (int n = 0; n < siz; n++) {
            blockNames[n] = fbsBlock.get(n).getBlockName();
        }
        floorNos = new String[treeSet.size()];
        Iterator iterator = treeSet.iterator();
        l = 0;
        while (iterator.hasNext()) {
            floorNos[l] = (String) iterator.next();
            l++;
        }
        flats = new String[treeSet1.size()];
        Iterator iterator1 = treeSet1.iterator();
        l = 0;
        while (iterator1.hasNext()) {
            flats[l] = (String) iterator1.next();
            l++;
        }
        this.refBookingList = bookingList;

        treeSet.clear();
        treeSet1.clear();

    }
    }
    public void populateBookingDetailByFilter() {

        populateBookingDetail();
        bookingTemps = this.bookingList;
        int l = 0;

        if ((blockName.equals("Select Block")) && (floorNO.equals("Select Floor")) && (flatNO.equals("Select Flat")) && (name.equals("")) && (startDate == null || endDate == null)) {
            l = 0;

            bookingTemps = this.bookingList;
        } else if ((blockName.equals("Select Block")) && (!(this.dat))) {

            reset();

        } else {

            if (this.blok) {
                flOption[0] = this.blok;
            }
            if (this.flor) {
                flOption[1] = this.flor;
            }
            if (this.flt) {
                flOption[2] = this.flt;
            }
            if (this.nam) {
                flOption[3] = this.nam;
            }
            if (this.dat) {
                flOption[4] = this.dat;
            }
            if (flOption[0]) {
                bookingTemps = filterByBlock(bookingTemps, this.blockName);
            }
            if (flOption[1]) {
                bookingTemps = filterByFloor(bookingTemps, floorNO);
            }
            if (flOption[2]) {
                bookingTemps = filterByFlat(bookingTemps, flatNO);
            }
            if (flOption[3]) {
                bookingTemps = filterByName(bookingTemps, name);
            }
            if (flOption[4]) {
                bookingTemps = populateBookingDetailByDateFilter(bookingTemps, startDate, endDate);
            }
        }
        refBookingList = bookingTemps;
        this.blok = false;
        this.flor = false;
        this.flt = false;
        this.nam = false;
        this.dat = false;
    }

    public List<FbsBookingTemp> filterByBlock(List<FbsBookingTemp> fbsBookingTemps, String blockName) {
        int k = 0;
        List<FbsBookingTemp> bookingTemps1 = new ArrayList<FbsBookingTemp>();
        for (int i = 0; i < fbsBookingTemps.size(); i++) {
            if (fbsBookingTemps.get(i).getBlockNo().equals(blockName)) {
                bookingTemps1.add(k, fbsBookingTemps.get(i));
                k++;
            }
        }
        return bookingTemps1;
    }

    public List<FbsBookingTemp> filterByFloor(List<FbsBookingTemp> fbsBookingTemps, String floorNo) {
        int k = 0;
        List<FbsBookingTemp> bookingTemps1 = new ArrayList<FbsBookingTemp>();
        for (int i = 0; i < fbsBookingTemps.size(); i++) {
            if (fbsBookingTemps.get(i).getFloorNo().equals(floorNo)) {
                bookingTemps1.add(k, fbsBookingTemps.get(i));
                k++;
            }
        }
        return bookingTemps1;
    }

    public List<FbsBookingTemp> filterByFlat(List<FbsBookingTemp> fbsBookingTemps, String flatNo) {
        int k = 0;
        List<FbsBookingTemp> bookingTemps1 = new ArrayList<FbsBookingTemp>();
        for (int i = 0; i < fbsBookingTemps.size(); i++) {

            if (fbsBookingTemps.get(i).getFlatNo().equals(flatNo)) {

                bookingTemps1.add(k, fbsBookingTemps.get(i));

                k++;
            }
        }
        return bookingTemps1;
    }

    public List<FbsBookingTemp> filterByName(List<FbsBookingTemp> fbsBookingTemps, String name) {
        int k = 0;
        boolean nameStatus = false;
        List<FbsBookingTemp> bookingTemps1 = new ArrayList<FbsBookingTemp>();
        for (int i = 0; i < fbsBookingTemps.size(); i++) {
            nameStatus = fbsBookingTemps.get(i).getApplicantName().toUpperCase().contains(name.toUpperCase());

            if (nameStatus) {

                bookingTemps1.add(k, fbsBookingTemps.get(i));

                k++;
            }
        }
        return bookingTemps1;

    }

    public List<FbsBookingTemp> populateBookingDetailByDateFilter(List<FbsBookingTemp> fbsBookingTemps, Date startDate, Date endDate) {
        int k = 0;
        List<FbsBookingTemp> bookingTemps1 = new ArrayList<FbsBookingTemp>();
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

            if (((fbsBookingTemps.get(i).getBookingDt().after(startDate)) || (fbsBookingTemps.get(i).getBookingDt().equals(startDate))) && ((fbsBookingTemps.get(i).getBookingDt().before(endDate))) || (fbsBookingTemps.get(i).getBookingDt().equals(endDate))) {

                bookingTemps1.add(k, fbsBookingTemps.get(i));
                k++;
            }
        }
        return bookingTemps1;
    }

//    public boolean checkApplicantName(String argName) {
//        int length = this.name.length();
//        String tmpStr = argName.substring(0, length);
//        if (this.name.toUpperCase().equals(tmpStr.toUpperCase())) {
//            return true;
//        } else {
//            return false;
//        }
//    }

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
            System.out.println("hi");
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

    public void populateTextBox() {
        this.namTemp = false;
        this.nam = true;
    }

    public void reset() {

        resetDateOption();
        resetNameOption();
        resetblockOption();
        resetFloorOption();
        resetFlatOption();
        resetArrayOfOption();
        populateBookingDetail();
    }

    public void resetFlatOption() {
        this.flatNO = "";
        this.flt = false;
        this.flatNoList = null;
        this.flOption[2] = false;
    }

    public void resetFlatOptionOnSelect() {
        this.flatNO = "";
        this.flt = false;
        this.flOption[2] = false;
    }

    public void resetFloorOption() {
        this.floorNO = "";
        this.flor = false;
        this.floorList = null;
        this.flOption[1] = false;
    }

    public void resetFloorOptionOnSelect() {
        this.floorNO = "";
        this.flor = false;
        this.flOption[1] = false;
    }

    public void resetblockOption() {
        this.blockName = "";
        this.blok = false;
        this.flOption[0] = false;
    }

    public void resetNameOption() {
        this.name = "";
        this.nam = false;
        this.flOption[3] = false;
    }

    public void resetDateOption() {
        this.startDate = null;
        this.endDate = null;
    }

    public void resetArrayOfOption() {
        flOption[0] = false;
        flOption[1] = false;
        flOption[2] = false;
        flOption[3] = false;
    }

    public String populateFloorNo(String flatId) {
        return flatId.substring((flatId.length() - 3), (flatId.length() - 2));
    }

    public List<FbsBookingTemp> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<FbsBookingTemp> bookingList) {
        this.bookingList = bookingList;
    }

    public List<FbsBookingTemp> getRefBookingList() {
        return refBookingList;
    }

    public void setRefBookingList(List<FbsBookingTemp> refBookingList) {
        this.refBookingList = refBookingList;
    }

    public Date getStartDate() {
        return startDate;
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

    public SelectItem[] getBlockNameOption() {
        return blockNameOption;
    }

    public void setBlockNameOption(SelectItem[] blockNameOption) {
        this.blockNameOption = blockNameOption;
    }

    public String[] getFloorNos() {
        return floorNos;
    }

    public void setFloorNos(String[] floorNos) {
        this.floorNos = floorNos;
    }

    public String[] getBlockNames() {
        return blockNames;
    }

    public void setBlockNames(String[] blockNames) {
        this.blockNames = blockNames;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
        if ((!(blockName.equals(""))) && (!(blockName.equals("Select Block")))) {
            this.blok = true;
        } else {
            this.blok = false;
        }
    }

    public String getBlockName() {
        return blockName;
    }

    public String getFloorNO() {
        return floorNO;
    }

    public void setFloorNO(String floorNO) {

        this.floorNO = floorNO;
        if ((!(floorNO.equals(""))) && (!(floorNO.equals("Select Floor")))) {
            this.flor = true;
        } else {
            this.flor = false;
        }
    }

    public TreeSet<String> getTreeSet1() {
        return treeSet1;
    }

    public void setTreeSet1(TreeSet<String> treeset1) {
        this.treeSet1 = treeset1;
    }

    public String[] getFlats() {
        return flats;
    }

    public void setFlats(String[] flats) {
        this.flats = flats;

    }

    public String getFlatNO() {
        return flatNO;
    }

    public void setFlatNO(String flatNO) {
        this.flatNO = flatNO;
        if ((!(flatNO.equals(""))) && (!(flatNO.equals("Select Flat")))) {
            this.flt = true;
        } else {
            resetFlatOptionOnSelect();
        }
    }

    public void setBookings(List<FbsBooking> bookings) {
        this.bookings = bookings;
    }

    public List<FbsBooking> getBookings() {
        return bookings;
    }

    public List<FbsBookingDetail> getFbsBookingT() {
        return fbsBookingT;
    }

    public void setFbsBookingT(List<FbsBookingDetail> fbsBookingT) {
        this.fbsBookingT = fbsBookingT;
    }

    public void setName(String name) {
        this.name = name;
        if (!(name.equals(""))) {
            this.nam = true;
            this.namTemp = false;
        } else {
            this.nam = false;
        }
    }

    public String getName() {
        return name;
    }

    public void setFloorList(String[] floorList) {
        this.floorList = floorList;
    }

    public String[] getFloorList() {
        return floorList;
    }

    public void setFlatNoList(ArrayList flatNoList) {
        this.flatNoList = flatNoList;
    }

    public ArrayList getFlatNoList() {
        return flatNoList;
    }

    public Boolean getStartDtStatus() {
        return startDtStatus;
    }

    public void setStartDtStatus(Boolean startDtStatus) {
        this.startDtStatus = startDtStatus;
    }

    public Boolean getEndDtStatus() {
        return endDtStatus;
    }

    public void setEndDtStatus(Boolean endDtStatus) {
        this.endDtStatus = endDtStatus;
    }

    public void setBookingTemps(List<FbsBookingTemp> bookingTemps) {
        this.bookingTemps = bookingTemps;
    }

    public List<FbsBookingTemp> getBookingTemps() {
        return bookingTemps;
    }

    public void setNamTemp(boolean namTemp) {
        this.namTemp = namTemp;
    }

    public boolean getNamTemp() {
        return this.namTemp;
    }

    public void setNam(boolean nam) {
        this.nam = nam;
    }

    public boolean getNam() {
        return this.nam;
    }
}
