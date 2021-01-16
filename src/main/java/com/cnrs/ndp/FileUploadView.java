package com.cnrs.ndp;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


@Named
@RequestScoped
public class FileUploadView {

    private UploadedFile file;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void upload() throws Exception {
        if (file != null) {
            FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            handleFileUpload();
        }
    }

    public void handleFileUpload(FileUploadEvent event) throws Exception {
        FileOutputStream fos = new FileOutputStream(new File(event.getFile().getFileName()));
        byte[] buffer = new byte[8192];
        InputStream is = event.getFile().getInputstream();
        int a;
        while (true) {
            a = is.read(buffer);
            if (a < 0) {
                break;
            }
            fos.write(buffer, 0, a);
            fos.flush();
        }
        fos.close();
        is.close();

        FacesMessage msg = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void handleFileUpload() throws Exception {
        FileOutputStream fos = new FileOutputStream(new File(file.getFileName()));
        byte[] buffer = new byte[8192];
        InputStream is = file.getInputstream();
        int a;
        while (true) {
            a = is.read(buffer);
            if (a < 0) {
                break;
            }
            fos.write(buffer, 0, a);
            fos.flush();
        }
        fos.close();
        is.close();

        FacesMessage msg = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
