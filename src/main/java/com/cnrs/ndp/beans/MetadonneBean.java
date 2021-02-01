package com.cnrs.ndp.beans;

import com.cnrs.ndp.model.Metadonne;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Named(value = "metadonneBean")
@SessionScoped
public class MetadonneBean implements Serializable {

    private List<Metadonne> metadonneList;
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

    @PostConstruct
    public void initComposant() {

        metadonneList = new ArrayList<>();

        metadonneList.add(new Metadonne("Deblin core",
                "metadonnes/csv/Dublin Core.csv",
                "metadonnes/xlsx/Dublin Core.xlsx",
                "Toutes les formats"));

        metadonneList.add(new Metadonne("Article de Press (gt-Emotion)",
                "metadonnes/csv/Article de press.csv",
                "metadonnes/xlsx/Article de press.xlsx",
                textsFormat.toString()));

        metadonneList.add(new Metadonne("URL",
                "metadonnes/csv/Url.csv",
                "metadonnes/xlsx/Url.xlsx",
                urlsFormat.toString()));

        metadonneList.add(new Metadonne("Vidéo",
                "metadonnes/csv/Video.csv",
                "metadonnes/xlsx/Video.xlsx",
                videosFormat.toString()));

        metadonneList.add(new Metadonne("Image (LRMH)",
                "metadonnes/csv/Image.csv",
                "metadonnes/xlsx/Image.xlsx",
                imagesFormat.toString()));

        metadonneList.add(new Metadonne("Audio Wawe BWF",
                "metadonnes/csv/Audio.csv",
                "metadonnes/xlsx/Audio.xlsx",
                audiosFormat.toString()));

    }

    public StreamedContent dowloadFile(String metadonneFile, String format) throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:" + metadonneFile);
        InputStream in = new FileInputStream(file);
        return new DefaultStreamedContent(in, "*/*", file.getName());

    }

    public List<Metadonne> getMetadonneList() {
        return metadonneList;
    }

    public void setMetadonneList(List<Metadonne> metadonneList) {
        this.metadonneList = metadonneList;
    }
}
