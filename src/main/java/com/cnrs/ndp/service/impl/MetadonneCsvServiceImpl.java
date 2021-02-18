package com.cnrs.ndp.service.impl;

import com.cnrs.ndp.model.Label;
import com.cnrs.ndp.model.resources.*;
import com.cnrs.ndp.service.MetadonneCsvService;
import com.cnrs.ndp.utils.DateUtils;
import com.cnrs.ndp.utils.MetadonneTitles;
import com.cnrs.ndp.utils.StringUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;


@Service
public class MetadonneCsvServiceImpl implements MetadonneCsvService {

    @Value("${format.mot_cle.separateur_mot}")
    private String motCleSeparateurMot = ";";

    @Value("${format.mot_cle.separateur_url}")
    private String motCleSeparateurUrl;

    private char CHAMPS_DELIMITER = ';';


    public List<Resource> readCsvMetadonne(File file, String schemasSelected) throws IOException {

        List<Resource> resources = new ArrayList<>();

        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().
                withDelimiter(CHAMPS_DELIMITER).withIgnoreEmptyLines().withTrim().parse(new FileReader(file));

        switch (Integer.parseInt(schemasSelected)) {
            case 1:
                resources.addAll(readDeblinCoreCsv(records));
                break;
            case 2:
                resources.addAll(readArticlePresseCsv(records));
                break;
            case 3:
                resources.addAll(readUrlCsv(records));
                break;
            case 4:
                resources.addAll(readVideoCsv(records));
                break;
            case 5:
                resources.addAll(readImageCsv(records));
                break;
            case 6:
                resources.addAll(readAudioWaweBwfCsv(records));
                break;
            case 7:
                resources.addAll(readDonneeLaserBrutCsv(records));
                break;
            case 8:
                resources.addAll(readDonneeLaserConsoCsv(records));
                break;
            case 9:
                resources.addAll(readNuagePointsPhotogrammetrieCsv(records));
                break;
            case 10:
                resources.addAll(readMaillage3dPhotogrammetrieCsv(records));
                break;
            case 11:
                resources.addAll(readMaillage3dGeometryCsv(records));
                break;
        }

        return resources;
    }


    private List<NuagePointsPhotogrammetrie> readNuagePointsPhotogrammetrieCsv(Iterable<CSVRecord> records) {

        List<NuagePointsPhotogrammetrie> resources = new ArrayList<>();

        records.forEach(record -> {
            NuagePointsPhotogrammetrie nuagePointsPhotogrammetrie = new NuagePointsPhotogrammetrie();
            nuagePointsPhotogrammetrie.setTitre(StringUtils.formatFileName(readStringValue(record.get(MetadonneTitles.TITRE))));
            nuagePointsPhotogrammetrie.setCreateur(readStringValue(record.get(MetadonneTitles.CREATEUR)));

            List<Label> labelList = motsCleList(readStringValue(record.get(MetadonneTitles.MOTS_CLES)));
            if (!CollectionUtils.isEmpty(labelList)) {
                nuagePointsPhotogrammetrie.setMotsCles(labelList.stream().map(label -> label.getLabel()).collect(Collectors.toList()));
                nuagePointsPhotogrammetrie.setMotsClesLabel(labelList);
            }

            nuagePointsPhotogrammetrie.setDescription(readStringValue(record.get(MetadonneTitles.DESCRIPTION)));
            nuagePointsPhotogrammetrie.setEditeur(readStringValue(record.get(MetadonneTitles.EDITEUR)));
            nuagePointsPhotogrammetrie.setContributeur(readStringValue(record.get(MetadonneTitles.CONTRIBUTEUR)));
            nuagePointsPhotogrammetrie.setDateMiseDisposition(DateUtils.formatStringToDate(readStringValue(record.get(MetadonneTitles.DATE_MISE_DISPOSITION))));
            nuagePointsPhotogrammetrie.setType(readStringValue(record.get(MetadonneTitles.TYPE)));
            nuagePointsPhotogrammetrie.setFormat(readStringValue(record.get(MetadonneTitles.FORMAT)));
            nuagePointsPhotogrammetrie.setIdentifiantUnique(readStringValue(record.get(MetadonneTitles.IDENTIFIANT_UNIQUE)));
            nuagePointsPhotogrammetrie.setSource(readStringValue(record.get(MetadonneTitles.SOURCE)));
            nuagePointsPhotogrammetrie.setLangue(readStringValue(record.get(MetadonneTitles.LANGUE)));
            nuagePointsPhotogrammetrie.setRelation(readStringValue(record.get(MetadonneTitles.RELATION)));
            nuagePointsPhotogrammetrie.setCouverture(readStringValue(record.get(MetadonneTitles.COUVERTURE)));
            nuagePointsPhotogrammetrie.setGestionDesDroits(readStringValue(record.get(MetadonneTitles.GESTION_DROITS)));
            nuagePointsPhotogrammetrie.setIdSources(readStringValue(record.get(MetadonneTitles.ID_SOURCES)));
            nuagePointsPhotogrammetrie.setLogicielTraitement(readStringValue(record.get(MetadonneTitles.LOGICIEL)));
            nuagePointsPhotogrammetrie.setDensitePointsMoyenne(readStringValue(record.get(MetadonneTitles.DENSITE_POINTS_MOYENNE)));
            nuagePointsPhotogrammetrie.setSystemeCoordonnees(readStringValue(record.get(MetadonneTitles.SYSTEME_COORDONNEES)));
            nuagePointsPhotogrammetrie.setArchitectureFichier(readStringValue(record.get(MetadonneTitles.ARCHITECTURE_FICHIER)));

            resources.add(nuagePointsPhotogrammetrie);
        });

        return resources;
    }

    private List<Maillage3dGeometry> readMaillage3dGeometryCsv(Iterable<CSVRecord> records) {

        List<Maillage3dGeometry> resources = new ArrayList<>();

        records.forEach(record -> {
            Maillage3dGeometry maillage3dGeometry = new Maillage3dGeometry();
            maillage3dGeometry.setTitre(StringUtils.formatFileName(readStringValue(record.get(MetadonneTitles.TITRE))));
            maillage3dGeometry.setAxeOrientation(readStringValue(record.get(MetadonneTitles.AXE_ORIENTATION)));
            maillage3dGeometry.setAxeVertical(readStringValue(record.get(MetadonneTitles.AXE_VERTICAL)));
            maillage3dGeometry.setUniteMesure(readStringValue(record.get(MetadonneTitles.UNITE_MESURE)));
            maillage3dGeometry.setLogicielTraitement(readStringValue(record.get(MetadonneTitles.LOGICIEL_TRAITEMENT)));
            maillage3dGeometry.setDimensionX(readStringValue(record.get(MetadonneTitles.DIMENSION_X)));
            maillage3dGeometry.setDimensionY(readStringValue(record.get(MetadonneTitles.DIMENSION_Y)));
            maillage3dGeometry.setDimensionZ(readStringValue(record.get(MetadonneTitles.DIMENSION_Z)));
            maillage3dGeometry.setCheminFichier(readStringValue(record.get(MetadonneTitles.CHEMIN_FICHIER)));
            maillage3dGeometry.setCreateur(readStringValue(record.get(MetadonneTitles.CREATEUR)));
            maillage3dGeometry.setDateFichier(readDateValue(record.get(MetadonneTitles.DATE_FICHIER)));
            maillage3dGeometry.setFormatFichier(readStringValue(record.get(MetadonneTitles.FORMAT_FICHIER)));
            maillage3dGeometry.setDescription(readStringValue(record.get(MetadonneTitles.DESCRIPTION)));
            maillage3dGeometry.setEncodage(readStringValue(record.get(MetadonneTitles.ENCODAGE)));

            resources.add(maillage3dGeometry);
        });

        return resources;
    }

    private List<Maillage3dPhotogrammetrie> readMaillage3dPhotogrammetrieCsv(Iterable<CSVRecord> records) {

        List<Maillage3dPhotogrammetrie> resources = new ArrayList<>();

        records.forEach(record -> {
            Maillage3dPhotogrammetrie maillage3dPhotogrammetrie = new Maillage3dPhotogrammetrie();

            maillage3dPhotogrammetrie.setTitre(StringUtils.formatFileName(readStringValue(record.get(MetadonneTitles.TITRE))));
            maillage3dPhotogrammetrie.setIdSources(StringUtils.formatFileName(readStringValue(record.get(MetadonneTitles.ID_SOURCES))));
            maillage3dPhotogrammetrie.setMethodeAssemblageNuagesPoints(readStringValue(record.get(MetadonneTitles.METHODE_ASSEMBLAGE_NUAGES_POINTS)));
            maillage3dPhotogrammetrie.setPourcentageDecimation(readStringValue(record.get(MetadonneTitles.POURCENTAGE_DECIMATION)));
            maillage3dPhotogrammetrie.setAlgorithmeUtiliseMaillage(readStringValue(record.get(MetadonneTitles.ALGORITHME_UTILISE_MAILLLAGE)));
            maillage3dPhotogrammetrie.setMethodeTexturage(readStringValue(record.get(MetadonneTitles.METHODE_TEXTURAGE)));
            maillage3dPhotogrammetrie.setProjectionTexture(readStringValue(record.get(MetadonneTitles.PROJECTION_TXTURE)));
            maillage3dPhotogrammetrie.setSourcesFichiersTexture(readStringValue(record.get(MetadonneTitles.SOURCE_FICHIER_TEXTURE)));

            resources.add(maillage3dPhotogrammetrie);
        });

        return resources;
    }

    private List<DonneeLaserBrut> readDonneeLaserBrutCsv(Iterable<CSVRecord> records) {

        List<DonneeLaserBrut> resources = new ArrayList<>();

        records.forEach(record -> {
            DonneeLaserBrut donneeLaserBrut = new DonneeLaserBrut();
            donneeLaserBrut.setTitre(StringUtils.formatFileName(readStringValue(record.get(MetadonneTitles.TITRE))));
            donneeLaserBrut.setCreateur(readStringValue(record.get(MetadonneTitles.CREATEUR)));

            List<Label> labelList = motsCleList(readStringValue(record.get(MetadonneTitles.MOTS_CLES)));
            if (!CollectionUtils.isEmpty(labelList)) {
                donneeLaserBrut.setMotsCles(labelList.stream().map(label -> label.getLabel()).collect(Collectors.toList()));
                donneeLaserBrut.setMotsClesLabel(labelList);
            }

            donneeLaserBrut.setDescription(readStringValue(record.get(MetadonneTitles.DESCRIPTION)));
            donneeLaserBrut.setEditeur(readStringValue(record.get(MetadonneTitles.EDITEUR)));
            donneeLaserBrut.setContributeur(readStringValue(record.get(MetadonneTitles.CONTRIBUTEUR)));
            donneeLaserBrut.setDateMiseDisposition(DateUtils.formatStringToDate(readStringValue(record.get(MetadonneTitles.DATE_MISE_DISPOSITION))));
            donneeLaserBrut.setType(readStringValue(record.get(MetadonneTitles.TYPE)));
            donneeLaserBrut.setFormat(readStringValue(record.get(MetadonneTitles.FORMAT)));
            donneeLaserBrut.setIdentifiantUnique(readStringValue(record.get(MetadonneTitles.IDENTIFIANT_UNIQUE)));
            donneeLaserBrut.setSource(readStringValue(record.get(MetadonneTitles.SOURCE)));
            donneeLaserBrut.setNuage(readStringValue(record.get(MetadonneTitles.NUAGE)));
            donneeLaserBrut.setLangue(readStringValue(record.get(MetadonneTitles.LANGUE)));
            donneeLaserBrut.setRelation(readStringValue(record.get(MetadonneTitles.RELATION)));
            donneeLaserBrut.setCouverture(readStringValue(record.get(MetadonneTitles.COUVERTURE)));
            donneeLaserBrut.setGestionDesDroits(readStringValue(record.get(MetadonneTitles.GESTION_DROITS)));
            donneeLaserBrut.setMateriel(readStringValue(record.get(MetadonneTitles.MATERIEL)));
            donneeLaserBrut.setMethodeMetrologique(readStringValue(record.get(MetadonneTitles.METHODE_METROLOGIQUE)));
            donneeLaserBrut.setResolutionAngulaire(readStringValue(record.get(MetadonneTitles.RESOLUTION_ANGULAIRE)));
            donneeLaserBrut.setResolutionSpatiale(readStringValue(record.get(MetadonneTitles.RESOLUTION_SPATIALE)));
            donneeLaserBrut.setDensitePointMoyenne(readStringValue(record.get(MetadonneTitles.DENSITE_POINT_MOYENNE)));
            donneeLaserBrut.setChampHorizontalStation(readStringValue(record.get(MetadonneTitles.CHAMP_HORIZONATAL_STATION)));
            donneeLaserBrut.setChampVerticalStation(readStringValue(record.get(MetadonneTitles.CHAMP_VERTICAL_STATION)));

            resources.add(donneeLaserBrut);
        });

        return resources;
    }

    private List<DonneeLaserConso> readDonneeLaserConsoCsv(Iterable<CSVRecord> records) {

        List<DonneeLaserConso> resources = new ArrayList<>();

        records.forEach(record -> {
            DonneeLaserConso donneeLaserConso = new DonneeLaserConso();
            donneeLaserConso.setTitre(StringUtils.formatFileName(readStringValue(record.get(MetadonneTitles.TITRE))));
            donneeLaserConso.setCreateur(readStringValue(record.get(MetadonneTitles.CREATEUR)));

            List<Label> labelList = motsCleList(readStringValue(record.get(MetadonneTitles.MOTS_CLES)));
            if (!CollectionUtils.isEmpty(labelList)) {
                donneeLaserConso.setMotsCles(labelList.stream().map(label -> label.getLabel()).collect(Collectors.toList()));
                donneeLaserConso.setMotsClesLabel(labelList);
            }

            donneeLaserConso.setDescription(readStringValue(record.get(MetadonneTitles.DESCRIPTION)));
            donneeLaserConso.setEditeur(readStringValue(record.get(MetadonneTitles.EDITEUR)));
            donneeLaserConso.setContributeur(readStringValue(record.get(MetadonneTitles.CONTRIBUTEUR)));
            donneeLaserConso.setDateMiseDisposition(DateUtils.formatStringToDate(readStringValue(record.get(MetadonneTitles.DATE_MISE_DISPOSITION))));
            donneeLaserConso.setType(readStringValue(record.get(MetadonneTitles.TYPE)));
            donneeLaserConso.setFormat(readStringValue(record.get(MetadonneTitles.FORMAT)));
            donneeLaserConso.setIdentifiantUnique(readStringValue(record.get(MetadonneTitles.IDENTIFIANT_UNIQUE)));
            donneeLaserConso.setSource(readStringValue(record.get(MetadonneTitles.SOURCE)));
            donneeLaserConso.setLangue(readStringValue(record.get(MetadonneTitles.LANGUE)));
            donneeLaserConso.setRelation(readStringValue(record.get(MetadonneTitles.RELATION)));
            donneeLaserConso.setCouverture(readStringValue(record.get(MetadonneTitles.COUVERTURE)));
            donneeLaserConso.setGestionDesDroits(readStringValue(record.get(MetadonneTitles.GESTION_DROITS)));
            donneeLaserConso.setIdSources(readStringValue(record.get(MetadonneTitles.ID_SOURCES)));
            donneeLaserConso.setLogiciel(readStringValue(record.get(MetadonneTitles.LOGICIEL)));
            donneeLaserConso.setMethodeConsolidation(readStringValue(record.get(MetadonneTitles.METHODE_CONSOLIDATION)));
            donneeLaserConso.setSystemeCoordonnees(readStringValue(record.get(MetadonneTitles.SYSTEME_COORDONNEES)));

            resources.add(donneeLaserConso);
        });

        return resources;
    }

    private List<AudioWaweBwf> readAudioWaweBwfCsv(Iterable<CSVRecord> records) {

        List<AudioWaweBwf> resources = new ArrayList<>();

        records.forEach(record -> {
            AudioWaweBwf audioWaweBwf = new AudioWaweBwf();
            audioWaweBwf.setTitre(StringUtils.formatFileName(readStringValue(record.get(MetadonneTitles.TITRE))));
            audioWaweBwf.setCreateur(record.get(MetadonneTitles.CREATEUR));

            List<Label> labelList = motsCleList(readStringValue(record.get(MetadonneTitles.MOTS_CLES)));
            if (!CollectionUtils.isEmpty(labelList)) {
                audioWaweBwf.setMotsCles(labelList.stream().map(label -> label.getLabel()).collect(Collectors.toList()));
                audioWaweBwf.setMotsClesLabel(labelList);
            }

            audioWaweBwf.setEditeur(readStringValue(record.get(MetadonneTitles.EDITEUR)));
            audioWaweBwf.setContributeur(readStringValue(record.get(MetadonneTitles.CONTRIBUTEUR)));
            audioWaweBwf.setFormat(readStringValue(record.get(MetadonneTitles.FORMAT)));
            audioWaweBwf.setIdentifiantUnique(readStringValue(record.get(MetadonneTitles.IDENTIFIANT_UNIQUE)));
            audioWaweBwf.setDescription(readStringValue(record.get(MetadonneTitles.DESCRIPTION)));
            audioWaweBwf.setLangue(readStringValue(record.get(MetadonneTitles.LANGUE)));
            audioWaweBwf.setRelation(readStringValue(record.get(MetadonneTitles.RELATION)));
            audioWaweBwf.setSource(readStringValue(record.get(MetadonneTitles.SOURCE)));
            audioWaweBwf.setGestionDesDroits(readStringValue(record.get(MetadonneTitles.GESTION_DROITS)));
            audioWaweBwf.setCouverture(readStringValue(record.get(MetadonneTitles.COUVERTURE)));
            audioWaweBwf.setIcrd(DateUtils.formatStringToDate(readStringValue(record.get(MetadonneTitles.ICRD))));
            audioWaweBwf.setIgnr(readStringValue(record.get(MetadonneTitles.IGNR)));
            audioWaweBwf.setOriginatorReference(readStringValue(record.get(MetadonneTitles.ORIGINATOR_REFERENCE)));
            audioWaweBwf.setOriginationDate(DateUtils.formatStringToDate(readStringValue(record.get(MetadonneTitles.ORIGINATION_DATE))));
            audioWaweBwf.setOriginationTime(readStringValue(record.get(MetadonneTitles.ORIGINATION_TIME)));
            audioWaweBwf.setTimeReferenceTranslated(readStringValue(record.get(MetadonneTitles.TIME_REFERENCE_TRANSLATED)));
            audioWaweBwf.setTimeReference(readStringValue(record.get(MetadonneTitles.TIME_REFERENCE)));
            audioWaweBwf.setBextVersion(readStringValue(record.get(MetadonneTitles.BEXT_VERSION)));
            audioWaweBwf.setCodingHistory(readStringValue(record.get(MetadonneTitles.CODING_HISTORY)));
            audioWaweBwf.setIarl(readStringValue(record.get(MetadonneTitles.IARL)));
            audioWaweBwf.setIcmt(readStringValue(record.get(MetadonneTitles.ICMT)));
            audioWaweBwf.setIeng(readStringValue(record.get(MetadonneTitles.IENG)));
            audioWaweBwf.setImed(readStringValue(record.get(MetadonneTitles.IMED)));
            audioWaweBwf.setIsft(readStringValue(record.get(MetadonneTitles.ISFT)));

            resources.add(audioWaweBwf);
        });

        return resources;
    }

    private List<Image> readImageCsv(Iterable<CSVRecord> records) {

        List<Image> resources = new ArrayList<>();

        records.forEach(record -> {
            Image image = new Image();
            image.setTitre(StringUtils.formatFileName(readStringValue(record.get(MetadonneTitles.TITRE))));
            image.setCreateur(readStringValue(record.get(MetadonneTitles.CREATEUR)));

            List<Label> labelList = motsCleList(readStringValue(record.get(MetadonneTitles.MOTS_CLES)));
            if (!CollectionUtils.isEmpty(labelList)) {
                image.setMotsCles(labelList.stream().map(label -> label.getLabel()).collect(Collectors.toList()));
                image.setMotsClesLabel(labelList);
            }

            image.setEditeur(readStringValue(record.get(MetadonneTitles.EDITEUR)));
            image.setContributeur(readStringValue(record.get(MetadonneTitles.CONTRIBUTEUR)));
            image.setDateCreation(DateUtils.formatStringToDate(readStringValue(record.get(MetadonneTitles.DATE_CREATION))));
            image.setType(readStringValue(record.get(MetadonneTitles.TYPE)));
            image.setSupportOriginalFichier(readStringValue(record.get(MetadonneTitles.SUPPORT_ORIGINAL_FICHIER)));
            image.setExtension(readStringValue(record.get(MetadonneTitles.EXTENTION)));
            image.setDateMiseDisposition(DateUtils.formatStringToDate(readStringValue(record.get(MetadonneTitles.DATE_MISE_DISPOSITION))));
            image.setCitationBibliographie(readStringValue(record.get(MetadonneTitles.CITATION_BIBLIOGRAPHIQUE)));
            image.setGestionDesDroits(readStringValue(record.get(MetadonneTitles.GESTION_DROITS)));
            image.setIdentifiantUnique(readStringValue(record.get(MetadonneTitles.IDENTIFIANT_UNIQUE)));
            image.setFileSize(readStringValue(record.get(MetadonneTitles.FILE_SIZE)));
            image.setModel(readStringValue(record.get(MetadonneTitles.MODEL)));
            image.setImageSize(readStringValue(record.get(MetadonneTitles.IMAGE_SIZE)));
            image.setQuality(readStringValue(record.get(MetadonneTitles.QUALITY)));
            image.setFocalLength(readStringValue(record.get(MetadonneTitles.FOCAL_LENGTH)));
            image.setShutterSpeed(readStringValue(record.get(MetadonneTitles.SHITTER_SPEED)));
            image.setAperture(readStringValue(record.get(MetadonneTitles.APERTURE)));
            image.setIso(readStringValue(record.get(MetadonneTitles.ISO)));
            image.setWhiteBalance(readStringValue(record.get(MetadonneTitles.WHITE_BALANCE)));
            image.setFlash(readStringValue(record.get(MetadonneTitles.FLASH)));
            image.setXResolution(readStringValue(record.get(MetadonneTitles.XRESOLUTION)));
            image.setYResolution(readStringValue(record.get(MetadonneTitles.YRESOLUTION)));
            image.setPreservedFileName(readStringValue(record.get(MetadonneTitles.PRESERVED_FILENAME)));

            resources.add(image);
        });

        return resources;
    }

    private List<Video> readVideoCsv(Iterable<CSVRecord> records) {

        List<Video> resources = new ArrayList<>();

        records.forEach(record -> {
            Video video = new Video();
            video.setTitre(StringUtils.formatFileName(readStringValue(record.get(MetadonneTitles.TITRE))));
            video.setCreateur(readStringValue(record.get(MetadonneTitles.CREATEUR)));

            List<Label> labelList = motsCleList(readStringValue(record.get(MetadonneTitles.MOTS_CLES)));
            if (!CollectionUtils.isEmpty(labelList)) {
                video.setMotsCles(labelList.stream().map(label -> label.getLabel()).collect(Collectors.toList()));
                video.setMotsClesLabel(labelList);
            }

            video.setDescription(readStringValue(record.get(MetadonneTitles.DESCRIPTION)));
            video.setMedia(readStringValue(record.get(MetadonneTitles.MEDIA)));
            video.setEditeur(readStringValue(record.get(MetadonneTitles.EDITEUR)));
            video.setContributeur(readStringValue(record.get(MetadonneTitles.CONTRIBUTEUR)));
            video.setLangue(readStringValue(record.get(MetadonneTitles.LANGUE)));
            video.setDateCreationFichier(readDateValue(record.get(MetadonneTitles.DATE_CREATION_FICHIER)));
            video.setType(readStringValue(record.get(MetadonneTitles.TYPE)));
            video.setSupport(readStringValue(record.get(MetadonneTitles.SUPPORT)));
            video.setFormat(readStringValue(record.get(MetadonneTitles.FORMAT)));
            video.setIdentifiantUnique(readStringValue(record.get(MetadonneTitles.IDENTIFIANT_UNIQUE)));
            video.setExtension(readStringValue(record.get(MetadonneTitles.EXTENTION)));
            video.setLienInternet(readStringValue(record.get(MetadonneTitles.LIEN_INTERNET)));
            video.setDateConsultation(readDateValue(record.get(MetadonneTitles.DATE_CONSULTATION)));
            video.setDateCreationMp4(readDateValue(record.get(MetadonneTitles.DATE_CREATION_MP4)));
            video.setRelation(readStringValue(record.get(MetadonneTitles.RELATION)));
            video.setLienRelation(readStringValue(record.get(MetadonneTitles.LIEN_RELATION)));
            video.setNotesInternes(readStringValue(record.get(MetadonneTitles.NOTES_INTERNES)));
            video.setPreparation(readStringValue(record.get(MetadonneTitles.PREPARATION)));
            video.setCollecteur(readStringValue(record.get(MetadonneTitles.COLLECTEUR)));
            video.setCitationBibliographie(readStringValue(record.get(MetadonneTitles.CITATION_BIBLIOGRAPHIQUE)));
            video.setGestionDesDroits(readStringValue(record.get(MetadonneTitles.GESTION_DROITS)));
            resources.add(video);
        });

        return resources;
    }

    private List<Url> readUrlCsv(Iterable<CSVRecord> records) {

        List<Url> resources = new ArrayList<>();

        records.forEach(record -> {
            Url url = new Url();
            url.setTitre(StringUtils.formatFileName(readStringValue(record.get(MetadonneTitles.TITRE))));
            url.setDateCreationFichier(readDateValue(record.get(MetadonneTitles.DATE_CREATION_FICHIER)));
            url.setLienInternet(readStringValue(record.get(MetadonneTitles.LIEN_INTERNET)));
            url.setCreateur(readStringValue(record.get(MetadonneTitles.CREATEUR)));
            url.setType(readStringValue(record.get(MetadonneTitles.TYPE)));
            url.setEditeur(readStringValue(record.get(MetadonneTitles.EDITEUR)));
            url.setRelation(readStringValue(record.get(MetadonneTitles.RELATION)));
            url.setNotesInternes(readStringValue(record.get(MetadonneTitles.NOTES_INTERNES)));
            url.setPreparation(readStringValue(record.get(MetadonneTitles.PREPARATION)));
            url.setIdentifiantUnique(readStringValue(record.get(MetadonneTitles.IDENTIFIANT_UNIQUE)));
            resources.add(url);
        });

        return resources;
    }

    private List<ArticlePresse> readArticlePresseCsv(Iterable<CSVRecord> records) {

        List<ArticlePresse> resources = new ArrayList<>();

        records.forEach(record -> {
            ArticlePresse articlePresse = new ArticlePresse();

            articlePresse.setTitre(StringUtils.formatFileName(readStringValue(record.get(MetadonneTitles.TITRE))));
            articlePresse.setCreateur(readStringValue(record.get(MetadonneTitles.CREATEUR)));

            List<Label> labelList = motsCleList(readStringValue(record.get(MetadonneTitles.MOTS_CLES)));
            if (!CollectionUtils.isEmpty(labelList)) {
                articlePresse.setMotsCles(labelList.stream().map(label -> label.getLabel()).collect(Collectors.toList()));
                articlePresse.setMotsClesLabel(labelList);
            }

            articlePresse.setDescription(readStringValue(record.get(MetadonneTitles.DESCRIPTION)));
            articlePresse.setMedia(readStringValue(record.get(MetadonneTitles.MEDIA)));
            articlePresse.setEditeur(readStringValue(record.get(MetadonneTitles.EDITEUR)));
            articlePresse.setContributeur(readStringValue(record.get(MetadonneTitles.CONTRIBUTEUR)));
            articlePresse.setLangue(readStringValue(record.get(MetadonneTitles.LANGUE)));
            articlePresse.setDateCreationFichier(readDateValue(record.get(MetadonneTitles.DATE_CREATION_FICHIER)));
            articlePresse.setType(readStringValue(record.get(MetadonneTitles.TYPE)));
            articlePresse.setSupport(readStringValue(record.get(MetadonneTitles.SUPPORT)));
            articlePresse.setFormat(readStringValue(record.get(MetadonneTitles.FORMAT)));
            articlePresse.setIdentifiantUnique(readStringValue(record.get(MetadonneTitles.IDENTIFIANT_UNIQUE)));
            articlePresse.setExtension(readStringValue(record.get(MetadonneTitles.EXTENTION)));
            articlePresse.setLienInternet(readStringValue(record.get(MetadonneTitles.LIEN_INTERNET)));
            articlePresse.setDateConsultation(readDateValue(record.get(MetadonneTitles.DATE_CONSULTATION)));
            articlePresse.setRelation(readStringValue(record.get(MetadonneTitles.RELATION)));
            articlePresse.setRelationLien(readStringValue(record.get(MetadonneTitles.RELATION_LIEN)));
            articlePresse.setDateCreationPDF(readDateValue(record.get(MetadonneTitles.DATE_CREATION_PDF)));
            articlePresse.setNotesInternes(readStringValue(record.get(MetadonneTitles.NOTES_INTERNES)));
            articlePresse.setPreparation(readStringValue(record.get(MetadonneTitles.PREPARATION)));
            articlePresse.setCollecteur(readStringValue(record.get(MetadonneTitles.COLLECTEUR)));
            articlePresse.setCitationBibliographie(readStringValue(record.get(MetadonneTitles.CITATION_BIBLIOGRAPHIQUE)));
            articlePresse.setGestionDesDroits(readStringValue(record.get(MetadonneTitles.GESTION_DROITS)));

            resources.add(articlePresse);
        });

        return resources;
    }

    private List<DeblinCore> readDeblinCoreCsv(Iterable<CSVRecord> records) throws IndexOutOfBoundsException, IOException {

        List<DeblinCore> deblinCoresList = new ArrayList<>();

        records.forEach(record -> {
            DeblinCore deblinCore = new DeblinCore();
            deblinCore.setTitre(StringUtils.formatFileName(readStringValue(record.get(MetadonneTitles.TITRE))));
            deblinCore.setCreateur(readStringValue(record.get(MetadonneTitles.CREATEUR)));

            List<Label> labelList = motsCleList(readStringValue(record.get(MetadonneTitles.MOTS_CLES)));
            if (!CollectionUtils.isEmpty(labelList)) {
                deblinCore.setMotsCles(labelList.stream().map(label -> label.getLabel()).collect(Collectors.toList()));
                deblinCore.setMotsClesLabel(labelList);
            }

            deblinCore.setDescription(readStringValue(record.get(MetadonneTitles.DESCRIPTION)));
            deblinCore.setEditeur(readStringValue(record.get(MetadonneTitles.EDITEUR)));
            deblinCore.setContributeur(readStringValue(record.get(MetadonneTitles.CONTRIBUTEUR)));
            deblinCore.setDateMiseDisposition(readDateValue(record.get(MetadonneTitles.DATE_MISE_DISPOSITION)));
            deblinCore.setType(readStringValue(record.get(MetadonneTitles.TYPE)));
            deblinCore.setFormat(readStringValue(record.get(MetadonneTitles.FORMAT)));
            deblinCore.setIdentifiantUnique(readStringValue(record.get(MetadonneTitles.IDENTIFIANT_UNIQUE)));
            deblinCore.setSource(readStringValue(record.get(MetadonneTitles.SOURCE)));
            deblinCore.setLangue(readStringValue(record.get(MetadonneTitles.LANGUE)));
            deblinCore.setRelation(readStringValue(record.get(MetadonneTitles.RELATION)));
            deblinCore.setCouverture(readStringValue(record.get(MetadonneTitles.COUVERTURE)));
            deblinCore.setGestionDesDroits(readStringValue(record.get(MetadonneTitles.GESTION_DROITS)));

            deblinCoresList.add(deblinCore);
        });

        return deblinCoresList;
    }

    private List<Label> motsCleList(String str) {
        List<Label> labels = new ArrayList<>();

        List<String> labelsStr = Arrays.asList(str.split(motCleSeparateurMot));
        for (String labelStr : labelsStr) {
            Label label = new Label();
            List<String> tmp = Arrays.asList(labelStr.split(motCleSeparateurUrl));
            if (!CollectionUtils.isEmpty(tmp)) {
                label.setLabel(tmp.get(0));
                if (tmp.size()>1) {
                    label.setUri(tmp.get(1));
                }
            }
            labels.add(label);
        }
        return labels;
    }

    private String readStringValue(String str) {
        try {
            return org.springframework.util.StringUtils.isEmpty(str) ? "" : str;
        } catch (Exception ex) {
            return "";
        }
    }

    private Date readDateValue(String str) {
        try {
            return org.springframework.util.StringUtils.isEmpty(str) ? null : DateUtils.formatStringToDate(str);
        } catch (Exception ex) {
            return null;
        }
    }
}
