package chat;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Bu sinif sunucu panelin olusturup tabpanede ilgili alanlarin yerlesmesini ve
 * sunucu islemlerinin gerceklestirilmesini saglar
 */
class SunucuPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTextField txtPortNumarasi = null;

    private JButton btnBaglan = null;

    private JButton btnKop = null;

    private JScrollPane scrPane = null;

    private JTextArea txtMesajlar = null;

    // Sunucunun dinleme isini surdurecek olan thread
    private SunucuThread _thread = null;

    SunucuPanel() {
        super();
        // Arayuzu olusturalim
        initialize();
    }

    private void initialize() {
        JLabel lblPortNumarasi = new JLabel();
        lblPortNumarasi.setBounds(new java.awt.Rectangle(15, 15, 171, 31));
        lblPortNumarasi.setText("Port Numarası:");
        this.setLayout(null);
        this.setBounds(new java.awt.Rectangle(0, 0, 300, 300));
        this.add(lblPortNumarasi, null);
        this.add(getTxtPortNumarasi(), null);
        this.add(getBtnBaglan(), null);
        this.add(getBtnKop(), null);

        this.add(getTxtMesajlar(), null);
    }

    private JTextField getTxtPortNumarasi() {
        if (txtPortNumarasi == null) {
            txtPortNumarasi = new JTextField();
            txtPortNumarasi.setBounds(new java.awt.Rectangle(195, 15, 91, 31));
        }
        return txtPortNumarasi;
    }

    private JScrollPane getTxtMesajlar() {
        if (scrPane == null) {
            txtMesajlar = new JTextArea();
            scrPane = new JScrollPane(txtMesajlar);
            scrPane.setBounds(new java.awt.Rectangle(15, 105, 271, 181));
        }
        return scrPane;
    }

    private JButton getBtnBaglan() {
        if (btnBaglan == null) {
            btnBaglan = new JButton();
            btnBaglan.setBounds(new java.awt.Rectangle(15, 60, 121, 31));
            btnBaglan.setText("BAĞLAN");
            // Alt tusuyla beraber b tusuna basilinca eylem olussun
            btnBaglan.setMnemonic('b');
            // Tiklamada, Alt+b'de enterda olusacak eylem
            btnBaglan.addActionListener(e -> {
                try {
                    // Girilen port numarasi bos degil ve sayi
                    // ise baglansin
                    baglan(Integer.parseInt(txtPortNumarasi.getText()));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Lütfen port numarasını kontrol ediniz!");
                }
            });
        }
        return btnBaglan;
    }

    private JButton getBtnKop() {
        if (btnKop == null) {
            btnKop = new JButton("KOPAR");
            btnKop.setBounds(new java.awt.Rectangle(180, 60, 106, 31));
            // Alt+K kombinasyonunda eylem olussun
            btnKop.setMnemonic('k');
            // Eylemi ayarlayalim
            btnKop.addActionListener(e -> {
                // dugmeye basilinca baglantiyi kopar
                kopar();
            });
        }
        return btnKop;
    }

    private void baglan(int port) {
        try {
            _thread = new SunucuThread(port);
            _thread.execute();
            this.updateUI();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Bir hata oluştu:"
                    + e.getMessage());
        }
    }

    private void kopar() {
        // While dongusunu kirmaliyiz
        if (_thread != null) {
            _thread.durdur();
            _thread = null;
        }
    }

    private void mesajEkle(String mesaj) {
        if (mesaj != null && !mesaj.equals("")) {
            txtMesajlar.setText(mesaj + "\n" + txtMesajlar.getText());
        }
    }

    /**
     * Bu thread sinifi Swing'teki thread problemleri nedeniyle SwingWorker
     * sinifindan turetilmistir. Boylece Swing+Thread calismasi duzelmistir.
     */
    class SunucuThread extends SwingWorker<String, Object> {

        private final int _port;

        private boolean calis = true;

        private ServerSocket server = null;

        private SunucuThread(int port) {
            _port = port;
        }

        public String doInBackground() {
            try {
                mesajEkle("Sunucu başladı!");
                // Sunucu soketini yarat
                server = new ServerSocket(_port);

                // calis dogru oldukca calisacak
                while (calis) {
                    // Beklemeye basliyor
                    mesajEkle("İstemciler bekleniyor!");
                    // Soketimiz istemci dinlemeye basladi
                    Socket client = server.accept();
                    // Bir istemci gelince chat kotarici devreye giriyor
                    mesajEkle(client.getInetAddress()
                            + " adresinden istemci kabul edildi!");
                    ChatHandler c = new ChatHandler(client);
                    // ve chat islerini kotarmaya basliyor
                    c.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        void durdur() {
            // thread durdurulacak
            // calismayi durduralim
            calis = false;
            try {
                // soketi kapatalim
                server.close();
                mesajEkle("Sunucu durdu!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
