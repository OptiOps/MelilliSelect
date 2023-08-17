/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package melilliselect.Models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import melilliselect.MainMenu;
import melilliselect.Settings;

import melilliselect.StaticData;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.EncryptionMethod;

/**
 *
 * @author arsam
 */
public class FileManager {

    private Map<String, ImageLikeModel> fileLikeDislikeMap;
    public SettingsModel settingsModel;
    private String dataFilePath = "melliliselectdata.dat";
    private String settingsFilePath = "mellilisettings.dat";
    public static int totalLiked = 0;
    public static int totalDiamond = 0;
    public static int totalUpload = 0;

    public FileManager() {
        fileLikeDislikeMap = new HashMap<>();

    }
    public SettingsModel getSettingsModel(){
        return this.settingsModel;
    }

    public void recordHeart(ImageLikeModel ifm) {
        String finalCopyPath = getCoppiedPath(ifm.getPath());
        System.out.println(finalCopyPath);
        String sourcePath = ifm.getPath();
        if (ifm.isIsDiamond() || ifm.isIsHeart()) {
            ifm.setUpdateDateTime();
            fileLikeDislikeMap.put(ifm.getPath(), ifm);
            try {
                copyFileWithDirectoryStructure(sourcePath, finalCopyPath);
            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            deleteDestinationFile(sourcePath, finalCopyPath);
            this.removeFileRecord(ifm.getPath());
        }
        saveDataToFile();
    }

    public Map<String, ImageLikeModel> getFileLikeDislikeMap() {
        return fileLikeDislikeMap;
    }

    public String getCoppiedPath(String path) {
        String finalCopyPath = path.replace(StaticData.currentWorkingDirectory, "");
        int lastSlashIndex = finalCopyPath.lastIndexOf('/');
        finalCopyPath = finalCopyPath.substring(0, lastSlashIndex + 1);
        finalCopyPath = StaticData.destinationFolderPath + finalCopyPath;
        return finalCopyPath;
    }

    public void getTotalLiked() {
        totalLiked = 0;
        totalDiamond = 0;
        Collection<ImageLikeModel> values = fileLikeDislikeMap.values();
        for (ImageLikeModel ilm : values) {
            if (ilm.isIsDiamond()) {
                totalDiamond++;
            } else if (ilm.isIsHeart()) {
                totalLiked++;
            }
            if (ilm.isIsUploaded()) {
                totalUpload++;
            }
        }
    }

    public ImageLikeModel getImageFile(String filePath, String name) {
        return fileLikeDislikeMap.getOrDefault(filePath, new ImageLikeModel(name, filePath));
    }

    // Save data to a file using serialization
    public void saveDataToFile() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.dataFilePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(fileLikeDislikeMap);
            objectOutputStream.close();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void saveSettingsToFile() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.settingsFilePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(settingsModel);
            objectOutputStream.close();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load data from a file
    public void loadDataFromFile() {
        try {
            FileInputStream fileInputStream = new FileInputStream(this.dataFilePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            fileLikeDislikeMap = (Map<String, ImageLikeModel>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            System.out.println("Data loaded from file: " + this.dataFilePath);
        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
        }
    }

    public void loadSettingsFromFile() {
        try {
            FileInputStream fileInputStream = new FileInputStream(this.settingsFilePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            settingsModel = (SettingsModel) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            if (settingsModel!= null && settingsModel.getEmail()!="") {
            }
            else{
                StaticData.sidenav.updateDashboardForce("5", new Settings());
            }
        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
        StaticData.sidenav.updateDashboardForce("5", new Settings());
        
        }
    }

    public int getDirectorySelectedItems(String path) {
        int count = 0;
        for (String filePath : fileLikeDislikeMap.keySet()) {
            if (filePath.startsWith(path)) {
                count++;
            }
        }
        return count;
    }

    public void removeFileRecord(String filePath) {
        fileLikeDislikeMap.remove(filePath);
    }

    public Collection<ImageLikeModel> getAllFilesSaved() {
        Collection<ImageLikeModel> values = fileLikeDislikeMap.values();
        return values;

    }

    public void copyFileWithDirectoryStructure(String sourceFilePath, String destinationFolderPath) throws IOException {
        File sourceFile = new File(sourceFilePath);
        File destinationFolder = new File(destinationFolderPath);

        if (!destinationFolder.exists()) {
            destinationFolder.mkdirs(); // Create the destination folder and any missing parent directories
        }

        Path sourcePath = Paths.get(sourceFilePath);
        Path destinationPath = Paths.get(destinationFolderPath, sourceFile.getName());

        Files.copy(sourcePath, destinationPath);
    }

    public void deleteDestinationFile(String sourceFilePath, String destinationFolderPath) {
        File sourceFile = new File(sourceFilePath);
        Path sourcePath = Paths.get(sourceFilePath);
        File destinationFolder = new File(destinationFolderPath, sourceFile.getName());
        if (destinationFolder.exists()) {
            destinationFolder.delete();
        }

    }
}
