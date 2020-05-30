import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.file.Paths;

public class Client implements Runnable {

    static Socket socket;
    static InetAddress host;
    static User user;
    static boolean online_status;
    static DataOutputStream outputStream;
    static DataInputStream inputStream;
    static BufferedReader br;
    public Client(User user) throws IOException {
        this.host = InetAddress.getLocalHost() ;
        try {
            socket = new Socket(host, 55337);
        } catch (Exception e) {
            e.printStackTrace();
        }
        outputStream = new DataOutputStream(socket.getOutputStream());
        inputStream = new DataInputStream(socket.getInputStream());

        online_status = true;
        this.user = user;
    }

    public static void send_image() throws IOException {
        // OutputStream outputStream = socket.getOutputStream();
        BufferedImage image = user.draw_word();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);
        byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
        outputStream.write(size);
        outputStream.write(byteArrayOutputStream.toByteArray());
        outputStream.flush();
    }

    public static BufferedImage get_image() throws IOException {
        byte[] sizeAr = new byte[4];
        inputStream.read(sizeAr);
        int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
        byte[] imageAr = new byte[size];
        inputStream.readFully(imageAr);
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
        return image;
    }

    public static void send_word(String word) throws IOException {
        DataOutputStream dos = new DataOutputStream(outputStream);
        //  String word = br.readLine();
        dos.writeUTF(word);
        dos.flush();
        user.last_word = word;
    }

    public static void set_word(String word) throws IOException {
        user.word_to_paint = word;
    }

    @Override
    public void run() {
        try(DataOutputStream ops = new DataOutputStream(outputStream);
            DataInputStream dis = new DataInputStream(inputStream)){
            while (!socket.isClosed()) {

               if (user.get_word() == "") {
                    try {
                        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                        BufferedImage image = get_image();
                        String savedImagePath = Paths.get(".").toAbsolutePath().normalize().toString() + "\\src\\tempImage.jpg";
                        File outputfile = new File(savedImagePath);
                        ImageIO.write(image, "jpg", outputfile);
                    } catch (Exception ex)
                    {}
                    String word = "";
                    ops.writeUTF(word);
                    ops.flush();
                } else {
                    String word = user.get_word();
                    ops.writeUTF(word);
                    ops.flush();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}