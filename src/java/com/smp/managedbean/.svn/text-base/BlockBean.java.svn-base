 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.managedbean;

import com.smp.entity.FbsBlock;
import com.smp.entity.FbsFlat;
import com.smp.entity.FbsFlatType;
import com.smp.entity.FbsPlc;
import com.smp.fbs.BlockInfo;
import com.smp.fbs.FlatInfo;
import com.smp.session.FbsBlockFacade;
import com.smp.session.FbsFlatTypeFacade;
import com.smp.session.FbsPlcFacade;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author smp7
 */
@ManagedBean(name = "blockBean")
@ApplicationScoped
@Stateless
public class BlockBean {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;
    @EJB
    FbsBlockFacade fbsBlockFacade;
    @EJB
    FbsFlatTypeFacade fbsFlatTypeFacade;
    @EJB
    FbsPlcFacade fbsPlcFacade;
    public List<FbsPlc> fbsPlc = new ArrayList<FbsPlc>();
    private FbsFlatType fbsFlatType = new FbsFlatType();
    public FbsBlock fbsBlock = new FbsBlock();
    public static List<FbsBlock> blockList;
    String projid = "26";
    private List<FbsBlock> list = new ArrayList<FbsBlock>();
    private List<FbsFlatType> flatTypeList = new ArrayList<FbsFlatType>();
    public List<FbsFlat> fbsFlatList = new ArrayList<FbsFlat>();
    private FbsFlat fbsFlat = new FbsFlat();
    int i = 0;
    boolean render0 = false;
    boolean render1 = false;
    boolean render2 = false;
    boolean render3 = false;
    boolean render4 = false;
    boolean render5 = false;
    boolean render6 = false;
    boolean render7 = false;
    boolean render8 = false;
    boolean render9 = false;
    String flatType0 = "";
    String flatType1 = "";
    String flatType2 = "";
    String flatType3 = "";
    String flatType4 = "";
    String flatType5 = "";
    String flatType6 = "";
    String flatType7 = "";
    String flatType8 = "";
    String flatType9 = "";
    String noOfFloor = "";
    String flatPerFloor = "";
    boolean temp;
    public ArrayList flatTypes;
    private String xmlFile1 = "";
    private int high = 0;
    private List<FbsFlat> refFlatList = new ArrayList<FbsFlat>();
    private ArrayList floorList = new ArrayList();
    private FlatInfo[] flatNoList;
    private FlatInfo flatInfo;
    private int count = 0;
    public ArrayList floorCountList = new ArrayList();
    public ArrayList flatCoutList = new ArrayList();
    public ArrayList<BlockInfo> blockInfoList;
    public BlockInfo blockInfo;
    public ArrayList<FbsFlat> fbsFlatList1;
    private boolean locked;
    private boolean unlocked;
    private TreeNode root;
    private TreeNode[] selectedNodes;
    private List<FbsPlc> node1List = new ArrayList<FbsPlc>();
    private List<TreeNode> subHeadChildList = new ArrayList<TreeNode>();
    String flatId;
    public static FbsPlc fbsPlc1 = new FbsPlc();
    public ArrayList al = new ArrayList();
    int blockId;
    String floorId;

    @PostConstruct
    public void populate() throws IOException {
        System.out.println("Block bean populate method is callled");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);
        blockList = new ArrayList();
        projid = (String) session.getAttribute("projId");
        if (projid != null) {
            blockList.clear();
            blockList = em.createNamedQuery("FbsBlock.findByFkProjId").setParameter("fkProjId", Integer.parseInt(projid)).getResultList();
            flatTypeList = em.createNamedQuery("FbsFlatType.findByFkProjId").setParameter("fkProjId", Integer.parseInt(projid)).getResultList();
            flatTypes = new ArrayList();
            for (int j = 0; j < flatTypeList.size(); j++) {
                flatTypes.add(new SelectItem(flatTypeList.get(j).getFlatTypeId(), flatTypeList.get(j).getFlatSpecification()));
            }
            blockInfoList = new ArrayList<BlockInfo>();
            for (int j = 0; j < blockList.size(); j++) {
                populateFloors(blockList.get(j));
                blockInfo = new BlockInfo();
                blockInfo.setBlockId(blockList.get(j).getBlockId());
                blockInfo.setBlockName(blockList.get(j).getBlockName());
                blockInfo.setNoOfFloors(String.valueOf(high + 1));
                blockInfo.setNoOfFlats(String.valueOf(count));
                if (blockList.get(j).getStatus().equals("lock")) {
                    blockInfo.setStatus(true);
                } else {
                    blockInfo.setStatus(false);
                }
                blockInfoList.add(blockInfo);


            }
        }
    }

    public void yahoo() {
        System.out.println("hureyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
    }

    public void plc(FbsFlat fbsFlat) {
        al.clear();
        flatId = fbsFlat.getFlatId().toString();
        blockId = fbsFlat.getBlockId();
        floorId = fbsFlat.getFloorId().toString();

        try {
            FbsBlock fbsBlock2 = fbsBlockFacade.find(blockId);
            xmlFile1 = fbsBlock2.getXmlFile();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(xmlFile1)));
            doc.getDocumentElement().normalize();
            Node block1 = doc.getFirstChild();
            NodeList floorList1 = block1.getChildNodes();
            for (int k = 0; k < floorList1.getLength(); k++) {
                Node floor = floorList1.item(k);
                if (floor.getNodeType() == Node.ELEMENT_NODE) {
                    Element floorElement = (Element) floor;
                    if (floorElement.getAttribute("floor_id").equals(floorId)) {
                        NodeList floorNoList = floorElement.getElementsByTagName("floor_number");
                        Node fnoElement = (Node) floorNoList.item(0);
                        NodeList flatList = floorElement.getElementsByTagName("flat");
                        for (int j = 0; j < flatList.getLength(); j++) {
                            Element flatElement = (Element) flatList.item(j);
                            if (flatElement.getAttribute("flat_id").equals(flatId)) {
                                NodeList plcList = flatElement.getElementsByTagName("plc");
                                System.out.println("hello smp" + plcList.getLength());
                                NodeList plcList1 = flatElement.getElementsByTagName("plc_id");
                                System.out.println("listing....>>" + plcList1.getLength());
                                for (int i = 0; i < plcList1.getLength(); i++) {
                                    fbsPlc1 = new FbsPlc();
                                    fbsPlc1 = (FbsPlc) em.createNamedQuery("FbsPlc.findByPlcId").setParameter("plcId", Integer.parseInt(plcList1.item(i).getTextContent())).getResultList().get(0);
                                    System.out.println("plcname..>" + fbsPlc1.getPlcName());
                                    al.add(i, fbsPlc1);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(QuickBookingBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("in plc function ....." + flatId);
        System.out.println("in plc function ......");
        root = new DefaultTreeNode("Root", null);
        fbsPlc = fbsPlcFacade.findAll();
        for (int i = 0; i < fbsPlc.size(); i++) {
            subHeadChildList.add(i, new DefaultTreeNode(fbsPlc.get(i).getPlcName(), root));
        }
    }

    public void add() {
        String flatId1 = this.flatId;

        System.out.println("fbsFlat..." + flatId1);

        try {
            int blockId1 = this.blockId;
            String floorId1 = this.floorId;

            FbsBlock fbsBlock2 = fbsBlockFacade.find(blockId1);
            xmlFile1 = fbsBlock2.getXmlFile();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(xmlFile1)));
            doc.getDocumentElement().normalize();
            Node block1 = doc.getFirstChild();
            // NodeList block = doc.getElementsByTagName("block");
            NodeList floorList1 = block1.getChildNodes();
            //NodeList floorList1 = block.item(0).getChildNodes();
            for (int k = 0; k < floorList1.getLength(); k++) {
                Node floor = floorList1.item(k);
                if (floor.getNodeType() == Node.ELEMENT_NODE) {
                    Element floorElement = (Element) floor;
                    if (floorElement.getAttribute("floor_id").equals(floorId1)) {
                        NodeList floorNoList = floorElement.getElementsByTagName("floor_number");
                        Node fnoElement = (Node) floorNoList.item(0);
                        NodeList flatList = floorElement.getElementsByTagName("flat");
                        for (int j = 0; j < flatList.getLength(); j++) {
                            Element flatElement = (Element) flatList.item(j);
                            if (flatElement.getAttribute("flat_id").equals(flatId1)) {
                                NodeList plcList = flatElement.getElementsByTagName("plc");
                                if (plcList.getLength() != 0) {
                                    Node plcElement = (Node) plcList.item(0);
                                    NodeList plcChilds = plcElement.getChildNodes();
                                    for (int i = 0; i < plcChilds.getLength(); i = i + 2) {
                                        plcElement.removeChild(plcChilds.item(i));
                                    }
                                    flatElement.removeChild(plcList.item(0));
                                    Node newNode = doc.createElement("plc");
                                    for (TreeNode node : selectedNodes) {
                                        System.out.println("in else part of checking plc....>");
                                        Node childNode = doc.createElement("plc_id");
                                        FbsPlc fbsPlcTemp = (FbsPlc) em.createNamedQuery("FbsPlc.findByPlcName").setParameter("plcName", node.getData().toString().trim()).getResultList().get(0);
                                        System.out.println("plc id ->" + fbsPlcTemp.getPlcId());
                                        childNode.setTextContent(String.valueOf(fbsPlcTemp.getPlcId()));
                                        newNode.appendChild(childNode);
                                    }
                                    flatElement.appendChild(newNode);
                                } else {
                                    Node newNode = doc.createElement("plc");
                                    for (TreeNode node : selectedNodes) {
                                        System.out.println("in else part of checking plc....>");
                                        Node childNode = doc.createElement("plc_id");
                                        FbsPlc fbsPlcTemp = (FbsPlc) em.createNamedQuery("FbsPlc.findByPlcName").setParameter("plcName", node.getData().toString().trim()).getResultList().get(0);
                                        System.out.println("plc id ->" + fbsPlcTemp.getPlcId());
                                        childNode.setTextContent(String.valueOf(fbsPlcTemp.getPlcId()));
                                        newNode.appendChild(childNode);
                                    }
                                    flatElement.appendChild(newNode);
                                }
                            }
                        }
                    }
                }
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StreamResult result = new StreamResult(new StringWriter());
            DOMSource source = new DOMSource(doc);
            transformer.transform(source, result);
            String xmlString = result.getWriter().toString();
            fbsBlock2.setXmlFile(xmlString);
            fbsBlockFacade.edit(fbsBlock2);
            populateBlock(fbsBlock2.getBlockId());
            FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/ProjectSetting/updateBlock.xhtml");
        } catch (Exception ex) {
            Logger.getLogger(QuickBookingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addBlock() throws IOException {

        String flatType[] = new String[i];
        for (int k = 0; k < i; k++) {
            if (k == 0) {
                flatType[k] = flatType0;
            } else if (k == 1) {
                flatType[k] = flatType1;
            } else if (k == 2) {
                flatType[k] = flatType2;
            } else if (k == 3) {
                flatType[k] = flatType3;
            } else if (k == 4) {
                flatType[k] = flatType4;
            } else if (k == 5) {
                flatType[k] = flatType5;
            } else if (k == 6) {
                flatType[k] = flatType6;
            } else if (k == 7) {
                flatType[k] = flatType7;
            } else if (k == 8) {
                flatType[k] = flatType8;
            } else if (k == 9) {
                flatType[k] = flatType9;
            }

        }
        fbsBlock.setFkProjId(Integer.parseInt(projid));
        fbsBlock.setBlockName(fbsBlock.getBlockName());
        fbsBlock.setStatus("unlock");
        fbsBlockFacade.create(fbsBlock);
        genrateXml(flatType);
        populate();
    }

    public void editBlockName(FbsBlock fbsBlock) {
        fbsBlockFacade.edit(fbsBlock);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Congrates! Block Name Successfully Updated"));
    }

    public void genrateXml(String[] flatType) {
        try {
            List<FbsBlock> list1 = fbsBlockFacade.findAll();
            int[] blockId1 = new int[list1.size()];
            for (int l = 0; l < list1.size(); l++) {
                blockId1[l] = list1.get(l).getBlockId();
            }
            Arrays.sort(blockId1);
            int blockId = blockId1[list1.size() - 1];
            String xmlFile = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                    + "<block block_id=\"" + blockId + "\">";
            for (int ii = 0; ii < Integer.parseInt(this.noOfFloor); ii++) {
                xmlFile = xmlFile + "<floor floor_id=\"" + projid + blockId + ii + "\">"
                        + "<floor_number>" + ii + "</floor_number>";
                for (int j = 0; j < this.i; j++) {
                    int t = j + 1;
                    xmlFile = xmlFile + "<flat flat_id=\"" + projid + blockId + ii + "0" + j + "\">"
                            + "<flattype flatTypeId=\"" + flatType[j] + "\">"
                            + "</flattype>";
                    if (t < 10) {
                        xmlFile = xmlFile + "<flatno flatNo=\"" + ii + "0" + j + "\">"
                                + "</flatno>";
                    } else {
                        xmlFile = xmlFile + "<flatno flatNo=\"" + ii + j + "\">"
                                + "</flatno>";

                    }
                    xmlFile = xmlFile + "</flat>";

                }
                xmlFile = xmlFile + "</floor>";
            }
            xmlFile = xmlFile + "</block>";
            //System.out.println(xmlFile);
            fbsBlock.setXmlFile(xmlFile);
            fbsBlock.setBlockId(blockId);
            fbsBlockFacade.edit(fbsBlock);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void editRow(BlockInfo blockInfo) throws IOException {
        fbsBlock = fbsBlockFacade.find(blockInfo.blockId);
        populateBlock(fbsBlock.getBlockId());
        FacesContext.getCurrentInstance().getExternalContext().redirect("/FbsFaces/faces/jsfpages/ProjectSetting/updateBlock.xhtml");
    }

    public String findPlcName(List fbsPlcList) {
        System.out.println("in findplcname option" + fbsPlcList);
        String plcName = "";
        if (fbsPlcList == null || fbsPlcList.isEmpty()) {
            plcName = "";
        } else {

            for (int i = 0; i < fbsPlcList.size(); i++) {
                fbsPlc1 = new FbsPlc();
                System.out.println("......................" + fbsPlcList.size());
                fbsPlc1 = (FbsPlc) em.createNamedQuery("FbsPlc.findByPlcId").setParameter("plcId", Integer.valueOf(fbsPlcList.get(i).toString())).getResultList().get(0);
                // System.out.println(fbsPlcList.get(i).toString());
                if (plcName.equals("")) {
                    plcName = fbsPlc1.getPlcName();
                } else {
                    plcName = plcName + ", " + fbsPlc1.getPlcName();
                }
            }
        }
        return plcName;
    }

    public void populateBlock(int blockId) {
        try {
            int l = 0;
            FbsBlock fbsBlock1 = fbsBlockFacade.find(blockId);

            fbsFlatList1 = new ArrayList();
            xmlFile1 = fbsBlock1.getXmlFile();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(xmlFile1)));
            doc.getDocumentElement().normalize();
            NodeList block = doc.getElementsByTagName("block");
            NodeList floorList1 = block.item(0).getChildNodes();
            for (int k = 0; k < floorList1.getLength(); k++) {
                Node floor = floorList1.item(k);
                if (floor.getNodeType() == Node.ELEMENT_NODE) {
                    Element floorElement = (Element) floor;
                    String floorId1 = floorElement.getAttribute("floor_id").trim();
                    NodeList floorNoList = floorElement.getElementsByTagName("floor_number");
                    Node fnoElement = (Node) floorNoList.item(0);
                    NodeList flatList = floorElement.getElementsByTagName("flat");
                    for (int j = 0; j < flatList.getLength(); j++) {

                        fbsFlat = new FbsFlat();
                        fbsFlat.setBlockId(fbsBlock.getBlockId());
                        fbsFlat.setBlockName(fbsBlock.getBlockName());
                        fbsFlat.setFloorNo(fnoElement.getTextContent());
                        fbsFlat.setFloorId(Long.parseLong(floorId1));
                        Element flatElement = (Element) flatList.item(j);
                        String flatId = flatElement.getAttribute("flat_id").trim();
                        fbsFlat.setFlatId(Long.parseLong(flatId));
                        NodeList flatTypeList1 = flatElement.getElementsByTagName("flattype");
                        Element typeElement = (Element) flatTypeList1.item(0);
                        fbsFlat.setFlatType(typeElement.getAttribute("flatTypeId").trim());
                        fbsFlatType = fbsFlatTypeFacade.find(Integer.parseInt(typeElement.getAttribute("flatTypeId").trim()));
                        fbsFlat.setFlatTypeSpecification(fbsFlatType.getFlatSpecification());
                        NodeList flatNoList1 = flatElement.getElementsByTagName("flatno");
                        Element noElement = (Element) flatNoList1.item(0);
                        fbsFlat.setFlatNo(noElement.getAttribute("flatNo").trim());

                        List plcIdList = new ArrayList();
                        NodeList plc = flatElement.getElementsByTagName("plc");
                        if (plc.getLength() != 0) {
                            NodeList plcList = plc.item(0).getChildNodes();


                            for (int a = 1; a < plcList.getLength(); a = a + 2) {

                                Node plcElement = plcList.item(a);
                                System.out.println("plc childs =========" + plcList.item(a).getTextContent());
                                //fbsFlat.setPlc(plcElement.getTextContent());
                                plcIdList.add(plcElement.getTextContent());
                                //System.out.println("plc in populate block method...>" + fbsFlat.getPlc());
                            }
                        } else {
                        }
                        fbsFlat.setFbsPlsList(plcIdList);
                        fbsFlatList1.add(l, fbsFlat);
                        l++;


                    }
                }


            }
        } catch (Exception ex) {
            Logger.getLogger(QuickBookingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editBlock(org.primefaces.event.RowEditEvent e) {
        fbsFlat = (FbsFlat) e.getObject();
        //System.out.println(fbsFlat.getBlockName() + "  " + fbsFlat.getFloorId() + " " + fbsFlat.getFloorNo() + "  " + fbsFlat.getFlatId() + " " + fbsFlat.getFlatNo() + " " + fbsFlat.getFlatTypeSpecification());
        try {
            fbsFlatList = new ArrayList();
            int blockId = fbsFlat.getBlockId();
            int l = 0;
            FbsBlock fbsBlock2 = fbsBlockFacade.find(blockId);
            xmlFile1 = fbsBlock2.getXmlFile();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(xmlFile1)));
            doc.getDocumentElement().normalize();
            Node block1 = doc.getFirstChild();
            // NodeList block = doc.getElementsByTagName("block");
            NodeList floorList1 = block1.getChildNodes();
            //NodeList floorList1 = block.item(0).getChildNodes();
            for (int k = 0; k < floorList1.getLength(); k++) {
                Node floor = floorList1.item(k);
                if (floor.getNodeType() == Node.ELEMENT_NODE) {
                    Element floorElement = (Element) floor;
                    if (floorElement.getAttribute("floor_id").equals(fbsFlat.getFloorId().toString())) {
                        NodeList floorNoList = floorElement.getElementsByTagName("floor_number");
                        Node fnoElement = (Node) floorNoList.item(0);
                        NodeList flatList = floorElement.getElementsByTagName("flat");
                        for (int j = 0; j < flatList.getLength(); j++) {

                            Element flatElement = (Element) flatList.item(j);
                            if (flatElement.getAttribute("flat_id").equals(fbsFlat.getFlatId().toString())) {

                                NodeList flatTypeList1 = flatElement.getElementsByTagName("flattype");
                                Node ft = flatTypeList1.item(0);
                                NamedNodeMap attr = ft.getAttributes();
                                Node nodeAttr = attr.getNamedItem("flatTypeId");
                                nodeAttr.setTextContent(fbsFlat.getFlatTypeSpecification());
                            }
                        }
                    }
                }
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StreamResult result = new StreamResult(new StringWriter());
            DOMSource source = new DOMSource(doc);
            transformer.transform(source, result);

            String xmlString = result.getWriter().toString();
            // System.out.println(xmlString);
            fbsBlock2.setXmlFile(xmlString);
            fbsBlockFacade.edit(fbsBlock2);
            populateBlock(fbsBlock2.getBlockId());


        } catch (Exception ex) {
            Logger.getLogger(QuickBookingBean.class.getName()).log(Level.SEVERE, null, ex);
        }



    }

    public void lockBlock(BlockInfo blockInfo) throws IOException {
        FbsBlock fbsBlock1 = new FbsBlock();
        fbsBlock1 = fbsBlockFacade.find(blockInfo.getBlockId());
        fbsBlock1.setStatus("lock");
        fbsBlockFacade.edit(fbsBlock1);
        populate();

    }

    public void deleteFlat(FbsFlat fbsFlat) {


        //System.out.println(fbsFlat.getBlockName() + "  " + fbsFlat.getFloorId() + " " + fbsFlat.getFloorNo() + "  " + fbsFlat.getFlatId() + " " + fbsFlat.getFlatNo() + " " + fbsFlat.getFlatTypeSpecification());
        try {
            fbsFlatList = new ArrayList();
            int blockId = fbsFlat.getBlockId();
            int l = 0;
            FbsBlock fbsBlock2 = fbsBlockFacade.find(blockId);
            xmlFile1 = fbsBlock2.getXmlFile();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(xmlFile1)));
            doc.getDocumentElement().normalize();
            Node block1 = doc.getFirstChild();
            // NodeList block = doc.getElementsByTagName("block");
            NodeList floorList1 = block1.getChildNodes();
            //NodeList floorList1 = block.item(0).getChildNodes();
            for (int k = 0; k < floorList1.getLength(); k++) {
                Node floor = floorList1.item(k);
                if (floor.getNodeType() == Node.ELEMENT_NODE) {
                    Element floorElement = (Element) floor;
                    if (floorElement.getAttribute("floor_id").equals(fbsFlat.getFloorId().toString())) {
                        NodeList floorNoList = floorElement.getElementsByTagName("floor_number");
                        Node fnoElement = (Node) floorNoList.item(0);
                        NodeList flatList = floorElement.getElementsByTagName("flat");
                        for (int j = 0; j < flatList.getLength(); j++) {

                            Element flatElement = (Element) flatList.item(j);

                            if (flatElement.getAttribute("flat_id").equals(fbsFlat.getFlatId().toString())) {
                                floor.removeChild(flatList.item(j));
                            }
                        }
                    }
                }
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StreamResult result = new StreamResult(new StringWriter());
            DOMSource source = new DOMSource(doc);
            transformer.transform(source, result);

            String xmlString = result.getWriter().toString();
            // System.out.println(xmlString);
            fbsBlock2.setXmlFile(xmlString);
            fbsBlockFacade.edit(fbsBlock2);
            populateBlock(fbsBlock2.getBlockId());


        } catch (Exception ex) {
            Logger.getLogger(QuickBookingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteBlock(BlockInfo blockInfo) throws IOException {
        fbsBlock = fbsBlockFacade.find(blockInfo.blockId);
        fbsBlockFacade.remove(fbsBlock);
        populate();
        // blockList = em.createNamedQuery("FbsBlock.findByFkProjId").setParameter("fkProjId", Integer.parseInt(projid)).getResultList();
    }

    public void renderForm() {
        render0 = false;
        render1 = false;
        render2 = false;
        render3 = false;
        render4 = false;
        render5 = false;
        render6 = false;
        render7 = false;
        render8 = false;
        render9 = false;
        for (int j = 0; j < i; j++) {
            if (j == 0) {
                this.render0 = !this.render0;
            } else if (j == 1) {
                this.render1 = !this.render1;
            } else if (j == 2) {
                this.render2 = !this.render2;
            } else if (j == 3) {
                this.render3 = !this.render3;
            } else if (j == 4) {
                this.render4 = !this.render4;
            } else if (j == 5) {
                this.render5 = !this.render5;
            } else if (j == 6) {
                this.render6 = !this.render6;
            } else if (j == 7) {
                this.render7 = !this.render7;
            } else if (j == 8) {
                this.render8 = !this.render8;
            } else if (j == 9) {
                this.render9 = !this.render9;
            }
        }
    }

    public void populateFloors(FbsBlock fbsBlock) throws IOException {
        // fbsBlock = (FbsBlock) em.createNamedQuery("FbsBlock.findByBlockName").setParameter("blockName", fbsBlock.getBlockName()).getResultList().get(0);
        //System.out.println(fbsBlock.getBlockName());
        try {
            fbsFlatList = new ArrayList();
            int blockId = fbsBlock.getBlockId();
            //System.out.println(fbsBlock.getBlockName());
            int l = 0;
            FbsBlock list1 = fbsBlockFacade.find(blockId);
            xmlFile1 = list1.getXmlFile();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(xmlFile1)));
            doc.getDocumentElement().normalize();
            NodeList block = doc.getElementsByTagName("block");
            NodeList floorList1 = block.item(0).getChildNodes();
            for (int k = 0; k < floorList1.getLength(); k++) {
                Node floor = floorList1.item(k);
                if (floor.getNodeType() == Node.ELEMENT_NODE) {
                    Element floorElement = (Element) floor;
                    String floorId1 = floorElement.getAttribute("floor_id").trim();
                    NodeList floorNoList = floorElement.getElementsByTagName("floor_number");
                    Node fnoElement = (Node) floorNoList.item(0);
                    NodeList flatList = floorElement.getElementsByTagName("flat");
                    for (int j = 0; j < flatList.getLength(); j++) {
                        fbsFlat = new FbsFlat();
                        fbsFlat.setBlockId(fbsBlock.getBlockId());
                        fbsFlat.setBlockName(fbsBlock.getBlockName());
                        fbsFlat.setFloorNo(fnoElement.getTextContent());
                        fbsFlat.setFloorId(Long.parseLong(floorId1));
                        Element flatElement = (Element) flatList.item(j);
                        String flatId = flatElement.getAttribute("flat_id").trim();
                        fbsFlat.setFlatId(Long.parseLong(flatId));
                        NodeList flatTypeList1 = flatElement.getElementsByTagName("flattype");
                        Element typeElement = (Element) flatTypeList1.item(0);
                        fbsFlat.setFlatType(typeElement.getAttribute("flatTypeId").trim());
                        fbsFlatType = fbsFlatTypeFacade.find(Integer.parseInt(typeElement.getAttribute("flatTypeId").trim()));
                        fbsFlat.setFlatTypeSpecification(fbsFlatType.getFlatSpecification());
                        NodeList flatNoList1 = flatElement.getElementsByTagName("flatno");
                        Element noElement = (Element) flatNoList1.item(0);
                        fbsFlat.setFlatNo(noElement.getAttribute("flatNo").trim());
                        fbsFlatList.add(l, fbsFlat);
                        l++;
                        refFlatList = fbsFlatList;
                    }
                }
            }
            high = 0;
            count = 0;
            for (int a = 0; a < fbsFlatList.size(); a++) {
                if (high < Integer.parseInt(fbsFlatList.get(a).getFloorNo())) {
                    high = Integer.parseInt(fbsFlatList.get(a).getFloorNo());
                }
                count++;
            }

        } catch (Exception ex) {
            Logger.getLogger(QuickBookingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        // populateFlats();
    }

    public void populateFlats() throws IOException {
        int l = 0;

        floorList = new ArrayList();
        for (int k = high; k >= 0; k--) {
            l = 0;
            fbsFlatList = new ArrayList<FbsFlat>();
            for (int j = 0; j < this.refFlatList.size(); j++) {
                if (this.refFlatList.get(j).getFloorNo().equals(String.valueOf(k))) {
                    this.fbsFlatList.add(l, this.refFlatList.get(j));
                    l++;
                }
            }
            flatNoList = new FlatInfo[fbsFlatList.size()];
            for (int j = 0; j < fbsFlatList.size(); j++) {
                flatInfo = new FlatInfo();
                flatInfo.setFlatId(fbsFlatList.get(j).getFlatId().toString());
                flatInfo.setFlatNo(fbsFlatList.get(j).getFlatNo());
                flatInfo.setFlatTypeId(fbsFlatList.get(j).getFlatType());
                flatInfo.setFlatSpecification(fbsFlatList.get(j).getFlatTypeSpecification());
                flatInfo.setStatus("u");
                flatNoList[j] = flatInfo;
            }
            floorList.add(flatNoList);
        }

    }

    public void populateBlockInfo(BlockInfo blockInfo) {
        this.blockInfo = blockInfo;
    }

    public FbsBlock getFbsBlock() {
        return fbsBlock;
    }

    public void setFbsBlock(FbsBlock fbsBlock) {
        this.fbsBlock = fbsBlock;
    }

    public void setBlockList(ArrayList<FbsBlock> blockList) {
        this.blockList = blockList;
    }

    public List<FbsBlock> getBlockList() {
        return blockList;
    }

    public void setRender0(boolean render0) {
        this.render0 = render0;
    }

    public boolean getRender0() {
        return this.render0;
    }

    public void setRender1(boolean render1) {
        this.render1 = render1;
    }

    public boolean getRender1() {
        return this.render1;
    }

    public void setRender2(boolean render2) {
        this.render2 = render2;
    }

    public boolean getRender2() {
        return this.render2;
    }

    public void setRender3(boolean render3) {
        this.render3 = render3;
    }

    public boolean getRender3() {
        return this.render3;
    }

    public void setRender4(boolean render4) {
        this.render4 = render4;
    }

    public boolean getRender4() {
        return this.render4;
    }

    public void setRender5(boolean render5) {
        this.render5 = render5;
    }

    public boolean getRender5() {
        return this.render5;
    }

    public void setRender6(boolean render6) {
        this.render6 = render6;
    }

    public boolean getRender6() {
        return this.render6;
    }

    public void setRender7(boolean render7) {
        this.render7 = render7;
    }

    public boolean getRender7() {
        return this.render7;
    }

    public void setRender8(boolean render8) {
        this.render8 = render8;
    }

    public boolean getRender8() {
        return this.render8;
    }

    public void setRender9(boolean render9) {
        this.render9 = render9;
    }

    public boolean getRender9() {
        return this.render9;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getI() {
        return i;
    }

    public ArrayList getFlatTypes() {
        return flatTypes;
    }

    public void setFlatTypes(ArrayList flatTypes) {
        this.flatTypes = flatTypes;
    }

    public String getFlatType0() {
        return flatType0;
    }

    public void setFlatType0(String flatType0) {
        this.flatType0 = flatType0;
    }

    public String getFlatType1() {
        return flatType1;
    }

    public void setFlatType1(String flatType1) {
        this.flatType1 = flatType1;
    }

    public String getFlatType2() {
        return flatType2;
    }

    public void setFlatType2(String flatType2) {
        this.flatType2 = flatType2;
    }

    public String getFlatType3() {
        return flatType3;
    }

    public void setFlatType3(String flatType3) {
        this.flatType3 = flatType3;
    }

    public String getFlatType4() {
        return flatType4;
    }

    public void setFlatType4(String flatType4) {
        this.flatType4 = flatType4;
    }

    public String getFlatType5() {
        return flatType5;
    }

    public void setFlatType5(String flatType5) {
        this.flatType5 = flatType5;
    }

    public String getFlatType6() {
        return flatType6;
    }

    public void setFlatType6(String flatType6) {
        this.flatType6 = flatType6;
    }

    public String getFlatType7() {
        return flatType7;
    }

    public void setFlatType7(String flatType7) {
        this.flatType7 = flatType7;
    }

    public String getFlatType8() {
        return flatType8;
    }

    public void setFlatType8(String flatType8) {
        this.flatType8 = flatType8;
    }

    public String getFlatType9() {
        return flatType9;
    }

    public void setFlatType9(String flatType9) {
        this.flatType9 = flatType9;
    }

    public String getFlatPerFloor() {
        return flatPerFloor;
    }

    public void setFlatPerFloor(String flatPerFloor) {
        this.flatPerFloor = flatPerFloor;
    }

    public String getNoOfFloor() {
        return noOfFloor;
    }

    public void setNoOfFloor(String noOfFloor) {
        this.noOfFloor = noOfFloor;
    }

    public ArrayList getFlatCoutList() {
        return flatCoutList;
    }

    public void setFlatCoutList(ArrayList flatCoutList) {
        this.flatCoutList = flatCoutList;
    }

    public ArrayList getFloorCountList() {
        return floorCountList;
    }

    public void setFloorCountList(ArrayList floorCountList) {
        this.floorCountList = floorCountList;
    }

    public ArrayList<BlockInfo> getBlockInfoList() {
        return blockInfoList;
    }

    public void setBlockInfoList(ArrayList<BlockInfo> blockInfoList) {
        this.blockInfoList = blockInfoList;
    }

    public BlockInfo getBlockInfo() {
        return blockInfo;
    }

    public void setBlockInfo(BlockInfo blockInfo) {
        this.blockInfo = blockInfo;
    }

    public ArrayList getFloorList() {
        return floorList;
    }

    public void setFloorList(ArrayList floorList) {
        this.floorList = floorList;
    }

    public List<FbsFlat> getFbsFlatList() {
        return fbsFlatList;
    }

    public void setFbsFlatList(List<FbsFlat> fbsFlatList) {
        this.fbsFlatList = fbsFlatList;
    }

    public ArrayList<FbsFlat> getFbsFlatList1() {
        return fbsFlatList1;
    }

    public void setFbsFlatList1(ArrayList<FbsFlat> fbsFlatList1) {
        this.fbsFlatList1 = fbsFlatList1;
    }

    public List<FbsPlc> getNode1List() {
        return node1List;
    }

    public void setNode1List(List<FbsPlc> node1List) {
        this.node1List = node1List;
    }

    public List<TreeNode> getSubHeadChildList() {
        return subHeadChildList;
    }

    public void setSubHeadChildList(List<TreeNode> subHeadChildList) {
        this.subHeadChildList = subHeadChildList;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public void setSelectedNodes(TreeNode[] selectedNodes) {
        this.selectedNodes = selectedNodes;
    }

    public TreeNode[] getSelectedNodes() {
        return selectedNodes;
    }

    public String getFlatId() {
        return flatId;
    }

    public void setFlatId(String flatId) {
        this.flatId = flatId;
    }

    public int getBlockId() {
        return blockId;
    }

    public void setBlockId(int blockId) {
        this.blockId = blockId;
    }

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public void setFbsPlc1(FbsPlc fbsPlc1) {
        this.fbsPlc1 = fbsPlc1;
    }

    public FbsPlc getFbsPlc1() {
        return fbsPlc1;
    }

    public ArrayList getAl() {
        return al;
    }

    public void setAl(ArrayList al) {
        this.al = al;
    }

    public boolean isTemp() throws IOException {
        temp = true;
        populate();
        return temp;
    }

    public void setTemp(boolean temp) {
        this.temp = temp = true;

    }
}
