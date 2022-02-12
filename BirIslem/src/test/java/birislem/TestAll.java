package birislem;

import birislem.exception.FractionDivideException;
import birislem.utility.NumberUtility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestAll {

    @Test
    void testCompareTo() {
        NumberUtility.getInstance().initialize(new int[]{5, 10, 5, 5, 10, 10},
                100);
        int[] siralama = new int[]{0, 1, 2, 3, 4, 5};
        // Hesap sonucu : 80 Uygunluk : 20
        Chromosome chromosome1 = new Chromosome(siralama,
                new int[]{NumberUtility.ARTI, NumberUtility.CARPI, NumberUtility.EKSI,
                        NumberUtility.KULLANMA, NumberUtility.ARTI});
        assertEquals(20, chromosome1.uygunluk());
        // Hesap sonucu : 3850 Uygunluk : 3750
        Chromosome chromosome2 = new Chromosome(siralama, new int[]{NumberUtility.ARTI,
                NumberUtility.CARPI, NumberUtility.CARPI, NumberUtility.ARTI, NumberUtility.CARPI});
        assertEquals(3750, chromosome2.uygunluk());
        // Hesap sonucu : 100 uygunluk : 0
        Chromosome chromosome3 = new Chromosome(siralama, new int[]{
                NumberUtility.KULLANMA, NumberUtility.ARTI, NumberUtility.KULLANMA,
                NumberUtility.CARPI, NumberUtility.KULLANMA});
        assertEquals(0, chromosome3.uygunluk());
        // kromozom3>kromozom1>kromozom2
        assertTrue(chromosome3.compareTo(chromosome1) < 0);
        assertTrue(chromosome3.compareTo(chromosome2) < 0);
        assertTrue(chromosome1.compareTo(chromosome2) < 0);
    }

    @Test
    void testGetHesapSonucu() throws Exception {
        NumberUtility.getInstance().initialize(new int[]{5, 7, 9, 9, 25, 10}, 8);
        int[] siralama = {0, 1, 2, 3, 4, 5};
        // islem : (((5+7)*9)-25)/10 = (Tam sayi sonuc dondurmeli)
        try {
            NumberUtility.getInstance().getHesapSonuc(
                    siralama,
                    new int[]{NumberUtility.ARTI, NumberUtility.CARPI,
                            NumberUtility.KULLANMA, NumberUtility.EKSI, NumberUtility.BOLU});
            fail("");
        } catch (FractionDivideException e) {
            assertTrue(true);
        }
        // islem : (((5*9)+9)-25)*10 = 290 (Tam sayi sonuc dondurmeli)
        assertEquals(290, NumberUtility.getInstance().getHesapSonuc(
                siralama,
                new int[]{NumberUtility.KULLANMA, NumberUtility.CARPI, NumberUtility.ARTI,
                        NumberUtility.EKSI, NumberUtility.CARPI}));
        // islem : (((5+7)*9)*9-25) = 947 (Tam sayi sonuc dondurmeli)
        assertEquals(947, NumberUtility.getInstance().getHesapSonuc(
                siralama,
                new int[]{NumberUtility.ARTI, NumberUtility.CARPI, NumberUtility.CARPI,
                        NumberUtility.EKSI, NumberUtility.KULLANMA}));
        // islem : 25+2*7*5*1=472
        NumberUtility.getInstance().initialize(new int[]{25, 2, 7, 5, 1, 2}, 472);
        assertEquals(945, NumberUtility.getInstance().getHesapSonuc(
                siralama,
                new int[]{NumberUtility.ARTI, NumberUtility.CARPI, NumberUtility.CARPI,
                        NumberUtility.CARPI, NumberUtility.KULLANMA}));
    }

    @Test
    void testGetRastgeleSayilar() {
        // Uretilen sayilarin bizim verdigimiz sayilari disinda sayi icermemesi
        // gerekiyor ve hepsini icermesi gerekiyor
        NumberUtility.getInstance().initialize(new int[]{0, 1, 2, 3, 4, 5}, 0);

        int[] rastgele = NumberUtility.getInstance().getRastgeleSayilar();
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
