/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package melilliselect.Models;

/**
 *
 * @author arsam
 */
public class FolderModel {
    private String name;
    private String directory;
    private long totalFiles;
    private long selectedFiles;

    public FolderModel(String name, String directory, long totalFiles, long selectedFiles) {
        this.name = name;
        this.directory = directory;
        this.totalFiles = totalFiles;
        this.selectedFiles = selectedFiles;
    }

    public FolderModel(String name, String directory, long totalFiles) {
        this.name = name;
        this.directory = directory;
        this.totalFiles = totalFiles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public long getTotalFiles() {
        return totalFiles;
    }

    public void setTotalFiles(long totalFiles) {
        this.totalFiles = totalFiles;
    }

    public long getSelectedFiles() {
        return selectedFiles;
    }

    public void setSelectedFiles(long selectedFiles) {
        this.selectedFiles = selectedFiles;
    }
    
    
}
