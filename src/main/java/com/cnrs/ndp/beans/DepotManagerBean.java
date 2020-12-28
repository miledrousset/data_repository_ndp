package com.cnrs.ndp.beans;


import com.cnrs.ndp.entity.Depots;
import com.cnrs.ndp.repository.DepotsRepository;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.cnrs.ndp.service.DirectoryService;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


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

    
    public void initComposant() {
        depotsList = depotsRepository.findAll();
    }

    public StreamedContent dowloadMetadonneFile(Depots depots) {
        StreamedContent file = null;
        try {
            File depoFile = new File(pathDepot + "/" + depots.getGroupeTravail() + "/" + depots.getRepertoir()
                    + "/" + depots.getNomDepot() + "/" + depots.getNomDepot() + ".csv");

            InputStream stream = new FileInputStream(depoFile);
            file = new DefaultStreamedContent(stream, "application/csv",
                    depots.getNomDepot() + ".csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public StreamedContent dowloadDepot(Depots depots) {
        try {
            File tempFile = File.createTempFile(depots.getNomDepot(), ".zip");
            File depoFile = new File(pathDepot + "/" + depots.getGroupeTravail() + "/" + depots.getRepertoir()
                                + "/" + depots.getNomDepot());

            directoryService.compressedDirectory(depoFile, tempFile);

            InputStream stream = new FileInputStream(tempFile);
            StreamedContent file = new DefaultStreamedContent(stream, "application/zip",
                    depots.getNomDepot() + ".zip");
            tempFile.deleteOnExit();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void modifierDepot() {
        depotsRepository.save(depotSelected);
    }

    public void annuler() {
        depotSelected = depotsRepository.findById(depotSelected.getId()).get();
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
    
}
