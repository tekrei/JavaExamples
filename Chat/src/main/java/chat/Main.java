package chat;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel jContentPane = null;

    private Main() {
        super();
        initialize();
    }

    public static void main(String[] args) {
        new Main();
    }

    private void initialize() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(420, 450);
        this.setContentPane(getJContentPane());
        this.setTitle("Chat Programi");
        // Ekranda pencereyi gosterelim
        this.setVisible(true);
    }

    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            // Layout ayarlayalim
            jContentPane.setLayout(new BorderLayout());
            // Pencereye sunucu ve istemciyi ayri ayri gostermek icin
            // tab ekleyelim
            jContentPane.add(getTabbedPane());
        }
        return jContentPane;
    }

    private JTabbedPane getTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();
        // Sunucu paneli ekleyelim
        tabbedPane.add("Sunucu", new ServerPanel());
        // Istemci paneli ekleyelim
        tabbedPane.add("İstemci", new ClientPanel());
        return tabbedPane;
    }

}
