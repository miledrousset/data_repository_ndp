package com.cnrs.ndp.beans;

import com.cnrs.ndp.entity.Depots;
import com.cnrs.ndp.model.resources.*;
import com.cnrs.ndp.repository.DepotsRepository;
import com.cnrs.ndp.service.FileManager;
import com.cnrs.ndp.service.MetadonneService;
import com.cnrs.ndp.service.RepportService;
import com.cnrs.ndp.service.ThesaurusService;
import com.cnrs.ndp.utils.DateUtils;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Autowired
    private RepportService repportService;

    @Autowired
    private ThesaurusService thesaurusService;


    @Value("#{'${upload.file.repertoires}'.split(';')}")
    private List<String> repertoireList;

    @Value("#{'${upload.file.groupes_travail}'.split(';')}")
    private List<String> groupeTravail;

    @Value("${upload.file.nbr_file_max}")
    private int nbrFileUploadSimultaner;

    @Value("${upload.file.size_max}")
    private int sizeMaxFileUpload;

    @Value("${upload.file.format_allow}")
    private String formatAllow;

    @Value("${upload.file.small_name}")
    private String smallRep;

    @Value("${upload.file.path}")
    private String pathDepot;


    private boolean saveDepot, detailDepotVisible, uploadFilesVisible;
    private String schemasSelected, repertoirSelected, groupeTravailSelected, depotName, resourceName;
    private UploadedFile files;

    private DeblinCore deblinCoreSelected;
    private ArticlePresse articlePresseSelected;
    private Url urlSelected;
    private Image imageSelected;
    private Video videoSelected;
    private AudioWaweBwf audioSelected;
    private DonneeLaserBrut donneeLaserBrutSelected;
    private DonneeLaserConso donneeLaserConsoSelected;
    private Maillage3dGeometry maillage3dGeometrySelected;
    private NuagePointsPhotogrammetrie nuagePointsPhotogrammetrieSelected;
    private Maillage3dPhotogrammetrie maillage3dPhotogrammetrieSelected;
    private List<Resource> deblinCoreUploated, listMetadonnes;


    @PostConstruct
    public void initComposant() {
        schemasSelected = "1";
        detailDepotVisible = false;
        uploadFilesVisible = false;

        repertoirSelected = "";
        groupeTravailSelected = "";
        depotName = "";
        resourceName = "";

        urlSelected = new Url();
        imageSelected = new Image();
        videoSelected = new Video();
        audioSelected = new AudioWaweBwf();
        deblinCoreSelected = new DeblinCore();
        articlePresseSelected = new ArticlePresse();
        donneeLaserBrutSelected = new DonneeLaserBrut();
        donneeLaserConsoSelected = new DonneeLaserConso();
        maillage3dGeometrySelected = new Maillage3dGeometry();
        nuagePointsPhotogrammetrieSelected = new NuagePointsPhotogrammetrie();
        maillage3dPhotogrammetrieSelected = new Maillage3dPhotogrammetrie();

        deblinCoreUploated = new ArrayList<>();
        listMetadonnes = new ArrayList<>();
    }

    public void rechercheMotCle(String name) {
        if (CollectionUtils.isEmpty(deblinCoreSelected.getMotsCles())) {
            deblinCoreSelected.setMotsCles(new ArrayList<>());
        }
        deblinCoreSelected.getMotsCles().add(name);
        deblinCoreSelected.setMotCle("");
    }

    public List<String> rechercheMotsCle(String query) {
        return thesaurusService.getListTermes(query);
    }

    public void validerDepot() {
        String depoFile = new StringBuffer(pathDepot).append("/").append(groupeTravailSelected).append("/")
                .append(repertoirSelected).append("/").append(depotName).append("/").toString();

        repportService.createDeblinCoreRepport(deblinCoreUploated, depoFile, depotName, schemasSelected);

        showMessage(FacesMessage.SEVERITY_INFO, "Dépôt crée avec sucée !");
    }

    public void modifierResource() {
        PrimeFaces.current().executeScript("PF('modifierdeblinCore').hide();");
        PrimeFaces.current().ajax().update("mainDepos");
    }

    public void annulerDepot() {

        if (StringUtils.isNotEmpty(depotName)) {
            Depots depot = depotsRepository.findByNomDepot(depotName);
            depotsRepository.delete(depot);
        }

        initComposant();

        PrimeFaces.current().ajax().update("mainDepos");
    }

    public void supprimerResource() {

        switch (Integer.parseInt(schemasSelected)) {
            case 1:
                delete(deblinCoreSelected);
                break;
            case 2:
                delete(articlePresseSelected);
                break;
            case 3:
                delete(urlSelected);
                break;
            case 4:
                delete(videoSelected);
                break;
            case 5:
                delete(imageSelected);
                break;
            case 6:
                delete(audioSelected);
                break;
            case 7:
                delete(donneeLaserBrutSelected);
                break;
            case 8:
                delete(donneeLaserConsoSelected);
                break;
            case 9:
                delete(nuagePointsPhotogrammetrieSelected);
                break;
            case 10:
                delete(maillage3dPhotogrammetrieSelected);
                break;
            case 11:
                delete(maillage3dGeometrySelected);
                break;
        }

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage
                .SEVERITY_INFO, "", "Resource supprimée avec sucée !"));

        PrimeFaces.current().executeScript("PF('supprimerdeblinCore').hide();");
        PrimeFaces.current().ajax().update("mainDepos");
        PrimeFaces.current().ajax().update("messageIndex");
    }

    private void delete(Resource resource) {
        resource.getFile().delete();

        String name = resource.getFile().getName();
        String filePath = resource.getFile().getPath().substring(0, resource.getFile().getPath().lastIndexOf("/") + 1)
                + smallRep + name.substring(0, name.lastIndexOf(".")) + ".png";
        new File(filePath).delete();

        deblinCoreUploated.remove(resource);
    }

    public void uploadFiles(FileUploadEvent event) {

        if (fileManager.validateFormatFile(schemasSelected, FilenameUtils.getExtension(event.getFile().getFileName()))) {

            String username = "user";

            if (StringUtils.isEmpty(depotName)) {
                depotName = username + "-" + DateUtils.getDateTime(DIRECTORY_NAME);
            }
            deblinCoreUploated = new ArrayList<>();
            deblinCoreUploated.add(fileManager.uploadFiles(event.getFile(), depotName, groupeTravailSelected,
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
                depots.setNomDepot(depotName);
                depots.setRepertoir(repertoirSelected);
                depots.setGroupeTravail(groupeTravailSelected);
                depots.setArcheodrid("A venir");
                depots.setDepotHumaNum("A venir");
                depots.setDateDepot(new Date());
                depotsRepository.save(depots);
            }

        } else {
            showMessage(FacesMessage.SEVERITY_WARN, "Le format du fichier '" + event.getFile().getFileName()
                    + "' n'est pas valide !");
        }

    }

    public void uploadMetadonneFile(FileUploadEvent event) throws IOException {
        UploadedFile file = event.getFile();
        if (file.getFileName().toLowerCase().endsWith(".xlsx")) {
            InputStream is = event.getFile().getInputstream();
            File fileUploated = new File(event.getFile().getFileName());
            fileManager.uploadFile(is, fileUploated);
            listMetadonnes = metadonneService.readDeblinCoreMetadonne(fileUploated, schemasSelected);
            fileUploated.delete();
            PrimeFaces pf = PrimeFaces.current();
            pf.ajax().update("mainDepos");
        }
        else {
            showMessage(FacesMessage.SEVERITY_INFO, "Le format du fichier metadonne est invalide !");
        }
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

    public String afficherModifierResourceDialog() {
        String nomDialog = null;
        switch (Integer.parseInt(schemasSelected)) {
            case 1:
                nomDialog = "deblinCore";
                break;
            case 2:
                nomDialog = "articlePress";
                break;
            case 3:
                nomDialog = "urlDialog";
                break;
            case 4:
                nomDialog = "videoDialog";
                break;
            case 5:
                nomDialog = "imageDialog";
                break;
            case 6:
                nomDialog = "audioDialog";
                break;
            case 7:
                nomDialog = "donneLaserBrutesDialog";
                break;
            case 8:
                nomDialog = "donneLaserConsoDialog";
                break;
            case 9:
                nomDialog = "nuagePointsPhotogrammetrieDialog";
                break;
            case 10:
                nomDialog = "maillage3dPhotoDiagram";
                break;
            case 11:
                nomDialog = "maillage3dGeommetryDiagram";
                break;
        }

        return "PF('" + nomDialog + "').show();";
    }

    public void showMessage(FacesMessage.Severity messageType, String messageValue) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messageType, "", messageValue));
        PrimeFaces pf = PrimeFaces.current();
        pf.ajax().update("messages");
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

    public void setResourceSelected(Resource resourceSelected) {

        switch (Integer.parseInt(schemasSelected)) {
            case 1:
                deblinCoreSelected = (DeblinCore) resourceSelected;
                break;
            case 2:
                articlePresseSelected = (ArticlePresse) resourceSelected;
                break;
            case 3:
                urlSelected = (Url) resourceSelected;
                break;
            case 4:
                videoSelected = (Video) resourceSelected;
                break;
            case 5:
                imageSelected = (Image) resourceSelected;
                break;
            case 6:
                audioSelected = (AudioWaweBwf) resourceSelected;
                break;
            case 7:
                donneeLaserBrutSelected = (DonneeLaserBrut) resourceSelected;
                break;
            case 8:
                donneeLaserConsoSelected = (DonneeLaserConso) resourceSelected;
                break;
            case 9:
                nuagePointsPhotogrammetrieSelected = (NuagePointsPhotogrammetrie) resourceSelected;
                break;
            case 10:
                maillage3dPhotogrammetrieSelected = (Maillage3dPhotogrammetrie) resourceSelected;
                break;
            case 11:
                maillage3dGeometrySelected = (Maillage3dGeometry) resourceSelected;
                break;
        }
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

    public String getDepotName() {
        return depotName;
    }

    public ArticlePresse getArticlePresseSelected() {
        return articlePresseSelected;
    }

    public Url getUrlSelected() {
        return urlSelected;
    }

    public Video getVideoSelected() {
        return videoSelected;
    }

    public Image getImageSelected() {
        return imageSelected;
    }

    public AudioWaweBwf getAudioSelected() {
        return audioSelected;
    }

    public DonneeLaserBrut getDonneeLaserBrutSelected() {
        return donneeLaserBrutSelected;
    }

    public DonneeLaserConso getDonneeLaserConsoSelected() {
        return donneeLaserConsoSelected;
    }

    public Maillage3dGeometry getMaillage3dGeometrySelected() {
        return maillage3dGeometrySelected;
    }

    public Maillage3dPhotogrammetrie getMaillage3dPhotogrammetrieSelected() {
        return maillage3dPhotogrammetrieSelected;
    }

    public NuagePointsPhotogrammetrie getNuagePointsPhotogrammetrieSelected() {
        return nuagePointsPhotogrammetrieSelected;
    }

    public DeblinCore getDeblinCoreSelected() {
        return deblinCoreSelected;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
}
