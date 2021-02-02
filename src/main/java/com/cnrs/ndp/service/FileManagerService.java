package com.cnrs.ndp.service;

import com.cnrs.ndp.model.resources.Resource;
import org.primefaces.model.UploadedFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public interface FileManagerService {

    Resource uploadFiles (UploadedFile file, String repoName, String groupeTravailSelected, String repertoirSelected,
                                String schemasSelected, List<Resource> listMetadonnes, int groupSelectedIndex);

    void uploadFile(InputStream is, File fileOut) throws IOException;

    boolean validateFormatFile(String schemaSelected, String extentionFile);

    String createDestinationDirectoryPath(String repoName, String groupeTravailSelected, String repertoirSelected)
            throws IOException;

    String getFormatsListBySchema(String schemaSelected);

}
