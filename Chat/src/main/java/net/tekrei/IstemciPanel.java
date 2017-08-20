package net.tekrei;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

/**
 * Bu sinif istemci panelini olusturup tabpanede ilgili sahalarin yerlesmesini
 * istemci islemlerinin gerceklestirilmesini saglar
 */
class IstemciPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTextField txtPortNumarasi = null;

    private JButton btnBaglan = null;

    private JButton btnKopar = null;

    private JTextField txtMesaj = null;

    private JButton btnGonder = null;

    private JTextArea txtMesajlar = null;

    private JScrollPane scrMesajlar = null;

    private JTextField txtSunucuAdresi = null;

    // Gelen mesajlari bu akimdan okuyoruz
    private DataInputStream dis;

    // Gidecek mesajlari bu akima yaziyoruz
    private DataOutputStream dos;

    // Istemcinin baglanmasiyla acilan soket
    private Socket s;

    // Sunucudan gelen mesajlari surekli dinlemek icin bu threade ihtiyacimiz
    // var
    private MesajDinleyici mesajDinleyici;

    IstemciPanel() {
        super();
        // Arayuzu olusturalim
        initialize();
    }

    private void initialize() {
        JLabel lblHost = new JLabel();
        lblHost.setBounds(new java.awt.Rectangle(15, 15, 166, 30));
        lblHost.setText("Sunucu Adresi:");
        JLabel lblPortNumarasi = new JLabel();
        lblPortNumarasi.setBounds(new java.awt.Rectangle(15, 45, 166, 31));
        lblPortNumarasi.setText("Port Numarası:");
        this.setLayout(null);
        this.setBounds(new java.awt.Rectangle(0, 0, 400, 400));
        this.add(lblPortNumarasi, null);
        this.add(getTxtPortNumarasi(), null);
        this.add(getBtnBaglan(), null);
        this.add(getBtnKopar(), null);
        this.add(getTxtMesaj(), null);
        this.add(getBtnGonder(), null);
        this.add(getTxtMesajlar(), null);

        this.add(lblHost, null);
        this.add(getTxtSunucuAdresi(), null);
    }

    private JTextField getTxtPortNumarasi() {
        if (txtPortNumarasi == null) {
            txtPortNumarasi = new JTextField();
            txtPortNumarasi.setBounds(new java.awt.Rectangle(195, 45, 121, 31));
        }
        return txtPortNumarasi;
    }

    private JButton getBtnBaglan() {
        if (btnBaglan == null) {
            btnBaglan = new JButton();
            btnBaglan.setBounds(new java.awt.Rectangle(45, 90, 136, 31));
            btnBaglan.setMnemonic(java.awt.event.KeyEvent.VK_B);
            btnBaglan.setText("BAĞLAN");
            btnBaglan.addActionListener(e -> {
                try {
                    // Sunucu adresini ve port numarasini kullanarak
                    // baglanalim
                    baglan(txtSunucuAdresi.getText(), Integer
                            .parseInt(txtPortNumarasi.getText()));
                } catch (Exception ex) {
                    JOptionPane
                            .showMessageDialog(null,
                                    "Lütfen port numarası ve adresi kontrol ediniz!");
                    ex.printStackTrace();
                }
            });
        }
        return btnBaglan;
    }

    private JButton getBtnKopar() {
        if (btnKopar == null) {
            btnKopar = new JButton();
            btnKopar.setBounds(new java.awt.Rectangle(210, 90, 136, 31));
            btnKopar.setActionCommand("KOPAR");
            btnKopar.setMnemonic(java.awt.event.KeyEvent.VK_K);
            btnKopar.setText("KOPAR");
            btnKopar.addActionListener(e -> {
                // Var olan baglanti koparilacak
                kopar();
            });
        }
        return btnKopar;
    }

    private JTextField getTxtSunucuAdresi() {
        if (txtSunucuAdresi == null) {
            txtSunucuAdresi = new JTextField();
            txtSunucuAdresi.setBounds(new java.awt.Rectangle(195, 15, 195, 29));
        }
        return txtSunucuAdresi;
    }

    private JTextField getTxtMesaj() {
        if (txtMesaj == null) {
            txtMesaj = new JTextField();
            txtMesaj.setBounds(new java.awt.Rectangle(15, 135, 287, 31));
        }
        return txtMesaj;
    }

    private JScrollPane getTxtMesajlar() {
        if (scrMesajlar == null) {
            txtMesajlar = new JTextArea();
            scrMesajlar = new JScrollPane(txtMesajlar);
            scrMesajlar.setBounds(new java.awt.Rectangle(15, 174, 376, 217));
        }
        return scrMesajlar;
    }

    private JButton getBtnGonder() {
        if (btnGonder == null) {
            btnGonder = new JButton();
            btnGonder.setBounds(new java.awt.Rectangle(300, 135, 91, 31));
            btnGonder.setMnemonic(java.awt.event.KeyEvent.VK_G);
            btnGonder.setText("GÖNDER");
            btnGonder.addActionListener(e -> {
                // Kullanicinin girdigi mesaj sunucuya gonderilsin
                gonder();
            });
            btnGonder.setEnabled(false);
        }
        return btnGonder;
    }

    /**
     * Bu metod sunucuya baglanmada kullanilmaktadir parametre olarak sunucu
     * adresi ve port numarasini almaktadir baglan dugmesine basilinca
     * calismaktadir
     */
    private void baglan(String sunucu, int port) {
        // Eger bir baglanti yoksa
        if (s == null) {
            try {
                // Yeni bir soket baglantisi ac
                s = new Socket(sunucu, port);
                // Okuyacagimiz akimi olustur
                this.dis = new DataInputStream(new BufferedInputStream(s
                        .getInputStream()));
                // Yazacagimiz akimi olustur
                this.dos = new DataOutputStream(new BufferedOutputStream(s
                        .getOutputStream()));
                // Mesaj kutusuna odaklan
                txtMesaj.requestFocus();
                // Mesaj dinlemeye baslamak icin
                // threadi yarat
                mesajDinleyici = new MesajDinleyici();
                // baglanti oldugu icin gonderme dugmesini aktifle
                btnGonder.setEnabled(true);

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Bir hata oluştu:"
                        + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Bu metod eger aktif bir baglanti varsa onu kapatmamizi sagliyor kopar
     * dugmesine basilinca cagrilmaktadir
     */
    private void kopar() {
        // Eger soket aciksa
        if (s != null) {
            try {
                // yazilan akimi kapat
                dos.close();
                // okunan akimi kapat
                dis.close();
                // soketi kapat
                s.close();
                // mesaj dinleyici threadini durdur
                if (mesajDinleyici != null) {
                    mesajDinleyici.durdur();
                }
                // baglanti kapandigi icin gonderme dugmesini disable et
                mesajDinleyici = null;
                s = null;
                btnGonder.setEnabled(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Bu metod kullanicinin girdigi mesaji sunucuya gondermektedir
     */
    private void gonder() {
        try {
            // Metin kutusuna yazilan mesaji yazma akimina yaz
            dos.writeUTF(txtMesaj.getText());
            // yazma akimini bosalt
            dos.flush();
            // mesaj kutusunu bosalt
            txtMesaj.setText("");
            // mesaj kutusuna odaklan
            txtMesaj.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Bu sinif sunucudan gelen mesajlari baglanti oldugu surece dinleyen thread
     * sinifidir
     */
    class MesajDinleyici extends Thread {
        // baglanti kapanirsa calismasi duracak
        private boolean calis = true;

        MesajDinleyici() {
            // calismaya yaratilir yaratilmaz basla
            this.start();
        }

        /**
         * Bu metod asil calisan kisimdir, thread'ten gelmistir doldurulmasi
         * gerekmektedir
         */
        public void run() {
            try {
                // calis dogru oldukca calismaya devam et
                while (calis) {
                    // okuma akimindan veri oku
                    String line = dis.readUTF();
                    // bu okunan veriyi mesaj alanina ekle
                    txtMesajlar.setText(line + "\n" + txtMesajlar.getText());
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        void durdur() {
            // calismayi durdur
            calis = false;
        }
    }
}
