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
public class SettingsModel implements Serializable {

    private String email;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    private String path;
    private String dateOfBirth;

    public SettingsModel(String name, String email, String path, String dateOfBirth) {
        this.name = name;
        this.email = email;
        this.path = path;
        this.dateOfBirth = dateOfBirth;
    }

    public SettingsModel() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

}
