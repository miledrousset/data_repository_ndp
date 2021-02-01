package com.cnrs.ndp.service;

import com.cnrs.ndp.model.resources.Resource;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface MetadonneCsvService {

    List<Resource> readCsvMetadonne(File file, String schemasSelected) throws IOException;

}
