/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package melilliselect.Models;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
/**
 *
 * @author arsam
 */

public class LeftOnlyBorder extends AbstractBorder {
    private Color borderColor;
    private int borderThickness;

    public LeftOnlyBorder(int borderThickness, Color borderColor) {
        this.borderColor = borderColor;
        this.borderThickness = borderThickness;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();

        // Draw the left border
        g2d.setColor(borderColor);
        g2d.fillRect(x, y, borderThickness, height);

        g2d.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        // Return an Insets object with left padding only
        return new Insets(0, borderThickness, 0, 0);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        // Return an Insets object with left padding only
        insets.set(0, borderThickness, 0, 0);
        return insets;
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }
}