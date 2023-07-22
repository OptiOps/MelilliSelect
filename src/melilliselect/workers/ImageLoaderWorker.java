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
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import melilliselect.FileExplorer;
import melilliselect.ImageLabel;
import melilliselect.Models.ImageFileModel;
import melilliselect.StaticData;

public class ImageLoaderWorker extends SwingWorker<Void, ImageFileModel> {

    ArrayList<ImageFileModel> imageFiles;
    FileExplorer fe;
    int counter = 0;
    private GroupLayout groupLayout;
    private GroupLayout.SequentialGroup sg;
    private GroupLayout.ParallelGroup pg;
    JPanel j1;

    public ImageLoaderWorker(ArrayList<ImageFileModel> imageFiles, FileExplorer fe) {
        this.imageFiles = imageFiles;
        this.fe = fe;
    }

    @Override
    protected Void doInBackground() throws Exception {
        ImageIcon ic ;
        for (int i = 0; i < imageFiles.size(); i++) {
            File cr2File = new File(imageFiles.get(i).getPath());
            BufferedImage image = ImageIO.read(cr2File);
            if (image != null) {
                double aspectRatio = (double) image.getWidth() / image.getHeight();
                int newWidth = 113;
                if(image.getWidth()>newWidth || image.getHeight()>100){
                    
       
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
                }else{
                    ic = new ImageIcon(image);
                }
                imageFiles.get(i).setBufferedImage(ic);
                publish(imageFiles.get(i));
            }
        }
        return null;

    }

    @Override
    protected void process(List<ImageFileModel> chunks) {
        for (ImageFileModel image : chunks) {
            image.getImageLabel().setIcon(image.getBufferedImage());
        }
      
    }
}
