package gedcom;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class DateCheck {
	HashMap<String,Individual> testIndividualMap;
	ArrayList<Family> testFamilyList;
	@Before
	public void setUp() {
		testIndividualMap = new HashMap<String,Individual>();
		testFamilyList = new ArrayList<Family>();

	}
	
	@Test
	public void testCheckMarriageDatesBeforeCurrentDate1() {
//		fail("Not yet implemented");

	}
	@Test
	public void testCheckMarriageDatesBeforeCurrentDate() {
//		fail("Not yet implemented");
	}
//		return errorList;
}
