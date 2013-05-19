/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.report;

import com.smp.entity.FbsBlock;
import com.smp.entity.FbsBooking;
import com.smp.entity.FbsBrPayment;
import com.smp.entity.FbsBroker;
import com.smp.entity.FbsBrokerCat;
import com.smp.entity.FbsDiscount;
import com.smp.entity.FbsFlat;
import com.smp.entity.FbsFlatType;
import com.smp.entity.FbsPayment;
import com.smp.entity.FbsPlanname;
import com.smp.entity.FbsProject;
import com.smp.entity.FbsServicetax;
import com.smp.fbs.BrokerInfo;
import com.smp.fbs.FlatInfo;
import com.smp.managedbean.QuickBookingBean;
import com.smp.session.FbsBlockFacade;
import com.smp.session.FbsBookingFacade;
import com.smp.session.FbsBrPaymentFacade;
import com.smp.session.FbsBrokerCatFacade;
import com.smp.session.FbsBrokerFacade;
import com.smp.session.FbsDiscountFacade;
import com.smp.session.FbsFlatTypeFacade;
import com.smp.session.FbsPlannameFacade;
import com.smp.session.FbsProjectFacade;
import com.smp.session.FbsServicetaxFacade;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
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
@Stateless
public class CalculateCommision {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager entityManager;
    @EJB
    FbsFlatTypeFacade fbsFlatTypeFacade;
    @EJB
    FbsBrokerFacade fbsBrokerFacadeNew;
    @EJB
    FbsBrokerCatFacade fbsBrokerCatFacade;
    @EJB
    FbsBookingFacade fbsBookingFacade;
    @EJB
    FbsBlockFacade fbsBlockFacade;
    @EJB
    FbsProjectFacade fbsProjectFacade;
    @EJB
    FbsDiscountFacade fbsDiscountFacade;
    @EJB
    FbsBrokerFacade fbsBrokerFacade;
    @EJB
    FbsBrPaymentFacade fbsBrPaymentFacade;
    @EJB
    FbsServicetaxFacade fbsServicetaxFacade;
    @EJB
    FbsPlannameFacade fbsPlannameFacade;
    private FbsBlock fbsBlock = new FbsBlock();
    public FbsBooking fbsBooking = new FbsBooking();
    public FbsBroker fbsBroker = new FbsBroker();
    public FbsFlatType fbsFlatType = new FbsFlatType();
    public FbsDiscount fbsDiscount = new FbsDiscount();
    FbsProject fbsProject = new FbsProject();
    private List<FbsFlat> fbsFlatList = new ArrayList<FbsFlat>();
    List<FbsFlat> refFlatList = new ArrayList<FbsFlat>();
    public List<FbsBooking> fbsBookingList = new ArrayList<FbsBooking>();
    public FbsBooking fbsBooking1 = new FbsBooking();
    public List<FbsPayment> fbsPaymentList = new ArrayList<FbsPayment>();
    public List<BrokerInfo> brokerInfoList = new ArrayList<BrokerInfo>();
    public FlatInfo flatInfo;
    public FbsBroker fbsBrokerName = new FbsBroker();
    List<FbsBrPayment> fbsBrPaymentlist = new ArrayList<FbsBrPayment>();
    public List<BrokerInfo> reBrokerInfoList = new ArrayList<BrokerInfo>();
    public List<BrokerInfo> tempBrokerInfoList = new ArrayList<BrokerInfo>();
    String brokerName = "";
    String xmlFile = "";
    int categoryId = 0;
    String categoryName = "";
    private FbsBrokerCat fbsBrokerCat;
    BrokerInfo brokerInfo;
    float totalPaidAmount = 0;
    float totalPayableAmount = 0;
    float totalAmount = 0;
    int discountPercantadge = 0;
    int discountId = 0;
    int k = 0;
    List<FbsBrPayment> brokerPaymentList;
    List<FbsBrPayment> tempPaymentList=new ArrayList<FbsBrPayment>();

    public List<BrokerInfo> calculateCommission(Integer brokerId, String projId, String blockID, String uCode) throws IOException {

        fbsBroker = fbsBrokerFacadeNew.find(brokerId);


        String categoryId = fbsBroker.getCategoryId().toString();

        fbsBrokerCat = fbsBrokerCatFacade.find(Integer.parseInt(categoryId));
        int commission = fbsBrokerCat.getCommission();

        fbsBookingList = entityManager.createNamedQuery("FbsBooking.findByBrokerId").setParameter("brokerId", brokerId).getResultList();

        brokerInfoList.clear();
        for (int a = 0; a < fbsBookingList.size(); a++) {
            try {

                brokerInfo = new BrokerInfo();
                brokerInfo.setBlockId(fbsBookingList.get(a).getBlockId());
                fbsBlock = fbsBlockFacade.find(brokerInfo.getBlockId());
                brokerInfo.setBlockName(fbsBlock.getBlockName());
                brokerInfo.setProjId(Integer.parseInt(String.valueOf(fbsBlock.getFkProjId())));
                fbsProject = fbsProjectFacade.find(brokerInfo.getProjId());
                brokerInfo.setProjectName(fbsProject.getProjName());
                brokerInfo.setRegistrationNo(fbsBookingList.get(a).getRegNumber());
                brokerInfo.setBookingDate(fbsBookingList.get(a).getBookingDt());
                brokerInfo.setBrokerId(fbsBookingList.get(a).getBrokerId());
                fbsBrokerName = fbsBrokerFacade.find(brokerInfo.getBrokerId());
                brokerInfo.setBrName(fbsBrokerName.getBrName());

                fbsFlatList = new ArrayList();
                int l = 0;
                xmlFile = fbsBlock.getXmlFile();
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = (Document) db.parse(new InputSource(new StringReader(xmlFile)));
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

                for (int b = 0; b < fbsFlatList.size(); b++) {
                    if (fbsFlatList.get(b).getFlatId().toString().equals(fbsBookingList.get(a).getFlatId().toString())) {
                        brokerInfo.setFlatId(fbsFlatList.get(b).getFlatId().toString());
                        brokerInfo.setFlatNo(fbsFlatList.get(b).getFlatNo());

                        brokerInfo.setFloorNo(fbsFlatList.get(b).getFloorNo().toString());

                        brokerInfo.setFlatTypeId(Integer.parseInt(fbsFlatList.get(b).getFlatType()));
                        fbsFlatType = fbsFlatTypeFacade.find(brokerInfo.getFlatTypeId());
                        brokerInfo.setFlatSpecification(fbsFlatType.getFlatSpecification());



                        fbsBrPaymentlist = entityManager.createNamedQuery("FbsBrPayment.findByFkFlatId").setParameter("fkFlatId", fbsBookingList.get(a).getFlatId()).getResultList();
                        totalPaidAmount = 0;
                        for (int i = 0; i < fbsBrPaymentlist.size(); i++) {
                            totalPaidAmount = (float) totalPaidAmount + fbsBrPaymentlist.get(i).getAmount();
                        }
                        brokerInfo.setAmount(totalPaidAmount);

                        fbsBooking1 = (FbsBooking) entityManager.createNamedQuery("FbsBooking.findByFlatId").setParameter("flatId", fbsBookingList.get(a).getFlatId()).getResultList().get(0);

                        discountId = fbsBooking1.getDiscountId();
                        fbsDiscount = fbsDiscountFacade.find(discountId);
                        discountPercantadge = fbsDiscount.getPercentage();
                        brokerInfo.setDiscountPercantadge(discountPercantadge);




                        // fbsFlatType = FbsFlatTypeFacade.find(brokerInfo.getFlatTypeId());
                        //   brokerInfo.setFlatSpecification(fbsFlatType.getFlatSpecification());
FbsPlanname fbsPlanname=fbsPlannameFacade.find(fbsBookingList.get(a).getPlanId());
                        brokerInfo.setBr(fbsFlatType.getFlatBsp());
                        brokerInfo.setSba(fbsFlatType.getFlatSba());
                        int br = brokerInfo.getBr();
                        int sba = brokerInfo.getSba();

                        int bsp;
                        int br_commisson;
                        if (br != 0 && sba != 0) {
                            bsp = sba * br;
                            bsp = bsp - ((discountPercantadge * bsp) / 100)-( ((fbsPlanname.getDiscount() * bsp) / 100));

                        } else {
                            bsp = 0;
                        }

                        br_commisson = (bsp * commission) / 100;

                        brokerInfo.setCommission(br_commisson);


                        break;
                    }
                }
                brokerInfoList.add(brokerInfo);



            } catch (Exception ex) {
                Logger.getLogger(QuickBookingBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        reBrokerInfoList = brokerInfoList;

        tempBrokerInfoList = new ArrayList<BrokerInfo>();
        tempBrokerInfoList.addAll(brokerInfoList);


        if (!projId.equals("null")) {
            tempBrokerInfoList = filterByProjectName(tempBrokerInfoList, projId);
        }
        reBrokerInfoList = tempBrokerInfoList;

        if (!blockID.equals("null")) {
            tempBrokerInfoList = filterByBlockName(tempBrokerInfoList, blockID);
        }
        reBrokerInfoList = tempBrokerInfoList;

        if (!uCode.equals("null")) {
            tempBrokerInfoList = filterByUnitcode(tempBrokerInfoList, uCode);
        }
        reBrokerInfoList = tempBrokerInfoList;



        return reBrokerInfoList;
    }

    public List<BrokerInfo> filterByProjectName(List<BrokerInfo> tempBrokerInfoList, String projId) {

        int k = 0;
        List<BrokerInfo> projectTemp = new ArrayList<BrokerInfo>();
        for (int i = 0; i < tempBrokerInfoList.size(); i++) {
            if (tempBrokerInfoList.get(i).getProjId() == Integer.parseInt(projId)) {
                projectTemp.add(k, tempBrokerInfoList.get(i));
                k++;
            }
        }
        return projectTemp;
    }

    public List<BrokerInfo> filterByBlockName(List<BrokerInfo> tempBrokerInfoList, String blockID) {

        int k = 0;
        List<BrokerInfo> blockTemp = new ArrayList<BrokerInfo>();
        for (int i = 0; i < tempBrokerInfoList.size(); i++) {
            if (tempBrokerInfoList.get(i).getBlockId() == Integer.parseInt(blockID)) {
                blockTemp.add(k, tempBrokerInfoList.get(i));
                k++;
            }
        }
        return blockTemp;
    }

    public List<BrokerInfo> filterByUnitcode(List<BrokerInfo> tempBrokerInfoList, String unitCode) {

        int k = 0;
        List<BrokerInfo> flatTemp = new ArrayList<BrokerInfo>();
        for (int i = 0; i < tempBrokerInfoList.size(); i++) {

            if (tempBrokerInfoList.get(i).getFlatId().equals(unitCode)) {
                flatTemp.add(k, tempBrokerInfoList.get(i));
                k++;
            }
        }
        return flatTemp;
    }

    public float calculatePayableAmount(FbsBrokerCat fbsBrokerCat1, String unitcode, float totalCommission, float totalBSP) throws IOException {

        fbsPaymentList = entityManager.createNamedQuery("FbsPayment.findByUnitCode").setParameter("unitCode", unitcode).getResultList();
 FbsServicetax fbsServicetax=new FbsServicetax();
        totalAmount = 0;
        for (int j = 0; j < fbsPaymentList.size(); j++) {

            totalAmount = totalAmount + fbsPaymentList.get(j).getPaidAmount();
              fbsServicetax = fbsServicetaxFacade.find(fbsPaymentList.get(j).getServiceTax());
                        Double temp = 1 + ((double) fbsServicetax.getServicetax()) / 100;
                        totalAmount =  (float) (totalAmount / temp);
        }

        int bspPercantage = fbsBrokerCat1.getBspPercent();
        int brokerPercantage = fbsBrokerCat1.getBrokerPercent();

        int installment = (int) 100 / brokerPercantage;
        int rem = 100 % brokerPercantage;
        if (rem > 0) {
            installment++;
        }

        float bspAmount = 0;

        //float bspAmountNew = 0;
        int count = 0;



        bspAmount = 0;
        for (int i1 = 1; i1 <= installment; i1++) {

            bspAmount = (float) (bspPercantage * i1 * totalBSP) / 100;


            if (totalAmount >= bspAmount) {
                count++;
            }

        }
        totalPayableAmount = 0;

        if (count < installment) {
            totalPayableAmount = (float) (brokerPercantage * count * totalCommission) / 100;
        } else {

            totalPayableAmount = totalCommission;
        }


        return totalPayableAmount;
    }

    public List<FbsBrPayment> calculatePayment(List<BrokerInfo> brokerInfoList) throws IOException {
        brokerPaymentList = new ArrayList<FbsBrPayment>();
        brokerPaymentList = fbsBrPaymentFacade.findAll();
        tempPaymentList=new ArrayList<FbsBrPayment>();
        int k=0;
        System.out.println("brokerInfilist   ------>"+brokerInfoList.size());
        System.out.println("brokerPaymentList---->"+brokerPaymentList.size());
        for (int i = 0; i < brokerInfoList.size(); i++) {
            for (int j = 0; j < brokerPaymentList.size(); j++) {
                if (brokerInfoList.get(i).getFlatId().equals(brokerPaymentList.get(j).getFkFlatId().toString())) {
                    tempPaymentList.add(k,brokerPaymentList.get(j));
                    k++;
                }
            }
        }
        return tempPaymentList;


    }
}
