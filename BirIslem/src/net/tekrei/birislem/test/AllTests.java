package net.tekrei.birislem.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for net.tekrei.birislem.test");
		// $JUnit-BEGIN$
		suite.addTestSuite(KromozomTest.class);
		suite.addTestSuite(SayiAracTest.class);
		// $JUnit-END$
		return suite;
	}

}
