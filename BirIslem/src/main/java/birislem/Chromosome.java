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

import birislem.exception.EarlyConvergenceException;
import birislem.exception.FractionDivideException;
import birislem.utility.NumberUtility;

import java.util.Arrays;

/**
 * Bu sinif bireylerimizin (krozomlarin) genlerini saklayan ve ilgili
 * metotlarini gerceklestiren siniftir.
 */
public class Chromosome implements Comparable<Chromosome> {

    // Hesaplama sayilarinin kullanim sirasini tutan saha
    private int[] sayilar;
    // Kromozomun hesaplamadaki operatorlerini ve sirasini tutan saha
    private int[] operatorler;

    // Yapici
    Chromosome(int[] _sayilar, int[] _operatorler) {
        sayilar = _sayilar;
        operatorler = _operatorler;
    }

    /**
     * Bu kromozomun degerini hesaplayip donduren metot
     *
     * @return hesaplama sonucu
     */
    private int getDeger() {
        try {
            return NumberUtility.getInstance().getHesapSonuc(sayilar, operatorler);
        } catch (FractionDivideException kbe) {
            // Eger kesirli bolme varsa bu kromozom hatalidir
            // En dusuk deger olan 0'i dondurmelidir.
            return 0;
        } catch (EarlyConvergenceException sebe) {
            // Sayiya erken ulastik
            // Pozitifini alalim
            // Bu sayidan sonraki sayilari atacagiz
            for (int i = sebe.getWhichOperator(); i < operatorler.length; i++) {
                operatorler[i] = NumberUtility.KULLANMA;
            }// Hedef sayiya ulastik
            return NumberUtility.getInstance().getHedefSayi();
        }
    }

    int getSayi(int i) {
        return sayilar[i];
    }

    int getOperator(int i) {
        return operatorler[i];
    }

    void setSayilar(int[] _sayilar) {
        sayilar = _sayilar;
    }

    void setOperatorler(int[] _operatorler) {
        operatorler = _operatorler;
    }

    int uygunluk() {
        // 0' esit olunca en iyi sonuc
        // 0'dan mutlak olarak uzakligi bu kromozomun uygunlugudur
        // Uygunlugu 0'a yakin olan kromozom daha uzak olan kromozomdan kotudur
        return java.lang.Math.abs(getDeger()
                - NumberUtility.getInstance().getHedefSayi());
    }

    void setSayi(int hangi, int sayi) {
        sayilar[hangi] = sayi;
    }

    void setOperator(int hangi, int operator) {
        operatorler[hangi] = operator;
    }

    /**
     * Kromozomu ekrana yazdirmada kullanilan ve metin olarak ureten metot
     */
    public String toString() {
        return NumberUtility.getInstance().toString(sayilar, operatorler)
                + " Değer:" + getDeger() + " Uygunluk:" + uygunluk();
    }

    /**
     * Siralama isleminde kullanilan karsilastirma metotu
     */
    @Override
    public int compareTo(Chromosome o) {
        // Sonuc 0 ise iki kromozom aynidir
        // Sonuc >0 ise ilk kromozom ikincisinden kotudur
        // Sonuc <0 ise ilk kromozom ikincisinden iyidir
        return uygunluk() - o.uygunluk();
    }

    /**
     * Bu metot bu kromozomun aynisinin farkli bir bellek alaninda
     * olusturulmasini sagliyor
     */
    Chromosome getClone() {
        return new Chromosome(Arrays.copyOf(sayilar, sayilar.length), Arrays.copyOf(operatorler, operatorler.length));
    }
}
