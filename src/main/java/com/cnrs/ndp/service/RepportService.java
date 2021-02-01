package com.cnrs.ndp.service;

import com.cnrs.ndp.model.resources.Resource;

import java.util.List;


public interface RepportService {

    void createMetadonneFile(List<Resource> resources, String filePath, String fileName, String schemaSelected);

}
