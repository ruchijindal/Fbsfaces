/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smp.report;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.ZapfDingbatsList;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.smp.entity.FbsApplicant;
import com.smp.entity.FbsBooking;
import com.smp.entity.FbsBookingTemp;
import com.smp.entity.FbsCharge;
import com.smp.entity.FbsCompany;
import com.smp.entity.FbsDiscount;
import com.smp.entity.FbsFlatType;
import com.smp.entity.FbsInterest;
import com.smp.entity.FbsParkingAllot;
import com.smp.entity.FbsParkingType;
import com.smp.entity.FbsPayment;
import com.smp.entity.FbsPayplan;
import com.smp.entity.FbsPlanname;
import com.smp.entity.FbsPlc;
import com.smp.entity.FbsProject;
import com.smp.entity.FbsReport;
import com.smp.entity.FbsServicetax;
import com.smp.session.FbsApplicantFacade;
import com.smp.session.FbsBookingFacade;
import com.smp.session.FbsBookingTempFacade;
import com.smp.session.FbsChargeFacade;
import com.smp.session.FbsCompanyFacade;
import com.smp.session.FbsDiscountFacade;
import com.smp.session.FbsFlatTypeFacade;
import com.smp.session.FbsInterestFacade;
import com.smp.session.FbsParkingTypeFacade;
import com.smp.session.FbsPaymentFacade;
import com.smp.session.FbsPayplanFacade;
import com.smp.session.FbsPlannameFacade;
import com.smp.session.FbsPlcFacade;
import com.smp.session.FbsProjectFacade;
import com.smp.session.FbsReportFacade;
import com.smp.session.FbsServicetaxFacade;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author smp
 */
public class DueLetter extends HttpServlet {

@PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
@EJB
ChargesAndPlanDetails chargesAndPlanDetails;
    @EJB
    FbsProjectFacade fbsprojectfacade;
    FbsProject fbsproject=new FbsProject();
    @EJB
    FbsApplicantFacade fbsApplicantFacade;
   // FbsApplicant fbsApplicant=new FbsApplicant();
    @EJB
    FbsBookingFacade fbsBookingfacade;
    FbsBooking fbsBooking=new FbsBooking();
    @EJB
    FbsPlcFacade fbsPlcFacade;
    @EJB
    ReportHelper reportHelper;
    FbsPlc fbsPlc=new FbsPlc();
    @EJB
    FbsReportFacade fbsReportFacade;
    @EJB
    FbsPlannameFacade fbsPlannameFacade;
    FbsPlanname fbsPlanname=new FbsPlanname();
    @EJB
    FbsDiscountFacade fbsDiscountFacade;
    @EJB
    FbsChargeFacade fbsChargeFacade;
    FbsCharge fbsCharge=new FbsCharge();
    FbsDiscount fbsDiscount=new FbsDiscount();
    List<FbsCharge> fbsChargeList=new ArrayList<FbsCharge>();
    List<FbsPlc> fbsPlcList=new ArrayList<FbsPlc>();
    @EJB
    FbsFlatTypeFacade fbsFlatTypeFacade;
    FbsFlatType fbsFlatType=new FbsFlatType();
    @EJB
   FbsParkingTypeFacade fbsParkingTypeFacade;
    @EJB
    FbsPaymentFacade fbsPaymentFacade;
    FbsPayment fbsPayment=new FbsPayment();
    @EJB
    FbsPayplanFacade fbsPayplanFacade;
    FbsPayplan fbsPayplan=new FbsPayplan();
    @EJB
    FbsServicetaxFacade fbsServicetaxFacade;
    FbsServicetax fbsServicetax=new FbsServicetax();
    @EJB
    FbsCompanyFacade fbsCompanyFacade;
    FbsCompany fbsCompany=new FbsCompany();
    @EJB
    FbsBookingTempFacade fbsBookingTempFacade;
    @EJB
    FbsInterestFacade fbsInterestFacade;
    FbsInterest fbsInterest=new FbsInterest();
    FbsBookingTemp fbsBookingTemp=new FbsBookingTemp();
    List<FbsInterest> fbsInterests=new ArrayList<FbsInterest>();
    int r;
  //  private int num;
    private int k;
      FbsReport fbsReport=new FbsReport();
 Font catFont = new Font(Font.TIMES_ROMAN, 20, Font.BOLD);
 Font redFont = new Font(Font.TIMES_ROMAN, 14, Font.NORMAL);
 Font blackFont = new Font(Font.TIMES_ROMAN, 10, Font.NORMAL );
 Font subFont = new Font(Font.TIMES_ROMAN, 16, Font.BOLD);
 Font smallBold = new Font(Font.TIMES_ROMAN, 10, Font.BOLD);
 Font blackSmallFont = new Font(Font.TIMES_ROMAN, 6, Font.NORMAL, BaseColor.BLACK);
 Font smallBold1 = new Font(Font.TIMES_ROMAN, 14, Font.BOLD);
 Font smallBold2 = new Font(Font.TIMES_ROMAN, 12, Font.BOLD);
 Font smallBold3 = new Font(Font.TIMES_ROMAN, 11);
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    String[] unitdo = {"", " One", " Two", " Three", " Four", " Five",
        " Six", " Seven", " Eight", " Nine", " Ten", " Eleven", " Twelve",
        " Thirteen", " Fourteen", " Fifteen", " Sixteen", " Seventeen",
        " Eighteen", " Nineteen"};
    String[] tens = {"", "Ten", " Twenty", " Thirty", " Forty", " Fifty",
        " Sixty", " Seventy", " Eighty", " Ninety"};
    String[] digit = {"", " Hundred", " Thousand", " Lakh", " Crore"};
     DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      HttpSession session=request.getSession(false);
        response.setContentType("text/html;charset=UTF-8");
     //   PrintWriter out = response.getWriter();
        ServletOutputStream out=response.getOutputStream();
       Integer projid=Integer.parseInt((String) session.getAttribute("projId"));
       Integer flatNo=Integer.parseInt((String) session.getAttribute("flatNo"));
       String flatId=(String) session.getAttribute("flatId");
       String blockName=(String) session.getAttribute("blockName");
       String companyid=request.getParameter("companyId");
       System.out.println("copany id "+companyid);
      Integer companyId=Integer.parseInt(companyid.trim());
       fbsproject=fbsprojectfacade.find(projid);
       Integer flatTypeId=Integer.parseInt((String)session.getAttribute("flatTypeId"));
       String applicantId=(String) session.getAttribute("applicantId");
       System.out.println("applivcant  id is :->  "+applicantId);
       boolean withInterest=false;
       String interest=request.getParameter("interest");
       System.out.println("the value of interest in due report is "+interest);
       withInterest=Boolean.parseBoolean(interest);
       System.out.println("interest is "+interest);
       //fbsApplicant=fbsApplicantFacade.find(Integer.parseInt("124"));
 List<FbsApplicant> fbsApplicantList =  em.createNamedQuery("FbsApplicant.findByFlatId").setParameter("flatId", Integer.parseInt(flatId)).getResultList();
fbsBooking=(FbsBooking) em.createNamedQuery("FbsBooking.findByFlatId").setParameter("flatId", Integer.parseInt(flatId)).getResultList().get(0);
System.out.println("FbsBooking plc Id->"+fbsBooking.getPlcId()+"\n discount Id->"+fbsBooking.getDiscountId()+"\nregistration number"+fbsBooking.getRegNumber());
         //fbsPlc=em.find(FbsPlc.class,fbsBooking.getPlcId());
        
        ChargesAndPlanDetails chargesAndPlanDetails1=chargesAndPlanDetails.findPlannameAndDiscount(flatId);
        List<String> plcList=chargesAndPlanDetails1.plcidlist;
        fbsPlcList.clear();
         for(int pl=0;pl<plcList.size();pl++)
         {
             System.out.println("p "+plcList.get(pl));
            FbsPlc temp=fbsPlcFacade.find(Integer.parseInt(plcList.get(pl)));
             fbsPlcList.add(temp);
         }

        //------ fbsBookingTemp=(FbsBookingTemp) em.createNamedQuery("FbsBookingTemp.findByRegNumber").setParameter("regNumber",fbsBooking.getRegNumber()).getSingleResult();
        fbsDiscount=em.find(FbsDiscount.class, fbsBooking.getDiscountId());
        fbsChargeList=em.createNamedQuery("FbsCharge.findByFkProjId").setParameter("fkProjId",projid.toString()).getResultList();
        int plcCharge=0;//fbsPlc.getPlcCharge();
        for(int pl1=0;pl1<fbsPlcList.size();pl1++)
        {
            plcCharge+=fbsPlcList.get(pl1).getPlcCharge();
        }
        int discountPercentage=fbsDiscount.getPercentage();
      //  System.out.println("fbs charegs"+fbsCharges.toString()+"amount->");
        long chargeAmount=0;//=fbsCharges.getAddCovered()+fbsCharges.getClubCharge()+fbsCharges.getCoveredParking()+fbsCharges.getEec()+fbsCharges.getFfc()+fbsCharges.getLeaseRent()+fbsCharges.getMaintenance()+fbsCharges.getOpenParking()+fbsCharges.getOtherCharges()+fbsCharges.getPowerBackup();
        List<FbsParkingAllot> fbsParkingAllot=em.createNamedQuery("FbsParkingAllot.findByFkFlatId").setParameter("fkFlatId",Integer.parseInt(flatId)).getResultList();
        long parkingAmount=0;
        FbsParkingType fbsParkingType=new FbsParkingType();
        for(int j=0;j<fbsParkingAllot.size();j++)
        {
            fbsParkingType=fbsParkingTypeFacade.find(fbsParkingAllot.get(j).getParkingTypeId());
            parkingAmount+=fbsParkingType.getParkingCharge();
            System.out.println("parking->"+fbsParkingType.getParkingCharge());
        }
      //  List<FbsPayment> fbsPaymentList=em.createNamedQuery("FbsPayment.findByUnitCode").setParameter("unitCode",flatId).getResultList();
        long paidAmount=reportHelper.findPayment(flatId, String.valueOf(projid));
        //for(int i=0;i<fbsPaymentList.size();i++)
       // {
       //     fbsPayment=fbsPaymentList.get(i);
           // paidAmount+=fbsPayment.getPaidAmount();
       // }
        System.out.println("pAid AMount->"+paidAmount);
      //  int chargeAmount=fbsCharge.getAmount();
        //  System.out.println("charges->"+chargesAmount);
          fbsFlatType=(FbsFlatType) em.createNamedQuery("FbsFlatType.findByFlatTypeId").setParameter("flatTypeId",flatTypeId).getSingleResult();
          Integer bsp=fbsFlatType.getFlatBsp();
          Integer sba=fbsFlatType.getFlatSba();
         Integer basicSalePrice=bsp*sba;
         for(int i=0;i<fbsChargeList.size();i++)
            chargeAmount+=fbsChargeList.get(i).getAmount()*sba;
        System.out.println("charge amount of fbs charge->"+chargeAmount);
        fbsPlanname=fbsPlannameFacade.find(fbsBooking.getPlanId());
        Double plan_discount=((double)fbsPlanname.getDiscount())*(double)basicSalePrice/100;
         Double netBasicSalePrice =basicSalePrice-basicSalePrice*discountPercentage/100-plan_discount;
         long plcsba=sba*plcCharge;
         Double aftercharges=netBasicSalePrice+plcsba+chargeAmount+parkingAmount;
        Double withoutPlc=aftercharges-plcsba;
         System.out.println("plcCharges->"+plcCharge+"sba"+sba);
         System.out.println("netbasicsalesprice-> "+netBasicSalePrice+"\naftercharges"+aftercharges+"Plc charges"+sba*plcCharge+"othercharge"+chargeAmount);
         //long totalPayableAmount=aftercharges-paidAmount;

         List<FbsPayplan> fbsPayplanList=em.createNamedQuery("FbsPayplan.findByFkPlanId").setParameter("fkPlanId",fbsBooking.getPlanId()).getResultList();
         long currentInstallment=0;
         int i=0;
         long outstandAmount = 0;
         Double netBasicSalePrice1=0.0;
         for(;i<fbsPayplanList.size();i++)
         {

             Date today=new Date();
            
             
             Date dueDate=fbsPayplanList.get(i).getDueDate();
            // System.out.println("i is"+i+" due date is "+dueDate.toString());
             System.out.println("Date is"+today.toString()+"  i="+i);
             if(dueDate!=null)
             {
             if(today.after(dueDate))
             {
                 System.out.println("i is "+i);
                  fbsPayplan=fbsPayplanList.get(i);
                 currentInstallment=(long) ((netBasicSalePrice) * (fbsPayplanList.get(i).getPercentage()) / 100 + plcsba * fbsPayplanList.get(i).getPercentage() / 100);
                 netBasicSalePrice1=fbsPayplanList.get(i).getPercentage()*netBasicSalePrice/100;
                 if(i>0)
                 {

                 outstandAmount+=fbsPayplanList.get(i-1).getPercentage()*(netBasicSalePrice)/100+plcsba*fbsPayplanList.get(i-1).getPercentage()/100;
                  if(i==fbsPayplanList.size()-1)
                  {
                      outstandAmount+=withoutPlc;
                      break;
                  }
             }
             }else
                 break;
                 
             }else
             {
                 if(i>0)
                 {

                 outstandAmount+=fbsPayplanList.get(i-1).getPercentage()*(netBasicSalePrice)/100+plcsba*fbsPayplanList.get(i-1).getPercentage()/100;
                 }
                  fbsPayplan=fbsPayplanList.get(i);
                 currentInstallment=(long) ((netBasicSalePrice) * (fbsPayplanList.get(i).getPercentage()) / 100 + plcsba * fbsPayplanList.get(i).getPercentage() / 100);
                 break;
             }
             
        }

         System.out.println("Outstanding Amount"+outstandAmount+"i="+i);
         outstandAmount-=paidAmount;
       //  fbsPayplan=fbsPayplanList.get(i-1);
         if(i==fbsPayplanList.size())
         {
          //   currentInstallment=(long) ((netBasicSalePrice) * (fbsPayplan.getPercentage()) / 100 + plcsba * fbsPayplanList.get(i-1).getPercentage() / 100);
             //currentInstallment=0;
         }
         else
       //  currentInstallment=(long) ((netBasicSalePrice) * (fbsPayplan.getPercentage()) / 100 + plcsba * fbsPayplanList.get(i-1).getPercentage() / 100);
      System.out.println("\nouststaing"+outstandAmount+"currentInstallment"+currentInstallment+"percentage"+fbsPayplan.getPercentage());
     // System.out.println("PLC "+plcsba*fbsPayplanList.get(i).getPercentage()/100+"install"+(netBasicSalePrice)*(fbsPayplan.getPercentage())/100);
         long amountPayable=0;
         long interestamount=0;
         amountPayable=outstandAmount+currentInstallment;
         Double Remaining=aftercharges-amountPayable-paidAmount;
         if(i==fbsPayplanList.size())
         {
             amountPayable+=Remaining;
             Remaining=0.0;
         }
         if(withInterest==true)
         {

          fbsInterests=em.createNamedQuery("FbsInterest.findByCompanyId").setParameter("companyId", companyId).getResultList();
          for(int l=0;l<fbsInterests.size();l++)
          {
              Date d=new Date();
              if(fbsInterests.get(l).getStDate().before(d)&&fbsInterests.get(l).getEndDate().after(d))
              {
                  fbsInterest=fbsInterests.get(l);
                  break;
              }
          }
          interestamount=Math.round(amountPayable*fbsInterest.getRate()/(100*12));
          //amountPayable=Math.round(amountPayable+interestamount);

         }

                 List<FbsServicetax> servicetaxList=fbsServicetaxFacade.findAll();
                 for(int q=0;q<servicetaxList.size();q++)
                 {
                     Date date=new Date();
                     Date std=servicetaxList.get(q).getStDate();
                     if(date.after(std))
                     {
                         fbsServicetax=servicetaxList.get(q);

                     }
 else
     break;
                 }
long serviceTax=fbsServicetax.getServicetax();
  double totalAmountPayable=0;
  if(amountPayable>0)
        totalAmountPayable+=amountPayable+amountPayable*serviceTax/100;
     
      System.out.println("Service tax is ->"+fbsServicetax);
  fbsCompany=fbsCompanyFacade.find(companyId);
//segment code for number conversion
  int len, q = 0, r = 0;
        String ltr = " ";
        String Str = "Rupees";

        int num = Integer.parseInt(String.valueOf(aftercharges.intValue()).toString());

        while (num > 0) {

            len = numberCount(num);

            //Take the length of the number and do letter conversion

            switch (len) {
                case 8:
                    q = num / 10000000;
                    r = num % 10000000;
                    ltr = twonum(q);
                    Str = Str + ltr + digit[4];
                    num = r;
                    break;

                case 7:
                case 6:
                    q = num / 100000;
                    r = num % 100000;
                    ltr = twonum(q);
                    Str = Str + ltr + digit[3];
                    num = r;
                    break;

                case 5:
                case 4:

                    q = num / 1000;
                    r = num % 1000;
                    ltr = twonum(q);
                    Str = Str + ltr + digit[2];
                    num = r;
                    break;

                case 3:


                    if (len == 3) {
                        r = num;
                    }
                    ltr = threenum(r);
                    Str = Str + ltr;
                    num = 0;
                    break;

                case 2:

                    ltr = twonum(num);
                    Str = Str + ltr;
                    num = 0;
                    break;

                case 1:
                    Str = Str + unitdo[num];
                    num = 0;
                    break;
                default:

                    num = 0;
                   


            }
            if (num == 0) {
                System.out.println(Str + " Only");
            }
        }
 //nd of code segment






        try {
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DueLetter</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DueLetter at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
            */

           //  Document document=new Document();
             Document document = new Document(PageSize.A4, 0, 0, 60, 30);
        Font normafont=new Font(Font.TIMES_ROMAN, 10,Font.NORMAL);
        try{
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
           PdfWriter writer=PdfWriter.getInstance(document,baos);
           //Date date=new Date();


        document.open();
        document.addTitle("Demand Letter against"+flatId.toString());
            document.addSubject("Using iText");
            document.addKeywords("Java, PDF, iText");
        PdfPTable outerTable= new PdfPTable(1);
        Paragraph companyDetail=new Paragraph(fbsCompany.getCompanyName(),smallBold1);
           companyDetail.add(new Paragraph("\n"+fbsCompany.getAddress(),smallBold));
          companyDetail.add(new Chunk("Phone: ",smallBold));
        companyDetail.add(new Chunk(fbsCompany.getTelNumber(),smallBold));
        companyDetail.add(new Chunk(" \nEmail: ",smallBold));
        companyDetail.add(new Chunk(fbsCompany.getEmail(),smallBold));
       companyDetail.add(new Chunk(" \nWebsite: ",smallBold));
        Chunk c1=new Chunk(fbsCompany.getWebsite(),smallBold);
        c1.setAnchor(fbsCompany.getWebsite());
        companyDetail.add(c1);
        companyDetail.setAlignment(Element.ALIGN_CENTER);
        //document.add(companyDetail);
        PdfPCell company=new PdfPCell(companyDetail);
        company.setHorizontalAlignment(Element.ALIGN_CENTER);
        outerTable.addCell(company);

       String string="DEMAND LETTER";
        Chunk c=new Chunk(string,smallBold2);
        //c.setUnderline(1, -1);
       // document.add(new Paragraph(applicantId));
        //document.add(new Paragraph(projid.toString()));
        Paragraph paragraph=new Paragraph(c);

        Chunk registrationNumber=new Chunk("Registration No. "+fbsBooking.getRegNumber().toString(),smallBold);
       
         Chunk chunk = new Chunk(" Dated: "+ dateFormat.format(new Date()),smallBold);
         Paragraph pagedate=new Paragraph(registrationNumber);
        // pagedate.setAlignment(Element.ALIGN_LEFT);
        
        
           PdfPCell Title=new PdfPCell(paragraph);
           Title.setHorizontalAlignment(Element.ALIGN_CENTER);//paragraph.setAlignment(Element.ALIGN_CENTER);
        outerTable.addCell(Title);//document.add(paragraph);
        PdfPCell registNo=new PdfPCell(new Paragraph(registrationNumber));
        registNo.setHorizontalAlignment(Element.ALIGN_LEFT);
         //document.add(registrationNumber);
          pagedate=new Paragraph(chunk);
         PdfPTable table3 = new PdfPTable(2);
            table3.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
          PdfPCell dat=new PdfPCell(pagedate);
          dat.setBorder(Rectangle.NO_BORDER);
          dat.setHorizontalAlignment(Element.ALIGN_RIGHT);
          registNo.setBorder(Rectangle.NO_BORDER);
          //registNo.addElement(dat);
          table3.addCell(registNo);
          table3.addCell(dat);
          registNo=new PdfPCell(new Paragraph("Unit Code- "+flatId.toString(),smallBold));
       dat= new PdfPCell(new Paragraph("Booking Date: "+dateFormat.format(fbsBooking.getBookingDt()),smallBold));
          
          PdfPTable tab=new PdfPTable(2);
          registNo.setHorizontalAlignment(Element.ALIGN_LEFT);
          registNo.setBorder(Rectangle.NO_BORDER);
          dat.setHorizontalAlignment(Element.ALIGN_RIGHT);
          dat.setBorder(Rectangle.NO_BORDER);
          table3.addCell(registNo);
          table3.addCell(dat);
          outerTable.addCell(table3);
          //outerTable.addCell(tab);
        //pagedate.setAlignment(Paragraph.ALIGN_RIGHT);


       //   outerTable.addCell(pagedate);//document.add(pagedate);
      Paragraph to=new Paragraph("To,\n   ",smallBold3);
        Phrase phrase1=new Phrase("\n   ",smallBold);
      
        for(int p=fbsApplicantList.size()-2;p<fbsApplicantList.size();p++)
        {
            phrase1.add(fbsApplicantList.get(p).getApplicantName());
            if(p!=fbsApplicantList.size()-1)
                phrase1.add(new Chunk(" & "));
        }
        PdfPCell applicant=new PdfPCell(to);
         
        applicant.addElement(to);
 to.add(phrase1);
        //outerTable.addCell(phrase1);
        phrase1=new Phrase("\n   "+fbsApplicantList.get(fbsApplicantList.size()-2).getResAdd(),blackFont);
        to.add(phrase1);
        //applicant.addElement(phrase1);
        phrase1=new Phrase("\n   Mobile-"+fbsApplicantList.get(fbsApplicantList.size()-2).getMobile(),blackFont);
        to.add(phrase1);
        //applicant.addElement(phrase1);
         phrase1=new Phrase("\n   "+fbsApplicantList.get(fbsApplicantList.size()-2).getEmail(),blackFont);
         to.add(phrase1);
        //applicant.addElement(phrase1);
        //to.add(phrase1);

        phrase1=new Phrase("\n\nSubject:",smallBold);
           applicant = new PdfPCell(to);
           applicant.setHorizontalAlignment(Element.ALIGN_LEFT);
           applicant.setBorder(Rectangle.NO_BORDER);
           to.add(phrase1);
        phrase1=new Phrase(" Request for payment towards your ",blackFont);
        to.add(phrase1);
      
         phrase1=new Phrase("Unit No."+flatId.toString(),smallBold);
         to.add(phrase1);
         phrase1=new Phrase(" Booked at \"",blackFont);
         to.add(phrase1);

         phrase1=new Phrase(fbsproject.getProjName(),smallBold);
         to.add(phrase1);
       //     phrase1=new Phrase("on "+fbsBooking.getBookingDt().toString(),blackFont);
       //  to.add(phrase1);
         phrase1=new Phrase("\" "+fbsproject.getAddress()+".\n",smallBold);
         to.add(phrase1);
         phrase1= new Phrase("\nDear customer,\n\n",smallBold);
         to.add(phrase1);
       phrase1=new Phrase("You have been alloted a Unit No. ",blackFont);
       to.add(phrase1);
       phrase1=new Phrase(flatId.toString(),smallBold);
          to.add(phrase1);
       phrase1=new Phrase(" on ",blackFont);
          to.add(phrase1);
          String floorno="";
          if(session.getAttribute("floorNo").toString().trim().equals("0"))
          {
              floorno="ground";
          }else{
              floorno=session.getAttribute("floorNo").toString()+"th";
          }
       phrase1= new Phrase( floorno,smallBold);
        to.add(phrase1);
                       phrase1=new Phrase(" floor in Block/Tower ",blackFont);
                        to.add(phrase1);
                       phrase1=new Phrase(blockName,smallBold);
                        to.add(phrase1);
                      phrase1= new Phrase(" in \"",blackFont);
                       to.add(phrase1);
                       phrase1=new Phrase(fbsproject.getProjName(),smallBold);
                        to.add(phrase1);
                     phrase1=  new Phrase("\". Group Housing Residential Complex being developed ",blackFont);
                      to.add(phrase1);
                      phrase1= new Phrase("on ",blackFont);
                       to.add(phrase1);
                       phrase1=new Phrase(fbsproject.getAddress(),smallBold);
                        to.add(phrase1);
                       phrase1=new Phrase(". You have booked the reffered unit in \"",blackFont);
                        to.add(phrase1);
                       phrase1=new Phrase(fbsproject.getProjName(),smallBold);
                        to.add(phrase1);
                       phrase1=new Phrase("\" Group Housing Project under Flexi Payement Plan.",blackFont);
                        to.add(phrase1);
                       phrase1=new Phrase("The basic cost of the booked Unit is Rs. ",blackFont);
                        to.add(phrase1);
                       phrase1=new Phrase(aftercharges.toString(),smallBold);
                        to.add(phrase1);
                       phrase1=new Phrase("/- (",blackFont);
                        to.add(phrase1);
                       phrase1=new Phrase(Str,smallBold);
                        to.add(phrase1);
                       phrase1=new Phrase(" Only).",blackFont);
                        to.add(phrase1);

        outerTable.addCell(to);
       // outerTable.spacingAfter();

       // Paragraph ex=new Paragraph(explain,normafont);
        //ex.setAlignment(Element.ALIGN_JUSTIFIED);
        //phrase1.add(ex);
       float[] floats={5f,2f};
        PdfPTable table= new PdfPTable(floats);
        Paragraph total=new Paragraph("TOTAL COST(A)", blackFont);
        PdfPCell firstrow= new PdfPCell(total);
        Paragraph recieved=new Paragraph("RECEIVED AMOUNT(B)", blackFont);
        PdfPCell secondrow= new PdfPCell(recieved);
        Paragraph currentinstallment=new Paragraph("CURRENT INSTALLMENT(C) "+fbsPayplan.getPlanDesc().toUpperCase(), blackFont);
        PdfPCell thirdrow=new PdfPCell(currentinstallment);
        Paragraph outstanding=new Paragraph("PREVIOUS OUTSTANDING(+)/PREVIOUS CREDIT(-)(D)", blackFont);
        Paragraph payable=new Paragraph("AMOUNT PAYABLE(C+D)", blackFont);
        PdfPCell fourthrow=new PdfPCell(outstanding);
        Paragraph servicetax=new Paragraph("SERVICE TAX("+fbsServicetax.getServicetax()+"%)(E)", blackFont);
        PdfPCell fifthrow=new PdfPCell(payable);
        Paragraph interestrow=new Paragraph("INTREST", blackFont);
        PdfPCell interestcell=new PdfPCell(interestrow);
        Paragraph totalpayable=new Paragraph("TOTAL AMOUNT PAYABLE(C+D+E)", blackFont);
        PdfPCell sixthrow=new PdfPCell(servicetax);
        Paragraph remaining=new Paragraph("REMAINING AMOUNT [A-(B+C+D)](Excluding Service Tax)", blackFont);
        PdfPCell seventhrow= new PdfPCell(totalpayable);
        PdfPCell eighthrow=new PdfPCell(remaining);
        Paragraph pp=new Paragraph("Description",smallBold);
        PdfPCell first=new PdfPCell(pp);
        pp=new Paragraph("Amount(Rs.)",smallBold);
        PdfPCell am=new PdfPCell(pp);
        first.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(first);
        am.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(am);
        table.addCell(firstrow);
 fbsReport.setDate(new Date());
         PdfPCell rowonevalue=new PdfPCell(new Paragraph(""+aftercharges,smallBold));
        rowonevalue.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(rowonevalue);
        table.addCell(secondrow);

           fbsReport.setTotalCost(aftercharges.floatValue());

        PdfPCell rowtwovalue= new PdfPCell(new Paragraph(""+paidAmount, smallBold));
        fbsReport.setRecievedAmt((float)paidAmount);
        rowtwovalue.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(rowtwovalue);
        table.addCell(thirdrow);
        PdfPCell rowthreevalue=new PdfPCell(new Paragraph(""+currentInstallment,smallBold));
        rowthreevalue.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(rowthreevalue);
        fbsReport.setCurInstallment((float)currentInstallment);

        fbsReport.setRemainingAmt(Remaining.floatValue());
        fbsReport.setRegNumber(fbsBooking.getRegNumber());
        
        table.addCell(fourthrow);
        PdfPCell rowfourvalue=new PdfPCell(new Paragraph(""+outstandAmount, smallBold));
        fbsReport.setOutCredit((float)outstandAmount);
        rowfourvalue.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(rowfourvalue);
        table.addCell(fifthrow);
          
        long temp=0;
        
        if(amountPayable>0)
        {
            temp=amountPayable;
                 
            }
         fbsReport.setServiceTax((float)(temp*serviceTax/100));
        PdfPCell rowfifthvalue=new PdfPCell(new Paragraph(""+temp, smallBold));
        rowfifthvalue.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(rowfifthvalue);
     if(withInterest==true)
     {
            table.addCell(interestcell);
       
         PdfPCell interestcellvalue=new PdfPCell(new Paragraph((""+interestamount),smallBold));
         interestcellvalue.setHorizontalAlignment(Element.ALIGN_RIGHT);
         table.addCell(interestcellvalue);
     }
        table.addCell(sixthrow);
        PdfPCell rowsixthvalue= new PdfPCell(new Paragraph(""+(temp*serviceTax/100), smallBold));
        rowsixthvalue.setHorizontalAlignment(Element.ALIGN_RIGHT);
       table.addCell(rowsixthvalue);      
        table.addCell(seventhrow);
        PdfPCell rowseventhvalue=new PdfPCell(new Paragraph(""+(totalAmountPayable+interestamount),smallBold));
        fbsReport.setAmountPayable((float)totalAmountPayable);
        rowseventhvalue.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(rowseventhvalue);
        table.addCell(eighthrow);
        PdfPCell roweighthvalue=new PdfPCell(new Paragraph(""+Remaining, smallBold));
        roweighthvalue.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(roweighthvalue);        
        table.setSpacingBefore(20f);
        table.setSpacingAfter(20f);
         outerTable.addCell(table);
       to=new Paragraph();
         phrase1=new Phrase("It is requested to remit the amount with in 7 days. We communicate the payment schedule of the amount of Rs. ",blackFont);
           to.add(phrase1);
         phrase1=new Phrase(Remaining.toString(),smallBold);
                    to.add(phrase1);

         phrase1=new Phrase("/- payable by you in due course.\n\n We assure you of our best services at all times.\n Thanking you\n\n",blackFont);
                    to.add(phrase1);
           outerTable.addCell(to);
                    // applicant=new PdfPCell();
        //Paragraph latep=new Paragraph(late, normafont);
        //latep.setAlignment(Element.ALIGN_JUSTIFIED);
       // PdfPTable table2=new PdfPTable(1);
        //table2.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        //table2.addCell(latep);
        // document.add(latep);
       phrase1=new Phrase("For "+fbsCompany.getCompanyName(),smallBold);
       to.add(phrase1);
        to.setSpacingAfter(60);
        //PdfPCell nam=new PdfPCell(name);
        //nam.setBorder(Rectangle.NO_BORDER);
        
          
         fbsReportFacade.create(fbsReport);
phrase1=new Phrase(("\n\nNOTE:\n"),blackFont);
           ZapfDingbatsList zapfDingbatsList=new ZapfDingbatsList(42, 15);
           to.add(phrase1);
           zapfDingbatsList.add(new ListItem("1. For any delayed in payment interest will be charged 18% p.a.\n", blackFont));
           zapfDingbatsList.add(new ListItem("2. Helpline No."+fbsCompany.getTelNumber()+"/Email ID:"+fbsCompany.getEmail()+".\n", blackFont));
           zapfDingbatsList.add(new ListItem("3. Please ignore this letter in case the payment has already been made.\n", blackFont));
           zapfDingbatsList.add(new ListItem("4. Service Tax is applicable as per Govt. Norms.\n", blackFont));
           zapfDingbatsList.add(new ListItem("5. This is computer generated sheet and does not require any signature.\n", blackFont));
        /*document.add(new Paragraph("NOTE:", new Font(Font.TIMES_ROMAN, 10, Font.BOLD)));
        document.add(new Paragraph("1. For any delayed in payment interest will be charged 18% p.a.\n"
                + "2. Helpline No."+fbsCompany.getTelNumber()+"/Email ID:"+fbsCompany.getEmail()+"\n"
                + "3. Please ignore this letter in case the payment has already been made.\n"
                + "4. Service Tax is applicable as per Govt. Norms.\n"
                + "5. This is computer generated sheet and does not require any signature.", normafont));*/
    //PdfPCell bottom=new PdfPCell(zapfDingbatsList);
           to.add(zapfDingbatsList);
      


        //table2.addCell(nam);
          //outerTable.addCell(table2);
          // outerTable.addCell(to);
           document.add(outerTable);
        document.close();
        response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setContentType("application/pdf");

            baos.writeTo(out);
            out.flush();
        }catch(DocumentException d)
        {
            System.out.println("error with the pdf writer"+d);
            response.sendRedirect("/FbsFaces/faces/jsfpages/common/errorPage.xhtml");
        }


        } finally {
            out.close();
        }
    }
    void spaceafter(float f)
    {
       Paragraph p=new Paragraph();
       p.setSpacingAfter(f);
   }
     //Count the number of digits in the input number
    int numberCount(int num) {
        int cnt = 0;

        while (num > 0) {
            r = num % 10;
            cnt++;
            num = num / 10;
        }

        return cnt;
    }

    //Function for Conversion of two digit
    String twonum(int numq) {
        int numr, nq;
        String ltr = "";

        nq = numq / 10;
        numr = numq % 10;

        if (numq > 19) {
            ltr = ltr + tens[nq] + unitdo[numr];
        } else {
            ltr = ltr + unitdo[numq];
        }

        return ltr;
    }

    //Function for Conversion of three digit
    String threenum(int numq) {
        int numr, nq;
        String ltr = "";

        nq = numq / 100;
        numr = numq % 100;

        if (numr == 0) {
            ltr = ltr + unitdo[nq] + digit[1];
        } else {
            ltr = ltr + unitdo[nq] + digit[1] + " and" + twonum(numr);
        }
        return ltr;



    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse  response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse  response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
