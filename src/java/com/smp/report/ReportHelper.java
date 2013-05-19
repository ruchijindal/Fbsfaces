/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.report;

import com.smp.entity.FbsBank;
import com.smp.entity.FbsPayment;
import com.smp.entity.FbsPayplan;
import com.smp.entity.FbsServicetax;
import com.smp.session.FbsPayplanFacade;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ReportHelper {

    @EJB
    FbsPayplanFacade fbsPayplanFacade;
    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager entityManager;
    ArrayList<java.util.Date> listforbillperiod = new ArrayList<java.util.Date>();
    FbsPayplan fbsPayplan = new FbsPayplan();
     
    public  List<java.util.Date> findBillperiod(int projectid, int planid, int noOfBillperiod) {
        try {

           // @NamedQuery(name = "FbsPayplan.findByPlanIdAndProjectId", query = "SELECT f FROM FbsPayplan f WHERE f.fkProjId = :fkProjId AND f.fkPlanId = :fkPlanId  ORDER BY f.sNo"),
    // @NamedQuery(name = "FbsPayplan.findByPlanIdAndProjectIdAndDueDate", query = "SELECT f FROM FbsPayplan f WHERE f.fkProjId = :fkProjId AND f.fkPlanId = :fkPlanId And f.dueDate is not null  ORDER BY f.sNo"),
            Query query1 = entityManager.createNamedQuery("FbsPayplan.findByPlanIdAndProjectIdAndDueDate");
            query1.setParameter("fkProjId", projectid);
            query1.setParameter("fkPlanId", planid);
            List<FbsPayplan> fbsPayplan1 = query1.getResultList();
            int size2 = fbsPayplan1.size();
            if (noOfBillperiod < size2 - 1) {
                if (noOfBillperiod == 0) {
                    listforbillperiod.clear();
                    listforbillperiod.add(fbsPayplan1.get(0).getDueDate());
                } else {
                    listforbillperiod.clear();
                    for (int i = noOfBillperiod; i < noOfBillperiod + 2; i++) {
                        listforbillperiod.add(fbsPayplan1.get(i - 1).getDueDate());
                    }
                }
            } else if (noOfBillperiod == size2 - 1) {
                listforbillperiod.clear();
                listforbillperiod.add(fbsPayplan1.get(size2 - 1).getDueDate());
            } else {
                listforbillperiod.clear();
            }
        } catch (Exception e) {
            System.out.print("Exception" + e);
        }
        return listforbillperiod;
    }
public long findPayment(String unitCode,String projectID){
    //public long findPayment(Date startDate, Date endDate, Date FirstDate, String unitCode,String projectID) {
       long paidamount=0;
       long cashamount=0;
       long serviceTaxcash=0;
       long totalCashAmount=0;
       //FbsServicetax fbsServicetax=new FbsServicetax();
       Query serviceTax=entityManager.createNamedQuery("FbsServicetax.findAllOrderbyDueDate");
       List<FbsServicetax> fbsServiceTax=serviceTax.getResultList();
       List<FbsPayment> fbsPayments=new ArrayList<FbsPayment>();
       fbsPayments=entityManager.createNamedQuery("FbsPayment.findByUnitCodeAndStatus").setParameter("unitCode",unitCode).getResultList();
       if(fbsPayments.size()>0)
       {
           for(int i=0;i<fbsPayments.size();i++)
           {
               Date paymentDate=fbsPayments.get(i).getPaymentDate();
               cashamount=fbsPayments.get(i).getPaidAmount();
          
                       for(int j=0;j<fbsServiceTax.size();j++)
                    {
                       if(fbsServiceTax.get(j).getStDate().before(paymentDate)&&fbsServiceTax.get(j).getEndDate().after(paymentDate))//if(fbsServiceTax.get(j).getStDate().compareTo(paymentDate)>=0)
                        {
                        serviceTaxcash=fbsServiceTax.get(j).getServicetax();
                        break;
                        }
                           
           }
               // System.out.println("cash AMount"+cashamount+" service tax"+serviceTaxcash);
                 double t=(1+(double)serviceTaxcash/100);
                  double recieved=(double)cashamount/t;
                       totalCashAmount= Math.round(totalCashAmount + recieved);
                       System.out.println("total cash"+totalCashAmount);
       }
        }
 else
     totalCashAmount=0;
       return totalCashAmount;
      /*  long paidAmountForBillPeriod = 0;
        long cashAmount = 0;
        long chequeAmount = 0;
        long serviceTaxcheque = 0;
        long serviceTaxcash = 0;
        long totalCashAmount=0;
        long totalChequeAmount=0;
        Date paymentDate = null;
        //these are the Named queries used by this method
        //  @NamedQuery(name="FbsPayment.FindByDueDateAndUnitCode",query="Select f from FbsPayment f Where  f.unitCode = :unitCode and f.paymentDate between :startDate AND :endDate and f.paymentMode ='cash'"),
        //  @NamedQuery(name="FbsPayment.FindBystartdateAndUnitCode",query="Select f from FbsPayment f Where  f.unitCode = :unitCode and f.paymentDate > :startDate and f.paymentMode ='cash' "),
        //  @NamedQuery(name="FbsPayment.FindBystartdate1AndUnitCode",query="Select f from FbsPayment f Where  f.unitCode = :unitCode and f.paymentDate =:startDate and f.paymentMode ='cash' "),
        //  @NamedQuery(name="FbsPayment.FindByDueDateAndUnitCodeandCheque",query="Select f from FbsPayment f Where  f.unitCode = :unitCode and f.paymentDate between :startDate AND :endDate and f.paymentMode ='cheque' and f.chequeStatus='c'"),
        //  @NamedQuery(name="FbsPayment.FindBystartdateAndUnitCodeandCheque",query="Select f from FbsPayment f Where  f.unitCode = :unitCode and f.paymentDate > :startDate and f.paymentMode ='cheque' and f.chequeStatus='c'"),
        //  @NamedQuery(name="FbsPayment.FindBystartdate1AndUnitCodeandCheque",query="Select f from FbsPayment f Where  f.unitCode = :unitCode and f.paymentDate =:startDate and f.paymentMode ='cheque' and f.chequeStatus='c'"),
        try {
//  @NamedQuery(name = "FbsServicetax.findAllOrderbyDueDate", query = "SELECT f FROM FbsServicetax f ORDER BY f.stDate"),
            Query serviceTax=entityManager.createNamedQuery("FbsServicetax.findAllOrderbyDueDate");
            List<FbsServicetax> fbsServiceTax=serviceTax.getResultList();

            if (FirstDate != null) {
                Query query2 = entityManager.createNamedQuery("FbsPayment.FindBystartdateAndUnitCode");
               System.out.println("unit code ->"+unitCode+"first date->"+FirstDate.toString());
                query2.setParameter("unitCode", unitCode);
                query2.setParameter("startDate",FirstDate);
                List<FbsPayment> fbsPayment = query2.getResultList();
                int size2 = fbsPayment.size();
                System.out.println("size2 ->"+size2);
               for (int i = 0; i < size2; i++) {
                   System.out.println("Payment Id"+fbsPayment.get(i).getPaymentId()+"  "+fbsPayment.get(i).getUnitCode());
                    paymentDate=fbsPayment.get(i).getPaymentDate();
                    if(paymentDate==null)
                    {
                        System.out.println("prroblem");
                        
                    }
                    System.out.println("Payment Date"+paymentDate.toString());
                    cashAmount = fbsPayment.get(i).getPaidAmount();//get paid amount
                    for(int j=0;j<fbsServiceTax.size();j++)
                    {
                       if(fbsServiceTax.get(j).getStDate().before(paymentDate)&&fbsServiceTax.get(j).getEndDate().after(paymentDate))//if(fbsServiceTax.get(j).getStDate().compareTo(paymentDate)>=0)
                        {
                        serviceTaxcash=fbsServiceTax.get(j).getServicetax();
                        break;
                        }
                    }
                   System.out.println(cashAmount+" Service Tax Cash"+(cashAmount*serviceTaxcash/100));
                    totalCashAmount= totalCashAmount+(cashAmount-cashAmount*serviceTaxcash/100);
                    System.out.println("total cash amount "+totalCashAmount);
                }
                Query query3 = entityManager.createNamedQuery("FbsPayment.FindBystartdate1AndUnitCodeandCheque");
                query3.setParameter("unitCode", unitCode);
                query3.setParameter("startDate", FirstDate);
                List<FbsPayment> fbsPaymentcheque = query3.getResultList();
                int sizecheque = fbsPaymentcheque.size();
                for (int i = 0; i < sizecheque; i++) {
                    chequeAmount =fbsPaymentcheque.get(i).getPaidAmount();//get paid amount
                    for(int j=0;j<fbsServiceTax.size();j++)
                    {
                         
                      if(fbsServiceTax.get(j).getStDate().before(paymentDate)&&fbsServiceTax.get(j).getEndDate().after(paymentDate))  //if(fbsServiceTax.get(j).getStDate().compareTo(paymentDate)>=0)
                        {
                        serviceTaxcheque=fbsServiceTax.get(j).getServicetax();
                        break;
                        }
                    }
                    System.out.println(chequeAmount+" ServiceTax cheque "+(chequeAmount*serviceTaxcheque/100));
                    totalChequeAmount=totalChequeAmount+( chequeAmount- chequeAmount*serviceTaxcheque/100);
                System.out.println("total cheque amount "+totalChequeAmount);
                }
                paidAmountForBillPeriod =totalCashAmount + totalChequeAmount;
            } else if (endDate != null&&(endDate.before(new Date())))
            {
                Query query2 = entityManager.createNamedQuery("FbsPayment.FindByDueDateAndUnitCode");
                query2.setParameter("unitCode", unitCode);
                System.out.println("endDate-->"+endDate);
                query2.setParameter("startDate", startDate);
                query2.setParameter("endDate", endDate);
                List<FbsPayment> fbsPayment = query2.getResultList();
                int size2 = fbsPayment.size();
                System.out.println("in else if size2 ->"+size2);

               for (int p = 0; p < size2; p++) {
                   System.out.println("Payment Id"+fbsPayment.get(p).getPaymentId()+"  "+fbsPayment.get(p).getUnitCode());
                    paymentDate=fbsPayment.get(p).getPaymentDate();
                    if(paymentDate==null)
                    {
                        System.out.println("prroblem");

                    }
                }
                for (int i = 0; i < size2; i++) {
                    // paymentDate=fbsPayment.get(i).getPaymentDate();
                     cashAmount = fbsPayment.get(i).getPaidAmount();//get paid amount
                      for(int j=0;j<fbsServiceTax.size();j++)
                    {
                          System.out.println("endt date"+fbsServiceTax.get(j).getStDate().toString());
                          System.out.println("Start date"+paymentDate.toString());
                       if(fbsServiceTax.get(j).getStDate().before(paymentDate)&&fbsServiceTax.get(j).getEndDate().after(paymentDate)) //if(fbsServiceTax.get(j).getStDate().compareTo(paymentDate)>=0)
                        {
                        serviceTaxcash=fbsServiceTax.get(j).getServicetax();
                        break;
                        }
                    }
System.out.println("Service Tax Cash"+(cashAmount*serviceTaxcash/100));
                    totalCashAmount= totalCashAmount+(cashAmount-cashAmount*serviceTaxcash/100);//get paid amount
                System.out.println("total cash amount "+totalCashAmount);
                }
                Query query3 = entityManager.createNamedQuery("FbsPayment.FindByDueDateAndUnitCodeandCheque");
                query3.setParameter("unitCode", unitCode);
                query3.setParameter("startDate", startDate);
                query3.setParameter("endDate", endDate);
                List<FbsPayment> fbsPaymentcheque = query3.getResultList();
                int sizecheque = fbsPaymentcheque.size();
                for (int i = 0; i < sizecheque; i++) {
                    chequeAmount =fbsPaymentcheque.get(i).getPaidAmount();//get paid amount
                     for(int j=0;j<fbsServiceTax.size();j++)
                    {
                       if(fbsServiceTax.get(j).getStDate().before(paymentDate)&&fbsServiceTax.get(j).getEndDate().after(paymentDate))// if(fbsServiceTax.get(j).getStDate().compareTo(paymentDate)>=0)
                        {
                        serviceTaxcheque=fbsServiceTax.get(j).getServicetax();
                        break;
                        }
                    }
                    System.out.println(chequeAmount+" ServiceTax cheque "+(chequeAmount*serviceTaxcheque/100));
                    totalChequeAmount= totalChequeAmount+(chequeAmount- chequeAmount*serviceTaxcheque/100);//get paid amount
                System.out.println("total cheque amount "+totalChequeAmount);
                }
                 paidAmountForBillPeriod =totalCashAmount + totalChequeAmount;
            } else if (startDate != null && endDate == null) {
                Query query2 = entityManager.createNamedQuery("FbsPayment.FindBystartdateAndUnitCode");
                query2.setParameter("unitCode", unitCode);
                query2.setParameter("startDate", startDate);
                List<FbsPayment> fbsPayment = query2.getResultList();
                int size2 = fbsPayment.size();
                 System.out.println("in else ifhhhhhhh size2 ->"+size2);
                /*for (int p = 0; p < size2; p++) {
                    paymentDate=fbsPayment.get(p).getPaymentDate();
                    if(paymentDate==null)
                    {
                        System.out.println("prroblem");

                    }
                }
                for (int i = 0; i < size2; i++) {
                    System.out.println("Payment Id"+fbsPayment.get(i).getPaymentId()+"  "+fbsPayment.get(i).getUnitCode());
                     //paymentDate=fbsPayment.get(i).getPaymentDate();
                    cashAmount = fbsPayment.get(i).getPaidAmount();//get paid amount
                     for(int j=0;j<fbsServiceTax.size();j++)
                    {
                        if(fbsServiceTax.get(j).getStDate().before(paymentDate)&&fbsServiceTax.get(j).getEndDate().after(paymentDate))//if(fbsServiceTax.get(j).getStDate().compareTo(paymentDate)>=0)
                        {
                        serviceTaxcash=fbsServiceTax.get(j).getServicetax();
                        break;
                        }
                    }
                    System.out.println(cashAmount+" Service Tax Cash"+(cashAmount*serviceTaxcash/100));
                    totalCashAmount= totalCashAmount+(cashAmount-cashAmount*serviceTaxcash/100);//get paid amount
                System.out.println("total cash amount "+totalCashAmount);
                }
                Query query3 = entityManager.createNamedQuery("FbsPayment.FindBystartdateAndUnitCodeandCheque");
                query3.setParameter("unitCode", unitCode);
                query3.setParameter("startDate", startDate);
                List<FbsPayment> fbsPaymentCheque = query3.getResultList();
                int sizeCheque = fbsPaymentCheque.size();
                for (int i = 0; i < sizeCheque; i++) {
                     chequeAmount =fbsPaymentCheque.get(i).getPaidAmount();//get paid amount
                     for(int j=0;j<fbsServiceTax.size();j++)
                    {
                       if(fbsServiceTax.get(j).getStDate().before(paymentDate)&&fbsServiceTax.get(j).getEndDate().after(paymentDate))// if(fbsServiceTax.get(j).getStDate().compareTo(paymentDate)>=0)
                        {
                        serviceTaxcheque=fbsServiceTax.get(j).getServicetax();
                        break;
                        }
                    }
                     System.out.println(chequeAmount+" ServiceTax cheque "+(chequeAmount*serviceTaxcheque/100));
                   totalChequeAmount=totalChequeAmount+(chequeAmount- chequeAmount*serviceTaxcheque/100);//get paid amount
                System.out.println("total cheque amount "+totalChequeAmount);
                }
                paidAmountForBillPeriod =totalCashAmount + totalChequeAmount;
            }
        } catch (Exception e) {
            System.out.println(e);
           
        }
        return paidAmountForBillPeriod;*/
    }

    public List<FbsPayment> findPaymentInformation(String Unitcode) {

        List<FbsPayment> paymentInformation = entityManager.createNamedQuery("FbsPayment.findByUnitCode").setParameter("unitCode", Unitcode).getResultList();

        return paymentInformation;
    }

    public List<FbsBank> findBankName(int Bankcode) {

        List<FbsBank> BankName = entityManager.createNamedQuery("FbsPayment.findByClearingBankId").setParameter("clearingBankId",Bankcode).getResultList();

        return BankName;
    }

}
