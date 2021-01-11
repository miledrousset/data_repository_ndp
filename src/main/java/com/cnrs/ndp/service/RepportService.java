package com.cnrs.ndp.service;

import com.cnrs.ndp.model.resources.*;
import com.cnrs.ndp.utils.DateUtils;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class RepportService {


    @Value("${format.mot_cle.separateur}")
    private String motCleSeparateur;

    private final static String FORMAT_REPPORT = ".csv";
    private final static char SEPARATEUR = ';';


    public void createDeblinCoreRepport(List<Resource> resources, String filePath, String fileName, String schemaSelected) {
        motCleSeparateur = "|";
        if (!CollectionUtils.isEmpty(resources)) {

            File file = new File(filePath + fileName + FORMAT_REPPORT);
            if (file.exists()) {
                file.delete();
            }

            try {
                FileWriter outputfile = new FileWriter(file);

                CSVWriter writer = new CSVWriter(outputfile,
                        SEPARATEUR,
                        CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END);

                List<String[]> data = new ArrayList<>();
                switch (Integer.parseInt(schemaSelected)) {
                    case 1:
                        data = createDeblinCoreRepport(resources);
                        break;
                    case 2:
                        data = createArticlePresseRepport(resources);
                        break;
                    case 3:
                        data = createUrlRepport(resources);
                        break;
                    case 4:
                        data = createVideoRepport(resources);
                        break;
                    case 5:
                        data = createImageRepport(resources);
                        break;
                    case 6:
                        data = createAudioRepport(resources);
                        break;
                    case 7:
                        data = createDonneLaserBruitRepport(resources);
                        break;
                    case 8:
                        data = createDonneLaserConsoRepport(resources);
                        break;
                    case 9:
                        data = createNuagePointsPhotogrammetrieRepport(resources);
                        break;
                    case 10:
                        data = createMaillage3dPhotogrammetrieRepport(resources);
                        break;
                    case 11:
                        data = createMaillage3dGeometryRepport(resources);
                        break;
                }

                writer.writeAll(data);
                writer.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private List<String[]> createMaillage3dGeometryRepport(List<Resource> resources) {
        List<String[]> data = new ArrayList<>();
        data.add(new String[] { "Titre", "Axe orientation", "Axe vertical", "Unité de mesure", "Logiciel de traitement",
                "Dimension X", "Dimension Y", "Dimension Z", "Chemin du fichier", "Créateur", "Date du fichier",
                "Format du fichier", "Description", "Encodage"});

        for (Resource rowData : resources) {
            Maillage3dGeometry maillage3dGeometry = (Maillage3dGeometry) rowData;
            data.add(new String[] {
                    maillage3dGeometry.getTitre(),
                    maillage3dGeometry.getAxeOrientation(),
                    maillage3dGeometry.getAxeVertical(),
                    maillage3dGeometry.getUniteMesure(),
                    maillage3dGeometry.getLogicielTraitement(),
                    maillage3dGeometry.getDimensionX(),
                    maillage3dGeometry.getDimensionY(),
                    maillage3dGeometry.getDimensionZ(),
                    maillage3dGeometry.getCheminFichier(),
                    maillage3dGeometry.getCreateur(),
                    maillage3dGeometry.getDateFichier() != null ? DateUtils.formatDateToString(maillage3dGeometry.getDateFichier()) : "",
                    maillage3dGeometry.getFormatFichier(),
                    maillage3dGeometry.getDescription(),
                    maillage3dGeometry.getEncodage()});
        }
        return data;
    }


    private List<String[]> createMaillage3dPhotogrammetrieRepport(List<Resource> resources) {
        List<String[]> data = new ArrayList<>();
        data.add(new String[] { "Titre", "ID Sources", "Méthode d'assemblage des nuages de points (si plusieurs sources)",
                "Pourcentage de décimation", "Algorithme utilisé pour le maillage", "Méthode de texturage",
                "Projection de la texture", "Sources des fichiers de texture"});

        for (Resource rowData : resources) {
            Maillage3dPhotogrammetrie maillage3dPhotogrammetrie = (Maillage3dPhotogrammetrie) rowData;
            data.add(new String[] {
                    maillage3dPhotogrammetrie.getTitre(),
                    maillage3dPhotogrammetrie.getIdSources(),
                    maillage3dPhotogrammetrie.getMethodeAssemblageNuagesPoints(),
                    maillage3dPhotogrammetrie.getPourcentageDecimation(),
                    maillage3dPhotogrammetrie.getAlgorithmeUtiliseMaillage(),
                    maillage3dPhotogrammetrie.getMethodeTexturage(),
                    maillage3dPhotogrammetrie.getProjectionTexture(),
                    maillage3dPhotogrammetrie.getSourcesFichiersTexture()});
        }
        return data;
    }


    private List<String[]> createNuagePointsPhotogrammetrieRepport(List<Resource> resources) {
        List<String[]> data = new ArrayList<>();
        data.add(new String[] { "Titre", "Createur", "Mots clés", "Description", "Editeur", "Contributeur", "Date de mise à disposition",
                "Type", "Format", "Identifiant unique", "Source", "Langue", "Relation", "Couverture", "Gestion des droits",
                "ID Sources", "Logiciel de traitement", "Densité de points moyenne", "Système de coordonnées", "Architecture du fichier"});

        for (Resource rowData : resources) {
            NuagePointsPhotogrammetrie nuagePointsPhotogrammetrie = (NuagePointsPhotogrammetrie) rowData;

            StringBuffer motsCle = new StringBuffer();
            if (!CollectionUtils.isEmpty(nuagePointsPhotogrammetrie.getMotsCles())) {
                for (String s : nuagePointsPhotogrammetrie.getMotsCles()) {
                    motsCle.append(s).append(motCleSeparateur);
                }
            }

            data.add(new String[] {
                    nuagePointsPhotogrammetrie.getTitre(),
                    nuagePointsPhotogrammetrie.getCreateur(),
                    motsCle.toString(),
                    nuagePointsPhotogrammetrie.getDescription(),
                    nuagePointsPhotogrammetrie.getEditeur(),
                    nuagePointsPhotogrammetrie.getContributeur(),
                    nuagePointsPhotogrammetrie.getDateMiseDisposition() != null ? DateUtils.formatDateToString(nuagePointsPhotogrammetrie.getDateMiseDisposition()) : "",
                    nuagePointsPhotogrammetrie.getType(),
                    nuagePointsPhotogrammetrie.getFormat(),
                    nuagePointsPhotogrammetrie.getIdentifiantUnique(),
                    nuagePointsPhotogrammetrie.getSource(),
                    nuagePointsPhotogrammetrie.getLangue(),
                    nuagePointsPhotogrammetrie.getRelation(),
                    nuagePointsPhotogrammetrie.getCouverture(),
                    nuagePointsPhotogrammetrie.getGestionDesDroits(),
                    nuagePointsPhotogrammetrie.getIdSources(),
                    nuagePointsPhotogrammetrie.getLogicielTraitement(),
                    nuagePointsPhotogrammetrie.getDensitePointsMoyenne(),
                    nuagePointsPhotogrammetrie.getSystemeCoordonnees(),
                    nuagePointsPhotogrammetrie.getArchitectureFichier()});
        }
        return data;
    }


    private List<String[]> createDonneLaserConsoRepport(List<Resource> resources) {
        List<String[]> data = new ArrayList<>();
        data.add(new String[] { "Titre", "Createur", "Mots clés", "Description", "Editeur", "Contributeur", "Date de mise à disposition",
                "Type", "Format", "Identifiant unique", "Source", "Langue", "Relation", "Couverture",
                "Gestion des droits", "ID Sources", "Logiciel", "Méthode de consolidation", "Système de coordonnées"});

        for (Resource rowData : resources) {
            DonneeLaserConso donneeLaserConso = (DonneeLaserConso) rowData;

            StringBuffer motsCle = new StringBuffer();
            if (!CollectionUtils.isEmpty(donneeLaserConso.getMotsCles())) {
                for (String s : donneeLaserConso.getMotsCles()) {
                    motsCle.append(s).append(motCleSeparateur);
                }
            }

            data.add(new String[] {
                    donneeLaserConso.getTitre(),
                    donneeLaserConso.getCreateur(),
                    motsCle.toString(),
                    donneeLaserConso.getDescription(),
                    donneeLaserConso.getEditeur(),
                    donneeLaserConso.getContributeur(),
                    donneeLaserConso.getDateMiseDisposition() != null ? DateUtils.formatDateToString(donneeLaserConso.getDateMiseDisposition()) : "",
                    donneeLaserConso.getType(),
                    donneeLaserConso.getFormat(),
                    donneeLaserConso.getIdentifiantUnique(),
                    donneeLaserConso.getSource(),
                    donneeLaserConso.getLangue(),
                    donneeLaserConso.getRelation(),
                    donneeLaserConso.getCouverture(),
                    donneeLaserConso.getGestionDesDroits(),
                    donneeLaserConso.getIdSources(),
                    donneeLaserConso.getLogiciel(),
                    donneeLaserConso.getMethodeConsolidation(),
                    donneeLaserConso.getSystemeCoordonnees()});
        }
        return data;
    }


    private List<String[]> createDonneLaserBruitRepport(List<Resource> resources) {
        List<String[]> data = new ArrayList<>();
        data.add(new String[] { "Titre", "Createur", "Mots clés", "Description", "Editeur", "Contributeur", "Date de mise à disposition",
                "Type", "Format", "Identifiant unique", "Source", "Langue", "Nuage", "Relation", "Couverture",
                "Gestion des droits", "Matériel", "Méthode métrologique", "Résolution angulaire", "Résolution spatiale",
                "Densité de points moyenne", "Champ horizontal de la station", "Champ vertical de la station" });

        for (Resource rowData : resources) {
            DonneeLaserBrut donneeLaserBrut = (DonneeLaserBrut) rowData;

            StringBuffer motsCle = new StringBuffer();
            if (!CollectionUtils.isEmpty(donneeLaserBrut.getMotsCles())) {
                for (String s : donneeLaserBrut.getMotsCles()) {
                    motsCle.append(s).append(motCleSeparateur);
                }
            }

            data.add(new String[] {
                    donneeLaserBrut.getTitre(),
                    donneeLaserBrut.getCreateur(),
                    motsCle.toString(),
                    donneeLaserBrut.getDescription(),
                    donneeLaserBrut.getEditeur(),
                    donneeLaserBrut.getContributeur(),
                    donneeLaserBrut.getDateMiseDisposition() != null ? DateUtils.formatDateToString(donneeLaserBrut.getDateMiseDisposition()) : "",
                    donneeLaserBrut.getType(),
                    donneeLaserBrut.getFormat(),
                    donneeLaserBrut.getIdentifiantUnique(),
                    donneeLaserBrut.getSource(),
                    donneeLaserBrut.getLangue(),
                    donneeLaserBrut.getNuage(),
                    donneeLaserBrut.getRelation(),
                    donneeLaserBrut.getCouverture(),
                    donneeLaserBrut.getGestionDesDroits(),
                    donneeLaserBrut.getMateriel(),
                    donneeLaserBrut.getMethodeMetrologique(),
                    donneeLaserBrut.getResolutionAngulaire(),
                    donneeLaserBrut.getResolutionSpatiale(),
                    donneeLaserBrut.getDensitePointMoyenne(),
                    donneeLaserBrut.getChampHorizontalStation(),
                    donneeLaserBrut.getChampVerticalStation()});
        }
        return data;
    }


    private List<String[]> createAudioRepport(List<Resource> resources) {
        List<String[]> data = new ArrayList<>();
        data.add(new String[] { "Titre", "Createur", "Mots clés", "Editeur", "Contributeur", "Format", "Identifiant unique",
                "Description", "Langue", "Relation", "Source", "Gestion des droits", "Couverture", "ICRD", "IGNR",
                "Origination Reference", "Origination Date", "Origination Time", "Time Reference translated",
                "Time Reference", "Bext Version", "Coding History", "IARL", "ICMT", "IENG", "IMED", "ISFT" });

        for (Resource rowData : resources) {
            AudioWaweBwf audioWaweBwf = (AudioWaweBwf) rowData;

            StringBuffer motsCle = new StringBuffer();
            if (!CollectionUtils.isEmpty(audioWaweBwf.getMotsCles())) {
                for (String s : audioWaweBwf.getMotsCles()) {
                    motsCle.append(s).append(motCleSeparateur);
                }
            }

            data.add(new String[] {
                    audioWaweBwf.getTitre(),
                    audioWaweBwf.getCreateur(),
                    motsCle.toString(),
                    audioWaweBwf.getEditeur(),
                    audioWaweBwf.getContributeur(),
                    audioWaweBwf.getFormat(),
                    audioWaweBwf.getIdentifiantUnique(),
                    audioWaweBwf.getDescription(),
                    audioWaweBwf.getLangue(),
                    audioWaweBwf.getRelation(),
                    audioWaweBwf.getSource(),
                    audioWaweBwf.getGestionDesDroits(),
                    audioWaweBwf.getCouverture(),
                    audioWaweBwf.getIcrd() != null ? DateUtils.formatDateToString(audioWaweBwf.getIcrd()) : "",
                    audioWaweBwf.getIgnr(),
                    audioWaweBwf.getOriginatorReference(),
                    audioWaweBwf.getOriginationDate() != null ? DateUtils.formatDateToString(audioWaweBwf.getOriginationDate()) : "",
                    audioWaweBwf.getOriginationTime(),
                    audioWaweBwf.getTimeReferenceTranslated(),
                    audioWaweBwf.getTimeReference(),
                    audioWaweBwf.getBextVersion(),
                    audioWaweBwf.getCodingHistory(),
                    audioWaweBwf.getIarl(),
                    audioWaweBwf.getIcmt(),
                    audioWaweBwf.getIeng(),
                    audioWaweBwf.getImed(),
                    audioWaweBwf.getIsft()});
        }
        return data;
    }


    private List<String[]> createImageRepport(List<Resource> resources) {
        List<String[]> data = new ArrayList<>();
        data.add(new String[] { "Titre", "Createur", "Mots clés", "Description", "Media", "Éditeur", "Contributeur",
                "Langue", "Date de création du fichier", "Type", "Support", "Format", "Identifiant unique",
                "Extension", "Lien internet", "Date de consultation", "Date de création du .mp4", "Relation",
                "Lien internet", "Preparation", "Collecteur", "Citation Bibliographique", "Gestion des droits" });

        for (Resource rowData : resources) {
            Image video = (Image) rowData;

            StringBuffer motsCle = new StringBuffer();
            if (!CollectionUtils.isEmpty(video.getMotsCles())) {
                for (String s : video.getMotsCles()) {
                    motsCle.append(s).append(motCleSeparateur);
                }
            }

            data.add(new String[] {
                    video.getTitre(),
                    video.getCreateur(),
                    motsCle.toString(),
                    video.getEditeur(),
                    video.getContributeur(),
                    video.getDateCreation() != null ? DateUtils.formatDateToString(video.getDateCreation()) : "",
                    video.getType(),
                    video.getSupportOriginalFichier(),
                    video.getExtension(),
                    video.getDateMiseDisposition() != null ? DateUtils.formatDateToString(video.getDateMiseDisposition()) : "",
                    video.getCitationBibliographie(),
                    video.getGestionDesDroits(),
                    video.getIdentifiantUnique(),
                    video.getFileSize(),
                    video.getModel(),
                    video.getImageSize(),
                    video.getQuality(),
                    video.getFocalLength(),
                    video.getShutterSpeed(),
                    video.getAperture(),
                    video.getAperture(),
                    video.getIso(),
                    video.getWhiteBalance(),
                    video.getFlash(),
                    video.getXResolution(),
                    video.getYResolution(),
                    video.getPreservedFileName()});
        }
        return data;
    }


    private List<String[]> createVideoRepport(List<Resource> resources) {
        List<String[]> data = new ArrayList<>();
        data.add(new String[] { "Titre", "Createur", "Mots clés", "Description", "Media", "Éditeur", "Contributeur",
                "Langue", "Date de création du fichier", "Type", "Support", "Format", "Identifiant unique",
                "Extension", "Lien internet", "Date de consultation", "Date de création du .mp4", "Relation",
                "Lien internet", "Preparation", "Collecteur", "Citation Bibliographique", "Gestion des droits" });

        for (Resource rowData : resources) {
            Video video = (Video) rowData;

            StringBuffer motsCle = new StringBuffer();
            if (!CollectionUtils.isEmpty(video.getMotsCles())) {
                for (String s : video.getMotsCles()) {
                    motsCle.append(s).append(motCleSeparateur);
                }
            }

            data.add(new String[] {
                    video.getTitre(),
                    video.getCreateur(),
                    motsCle.toString(),
                    video.getDescription(),
                    video.getMedia(),
                    video.getEditeur(),
                    video.getContributeur(),
                    video.getLangue(),
                    video.getDateCreationFichier() != null ? DateUtils.formatDateToString(video.getDateCreationFichier()) : "",
                    video.getType(),
                    video.getSupport(),
                    video.getFormat(),
                    video.getIdentifiantUnique(),
                    video.getExtension(),
                    video.getLienInternet(),
                    video.getDateConsultation(),
                    video.getDateCreationMp4() != null ? DateUtils.formatDateToString(video.getDateCreationMp4()) : "",
                    video.getRelation(),
                    video.getLienInternet(),
                    video.getPreparation(),
                    video.getCollecteur(),
                    video.getCitationBibliographie(),
                    video.getGestionDesDroits()});
        }
        return data;
    }


    private List<String[]> createUrlRepport(List<Resource> resources) {
        List<String[]> data = new ArrayList<>();
        data.add(new String[] { "Titre", "Date de creation du fichier", "Lien internet", "Créateur", "Type", "Éditeur",
                "Relation", "Notes internes", "Préparation", "Identifiant unique"});

        for (Resource rowData : resources) {
            Url url = (Url) rowData;
            data.add(new String[] {
                    url.getTitre(),
                    url.getDateCreationFichier() != null ? DateUtils.formatDateToString(url.getDateCreationFichier()) : "",
                    url.getLienInternet(),
                    url.getCreateur(),
                    url.getType(),
                    url.getType(),
                    url.getEditeur(),
                    url.getRelation(),
                    url.getNotesInternes(),
                    url.getPreparation(),
                    url.getIdentifiantUnique()});
        }
        return data;
    }


    private List<String[]> createArticlePresseRepport(List<Resource> resources) {
        List<String[]> data = new ArrayList<>();
        data.add(new String[] { "Titre", "Createur", "Mots clés", "Description", "Media", "Éditeur", "Contributeur",
                "Langue", "Date de création du fichier", "Type", "Support", "Format", "Identifiant unique",
                "Extension", "Lien internet", "Date de consultation", "Relation", "Lien relation", "Date de cration du PDF",
                "Notes internes", "Preparation", "Collecteur", "Citation Bibliographique", "Gestion des droits" });

        for (Resource rowData : resources) {
            ArticlePresse articlePresse = (ArticlePresse) rowData;

            StringBuffer motsCle = new StringBuffer();
            if (!CollectionUtils.isEmpty(articlePresse.getMotsCles())) {
                for (String s : articlePresse.getMotsCles()) {
                    motsCle.append(s).append(motCleSeparateur);
                }
            }

            data.add(new String[] {
                    articlePresse.getTitre(),
                    articlePresse.getCreateur(),
                    motsCle.toString(),
                    articlePresse.getDescription(),
                    articlePresse.getMedia(),
                    articlePresse.getEditeur(),
                    articlePresse.getContributeur(),
                    articlePresse.getLangue(),
                    articlePresse.getDateCreationFichier() != null ? DateUtils.formatDateToString(articlePresse.getDateCreationFichier()) : "",
                    articlePresse.getType(),
                    articlePresse.getSupport(),
                    articlePresse.getFormat(),
                    articlePresse.getIdentifiantUnique(),
                    articlePresse.getExtension(),
                    articlePresse.getLienInternet(),
                    articlePresse.getDateConsultation() != null ? DateUtils.formatDateToString(articlePresse.getDateConsultation()) : "",
                    articlePresse.getRelation(),
                    articlePresse.getRelationLien(),
                    articlePresse.getDateCreationPDF() != null ? DateUtils.formatDateToString(articlePresse.getDateCreationPDF()) : "",
                    articlePresse.getNotesInternes(),
                    articlePresse.getPreparation(),
                    articlePresse.getCollecteur(),
                    articlePresse.getCitationBibliographie(),
                    articlePresse.getGestionDesDroits()});
        }
        return data;
    }


    private List<String[]> createDeblinCoreRepport(List<Resource> resources) {
        List<String[]> data = new ArrayList<>();
        data.add(new String[] { "Titre", "Createur", "Mots clés", "Description", "Éditeur",
                "Contributeur", "Date de mise à disposition", "Type", "Format", "Identifiant unique",
                "Source", "Langue", "Relation", "Couverture", "Gestion des droits" });
        for (Resource rowData : resources) {
            DeblinCore deblinCore = (DeblinCore) rowData;

            StringBuffer motsCle = new StringBuffer();
            if (!CollectionUtils.isEmpty(deblinCore.getMotsCles())) {
                for (String s : deblinCore.getMotsCles()) {
                    motsCle.append(s).append(motCleSeparateur);
                }
            }

            data.add(new String[] { deblinCore.getTitre(),
                    deblinCore.getCreateur(),
                    motsCle.toString(),
                    deblinCore.getDescription(),
                    deblinCore.getEditeur(),
                    deblinCore.getContributeur(),
                    deblinCore.getDateMiseDisposition() != null ? DateUtils.formatDateToString(deblinCore.getDateMiseDisposition()) : "",
                    deblinCore.getType(),
                    deblinCore.getFormat(),
                    deblinCore.getIdentifiantUnique(),
                    deblinCore.getSource(),
                    deblinCore.getLangue(),
                    deblinCore.getRelation(),
                    deblinCore.getCouverture(),
                    deblinCore.getGestionDesDroits()});
        }
        return data;
    }

}
