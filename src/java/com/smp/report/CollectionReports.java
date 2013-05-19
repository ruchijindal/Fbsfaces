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
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.smp.entity.FbsCompany;
import com.smp.entity.FbsPayment;
import com.smp.session.FbsCompanyFacade;
import com.smp.session.FbsPaymentFacade;
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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
/**
 *
 * @author smp
 */
@WebServlet(name = "CollectionReports", urlPatterns = {"/CollectionReports"})
public class CollectionReports extends HttpServlet {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager entityManager;
    @EJB
    FbsPaymentFacade fbsPaymentFacade;
    @EJB
    FbsCompanyFacade fbsCompanyFacade;
    FbsPayment fbsPayment;
    FbsCompany fbsCompany;
    long totalClearedAmount = 0;
    long chequeAmount = 0;
    long totalPendingAmount = 0;
    long totalPaidAmount = 0;
    Date opStartDate1;
    Date opEndDate1;
    long totalCollection = 0;
    long totalPendingAmount1 = 0;
    long totalClearedAmount1 = 0;
    Date opStartDate;
    Date opEndDate;
    boolean[] payOption = new boolean[10];

    public CollectionReports() {
        fbsPayment = new FbsPayment();
        fbsCompany = new FbsCompany();

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

        Integer companyId = Integer.parseInt(request.getParameter("companyId"));
        // List<FbsPayment> paymentList=(List<FbsPayment>)request.getParameter("paymentList");

        HttpSession session = request.getSession(false);
        //  String applicantId = (String) session.getAttribute("applicantId");

        Date startDate = (Date) session.getAttribute("startDate");
        Date endDate = (Date) session.getAttribute("endDate");

        System.out.println("startdate***" + startDate);
        System.out.println("endDate****" + endDate);

        fbsCompany = fbsCompanyFacade.find(companyId);
        List<FbsPayment> FbsPaymentList = new ArrayList<FbsPayment>();
        List<FbsPayment> fbsPaymentList = fbsPaymentFacade.findAll();
        FbsPaymentList = fbsPaymentFacade.findAll();
        List<FbsPayment> paymentTemp1 = new ArrayList<FbsPayment>();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //    List<FbsPayment> fbsPaymentList = fbsPaymentFacade.findAll();




        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4, 0, 0, 0, 0);
            PdfWriter docWriter = PdfWriter.getInstance(document, bos);
            document.open();
            document.addTitle("Collection Report");
            document.addSubject("Using iText");
            document.addKeywords("Java, PDF, iText");

            Paragraph preface = new Paragraph();
            preface.add(new Paragraph("", blackFont));
            addEmptyLine(preface, 1);
            document.add(preface);

            //create the outermost table for collection report
            PdfPTable outerTable = new PdfPTable(1);

            //TABLE 1 FOR COMAPANY DETAILS
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


            //table 2 for collection report heading
            PdfPTable table2 = new PdfPTable(1);
            if (startDate == null && endDate == null) {
                c1 = new PdfPCell(new Paragraph("Collection Report", smallBold));
            } else {
                c1 = new PdfPCell(new Paragraph("Collection Report From " + dateFormat.format(startDate) + " To " + dateFormat.format(endDate), smallBold));
            }


            c1.setHorizontalAlignment(Element.ALIGN_CENTER);

            c1.setBorder(Rectangle.NO_BORDER);
            table2.addCell(c1);
            outerTable.addCell(table2);

            //third table for the collection report column name


            PdfPTable collectionDetailtable = new PdfPTable(4);
            collectionDetailtable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            paragraph = new Paragraph();

            PdfPCell cell1 = new PdfPCell(new Phrase("UNIT CODE", smallBold));
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell cell2 = new PdfPCell(new Phrase("PAYMENT DATE", smallBold));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell cell3 = new PdfPCell(new Phrase("STATUS", smallBold));
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell cell4 = new PdfPCell(new Phrase("PAID AMOUNT", smallBold));
            cell4.setHorizontalAlignment(Element.ALIGN_CENTER);

            collectionDetailtable.addCell(cell1);
            collectionDetailtable.addCell(cell2);
            collectionDetailtable.addCell(cell3);
            collectionDetailtable.addCell(cell4);

            if (startDate == null && endDate == null) {

                totalClearedAmount = 0;
                totalPendingAmount = 0;
                totalPaidAmount = 0;
                for (int i = 0; i < fbsPaymentList.size(); i++) {


                    PdfPCell cell11 = new PdfPCell(new Phrase(fbsPaymentList.get(i).getUnitCode(), blackFont));
                    cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
                    Date date = fbsPaymentList.get(i).getPaymentDate();
                    PdfPCell cell22 = new PdfPCell(new Phrase(dateFormat.format(date), blackFont));
                    cell22.setHorizontalAlignment(Element.ALIGN_CENTER);
                    PdfPCell cell33 = new PdfPCell(new Phrase(fbsPaymentList.get(i).getChequeStatus(), blackFont));
                    cell33.setHorizontalAlignment(Element.ALIGN_CENTER);
                    PdfPCell cell44 = new PdfPCell(new Phrase(String.valueOf(fbsPaymentList.get(i).getPaidAmount()).toString(), blackFont));
                    cell44.setHorizontalAlignment(Element.ALIGN_RIGHT);

                    collectionDetailtable.addCell(cell11);
                    collectionDetailtable.addCell(cell22);
                    collectionDetailtable.addCell(cell33);
                    collectionDetailtable.addCell(cell44);

                    totalPaidAmount = (long) totalPaidAmount + fbsPaymentList.get(i).getPaidAmount();



                    if (fbsPaymentList.get(i).getChequeStatus().equals("Cleared")) {
                        totalClearedAmount = (long) totalClearedAmount + fbsPaymentList.get(i).getPaidAmount();
                    } else {
                        totalPendingAmount = (long) totalPendingAmount + fbsPaymentList.get(i).getPaidAmount();
                    }
                }


            } else {
                int k = 0;
                if (startDate.after(endDate)) {
                    Date temp = endDate;
                    endDate = startDate;
                    startDate = temp;
                }
                //System.out.println("Payment Date");
                for (int i = 0; i < FbsPaymentList.size(); i++) {

                    if (!(FbsPaymentList.get(i).getPaymentDate() == null)) {
                        if (((FbsPaymentList.get(i).getPaymentDate().after(startDate)) || (FbsPaymentList.get(i).getPaymentDate().equals(startDate))) && ((FbsPaymentList.get(i).getPaymentDate().before(endDate))) || (FbsPaymentList.get(i).getPaymentDate().equals(endDate))) {

                            paymentTemp1.add(k, FbsPaymentList.get(i));
                            k++;
                        }
                    }

                }


                System.out.println("size of payment list=======> " + paymentTemp1.size());
                totalClearedAmount = 0;
                totalPendingAmount = 0;
                totalPaidAmount = 0;
                for (int i = 0; i < paymentTemp1.size(); i++) {


                    PdfPCell cell11 = new PdfPCell(new Phrase(paymentTemp1.get(i).getUnitCode(), blackFont));
                    cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
                    Date date = paymentTemp1.get(i).getPaymentDate();
                    PdfPCell cell22 = new PdfPCell(new Phrase(dateFormat.format(date), blackFont));
                    cell22.setHorizontalAlignment(Element.ALIGN_CENTER);
                    PdfPCell cell33 = new PdfPCell(new Phrase(paymentTemp1.get(i).getChequeStatus(), blackFont));
                    cell33.setHorizontalAlignment(Element.ALIGN_CENTER);
                    PdfPCell cell44 = new PdfPCell(new Phrase(String.valueOf(paymentTemp1.get(i).getPaidAmount()).toString(), blackFont));
                    cell44.setHorizontalAlignment(Element.ALIGN_RIGHT);

                    collectionDetailtable.addCell(cell11);
                    collectionDetailtable.addCell(cell22);
                    collectionDetailtable.addCell(cell33);
                    collectionDetailtable.addCell(cell44);

                    totalPaidAmount = (long) totalPaidAmount + paymentTemp1.get(i).getPaidAmount();



                    if (paymentTemp1.get(i).getChequeStatus().equals("Cleared")) {
                        totalClearedAmount = (long) totalClearedAmount + paymentTemp1.get(i).getPaidAmount();
                    } else {
                        totalPendingAmount = (long) totalPendingAmount + paymentTemp1.get(i).getPaidAmount();
                    }
                }
            }


            System.out.println("total paid amount is+++++s" + totalPaidAmount);
            System.out.println("total cleared amount is+++++s" + totalClearedAmount);
            System.out.println("total pending amount is+++++s" + totalPendingAmount);


            PdfPCell paidAmount = new PdfPCell(new Phrase("Total Paid Amount", smallBold));
            paidAmount.setColspan(3);
            paidAmount.setHorizontalAlignment(Element.ALIGN_RIGHT);
            PdfPCell paidamount = new PdfPCell(new Phrase(String.valueOf(totalPaidAmount).toString(), smallBold));
            paidamount.setHorizontalAlignment(Element.ALIGN_RIGHT);
            collectionDetailtable.addCell(paidAmount);
            collectionDetailtable.addCell(paidamount);

            PdfPCell clearedAmount = new PdfPCell(new Phrase("Total Cleared Amount", smallBold));
            clearedAmount.setColspan(3);
            clearedAmount.setHorizontalAlignment(Element.ALIGN_RIGHT);
            PdfPCell clearedamount = new PdfPCell(new Phrase(String.valueOf(totalClearedAmount).toString(), smallBold));
            clearedamount.setHorizontalAlignment(Element.ALIGN_RIGHT);
            collectionDetailtable.addCell(clearedAmount);
            collectionDetailtable.addCell(clearedamount);

            PdfPCell pendingAmount = new PdfPCell(new Phrase("Total Pending  Amount", smallBold));
            pendingAmount.setColspan(3);
            pendingAmount.setHorizontalAlignment(Element.ALIGN_RIGHT);
            PdfPCell pendingamount = new PdfPCell(new Phrase(String.valueOf(totalPendingAmount).toString(), smallBold));
            pendingamount.setHorizontalAlignment(Element.ALIGN_RIGHT);
            collectionDetailtable.addCell(pendingAmount);
            collectionDetailtable.addCell(pendingamount);


            c1 = new PdfPCell(paragraph);
            c1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            c1.setBorder(Rectangle.NO_BORDER);
            collectionDetailtable.addCell(c1);
            outerTable.addCell(collectionDetailtable);

            //table 4 for all details

            document.add(outerTable);
            // Start a new page
            document.newPage();
            document.close();

            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setContentType("application/pdf");

            bos.writeTo(out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i
                < number; i++) {
            paragraph.add(new Paragraph(" "));


        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
    //     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);



        } catch (DocumentException ex) {
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
        }


    }
}
