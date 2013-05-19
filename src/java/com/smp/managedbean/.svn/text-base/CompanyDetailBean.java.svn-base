/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.managedbean;

import com.smp.chart.ProjectBooking;
import com.smp.entity.FbsBank;
import com.smp.entity.FbsBlock;
import com.smp.entity.FbsBooking;
import com.smp.entity.FbsBrPayment;
import com.smp.entity.FbsBroker;
import com.smp.entity.FbsPayment;
import com.smp.entity.FbsProject;
import com.smp.fbs.BookingDetails;
import com.smp.fbs.CollectionDetail;
import com.smp.session.FbsBankFacade;
import com.smp.session.FbsBlockFacade;
import com.smp.session.FbsBookingFacade;
import com.smp.session.FbsBrPaymentFacade;
import com.smp.session.FbsBrokerFacade;
import com.smp.session.FbsPaymentFacade;
import com.smp.session.FbsProjectFacade;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
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

/**
 *
 * @author smp
 */
@ManagedBean
@ApplicationScoped
public class CompanyDetailBean implements Serializable {

    /** Creates a new instance of CompanyDetailBean */
    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
    @EJB
    FbsProjectFacade fbsProjectFacade;
    @EJB
    FbsBlockFacade fbsBlockFacade;
    @EJB
    FbsBookingFacade fbsBookingFacade;
    @EJB
    FbsBankFacade fbsBankFacade;
    @EJB
    FbsBrokerFacade fbsBrokerFacade;
    @EJB
    FbsPaymentFacade fbsPaymentFacade;
    @EJB
    FbsBrPaymentFacade fbsBrPaymentFacade;
    private List<ProjectBooking> refFlat;
    List<FbsProject> fbsProjectList = new ArrayList<FbsProject>();
    List<FbsBooking> projBookingList = new ArrayList<FbsBooking>();
    List<CollectionDetail> companyCollection = new ArrayList<CollectionDetail>();
    private List<ProjectBooking> flat;
    List<BookingDetails> fbsBrokerPaymentList = new ArrayList<BookingDetails>();
    List<String> flatIdList = new ArrayList<String>();
    List<CollectionDetail> Bankcollections = new ArrayList<CollectionDetail>();
    String viewStatus;
    boolean temp;
    String clearAmountPie = "";
    String brokerWiseAmount = "";
    String bankWiseCollection1 = "";
    String projectWiseBookingXml = "";
    int noOf = 0;
    long B = 0;
    long un = 0;
    long T = 0;

    public CompanyDetailBean() {
    }

    @PostConstruct
    public void populateCom() throws IOException {

        Integer companyId = LoginBean.fbsLogin.getCompanyId();
        fbsProjectList.clear();
        flatIdList.clear();
        fbsProjectList = em.createNamedQuery("FbsProject.findByCompanyId").setParameter("companyId", companyId).getResultList();
        flat = new ArrayList<ProjectBooking>();
        companyCollection = new ArrayList<CollectionDetail>();
        fbsBrokerPaymentList.clear();
        projectWiseBookingXml = "<chart caption='Project Wise Booking' shownames='1' showvalues='0' decimals='0' xAxisName='Project' yAxisName='No Of Flat' >";
        projectWiseBookingXml = projectWiseBookingXml + "<categories>";
        for (int i = 0; i < fbsProjectList.size(); i++) {
            projectWiseBookingXml = projectWiseBookingXml + "<category label='" + fbsProjectList.get(i).getProjName() + "' />";
        }
        projectWiseBookingXml = projectWiseBookingXml + "</categories>";
        projectWiseBookingXml = projectWiseBookingXml + "<dataset seriesName='Booked' color='AFD8F8' showValues='0'>";
        for (int i = 0; i < fbsProjectList.size(); i++) {

            PopulateBlo(i, fbsProjectList.get(i).getProjId().toString(), fbsProjectList.get(i).getProjName());

            projectWiseBookingXml = projectWiseBookingXml + "<set value='" + B + "' />";

        }
        projectWiseBookingXml = projectWiseBookingXml + "</dataset>";
        projectWiseBookingXml = projectWiseBookingXml + "<dataset seriesName='UnBooked' color='F6BD0F' showValues='0'>";
        for (int i = 0; i < fbsProjectList.size(); i++) {

            PopulateBlo(i, fbsProjectList.get(i).getProjId().toString(), fbsProjectList.get(i).getProjName());
            projectWiseBookingXml = projectWiseBookingXml + "<set value='" + un + "' />";


        }
        projectWiseBookingXml = projectWiseBookingXml + "</dataset>";
        projectWiseBookingXml = projectWiseBookingXml + "<dataset seriesName='Transfered' color='8BBA00' showValues='0'>";
        for (int i = 0; i < fbsProjectList.size(); i++) {

            PopulateBlo(i, fbsProjectList.get(i).getProjId().toString(), fbsProjectList.get(i).getProjName());
            projectWiseBookingXml = projectWiseBookingXml + "<set value='" + T + "' />";

        }
        projectWiseBookingXml = projectWiseBookingXml + "</dataset>";
        projectWiseBookingXml = projectWiseBookingXml + "</chart>";
        companyPayment();
        brokerDetails();
        bankWiseCollection();

    }

    public void PopulateBlo(int ii, String projId, String projName) throws IOException //Set ALL DETAIL OF EACH FLAT IN flat ARRAYLIST
    {

        // System.out.println("++++++++++++++++++++++++");
        //  flat = new ArrayList<ProjectBooking>();



        if (projId != null) {
            List<FbsBlock> fbsBlock = em.createNamedQuery("FbsBlock.findByFkProjId").setParameter("fkProjId", Integer.parseInt(projId)).getResultList();
            int siz = fbsBlock.size();
            if (siz == 0) {
                return;
            }
            //  System.out.println("fbs Block size  " + siz);
            try {

                List<FbsBooking> fbsBooking = fbsBookingFacade.findAll();
                noOf = 0;
                B = 0;
                T = 0;
                un = 0;
                ProjectBooking flat1 = new ProjectBooking();
                for (int n = 0; n < siz; n++) {

                    int blockId = fbsBlock.get(n).getBlockId();
                    String blockName = fbsBlock.get(n).getBlockName();
                    flat1.setProjectname(projName);

                    String xmlFile = "";
                    FbsBlock list = fbsBlockFacade.find(blockId);
                    xmlFile = list.getXmlFile();
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    Document doc = db.parse(new InputSource(new StringReader(xmlFile)));
                    doc.getDocumentElement().normalize();
                    NodeList block = doc.getElementsByTagName("block");
                    NodeList floorList = block.item(0).getChildNodes();

                    for (int i = 0; i < floorList.getLength(); i++) {

                        Node floor = (Node) floorList.item(i);
                        if (floor.getNodeType() == Node.ELEMENT_NODE) {
                            Element floorElement = (Element) floor;

                            NodeList flatList = floorElement.getElementsByTagName("flat");

                            for (int j = 0; j < flatList.getLength(); j++) {

                                Element flatElement = (Element) flatList.item(j);
                                String flatId = flatElement.getAttribute("flat_id").trim();
                                flat1.setFlatId(Long.parseLong(flatId));
                                //String flat3 = flat1.getFlatId() + "";
                                for (int k = 0; k < fbsBooking.size(); k++) {
                                    String flat3 = flat1.getFlatId() + "";
                                    String app = fbsBooking.get(k).getFlatId() + "";
                                    if (flat3.trim().equals(app.trim())) {
                                        flatIdList.add(app);
                                        projBookingList.add(fbsBooking.get(k));
                                        String status = fbsBooking.get(k).getStatus();
                                        if (status.equals("b")) {
                                            ++B;
                                            break;
                                        }
                                        if (status.equals("t")) {
                                            ++T;
                                            break;
                                        }
                                    }
                                }
                                noOf++;
                            }
                        }
                    }
                    //   System.out.println("booked flat are " + B);
                }

                un = (noOf - B - T);
                flat1.setTransfer(T);
                flat1.setNumberof(noOf);
                flat1.setBook(B);
                flat1.setUnbooked(un);


                flat.add(ii, flat1);
                this.refFlat = this.flat;
                if (flat.size() <= 5) {
                    viewStatus = "View";

                } else {
                    viewStatus = "View More";

                }
            } catch (Exception e) {
                e.printStackTrace();
                //   System.out.println(e + e.toString());
            }
            //   FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/Dashboard/companyDashboard.xhtml");
        }
    }

    public void bankWiseCollection()//top 5 bank on the basis of collection
    {
        List<FbsBank> fbsBankList = em.createNamedQuery("FbsBank.findByCompanyId").setParameter("companyId", LoginBean.fbsLogin.getCompanyId()).getResultList();
        List<FbsBank> topbankList = new ArrayList<FbsBank>();
        List<FbsPayment> paymentList = fbsPaymentFacade.findAll();
        Bankcollections = new ArrayList<CollectionDetail>();
        bankWiseCollection1 = "<chart caption='Bank Wise Collection' xAxisName='Bank' yAxisName='Collection' showValues='0' decimals='0' formatNumberScale='0'>";
        CollectionDetail collection = new CollectionDetail();
        List<FbsPayment> tempPayList = new ArrayList<FbsPayment>();
        for (int i = 0; i < flatIdList.size(); i++) {
            for (int j = 0; j < paymentList.size(); j++) {
                if (flatIdList.get(i).equals(paymentList.get(j).getUnitCode().trim())) {
                    tempPayList.add(paymentList.get(j));
                }
            }
        }
        for (int j = 0; j < fbsBankList.size(); j++) {
            long amount = 0;
            collection = new CollectionDetail();
            for (int i = 0; i < tempPayList.size(); i++) {
                if (fbsBankList.get(j).getBankId().equals(tempPayList.get(i).getClearingBankId())) {
                    amount += tempPayList.get(i).getPaidAmount();
                }
            }
            collection.setAmount(amount);
            collection.setName(fbsBankList.get(j).getBankName());
            Bankcollections.add(collection);
            bankWiseCollection1 = bankWiseCollection1 + "<set label='" + fbsBankList.get(j).getBankName() + "' value='" + amount + "'/>";
        }
        List<CollectionDetail> bank = Bankcollections;
        List<CollectionDetail> banktemp = new ArrayList<CollectionDetail>();
        //  for(int i=0;i<Bankcollections.size();i++)
        // {
        //  System.out.println("name "+Bankcollections.get(i).getName()+" Amount "+Bankcollections.get(i).getAmount()+" day "+Bankcollections.get(i).getDay());
        // }
        for (int i = 0; i < Bankcollections.size(); i++) {
            CollectionDetail c = Bankcollections.get(i);

            for (int j = 0; j < Bankcollections.size(); j++) {
                CollectionDetail n = Bankcollections.get(j);
                if ((c.getAmount() < n.getAmount())) {
                    Collections.swap(Bankcollections, i, j);
                    //   System.out.println("index c" + Bankcollections.indexOf(c) + "  n  " + Bankcollections.indexOf(n));
                }
            }
        }

        // Bankcollections.clear();
        // Bankcollections.addAll(banktemp);
        bankWiseCollection1 = bankWiseCollection1 + "</chart>";
    }

    public void brokerDetails()//top 5 broker on the basis of payement
    {

        List<FbsBrPayment> brokerPayment = fbsBrPaymentFacade.findAll();
        List<BookingDetails> brokerpayments = new ArrayList<BookingDetails>();
        if (brokerPayment.isEmpty()) {
            return;
        }
        BookingDetails collection = new BookingDetails();
        List<FbsBroker> brokerList = em.createNamedQuery("FbsBroker.findByCompanyId").setParameter("companyId", LoginBean.fbsLogin.getCompanyId()).getResultList();
        for (int j = 0; j < brokerList.size(); j++) {
            collection = new BookingDetails();
            int paid = 0;
            for (int i = 0; i < brokerPayment.size(); i++) {
                if (brokerList.get(j).getBrokerId().equals(brokerPayment.get(i).getBrokerId())) {
                    paid += brokerPayment.get(i).getAmount();
                }
            }
            collection.setBook(paid);
            //    System.out.println("Amount " + paid + "broker id in first looping " + brokerList.get(j).getBrokerId().toString());
            collection.setBlock(brokerList.get(j).getBrokerId().toString());
            //  System.out.println("broker id is.............. "+brokerList.get(j).getBrokerId().toString());
            brokerpayments.add(collection);
        }
        int n = 0;

        brokerWiseAmount = "<chart caption='Broker Wise Collection' xAxisName='Broker' yAxisName='Collection' showValues='0' decimals='0' formatNumberScale='0'>";

        for (int i = 0; i < brokerpayments.size(); i++) {
            int payment = brokerpayments.get(i).getBook();
            int id = Integer.parseInt(brokerpayments.get(i).getBlock());
            //    System.out.println("Amount " + payment + "broker id in  " + id);
            for (int j = 1; j < brokerpayments.size(); j++) {
                if (payment < brokerpayments.get(j).getBook()) {
                    payment = brokerpayments.get(j).getBook();
                    id = Integer.parseInt(brokerpayments.get(j).getBlock());
                }
            }
            //   System.out.println("after Amount " + payment + "broker id in   " + id);
            collection = new BookingDetails();
            collection.setBook(payment);

            collection.setBlock(String.valueOf(id));
            fbsBrokerPaymentList.add(n++, collection);


            if (n == 5) {
                break;
            }
        }
        int cleared = 0, uncleared = 0;
        brokerpayments.clear();
        /*for (int i = 0; i < fbsBrokerPaymentList.size(); i++) {
        System.out.println("fbs Payment list "+fbsBrokerPaymentList.get(i).getBlock());
        System.out.println("fbs Payment list "+fbsBrokerPaymentList.get(i).getBook());
        System.out.println("fbs Payment list "+fbsBrokerPaymentList.get(i).getCancel());
        System.out.println("fbs Payment list "+fbsBrokerPaymentList.get(i).getMonth());
        System.out.println("fbs Payment list "+fbsBrokerPaymentList.get(i).getTransfer());
        }*/
        for (int i = 0; i < fbsBrokerPaymentList.size(); i++) {
            cleared = 0;
            uncleared = 0;
            // System.out.println("  .................. " + fbsBrokerPaymentList.get(i).getBlock());
            int id = Integer.parseInt(fbsBrokerPaymentList.get(i).getBlock());
            for (int j = 0; j < brokerPayment.size(); j++) {
                if (id == brokerPayment.get(j).getBrokerId()) {
                    if (brokerPayment.get(j).getStatus().toUpperCase().trim().equals("CLEARED")) {
                        cleared += brokerPayment.get(j).getAmount();
                    } else {
                        uncleared += brokerPayment.get(j).getAmount();
                    }
                }

            }
            //    System.out.println("uncleared Amount is " + uncleared + " Cleared Amount is " + cleared);
            collection = new BookingDetails();
            collection.setBook(cleared);
            collection.setCancel(Integer.parseInt(fbsBrokerPaymentList.get(i).getBlock()));
            FbsBroker fbsBroker = fbsBrokerFacade.find(id);
            collection.setBlock(fbsBroker.getBrokerId().toString());
            collection.setTransfer(uncleared);
            brokerWiseAmount = brokerWiseAmount + "<set label='" + fbsBroker.getBrName() + "' value='" + cleared + "'/>";
            brokerpayments.add(collection);
        }
        fbsBrokerPaymentList.clear();
        fbsBrokerPaymentList.addAll(brokerpayments);
        brokerWiseAmount = brokerWiseAmount + "</chart>";
        //   System.out.println("value of n is " + fbsBrokerPaymentList.size() + "  " + brokerpayments.size());
        for (int i = 0; i < fbsBrokerPaymentList.size(); i++) {
            collection = fbsBrokerPaymentList.get(i);
            //    System.out.println("Id " + collection.getCancel() + " Name " + collection.getBlock() + " uncleared " + collection.getTransfer() + " Cleared " + collection.getBook());
        }
    }

    public void companyPayment() {
        companyCollection.clear();
        List<FbsPayment> fbsPaymentList = fbsPaymentFacade.findAll();
        long cleared = 0;
        long uncleared = 0;
        for (int j = 0; j < flatIdList.size(); j++) {
            for (int i = 0; i < fbsPaymentList.size(); i++) {
                if (fbsPaymentList.get(i).getUnitCode().trim().equals(flatIdList.get(j).trim())) {
                    if (fbsPaymentList.get(i).getChequeStatus().toUpperCase().trim().equals("CLEARED")) {
                        cleared += fbsPaymentList.get(i).getPaidAmount();
                    } else {
                        uncleared += fbsPaymentList.get(i).getPaidAmount();
                    }
                }
            }
        }
        clearAmountPie = "<chart palette='4' caption='Company Collection' decimals='0' enableSmartLabels='1' enableRotation='0'  startingAngle='70' >";
        clearAmountPie = clearAmountPie + "<set label='Cleared Amount' value='" + cleared + "'/>";
        clearAmountPie = clearAmountPie + "<set label='UnCleared Amount' value='" + uncleared + "'/>";
        CollectionDetail fbstemp = new CollectionDetail();
        fbstemp.setAmount(cleared);
        fbstemp.setName("Cleared Amount");
        companyCollection.add(0, fbstemp);
        fbstemp = new CollectionDetail();
        fbstemp.setAmount(uncleared);
        fbstemp.setName("UnCleared Amount");
        companyCollection.add(1, fbstemp);
        clearAmountPie = clearAmountPie + "</chart>";
    }

    public List<FbsProject> getFbsProjectList() {
        return fbsProjectList;
    }

    public void setFbsProjectList(List<FbsProject> fbsProjectList) {
        this.fbsProjectList = fbsProjectList;
    }

    public List<ProjectBooking> getFlat() {
        return flat;
    }

    public void setFlat(List<ProjectBooking> flat) {
        this.flat = flat;
    }

    public List<FbsBooking> getProjBookingList() {
        return projBookingList;
    }

    public void setProjBookingList(List<FbsBooking> projBookingList) {
        this.projBookingList = projBookingList;
    }

    public List<ProjectBooking> getRefFlat() {
        return refFlat;
    }

    public void setRefFlat(List<ProjectBooking> refFlat) {
        this.refFlat = refFlat;
    }

    public String getViewStatus() {
        return viewStatus;
    }

    public void setViewStatus(String viewStatus) {
        this.viewStatus = viewStatus;
    }

    public List<CollectionDetail> getCompanyCollection() {
        return companyCollection;
    }

    public void setCompanyCollection(List<CollectionDetail> companyCollection) {
        this.companyCollection = companyCollection;
    }

    public List<BookingDetails> getFbsBrokerPaymentList() {
        return fbsBrokerPaymentList;
    }

    public void setFbsBrokerPaymentList(List<BookingDetails> fbsBrokerPaymentList) {
        this.fbsBrokerPaymentList = fbsBrokerPaymentList;
    }

    public List<CollectionDetail> getBankcollections() {
        return Bankcollections;
    }

    public void setBankcollections(List<CollectionDetail> Bankcollections) {
        this.Bankcollections = Bankcollections;
    }

    public boolean isTemp() throws IOException {
        temp = true;

        populateCom();
        return temp;
    }

    public void setTemp(boolean temp) {
        this.temp = temp;
    }

    public String getClearAmountPie() {
        return clearAmountPie;
    }

    public void setClearAmountPie(String clearAmountPie) {
        this.clearAmountPie = clearAmountPie;
    }

    public String getBrokerWiseAmount() {
        return brokerWiseAmount;
    }

    public void setBrokerWiseAmount(String brokerWiseAmount) {
        this.brokerWiseAmount = brokerWiseAmount;
    }

    public String getBankWiseCollection1() {
        return bankWiseCollection1;
    }

    public void setBankWiseCollection1(String bankWiseCollection1) {
        this.bankWiseCollection1 = bankWiseCollection1;
    }

    public String getProjectWiseBookingXml() {
        return projectWiseBookingXml;
    }

    public void setProjectWiseBookingXml(String projectWiseBookingXml) {
        this.projectWiseBookingXml = projectWiseBookingXml;
    }
}
