/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.report;

import com.lowagie.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.DocumentException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.smp.entity.FbsApplicant;
import com.smp.entity.FbsBooking;
import com.smp.entity.FbsBookingTemp;
import com.smp.entity.FbsCharge;
import com.smp.entity.FbsCompany;
import com.smp.entity.FbsDiscount;
import com.smp.entity.FbsFlatType;
import com.smp.entity.FbsPayment;
import com.smp.entity.FbsPayplan;
import com.smp.entity.FbsPlanname;
import com.smp.entity.FbsPlc;
import com.smp.entity.FbsProject;
import com.smp.entity.FbsParkingAllot;
import com.smp.entity.FbsParkingType;
import com.smp.entity.FbsServicetax;
import com.smp.session.FbsApplicantFacade;
import com.smp.session.FbsBookingFacade;
import com.smp.session.FbsBookingTempFacade;
import com.smp.session.FbsCompanyFacade;
import com.smp.session.FbsFlatTypeFacade;
import com.smp.session.FbsParkingTypeFacade;
import com.smp.session.FbsPlannameFacade;
import com.smp.session.FbsPlcFacade;
import com.smp.session.FbsProjectFacade;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author smp
 */
@WebServlet(name = "Letter", urlPatterns = {"/Letter"})
public class Letter extends HttpServlet {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager entityManager;
    List<java.util.Date> listBillperiod;
    public List<FbsParkingAllot> fbsParkingAllotList = new ArrayList<FbsParkingAllot>();
    public List<FbsParkingType> fbsParkingTypesList = new ArrayList<FbsParkingType>();
    public FbsParkingType fbsParkingType = new FbsParkingType();
    @EJB
    FbsCompanyFacade fbsCompanyFacade;
    @EJB
    FbsBookingFacade fbsBookingFacade;
    @EJB
    FbsApplicantFacade fbsApplicantFacade;
    @EJB
    FbsFlatTypeFacade fbsFlatTypeFacade;
    @EJB
    FbsProjectFacade fbsProjectFacade;
    @EJB
    FbsBookingTempFacade fbsBookingTempFacade;
    @EJB
    FbsPlannameFacade fbsPlannameFacade;
    @EJB
    ChargesAndPlanDetails totalcharges;
    @EJB
    FbsPlcFacade fbsPlcfacade;
    @EJB
    BrokerDetails brokerDetail;
    @EJB
    ReportHelper reporthelper;
    @EJB
    FbsParkingTypeFacade fbsParkingTypeFacade;
    @EJB
    ApplicantDetails applicantDetails1;
    FbsCompany fbsCompany;
    FbsBooking fbsBooking;
    FbsFlatType fbsFlatType;
    FbsApplicant fbsApplicant;
    FbsPayplan fbsPayplan;
    FbsPlanname fbsPlanname;
    FbsPayment fbsPayment;
    FbsBookingTemp fbsBookingTemp;
    FbsPlc fbsPlc;
    FbsDiscount fbsDiscount;
    private String unitCode;
    private FbsProject fbsProject;
    private String flat_specification;
    String Applicant_name = "";
    String mobile = "";
    String email = "";
    String off_add = "";
    String flattypeId = "";
    String plan_id = "";
    long flat_Sba = 0;
    long Rate = 0;
    long percentage = 0;
    long sumofinstallmentamount = 0;
    long netBasicSaleprice = 0;
    long BasicPrice = 0;
    long totalBasicSalePrice = 0;
    long totalPlcPercentage = 0;
    long Discount = 0, Plc_charge = 0;
    long Paid_amount = 0;
    long Total_Cost = 0;
    long netBasicSalePrice1 = 0;
    long totalreceivedamount = 0;
    long totalOtherCharges = 0;
    long bsp = 0;
    long sba = 0;
    long br = 0;
    long sumofOutstandingAmount = 0;
    long outStandingAmount = 0;
    long creditAmount = 0;
    long amountReceived = 0;
    long amountDue = 0;
    long sumOfCredit = 0;
    long totalPaidAmount = 0;
    long amountPayable = 0;
    long serTaxAmount = 0;
    long totalamountPayable = 0;
    long remainingAmount = 0;
    String plan_desc = "";
    String current_desc = "";
    long currentInstallment = 0;
    long netPayable = 0;
    long installment_amount = 0;
    long plc_percentage = 0;
    Date startingbillDate = null;
    Date endDate = null;
    Date firstDate = null;
    Date lastDueDate = null;
    Date current_date = new Date();
    String dueDate = null;
    private int serTax = 0;
    Integer parkingTypeId;
    Integer totalParkingCharges = 0;
    String Plc_id = "";
    String plc_name = "";
    String br_name = "";
    String br_id = "";
    String[] unitdo = {"", " One", " Two", " Three", " Four", " Five",
        " Six", " Seven", " Eight", " Nine", " Ten", " Eleven", " Twelve",
        " Thirteen", " Fourteen", " Fifteen", " Sixteen", " Seventeen",
        " Eighteen", " Nineteen"};
    String[] tens = {"", "Ten", " Twenty", " Thirty", " Forty", " Fifty",
        " Sixty", " Seventy", " Eighty", " Ninety"};
    String[] digit = {"", " Hundred", " Thousand", " Lakh", " Crore"};
    int r;
    private int num;
    private int k;
    private String currentDate;

    public void Letter() {
        fbsCompany = new FbsCompany();
        fbsBooking = new FbsBooking();
        fbsFlatType = new FbsFlatType();
        fbsApplicant = new FbsApplicant();
        fbsProject = new FbsProject();
        fbsPayplan = new FbsPayplan();
        fbsPayment = new FbsPayment();
        fbsBookingTemp = new FbsBookingTemp();
        fbsPlanname = new FbsPlanname();
        fbsPlc = new FbsPlc();
        fbsDiscount = new FbsDiscount();
    }
    private static Font catFont = new Font(Font.TIMES_ROMAN, 18, Font.BOLD);
    private static Font redFont = new Font(Font.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
    private static Font blackFont = new Font(Font.TIMES_ROMAN, 8, Font.NORMAL, BaseColor.BLACK);
    private static Font subFont = new Font(Font.TIMES_ROMAN, 16, Font.BOLD);
    private static Font smallBold = new Font(Font.TIMES_ROMAN, 8, Font.BOLD);
    private static Font blackSmallFont = new Font(Font.TIMES_ROMAN, 6, Font.NORMAL, BaseColor.BLACK);
    private static Font smallBold1 = new Font(Font.BOLD, 12);
    private static Font smallBold2 = new Font(Font.BOLD, 10);
    private static Font smallBold3 = new Font(Font.BOLD, 9);
//  FontSelector fontselectorhd2 = new FontSelector();
//    fontselectorhd2.addFont(new Font(Font.BOLD, 10));

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, DocumentException, com.itextpdf.text.DocumentException {
        response.setContentType("text/html;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        unitCode = request.getParameter("unitCode");
        fbsBooking = (FbsBooking) entityManager.createNamedQuery("FbsBooking.findByFlatId").setParameter("flatId", Integer.parseInt(unitCode.trim())).getResultList().get(0);
        HttpSession session = request.getSession();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        String companyId = request.getParameter("companyId");
        String applicantId = (String) request.getParameter("applicantId");
        String projId = (String) request.getParameter("projId");
        String blockName = (String) session.getAttribute("blockName");
        String flattypeId = (String) request.getParameter("flattypeId");

        System.out.println("*********" + applicantId);
        String flatId = (String) request.getParameter("flatId");
        fbsApplicant = (FbsApplicant) entityManager.createNamedQuery("FbsApplicant.findByFlatId").setParameter("flatId", Integer.parseInt(flatId)).getResultList().get(0);
        fbsBooking = (FbsBooking) entityManager.createNamedQuery("FbsBooking.findByFlatId").setParameter("flatId", Integer.parseInt(flatId)).getResultList().get(0);
        fbsCompany = fbsCompanyFacade.find(Integer.parseInt(companyId));
        fbsApplicant = fbsApplicantFacade.find(Integer.parseInt(applicantId));
        fbsProject = fbsProjectFacade.find((Integer.valueOf(projId)));
        fbsPlanname = (FbsPlanname) entityManager.createNamedQuery("FbsPlanname.findByFkProjId").setParameter("fkProjId", fbsProject.getProjId()).getResultList().get(0);


        fbsFlatType = (FbsFlatType) entityManager.createNamedQuery("FbsFlatType.findByFlatTypeId").setParameter("flatTypeId", Integer.parseInt(flattypeId)).getSingleResult();
        Rate = fbsFlatType.getFlatBsp();
        flat_Sba = fbsFlatType.getFlatSba();


        System.out.println("CompanyId===> " + companyId);
        System.out.println("projId===> " + projId);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        currentDate = dateFormat.format(current_date);

        List<FbsCharge> fbsChargeList = new ArrayList<FbsCharge>();
        fbsChargeList = entityManager.createNamedQuery("FbsCharge.findByFkProjId").setParameter("fkProjId", projId.toString()).getResultList();
        long plcCharges = 0;

        fbsPlc = entityManager.find(FbsPlc.class, fbsBooking.getPlcId());
        long plcCharge = fbsPlc.getPlcCharge();
        plcCharges = plcCharge * flat_Sba;
        System.out.println("Plc charges cal" + plcCharges);
        if (Rate != 0 && flat_Sba != 0) {
            BasicPrice = Rate * flat_Sba;
        } else {
            BasicPrice = 0;
        }

        fbsDiscount = entityManager.find(FbsDiscount.class, fbsBooking.getDiscountId());
        Discount = fbsDiscount.getPercentage();
        long discount_amount = (BasicPrice * Discount) / 100;
        System.out.println("discount_amount" + discount_amount);
        netBasicSalePrice1 = (BasicPrice - discount_amount);
        System.out.println("netBasicSalePrice1" + netBasicSalePrice1);

        totalOtherCharges = 0;
        List<FbsCharge> fbsCharge = totalcharges.findTotalCharges(projId);
        for (int l = 0; l < fbsCharge.size(); l++) {
            totalOtherCharges += fbsCharge.get(l).getAmount() * flat_Sba;
            System.out.println(fbsCharge.get(l).getAmount() * flat_Sba);
        }
        System.out.println("totalothercharges" + totalOtherCharges);

        fbsParkingAllotList = entityManager.createNamedQuery("FbsParkingAllot.findByFkFlatId").setParameter("fkFlatId", Integer.parseInt(unitCode)).getResultList();
        totalParkingCharges = 0;
        for (int i = 0; i < fbsParkingAllotList.size(); i++) {
            parkingTypeId = fbsParkingAllotList.get(i).getParkingTypeId();
            fbsParkingType = fbsParkingTypeFacade.find(parkingTypeId);
            totalParkingCharges = totalParkingCharges + fbsParkingType.getParkingCharge();
        }
        System.out.println("totalParkingCharges" + totalParkingCharges);
        Total_Cost = (netBasicSalePrice1 + plcCharges + totalOtherCharges + totalParkingCharges);
        System.out.println("total_cost" + Total_Cost);
        List<FbsPayment> fbsPaymentList = entityManager.createNamedQuery("FbsPayment.findByUnitCode").setParameter("unitCode", flatId).getResultList();
        long paidAmount = 0;
        for (int i = 0; i < fbsPaymentList.size(); i++) {
            fbsPayment = fbsPaymentList.get(i);
            paidAmount += fbsPayment.getPaidAmount();
        }
        System.out.println("Paid Amount->" + paidAmount);

        int len, q = 0, r = 0;
        String ltr = " ";
        String Str = "Rupees";

        int num = Integer.parseInt(String.valueOf(Total_Cost).toString());

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
                    System.exit(1);


            }
            if (num == 0) {
                System.out.println(Str + " Only");
            }
        }

        ///*************************************************

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4, 0, 0, 60, 30);

            PdfWriter docWriter = PdfWriter.getInstance(document, baos);
            document.open();
            document.addTitle("Demand Letter against" + unitCode);
            document.addSubject("Using iText");
            document.addKeywords("Java, PDF, iText");
            Paragraph preface = new Paragraph();
            preface.add(new Paragraph("", blackFont));
            addEmptyLine(preface, 1);
            document.add(preface);
            // create the outermost table for the receipt
            PdfPTable outerTable = new PdfPTable(1);
            //first table in the outside table for the company details
            PdfPTable table1 = new PdfPTable(1);
            Phrase phrase1 = new Phrase(fbsCompany.getCompanyName(), smallBold1);
            Paragraph paragraph = new Paragraph("");
            paragraph.add(phrase1);
            // We add one empty line
            addEmptyLine(paragraph, 1);

            phrase1 = new Phrase(fbsCompany.getAddress(), smallBold);
            paragraph.add(phrase1);
            addEmptyLine(paragraph, 1);

            phrase1 = new Phrase("Telephone:" + fbsCompany.getTelNumber(), smallBold);
            paragraph.add(phrase1);
            addEmptyLine(paragraph, 1);

            phrase1 = new Phrase("Email:" + fbsCompany.getEmail(), smallBold);
            paragraph.add(phrase1);
            addEmptyLine(paragraph, 1);

            PdfPCell c1 = new PdfPCell(paragraph);
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBorder(Rectangle.NO_BORDER);
            table1.addCell(c1);
            outerTable.addCell(table1);
            // third table for the Applicant Id & current date details
            PdfPTable table4 = new PdfPTable(2);
            table4.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            paragraph = new Paragraph("Applicant Id: " + fbsApplicant.getApplicantId(), smallBold);

            c1 = new PdfPCell(paragraph);
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            c1.setBorder(Rectangle.NO_BORDER);
            table4.addCell(c1);

            paragraph = new Paragraph("Date: " + dateFormat.format(new Date()), smallBold);
            c1 = new PdfPCell(paragraph);
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);

            c1.setBorder(Rectangle.NO_BORDER);
            table4.addCell(c1);
            outerTable.addCell(table4);

            // second inner table for the receipt
            PdfPTable table2 = new PdfPTable(1);
            c1 = new PdfPCell(new Paragraph("DEMAND LETTER", smallBold2));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBorder(Rectangle.NO_BORDER);
            table2.addCell(c1);
            outerTable.addCell(table2);

            PdfPTable table3 = new PdfPTable(1);
            paragraph = new Paragraph("To,", smallBold3);
            c1 = new PdfPCell(paragraph);
            c1.setBorder(Rectangle.NO_BORDER);
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            addEmptyLine(paragraph, 1);
            addEmptyLine(paragraph, 1);
            ApplicantDetails applicantDetails = applicantDetails1.findApplicantDetails(unitCode.toString());
            phrase1 = new Phrase("      " + fbsApplicant.getApplicantName() + " & " + applicantDetails.Applicant_name2, smallBold);
            paragraph.add(phrase1);
            c1 = new PdfPCell(paragraph);
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            c1.setBorder(Rectangle.NO_BORDER);
            addEmptyLine(paragraph, 1);

            phrase1 = new Phrase("      " + fbsApplicant.getResAdd(), blackFont);
            paragraph.add(phrase1);
            c1 = new PdfPCell(paragraph);
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            c1.setBorder(Rectangle.NO_BORDER);
            addEmptyLine(paragraph, 1);

            phrase1 = new Phrase("      " + fbsApplicant.getMobile(), blackFont);
            paragraph.add(phrase1);
            c1 = new PdfPCell(paragraph);
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            c1.setBorder(Rectangle.NO_BORDER);
            addEmptyLine(paragraph, 1);

            phrase1 = new Phrase("      " + fbsApplicant.getEmail(), blackFont);
            paragraph.add(phrase1);
            c1 = new PdfPCell(paragraph);
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            c1.setBorder(Rectangle.NO_BORDER);
            addEmptyLine(paragraph, 1);
            addEmptyLine(paragraph, 1);


            phrase1 = new Phrase("Subject:", smallBold);
            paragraph.add(phrase1);
            c1 = new PdfPCell(paragraph);
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            c1.setBorder(Rectangle.NO_BORDER);

            phrase1 = new Phrase("  Request for payment towards your", blackFont);
            paragraph.add(phrase1);
            phrase1 = new Phrase(" Unit No " + fbsApplicant.getFlatId(), smallBold);
            paragraph.add(phrase1);
            phrase1 = new Phrase(" Booked at ", blackFont);
            paragraph.add(phrase1);
            phrase1 = new Phrase(" " + fbsProject.getProjName(), smallBold);
            paragraph.add(phrase1);
            phrase1 = new Phrase(" " + fbsProject.getAddress(), smallBold);
            paragraph.add(phrase1);
            phrase1 = new Phrase(".", smallBold);
            paragraph.add(phrase1);
            addEmptyLine(paragraph, 1);
            addEmptyLine(paragraph, 1);
            phrase1 = new Phrase("Dear Customer ", smallBold);
            paragraph.add(phrase1);
            addEmptyLine(paragraph, 1);
            addEmptyLine(paragraph, 1);
            phrase1 = new Phrase("You have been allotted a ", blackFont);
            paragraph.add(phrase1);
            phrase1 = new Phrase(" Unit No. " + fbsApplicant.getFlatId(), smallBold);
            paragraph.add(phrase1);
            phrase1 = new Phrase(session.getAttribute("floorNo").toString(), smallBold);
            paragraph.add(phrase1);
            phrase1 = new Phrase("th floor in Block/Tower ", blackFont);
            paragraph.add(phrase1);
            phrase1 = new Phrase(blockName, smallBold);
            paragraph.add(phrase1);
            phrase1 = new Phrase(" in \"", blackFont);
            paragraph.add(phrase1);

            phrase1 = new Phrase(" at " + fbsProject.getProjName() + " " + "developed on", blackFont);
            paragraph.add(phrase1);
            phrase1 = new Phrase(" " + fbsProject.getAddress(), smallBold);
            paragraph.add(phrase1);
            phrase1 = new Phrase(". You have booked the referred Unit No. ", blackFont);
            paragraph.add(phrase1);
            phrase1 = new Phrase("" + fbsApplicant.getFlatId(), smallBold);
            paragraph.add(phrase1);
            phrase1 = new Phrase("  under ", blackFont);
            paragraph.add(phrase1);
            phrase1 = new Phrase(" " + fbsPlanname.getPlanName(), smallBold);
            paragraph.add(phrase1);
            phrase1 = new Phrase(" Payment Plan ", blackFont);
            paragraph.add(phrase1);
            phrase1 = new Phrase(" The total cost of the booked Unit is  ", blackFont);
            paragraph.add(phrase1);
            phrase1 = new Phrase(String.valueOf(Total_Cost).toString() + "/- (" + Str + " Only)", smallBold);
            paragraph.add(phrase1);
            phrase1 = new Phrase(".", smallBold);
            paragraph.add(phrase1);

            table3.addCell(c1);
            outerTable.addCell(table3);

            Query serviceTax = entityManager.createNamedQuery("FbsServicetax.findAll");
            List<FbsServicetax> fbsServiceTax = serviceTax.getResultList();
            for (int x = 0; x < fbsServiceTax.size(); x++) {
                Date stDate = fbsServiceTax.get(x).getStDate();
                if (current_date.after(stDate)) {
                    serTax = fbsServiceTax.get(x).getServicetax();
                }

            }
            List<FbsPayplan> fbsPayplanList = entityManager.createNamedQuery("FbsPayplan.findByFkPlanId").setParameter("fkPlanId", fbsBooking.getPlanId()).getResultList();
            Date dueDate11 = new Date();
            for (int i = 0; i < fbsPayplanList.size(); i++) {
                Date dueDate1 = fbsPayplanList.get(i).getDueDate();
                if (dueDate1.before(current_date)) {
                    dueDate11 = dueDate1;
                } else {
                    break;
                }

            }
            amountPayable = 0;
            for (int i = 0; i < fbsPayplanList.size(); i++) {

                if (fbsPayplanList.get(i).getDueDate().before(dueDate11)) {
                    System.out.println("due max" + dueDate11);
                    percentage = fbsPayplanList.get(i).getPercentage();
                    plan_desc = fbsPayplanList.get(i).getPlanDesc().toString().trim();
                    System.out.println(" plan_desc3" + plan_desc);
                    if (plan_desc == null) {
                        plan_desc = " ";
                    }

                    netBasicSaleprice = (netBasicSalePrice1 * percentage) / 100;
                    System.out.println("netBasicSaleprice3" + netBasicSaleprice);
                    totalBasicSalePrice = totalBasicSalePrice + netBasicSaleprice;
                    plc_percentage = (plcCharges * percentage) / 100;
                    System.out.println("plc_percentage3" + plc_percentage);
                    currentInstallment = netBasicSaleprice + plc_percentage;

                    amountPayable = amountPayable + currentInstallment;

                    System.out.println("amountPayable3" + amountPayable);

                    System.out.println("currentInstallment3" + currentInstallment);

                    if (paidAmount > amountPayable) {
                        amountReceived = amountPayable - paidAmount;
                        System.out.println("credit" + amountReceived);
                    } else if (paidAmount < amountPayable) {
                        amountReceived = amountPayable - paidAmount;
                        System.out.println("outs" + amountReceived);
                    }
                    if (amountReceived < 0) {
                        netPayable = 0;
                        serTaxAmount = 0;
                        totalamountPayable = 0;
                        remainingAmount = Total_Cost - (currentInstallment + paidAmount + amountReceived);
                    } else {
                        netPayable = currentInstallment + amountReceived;
                        System.out.println("net" + netPayable);
                        serTaxAmount = (serTax * netPayable) / 100;
                        totalamountPayable = netPayable + serTaxAmount;
                        remainingAmount = Total_Cost - (currentInstallment + paidAmount + amountReceived);
                    }

                }

            }
            System.out.println("credit1" + amountReceived);
            for (int i = 0; i < fbsPayplanList.size(); i++) {
                Date dueDate = fbsPayplanList.get(i).getDueDate();


                if (current_date.after(dueDate)) {
                    percentage = fbsPayplanList.get(i).getPercentage();
                    plan_desc = fbsPayplanList.get(i).getPlanDesc().toString().trim();
                    System.out.println(" plan_desc3" + plan_desc);
                    if (plan_desc == null) {
                        plan_desc = " ";
                    }

                    netBasicSaleprice = (netBasicSalePrice1 * percentage) / 100;
                    System.out.println("netBasicSaleprice3" + netBasicSaleprice);
                    totalBasicSalePrice = totalBasicSalePrice + netBasicSaleprice;
                    plc_percentage = (plcCharges * percentage) / 100;
                    System.out.println("plc_percentage3" + plc_percentage);
                    currentInstallment = netBasicSaleprice + plc_percentage;

                    System.out.println("currentInstallment3" + currentInstallment);


                } else {
                    break;
                }



            }

            System.out.println("amountRcieved" + amountReceived);
            PdfPTable table5 = new PdfPTable(2);
            PdfPCell cell0 = new PdfPCell(new Phrase("DESCRIPTION", smallBold3));
            cell0.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell cell00 = new PdfPCell(new Phrase("Amount(Rs.)", smallBold3));
            cell00.setHorizontalAlignment(Element.ALIGN_LEFT);
            table5.addCell(cell0);
            table5.addCell(cell00);
            PdfPCell cell1 = new PdfPCell(new Phrase("Total Cost(A)", blackFont));
            cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell cell2 = new PdfPCell(new Phrase(String.valueOf(Total_Cost), smallBold));
            cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
            table5.addCell(cell1);
            table5.addCell(cell2);
            PdfPCell cell11 = new PdfPCell(new Phrase("RECEIVED AMOUNT(B)", blackFont));
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell cell22 = new PdfPCell(new Phrase(String.valueOf(paidAmount), smallBold));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table5.addCell(cell11);
            table5.addCell(cell22);
            PdfPCell cell111 = new PdfPCell(new Phrase("CURRENT INSTALLMENT(C)" + plan_desc.toUpperCase() + "", blackFont));
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell cell222 = new PdfPCell(new Phrase(String.valueOf(currentInstallment), smallBold));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table5.addCell(cell111);
            table5.addCell(cell222);


            PdfPCell cell1111 = new PdfPCell(new Phrase("PREVIOUS OUTSTANDING(+)/PREVIOUS CREDIT(-)(D)", blackFont));
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell cell2222 = new PdfPCell(new Phrase(String.valueOf(amountReceived), smallBold));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table5.addCell(cell1111);
            table5.addCell(cell2222);

            PdfPCell cell11111 = new PdfPCell(new Phrase("AMOUNT PAYABLE(C+D)", blackFont));
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell cell22222 = new PdfPCell(new Phrase(String.valueOf(netPayable), smallBold));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table5.addCell(cell11111);
            table5.addCell(cell22222);
            PdfPCell cell111111 = new PdfPCell(new Phrase("Service Tax" + serTax + "%(E)", blackFont));

            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell cell222222 = new PdfPCell(new Phrase(String.valueOf(serTaxAmount), smallBold));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table5.addCell(cell111111);
            table5.addCell(cell222222);
            PdfPCell cell1111111 = new PdfPCell(new Phrase("TOTAL AMOUNT PAYABLE(C+D+E)", blackFont));
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell cell2222222 = new PdfPCell(new Phrase(String.valueOf(totalamountPayable), smallBold));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table5.addCell(cell1111111);
            table5.addCell(cell2222222);
            PdfPCell cell11111111 = new PdfPCell(new Phrase("REMAINING[A-(B+C+D)](Excluding Service Tax)", blackFont));
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell cell22222222 = new PdfPCell(new Phrase(String.valueOf(remainingAmount), smallBold));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table5.addCell(cell11111111);
            table5.addCell(cell22222222);
            outerTable.addCell(table5);
            PdfPTable table6 = new PdfPTable(1);
            paragraph = new Paragraph("");
            c1 = new PdfPCell(paragraph);
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            c1.setBorder(Rectangle.NO_BORDER);
            phrase1 = new Phrase("You are requested to remit amount with in 7 days.", blackFont);
            paragraph.add(phrase1);
            phrase1 = new Phrase("We will communicate the payment schedule of the remaining of Rs.", blackFont);
            paragraph.add(phrase1);
            phrase1 = new Phrase(String.valueOf(remainingAmount), smallBold);
            paragraph.add(phrase1);
            phrase1 = new Phrase(" payable by you in due course.", blackFont);
            paragraph.add(phrase1);
            addEmptyLine(paragraph, 1);
            addEmptyLine(paragraph, 1);
            phrase1 = new Phrase("We assure you of our services at all times ", blackFont);
            paragraph.add(phrase1);
            addEmptyLine(paragraph, 1);
            addEmptyLine(paragraph, 1);
            addEmptyLine(paragraph, 1);
            phrase1 = new Phrase("Thanking you", blackFont);
            paragraph.add(phrase1);
            addEmptyLine(paragraph, 1);
            addEmptyLine(paragraph, 1);
            addEmptyLine(paragraph, 1);

            phrase1 = new Phrase("For  " + fbsCompany.getCompanyName(), smallBold3);
            paragraph.add(phrase1);
            addEmptyLine(paragraph, 1);
            addEmptyLine(paragraph, 1);
            phrase1 = new Phrase("Note:", blackFont);
            paragraph.add(phrase1);
            addEmptyLine(paragraph, 1);
            addEmptyLine(paragraph, 1);
            phrase1 = new Phrase("1.For any delayed in payment interest will be charged 18% p.a..", blackFont);
            paragraph.add(phrase1);
            addEmptyLine(paragraph, 1);
            addEmptyLine(paragraph, 1);
            phrase1 = new Phrase("2.Helpline No." + fbsCompany.getTelNumber() + "/E-mail ID:" + fbsCompany.getEmail(), blackFont);
            paragraph.add(phrase1);
            addEmptyLine(paragraph, 1);
            addEmptyLine(paragraph, 1);
            phrase1 = new Phrase("3.Please ignore this letter in case the payment has already been made.", blackFont);
            paragraph.add(phrase1);
            addEmptyLine(paragraph, 1);
            addEmptyLine(paragraph, 1);
            phrase1 = new Phrase("4.Service Tax is applicable as per Govt.Norms.", blackFont);
            paragraph.add(phrase1);
            addEmptyLine(paragraph, 1);
            addEmptyLine(paragraph, 1);
            phrase1 = new Phrase("5.This is computer generated sheet and does not require any signature.", blackFont);
            paragraph.add(phrase1);
            addEmptyLine(paragraph, 1);
            addEmptyLine(paragraph, 1);

            addEmptyLine(paragraph, 1);
            addEmptyLine(paragraph, 1);
            addEmptyLine(paragraph, 1);
            table6.addCell(c1);
            outerTable.addCell(table6);
            document.add(outerTable);
            document.close();
            // String fileName=fbsApplicant.getApplicantName();

            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");

            response.setContentType("application/pdf");

            baos.writeTo(out);
            out.flush();


        } finally {
            out.close();
        }
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,
            IOException {
        try {
            processRequest(request, response);
        } catch (DocumentException ex) {
            Logger.getLogger(Letter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (com.itextpdf.text.DocumentException ex) {
            Logger.getLogger(Letter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addEmptyLine(Paragraph preface, int number) {
        for (int i = 0; i < number; i++) {
            preface.add(new Paragraph(" "));



        }
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
        try {
            processRequest(request, response);
        } catch (DocumentException ex) {
            Logger.getLogger(Letter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (com.itextpdf.text.DocumentException ex) {
            Logger.getLogger(Letter.class.getName()).log(Level.SEVERE, null, ex);
        }
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
