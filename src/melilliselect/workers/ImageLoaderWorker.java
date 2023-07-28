/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package melilliselect.workers;

/**
 *
 * @author arsam
 */
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import melilliselect.FileExplorer;
import melilliselect.Models.ImageEncryptionDecryption;
import melilliselect.Models.ImageFileModel;

public class ImageLoaderWorker extends SwingWorker<Void, ImageFileModel> {

    ArrayList<ImageFileModel> imageFiles;
    int counter = 0;
    private GroupLayout groupLayout;
    private GroupLayout.SequentialGroup sg;
    private GroupLayout.ParallelGroup pg;
    JPanel j1;

    public ImageLoaderWorker(ArrayList<ImageFileModel> imageFiles) {
        this.imageFiles = imageFiles;
    }

    @Override
    protected Void doInBackground() {
        ImageIcon ic;
        for (int i = 0; i < imageFiles.size(); i++) {
            try {
                BufferedImage image = getBufferedImage(imageFiles.get(i).getPath());
                if (image != null) {
                    double aspectRatio = (double) image.getWidth() / image.getHeight();
                    int newWidth = 113;
                    if (image.getWidth() > newWidth || image.getHeight() > 100) {

                        int newHeight = (int) (113 / aspectRatio);
                        if (newHeight > 100) {
                            newHeight = 113;
                            newWidth = (int) (113 * aspectRatio);
                        }
                        Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                        BufferedImage thumbnail = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
                        Graphics2D g2d = thumbnail.createGraphics();
                        g2d.drawImage(scaledImage, 0, 0, null);
                        g2d.dispose();
                        ic = new ImageIcon(thumbnail);
                    } else {
                        ic = new ImageIcon(image);
                    }
                    imageFiles.get(i).setBufferedImage(ic);
                    publish(imageFiles.get(i));
                }
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
        return null;

    }

    public static byte[] findThumbnailData(byte[] data) {
        byte[] thumbnailData = new byte[0];
        byte[] cnthTag = {
            (byte) 0xEA, (byte) 0xF4, (byte) 0x2B, (byte) 0x5E,
            (byte) 0x1C, (byte) 0x98, (byte) 0x4B, (byte) 0x88,
            (byte) 0xB9, (byte) 0xFB, (byte) 0xB7, (byte) 0xDC,
            (byte) 0x40, (byte) 0x6E, (byte) 0x4D, (byte) 0x16
        };

        byte[] buffer = data;
        for (int i = 0; i < buffer.length - cnthTag.length; i++) {
            boolean tagFound = true;
            for (int j = 0; j < cnthTag.length; j++) {
                if (buffer[i + j] != cnthTag[j]) {
                    tagFound = false;
                    break;
                }
            }

            if (tagFound) {
                int thumbnailStart = i + 16;
                int thumbnailEnd = thumbnailStart + 16 + 8 + 20;
                thumbnailData = new byte[thumbnailEnd - thumbnailStart];
                System.arraycopy(buffer, thumbnailStart, thumbnailData, 0, thumbnailData.length);
                break;
            }
        }

        // Additional code to search for JPEG start and end markers after thumbnail_data
        byte[] jpegStartMarker = {(byte) 0xFF, (byte) 0xD8};
        byte[] jpegEndMarker = {(byte) 0xFF, (byte) 0xD9};
        int startOffset = indexOf(buffer, jpegStartMarker, thumbnailData.length);
        int endOffset = indexOf(buffer, jpegEndMarker, startOffset + jpegStartMarker.length);

        if (startOffset != -1 && endOffset != -1) {
            int jpegDataLength = endOffset - startOffset + jpegEndMarker.length;
            byte[] jpegData = new byte[jpegDataLength];
            System.arraycopy(buffer, startOffset, jpegData, 0, jpegDataLength);
            return jpegData;
        } else {
            return null;
        }

    }

    public static BufferedImage getBufferedImage(String path) throws IOException {
        File cr2File = new File(path);
        byte[] fileData;
        try {
            fileData = ImageEncryptionDecryption.decryptImage("arsam", cr2File);

            InputStream inputStream = new ByteArrayInputStream(fileData);
            BufferedImage image = ImageIO.read(inputStream);
            if (image == null) {
                byte[] ia = findThumbnailData(fileData);
                ByteArrayInputStream bis = new ByteArrayInputStream(ia);
                image = ImageIO.read(bis);

            }
            return image;
        } catch (Exception ex) {
            Logger.getLogger(ImageLoaderWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR);
    }

    private static int indexOf(byte[] array, byte[] subArray, int startIndex) {
        for (int i = startIndex; i < array.length - subArray.length; i++) {
            boolean match = true;
            for (int j = 0; j < subArray.length; j++) {
                if (array[i + j] != subArray[j]) {
                    match = false;
                    break;
                }
            }
            if (match) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void process(List<ImageFileModel> chunks) {
        for (ImageFileModel image : chunks) {
            image.getImageLabel().setIcon(image.getBufferedImage());
        }

    }
}
