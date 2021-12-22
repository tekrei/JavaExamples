/*
 * BirIslem Java ile yazilmis ve Genetik Algoritma kullanimini ornekleme
 * amaci guden bir ozgur yazilimdir. 
 * Copyright (C) 2007
 *  
 * BirIslem is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package birislem;

import birislem.araclar.SayiArac;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;

/**
 * Bu sinifin temel goreve uygulamayi baslatmak ve ilgili grafiksel arayuz
 * bilesenlerini hazirlayip yuklemek ayrica kullanici etkilesimini saglayarak
 * genetik algoritmanin tetiklenmesi gorevini de gerceklestirmektedir.
 */
public class BirIslemApp extends JFrame {

    // Hesaplamada kullanilacak olan sayilar
    private JFormattedTextField txtSayi1;
    private JFormattedTextField txtSayi2;
    private JFormattedTextField txtSayi3;
    private JFormattedTextField txtSayi4;
    private JFormattedTextField txtSayi5;
    private JFormattedTextField txtSayi6;
    // Ulasilmasi gereken hedef sayi
    private JFormattedTextField txtHedefSayi;
    // Cozumun yazilacagi etiket
    private JLabel lblCozum;

    /**
     * yapici metot
     */
    private BirIslemApp() {
        super();
        initialize();
    }

    /**
     * Uygulamayi baslatan ana metot
     */
    public static void main(String[] args) {
        new BirIslemApp().setVisible(true);
    }

    /**
     * Grafiksel arayuz bilesenlerini olusturup pencereye ekleme isini yapan
     * metot
     */
    private void initialize() {
        this.setSize(395, 110);
        this.setTitle("Bir İşlem");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.getContentPane().setLayout(null);

        // Sayilar arayuze ekleniyor
        this.getContentPane().add(
                createLabel("SAYILAR:", new Rectangle(0, 0, 100, 25)));
        try {
            // Sadece iki basamak kabul eden bicimlendirici
            MaskFormatter ikiBasamak = new MaskFormatter("##");
            // Sadece uc basamak kabul eden bicimlendirici
            MaskFormatter ucBasamak = new MaskFormatter("###");

            // Metin alanlari bicimli metin alani olarak yaratiliyor
            // veri giris hatalarini onlemeye calisiyoruz
            txtSayi1 = new JFormattedTextField(ikiBasamak);
            txtSayi1.setBounds(new Rectangle(100, 0, 30, 25));
            this.getContentPane().add(txtSayi1);
            txtSayi2 = new JFormattedTextField(ikiBasamak);
            txtSayi2.setBounds(new Rectangle(130, 0, 30, 25));
            this.getContentPane().add(txtSayi2);
            txtSayi3 = new JFormattedTextField(ikiBasamak);
            txtSayi3.setBounds(new Rectangle(160, 0, 30, 25));
            this.getContentPane().add(txtSayi3);
            txtSayi4 = new JFormattedTextField(ikiBasamak);
            txtSayi4.setBounds(new Rectangle(190, 0, 30, 25));
            this.getContentPane().add(txtSayi4);
            txtSayi5 = new JFormattedTextField(ikiBasamak);
            txtSayi5.setBounds(new Rectangle(220, 0, 30, 25));
            this.getContentPane().add(txtSayi5);
            txtSayi6 = new JFormattedTextField(ucBasamak);
            txtSayi6.setBounds(new Rectangle(250, 0, 30, 25));
            this.getContentPane().add(txtSayi6);
            // Hedef Sayi arayuze ekleniyor
            this.getContentPane().add(
                    createLabel("HEDEF SAYI:", new Rectangle(0, 25, 100, 25)));
            txtHedefSayi = new JFormattedTextField(ucBasamak);
            txtHedefSayi.setBounds(100, 25, 30, 25);
            this.getContentPane().add(txtHedefSayi);
            // Dugmeler ekleniyor
            this.getContentPane().add(getBtnRandomSayi());
            this.getContentPane().add(getBtnRandomHedefSayi());
            this.getContentPane().add(getBtnHesapla());
            // Sonuc ekleniyor
            lblCozum = new JLabel();
            lblCozum.setBounds(0, 50, 300, 25);
            this.getContentPane().add(lblCozum);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Bu metodun amaci hedef sayiyi 100-999 arasinda rastgele uretip ilgili
     * metin alanina yazma islemini tetikleyen dugme nesnesini olusturmak
     */
    private JButton getBtnRandomHedefSayi() {
        JButton btn = new JButton("RASTGELE");
        // alt+r'de dugmeyi tetikleyecek
        btn.setMnemonic('r');
        btn.setBounds(130, 25, 125, 25);
        btn.addActionListener(e -> {
            // 100 ile 999 arasinda sayi uretip hedef sayi metin alanina
            // yaziyoruz
            txtHedefSayi.setText(""
                    + (SayiArac.getInstance().nextInt(1000) + 100));
        });
        return btn;
    }

    /**
     * Bu metodun amaci hesaplamada kullanilacak 6 sayiyi rastgele uretip ilgili
     * metin alanina yazma islemini tetikleyen dugme nesnesini olusturmak
     */
    private JButton getBtnRandomSayi() {
        JButton btn = new JButton("RASTGELE");
        // alt+s'de dugmeyi tetikliyor
        btn.setMnemonic('s');
        btn.setBounds(280, 0, 100, 25);
        btn.addActionListener(e -> rastgeleSayilariUret());
        return btn;
    }

    /**
     * Bu metot tum veriler girildikten sonra verilere dayanarak genetik
     * hesaplama islemini baslatacak olan dugmeyi yaratip donduren metottur.
     */
    private JButton getBtnHesapla() {
        JButton btn = new JButton("HESAPLA");
        // alt+h'de dugmeyi tetikliyor
        btn.setMnemonic('h');
        btn.setBounds(255, 25, 125, 25);
        btn.addActionListener(e -> hesapla());
        return btn;
    }

    /**
     * Metin alanindaki sayilari okuyarak genetik hesaplama sinifindaki ilgili
     * metodu cagiran ve hesaplama sonucunu sonuc etiketine yazan metot
     */
    private void hesapla() {
        // Hesaplama sayilarini okuyalim
        int sayi1 = Integer.parseInt(txtSayi1.getText());
        int sayi2 = Integer.parseInt(txtSayi2.getText());
        int sayi3 = Integer.parseInt(txtSayi3.getText());
        int sayi4 = Integer.parseInt(txtSayi4.getText());
        int sayi5 = Integer.parseInt(txtSayi5.getText());
        int sayi6 = Integer.parseInt(txtSayi6.getText());
        // Hedef sayiyi okuyalim
        int hedefSayi = Integer.parseInt(txtHedefSayi.getText());
        // Bosu bosuna hesaplama yapmamak icin asagidaki kontrolu yapiyoruz
        if ((sayi1 * sayi2 * sayi3 * sayi4 * sayi5 * sayi6) < hedefSayi) {
            JOptionPane.showMessageDialog(this,
                    "Bu sayılarla hedef sayı üretilemez!", "ÜRETİLEMEZ",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            // GenetikHesaplama sinifinda elimizdeki sayilara gore
            // hesaplama yapip sonucu cozum etiketine yazalim
            lblCozum.setText(""
                    + GenetikHesaplama.getInstance().hesapla(
                    new int[]{sayi1, sayi2, sayi3, sayi4, sayi5,
                            sayi6}, hedefSayi));
        }
    }

    /**
     * Hesaplama sayilarini rastgele uretip ilgili metin alanlarina yazan metot
     */
    private void rastgeleSayilariUret() {
        // Ilk 5 sayi 1 ile 9 arasinda (1 ve 9 dahil) herhangi bir sayi
        // olabilir
        txtSayi1.setText("0" + (SayiArac.getInstance().nextInt(9) + 1));
        txtSayi2.setText("0" + (SayiArac.getInstance().nextInt(9) + 1));
        txtSayi3.setText("0" + (SayiArac.getInstance().nextInt(9) + 1));
        txtSayi4.setText("0" + (SayiArac.getInstance().nextInt(9) + 1));
        txtSayi5.setText("0" + (SayiArac.getInstance().nextInt(9) + 1));
        // 6. sayi 10-25-50-75 ve 100 olabilir
        int rnd = SayiArac.getInstance().nextInt(5);
        if (rnd == 0) {
            txtSayi6.setText("010");
        } else {
            txtSayi6.setText("" + 25 * rnd);
        }
    }

    /**
     * Etiket yaratma islemini kolaylastiran metot
     */
    private JLabel createLabel(String label, Rectangle bounds) {
        JLabel jlabel = new JLabel(label);
        jlabel.setBounds(bounds);
        return jlabel;
    }
}
