package notepad;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;


public class Main extends JFrame {
    private static final long serialVersionUID = 1L;
    private JMenuBar jmnMenu = null;
    private JTextArea txtFile = null;
    private File selectedFile = null;
    private JFileChooser fileChooser;

    private Main() {
        super();
        initialize();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }

    private void initialize() {
        initializeFileChooser();

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setName("frmNotepad");
        this.setContentPane(getTxtFile());
        this.setJMenuBar(getJmnMenu());
        this.setTitle("A Simple Notepad");
        this.setSize(new java.awt.Dimension(800, 600));
    }

    private void initializeFileChooser() {
        String currentDir = "";

        try {
            currentDir = System.getProperty("user.dir");
        } catch (SecurityException se) {
            se.printStackTrace();
        }
        fileChooser = new JFileChooser(currentDir);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text File (TXT)", "TXT"));
    }

    private JMenuBar getJmnMenu() {
        if (jmnMenu == null) {
            jmnMenu = new JMenuBar();
            jmnMenu.add(dosyaMenuOlustur());
        }

        return jmnMenu;
    }

    private JMenu dosyaMenuOlustur() {
        JMenu jmFile = new JMenu("File");

        JMenuItem jmi = new JMenuItem("New");
        jmi.addActionListener(e -> newFile());

        jmFile.add(jmi);

        jmi = new JMenuItem("Open");
        jmi.addActionListener(e -> {
            try {
                ac();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        jmFile.add(jmi);

        jmi = new JMenuItem("Save");
        jmi.addActionListener(e -> {
            try {
                save();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        jmFile.add(jmi);

        jmi = new JMenuItem("Exit");
        jmi.addActionListener(e -> System.exit(0));

        jmFile.add(jmi);

        return jmFile;
    }

    private void ac() throws IOException {
        int ret = fileChooser.showDialog(null, "Open");

        if (ret == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();

            //Dosyayi ekrana yukleyelim
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(selectedFile)));

            StringBuilder content = new StringBuilder();


            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }

            txtFile.setText(content.toString());

            bufferedReader.close();
        }
    }

    private void save() throws IOException {
        if (selectedFile == null) {
            //Once kaydedilecek dosya bilgisini alalim
            int ret = fileChooser.showDialog(null, "Save");

            if (ret == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
            }
        }

        //Daha sonra selectedFile uzerine kayit yapalim
        String dosya = selectedFile.getAbsolutePath();
        if (selectedFile.delete()) {
            selectedFile = new File(dosya);

            //Dolduralim
            FileOutputStream fos = new FileOutputStream(selectedFile);
            fos.write(txtFile.getText().getBytes());
            fos.close();
        }
    }

    private void newFile() {
        selectedFile = null;
        txtFile.setText("");
    }

    private JScrollPane getTxtFile() {
        if (txtFile == null) {
            txtFile = new JTextArea();
        }

        return new JScrollPane(txtFile);
    }
}