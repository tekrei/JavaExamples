package db2pojo;

import javax.swing.*;
import java.awt.*;

public class Db2Pojo extends JFrame {

    private static final long serialVersionUID = 1L;

    private Db2Pojo() {
        super();
        initialize();
    }

    public static void main(String[] args) {
        new Db2Pojo();
    }

    private void initialize() {
        this.setSize(new Dimension(800, 600));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("DB2POJO");
        this.setContentPane(new Db2PojoPanel());
        this.setVisible(true);
    }

}
