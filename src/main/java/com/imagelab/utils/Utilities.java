package com.imagelab.utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.opencv.imgcodecs.Imgcodecs.imencode;

/**
 * This class contains the reusable
 * utility methods of the application.
 */
public final class Utilities {
    /**
     * Utility class constructor.
     */
    private Utilities() {
    }

    /**
     * This is a utility function which converts an OpenCV Mat object
     * to a writable image file.
     *
     * @param image         - Mat object generated from the build pipeline.
     * @param fileExtension - jpg/png.
     * @return writableImage.
     */
    public static WritableImage loadImage(Mat image, String fileExtension) {
        //returning a writable image
        return SwingFXUtils.toFXImage(convertMatToBufImage(image,
                fileExtension),
                null
        );
    }

    /**
     * This utility methods converts a given
     * mat image object to a buffered image
     * with the given image extension.
     *
     * @param image         - Mat image object.
     * @param fileExtension - File extension to be added to the bugImage.
     * @return - Converted Buffered image.
     */
    private static BufferedImage convertMatToBufImage(Mat image,
                                                      String fileExtension) {
        // convert the matrix into a matrix.
        // of bytes appropriate for this file extension.
        MatOfByte matOfByte = new MatOfByte();
        if (image != null) {
            imencode(fileExtension, image, matOfByte);
        } else {
            throw new NullPointerException("image");
        }
        //convert the "matrix of bytes" into a byte array.
        byte[] byteArray = matOfByte.toArray();
        BufferedImage bufImage = null;
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bufImage;
    }

    /**
     * The utility method which draws
     * an ascii art for a given string.
     *
     * @param string - string to be generate as art.
     * @param width  - int width of the art.
     * @param height -  int height of the art.
     */
    public static void drawAsciiArt(String string, int width, int height) {
        // matching width = 150 height = 30
        BufferedImage image = new BufferedImage(
                width,
                height,
                BufferedImage.TYPE_INT_RGB
        );
        Graphics graphics = image.getGraphics();
        graphics.setFont(new Font(
                "SansSerif",
                Font.BOLD,
                15)
        );
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON
        );
        graphics2D.drawString(string, 20, 20);
        for (int y = 0; y < height; y++) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int x = 0; x < width; x++) {
                stringBuilder.append(image.getRGB(x, y) == -16777216 ? " " : "#");
            }
            if (stringBuilder.toString().trim().isEmpty()) {
                continue;
            }
            System.out.println(stringBuilder);
        }
    }
}
