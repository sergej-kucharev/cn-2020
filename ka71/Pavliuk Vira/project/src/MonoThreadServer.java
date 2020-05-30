import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.file.Paths;

public class MonoThreadServer implements Runnable {

    private static Socket clientDialog;
    private static String play_word;
    static DataInputStream in;
    static DataOutputStream out;

    public MonoThreadServer(Socket client, String word) {
        clientDialog = client;
        play_word = word;
    }

    public static BufferedImage get_image() throws IOException {
        byte[] sizeAr = new byte[4];
        in.read(sizeAr);
        int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
        byte[] imageAr = new byte[size];
        in.read(imageAr);
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
        return image;
    }

    public static void send_image(BufferedImage image) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);
        byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
        out.write(size);
        out.write(byteArrayOutputStream.toByteArray());
        out.flush();
    }

    public static String get_word() throws IOException {
        String entry = in.readUTF();
        System.out.println("Answer: " + entry);
        return  entry;
    }

    @Override
    public void run() {

        try {
            out = new DataOutputStream(clientDialog.getOutputStream());
            in = new DataInputStream(clientDialog.getInputStream());

            while (!clientDialog.isClosed()) {
                //BufferedImage entry = get_image();
                BufferedImage entry = null;
                try {
                    String imagePath = Paths.get(".").toAbsolutePath().normalize().toString() + "\\src\\tempImage.jpg";
                    entry = ImageIO.read(new File(imagePath));
                }
                catch (Exception ex)
                {}

                if (entry != null) {
                    send_image(entry);

                    String word = get_word();
                    if (word.equalsIgnoreCase(play_word)) {
                        JOptionPane.showMessageDialog(null, "Success!");
                        out.writeUTF("success");
                        out.flush();
                        break;
                    }

                    if (word.equalsIgnoreCase("time-off")) {
                        JOptionPane.showMessageDialog(null, "Time off");
                        break;
                    }
                    out.flush();
                }
            }
            in.close();
            out.close();

            clientDialog.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
