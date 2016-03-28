package net.tekrei.birislem.test;

import junit.framework.TestCase;
import net.tekrei.birislem.araclar.SayiArac;
import net.tekrei.birislem.exception.KesirliBolmeException;

public class SayiAracTest extends TestCase {

	public void testGetHesapSonucu() throws Exception {
		SayiArac.getInstance().initialize(new int[] { 5, 7, 9, 9, 25, 10 }, 8);
		int[] siralama = { 0, 1, 2, 3, 4, 5 };
		// islem : (((5+7)*9)-25)/10 = (Tam sayi sonuc dondurmeli)
		try {
			SayiArac.getInstance().getHesapSonuc(
					siralama,
					new int[] { SayiArac.ARTI, SayiArac.CARPI,
							SayiArac.KULLANMA, SayiArac.EKSI, SayiArac.BOLU });
			fail();
		} catch (KesirliBolmeException e) {
			assertTrue(true);
		}
		// islem : (((5*9)+9)-25)*10 = 290 (Tam sayi sonuc dondurmeli)
		assertEquals(290, SayiArac.getInstance().getHesapSonuc(
				siralama,
				new int[] { SayiArac.KULLANMA, SayiArac.CARPI, SayiArac.ARTI,
						SayiArac.EKSI, SayiArac.CARPI }));
		// islem : (((5+7)*9)*9-25) = 947 (Tam sayi sonuc dondurmeli)
		assertEquals(947, SayiArac.getInstance().getHesapSonuc(
				siralama,
				new int[] { SayiArac.ARTI, SayiArac.CARPI, SayiArac.CARPI,
						SayiArac.EKSI, SayiArac.KULLANMA }));
		// islem : 25+2*7*5*1=472
		SayiArac.getInstance().initialize(new int[] { 25, 2, 7, 5, 1, 2 }, 472);
		assertEquals(945, SayiArac.getInstance().getHesapSonuc(
				siralama,
				new int[] { SayiArac.ARTI, SayiArac.CARPI, SayiArac.CARPI,
						SayiArac.CARPI, SayiArac.KULLANMA }));
	}

	public void testGetRastgeleSayilar() {
		// Uretilen sayilarin bizim verdigimiz sayilari disinda sayi icermemesi
		// gerekiyor ve hepsini icermesi gerekiyor
		SayiArac.getInstance().initialize(new int[] { 0, 1, 2, 3, 4, 5 }, 0);

		int[] rastgele = SayiArac.getInstance().getRastgeleSayilar();
		int[] count = new int[6];

		for (int i = 0; i < 6; i++) {
			if (rastgele[i] > 5)
				fail();
			if (rastgele[i] < 0)
				fail();
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
