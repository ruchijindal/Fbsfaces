/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.fbs;

import com.smp.entity.FbsApplicant;
import com.smp.entity.FbsDocs;
import com.smp.managedbean.FlatMasterBean;

import com.smp.session.FbsApplicantFacade;
import com.smp.session.FbsDocsFacade;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author smp
 */
@WebServlet(name = "UploadImage", urlPatterns = {"/UploadImage1","/UploadImage2"})
public class UploadImage extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    byte b[] = null;
    byte a[] = null;
    int size = 0;
    String path = null;
    int x = 0;
    int j = 0;
    String div;
    String img;
    String temppath;
    File tmpDir;
    File destinationDir;
    FileInputStream fis;
    String description;
    String documentName;
    String heading;
    String page;
    String sql;
    PreparedStatement pst;
    Connection con;
    String filename;
    ByteArrayOutputStream buffer;
    String unitCode;
    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager em;

    @EJB
    FbsDocsFacade fbsDocsFacade;
    @EJB
    FlatMasterBean flatMasterBean;
    @EJB
    FbsApplicantFacade fbsApplicantFacade;
    public static FbsDocs fbsDocs = new FbsDocs();
    public static List<FbsDocs> fbsDocList=new ArrayList<FbsDocs>();

    public UploadImage() {
    }
   

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

          try {
          unitCode=request.getParameter("unitCode");
         // String tts=request.getParameter("t");
        //    System.out.println("T is in servlet "+tts);
                // write bytes taken from uploaded file to target file
                ServletInputStream in = request.getInputStream();


                byte[] line = new byte[128];
                int i = in.readLine(line, 0, 128);
                int boundaryLength = i - 2;
                String boundary = new String(line, 0, boundaryLength);

                while (i != -1) {
                    String newLine = new String(line, 0, i);
                    if (newLine.startsWith("Content-Disposition: form-data; name=\"docName\"")) {
                        String s = new String(line, 0, i - 2);
                        //this is the  content
                        i = in.readLine(line, 0, 128);
                        i = in.readLine(line, 0, 128);
                        // blank line
                        buffer = new ByteArrayOutputStream();
                        newLine = new String(line, 0, i);
                        while (i != -1 && !newLine.startsWith(boundary)) {

                            buffer.write(line, 0, i);
                            i = in.readLine(line, 0, 128);
                            newLine = new String(line, 0, i);
                        }
                        documentName = new String(buffer.toByteArray());


                    } else if (newLine.startsWith("Content-Disposition: form-data; name=\"desc\"")) {
                        String s = new String(line, 0, i - 2);
                        //this is the  content
                        i = in.readLine(line, 0, 128);
                        // blank line
                        i = in.readLine(line, 0, 128);
                        buffer = new ByteArrayOutputStream();
                        newLine = new String(line, 0, i);
                        while (i != -1 && !newLine.startsWith(boundary)) {
                            buffer.write(line, 0, i);
                            i = in.readLine(line, 0, 128);
                            newLine = new String(line, 0, i);
                        }
                        description = new String(buffer.toByteArray());

                    } else if (newLine.startsWith("Content-Disposition: form-data; name=\"image\"")) {

                        String s = new String(line, 0, i - 2);

                        //We don't require file name.
                        int pos = s.indexOf("filename=\"");
                        if (pos != -1) {
                            String filepath = s.substring(pos + 10, s.length() - 1);
                            // Windows browsers include the full path on the client
                            // But Linux/Unix and Mac browsers only send the filename
                            // test if this is from a Windows browser

                            pos = filepath.lastIndexOf("\"");
                            if (pos != -1) {
                                filename = filepath.substring(pos + 1);
                            } else {
                                filename = filepath;
                            }
                        }
                        //this is the file content
                        i = in.readLine(line, 0, 128);
                        i = in.readLine(line, 0, 128);
                        // blank line
                        i = in.readLine(line, 0, 128);
                        buffer = new ByteArrayOutputStream();
                        newLine = new String(line, 0, i);
                        while (i != -1 && !newLine.startsWith(boundary)) {
                            buffer.write(line, 0, i);
                            i = in.readLine(line, 0, 128);
                            newLine = new String(line, 0, i);
                        }

                    }
//                    else if (newLine.startsWith("Content-Disposition: form-data; name=\"id\"")) {
//                        String s = new String(line, 0, i - 2);
//                        //this is the  content
//                        i = in.readLine(line, 0, 128);
//                        i = in.readLine(line, 0, 128);
//                        // blank line
//                        buffer = new ByteArrayOutputStream();
//                        newLine = new String(line, 0, i);
//                        while (i != -1 && !newLine.startsWith(boundary)) {
//
//                            buffer.write(line, 0, i);
//                            i = in.readLine(line, 0, 128);
//                            newLine = new String(line, 0, i);
//                        }
//                        tts = new String(buffer.toByteArray());
//                    }
                    i = in.readLine(line, 0, 128);
                }


              

            String outputfile = this.getServletContext().getRealPath("/") + "resources/images/" + filename;  // get path on the server
            System.out.println("path+++++++++++++++" + outputfile);
            File file=new File(outputfile);
            FileOutputStream fos = new FileOutputStream(outputfile);
            fos.write(buffer.toByteArray());

            //FileWriter fw=new FileWriter("/home/smp/Desktop/demo.txt");
            //fw.write(file);
            //fw.close();

            path = "resources/uploadimg/" + filename;

            System.out.println("in upload file??????????" + unitCode);
            System.out.println("in upload file??????????" + description);
            System.out.println("in upload file??????????" + documentName);
             System.out.println(flatMasterBean.isColabel()+ " in upload file??????????........" +flatMasterBean.isCoeditdetail());


           // fbsDocs = (FbsDocs) em.createNamedQuery("FbsDocs.findByUnitCode").setParameter("unitCode", Integer.parseInt(unitCode.trim())).getResultList().get(0);

        //    System.out.println("in upload file....." + fbsDocs.getDocId());


            //fbsApplicant.setImage1Path(buffer.toByteArray());
            
            if(flatMasterBean.uploading==true)
            {
            int st=Integer.parseInt(flatMasterBean.appId);
           FbsApplicant applicant=fbsApplicantFacade.find(st);
           System.out.println("Applicant name "+applicant.getApplicantName());
           applicant.setImage1Path(buffer.toByteArray());
           fbsApplicantFacade.edit(applicant);
           flatMasterBean.uploading=false;
           System.out.println("Image uploaded successfully........................q");
              }else{
                
      fbsDocs.setUnitCode(Integer.parseInt(unitCode.trim()));
            fbsDocs.setFile(buffer.toByteArray());
            fbsDocs.setDocName(documentName);
            fbsDocs.setDescription(description);
            fbsDocsFacade.create(fbsDocs);
            // fbsDocList=fbsDocsFacade.findAll();
           
             fbsDocList=flatMasterBean.findDoc(unitCode);
                
              }
          response.sendRedirect("/FbsFaces/faces/jsfpages/Project/directFlatMaster.xhtml");
        } catch (Exception ex) {
            ex.printStackTrace();
            //System.out.println("Exception in insert image" + ex);


            //response.sendRedirect("abc.xhtml");



        } finally {
            out.close();
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public static FbsDocs getFbsDocs() {
        return fbsDocs;
    }

    public static void setFbsDocs(FbsDocs fbsDocs) {
        UploadImage.fbsDocs = fbsDocs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public List<FbsDocs> getFbsDocList() {
        return fbsDocList;
    }

    public void setFbsDocList(List<FbsDocs> fbsDocList) {
        this.fbsDocList = fbsDocList;
    }

  
}
