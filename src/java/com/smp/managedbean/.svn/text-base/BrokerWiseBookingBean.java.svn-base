/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.managedbean;

import com.itextpdf.text.DocumentException;
import com.smp.entity.FbsBlock;
import com.smp.entity.FbsBooking;
import com.smp.entity.FbsBroker;
import com.smp.entity.FbsFlat;
import com.smp.entity.FbsProject;
import com.smp.fbs.BrokerInfo;
import com.smp.session.FbsBlockFacade;
import com.smp.session.FbsBookingFacade;
import com.smp.session.FbsBrokerFacade;
import com.smp.session.FbsProjectFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.ejb.Stateful;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author smp
 */
@ManagedBean(name = "brokerWiseBookingBean")
@Stateful
@SessionScoped
public class BrokerWiseBookingBean implements Serializable{

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
    @EJB
    FbsBrokerFacade fbsBrokerFacade;
    @EJB
    FbsBookingFacade fbsBookingFacade;
    @EJB
    FbsProjectFacade fbsProjectFacade;
    @EJB
    FbsBlockFacade fbsBlockFacade;
    public FbsBroker fbsBroker = new FbsBroker();
    public ArrayList brokerList = new ArrayList<FbsBroker>();
    public List<FbsBooking> bookingList = new ArrayList<FbsBooking>();
    private List<FbsBlock> fbsBlockList = new ArrayList<FbsBlock>();
    public ArrayList projectNameList = new ArrayList();
    public ArrayList blockNameList = new ArrayList();
    public FbsProject fbsProject = new FbsProject();
    public FbsProject fbsProject1 = new FbsProject();
    public FbsProject fbsProject2 = new FbsProject();
    public FbsBlock fbsBlock = new FbsBlock();
    public FbsBlock fbsBlock1 = new FbsBlock();
    public FbsBlock fbsBlock2 = new FbsBlock();
    private List<FbsFlat> fbsFlatList = new ArrayList<FbsFlat>();
    public ArrayList flatNumberList = new ArrayList();
    public List<FbsBooking> fbsBookingList = new ArrayList<FbsBooking>();
    public List<BrokerInfo> brokerInfoList = new ArrayList<BrokerInfo>();
    public List<BrokerInfo> reBrokerInfoList = new ArrayList<BrokerInfo>();
    public List<BrokerInfo> tempBrokerInfoList = new ArrayList<BrokerInfo>();
    public FbsBroker fbsBrokerName = new FbsBroker();
    public FbsBroker fbsBrokerName1 = new FbsBroker();
    FbsBlock fbsBlockname = new FbsBlock();
    int projId = 0;
    boolean flag = false;
    String xmlFile = "";
    BrokerInfo brokerInfo = new BrokerInfo();
    FbsBlock fbsBlockName = new FbsBlock();
    FbsProject fbsProjectName = new FbsProject();
    private String projName;
    private Integer brokerId;
    private String brName;
    private String blockName;
    private int projID;
    boolean status;
    boolean proj;
    boolean blok;
    boolean flor;
    boolean flt;
    boolean broker;
    String unitCode;

    public BrokerWiseBookingBean() {

        proj = false;
        blok = false;
        flor = false;
        flt = false;
        broker = false;
        this.projName = "";
        this.brokerId = 0;
        this.blockName = "";
        this.brName = "";
        this.unitCode = "";

    }

    @PostConstruct
    public void populate() {

        for (int i = 0; i < fbsBrokerFacade.findAll().size(); i++) {
            brokerList.add(new SelectItem(fbsBrokerFacade.findAll().get(i).getBrokerId(), fbsBrokerFacade.findAll().get(i).getBrName()));
        }

        fbsBookingList = new ArrayList<FbsBooking>();
        brokerInfoList = new ArrayList<BrokerInfo>();
        fbsBookingList = fbsBookingFacade.findAll();

        //brokerInfoList.clear();

        for (int i = 0; i < fbsBookingList.size(); i++) {
            brokerInfo = new BrokerInfo();
            brokerInfo.setRegistrationNo(fbsBookingList.get(i).getRegNumber());
            brokerInfo.setBookingDate(fbsBookingList.get(i).getBookingDt());
            brokerInfo.setBlockId(fbsBookingList.get(i).getBlockId());
            fbsBlockName = fbsBlockFacade.find(brokerInfo.getBlockId());
            brokerInfo.setBlockName(fbsBlockName.getBlockName());
            brokerInfo.setProjId(Integer.parseInt(String.valueOf(fbsBlockName.getFkProjId())));
            fbsProjectName = fbsProjectFacade.find(brokerInfo.getProjId());
            brokerInfo.setProjectName(fbsProjectName.getProjName());
            brokerInfo.setBrokerId(fbsBookingList.get(i).getBrokerId());

            fbsBrokerName = fbsBrokerFacade.find(brokerInfo.getBrokerId());
            brokerInfo.setBrName(fbsBrokerName.getBrName());
            brokerInfo.setFlatId(fbsBookingList.get(i).getFlatId().toString());

            brokerInfoList.add(brokerInfo);

        }
        reBrokerInfoList = brokerInfoList;

    }

    public void populateProject() {
        projectNameList.clear();
        blockNameList.clear();
        flatNumberList.clear();
        fbsProject.setProjId(null);
        fbsBlock1.setBlockId(null);
        this.unitCode = null;
        this.proj = false;
        this.blok = false;
        this.flt = false;
        bookingList = em.createNamedQuery("FbsBooking.findByBrokerId").setParameter("brokerId", fbsBroker.getBrokerId()).getResultList();

        for (int i = 0; i < bookingList.size(); i++) {
            fbsBlock = fbsBlockFacade.find(bookingList.get(i).getBlockId());
            projId = (int) fbsBlock.getFkProjId();
            fbsProject1 = fbsProjectFacade.find(projId);

            Iterator itr = projectNameList.iterator();
            flag = false;
            while (itr.hasNext()) {
                SelectItem ProjID = (SelectItem) itr.next();
                if (ProjID.getValue().equals(projId)) {

                    flag = true;
                    break;
                }


            }

            if (flag == false) {
                projectNameList.add(new SelectItem(fbsProject1.getProjId(), fbsProject1.getProjName()));
            }

        }
        populateBrokerDetailByFilter();

    }

    public void populateBlock() {

        bookingList.clear();
        fbsBlock = new FbsBlock();
        blockNameList.clear();
        flatNumberList.clear();
        fbsBlock1.setBlockId(null);
        this.unitCode = null;
        this.blok = false;
        this.flt = false;

        bookingList = em.createNamedQuery("FbsBooking.findByBrokerId").setParameter("brokerId", fbsBroker.getBrokerId()).getResultList();
        for (int i = 0; i < bookingList.size(); i++) {
            fbsBlock = fbsBlockFacade.find(bookingList.get(i).getBlockId());
            if (fbsBlock.getFkProjId() == fbsProject.getProjId()) {

                Iterator itr = blockNameList.iterator();
                flag = false;
                while (itr.hasNext()) {
                    SelectItem blockId = (SelectItem) itr.next();
                    if (blockId.getValue().equals(fbsBlock.getBlockId())) {

                        flag = true;
                        break;
                    }

                }

                if (flag == false) {
                    blockNameList.add(new SelectItem(fbsBlock.getBlockId(), fbsBlock.getBlockName()));

                }
            }


        }
        populateBrokerDetailByFilter();


    }

    public void populateFlat() throws ParserConfigurationException, SAXException, IOException {
        flatNumberList.clear();
        this.flt = false;
        flatNumberList.clear();
        bookingList = em.createNamedQuery("FbsBooking.findByBrokerId").setParameter("brokerId", fbsBroker.getBrokerId()).getResultList();
        for (int a = 0; a < bookingList.size(); a++) {
            if (fbsBlock1.getBlockId().toString().equals(bookingList.get(a).getBlockId().toString())) {
                flatNumberList.add(new SelectItem(bookingList.get(a).getFlatId()));
            }
        }

        populateBrokerDetailByFilter();
    }

    public void populateBrokerDetailByFilter() {
        tempBrokerInfoList = new ArrayList<BrokerInfo>();
        tempBrokerInfoList.addAll(brokerInfoList);

        int l = 0;

        if (fbsBroker.getBrokerId() != null) {
            fbsBrokerName1 = new FbsBroker();
            fbsBrokerName1 = fbsBrokerFacade.find(fbsBroker.getBrokerId());
            brName = fbsBrokerName1.getBrName();

            if ((!(this.brName.equals(""))) && (!(this.brName.equals("Select")))) {
                this.broker = true;
            }
        }
        if (fbsProject.getProjId() != null) {
            fbsProject1 = new FbsProject();
            fbsProject1 = fbsProjectFacade.find(fbsProject.getProjId());
            projName = fbsProject1.getProjName();
            System.out.println("project id===>" + fbsProject.getProjId());

            if ((!(this.projName.equals(""))) && (!(this.projName.equals("Select")))) {
                this.proj = true;
            }
        }
        if (fbsBlock1.getBlockId() != null) {
            fbsBlockname = new FbsBlock();
            fbsBlockname = fbsBlockFacade.find(fbsBlock1.getBlockId());
            blockName = fbsBlockname.getBlockName();
            System.out.println("blockId====>" + fbsBlock1.getBlockId());

            if ((!(this.blockName.equals(""))) && (!(this.blockName.equals("Select")))) {
                this.blok = true;
            }
        }

        if (this.unitCode != null) {

            if ((!(this.unitCode.equals(""))) && (!(this.unitCode.equals("Select")))) {
                this.flt = true;
            }
        }



        if (this.brName.equals("Select") && this.projName.equals("Select") && this.blockName.equals("Select") && this.unitCode.equals("Select")) {
            l = 0;
        } else if (brName.equals("Select")) {
            reset();
        } else {


            if (this.broker) {
                tempBrokerInfoList = filterByBrokerName(tempBrokerInfoList, this.brName);
            }
            if (this.proj) {
                tempBrokerInfoList = filterByProjectName(tempBrokerInfoList, this.projName);

            }
            if (this.blok) {
                tempBrokerInfoList = filterByBlockName(tempBrokerInfoList, this.blockName);

            }
            if (this.flt) {
                tempBrokerInfoList = filterByUnitcode(tempBrokerInfoList, this.unitCode);

            }


        }
        reBrokerInfoList = tempBrokerInfoList;
        this.broker = false;
        this.proj = false;
        this.blok = false;
        this.flt = false;


    }

    public List<BrokerInfo> filterByBrokerName(List<BrokerInfo> tempBrokerInfoList, String brName) {


        int k = 0;
        List<BrokerInfo> brokerTemp = new ArrayList<BrokerInfo>();

        for (int i = 0; i < tempBrokerInfoList.size(); i++) {
            if (tempBrokerInfoList.get(i).getBrName().equals(brName)) {
                brokerTemp.add(k, tempBrokerInfoList.get(i));
                k++;
            }
        }
        return brokerTemp;

    }

    public List<BrokerInfo> filterByProjectName(List<BrokerInfo> tempBrokerInfoList, String projName) {

        int k = 0;
        List<BrokerInfo> projectTemp = new ArrayList<BrokerInfo>();
        for (int i = 0; i < tempBrokerInfoList.size(); i++) {
            if (tempBrokerInfoList.get(i).getProjectName().equals(projName)) {
                projectTemp.add(k, tempBrokerInfoList.get(i));
                k++;
            }
        }
        return projectTemp;
    }

    public List<BrokerInfo> filterByBlockName(List<BrokerInfo> tempBrokerInfoList, String blockName) {

        int k = 0;
        List<BrokerInfo> blockTemp = new ArrayList<BrokerInfo>();
        for (int i = 0; i < tempBrokerInfoList.size(); i++) {
            if (tempBrokerInfoList.get(i).getBlockName().equals(blockName)) {
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

    public void reset() {
        resetBrokerOption();
        resetFlatOption();
        resetProjctOption();
        resetblockOption();
        this.reBrokerInfoList = this.brokerInfoList;
        projectNameList.clear();
        blockNameList.clear();
        flatNumberList.clear();



    }

    public void resetBrokerOption() {

        this.broker = false;
        this.brName = null;
        fbsBroker.setBrokerId(null);

    }

    public void resetFlatOption() {

        this.flt = false;
        this.unitCode = null;

    }

    public void resetProjctOption() {
        this.projName = "";
        this.proj = false;
        fbsProject.setProjId(null);

    }

    public void resetblockOption() {
        this.blockName = "";
        this.blok = false;
        fbsBlock1.setBlockId(null);

    }

    public void resetblockOptionOnSelect() {
        this.flt = false;
        this.unitCode = null;

        fbsBlock1 = new FbsBlock();
    }

    public void genrateReceipt(BrokerInfo brokerInfo) throws DocumentException, IOException {
        Integer brokerId = brokerInfo.getBrokerId();
        Integer projId = brokerInfo.getProjId();
        Integer blockId =brokerInfo.getBlockId();
        String unitCode = brokerInfo.getFlatId();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/masterBrokerReceipt?brokerId=" + brokerId + "&companyId=" + LoginBean.fbsLogin.getCompanyId()
                + "&projId=" + projId + "&blockId=" + blockId + "&unitCode=" + unitCode + "");
    }

    public void genrateReceipt1() throws DocumentException, IOException {
        Integer brokerId = fbsBroker.getBrokerId();
        Integer projId = fbsProject.getProjId();
        Integer blockId = fbsBlock1.getBlockId();
        String unitCode = this.unitCode;
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/masterBrokerReceipt?brokerId=" + brokerId + "&companyId=" + LoginBean.fbsLogin.getCompanyId()
                + "&projId=" + projId + "&blockId=" + blockId + "&unitCode=" + unitCode + "");


    }

    public ArrayList getBrokerList() {
        return brokerList;


    }

    public void setBrokerList(ArrayList brokerList) {
        this.brokerList = brokerList;

    }

    public FbsBroker getFbsBroker() {
        return fbsBroker;


    }

    public void setFbsBroker(FbsBroker fbsBroker) {
        this.fbsBroker = fbsBroker;


    }

    public ArrayList getProjectNameList() {
        return projectNameList;


    }

    public void setProjectNameList(ArrayList projectNameList) {
        this.projectNameList = projectNameList;


    }

    public ArrayList getBlockNameList() {
        return blockNameList;


    }

    public void setBlockNameList(ArrayList blockNameList) {
        this.blockNameList = blockNameList;


    }

    public FbsProject getFbsProject() {
        return fbsProject;


    }

    public void setFbsProject(FbsProject fbsProject) {
        this.fbsProject = fbsProject;

    }

    public FbsBlock getFbsBlock1() {
        return fbsBlock1;
    }

    public void setFbsBlock1(FbsBlock fbsBlock1) {
        this.fbsBlock1 = fbsBlock1;

    }

    public ArrayList getFlatNumberList() {
        return flatNumberList;
    }

    public void setFlatNumberList(ArrayList flatNumberList) {
        this.flatNumberList = flatNumberList;
    }

    public List<BrokerInfo> getBrokerInfoList() {
        return brokerInfoList;
    }

    public void setBrokerInfoList(List<BrokerInfo> brokerInfoList) {
        this.brokerInfoList = brokerInfoList;
    }

    public List<BrokerInfo> getReBrokerInfoList() {
        return reBrokerInfoList;
    }

    public void setReBrokerInfoList(List<BrokerInfo> reBrokerInfoList) {
        this.reBrokerInfoList = reBrokerInfoList;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;

    }
}
