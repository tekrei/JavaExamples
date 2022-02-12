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

import birislem.utility.NumberUtility;

import java.util.Arrays;

/**
 * Genetik hesaplama islemlerinin yapildigi ve singleton tasarim deseni ile
 * gerceklestirilmis sinif, bu sinifta sadece bir metot (hesapla(int[],int))
 * disaridan erisilebiliyor (elbette ayrica getInstance() metodu ornek olusturup
 * donduruyor)
 * <p>
 * Her bir Kromozom bir bireydir.
 */
class GeneticAlgorithm {

    // Singleton nesnemiz
    private static GeneticAlgorithm instance = null;

    /**
     * dis erisime kapali yapici
     */
    private GeneticAlgorithm() {

    }

    /**
     * Singleton sinifin ornegini donduren metot
     *
     * @return sinifin tek ornegi
     */
    static GeneticAlgorithm getInstance() {
        if (instance == null) {
            instance = new GeneticAlgorithm();
        }
        return instance;
    }

    /**
     * Disaridan erisilebilen yegane metot Bu metodun amaci kendisine parametre
     * olarak verilen hesaplama sayilarini genetik algoritmada isletip hedef
     * sayisina en yakin cozumu bulmak ve bu cozumu cagiran yere dondurmektir.
     *
     * @param sayilar   Hesaplamada kullanilacak olan sayilar
     * @param hedefSayi Ulasmak istedigimiz sayi
     * @return En iyi cozum
     */
    Chromosome hesapla(int[] sayilar, int hedefSayi) {
        // FIX Uygun sezgilerle ve incelemeyle ise yaradigi bulundu
        // artik 1. nesil disindaki durumlarda da bulunabiliyor

        // Ilk olarak sayi aracimizi ilkliyoruz
        // Bu arac sayilarla ilgili bir cok islemi yapiyor
        NumberUtility.getInstance().initialize(sayilar, hedefSayi);
        // 1000 bireyden olusan rastgele genlere sahip bireylerden
        // olusan toplum olusturuluyor
        Chromosome[] toplum = rastgeleToplumOlustur(1000);

        // nesil sayacimiz
        int nesil = 0;

        // Toplumu uygunluklarina gore (azalan sirada) siraliyoruz
        Arrays.sort(toplum);

        // Siralama islemi sonunda en iyi bireyimiz en uygun bireyimizdir
        // bu da ilk bireydir
        Chromosome enIyi = toplum[0].getClone();

        // ne kadar surdugunu tutacagiz
        long baslangic = System.currentTimeMillis();

        // Genetik hesaplamayi 1000 nesil icin yapacagiz
        while (nesil < 1000) {
            // nesil sayacini arttiralim
            nesil++;

            // toplumda caprazlama yapalim
            // TODO caprazlama ise yariyor mu denemek lazim
            toplum = caprazla(toplum);
            // toplumda mutasyon yapalim
            toplum = mutasyon(toplum);

            // Toplumu uygunluklarina gore (azalan sirada) siraliyoruz
            Arrays.sort(toplum);

            // Eger elimizde hedef sayiyi tam olarak veren bir birey varsa
            // en iyi sonuc odur ve genetik hesaplamaya gerek yoktur
            // hemen en iyi kromozomu dondurebiliriz
            Chromosome tamSonuc = tamSonuc(toplum);
            if (tamSonuc != null) {
                enIyi = tamSonuc;
                break;
            }

            // Eger yeni toplumdaki en uygun birey elimizdeki en iyi bireyden
            // daha uygun ise en iyi bireyi yeni toplumdaki en uygun bireyle
            // degistiriyoruz
            if (enIyi.compareTo(toplum[0]) > 0) {
                enIyi = toplum[0].getClone();
            }
            // En iyi bireyimiz tam sonuc ise cikiyoruz
            // TODO Aslinda yukaridaki tam sonuc kontrolu bunu yakalayacagi icin
            // bu satira gerek olmayabilir?
            if (tamSonucmu(enIyi)) {
                break;
            }
        }
        // Genetik hesaplama ile ilgili bilgileri verelim
        System.out.println("NESİL:" + nesil + " GEÇEN SÜRE:"
                + (System.currentTimeMillis() - baslangic) + " ms" + " En İyi:"
                + enIyi);
        // En iyi sonucumuzu dondurelim
        return enIyi;
    }

    /**
     * Girdi olarak verilen toplumdaki bireyler arasinda hedef sayiyi tam olarak
     * veren birey varsa sonuc olarak dondurelecek, yoksa null dondurelecek
     *
     * @param toplum kontrol edilecek olan toplum
     * @return tam sonuc varsa ilgili birey, yoksa null
     */
    private Chromosome tamSonuc(Chromosome[] toplum) {
        for (Chromosome chromosome : toplum) {
            if (tamSonucmu(chromosome)) {
                return chromosome;
            }
        }
        return null;
    }

    /**
     * Girdi olarak verilen bireyin tam sonuc uretip uretmedigini kontrol eden
     * metot
     *
     * @param birey kontrol edilecek olan birey
     * @return tam sonuc ise true, degilse false
     */
    private boolean tamSonucmu(Chromosome birey) {
        // Eger bir birey tam sonuc uretiyorsa uygunlugu 0 olacaktir
        return birey.uygunluk() == 0;
    }

    /**
     * Asagidaki metod mutasyon islemini toplum uzerinde uygular
     */
    private Chromosome[] mutasyon(Chromosome[] toplum) {
        for (int i = 0; i < toplum.length; i++) {
            try {
                Chromosome birey = toplum[i].getClone();
                // Sayi Mutasyonu
                boolean mutasyon = false;
                // Toplumun sadece %2'sinde sayi mutasyonu oluyor
                if (NumberUtility.getInstance().nextFloat() < 0.2) {
                    sayiMutasyon(birey);
                    mutasyon = true;
                }
                // Operator mutasyonu
                // Toplumun sadece %1'inde operator mutasyonu oluyor
                if (NumberUtility.getInstance().nextFloat() < 0.1) {
                    operatorMutasyon(birey);
                    mutasyon = true;
                }
                // sadece daha iyi mutasyonlari saklayalim
                // Eger bir mutasyon daha kotu ise saklanmiyor
                if (mutasyon) {
                    if (birey.uygunluk() < toplum[i].uygunluk()) {
                        toplum[i] = birey;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Degisen toplum donduruluyor
        return toplum;
    }

    /**
     * Sayilar arasindaki mutasyon islemi
     */
    private void sayiMutasyon(Chromosome birey) {
        // Rastgele iki sayi secilip yerleri degistiriliyor
        int birinci = NumberUtility.getInstance().nextInt(6);
        int ikinci = NumberUtility.getInstance().nextInt(6);
        int temp = birey.getSayi(birinci);
        birey.setSayi(birinci, birey.getSayi(ikinci));
        birey.setSayi(ikinci, temp);
    }

    /**
     * Parametreler arasindaki mutasyon
     */
    private void operatorMutasyon(Chromosome birey) {
        // Rastgele iki parametre secilip yerleri degistiriliyor
        int birinci = NumberUtility.getInstance().nextInt(5);
        int ikinci = NumberUtility.getInstance().nextInt(5);
        int temp = birey.getOperator(birinci);
        birey.setOperator(birinci, birey.getOperator(ikinci));
        birey.setOperator(ikinci, temp);
    }

    /**
     * Asagidaki metod toplum uzerinde caprazlama islemini uygular
     */
    private Chromosome[] caprazla(Chromosome[] toplum) {
        for (int i = 0; i < toplum.length - 1; i++) {
            try {
                // Toplumun sadece %90'i uzerinde caprazlama yapiliyor
                if (NumberUtility.getInstance().nextFloat() < 0.9) {
                    // Sadece toplum[i] degistiriliyor
                    // esle(toplum[i], toplum[i + 1]);
                    // eslemenin yapilacagi ikinci birey rastgele seciliyor
                    esle(toplum[i], toplum[NumberUtility.getInstance().nextInt(
                            toplum.length)]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Degisen toplumu dondur
        return toplum;
    }

    /**
     * Iki birey arasinda caprazlamayi yapan metot
     *
     * @param ilk    birinci (degisecek olan) birey
     * @param ikinci caprazlama yardimcisi birey
     */
    private void esle(Chromosome ilk, Chromosome ikinci) {

        // Caprazlama sonuclarini saklamak icin
        int[] yeniSayilar = new int[6];
        int[] yeniOperatorler = new int[5];

        // Sayilar caprazlansin
        for (int i = 0; i < 6; i++) {
            // Uniform (bir,iki,bir,iki,bir,iki)
            if (i % 2 == 0) {
                yeniSayilar[i] = ilk.getSayi(i);
            } else {
                yeniSayilar[i] = ikinci.getSayi(i);
            }
        }
        // Degisecek bireyin sayilarini yeni sayilar olarak atiyoruz
        ilk.setSayilar(duzeltme(yeniSayilar));

        // Operatorler caprazlansin
        for (int i = 0; i < 5; i++) {
            // uniform (bir,iki,bir,iki,bir)
            if (i % 2 == 0) {
                yeniOperatorler[i] = ilk.getOperator(i);
            } else {
                yeniOperatorler[i] = ikinci.getOperator(i);
            }
        }
        // Degisecek bireyin operatorlerini yeni operatorler olarak atiyoruz
        ilk.setOperatorler(duzeltme(yeniOperatorler));
    }

    /**
     * Caprazlama sonucunda bozuk kromozomlar olusabilir bunu onlemek icin
     * tekrar eden sayilari kaldirmak, yerine hic kullanilmayan sayilari koymak
     * icin bu metot kullaniliyor Ornek
     * <p>
     * Birey1 : 1 0 3 4 5 2
     * <p>
     * Birey2 : 0 1 2 5 3 4
     * <p>
     * CaprazlamaSonucuBirey1 : 1 1 3 5 5 4
     * <p>
     * DuzeltmeSonucuBirey1 : 1 0 3 5 2 4 (ikinci 1 yerine olmayan en kucuk 0,
     * ikinci 5 yerine olmayan en kucuk iki koyuldu)
     *
     * @param genes bozuk genler
     * @return duzeltilmis genler
     */
    private int[] duzeltme(int[] genes) {
        int boyut = genes.length;
        boolean[] var = new boolean[boyut];

        for (int i = 0; i < boyut; i++) {
            if (var[genes[i]]) {
                genes[i] = varolmayan(var);
            }

            var[genes[i]] = true;
        }

        return genes;
    }

    /**
     * Kromozomda bulunmayan en kucuk geni dondurur
     *
     * @return olmayan en kucuk gen
     */
    private int varolmayan(boolean[] genesStatus) {
        for (int i = 0; i < genesStatus.length; i++) {
            if (!genesStatus[i]) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Parametre olarak girilen birey sayisi kadar rastgele genlere sahip toplum
     * olusturulur ve dondurulur
     *
     * @param toplumBuyuklugu toplumdaki birey sayisi
     * @return olusturulan toplum
     */
    private Chromosome[] rastgeleToplumOlustur(int toplumBuyuklugu) {
        Chromosome[] toplum = new Chromosome[toplumBuyuklugu];
        for (int i = 0; i < toplumBuyuklugu; i++) {
            toplum[i] = new Chromosome(NumberUtility.getInstance()
                    .getRastgeleSayilar(), NumberUtility.getInstance()
                    .getRastgeleOperatorler());
        }
        return toplum;
    }
}
