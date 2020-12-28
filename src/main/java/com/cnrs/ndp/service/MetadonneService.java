package com.cnrs.ndp.service;

import com.cnrs.ndp.model.resources.*;
import com.cnrs.ndp.utils.DateUtils;
import com.cnrs.ndp.utils.StringUtils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Service
public class MetadonneService {



    public List<Resource> readDeblinCoreMetadonne(File file, String schemasSelected) throws IOException {

        FileInputStream fis = new FileInputStream(file);
        XSSFWorkbook myWorkBook = new XSSFWorkbook (fis);
        XSSFSheet mySheet = myWorkBook.getSheetAt(0);
        Iterator<Row> rowIterator = mySheet.iterator();
        boolean excludFirstLigne = true;

        List<Resource> resources = new ArrayList<>();

        while (rowIterator.hasNext()) {
            if (excludFirstLigne) {
                excludFirstLigne = false;
                rowIterator.next();
                continue;
            }

            Row row = rowIterator.next();
            switch (Integer.parseInt(schemasSelected)) {
                case 1:
                    resources.add(readDeblinCore(row));
                    break;
                case 2:
                    resources.add(readArticlePresse(row));
                    break;
                case 3:
                    resources.add(readUrl(row));
                    break;
                case 4:
                    resources.add(readVideo(row));
                    break;
                case 5:
                    resources.add(readImage(row));
                    break;
                case 6:
                    resources.add(readAudioWaweBwf(row));
                    break;
                case 7:
                    resources.add(readDonneeLaserBrut(row));
                    break;
                case 8:
                    resources.add(readDonneeLaserConso(row));
                    break;
                case 9:
                    resources.add(readNuagePointsPhotogrammetrie(row));
                    break;
                case 10:
                    resources.add(readMaillage3dPhotogrammetrie(row));
                    break;
                case 11:
                    resources.add(readMaillage3dGeometry(row));
                    break;
            }

        }

        return resources;
    }

    private ArticlePresse readArticlePresse(Row row) {
        ArticlePresse articlePresse = new ArticlePresse();
        articlePresse.setTitre(StringUtils.formatFileName(row.getCell(1).getStringCellValue()));
        articlePresse.setCreateur(row.getCell(2).getStringCellValue());
        articlePresse.setMotsCles(row.getCell(3).getStringCellValue());
        articlePresse.setDescription(row.getCell(4).getStringCellValue());
        articlePresse.setMedia(row.getCell(5).getStringCellValue());
        articlePresse.setEditeur(row.getCell(6).getStringCellValue());
        articlePresse.setContributeur(row.getCell(7).getStringCellValue());
        articlePresse.setLangue(row.getCell(8).getStringCellValue());
        articlePresse.setDateCreationFichier(row.getCell(9).getStringCellValue());
        articlePresse.setType(row.getCell(10).getStringCellValue());
        articlePresse.setSupport(row.getCell(11).getStringCellValue());
        articlePresse.setFormat(row.getCell(12).getStringCellValue());
        articlePresse.setIdentifiantUnique(row.getCell(13).getStringCellValue());
        articlePresse.setExtension(row.getCell(14).getStringCellValue());
        articlePresse.setLienInternet(row.getCell(15).getStringCellValue());
        articlePresse.setDateConsultation(row.getCell(16).getStringCellValue());
        articlePresse.setRelation(row.getCell(17).getStringCellValue());
        articlePresse.setRelationLien(row.getCell(18).getStringCellValue());
        articlePresse.setDateCreationPDF(row.getCell(19).getStringCellValue());
        articlePresse.setNotesInternes(row.getCell(20).getStringCellValue());
        articlePresse.setPreparation(row.getCell(21).getStringCellValue());
        articlePresse.setCollecteur(row.getCell(22).getStringCellValue());
        articlePresse.setCitationBibliographie(row.getCell(23).getStringCellValue());
        articlePresse.setGestionDesDroits(row.getCell(24).getStringCellValue());

        return articlePresse;
    }

    private Url readUrl(Row row) {
        Url url = new Url();
        url.setTitre(StringUtils.formatFileName(row.getCell(0).getStringCellValue()));
        url.setDateCreationFichier(row.getCell(1).getStringCellValue());
        url.setLienInternet(row.getCell(2).getStringCellValue());
        url.setCreateur(row.getCell(3).getStringCellValue());
        url.setType(row.getCell(4).getStringCellValue());
        url.setEditeur(row.getCell(5).getStringCellValue());
        url.setRelation(row.getCell(6).getStringCellValue());
        url.setNotesInternes(row.getCell(7).getStringCellValue());
        url.setPreparation(row.getCell(8).getStringCellValue());
        url.setIdentifiantUnique(row.getCell(9).getStringCellValue());
        return url;
    }

    private Video readVideo(Row row) {
        Video video = new Video();
        video.setTitre(StringUtils.formatFileName(row.getCell(1).getStringCellValue()));
        video.setCreateur(row.getCell(2).getStringCellValue());
        video.setMotsCles(row.getCell(3).getStringCellValue());
        video.setDescription(row.getCell(4).getStringCellValue());
        video.setMedia(row.getCell(5).getStringCellValue());
        video.setEditeur(row.getCell(6).getStringCellValue());
        video.setContributeur(row.getCell(7).getStringCellValue());
        video.setLangue(row.getCell(8).getStringCellValue());
        video.setDateCreationFichier(row.getCell(9).getStringCellValue());
        video.setType(row.getCell(10).getStringCellValue());
        video.setSupport(row.getCell(11).getStringCellValue());
        video.setFormat(row.getCell(12).getStringCellValue());
        video.setIdentifiantUnique(row.getCell(13).getStringCellValue());
        video.setExtension(row.getCell(14).getStringCellValue());
        video.setLienInternet(row.getCell(15).getStringCellValue());
        video.setDateConsultation(row.getCell(16).getStringCellValue());
        video.setDateCreationMp4(row.getCell(17).getStringCellValue());
        video.setRelation(row.getCell(18).getStringCellValue());
        video.setNotesInternes(row.getCell(19).getStringCellValue());
        video.setPreparation(row.getCell(20).getStringCellValue());
        video.setCollecteur(row.getCell(21).getStringCellValue());
        video.setCitationBibliographie(row.getCell(22).getStringCellValue());
        video.setGestionDesDroits(row.getCell(23).getStringCellValue());


        return video;
    }

    private Image readImage(Row row) {
        Image image = new Image();
        image.setTitre(StringUtils.formatFileName(row.getCell(0).getStringCellValue()));
        image.setCreateur(row.getCell(1).getStringCellValue());
        image.setMotsCles(row.getCell(2).getStringCellValue());
        image.setEditeur(row.getCell(3).getStringCellValue());
        image.setContributeur(row.getCell(4).getStringCellValue());
        image.setDateCreation(row.getCell(5).getStringCellValue());
        image.setType(row.getCell(6).getStringCellValue());
        image.setSupportOriginalFichier(row.getCell(7).getStringCellValue());
        image.setExtension(row.getCell(8).getStringCellValue());
        image.setDateMiseDisposition(row.getCell(9).getStringCellValue());
        image.setCitationBibliographie(row.getCell(10).getStringCellValue());
        image.setGestionDesDroits(row.getCell(11).getStringCellValue());
        image.setIdentifiantUnique(row.getCell(12).getStringCellValue());
        image.setFileSize(row.getCell(13).getStringCellValue());
        image.setModel(row.getCell(14).getStringCellValue());
        image.setImageSize(row.getCell(15).getStringCellValue());
        image.setQuality(row.getCell(16).getStringCellValue());
        image.setFocalLength(row.getCell(17).getStringCellValue());
        image.setShutterSpeed(row.getCell(18).getStringCellValue());
        image.setAperture(row.getCell(19).getStringCellValue());
        image.setIso(row.getCell(20).getStringCellValue());
        image.setWhiteBalance(row.getCell(21).getStringCellValue());
        image.setFlash(row.getCell(22).getStringCellValue());
        image.setXResolution(row.getCell(23).getStringCellValue());
        image.setYResolution(row.getCell(24).getStringCellValue());
        image.setPreservedFileName(row.getCell(25).getStringCellValue());

        return image;
    }

    private AudioWaweBwf readAudioWaweBwf(Row row) {
        AudioWaweBwf audioWaweBwf = new AudioWaweBwf();
        audioWaweBwf.setTitre(StringUtils.formatFileName(row.getCell(0).getStringCellValue()));
        audioWaweBwf.setCreateur(row.getCell(1).getStringCellValue());
        audioWaweBwf.setMotsCles(row.getCell(2).getStringCellValue());
        audioWaweBwf.setEditeur(row.getCell(3).getStringCellValue());
        audioWaweBwf.setContributeur(row.getCell(4).getStringCellValue());
        audioWaweBwf.setFormat(row.getCell(5).getStringCellValue());
        audioWaweBwf.setIdentifiantUnique(row.getCell(6).getStringCellValue());
        audioWaweBwf.setDescription(row.getCell(7).getStringCellValue());
        audioWaweBwf.setLangue(row.getCell(8).getStringCellValue());
        audioWaweBwf.setRelation(row.getCell(9).getStringCellValue());
        audioWaweBwf.setSource(row.getCell(10).getStringCellValue());
        audioWaweBwf.setGestionDesDroits(row.getCell(11).getStringCellValue());
        audioWaweBwf.setCouverture(row.getCell(12).getStringCellValue());
        audioWaweBwf.setIcrd(row.getCell(13).getStringCellValue());
        audioWaweBwf.setIgnr(row.getCell(14).getStringCellValue());
        audioWaweBwf.setOriginatorReference(row.getCell(15).getStringCellValue());
        audioWaweBwf.setOriginationDate(row.getCell(16).getStringCellValue());
        audioWaweBwf.setOriginationTime(row.getCell(17).getStringCellValue());
        audioWaweBwf.setTimeReferenceTranslated(row.getCell(18).getStringCellValue());
        audioWaweBwf.setTimeReference(row.getCell(19).getStringCellValue());
        audioWaweBwf.setBextVersion(row.getCell(20).getStringCellValue());
        audioWaweBwf.setCodingHistory(row.getCell(21).getStringCellValue());
        audioWaweBwf.setIarl(row.getCell(22).getStringCellValue());
        audioWaweBwf.setIcmt(row.getCell(23).getStringCellValue());
        audioWaweBwf.setIeng(row.getCell(24).getStringCellValue());
        audioWaweBwf.setImed(row.getCell(25).getStringCellValue());
        audioWaweBwf.setIsft(row.getCell(26).getStringCellValue());

        return audioWaweBwf;
    }

    private DonneeLaserBrut readDonneeLaserBrut(Row row) {
        DonneeLaserBrut donneeLaserBrut = new DonneeLaserBrut();
        donneeLaserBrut.setTitre(StringUtils.formatFileName(row.getCell(0).getStringCellValue()));
        donneeLaserBrut.setCreateur(row.getCell(1).getStringCellValue());
        donneeLaserBrut.setMotsCles(row.getCell(2).getStringCellValue());
        donneeLaserBrut.setDescription(row.getCell(3).getStringCellValue());
        donneeLaserBrut.setEditeur(row.getCell(4).getStringCellValue());
        donneeLaserBrut.setContributeur(row.getCell(5).getStringCellValue());
        donneeLaserBrut.setDateMiseDisposition(row.getCell(6).getStringCellValue());
        donneeLaserBrut.setType(row.getCell(7).getStringCellValue());
        donneeLaserBrut.setFormat(row.getCell(8).getStringCellValue());
        donneeLaserBrut.setIdentifiantUnique(row.getCell(9).getStringCellValue());
        donneeLaserBrut.setSource(row.getCell(10).getStringCellValue());
        donneeLaserBrut.setNuage(row.getCell(11).getStringCellValue());
        donneeLaserBrut.setLangue(row.getCell(12).getStringCellValue());
        donneeLaserBrut.setRelation(row.getCell(13).getStringCellValue());
        donneeLaserBrut.setCouverture(row.getCell(14).getStringCellValue());
        donneeLaserBrut.setGestionDesDroits(row.getCell(15).getStringCellValue());
        donneeLaserBrut.setMateriel(row.getCell(16).getStringCellValue());
        donneeLaserBrut.setMethodeMetrologique(row.getCell(17).getStringCellValue());
        donneeLaserBrut.setResolutionAngulaire(row.getCell(18).getStringCellValue());
        donneeLaserBrut.setResolutionSpatiale(row.getCell(19).getStringCellValue());
        donneeLaserBrut.setDensitePointMoyenne(row.getCell(20).getStringCellValue());
        donneeLaserBrut.setChampHorizontalStation(row.getCell(21).getStringCellValue());
        donneeLaserBrut.setChampVerticalStation(row.getCell(22).getStringCellValue());

        return donneeLaserBrut;
    }

    private DonneeLaserConso readDonneeLaserConso(Row row) {
        DonneeLaserConso donneeLaserConso = new DonneeLaserConso();
        donneeLaserConso.setTitre(StringUtils.formatFileName(row.getCell(0).getStringCellValue()));
        donneeLaserConso.setCreateur(row.getCell(1).getStringCellValue());
        donneeLaserConso.setMotsCles(row.getCell(2).getStringCellValue());
        donneeLaserConso.setDescription(row.getCell(3).getStringCellValue());
        donneeLaserConso.setEditeur(row.getCell(4).getStringCellValue());
        donneeLaserConso.setContributeur(row.getCell(5).getStringCellValue());
        donneeLaserConso.setDateMiseDisposition(row.getCell(6).getStringCellValue());
        donneeLaserConso.setType(row.getCell(7).getStringCellValue());
        donneeLaserConso.setFormat(row.getCell(8).getStringCellValue());
        donneeLaserConso.setIdentifiantUnique(row.getCell(9).getStringCellValue());
        donneeLaserConso.setSource(row.getCell(10).getStringCellValue());
        donneeLaserConso.setLangue(row.getCell(11).getStringCellValue());
        donneeLaserConso.setRelation(row.getCell(12).getStringCellValue());
        donneeLaserConso.setCouverture(row.getCell(13).getStringCellValue());
        donneeLaserConso.setGestionDesDroits(row.getCell(14).getStringCellValue());
        donneeLaserConso.setIdSources(row.getCell(15).getStringCellValue());
        donneeLaserConso.setLogiciel(row.getCell(16).getStringCellValue());
        donneeLaserConso.setMethodeConsolidation(row.getCell(17).getStringCellValue());
        donneeLaserConso.setSystemeCoordonnees(row.getCell(18).getStringCellValue());

        return donneeLaserConso;
    }

    private NuagePointsPhotogrammetrie readNuagePointsPhotogrammetrie(Row row) {
        NuagePointsPhotogrammetrie nuagePointsPhotogrammetrie = new NuagePointsPhotogrammetrie();
        nuagePointsPhotogrammetrie.setTitre(StringUtils.formatFileName(row.getCell(0).getStringCellValue()));
        nuagePointsPhotogrammetrie.setCreateur(row.getCell(1).getStringCellValue());
        nuagePointsPhotogrammetrie.setMotsCles(row.getCell(2).getStringCellValue());
        nuagePointsPhotogrammetrie.setDescription(row.getCell(3).getStringCellValue());
        nuagePointsPhotogrammetrie.setEditeur(row.getCell(4).getStringCellValue());
        nuagePointsPhotogrammetrie.setContributeur(row.getCell(5).getStringCellValue());
        nuagePointsPhotogrammetrie.setDateMiseDisposition(row.getCell(6).getStringCellValue());
        nuagePointsPhotogrammetrie.setType(row.getCell(7).getStringCellValue());
        nuagePointsPhotogrammetrie.setFormat(row.getCell(8).getStringCellValue());
        nuagePointsPhotogrammetrie.setIdentifiantUnique(row.getCell(9).getStringCellValue());
        nuagePointsPhotogrammetrie.setSource(row.getCell(10).getStringCellValue());
        nuagePointsPhotogrammetrie.setLangue(row.getCell(11).getStringCellValue());
        nuagePointsPhotogrammetrie.setRelation(row.getCell(12).getStringCellValue());
        nuagePointsPhotogrammetrie.setCouverture(row.getCell(13).getStringCellValue());
        nuagePointsPhotogrammetrie.setGestionDesDroits(row.getCell(14).getStringCellValue());
        nuagePointsPhotogrammetrie.setIdSources(row.getCell(15).getStringCellValue());
        nuagePointsPhotogrammetrie.setLogicielTraitement(row.getCell(16).getStringCellValue());
        nuagePointsPhotogrammetrie.setDensitePointsMoyenne(row.getCell(17).getStringCellValue());
        nuagePointsPhotogrammetrie.setSystemeCoordonnees(row.getCell(18).getStringCellValue());
        nuagePointsPhotogrammetrie.setArchitectureFichier(row.getCell(19).getStringCellValue());

        return nuagePointsPhotogrammetrie;
    }

    private Maillage3dGeometry readMaillage3dGeometry(Row row) {
        Maillage3dGeometry maillage3dGeometry = new Maillage3dGeometry();
        maillage3dGeometry.setTitre(StringUtils.formatFileName(row.getCell(0).getStringCellValue()));
        maillage3dGeometry.setAxeOrientation(row.getCell(1).getStringCellValue());
        maillage3dGeometry.setAxeVertical(row.getCell(2).getStringCellValue());
        maillage3dGeometry.setUniteMesure(row.getCell(3).getStringCellValue());
        maillage3dGeometry.setLogicielTraitement(row.getCell(4).getStringCellValue());
        maillage3dGeometry.setDimensionX(row.getCell(5).getStringCellValue());
        maillage3dGeometry.setDimensionY(row.getCell(6).getStringCellValue());
        maillage3dGeometry.setDimensionZ(row.getCell(7).getStringCellValue());
        maillage3dGeometry.setCheminFichier(row.getCell(8).getStringCellValue());
        maillage3dGeometry.setCreateur(row.getCell(9).getStringCellValue());
        maillage3dGeometry.setDateFichier(row.getCell(10).getStringCellValue());
        maillage3dGeometry.setFormatFichier(row.getCell(11).getStringCellValue());
        maillage3dGeometry.setDescription(row.getCell(12).getStringCellValue());
        maillage3dGeometry.setEncodage(row.getCell(13).getStringCellValue());
        return maillage3dGeometry;
    }

    private Maillage3dPhotogrammetrie readMaillage3dPhotogrammetrie(Row row) {
        Maillage3dPhotogrammetrie maillage3dPhotogrammetrie = new Maillage3dPhotogrammetrie();
        maillage3dPhotogrammetrie.setTitre(StringUtils.formatFileName(row.getCell(0).getStringCellValue()));
        maillage3dPhotogrammetrie.setMethodeAssemblageNuagesPoints(row.getCell(1).getStringCellValue());
        maillage3dPhotogrammetrie.setPourcentageDecimation(row.getCell(2).getStringCellValue());
        maillage3dPhotogrammetrie.setAlgorithmeUtiliseMaillage(row.getCell(3).getStringCellValue());
        maillage3dPhotogrammetrie.setMethodeTexturage(row.getCell(4).getStringCellValue());
        maillage3dPhotogrammetrie.setProjectionTexture(row.getCell(5).getStringCellValue());
        maillage3dPhotogrammetrie.setSourcesFichiersTexture(row.getCell(6).getStringCellValue());
        return maillage3dPhotogrammetrie;
    }

    private DeblinCore readDeblinCore(Row row) {
        DeblinCore deblinCore = new DeblinCore();
        deblinCore.setTitre(StringUtils.formatFileName(row.getCell(0).getStringCellValue()));
        deblinCore.setCreateur(row.getCell(1).getStringCellValue());
        deblinCore.setMotsCles(row.getCell(2).getStringCellValue());
        deblinCore.setDescription(row.getCell(3).getStringCellValue());
        deblinCore.setEditeur(row.getCell(4).getStringCellValue());
        deblinCore.setContributeur(row.getCell(5).getStringCellValue());
        deblinCore.setDateMiseDisposition(DateUtils.formatStringToDate(row.getCell(6).getStringCellValue()));
        deblinCore.setType(row.getCell(7).getStringCellValue());
        deblinCore.setFormat(row.getCell(8).getStringCellValue());
        deblinCore.setIdentifiantUnique(row.getCell(9).getStringCellValue());
        deblinCore.setSource(row.getCell(10).getStringCellValue());
        deblinCore.setLangue(row.getCell(11).getStringCellValue());
        deblinCore.setRelation(row.getCell(12).getStringCellValue());
        deblinCore.setCouverture(row.getCell(13).getStringCellValue());
        deblinCore.setGestionDesDroits(row.getCell(14).getStringCellValue());
        return deblinCore;
    }
}
