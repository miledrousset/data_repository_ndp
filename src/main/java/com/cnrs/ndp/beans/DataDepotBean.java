package com.cnrs.ndp.beans;

import com.cnrs.ndp.entity.Depots;
import com.cnrs.ndp.model.DeblinCore;
import com.cnrs.ndp.model.Resource;
import com.cnrs.ndp.repository.DepotsRepository;
import com.cnrs.ndp.service.FileManager;
import com.cnrs.ndp.service.MetadonneService;

import com.cnrs.ndp.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Named(value = "dataDepotManager")
@SessionScoped
public class DataDepotBean implements Serializable {

    private final static String DIRECTORY_NAME = "dd-MM-yyyy_HH:mm";

    @Autowired
    private FileManager fileManager;

    @Autowired
    private MetadonneService metadonneService;

    @Autowired
    private DepotsRepository depotsRepository;


    private boolean saveDepot;
    private boolean detailDepotVisible, uploadFilesVisible;
    private String schemasSelected, repertoirSelected, groupeTravailSelected, name;
    private UploadedFile files;

    private Resource resourceSelected;
    private List<Resource> deblinCoreUploated;
    private List<Resource> listMetadonnes;


    @Value("#{'${upload.file.repertoires}'.split(',')}")
    private List<String> repertoireList;

    @Value("#{'${upload.file.groupes_travail}'.split(',')}")
    private List<String> groupeTravail;

    @Value("${upload.file.nbr_file_max}")
    private int nbrFileUploadSimultaner;

    @Value("${upload.file.size_max}")
    private int sizeMaxFileUpload;

    @Value("${upload.file.format_allow}")
    private String formatAllow;

    @Value("${upload.file.small_name}")
    private String smallRep;


    @PostConstruct
    public void initComposant() {
        schemasSelected = "1";
        detailDepotVisible = false;
        uploadFilesVisible = false;
        deblinCoreUploated = new ArrayList<>();
        listMetadonnes = new ArrayList<>();
    }

    public void modifierResource() {

        PrimeFaces.current().executeScript("PF('modifierdeblinCore').hide();");
        PrimeFaces.current().ajax().update("mainDepos");
    }

    public void supprimerResource() {

        resourceSelected.getFile().delete();

        String name = resourceSelected.getFile().getName();
        String filePath = resourceSelected.getFile().getPath().substring(0, resourceSelected.getFile().getPath().lastIndexOf("/") + 1) + smallRep +
                name.substring(0, name.lastIndexOf(".")) + ".png";
        File smallFile = new File(filePath);
        smallFile.delete();

        deblinCoreUploated.remove(resourceSelected);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage
                .SEVERITY_INFO, "", "Resource supprimée avec sucée !"));

        PrimeFaces.current().executeScript("PF('supprimerdeblinCore').hide();");
        PrimeFaces.current().ajax().update("mainDepos");
        PrimeFaces.current().ajax().update("messageIndex");
    }

    public void uploadFiles(FileUploadEvent event) {

        String username = "user";

        if (StringUtils.isEmpty(name)) {
            name = username + "-" + DateUtils.getDateTime(DIRECTORY_NAME);
        }
        deblinCoreUploated = new ArrayList<>();
        deblinCoreUploated.add(fileManager.uploadFiles(event.getFile(), username, groupeTravailSelected,
                repertoirSelected, schemasSelected, listMetadonnes));

        FacesMessage message = new FacesMessage("Successful", "All files are uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);

        PrimeFaces pf = PrimeFaces.current();
        if (pf.isAjaxRequest()) {
            pf.ajax().update("mainDepos");
        }

        if (saveDepot) {
            saveDepot = false;
            Depots depots = new Depots();
            depots.setNomDepot(name);
            depots.setRepertoir(repertoirSelected);
            depots.setGroupeTravail(groupeTravailSelected);
            depots.setArcheodrid("A venir");
            depots.setDepotHumaNum("A venir");
            depots.setDateDepot(new Date());
            depotsRepository.save(depots);
        }
    }

    public void uploadMetadonneFile(FileUploadEvent event) throws IOException {
        UploadedFile file = event.getFile();
        if (file.getFileName().toLowerCase().endsWith(".xlsx")) {
            InputStream is = event.getFile().getInputstream();
            File fileUploated = new File(event.getFile().getFileName());
            fileManager.uploadFile(is, fileUploated);
            listMetadonnes = metadonneService.readDeblinCoreMetadonne(fileUploated);
            fileUploated.delete();
            PrimeFaces pf = PrimeFaces.current();
            pf.ajax().update("mainDepos");
        }
        else {
            showMessage(FacesMessage.SEVERITY_INFO, "Le format du fichier metadonne est invalide !");
        }
    }

    public void beforEdit(DeblinCore deblinCore) {
        this.resourceSelected = deblinCore;
        PrimeFaces.current().executeScript("PF('modifierdeblinCore').show();");
        PrimeFaces pf = PrimeFaces.current();
        pf.ajax().update("mainDepos");
    }

    public void onValiderSchema() {
        detailDepotVisible = true;
        PrimeFaces pf = PrimeFaces.current();
        if (pf.isAjaxRequest()) {
            pf.ajax().update("mainDepos");
        }
    }

    public void onValiderUploadParams() {
        uploadFilesVisible = true;
        saveDepot = true;
        PrimeFaces pf = PrimeFaces.current();
        if (pf.isAjaxRequest()) {
            pf.ajax().update("mainDepos");
        }
    }

    public void showMessage(FacesMessage.Severity messageType, String messageValue) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messageType, "", messageValue));
        PrimeFaces pf = PrimeFaces.current();
        pf.ajax().update("messages");
    }

    public boolean isSelectAll() {
        return detailDepotVisible;
    }

    public void setSelectAll(boolean selectAll) {
        this.detailDepotVisible = selectAll;
    }

    public String getSchemasSelected() {
        return schemasSelected;
    }

    public void setSchemasSelected(String schemasSelected) {
        this.schemasSelected = schemasSelected;
    }

    public UploadedFile getFiles() {
        return files;
    }

    public void setFiles(UploadedFile files) {
        this.files = files;
    }

    public Resource getResourceSelected() {
        return resourceSelected;
    }

    public void setResourceSelected(Resource resourceSelected) {
        this.resourceSelected = resourceSelected;
    }

    public List<Resource> getDeblinCoreUploated() {
        return deblinCoreUploated;
    }

    public void setDeblinCoreUploated(List<Resource> deblinCoreUploated) {
        this.deblinCoreUploated = deblinCoreUploated;
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

    public String getRepertoirSelected() {
        return repertoirSelected;
    }

    public void setRepertoirSelected(String repertoirSelected) {
        this.repertoirSelected = repertoirSelected;
    }

    public String getGroupeTravailSelected() {
        return groupeTravailSelected;
    }

    public void setGroupeTravailSelected(String groupeTravailSelected) {
        this.groupeTravailSelected = groupeTravailSelected;
    }

    public List<String> getRepertoireList() {
        return repertoireList;
    }

    public void setRepertoireList(List<String> repertoireList) {
        this.repertoireList = repertoireList;
    }

    public List<String> getGroupeTravail() {
        return groupeTravail;
    }

    public void setGroupeTravail(List<String> groupeTravail) {
        this.groupeTravail = groupeTravail;
    }

    public boolean isDetailDepotVisible() {
        return detailDepotVisible;
    }

    public void setDetailDepotVisible(boolean detailDepotVisible) {
        this.detailDepotVisible = detailDepotVisible;
    }

    public boolean isUploadFilesVisible() {
        return uploadFilesVisible;
    }

    public void setUploadFilesVisible(boolean uploadFilesVisible) {
        this.uploadFilesVisible = uploadFilesVisible;
    }

    public List<Resource> getListMetadonnes() {
        return listMetadonnes;
    }

    public void setListMetadonnes(List<Resource> listMetadonnes) {
        this.listMetadonnes = listMetadonnes;
    }
}
