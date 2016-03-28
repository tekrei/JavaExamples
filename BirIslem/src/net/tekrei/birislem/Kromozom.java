/*
 * BirIslem Java ile yazilmis ve Genetik Algoritma kullanimini ornekleme
 * amaci guden bir ozgur yazilimdir.
 * Copyright (C) 2007 Tahir Emre KALAYCI (http://kodveus.blogspot.com)
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
package net.tekrei.birislem;

import net.tekrei.birislem.araclar.SayiArac;
import net.tekrei.birislem.exception.KesirliBolmeException;
import net.tekrei.birislem.exception.SonucErkenBulunduException;

/**
 * Bu sinif bireylerimizin (krozomlarin) genlerini saklayan ve ilgili
 * metotlarini gerceklestiren siniftir.
 *
 * @author emre
 *
 */
public class Kromozom implements Comparable<Kromozom>, Cloneable {

	// Hesaplama sayilarinin kullanim sirasini tutan saha
	private int[] sayilar;
	// Kromozomun hesaplamadaki operatorlerini ve sirasini tutan saha
	private int[] operatorler;

	// Yapici
	public Kromozom(int[] _sayilar, int[] _operatorler) {
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
			return SayiArac.getInstance().getHesapSonuc(sayilar, operatorler);
		} catch (KesirliBolmeException kbe) {
			// Eger kesirli bolme varsa bu kromozom hatalidir
			// En dusuk deger olan 0'i dondurmelidir.
			return 0;
		} catch (SonucErkenBulunduException sebe) {
			// Sayiya erken ulastik
			// Pozitifini alalim
			// Bu sayidan sonraki sayilari atacagiz
			for (int i = sebe.getHangiOperator(); i < operatorler.length; i++) {
				operatorler[i] = SayiArac.KULLANMA;
			}// Hedef sayiya ulastik
			return SayiArac.getInstance().getHedefSayi();
		}
	}

	public int getSayi(int i) {
		return sayilar[i];
	}

	public int getOperator(int i) {
		return operatorler[i];
	}

	public int[] getSayilar() {
		return sayilar;
	}

	public int[] getOperatorler() {
		return operatorler;
	}

	public int uygunluk() {
		// 0' esit olunca en iyi sonuc
		// 0'dan mutlak olarak uzakligi bu kromozomun uygunlugudur
		// Uygunlugu 0'a yakin olan kromozom daha uzak olan kromozomdan kotudur
		return java.lang.Math.abs(getDeger()
				- SayiArac.getInstance().getHedefSayi());
	}

	public void setSayilar(int[] _sayilar) {
		sayilar = _sayilar;
	}

	public void setOperatorler(int[] _operatorler) {
		operatorler = _operatorler;
	}

	public void setSayi(int hangi, int sayi) {
		sayilar[hangi] = sayi;
	}

	public void setOperator(int hangi, int operator) {
		operatorler[hangi] = operator;
	}

	/**
	 * Kromozomu ekrana yazdirmada kullanilan ve metin olarak ureten metot
	 */
	public String toString() {
		return SayiArac.getInstance().toString(sayilar, operatorler)
				+ " Değer:" + getDeger() + " Uygunluk:" + uygunluk();
	}

	/**
	 * Siralama isleminde kullanilan karsilastirma metotu
	 *
	 * @see java.lang.Comparable.compareTo(Object o)
	 */
	public int compareTo(Kromozom o) {
		// Sonuc 0 ise iki kromozom aynidir
		// Sonuc >0 ise ilk kromozom ikincisinden kotudur
		// Sonuc <0 ise ilk kromozom ikincisinden iyidir
		return uygunluk() - o.uygunluk();
	}

	@Override
	/**
	 * Bu metot bu kromozomun aynisinin farkli bir bellek alaninda
	 * olusturulmasini sagliyor
	 */
	protected Kromozom clone() {
		return new Kromozom(SayiArac.getInstance().copyOf(getSayilar()),
				SayiArac.getInstance().copyOf(getOperatorler()));
	}
}
