package com.cnrs.ndp.beans;


import com.cnrs.ndp.entity.Depots;
import com.cnrs.ndp.repository.DepotsRepository;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.*;
import java.util.List;
import java.util.Objects;

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

    
    public void initComposant() {
        depotsList = depotsRepository.findAll();
    }


    public StreamedContent dowloadMetadonneFile() {
        StreamedContent file = null;
        try {
            File depoFile = new File(pathDepot + "/" + depotSelected.getGroupeTravail() + "/" + depotSelected.getRepertoir()
                    + "/" + depotSelected.getNomDepot() + "/" + depotSelected.getNomDepot() + ".csv");

            InputStream stream = new FileInputStream(depoFile);
            file = new DefaultStreamedContent(stream, "application/csv",
                    depotSelected.getNomDepot() + ".csv");
        } catch (IOException e) {
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
    }
    
    public String modifierDepot() {
        if (!ObjectUtils.isEmpty(depotSelected)) {
            depotsRepository.save(depotSelected);
            initComposant();
        }
        return "PF('modifierDepot').hide();";
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

    public String setDepotSelected(Depots depotSelected, int digramId) {
        this.depotSelected = depotSelected;
        if (digramId == 1) {
            return "PF('depotDownload').show();";
        } else if (digramId == 2){
            return "PF('modifierDepot').show();";
        } else {
            return "PF('depotMetadonne').show();";
        }
    }

    public void setDepotSelected(Depots depotSelected) {
        this.depotSelected = depotSelected;
    }
}
