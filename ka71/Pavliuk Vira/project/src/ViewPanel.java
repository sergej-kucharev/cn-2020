import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;


public class ViewPanel extends JPanel {

    private BufferedImage argb = null;
    final private JFileChooser fc = new JFileChooser();

    public void readImage() throws IOException {
        BufferedImage image = null;
        File file = new File(Paths.get(".").toAbsolutePath().normalize().toString() + "\\src\\tempImage.jpg");
        try {
            image = ImageIO.read(file);
        } catch(Exception ex) {}

        if(image != null) {
            int width = image.getWidth();
            int height = image.getHeight();
            argb = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2 = argb.createGraphics();
            g2.drawImage(image, null, 0, 0);

            image = null;

            this.setPreferredSize(new Dimension(width,height));

            repaint();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(argb != null) {
            Graphics2D g2 = (Graphics2D)g;
            g2.drawImage(argb, null, 0, 0);
            //g2.dispose();
        }
    }

}