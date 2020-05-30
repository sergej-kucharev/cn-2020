import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetAddress;
import java.nio.file.Paths;
import java.util.List;
import java.util.Timer;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game {
    MultiThreadServer server;
    public static List<Client> players;
    Dictionary<Integer, String> level;
    public static UIFrame mainFrame;
    public static UIFrame ruleFrame;
    public static UIFrame guessFrame;
    public static UIFrame paintFrame;
    public static UIFrame endFrame;
    public static UIFrame FinalFrame;
    public static File users;
    public int count_players = 2;
    public Paint paint;
    private ViewPanel view;
    public Client client = null;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new  Runnable() {
            public void run() {
                try {
                    new  Game();
                    System.out.println(InetAddress.getLocalHost());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public Game() throws IOException {
        // initialize global classes
        //create our main frame
        level = new Hashtable<>();
        initialize_dictionary("low.txt", level);
        //initialize_dictionary("mid.txt", mid_level);
        //initialize_dictionary("hard.txt", hard_level);
        String usersPath = Paths.get(".").toAbsolutePath().normalize().toString() + "\\src\\gameUsers.txt";
        users = new File(usersPath);
        JPanel panel = new JPanel();
        mainFrame = new UIFrame("Pencil Game");
        guessFrame = new UIFrame("Guessing Frame");
        paintFrame = new UIFrame("Paint Frame");
        endFrame = new UIFrame("End Frame");
        FinalFrame = new UIFrame("Game Over");
        ruleFrame = new UIFrame("Rules");
        final Font font = new Font("Verdana", Font.PLAIN, 14);
        JLabel lb = new JLabel("Insert Your Username");
        JTextField userName_text = new JTextField(30);
        userName_text.setAlignmentX(Component.CENTER_ALIGNMENT);
        lb.setFont(font);
        lb.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel levelLable = new JLabel("Choose level");
        levelLable.setFont(font);
        levelLable.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        JButton draw = new JButton("PAINT");
        JButton guess = new JButton("GUESS");
        JButton rules = new JButton("RULES");
        final User user = new User("guest");
        final JButton send = new JButton("Send");
        final JTextField word = new JTextField(20);
        JLabel lev = new JLabel("Choose level");
        lev.setFont(font);

        ButtonGroup buttonGroup = new ButtonGroup();
        JPanel buttonPanel = new JPanel();
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                switch (actionEvent.getActionCommand()){
                    case "LOW":{
                        level = new Hashtable<>();
                        initialize_dictionary("low.txt", level);
                    }
                    case "MID":{
                        level = new Hashtable<>();
                        initialize_dictionary("mid.txt", level);
                    }
                    case "HARD":{
                        level = new Hashtable<>();
                        initialize_dictionary("hard.txt", level);
                    }
                    default:
                }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        };

        JToggleButton  low = new JToggleButton ("LOW");
        low.addActionListener(listener);
        buttonGroup.add(low);
        buttonPanel.add(low);
        JToggleButton  mid = new JToggleButton ("MID");
        mid.addActionListener(listener);
        buttonGroup.add(mid);
        buttonPanel.add(mid);
        JToggleButton  hrd = new JToggleButton ("HARD");
        hrd.addActionListener(listener);
        buttonGroup.add(hrd);
        buttonPanel.add(hrd);

        draw.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                //JLabel pnt = new JLabel((Icon) paint.make_image());
                //pnt.setAlignmentX(Component.CENTER_ALIGNMENT);
                mainFrame.close();
                final JLabel wrd = new JLabel(level.get((int) (Math.random() * level.size())));
                wrd.setFont(font);
                //Game.paintFrame.add(new JLabel(user.get_word()));
                paintFrame.add(wrd);
                paintFrame.pack();
                paintFrame.setVisible(true);
                paint  = new Paint();

                Thread t1 = new Thread(new Runnable() {
                    public void run()
                    {
                        new MultiThreadServer(wrd.getText());
                    }});
                t1.start();

                Thread t2 = new Thread(new Runnable() {
                    public void run()
                    {
                        try {
                            while(true) {
                                Thread.sleep(2000);
                                BufferedImage image = paint.get_image();
                                String savedImagePath = Paths.get(".").toAbsolutePath().normalize().toString() + "\\src\\tempImage.jpg";
                                File outputfile = new File(savedImagePath);
                                ImageIO.write(image, "jpg", outputfile);
                            }
                        } catch (InterruptedException | IOException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                    }});
                t2.start();
            }
        });
        guess.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // paintFrame.close();
                mainFrame.close();

                JLabel txt = new JLabel("What do you think it is?");
                txt.setFont(font);
                send.setBounds(100, 100, 140, 40);
                //send.setAlignmentX(Component.RIGHT_ALIGNMENT);
                JPanel pan = new JPanel();
                //pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
                pan.add(txt);
                pan.add(word);
                pan.add(send);
                guessFrame.add(pan);

                view = new ViewPanel();
                try {
                    view.readImage();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                JScrollPane scrollPane = new JScrollPane(view);
                guessFrame.add(scrollPane, BorderLayout.CENTER);

                guessFrame.pack();

                //Game.paintFrame.add(new JLabel(user.get_word()));
                Game.guessFrame.setVisible(true);

                Thread t0 = new Thread(new Runnable() {
                    public void run()
                    {
                        try {
                            while (true) {
                                Thread.sleep(2000);
                                view.readImage();
                            }
                        } catch (InterruptedException | IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }});
                t0.start();

                Thread t1 = new Thread(new Runnable() {
                    public void run()
                    {
                        try {
                            client = new Client(new User("guest"));
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        client.run();
                    }});
                t1.start();
            }
        });

        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    client.set_word(word.getText());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        rules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.close();
                JTextArea r = new JTextArea("Hello!\n Now you have an opportunity to play game which called Pencil Game.\n " +
                        "What should you do?"+"\n\n"+"1. Choose what you want to do: paint or guess what someone is painting\n"+
                        "2. If you choose paint you will see a painting place and a word that you should paint.\n\n Your task is to paint as clear as possible. If no one guess the word you will have a penalty\n");
                r.setColumns(45);
                JPanel rl = new JPanel();
                r.setEditable(false);
                r.setLineWrap(true);
                r.setWrapStyleWord(true);
                r.setOpaque(false);
                rl.setBackground(Color.white);
                rl.add(r);
                ruleFrame.add(rl);
                ruleFrame.pack();
                ruleFrame.setVisible(true);

            }
        });
        draw.setBounds(100,100,140, 40);
        draw.setAlignmentX(Component.CENTER_ALIGNMENT);
        rules.setBounds(100,100,140, 40);
        rules.setAlignmentX(Component.CENTER_ALIGNMENT);
        guess.setBounds(100,100,140, 40);
        guess.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setBackground(new Color(135,206,235));
        panel.add(lb);
        panel.add(userName_text);
        panel.add(draw);
        panel.add(guess);
        panel.add(rules);
        panel.add(levelLable);
        panel.add(buttonPanel, BorderLayout.NORTH);


        //JPanel p = new JPanel();
        Scanner sc = new Scanner(users);
        while(sc.hasNextLine())
        {
            JLabel tmp = new JLabel(sc.nextLine());
            panel.add(tmp);
        }
        mainFrame.add(panel);
        mainFrame.pack();
        mainFrame.setVisible(true);

    }

    public void play_game(){
        ExecutorService exec = Executors.newFixedThreadPool(count_players);
        for (int counter = 0; counter < count_players; counter ++){
            exec.execute(players.get(counter));
        }
        play_round(level, 5);
        server.round ++;
        play_round(level, 10);
        server.round ++;
        play_round(level, 15);
        server.round ++;

        int max_score = players.get(0).user.score;
        String win_user = players.get(0).user.name;
        for (int counter = 0; counter < players.size(); counter++) {
            if (max_score < players.get(counter).user.score) {
                max_score = players.get(counter).user.score;
                win_user = players.get(counter).user.name;
            }
        }
        Font font = new Font("Verdana", Font.PLAIN, 14);
        JLabel win = new JLabel("Win " + win_user);
        win.setFont(font);
        endFrame.add(win);
        endFrame.pack();
        endFrame.setVisible(true);

        exec.shutdown();
    }
    private static Client authorization(String name) throws IOException {
        Scanner scan = new Scanner(new BufferedReader(new InputStreamReader(new FileInputStream(users), "Cp1251")));
        FileWriter wr = new FileWriter(users);
        //String name = "";
        if (!scan.hasNext(name)) {
            wr.write(name + "\n");
        }

        User newUser = new User(name);
        Client player = new Client(newUser);
        players.add(player);
        wr.close();
        scan.close();

        return player;
    }

    private void initialize_dictionary(String file_name, Dictionary<Integer, String> level) throws FileNotFoundException {
        try {
            String filePath = Paths.get(".").toAbsolutePath().normalize().toString() + "\\src\\Words\\" + file_name;
            File file = new File( filePath);
            Scanner scan = null;
            scan = new Scanner(new BufferedReader(new InputStreamReader(new FileInputStream(file), "Cp1251")));
            String line = scan.nextLine();
            String[] words = line.split(", ");
            int counter = 0;
            while (counter < words.length) {
                level.put(counter, words[counter]);
                counter++;
            }
            scan.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void client_dialog(final Client painter, final int points){
        final Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            int i = 60;
            public void run() {
                try {
                    if (i < 0) {
                        painter.user.score -= points / 2;
                        painter.send_word("time-off");
                        timer.cancel();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }, 0, 1000);
        painter.user.set_word("");
    }

    public void analise_results(Client painter, String play_word, int points){
        boolean status = false;
        for (int counter = 0; counter < players.size(); counter++){
            if (players.get(counter).user.last_word == play_word) {
                players.get(counter).user.score += points;
            }
        }
        if (status) painter.user.score += (int) points / 2;
    }

    public void play_round(Dictionary<Integer, String> level, int points) {

        for (int counter = 0; counter < players.size(); counter++) {
            int random_int = (int) (Math.random() * level.size());
            String play_word = level.get(random_int);
            players.get(counter).user.set_word(play_word);

            client_dialog(players.get(counter), points);
            analise_results(players.get(counter), play_word, points);
        }
    }


}
