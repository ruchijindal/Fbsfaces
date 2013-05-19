/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.report;

import com.smp.entity.FbsBlock;
import com.smp.entity.FbsBooking;
import com.smp.entity.FbsCharge;
import com.smp.entity.FbsPlc;
import com.smp.session.FbsBlockFacade;
import com.smp.session.FbsDiscountFacade;
import com.smp.session.FbsPlannameFacade;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

@Stateless
public class ChargesAndPlanDetails {
    String flatId = "";
    String planname = "";
    String fullPlanName="";
    String plcId = "";
    List<String> plcidlist=new ArrayList<String>();
    int planId;
    int discountid;
    Date bookingdate = new Date();
    long discount=0;
    String discountType;
    @EJB
    FbsPlannameFacade fbsPlannameFacade;
    @EJB
    FbsDiscountFacade fbsDiscountFacade;
    @EJB
    FbsBlockFacade fbsBlockFacade;
    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager entityManager;
    public ChargesAndPlanDetails findPlannameAndDiscount(String unitCode) {
        plcidlist.clear();
System.out.println("uniot code in charge and plan "+unitCode);
        List<FbsBooking> fbsBooking = entityManager.createNamedQuery("FbsBooking.findByFlatId").setParameter("flatId", Integer.parseInt(unitCode.trim())).getResultList();
        for (int j = 0; j < fbsBooking.size(); j++) {
            flatId = unitCode;
            planId = fbsBooking.get(j).getPlanId();
           // System.out.println("Plan Idddddddddddddddddddd"+planId);
            planname=fbsPlannameFacade.find(planId).getPlanName();
            fullPlanName=fbsPlannameFacade.find(planId).getFullName();
            if (planname == null) {
                planname = "";
            }
            if(fullPlanName==null)
            {
                fullPlanName="";
            }
           // plcId = fbsBooking.get(j).getPlcId().toString();
            List<FbsPlc> plclist=new ArrayList<FbsPlc>();
            //System.out.println("unit code"+unitCode);
            //System.out.println("bloc id"+entityManager.createNamedQuery("FbsBooking.findByFlatId").setParameter("flatId",Integer.parseInt(unitCode)));
            FbsBooking fbsBookingtemp=(FbsBooking) entityManager.createNamedQuery("FbsBooking.findByFlatId").setParameter("flatId",Integer.parseInt(unitCode)).getSingleResult();
            FbsBlock fbsblock=fbsBlockFacade.find(fbsBookingtemp.getBlockId());
           String xmlFile1 = fbsblock.getXmlFile();
           try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            org.w3c.dom.Document doc = db.parse(new InputSource(new StringReader(xmlFile1)));
            doc.getDocumentElement().normalize();
            Node block1 = doc.getFirstChild();
            // NodeList block = doc.getElementsByTagName("block");
            NodeList floorList1 = block1.getChildNodes();
            //NodeList floorList1 = block.item(0).getChildNodes();
            for (int k = 0; k < floorList1.getLength(); k++) {
                Node floor = floorList1.item(k);
                if (floor.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element floorElement = (org.w3c.dom.Element) floor;
                //    if (floorElement.getAttribute("floor_id").equals(floorId1))
                    {
                        NodeList floorNoList = floorElement.getElementsByTagName("floor_number");
                        Node fnoElement = (Node) floorNoList.item(0);
                        NodeList flatList = floorElement.getElementsByTagName("flat");
                        for (int l = 0; l < flatList.getLength(); l++) {
                            org.w3c.dom.Element flatElement = (org.w3c.dom.Element) flatList.item(l);

                            if (flatElement.getAttribute("flat_id").equals(unitCode)) {

                                NodeList plcList = flatElement.getElementsByTagName("plc");
                                //System.out.println("hello smp"+ plcList.getLength());
                                if (plcList.getLength() != 0) {
                                    Node plcElement = (Node) plcList.item(0);

                                    NodeList plcChilds = plcElement.getChildNodes();
                                    for (int i = 0; i < plcChilds.getLength(); i++)
                                    {
                                        Node plcchildnode= plcChilds.item(i);
                                        if(plcchildnode.getNodeType()==Node.ELEMENT_NODE)
                                        {
                                        org.w3c.dom.Element plcelement=(org.w3c.dom.Element) plcchildnode;
                                     //   System.out.println("plc child "+plcelement.getTextContent());
                                     //   System.out.println("plc child11111 "+plcchildnode.getNodeName().toString());
                                        plcidlist.add(plcelement.getTextContent());
                                        }
                                    }

                                }else{

                                     }
                            }
                        }
                    }
                }
            }
                }catch(Exception e)
           {
                    e.printStackTrace();
                }
            discountid=fbsBooking.get(j).getDiscountId();
            discount=fbsDiscountFacade.find(discountid).getPercentage();
            discountType=fbsDiscountFacade.find(discountid).getDiscountType();
        }

//************************************Total other Charges ********************************************************************
        this.flatId = flatId;
        this.planname = planname;
        this.plcId =plcId;
        this.discount=discount;
        this.discountType=discountType;

        return this;
    }
    public List<FbsCharge> findTotalCharges(String projectID)
    {
       List<FbsCharge> fbsCharge = entityManager.createNamedQuery("FbsCharge.findByFkProjId").setParameter("fkProjId", projectID).getResultList();
      return fbsCharge;
    }
}
