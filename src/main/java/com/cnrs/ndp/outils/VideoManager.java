package com.cnrs.ndp.outils;

import javax.imageio.ImageIO;

import java.io.File;

import java.awt.image.BufferedImage;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IVideoPictureEvent;

import org.springframework.stereotype.Service;


@Service
public class VideoManager extends MediaListenerAdapter {

    private int mVideoStreamIndex = -1;
    private boolean gotFirst = false;
    private String saveFile;
    private Exception e;


    public void videoTraitement(String videoFile, String saveFile)throws Exception {

        this.saveFile = saveFile;
        this.e = null;

        IMediaReader reader = ToolFactory.makeReader(videoFile);

        reader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);

        reader.addListener(this);

        while (reader.readPacket() == null && !gotFirst);

        if (e != null) throw e;
    }


    public void onVideoPicture(IVideoPictureEvent event) {
        try {
            if (event.getStreamIndex() != mVideoStreamIndex) {
                if (-1 == mVideoStreamIndex)
                    mVideoStreamIndex = event.getStreamIndex();
                else
                    return;
            }

            ImageIO.write(event.getImage(), "jpg", new File(saveFile.substring(0, saveFile.lastIndexOf(".")+1) + "jpg"));
            gotFirst = true;

        } catch (Exception e) {
            this.e = e;
        }
    }
}