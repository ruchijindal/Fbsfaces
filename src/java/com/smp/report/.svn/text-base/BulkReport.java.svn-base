/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.report;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.ExceptionConverter;
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
import com.smp.entity.FbsBlock;
import com.smp.entity.FbsBooking;
import com.smp.entity.FbsCharge;
import com.smp.entity.FbsCompany;
import com.smp.entity.FbsDiscount;
import com.smp.entity.FbsFlat;
import com.smp.entity.FbsFlatType;
import com.smp.entity.FbsInterest;
import com.smp.entity.FbsParkingAllot;
import com.smp.entity.FbsParkingType;
import com.smp.entity.FbsPayment;
import com.smp.entity.FbsPayplan;
import com.smp.entity.FbsPlanname;
import com.smp.entity.FbsPlc;
import com.smp.entity.FbsProject;
import com.smp.entity.FbsServicetax;
import com.smp.managedbean.BulkManagedBean;
import com.smp.session.FbsApplicantFacade;
import com.smp.session.FbsBlockFacade;
import com.smp.session.FbsBookingFacade;
import com.smp.session.FbsCompanyFacade;
import com.smp.session.FbsInterestFacade;
import com.smp.session.FbsParkingTypeFacade;
import com.smp.session.FbsPlannameFacade;
import com.smp.session.FbsPlcFacade;
import com.smp.session.FbsProjectFacade;
import com.smp.session.FbsServicetaxFacade;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.jms.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpSession;

/**
 *
 * @author smp
 */
public class BulkReport extends HttpServlet {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
    @EJB
    FbsCompanyFacade fbsCompanyFacade;
    @EJB
    FbsApplicantFacade fbsApplicantFacade;
    @EJB
    FbsBookingFacade fbsBookingFacade;
    @EJB
    FbsProjectFacade fbsProjectFacade;
    @EJB
    FbsBlockFacade fbsBlockFacade;
    @EJB
    FbsParkingTypeFacade fbsParkingTypeFacade;
    @EJB
    FbsServicetaxFacade fbsServicetaxFacade;
    @EJB
    FbsInterestFacade fbsInterestFacade;
    @EJB
    ChargesAndPlanDetails chargesAndPlanDetails;
    @EJB
    FbsPlcFacade fbsPlcFacade;
    @EJB
    FbsPlannameFacade fbsPlannameFacade;
    //Entity
    FbsInterest fbsInterest = new FbsInterest();
    FbsApplicant fbsApplicant = new FbsApplicant();
    FbsBooking fbsBooking = new FbsBooking();
    FbsBlock fbsBlock = new FbsBlock();
    FbsCompany fbsCompany = new FbsCompany();
    FbsPlanname fbsPlanname = new FbsPlanname();
    FbsProject fbsProject = new FbsProject();
    FbsPlc fbsPlc = new FbsPlc();
    FbsDiscount fbsDiscount = new FbsDiscount();
    FbsPayment fbsPayment = new FbsPayment();
    FbsFlatType fbsFlatType = new FbsFlatType();
    FbsPayplan fbsPayplan = new FbsPayplan();
    FbsServicetax fbsServicetax = new FbsServicetax();
    FbsFlat fbsFlat = new FbsFlat();
    BulkManagedBean bulkManagedBean;
    List<FbsInterest> fbsInterests = new ArrayList<FbsInterest>();
    String reportType="";
    //end Entity
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    //List<FbsCompany> fbsCompanyList=new ArrayList<FbsCompany>();
    String[] unitdo = {"", " One", " Two", " Three", " Four", " Five",
        " Six", " Seven", " Eight", " Nine", " Ten", " Eleven", " Twelve",
        " Thirteen", " Fourteen", " Fifteen", " Sixteen", " Seventeen",
        " Eighteen", " Nineteen"};
    String[] tens = {"", "Ten", " Twenty", " Thirty", " Forty", " Fifty",
        " Sixty", " Seventy", " Eighty", " Ninety"};
    String[] digit = {"", " Hundred", " Thousand", " Lakh", " Crore"};

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //  PrintWriter out = response.getWriter();
        ServletOutputStream out = response.getOutputStream();
        com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.A4, 0, 0, 60, 30);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            try {
                PdfWriter writer = PdfWriter.getInstance(document, baos);

                document.open();
                document.addTitle(" Bulk Demand Letter ");
                document.addSubject("Using iText");
                document.addKeywords("Java, PDF, iText");

                // System.out.println("in bulk report ->" + request.getParameter("companyId"));
                Integer companyId = Integer.parseInt(request.getParameter("companyId").toString());
                System.out.println("compaany id is->" + companyId);
                fbsCompany = fbsCompanyFacade.find(companyId);
                reportType = request.getParameter("type");
                System.out.println("report type in bulk report is " + reportType);
                List<FbsProject> fbsProjects = new ArrayList<FbsProject>();
                fbsProjects = em.createNamedQuery("FbsProject.findByCompanyId").setParameter("companyId", companyId).getResultList();
                //  for (int i = 0; i < fbsProjects.size(); i++) {
                System.out.println("fbs project is->>" + request.getParameter("projId").toString());
                fbsProject = fbsProjectFacade.find(Integer.parseInt(request.getParameter("projId").toString()));//= fbsProjects.get(i);
                // System.out.println("fbs projectis->>"+Integer.parseInt(request.getParameter("projId")));
                List<FbsBlock> fbsBlocks = new ArrayList<FbsBlock>();
                fbsBlocks = em.createNamedQuery("FbsBlock.findByFkProjId").setParameter("fkProjId", fbsProject.getProjId()).getResultList();

                for (int b = 0; b < fbsBlocks.size(); b++) {
                    System.out.println("fbsBlock id->" + fbsBlocks.get(b));
                    fbsBlock = fbsBlocks.get(b);
                    List<FbsFlat> fbsFlatList = new ArrayList<FbsFlat>();
                    try {
                        fbsFlatList = parseXml(fbsBlock);
                    } catch (Exception e) {
                        System.out.println("Exception in Bulk Report->" + e);
                    }
                    for (int f = 0; f < fbsFlatList.size(); f++) {
                        FbsFlat fbsFlat = new FbsFlat();
                        fbsFlat = fbsFlatList.get(f);
                        fbsFlat.setBlockId(fbsBlock.getBlockId());
                        fbsFlat.setBlockName(fbsBlock.getBlockName());
                        fbsFlat.setProjId((int) fbsBlock.getFkProjId());
                        fbsFlat.setStatus(fbsBlock.getStatus());
                        System.out.println("Block Id.-> " + fbsFlat.getBlockId() + "block name-> " + fbsFlat.getBlockName() + " applicant name ->"
                                + fbsFlat.getApplicantName() + " flat No-> " + fbsFlat.getFlatNo() + " \nflattype ->"
                                + fbsFlat.getFlatType() + "  block id->"
                                + fbsFlat.getBlockId() + " flattype specification ->" + fbsFlat.getFlatTypeSpecification() + " \n florrNo ->"
                                + fbsFlat.getFloorNo() + " flatid--> " + fbsFlat.getFlatId() + " " + fbsFlat.getProjId() + " \nfkfloor id->" + fbsFlat.getfkFloorId()
                                + "  floor id->" + fbsFlat.getFloorId() + " bookdate ->" + fbsFlat.getBookDate() + " status->" + fbsFlat.getStatus());

                        List<FbsBooking> fbsBookings = new ArrayList<FbsBooking>();
                        List<FbsApplicant> fbsApplicants = new ArrayList<FbsApplicant>();
                        Query query1 = em.createNamedQuery("FbsBooking.findByBlockIdAndFlatId");
                        query1.setParameter("blockId", fbsFlat.getBlockId());
                        query1.setParameter("flatId", fbsFlat.getFlatId().intValue());
                        fbsBookings = query1.getResultList();//fbsFlat.getBlockId

                        /*for(int book=0;book<fbsBookings.size();book++)
                        {
                        System.out.println("id"+fbsBookings.get(book).getRegNumber()+"flat id"+fbsBookings.get(book).getFlatId()+"user");
                        }*/
                        for (int bo = 0; bo < fbsBookings.size(); bo++) {
                            fbsBooking = fbsBookings.get(bo);
                            fbsApplicants = em.createNamedQuery("FbsApplicant.findByFlatId").setParameter("flatId", fbsFlat.getFlatId()).getResultList();//fbsBooking
                            if (fbsApplicants.isEmpty()) {
                                continue;
                            }
                            fbsApplicant = fbsApplicants.get(0);
                            ChargesAndPlanDetails chargesAndPlanDetails1 = chargesAndPlanDetails.findPlannameAndDiscount(fbsFlat.getFlatId().toString().trim());
                            List<String> plcList = chargesAndPlanDetails1.plcidlist;
                            List<FbsPlc> fbsPlcList = new ArrayList<FbsPlc>();

                            for (int pl = 0; pl < plcList.size(); pl++) {
                                System.out.println("p " + plcList.get(pl));
                                FbsPlc temp = fbsPlcFacade.find(Integer.parseInt(plcList.get(pl)));
                                fbsPlcList.add(temp);
                            }
                            // fbsFlat.setApplicantName(fbsApplicant.getApplicantName());
                            // fbsPlc = em.find(FbsPlc.class, fbsBooking.getPlcId());
                            System.out.println("Appliant Name is ->" + fbsApplicant.getApplicantName() + " FLAT ID " + fbsFlat.getFlatId());//fbsBooking
                            fbsDiscount = em.find(FbsDiscount.class, fbsBooking.getDiscountId());
                            List<FbsParkingAllot> fbsParkingAllots = em.createNamedQuery("FbsParkingAllot.findByFkFlatId").setParameter("fkFlatId", fbsFlat.getFlatId()).getResultList();//fbsBooking
                            long parkingAmount = 0;
                            FbsParkingType fbsParkingType = new FbsParkingType();
                            for (int j = 0; j < fbsParkingAllots.size(); j++) {
                                fbsParkingType = fbsParkingTypeFacade.find(fbsParkingAllots.get(j).getParkingTypeId());
                                parkingAmount += fbsParkingType.getParkingCharge();
                                System.out.println("parking->" + fbsParkingType.getParkingCharge());
                            }
                            List<FbsPayment> fbsPaymentList = em.createNamedQuery("FbsPayment.findByUnitCode").setParameter("unitCode", fbsFlat.getFlatId().toString()).getResultList();//fbsBooking
                            long paidAmount = 0;
                            for (int p = 0; p < fbsPaymentList.size(); p++) {
                                fbsPayment = fbsPaymentList.get(p);
                                paidAmount += fbsPayment.getPaidAmount();
                            }
                            System.out.println("pAid AMount->" + paidAmount);
                            fbsFlatType = (FbsFlatType) em.createNamedQuery("FbsFlatType.findByFlatTypeId").setParameter("flatTypeId", Integer.parseInt(fbsFlat.getFlatType())).getSingleResult();
                            Integer bsp = fbsFlatType.getFlatBsp();
                            Integer sba = fbsFlatType.getFlatSba();
                            Integer basicSalePrice = bsp * sba;
                            long plcCharge = 0;// fbsPlc.getPlcCharge();
                            for (int plc = 0; plc < fbsPlcList.size(); plc++) {
                                plcCharge = plcCharge + fbsPlcList.get(plc).getPlcCharge();
                            }

                            Integer discountPercentage = fbsDiscount.getPercentage();
                            long chargeAmount = 0;
                            System.out.println("PROJECT ID" + fbsProject.getProjId());
                            List<FbsCharge> fbsChargeList = new ArrayList<FbsCharge>();
                            fbsChargeList = em.createNamedQuery("FbsCharge.findByFkProjId").setParameter("fkProjId", fbsProject.getProjId().toString()).getResultList();
                            for (int ch = 0; ch < fbsChargeList.size(); ch++) {
                                chargeAmount += fbsChargeList.get(ch).getAmount() * sba;
                            }
                            System.out.println("charge amount of fbs charge->" + chargeAmount);
                            fbsPlanname = fbsPlannameFacade.find(fbsBooking.getPlanId());
                            Double plan_discount = ((double) fbsPlanname.getDiscount()) * (double) basicSalePrice / 100;
                            Long netBasicSalePrice = Math.round(basicSalePrice - basicSalePrice * discountPercentage / 100 - plan_discount);
                            long plcsba = sba * plcCharge;
                            Long aftercharges = netBasicSalePrice + plcsba + chargeAmount + parkingAmount;
                            long withoutPlc = aftercharges - plcsba;
                            System.out.println("plcCharges->" + plcCharge + "sba" + sba);
                            System.out.println("netbasicsalesprice-> " + netBasicSalePrice + "\naftercharges" + aftercharges + "Plc charges" + sba * plcCharge + "othercharge" + chargeAmount);
                            //long totalPayableAmount=aftercharges-paidAmount;
                            System.out.println("PLAN ID IS ->" + fbsBooking.getPlanId());
                            //  if (fbsBooking.getPlanId().intValue() != 26) {
                            //continue;
                            //  }
                            //break;
                            // System.out.println("helloooooooooooooooooooooooooooooooooooooooooooooooo");
                            List<FbsPayplan> fbsPayplanList = em.createNamedQuery("FbsPayplan.findByFkPlanId").setParameter("fkPlanId", fbsBooking.getPlanId()).getResultList();//fbsBooking.getPlanId()

                            long currentInstallment = 0;
                            int qp = 0;
                            long outstandAmount = 0;
                            long netBasicSalePrice1 = 0;
                            System.out.println("withoutplc" + withoutPlc + "size->" + fbsPayplanList.size());
                            for (; qp < fbsPayplanList.size(); qp++) {

                                Date today = new Date();
                                Date dueDate = fbsPayplanList.get(qp).getDueDate();
                                if (dueDate != null) {


                                    System.out.println("Date is->" + today.toString() + "due date->" + dueDate.toString() + "qp is-> " + qp);
                                    if (today.after(dueDate)) {
                                        fbsPayplan = fbsPayplanList.get(qp);
                                        currentInstallment = (netBasicSalePrice) * (fbsPayplan.getPercentage()) / 100 + plcsba * fbsPayplanList.get(qp).getPercentage() / 100;
                                        netBasicSalePrice1 = fbsPayplanList.get(qp).getPercentage() * netBasicSalePrice / 100;
                                        if (qp > 0) {

                                            outstandAmount += fbsPayplanList.get(qp - 1).getPercentage() * (netBasicSalePrice) / 100 + plcsba * fbsPayplanList.get(qp - 1).getPercentage() / 100;
                                            if (qp == fbsPayplanList.size() - 1) {
                                                outstandAmount += withoutPlc;
                                            }
                                        }
                                    } else {
                                        break;
                                    }
                                } else {
                                    fbsPayplan = fbsPayplanList.get(qp);
                                    if (qp > 0) {

                                        outstandAmount += fbsPayplanList.get(qp - 1).getPercentage() * (netBasicSalePrice) / 100 + plcsba * fbsPayplanList.get(qp - 1).getPercentage() / 100;
                                    }
                                    currentInstallment = (netBasicSalePrice) * (fbsPayplan.getPercentage()) / 100 + plcsba * fbsPayplanList.get(qp).getPercentage() / 100;
                                    break;
                                }


                            }

                            //if(qp==0)
                            // continue;
                            System.out.println("Outstanding Amount " + outstandAmount + "qp-> =" + qp);
                            outstandAmount -= paidAmount;
                            //   if(outstandAmount<=0)
                            //  continue;
                            // int size=fbsPayplanList.size();

                            //                    if(qp==fbsPayplanList.size())
                            //{
                            // currentInstallment=0;
                            //}
                            //else

                            //   System.out.println("\nouststaing" + outstandAmount + "currentInstallment" + currentInstallment + "percentage" + fbsPayplan.getPercentage());
                            //  System.out.println("PLC " + plcsba * fbsPayplanList.get(qp).getPercentage() / 100 + "install" + (netBasicSalePrice) * (fbsPayplan.getPercentage()) / 100);
                            long amountPayable = outstandAmount + currentInstallment;
                            long interestamount = 0;
                            System.out.println("debug complete1");

                            if (reportType.equals("true")) {

                                fbsInterests = em.createNamedQuery("FbsInterest.findByCompanyId").setParameter("companyId", companyId).getResultList();
                                for (int l = 0; l < fbsInterests.size(); l++) {
                                    Date d = new Date();
                                    if (fbsInterests.get(l).getStDate().before(d) && fbsInterests.get(l).getEndDate().after(d)) {
                                        fbsInterest = fbsInterests.get(l);
                                        break;
                                    }
                                }
                                interestamount = Math.round(amountPayable * fbsInterest.getRate() / (100 * 12));
                                //amountPayable=Math.round(amountPayable+interestamount);

                            }
                            List<FbsServicetax> servicetaxList = fbsServicetaxFacade.findAll();
                            for (int qt = 0; qt < servicetaxList.size(); qt++) {
                                Date date = new Date();
                                Date std = servicetaxList.get(qt).getStDate();
                                if (date.after(std)) {
                                    fbsServicetax = servicetaxList.get(qt);

                                } else {
                                    break;
                                }
                            }
                            System.out.println("debug complete2");
                            long serviceTax = fbsServicetax.getServicetax();
                            double totalAmountPayable = 0;
                            if (amountPayable > 0) {
                                totalAmountPayable += amountPayable + amountPayable * serviceTax / 100;
                            }
                            Long Remaining = aftercharges - amountPayable - paidAmount;
                            System.out.println("Service tax is ->" + fbsServicetax);
                            //fbsCompany=fbsCompanyFacade.find(companyId);
//segment code for number conversion
                            int len, q = 0, r = 0;
                            String ltr = " ";
                            String Str = "Rupees";

                            int num = Integer.parseInt(String.valueOf(aftercharges).toString());

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
                                    // System.exit(1);


                                }
                                if (num == 0) {
                                    System.out.println(Str + " Only");
                                }
                            }//end while
                            //PdfPTable outerTable= new PdfPTable(1);
                            // document.add();
                            if (totalAmountPayable <= 0) {
                                continue;
                            }
                            System.out.println("callllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll");
                            com.itextpdf.text.pdf.PdfPTable tabe = new PdfPTable(1);
                            tabe = generatePdf(amountPayable, totalAmountPayable, outstandAmount, serviceTax, paidAmount, currentInstallment, fbsPayplan, Remaining, Str, aftercharges, fbsCompany, fbsBooking, fbsApplicants, fbsProject, fbsFlat,interestamount);
                            document.add(tabe);
                            document.newPage();
                        }
                    }
                }
                // }//

                document.close();
                response.setHeader("Expires", "0");
                response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
                response.setHeader("Pragma", "public");
                response.setContentType("application/pdf");

                baos.writeTo(out);
                out.flush();
                //
            } catch (ExceptionConverter ec) {

                response.sendRedirect("/FbsFaces/faces/jsfpages/Report/consumerReport.xhtml");
                //response.getOutputStream().println("No one has the due amount for this project.");
            } catch (Exception e) {
                e.printStackTrace();
                //System.out.println("Exception in pdfWriter" + e);
                response.sendRedirect("/FbsFaces/faces/jsfpages/common/errorPage.xhtml");

            }
        } finally {
            out.close();
        }
    }

    PdfPTable generatePdf(long amountPayable, double totalAmountPayable, long outstandAmount, long serviceTax, long paidAmount, long currentInstallment, FbsPayplan fbsPayplan, Long Remaining, String Str, Long aftercharges, FbsCompany fbsCompany, FbsBooking fbsBooking, List<FbsApplicant> fbsApplicantList, FbsProject fbsproject, FbsFlat fbsFlat,long interestamount) {
        Font catFont = new Font(Font.TIMES_ROMAN, 18, Font.BOLD);
        Font redFont = new Font(Font.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
        Font blackFont = new Font(Font.TIMES_ROMAN, 8, Font.NORMAL, BaseColor.BLACK);
        Font subFont = new Font(Font.TIMES_ROMAN, 16, Font.BOLD);
        Font smallBold = new Font(Font.TIMES_ROMAN, 8, Font.BOLD);
        Font blackSmallFont = new Font(Font.TIMES_ROMAN, 6, Font.NORMAL, BaseColor.BLACK);
        Font smallBold1 = new Font(Font.BOLD, 12);
        Font smallBold2 = new Font(Font.BOLD, 10);
        Font smallBold3 = new Font(Font.BOLD, 9);
        PdfPTable outerTable = new PdfPTable(1);
        Paragraph companyDetail = new Paragraph(fbsCompany.getCompanyName(), smallBold1);
        companyDetail.add(new Paragraph("\n" + fbsCompany.getAddress(), smallBold));
        companyDetail.add(new Chunk("Phone: ", smallBold));
        companyDetail.add(new Chunk(fbsCompany.getTelNumber(), smallBold));
        companyDetail.add(new Chunk(" \nEmail: ", smallBold));
        companyDetail.add(new Chunk(fbsCompany.getEmail(), smallBold));
        companyDetail.add(new Chunk(" \nWebsite: ", smallBold));
        Chunk c1 = new Chunk(fbsCompany.getWebsite(), smallBold);
        c1.setAnchor(fbsCompany.getWebsite());
        companyDetail.add(c1);
        companyDetail.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);

        PdfPCell company = new PdfPCell(companyDetail);
        company.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
        outerTable.addCell(company);
        String string = "DEMAND LETTER";
        Chunk c = new Chunk(string, smallBold2);

        Paragraph paragraph = new Paragraph(c);

        Chunk registrationNumber = new Chunk("Registration No. " + fbsBooking.getRegNumber().toString(), smallBold);

        Chunk chunk = new Chunk(" Dated: " + dateFormat.format(new Date()), smallBold);
        Paragraph pagedate = new Paragraph(registrationNumber);
        PdfPCell Title = new PdfPCell(paragraph);
        Title.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);//paragraph.setAlignment(Element.ALIGN_CENTER);
        outerTable.addCell(Title);//document.add(paragraph);
        PdfPCell registNo = new PdfPCell(new Paragraph(registrationNumber));
        registNo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);

        pagedate = new Paragraph(chunk);
        PdfPTable table3 = new PdfPTable(2);
        table3.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        PdfPCell dat = new PdfPCell(pagedate);
        dat.setBorder(Rectangle.NO_BORDER);
        dat.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
        registNo.setBorder(Rectangle.NO_BORDER);

        table3.addCell(registNo);
        table3.addCell(dat);

        outerTable.addCell(table3);

        Paragraph to = new Paragraph("To,\n   ", smallBold3);
        Phrase phrase1 = new Phrase("\n   ", smallBold);
        for (int p = 0; p < fbsApplicantList.size(); p++) {
            phrase1.add(fbsApplicantList.get(p).getApplicantName());
            if (p != fbsApplicantList.size() - 1) {
                phrase1.add(new Chunk(" & "));
            }
        }
        PdfPCell applicant = new PdfPCell(to);

        applicant.addElement(to);
        to.add(phrase1);
        //outerTable.addCell(phrase1);
        phrase1 = new Phrase("\n   " + fbsApplicantList.get(0).getResAdd(), blackFont);
        to.add(phrase1);
        //applicant.addElement(phrase1);
        phrase1 = new Phrase("\n   Mobile-" + fbsApplicantList.get(0).getMobile(), blackFont);
        to.add(phrase1);
        //applicant.addElement(phrase1);
        phrase1 = new Phrase("\n   " + fbsApplicantList.get(0).getEmail(), blackFont);
        to.add(phrase1);
        //applicant.addElement(phrase1);
        //to.add(phrase1);

        phrase1 = new Phrase("\n\nSubject:", smallBold);
        applicant = new PdfPCell(to);
        applicant.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
        applicant.setBorder(Rectangle.NO_BORDER);
        to.add(phrase1);
        phrase1 = new Phrase(" Request for payment towards your ", blackFont);
        to.add(phrase1);
        phrase1 = new Phrase("Unit No." + fbsBooking.getFlatId().toString(), smallBold);
        to.add(phrase1);
        phrase1 = new Phrase(" Booked at \"", blackFont);
        to.add(phrase1);

        phrase1 = new Phrase(fbsproject.getProjName(), smallBold);
        to.add(phrase1);
        phrase1 = new Phrase("\" " + fbsproject.getAddress() + ".\n", smallBold);
        to.add(phrase1);
        phrase1 = new Phrase("\nDear customer,\n\n", smallBold);
        to.add(phrase1);
        phrase1 = new Phrase("You have been alloted a Unit No. ", blackFont);
        to.add(phrase1);
        phrase1 = new Phrase(fbsBooking.getFlatId().toString(), smallBold);
        to.add(phrase1);
        phrase1 = new Phrase(" on ", blackFont);
        to.add(phrase1);
        phrase1 = new Phrase(fbsFlat.getFloorNo(), smallBold);
        to.add(phrase1);
        phrase1 = new Phrase("th floor in Block/Tower ", blackFont);
        to.add(phrase1);
        phrase1 = new Phrase(fbsFlat.getBlockName(), smallBold);
        to.add(phrase1);
        phrase1 = new Phrase(" in \"", blackFont);
        to.add(phrase1);
        phrase1 = new Phrase(fbsproject.getProjName(), smallBold);
        to.add(phrase1);
        phrase1 = new Phrase("\". Group Hosuing Residential Complex being developed ", blackFont);
        to.add(phrase1);
        phrase1 = new Phrase("on ", blackFont);
        to.add(phrase1);
        phrase1 = new Phrase(fbsproject.getAddress(), smallBold);
        to.add(phrase1);
        phrase1 = new Phrase(". You have booked the reffered unit in \"", blackFont);
        to.add(phrase1);
        phrase1 = new Phrase(fbsproject.getProjName(), smallBold);
        to.add(phrase1);
        phrase1 = new Phrase("\" Group Housing Project under Flexi Payement Plan.", blackFont);
        to.add(phrase1);
        phrase1 = new Phrase("The basic cost of the booked Unit is Rs. ", blackFont);
        to.add(phrase1);
        phrase1 = new Phrase(aftercharges.toString(), smallBold);
        to.add(phrase1);
        phrase1 = new Phrase("/- (", blackFont);
        to.add(phrase1);
        phrase1 = new Phrase(Str, smallBold);
        to.add(phrase1);
        phrase1 = new Phrase(" Only).", blackFont);
        to.add(phrase1);

        outerTable.addCell(to);
        float[] floats = {5f, 2f};
        PdfPTable table = new PdfPTable(floats);
        Paragraph total = new Paragraph("Total Cost(A)", blackFont);
        PdfPCell firstrow = new PdfPCell(total);
        Paragraph recieved = new Paragraph("RECEIVED AMOUNT(B)", blackFont);
        PdfPCell secondrow = new PdfPCell(recieved);
        Paragraph currentinstallment = new Paragraph("CURRENT INSTALLMENT(C) " + fbsPayplan.getPlanDesc().toUpperCase(), blackFont);
        PdfPCell thirdrow = new PdfPCell(currentinstallment);
        Paragraph outstanding = new Paragraph("PREVIOUS OUTSTANDING(+)/PREVIOUS CREDIT(-)(D)", blackFont);
        Paragraph payable = new Paragraph("AMOUNT PAYABLE(C+D)", blackFont);
        PdfPCell fourthrow = new PdfPCell(outstanding);
        Paragraph servicetax = new Paragraph("Service Tax(" + fbsServicetax.getServicetax() + "%)(E)", blackFont);
        PdfPCell fifthrow = new PdfPCell(payable);
        Paragraph interestrow=new Paragraph("Interest", blackFont);
        PdfPCell interestcell=new PdfPCell(interestrow);
        Paragraph totalpayable = new Paragraph("TOTAL AMOUNT PAYABLE(C+D+E)", blackFont);
        PdfPCell sixthrow = new PdfPCell(servicetax);
        Paragraph remaining = new Paragraph("REMAINING[A-(B+C+D)](Excluding Service Tax)", blackFont);
        PdfPCell seventhrow = new PdfPCell(totalpayable);
        PdfPCell eighthrow = new PdfPCell(remaining);
        Paragraph pp = new Paragraph("Description", smallBold);
        PdfPCell first = new PdfPCell(pp);
        pp = new Paragraph("Amount(Rs.)", smallBold);
        PdfPCell am = new PdfPCell(pp);
        first.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
        table.addCell(first);
        am.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
        table.addCell(am);
        table.addCell(firstrow);

        PdfPCell rowonevalue = new PdfPCell(new Paragraph("" + aftercharges, smallBold));
        rowonevalue.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
        table.addCell(rowonevalue);
        table.addCell(secondrow);
        PdfPCell rowtwovalue = new PdfPCell(new Paragraph("" + paidAmount, smallBold));
        rowtwovalue.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
        table.addCell(rowtwovalue);
        table.addCell(thirdrow);
        PdfPCell rowthreevalue = new PdfPCell(new Paragraph("" + currentInstallment, smallBold));
        rowthreevalue.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
        table.addCell(rowthreevalue);
        table.addCell(fourthrow);
        PdfPCell rowfourvalue = new PdfPCell(new Paragraph("" + outstandAmount, smallBold));
        rowfourvalue.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
        table.addCell(rowfourvalue);
        table.addCell(fifthrow);
        long temp = 0;
        if (amountPayable > 0) {
            temp = amountPayable;
            System.out.println("temporary------------------" + temp);
        }
        PdfPCell rowfifthvalue = new PdfPCell(new Paragraph("" + temp, smallBold));
        rowfifthvalue.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
        table.addCell(rowfifthvalue);
        if(reportType.equals("true"))
     {
            table.addCell(interestcell);

         PdfPCell interestcellvalue=new PdfPCell(new Paragraph((""+interestamount),smallBold));
         interestcellvalue.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
         table.addCell(interestcellvalue);
     }
        table.addCell(sixthrow);
        PdfPCell rowsixthvalue = new PdfPCell(new Paragraph("" + (temp * serviceTax / 100), smallBold));
        rowsixthvalue.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
        table.addCell(rowsixthvalue);
        table.addCell(seventhrow);
        PdfPCell rowseventhvalue = new PdfPCell(new Paragraph("" + totalAmountPayable, smallBold));
        rowseventhvalue.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
        table.addCell(rowseventhvalue);
        table.addCell(eighthrow);
        PdfPCell roweighthvalue = new PdfPCell(new Paragraph("" + Remaining, smallBold));
        roweighthvalue.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
        table.addCell(roweighthvalue);
        table.setSpacingBefore(20f);
        table.setSpacingAfter(20f);
        outerTable.addCell(table);
        to = new Paragraph();
        phrase1 = new Phrase("It is requested to remit the amount with in 7 days. We communicate the payment schedule of the amount of Rs. ", blackFont);
        to.add(phrase1);
        phrase1 = new Phrase(Remaining.toString(), smallBold);
        to.add(phrase1);

        phrase1 = new Phrase("/- payable by you in due course.\n\n We assure you of our best services at all times.\n Thanking you\n\n", blackFont);
        to.add(phrase1);
        outerTable.addCell(to);

        phrase1 = new Phrase("For " + fbsCompany.getCompanyName(), smallBold);
        to.add(phrase1);
        to.setSpacingAfter(60);
        phrase1 = new Phrase(("\n\nNOTE:\n"), blackFont);
        ZapfDingbatsList zapfDingbatsList = new ZapfDingbatsList(42, 15);
        to.add(phrase1);
        zapfDingbatsList.add(new ListItem("1. For any delayed in payment interest will be charged 18% p.a.\n", blackFont));
        zapfDingbatsList.add(new ListItem("2. Helpline No." + fbsCompany.getTelNumber() + "/Email ID:" + fbsCompany.getEmail() + ".\n", blackFont));
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
        //document.add(outerTable);
        return outerTable;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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

    List<FbsFlat> parseXml(FbsBlock fbsBlock) throws SAXException, IOException, ParserConfigurationException {
        int l = 0;
        List<FbsFlat> fbsFlatList = new ArrayList<FbsFlat>();
        FbsFlat fbsflat = new FbsFlat();
        String xmlFile = fbsBlock.getXmlFile();
        List<FbsFlat> refFlatList = new ArrayList<FbsFlat>();
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

        return refFlatList;
    }

    int numberCount(int num) {
        int cnt = 0;
        int r;
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
}
