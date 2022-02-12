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
package birislem.utility;

import birislem.exception.EarlyConvergenceException;
import birislem.exception.FractionDivideException;

import java.util.Random;

/**
 * Bu sinif sayi hesaplama ve metine donusturme islemlerini kolaylastirmak ve
 * saklanmasi gereken verileri saklamak icin kullanilmaktadir
 */
public class NumberUtility {

    // Operatorler
    public static final int KULLANMA = 0;
    public static final int ARTI = 1;
    public static final int EKSI = 2;
    public static final int CARPI = 3;
    public static final int BOLU = 4;
    // Bu sinif bir singleton desen gerceklestirimidir
    private static NumberUtility instance = null;
    // Rastgele sayi uretecimiz
    private final Random generator;
    // Ulasmaya calistigimiz sayi
    private int hedefSayi;
    // Hesaplamada kullanilacak sayilar
    private int[] hesaplamaSayilari;

    private NumberUtility() {
        // Ilklendigi zaman rastgele sayi uretecini ilkleyelim
        generator = new Random();
    }

    /**
     * Singleton ornek dondurucu
     */
    public static NumberUtility getInstance() {
        if (instance == null) {
            instance = new NumberUtility();
        }
        return instance;
    }

    /**
     * Hesaplamada kullanilacak olan sayilari ve hedef sayiyi saklamak icin
     * kullanilan metot. Bu metot genetik algoritma calistirilmadan once
     * cagrilmalidir
     *
     * @param _hesaplamaSayilari hesaplamada kullanilacak olan sayilar
     * @param _hedefSayi         ulasmaya calistigimiz hedef sayi
     */
    public void initialize(int[] _hesaplamaSayilari, int _hedefSayi) {
        hedefSayi = _hedefSayi;
        hesaplamaSayilari = _hesaplamaSayilari;
    }

    public int getHedefSayi() {
        return hedefSayi;
    }

    /**
     * Bu metodun amaci rastgele sayi uretmek Bu sayilar hesaplama sayilari
     * dizisine indisleri icermektedir Ayrica uretilen sayilarin tekrar etmemesi
     * saglaniyor
     *
     * @return rastgele uretilmis 6 elemanli int dizisi
     */
    public int[] getRastgeleSayilar() {
        int[] result = {-1, -1, -1, -1, -1, -1};
        int uretilen = nextInt(6);

        for (int i = 0; i < 6; i++) {
            while (contains(result, uretilen)) {
                uretilen = nextInt(6);
            }
            result[i] = uretilen;
        }
        return result;
    }

    /**
     * ikinci parametrede verilen degerin birinci parametredeki dizide olup
     * olmadigini kontrol eden metot
     *
     * @param array  kontrol edilecek dizi
     * @param _deger dizide aranacak olan deger
     * @return deger dizide varsa true, yoksa false
     */
    private boolean contains(int[] array, int _deger) {
        for (int anArray : array) {
            if (anArray == _deger) {
                return true;
            }
        }
        return false;
    }

    /**
     * Bu metodun amaci rastgele operator uretmek
     *
     * @return rastgele uretilmis 5 elemanli int dizisi
     */
    public int[] getRastgeleOperatorler() {
        int[] result = new int[5];

        for (int i = 0; i < 5; i++) {
            result[i] = nextInt(5);
        }
        return result;
    }

    /**
     * Rastgele uretilen noktali sayi ureten metot
     *
     * @return rastgele uretilmis noktali sayi
     */
    public float nextFloat() {
        return generator.nextFloat();
    }

    /**
     * 0 ile (ustSinir-1) arasinda rastgele tamsayi ureten metot
     *
     * @param ustSinir araligin ust siniri
     * @return uretilen rastgele sayi
     */
    public int nextInt(int ustSinir) {

        return generator.nextInt(ustSinir);
    }

    /**
     * Bu metot girilen parametrelere gore hesaplama sonucunu uretiyor
     *
     * @param sayilar     hesaplamada kullanilacak sayilara indisler
     * @param operatorler hesaplamada kullanilacak operatorler
     * @return hesaplama sonucu (tamsayi)
     * @throws FractionDivideException   eger bolme islemi tamsayi sonuc uretmiyorsa
     * @throws EarlyConvergenceException hesaplamanin erken asamalarinda hedefe ulasirsak
     */
    public int getHesapSonuc(int[] sayilar, int[] operatorler)
            throws FractionDivideException, EarlyConvergenceException {
        int hesapSonucu;
        int hangiSayi;
        int baslangic = 0;
        // TODO ilk sayiyi her zaman kullaniyor
        // if (operatorler[0] != SayiArac.KULLANMA) {
        hesapSonucu = hesaplamaSayilari[sayilar[0]];
        hangiSayi = 1;
        /*
         * } else { hesapSonucu = hesaplamaSayilari[sayilar[1]]; hangiSayi = 2;
         * baslangic = 1; }
         */
        for (int i = baslangic; i < operatorler.length; i++) {
            int sayi = hesaplamaSayilari[sayilar[hangiSayi]];
            if (operatorler[i] == NumberUtility.ARTI) {
                hesapSonucu += sayi;
            }
            if (operatorler[i] == NumberUtility.CARPI) {
                hesapSonucu *= sayi;
            }
            if (operatorler[i] == NumberUtility.BOLU) {
                if (hesapSonucu % sayi != 0) {
                    // FIX Bolme isleminde mutlaka tam sayi elde etmeliyiz
                    // FIX her seferinde yeni bir ornek yaratmak
                    // gereksiz zaman harcatiyordu bunun yerine
                    // singleton kullanildi
                    throw FractionDivideException.getInstance();
                }
                hesapSonucu /= sayi;
            }
            if (operatorler[i] == NumberUtility.EKSI) {
                hesapSonucu -= sayi;
            }
            if (hesapSonucu == hedefSayi) {
                throw new EarlyConvergenceException((i + 1));
            }
            hangiSayi++;
        }
        return hesapSonucu;
    }

    /**
     * Hesaplamayi ekrana basmayi kolaylastiran metot
     *
     * @param sayilar     hesaplamada kullanilacak sayilara indisler
     * @param operatorler hesaplamada kullanilacak operatorler
     * @return hesaplama metini
     */
    public String toString(int[] sayilar, int[] operatorler) {
        int hangiSayi;
        int baslangic = 0;
        StringBuilder katar = new StringBuilder();
        katar.append(hesaplamaSayilari[sayilar[0]]);
        hangiSayi = 1;
        for (int i = baslangic; i < operatorler.length; i++) {
            int sayi = hesaplamaSayilari[sayilar[hangiSayi]];
            if (operatorler[i] == NumberUtility.ARTI) {
                katar.append("+").append(sayi);
            }
            if (operatorler[i] == NumberUtility.CARPI) {
                katar.append("*").append(sayi);
            }
            if (operatorler[i] == NumberUtility.BOLU) {
                katar.append("/").append(sayi);
            }
            if (operatorler[i] == NumberUtility.EKSI) {
                katar.append("-").append(sayi);
            }
            hangiSayi++;
        }
        return katar.toString();
    }
}
