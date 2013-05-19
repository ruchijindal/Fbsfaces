/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.managedbean;

import com.itextpdf.text.DocumentException;
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
import com.smp.report.BrokerDetails;
import com.smp.session.FbsBlockFacade;
import com.smp.session.FbsBrokerCatFacade;
import com.smp.session.FbsBrokerFacade;
import com.smp.session.FbsDiscountFacade;
import com.smp.session.FbsFlatTypeFacade;
import com.smp.session.FbsPlannameFacade;
import com.smp.session.FbsProjectFacade;
import com.smp.session.FbsServicetaxFacade;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import javax.faces.model.SelectItem;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author smp
 */
@ManagedBean(name = "brokerBean")
@ApplicationScoped
@Stateful
public class BrokerBean {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
    @EJB

    FbsBrokerFacade fbsBrokerFacade;
    @EJB
    FbsFlatTypeFacade FbsFlatTypeFacade;
    @EJB
    FbsBrokerCatFacade fbsBrokerCatFacade;
    @EJB
    FbsBlockFacade fbsBlockFacade;
    @EJB
    FbsProjectFacade fbsProjectFacade;
    @EJB
    BrokerDetails brokerDetails;
    @EJB
    FbsDiscountFacade fbsDiscountFacade;
    @EJB
    FbsPlannameFacade fbsPlannameFacade;
    @EJB
    FbsServicetaxFacade fbsServicetaxFacade;
    FbsPlanname fbsPlanname;
    public FbsBroker fbsBroker = new FbsBroker();
    public List<FbsBroker> brokerList = new ArrayList();
    private String projid = "";
    public ArrayList categoryList;
    BrokerInfo brokerInfo = new BrokerInfo();
    static BrokerInfo brokerdelInfo = new BrokerInfo();
    BrokerInfo brokerInfo1 = new BrokerInfo();
    public List<BrokerInfo> brokerInfoList;
    private FbsBrokerCat fbsBrokerCat = new FbsBrokerCat();
    private FbsBrokerCat fbsBrokerCat1 = new FbsBrokerCat();
    private FbsBroker fbsBroker1 = new FbsBroker();
    private FbsProject fbsProject = new FbsProject();
    private FbsBlock fbsBlock = new FbsBlock();
    public List<FbsBooking> fbsBookingList = new ArrayList<FbsBooking>();
    FbsBlock fbsBlockName = new FbsBlock();
    FbsProject fbsProjectName = new FbsProject();
    private List<FbsFlat> fbsFlatList = new ArrayList<FbsFlat>();
    String xmlFile = "";
    List<FbsFlat> refFlatList = new ArrayList<FbsFlat>();
    public FbsFlatType fbsFlatType = new FbsFlatType();
    public List<BrokerInfo> brokerInfoList1;
    List<FbsBrPayment> fbsBrPaymentlist = new ArrayList<FbsBrPayment>();
    public List<FbsPayment> fbsPaymentList = new ArrayList<FbsPayment>();
    public FbsDiscount fbsDiscount = new FbsDiscount();
    List<FbsBrPayment> brokerPaymentList = new ArrayList<FbsBrPayment>();
    float totalPaidAmount = 0;
    float totalPayableAmount = 0;
    float totalAmount = 0;
    int discountPercantadge = 0;
    int discountId = 0;
    public FbsBooking fbsBooking1 = new FbsBooking();
    float totalCommission = 0;
    int total;
    float totalPayable;
    float totalPaid;
    float totalpayoutstanding = 0;
    float totalOutstanding = 0;
    float brokerpaymentAmount = 0;
    float clearedAmount = 0;
    float pendingAmount = 0;
    String viewStatus;

    @PostConstruct
    public void populate() {
        fbsBroker = new FbsBroker();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);
        projid = (String) session.getAttribute("projId");
        //    System.out.println("project id in broker bean....................."+projid);
        brokerList.clear();
        brokerList = fbsBrokerFacade.findAll();
        if (brokerList.size() <= 5) {
            viewStatus = "View";
        } else {
            viewStatus = "View More";
        }

        categoryList = new ArrayList();
        for (int i = 0; i < fbsBrokerCatFacade.findAll().size(); i++) {
            categoryList.add(new SelectItem(fbsBrokerCatFacade.findAll().get(i).getCategoryId(), fbsBrokerCatFacade.findAll().get(i).getCategoryName()));
        }

        brokerInfoList1 = new ArrayList<BrokerInfo>();
        for (int i = 0; i < brokerList.size(); i++) {
            brokerInfo = new BrokerInfo();
            brokerInfo.setBrokerId(brokerList.get(i).getBrokerId());
            brokerInfo.setBrName(brokerList.get(i).getBrName());
            brokerInfo.setBrAdd(brokerList.get(i).getBrAdd());
            brokerInfo.setBrMail(brokerList.get(i).getBrMail());
            brokerInfo.setBrMobile(brokerList.get(i).getBrMobile());
            brokerInfo.setBrPhone(brokerList.get(i).getBrPhone());
            brokerInfo.setBrSite(brokerList.get(i).getSite());
            brokerInfo.setCategoryId(brokerList.get(i).getCategoryId());
            brokerInfo.setCompanyId(LoginBean.fbsLogin.getCompanyId().toString());

            fbsBrokerCat1 = fbsBrokerCatFacade.find(brokerInfo.getCategoryId());
            brokerInfo.setCategoryName(fbsBrokerCat1.getCategoryName());
            brokerInfo.setComissionRate(fbsBrokerCat1.getCommission());
            brokerInfoList1.add(brokerInfo);
        }

    }

    public void addBroker() {
        fbsBroker.setCompanyId(LoginBean.fbsLogin.getCompanyId());
        fbsBrokerFacade.create(fbsBroker);
        fbsBroker = new FbsBroker();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Congrates! Broker Successfully Added"));
        populate();
    }

    public void editBroker(org.primefaces.event.RowEditEvent e) {
        // fbsBrokerFacade.edit((FbsBroker) e.getObject());
        brokerInfo = (BrokerInfo) e.getObject();
        fbsBroker = new FbsBroker();
        fbsBroker.setBrAdd(brokerInfo.getBrAdd());
        fbsBroker.setBrMail(brokerInfo.getBrMail());
        fbsBroker.setBrMobile(brokerInfo.getBrMobile());
        fbsBroker.setBrName(brokerInfo.getBrName());
        fbsBroker.setBrPhone(brokerInfo.getBrPhone());
        fbsBroker.setBrokerId(brokerInfo.getBrokerId());
        fbsBroker.setCategoryId(brokerInfo.getCategoryId());
        fbsBroker.setSite(brokerInfo.getBrSite());
        fbsBroker.setCompanyId(LoginBean.fbsLogin.getCompanyId());
        fbsBrokerFacade.edit(fbsBroker);
        populate();
    }

    public void deleteBroker() throws IOException {
        fbsBroker = fbsBrokerFacade.find(brokerdelInfo.getBrokerId());
        fbsBrokerFacade.remove(fbsBroker);
        populate();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/Broker/setBroker.xhtml");
    }

    public void delBroker(BrokerInfo brokerInfo) {
        brokerdelInfo = brokerInfo;
    }

    public void findBroker(BrokerInfo brokerInfo) throws IOException {
        // brokerInfo = (BrokerInfo) event.getObject();
        brokerPaymentList.clear();
        String categoryId = brokerInfo.getCategoryId().toString();
        System.out.println("catId" + categoryId);

        fbsBrokerCat = (FbsBrokerCat) em.createNamedQuery("FbsBrokerCat.findByCategoryId").setParameter("categoryId", Integer.parseInt(categoryId)).getResultList().get(0);
        int commission = fbsBrokerCat.getCommission();

        System.out.println("+++com +++" + commission);
        fbsBookingList = em.createNamedQuery("FbsBooking.findByBrokerId").setParameter("brokerId", brokerInfo.getBrokerId()).getResultList();
//     for(int i=0;i<fbsBooking.size();i++)
//     {
//         int blockId=fbsBooking.get(i).getBlockId();
//         fbsBlock=(FbsBlock)em.createNamedQuery("FbsBlock.findByBlockId").setParameter("blockId", blockId).getResultList().get(0);
//        String projId=Long.toString(fbsBlock.getFkProjId());
//        fbsProject=(FbsProject)em.createNamedQuery("FbsProject.findByProjId").setParameter("projId", Integer.parseInt(projId)).getResultList().get(0);
//     }

        brokerInfo1 = new BrokerInfo();
        brokerInfo1.setBrokerId(brokerInfo.getBrokerId());
        brokerInfo1.setBrName(brokerInfo.getBrName());
        brokerInfo1.setBrAdd(brokerInfo.getBrAdd());
        brokerInfo1.setBrMail(brokerInfo.getBrMail());
        brokerInfo1.setBrMobile(brokerInfo.getBrMobile());
        brokerInfo1.setBrPhone(brokerInfo.getBrPhone());
        brokerInfo1.setBrSite(brokerInfo.getBrSite());
        brokerInfo1.setCategoryName(brokerInfo.getCategoryName());
        brokerInfo1.setComissionRate(brokerInfo.getComissionRate());
        brokerInfo1.setCompanyId(LoginBean.fbsLogin.getCompanyId().toString());

        brokerInfoList = new ArrayList<BrokerInfo>();
        for (int a = 0; a < fbsBookingList.size(); a++) {
            try {

                brokerInfo = new BrokerInfo();
                brokerInfo.setBlockId(fbsBookingList.get(a).getBlockId());
                fbsBlockName = fbsBlockFacade.find(brokerInfo.getBlockId());
                brokerInfo.setBlockName(fbsBlockName.getBlockName());
                brokerInfo.setProjId(Integer.parseInt(String.valueOf(fbsBlockName.getFkProjId())));
                fbsProjectName = fbsProjectFacade.find(brokerInfo.getProjId());
                brokerInfo.setProjectName(fbsProjectName.getProjName());

                fbsFlatList = new ArrayList();
                int l = 0;
                xmlFile = fbsBlockName.getXmlFile();
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

                for (int b = 0; b < fbsFlatList.size(); b++) {
                    if (fbsFlatList.get(b).getFlatId().toString().equals(fbsBookingList.get(a).getFlatId().toString())) {
                        brokerInfo.setFlatId(fbsFlatList.get(b).getFlatId().toString());
                        brokerInfo.setFlatNo(fbsFlatList.get(b).getFlatNo());
                        System.out.println("++flat  no++++" + brokerInfo.getFlatNo());
                        brokerInfo.setFloorNo(fbsFlatList.get(b).getFloorNo().toString());
                        System.out.println("+++floor no+++" + brokerInfo.getFloorNo());
                        brokerInfo.setFlatTypeId(Integer.parseInt(fbsFlatList.get(b).getFlatType()));
                        fbsFlatType = FbsFlatTypeFacade.find(brokerInfo.getFlatTypeId());
                        brokerInfo.setFlatSpecification(fbsFlatType.getFlatSpecification());
                        System.out.println("+++flattype+++" + brokerInfo.getFlatSpecification());

                        fbsBrPaymentlist = em.createNamedQuery("FbsBrPayment.findByFkFlatId").setParameter("fkFlatId", fbsBookingList.get(a).getFlatId()).getResultList();
                        totalPaidAmount = 0;
                        for (int i = 0; i < fbsBrPaymentlist.size(); i++) {
                            totalPaidAmount = (float) totalPaidAmount + fbsBrPaymentlist.get(i).getAmount();
                        }
                        brokerInfo.setAmount(totalPaidAmount);

                        fbsBooking1 = (FbsBooking) em.createNamedQuery("FbsBooking.findByFlatId").setParameter("flatId", fbsBookingList.get(a).getFlatId()).getResultList().get(0);

                        discountId = fbsBooking1.getDiscountId();
                        fbsDiscount = fbsDiscountFacade.find(discountId);
                        discountPercantadge = fbsDiscount.getPercentage();
                        brokerInfo.setDiscountPercantadge(discountPercantadge);
                        fbsPlanname = fbsPlannameFacade.find(fbsBooking1.getPlanId());


                        // fbsFlatType = FbsFlatTypeFacade.find(brokerInfo.getFlatTypeId());
                        //   brokerInfo.setFlatSpecification(fbsFlatType.getFlatSpecification());

                        brokerInfo.setBr(fbsFlatType.getFlatBsp());
                        brokerInfo.setSba(fbsFlatType.getFlatSba());
                        int br = brokerInfo.getBr();
                        int sba = brokerInfo.getSba();
                        System.out.println("+++br +++" + br);
                        System.out.println("+++ sba +++" + sba);
                        int bsp;
                        int br_commisson;
                        if (br != 0 && sba != 0) {
                            bsp = sba * br;
                            bsp = bsp - ((discountPercantadge * bsp) / 100) - ((fbsPlanname.getDiscount() * bsp) / 100);
                            System.out.println("+++bsp +++" + bsp);
                        } else {
                            bsp = 0;
                        }
                        brokerInfo.setBsp(bsp);
                        br_commisson = (bsp * commission) / 100;
                        System.out.println("++++++" + br_commisson);
                        brokerInfo.setCommission(br_commisson);
                        // totalCommission=getTotalCommission(brokerInfo.getCommission());


                        fbsPaymentList = em.createNamedQuery("FbsPayment.findByUnitCode").setParameter("unitCode", fbsFlatList.get(b).getFlatId().toString()).getResultList();
                        System.out.println(" size of payment list==> " + fbsPaymentList.size());
                        totalAmount = 0;
                        FbsServicetax fbsServicetax = new FbsServicetax();

                        for (int j = 0; j < fbsPaymentList.size(); j++) {
                            System.out.println("amount===> " + fbsPaymentList.get(j).getPaidAmount());
                            totalAmount = totalAmount + fbsPaymentList.get(j).getPaidAmount();
                            fbsServicetax = fbsServicetaxFacade.find(fbsPaymentList.get(j).getServiceTax());
                            //    System.out.println("servicetax..?????????????/"+fbsServiceTax.getServicetax());
                            // totalAmount=totalPaidAmount+(totalPaidAmount*fbsServicetax.getServicetax()/100);
                            Double temp = 1 + ((double) fbsServicetax.getServicetax()) / 100;
                            //   System.out.println("total Amount......."+totalAmount);
                            //   System.out.println("temp value........"+temp);
                            totalAmount = (float) (totalAmount / temp);
                        }
                        System.out.println("total amount=-==>" + totalAmount);

                        int bspPercantage = fbsBrokerCat.getBspPercent();
                        int brokerPercantage = fbsBrokerCat.getBrokerPercent();
                        System.out.println("bspPercantage===> " + bspPercantage);
                        System.out.println(" brokerPercantage===> " + brokerPercantage);
                        int installment = (int) 100 / brokerPercantage;
                        int rem = 100 % brokerPercantage;
                        if (rem > 0) {
                            installment++;
                        }
                        System.out.println(" installment===> " + installment);
                        float bspAmount = 0;

                        //float bspAmountNew = 0;
                        int count = 0;

                        System.out.println(" totalAmount===> " + totalAmount);
                        System.out.println("totalCommission===> " + br_commisson);
                        System.out.println("totalBSP===> " + bsp);

                        bspAmount = 0;
                        for (int i1 = 1; i1 <= installment; i1++) {

                            bspAmount = (float) (bspPercantage * i1 * bsp) / 100;
                            System.out.println("  bspAmount===> " + bspAmount);


                            if (totalAmount >= bspAmount) {
                                count++;
                            }
                            System.out.println("  count ===> " + count);
                        }
                        totalPayableAmount = 0;

                        if (count < installment) {
                            totalPayableAmount = (float) (brokerPercantage * count * br_commisson) / 100;
                            System.out.println("total payable amount is in broker  bean   " + totalPayableAmount);
                        } else {

                            totalPayableAmount = br_commisson;
                        }
                        System.out.println("total Payable amount==>*" + totalPayableAmount);

                        brokerInfo.setPayableAmount(totalPayableAmount - totalPaidAmount);



                        break;
                    } else {
                        System.out.println("+++HELLO+++");
                    }
                }
                brokerInfo.setTotalCommission(totalCommission);
                brokerInfoList.add(brokerInfo);
            } catch (Exception ex) {
                Logger.getLogger(QuickBookingBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            total = 0;
            totalPayable = 0;
            totalPaid = 0;
            totalpayoutstanding = 0;
            totalOutstanding = 0;

            for (int i = 0; i < brokerInfoList.size(); i++) {
                total += brokerInfoList.get(i).getCommission();
                totalPayable += brokerInfoList.get(i).getPayableAmount();
                totalPaid += brokerInfoList.get(i).getAmount();
                totalpayoutstanding = totalPayable;
                totalOutstanding = total - totalPaid;

            }

        }
        clearedAmount = 0;
        pendingAmount = 0;
        brokerpaymentAmount = 0;
        brokerPaymentList = em.createNamedQuery("FbsBrPayment.findByBrokerId").setParameter("brokerId", brokerInfo1.getBrokerId()).getResultList();
        System.out.println("size of broker Payment Lis is " + brokerPaymentList.size());
        for (int i = 0; i < brokerPaymentList.size(); i++) {
            brokerpaymentAmount = (float) brokerpaymentAmount + brokerPaymentList.get(i).getAmount();

            if (brokerPaymentList.get(i).getStatus().equals("Cleared")) {
                clearedAmount = clearedAmount + brokerPaymentList.get(i).getAmount();
            } else {
                pendingAmount = pendingAmount + brokerPaymentList.get(i).getAmount();
            }
        }

        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/Broker/brokerMaster.xhtml");

    }

    public List<FbsBrPayment> getBrokerPaymentList() {
        return brokerPaymentList;
    }

    public void setBrokerPaymentList(List<FbsBrPayment> brokerPaymentList) {
        this.brokerPaymentList = brokerPaymentList;
    }

    public void genrateReceipt(Integer brokerId) throws DocumentException, IOException {
        System.out.println("brokerId*=======> " + brokerId);
        System.out.println("comapnyId*=======> " + LoginBean.fbsLogin.getCompanyId());
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/masterBrokerReceipt?brokerId=" + brokerId + "&companyId=" + LoginBean.fbsLogin.getCompanyId());
    }

    public void setFbsBooking(List<FbsBooking> fbsBooking) {
        this.fbsBookingList = fbsBooking;
    }

    public List<FbsBooking> getFbsBooking() {
        return fbsBookingList;
    }

    public FbsBrokerCat getFbsBrokerCat() {
        return fbsBrokerCat;
    }

    public void setFbsBrokerCat(FbsBrokerCat fbsBrokerCat) {
        this.fbsBrokerCat = fbsBrokerCat;
    }

    public void setFbsBroker(FbsBroker fbsBroker) {
        this.fbsBroker = fbsBroker;
    }

    public FbsBroker getFbsBroker() {
        return fbsBroker;
    }

    public void setBrokerList(List<FbsBroker> brokerList) {
        this.brokerList = brokerList;
    }

    public List<FbsBroker> getBrokerList() {
        return brokerList;
    }

    public void setCategoryList(ArrayList categoryList) {
        this.categoryList = categoryList;
    }

    public ArrayList getCategoryList() {
        return categoryList;
    }

    public List<BrokerInfo> getBrokerInfoList() {
        return brokerInfoList;
    }

    public void setBrokerInfoList(List<BrokerInfo> brokerInfoList) {
        this.brokerInfoList = brokerInfoList;
    }

    public List<BrokerInfo> getBrokerInfoList1() {
        return brokerInfoList1;
    }

    public void setBrokerInfoList1(List<BrokerInfo> brokerInfoList1) {
        this.brokerInfoList1 = brokerInfoList1;
    }

    public BrokerInfo getBrokerInfo() {
        return brokerInfo;
    }

    public void setBrokerInfo(BrokerInfo brokerInfo) {
        this.brokerInfo = brokerInfo;
    }

    public BrokerInfo getBrokerInfo1() {
        return brokerInfo1;
    }

    public void setBrokerInfo1(BrokerInfo brokerInfo1) {
        this.brokerInfo1 = brokerInfo1;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public float getTotalPayable() {
        return totalPayable;
    }

    public void setTotalPayable(float totalPayable) {
        this.totalPayable = totalPayable;
    }

    public float getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(float totalPaid) {
        this.totalPaid = totalPaid;
    }

    public float getTotalOutstanding() {
        return totalOutstanding;
    }

    public void setTotalOutstanding(float totalOutstanding) {
        this.totalOutstanding = totalOutstanding;
    }

    public float getTotalpayoutstanding() {
        return totalpayoutstanding;
    }

    public void setTotalpayoutstanding(float totalpayoutstanding) {
        this.totalpayoutstanding = totalpayoutstanding;
    }

    public float getBrokerpaymentAmount() {
        return brokerpaymentAmount;
    }

    public void setBrokerpaymentAmount(float brokerpaymentAmount) {
        this.brokerpaymentAmount = brokerpaymentAmount;
    }

    public float getClearedAmount() {
        return clearedAmount;
    }

    public void setClearedAmount(float clearedAmount) {
        this.clearedAmount = clearedAmount;
    }

    public float getPendingAmount() {
        return pendingAmount;
    }

    public void setPendingAmount(float pendingAmount) {
        this.pendingAmount = pendingAmount;
    }

    public String getViewStatus() {
        return viewStatus;
    }

    public void setViewStatus(String viewStatus) {
        this.viewStatus = viewStatus;
    }
}
