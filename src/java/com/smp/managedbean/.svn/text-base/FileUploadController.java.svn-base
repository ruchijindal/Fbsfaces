/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smp.managedbean;


import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;

@ManagedBean(name = "fileUploadController")
@Stateful
@SessionScoped
public class FileUploadController {

//	private Logger logger = LoggerFactory.getLogger(FileUploadController.class);

	public void handleFileUpload(FileUploadEvent event) {
//		logger.info("Uploaded: {}", event.getFile().getFileName());
            String fileName = event.getFile().getFileName();
            String outputfile = "${facesContext.externalContext.requestContextPath}/resources/uploadimg/"+fileName;

		FacesMessage msg = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}
	