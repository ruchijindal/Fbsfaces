package com.smp.report;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.smp.entity.FbsApplicant;
import com.smp.entity.FbsBank;
import com.smp.entity.FbsBlock;
import com.smp.entity.FbsBooking;
import com.smp.entity.FbsCharge;
import com.smp.entity.FbsFlatType;
import com.smp.entity.FbsParkingAllot;
import com.smp.entity.FbsParkingType;
import com.smp.entity.FbsPayment;
import com.smp.entity.FbsPlanname;
import com.smp.entity.FbsPlc;
import com.smp.entity.FbsProject;
import com.smp.entity.FbsPayplan;
import com.smp.entity.FbsServicetax;
import com.smp.managedbean.LoginBean;
import com.smp.session.FbsApplicantFacade;
import com.smp.session.FbsBankFacade;
import com.smp.session.FbsBlockFacade;
import com.smp.session.FbsBookingFacade;
import com.smp.session.FbsBrokerCatFacade;
import com.smp.session.FbsBrokerFacade;
import com.smp.session.FbsFlatTypeFacade;
import com.smp.session.FbsParkingTypeFacade;
import com.smp.session.FbsPlannameFacade;
import com.smp.session.FbsPlcFacade;
import com.smp.session.FbsProjectFacade;
import com.smp.session.FbsServicetaxFacade;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.processing.RoundEnvironment;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
//import org.w3c.dom.Element;

public class Report extends HttpServlet {

    FbsApplicant applicant = new FbsApplicant();
    FbsFlatType fbsFlatType = new FbsFlatType();
    FbsProject fbsProject = new FbsProject();
    FbsBooking fbsBooking = new FbsBooking();
    FbsPlc fbsPlc = new FbsPlc();
    FbsPlanname fbsPlanName = new FbsPlanname();
    @EJB
    FbsPlannameFacade fbsPlanNameFacade;
    @EJB
    FbsBankFacade fbsBankFacade;
    @EJB
    FbsPlcFacade fbsPlcfacade;
    @EJB
    ReportHelper reporthelper;
    @EJB
    ChargesAndPlanDetails totalcharges;
    @EJB
    FbsProjectFacade fbsProjectFacade;
    @EJB
    FbsApplicantFacade fbsApplicantFacade;
    @EJB
    FbsBookingFacade fbsBookingFacade;
    @EJB
    FbsFlatTypeFacade fbsFlatTypeFacade;
    @EJB
    FbsBrokerFacade fbsBrokerFacade;
    @EJB
    FbsBrokerCatFacade fbsBrokerCatFacade;
    @EJB
    FbsParkingTypeFacade fbsParkingTypeFacade;
    @EJB
    ApplicantDetails applicantDetails1;
    @EJB
    BrokerDetails brokerDetail;
    @EJB
    FbsBookingFacade FbsBookingFacade;
    @EJB
    FbsServicetaxFacade fbsServicetaxFacade;
    @EJB
    FbsBlockFacade fbsBlockFacade;
    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager entityManager;
    List<java.util.Date> listBillperiod;
    public List<FbsParkingAllot> fbsParkingAllotList = new ArrayList<FbsParkingAllot>();
    public List<FbsParkingType> fbsParkingTypesList = new ArrayList<FbsParkingType>();
    public FbsParkingType fbsParkingType = new FbsParkingType();
    DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private static Font blackFont = new Font(Font.TIMES_ROMAN, 8, Font.NORMAL, BaseColor.BLACK);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String commisson = "";
        String plan_id = "";
        String plan_name = "";
        String projectid = "";
        String flatno = "";
        String flattypeid = "";
        String flat_specification = "";
        String name = "";
        String city = "";
        String address = "";
        String state = "";
        String flat_id = "";
        String block_name = "";
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
        String Plc_id = "";
        String plc_name = "";
        String br_name = "";
        String br_id = "";
        Date booking_date = new Date();
        long netBasicSalePrice1 = 0;
        long totalreceivedamount = 0;
        long totalOtherCharges = 0;
        long bsp = 0;
        long sba = 0;
        long br = 0;
        int regNo = 0;
        String brcategory = "";
        Integer parkingTypeId;
        Integer totalParkingCharges = 0;

        try {
//*****************get the values from the request Object
            HttpSession session = request.getSession(false);
            String unitCode = (String) session.getAttribute("flatId");
            flatno = (String) session.getAttribute("flatNo");
            flattypeid = (String) session.getAttribute("flatTypeId");
            projectid = (String) session.getAttribute("projId");
            block_name = (String) session.getAttribute("blockName");
            int companyId = LoginBean.fbsLogin.getCompanyId();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4, 0, 0, 60, 30);
//    get flatspecification
            fbsFlatType = fbsFlatTypeFacade.find(Integer.parseInt(flattypeid.trim()));
            flat_specification = fbsFlatType.getFlatSpecification();
            flat_Sba = fbsFlatType.getFlatSba();

            Rate = fbsFlatType.getFlatBsp();
            fbsProject = fbsProjectFacade.find(Integer.parseInt(projectid.trim()));
            name = fbsProject.getProjName();
            address = fbsProject.getAddress();
//find plc charges
            ChargesAndPlanDetails bookingDetails = totalcharges.findPlannameAndDiscount(unitCode);// get object of type Class  ChargesAndPlanDetails
            List<String> plclisting = bookingDetails.plcidlist;
            List<FbsPlc> fbsPlcList = new ArrayList<FbsPlc>();

            fbsPlcList.clear();
            for (int pl = 0; pl < plclisting.size(); pl++) {
                FbsPlc temp = new FbsPlc();
                temp = fbsPlcfacade.find(Integer.parseInt(plclisting.get(pl)));
                fbsPlcList.add(temp);
            }
            // Plc_id = bookingDetails.plcId.toString();
            // fbsPlc = fbsPlcfacade.find(Integer.parseInt(Plc_id.trim()));
            // Plc_charge = fbsPlc.getPlcCharge();
            // plc_name = fbsPlc.getPlcName();
            PdfWriter docWriter = PdfWriter.getInstance(document, baos);
            document.open();
            float[] widths1 = {2.0f, 3.0f};
            PdfPTable table = new PdfPTable(widths1);//this is for applicant deatails
            float[] widths2 = {1.5f, 1.9f};
            PdfPTable table2 = new PdfPTable(2);//for first nested table
            PdfPTable table3 = new PdfPTable(2);
            float[] widths8 = {1f, 2.9f, 3.0f};
            PdfPTable plctable = new PdfPTable(widths8);
            float[] widths9 = {1f, 2.9f, 3.0f};
            PdfPTable other_charge_table = new PdfPTable(widths9);
            FontSelector fontselectorhd = new FontSelector();
            fontselectorhd.addFont(new Font(Font.TIMES_ROMAN, 10, Font.BOLD));
            //for secondary headingReg_no
            FontSelector fontselectorhd2 = new FontSelector();
            fontselectorhd2.addFont(new Font(Font.TIMES_ROMAN, 10, Font.BOLD));

            //for Description
            FontSelector fontselectorDesc = new FontSelector();
            fontselectorDesc.addFont(new Font(Font.TIMES_ROMAN, 12, Font.BOLD));

            //for Table total Row
            FontSelector fontselectorhd3 = new FontSelector();
            fontselectorhd3.addFont(new Font(Font.TIMES_ROMAN, 8, Font.BOLD));

            //find plc charges
            Phrase ph = fontselectorhd.process("");
            PdfPCell cell = new PdfPCell(new Paragraph(ph));
            FontSelector fontselector = new FontSelector();
            fontselector.addFont(new Font(Font.TIMES_ROMAN, 8, Font.BOLD));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(2);
            table.addCell(cell);
            PdfPCell Space = new PdfPCell(new Paragraph(""));
            Space.setBorder(Rectangle.NO_BORDER);
            Space.setColspan(2);
            table.addCell(Space);
            // other charges information table
            List<FbsCharge> fbsCharge = totalcharges.findTotalCharges(projectid);// call to TotaloTherCharges method
            Phrase phoc = fontselectorhd2.process("Other Charges Information");
            PdfPCell celloc = new PdfPCell(new Paragraph(phoc));
            celloc.setBorder(Rectangle.NO_BORDER);
            celloc.setHorizontalAlignment(Element.ALIGN_CENTER);
            celloc.setColspan(3);
            other_charge_table.addCell(celloc);
            Phrase phoc1 = fontselector.process("S.No.");
            PdfPCell celloc1 = new PdfPCell(new Paragraph(phoc1));
            other_charge_table.addCell(celloc1);
            Phrase phoc2 = fontselector.process("Description");
            PdfPCell celloc2 = new PdfPCell(new Paragraph(phoc2));
            other_charge_table.addCell(celloc2);
            Phrase phoc3 = fontselector.process("Amount(Rs)");
            PdfPCell celloc3 = new PdfPCell(new Paragraph(phoc3));
            celloc3.setHorizontalAlignment(Element.ALIGN_CENTER);
            other_charge_table.addCell(celloc3);
            for (int l = 0; l < fbsCharge.size(); l++) {
                Phrase phoc6 = fontselector.process(String.valueOf(l + 1));
                PdfPCell celloc6 = new PdfPCell(new Paragraph(phoc6));
                celloc6.setHorizontalAlignment(Element.ALIGN_CENTER);
                other_charge_table.addCell(celloc6);
                Phrase phoc7 = fontselector.process(fbsCharge.get(l).getChargeName());
                PdfPCell celloc7 = new PdfPCell(new Paragraph(phoc7));
                other_charge_table.addCell(celloc7);
                Phrase phoc8 = fontselector.process(String.valueOf(fbsCharge.get(l).getAmount() * flat_Sba));
                PdfPCell celloc8 = new PdfPCell(new Paragraph(phoc8));
                celloc8.setHorizontalAlignment(Element.ALIGN_RIGHT);
                other_charge_table.addCell(celloc8);
                totalOtherCharges = totalOtherCharges + fbsCharge.get(l).getAmount() * flat_Sba;
            }
            Phrase phoc29 = fontselectorhd2.process("Total");
            PdfPCell celloc29 = new PdfPCell(new Paragraph(phoc29));
            celloc29.setHorizontalAlignment(Element.ALIGN_CENTER);
            celloc29.setColspan(2);
            other_charge_table.addCell(celloc29);
            Phrase phoc30 = fontselectorhd2.process(String.valueOf(totalOtherCharges));
            PdfPCell celloc30 = new PdfPCell(new Paragraph(phoc30));
            celloc30.setHorizontalAlignment(Element.ALIGN_RIGHT);
            other_charge_table.addCell(celloc30);
            Phrase phplchsp = fontselectorhd2.process("");
            PdfPCell cellplcsp = new PdfPCell(new Paragraph(phplchsp));
            cellplcsp.setBorder(Rectangle.NO_BORDER);
            cellplcsp.setColspan(3);
            other_charge_table.addCell(cellplcsp);
            other_charge_table.addCell(cellplcsp);
            other_charge_table.addCell(cellplcsp);

            //Parking Detail Table


            Phrase parking = fontselectorhd2.process("Parking Details");
            PdfPCell parkingcell = new PdfPCell(new Paragraph(parking));
            parkingcell.setBorder(Rectangle.NO_BORDER);
            parkingcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            parkingcell.setColspan(3);
            other_charge_table.addCell(parkingcell);
            Phrase sno = fontselector.process("S.NO.");
            PdfPCell parkingcell1 = new PdfPCell(new Paragraph(sno));
            parkingcell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            other_charge_table.addCell(parkingcell1);
            Phrase ptype = fontselector.process("Parking Type");
            PdfPCell parkingcell2 = new PdfPCell(new Paragraph(ptype));
            parkingcell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            other_charge_table.addCell(parkingcell2);
            Phrase charges = fontselector.process("Charges(Rs)");
            PdfPCell parkingcell3 = new PdfPCell(new Paragraph(charges));
            parkingcell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            other_charge_table.addCell(parkingcell3);
            fbsParkingAllotList = entityManager.createNamedQuery("FbsParkingAllot.findByFkFlatId").setParameter("fkFlatId", Integer.parseInt(unitCode)).getResultList();
            totalParkingCharges = 0;
            int temp = 0;
            if (fbsParkingAllotList.size() == 0) {
                Phrase p3 = fontselector.process("No Parking Alloted");
                PdfPCell pc3 = new PdfPCell(new Paragraph(p3));
                pc3.setColspan(3);
                pc3.setHorizontalAlignment(Element.ALIGN_CENTER);
                other_charge_table.addCell(pc3);
            } else {
                for (int i = 0; i < fbsParkingAllotList.size(); i++) {
                    parkingTypeId = fbsParkingAllotList.get(i).getParkingTypeId();
                    fbsParkingType = fbsParkingTypeFacade.find(parkingTypeId);
                    Phrase p3 = fontselector.process(String.valueOf(i + 1));
                    PdfPCell pc3 = new PdfPCell(new Paragraph(p3));
                    pc3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    other_charge_table.addCell(pc3);
                    Phrase p1 = fontselector.process(fbsParkingType.getParkingType());
                    PdfPCell pc1 = new PdfPCell(new Paragraph(p1));
                    pc1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    other_charge_table.addCell(pc1);
                    Phrase p2 = fontselector.process(String.valueOf(fbsParkingType.getParkingCharge()));
                    PdfPCell pc2 = new PdfPCell(new Paragraph(p2));
                    pc2.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    other_charge_table.addCell(pc2);
                    totalParkingCharges = totalParkingCharges + fbsParkingType.getParkingCharge();

                }
                PdfPCell total = new PdfPCell(new Phrase(fontselectorhd2.process("Total")));
                total.setColspan(2);
                total.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell amount = new PdfPCell(new Phrase(fontselectorhd2.process(String.valueOf(totalParkingCharges))));
                amount.setHorizontalAlignment(Element.ALIGN_RIGHT);
                other_charge_table.addCell(total);
                other_charge_table.addCell(amount);
            }
            // BrokerCommisson brokercom = brokercomm.findBrokercommisson(unitCode.toString());

            other_charge_table.addCell(cellplcsp);
            other_charge_table.addCell(cellplcsp);
            other_charge_table.addCell(cellplcsp);





//******************************************end of other Charges Table*******************************************************
//plc table
            Phrase phplch = fontselectorhd2.process("PLC Information");
            PdfPCell cellplc = new PdfPCell(new Paragraph(phplch));
            cellplc.setBorder(Rectangle.NO_BORDER);
            cellplc.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellplc.setColspan(3);
            plctable.addCell(cellplc);
            Phrase phplc2 = fontselector.process("S.No.");
            PdfPCell cellplc2 = new PdfPCell(new Paragraph(phplc2));
            plctable.addCell(cellplc2);
            Phrase phplc3 = fontselector.process("PLC Name");
            PdfPCell cellplc3 = new PdfPCell(new Paragraph(phplc3));
            plctable.addCell(cellplc3);
            Phrase phplc4 = fontselector.process("PLC Charge(Rs/sqft)");
            PdfPCell cellplc4 = new PdfPCell(new Paragraph(phplc4));
            plctable.addCell(cellplc4);

            for (int p = 0; p < fbsPlcList.size(); p++) {
                Phrase phplc5 = fontselector.process(String.valueOf(p + 1));
                PdfPCell cellplc5 = new PdfPCell(new Paragraph(phplc5));
                plctable.addCell(cellplc5);
                Phrase phplc6 = fontselector.process(fbsPlcList.get(p).getPlcName());
                PdfPCell cellplc6 = new PdfPCell(new Paragraph(phplc6));
                plctable.addCell(cellplc6);
                Phrase phplc7 = fontselector.process(String.valueOf(fbsPlcList.get(p).getPlcCharge()));
                PdfPCell cellplc7 = new PdfPCell(new Paragraph(phplc7));
                plctable.addCell(cellplc7);
            }
            plctable.addCell(cellplcsp);
            plctable.addCell(cellplcsp);
            plctable.addCell(cellplcsp);

//Broker information
            BrokerDetails brokerDetails = brokerDetail.findBrokerdetails(Integer.parseInt(unitCode));
            Phrase phplch1 = fontselectorhd2.process("Broker Information");
            PdfPCell cellplc1 = new PdfPCell(new Paragraph(phplch1));
            cellplc1.setBorder(Rectangle.NO_BORDER);
            cellplc1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellplc1.setColspan(3);
            plctable.addCell(cellplc1);
            Phrase phplc1 = fontselector.process("Code");
            PdfPCell cellplc11 = new PdfPCell(new Paragraph(phplc1));
            plctable.addCell(cellplc11);
            Phrase phplc21 = fontselector.process("Broker Name");
            PdfPCell cellplc21 = new PdfPCell(new Paragraph(phplc21));
            plctable.addCell(cellplc21);
            Phrase phplc31 = fontselector.process("Broker Category");
            PdfPCell cellplc31 = new PdfPCell(new Paragraph(phplc31));
            plctable.addCell(cellplc31);
            Phrase phplc51 = fontselector.process(String.valueOf(brokerDetails.brokerId));
            PdfPCell cellplc51 = new PdfPCell(new Paragraph(phplc51));
            plctable.addCell(cellplc51);
            Phrase phplc61 = fontselector.process(brokerDetails.brokerName);
            PdfPCell cellplc61 = new PdfPCell(new Paragraph(phplc61));
            plctable.addCell(cellplc61);
            // BrokerCommisson brokercom = brokercomm.findBrokercommisson(unitCode.toString());
            Phrase phplc71 = fontselector.process(brokerDetails.categoryName);
            PdfPCell cellplc71 = new PdfPCell(new Paragraph(phplc71));
            plctable.addCell(cellplc71);
            plctable.addCell(cellplcsp);
            plctable.addCell(cellplcsp);
            plctable.addCell(cellplcsp);
//applicant table

            ApplicantDetails applicantDetails = applicantDetails1.findApplicantDetails(unitCode.toString());
            Phrase ph3 = fontselector.process("Name :");
            PdfPCell cell3 = new PdfPCell(new Paragraph(ph3));
            cell3.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell3);
            Phrase ph4 = fontselectorhd2.process(applicantDetails.Applicant_name);
            PdfPCell cell4 = new PdfPCell(new Paragraph(ph4));
            cell4.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell4);
            Phrase ph7 = fontselector.process("Mobile No :");
            PdfPCell cell7 = new PdfPCell(new Paragraph(ph7));
            cell7.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell7);
            Phrase ph8 = fontselector.process(String.valueOf(applicantDetails.mobile));
            PdfPCell cell8 = new PdfPCell(new Paragraph(ph8));
            cell8.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell8);
            Phrase ph9 = fontselector.process("E-mail :");
            PdfPCell cell9 = new PdfPCell(new Paragraph(ph9));
            cell9.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell9);
            Phrase ph10 = fontselector.process(String.valueOf(applicantDetails.email));
            PdfPCell cell10 = new PdfPCell(new Paragraph(ph10));
            cell10.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell10);
            Phrase ph11 = fontselector.process("Pan No :");
            PdfPCell cell11 = new PdfPCell(new Paragraph(ph11));
            cell11.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell11);
            Phrase ph12 = fontselector.process(applicantDetails.pan_no);
            PdfPCell cell12 = new PdfPCell(new Paragraph(ph12));
            cell12.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell12);
            Phrase ph21 = fontselector.process("Address :");
            PdfPCell cell21 = new PdfPCell(new Paragraph(ph21));
            cell21.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell21);
            Phrase ph22 = fontselector.process(applicantDetails.res_add);
            PdfPCell cell22 = new PdfPCell(new Paragraph(ph22));
            cell22.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell22);
            PdfPCell Space_bottam16 = new PdfPCell(new Paragraph(""));
            Space_bottam16.setBorder(Rectangle.NO_BORDER);
            Space_bottam16.setColspan(2);
            table.addCell(Space_bottam16);
//applicant details end here
            PdfPCell Space_bottam161 = new PdfPCell(new Paragraph(""));
            Space_bottam161.setBorder(Rectangle.NO_BORDER);
            Space_bottam161.setColspan(4);
            table.addCell(Space_bottam161);
            FontSelector fontselectorhdp = new FontSelector();
            fontselectorhdp.addFont(new Font(Font.TIMES_ROMAN, 12, Font.BOLD));
            float[] widths3 = {2.0f, 3.0f};
            PdfPTable tableflat = new PdfPTable(widths3);
            //flat details start here
            Phrase ph29 = fontselector.process("Project :");
            PdfPCell cell29 = new PdfPCell(new Paragraph(ph29));
            cell29.setBorder(Rectangle.NO_BORDER);
            tableflat.addCell(cell29);
            Phrase ph30 = fontselectorhd2.process(name);
            PdfPCell cell30 = new PdfPCell(new Paragraph(ph30));
            cell30.setBorder(Rectangle.NO_BORDER);
            tableflat.addCell(cell30);
            Phrase phblock = fontselector.process("Block Name :");
            PdfPCell cellblock = new PdfPCell(new Paragraph(phblock));
            cellblock.setBorder(Rectangle.NO_BORDER);
            tableflat.addCell(cellblock);
            Phrase phblock1 = fontselectorhd2.process(block_name);
            PdfPCell cellblock1 = new PdfPCell(new Paragraph(phblock1));
            cellblock1.setBorder(Rectangle.NO_BORDER);
            tableflat.addCell(cellblock1);
            Phrase ph23 = fontselector.process("Unit No :");
            PdfPCell cell23 = new PdfPCell(new Paragraph(ph23));
            cell23.setBorder(Rectangle.NO_BORDER);
            tableflat.addCell(cell23);
            Phrase ph24 = fontselector.process(unitCode);
            PdfPCell cell24 = new PdfPCell(new Paragraph(ph24));
            cell24.setBorder(Rectangle.NO_BORDER);
            tableflat.addCell(cell24);
            Phrase ph25 = fontselector.process("Unit Type");
            PdfPCell cell25 = new PdfPCell(new Paragraph(ph25));
            cell25.setBorder(Rectangle.NO_BORDER);
            tableflat.addCell(cell25);
            Phrase ph26 = fontselector.process(flat_specification);
            PdfPCell cell26 = new PdfPCell(new Paragraph(ph26));
            cell26.setBorder(Rectangle.NO_BORDER);
            tableflat.addCell(cell26);
            Phrase ph31 = fontselector.process("Address :");
            PdfPCell cell31 = new PdfPCell(new Paragraph(ph31));
            cell31.setBorder(Rectangle.NO_BORDER);
            tableflat.addCell(cell31);
            Phrase ph32 = fontselector.process(address);
            PdfPCell cell32 = new PdfPCell(new Paragraph(ph32));
            cell32.setBorder(Rectangle.NO_BORDER);
            tableflat.addCell(cell32);
            Phrase ph311 = fontselector.process("Plan Name :");
            PdfPCell cell311 = new PdfPCell(new Paragraph(ph311));
            cell311.setBorder(Rectangle.NO_BORDER);
            tableflat.addCell(cell311);


            fbsBooking = (FbsBooking) entityManager.createNamedQuery("FbsBooking.findByFlatId").setParameter("flatId", Integer.parseInt(unitCode)).getResultList().get(0);

            Phrase ph321 = fontselector.process(fbsPlanNameFacade.find(fbsBooking.getPlanId()).getFullName() + "(" + fbsPlanNameFacade.find(fbsBooking.getPlanId()).getPlanName() + ")");
            PdfPCell cell321 = new PdfPCell(new Paragraph(ph321));
            cell321.setBorder(Rectangle.NO_BORDER);
            tableflat.addCell(cell321);
            Phrase phn = fontselectorhd2.process("APPLICANT FILE  (" + name + ")");
            PdfPCell celln = new PdfPCell(new Paragraph(phn));
            celln.setBorder(Rectangle.NO_BORDER);
            celln.setHorizontalAlignment(Element.ALIGN_CENTER);
            celln.setColspan(2);
            table2.addCell(celln);
            PdfPCell celln1 = new PdfPCell(new Paragraph(""));
            celln1.setBorder(Rectangle.NO_BORDER);
            celln1.setColspan(2);
            table2.addCell(celln1);
            table2.addCell(celln1);
            table2.addCell(celln1);
            table2.addCell(celln1);
            Phrase ph1 = fontselector.process("UnitCode :" + unitCode);
            PdfPCell cell1 = new PdfPCell(new Paragraph(ph1));
            cell1.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell1);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
            String bookingDate = formatter.format(booking_date);
            Phrase phdate = fontselector.process("Date :" + bookingDate);
            PdfPCell cellphdate = new PdfPCell(new Paragraph(phdate));
            cellphdate.setBorder(Rectangle.NO_BORDER);
            cellphdate.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table2.addCell(cellphdate);
            table2.addCell(celln1);
            Phrase ph5 = fontselector.process("Registration No. :" + fbsBooking.getRegNumber());
            PdfPCell cell5 = new PdfPCell(new Paragraph(ph5));
            cell5.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell5);
            String fbsbookingDate = formatter.format(fbsBooking.getBookingDt());
            Phrase phbook = fontselector.process("Booking Date :" + fbsbookingDate);
            PdfPCell cellbook = new PdfPCell(new Paragraph(phbook));
            cellbook.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellbook.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cellbook);
            table2.addCell(celln1);
            table2.addCell(celln1);
            table2.addCell(celln1);
            Phrase applicantdetail = fontselectorDesc.process("Applicant Details");
            PdfPCell cella = new PdfPCell(new Paragraph(applicantdetail));
            table2.addCell(cella);
            Phrase coapplicantdetail = fontselectorDesc.process("Co-Applicant Details");
            PdfPCell cellaphacoapplicant = new PdfPCell(new Paragraph(coapplicantdetail));
            table2.addCell(cellaphacoapplicant);
            table2.addCell(table);
            //we use table here for adding co applicant details
            float[] widths4 = {2.0f, 3.0f};
            PdfPTable coapplicant = new PdfPTable(widths4);
            FontSelector fontselectorcoapp = new FontSelector();
            fontselectorcoapp.addFont(new Font(Font.TIMES_ROMAN, 12, Font.BOLD));
            Phrase phcoapp = fontselectorcoapp.process("");
            PdfPCell cellcoapp = new PdfPCell(new Paragraph(phcoapp));
            FontSelector fontselectorcoapp1 = new FontSelector();
            fontselectorcoapp1.addFont(new Font(Font.TIMES_ROMAN, 12, Font.BOLD));
            cellcoapp.setBorder(Rectangle.NO_BORDER);
            cellcoapp.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellcoapp.setColspan(2);
            coapplicant.addCell(cellcoapp);
            PdfPCell Spacecoapp = new PdfPCell(new Paragraph(""));
            Spacecoapp.setBorder(Rectangle.NO_BORDER);
            Spacecoapp.setColspan(4);
            coapplicant.addCell(Spacecoapp);
            Phrase ph3coapp = fontselector.process("Name :");
            PdfPCell cell3coapp = new PdfPCell(new Paragraph(ph3coapp));
            cell3coapp.setBorder(Rectangle.NO_BORDER);
            coapplicant.addCell(cell3coapp);
            Phrase ph4coapp = fontselectorhd2.process(applicantDetails.Applicant_name2);
            PdfPCell cell4coapp = new PdfPCell(new Paragraph(ph4coapp));
            cell4coapp.setBorder(Rectangle.NO_BORDER);
            coapplicant.addCell(cell4coapp);
            Phrase ph7coapp = fontselector.process("Mobile No :");
            PdfPCell cell7coapp = new PdfPCell(new Paragraph(ph7coapp));
            cell7coapp.setBorder(Rectangle.NO_BORDER);
            coapplicant.addCell(cell7coapp);
            Phrase ph8coapp = fontselector.process(String.valueOf(applicantDetails.mobile2));
            PdfPCell cell8coapp = new PdfPCell(new Paragraph(ph8coapp));
            cell8coapp.setBorder(Rectangle.NO_BORDER);
            coapplicant.addCell(cell8coapp);
            Phrase ph9coapp = fontselector.process("E-Mail :");
            PdfPCell cell9coapp = new PdfPCell(new Paragraph(ph9coapp));
            cell9coapp.setBorder(Rectangle.NO_BORDER);
            coapplicant.addCell(cell9coapp);
            Phrase ph10coapp = fontselector.process(String.valueOf(applicantDetails.email2));
            PdfPCell cell10coapp = new PdfPCell(new Paragraph(ph10coapp));
            cell10coapp.setBorder(Rectangle.NO_BORDER);
            coapplicant.addCell(cell10coapp);
            Phrase ph11coapp = fontselector.process("Pan No :");
            PdfPCell cell11coapp = new PdfPCell(new Paragraph(ph11));
            cell11coapp.setBorder(Rectangle.NO_BORDER);
            coapplicant.addCell(cell11coapp);
            Phrase ph12coapp = fontselector.process(applicantDetails.pan_no2);
            PdfPCell cell12coapp = new PdfPCell(new Paragraph(ph12coapp));
            cell12coapp.setBorder(Rectangle.NO_BORDER);
            coapplicant.addCell(cell12coapp);
            Phrase ph21coapp = fontselector.process("Address :");
            PdfPCell cell21coapp = new PdfPCell(new Paragraph(ph21coapp));
            cell21coapp.setBorder(Rectangle.NO_BORDER);
            coapplicant.addCell(cell21coapp);
            Phrase ph22coapp = fontselector.process(applicantDetails.res_add2);
            PdfPCell cell22coapp = new PdfPCell(new Paragraph(ph22coapp));
            cell22coapp.setBorder(Rectangle.NO_BORDER);
            coapplicant.addCell(cell22coapp);
            PdfPCell Space_bottam16coapp = new PdfPCell(new Paragraph(""));
            Space_bottam16coapp.setBorder(Rectangle.NO_BORDER);
            Space_bottam16coapp.setColspan(2);
            coapplicant.addCell(Space_bottam16coapp);
            FontSelector fontselectorhd2coapp = new FontSelector();
            fontselectorhd2coapp.addFont(new Font(Font.TIMES_ROMAN, 12, Font.BOLD));
            table2.addCell(coapplicant);
            //co-applicant end here  details end here
            PdfPCell celln11 = new PdfPCell(new Paragraph(""));
            celln11.setBorder(Rectangle.NO_BORDER);
            celln11.setHorizontalAlignment(Element.ALIGN_CENTER);
            celln11.setColspan(2);
            table2.addCell(celln11);
            PdfPCell celln111 = new PdfPCell(new Paragraph(""));
            celln111.setBorder(Rectangle.NO_BORDER);
            celln111.setHorizontalAlignment(Element.ALIGN_CENTER);
            celln111.setColspan(2);
            table2.addCell(celln111);
            float[] widths5 = {1};
            PdfPTable nestedcalulationtable = new PdfPTable(2);
            float[] widths6 = {3.0f, 0.5f, 1.0f, 2.0f};
            PdfPTable calulationtable = new PdfPTable(widths6);
            Phrase ph27 = fontselector.process("Super Area(sqft) :");
            PdfPCell cell27 = new PdfPCell(new Paragraph(ph27));
            cell27.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell27.setBorder(Rectangle.NO_BORDER);
            cell27.setColspan(2);
            calulationtable.addCell(cell27);
            Phrase ph28 = fontselector.process(String.valueOf(flat_Sba));
            PdfPCell cell28 = new PdfPCell(new Paragraph(ph28));
            cell28.setColspan(2);
            cell28.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell28.setBorder(Rectangle.NO_BORDER);
            calulationtable.addCell(cell28);
            Phrase phrate = fontselector.process("Basic Rate(Rs/sqft) :");
            PdfPCell cellrate = new PdfPCell(new Paragraph(phrate));
            cellrate.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellrate.setBorder(Rectangle.NO_BORDER);
            cellrate.setColspan(2);
            calulationtable.addCell(cellrate);
            Phrase phrate1 = fontselector.process(String.valueOf(Rate));
            PdfPCell cellrate1 = new PdfPCell(new Paragraph(phrate1));
            cellrate1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellrate1.setBorder(Rectangle.NO_BORDER);
            cellrate1.setColspan(2);
            calulationtable.addCell(cellrate1);
            Phrase phrate02 = fontselector.process("MULTIPLY :");
            PdfPCell cellrate02 = new PdfPCell(new Paragraph(phrate02));
            cellrate02.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellrate02.setColspan(4);
            cellrate02.setBorder(Rectangle.NO_BORDER);
            calulationtable.addCell(cellrate02);

            Phrase phrate01 = fontselector.process("_________________________________________________");
            PdfPCell cellrate01 = new PdfPCell(new Paragraph(phrate01));
            cellrate01.setHorizontalAlignment(Element.ALIGN_JUSTIFIED_ALL);
            cellrate01.setBorder(Rectangle.NO_BORDER);
            cellrate01.setColspan(4);
            calulationtable.addCell(cellrate01);




            FontSelector fontselectorsale = new FontSelector();
            fontselectorsale.addFont(new Font(Font.TIMES_ROMAN, 10, Font.BOLD));
            Phrase phrate4 = fontselectorsale.process("Basic Sale Price  :");
            PdfPCell cellrate4 = new PdfPCell(new Paragraph(phrate4));
            cellrate4.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellrate4.setBorder(Rectangle.NO_BORDER);
            cellrate4.setColspan(2);
            calulationtable.addCell(cellrate4);
            //*********************************************calculation*********************************************
            if (Rate != 0 && flat_Sba != 0) {
                BasicPrice = Rate * flat_Sba;
            } else {
                BasicPrice = 0;
            }
            Discount = bookingDetails.discount;
            long plan_discount = BasicPrice * (fbsPlanNameFacade.find((fbsBooking.getPlanId())).getDiscount()) / 100;
            long discount_amount = (BasicPrice * Discount) / 100;
            netBasicSalePrice1 = (BasicPrice - discount_amount - plan_discount);
            long totalPlc = 0;
            long totalplccharge = 0;
            for (int pp = 0; pp < fbsPlcList.size(); pp++) {
                totalplccharge += fbsPlcList.get(pp).getPlcCharge();
                totalPlc = totalPlc + flat_Sba * fbsPlcList.get(pp).getPlcCharge();
            }
            Total_Cost = (netBasicSalePrice1 + totalPlc + totalOtherCharges + totalParkingCharges);
            List<FbsPayment> paiAmount = reporthelper.findPaymentInformation(unitCode);
            FbsServicetax servicetaxtemp = new FbsServicetax();
            List<FbsServicetax> servicetaxs = new ArrayList<FbsServicetax>();
            servicetaxs = fbsServicetaxFacade.findAll();
            double totalservicetax = 0;
            for (int i = 0; i < paiAmount.size(); i++) {
                int j = 0;
                for (; j < servicetaxs.size(); j++) {
                    if ((servicetaxs.get(j).getStDate().before(paiAmount.get(i).getPaymentDate())) && (servicetaxs.get(j).getEndDate().after(paiAmount.get(i).getPaymentDate()))) {
                        servicetaxtemp = servicetaxs.get(j);
                        break;
                    }

                }
             //   System.out.println("service tax is" + servicetaxtemp.getServicetax());
                double temp1 = (1 + (double) servicetaxtemp.getServicetax() / 100);

                // System.out.println("ghdf"+servicetaxtemp.getServicetax()+" t is "+temp1);
                double recieved1 = paiAmount.get(i).getPaidAmount() / temp1;

                //   System.out.println("recieved ............."+recieved1);

                totalservicetax = totalservicetax + (paiAmount.get(i).getPaidAmount() - recieved1);
                Paid_amount = Paid_amount + paiAmount.get(i).getPaidAmount();
                //for (int i = 0; i < paiAmount.size(); i++) {
                // Paid_amount = Paid_amount + paiAmount.get(i).getPaidAmount();
            }
            Paid_amount = Paid_amount;
            double Payable_amount = (Total_Cost - Paid_amount);

            //*******************************************calculation
            Phrase phrate5 = fontselectorsale.process(String.valueOf(BasicPrice));
            PdfPCell cellrate5 = new PdfPCell(new Paragraph(phrate5));
            cellrate5.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellrate5.setBorder(Rectangle.NO_BORDER);
            cellrate5.setColspan(2);
            calulationtable.addCell(cellrate5);
            Phrase phrate8 = fontselectorsale.process("Less :");
            PdfPCell cellrate8 = new PdfPCell(new Paragraph(phrate8));
            cellrate8.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellrate8.setBorder(Rectangle.NO_BORDER);
            cellrate8.setColspan(4);
            calulationtable.addCell(cellrate8);
            Phrase phrate99 = fontselector.process("Plan Discount :" + String.valueOf(fbsPlanNameFacade.find((fbsBooking.getPlanId())).getDiscount()) + "%");
            PdfPCell cellrate99 = new PdfPCell(new Paragraph(phrate99));
            cellrate99.setColspan(2);
            cellrate99.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellrate99.setBorder(Rectangle.NO_BORDER);
            calulationtable.addCell(cellrate99);
            Phrase phrate1019 = fontselector.process(String.valueOf(plan_discount));
            PdfPCell cellrate1019 = new PdfPCell(new Paragraph(phrate1019));
            cellrate1019.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellrate1019.setBorder(Rectangle.NO_BORDER);
            cellrate1019.setColspan(2);
            calulationtable.addCell(cellrate1019);
            Phrase phrate9 = fontselector.process("Other Discount :" + String.valueOf(Discount) + "%");
            PdfPCell cellrate9 = new PdfPCell(new Paragraph(phrate9));
            cellrate9.setColspan(2);
            cellrate9.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellrate9.setBorder(Rectangle.NO_BORDER);
            calulationtable.addCell(cellrate9);

            Phrase phrate101 = fontselector.process(String.valueOf(discount_amount));
            PdfPCell cellrate101 = new PdfPCell(new Paragraph(phrate101));
            cellrate101.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellrate101.setBorder(Rectangle.NO_BORDER);
            cellrate101.setColspan(2);
            calulationtable.addCell(cellrate101);

            Phrase phrate11 = fontselector.process("_________________________________________________");
            PdfPCell cellrate11 = new PdfPCell(new Paragraph(phrate11));
            cellrate11.setHorizontalAlignment(Element.ALIGN_JUSTIFIED_ALL);
            cellrate11.setBorder(Rectangle.NO_BORDER);
            cellrate11.setColspan(4);
            calulationtable.addCell(cellrate11);
            Phrase phrate12 = fontselectorsale.process("Net Basic Sale Price:");
            PdfPCell cellrate12 = new PdfPCell(new Paragraph(phrate12));
            cellrate12.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellrate12.setColspan(2);
            cellrate12.setBorder(Rectangle.NO_BORDER);
            calulationtable.addCell(cellrate12);
            Phrase phrate13 = fontselectorsale.process(String.valueOf(netBasicSalePrice1));
            PdfPCell cellrate13 = new PdfPCell(new Paragraph(phrate13));
            cellrate13.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellrate13.setBorder(Rectangle.NO_BORDER);
            cellrate13.setColspan(2);
            calulationtable.addCell(cellrate13);
            Phrase phrate14 = fontselectorsale.process("Add :");
            PdfPCell cellrate14 = new PdfPCell(new Paragraph(phrate14));
            cellrate14.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellrate14.setColspan(4);
            cellrate14.setBorder(Rectangle.NO_BORDER);
            calulationtable.addCell(cellrate14);
            Phrase phrate15 = fontselector.process("PLC : " + String.valueOf(totalplccharge) + " Rs/sqft");
            PdfPCell cellrate15 = new PdfPCell(new Paragraph(phrate15));
            cellrate15.setColspan(2);
            cellrate15.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellrate15.setBorder(Rectangle.NO_BORDER);
            calulationtable.addCell(cellrate15);
            Phrase phrate161 = fontselector.process(String.valueOf(totalPlc));
            PdfPCell cellrate161 = new PdfPCell(new Paragraph(phrate161));
            cellrate161.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellrate161.setBorder(Rectangle.NO_BORDER);
            cellrate161.setColspan(2);
            calulationtable.addCell(cellrate161);
            Phrase phrate151 = fontselector.process("Other Charges:");
            PdfPCell cellrate151 = new PdfPCell(new Paragraph(phrate151));
            cellrate151.setColspan(2);
            cellrate151.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellrate151.setBorder(Rectangle.NO_BORDER);
            calulationtable.addCell(cellrate151);


            Phrase phrate152 = fontselector.process(String.valueOf(totalOtherCharges));
            PdfPCell cellrate152 = new PdfPCell(new Paragraph(phrate152));
            cellrate152.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellrate152.setBorder(Rectangle.NO_BORDER);
            cellrate152.setColspan(2);
            calulationtable.addCell(cellrate152);
            // calulationtable.addCell(cellrate11);
            // parking
            Phrase phrate1511 = fontselector.process("Parking Charges:");
            PdfPCell cellrate1511 = new PdfPCell(new Paragraph(phrate1511));
            cellrate1511.setColspan(2);
            cellrate1511.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellrate1511.setBorder(Rectangle.NO_BORDER);
            calulationtable.addCell(cellrate1511);

            Phrase phrate1521 = fontselector.process(String.valueOf(totalParkingCharges));
            PdfPCell cellrate1521 = new PdfPCell(new Paragraph(phrate1521));
            cellrate1521.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellrate1521.setBorder(Rectangle.NO_BORDER);
            cellrate1521.setColspan(2);
            calulationtable.addCell(cellrate1521);
            calulationtable.addCell(cellrate11);

            Phrase phrate17 = fontselectorsale.process("Total Cost(A):");
            PdfPCell cellrate17 = new PdfPCell(new Paragraph(phrate17));
            cellrate17.setColspan(2);
            cellrate17.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellrate17.setBorder(Rectangle.NO_BORDER);
            calulationtable.addCell(cellrate17);


            Phrase phrate18 = fontselectorsale.process(String.valueOf(Total_Cost));
            PdfPCell cellrate18 = new PdfPCell(new Paragraph(phrate18));
            cellrate18.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellrate18.setBorder(Rectangle.NO_BORDER);
            cellrate18.setColspan(2);
            calulationtable.addCell(cellrate18);
            Phrase phrate19 = fontselectorsale.process("Less :");
            PdfPCell cellrate19 = new PdfPCell(new Paragraph(phrate19));
            cellrate19.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellrate19.setBorder(Rectangle.NO_BORDER);
            cellrate19.setColspan(4);
            calulationtable.addCell(cellrate19);
            Phrase phrate20 = fontselector.process("Total Paid Amount(C):");
            PdfPCell cellrate20 = new PdfPCell(new Paragraph(phrate20));
            cellrate20.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellrate20.setBorder(Rectangle.NO_BORDER);
            cellrate20.setColspan(2);
            calulationtable.addCell(cellrate20);
            Phrase phrate21 = fontselector.process(decimalFormat.format(Paid_amount));
            PdfPCell cellrate21 = new PdfPCell(new Paragraph(phrate21));
            cellrate21.setColspan(2);
            cellrate21.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellrate21.setBorder(Rectangle.NO_BORDER);
            calulationtable.addCell(cellrate21);

            Phrase phrate200 = fontselector.process("Service Tax(D):");
            PdfPCell cellrate200 = new PdfPCell(new Paragraph(phrate200));
            cellrate200.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellrate200.setBorder(Rectangle.NO_BORDER);
            cellrate200.setColspan(2);
            calulationtable.addCell(cellrate200);
            Phrase phrate210 = fontselector.process(decimalFormat.format(Math.round(totalservicetax)));
            PdfPCell cellrate210 = new PdfPCell(new Paragraph(phrate210));
            cellrate210.setColspan(2);
            cellrate210.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellrate210.setBorder(Rectangle.NO_BORDER);
            calulationtable.addCell(cellrate210);
            Phrase phrate211 = fontselectorsale.process("Paid Amount(B=C-D):");
            PdfPCell cellrate211 = new PdfPCell(new Paragraph(phrate211));
            cellrate211.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellrate211.setBorder(Rectangle.NO_BORDER);
            cellrate211.setColspan(2);
            calulationtable.addCell(cellrate211);
            Phrase phrate212 = fontselectorsale.process(decimalFormat.format(Math.round(Paid_amount - totalservicetax)));
            PdfPCell cellrate212 = new PdfPCell(new Paragraph(phrate212));
            cellrate212.setColspan(2);
            cellrate212.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellrate212.setBorder(Rectangle.NO_BORDER);
            calulationtable.addCell(cellrate212);
            calulationtable.addCell(cellrate11);
            Phrase phrate22 = fontselectorsale.process("Total Payable Amount(A-B):");
            PdfPCell cellrate22 = new PdfPCell(new Paragraph(phrate22));
            //cell.setBorder(Rectangle.NO_BORDER);
            cellrate22.setColspan(3);
            cellrate22.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellrate22.setBorder(Rectangle.NO_BORDER);
            calulationtable.addCell(cellrate22);
            //total Payable Amount
            Phrase phrate23 = fontselectorsale.process(decimalFormat.format(Math.round(Payable_amount + totalservicetax)));
            PdfPCell cellrate23 = new PdfPCell(new Paragraph(phrate23));
            //cell.setBorder(Rectangle.NO_BORDER);
            cellrate23.setColspan(1);
            cellrate23.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellrate23.setBorder(Rectangle.NO_BORDER);
            calulationtable.addCell(cellrate23);
            ////./////// nested calculation table
            nestedcalulationtable.addCell(calulationtable);
            nestedcalulationtable.addCell(other_charge_table);
            document.add(table2);
            table3.addCell(tableflat);
            table3.addCell(plctable);
            table3.addCell(celln1);
            table3.addCell(celln1);
            document.add(table3);
            document.add(nestedcalulationtable);

            //*******************************************************************second Report1 starts here****************************************
            //Regarding  Payment Schedule
            document.newPage();//add new page here
            float[] widths10 = {1.4f, 5.5f, 1.8f, 2.0f, 2.0f, 1.5f, 2.0f, 1.8f, 1.5f, 2.0f};
            PdfPTable report2nd = new PdfPTable(widths10);
            Phrase phr2 = fontselectorhd.process("Payment Schedule");
            PdfPCell cellr2 = new PdfPCell(new Paragraph(phr2));
            cellr2.setColspan(10);
            cellr2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellr2.setBorder(Rectangle.NO_BORDER);
            report2nd.addCell(cellr2);
            Phrase phr3 = fontselectorhd.process("");
            PdfPCell cellr3 = new PdfPCell(new Paragraph(phr3));//cellr3 is used for giving space in second Report1
            cellr3.setColspan(10);
            cellr3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellr3.setBorder(Rectangle.NO_BORDER);
            report2nd.addCell(cellr3);
            report2nd.addCell(cellr3);
            Phrase phr10 = fontselector.process("Plan Name :" + bookingDetails.fullPlanName + "(" + bookingDetails.planname + ")");
            PdfPCell cellr10 = new PdfPCell(new Paragraph(phr10));
            cellr10.setColspan(6);
            cellr10.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellr10.setBorder(Rectangle.NO_BORDER);
            report2nd.addCell(cellr10);
            Phrase phr6 = fontselector.process("Registartion No.:" + fbsBooking.getRegNumber());
            PdfPCell cellr6 = new PdfPCell(new Paragraph(phr6));
            cellr6.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellr6.setColspan(6);
            cellr6.setBorder(Rectangle.NO_BORDER);
            report2nd.addCell(cellr6);
            Phrase phr4 = fontselector.process("Project Name :" + name);
            PdfPCell cellr4 = new PdfPCell(new Paragraph(phr4));
            cellr4.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellr4.setColspan(4);
            cellr4.setBorder(Rectangle.NO_BORDER);
            report2nd.addCell(cellr4);
            Phrase phr8 = fontselector.process("Applicant Name :" + applicantDetails.Applicant_name);
            PdfPCell cellr8 = new PdfPCell(new Paragraph(phr8));
            cellr8.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellr8.setColspan(6);
            cellr8.setBorder(Rectangle.NO_BORDER);
            report2nd.addCell(cellr8);
            report2nd.addCell(cellr3);
            report2nd.addCell(cellr3);
            report2nd.addCell(cellr3);
//1
            Phrase phr11 = fontselector.process("SR.NO.");
            PdfPCell cellr11 = new PdfPCell(new Paragraph(phr11));
            cellr11.setHorizontalAlignment(Element.ALIGN_CENTER);
            report2nd.addCell(cellr11);
//2
            Phrase phr12 = fontselector.process("Description");
            PdfPCell cellr12 = new PdfPCell(new Paragraph(phr12));
            cellr12.setHorizontalAlignment(Element.ALIGN_CENTER);
            report2nd.addCell(cellr12);
//3
            Phrase phr13 = fontselector.process("Due Date");
            PdfPCell cellr13 = new PdfPCell(new Paragraph(phr13));
            cellr13.setHorizontalAlignment(Element.ALIGN_CENTER);
            report2nd.addCell(cellr13);
//4
            Phrase phrbsp = fontselector.process("Installment Amt.");
            PdfPCell cellrbsp = new PdfPCell(new Paragraph(phrbsp));
            cellrbsp.setHorizontalAlignment(Element.ALIGN_CENTER);
            report2nd.addCell(cellrbsp);
//5
            Phrase phr14 = fontselector.process("Other Charges");
            PdfPCell cellr14 = new PdfPCell(new Paragraph(phr14));
            cellr14.setHorizontalAlignment(Element.ALIGN_CENTER);
            report2nd.addCell(cellr14);
//6
            Phrase phr15 = fontselector.process("PLC");
            PdfPCell cellr15 = new PdfPCell(new Paragraph(phr15));
            cellr15.setHorizontalAlignment(Element.ALIGN_CENTER);
            report2nd.addCell(cellr15);
            //7
            Phrase phr16 = fontselector.process(" Payable Amt.");
            PdfPCell cellr16 = new PdfPCell(new Paragraph(phr16));
            cellr16.setHorizontalAlignment(Element.ALIGN_CENTER);
            report2nd.addCell(cellr16);
//7
            ///*************************************************
            //named query for finding planId and servicetax
            Query query = entityManager.createNamedQuery("FbsPlanname.findByFkProjIdAndPlanName");
            query.setParameter("fkProjId", Integer.parseInt(projectid.trim()));
            query.setParameter("planName", bookingDetails.planname.trim().toString());
            List<FbsPlanname> fbsPlanName1 = query.getResultList();
            int size1 = fbsPlanName1.size();
            System.out.print(fbsPlanName1.size());
            for (int k = 0; k < fbsPlanName1.size(); k++) {
                plan_id = fbsPlanName1.get(k).getPlanId().toString();
            }
//8
            Phrase paidamount = fontselector.process("Recieved Amt.");
            PdfPCell cellpaidamount = new PdfPCell(new Paragraph(paidamount));
            cellpaidamount.setHorizontalAlignment(Element.ALIGN_CENTER);
            report2nd.addCell(cellpaidamount);
            //9
            Phrase phrcredit = fontselector.process("Credit");
            PdfPCell credit = new PdfPCell(new Paragraph(phrcredit));
            cellr11.setHorizontalAlignment(Element.ALIGN_CENTER);
            report2nd.addCell(credit);
            //10
            Phrase amountos = fontselector.process("Due Amt.");
            PdfPCell cellamountos = new PdfPCell(new Paragraph(amountos));
            cellamountos.setHorizontalAlignment(Element.ALIGN_CENTER);
            report2nd.addCell(cellamountos);
            int i = 1;
            Query query1 = entityManager.createNamedQuery("FbsPayplan.findByPlanIdAndProjectId");
            query1.setParameter("fkProjId", Integer.parseInt(projectid.trim()));
            query1.setParameter("fkPlanId", Integer.parseInt(plan_id));
            List<FbsPayplan> fbsPayplan = query1.getResultList();
            int size2 = fbsPayplan.size();
            long sumofOutstandingAmount = 0;
            long outStandingAmount = 0;
            long creditAmount = 0;
            long amountReceived = 0;
            long sumOfCredit = 0;
            long amount1 = 0;
            long lastcredit = 0;
            FontSelector fontselectordes = new FontSelector();
            fontselectordes.addFont(new Font(Font.TIMES_ROMAN, 7, Font.BOLD));
            //this is the main loop for  payplandetails
            for (int k = 0; k < fbsPayplan.size(); k++) {

                long totalPaidAmount = 0;
                String plan_desc = "";
                long installment_amount = 0;
                long plc_percentage = 0;
                plan_desc = fbsPayplan.get(k).getPlanDesc().toString().trim();
                if (plan_desc == null) {
                    plan_desc = " ";
                }
//1
                Phrase phr17 = fontselector.process(String.valueOf(i));
                PdfPCell cellr17 = new PdfPCell(new Paragraph(phr17));
                cellr17.setHorizontalAlignment(Element.ALIGN_CENTER);
                report2nd.addCell(cellr17);
//2
                Phrase phr18 = fontselectordes.process(plan_desc.toUpperCase());
                PdfPCell cellr18 = new PdfPCell(new Paragraph(phr18));
                cellr18.setHorizontalAlignment(Element.ALIGN_LEFT);
                report2nd.addCell(cellr18);

                //********************calculation*****************************************************************************
                percentage = fbsPayplan.get(k).getPercentage();

                netBasicSaleprice = (netBasicSalePrice1 * percentage) / 100;
                totalBasicSalePrice = totalBasicSalePrice + netBasicSaleprice;
                plc_percentage = (totalPlc * percentage) / 100;
                totalPlcPercentage = totalPlcPercentage + plc_percentage;
                installment_amount = netBasicSaleprice + plc_percentage;//installment amount for billperiod
                sumofinstallmentamount = sumofinstallmentamount + installment_amount;

                //generate billperiod using ReportHelper Class
                listBillperiod = reporthelper.findBillperiod(Integer.parseInt(projectid.trim()), Integer.parseInt(plan_id), k);
                int sizeOflistBillperiod = listBillperiod.size();
                Date startingbillDate = null;
                Date endDate = null;
                Date firstDate = null;
                Date lastDueDate = null;
                if (k == 0 && sizeOflistBillperiod == 1) {
                    firstDate = listBillperiod.get(0);
                    System.out.println("first date" + firstDate.toString());
                }
                if (sizeOflistBillperiod == 2) {
                    startingbillDate = listBillperiod.get(0);//starting billperiod
                    endDate = listBillperiod.get(1);
                    System.out.println("stratingbilling date" + startingbillDate.toString() + "enddate>>>>>>>>>>" + endDate);
                } else if (k != 0 && sizeOflistBillperiod == 1) {
                    startingbillDate = listBillperiod.get(0);
                    lastDueDate = listBillperiod.get(0);
                    System.out.println("stratingbilling date" + startingbillDate.toString());
                    System.out.println("last due date" + lastDueDate.toString());
                }

                if (k == 0) {
                    totalPaidAmount = reporthelper.findPayment(unitCode.trim(), projectid);//findPayment(startingbillDate, endDate, firstDate, unitCode.trim(), projectid);//Get Total Paid Amount From ReportHelper Class//
                } else {
                    totalPaidAmount = 0;
                }
                System.out.println("total paid Amount in report " + totalPaidAmount);
                //totalPaidAmount=totalPaidAmount-(totalPaidAmount*fbsPlanNameFacade.find(Integer.parseInt(plan_id)).getDiscount())/100;
                System.out.println("total paid Amount in report " + totalPaidAmount);
                //get paid Amount Starts Here**************************************
                // paid amount and outstanding amount

                OutstandingAmount outstandingamount = new OutstandingAmount();

                if (k == fbsPayplan.size() - 1) {
                    totalOtherCharges += totalParkingCharges;
                    sumofinstallmentamount = sumofinstallmentamount + totalOtherCharges;
                    installment_amount = installment_amount + totalOtherCharges;
                }
                if (startingbillDate != null || firstDate != null || endDate != null || lastDueDate != null) {
                    //long install=outstandingamount.installmentAmountcaluculation(installment_amount);
                    // System.out.println("install"+install);
                    OutstandingAmount outstandingamount1 = outstandingamount.findOutStandingAndCreditAmount(installment_amount, totalPaidAmount, outStandingAmount, creditAmount, startingbillDate, endDate);
                    outStandingAmount = outstandingamount1.outstandingAmount;

                    sumofOutstandingAmount = sumofOutstandingAmount + outStandingAmount;
                    creditAmount = outstandingamount1.creditAmount;
                    sumOfCredit = sumOfCredit + creditAmount;

                    amountReceived = outstandingamount1.amountRecevied;
                    totalreceivedamount = totalreceivedamount + amountReceived;
                    temp++;
                } else {
                    temp++;
                    creditAmount = 0;
                    outStandingAmount = 0;
                    amountReceived = 0;
                }
                //  }
                //3
                if (firstDate != null) {                   //for first due date
                    String dueDate = formatter.format(firstDate);
                    Phrase phr19 = fontselector.process(dueDate);
                    PdfPCell cellr19 = new PdfPCell(new Paragraph(phr19));
                    cellr19.setHorizontalAlignment(Element.ALIGN_CENTER);
                    report2nd.addCell(cellr19);
                } else if (lastDueDate != null) {
                    String dueDate = formatter.format(listBillperiod.get(0));
                    Phrase phr19 = fontselector.process(dueDate);
                    PdfPCell cellr19 = new PdfPCell(new Paragraph(phr19));
                    cellr19.setHorizontalAlignment(Element.ALIGN_CENTER);
                    report2nd.addCell(cellr19);
                } else if (endDate != null) {
                    String dueDate = formatter.format(endDate);
                    Phrase phr19 = fontselector.process(dueDate);
                    // Phrase phr191 = fontselector.process("N/A");
                    PdfPCell cellr191 = new PdfPCell(new Paragraph(phr19));
                    cellr191.setHorizontalAlignment(Element.ALIGN_CENTER);
                    report2nd.addCell(cellr191);

                } else {
                    Phrase phr191 = fontselector.process("N/A");
                    PdfPCell cellr191 = new PdfPCell(new Paragraph(phr191));
                    cellr191.setHorizontalAlignment(Element.ALIGN_CENTER);
                    report2nd.addCell(cellr191);
                }
//4
                Phrase phr20 = fontselector.process(String.valueOf(netBasicSaleprice));
                PdfPCell cellr20 = new PdfPCell(new Paragraph(phr20));
                cellr20.setHorizontalAlignment(Element.ALIGN_RIGHT);
                report2nd.addCell(cellr20);
//5 other Charges
                if (k == fbsPayplan.size() - 1) {

                    Phrase phr21 = fontselector.process(String.valueOf(totalOtherCharges));
                    PdfPCell cellr21 = new PdfPCell(new Paragraph(phr21));
                    cellr21.setHorizontalAlignment(Element.ALIGN_CENTER);
                    report2nd.addCell(cellr21);
                } else {
                    Phrase phr21 = fontselector.process("N/A");
                    PdfPCell cellr21 = new PdfPCell(new Paragraph(phr21));
                    cellr21.setHorizontalAlignment(Element.ALIGN_CENTER);
                    report2nd.addCell(cellr21);
                }
//6 plc percent
                Phrase phr22 = fontselector.process(String.valueOf(plc_percentage));
                PdfPCell cellr22 = new PdfPCell(new Paragraph(phr22));
                cellr22.setHorizontalAlignment(Element.ALIGN_RIGHT);
                report2nd.addCell(cellr22);
//7installment amount
                if (k == fbsPayplan.size() - 1) {
                    Phrase phiam = fontselector.process(String.valueOf(installment_amount));
                    PdfPCell celliam = new PdfPCell(new Paragraph(phiam));
                    celliam.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    report2nd.addCell(celliam);
                } else {
                    Phrase phiam = fontselector.process(String.valueOf(installment_amount));
                    PdfPCell celliam = new PdfPCell(new Paragraph(phiam));
                    celliam.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    report2nd.addCell(celliam);

                }
                //8 amount recieved
                Phrase paidamt1 = fontselector.process(String.valueOf(amountReceived));
                PdfPCell cellpaidamt1 = new PdfPCell(new Paragraph(paidamt1));
                cellpaidamt1.setHorizontalAlignment(Element.ALIGN_RIGHT);
                report2nd.addCell(cellpaidamt1);
//9 
                Date currentdate = new Date();
                Phrase paidamtcredit = null;//fontselector.process(String.valueOf(creditAmount));

                if (startingbillDate != null) {
                    if ((startingbillDate.getMonth() == (currentdate.getMonth())) && startingbillDate.getYear() == currentdate.getYear()) {
                        if (creditAmount != 0) {
                            lastcredit = creditAmount;
                        }

                        System.out.println("credit amounnt " + creditAmount + "last credit " + lastcredit);
                        paidamtcredit = fontselector.process(String.valueOf(creditAmount));

                    } else {
                        paidamtcredit = fontselector.process(String.valueOf(0));
                    }
                } else {
                    paidamtcredit = fontselector.process(String.valueOf(000));
                }
                if (endDate != null) {
                    if ((endDate.getMonth() == (currentdate.getMonth())) && startingbillDate.getYear() == currentdate.getYear()) {
                        if (creditAmount != 0) {
                            lastcredit = creditAmount;
                        }
                        System.out.println("credit amounnt " + creditAmount + "last credit " + lastcredit);
                        paidamtcredit = fontselector.process(String.valueOf(creditAmount));

                    } else {
                        paidamtcredit = fontselector.process(String.valueOf(0));
                    }
                } else {

                    outStandingAmount = OutstandingAmount.storeoutstand;

                }
                if (endDate != null && startingbillDate != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("MM");
                    String endDateMonth = sdf.format(endDate);
                    String startMonth = sdf.format(startingbillDate);
                    String currentMonth = sdf.format(new Date());
                    if ((Integer.parseInt(endDateMonth) > Integer.parseInt(currentMonth)) && (Integer.parseInt(startMonth) < Integer.parseInt(currentMonth))) {
                    if (creditAmount != 0) {
                            lastcredit = creditAmount;
                        }
                        System.out.println("credit amounnt " + creditAmount + "last credit " + lastcredit);
                        paidamtcredit = fontselector.process(String.valueOf(creditAmount));

                    } else {
                        paidamtcredit = fontselector.process(String.valueOf(0));
                    }    
                    }



                PdfPCell cellcredit = new PdfPCell(new Paragraph(paidamtcredit));
                cellcredit.setHorizontalAlignment(Element.ALIGN_RIGHT);
                report2nd.addCell(cellcredit);
//10
                Phrase amountos1 = fontselector.process(String.valueOf(outStandingAmount));
                PdfPCell cellamountos1 = new PdfPCell(new Paragraph(amountos1));
                cellamountos1.setHorizontalAlignment(Element.ALIGN_RIGHT);
                report2nd.addCell(cellamountos1);
                i++;
            }
//1
            Phrase phr23 = fontselectorhd3.process("Total");
            PdfPCell cellr23 = new PdfPCell(new Paragraph(phr23));
            cellr23.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellr23.setColspan(3);
            report2nd.addCell(cellr23);
            //2
            //4  Basic price
            Phrase phrbp = fontselectorhd3.process(String.valueOf(totalBasicSalePrice));
            PdfPCell cellbp = new PdfPCell(new Paragraph(phrbp));
            cellbp.setHorizontalAlignment(Element.ALIGN_RIGHT);
            report2nd.addCell(cellbp);
            //5
            Phrase phroc = fontselectorhd3.process(String.valueOf(totalOtherCharges));
            PdfPCell celloch = new PdfPCell(new Paragraph(phroc));
            celloch.setHorizontalAlignment(Element.ALIGN_CENTER);
            report2nd.addCell(celloch);
            //6 plc
            Phrase phrtplcp = fontselectorhd3.process(String.valueOf(totalPlcPercentage));
            PdfPCell cellplcp = new PdfPCell(new Paragraph(phrtplcp));
            cellplcp.setHorizontalAlignment(Element.ALIGN_RIGHT);
            report2nd.addCell(cellplcp);
            //7 totalamountinstallment
            Phrase phttiamt = fontselectorhd3.process(String.valueOf(sumofinstallmentamount));
            PdfPCell cellttiamt = new PdfPCell(new Paragraph(phttiamt));
            cellttiamt.setHorizontalAlignment(Element.ALIGN_RIGHT);
            report2nd.addCell(cellttiamt);
            //8
            Phrase paidamt2 = fontselectorhd3.process(String.valueOf(totalreceivedamount));
            PdfPCell cellpaidamt2 = new PdfPCell(new Paragraph(paidamt2));
            cellpaidamt2.setHorizontalAlignment(Element.ALIGN_RIGHT);
            report2nd.addCell(cellpaidamt2);
            //9


            Phrase paidcredit = fontselectorhd3.process(String.valueOf(lastcredit));
            PdfPCell cellpaidcredit = new PdfPCell(new Paragraph(paidcredit));
            cellpaidcredit.setHorizontalAlignment(Element.ALIGN_RIGHT);
            report2nd.addCell(cellpaidcredit);
            //10
            Phrase amtos = fontselectorhd3.process(String.valueOf(outStandingAmount));
            PdfPCell cellamtos = new PdfPCell(new Paragraph(amtos));
            cellamtos.setHorizontalAlignment(Element.ALIGN_RIGHT);
            report2nd.addCell(cellamtos);
            float[] widths = {2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 4.0f, 4.0f, 2.0f, 2.0f, 2.5f};//this table is used for payment Information
            List<FbsPayment> fbsPaymentInformation = reporthelper.findPaymentInformation(unitCode);//this object contain information about
            PdfPTable report3 = new PdfPTable(widths);
            Phrase paymentInformation1 = fontselector.process("");
            PdfPCell cellpaymentInformation1 = new PdfPCell(new Paragraph(paymentInformation1));
            cellpaymentInformation1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellpaymentInformation1.setColspan(12);
            cellpaymentInformation1.setBorder(Rectangle.NO_BORDER);
            report3.addCell(cellpaymentInformation1);
            report3.addCell(cellpaymentInformation1);

            //Regarding Payment information
            Phrase paymentInformation = fontselectorhd.process("Payment Information");
            PdfPCell cellpaymentInformation = new PdfPCell(new Paragraph(paymentInformation));
            cellpaymentInformation.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellpaymentInformation.setColspan(12);
            cellpaymentInformation.setBorder(Rectangle.NO_BORDER);
            report3.addCell(cellpaymentInformation);
            report3.addCell(cellpaymentInformation1);
            report3.addCell(cellpaymentInformation1);
            Phrase paymentInformation2 = fontselector.process("SR.NO.");
            PdfPCell cellpaymentInformation2 = new PdfPCell(new Paragraph(paymentInformation2));
            cellpaymentInformation2.setHorizontalAlignment(Element.ALIGN_CENTER);
            report3.addCell(cellpaymentInformation2);
            Phrase paymentInformation3 = fontselector.process("Receipt No.");
            PdfPCell cellpaymentInformation3 = new PdfPCell(new Paragraph(paymentInformation3));
            cellpaymentInformation3.setHorizontalAlignment(Element.ALIGN_CENTER);
            report3.addCell(cellpaymentInformation3);
            Phrase paymentInformation4 = fontselector.process("Receipt Date");
            PdfPCell cellpaymentInformation4 = new PdfPCell(new Paragraph(paymentInformation4));
            cellpaymentInformation4.setHorizontalAlignment(Element.ALIGN_CENTER);
            report3.addCell(cellpaymentInformation4);
            Phrase paymentInformation5 = fontselector.process("Payment Mode");
            PdfPCell cellpaymentInformation5 = new PdfPCell(new Paragraph(paymentInformation5));
            cellpaymentInformation5.setHorizontalAlignment(Element.ALIGN_CENTER);
            report3.addCell(cellpaymentInformation5);
            Phrase paymentInformation6 = fontselector.process("Cheque No.");
            PdfPCell cellpaymentInformation6 = new PdfPCell(new Paragraph(paymentInformation6));
            cellpaymentInformation6.setHorizontalAlignment(Element.ALIGN_CENTER);
            report3.addCell(cellpaymentInformation6);
            Phrase paymentInformation7 = fontselector.process("Cheque Date");
            PdfPCell cellpaymentInformation7 = new PdfPCell(new Paragraph(paymentInformation7));
            cellpaymentInformation7.setHorizontalAlignment(Element.ALIGN_CENTER);
            report3.addCell(cellpaymentInformation7);
            Phrase paymentInformation8 = fontselector.process("Drawn On");
            PdfPCell cellpaymentInformation8 = new PdfPCell(new Paragraph(paymentInformation8));
            cellpaymentInformation8.setHorizontalAlignment(Element.ALIGN_CENTER);
            report3.addCell(cellpaymentInformation8);
            Phrase paymentInformation9 = fontselector.process("Clearing Bank");
            PdfPCell cellpaymentInformation9 = new PdfPCell(new Paragraph(paymentInformation9));
            cellpaymentInformation9.setHorizontalAlignment(Element.ALIGN_CENTER);
            report3.addCell(cellpaymentInformation9);
            Phrase paymentInformation10 = fontselector.process("Amount");
            PdfPCell cellpaymentInformation10 = new PdfPCell(new Paragraph(paymentInformation10));
            cellpaymentInformation10.setHorizontalAlignment(Element.ALIGN_CENTER);
            report3.addCell(cellpaymentInformation10);

            Phrase paymentInformation12 = fontselector.process("Service Tax");
            PdfPCell cellpaymentInformation12 = new PdfPCell(new Paragraph(paymentInformation12));
            cellpaymentInformation12.setHorizontalAlignment(Element.ALIGN_CENTER);
            report3.addCell(cellpaymentInformation12);
            Phrase paymentInformation11 = fontselector.process("Payment Status");
            PdfPCell cellpaymentInformation11 = new PdfPCell(new Paragraph(paymentInformation11));
            cellpaymentInformation11.setHorizontalAlignment(Element.ALIGN_CENTER);
            report3.addCell(cellpaymentInformation11);
            long clearAmount = 0;
            long pendingAmount = 0;
            long cashAmount = 0;
            double amountreceived = 0;
            double totalServiceTax = 0;
            for (int m = 0; m < fbsPaymentInformation.size(); m++) {
                Phrase paymentInformationc = fontselector.process(String.valueOf(m + 1));
                PdfPCell cellpaymentInformationc = new PdfPCell(new Paragraph(paymentInformationc));
                cellpaymentInformationc.setHorizontalAlignment(Element.ALIGN_CENTER);
                report3.addCell(cellpaymentInformationc);
                String receiptno = fbsPaymentInformation.get(m).getPaymentId().toString();
                if (receiptno == null) {
                    receiptno = "";
                }
                Phrase paymentInformationc1 = fontselector.process(receiptno);//
                PdfPCell cellpaymentInformationc1 = new PdfPCell(new Paragraph(paymentInformationc1));
                cellpaymentInformationc1.setHorizontalAlignment(Element.ALIGN_CENTER);
                report3.addCell(cellpaymentInformationc1);

                Date receiptDate = fbsPaymentInformation.get(m).getPaymentDate();
                if (receiptDate != null) {
                    Phrase paymentInformationc4 = fontselector.process(formatter.format(receiptDate));//
                    PdfPCell cellpaymentInformationc4 = new PdfPCell(new Paragraph(paymentInformationc4));
                    cellpaymentInformationc4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    report3.addCell(cellpaymentInformationc4);
                } else {
                    Phrase paymentInformationc4 = fontselector.process("N/A");//
                    PdfPCell cellpaymentInformationc4 = new PdfPCell(new Paragraph(paymentInformationc4));
                    cellpaymentInformationc4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    report3.addCell(cellpaymentInformationc4);
                }
                String modeOfPayment = fbsPaymentInformation.get(m).getPaymentMode();
                if (modeOfPayment == null) {
                    modeOfPayment = "";
                }
                Phrase paymentInformationc5 = fontselector.process(modeOfPayment);
                PdfPCell cellpaymentInformationc5 = new PdfPCell(new Paragraph(paymentInformationc5));
                cellpaymentInformationc5.setHorizontalAlignment(Element.ALIGN_CENTER);
                report3.addCell(cellpaymentInformationc5);
                String chequeNo = fbsPaymentInformation.get(m).getChequeNo();
                if (chequeNo == null || chequeNo.equals("")) {
                    Phrase paymentInformationc6 = fontselector.process("N/A");
                    PdfPCell cellpaymentInformationc6 = new PdfPCell(new Paragraph(paymentInformationc6));
                    cellpaymentInformationc6.setHorizontalAlignment(Element.ALIGN_CENTER);
                    report3.addCell(cellpaymentInformationc6);
                } else {
                    Phrase paymentInformationc6 = fontselector.process(chequeNo);
                    PdfPCell cellpaymentInformationc6 = new PdfPCell(new Paragraph(paymentInformationc6));
                    cellpaymentInformationc6.setHorizontalAlignment(Element.ALIGN_CENTER);
                    report3.addCell(cellpaymentInformationc6);
                }
                Date chequeDate = fbsPaymentInformation.get(m).getChequeDate();
                if (chequeDate != null) {
                    Phrase paymentInformationc7 = fontselector.process(formatter.format(chequeDate));
                    PdfPCell cellpaymentInformationc7 = new PdfPCell(new Paragraph(paymentInformationc7));
                    cellpaymentInformationc7.setHorizontalAlignment(Element.ALIGN_CENTER);
                    report3.addCell(cellpaymentInformationc7);
                } else {
                    Phrase paymentInformationc7 = fontselector.process("N/A");
                    PdfPCell cellpaymentInformationc7 = new PdfPCell(new Paragraph(paymentInformationc7));
                    cellpaymentInformationc7.setHorizontalAlignment(Element.ALIGN_CENTER);
                    report3.addCell(cellpaymentInformationc7);
                }
                String Drownon = fbsPaymentInformation.get(m).getDrawnOn();
                if (Drownon == null || Drownon.equals("")) {
                    Phrase paymentInformationc8 = fontselector.process("N/A");
                    PdfPCell cellpaymentInformationc8 = new PdfPCell(new Paragraph(paymentInformationc8));
                    cellpaymentInformationc8.setHorizontalAlignment(Element.ALIGN_CENTER);
                    report3.addCell(cellpaymentInformationc8);
                } else {
                    Phrase paymentInformationc8 = fontselector.process(Drownon);
                    PdfPCell cellpaymentInformationc8 = new PdfPCell(new Paragraph(paymentInformationc8));
                    cellpaymentInformationc8.setHorizontalAlignment(Element.ALIGN_CENTER);
                    report3.addCell(cellpaymentInformationc8);
                }
                String ClearingBankCode = new String();
                if (fbsPaymentInformation.get(m).getClearingBankId() == null) {
                    // ClearingBankCode = fbsPaymentInformation.get(m).getClearingBankId().toString();

                    Phrase paymentInformationc9 = fontselector.process("N/A");
                    PdfPCell cellpaymentInformationc9 = new PdfPCell(new Paragraph(paymentInformationc9));
                    cellpaymentInformationc9.setHorizontalAlignment(Element.ALIGN_CENTER);
                    report3.addCell(cellpaymentInformationc9);
                } else {
                    ClearingBankCode = fbsPaymentInformation.get(m).getClearingBankId().toString();
                    List<FbsPayment> bankName1 = (List) reporthelper.findBankName(Integer.parseInt(ClearingBankCode));//get Bank Name
                    int bankId = bankName1.get(0).getClearingBankId();
                    FbsBank fbsBank = new FbsBank();
                    fbsBank = fbsBankFacade.find(bankId);
                    Phrase paymentInformationc9 = fontselector.process(fbsBank.getBankName());
                    PdfPCell cellpaymentInformationc9 = new PdfPCell(new Paragraph(paymentInformationc9));
                    cellpaymentInformationc9.setHorizontalAlignment(Element.ALIGN_CENTER);
                    report3.addCell(cellpaymentInformationc9);
                }
                String PaidAmount = fbsPaymentInformation.get(m).getPaidAmount().toString();
                Phrase paymentInformationc10 = fontselector.process(PaidAmount);
                PdfPCell cellpaymentInformationc10 = new PdfPCell(new Paragraph(paymentInformationc10));
                cellpaymentInformationc10.setHorizontalAlignment(Element.ALIGN_RIGHT);
                report3.addCell(cellpaymentInformationc10);
                //  System.out.println("service tax id "+(fbsPaymentInformation.get(m).getServiceTax()));
                FbsServicetax servicetax = fbsServicetaxFacade.find(fbsPaymentInformation.get(m).getServiceTax());

                double t = (1 + (double) servicetax.getServicetax() / 100);

                //  System.out.println("ghdf"+servicetax.getServicetax()+" t is "+t);
                double recieved = fbsPaymentInformation.get(m).getPaidAmount() / t;

                //    System.out.println("recieved ............."+recieved);
                double tempservicetax = fbsPaymentInformation.get(m).getPaidAmount() - recieved;
                totalServiceTax = totalServiceTax + tempservicetax;
                String servicetaxamount = decimalFormat.format(Math.round(tempservicetax));
                Phrase paymentInformationc12 = fontselector.process(servicetaxamount);
                PdfPCell cellpaymentInformationc12 = new PdfPCell(new Paragraph(paymentInformationc12));
                cellpaymentInformationc12.setHorizontalAlignment(Element.ALIGN_RIGHT);
                report3.addCell(cellpaymentInformationc12);
                String status = fbsPaymentInformation.get(m).getChequeStatus();
                if (status != null) {
                    if (status.toUpperCase().equals("PENDING")) {
                        pendingAmount = pendingAmount + fbsPaymentInformation.get(m).getPaidAmount();
                        status = "Pending";
                    } else if (status.toUpperCase().equals("CLEARED")) {
                        status = "Cleared";
                        clearAmount = clearAmount + fbsPaymentInformation.get(m).getPaidAmount();
                    }
                } else {
                    status = "N/A";
                }
                Phrase paymentInformationc11 = fontselector.process(status);
                PdfPCell cellpaymentInformationc11 = new PdfPCell(new Paragraph(paymentInformationc11));
                cellpaymentInformationc11.setHorizontalAlignment(Element.ALIGN_CENTER);
                report3.addCell(cellpaymentInformationc11);


            }


            Phrase paymentInformationc11 = fontselectorhd2.process("Total Received Amount(A)");
            PdfPCell cellpaymentInformationc11 = new PdfPCell(new Paragraph(paymentInformationc11));
            cellpaymentInformationc11.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellpaymentInformationc11.setColspan(9);
            cellpaymentInformationc11.setHorizontalAlignment(Element.ALIGN_RIGHT);
            report3.addCell(cellpaymentInformationc11);
            Phrase paymentInformationc111 = fontselectorhd2.process(decimalFormat.format(clearAmount + pendingAmount + cashAmount));
            PdfPCell cellpaymentInformationc111 = new PdfPCell(new Paragraph(paymentInformationc111));
            cellpaymentInformationc111.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellpaymentInformationc111.setColspan(5);
            cellpaymentInformationc111.setHorizontalAlignment(Element.ALIGN_RIGHT);
            report3.addCell(cellpaymentInformationc111);
            Phrase paymentInformationc12 = fontselectorhd2.process("Total Service Tax(B)");
            PdfPCell cellpaymentInformationc12 = new PdfPCell(new Paragraph(paymentInformationc12));
            cellpaymentInformationc12.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellpaymentInformationc12.setColspan(9);
            cellpaymentInformationc12.setHorizontalAlignment(Element.ALIGN_RIGHT);
            report3.addCell(cellpaymentInformationc12);
            Phrase paymentInformationc112 = fontselectorhd2.process(decimalFormat.format(Math.round(totalServiceTax)));
            PdfPCell cellpaymentInformationc112 = new PdfPCell(new Paragraph(paymentInformationc112));
            cellpaymentInformationc112.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellpaymentInformationc112.setColspan(5);
            cellpaymentInformationc112.setHorizontalAlignment(Element.ALIGN_RIGHT);
            report3.addCell(cellpaymentInformationc112);
            Phrase paymentInformationc13 = fontselectorhd2.process("Total Paid Amount(A-B)");
            PdfPCell cellpaymentInformationc13 = new PdfPCell(new Paragraph(paymentInformationc13));
            cellpaymentInformationc13.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellpaymentInformationc13.setColspan(9);
            cellpaymentInformationc13.setHorizontalAlignment(Element.ALIGN_RIGHT);
            report3.addCell(cellpaymentInformationc13);
            Phrase paymentInformationc113 = fontselectorhd2.process(decimalFormat.format(Math.round((clearAmount + pendingAmount + cashAmount) - totalServiceTax)));
            PdfPCell cellpaymentInformationc113 = new PdfPCell(new Paragraph(paymentInformationc113));
            cellpaymentInformationc113.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellpaymentInformationc113.setColspan(5);
            cellpaymentInformationc113.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell28.setHorizontalAlignment(Element.ALIGN_RIGHT);
            report3.addCell(cellpaymentInformationc113);

            document.add(report2nd);
            document.add(report3);
            document.close();
            response.setHeader("Content-Disposition", " filename=Applicant-File-For-Registration-No." + fbsBooking.getRegNumber());
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setContentType("application/pdf");
            ServletOutputStream out = response.getOutputStream();
            baos.writeTo(out);
            out.flush();
        } catch (Exception ex) {
            response.sendRedirect("/FbsFaces/faces/jsfpages/common/errorPage.xhtml");
            System.out.println(ex);
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
}
