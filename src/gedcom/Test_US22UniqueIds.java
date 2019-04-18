package gedcom;

import static org.junit.Assert.*;

import java.util.*;
import org.junit.Before;
import org.junit.*;

public class Test_US22UniqueIds {

	HashMap<String, Individual> testIndividualMap;
	ArrayList<Family> testFamilyList;

	@Before
	public void setUp() {
		testIndividualMap = new HashMap<String, Individual>();
		testFamilyList = new ArrayList<Family>();
	}

	@Test
	public void checkFamilyUniqueId() {

		Individual individual = new Individual();

		Family family = new Family();
		family.setId("F221195");
		testFamilyList.add(family);

		family = new Family();
		family.setId("F221195");
		testFamilyList.add(family);

		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<ErrorData> recordError = US_CheckUniqueness.US22_check_Unique_FamilyId(Ftp);

		ErrorData error = new ErrorData();
		error.setIndividualId("F221195");
		error.setErrorDetails("Family id F221195 is duplicated.");

		assertEquals(error.getErrorDetails(), recordError.get(0).getErrorDetails());
	}

	@Test
	public void checkFamilyUniqueId2() {

		Individual individual = new Individual();

		Family family = new Family();
		family.setId("F221195555");
		testFamilyList.add(family);

		family = new Family();
		family.setId("F2211955555");
		testFamilyList.add(family);

		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<ErrorData> recordError = US_CheckUniqueness.US22_check_Unique_FamilyId(Ftp);

		ErrorData error = new ErrorData();
		error.setIndividualId("F2211955555");
		error.setErrorDetails("Family id F2211955555 is duplicated.");

		assertTrue(recordError.size() == 0);
	}

}
