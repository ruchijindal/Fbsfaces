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
import com.smp.entity.FbsBlock;
import com.smp.entity.FbsBooking;
import com.smp.entity.FbsBrPayment;
import com.smp.entity.FbsBroker;
import com.smp.entity.FbsBrokerCat;
import com.smp.entity.FbsCompany;
import com.smp.entity.FbsFlatType;
import com.smp.entity.FbsPlanname;
import com.smp.entity.FbsProject;
import com.smp.fbs.BrokerInfo;
import com.smp.session.FbsBookingFacade;
import com.smp.session.FbsBrPaymentFacade;
import com.smp.session.FbsBrokerCatFacade;
import com.smp.session.FbsBrokerFacade;
import com.smp.session.FbsCompanyFacade;
import com.smp.session.FbsPlannameFacade;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MasterBrokerReceipt", urlPatterns = {"/masterBrokerReceipt"})
public class MasterBrokerReceipt extends HttpServlet {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager entityManager;
    @EJB
    FbsCompanyFacade fbsCompanyFacade;
    @EJB
    FbsBrokerFacade fbsBrokerFacade;
    @EJB
    FbsBrokerCatFacade fbsBrokerCatFacade;
    @EJB
    CalculateCommision calculateCommision;
    @EJB
    FbsBrPaymentFacade fbsBrPaymentFacade;
    @EJB
    FbsBookingFacade fbsBookingFacade;
    @EJB
    FbsPlannameFacade fbsPlannameFacade;
    FbsCompany fbsCompany;
    FbsBooking fbsBooking;
    FbsBlock fbsBlock;
    FbsProject fbsProject;
    FbsFlatType fbsFlatType;
    FbsBroker fbsBroker;
    FbsBrokerCat fbsBrokerCat;
    List<BrokerInfo> brokerInfoList;
    List<FbsBrPayment> brokerPaymentList;
    public List<FbsBooking> fbsBookingList;
    Object brokerIdArray[];
    float totalCommission = 0;
    float totalPaidAmount = 0;
    float totalPayableAmount = 0;
    float totalBSP = 0;
    String unitCode;
    float PayableAmount = 0;
    float paymentAmount = 0;
    float clearedAmount = 0;
    float pendingAmount = 0;
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");
    int chkBrokerId = 0;

    public MasterBrokerReceipt() {

        fbsCompany = new FbsCompany();
        fbsBooking = new FbsBooking();
        fbsBlock = new FbsBlock();
        fbsProject = new FbsProject();
        fbsFlatType = new FbsFlatType();
        fbsBroker = new FbsBroker();
        fbsBrokerCat = new FbsBrokerCat();
        brokerInfoList = new ArrayList<BrokerInfo>();
        brokerPaymentList = new ArrayList<FbsBrPayment>();
        fbsBookingList = new ArrayList<FbsBooking>();


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

        String BrokerId = request.getParameter("brokerId");
        String ProjId = request.getParameter("projId");
        if(ProjId==null)
            ProjId="null";
        System.out.println("project id in master broker recipt "+ProjId);
        String blockId = request.getParameter("blockId");
        String uCode = request.getParameter("unitCode");
        if(blockId==null)
            blockId="null";
        if(uCode==null)
            uCode="null";

        Integer companyId = Integer.parseInt(request.getParameter("companyId"));
        fbsCompany = fbsCompanyFacade.find(companyId);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");


        if (!BrokerId.equals("null")) {
            Integer brokerId = Integer.parseInt(request.getParameter("brokerId"));

            fbsBroker = fbsBrokerFacade.find(brokerId);
            fbsBrokerCat = fbsBrokerCatFacade.find(fbsBroker.getCategoryId());

            brokerInfoList = calculateCommision.calculateCommission(brokerId, ProjId, blockId, uCode);
            brokerPaymentList = calculateCommision.calculatePayment(brokerInfoList);
            try {

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Document document = new Document(PageSize.A4, 0, 0, 60, 30);
                PdfWriter docWriter = PdfWriter.getInstance(document, baos);
                document.open();
                document.addTitle("BROKER COMMISSION REPORT");
                document.addSubject("Using iText");
                document.addKeywords("Java, PDF, iText");

                Paragraph preface = new Paragraph();
                preface.add(new Paragraph("", blackFont));
                addEmptyLine(preface, 1);
                document.add(preface);
                // create the outermost table for the receipt
                PdfPTable outerTable = new PdfPTable(1);
                //first table in the outside table for the Company details
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
                c1 = new PdfPCell(new Paragraph("BROKER COMMISSION REPORT", smallBold));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);

                c1.setBorder(Rectangle.NO_BORDER);
                table2.addCell(c1);
                outerTable.addCell(table2);

                // third table for the broker Id & current date details

                PdfPTable table3 = new PdfPTable(2);
                table3.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                paragraph = new Paragraph("Broker Id: " + brokerId, smallBold);

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

                //table 4 for broker detail heading
                PdfPTable table4 = new PdfPTable(1);
                c1 = new PdfPCell(new Paragraph("BROKER DETAILS", smallBold));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);

                c1.setBorder(Rectangle.NO_BORDER);
                table4.addCell(c1);
                outerTable.addCell(table4);

                //fifth table for the broker details


                float[] widths = {3.5f, 6.5f, 2.4f, 4.0f, 1.6f, 2.1f};
                PdfPTable brokerDetailtable = new PdfPTable(widths);
                brokerDetailtable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                paragraph = new Paragraph();


                PdfPCell cell2 = new PdfPCell(new Phrase("Name", smallBold));
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell3 = new PdfPCell(new Phrase("Address", smallBold));
                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell4 = new PdfPCell(new Phrase("Mobile", smallBold));
                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell5 = new PdfPCell(new Phrase("Email", smallBold));
                cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell6 = new PdfPCell(new Phrase("Category", smallBold));
                cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell7 = new PdfPCell(new Phrase("Commission", smallBold));
                cell7.setHorizontalAlignment(Element.ALIGN_CENTER);



                brokerDetailtable.addCell(cell2);
                brokerDetailtable.addCell(cell3);
                brokerDetailtable.addCell(cell4);
                brokerDetailtable.addCell(cell5);
                brokerDetailtable.addCell(cell6);
                brokerDetailtable.addCell(cell7);

                PdfPCell cell22 = new PdfPCell(new Phrase(capitalizeFirst(fbsBroker.getBrName()), blackFont));
                cell22.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell33 = new PdfPCell(new Phrase(capitalizeFirst(fbsBroker.getBrAdd()), blackFont));
                cell33.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell44 = new PdfPCell(new Phrase(fbsBroker.getBrMobile().toString(), blackFont));
                cell44.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell55 = new PdfPCell(new Phrase(fbsBroker.getBrMail(), blackFont));
                cell55.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell66 = new PdfPCell(new Phrase(fbsBrokerCat.getCategoryName(), blackFont));
                cell66.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cell77 = new PdfPCell(new Phrase(fbsBrokerCat.getCommission().toString() + "%", blackFont));
                cell77.setHorizontalAlignment(Element.ALIGN_CENTER);

                brokerDetailtable.addCell(cell22);
                brokerDetailtable.addCell(cell33);
                brokerDetailtable.addCell(cell44);
                brokerDetailtable.addCell(cell55);
                brokerDetailtable.addCell(cell66);
                brokerDetailtable.addCell(cell77);

                c1 = new PdfPCell(paragraph);
                c1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);

                c1.setBorder(Rectangle.NO_BORDER);
                brokerDetailtable.addCell(c1);

                outerTable.addCell(brokerDetailtable);


                //table for broker payment detail unit wise
                PdfPTable unitHead = new PdfPTable(1);
                c1 = new PdfPCell(new Paragraph("COMMISSION DEATILS", smallBold));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);

                c1.setBorder(Rectangle.NO_BORDER);
                unitHead.addCell(c1);
                outerTable.addCell(unitHead);

                //add table 4 unit deatils

                float[] widths1 = {2.5f, 1.8f, 1.6f, 3.3f, 2.0f, 2.6f, 2.0f};
                PdfPTable unitDetails = new PdfPTable(widths1);
                unitDetails.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                paragraph = new Paragraph();
                PdfPCell ucell1 = new PdfPCell(new Phrase("Project Name", smallBold));
                ucell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell ucell2 = new PdfPCell(new Phrase("Block Name", smallBold));
                ucell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell ucell3 = new PdfPCell(new Phrase("Unit Code", smallBold));
                ucell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell ucell4 = new PdfPCell(new Phrase("Flat Type", smallBold));
                ucell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell ucell5 = new PdfPCell(new Phrase("Commission(A)", smallBold));
                ucell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell ucell6 = new PdfPCell(new Phrase("Payable Amt.(B)", smallBold));
                ucell6.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell ucell7 = new PdfPCell(new Phrase("Paid Amt.(C)", smallBold));
                ucell7.setHorizontalAlignment(Element.ALIGN_CENTER);

                unitDetails.addCell(ucell1);
                unitDetails.addCell(ucell2);
                unitDetails.addCell(ucell3);
                unitDetails.addCell(ucell4);
                unitDetails.addCell(ucell5);
                unitDetails.addCell(ucell6);
                unitDetails.addCell(ucell7);
                totalCommission = 0;
                totalBSP = 0;
                totalPaidAmount = 0;
                totalPayableAmount = 0;
                for (int i = 0; i < brokerInfoList.size(); i++) {

                    totalBSP = 0;
                    unitCode = brokerInfoList.get(i).getFlatId();

                    PdfPCell ucell11 = new PdfPCell(new Phrase(brokerInfoList.get(i).getProjectName(), blackFont));
                    ucell11.setHorizontalAlignment(Element.ALIGN_CENTER);
                    PdfPCell ucell22 = new PdfPCell(new Phrase(brokerInfoList.get(i).getBlockName(), blackFont));
                    ucell22.setHorizontalAlignment(Element.ALIGN_CENTER);
                    PdfPCell ucell33 = new PdfPCell(new Phrase(unitCode, blackFont));
                    ucell33.setHorizontalAlignment(Element.ALIGN_CENTER);
                    PdfPCell ucell44 = new PdfPCell(new Phrase(brokerInfoList.get(i).getFlatSpecification(), blackFont));
                    ucell44.setHorizontalAlignment(Element.ALIGN_CENTER);
                    PdfPCell ucell55 = new PdfPCell(new Phrase(decimalFormat.format(brokerInfoList.get(i).getCommission()), blackFont));
                    ucell55.setHorizontalAlignment(Element.ALIGN_RIGHT);
                       FbsBooking fbsBooking1 = (FbsBooking) entityManager.createNamedQuery("FbsBooking.findByFlatId").setParameter("flatId",Integer.parseInt(unitCode)).getResultList().get(0);
                    FbsPlanname fbsPlanname= fbsPlannameFacade.find(fbsBooking1.getPlanId());
                       totalBSP = totalBSP + (brokerInfoList.get(i).getSba() * brokerInfoList.get(i).getBr());
                    totalBSP = totalBSP - ((brokerInfoList.get(i).getDiscountPercantadge() * totalBSP)) / 100-((fbsPlanname.getDiscount() * totalBSP) / 100);

                    PayableAmount = calculateCommision.calculatePayableAmount(fbsBrokerCat, unitCode, brokerInfoList.get(i).getCommission(), totalBSP)-brokerInfoList.get(i).getAmount();

                    PdfPCell ucell66 = new PdfPCell(new Phrase(decimalFormat.format(PayableAmount), blackFont));
                    ucell66.setHorizontalAlignment(Element.ALIGN_RIGHT);

                    PdfPCell ucell77 = new PdfPCell(new Phrase(decimalFormat.format(brokerInfoList.get(i).getAmount()), blackFont));
                    ucell77.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    totalPayableAmount = totalPayableAmount + PayableAmount;

                    totalPaidAmount = totalPaidAmount + brokerInfoList.get(i).getAmount();
                    totalCommission = (float) totalCommission + brokerInfoList.get(i).getCommission();



                    unitDetails.addCell(ucell11);
                    unitDetails.addCell(ucell22);
                    unitDetails.addCell(ucell33);
                    unitDetails.addCell(ucell44);
                    unitDetails.addCell(ucell55);
                    unitDetails.addCell(ucell66);
                    unitDetails.addCell(ucell77);

                }


                PdfPCell total = new PdfPCell(new Phrase("Total ", smallBold));
                total.setColspan(4);
                total.setHorizontalAlignment(Element.ALIGN_RIGHT);
                PdfPCell amount = new PdfPCell(new Phrase(decimalFormat.format(totalCommission), smallBold));
                amount.setHorizontalAlignment(Element.ALIGN_RIGHT);
                PdfPCell payableamount = new PdfPCell(new Phrase(decimalFormat.format(totalPayableAmount), smallBold));
                payableamount.setHorizontalAlignment(Element.ALIGN_RIGHT);
                PdfPCell paidamount = new PdfPCell(new Phrase(decimalFormat.format(totalPaidAmount), smallBold));
                paidamount.setHorizontalAlignment(Element.ALIGN_RIGHT);

                unitDetails.addCell(total);
                unitDetails.addCell(amount);
                unitDetails.addCell(payableamount);
                unitDetails.addCell(paidamount);


                PdfPCell payableout = new PdfPCell(new Phrase("  Payable Outstanding Amount (B-C)", smallBold));
                payableout.setColspan(6);
                payableout.setHorizontalAlignment(Element.ALIGN_RIGHT);
                PdfPCell pamount = new PdfPCell(new Phrase(decimalFormat.format(totalPayableAmount ), smallBold));
                pamount.setHorizontalAlignment(Element.ALIGN_RIGHT);

                unitDetails.addCell(payableout);
                unitDetails.addCell(pamount);

                PdfPCell paidOut = new PdfPCell(new Phrase("Total Outstanding Amount(A-C)", smallBold));
                paidOut.setColspan(6);
                paidOut.setHorizontalAlignment(Element.ALIGN_RIGHT);
                PdfPCell paidout = new PdfPCell(new Phrase(decimalFormat.format(totalCommission - totalPaidAmount), smallBold));
                paidout.setHorizontalAlignment(Element.ALIGN_RIGHT);

                unitDetails.addCell(paidOut);
                unitDetails.addCell(paidout);




                c1 = new PdfPCell(paragraph);
                c1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);

                c1.setBorder(Rectangle.NO_BORDER);
                unitDetails.addCell(c1);

                outerTable.addCell(unitDetails);

                //for payment detail head
                PdfPTable paymentHead = new PdfPTable(1);
                c1 = new PdfPCell(new Paragraph("PAYMENT DETAILS", smallBold));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);

                c1.setBorder(Rectangle.NO_BORDER);
                paymentHead.addCell(c1);
                outerTable.addCell(paymentHead);

                //for payment details
                float[] widths2 = {2.0f, 2.0f, 1.8f, 3.2f, 1.6f, 1.6f, 2.0f};
                PdfPTable paymentDetailtable = new PdfPTable(widths2);
                paymentDetailtable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                paragraph = new Paragraph();


                PdfPCell pcell1 = new PdfPCell(new Phrase("Unitcode", smallBold));
                pcell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell pcell2 = new PdfPCell(new Phrase("Payment Date", smallBold));
                pcell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell pcell7 = new PdfPCell(new Phrase("Payment Mode", smallBold));
                pcell7.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell pcell5 = new PdfPCell(new Phrase("Cheque No./Trans.Id", smallBold));
                pcell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell pcell6 = new PdfPCell(new Phrase("Cheque Date", smallBold));
                pcell6.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell pcell3 = new PdfPCell(new Phrase("Status", smallBold));
                pcell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell pcell4 = new PdfPCell(new Phrase("Amount", smallBold));
                pcell4.setHorizontalAlignment(Element.ALIGN_RIGHT);


                paymentDetailtable.addCell(pcell1);
                paymentDetailtable.addCell(pcell2);
                paymentDetailtable.addCell(pcell7);
                paymentDetailtable.addCell(pcell5);
                paymentDetailtable.addCell(pcell6);
                paymentDetailtable.addCell(pcell3);
                paymentDetailtable.addCell(pcell4);

                paymentAmount = 0;
                clearedAmount = 0;
                pendingAmount = 0;

                for (int i = 0; i < brokerPaymentList.size(); i++) {
                    PdfPCell pcell11 = new PdfPCell(new Phrase(brokerPaymentList.get(i).getFkFlatId().toString(), blackFont));
                    pcell11.setHorizontalAlignment(Element.ALIGN_CENTER);
                    PdfPCell pcell22 = new PdfPCell(new Phrase(dateFormat.format(brokerPaymentList.get(i).getPaymentDate()), blackFont));
                    pcell22.setHorizontalAlignment(Element.ALIGN_CENTER);
                    PdfPCell pcell77 = new PdfPCell(new Phrase(brokerPaymentList.get(i).getPaymentMode(), blackFont));
                    pcell77.setHorizontalAlignment(Element.ALIGN_CENTER);
                    PdfPCell pcell33 = new PdfPCell(new Phrase(brokerPaymentList.get(i).getStatus(), blackFont));
                    pcell33.setHorizontalAlignment(Element.ALIGN_CENTER);
                    PdfPCell pcell44 = new PdfPCell(new Phrase(decimalFormat.format(brokerPaymentList.get(i).getAmount()), blackFont));
                    pcell44.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    paymentDetailtable.addCell(pcell11);
                    paymentDetailtable.addCell(pcell22);
                    paymentDetailtable.addCell(pcell77);

                    paymentAmount = (float) paymentAmount + brokerPaymentList.get(i).getAmount();

                    if (brokerPaymentList.get(i).getStatus().equals("Cleared")) {
                        clearedAmount = clearedAmount + brokerPaymentList.get(i).getAmount();
                    } else {
                        pendingAmount = pendingAmount + brokerPaymentList.get(i).getAmount();
                    }

                    if (brokerPaymentList.get(i).getPaymentMode().equals("Cash")) {
                        PdfPCell pcell55 = new PdfPCell(new Phrase("----", blackFont));
                        pcell55.setHorizontalAlignment(Element.ALIGN_CENTER);
                        PdfPCell pcell66 = new PdfPCell(new Phrase("----", blackFont));
                        pcell66.setHorizontalAlignment(Element.ALIGN_CENTER);

                        paymentDetailtable.addCell(pcell55);
                        paymentDetailtable.addCell(pcell66);

                    } else if (brokerPaymentList.get(i).getPaymentMode().equals("Cheque")) {
                        PdfPCell pcell55 = new PdfPCell(new Phrase(brokerPaymentList.get(i).getChequeNo().toString(), blackFont));
                        pcell55.setHorizontalAlignment(Element.ALIGN_CENTER);
                        PdfPCell pcell66 = new PdfPCell(new Phrase(dateFormat.format(brokerPaymentList.get(i).getChequeDate()), blackFont));
                        pcell66.setHorizontalAlignment(Element.ALIGN_CENTER);

                        paymentDetailtable.addCell(pcell55);
                        paymentDetailtable.addCell(pcell66);

                    } else {
                        PdfPCell pcell55 = new PdfPCell(new Phrase(brokerPaymentList.get(i).getTransactionId(), blackFont));
                        pcell55.setHorizontalAlignment(Element.ALIGN_CENTER);
                        PdfPCell pcell66 = new PdfPCell(new Phrase("----", blackFont));
                        pcell66.setHorizontalAlignment(Element.ALIGN_CENTER);

                        paymentDetailtable.addCell(pcell55);
                        paymentDetailtable.addCell(pcell66);
                    }


                    paymentDetailtable.addCell(pcell33);
                    paymentDetailtable.addCell(pcell44);

                }


                PdfPCell totalpayment = new PdfPCell(new Phrase(" Total Recieved Amount ", smallBold));
                totalpayment.setColspan(6);
                totalpayment.setHorizontalAlignment(Element.ALIGN_RIGHT);
                PdfPCell payamount = new PdfPCell(new Phrase(decimalFormat.format(paymentAmount), smallBold));
                payamount.setHorizontalAlignment(Element.ALIGN_RIGHT);

                paymentDetailtable.addCell(totalpayment);
                paymentDetailtable.addCell(payamount);

                PdfPCell totalclear = new PdfPCell(new Phrase(" Total Cleared Amount ", smallBold));
                totalclear.setColspan(6);
                totalclear.setHorizontalAlignment(Element.ALIGN_RIGHT);
                PdfPCell totalclearamt = new PdfPCell(new Phrase(decimalFormat.format(clearedAmount), smallBold));
                totalclearamt.setHorizontalAlignment(Element.ALIGN_RIGHT);

                paymentDetailtable.addCell(totalclear);
                paymentDetailtable.addCell(totalclearamt);

                PdfPCell totalpending = new PdfPCell(new Phrase(" Total Pending Amount ", smallBold));
                totalpending.setColspan(6);
                totalpending.setHorizontalAlignment(Element.ALIGN_RIGHT);
                PdfPCell pendingamount = new PdfPCell(new Phrase(decimalFormat.format(pendingAmount), smallBold));
                pendingamount.setHorizontalAlignment(Element.ALIGN_RIGHT);

                paymentDetailtable.addCell(totalpending);
                paymentDetailtable.addCell(pendingamount);

                c1 = new PdfPCell(paragraph);
                c1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);

                c1.setBorder(Rectangle.NO_BORDER);
                paymentDetailtable.addCell(c1);

                outerTable.addCell(paymentDetailtable);


                //table 5 for all details
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
                zapfDingbatsList.add(
                        new ListItem("The receipts are not transferable without written consent of the company.", blackSmallFont));
                zapfDingbatsList.add(
                        new ListItem("This is only the receipt for the remittance as above and this does not entitle you to claim ownership / title of the above property unless you are the confirmed owner of the property, as per Company's record.", blackSmallFont));
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4, 0, 0, 60, 30);
            PdfWriter docWriter = PdfWriter.getInstance(document, baos);
            // @NamedQuery(name = "FbsBooking.distinctBrokerId", query = "SELECT DISTINCT f.brokerId from FbsBooking f")})

            brokerIdArray = entityManager.createNamedQuery("FbsBooking.distinctBrokerId").getResultList().toArray();

            try {
                document.open();
                document.addTitle("BROKERWISE BOOKING REPORT");
                document.addSubject("Using iText");
                document.addKeywords("Java, PDF, iText");

                Paragraph preface = new Paragraph();
                preface.add(new Paragraph("", blackFont));
                addEmptyLine(preface, 1);
                document.add(preface);
                // create the outermost table for the receipt
                float[] widths2 = {2.4f, 1.2f, 1.8f, 2.0f, 2.0f, 1.2f, 1.6f, 1.6f, 1.6f};
                PdfPTable outerTable = new PdfPTable(widths2);
                //first table in the outside table for the Company details

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
                c1.setBorder(Rectangle.BOTTOM);
                c1.setColspan(9);
                outerTable.addCell(c1);
                //outerTable.addCell(table1);
                addEmptyLine(paragraph, 2);
                // second inner table for the receipt


                Chunk chunk = new Chunk("BROKER COMMISSION REPORT", smallBold);
                chunk.setUnderline(1.2f, -2f);
                c1 = new PdfPCell(new Paragraph(chunk));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setColspan(9);
                c1.setBorder(Rectangle.NO_BORDER);
                outerTable.addCell(c1);

                c1 = new PdfPCell(new Paragraph(" "));
                c1.setColspan(9);
                c1.setBorder(Rectangle.NO_BORDER);
                outerTable.addCell(c1);



                PdfPCell pcell1 = new PdfPCell(new Phrase("Broker Name", smallBold));
                pcell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell pcell2 = new PdfPCell(new Phrase("Reg No.", smallBold));
                pcell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell pcell3 = new PdfPCell(new Phrase("Booking date", smallBold));
                pcell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell pcell4 = new PdfPCell(new Phrase("Project", smallBold));
                pcell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell pcell5 = new PdfPCell(new Phrase("Block", smallBold));
                pcell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell pcell6 = new PdfPCell(new Phrase("Unitcode", smallBold));
                pcell6.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell pcell7 = new PdfPCell(new Phrase("Commission", smallBold));
                pcell7.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell pcell8 = new PdfPCell(new Phrase("Payable amt.", smallBold));
                pcell8.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell pcell9 = new PdfPCell(new Phrase("Paid amt.", smallBold));
                pcell9.setHorizontalAlignment(Element.ALIGN_CENTER);

                outerTable.addCell(pcell1);
                outerTable.addCell(pcell2);
                outerTable.addCell(pcell3);
                outerTable.addCell(pcell4);
                outerTable.addCell(pcell5);
                outerTable.addCell(pcell6);
                outerTable.addCell(pcell7);
                outerTable.addCell(pcell8);
                outerTable.addCell(pcell9);

                for (int a = 0; a < brokerIdArray.length; a++) {

                    Integer bid = (Integer) brokerIdArray[a];
                    brokerInfoList = calculateCommision.calculateCommission(bid, "null", "null", "null");
                    fbsBroker = fbsBrokerFacade.find(brokerIdArray[a]);
                    fbsBrokerCat = fbsBrokerCatFacade.find(fbsBroker.getCategoryId());
                    for (int i = 0; i < brokerInfoList.size(); i++) {
                        totalBSP = 0;
                        unitCode = brokerInfoList.get(i).getFlatId();

                        PdfPCell pcell11 = new PdfPCell(new Phrase(capitalizeFirst(brokerInfoList.get(i).getBrName()), blackFont));
                        pcell11.setHorizontalAlignment(Element.ALIGN_CENTER);
                        PdfPCell pcell22 = new PdfPCell(new Phrase(String.valueOf(brokerInfoList.get(i).getRegistrationNo()), blackFont));
                        pcell22.setHorizontalAlignment(Element.ALIGN_CENTER);
                        PdfPCell pcell33 = new PdfPCell(new Phrase(dateFormat.format(brokerInfoList.get(i).getBookingDate()), blackFont));
                        pcell33.setHorizontalAlignment(Element.ALIGN_CENTER);
                        PdfPCell pcell44 = new PdfPCell(new Phrase(brokerInfoList.get(i).getProjectName(), blackFont));
                        pcell44.setHorizontalAlignment(Element.ALIGN_CENTER);
                        PdfPCell pcell55 = new PdfPCell(new Phrase(brokerInfoList.get(i).getBlockName(), blackFont));
                        pcell55.setHorizontalAlignment(Element.ALIGN_CENTER);
                        PdfPCell pcell66 = new PdfPCell(new Phrase(unitCode, blackFont));
                        pcell66.setHorizontalAlignment(Element.ALIGN_CENTER);
                        PdfPCell pcell77 = new PdfPCell(new Phrase(String.valueOf(brokerInfoList.get(i).getCommission()), blackFont));
                        pcell77.setHorizontalAlignment(Element.ALIGN_RIGHT);
  FbsBooking fbsBooking1 = (FbsBooking) entityManager.createNamedQuery("FbsBooking.findByFlatId").setParameter("flatId",Integer.parseInt(unitCode)).getResultList().get(0);
                    FbsPlanname fbsPlanname= fbsPlannameFacade.find(fbsBooking1.getPlanId());
                        totalBSP = totalBSP + (brokerInfoList.get(i).getSba() * brokerInfoList.get(i).getBr());
                        totalBSP = totalBSP - ((brokerInfoList.get(i).getDiscountPercantadge() * totalBSP)) / 100-((fbsPlanname.getDiscount() * totalBSP) / 100);
                        PayableAmount = calculateCommision.calculatePayableAmount(fbsBrokerCat, unitCode, brokerInfoList.get(i).getCommission(), totalBSP)-brokerInfoList.get(i).getAmount();

                        PdfPCell pcell88 = new PdfPCell(new Phrase(decimalFormat.format(PayableAmount), blackFont));
                        pcell88.setHorizontalAlignment(Element.ALIGN_RIGHT);

                        PdfPCell pcell99 = new PdfPCell(new Phrase(decimalFormat.format(brokerInfoList.get(i).getAmount()), blackFont));
                        pcell99.setHorizontalAlignment(Element.ALIGN_RIGHT);
                      

                        outerTable.addCell(pcell11);
                        outerTable.addCell(pcell22);
                        outerTable.addCell(pcell33);
                        outerTable.addCell(pcell44);
                        outerTable.addCell(pcell55);
                        outerTable.addCell(pcell66);
                        outerTable.addCell(pcell77);
                        outerTable.addCell(pcell88);
                        outerTable.addCell(pcell99);

                    }

                }


                //outerTable.addCell(bookingDetailTable);

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
                zapfDingbatsList.add(
                        new ListItem("The receipts are not transferable without written consent of the company.", blackSmallFont));
                zapfDingbatsList.add(
                        new ListItem("This is only the receipt for the remittance as above and this does not entitle you to claim ownership / title of the above property unless you are the confirmed owner of the property, as per Company's record.", blackSmallFont));
                c1 = new PdfPCell();
                c1.addElement(paragraph);
                c1.addElement(zapfDingbatsList);
                c1.setHorizontalAlignment(Element.ALIGN_LEFT);

                c1.setBorder(Rectangle.NO_BORDER);
                table5.addCell(c1);
                outerTable.addCell(table5);
                document.add(outerTable);

                document.close();

                response.setHeader("Expires", "0");
                response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
                response.setHeader("Pragma", "public");
                response.setContentType("application/pdf");

                baos.writeTo(out);
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i
                < number; i++) {
            paragraph.add(new Paragraph(" "));

        }
    }

    private static String capitalizeFirst(String s) {
        int j = 0;
        String str = null;
        ArrayList<Integer> indexList = new ArrayList();
        if (s.length() == 0) {
            return s;
        } else if (s.indexOf(" ") > 0) {

            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == ' ') {
                    indexList.add(i);
                }
            }
            str = s.substring(0, 1).toUpperCase() + s.substring(1, indexList.get(0)).toLowerCase();
            for (j = 0; j < indexList.size() - 1; j++) {
                str = str + " " + s.substring((indexList.get(j) + 1), (indexList.get(j) + 2)).toUpperCase() + s.substring((indexList.get(j) + 2), (indexList.get(j + 1))).toLowerCase();
            }
            str = str + " " + s.substring((indexList.get(j) + 1), (indexList.get(j) + 2)).toUpperCase() + s.substring((indexList.get(j) + 2));

            return str;
        }

        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();

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
