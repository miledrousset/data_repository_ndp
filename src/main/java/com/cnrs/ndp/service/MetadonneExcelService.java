package com.cnrs.ndp.service;

import com.cnrs.ndp.model.resources.Resource;

import java.io.File;
import java.io.IOException;
import java.util.List;


public interface MetadonneExcelService {

    List<Resource> readDeblinCoreMetadonne(File file, String schemasSelected) throws IOException;

}
