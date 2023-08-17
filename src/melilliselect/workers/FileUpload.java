/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package melilliselect.workers;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @author arsam
 */
public class FileUpload {

    private String server = "files.000webhost.com";
    private int port = 21;
    private String user = "onesourcedev";
    private String password = "StarGate22@";
    private FTPClient ftpClient;
    private FileInputStream inputStream = null;

    public FileUpload() {
        ftpClient = new FTPClient();
    
    }
    public void connectFile() throws IOException{
        ftpClient.connect(server, port);
        ftpClient.login(user, password);
        ftpClient.enterLocalPassiveMode();
    }

    public void uploadFile(String filePath,String email) throws IOException {
        String remoteFilePath =  email + filePath; // Change this to your desired remote path
        inputStream = new FileInputStream(filePath); // Change this to your local file path
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.storeFile(remoteFilePath, inputStream);
        System.out.println("File uploaded successfully");
    }
}
