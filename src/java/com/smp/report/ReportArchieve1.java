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
import com.smp.entity.FbsCompany;
import com.smp.entity.FbsPayplan;
import com.smp.entity.FbsProject;
import com.smp.entity.FbsReport;
import com.smp.entity.FbsServicetax;
import com.smp.session.FbsApplicantFacade;
import com.smp.session.FbsCompanyFacade;
import com.smp.session.FbsPayplanFacade;
import com.smp.session.FbsProjectFacade;
import com.smp.session.FbsReportFacade;
import com.smp.session.FbsServicetaxFacade;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
@WebServlet(name = "ReportArchieve1", urlPatterns = {"/ReportArchieve1"})
public class ReportArchieve1 extends HttpServlet {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
    @EJB
    FbsReportFacade fbsReportFacade;
    @EJB
    FbsApplicantFacade fbsApplicantFacade;
    @EJB
    FbsCompanyFacade fbsCompanyFacade;
    @EJB
    FbsProjectFacade fbsprojectfacade;
    @EJB
    FbsPayplanFacade fbsPayplanFacade;
    @EJB
    FbsServicetaxFacade fbsServicetaxFacade;
    FbsServicetax fbsServicetax = new FbsServicetax();
    FbsPayplan fbsPayplan = new FbsPayplan();
    FbsProject fbsproject=new FbsProject();
    FbsCompany fbsCompany=new FbsCompany();
    FbsReport fbsReport=new FbsReport();
    List<FbsApplicant> fbsApplicantList = new ArrayList<FbsApplicant>();
    List<FbsPayplan> fbsPayplanList = new ArrayList<FbsPayplan>();
    List<FbsServicetax> fbsServicetaxList = new ArrayList<FbsServicetax>();
    private FbsBooking fbsBooking=new FbsBooking();
    private FbsApplicant fbsApplicant=new FbsApplicant();
    String ApplicantName;
    String coApplicantName;

    public ReportArchieve1() {
        fbsReport = new FbsReport();
        fbsCompany = new FbsCompany();
        fbsproject = new FbsProject();


    }
    private static Font catFont = new Font(Font.TIMES_ROMAN, 18, Font.BOLD);
    private static Font redFont = new Font(Font.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
    private static Font blackFont = new Font(Font.TIMES_ROMAN, 8, Font.NORMAL, BaseColor.BLACK);
    private static Font subFont = new Font(Font.TIMES_ROMAN, 16, Font.BOLD);
    private static Font smallBold = new Font(Font.TIMES_ROMAN, 8, Font.BOLD);
    private static Font blackSmallFont = new Font(Font.TIMES_ROMAN, 6, Font.NORMAL, BaseColor.BLACK);
    Font smallBold1 = new Font(Font.TIMES_ROMAN, 14, Font.BOLD);
    Font smallBold2 = new Font(Font.TIMES_ROMAN, 12, Font.BOLD);
    Font smallBold3 = new Font(Font.TIMES_ROMAN, 11, Font.BOLD);
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, DocumentException {


        HttpSession session = request.getSession(false);
        response.setContentType("text/html;charset=UTF-8");
        //   PrintWriter out = response.getWriter();

        ServletOutputStream out = response.getOutputStream();
        

        List<FbsReport> FbsReportList = new ArrayList<FbsReport>();
        FbsReportList = fbsReportFacade.findAll();
      
//            Integer regNo = FbsReportList.get(i).getRegNumber();
//            System.out.println("regNo"+regNo);
//            String reportId=request.getParameter("reportId");

        //    fbsBooking = (FbsBooking) em.createNamedQuery("FbsBooking.findByRegNumber").setParameter("regNumber", regNo).getResultList().get(0);
          //  Integer flatId = fbsBooking.getFlatId();
            String flatId=(String)session.getAttribute("flatId");
              System.out.println("in servlet....." + flatId);
            List<FbsApplicant> applicants = new ArrayList<FbsApplicant>();
            applicants = em.createNamedQuery("FbsApplicant.findByFlatId").setParameter("flatId", Integer.parseInt(flatId)).getResultList();
            for (int j = 0; j < applicants.size(); j++) {
//                fbsApplicantList.add(applicants.get(j));
                if (applicants.get(j).getApplicantFlag() == 1) {
                    ApplicantName = applicants.get(j).getApplicantName();
                    fbsApplicant = fbsApplicantFacade.find(applicants.get(j).getApplicantId());
                } else {
                    coApplicantName = applicants.get(j).getApplicantName();
                }
            }

        

        String floorNo = (String) session.getAttribute("floorNo");
        System.out.println("in servlet....." + floorNo);

        String blockName = (String) session.getAttribute("blockName");
        System.out.println("in servlet....." + blockName);
        String companyid = request.getParameter("companyId");
        Integer companyId = Integer.parseInt(companyid);
        fbsCompany = fbsCompanyFacade.find(companyId);
        fbsproject = (FbsProject) em.createNamedQuery("FbsProject.findByCompanyId").setParameter("companyId", companyId).getResultList().get(0);

        List<FbsReport> fbsReportList = new ArrayList<FbsReport>();
        fbsReportList = fbsReportFacade.findAll();
        fbsPayplanList = fbsPayplanFacade.findAll();
        fbsServicetaxList = fbsServicetaxFacade.findAll();
        int reportId = 0;
        if (request.getParameter("reportId").equals("null")) {
            System.out.println("inside if");
            fbsReportList.clear();
            fbsReportList = fbsReportFacade.findAll();
            //report with botton
        } else {
            System.out.println("inside else");
            reportId = Integer.parseInt(request.getParameter("reportId"));
            fbsReport = (FbsReport) em.createNamedQuery("FbsReport.findById").setParameter("id", reportId).getResultList().get(0);
            fbsBooking = (FbsBooking) em.createNamedQuery("FbsBooking.findByRegNumber").setParameter("regNumber", fbsReport.getRegNumber()).getResultList().get(0);

        }

        try {

            Document document = new Document(PageSize.A4, 0, 0, 60, 30);
            Font normafont = new Font(Font.TIMES_ROMAN, 10, Font.NORMAL);
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PdfWriter writer = PdfWriter.getInstance(document, baos);
                document.open();
                // document.addTitle("Demand Letter against" + flatId.toString());
                document.addSubject("Using iText");
                document.addKeywords("Java, PDF, iText");

                PdfPTable outerTable = new PdfPTable(1);
                Paragraph companyDetail = new Paragraph(fbsCompany.getCompanyName(), smallBold1);
                companyDetail.add(new Paragraph("\n" + fbsCompany.getAddress(), smallBold));
                companyDetail.add(new Chunk("Phone: ", smallBold));
                companyDetail.add(new Chunk(fbsCompany.getTelNumber(), smallBold));
                companyDetail.add(new Chunk(" \nEmail: ", smallBold));
                companyDetail.add(new Chunk(fbsCompany.getEmail(), smallBold));
                companyDetail.add(new Chunk(" \nWebsite: ", smallBold));
                Paragraph paragraph1 = new Paragraph("");
                PdfPCell c11 = new PdfPCell(paragraph1);
                Chunk c1 = new Chunk(fbsCompany.getWebsite(), smallBold);
                c1.setAnchor(fbsCompany.getWebsite());
                companyDetail.add(c1);
                companyDetail.setAlignment(Element.ALIGN_CENTER);
                PdfPCell company = new PdfPCell(companyDetail);
                company.setHorizontalAlignment(Element.ALIGN_CENTER);
                outerTable.addCell(company);

                String string = "DEMAND LETTER";
                Chunk c = new Chunk(string, smallBold2);

                Paragraph paragraph = new Paragraph(c);

                Chunk registrationNumber = new Chunk("Registration No. " + fbsBooking.getRegNumber().toString(), smallBold);

                Chunk chunk = new Chunk(" Dated: " + dateFormat.format(new Date()), smallBold);
                Paragraph pagedate = new Paragraph(registrationNumber);
                // pagedate.setAlignment(Element.ALIGN_LEFT);


                PdfPCell Title = new PdfPCell(paragraph);
                Title.setHorizontalAlignment(Element.ALIGN_CENTER);//paragraph.setAlignment(Element.ALIGN_CENTER);
                outerTable.addCell(Title);//document.add(paragraph);
                PdfPCell registNo = new PdfPCell(new Paragraph(registrationNumber));
                registNo.setHorizontalAlignment(Element.ALIGN_LEFT);
                pagedate = new Paragraph(chunk);
                PdfPTable table3 = new PdfPTable(2);
                table3.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                PdfPCell dat = new PdfPCell(pagedate);
                dat.setBorder(Rectangle.NO_BORDER);
                dat.setHorizontalAlignment(Element.ALIGN_RIGHT);
                registNo.setBorder(Rectangle.NO_BORDER);
                //registNo.addElement(dat);
                table3.addCell(registNo);
                table3.addCell(dat);

                outerTable.addCell(table3);
                //pagedate.setAlignment(Paragraph.ALIGN_RIGHT);


                //   outerTable.addCell(pagedate);//document.add(pagedate);



                Paragraph to = new Paragraph();
                Phrase phrase1 = new Phrase();



//            for (int p = 0; p < fbsApplicantList.size(); p++) {
//                phrase1.add(fbsApplicantList.get(p).getApplicantName());
//                if (p != fbsApplicantList.size() - 1) {
//                    phrase1.add(new Chunk(" & "));
//                }
//            }

                PdfPCell applicant = new PdfPCell(to);


                applicant.addElement(to);

                to.add(phrase1);


                phrase1 = new Phrase("To,\n   ", smallBold);
                to.add(phrase1);


//                 phrase1 = new Phrase("\n   ", smallBold);
//                to.add(phrase1);

                phrase1 = new Phrase("\n   " + ApplicantName + "&" + coApplicantName, blackFont);
                to.add(phrase1);

                //outerTable.addCell(phrase1);
                phrase1 = new Phrase("\n   " + fbsApplicant.getResAdd(), blackFont);
                to.add(phrase1);

                //applicant.addElement(phrase1);
                phrase1 = new Phrase("\n   Mobile-" + fbsApplicant.getMobile(), blackFont);
                to.add(phrase1);
                //applicant.addElement(phrase1);
                phrase1 = new Phrase("\n   " + fbsApplicant.getEmail(), blackFont);
                to.add(phrase1);
                //applicant.addElement(phrase1);
                //to.add(phrase1);

                phrase1 = new Phrase("\n\nSubject:", smallBold);
                applicant = new PdfPCell(to);
                applicant.setHorizontalAlignment(Element.ALIGN_LEFT);
                applicant.setBorder(Rectangle.NO_BORDER);
                //to = new Paragraph();
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
                phrase1 = new Phrase("You have been alloted a  ", blackFont);
                to.add(phrase1);
                phrase1 = new Phrase("Unit No. " + fbsBooking.getFlatId().toString(), smallBold);
                to.add(phrase1);
                phrase1 = new Phrase(" on ", blackFont);
                to.add(phrase1);
                phrase1 = new Phrase(floorNo, smallBold);//session.getAttribute("floorNo").toString()
                to.add(phrase1);
                phrase1 = new Phrase("th floor in Block/Tower ", blackFont);
                to.add(phrase1);
                phrase1 = new Phrase(blockName, smallBold);
                to.add(phrase1);
                phrase1 = new Phrase(" in \"", blackFont);
                to.add(phrase1);
                phrase1 = new Phrase(fbsproject.getProjName(), smallBold);
                to.add(phrase1);
                phrase1 = new Phrase("\". Group Housing Residential Complex being developed ", blackFont);
                to.add(phrase1);
                phrase1 = new Phrase("on ", blackFont);
                to.add(phrase1);
                phrase1 = new Phrase(fbsproject.getAddress(), smallBold);
                to.add(phrase1);
                phrase1 = new Phrase(". You have booked the reffered unit in \"", blackFont);
                to.add(phrase1);
                phrase1 = new Phrase(fbsproject.getProjName(), smallBold);
                to.add(phrase1);
                phrase1 = new Phrase("\" Group Housing Project under Flexi Payement Plan.\n\n", blackFont);
                to.add(phrase1);

//                phrase1 = new Phrase("The basic cost of the booked Unit is Rs. ", blackFont);
//                to.add(phrase1);
//                phrase1 = new Phrase("/- (", blackFont);
//                to.add(phrase1);
//                phrase1 = new Phrase(" Only).", blackFont);
//                to.add(phrase1);

                outerTable.addCell(to);
                 //reporttable.setSpacingBefore(20f);


                float[] floats = {5f, 2f};
                PdfPTable reporttable = new PdfPTable(floats);
                //reporttable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                // paragraph = new Paragraph();


                Paragraph pp = new Paragraph("Description", smallBold);
                PdfPCell first = new PdfPCell(pp);
                Paragraph p = new Paragraph("Amount(Rs.)", smallBold);
                PdfPCell am = new PdfPCell(p);
                first.setHorizontalAlignment(Element.ALIGN_CENTER);
                reporttable.addCell(first);
                am.setHorizontalAlignment(Element.ALIGN_LEFT);
                reporttable.addCell(am);

                Paragraph total = new Paragraph("TOTAL COST(A)", blackFont);
                PdfPCell cell1 = new PdfPCell(new Paragraph(String.valueOf(fbsReport.getTotalCost()).toString(), smallBold));
                reporttable.addCell(total);
                reporttable.addCell(cell1);

                Paragraph recieved = new Paragraph("RECEIVED AMOUNT(B)", blackFont);
                PdfPCell cell2 = new PdfPCell(new Paragraph(fbsReport.getRecievedAmt().toString(), smallBold));
                reporttable.addCell(recieved);
                reporttable.addCell(cell2);

                Paragraph currentinstallment = new Paragraph("CURRENT INSTALLMENT(C)" + fbsPayplanList.get(0).getPlanDesc().toUpperCase(), blackFont);
                PdfPCell cell3 = new PdfPCell(new Paragraph(fbsReport.getCurInstallment().toString(), smallBold));
                reporttable.addCell(currentinstallment);
                reporttable.addCell(cell3);

                Paragraph outcredit = new Paragraph("PREVIOUS OUTSTANDING(+)/PREVIOUS CREDIT(-)(D) ", blackFont);
                PdfPCell cell4 = new PdfPCell(new Paragraph(fbsReport.getOutCredit().toString(), smallBold));
                reporttable.addCell(outcredit);
                reporttable.addCell(cell4);
               float amt1=fbsReport.getCurInstallment()+fbsReport.getOutCredit();
                Paragraph amt = new Paragraph("AMOUNT PAYABLE(C+D)", blackFont);
                PdfPCell cell5 = new PdfPCell(new Paragraph(String.valueOf(amt1), smallBold));
                reporttable.addCell(amt);
                reporttable.addCell(cell5);

                Paragraph service = new Paragraph("SERVICE TAX(" + fbsServicetaxList.get(0).getServicetax() + "%)(E)", blackFont);
                PdfPCell cell6 = new PdfPCell(new Paragraph(String.valueOf(fbsReport.getServiceTax()).toString(), smallBold));
                reporttable.addCell(service);
                reporttable.addCell(cell6);

                 Paragraph total1 = new Paragraph("TOTAL AMOUNT PAYABALE(C+D+E)", blackFont);
                PdfPCell cell7 = new PdfPCell(new Paragraph(String.valueOf(fbsReport.getAmountPayable()).toString(), smallBold));
                reporttable.addCell(total1);
                reporttable.addCell(cell7);



                Paragraph remaining = new Paragraph("REMAINING AMOUNT [A-(B+C+D)](Excluding Service Tax)", blackFont);
                PdfPCell cell8 = new PdfPCell(new Paragraph(String.valueOf(fbsReport.getRemainingAmt()).toString(), smallBold));
                reporttable.addCell(remaining);
                reporttable.addCell(cell8);

                reporttable.setSpacingBefore(20f);
                reporttable.setSpacingAfter(20f);
                reporttable.addCell(c11);
                outerTable.addCell(reporttable);


                to = new Paragraph();
                phrase1 = new Phrase("It is requested to remit the amount with in 7 days. We communicate the payment schedule of the amount of Rs. ", blackFont);
                to.add(phrase1);
                phrase1 = new Phrase(fbsReport.getRemainingAmt().toString(), smallBold);
                to.add(phrase1);

                phrase1 = new Phrase("/- payable by you in due course.\n\n We assure you of our best services at all times.\n Thanking you\n\n", blackFont);
                to.add(phrase1);
                outerTable.addCell(to);

                phrase1 = new Phrase("For " + fbsCompany.getCompanyName(), smallBold);
                to.add(phrase1);
                to.setSpacingAfter(60);

                //   fbsReportFacade.create(fbsReport);
                phrase1 = new Phrase(("\n\nNOTE:\n"), blackFont);
                ZapfDingbatsList zapfDingbatsList = new ZapfDingbatsList(42, 15);
                to.add(phrase1);
                zapfDingbatsList.add(new ListItem("1. For any delayed in payment interest will be charged 18% p.a.\n", blackFont));
                zapfDingbatsList.add(new ListItem("2. Helpline No." + fbsCompany.getTelNumber() + "/Email ID:" + fbsCompany.getEmail() + ".\n", blackFont));
                zapfDingbatsList.add(new ListItem("3. Please ignore this letter in case the payment has already been made.\n", blackFont));
                zapfDingbatsList.add(new ListItem("4. Service Tax is applicable as per Govt. Norms.\n", blackFont));
                zapfDingbatsList.add(new ListItem("5. This is computer generated sheet and does not require any signature.\n", blackFont));
                to.add(zapfDingbatsList);

                document.add(outerTable);



                document.close();
                response.setHeader("Expires", "0");
                response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
                response.setHeader("Pragma", "public");
                response.setContentType("application/pdf");

                baos.writeTo(out);
                out.flush();

            } catch (DocumentException d) {
                System.out.println("error with the pdf writer" + d);
                response.sendRedirect("/FbsFaces/faces/jsfpages/common/errorPage.xhtml");
            }
        } finally {
            out.close();
        }
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
        try {
            processRequest(request, response);



        } catch (DocumentException ex) {
            Logger.getLogger(ReportArchieve1.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ReportArchieve1.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i
                < number; i++) {
            paragraph.add(new Paragraph(" "));


        }

    }

    public String getApplicantName() {
        return ApplicantName;
    }

    public void setApplicantName(String ApplicantName) {
        this.ApplicantName = ApplicantName;
    }

    public FbsApplicant getFbsApplicant() {
        return fbsApplicant;
    }

    public void setFbsApplicant(FbsApplicant fbsApplicant) {
        this.fbsApplicant = fbsApplicant;
    }
}
