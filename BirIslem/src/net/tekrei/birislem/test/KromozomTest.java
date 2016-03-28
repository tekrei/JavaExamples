package net.tekrei.birislem.test;

import junit.framework.TestCase;
import net.tekrei.birislem.Kromozom;
import net.tekrei.birislem.araclar.SayiArac;

public class KromozomTest extends TestCase {

	public void testCompareTo() {
		SayiArac.getInstance().initialize(new int[] { 5, 10, 5, 5, 10, 10 },
				100);
		int[] siralama = new int[] { 0, 1, 2, 3, 4, 5 };
		// Hesap sonucu : 80 Uygunluk : 20
		Kromozom kromozom1 = new Kromozom(siralama,
				new int[] { SayiArac.ARTI, SayiArac.CARPI, SayiArac.EKSI,
						SayiArac.KULLANMA, SayiArac.ARTI });
		assertEquals(20, kromozom1.uygunluk());
		// Hesap sonucu : 3850 Uygunluk : 3750
		Kromozom kromozom2 = new Kromozom(siralama, new int[] { SayiArac.ARTI,
				SayiArac.CARPI, SayiArac.CARPI, SayiArac.ARTI, SayiArac.CARPI });
		assertEquals(3750, kromozom2.uygunluk());
		// Hesap sonucu : 100 uygunluk : 0
		Kromozom kromozom3 = new Kromozom(siralama, new int[] {
				SayiArac.KULLANMA, SayiArac.ARTI, SayiArac.KULLANMA,
				SayiArac.CARPI, SayiArac.KULLANMA });
		assertEquals(0, kromozom3.uygunluk());
		// kromozom3>kromozom1>kromozom2
		assertTrue(kromozom3.compareTo(kromozom1) < 0);
		assertTrue(kromozom3.compareTo(kromozom2) < 0);
		assertTrue(kromozom1.compareTo(kromozom2) < 0);
	}
}
