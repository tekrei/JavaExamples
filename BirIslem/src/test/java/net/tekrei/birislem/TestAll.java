package net.tekrei.birislem;

import net.tekrei.birislem.araclar.SayiArac;
import net.tekrei.birislem.exception.KesirliBolmeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestAll {

    @Test
    void testCompareTo() {
        SayiArac.getInstance().initialize(new int[]{5, 10, 5, 5, 10, 10},
                100);
        int[] siralama = new int[]{0, 1, 2, 3, 4, 5};
        // Hesap sonucu : 80 Uygunluk : 20
        Kromozom kromozom1 = new Kromozom(siralama,
                new int[]{SayiArac.ARTI, SayiArac.CARPI, SayiArac.EKSI,
                        SayiArac.KULLANMA, SayiArac.ARTI});
        assertEquals(20, kromozom1.uygunluk());
        // Hesap sonucu : 3850 Uygunluk : 3750
        Kromozom kromozom2 = new Kromozom(siralama, new int[]{SayiArac.ARTI,
                SayiArac.CARPI, SayiArac.CARPI, SayiArac.ARTI, SayiArac.CARPI});
        assertEquals(3750, kromozom2.uygunluk());
        // Hesap sonucu : 100 uygunluk : 0
        Kromozom kromozom3 = new Kromozom(siralama, new int[]{
                SayiArac.KULLANMA, SayiArac.ARTI, SayiArac.KULLANMA,
                SayiArac.CARPI, SayiArac.KULLANMA});
        assertEquals(0, kromozom3.uygunluk());
        // kromozom3>kromozom1>kromozom2
        assertTrue(kromozom3.compareTo(kromozom1) < 0);
        assertTrue(kromozom3.compareTo(kromozom2) < 0);
        assertTrue(kromozom1.compareTo(kromozom2) < 0);
    }

    @Test
    void testGetHesapSonucu() throws Exception {
        SayiArac.getInstance().initialize(new int[]{5, 7, 9, 9, 25, 10}, 8);
        int[] siralama = {0, 1, 2, 3, 4, 5};
        // islem : (((5+7)*9)-25)/10 = (Tam sayi sonuc dondurmeli)
        try {
            SayiArac.getInstance().getHesapSonuc(
                    siralama,
                    new int[]{SayiArac.ARTI, SayiArac.CARPI,
                            SayiArac.KULLANMA, SayiArac.EKSI, SayiArac.BOLU});
            fail("");
        } catch (KesirliBolmeException e) {
            assertTrue(true);
        }
        // islem : (((5*9)+9)-25)*10 = 290 (Tam sayi sonuc dondurmeli)
        assertEquals(290, SayiArac.getInstance().getHesapSonuc(
                siralama,
                new int[]{SayiArac.KULLANMA, SayiArac.CARPI, SayiArac.ARTI,
                        SayiArac.EKSI, SayiArac.CARPI}));
        // islem : (((5+7)*9)*9-25) = 947 (Tam sayi sonuc dondurmeli)
        assertEquals(947, SayiArac.getInstance().getHesapSonuc(
                siralama,
                new int[]{SayiArac.ARTI, SayiArac.CARPI, SayiArac.CARPI,
                        SayiArac.EKSI, SayiArac.KULLANMA}));
        // islem : 25+2*7*5*1=472
        SayiArac.getInstance().initialize(new int[]{25, 2, 7, 5, 1, 2}, 472);
        assertEquals(945, SayiArac.getInstance().getHesapSonuc(
                siralama,
                new int[]{SayiArac.ARTI, SayiArac.CARPI, SayiArac.CARPI,
                        SayiArac.CARPI, SayiArac.KULLANMA}));
    }

    @Test
    void testGetRastgeleSayilar() {
        // Uretilen sayilarin bizim verdigimiz sayilari disinda sayi icermemesi
        // gerekiyor ve hepsini icermesi gerekiyor
        SayiArac.getInstance().initialize(new int[]{0, 1, 2, 3, 4, 5}, 0);

        int[] rastgele = SayiArac.getInstance().getRastgeleSayilar();
        int[] count = new int[6];

        for (int i = 0; i < 6; i++) {
            if (rastgele[i] > 5)
                fail("");
            if (rastgele[i] < 0)
                fail("");
            count[i]++;
        }
        assertEquals(1, count[0]);
        assertEquals(1, count[1]);
        assertEquals(1, count[2]);
        assertEquals(1, count[3]);
        assertEquals(1, count[4]);
        assertEquals(1, count[5]);
    }
}
