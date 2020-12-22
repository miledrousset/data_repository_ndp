package com.cnrs.ndp.beans;
/*
import com.cnrs.ndp.model.ArticlePresse;
import com.cnrs.ndp.model.DeblinCore;
import com.cnrs.ndp.model.Resource;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.UploadedFiles;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named(value = "dataDepotManager")
@SessionScoped
public class DataDepotBeanBackup implements Serializable {

    private boolean isDeblinCoreVisible, selectAll;
    private String schemasSelected;
    private UploadedFiles files;

    private Resource resourceSelected;
    private List<Resource> deblinCoreUploated;


    private String formatAllow = "/(\\.|\\/)(gif|jpe?g|png|txt|csv)$/";
    private int nbrFileUploadSimultaner = 30;
    private int sizeMaxFileUpload = 1951200;


    @PostConstruct
    public void initComposant() {
        schemasSelected = "1";
        isDeblinCoreVisible = false;

        DeblinCore resource = new DeblinCore();
        resource.setTitre("Titre 1");
        resource.setFile(new File("/Users/firasgabs/index.php"));

        DeblinCore resource2 = new DeblinCore();
        resource2.setTitre("Titre 2");
        resource2.setFile(new File("/Users/firasgabs/firas.png"));

        deblinCoreUploated = new ArrayList<>();
        deblinCoreUploated.add(resource);
        deblinCoreUploated.add(resource2);
    }

    public UploadedFiles getFiles() {
        return files;
    }

    public void setFiles(UploadedFiles files) {
        this.files = files;
    }

    public void modifierResource() {

        PrimeFaces.current().executeScript("PF('modifierdeblinCore').hide();");
        PrimeFaces.current().ajax().update("mainDepos");
    }

    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void supprimerResource() {
        deblinCoreUploated.remove(resourceSelected);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage
                .SEVERITY_INFO, "", "Resource supprimée avec sucée !"));

        PrimeFaces.current().executeScript("PF('supprimerdeblinCore').hide();");
        PrimeFaces.current().ajax().update("mainDepos");
        PrimeFaces.current().ajax().update("messageIndex");
    }

    public void uploadMultiple(FileUploadEvent event) {
        if (files != null) {
            deblinCoreUploated = uploadFiles(files, schemasSelected);
        }

        isDeblinCoreVisible = true;

        FacesMessage message = new FacesMessage("Successful", "All files are uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);

        PrimeFaces.current().ajax().update("mainDepos");
    }

    public String createDestinationDirectory() {
        String path = "/Users/firasgabsi";
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        String reformattedStr = "/";
        try {
            reformattedStr += myFormat.format(myFormat.parse(new Date().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return path + reformattedStr;
    }

    public List<Resource> uploadFiles(UploadedFiles files, String schemasSelected) {
        List<Resource> resourcesUploated = new ArrayList<Resource>();
        String destinationPath = "";//createDestinationDirectory();
        for (UploadedFile uploadedFile : files.getFiles()) {
            try {
                String fileName = uploadedFile.getFileName();
                File fileOut = new File(destinationPath + fileName);
                InputStream is = uploadedFile.getInputStream();
                //copyFile(is, fileOut);
                Resource resource = createResource(schemasSelected, fileName);
                resource.setFile(fileOut);
                resourcesUploated.add(resource);
            } catch (IOException e) {
            }
        }
        return resourcesUploated;
    }

    private Resource createResource(String schemasSelected, String fileName) {
        Resource resource = null;
        switch(schemasSelected) {
            case "1" :
                resource = new DeblinCore();
                break;
            default :
                resource = new ArticlePresse();
                break;
        }
        resource.setTitre(fileName);
        return resource;
    }

    private void copyFile(InputStream is, File fileOut) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(fileOut);
        int BUFFER_SIZE = 8192;
        byte[] buffer = new byte[BUFFER_SIZE];
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
    }

    public void beforEdit(DeblinCore deblinCore) {
        this.resourceSelected = deblinCore;
        PrimeFaces.current().executeScript("PF('modifierdeblinCore').show();");
        PrimeFaces pf = PrimeFaces.current();
        pf.ajax().update("mainDepos");
    }

    public void onChangeSchemasList() {
        //if (!StringUtils.isEmpty(schemasSelected)) {
        isDeblinCoreVisible = false;

        PrimeFaces pf = PrimeFaces.current();
        if (pf.isAjaxRequest()) {
            pf.ajax().update("mainDepos");
        }
        //}
    }

    public String getSchemasSelected() {
        return schemasSelected;
    }

    public void setSchemasSelected(String schemasSelected) {
        this.schemasSelected = schemasSelected;
    }

    public boolean isIsDeblinCoreVisible() {
        return isDeblinCoreVisible;
    }

    public void setIsDeblinCoreVisible(boolean isDeblinCoreVisible) {
        this.isDeblinCoreVisible = isDeblinCoreVisible;
    }

    public Resource getResourceSelected() {
        return resourceSelected;
    }

    public void setResourceSelected(DeblinCore resourceSelected) {
        this.resourceSelected = resourceSelected;

    }

    public List<Resource> getDeblinCoreUploated() {
        return deblinCoreUploated;
    }

    public void setDeblinCoreUploated(List<Resource> deblinCoreUploated) {
        this.deblinCoreUploated = deblinCoreUploated;
    }

    public boolean isSelectAll() {
        return selectAll;
    }

    public void setSelectAll(boolean selectAll) {
        this.selectAll = selectAll;
    }

    public int getNbrFileUploadSimultaner() {
        return nbrFileUploadSimultaner;
    }

    public void setNbrFileUploadSimultaner(int nbrFileUploadSimultaner) {
        this.nbrFileUploadSimultaner = nbrFileUploadSimultaner;
    }

    public int getSizeMaxFileUpload() {
        return sizeMaxFileUpload;
    }

    public void setSizeMaxFileUpload(int sizeMaxFileUpload) {
        this.sizeMaxFileUpload = sizeMaxFileUpload;
    }

    public String getFormatAllow() {
        return formatAllow;
    }

    public void setFormatAllow(String formatAllow) {
        this.formatAllow = formatAllow;
    }

}
*/
