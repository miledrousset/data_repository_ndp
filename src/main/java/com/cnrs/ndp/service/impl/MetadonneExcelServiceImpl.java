package com.cnrs.ndp.service.impl;

import com.cnrs.ndp.model.Label;
import com.cnrs.ndp.model.resources.*;
import com.cnrs.ndp.service.MetadonneExcelService;
import com.cnrs.ndp.utils.DateUtils;
import com.cnrs.ndp.utils.StringUtils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class MetadonneExcelServiceImpl implements MetadonneExcelService {

    private String motCleSeparateurMot = ";";

    @Value("${format.mot_cle.separateur_url}")
    private String motCleSeparateurUrl;


    public List<Resource> readDeblinCoreMetadonne(File file, String schemasSelected) throws IOException, IndexOutOfBoundsException {

        FileInputStream fis = new FileInputStream(file);
        XSSFWorkbook myWorkBook = new XSSFWorkbook (fis);
        XSSFSheet mySheet = myWorkBook.getSheetAt(0);
        Iterator<Row> rowIterator = mySheet.iterator();
        boolean excludFirstLigne = true;

        List<Resource> resources = new ArrayList<>();

        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();

            if (excludFirstLigne) {
                excludFirstLigne = false;
                rowIterator.next();
                continue;
            }

            switch (Integer.parseInt(schemasSelected)) {
                case 1:
                    if (row.getRowNum() != 15) {
                        throw new IndexOutOfBoundsException();
                    }
                    DeblinCore deblinCore = readDeblinCore(row);
                    if (!ObjectUtils.isEmpty(deblinCore)) {
                        resources.add(deblinCore);
                    }
                    break;
                case 2:
                    if (row.getRowNum() != 24) {
                        throw new IndexOutOfBoundsException();
                    }
                    ArticlePresse articlePresse = readArticlePresse(row);
                    if (!ObjectUtils.isEmpty(articlePresse)) {
                        resources.add(articlePresse);
                    }
                    break;
                case 3:
                    if (row.getRowNum() != 10) {
                        throw new IndexOutOfBoundsException();
                    }
                    Url url = readUrl(row);
                    if (!ObjectUtils.isEmpty(url)) {
                        resources.add(url);
                    }
                    break;
                case 4:
                    if (row.getRowNum() != 24) {
                        throw new IndexOutOfBoundsException();
                    }
                    Video video = readVideo(row);
                    if (!ObjectUtils.isEmpty(video)) {
                        resources.add(video);
                    }
                    break;
                case 5:
                    if (row.getRowNum() != 26) {
                        throw new IndexOutOfBoundsException();
                    }
                    Image image = readImage(row);
                    if (!ObjectUtils.isEmpty(image)) {
                        resources.add(image);
                    }
                    break;
                case 6:
                    if (row.getRowNum() != 27) {
                        throw new IndexOutOfBoundsException();
                    }
                    AudioWaweBwf audioWaweBwf = readAudioWaweBwf(row);
                    if (!ObjectUtils.isEmpty(audioWaweBwf)) {
                        resources.add(audioWaweBwf);
                    }
                    break;
                case 7:
                    if (row.getRowNum() != 23) {
                        throw new IndexOutOfBoundsException();
                    }
                    DonneeLaserBrut donneeLaserBrut = readDonneeLaserBrut(row);
                    if (!ObjectUtils.isEmpty(donneeLaserBrut)) {
                        resources.add(donneeLaserBrut);
                    }
                    break;
                case 8:
                    if (row.getRowNum() != 19) {
                        throw new IndexOutOfBoundsException();
                    }
                    DonneeLaserConso donneeLaserConso = readDonneeLaserConso(row);
                    if (!ObjectUtils.isEmpty(donneeLaserConso)) {
                        resources.add(donneeLaserConso);
                    }
                    break;
                case 9:
                    if (row.getRowNum() != 20) {
                        throw new IndexOutOfBoundsException();
                    }
                    NuagePointsPhotogrammetrie nuagePointsPhotogrammetrie = readNuagePointsPhotogrammetrie(row);
                    if (!ObjectUtils.isEmpty(nuagePointsPhotogrammetrie)) {
                        resources.add(nuagePointsPhotogrammetrie);
                    }
                    break;
                case 10:
                    if (row.getRowNum() != 7) {
                        throw new IndexOutOfBoundsException();
                    }
                    Maillage3dPhotogrammetrie maillage3dPhotogrammetrie = readMaillage3dPhotogrammetrie(row);
                    if (!ObjectUtils.isEmpty(maillage3dPhotogrammetrie)) {
                        resources.add(maillage3dPhotogrammetrie);
                    }
                    break;
                case 11:
                    if (row.getRowNum() != 14) {
                        throw new IndexOutOfBoundsException();
                    }
                    Maillage3dGeometry maillage3dGeometry = readMaillage3dGeometry(row);
                    if (!ObjectUtils.isEmpty(maillage3dGeometry)) {
                        resources.add(maillage3dGeometry);
                    }
                    break;
            }

        }

        return resources;
    }

    private ArticlePresse readArticlePresse(Row row) {
        try {
            ArticlePresse articlePresse = new ArticlePresse();
            articlePresse.setTitre(StringUtils.formatFileName(readStringValue(row, 0)));
            articlePresse.setCreateur(readStringValue(row, 1));

            List<Label> labelList = motsCleList(readStringValue(row, 2));
            if (!CollectionUtils.isEmpty(labelList)) {
                articlePresse.setMotsCles(labelList.stream().map(label -> label.getLabel()).collect(Collectors.toList()));
                articlePresse.setMotsClesLabel(labelList);
            }

            articlePresse.setDescription(readStringValue(row, 3));
            articlePresse.setMedia(readStringValue(row, 4));
            articlePresse.setEditeur(readStringValue(row, 5));
            articlePresse.setContributeur(readStringValue(row, 6));
            articlePresse.setLangue(readStringValue(row, 7));
            articlePresse.setDateCreationFichier(DateUtils.formatStringToDate(readStringValue(row, 8)));
            articlePresse.setType(readStringValue(row, 9));
            articlePresse.setSupport(readStringValue(row, 10));
            articlePresse.setFormat(readStringValue(row, 11));
            articlePresse.setIdentifiantUnique(readStringValue(row, 12));
            articlePresse.setExtension(readStringValue(row, 13));
            articlePresse.setLienInternet(readStringValue(row, 14));
            articlePresse.setDateConsultation(DateUtils.formatStringToDate(readStringValue(row, 15)));
            articlePresse.setRelation(readStringValue(row, 16));
            articlePresse.setRelationLien(readStringValue(row, 17));
            articlePresse.setDateCreationPDF(DateUtils.formatStringToDate(readStringValue(row, 18)));
            articlePresse.setNotesInternes(readStringValue(row, 19));
            articlePresse.setPreparation(readStringValue(row, 20));
            articlePresse.setCollecteur(readStringValue(row, 21));
            articlePresse.setCitationBibliographie(readStringValue(row, 22));
            articlePresse.setGestionDesDroits(readStringValue(row, 23));

            return articlePresse;
        } catch (Exception ex) {
            return null;
        }
    }

    private Url readUrl(Row row) {
        try {
            Url url = new Url();
            url.setTitre(StringUtils.formatFileName(row.getCell(0).getStringCellValue()));
            url.setDateCreationFichier(DateUtils.formatStringToDate(readStringValue(row, 1)));
            url.setLienInternet(readStringValue(row, 2));
            url.setCreateur(readStringValue(row, 3));
            url.setType(readStringValue(row, 4));
            url.setEditeur(readStringValue(row, 5));
            url.setRelation(readStringValue(row, 6));
            url.setNotesInternes(readStringValue(row, 7));
            url.setPreparation(readStringValue(row, 8));
            url.setIdentifiantUnique(readStringValue(row, 9));

            return url;
        } catch (Exception ex) {
            return null;
        }
    }

    private Video readVideo(Row row) {
        try {
            Video video = new Video();
            video.setTitre(StringUtils.formatFileName(readStringValue(row, 0)));
            video.setCreateur(readStringValue(row, 1));

            List<Label> labelList = motsCleList(readStringValue(row, 2));
            if (!CollectionUtils.isEmpty(labelList)) {
                video.setMotsCles(labelList.stream().map(label -> label.getLabel()).collect(Collectors.toList()));
                video.setMotsClesLabel(labelList);
            }

            video.setDescription(readStringValue(row, 3));
            video.setMedia(readStringValue(row, 4));
            video.setEditeur(readStringValue(row, 5));
            video.setContributeur(readStringValue(row, 6));
            video.setLangue(readStringValue(row, 7));
            video.setDateCreationFichier(DateUtils.formatStringToDate(readStringValue(row, 8)));
            video.setType(readStringValue(row, 9));
            video.setSupport(readStringValue(row, 10));
            video.setFormat(readStringValue(row, 11));
            video.setIdentifiantUnique(readStringValue(row, 12));
            video.setExtension(readStringValue(row, 13));
            video.setLienInternet(readStringValue(row, 14));
            video.setDateConsultation(DateUtils.formatStringToDate(readStringValue(row, 15)));
            video.setDateCreationMp4(DateUtils.formatStringToDate(readStringValue(row, 16)));
            video.setRelation(readStringValue(row, 17));
            video.setLienRelation(readStringValue(row, 18));
            video.setNotesInternes(readStringValue(row, 19));
            video.setPreparation(readStringValue(row, 20));
            video.setCollecteur(readStringValue(row, 21));
            video.setCitationBibliographie(readStringValue(row, 22));
            video.setGestionDesDroits(readStringValue(row, 23));

            return video;
        } catch (Exception ex) {
            return null;
        }
    }

    private Image readImage(Row row) {
        try {
            Image image = new Image();
            image.setTitre(StringUtils.formatFileName(row.getCell(0).getStringCellValue()));
            image.setCreateur(readStringValue(row, 1));

            List<Label> labelList = motsCleList(readStringValue(row, 2));
            if (!CollectionUtils.isEmpty(labelList)) {
                image.setMotsCles(labelList.stream().map(label -> label.getLabel()).collect(Collectors.toList()));
                image.setMotsClesLabel(labelList);
            }

            image.setEditeur(readStringValue(row, 3));
            image.setContributeur(readStringValue(row, 4));
            image.setDateCreation(DateUtils.formatStringToDate(readStringValue(row, 5)));
            image.setType(readStringValue(row, 6));
            image.setSupportOriginalFichier(readStringValue(row, 7));
            image.setExtension(readStringValue(row, 8));
            image.setDateMiseDisposition(DateUtils.formatStringToDate(readStringValue(row, 9)));
            image.setCitationBibliographie(readStringValue(row, 10));
            image.setGestionDesDroits(readStringValue(row, 11));
            image.setIdentifiantUnique(readStringValue(row, 12));
            image.setFileSize(readStringValue(row, 13));
            image.setModel(readStringValue(row, 14));
            image.setImageSize(readStringValue(row, 15));
            image.setQuality(readStringValue(row, 16));
            image.setFocalLength(readStringValue(row, 17));
            image.setShutterSpeed(readStringValue(row, 18));
            image.setAperture(readStringValue(row, 19));
            image.setIso(readStringValue(row, 20));
            image.setWhiteBalance(readStringValue(row, 21));
            image.setFlash(readStringValue(row, 22));
            image.setXResolution(readStringValue(row, 23));
            image.setYResolution(readStringValue(row, 24));
            image.setPreservedFileName(readStringValue(row, 25));

            return image;
        } catch (Exception ex) {
            return null;
        }
    }

    private AudioWaweBwf readAudioWaweBwf(Row row) {
        try {
            AudioWaweBwf audioWaweBwf = new AudioWaweBwf();
            audioWaweBwf.setTitre(StringUtils.formatFileName(row.getCell(0).getStringCellValue()));
            audioWaweBwf.setCreateur(readStringValue(row, 1));

            List<Label> labelList = motsCleList(readStringValue(row, 2));
            if (!CollectionUtils.isEmpty(labelList)) {
                audioWaweBwf.setMotsCles(labelList.stream().map(label -> label.getLabel()).collect(Collectors.toList()));
                audioWaweBwf.setMotsClesLabel(labelList);
            }

            audioWaweBwf.setEditeur(readStringValue(row, 3));
            audioWaweBwf.setContributeur(readStringValue(row, 4));
            audioWaweBwf.setFormat(readStringValue(row, 5));
            audioWaweBwf.setIdentifiantUnique(readStringValue(row, 6));
            audioWaweBwf.setDescription(readStringValue(row, 7));
            audioWaweBwf.setLangue(readStringValue(row, 8));
            audioWaweBwf.setRelation(readStringValue(row, 9));
            audioWaweBwf.setSource(readStringValue(row, 10));
            audioWaweBwf.setGestionDesDroits(readStringValue(row, 11));
            audioWaweBwf.setCouverture(readStringValue(row, 12));
            audioWaweBwf.setOriginationDate(DateUtils.formatStringToDate(readStringValue(row, 13)));
            audioWaweBwf.setIgnr(readStringValue(row, 14));
            audioWaweBwf.setOriginatorReference(readStringValue(row, 15));
            audioWaweBwf.setOriginationDate(DateUtils.formatStringToDate(readStringValue(row, 16)));
            audioWaweBwf.setOriginationTime(readStringValue(row, 17));
            audioWaweBwf.setTimeReferenceTranslated(readStringValue(row, 18));
            audioWaweBwf.setTimeReference(readStringValue(row, 19));
            audioWaweBwf.setBextVersion(readStringValue(row, 20));
            audioWaweBwf.setCodingHistory(readStringValue(row, 21));
            audioWaweBwf.setIarl(readStringValue(row, 22));
            audioWaweBwf.setIcmt(readStringValue(row, 23));
            audioWaweBwf.setIeng(readStringValue(row, 24));
            audioWaweBwf.setImed(readStringValue(row, 25));
            audioWaweBwf.setIsft(readStringValue(row, 26));

            return audioWaweBwf;
        } catch (Exception ex) {
            return null;
        }
    }

    private DonneeLaserBrut readDonneeLaserBrut(Row row) {
        try {
            DonneeLaserBrut donneeLaserBrut = new DonneeLaserBrut();
            donneeLaserBrut.setTitre(StringUtils.formatFileName(row.getCell(0).getStringCellValue()));
            donneeLaserBrut.setCreateur(readStringValue(row, 1));

            List<Label> labelList = motsCleList(readStringValue(row, 2));
            if (!CollectionUtils.isEmpty(labelList)) {
                donneeLaserBrut.setMotsCles(labelList.stream().map(label -> label.getLabel()).collect(Collectors.toList()));
                donneeLaserBrut.setMotsClesLabel(labelList);
            }

            donneeLaserBrut.setDescription(readStringValue(row, 3));
            donneeLaserBrut.setEditeur(readStringValue(row, 4));
            donneeLaserBrut.setContributeur(readStringValue(row, 5));
            donneeLaserBrut.setDateMiseDisposition(DateUtils.formatStringToDate(readStringValue(row, 6)));
            donneeLaserBrut.setType(readStringValue(row, 7));
            donneeLaserBrut.setFormat(readStringValue(row, 8));
            donneeLaserBrut.setIdentifiantUnique(readStringValue(row, 9));
            donneeLaserBrut.setSource(readStringValue(row, 10));
            donneeLaserBrut.setNuage(readStringValue(row, 11));
            donneeLaserBrut.setLangue(readStringValue(row, 12));
            donneeLaserBrut.setRelation(readStringValue(row, 13));
            donneeLaserBrut.setCouverture(readStringValue(row, 14));
            donneeLaserBrut.setGestionDesDroits(readStringValue(row, 15));
            donneeLaserBrut.setMateriel(readStringValue(row, 16));
            donneeLaserBrut.setMethodeMetrologique(readStringValue(row, 17));
            donneeLaserBrut.setResolutionAngulaire(readStringValue(row, 18));
            donneeLaserBrut.setResolutionSpatiale(readStringValue(row, 19));
            donneeLaserBrut.setDensitePointMoyenne(readStringValue(row, 20));
            donneeLaserBrut.setChampHorizontalStation(readStringValue(row, 21));
            donneeLaserBrut.setChampVerticalStation(readStringValue(row, 22));

            return donneeLaserBrut;
        } catch (Exception ex) {
            return null;
        }
    }

    private DonneeLaserConso readDonneeLaserConso(Row row) {
        try {
            DonneeLaserConso donneeLaserConso = new DonneeLaserConso();
            donneeLaserConso.setTitre(StringUtils.formatFileName(row.getCell(0).getStringCellValue()));
            donneeLaserConso.setCreateur(readStringValue(row, 1));

            List<Label> labelList = motsCleList(readStringValue(row, 2));
            if (!CollectionUtils.isEmpty(labelList)) {
                donneeLaserConso.setMotsCles(labelList.stream().map(label -> label.getLabel()).collect(Collectors.toList()));
                donneeLaserConso.setMotsClesLabel(labelList);
            }

            donneeLaserConso.setDescription(readStringValue(row, 3));
            donneeLaserConso.setEditeur(readStringValue(row, 4));
            donneeLaserConso.setContributeur(readStringValue(row, 5));
            donneeLaserConso.setDateMiseDisposition(DateUtils.formatStringToDate(readStringValue(row, 6)));
            donneeLaserConso.setType(readStringValue(row, 7));
            donneeLaserConso.setFormat(readStringValue(row, 8));
            donneeLaserConso.setIdentifiantUnique(readStringValue(row, 9));
            donneeLaserConso.setSource(readStringValue(row, 10));
            donneeLaserConso.setLangue(readStringValue(row, 11));
            donneeLaserConso.setRelation(readStringValue(row, 12));
            donneeLaserConso.setCouverture(readStringValue(row, 13));
            donneeLaserConso.setGestionDesDroits(readStringValue(row, 14));
            donneeLaserConso.setIdSources(readStringValue(row, 15));
            donneeLaserConso.setLogiciel(readStringValue(row, 16));
            donneeLaserConso.setMethodeConsolidation(readStringValue(row, 17));
            donneeLaserConso.setSystemeCoordonnees(readStringValue(row, 18));

            return donneeLaserConso;
        } catch (Exception e) {
            return null;
        }
    }

    private NuagePointsPhotogrammetrie readNuagePointsPhotogrammetrie(Row row) {
        try {
            NuagePointsPhotogrammetrie nuagePointsPhotogrammetrie = new NuagePointsPhotogrammetrie();
            nuagePointsPhotogrammetrie.setTitre(StringUtils.formatFileName(row.getCell(0).getStringCellValue()));
            nuagePointsPhotogrammetrie.setCreateur(readStringValue(row, 1));

            List<Label> labelList = motsCleList(readStringValue(row, 2));
            if (!CollectionUtils.isEmpty(labelList)) {
                nuagePointsPhotogrammetrie.setMotsCles(labelList.stream().map(label -> label.getLabel()).collect(Collectors.toList()));
                nuagePointsPhotogrammetrie.setMotsClesLabel(labelList);
            }

            nuagePointsPhotogrammetrie.setDescription(readStringValue(row, 3));
            nuagePointsPhotogrammetrie.setEditeur(readStringValue(row, 4));
            nuagePointsPhotogrammetrie.setContributeur(readStringValue(row, 5));
            nuagePointsPhotogrammetrie.setDateMiseDisposition(DateUtils.formatStringToDate(readStringValue(row, 6)));
            nuagePointsPhotogrammetrie.setType(readStringValue(row, 7));
            nuagePointsPhotogrammetrie.setFormat(readStringValue(row, 8));
            nuagePointsPhotogrammetrie.setIdentifiantUnique(readStringValue(row, 9));
            nuagePointsPhotogrammetrie.setSource(readStringValue(row, 10));
            nuagePointsPhotogrammetrie.setLangue(readStringValue(row, 11));
            nuagePointsPhotogrammetrie.setRelation(readStringValue(row, 12));
            nuagePointsPhotogrammetrie.setCouverture(readStringValue(row, 13));
            nuagePointsPhotogrammetrie.setGestionDesDroits(readStringValue(row, 14));
            nuagePointsPhotogrammetrie.setIdSources(readStringValue(row, 15));
            nuagePointsPhotogrammetrie.setLogicielTraitement(readStringValue(row, 16));
            nuagePointsPhotogrammetrie.setDensitePointsMoyenne(readStringValue(row, 17));
            nuagePointsPhotogrammetrie.setSystemeCoordonnees(readStringValue(row, 18));
            nuagePointsPhotogrammetrie.setArchitectureFichier(readStringValue(row, 19));

            return nuagePointsPhotogrammetrie;
        } catch (Exception ex) {
            return null;
        }
    }

    private Maillage3dGeometry readMaillage3dGeometry(Row row) {
        try {
            Maillage3dGeometry maillage3dGeometry = new Maillage3dGeometry();
            maillage3dGeometry.setTitre(StringUtils.formatFileName(readStringValue(row, 0)));
            maillage3dGeometry.setAxeOrientation(readStringValue(row, 1));
            maillage3dGeometry.setAxeVertical(readStringValue(row, 2));
            maillage3dGeometry.setUniteMesure(readStringValue(row, 3));
            maillage3dGeometry.setLogicielTraitement(readStringValue(row, 4));
            maillage3dGeometry.setDimensionX(readStringValue(row, 5));
            maillage3dGeometry.setDimensionY(readStringValue(row, 6));
            maillage3dGeometry.setDimensionZ(readStringValue(row, 7));
            maillage3dGeometry.setCheminFichier(readStringValue(row, 8));
            maillage3dGeometry.setCreateur(readStringValue(row, 9));
            maillage3dGeometry.setDateFichier(readDateValue(row, 10));
            maillage3dGeometry.setFormatFichier(readStringValue(row, 11));
            maillage3dGeometry.setDescription(readStringValue(row, 12));
            maillage3dGeometry.setEncodage(readStringValue(row, 13));
            return maillage3dGeometry;
        } catch (Exception ex) {
            return null;
        }
    }

    private Maillage3dPhotogrammetrie readMaillage3dPhotogrammetrie(Row row) {
        try {
            Maillage3dPhotogrammetrie maillage3dPhotogrammetrie = new Maillage3dPhotogrammetrie();
            maillage3dPhotogrammetrie.setTitre(StringUtils.formatFileName(readStringValue(row, 0)));
            maillage3dPhotogrammetrie.setMethodeAssemblageNuagesPoints(readStringValue(row, 1));
            maillage3dPhotogrammetrie.setPourcentageDecimation(readStringValue(row, 2));
            maillage3dPhotogrammetrie.setAlgorithmeUtiliseMaillage(readStringValue(row, 3));
            maillage3dPhotogrammetrie.setMethodeTexturage(readStringValue(row, 4));
            maillage3dPhotogrammetrie.setProjectionTexture(readStringValue(row, 5));
            maillage3dPhotogrammetrie.setSourcesFichiersTexture(readStringValue(row, 6));
            return maillage3dPhotogrammetrie;
        } catch (Exception e) {
            return null;
        }
    }

    private DeblinCore readDeblinCore(Row row) {
        try {
            DeblinCore deblinCore = new DeblinCore();
            deblinCore.setTitre(StringUtils.formatFileName(readStringValue(row, 0)));
            deblinCore.setCreateur(readStringValue(row, 1));

            List<Label> labelList = motsCleList(readStringValue(row, 2));
            if (!CollectionUtils.isEmpty(labelList)) {
                deblinCore.setMotsCles(labelList.stream().map(label -> label.getLabel()).collect(Collectors.toList()));
                deblinCore.setMotsClesLabel(labelList);
            }

            deblinCore.setDescription(readStringValue(row, 3));
            deblinCore.setEditeur(readStringValue(row, 4));
            deblinCore.setContributeur(readStringValue(row, 5));
            deblinCore.setDateMiseDisposition(readDateValue(row, 6));
            deblinCore.setType(readStringValue(row, 7));
            deblinCore.setFormat(readStringValue(row, 8));
            deblinCore.setIdentifiantUnique(readStringValue(row, 9));
            deblinCore.setSource(readStringValue(row, 10));
            deblinCore.setLangue(readStringValue(row, 11));
            deblinCore.setRelation(readStringValue(row, 12));
            deblinCore.setCouverture(readStringValue(row, 13));
            deblinCore.setGestionDesDroits(readStringValue(row, 14));
            return deblinCore;
        } catch (IndexOutOfBoundsException ex) {
            throw new IndexOutOfBoundsException();
        }
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

    private String readStringValue(Row row, int index) {
        return row.getCell(index) == null ? "" : row.getCell(index).getStringCellValue();
    }

    private Date readDateValue(Row row, int index) {
        try {
            return row.getCell(index) == null ? null : row.getCell(index).getDateCellValue();
        } catch (IllegalStateException ex) {
            return row.getCell(index) == null ? null : DateUtils.formatStringToDate(row.getCell(index).getStringCellValue());
        }
    }
}
