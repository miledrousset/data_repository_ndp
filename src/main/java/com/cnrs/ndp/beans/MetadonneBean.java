package com.cnrs.ndp.beans;

import com.cnrs.ndp.model.Metadonne;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
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
                "/metadonnes/csv/Dublin Core.csv",
                "/metadonnes/xlsx/Dublin Core.xlsx",
                "[Tous les formats]"));

        metadonneList.add(new Metadonne("Article de Press (gt-Emotion)",
                "/metadonnes/csv/Article de press.csv",
                "/metadonnes/xlsx/Article de press.xlsx",
                (CollectionUtils.isEmpty(textsFormat))? "[Tous les formats]" : textsFormat.toString()));

        metadonneList.add(new Metadonne("URL",
                "/metadonnes/csv/Url.csv",
                "/metadonnes/xlsx/Url.xlsx",
                (CollectionUtils.isEmpty(urlsFormat))? "[Tous les formats]" : urlsFormat.toString()));

        metadonneList.add(new Metadonne("Vidéo",
                "/metadonnes/csv/Video.csv",
                "/metadonnes/xlsx/Video.xlsx",
                (CollectionUtils.isEmpty(videosFormat))? "[Tous les formats]" : videosFormat.toString()));

        metadonneList.add(new Metadonne("Image (LRMH)",
                "/metadonnes/csv/Image.csv",
                "/metadonnes/xlsx/Image.xlsx",
                (CollectionUtils.isEmpty(imagesFormat))? "[Tous les formats]" : imagesFormat.toString()));

        metadonneList.add(new Metadonne("Audio Wawe BWF",
                "/metadonnes/csv/Audio.csv",
                "/metadonnes/xlsx/Audio.xlsx",
                (CollectionUtils.isEmpty(audiosFormat))? "[Tous les formats]" : audiosFormat.toString()));

        metadonneList.add(new Metadonne("Données laser brutes",
                "/metadonnes/csv/Données laser brutes.csv",
                "/metadonnes/xlsx/Données laser brutes.xlsx",
                "[Tous les formats]"));

        metadonneList.add(new Metadonne("Données laser conso",
                "/metadonnes/csv/Données laser conso.csv",
                "/metadonnes/xlsx/Données laser conso.xlsx",
                "[Tous les formats]"));

        metadonneList.add(new Metadonne("Nuage de points photogrammétrie",
                "/metadonnes/csv/Nuage de points photogrammétrie.csv",
                "/metadonnes/xlsx/Nuage de points photogrammétrie.xlsx",
                (CollectionUtils.isEmpty(nuagePointsFormat))? "[Tous les formats]" : nuagePointsFormat.toString()));

        metadonneList.add(new Metadonne("Maillage 3D photogrammétrie",
                "/metadonnes/csv/Maillage 3D photogrammétrie.csv",
                "/metadonnes/xlsx/Maillage 3D photogrammétrie.xlsx",
                (CollectionUtils.isEmpty(representations2DsFormat))? "[Tous les formats]" : representations2DsFormat.toString()));

        metadonneList.add(new Metadonne("Restitution 3D Geometry",
                "/metadonnes/csv/Restitution 3D Geometry.csv",
                "/metadonnes/xlsx/Restitution 3D Geometry.xlsx",
                (CollectionUtils.isEmpty(representations3DsFormat))? "[Tous les formats]" : representations3DsFormat.toString()));

    }

    public StreamedContent dowloadFile(Metadonne metadonne) {

        InputStream is = MetadonneBean.class.getResourceAsStream(metadonne.getCsvFilePath());
        return new DefaultStreamedContent(is, "*/*", metadonne.getNom() + ".csv");

    }

    public List<Metadonne> getMetadonneList() {
        return metadonneList;
    }

    public void setMetadonneList(List<Metadonne> metadonneList) {
        this.metadonneList = metadonneList;
    }
}
