/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.managedbean;

import com.smp.entity.FbsPayplan;
import com.smp.entity.FbsPlanname;
import com.smp.fbs.PayplanInfo;
import com.smp.session.FbsPayplanFacade;
import com.smp.session.FbsPlannameFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import javax.faces.model.SelectItem;

/**
 *
 * @author smp
 */
@ManagedBean(name = "payplanBean")
@ApplicationScoped
@Stateless
public class PayplanBean implements Serializable{

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
    @EJB
    FbsPayplanFacade fbsPayplanFacade;
    @EJB
    FbsPlannameFacade fbsPlannameFacade;
    public FbsPayplan fbsPayplan = new FbsPayplan();
    public List<FbsPayplan> payplanList = new ArrayList();
    public List<FbsPlanname> plannameList = new ArrayList();
    public List<PayplanInfo> payplanInfoList;
    private ArrayList planName;
    private String projid = "";
    private String planid="";
    public ArrayList planList;
    PayplanInfo payplanInfo=new PayplanInfo();
    static PayplanInfo payplandelInfo=new PayplanInfo();
    FbsPlanname fbsPlanname=new FbsPlanname();
    @PostConstruct
    public void populate() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);
        projid = (String) session.getAttribute("projId");
        payplanList.clear();
        plannameList.clear();
        if (projid != null) {
            payplanList = em.createNamedQuery("FbsPayplan.findByFkProjId").setParameter("fkProjId", Integer.parseInt(projid)).getResultList();

            plannameList = em.createNamedQuery("FbsPlanname.findByFkProjId").setParameter("fkProjId", Integer.parseInt(projid)).getResultList();
             planList = new ArrayList();
        for (int i = 0; i < plannameList.size(); i++) {
           planList.add(new SelectItem(plannameList.get(i).getPlanId(),plannameList.get(i).getPlanName()));
        }
          payplanInfoList=new ArrayList<PayplanInfo>();
          for(int i=0;i<payplanList.size();i++)
          {
              payplanInfo=new PayplanInfo();
              payplanInfo.setDueDate(payplanList.get(i).getDueDate());
              payplanInfo.setFkProjId(Integer.parseInt(projid));
              payplanInfo.setPayplanId(payplanList.get(i).getPayplanId());
              payplanInfo.setPercentage(payplanList.get(i).getPercentage());
              payplanInfo.setPlanDesc(payplanList.get(i).getPlanDesc());
              payplanInfo.setPlanId(payplanList.get(i).getFkPlanId());
             
              fbsPlanname=fbsPlannameFacade.find(payplanInfo.getPlanId());
              payplanInfo.setPlanName(fbsPlanname.getPlanName());
              payplanInfo.setSerialNo(payplanList.get(i).getSerialNo());
              payplanInfoList.add(payplanInfo);
          }
        }
    }

    public void addPayplan() {
        fbsPayplan.setFkProjId(Integer.parseInt(projid));
       
        fbsPayplanFacade.create(fbsPayplan);
        fbsPayplan=new FbsPayplan();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Congrates! Payplan Successfully Added"));
        //payplanList = em.createNamedQuery("FbsPayplan.findByFkProjId").setParameter("fkProjId", Integer.parseInt(projid)).getResultList();
        populate();
    }

    public void editPayplan(org.primefaces.event.RowEditEvent e) {

//        fbsPayplanFacade.edit((FbsPayplan) e.getObject());
        payplanInfo=(PayplanInfo) e.getObject();
        fbsPayplan=new FbsPayplan();
        fbsPayplan.setPayplanId(payplanInfo.getPayplanId());
        fbsPayplan.setFkProjId(payplanInfo.getFkProjId());
        fbsPayplan.setDueDate(payplanInfo.getDueDate());
        fbsPayplan.setPercentage(payplanInfo.getPercentage());
        fbsPayplan.setPlanDesc(payplanInfo.getPlanDesc());
        fbsPayplan.setSerialNo(payplanInfo.getSerialNo());
        fbsPayplan.setFkPlanId(payplanInfo.getFkPlanId());
        fbsPayplanFacade.edit(fbsPayplan);
        populate();
    }

    public void deletePayplan() throws IOException {
        fbsPayplan=fbsPayplanFacade.find(payplandelInfo.getPayplanId());
        fbsPayplanFacade.remove(fbsPayplan);
        populate();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/ProjectSetting/setPayplan.xhtml");
    }
public void delPayplan(PayplanInfo payplanInfo) {
           payplandelInfo=payplanInfo;
    }
    public void setFbsPayplan(FbsPayplan fbsPayplan) {
        this.fbsPayplan = fbsPayplan;
    }

    public FbsPayplan getFbsPayplan() {
        return fbsPayplan;
    }

    public void setPayplanList(List<FbsPayplan> payplanList) {
        this.payplanList = payplanList;
    }

    public List<FbsPayplan> getPayplanList() {
        return payplanList;
    }

    public void setPlanid(String planid) {
        this.planid = planid;
    }

    public String getPlanid() {
        return planid;
    }

    public void setProjid(String projid) {
        this.projid = projid;
    }

    public String getProjid() {
        return projid;
    }

    public void setPlanList(ArrayList planList) {
        this.planList = planList;
    }

    public ArrayList getPlanList() {
        return planList;
    }

    public void setPlanName(ArrayList planName) {
        this.planName = planName;
    }

    public ArrayList getPlanName() {
        return planName;
    }

    public void setPayplanInfoList(List<PayplanInfo> payplanInfoList) {
        this.payplanInfoList = payplanInfoList;
    }

    public List<PayplanInfo> getPayplanInfoList() {
        return payplanInfoList;
    }
   
    
}
