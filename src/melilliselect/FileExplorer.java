/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package melilliselect;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

import melilliselect.Models.FolderModel;
import melilliselect.Models.ImageFileModel;
import melilliselect.workers.ImageLoaderWorker;

/**
 *
 * @author arsam
 */
public class FileExplorer extends javax.swing.JPanel {

    /**
     * Creates new form FileExplorer
     */
    private int padding = 15;
    private int currentIndex = 0;
    ArrayList<FolderModel> folders;
    ArrayList<ImageFileModel> imageFiles;
    private String currentDirectory = "";

    public FileExplorer() {
        initComponents();
        JScrollBar verticalScrollBar = scrollpane.getVerticalScrollBar();
        JScrollBar horizontalScrollBar = scrollpane.getHorizontalScrollBar();
        scrollpane.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        verticalScrollBar.setPreferredSize(new Dimension(8, 20));
        verticalScrollBar.setForeground(new Color(48, 144, 216));
        verticalScrollBar.setBackground(Color.WHITE);
        horizontalScrollBar.setPreferredSize(new Dimension(8, 20));
        horizontalScrollBar.setForeground(new Color(48, 144, 216));
        horizontalScrollBar.setBackground(Color.WHITE);
        getCurrentDirectoryFolders();
        initFolders();
        ImageLoaderWorker lg = new ImageLoaderWorker(imageFiles, this);
        lg.execute();

    }

    private void getCurrentDirectoryFolders() {
        folders = new ArrayList<FolderModel>();
        imageFiles = new ArrayList<ImageFileModel>();
        String currentDirectory = System.getProperty("user.dir");
        if(this.currentDirectory!=""){
            currentDirectory = this.currentDirectory;
        }
        File directory = new File(currentDirectory);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    folders.add(new FolderModel(file.getName(), file.getPath(), file.list().length, 0));
                }
                else if(checkImage(file)==true){
                    imageFiles.add(new ImageFileModel(file.getName(), file.getPath()));
                }

            }
        }
    }

    private boolean checkImage(File file) {
        URLConnection connection;
        try {
            connection = file.toURL().openConnection();
            String mimeType = connection.getContentType();
            if (mimeType.contains("image")) {
                return true;
            }
        } catch (IOException ex) {
            Logger.getLogger(FileExplorer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public void changeWorkingDirectory(String path){
        if(new File(path).exists()){
            this.currentDirectory = path;
            getCurrentDirectoryFolders();
            ImageLoaderWorker lg = new ImageLoaderWorker(imageFiles, this);
            lg.execute();
            revalidate();
            repaint();
        }
        
    }

    private void initFolders() {
        JPanel j1 = getNewJPanel();
        int maxColumns = getMaxColumns();
        int a = (int) Math.ceil((double) (maxColumns * padding) / (StaticData.folderWidth));
        GroupLayout groupLayout = setRootPanelLayout();
        SequentialGroup sg = groupLayout.createSequentialGroup();
        ParallelGroup pg = groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING);

        for (int i = 0; i < imageFiles.size() + folders.size(); i++) {
            if (i % maxColumns == 0) {
                j1 = getNewJPanel();
            }
            if (i < folders.size()) {
                j1.add(new Folder(folders.get(i),this));
            } else {
                ImageLabel jl;
                ImageFileModel ifm = imageFiles.get(i - folders.size());
                System.out.println(ifm.getPath());
                if (ifm.getBufferedImage() != null) {
                    jl = new ImageLabel(ifm.getBufferedImage(), ifm.getName(), ifm.getPath());

                } else {
                    jl = new ImageLabel(ifm.getName(), ifm.getPath());
                }
                ifm.setImageLabel(jl);
                j1.add(jl);
            }
            if (i % maxColumns == 0) {
                setJPanelSGPG(j1, sg, pg);
            }
        }
        setGroupLayouts(groupLayout, sg, pg);
    }

    @Override
    public void paint(Graphics g) {
        jPanel1.removeAll();
        initFolders();
        super.paint(g); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    public int getMaxColumns() {
        Dimension screenSize = MainMenu.screenDimension;
        int maxWidth = screenSize.width - StaticData.sideNavWidth;
        int maxColumns = (int) Math.floor((double) (maxWidth) / (StaticData.folderWidth + padding));
        return maxColumns;

    }

    public GroupLayout setRootPanelLayout() {
        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        return jPanel1Layout;

    }

    public JPanel getMainJPanel() {
        return this.jPanel1;
    }

    public void setGroupLayouts(GroupLayout groupLayout, SequentialGroup sg, ParallelGroup pg) {
        groupLayout.setHorizontalGroup(pg);
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(sg));
    }

    public void setJPanelSGPG(JPanel j1, SequentialGroup sg, ParallelGroup pg) {
        pg.addComponent(j1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        sg.addComponent(j1, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
                .addGap(padding, padding, padding);
    }

    public JPanel getNewJPanel() {
        JPanel jpanel = new JPanel();
        jpanel.setLayout(new FlowLayout(FlowLayout.LEFT, padding, padding));
        jpanel.setMinimumSize(new Dimension(200, 170));
        jpanel.setBackground(StaticData.dashboardBackground);
        return jpanel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollpane = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();

        setBackground(StaticData.dashboardBackground);

        scrollpane.setBackground(StaticData.dashboardBackground);
        scrollpane.setBorder(null);

        jPanel1.setBackground(StaticData.dashboardBackground);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 249, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 446, Short.MAX_VALUE)
        );

        scrollpane.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollpane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollpane)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane scrollpane;
    // End of variables declaration//GEN-END:variables

}
