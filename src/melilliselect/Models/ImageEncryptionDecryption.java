/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package melilliselect.Models;

/**
 *
 * @author arsam
 */
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImageEncryptionDecryption {

    private static SecretKeySpec generateKey(String password) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = sha.digest(password.getBytes("UTF-8"));
        key = Arrays.copyOf(key, 16); // Use only first 128 bits (16 bytes) for AES-128
        return new SecretKeySpec(key, "AES");
    }

    public static void encryptImage(String password, File inputFile, File outputFile) throws Exception {
        SecretKeySpec secretKey = generateKey(password);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        try (FileInputStream inputStream = new FileInputStream(inputFile); FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);
            outputStream.write(outputBytes);
        }
    }

    public  static byte[] decryptImage(String password, File inputFile) throws Exception {
        SecretKeySpec secretKey = generateKey(password);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        try (FileInputStream inputStream = new FileInputStream(inputFile);) {
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);
            return outputBytes;
        }
    }

    public static void main(String[] args) {
        String password = "arsam";

        File sourceDirectory = new File("/home/arsam/Workspace/MelilliSelect/MelilliSelect/src/melilliselect/resources");
        String directoryPath = "encryptedMelliel";

        // Create a File object representing the directory
        File directory = new File(directoryPath);
        System.out.println(directory.exists());
        // Check if the directory already exists
        if (!directory.exists()) {
            // Create the directory
            boolean success = directory.mkdirs();
        }
        if (sourceDirectory.exists() && sourceDirectory.isDirectory()) {
            File[] files = sourceDirectory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        File encryptedFile = new File(directoryPath+"/"+file.getName());
            
                        try {
                            encryptImage(password, file, encryptedFile);
                        } catch (Exception ex) {
                            Logger.getLogger(ImageEncryptionDecryption.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                System.out.println("Encryption complete.");
            } else {
                System.out.println("Source directory is empty.");
            }
        } else {
            System.out.println("Source directory does not exist or is not a directory.");
        }
       
    }

}
