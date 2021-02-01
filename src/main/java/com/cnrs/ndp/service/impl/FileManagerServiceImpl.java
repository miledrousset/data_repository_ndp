package com.cnrs.ndp.service.impl;

import com.cnrs.ndp.model.Label;
import com.cnrs.ndp.model.resources.*;
import com.cnrs.ndp.outils.ImageManager;
import com.cnrs.ndp.outils.PdfManager;
import com.cnrs.ndp.service.FileManagerService;
import com.cnrs.ndp.service.ThesaurusService;
import com.cnrs.ndp.utils.StringUtils;
import com.cnrs.ndp.outils.VideoManager;

import org.apache.commons.io.FileUtils;

import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class FileManagerServiceImpl implements FileManagerService {

    @Autowired
    private ImageManager imageManager;

    @Autowired
    private PdfManager pdfManager;

    @Autowired
    private VideoManager videoManager;

    @Autowired
    private ThesaurusService thesaurusService;

    @Value("${upload.file.path}")
    private String pathUpload;

    @Value("${upload.file.small_name}")
    private String smallDirectory;

    @Value("${upload.file.default_icon}")
    private String defaultIcon;

    @Value("#{'${upload.file.format.image}'.split(';')}")
    private List<String> imagesFormat;

    @Value("#{'${upload.file.format.video}'.split(';')}")
    private List<String> videosFormat;

    @Value("#{'${upload.file.format.audio}'.split(';')}")
    private List<String> audiosFormat;

    @Value("#{'${upload.file.format.text}'.split(';')}")
    private List<String> textsFormat;

    @Value("#{'${upload.file.format.url}'.split(';')}")
    private List<String> urlsFormat;

    @Value("#{'${upload.file.format.tableau}'.split(';')}")
    private List<String> tableausFormat;

    @Value("#{'${upload.file.format.representations_2d}'.split(';')}")
    private List<String> representations2DsFormat;

    @Value("#{'${upload.file.format.representations_3d}'.split(';')}")
    private List<String> representations3DsFormat;

    @Value("#{'${upload.file.format.nuage_point}'.split(';')}")
    private List<String> nuagePointsFormat;

    private final static int BUFFER_SIZE = 8192;


    public Resource uploadFiles(UploadedFile file,
                                String repoName,
                                String groupeTravailSelected,
                                String repertoirSelected,
                                String schemasSelected,
                                List<Resource> listMetadonnes,
                                int groupSelectedIndex) {
        Resource resource = null;
        try {
            String destinationPath = createDestinationDirectoryPath(repoName, groupeTravailSelected, repertoirSelected);
            String fileName = StringUtils.formatFileName(file.getFileName());
            File fileOut = new File(destinationPath + fileName);
            InputStream is = file.getInputstream();
            copyFile(is, fileOut, destinationPath + smallDirectory + fileName);
            String titre = StringUtils.formatFileName(fileName.substring(0, fileName.lastIndexOf(".")));
            resource = createResource(schemasSelected, titre, listMetadonnes, fileOut, groupSelectedIndex);
            resource.setFile(fileOut);
        } catch (Exception e) { }
        return resource;
    }


    private Resource createResource(String schemasSelected, String fileName, List<Resource> listMetadonnes,
                                    File fileOut, int groupSelectedIndex) {
        Resource resource = getResourceFromMetadonnes(listMetadonnes, fileName, groupSelectedIndex);
        if (resource == null) {
            switch(Integer.parseInt(schemasSelected)) {
                case 1 :
                    resource = new DeblinCore();
                    ((DeblinCore) resource).setDateMiseDisposition(new Date());
                    ((DeblinCore) resource).setCouverture(new Date());
                    break;
                case 2:
                    resource = new ArticlePresse();
                    ((ArticlePresse) resource).setDateCreationPDF(new Date());
                    ((ArticlePresse) resource).setDateConsultation(new Date());
                    ((ArticlePresse) resource).setDateCreationFichier(new Date());
                    break;
                case 3:
                    resource = new Url();
                    ((Url) resource).setDateCreationFichier(new Date());
                    break;
                case 4:
                    resource = new Video();
                    ((Video) resource).setDateCreationFichier(new Date());
                    ((Video) resource).setDateCreationMp4(new Date());
                    ((Video) resource).setDateConsultation(new Date());
                    break;
                case 5:
                    resource = new Image();
                    ((Image) resource).setDateCreation(new Date());
                    ((Image) resource).setDateMiseDisposition(new Date());
                    break;
                case 6:
                    resource = new AudioWaweBwf();
                    ((AudioWaweBwf) resource).setOriginationDate(new Date());
                    ((AudioWaweBwf) resource).setIcrd(new Date());
                    break;
                case 7:
                    resource = new DonneeLaserBrut();
                    ((DonneeLaserBrut) resource).setDateMiseDisposition(new Date());
                    break;
                case 8:
                    resource = new DonneeLaserConso();
                    ((DonneeLaserConso) resource).setDateMiseDisposition(new Date());
                    break;
                case 9:
                    resource = new NuagePointsPhotogrammetrie();
                    ((NuagePointsPhotogrammetrie) resource).setDateMiseDisposition(new Date());
                    break;
                case 10:
                    resource = new Maillage3dPhotogrammetrie();
                    break;
                case 11:
                    resource = new Maillage3dGeometry();
                    ((Maillage3dGeometry) resource).setDateFichier(new Date());
                    break;
            }
            resource.setTitre(fileName);
        }
        resource.setFile(fileOut);
        return resource;
    }

    private Resource getResourceFromMetadonnes(List<Resource> listMetadonnes, String fileName, int groupSelectedIndex) {
        Resource resourceFound = null;
        for (Resource resource : listMetadonnes) {
            if (resource.getTitre().equalsIgnoreCase(fileName)) {
                resourceFound = resource;
            }
        }

        if (!ObjectUtils.isEmpty(resourceFound)) {
            List<Label> motsValide = new ArrayList<>();
            List<Label> motsNonValide = new ArrayList<>();
            for (Label label : resourceFound.getMotsClesLabel()) {
                List<Label> mots = thesaurusService.getListTermes(label.getLabel(), groupSelectedIndex);
                if (!CollectionUtils.isEmpty(mots)) {
                    Label newLabel = new Label();
                    newLabel.setLabel(label.getLabel());
                    newLabel.setUri(mots.get(0).getUri());
                    motsValide.add(newLabel);
                } else {
                    Label newLabel = new Label();
                    newLabel.setLabel(label.getLabel());
                    motsNonValide.add(newLabel);
                }
            }
            resourceFound.setMotsClesValide(motsValide);
            resourceFound.setMotsClesNonValide(motsNonValide);
        }

        return resourceFound;
    }

    private void copyFile(InputStream is, File fileOut, String destinationPath) throws Exception {
        uploadFile(is, fileOut);
        createSmallFile(fileOut, destinationPath);
    }

    public void uploadFile(InputStream is, File fileOut) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileOut);
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

    private void createSmallFile(File file, String destinationPath) throws Exception {
        try {
            URLConnection connection = file.toURL().openConnection();
            String mimeType = connection.getContentType();
            switch (mimeType) {
                case "image/jpeg":
                case "image/png":
                case "image/tiff":
                    imageManager.imageTraitement(file, destinationPath);
                    break;
                case "application/pdf":
                    pdfManager.pdfTraitement(file, destinationPath);
                    break;
                case "video/mp4":
                case "video/webm":
                    videoManager.videoTraitement(file.getPath(), destinationPath);
                    break;
                default:
                    File destination = new File(destinationPath.substring(0, destinationPath.lastIndexOf(".")+1) + "jpg");
                    createDefaultImage(destination);
            }
        } catch (Exception ex) {
            File destination = new File(destinationPath.substring(0, destinationPath.lastIndexOf(".")) + ".jpg");
            createDefaultImage(destination);
        }

    }

    private void createDefaultImage(File destination) throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        File original = new File(classLoader.getResource(defaultIcon).getFile());

        FileUtils.copyFile(original, destination);
    }

    public String createDestinationDirectoryPath(String repoName, String groupeTravailSelected, String repertoirSelected)
            throws IOException {

        String path = pathUpload + "/" + groupeTravailSelected + "/" + repertoirSelected + "/"
                + repoName + "/";
        Files.createDirectories(Paths.get(path));
        Files.createDirectories(Paths.get(path + smallDirectory));
        return path;
    }

    public boolean validateFormatFile(String schemaSelected, String extentionFile) {
        boolean response = false;

        switch(Integer.parseInt(schemaSelected)) {
            case 1 :
                response = true;
                break;
            case 2:
                response = textsFormat.contains(extentionFile);
                break;
            case 3:
                response = urlsFormat.contains(extentionFile);
                break;
            case 4:
                response = videosFormat.contains(extentionFile);
                break;
            case 5:
                response = imagesFormat.contains(extentionFile);
                break;
            case 6:
                response = audiosFormat.contains(extentionFile);
                break;
            case 7 :
                response = true;
                break;
            case 8 :
                response = true;
                break;
            case 9:
                response = nuagePointsFormat.contains(extentionFile);
                break;
            case 10:
                response = representations2DsFormat.contains(extentionFile);
                break;
            case 11:
                response = representations3DsFormat.contains(extentionFile);
                break;
        }

        return response;
    }

}
