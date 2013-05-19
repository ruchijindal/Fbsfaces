/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.report;

import com.smp.entity.FbsBlock;
import com.smp.entity.FbsBooking;
import com.smp.entity.FbsBroker;
import com.smp.entity.FbsBrokerCat;
import com.smp.entity.FbsFlat;
import com.smp.entity.FbsFlatType;
import com.smp.entity.FbsProject;
import com.smp.fbs.BrokerInfo;
import com.smp.fbs.FlatInfo;
import com.smp.session.FbsBlockFacade;
import com.smp.session.FbsBookingFacade;
import com.smp.session.FbsBrokerCatFacade;
import com.smp.session.FbsBrokerFacade;
import com.smp.session.FbsFlatTypeFacade;
import com.smp.session.FbsProjectFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author smp
 */
@ManagedBean(name = "brokerDetails")
@Stateless
public class BrokerDetails {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager entityManager;
    @EJB
    FbsFlatTypeFacade fbsFlatTypeFacade;
    @EJB
    FbsBrokerFacade fbsBrokerFacade;
    @EJB
    FbsBrokerCatFacade fbsBrokerCatFacade;
    @EJB
    FbsBookingFacade fbsBookingFacade;
    @EJB
    FbsBlockFacade fbsBlockFacade;
    @EJB
    FbsProjectFacade fbsProjectFacade;
    private FbsBlock fbsBlock = new FbsBlock();
    public FbsBooking fbsBooking = new FbsBooking();
    public FbsBroker fbsBroker = new FbsBroker();
    public FbsFlatType fbsFlatType = new FbsFlatType();
    FbsProject fbsProject = new FbsProject();
    private List<FbsFlat> fbsFlatList = new ArrayList<FbsFlat>();
    List<FbsFlat> refFlatList = new ArrayList<FbsFlat>();
    public List<FbsBooking> fbsBookingList = new ArrayList<FbsBooking>();
    public List<BrokerInfo> brokerInfoList = new ArrayList<BrokerInfo>();
    public FlatInfo flatInfo;
    int brokerId = 0;
    String brokerName = "";
    String xmlFile = "";
    int categoryId = 0;
    String categoryName = "";
    private FbsBrokerCat fbsBrokerCat;
    BrokerInfo brokerInfo;

    public BrokerDetails findBrokerdetails(int unitCode) {


        System.out.println("unite code-->"+unitCode);
        fbsBooking = (FbsBooking) entityManager.createNamedQuery("FbsBooking.findByFlatId").setParameter("flatId", unitCode).getResultList().get(0);
        int brokerId = fbsBooking.getBrokerId();

        FbsBroker fbsBroker = fbsBrokerFacade.find(brokerId);
        System.out.println("brokerId-->"+brokerId);
        System.out.println("FbsBrokerList Size-->"+fbsBroker.getCategoryId());
        //int brokerId = fbsBroker.getBrokerId();
       String brokerName = fbsBroker.getBrName();
        int categoryId = fbsBroker.getCategoryId();
        FbsBrokerCat fbsBrokerCat = fbsBrokerCatFacade.find(categoryId);
        String categoryName = fbsBrokerCat.getCategoryName();
        if (brokerId == 0) {
            brokerId = 0;
        }

        if (brokerName == null) {
            brokerName = " ";
        }

        if (categoryName == null) {
            categoryName = " ";
        }

        this.brokerId = brokerId;
        this.brokerName = brokerName;
        this.categoryName = categoryName;
        return this;
    }

    

    public List<BrokerInfo> getBrokerInfoList() {
        return brokerInfoList;
    }

    public void setBrokerInfoList(List<BrokerInfo> brokerInfoList) {
        this.brokerInfoList = brokerInfoList;
    }

    public FbsBroker getFbsBroker() {
        return fbsBroker;
    }

    public void setFbsBroker(FbsBroker fbsBroker) {
        this.fbsBroker = fbsBroker;
    }

    
}
