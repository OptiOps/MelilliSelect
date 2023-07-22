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
    private String name;
    private String path;
    private boolean isHeart;
    private boolean isDiamond;
    private ImageLabel imageLabel;
    private ImageIcon bufferedImage;

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
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isIsHeart() {
        return isHeart;
    }

    public void setIsHeart(boolean isHeart) {
        this.isHeart = isHeart;
    }

    public boolean isIsDiamond() {
        return isDiamond;
    }

    public void setIsDiamond(boolean isDiamond) {
        this.isDiamond = isDiamond;
    }
    
}
