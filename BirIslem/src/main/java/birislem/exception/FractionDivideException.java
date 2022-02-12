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
package birislem.exception;

/**
 * Bu istisna sinifinin amaci bolme isleminde tamsayi sonucu olusmamasi durumunu
 * belirtmek
 */
public class FractionDivideException extends Exception {
    private static final long serialVersionUID = 1L;
    // FIX Performans kazanci
    // Her seferinde bu istisnadan yeni bir tane yaratmak yerine
    // tum uygulamada sadece bir ornegini kullanmak icin
    // singleton kullaniliyor
    private static FractionDivideException instance = null;

    private FractionDivideException() {

    }

    public static FractionDivideException getInstance() {
        if (instance == null) {
            instance = new FractionDivideException();
        }
        return instance;
    }

}
