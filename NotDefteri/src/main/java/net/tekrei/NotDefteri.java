/*
 * tekrei tarafindan Apr 7, 2006 tarihinde yaratilmistir.
 */
package net.tekrei;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;


public class NotDefteri extends JFrame {
    private static final long serialVersionUID = 1L;
    private JMenuBar jmnMenu = null;
    private JTextArea txtDosya = null;
    private File seciliDosya = null;
    private JFileChooser dosyaSecici;

    private NotDefteri() {
        super();
        initialize();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new NotDefteri();
    }

    private void initialize() {
        dosyaSeciciIlkleme();

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setName("frmNotDefteri");
        this.setContentPane(getTxtDosya());
        this.setJMenuBar(getJmnMenu());
        this.setTitle("Basit Not Defteri");
        this.setSize(new java.awt.Dimension(800, 600));
    }

    private void dosyaSeciciIlkleme() {
        String currentDir = "";

        try {
            currentDir = System.getProperty("user.dir");
        } catch (SecurityException se) {
            se.printStackTrace();
        }
        dosyaSecici = new JFileChooser(currentDir);
        dosyaSecici.setFileFilter(new FileNameExtensionFilter("Metin Dosyası (TXT)", "TXT"));
    }

    private JMenuBar getJmnMenu() {
        if (jmnMenu == null) {
            jmnMenu = new JMenuBar();
            jmnMenu.add(dosyaMenuOlustur());
        }

        return jmnMenu;
    }

    private JMenu dosyaMenuOlustur() {
        JMenu jmFile = new JMenu("Dosya");

        JMenuItem jmi = new JMenuItem("Yeni");
        jmi.addActionListener(e -> yeni());

        jmFile.add(jmi);

        jmi = new JMenuItem("Aç");
        jmi.addActionListener(e -> {
            try {
                ac();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        jmFile.add(jmi);

        jmi = new JMenuItem("Kaydet");
        jmi.addActionListener(e -> {
            try {
                kaydet();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        jmFile.add(jmi);

        jmi = new JMenuItem("Çıkış");
        jmi.addActionListener(e -> System.exit(0));

        jmFile.add(jmi);

        return jmFile;
    }

    private void ac() throws IOException {
        int ret = dosyaSecici.showDialog(null, "Aç");

        if (ret == JFileChooser.APPROVE_OPTION) {
            seciliDosya = dosyaSecici.getSelectedFile();

            //Dosyayi ekrana yukleyelim
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(seciliDosya)));

            StringBuilder hepsi = new StringBuilder();


            String line;
            while ((line = bufferedReader.readLine()) != null) {
                hepsi.append(line).append("\n");
            }

            txtDosya.setText(hepsi.toString());

            bufferedReader.close();
        }
    }

    private void kaydet() throws IOException {
        if (seciliDosya == null) {
            //Once kaydedilecek dosya bilgisini alalim
            int ret = dosyaSecici.showDialog(null, "Kaydet");

            if (ret == JFileChooser.APPROVE_OPTION) {
                seciliDosya = dosyaSecici.getSelectedFile();
            }
        }

        //Daha sonra seciliDosya uzerine kayit yapalim
        String dosya = seciliDosya.getAbsolutePath();
        if(seciliDosya.delete()) {
            seciliDosya = new File(dosya);

            //Dolduralim
            FileOutputStream fos = new FileOutputStream(seciliDosya);
            fos.write(txtDosya.getText().getBytes());
            fos.close();
        }
    }

    private void yeni() {
        seciliDosya = null;
        txtDosya.setText("");
    }

    private JScrollPane getTxtDosya() {
        if (txtDosya == null) {
            txtDosya = new JTextArea();
        }

        return new JScrollPane(txtDosya);
    }
}