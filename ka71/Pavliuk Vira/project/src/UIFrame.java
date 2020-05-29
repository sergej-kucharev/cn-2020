import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class UIFrame extends JFrame {
    ImageIcon img = new ImageIcon(ImageIO.read(new File(Paths.get(".").toAbsolutePath().normalize().toString() + "\\src\\pic.png")));

    JLabel background = new JLabel(img);
    UIFrame(String s) throws IOException {
        new JFrame(s);
        setContentPane(background);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
        setSize(500, 500);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    public void close()
    {
        setVisible(false);
        getContentPane().setVisible(false);
    }
}
