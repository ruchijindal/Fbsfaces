/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.managedbean;

import com.smp.entity.FbsBlock;
import com.smp.entity.FbsBooking;
import com.smp.entity.FbsFlat;
import com.smp.entity.FbsProject;
import com.smp.entity.FbsReport;
import com.smp.fbs.FlatInfo;
import com.smp.session.FbsBlockFacade;
import com.smp.session.FbsBookingFacade;
import com.smp.session.FbsProjectFacade;
import com.smp.session.FbsReportFacade;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author smp
 */
@ManagedBean(name = "reportArchieveBean")
@SessionScoped
public class ReportArchieveBean {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
    @EJB
    FbsReportFacade fbsReportFacade;
    @EJB
    FbsBlockFacade fbsBlockFacade;
    @EJB
    FbsProjectFacade fbsProjectFacade;
    @EJB
    FbsBookingFacade fbsBookingFacade;
    FbsReport fbsReport;
    FbsBooking fbsBooking;
    public FbsBlock fbsBlock = new FbsBlock();
    public static FbsProject fbsProject = new FbsProject();
    private List<FbsFlat> fbsFlatList = new ArrayList<FbsFlat>();
    List<FbsFlat> refFlatList = new ArrayList<FbsFlat>();
    List<FbsReport> reportList;
    // List<HmsPatient> hmsPatientList1;
    List<FbsReport> refReportList;
    public static FlatInfo flatInfo;
    public static FbsBlock fbsBlock1 = new FbsBlock();
    String xmlFile = "";
    Date date;
    Date opStartDate1;
    Date opEndDate1;
    Date opStartDate;
    Date opEndDate;
    Integer regNo;
    boolean sNam;
    public static boolean[] searchOption = new boolean[1];

    public ReportArchieveBean() {

        fbsReport = new FbsReport();
    }

    @PostConstruct
    public void populate() {

        reportList = new ArrayList<FbsReport>();
        reportList = fbsReportFacade.findAll();
        refReportList = reportList;
    }

    public void showResult() {

        List<FbsReport> list;
        list = reportList;

        if (searchOption[0]) {
//            System.out.println("in function1-->" + searchOption[0]);
//            System.out.print("firstname****** " + firstName);
            list = searchByDate(list, opStartDate1, opEndDate1);
        }
//        if (searchOption[0]) {
//            //System.out.println("in function2" + searchOption[1]);
//            list = searchByRegNo(list, regNo);
//        }


        //System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        refReportList = list;

    }

    public List<FbsReport> searchByDate(List<FbsReport> reportTemp, Date opStartDate1, Date opEndDate1) {
        // System.out.println("in function");
        int k = 0;
        List<FbsReport> reportTemp1 = new ArrayList<FbsReport>();
        if ((opStartDate1 == null) || (opEndDate1 == null)) {
            // System.out.println("Start date & End Date is Null");
            resetDateOption();
            return reportTemp;

        } else {
            //System.out.println("in else part");
            if (opStartDate1.after(opEndDate1)) {
                Date temp = opEndDate1;
                opEndDate1 = opStartDate1;
                opStartDate1 = temp;
                this.opStartDate1 = opStartDate1;
                this.opEndDate1 = opEndDate1;
            }
            //System.out.println("Payment Date");
            for (int i = 0; i < reportTemp.size(); i++) {
                //System.out.println("date==>" + hmsPatientTemp.get(i).getRegistrationDate());

                if (!(reportTemp.get(i).getDate() == null)) {
                    if (((reportTemp.get(i).getDate().after(opStartDate1)) || (reportTemp.get(i).getDate().equals(opStartDate1))) && ((reportTemp.get(i).getDate().before(opEndDate1))) || (reportTemp.get(i).getDate().equals(opEndDate1))) {

                        reportTemp1.add(k, reportTemp.get(i));
                        k++;
                    }

                }

            }

        }
        return reportTemp1;
    }

    public ArrayList<FbsReport> searchByRegNo(List<FbsReport> fbsReport, int regNo) {
        //System.out.println("populateFilterBylastName***********");
        int k = 0;

        ArrayList<FbsReport> fbsReports = new ArrayList<FbsReport>();

        for (int i = 0; i < fbsReport.size(); i++) {

            if (fbsReport.get(i).getRegNumber() == regNo) {

                fbsReports.add(k, fbsReport.get(i));
                k++;
            }
        }

        return fbsReports;
    }

    public void reset() {
        // System.out.println("**************************************");
        resetDateOption();
       // resetRegOption();
        resetArrayOfOption();


        this.refReportList = this.reportList;
        // this.refWardDetailsList = this.wardDetailList1;

        //System.out.println("ref-->"+refBookingList.size());
        //System.out.println("bookinglist-->"+bookingList.size());
    }

    public void resetDateOption() {
        this.opStartDate1 = null;
        this.opEndDate1 = null;
        searchOption[0] = false;
    }

//    public void resetRegOption() {
//        this.regNo = 0;
//        this.sNam = false;
//        ReportArchieveBean.searchOption[0] = false;
//        //  System.out.println("i m in reset++++++++++++");
//
//    }

    public void resetArrayOfOption() {
        System.out.println("##############################################");
        searchOption[0] = false;
       // searchOption[1] = false;
    }

    public void genrateReceiptforReport1(int reportId) {

      
 System.out.println("++++++++" + reportId);
        //System.out.println("++++++++" + flatId1);
        try {
            fbsReport = fbsReportFacade.find(reportId);

            fbsBooking = (FbsBooking) em.createNamedQuery("FbsBooking.findByRegNumber").setParameter("regNumber", fbsReport.getRegNumber()).getResultList().get(0);
        
          int blockId = fbsBooking.getBlockId();
            fbsBlock = fbsBlockFacade.find(blockId);
            String projId = String.valueOf(fbsBlock.getFkProjId());
              FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);
            session.setAttribute("projId", projId);
            fbsProject = fbsProjectFacade.find(Integer.parseInt(projId));
            String blockName = fbsBlock.getBlockName();
            session.setAttribute("blockName", blockName);
            //   fbsBlock = (FbsBlock) em.createNamedQuery("FbsBlock.findByBlockId").setParameter("blockId", blockId).getResultList().get(0);

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
                if (fbsFlatList.get(b).getFlatId().toString().equals(fbsBooking.getFlatId().toString())) {
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


                    FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/ReportArchieve1?companyId=" + LoginBean.fbsLogin.getCompanyId() + "&reportId=" + reportId + "");


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Date getOpEndDate() {
        return opEndDate;
    }

    public void setOpEndDate(Date opEndDate) {
        this.opEndDate = opEndDate;
    }

    public Date getOpEndDate1() {
        return opEndDate1;
    }

    public void setOpEndDate1(Date opEndDate1) {
        this.opEndDate1 = opEndDate1;

        this.searchOption[0] = true;
    }

    public Date getOpStartDate() {
        return opStartDate;
    }

    public void setOpStartDate(Date opStartDate) {
        this.opStartDate = opStartDate;
    }

    public Date getOpStartDate1() {
        return opStartDate1;
    }

    public void setOpStartDate1(Date opStartDate1) {
        this.opStartDate1 = opStartDate1;
         this.searchOption[0] = true;
    }

    public List<FbsFlat> getRefFlatList() {
        return refFlatList;
    }

    public void setRefFlatList(List<FbsFlat> refFlatList) {
        this.refFlatList = refFlatList;
    }

    public List<FbsReport> getRefReportList() {
        return refReportList;
    }

    public void setRefReportList(List<FbsReport> refReportList) {
        this.refReportList = refReportList;
    }

    public List<FbsReport> getReportList() {
        return reportList;
    }

    public void setReportList(List<FbsReport> reportList) {
        this.reportList = reportList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<FbsFlat> getFbsFlatList() {
        return fbsFlatList;
    }

    public void setFbsFlatList(List<FbsFlat> fbsFlatList) {
        this.fbsFlatList = fbsFlatList;
    }

    public static FlatInfo getFlatInfo() {
        return flatInfo;
    }

    public static void setFlatInfo(FlatInfo flatInfo) {
        ReportArchieveBean.flatInfo = flatInfo;
    }

    public static boolean[] getSearchOption() {
        return searchOption;
    }

    public static void setSearchOption(boolean[] searchOption) {
        ReportArchieveBean.searchOption = searchOption;
    }

//    public Integer getRegNo() {
//        return regNo;
//    }
//
//    public void setRegNo(Integer regNo) {
//        this.regNo = regNo;
//        if (((regNo != 0))) {
//            //System.out.println("in if last-->");
//            this.regNo = regNo;
//            ReportArchieveBean.searchOption[0] = true;
//            //  System.out.println("in if set last name-->" + SearchPatientBean.searchOption[1]);
//        } else {
//            //  System.out.println("in else last-->");
//            this.regNo = 0;
//            ReportArchieveBean.searchOption[0] = false;
//            // System.out.println("in else set last name-->" + SearchPatientBean.searchOption[1]);
//        }
//
//
//    }
}
