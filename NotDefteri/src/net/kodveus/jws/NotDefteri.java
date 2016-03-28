/*
 * tekrei tarafindan Apr 7, 2006 tarihinde yaratilmistir.
 */
package net.kodveus.jws;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;


public class NotDefteri extends JFrame {
    private static final long serialVersionUID = 1L;
    private JMenuBar jmnMenu = null;
    private JTextArea txtDosya = null;
    private File seciliDosya = null;
    private JFileChooser dosyaSecici;

    public NotDefteri() {
        super();
        initialize();
        this.setVisible(true);
    }

    private void initialize() {
        dosyaSeciciIlkleme();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setName("frmNotDefteri");
        this.setContentPane(getTxtDosya());
        this.setJMenuBar(getJmnMenu());
        this.setTitle("Basit Not Defteri");
        this.setSize(new java.awt.Dimension(800, 600));
    }

    private void dosyaSeciciIlkleme() {
        String currentDir = new String();

        try {
            currentDir = System.getProperty("user.dir");
        } catch (SecurityException se) {
        }
        dosyaSecici = new JFileChooser(currentDir);
        dosyaSecici.setFileFilter(new FileNameExtensionFilter("Metin Dosyası (TXT)", new String[] {"TXT"}));
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
        jmi.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    yeni();
                }
            });

        jmFile.add(jmi);

        jmi = new JMenuItem("Aç");
        jmi.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        ac();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });

        jmFile.add(jmi);

        jmi = new JMenuItem("Kaydet");
        jmi.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        kaydet();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });

        jmFile.add(jmi);

        jmi = new JMenuItem("Çıkış");
        jmi.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });

        jmFile.add(jmi);

        return jmFile;
    }

    void ac() throws IOException {
        int ret = dosyaSecici.showDialog(null, "Aç");

        if (ret == JFileChooser.APPROVE_OPTION) {
            seciliDosya = dosyaSecici.getSelectedFile();

            //Dosyayi ekrana yukleyelim
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                        new FileInputStream(seciliDosya)));

            String line = "";
            String hepsi = "";

            while ((line = bufferedReader.readLine()) != null) {
                hepsi+=line+"\n";
            }
            
            txtDosya.setText(hepsi);

            bufferedReader.close();
        }
    }

    void kaydet() throws IOException {
        if (seciliDosya == null) {
            //Once kaydedilecek dosya bilgisini alalim
            int ret = dosyaSecici.showDialog(null, "Kaydet");

            if (ret == JFileChooser.APPROVE_OPTION) {
                seciliDosya = dosyaSecici.getSelectedFile();
            }
        }

        //Daha sonra seciliDosya uzerine kayit yapalim
        String dosya = seciliDosya.getAbsolutePath();
        seciliDosya.delete();
        seciliDosya = new File(dosya);

        //Dolduralim
        FileOutputStream fos = new FileOutputStream(seciliDosya);
        fos.write(txtDosya.getText().getBytes());
        fos.close();
    }

    void yeni() {
        seciliDosya = null;
        txtDosya.setText("");
    }

    private JScrollPane getTxtDosya() {
        if (txtDosya == null) {
            txtDosya = new JTextArea();
        }

        return new JScrollPane(txtDosya);
    }

    public static void main(String[] args) {
        new NotDefteri();
    }
}