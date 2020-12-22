package com.cnrs.ndp.service;

import com.cnrs.ndp.model.DeblinCore;
import com.cnrs.ndp.model.Resource;
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



    public List<Resource> readDeblinCoreMetadonne(File file) throws IOException {

        FileInputStream fis = new FileInputStream(file);
        XSSFWorkbook myWorkBook = new XSSFWorkbook (fis);
        XSSFSheet mySheet = myWorkBook.getSheetAt(0);
        Iterator<Row> rowIterator = mySheet.iterator();
        boolean excludFirstLigne = true;

        List<Resource> deblinCoresList = new ArrayList<>();

        while (rowIterator.hasNext()) {
            if (excludFirstLigne) {
                excludFirstLigne = false;
                rowIterator.next();
                continue;
            }
            DeblinCore deblinCore = new DeblinCore();
            Row row = rowIterator.next();
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
            deblinCoresList.add(deblinCore);
        }

        return deblinCoresList;
    }

}
