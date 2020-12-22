package com.cnrs.ndp.model;

import java.util.Date;


public class ArticlePresse extends Resource {
    
    String media;
    String createur;
    String extension;
    String lienInternet;
    Date consultation;
    String relationLien;
    Date creationPDF;
    Date noteInternes;
    String preparation;
    String collecteur;

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getCreateur() {
        return createur;
    }

    public void setCreateur(String createur) {
        this.createur = createur;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getLienInternet() {
        return lienInternet;
    }

    public void setLienInternet(String lienInternet) {
        this.lienInternet = lienInternet;
    }

    public Date getConsultation() {
        return consultation;
    }

    public void setConsultation(Date consultation) {
        this.consultation = consultation;
    }

    public String getRelationLien() {
        return relationLien;
    }

    public void setRelationLien(String relationLien) {
        this.relationLien = relationLien;
    }

    public Date getCreationPDF() {
        return creationPDF;
    }

    public void setCreationPDF(Date creationPDF) {
        this.creationPDF = creationPDF;
    }

    public Date getNoteInternes() {
        return noteInternes;
    }

    public void setNoteInternes(Date noteInternes) {
        this.noteInternes = noteInternes;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public String getCollecteur() {
        return collecteur;
    }

    public void setCollecteur(String collecteur) {
        this.collecteur = collecteur;
    }
    
    
}
