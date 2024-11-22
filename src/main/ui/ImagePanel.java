package ui;

import java.awt.Dimension;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {
    // Constructs an image panel for logo and graph
    // EFFECTS: sets an image to appear in the upper section of the frame;
    //          update this with the image whose graph is to be displayed
    public ImagePanel() {
        super();
    }

    // EFFECTS: sets a fixed size for the image panel
    public void initImagePanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setVisible(true);
    }
}
