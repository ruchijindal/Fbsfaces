/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.report;

import java.util.Date;

/**
 *
 * @author smp
 */
public class OutstandingAmount {

    long outstandingAmount = 0;
    long paidAmount = 0;
    long amountRecevied = 0;
    long creditAmount = 0;
    static int counter = 0;
    static long storecredit=0;
    static long storeoutstand=0;


    public synchronized long installmentAmountcaluculation(long installmentAmount)
    {

        this.outstandingAmount+=installmentAmount;
        return this.outstandingAmount;
    }
    public synchronized   OutstandingAmount  findOutStandingAndCreditAmount(long installmentAmount, long paidamount, long outstandingAmount, long creditAmount,Date startingbillDate,Date endDate) {
counter++;
           Date currentdate=new Date();
      //  System.out.println(counter+" in start "+toString());
       // System.out.println("in function installment Amount"+installmentAmount+"paidamount"+paidamount+"outstandingAmount"+outstandingAmount+"creditAmount"+creditAmount);
       // System.out.println("outstanding AMount"+outstandingAmount+" paid Amount"+paidAmount+" credit Amount"+creditAmount+" store credit"+storecredit+" store Outstand"+storeoutstand);
                if(startingbillDate!=null)
                 {
          //   System.out.println("starting billing date in outstanding is-> "+startingbillDate+"current dtae -. "+currentdate);
          //   System.out.println("1 "+toString());
             if((startingbillDate.getMonth()>=(currentdate.getMonth()))&&startingbillDate.getYear()==currentdate.getYear())
                 {
               //  System.out.println("2 "+toString());
               //  System.out.println("install ment amount "+installmentAmount+" store"+storeoutstand);
                      outstandingAmount=storeoutstand+installmentAmount;
                      installmentAmount=0;
                  //    System.out.println("outStanding Amount"+outstandingAmount+" store outstandig"+storeoutstand+" install "+installmentAmount);
                       creditAmount=0;
                       amountRecevied=0;
                    //   System.out.println("3 "+toString());

                 }

             else {
                          //  System.out.println("4 "+toString());
                        creditAmount=storecredit;
                        storecredit=0;
                      //  System.out.println("5 "+toString());

                    }
                          //   System.out.println("jl "+toString());
                 }
                     //   System.out.println("jk "+toString());           
       /* else if(endDate != null)
                 {
                      if(endDate.before(currentdate))
                 {
                      creditAmount=storecredit;
                 }
                    else{
                           outstandingAmount=storeoutstand+amountRecevied;
                       creditAmount=0;

                    }
                 }*/
              
                if( outstandingAmount > 0)
        {
                  //  System.out.println("6 "+toString());
           installmentAmount= installmentAmount+outstandingAmount;
           //System.out.println("6 "+toString());
        }
                                     //   System.out.println("jm "+toString());
        if(creditAmount > 0)
        {
           // System.out.println("7 "+toString());
            paidamount=creditAmount;
            creditAmount=0;
            storecredit=0;
           // System.out.println("8 "+toString());
            //installmentAmount=installmentAmount-creditAmount;
        }
              //  System.out.println("jn "+toString());

        if ((installmentAmount < paidamount)) {
         //   System.out.println("paid amount "+paidamount);
          //  System.out.println("9 "+toString());
            storeoutstand=outstandingAmount = 0;
            storecredit=creditAmount = paidamount - installmentAmount;
            amountRecevied=installmentAmount;
           // System.out.println("10 "+toString());
        } else if ((installmentAmount > paidamount)) {
          //  System.out.println("11 "+toString());
           // System.out.println("install"+installmentAmount+"paid "+paidamount);
            storeoutstand=outstandingAmount = installmentAmount - paidamount;
            //System.out.println("outStanding Amount "+outstandingAmount);
            amountRecevied=paidamount;
            storecredit=creditAmount = 0;
           // System.out.println("12 "+toString());
            //paidamount;

        } else if (installmentAmount == paidamount) {
           // System.out.println("13 "+toString());
            storeoutstand=outstandingAmount = 0;
            storecredit=creditAmount=0;
            amountRecevied=installmentAmount;
           // System.out.println("14 "+toString());
        }
        
         //  System.out.println("15 "+toString());
        this.outstandingAmount = outstandingAmount;   
        this.creditAmount =creditAmount;
        this.amountRecevied=amountRecevied;
       // System.out.println("16 "+toString());
      //  System.out.println("counter value"+counter);
        return this;
    }
    
    public String toString()
    {
        return " with obect crdit Amount"+creditAmount+" store credit"+storecredit+" paid Amount"+paidAmount;
    }
}
