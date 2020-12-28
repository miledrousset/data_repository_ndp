package com.cnrs.ndp.service;

import com.cnrs.ndp.model.resources.ArticlePresse;
import com.cnrs.ndp.model.resources.DeblinCore;
import com.cnrs.ndp.model.resources.Resource;
import com.cnrs.ndp.outils.ImageManager;
import com.cnrs.ndp.outils.PdfManager;
import com.cnrs.ndp.utils.StringUtils;
import com.cnrs.ndp.outils.VideoManager;

import org.primefaces.model.UploadedFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


@Service
public class FileManager {

    @Autowired
    private ImageManager imageManager;

    @Autowired
    private PdfManager pdfManager;

    @Autowired
    private VideoManager videoManager;

    @Value("${upload.file.path}")
    private String pathUpload;

    @Value("${upload.file.small_name}")
    private String smallDirectory;

    private final static int BUFFER_SIZE = 8192;


    public Resource uploadFiles(UploadedFile file, String repoName, String groupeTravailSelected,
                                String repertoirSelected, String schemasSelected, List<Resource> listMetadonnes) {
        Resource resource = null;
        try {
            String destinationPath = createDestinationDirectoryPath(repoName, groupeTravailSelected, repertoirSelected);
            String fileName = StringUtils.formatFileName(file.getFileName());
            File fileOut = new File(destinationPath + fileName);
            InputStream is = file.getInputstream();
            copyFile(is, fileOut, destinationPath + smallDirectory + fileName);
            String titre = StringUtils.formatFileName(fileName.substring(0, fileName.lastIndexOf(".")));
            resource = createResource(schemasSelected, titre, listMetadonnes, fileOut);
            resource.setFile(fileOut);
        } catch (Exception e) { }
        return resource;
    }

    private Resource createResource(String schemasSelected, String fileName, List<Resource> listMetadonnes,  File fileOut) {
        Resource resource = getResourceFromMetadonnes(listMetadonnes, fileName);
        if (resource == null) {
            switch(schemasSelected) {
                case "1" :
                    resource = new DeblinCore();
                    break;
                default :
                    resource = new ArticlePresse();
                    break;
            }
            resource.setTitre(fileName);
        }
        resource.setFile(fileOut);
        return resource;
    }

    private Resource getResourceFromMetadonnes(List<Resource> listMetadonnes, String fileName) {
        Resource resourceFound = null;
        for (Resource resource : listMetadonnes) {
            if (resource.getTitre().equalsIgnoreCase(fileName)) {
                resourceFound = resource;
            }
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
                //TODO copie icon nodDisponible
        }
    }

    public String createDestinationDirectoryPath(String repoName, String groupeTravailSelected, String repertoirSelected)
            throws IOException {

        String path = pathUpload + "/" + groupeTravailSelected + "/" + repertoirSelected + "/"
                + repoName + "/";
        Files.createDirectories(Paths.get(path));
        Files.createDirectories(Paths.get(path + smallDirectory));
        return path;
    }

}
