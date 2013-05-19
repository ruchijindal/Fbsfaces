/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.managedbean;

import com.smp.entity.FbsBlock;
import com.smp.entity.FbsBooking;
import com.smp.entity.FbsFlat;
import com.smp.entity.FbsLoan;
import com.smp.entity.FbsProject;
import com.smp.session.FbsBlockFacade;
import com.smp.session.FbsBookingFacade;
import com.smp.session.FbsLoanFacade;
import com.smp.session.FbsProjectFacade;
import java.io.Serializable;
import java.io.StringReader;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.PostActivate;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
@Stateless
public class LoanInfoBean implements Serializable{

    /** Creates a new instance of LoanInfoBean */
    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
    @EJB
    FbsLoanFacade fbsLoanFacade;
    @EJB
    FbsProjectFacade fbsProjectFacade;
    @EJB
    FbsBlockFacade fbsBlockFacade;
    @EJB
    FbsBookingFacade fbsBookingFacade;
    FbsLoan fbsLoan = new FbsLoan();
    List<FbsLoan> fbsLoanList = new ArrayList<FbsLoan>();
    List<FbsBlock> fbsBlockList = new ArrayList<FbsBlock>();
    List<FbsFlat> fbsFlatList = new ArrayList<FbsFlat>();
    List<FbsFlat> refFlatList = new ArrayList<FbsFlat>();
    List<FbsBooking> fbsBookingList = new ArrayList<FbsBooking>();
    public ArrayList blockList = new ArrayList();
    public ArrayList flatNoList = new ArrayList();
    public String[] floorList;
    FbsProject fbsProject = new FbsProject();
    FbsBlock fbsBlock = new FbsBlock();
    public ArrayList projectList;
    String xmlFile = "";
    public String floorName;
    Integer flatid;

    public LoanInfoBean() {
        fbsProject = new FbsProject();
    }

    @PostConstruct
    public void populate() {
        fbsLoanList = fbsLoanFacade.findAll();
        fbsLoan=new FbsLoan();
        projectList = new ArrayList();
        for (int i = 0; i < fbsProjectFacade.findAll().size(); i++) {
            projectList.add(new SelectItem(fbsProjectFacade.findAll().get(i).getProjId(), fbsProjectFacade.findAll().get(i).getProjName()));
        }

    }
   

    public void populateProjectCode() {
        fbsProject = (FbsProject) em.createNamedQuery("FbsProject.findByProjId").setParameter("projId", fbsProject.getProjId()).getResultList().get(0);
        //  System.out.println("yahoooooooooooooooooooooooooooooooooooo");
        populateBlocks();
    }

    public void populateBlocks() {
        //int size = em.createNamedQuery("FbsBlock.findByFkProjId").setParameter("fkProjId", fbsProject.getProjId()).getResultList().size();
        fbsBlockList.clear();
        blockList.clear();
        Query query = em.createNamedQuery("FbsBlock.findByProjId&Status");
        query.setParameter("status", "lock");
        query.setParameter("fkProjId", fbsProject.getProjId());
        fbsBlockList = query.getResultList();
        for (int i = 0; i < fbsBlockList.size(); i++) {
            blockList.add(new SelectItem(fbsBlockList.get(i).getBlockId(), fbsBlockList.get(i).getBlockName()));
        }
        //  System.out.println("Size of the bl;ock List is "+blockList.size());
        //populateFloors();
      //  System.out.println("Hellooooooooooooooooooooooojjjjjjjjjjjjjjjjjjjjjjjjjjjj");
    }

    public void populateFloors() {
        System.out.println("huuuuuuuuuuureeeyyyyyyyy "+fbsBlock.getBlockId());
        FbsBlock temp=new FbsBlock();
        temp = (FbsBlock) em.createNamedQuery("FbsBlock.findByBlockId").setParameter("blockId", fbsBlock.getBlockId()).getResultList().get(0);
        fbsBlock=temp;
        
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
            ex.printStackTrace();
            // Logger.getLogger(QuickBookingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void populateFlats() {
       // System.out.println("helllllllllllllllllllllllllllllllllll");
        int l = 0;
        int f = 0;
        fbsLoan=new FbsLoan();
        fbsFlatList = new ArrayList<FbsFlat>();
        for (int i = 0; i < this.refFlatList.size(); i++) {
            if (this.refFlatList.get(i).getFloorNo().equals(this.floorName.trim())) {
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
                    //  f = 1;
                    flatNoList.add(new SelectItem(fbsFlatList.get(j).getFlatId(), fbsFlatList.get(j).getFlatNo()));
                    // break;
                } else {
                    continue;
                }
            }

        }

    }

    public void checkloaninfo() {
       // System.out.println("flat id"+flatid);
        for (int i = 0; i < fbsLoanList.size(); i++) {
               //System.out.println("in loop "+fbsLoanList.get(i).getUnitCode());
            if (flatid.equals(fbsLoanList.get(i).getUnitCode())) {
                fbsLoan = fbsLoanList.get(i);
                System.out.println("loan info available");
            }
        }
        System.out.println("finshing loan checking"+ fbsLoan.getBankName());
    }

    public void addLoanInfo() {
         FacesContext context = FacesContext.getCurrentInstance();
        int s = 0;
        if(flatid!=null&&(!flatid.equals(""))){
        for (int i = 0; i < fbsLoanList.size(); i++) {
            if (flatid.equals(fbsLoanList.get(i).getUnitCode())) {
                s = 1;
                fbsLoanFacade.edit(fbsLoan);
                fbsLoan=new FbsLoan();

            }
        }
        if (s == 1) {
        } else {
            fbsLoan.setUnitCode(flatid);
            fbsLoanFacade.create(fbsLoan);
            fbsLoan=new FbsLoan();
        }
        populate();
        }
 else{
            context.addMessage(null, new FacesMessage("Invalid Entry! Unit Code is Not Selected "));
 }
    }
public void editLoan(org.primefaces.event.RowEditEvent e)
    {
        fbsLoanFacade.edit((FbsLoan) e.getObject());
        System.out.println("Row edit Function Called");
      //  fbsLoanList = em.createNamedQuery("FbsLoan.findAll").getResultList();
        //populate();
        fbsLoan=new FbsLoan();
    }
public void deleteLoan(FbsLoan fbsLoan)
    {
    fbsLoanFacade.remove(fbsLoan);
    populate();
}
    public ArrayList getBlockList() {
        return blockList;
    }

    public void setBlockList(ArrayList blockList) {
        this.blockList = blockList;
    }

    public FbsBlock getFbsBlock() {
        return fbsBlock;
    }

    public void setFbsBlock(FbsBlock fbsBlock) {
        this.fbsBlock = fbsBlock;
    }

    public List<FbsBlock> getFbsBlockList() {
        return fbsBlockList;
    }

    public void setFbsBlockList(List<FbsBlock> fbsBlockList) {
        this.fbsBlockList = fbsBlockList;
    }

    public List<FbsBooking> getFbsBookingList() {
        return fbsBookingList;
    }

    public void setFbsBookingList(List<FbsBooking> fbsBookingList) {
        this.fbsBookingList = fbsBookingList;
    }

    public List<FbsFlat> getFbsFlatList() {
        return fbsFlatList;
    }

    public void setFbsFlatList(List<FbsFlat> fbsFlatList) {
        this.fbsFlatList = fbsFlatList;
    }

    public FbsLoan getFbsLoan() {
        return fbsLoan;
    }

    public void setFbsLoan(FbsLoan fbsLoan) {
        this.fbsLoan = fbsLoan;
    }

    public List<FbsLoan> getFbsLoanList() {
        return fbsLoanList;
    }

    public void setFbsLoanList(List<FbsLoan> fbsLoanList) {
        this.fbsLoanList = fbsLoanList;
    }

    public FbsProject getFbsProject() {
        return fbsProject;
    }

    public void setFbsProject(FbsProject fbsProject) {
        this.fbsProject = fbsProject;
    }

    public ArrayList getFlatNoList() {
        return flatNoList;
    }

    public void setFlatNoList(ArrayList flatNoList) {
        this.flatNoList = flatNoList;
    }

    public Integer getFlatid() {
        return flatid;
    }

    public void setFlatid(Integer flatid) {
        this.flatid = flatid;
    }

    public String[] getFloorList() {
        return floorList;
    }

    public void setFloorList(String[] floorList) {
        this.floorList = floorList;

    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public ArrayList getProjectList() {
        return projectList;
    }

    public void setProjectList(ArrayList projectList) {
        this.projectList = projectList;
    }
}
