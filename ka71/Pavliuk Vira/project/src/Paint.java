import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.nio.file.Paths;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Paint {
    int mode = 0;
    int xPad;
    int yPad;
    int xf;
    int yf;
    int thickness;
    boolean pressed = false;
    Color maincolor;
    Paint.MyFrame f = new Paint.MyFrame("Place for painting");
    Paint.MyPanel japan;
    JButton colorbutton;
    JColorChooser tcc;
    BufferedImage imag;

    public Paint() {
        this.f.setSize(400, 400);
        this.f.setResizable(false);
        this.f.setDefaultCloseOperation(3);
        this.maincolor = Color.black;
        this.japan = new Paint.MyPanel();
        this.japan.setBounds(30, 30, 660, 660);
        this.japan.setBackground(Color.white);
        this.japan.setOpaque(true);
        this.f.add(this.japan);
        JToolBar toolbar = new JToolBar("Toolbar", 1);
        JButton penbutton = new JButton(new ImageIcon(Paths.get(".").toAbsolutePath().normalize().toString() + "\\src\\images\\pen.png"));
        penbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Paint.this.mode = 0;
            }
        });
        toolbar.add(penbutton);
        JButton brushbutton = new JButton(new ImageIcon(Paths.get(".").toAbsolutePath().normalize().toString() + "\\src\\images\\brush.png"));
        brushbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Paint.this.mode = 1;
            }
        });
        toolbar.add(brushbutton);
        JButton lasticbutton = new JButton(new ImageIcon(Paths.get(".").toAbsolutePath().normalize().toString() + "\\src\\images\\lastic.png"));
        lasticbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Paint.this.mode = 2;
            }
        });
        toolbar.add(lasticbutton);
        JButton linebutton = new JButton(new ImageIcon(Paths.get(".").toAbsolutePath().normalize().toString() + "\\src\\images\\line.png"));
        linebutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Paint.this.mode = 3;
            }
        });
        toolbar.add(linebutton);
        JButton elipsbutton = new JButton(new ImageIcon(Paths.get(".").toAbsolutePath().normalize().toString() + "\\src\\images\\elips.png"));
        elipsbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Paint.this.mode = 4;
            }
        });
        toolbar.add(elipsbutton);
        JButton rectbutton = new JButton(new ImageIcon(Paths.get(".").toAbsolutePath().normalize().toString() + "\\src\\images\\rect.png"));
        rectbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Paint.this.mode = 5;
            }
        });
        toolbar.add(rectbutton);
        toolbar.setBounds(0, 0, 40, 700);
        this.f.add(toolbar);
        JToolBar colorbar = new JToolBar("Colorbar", 0);
        colorbar.setBounds(40, 0, 660, 40);
        this.colorbutton = new JButton();
        this.colorbutton.setBackground(this.maincolor);
        this.colorbutton.setBounds(15, 5, 20, 20);
        this.colorbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Paint.ColorDialog coldi = Paint.this.new ColorDialog(Paint.this.f, "Choose color");
                coldi.setVisible(true);
            }
        });
        colorbar.add(this.colorbutton);
        JButton redbutton = new JButton();
        redbutton.setBackground(Color.red);
        redbutton.setBounds(40, 5, 15, 15);
        redbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Paint.this.maincolor = Color.red;
                Paint.this.colorbutton.setBackground(Paint.this.maincolor);
            }
        });
        colorbar.add(redbutton);
        JButton orangebutton = new JButton();
        orangebutton.setBackground(Color.orange);
        orangebutton.setBounds(60, 5, 15, 15);
        orangebutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Paint.this.maincolor = Color.orange;
                Paint.this.colorbutton.setBackground(Paint.this.maincolor);
            }
        });
        colorbar.add(orangebutton);
        JButton yellowbutton = new JButton();
        yellowbutton.setBackground(Color.yellow);
        yellowbutton.setBounds(80, 5, 15, 15);
        yellowbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Paint.this.maincolor = Color.yellow;
                Paint.this.colorbutton.setBackground(Paint.this.maincolor);
            }
        });
        colorbar.add(yellowbutton);
        JButton greenbutton = new JButton();
        greenbutton.setBackground(Color.green);
        greenbutton.setBounds(100, 5, 15, 15);
        greenbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Paint.this.maincolor = Color.green;
                Paint.this.colorbutton.setBackground(Paint.this.maincolor);
            }
        });
        colorbar.add(greenbutton);
        JButton bluebutton = new JButton();
        bluebutton.setBackground(Color.blue);
        bluebutton.setBounds(120, 5, 15, 15);
        bluebutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Paint.this.maincolor = Color.blue;
                Paint.this.colorbutton.setBackground(Paint.this.maincolor);
            }
        });
        colorbar.add(bluebutton);
        JButton cyanbutton = new JButton();
        cyanbutton.setBackground(Color.cyan);
        cyanbutton.setBounds(140, 5, 15, 15);
        cyanbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Paint.this.maincolor = Color.cyan;
                Paint.this.colorbutton.setBackground(Paint.this.maincolor);
            }
        });
        colorbar.add(cyanbutton);
        JButton magentabutton = new JButton();
        magentabutton.setBackground(Color.magenta);
        magentabutton.setBounds(160, 5, 15, 15);
        magentabutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Paint.this.maincolor = Color.magenta;
                Paint.this.colorbutton.setBackground(Paint.this.maincolor);
            }
        });
        colorbar.add(magentabutton);
        JButton whitebutton = new JButton();
        whitebutton.setBackground(Color.white);
        whitebutton.setBounds(180, 5, 15, 15);
        whitebutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Paint.this.maincolor = Color.white;
                Paint.this.colorbutton.setBackground(Paint.this.maincolor);
            }
        });
        colorbar.add(whitebutton);
        JButton blackbutton = new JButton();
        blackbutton.setBackground(Color.black);
        blackbutton.setBounds(200, 5, 15, 15);
        blackbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Paint.this.maincolor = Color.black;
                Paint.this.colorbutton.setBackground(Paint.this.maincolor);
            }
        });
        colorbar.add(blackbutton);
        colorbar.setLayout((LayoutManager)null);
        this.f.add(colorbar);
        this.tcc = new JColorChooser(this.maincolor);
        this.tcc.getSelectionModel().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                Paint.this.maincolor = Paint.this.tcc.getColor();
                Paint.this.colorbutton.setBackground(Paint.this.maincolor);
            }
        });
        this.japan.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (Paint.this.pressed) {
                    Graphics g = Paint.this.imag.getGraphics();
                    Graphics2D g2 = (Graphics2D)g;
                    g2.setColor(Paint.this.maincolor);
                    switch(Paint.this.mode) {
                        case 0:
                            g2.drawLine(Paint.this.xPad, Paint.this.yPad, e.getX(), e.getY());
                            break;
                        case 1:
                            g2.setStroke(new BasicStroke(3.0F));
                            g2.drawLine(Paint.this.xPad, Paint.this.yPad, e.getX(), e.getY());
                            break;
                        case 2:
                            g2.setStroke(new BasicStroke(3.0F));
                            g2.setColor(Color.WHITE);
                            g2.drawLine(Paint.this.xPad, Paint.this.yPad, e.getX(), e.getY());
                    }

                    Paint.this.xPad = e.getX();
                    Paint.this.yPad = e.getY();
                }

                Paint.this.japan.repaint();
            }
        });
        this.japan.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Graphics g = Paint.this.imag.getGraphics();
                Graphics2D g2 = (Graphics2D)g;
                g2.setColor(Paint.this.maincolor);
                switch(Paint.this.mode) {
                    case 0:
                        g2.drawLine(Paint.this.xPad, Paint.this.yPad, Paint.this.xPad + 1, Paint.this.yPad + 1);
                        break;
                    case 1:
                        g2.setStroke(new BasicStroke(3.0F));
                        g2.drawLine(Paint.this.xPad, Paint.this.yPad, Paint.this.xPad + 1, Paint.this.yPad + 1);
                        break;
                    case 2:
                        g2.setStroke(new BasicStroke(3.0F));
                        g2.setColor(Color.WHITE);
                        g2.drawLine(Paint.this.xPad, Paint.this.yPad, Paint.this.xPad + 1, Paint.this.yPad + 1);
                }

                Paint.this.xPad = e.getX();
                Paint.this.yPad = e.getY();
                Paint.this.pressed = true;
                Paint.this.japan.repaint();
            }

            public void mousePressed(MouseEvent e) {
                Paint.this.xPad = e.getX();
                Paint.this.yPad = e.getY();
                Paint.this.xf = e.getX();
                Paint.this.yf = e.getY();
                Paint.this.pressed = true;
            }

            public void mouseReleased(MouseEvent e) {
                Graphics g = Paint.this.imag.getGraphics();
                Graphics2D g2 = (Graphics2D)g;
                g2.setColor(Paint.this.maincolor);
                int x1 = Paint.this.xf;
                int x2 = Paint.this.xPad;
                int y1 = Paint.this.yf;
                int y2 = Paint.this.yPad;
                if (Paint.this.xf > Paint.this.xPad) {
                    x2 = Paint.this.xf;
                    x1 = Paint.this.xPad;
                }

                if (Paint.this.yf > Paint.this.yPad) {
                    y2 = Paint.this.yf;
                    y1 = Paint.this.yPad;
                }

                switch(Paint.this.mode) {
                    case 3:
                        g.drawLine(Paint.this.xf, Paint.this.yf, e.getX(), e.getY());
                        break;
                    case 4:
                        g.drawOval(x1, y1, x2 - x1, y2 - y1);
                        break;
                    case 5:
                        g.drawRect(x1, y1, x2 - x1, y2 - y1);
                }

                Paint.this.xf = 0;
                Paint.this.yf = 0;
                Paint.this.pressed = false;
                Paint.this.japan.repaint();
            }
        });
        this.f.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                Paint.this.japan.setSize(Paint.this.f.getWidth() - 40, Paint.this.f.getHeight() - 80);
                BufferedImage tempImage = new BufferedImage(Paint.this.japan.getWidth(), Paint.this.japan.getHeight(), 1);
                Graphics2D d2 = tempImage.createGraphics();
                d2.setColor(Color.white);
                d2.fillRect(0, 0, Paint.this.japan.getWidth(), Paint.this.japan.getHeight());
                tempImage.setData(Paint.this.imag.getRaster());
                Paint.this.imag = tempImage;
                Paint.this.japan.repaint();
            }
        });
        this.f.setLayout((LayoutManager)null);
        this.f.setLocationRelativeTo(null);
        this.f.setVisible(true);
    }

    public BufferedImage get_image() {
        return this.imag;
    }

    public BufferedImage make_image() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Paint();
            }
        });
        return this.imag;
    }

    class MyPanel extends JPanel {
        public MyPanel() {
        }

        public void paintComponent(Graphics g) {
            if (Paint.this.imag == null) {
                Paint.this.imag = new BufferedImage(this.getWidth(), this.getHeight(), 1);
                Graphics2D d2 = Paint.this.imag.createGraphics();
                d2.setColor(Color.white);
                d2.fillRect(0, 0, this.getWidth(), this.getHeight());
            }

            super.paintComponent(g);
            g.drawImage(Paint.this.imag, 0, 0, this);
        }
    }

    class MyFrame extends JFrame {
        public void paint(Graphics g) {
            super.paint(g);
        }

        public MyFrame(String title) {
            super(title);
        }
    }

    class ColorDialog extends JDialog {
        public ColorDialog(JFrame owner, String title) {
            super(owner, title, true);
            this.add(Paint.this.tcc);
            this.setSize(200, 200);
        }
    }
}
