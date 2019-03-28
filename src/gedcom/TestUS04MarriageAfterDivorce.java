package gedcom;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.*;

public class TestUS04MarriageAfterDivorce {
	HashMap<String, Individual> testIndividualMap;
	ArrayList<Family> testFamilyList;
	
	@Before
	public void setUp() {
		testIndividualMap = new HashMap<String, Individual>();
		testFamilyList = new ArrayList<Family>();
	}

	@Test
	public void testMarriageAfterDivorce() {
		
		Individual individual = new Individual();
		individual.setId("I100");
		testIndividualMap.put("I100", individual);

		individual = new Individual();
		individual.setId("I101");
		testIndividualMap.put("I101", individual);
		
		Family family = new Family();
		family.setId("F100");
		family.setHusbandId("I100");
		family.setWifeId("I101");
		family.setMarriageDate("25 DEC 2006");
		family.setDivorceDate("13 FEB 2005");
		testFamilyList.add(family);
		
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<ErrorData> recordError = DatesCheckInFamily.us04MarriageBeforeDivorce(Ftp);
		
		ErrorData error = new ErrorData();
		error.setIndividualId("I100");
		error.setErrorDetails("Marriage Date 2006-12-25 in the family F100 is after the divorce date 2005-02-13");
		
		assertEquals(error.getErrorDetails(),recordError.get(0).getErrorDetails());
	}
	
	@Test
	public void testMarriageBeforeDivorce() {
		
		Individual individual = new Individual();
		individual.setId("I200");
		testIndividualMap.put("I200", individual);

		individual = new Individual();
		individual.setId("I201");
		testIndividualMap.put("I201", individual);
		
		Family family = new Family();
		family.setId("F200");
		family.setHusbandId("I200");
		family.setWifeId("I201");
		family.setMarriageDate("25 DEC 2006");
		family.setDivorceDate("13 FEB 2015");
		testFamilyList.add(family);
		
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<ErrorData> recordError = DatesCheckInFamily.us04MarriageBeforeDivorce(Ftp);
				
		assertTrue(recordError.size() == 0);
	}
	
	@Test
	public void testMarriageBeforeDivorceWhenMarriageDateIsNull() {
		
		Individual individual = new Individual();
		individual.setId("I300");
		testIndividualMap.put("I300", individual);

		individual = new Individual();
		individual.setId("I301");
		testIndividualMap.put("I301", individual);
		
		Family family = new Family();
		family.setId("F300");
		family.setHusbandId("I300");
		family.setWifeId("I301");
		family.setDivorceDate("13 FEB 2015");
		testFamilyList.add(family);
		
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<ErrorData> recordError = DatesCheckInFamily.us04MarriageBeforeDivorce(Ftp);
				
		assertTrue(recordError.size() == 0);
	}
	
	@Test
	public void testMarriageBeforeDivorceWhenDivroceDateIsNull() {
		
		Individual individual = new Individual();
		individual.setId("I400");
		testIndividualMap.put("I400", individual);

		individual = new Individual();
		individual.setId("I401");
		testIndividualMap.put("I401", individual);
		
		Family family = new Family();
		family.setId("F400");
		family.setHusbandId("I400");
		family.setWifeId("I401");
		family.setMarriageDate("25 DEC 2006");
		testFamilyList.add(family);
		
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<ErrorData> recordError = DatesCheckInFamily.us04MarriageBeforeDivorce(Ftp);
				
		assertTrue(recordError.size() == 0);
	}


}
