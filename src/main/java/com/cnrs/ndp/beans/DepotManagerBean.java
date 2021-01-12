package com.cnrs.ndp.beans;

import com.cnrs.ndp.entity.Depots;
import com.cnrs.ndp.repository.DepotsRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.*;
import java.util.List;

import com.cnrs.ndp.service.DirectoryService;
import org.apache.commons.lang3.ObjectUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileSystemUtils;


@Named(value = "depotManagerBean")
@SessionScoped
public class DepotManagerBean implements Serializable {

    @Autowired
    private DepotsRepository depotsRepository;

    @Autowired
    private DirectoryService directoryService;

    @Value("${upload.file.path}")
    private String pathDepot;
    
    private List<Depots> depotsList;
    private Depots depotSelected;
    private StreamedContent streamedContent;

    @PostConstruct
    public void initComposant() {
        depotsList = depotsRepository.findAllByOrderByDateDepotDesc();

        InputStream is = DepotManagerBean.class.getResourceAsStream("/Tutoriel de dépôt HumanumBox Janvier 2021.pdf");
        streamedContent = new DefaultStreamedContent(is, "application/pdf");
    }

    public StreamedContent dowloadMetadonneFile() {
        StreamedContent file = null;
        try {
            File depoFile = new File(pathDepot + "/" + depotSelected.getGroupeTravail() + "/" + depotSelected.getRepertoir()
                    + "/" + depotSelected.getNomDepot() + "/" + depotSelected.getNomDepot() + ".csv");

            InputStream stream = new FileInputStream(depoFile);
            file = new DefaultStreamedContent(stream, "application/csv", depoFile.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public StreamedContent dowloadDepot() {
        try {
            File tempFile = File.createTempFile(depotSelected.getNomDepot(), ".zip");
            File depoFile = new File(pathDepot + "/" + depotSelected.getGroupeTravail() + "/" + depotSelected.getRepertoir()
                                + "/" + depotSelected.getNomDepot());

            directoryService.compressedDirectory(depoFile, tempFile);

            InputStream stream = new FileInputStream(tempFile);
            StreamedContent file = new DefaultStreamedContent(stream, "application/zip",
                    depotSelected.getNomDepot() + ".zip");

            tempFile.deleteOnExit();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteDepot(Depots depots) {
        File depoFile = new File(pathDepot + "/" + depots.getGroupeTravail() + "/" + depots.getRepertoir()
                + "/" + depots.getNomDepot());
        FileSystemUtils.deleteRecursively(depoFile);
        depotsRepository.delete(depots);
        depotsList = depotsRepository.findAll();

        showMessage(FacesMessage.SEVERITY_INFO, "Dépôt supprimé avec sucée !");
    }
    
    public void modifierDepot() {
        if (!ObjectUtils.isEmpty(depotSelected)) {
            depotsRepository.save(depotSelected);
            depotsList = depotsRepository.findAllByOrderByDateDepotDesc();
            showMessage(FacesMessage.SEVERITY_INFO, "Dépôt modifié avec sucée !");
        }

        PrimeFaces.current().executeScript("PF('modifierDepot').hide();");
        PrimeFaces.current().ajax().update("mainDepos");
    }

    private void showMessage(FacesMessage.Severity messageType, String messageValue) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messageType, "", messageValue));
        PrimeFaces pf = PrimeFaces.current();
        pf.ajax().update("messages");
    }
    
    public List<Depots> getDepotsList() {
        return depotsList;
    }

    public void setDepotsList(List<Depots> depotsList) {
        this.depotsList = depotsList;
    }

    public Depots getDepotSelected() {
        return depotSelected;
    }

    public void setDepotSelected(Depots depotSelected) {
        this.depotSelected = depotSelected;
    }

    public StreamedContent getStreamedContent() {
        return streamedContent;
    }

    public void setStreamedContent(StreamedContent streamedContent) {
        this.streamedContent = streamedContent;
    }
}
