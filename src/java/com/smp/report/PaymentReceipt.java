/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.report;

import com.itextpdf.text.BaseColor;
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
import com.smp.entity.FbsBlock;
import com.smp.entity.FbsBooking;
import com.smp.entity.FbsCompany;
import com.smp.entity.FbsFlatType;
import com.smp.entity.FbsPayment;
import com.smp.entity.FbsProject;
import com.smp.entity.FbsServicetax;
import com.smp.session.FbsApplicantFacade;
import com.smp.session.FbsBlockFacade;
import com.smp.session.FbsBookingFacade;
import com.smp.session.FbsCompanyFacade;
import com.smp.session.FbsFlatTypeFacade;
import com.smp.session.FbsPaymentFacade;
import com.smp.session.FbsProjectFacade;
import com.smp.session.FbsServicetaxFacade;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

/**
 *
 * @author smp7
 */
@WebServlet(name = "PaymentReceipt1", urlPatterns = {"/PaymentReceipt1"})
public class PaymentReceipt extends HttpServlet {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager entityManager;
    @EJB
    FbsCompanyFacade fbsCompanyFacade;
    @EJB
    FbsPaymentFacade fbsPaymentFacade;
    @EJB
    FbsBookingFacade fbsBookingFacade;
    @EJB
    FbsApplicantFacade fbsApplicantFacade;
    @EJB
    FbsBlockFacade fbsBlockFacade;
    @EJB
    FbsProjectFacade fbsProjectFacade;
    @EJB
    FbsFlatTypeFacade fbsFlatTypeFacade;
    @EJB
    FbsServicetaxFacade fbsServicetaxFacade;
    FbsApplicant fbsApplicant;
    FbsCompany fbsCompany;
    FbsBooking fbsBooking;
    FbsPayment fbsPayment;
    FbsBlock fbsBlock;
    FbsProject fbsProject;
    FbsFlatType fbsFlatType;
    FbsServicetax fbsServicetax;
    List<FbsServicetax> fbsServicetaxList;
    String[] unitdo = {"", " One", " Two", " Three", " Four", " Five",
        " Six", " Seven", " Eight", " Nine", " Ten", " Eleven", " Twelve",
        " Thirteen", " Fourteen", " Fifteen", " Sixteen", " Seventeen",
        " Eighteen", " Nineteen"};
    String[] tens = {"", "Ten", " Twenty", " Thirty", " Forty", " Fifty",
        " Sixty", " Seventy", " Eighty", " Ninety"};
    String[] digit = {"", " Hundred", " Thousand", " Lakh", " Crore"};
    int r;

    public PaymentReceipt() {
        fbsApplicant = new FbsApplicant();
        fbsCompany = new FbsCompany();
        fbsBooking = new FbsBooking();
        fbsPayment = new FbsPayment();
        fbsBlock = new FbsBlock();
        fbsProject = new FbsProject();
        fbsFlatType = new FbsFlatType();
        fbsServicetax = new FbsServicetax();
        fbsServicetaxList = new ArrayList<FbsServicetax>();
    }
    private static Font catFont = new Font(Font.TIMES_ROMAN, 18, Font.BOLD);
    private static Font redFont = new Font(Font.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
    private static Font blackFont = new Font(Font.TIMES_ROMAN, 8, Font.NORMAL, BaseColor.BLACK);
    private static Font subFont = new Font(Font.TIMES_ROMAN, 16, Font.BOLD);
    private static Font smallBold = new Font(Font.TIMES_ROMAN, 8, Font.BOLD);
    private static Font blackSmallFont = new Font(Font.TIMES_ROMAN, 6, Font.NORMAL, BaseColor.BLACK);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, DocumentException {
        response.setContentType("text/html;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();

        String paymentId = request.getParameter("paymentId");
        String unitCode = request.getParameter("unitCode");
        String companyId = request.getParameter("companyId");
        fbsPayment = fbsPaymentFacade.find(Integer.parseInt(paymentId));
        fbsCompany = fbsCompanyFacade.find(Integer.parseInt(companyId));
        fbsBooking = (FbsBooking) entityManager.createNamedQuery("FbsBooking.findByFlatId").setParameter("flatId", Integer.parseInt(unitCode)).getResultList().get(0);
        fbsApplicant = (FbsApplicant) entityManager.createNamedQuery("FbsApplicant.findByFlatId").setParameter("flatId", Integer.parseInt(unitCode)).getResultList().get(0);
        fbsBlock = fbsBlockFacade.find(fbsBooking.getBlockId());
        fbsProject = fbsProjectFacade.find(Integer.valueOf(String.valueOf(fbsBlock.getFkProjId())));
        fbsFlatType = (FbsFlatType) entityManager.createNamedQuery("FbsFlatType.findByFkProjId").setParameter("fkProjId", fbsProject.getProjId()).getResultList().get(0);
        fbsServicetaxList = fbsServicetaxFacade.findAll();

        for (int i = 0; i < fbsServicetaxList.size(); i++) {
            if ((fbsPayment.getPaymentDate().after(fbsServicetaxList.get(i).getStDate()))&&fbsPayment.getPaymentDate().before(fbsServicetaxList.get(i).getEndDate())) {
                fbsServicetax = fbsServicetaxList.get(i);
                break;
            }
        }
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        long serviceTax = 0;//(int) (fbsPayment.getPaidAmount() * fbsServicetax.getServicetax() / 100);
        long temp=Math.round(fbsPayment.getPaidAmount()/(1+(double)fbsServicetax.getServicetax()/100));
        long netAmount =  temp;
        serviceTax=fbsPayment.getPaidAmount()-temp;
        /******************************************************/
        int len, q = 0, r = 0;
        String ltr = " ";
        String Str = "Rupees";

        int num = Integer.parseInt(fbsPayment.getPaidAmount().toString());

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


        /********************************************************/
        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4, 0, 0, 60, 30);
            PdfWriter docWriter = PdfWriter.getInstance(document, baos);
            document.open();
            document.addTitle("Payment Receipt");
            document.addSubject("Using iText");
            document.addKeywords("Java, PDF, iText");
            document.addAuthor("Peeyush Kumar");
            document.addCreator("Peeyush Kumar");


            Paragraph preface = new Paragraph();
            // Lets write a small header
            preface.add(new Paragraph("", blackFont));
            // We add one empty line
            addEmptyLine(preface, 1);
            document.add(preface);
            // create the outermost table for the receipt
            PdfPTable outerTable = new PdfPTable(1);
            //first table in the outside table for the company details
            PdfPTable table1 = new PdfPTable(1);
            table1.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            Phrase phrase1 = new Phrase(fbsCompany.getCompanyName(), smallBold);
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
            PdfPCell c1 = new PdfPCell(paragraph);
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBorder(Rectangle.NO_BORDER);
            table1.addCell(c1);
            outerTable.addCell(table1);

            // second inner table for the receipt
            PdfPTable table2 = new PdfPTable(1);
            c1 = new PdfPCell(new Paragraph("RECEIPT", smallBold));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);

            c1.setBorder(Rectangle.NO_BORDER);
            table2.addCell(c1);
            outerTable.addCell(table2);

            // third table for the payment details
            PdfPTable table3 = new PdfPTable(2);
            table3.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

            paragraph = new Paragraph("Receipt No: " + fbsPayment.getPaymentId(), smallBold);
            addEmptyLine(paragraph, 1);
            phrase1 = new Phrase("Customer Id: " + fbsBooking.getRegNumber(), smallBold);

            paragraph.add(phrase1);

            c1 = new PdfPCell(paragraph);
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);

            c1.setBorder(Rectangle.NO_BORDER);
            table3.addCell(c1);

            paragraph = new Paragraph("Date: " + dateFormat.format(new Date()), smallBold);
            c1 = new PdfPCell(paragraph);
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);

            c1.setBorder(Rectangle.NO_BORDER);
            table3.addCell(c1);
            outerTable.addCell(table3);

            PdfPTable table4 = new PdfPTable(1);
            table4.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            paragraph = new Paragraph();
            addEmptyLine(paragraph, 1);
            phrase1 = new Phrase("Received with thanks from ", blackFont);
            paragraph.add(phrase1);
            phrase1 = new Phrase("Mr. " + fbsApplicant.getApplicantName(), smallBold);
            paragraph.add(phrase1);
            phrase1 = new Phrase(" S/W/D/o " + fbsApplicant.getSWD() + " residing at " + fbsApplicant.getResAdd() + " a sum of Rs. ", blackFont);
            paragraph.add(phrase1);
            phrase1 = new Phrase(fbsPayment.getPaidAmount().toString() + "/- (" + Str + " Only)", smallBold);
            paragraph.add(phrase1);
            phrase1 = new Phrase(" on account of ", blackFont);
            paragraph.add(phrase1);
            phrase1 = new Phrase("Ist INSTL ", smallBold);
            paragraph.add(phrase1);
            phrase1 = new Phrase("against Unit No. ", blackFont);
            paragraph.add(phrase1);
            phrase1 = new Phrase(fbsPayment.getUnitCode() + " at Tower " + fbsBlock.getBlockName(), smallBold);
            paragraph.add(phrase1);
            phrase1 = new Phrase(" measuring ", blackFont);
            paragraph.add(phrase1);
            phrase1 = new Phrase(fbsFlatType.getFlatSba() + " SQ FT Super Area ", smallBold);
            paragraph.add(phrase1);
            phrase1 = new Phrase("(Approximately) in ", blackFont);
            paragraph.add(phrase1);
            phrase1 = new Phrase(fbsProject.getProjName() + ", " + fbsProject.getAddress() + ", " + fbsProject.getCity() + ", " + fbsProject.getState(), smallBold);
            paragraph.add(phrase1);
            phrase1 = new Phrase(" as per following details: ", blackFont);
            paragraph.add(phrase1);
            addEmptyLine(paragraph, 2);
            c1 = new PdfPCell(paragraph);
            c1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);

            c1.setBorder(Rectangle.NO_BORDER);
            table4.addCell(c1);
            if (fbsPayment.getPaymentMode().equals("Cheque")) {
                PdfPTable chequePayDetailTable = new PdfPTable(7);
                PdfPCell cell1 = new PdfPCell(new Phrase("S.NO.", smallBold));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell2 = new PdfPCell(new Phrase("Cheque No.", smallBold));
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell3 = new PdfPCell(new Phrase("Drawn On", smallBold));
                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell4 = new PdfPCell(new Phrase("Dated", smallBold));
                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                paragraph = new Paragraph();
                paragraph.add(new Phrase("Amount", smallBold));
                addEmptyLine(paragraph, 1);
                paragraph.add(new Phrase("(Rs.)", smallBold));
                PdfPCell cell5 = new PdfPCell(paragraph);
                cell5.setHorizontalAlignment(Element.ALIGN_CENTER);

                paragraph = new Paragraph();
                paragraph.add(new Phrase("Service Tax", smallBold));
                addEmptyLine(paragraph, 1);
                paragraph.add(new Phrase("(Rs.)", smallBold));
                PdfPCell cell6 = new PdfPCell(paragraph);
                cell6.setHorizontalAlignment(Element.ALIGN_CENTER);

                paragraph = new Paragraph();
                paragraph.add(new Phrase("Net Amount", smallBold));
                addEmptyLine(paragraph, 1);
                paragraph.add(new Phrase("(Rs.)", smallBold));
                PdfPCell cell7 = new PdfPCell(paragraph);
                cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
                chequePayDetailTable.addCell(cell1);
                chequePayDetailTable.addCell(cell2);
                chequePayDetailTable.addCell(cell3);
                chequePayDetailTable.addCell(cell4);
                chequePayDetailTable.addCell(cell5);
                chequePayDetailTable.addCell(cell6);
                chequePayDetailTable.addCell(cell7);
                PdfPCell cell11 = new PdfPCell(new Phrase("1", smallBold));
                cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell22 = new PdfPCell(new Phrase(fbsPayment.getChequeNo(), blackFont));
                cell22.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell33 = new PdfPCell(new Phrase(fbsPayment.getDrawnOn(), blackFont));
                cell33.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell44 = new PdfPCell(new Phrase(dateFormat.format(fbsPayment.getPaymentDate()), blackFont));
                cell44.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell55 = new PdfPCell(new Phrase(fbsPayment.getPaidAmount().toString() + "/-", blackFont));
                cell55.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell66 = new PdfPCell(new Phrase(String.valueOf(serviceTax) + "/-", blackFont));
                cell66.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell77 = new PdfPCell(new Phrase(String.valueOf(netAmount) + "/-", blackFont));
                cell77.setHorizontalAlignment(Element.ALIGN_CENTER);
                chequePayDetailTable.addCell(cell11);
                chequePayDetailTable.addCell(cell22);
                chequePayDetailTable.addCell(cell33);
                chequePayDetailTable.addCell(cell44);
                chequePayDetailTable.addCell(cell55);
                chequePayDetailTable.addCell(cell66);
                chequePayDetailTable.addCell(cell77);
                PdfPCell cell111 = new PdfPCell();
                cell111.addElement(new Phrase("", smallBold));
                PdfPCell cell222 = new PdfPCell();
                cell222.addElement(new Phrase("", blackFont));
                PdfPCell cell333 = new PdfPCell();
                cell333.addElement(new Phrase("", blackFont));
                PdfPCell cell444 = new PdfPCell(new Phrase("Total", smallBold));
                cell444.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell555 = new PdfPCell(new Phrase(fbsPayment.getPaidAmount().toString() + "/-", smallBold));
                cell555.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell666 = new PdfPCell(new Phrase(String.valueOf(serviceTax) + "/-", smallBold));
                cell666.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell777 = new PdfPCell(new Phrase(String.valueOf(netAmount) + "/-", smallBold));
                cell777.setHorizontalAlignment(Element.ALIGN_CENTER);
                chequePayDetailTable.addCell(cell111);
                chequePayDetailTable.addCell(cell222);
                chequePayDetailTable.addCell(cell333);
                chequePayDetailTable.addCell(cell444);
                chequePayDetailTable.addCell(cell555);
                chequePayDetailTable.addCell(cell666);
                chequePayDetailTable.addCell(cell777);
                table4.addCell(chequePayDetailTable);
            } else if (fbsPayment.getPaymentMode().equals("Cash")) {
                PdfPTable cashPayDetailTable = new PdfPTable(5);
                PdfPCell cell1 = new PdfPCell(new Phrase("S.NO.", smallBold));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell4 = new PdfPCell(new Phrase("Dated", smallBold));
                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);

                paragraph = new Paragraph();
                paragraph.add(new Phrase("Amount", smallBold));
                addEmptyLine(paragraph, 1);
                paragraph.add(new Phrase("(Rs.)", smallBold));
                PdfPCell cell5 = new PdfPCell(paragraph);
                cell5.setHorizontalAlignment(Element.ALIGN_CENTER);

                paragraph = new Paragraph();
                paragraph.add(new Phrase("Service Tax", smallBold));
                addEmptyLine(paragraph, 1);
                paragraph.add(new Phrase("(Rs.)", smallBold));
                PdfPCell cell6 = new PdfPCell(paragraph);
                cell6.setHorizontalAlignment(Element.ALIGN_CENTER);

                paragraph = new Paragraph();
                paragraph.add(new Phrase("Net Amount", smallBold));
                addEmptyLine(paragraph, 1);
                paragraph.add(new Phrase("(Rs.)", smallBold));
                PdfPCell cell7 = new PdfPCell(paragraph);
                cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
                cashPayDetailTable.addCell(cell1);
                cashPayDetailTable.addCell(cell4);
                cashPayDetailTable.addCell(cell5);
                cashPayDetailTable.addCell(cell6);
                cashPayDetailTable.addCell(cell7);
                PdfPCell cell11 = new PdfPCell(new Phrase("1", smallBold));
                cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell44 = new PdfPCell(new Phrase(dateFormat.format(fbsPayment.getPaymentDate()), blackFont));
                cell44.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell55 = new PdfPCell(new Phrase(fbsPayment.getPaidAmount().toString() + "/-", blackFont));
                cell55.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell66 = new PdfPCell(new Phrase(String.valueOf(serviceTax) + "/-", blackFont));
                cell66.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell77 = new PdfPCell(new Phrase(String.valueOf(netAmount) + "/-", blackFont));
                cell77.setHorizontalAlignment(Element.ALIGN_CENTER);
                cashPayDetailTable.addCell(cell11);
                cashPayDetailTable.addCell(cell44);
                cashPayDetailTable.addCell(cell55);
                cashPayDetailTable.addCell(cell66);
                cashPayDetailTable.addCell(cell77);
                PdfPCell cell111 = new PdfPCell(new Phrase("", smallBold));
                cell111.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell444 = new PdfPCell(new Phrase("Total", smallBold));
                cell444.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell555 = new PdfPCell(new Phrase(fbsPayment.getPaidAmount().toString() + "/-", smallBold));
                cell555.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell666 = new PdfPCell(new Phrase(String.valueOf(serviceTax) + "/-", smallBold));
                cell666.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell777 = new PdfPCell(new Phrase(String.valueOf(netAmount) + "/-", smallBold));
                cell777.setHorizontalAlignment(Element.ALIGN_CENTER);
                cashPayDetailTable.addCell(cell111);
                cashPayDetailTable.addCell(cell444);
                cashPayDetailTable.addCell(cell555);
                cashPayDetailTable.addCell(cell666);
                cashPayDetailTable.addCell(cell777);
                table4.addCell(cashPayDetailTable);

            } else if (fbsPayment.getPaymentMode().equals("NEFT")) {
                PdfPTable chequePayDetailTable = new PdfPTable(7);
                PdfPCell cell1 = new PdfPCell(new Phrase("S.NO.", smallBold));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setVerticalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell2 = new PdfPCell(new Phrase("Transaction Id", smallBold));
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell3 = new PdfPCell(new Phrase("Bank Name", smallBold));
                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell4 = new PdfPCell(new Phrase("Dated", smallBold));
                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);

                paragraph = new Paragraph();
                paragraph.add(new Phrase("Amount", smallBold));
                addEmptyLine(paragraph, 1);
                paragraph.add(new Phrase("(Rs.)", smallBold));
                PdfPCell cell5 = new PdfPCell(paragraph);
                cell5.setHorizontalAlignment(Element.ALIGN_CENTER);

                paragraph = new Paragraph();
                paragraph.add(new Phrase("Service Tax", smallBold));
                addEmptyLine(paragraph, 1);
                paragraph.add(new Phrase("(Rs.)", smallBold));
                PdfPCell cell6 = new PdfPCell(paragraph);
                cell6.setHorizontalAlignment(Element.ALIGN_CENTER);

                paragraph = new Paragraph();
                paragraph.add(new Phrase("Net Amount", smallBold));
                addEmptyLine(paragraph, 1);
                paragraph.add(new Phrase("(Rs.)", smallBold));
                PdfPCell cell7 = new PdfPCell(paragraph);
                cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
                chequePayDetailTable.addCell(cell1);
                chequePayDetailTable.addCell(cell2);
                chequePayDetailTable.addCell(cell3);
                chequePayDetailTable.addCell(cell4);
                chequePayDetailTable.addCell(cell5);
                chequePayDetailTable.addCell(cell6);
                chequePayDetailTable.addCell(cell7);
                PdfPCell cell11 = new PdfPCell(new Phrase("1", smallBold));
                cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell22 = new PdfPCell(new Phrase(fbsPayment.getTransactionId(), blackFont));
                cell22.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell33 = new PdfPCell(new Phrase(fbsPayment.getDrawnOn(), blackFont));
                cell33.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell44 = new PdfPCell(new Phrase(dateFormat.format(fbsPayment.getPaymentDate()), blackFont));
                cell44.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell55 = new PdfPCell(new Phrase(fbsPayment.getPaidAmount().toString() + "/-", blackFont));
                cell55.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell66 = new PdfPCell(new Phrase(String.valueOf(serviceTax) + "/-", blackFont));
                cell66.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell77 = new PdfPCell(new Phrase(String.valueOf(netAmount) + "/-", blackFont));
                cell77.setHorizontalAlignment(Element.ALIGN_CENTER);
                chequePayDetailTable.addCell(cell11);
                chequePayDetailTable.addCell(cell22);
                chequePayDetailTable.addCell(cell33);
                chequePayDetailTable.addCell(cell44);
                chequePayDetailTable.addCell(cell55);
                chequePayDetailTable.addCell(cell66);
                chequePayDetailTable.addCell(cell77);
                PdfPCell cell111 = new PdfPCell(new Phrase("", smallBold));
                cell111.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell222 = new PdfPCell(new Phrase("", blackFont));
                cell222.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell333 = new PdfPCell(new Phrase("", blackFont));
                cell333.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell444 = new PdfPCell(new Phrase("Total", smallBold));
                cell444.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell555 = new PdfPCell(new Phrase(fbsPayment.getPaidAmount().toString() + "/-", smallBold));
                cell555.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell666 = new PdfPCell(new Phrase(String.valueOf(serviceTax) + "/-", smallBold));
                cell666.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell777 = new PdfPCell(new Phrase(String.valueOf(netAmount) + "/-", smallBold));
                cell777.setHorizontalAlignment(Element.ALIGN_CENTER);
                chequePayDetailTable.addCell(cell111);
                chequePayDetailTable.addCell(cell222);
                chequePayDetailTable.addCell(cell333);
                chequePayDetailTable.addCell(cell444);
                chequePayDetailTable.addCell(cell555);
                chequePayDetailTable.addCell(cell666);
                chequePayDetailTable.addCell(cell777);
                table4.addCell(chequePayDetailTable);
            }
            outerTable.addCell(table4);

            PdfPTable table5 = new PdfPTable(1);

            paragraph = new Paragraph("");
            addEmptyLine(paragraph, 4);
            phrase1 = new Phrase("For ", blackFont);
            paragraph.add(phrase1);
            phrase1 = new Phrase(fbsCompany.getCompanyName(), smallBold);
            paragraph.add(phrase1);
            addEmptyLine(paragraph, 2);
            phrase1 = new Phrase("(Account Officer)", smallBold);
            paragraph.add(phrase1);
            addEmptyLine(paragraph, 2);
            ZapfDingbatsList zapfDingbatsList = new ZapfDingbatsList(42, 15);
            zapfDingbatsList.add(new ListItem("This receipt is subject to realization of cheque/draft.", blackSmallFont));
            zapfDingbatsList.add(new ListItem("The receipts are not transferable without written consent of the company.", blackSmallFont));
            zapfDingbatsList.add(new ListItem("This is only the receipt for the remittance as abouve and this does not entitle you to claim ownership / title of the above property unless you are the confirmed owner of the property, as per Company's record.", blackSmallFont));
            c1 = new PdfPCell();
            c1.addElement(paragraph);
            c1.addElement(zapfDingbatsList);
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);

            c1.setBorder(Rectangle.NO_BORDER);
            table5.addCell(c1);
            outerTable.addCell(table5);
            document.add(outerTable);

            // Start a new page
            document.newPage();

            document.close();

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

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
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
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (DocumentException ex) {
            Logger.getLogger(PaymentReceipt.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(PaymentReceipt.class.getName()).log(Level.SEVERE, null, ex);
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
