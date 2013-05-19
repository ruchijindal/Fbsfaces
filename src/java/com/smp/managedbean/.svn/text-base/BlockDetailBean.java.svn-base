/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.managedbean;

import com.smp.entity.FbsBlock;
import com.smp.entity.FbsBooking;
import com.smp.entity.FbsPayment;
import com.smp.fbs.BookingDetails;
import com.smp.fbs.CollectionDetail;
import com.smp.fbs.FbsFlat;
import com.smp.session.FbsBlockFacade;
import com.smp.session.FbsBookingFacade;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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
 * @author smp11
 */
@ManagedBean(name = "blockDetailBean")
@ApplicationScoped
@Stateless
public class BlockDetailBean {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
    @EJB
    FbsBlockFacade fbsBlockFacade;
    @EJB
    FbsBookingFacade fbsBookingFacade;
    public long booked;
    public long unBooked;
    public long noOfFlat;
    private String projId;
    private static List<FbsFlat> flat;
    boolean temp;
    private List<FbsFlat> refFlat;
    static String viewStatus;
    private List<CollectionDetail> collection;
    private static List<BookingDetails> monthflat = new ArrayList<BookingDetails>();
    List<FbsBooking> projBookingList = new ArrayList<FbsBooking>();
    private List<BookingDetails> daycollection = new ArrayList<BookingDetails>();
    static String clearAmountPie = "";
    static String weeklyCollection = "";
    static String lastFiveMonthBooking = "";
    static String blockWiseBookingXml = "";

    public BlockDetailBean() {
        collection = new ArrayList<CollectionDetail>();
    }

    @PostConstruct
    public void Populate() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(false);
        projId = (String) session.getAttribute("projId");
        System.out.println("project id populate method of block details bean is " + projId);
        projectWiseCollection(projId);
        // bookingmonthChart();
        //  PopulateBlo(projId);
    }

    public void PopulateBlo(String projId) throws IOException //Set ALL DETAIL OF EACH FLAT IN flat ARRAYLIST
    {

        projBookingList.clear();
        // System.out.println("++++++++++++++++++++++++");
        flat = new ArrayList<FbsFlat>();

        int noOf = 0;
        long B = 0;
        long un = 0;
        long T = 0;
        if (projId != null) {
            Query query = em.createNamedQuery("FbsBlock.findByProjId&Status");
            query.setParameter("status", "lock");
            query.setParameter("fkProjId", Integer.parseInt(projId));
            List<FbsBlock> fbsBlock = query.getResultList();
            int siz = fbsBlock.size();
            try {

                List<FbsBooking> fbsBooking = fbsBookingFacade.findAll();
                blockWiseBookingXml = "<chart caption='Block Wise Booking' shownames='1' showvalues='0' decimals='0' xAxisName='Block' yAxisName='No Of Flat' >";
                blockWiseBookingXml = blockWiseBookingXml + "<categories>";
                for (int i = 0; i < siz; i++) {
                    blockWiseBookingXml = blockWiseBookingXml + "<category label='" + fbsBlock.get(i).getBlockName() + "' />";
                }
                blockWiseBookingXml = blockWiseBookingXml + "</categories>";
                for (int n = 0; n < siz; n++) {
                    FbsFlat flat1 = new FbsFlat();
                    int blockId = fbsBlock.get(n).getBlockId();
                    String blockName = fbsBlock.get(n).getBlockName();
                    flat1.setBlockName(blockName);
                    String xmlFile = "";
                    FbsBlock list = fbsBlockFacade.find(blockId);
                    xmlFile = list.getXmlFile();
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    Document doc = db.parse(new InputSource(new StringReader(xmlFile)));
                    doc.getDocumentElement().normalize();
                    NodeList block = doc.getElementsByTagName("block");
                    NodeList floorList = block.item(0).getChildNodes();
                    noOf = 0;
                    B = 0;
                    T = 0;
                    un = 0;
                    for (int i = 0; i < floorList.getLength(); i++) {

                        Node floor = floorList.item(i);
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
                    un = (noOf - B - T);
                    flat1.setTransfer(T);
                    flat1.setBlockId((int) noOf);
                    flat1.setFlatId(B);
                    flat1.setFloorId(un);
                    flat.add(n, flat1);
                }
                this.refFlat = this.flat;
                if (flat.size() <= 5) {
                    viewStatus = "View";

                } else {
                    viewStatus = "View More";

                }
                blockWiseBookingXml = blockWiseBookingXml + "<dataset seriesName='Booked' color='AFD8F8' showValues='0'>";
                for (int i = 0; i < flat.size(); i++) {
                    blockWiseBookingXml = blockWiseBookingXml + "<set value='" + flat.get(i).getFlatId() + "' />";
                }
                blockWiseBookingXml = blockWiseBookingXml + "</dataset>";
                blockWiseBookingXml = blockWiseBookingXml + "<dataset seriesName='UnBooked' color='F6BD0F' showValues='0'>";
                for (int i = 0; i < flat.size(); i++) {
                    blockWiseBookingXml = blockWiseBookingXml + "<set value='" + flat.get(i).getFloorId() + "' />";
                }
                blockWiseBookingXml = blockWiseBookingXml + "</dataset>";
                blockWiseBookingXml = blockWiseBookingXml + "<dataset seriesName='Transfered' color='8BBA00' showValues='0'>";
                for (int i = 0; i < flat.size(); i++) {
                    blockWiseBookingXml = blockWiseBookingXml + "<set value='" + flat.get(i).getTransfer() + "' />";
                }
                blockWiseBookingXml = blockWiseBookingXml + "</dataset>";
                blockWiseBookingXml = blockWiseBookingXml + "</chart>";
            } catch (Exception e) {
                //   System.out.println(e + e.toString());
                e.printStackTrace();
            }

        }

        // bookingmonthChart();
        //   Populate();
        //  System.out.println("yahooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");

        System.out.println("size of project booking list is = " + projBookingList.size());
    }

    public void projectWiseCollection(String projId) {
        clearAmountPie = "<chart palette='4' caption='Project Collection' decimals='0' enableSmartLabels='1' enableRotation='0'  startingAngle='70' >";
        daycollection.clear();
        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        Query query = em.createNamedQuery("FbsBlock.findByProjId&Status");
        query.setParameter("fkProjId", Integer.parseInt(projId));
        query.setParameter("status", "lock");
        List<FbsBlock> fbsBlockList = query.getResultList();
        List<FbsPayment> fbsPaymentList = new ArrayList<FbsPayment>();
        long clearedamount = 0;
        long unclearedamount = 0;
        System.out.println("Size of block list isssssssssssssssssssssssssss  " + fbsBlockList.size());
        for (int i = 0; i < fbsBlockList.size(); i++) {
            fbsPaymentList = em.createNamedQuery("FbsPayment.findByBlockId").setParameter("blockId", fbsBlockList.get(i).getBlockId()).getResultList();
            System.out.println(fbsBlockList.get(i).getBlockName() + "  Size of Fbs Payment List in project dashboard " + fbsPaymentList.size());
            for (int j = 0; j < fbsPaymentList.size(); j++) {
                FbsPayment fbsPayment = new FbsPayment();
                fbsPayment = fbsPaymentList.get(j);
                if (fbsPayment.getChequeStatus().toUpperCase().trim().equals("cleared".toUpperCase())) {
                    clearedamount += fbsPayment.getPaidAmount();
                } else {
                    unclearedamount += fbsPayment.getPaidAmount();
                }
            }


        }

        collection = new ArrayList<CollectionDetail>();
        CollectionDetail fbstemp = new CollectionDetail();

        fbstemp.setName("Cleared");
        fbstemp.setAmount(clearedamount);
        collection.add(fbstemp);
        fbstemp = new CollectionDetail();
        fbstemp.setName("UnCleared");
        fbstemp.setAmount(unclearedamount);
        collection.add(fbstemp);
        clearAmountPie = clearAmountPie + "<set label='Cleared Amount' value='" + clearedamount + "'/>";
        clearAmountPie = clearAmountPie + "<set label='UnCleared Amount' value='" + unclearedamount + "'/>";
        clearAmountPie = clearAmountPie + "</chart>";
        fbsPaymentList.clear();
        SimpleDateFormat sd = new SimpleDateFormat("ddd");
        SimpleDateFormat sdm = new SimpleDateFormat("MM");
        SimpleDateFormat sdy = new SimpleDateFormat("yyyy");

        for (int k = 6; k >= 0; k--) {
            unclearedamount = 0;
            clearedamount = 0;
            for (int i = 0; i < fbsBlockList.size(); i++) {
                fbsPaymentList = em.createNamedQuery("FbsPayment.findByBlockId").setParameter("blockId", fbsBlockList.get(i).getBlockId()).getResultList();
                for (int j = 0; j < fbsPaymentList.size(); j++) {
                    FbsPayment fbsPayment = new FbsPayment();
                    fbsPayment = fbsPaymentList.get(j);
                    int date = Integer.parseInt(sd.format(fbsPayment.getPaymentDate()));

                    int current = Integer.parseInt(sd.format(new Date()));
                    //  System.out.println("Payment Date " + fbsPayment.getPaymentDate().toString());
                    //  System.out.println((Integer.parseInt(sdy.format(new Date()))) + " " + (Integer.parseInt(sdm.format(new Date()))) + "  " + current);
                    // System.out.println(Integer.parseInt(sdy.format(fbsPayment.getPaymentDate())) + "  " + Integer.parseInt(sdm.format(fbsPayment.getPaymentDate())) + "  " + date);
                    if ((Integer.parseInt(sdy.format(new Date())) == Integer.parseInt(sdy.format(fbsPayment.getPaymentDate())))) {
                        //System.out.println("year equal "+(Integer.parseInt(sdm.format(new Date())))+"  "+sdm.format(fbsPayment.getPaymentDate()));
                        if ((Integer.parseInt(sdm.format(new Date())) == Integer.parseInt(sdm.format(fbsPayment.getPaymentDate())))) {
                            // System.out.println("month equal "+fbsPayment.getPaymentDate().toString());
                            if ((current - date) == k) {
                                //     System.out.println("difference "+fbsPayment.getPaymentDate().toString());
                                //   System.out.println("payment date " + fbsPayment.getPaymentDate().toString() + " flat " + fbsPayment.getUnitCode());
                                if (fbsPayment.getChequeStatus().toUpperCase().trim().equals("cleared".toUpperCase())) {
                                    clearedamount += fbsPayment.getPaidAmount();
                                } else {
                                    unclearedamount += fbsPayment.getPaidAmount();
                                }
                            }
                        }
                    }
                }

            }
            // System.out.println("777777777777777777777777777777777777777777777value of k is " + k);
            //  daycollection = new ArrayList<BookingDetails>();
            BookingDetails fbsTemp = new BookingDetails();
            fbsTemp.setBook((int) clearedamount);
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            fbsTemp.setCancel((int) unclearedamount);
            int temp = cal.get(Calendar.DAY_OF_WEEK);
            Integer t = temp - k - 1;
            if (temp - k - 1 < 0) {
                t = 7 + temp - k - 1;
            }
            System.out.println("Cleared Amount " + clearedamount + "  unclearedamount  " + unclearedamount);
            fbsTemp.setBlock(days[t]);
            System.out.println("Cleared Amount " + fbsTemp.getBook() + "  unclearedamount   " + fbsTemp.getCancel() + "  day " + fbsTemp.getBlock());
            daycollection.add(fbsTemp);
            weeklyCollection = "<chart caption='Last 7 Days Collection' xAxisName='Day' yAxisName='Collection' showValues='0' decimals='0' formatNumberScale='0'>";
            for (int i = 0; i < daycollection.size(); i++) {
                weeklyCollection = weeklyCollection + "<set label='" + daycollection.get(i).getBlock() + "' value='" + daycollection.get(i).getBook() + "'/>";
            }
            weeklyCollection = weeklyCollection + "</chart>";
            //fbstemp = new CollectionDetail();
            //fbstemp.setName("cleared");
            //fbstemp.setAmount(clearedamount);
            //fbstemp.setDay(days[k]);
            //  daycollection.add(fbsTemp);

        }

        // System.out.println("day of the week "+);
        //  for (int i = 0; i < daycollection.size(); i++) {
        //    System.out.println(" cleared  " + daycollection.get(i).getBook() + " uncl" + daycollection.get(i).getCancel());
        //   }
        //  System.out.println("Size of day collection List is " + daycollection.size());
    }

    public void bookingmonthChart(String projid) throws IOException {
        monthflat.clear();
        this.projId = projid;
        lastFiveMonthBooking = "<chart caption='Last Five Months Booking' xAxisName='Month' yAxisName='No Of Booking' showValues='0' decimals='0' formatNumberScale='0'>";
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        List<FbsBooking> fbsBookings = projBookingList;
//System.out.println("size of fbs booking is "+fbsBookings.size()+"   "+projBookingList.size());
        List<FbsBooking> templist = new ArrayList<FbsBooking>();
        for (int i = 0; i < fbsBookings.size(); i++) {
            if (new Date().compareTo(fbsBookings.get(i).getBookingDt()) <= 5) {
                templist.add(fbsBookings.get(i));
            }
        }
        //  System.out.println("size of fbs Bookings " + fbsBookings.size() + " temp List size in block Detail Bean" + templist.size());
        BookingDetails bookingDetails = new BookingDetails();
        // Query query = em.createNamedQuery("FbsBlock.findByProjId&Status");
        //   query.setParameter("fkProjId", Integer.parseInt(projId));
        //   query.setParameter("status", "lock");
        int nob = 0;
        int not = 0;
        int noc = 0;
        // List<FbsBlock> fbsBlockList = query.getResultList();
        templist.clear();
        templist = projBookingList;
        for (int k = 4; k >= 0; k--) {

            SimpleDateFormat sdf = new SimpleDateFormat("MM");
            SimpleDateFormat sdy = new SimpleDateFormat("yyyy");
            String current = sdf.format(new Date());
            for (int j = 0; j < templist.size(); j++) {
                String book = sdf.format(templist.get(j).getBookingDt());
                //  System.out.println("current month is " + current + " booking month " + book);
                if (Integer.parseInt(sdy.format(new Date())) == Integer.parseInt(sdy.format(templist.get(j).getBookingDt()))) {
                    //  System.out.println("year is same for m ="+k);
                    if (((Integer.parseInt(current) - Integer.parseInt(book)) == k) || (Integer.parseInt(book) - (Integer.parseInt(current)) == k)) {
                        //  System.out.println("month is same for m ="+k);
                        //   System.out.println(" Ststausss    "+templist.get(j).getStatus());
                        if (templist.get(j).getStatus().equals("b")) {
                            nob = nob + 1;
                            //   System.out.println("nob updated............................."+nob);

                        } else if (templist.get(j).getStatus().equals("t")) {
                            not = not + 1;
                            System.out.println("not updated............................." + not);
                        } else if (templist.get(j).getStatus().equals("c")) {
                            noc = noc + 1;
                            //   System.out.println("noc updated............................."+noc);

                        }
                    }
                    // System.out.println("nob "+nob+"  noc "+noc+"  not "+not);
                }


            }
            System.out.println("finally nob " + nob + "  noc " + noc + "  not " + not);
            //   System.out.println("NOb is " + nob + " NOC " + noc + " NOT " + not + " block name" + k);
            BookingDetails bDetails = new BookingDetails();
            int m = Integer.parseInt(current) - k - 1;
            //   System.out.println("m is " + m);
            if (m < 0) {
                m = 12 + m;
            }

            bDetails.setBlock(months[m]);
            bDetails.setBook(nob);
            bDetails.setCancel(noc);
            bDetails.setTransfer(not);
            lastFiveMonthBooking = lastFiveMonthBooking + "<set label='" + months[m] + "' value='" + nob + "'/>";
            monthflat.add(bDetails);
            //    System.out.println("Size of nob  noc  not  is  "+nob+"  "+noc+"  "+not+" month flat size "+monthflat.size());
            nob = noc = not = 0;
        }
        lastFiveMonthBooking = lastFiveMonthBooking + "</chart>";
//System.out.println("Size of month flat is "+monthflat.size());
//for(int i=0;i<monthflat.size();i++)
//{
//    System.out.println(" "+monthflat.get(i).getBlock()+"  "+monthflat.get(i).getBook()+"   "+monthflat.get(i).getCancel()+"   "+monthflat.get(i).getMonth()+"   "+monthflat.get(i).getTransfer());
//}
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/Dashboard/projectDashboard.xhtml");
    }

    public void setFlat(FbsFlat fbsFlat) {
        this.flat = (List<FbsFlat>) fbsFlat;
    }

    public List<FbsFlat> getFlat() {
        return this.flat;
    }

    public void setRefFlat(FbsFlat fbsFlat) {
        this.refFlat = (List<FbsFlat>) fbsFlat;
    }

    public List<FbsFlat> getRefFlat() {
        return this.refFlat;
    }

    public String getViewStatus() {
        return viewStatus;
    }

    public void setViewStatus(String viewStatus) {
        this.viewStatus = viewStatus;
    }

    public List<CollectionDetail> getCollection() {
        return collection;
    }

    public void setCollection(List<CollectionDetail> collection) {
        this.collection = collection;
    }

    public List<BookingDetails> getMonthflat() {
        return monthflat;
    }

    public void setMonthflat(List<BookingDetails> monthflat) {
        BlockDetailBean.monthflat = monthflat;
    }

    public List<BookingDetails> getDaycollection() {
        return daycollection;
    }

    public void setDaycollection(List<BookingDetails> daycollection) {
        this.daycollection = daycollection;
    }

    public boolean isTemp() throws IOException {
        temp = true;
        // Populate();
        System.out.println("i am in block detail bean");
        PopulateBlo(projId);

        return temp;
    }

    public void setTemp(boolean temp) {
        this.temp = temp;
    }

    public String getBlockWiseBookingXml() {
        return blockWiseBookingXml;
    }

    public void setBlockWiseBookingXml(String blockWiseBookingXml) {
        this.blockWiseBookingXml = blockWiseBookingXml;
    }

    public String getClearAmountPie() {
        return clearAmountPie;
    }

    public void setClearAmountPie(String clearAmountPie) {
        this.clearAmountPie = clearAmountPie;
    }

    public String getLastFiveMonthBooking() {
        return lastFiveMonthBooking;
    }

    public void setLastFiveMonthBooking(String lastFiveMonthBooking) {
        this.lastFiveMonthBooking = lastFiveMonthBooking;
    }

    public String getWeeklyCollection() {
        return weeklyCollection;
    }

    public void setWeeklyCollection(String weeklyCollection) {
        this.weeklyCollection = weeklyCollection;
    }
}
