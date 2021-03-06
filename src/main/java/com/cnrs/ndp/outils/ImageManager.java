package com.cnrs.ndp.outils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


@Service
public class ImageManager {

    @Value("${upload.image.small.width.max}")
    private int widthSmallMax;

    @Value("${upload.image.small.height.max}")
    private int heightSmallMax;



    public void imageTraitement(File inputImageFile, String outputImagePath) throws IOException {

        BufferedImage bimg = ImageIO.read(inputImageFile);
        Dimension imgSize = new Dimension(bimg.getWidth(), bimg.getHeight());  // Image origine size
        Dimension boundary = new Dimension(widthSmallMax, heightSmallMax); // Box size to put image
        
        // Calcule de la nouvelle dimonsion de la photo
        Dimension result = getScaledDimension(imgSize, boundary);
 
        try {
            // resize to a fixed width 
            resize(inputImageFile, outputImagePath, (int) result.getWidth(), (int) result.getHeight());
        } catch (IOException ex) {
            System.out.println("Error resizing the image.");
            ex.printStackTrace();
        }
    }


    private Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

        int original_width = imgSize.width;
        int original_height = imgSize.height;
        int bound_width = boundary.width;
        int bound_height = boundary.height;
        int new_width = original_width;
        int new_height = original_height;

        // first check if we need to scale width
        if (original_width > bound_width) {
            //scale width to fit
            new_width = bound_width;
            //scale height to maintain aspect ratio
            new_height = (new_width * original_height) / original_width;
        }

        // then check if we need to scale even with the new height
        if (new_height > bound_height) {
            //scale height to fit instead
            new_height = bound_height;
            //scale width to maintain aspect ratio
            new_width = (new_height * original_width) / original_height;
        }

        return new Dimension(new_width, new_height);
    }
    
    /**
     * Resizes an image to a absolute width and height (the image may not be
     * proportional)
     * @param inputFile Path of the original image
     * @param outputImagePath Path to save the resized image
     * @param scaledWidth absolute width in pixels
     * @param scaledHeight absolute height in pixels
     * @throws IOException
     */
    public void resize(File inputFile, String outputImagePath, int scaledWidth, int scaledHeight)
            throws IOException {

        // reads input image
        BufferedImage inputImage = ImageIO.read(inputFile);
 
        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());
 
        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();
 
        // extracts extension of output file
        String formatName = outputImagePath.substring(outputImagePath
                .lastIndexOf(".") + 1);
 
        // writes to output file
        ImageIO.write(outputImage, formatName, new File(outputImagePath));
    }
 
}
