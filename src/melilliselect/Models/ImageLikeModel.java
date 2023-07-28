/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package melilliselect.Models;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author arsam
 */
public class ImageLikeModel implements Serializable{
    private String name;
    private String path;
    private boolean isHeart;
    private boolean isDiamond;
    private LocalDateTime updateDateTime;
    public ImageLikeModel(String name, String path) {
        this.name = name;
        this.path = path;
        this.isDiamond = false;
        this.isHeart = false;
        this.updateDateTime = LocalDateTime.now();
        
    }

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime() {
        
        this.updateDateTime = LocalDateTime.now();
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
