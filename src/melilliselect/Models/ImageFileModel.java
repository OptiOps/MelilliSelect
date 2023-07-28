/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package melilliselect.Models;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import melilliselect.ImageLabel;

/**
 *
 * @author arsam
 */
public class ImageFileModel {
    
    private ImageLabel imageLabel;
    private ImageIcon bufferedImage;
    private ImageLikeModel ilm;

    public ImageIcon getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(ImageIcon bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public ImageLabel getImageLabel() {
        return imageLabel;
    }

    public void setImageLabel(ImageLabel imageLabel) {
        this.imageLabel = imageLabel;
    }

    public ImageFileModel(String name, String path) {
        this.ilm = new ImageLikeModel(name, path);
    }
    
    public ImageLikeModel getImageLikeModel(){
        return this.ilm;
    }

    public String getName() {
        return this.ilm.getName();
    }

    public void setName(String name) {
        this.ilm.setName(name);
    }

    public String getPath() {
        return this.ilm.getPath();
    }

    public void setPath(String path) {
         this.ilm.setPath(path);
    }

    public boolean isIsHeart() {
         return this.ilm.isIsHeart();
    }

    public void setIsHeart(boolean isHeart) {
        this.ilm.setIsHeart(isHeart);   
    }

    public boolean isIsDiamond() {
        return this.ilm.isIsDiamond();
        
    }

    public void setIsDiamond(boolean isDiamond) {
       this.ilm.setIsDiamond(isDiamond);   
    }
    
}
