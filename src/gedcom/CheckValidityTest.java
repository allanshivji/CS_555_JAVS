package gedcom;

import static org.junit.Assert.*;
import org.junit.*;

public class CheckValidityTest {

	int Age1 = 151;
	int Age2 = 13;
	int Age3 = 30;
	int Age4 = 150;
	int Age5 = 149;
	int Age6 = 14;
	String SpouseID1 = null;
	String SpouseID2 = "F1";
	
	@Test
	public void testCheckAge() {
		//fail("Not yet implemented");
		assertFalse(CheckValidity.checkAge(Age1));
		assertTrue(CheckValidity.checkAge(Age2));
		assertTrue(CheckValidity.checkAge(Age3));
		assertFalse(CheckValidity.checkAge(Age4));
		assertTrue(CheckValidity.checkAge(Age5));
	}

	@Test
	public void testCheckMarriageAge() {
		//fail("Not yet implemented");
		assertTrue(CheckValidity.checkMarriageAge(SpouseID1, Age2));
		assertTrue(CheckValidity.checkMarriageAge(SpouseID1, Age3));
		assertTrue(CheckValidity.checkMarriageAge(SpouseID1, Age6));
		assertFalse(CheckValidity.checkMarriageAge(SpouseID2, Age2));
		assertTrue(CheckValidity.checkMarriageAge(SpouseID2, Age3));
		assertFalse(CheckValidity.checkMarriageAge(SpouseID2, Age6));
	}

}
