package com.cnrs.ndp.beans;

import com.cnrs.ndp.entity.Depots;
import com.cnrs.ndp.model.Label;
import com.cnrs.ndp.model.resources.*;
import com.cnrs.ndp.repository.DepotsRepository;
import com.cnrs.ndp.service.*;
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
import java.util.*;
import java.util.stream.Collectors;


@Named(value = "dataDepotManager")
@SessionScoped
public class DataDepotBean implements Serializable {

    private final static String DIRECTORY_NAME = "dd-MM-yyyy_HH:mm";

    @Autowired
    private DepotManagerBean depotManagerBean;

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

    @Autowired
    private FormValidateur formValidateur;

    @Autowired
    private ConnexionBean connexionBean;

    @Autowired
    private MetadonneCsvService metadonneCsvService;


    @Value("#{'${upload.file.repertoires}'.split(';')}")
    private List<String> repertoireList;

    @Value("#{'${upload.file.groupes_travail}'.split(';')}")
    private List<String> groupeTravail;

    @Value("#{'${upload.file.source}'.split(';')}")
    private List<String> sources;

    @Value("${upload.file.small_name}")
    private String smallRep;

    @Value("${upload.file.path}")
    private String pathDepot;


    private boolean saveDepot, detailDepotVisible, uploadFilesVisible;
    private String schemasSelected, repertoirSelected, groupeTravailSelected, sourceSelected, depotName, resourceName;
    private UploadedFile file;
    private Depots depotCreated;

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
    private List<Label> labelsSearched;


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

    public void elementDelete() {
        switch (Integer.parseInt(schemasSelected)) {
            case 1:
                for (Label label : deblinCoreSelected.getMotsClesLabel()) {
                    if (deblinCoreSelected.getMotsCles().indexOf(label.getLabel()) < 0) {
                        deblinCoreSelected.getMotsClesLabel().remove(label);
                    }
                }
                break;
            case 2:
                for (Label label : articlePresseSelected.getMotsClesLabel()) {
                    if (articlePresseSelected.getMotsCles().indexOf(label.getLabel()) < 0) {
                        articlePresseSelected.getMotsClesLabel().remove(label);
                    }
                }
                break;
            case 3:
                for (Label label : urlSelected.getMotsClesLabel()) {
                    if (urlSelected.getMotsCles().indexOf(label.getLabel()) < 0) {
                        urlSelected.getMotsClesLabel().remove(label);
                    }
                }
                break;
            case 4:
                for (Label label : videoSelected.getMotsClesLabel()) {
                    if (videoSelected.getMotsCles().indexOf(label.getLabel()) < 0) {
                        videoSelected.getMotsClesLabel().remove(label);
                    }
                }
                break;
            case 5:
                for (Label label : imageSelected.getMotsClesLabel()) {
                    if (imageSelected.getMotsCles().indexOf(label.getLabel()) < 0) {
                        imageSelected.getMotsClesLabel().remove(label);
                    }
                }
                break;
            case 6:
                for (Label label : audioSelected.getMotsClesLabel()) {
                    if (audioSelected.getMotsCles().indexOf(label.getLabel()) < 0) {
                        audioSelected.getMotsClesLabel().remove(label);
                    }
                }
                break;
            case 7:
                for (Label label : donneeLaserBrutSelected.getMotsClesLabel()) {
                    if (donneeLaserBrutSelected.getMotsCles().indexOf(label.getLabel()) < 0) {
                        donneeLaserBrutSelected.getMotsClesLabel().remove(label);
                    }
                }
                break;
            case 8:
                for (Label label : donneeLaserConsoSelected.getMotsClesLabel()) {
                    if (donneeLaserConsoSelected.getMotsCles().indexOf(label.getLabel()) < 0) {
                        donneeLaserConsoSelected.getMotsClesLabel().remove(label);
                    }
                }
                break;
            case 9:
                for (Label label : nuagePointsPhotogrammetrieSelected.getMotsClesLabel()) {
                    if (nuagePointsPhotogrammetrieSelected.getMotsCles().indexOf(label.getLabel()) < 0) {
                        nuagePointsPhotogrammetrieSelected.getMotsClesLabel().remove(label);
                    }
                }
                break;
            case 10:
                for (Label label : maillage3dPhotogrammetrieSelected.getMotsClesLabel()) {
                    if (maillage3dPhotogrammetrieSelected.getMotsCles().indexOf(label.getLabel()) < 0) {
                        maillage3dPhotogrammetrieSelected.getMotsClesLabel().remove(label);
                    }
                }
                break;
            case 11:
                for (Label label : maillage3dGeometrySelected.getMotsClesLabel()) {
                    if (maillage3dGeometrySelected.getMotsCles().indexOf(label.getLabel()) < 0) {
                        maillage3dGeometrySelected.getMotsClesLabel().remove(label);
                    }
                }
                break;
        }
    }

    public void addNewLabel(String name) {
        Label newLabel = new Label();
        newLabel.setLabel(name);
        addLabel(name, newLabel, false);
    }

    public void rechercheMotCle(String name) {

        Label labelSelected = null;
        for (Label label : labelsSearched) {
            if (label.getLabel().equals(name)) {
                labelSelected = label;
            }
        }
        addLabel(name, labelSelected, true);
    }

    private boolean isExistingLabel(List<Label> labels, String labelName) {
        for (Label label : labels) {
            if (label.getLabel().equals(labelName)) {
                return true;
            }
        }
        return false;
    }

    public void addLabel(String name, Label labelSelected, boolean isValidate) {
        switch (Integer.parseInt(schemasSelected)) {
            case 1:
                if (CollectionUtils.isEmpty(deblinCoreSelected.getMotsCles())) {
                    deblinCoreSelected.setMotsClesLabel(new ArrayList<>());
                    deblinCoreSelected.setMotsCles(new ArrayList<>());
                    deblinCoreSelected.setMotsClesValide(new ArrayList<>());
                    deblinCoreSelected.setMotsClesNonValide(new ArrayList<>());
                }
                if (!isExistingLabel(deblinCoreSelected.getMotsClesLabel(), name)) {
                    deblinCoreSelected.getMotsCles().add(name);
                    deblinCoreSelected.getMotsClesLabel().add(labelSelected);
                    if (isValidate) deblinCoreSelected.getMotsClesValide().add(labelSelected);
                    else deblinCoreSelected.getMotsClesNonValide().add(labelSelected);
                }
                deblinCoreSelected.setMotCle("");
                break;
            case 2:
                if (CollectionUtils.isEmpty(articlePresseSelected.getMotsCles())) {
                    articlePresseSelected.setMotsClesLabel(new ArrayList<>());
                    articlePresseSelected.setMotsCles(new ArrayList<>());
                }
                if (!isExistingLabel(articlePresseSelected.getMotsClesLabel(), name)) {
                    articlePresseSelected.getMotsCles().add(name);
                    articlePresseSelected.getMotsClesLabel().add(labelSelected);
                }
                articlePresseSelected.setMotCle("");
                break;
            case 3:
                if (CollectionUtils.isEmpty(urlSelected.getMotsCles())) {
                    urlSelected.setMotsClesLabel(new ArrayList<>());
                    urlSelected.setMotsCles(new ArrayList<>());
                }
                if (!isExistingLabel(urlSelected.getMotsClesLabel(), name)) {
                    urlSelected.getMotsCles().add(name);
                    urlSelected.getMotsClesLabel().add(labelSelected);
                }
                urlSelected.setMotCle("");
                break;
            case 4:
                if (CollectionUtils.isEmpty(videoSelected.getMotsCles())) {
                    videoSelected.setMotsClesLabel(new ArrayList<>());
                    videoSelected.setMotsCles(new ArrayList<>());
                }
                if (!isExistingLabel(videoSelected.getMotsClesLabel(), name)) {
                    videoSelected.getMotsCles().add(name);
                    videoSelected.getMotsClesLabel().add(labelSelected);
                }
                videoSelected.setMotCle("");
                break;
            case 5:
                if (CollectionUtils.isEmpty(imageSelected.getMotsCles())) {
                    imageSelected.setMotsClesLabel(new ArrayList<>());
                    imageSelected.setMotsCles(new ArrayList<>());
                }
                if (!isExistingLabel(imageSelected.getMotsClesLabel(), name)) {
                    imageSelected.getMotsCles().add(name);
                    imageSelected.getMotsClesLabel().add(labelSelected);
                }
                imageSelected.setMotCle("");
                break;
            case 6:
                if (CollectionUtils.isEmpty(audioSelected.getMotsCles())) {
                    audioSelected.setMotsClesLabel(new ArrayList<>());
                    audioSelected.setMotsCles(new ArrayList<>());
                }
                if (!isExistingLabel(audioSelected.getMotsClesLabel(), name)) {
                    audioSelected.getMotsCles().add(name);
                    audioSelected.getMotsClesLabel().add(labelSelected);
                }
                audioSelected.setMotCle("");
                break;
            case 7:
                if (CollectionUtils.isEmpty(donneeLaserBrutSelected.getMotsCles())) {
                    donneeLaserBrutSelected.setMotsClesLabel(new ArrayList<>());
                    donneeLaserBrutSelected.setMotsCles(new ArrayList<>());
                }
                if (!isExistingLabel(donneeLaserBrutSelected.getMotsClesLabel(), name)) {
                    donneeLaserBrutSelected.getMotsCles().add(name);
                    donneeLaserBrutSelected.getMotsClesLabel().add(labelSelected);
                }
                donneeLaserBrutSelected.setMotCle("");
                break;
            case 8:
                if (CollectionUtils.isEmpty(donneeLaserConsoSelected.getMotsCles())) {
                    donneeLaserConsoSelected.setMotsClesLabel(new ArrayList<>());
                    donneeLaserConsoSelected.setMotsCles(new ArrayList<>());
                }
                if (!isExistingLabel(donneeLaserConsoSelected.getMotsClesLabel(), name)) {
                    donneeLaserConsoSelected.getMotsCles().add(name);
                    donneeLaserConsoSelected.getMotsClesLabel().add(labelSelected);
                }
                donneeLaserConsoSelected.setMotCle("");
                break;
            case 9:
                if (CollectionUtils.isEmpty(nuagePointsPhotogrammetrieSelected.getMotsCles())) {
                    nuagePointsPhotogrammetrieSelected.setMotsClesLabel(new ArrayList<>());
                    nuagePointsPhotogrammetrieSelected.setMotsCles(new ArrayList<>());
                }
                if (!isExistingLabel(nuagePointsPhotogrammetrieSelected.getMotsClesLabel(), name)) {
                    nuagePointsPhotogrammetrieSelected.getMotsCles().add(name);
                    nuagePointsPhotogrammetrieSelected.getMotsClesLabel().add(labelSelected);
                }
                nuagePointsPhotogrammetrieSelected.setMotCle("");
                break;
            case 10:
                if (CollectionUtils.isEmpty(maillage3dPhotogrammetrieSelected.getMotsCles())) {
                    maillage3dPhotogrammetrieSelected.setMotsClesLabel(new ArrayList<>());
                    maillage3dPhotogrammetrieSelected.setMotsCles(new ArrayList<>());
                }
                if (!isExistingLabel(maillage3dPhotogrammetrieSelected.getMotsClesLabel(), name)) {
                    maillage3dPhotogrammetrieSelected.getMotsCles().add(name);
                    maillage3dPhotogrammetrieSelected.getMotsClesLabel().add(labelSelected);
                }
                maillage3dPhotogrammetrieSelected.setMotCle("");
                break;
            case 11:
                if (CollectionUtils.isEmpty(maillage3dGeometrySelected.getMotsCles())) {
                    maillage3dGeometrySelected.setMotsClesLabel(new ArrayList<>());
                    maillage3dGeometrySelected.setMotsCles(new ArrayList<>());
                }
                if (!isExistingLabel(maillage3dGeometrySelected.getMotsClesLabel(), name)) {
                    maillage3dGeometrySelected.getMotsCles().add(name);
                    maillage3dGeometrySelected.getMotsClesLabel().add(labelSelected);
                }
                maillage3dGeometrySelected.setMotCle("");
                break;
        }
    }

    public List<String> rechercheMotsCle(String query) {
        labelsSearched = thesaurusService.getListTermes(query, groupeTravail.indexOf(groupeTravailSelected));
        return labelsSearched.stream().map(label -> label.getLabel()).collect(Collectors.toList());
    }

    public void validerDepot() throws IOException {

        if (CollectionUtils.isEmpty(deblinCoreUploated)) {
            showMessage(FacesMessage.SEVERITY_INFO, "Vous devez ajouter des resources avant de valider!");
            return;
        }

        boolean isValide = true;
        for (Resource resource : deblinCoreUploated) {
            if (!formValidateur.forumValidateur(schemasSelected, resource)) {
                isValide = false;
            }
        }

        if (isValide) {
            depotCreated.setStatus(true);
            depotsRepository.save(depotCreated);
            depotManagerBean.initComposant();

            updateMethadonneFinalFile();
            initComposant();

            showMessage(FacesMessage.SEVERITY_INFO, "Dépôt crée avec sucée !");
            PrimeFaces.current().ajax().update("mainDepos");

            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        } else {
            showMessage(FacesMessage.SEVERITY_ERROR, "Le formulaire n'est pas complet !");
        }
    }

    private void updateMethadonneFinalFile() {
        String depoFile = new StringBuffer(pathDepot).append("/").append(groupeTravailSelected).append("/")
                .append(repertoirSelected).append("/").append(depotName).append("/").toString();

        repportService.createDeblinCoreRepport(deblinCoreUploated, depoFile, depotName, schemasSelected);
    }

    public void modifierResource() {

        Resource resource = null;
        switch (Integer.parseInt(schemasSelected)) {
            case 1:
                resource = deblinCoreSelected;
                break;
            case 2:
                resource = articlePresseSelected;
                break;
            case 3:
                resource = urlSelected;
                break;
            case 4:
                resource = videoSelected;
                break;
            case 5:
                resource = imageSelected;
                break;
            case 6:
                resource = audioSelected;
                break;
            case 7:
                resource = donneeLaserBrutSelected;
                break;
            case 8:
                resource = donneeLaserConsoSelected;
                break;
            case 9:
                resource = nuagePointsPhotogrammetrieSelected;
                break;
            case 10:
                resource = maillage3dPhotogrammetrieSelected;
                break;
            case 11:
                resource = maillage3dGeometrySelected;
                break;
        }

        updateMethadonneFinalFile();

        if (!formValidateur.forumValidateur(schemasSelected, resource)) {
            showMessage(FacesMessage.SEVERITY_ERROR, "Le formulaire n'est pas complet !");
        } else {
            showMessage(FacesMessage.SEVERITY_INFO, "Données du resource mis à jour !");
            PrimeFaces.current().executeScript("PF('modifierResource').hide();");
            PrimeFaces.current().ajax().update("mainDepos");
        }
    }

    public void annulerDepot() {

        if (StringUtils.isNotEmpty(depotName)) {
            Depots depot = depotsRepository.findByNomDepot(depotName);
            depotsRepository.delete(depot);
            depotManagerBean.initComposant();
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

        updateMethadonneFinalFile();

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

            if (StringUtils.isEmpty(depotName)) {
                depotName = connexionBean.getUsername() + "_" + DateUtils.getDateTime(DIRECTORY_NAME);
                deblinCoreUploated = new ArrayList<>();
            }
            deblinCoreUploated.add(fileManager.uploadFiles(event.getFile(), depotName, groupeTravailSelected,
                    repertoirSelected, schemasSelected, listMetadonnes, groupeTravail.indexOf(groupeTravailSelected)));

            FacesMessage message = new FacesMessage("Successful", "All files are uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);

            PrimeFaces pf = PrimeFaces.current();
            if (pf.isAjaxRequest()) {
                pf.ajax().update("mainDepos");
            }

            updateMethadonneFinalFile();

            if (saveDepot) {
                saveDepot = false;
                Depots depots = new Depots();
                depots.setNomDepot(depotName);
                depots.setRepertoir(repertoirSelected);
                depots.setGroupeTravail(groupeTravailSelected);
                depots.setArcheodrid("A venir");
                depots.setDepotHumaNum("A venir");
                depots.setDateDepot(new Date());
                depots.setSource(sourceSelected);
                depotCreated = depotsRepository.save(depots);
                depotManagerBean.initComposant();
            }

        } else {
            showMessage(FacesMessage.SEVERITY_WARN, "Le format du fichier '" + event.getFile().getFileName()
                    + "' n'est pas valide !");
        }
    }

    public void uploadMetadonneFile(FileUploadEvent event) throws IOException {
        UploadedFile file = event.getFile();

        if (file.getFileName().toLowerCase().endsWith(".xlsx") ||
                file.getFileName().toLowerCase().endsWith(".csv")) {

            InputStream is = event.getFile().getInputstream();
            File fileUploated = new File(event.getFile().getFileName());
            fileManager.uploadFile(is, fileUploated);

            if (file.getFileName().toLowerCase().endsWith(".xlsx")) {
                listMetadonnes = metadonneService.readDeblinCoreMetadonne(fileUploated, schemasSelected);
            } else {
                listMetadonnes = metadonneCsvService.readCsvMetadonne(fileUploated, schemasSelected);
            }

            fileUploated.delete();
            PrimeFaces pf = PrimeFaces.current();
            pf.ajax().update("mainDepos");

        } else {
            showMessage(FacesMessage.SEVERITY_ERROR, "Le format du fichier metadonne est invalide !");
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

    private void showMessage(FacesMessage.Severity messageType, String messageValue) {
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

    public void setResourceSelected(Resource resourceSelected) {

        this.resourceName = resourceSelected.getFile().getName();

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

    public void setDeblinCoreSelected(DeblinCore deblinCoreSelected) {
        this.deblinCoreSelected = deblinCoreSelected;
    }

    public String getResourceName() {
        return resourceName;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public boolean isDeblinSchemaSelected() {
        return "1".equals(schemasSelected);
    }

    public boolean isArticlePresseSchemaSelected() {
        return "2".equals(schemasSelected);
    }

    public boolean isUrlSchemaSelected() {
        return "3".equals(schemasSelected);
    }

    public boolean isVideoSchemaSelected() {
        return "4".equals(schemasSelected);
    }

    public boolean isImageSchemaSelected() {
        return "5".equals(schemasSelected);
    }

    public boolean isAudioSchemaSelected() {
        return "6".equals(schemasSelected);
    }

    public boolean isDonneesLaserBrutesSelected() {
        return "7".equals(schemasSelected);
    }

    public boolean isDonneesLaserConsoSelected() {
        return "8".equals(schemasSelected);
    }

    public boolean isNuagePointsPhotoSelected() {
        return "9".equals(schemasSelected);
    }

    public boolean isMaillage3DSelected() {
        return "10".equals(schemasSelected);
    }

    public boolean isRestitution3DSelected() {
        return "11".equals(schemasSelected);
    }

    public List<String> getSources() {
        return sources;
    }

    public void setSources(List<String> sources) {
        this.sources = sources;
    }

    public String getSourceSelected() {
        return sourceSelected;
    }

    public void setSourceSelected(String sourceSelected) {
        this.sourceSelected = sourceSelected;
    }
}
