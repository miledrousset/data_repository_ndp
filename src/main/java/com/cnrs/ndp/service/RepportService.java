package com.cnrs.ndp.service;

import com.cnrs.ndp.model.DeblinCore;
import com.cnrs.ndp.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


@Service
public class RepportService {

    private final static String FORMAT_REPPORT = ".csv";
    private final static String SEPARATEUR = ";";


    void createDeblinCoreRepport(List<DeblinCore> deblinCoreList, String filePath, String fileName) throws IOException {

        FileWriter csvWriter = new FileWriter(filePath + fileName + FORMAT_REPPORT);
        csvWriter.append("Titre").append(SEPARATEUR)
                .append("Createur").append(SEPARATEUR)
                .append("Mots clés").append(SEPARATEUR)
                .append("Description").append(SEPARATEUR)
                .append("Éditeur").append(SEPARATEUR)
                .append("Contributeur").append(SEPARATEUR)
                .append("Date de mise à disposition").append(SEPARATEUR)
                .append("Type").append(SEPARATEUR)
                .append("Format").append(SEPARATEUR)
                .append("Identifiant unique").append(SEPARATEUR)
                .append("Source").append(SEPARATEUR)
                .append("Langue").append(SEPARATEUR)
                .append("Relation").append(SEPARATEUR)
                .append("Couverture").append(SEPARATEUR)
                .append("Gestion des droits");
        csvWriter.append("\n");

        for (DeblinCore rowData : deblinCoreList) {
            csvWriter.append(String.join(rowData.getTitre(), SEPARATEUR));
            csvWriter.append(String.join(rowData.getCreateur(), SEPARATEUR));
            csvWriter.append(String.join(rowData.getMotsCles(), SEPARATEUR));
            csvWriter.append(String.join(rowData.getDescription(), SEPARATEUR));
            csvWriter.append(String.join(rowData.getEditeur(), SEPARATEUR));
            csvWriter.append(String.join(rowData.getContributeur(), SEPARATEUR));
            csvWriter.append(String.join(DateUtils.formatDateToString(rowData.getDateMiseDisposition()), SEPARATEUR));
            csvWriter.append(String.join(rowData.getType(), SEPARATEUR));
            csvWriter.append(String.join(rowData.getFormat(), SEPARATEUR));
            csvWriter.append(String.join(rowData.getIdentifiantUnique(), SEPARATEUR));
            csvWriter.append(String.join(rowData.getSource(), SEPARATEUR));
            csvWriter.append(String.join(rowData.getLangue(), SEPARATEUR));
            csvWriter.append(String.join(rowData.getRelation(), SEPARATEUR));
            csvWriter.append(String.join(rowData.getCouverture(), SEPARATEUR));
            csvWriter.append(String.join(rowData.getGestionDesDroits(), SEPARATEUR));
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();
    }

}
