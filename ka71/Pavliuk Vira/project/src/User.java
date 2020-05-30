import java.awt.image.BufferedImage;

public class User {
    public static String name;
    static String word_to_paint;
    public String last_word;
    public static int score;

    public User(String name) {
        User.name = name;
        score = 0;
        word_to_paint = "";
        this.last_word = "";
    }

    public void set_word(String myword) {
        word_to_paint = myword;
    }

    public static String get_word() {
        return word_to_paint;
    }

    public BufferedImage draw_word() {
        Paint painter = new Paint();
        return painter.make_image();
    }
}
