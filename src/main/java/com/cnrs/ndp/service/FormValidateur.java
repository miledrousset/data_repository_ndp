package com.cnrs.ndp.service;

import com.cnrs.ndp.model.resources.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Objects;

@Service
public class FormValidateur {


    public boolean forumValidateur(String shemasValidateur, Resource resource) {

        switch (Integer.parseInt(shemasValidateur)) {
            case 1:
                DeblinCore deblinCore = (DeblinCore) resource;
                return isDeblinCoreValide(deblinCore);
            case 2:
                ArticlePresse articlePresse = (ArticlePresse) resource;
                return isArticlePresseValide(articlePresse);
            case 3:
                Url url = (Url) resource;
                return isUrlValide(url);
            case 4:
                Video video = (Video) resource;
                return isVideoValide(video);
            case 5:
                Image image = (Image) resource;
                return isImageValide(image);
            case 6:
                AudioWaweBwf audio = (AudioWaweBwf) resource;
                return isAudioValide(audio);
            case 7:
                DonneeLaserBrut donneeLaserBrut = (DonneeLaserBrut) resource;
                return isDonneeLaserBruitValide(donneeLaserBrut);
            case 8:
                DonneeLaserConso donneeLaserConso = (DonneeLaserConso) resource;
                return isDonneeLaserConsoValide(donneeLaserConso);
            case 9:
                NuagePointsPhotogrammetrie nuagePointsPhotogrammetrie = (NuagePointsPhotogrammetrie) resource;
                return isNuagePointsPhotogrammetrieValide(nuagePointsPhotogrammetrie);
            case 10:
                Maillage3dPhotogrammetrie maillage3dPhotogrammetrie = (Maillage3dPhotogrammetrie) resource;
                return isMaillage3dPhotogrammetrieValide(maillage3dPhotogrammetrie);
            case 11:
                Maillage3dGeometry maillage3dGeometry = (Maillage3dGeometry) resource;
                return isMaillage3dGeometryValide(maillage3dGeometry);
            default :
                return false;
        }

    }

    private boolean isDeblinCoreValide(DeblinCore deblinCore) {
        if (StringUtils.isEmpty(deblinCore.getTitre())               || StringUtils.isEmpty(deblinCore.getCreateur())
                || StringUtils.isEmpty(deblinCore.getDescription())  || StringUtils.isEmpty(deblinCore.getEditeur())
                || StringUtils.isEmpty(deblinCore.getContributeur()) || Objects.isNull(deblinCore.getDateMiseDisposition())
                || StringUtils.isEmpty(deblinCore.getType())         || StringUtils.isEmpty(deblinCore.getFormat())
                || StringUtils.isEmpty(deblinCore.getLangue())       || CollectionUtils.isEmpty(deblinCore.getMotsCles())
                || StringUtils.isEmpty(deblinCore.getCouverture())   || StringUtils.isEmpty(deblinCore.getGestionDesDroits())) {
            return false;
        }
        return true;
    }

    private boolean isArticlePresseValide(ArticlePresse articlePresse) {
        if (StringUtils.isEmpty(articlePresse.getTitre())                 || StringUtils.isEmpty(articlePresse.getCreateur())
                || CollectionUtils.isEmpty(articlePresse.getMotsCles())   || StringUtils.isEmpty(articlePresse.getDescription())
                || StringUtils.isEmpty(articlePresse.getMedia())          || StringUtils.isEmpty(articlePresse.getEditeur())
                || StringUtils.isEmpty(articlePresse.getContributeur())   || StringUtils.isEmpty(articlePresse.getLangue())
                || Objects.isNull(articlePresse.getDateCreationFichier()) || StringUtils.isEmpty(articlePresse.getSupport())
                || StringUtils.isEmpty(articlePresse.getType())           || StringUtils.isEmpty(articlePresse.getFormat())
                || StringUtils.isEmpty(articlePresse.getGestionDesDroits())) {
            return false;
        }
        return true;
    }

    private boolean isUrlValide(Url url) {
        if (StringUtils.isEmpty(url.getTitre())          || Objects.isNull(url.getDateCreationFichier())
                || Objects.isNull(url.getLienInternet()) || StringUtils.isEmpty(url.getCreateur())
                || StringUtils.isEmpty(url.getEditeur()) || StringUtils.isEmpty(url.getType())
                || StringUtils.isEmpty(url.getRelation())) {
            return false;
        }
        return true;
    }

    private boolean isImageValide(Image image) {
        if (StringUtils.isEmpty(image.getCreateur())
                || CollectionUtils.isEmpty(image.getMotsCles())
                || StringUtils.isEmpty(image.getEditeur())
                || StringUtils.isEmpty(image.getContributeur())
                || Objects.isNull(image.getDateCreation())
                || StringUtils.isEmpty(image.getType())
                || StringUtils.isEmpty(image.getSupportOriginalFichier())
                || StringUtils.isEmpty(image.getExtension())
                || Objects.isNull(image.getDateMiseDisposition())
                || StringUtils.isEmpty(image.getGestionDesDroits())) {
            return false;
        }
        return true;
    }

    private boolean isVideoValide(Video video) {
        if (StringUtils.isEmpty(video.getTitre())
                || StringUtils.isEmpty(video.getCreateur())
                || CollectionUtils.isEmpty(video.getMotsCles())
                || StringUtils.isEmpty(video.getDescription())
                || StringUtils.isEmpty(video.getMedia())
                || StringUtils.isEmpty(video.getEditeur())
                || StringUtils.isEmpty(video.getContributeur())
                || StringUtils.isEmpty(video.getLangue())
                || Objects.isNull(video.getDateCreationFichier())
                || StringUtils.isEmpty(video.getType())
                || StringUtils.isEmpty(video.getSupport())
                || StringUtils.isEmpty(video.getFormat())
                || StringUtils.isEmpty(video.getExtension())) {
            return false;
        }
        return true;
    }

    private boolean isAudioValide(AudioWaweBwf audioWaweBwf) {
        if (StringUtils.isEmpty(audioWaweBwf.getTitre())
                || StringUtils.isEmpty(audioWaweBwf.getCreateur())
                || CollectionUtils.isEmpty(audioWaweBwf.getMotsCles())
                || StringUtils.isEmpty(audioWaweBwf.getEditeur())
                || StringUtils.isEmpty(audioWaweBwf.getContributeur())
                || StringUtils.isEmpty(audioWaweBwf.getFormat())
                || StringUtils.isEmpty(audioWaweBwf.getSource())
                || StringUtils.isEmpty(audioWaweBwf.getCouverture())
                || Objects.isNull(audioWaweBwf.getIcrd())) {
            return false;
        }
        return true;
    }

    private boolean isDonneeLaserBruitValide(DonneeLaserBrut donneeLaserBrut) {
        if (StringUtils.isEmpty(donneeLaserBrut.getTitre())
                || StringUtils.isEmpty(donneeLaserBrut.getCreateur())
                || CollectionUtils.isEmpty(donneeLaserBrut.getMotsCles())
                || StringUtils.isEmpty(donneeLaserBrut.getDescription())
                || StringUtils.isEmpty(donneeLaserBrut.getEditeur())
                || StringUtils.isEmpty(donneeLaserBrut.getContributeur())
                || Objects.isNull(donneeLaserBrut.getDateMiseDisposition())
                || StringUtils.isEmpty(donneeLaserBrut.getType())
                || StringUtils.isEmpty(donneeLaserBrut.getFormat())
                || StringUtils.isEmpty(donneeLaserBrut.getNuage())
                || StringUtils.isEmpty(donneeLaserBrut.getCouverture())
                || StringUtils.isEmpty(donneeLaserBrut.getGestionDesDroits())) {
            return false;
        }
        return true;
    }

    private boolean isDonneeLaserConsoValide(DonneeLaserConso donneeLaserConso) {
        if (StringUtils.isEmpty(donneeLaserConso.getTitre())
                || StringUtils.isEmpty(donneeLaserConso.getCreateur())
                || CollectionUtils.isEmpty(donneeLaserConso.getMotsCles())
                || StringUtils.isEmpty(donneeLaserConso.getDescription())
                || StringUtils.isEmpty(donneeLaserConso.getEditeur())
                || StringUtils.isEmpty(donneeLaserConso.getContributeur())
                || Objects.isNull(donneeLaserConso.getDateMiseDisposition())
                || StringUtils.isEmpty(donneeLaserConso.getType())
                || StringUtils.isEmpty(donneeLaserConso.getFormat())
                || StringUtils.isEmpty(donneeLaserConso.getIdentifiantUnique())
                || StringUtils.isEmpty(donneeLaserConso.getRelation())
                || StringUtils.isEmpty(donneeLaserConso.getCouverture())
                || StringUtils.isEmpty(donneeLaserConso.getGestionDesDroits())
                || StringUtils.isEmpty(donneeLaserConso.getIdSources())
                || StringUtils.isEmpty(donneeLaserConso.getLogiciel())
                || StringUtils.isEmpty(donneeLaserConso.getMethodeConsolidation())) {
            return false;
        }
        return true;
    }

    private boolean isNuagePointsPhotogrammetrieValide(NuagePointsPhotogrammetrie nuagePointsPhotogrammetrie) {
        if (StringUtils.isEmpty(nuagePointsPhotogrammetrie.getTitre())
                || StringUtils.isEmpty(nuagePointsPhotogrammetrie.getCreateur())
                || CollectionUtils.isEmpty(nuagePointsPhotogrammetrie.getMotsCles())
                || StringUtils.isEmpty(nuagePointsPhotogrammetrie.getDescription())
                || StringUtils.isEmpty(nuagePointsPhotogrammetrie.getEditeur())
                || StringUtils.isEmpty(nuagePointsPhotogrammetrie.getContributeur())
                || Objects.isNull(nuagePointsPhotogrammetrie.getDateMiseDisposition())
                || StringUtils.isEmpty(nuagePointsPhotogrammetrie.getType())
                || StringUtils.isEmpty(nuagePointsPhotogrammetrie.getFormat())
                || StringUtils.isEmpty(nuagePointsPhotogrammetrie.getIdentifiantUnique())
                || StringUtils.isEmpty(nuagePointsPhotogrammetrie.getSource())
                || StringUtils.isEmpty(nuagePointsPhotogrammetrie.getLangue())
                || StringUtils.isEmpty(nuagePointsPhotogrammetrie.getRelation())
                || StringUtils.isEmpty(nuagePointsPhotogrammetrie.getCouverture())
                || StringUtils.isEmpty(nuagePointsPhotogrammetrie.getGestionDesDroits())
                || StringUtils.isEmpty(nuagePointsPhotogrammetrie.getIdSources())
                || StringUtils.isEmpty(nuagePointsPhotogrammetrie.getLogicielTraitement())) {
            return false;
        }
        return true;
    }

    private boolean isMaillage3dPhotogrammetrieValide(Maillage3dPhotogrammetrie maillage3dPhotogrammetrie) {
        if (StringUtils.isEmpty(maillage3dPhotogrammetrie.getIdSources())
                || StringUtils.isEmpty(maillage3dPhotogrammetrie.getMethodeAssemblageNuagesPoints())
                || StringUtils.isEmpty(maillage3dPhotogrammetrie.getPourcentageDecimation())
                || StringUtils.isEmpty(maillage3dPhotogrammetrie.getAlgorithmeUtiliseMaillage())
                || StringUtils.isEmpty(maillage3dPhotogrammetrie.getMethodeTexturage())
                || StringUtils.isEmpty(maillage3dPhotogrammetrie.getProjectionTexture())) {
            return false;
        }
        return true;
    }

    private boolean isMaillage3dGeometryValide(Maillage3dGeometry maillage3dGeometry) {
        if (StringUtils.isEmpty(maillage3dGeometry.getAxeOrientation())
                || Objects.isNull(maillage3dGeometry.getAxeVertical())
                || StringUtils.isEmpty(maillage3dGeometry.getUniteMesure())
                || StringUtils.isEmpty(maillage3dGeometry.getLogicielTraitement())
                || StringUtils.isEmpty(maillage3dGeometry.getDimensionX())
                || StringUtils.isEmpty(maillage3dGeometry.getDimensionY())
                || StringUtils.isEmpty(maillage3dGeometry.getDimensionZ())
                || Objects.isNull(maillage3dGeometry.getDateFichier())) {
            return false;
        }
        return true;
    }

}
