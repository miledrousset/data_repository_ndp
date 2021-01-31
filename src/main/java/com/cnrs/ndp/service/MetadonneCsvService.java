package com.cnrs.ndp.service;

import com.cnrs.ndp.model.Label;
import com.cnrs.ndp.model.resources.*;
import com.cnrs.ndp.utils.DateUtils;
import com.cnrs.ndp.utils.StringUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class MetadonneCsvService {

    private String motCleSeparateurMot = ";";

    @Value("${format.mot_cle.separateur_url}")
    private String motCleSeparateurUrl;

    private String CHAMPS_DELIMITER = ";";


    public List<Resource> readCsvMetadonne(File file, String schemasSelected) throws IOException {

        List<Resource> resources = new ArrayList<>();

        switch (Integer.parseInt(schemasSelected)) {
            case 1:
                resources.addAll(readDeblinCoreCsv(new FileReader(file)));
                break;
            case 2:
                resources.addAll(readArticlePresseCsv(new FileReader(file)));
                break;
            case 3:
                resources.addAll(readUrlCsv(new FileReader(file)));
                break;
            case 4:
                resources.addAll(readVideoCsv(new FileReader(file)));
                break;
            case 5:
                resources.addAll(readImageCsv(new FileReader(file)));
                break;
            case 6:
                resources.addAll(readAudioWaweBwfCsv(new FileReader(file)));
                break;
            case 7:
                resources.addAll(readDonneeLaserBrutCsv(new FileReader(file)));
                break;
            case 8:
                resources.addAll(readDonneeLaserConsoCsv(new FileReader(file)));
                break;
            case 9:
                resources.addAll(readNuagePointsPhotogrammetrieCsv(new FileReader(file)));
                break;
            case 10:
                resources.addAll(readMaillage3dPhotogrammetrieCsv(new FileReader(file)));
                break;
            case 11:
                resources.addAll(readMaillage3dGeometryCsv(new FileReader(file)));
                break;
        }

        return resources;
    }



    private List<NuagePointsPhotogrammetrie> readNuagePointsPhotogrammetrieCsv(Reader file) {

        List<NuagePointsPhotogrammetrie> resources = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(file)) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] values = line.split(CHAMPS_DELIMITER);
                List<String> row = Arrays.asList(values);
                NuagePointsPhotogrammetrie nuagePointsPhotogrammetrie = readNuagePointsPhotogrammetrieRow(row);
                if (!ObjectUtils.isEmpty(nuagePointsPhotogrammetrie)) {
                    resources.add(nuagePointsPhotogrammetrie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resources;
    }

    private NuagePointsPhotogrammetrie readNuagePointsPhotogrammetrieRow(List<String> row) {
        try {
            NuagePointsPhotogrammetrie nuagePointsPhotogrammetrie = new NuagePointsPhotogrammetrie();
            nuagePointsPhotogrammetrie.setTitre(StringUtils.formatFileName(readStringValue(row, 0)));
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

    private List<Maillage3dGeometry> readMaillage3dGeometryCsv(Reader file) {

        List<Maillage3dGeometry> resources = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(file)) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] values = line.split(CHAMPS_DELIMITER);
                List<String> row = Arrays.asList(values);
                Maillage3dGeometry maillage3dGeometry = readMaillage3dGeometry(row);
                if (!ObjectUtils.isEmpty(maillage3dGeometry)) {
                    resources.add(maillage3dGeometry);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resources;
    }

    private Maillage3dGeometry readMaillage3dGeometry(List<String> row) {
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

    private List<Maillage3dPhotogrammetrie> readMaillage3dPhotogrammetrieCsv(Reader file) {

        List<Maillage3dPhotogrammetrie> resources = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(file)) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] values = line.split(CHAMPS_DELIMITER);
                List<String> row = Arrays.asList(values);
                Maillage3dPhotogrammetrie maillage3dPhotogrammetrie = readMaillage3dPhotogrammetrieRow(row);
                if (!ObjectUtils.isEmpty(maillage3dPhotogrammetrie)) {
                    resources.add(maillage3dPhotogrammetrie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resources;
    }

    private Maillage3dPhotogrammetrie readMaillage3dPhotogrammetrieRow(List<String> row) {
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

    private List<DonneeLaserBrut> readDonneeLaserBrutCsv(Reader file) {

        List<DonneeLaserBrut> resources = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(file)) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] values = line.split(CHAMPS_DELIMITER);
                List<String> row = Arrays.asList(values);
                DonneeLaserBrut donneeLaserBrut = readDonneeLaserBrutRow(row);
                if (!ObjectUtils.isEmpty(donneeLaserBrut)) {
                    resources.add(donneeLaserBrut);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resources;
    }

    private DonneeLaserBrut readDonneeLaserBrutRow(List<String> row) {
        try {
            DonneeLaserBrut donneeLaserBrut = new DonneeLaserBrut();
            donneeLaserBrut.setTitre(StringUtils.formatFileName(readStringValue(row, 0)));
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

    private List<DonneeLaserConso> readDonneeLaserConsoCsv(Reader file) {

        List<DonneeLaserConso> resources = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(file)) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] values = line.split(CHAMPS_DELIMITER);
                List<String> row = Arrays.asList(values);
                DonneeLaserConso donneeLaserConso = readDonneeLaserConsoRow(row);
                if (!ObjectUtils.isEmpty(donneeLaserConso)) {
                    resources.add(donneeLaserConso);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resources;
    }

    private DonneeLaserConso readDonneeLaserConsoRow(List<String> row) {
        try {
            DonneeLaserConso donneeLaserConso = new DonneeLaserConso();
            donneeLaserConso.setTitre(StringUtils.formatFileName(readStringValue(row, 0)));
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

    private List<AudioWaweBwf> readAudioWaweBwfCsv(Reader file) {

        List<AudioWaweBwf> resources = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(file)) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] values = line.split(CHAMPS_DELIMITER);
                List<String> row = Arrays.asList(values);
                AudioWaweBwf audioWaweBwf = readAudioWaweBwfRow(row);
                if (!ObjectUtils.isEmpty(audioWaweBwf)) {
                    resources.add(audioWaweBwf);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resources;
    }

    private AudioWaweBwf readAudioWaweBwfRow(List<String> row) {
        try {
            AudioWaweBwf audioWaweBwf = new AudioWaweBwf();
            audioWaweBwf.setTitre(StringUtils.formatFileName(readStringValue(row, 0)));
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

    private List<Image> readImageCsv(Reader file) {

        List<Image> resources = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(file)) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] values = line.split(CHAMPS_DELIMITER);
                List<String> row = Arrays.asList(values);
                Image image = readImageRow(row);
                if (!ObjectUtils.isEmpty(image)) {
                    resources.add(image);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resources;
    }

    private Image readImageRow(List<String> row) {
        try {
            Image image = new Image();
            image.setTitre(StringUtils.formatFileName(readStringValue(row, 0)));
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

    private List<Video> readVideoCsv(Reader file) {

        List<Video> resources = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(file)) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] values = line.split(CHAMPS_DELIMITER);
                List<String> row = Arrays.asList(values);
                Video video = readVideoRow(row);
                if (!ObjectUtils.isEmpty(video)) {
                    resources.add(video);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resources;
    }

    private Video readVideoRow(List<String> row) {
        try {
            Video video = new Video();
            video.setTitre(StringUtils.formatFileName(readStringValue(row, 1)));
            video.setCreateur(readStringValue(row, 2));

            List<Label> labelList = motsCleList(readStringValue(row, 3));
            if (!CollectionUtils.isEmpty(labelList)) {
                video.setMotsCles(labelList.stream().map(label -> label.getLabel()).collect(Collectors.toList()));
                video.setMotsClesLabel(labelList);
            }

            video.setDescription(readStringValue(row, 4));
            video.setMedia(readStringValue(row, 5));
            video.setEditeur(readStringValue(row, 6));
            video.setContributeur(readStringValue(row, 7));
            video.setLangue(readStringValue(row, 8));
            video.setDateCreationFichier(DateUtils.formatStringToDate(readStringValue(row, 9)));
            video.setType(readStringValue(row, 10));
            video.setSupport(readStringValue(row, 11));
            video.setFormat(readStringValue(row, 12));
            video.setIdentifiantUnique(readStringValue(row, 13));
            video.setExtension(readStringValue(row, 14));
            video.setLienInternet(readStringValue(row, 15));
            video.setDateConsultation(DateUtils.formatStringToDate(readStringValue(row, 16)));
            video.setDateCreationMp4(DateUtils.formatStringToDate(readStringValue(row, 17)));
            video.setRelation(readStringValue(row, 18));
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

    private List<Url> readUrlCsv(Reader file) {

        List<Url> resources = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(file)) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] values = line.split(CHAMPS_DELIMITER);
                List<String> row = Arrays.asList(values);
                Url url = readUrlRow(row);
                if (!ObjectUtils.isEmpty(url)) {
                    resources.add(url);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resources;
    }

    private Url readUrlRow(List<String> row) {
        try {
            Url url = new Url();
            url.setTitre(StringUtils.formatFileName(readStringValue(row, 0)));
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

    private List<ArticlePresse> readArticlePresseCsv(Reader file) {

        List<ArticlePresse> resources = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(file)) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] values = line.split(CHAMPS_DELIMITER);
                List<String> row = Arrays.asList(values);
                ArticlePresse articlePresse = readArticlePresseRow(row);
                if (!ObjectUtils.isEmpty(articlePresse)) {
                    resources.add(articlePresse);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resources;
    }

    private ArticlePresse readArticlePresseRow(List<String> row) {
        try {
            ArticlePresse articlePresse = new ArticlePresse();
            articlePresse.setTitre(StringUtils.formatFileName(readStringValue(row, 1)));
            articlePresse.setCreateur(readStringValue(row, 2));

            List<Label> labelList = motsCleList(readStringValue(row, 3));
            if (!CollectionUtils.isEmpty(labelList)) {
                articlePresse.setMotsCles(labelList.stream().map(label -> label.getLabel()).collect(Collectors.toList()));
                articlePresse.setMotsClesLabel(labelList);
            }

            articlePresse.setDescription(readStringValue(row, 4));
            articlePresse.setMedia(readStringValue(row, 5));
            articlePresse.setEditeur(readStringValue(row, 6));
            articlePresse.setContributeur(readStringValue(row, 7));
            articlePresse.setLangue(readStringValue(row, 8));
            articlePresse.setDateCreationFichier(DateUtils.formatStringToDate(readStringValue(row, 9)));
            articlePresse.setType(readStringValue(row, 10));
            articlePresse.setSupport(readStringValue(row, 11));
            articlePresse.setFormat(readStringValue(row, 12));
            articlePresse.setIdentifiantUnique(readStringValue(row, 13));
            articlePresse.setExtension(readStringValue(row, 14));
            articlePresse.setLienInternet(readStringValue(row, 15));
            articlePresse.setDateConsultation(DateUtils.formatStringToDate(readStringValue(row, 16)));
            articlePresse.setRelation(readStringValue(row, 17));
            articlePresse.setRelationLien(readStringValue(row, 18));
            articlePresse.setDateCreationPDF(DateUtils.formatStringToDate(readStringValue(row, 19)));
            articlePresse.setNotesInternes(readStringValue(row, 20));
            articlePresse.setPreparation(readStringValue(row, 21));
            articlePresse.setCollecteur(readStringValue(row, 22));
            articlePresse.setCitationBibliographie(readStringValue(row, 23));
            articlePresse.setGestionDesDroits(readStringValue(row, 24));

            return articlePresse;
        } catch (Exception ex) {
            return null;
        }
    }

    private List<DeblinCore> readDeblinCoreCsv(Reader file) {

        List<DeblinCore> deblinCoresList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(file)) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] values = line.split(CHAMPS_DELIMITER);
                List<String> row = Arrays.asList(values);
                DeblinCore deblinCore = readDeblinCoreRow(row);
                if (!ObjectUtils.isEmpty(deblinCore)) {
                    deblinCoresList.add(deblinCore);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deblinCoresList;
    }

    private DeblinCore readDeblinCoreRow(List<String> row) {
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
            deblinCore.setCouverture(readDateValue(row, 13));
            deblinCore.setGestionDesDroits(readStringValue(row, 14));

            return deblinCore;
        } catch (Exception ex) {
            return null;
        }
    }

    private List<Label> motsCleList(String str) {
        List<Label> labels = new ArrayList<>();

        List<String> labelsStr = Arrays.asList(str.split(" " + motCleSeparateurMot + " "));
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

    private String readStringValue(List<String> row, int index) {
        return row.get(index) == null ? "" : row.get(index);
    }

    private Date readDateValue(List<String> row, int index) {
        return row.get(index) == null ? null : DateUtils.formatStringToDate(row.get(index));
    }
}
