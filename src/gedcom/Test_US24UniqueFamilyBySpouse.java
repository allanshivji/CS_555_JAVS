package gedcom;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.*;

public class Test_US24UniqueFamilyBySpouse {
	HashMap<String, Individual> testIndividualMap;
	ArrayList<Family> testFamilyList;

	@Before
	public void setUp() {
		testIndividualMap = new HashMap<String, Individual>();
		testFamilyList = new ArrayList<Family>();
	}


	@Test
	public void testUniqueFamilyBySpouseRefactoredError() {
		Individual individual = new Individual();
		individual.setId("I600");
		individual.setName("Donald Draper");
		testIndividualMap.put("I600", individual);

		individual = new Individual();
		individual.setId("I601");
		individual.setName("Betty Draper");
		testIndividualMap.put("I601", individual);

		Family family = new Family();
		family.setId("F600");
		family.setHusbandId("I600");
		family.setWifeId("I601");
		family.setMarriageDate("25 DEC 2006");
		testFamilyList.add(family);
		
		family = new Family();
		family.setId("F601");
		family.setHusbandId("I600");
		family.setWifeId("I601");
		family.setMarriageDate("25 DEC 2006");
		testFamilyList.add(family);

		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<ErrorData> recordError = US_CheckUniqueness.findDuplicateSpousedetails(Ftp);
		
		ErrorData error = new ErrorData();
		error.setIndividualId("F601");
		error.setErrorDetails("The husband Donald Draper(I600) and wife Betty Draper(I601) have more than one family record with the same marriage date 2006-12-25" );
		
		assertEquals(error.getErrorDetails(), recordError.get(0).getErrorDetails());
	}
	
	@Test
	public void testUniqueFamilyBySpouseRefactoredSuccess() {
		Individual individual = new Individual();
		individual.setId("I600");
		individual.setName("Donald Draper");
		testIndividualMap.put("I600", individual);

		individual = new Individual();
		individual.setId("I601");
		individual.setName("Betty Draper");
		testIndividualMap.put("I601", individual);
		
		individual = new Individual();
		individual.setId("I602");
		individual.setName("Rachel Draper");
		testIndividualMap.put("I602", individual);

		Family family = new Family();
		family.setId("F600");
		family.setHusbandId("I600");
		family.setWifeId("I601");
		family.setMarriageDate("25 DEC 2006");
		family.setDivorceDate("12 AUG 2011");
		testFamilyList.add(family);
		
		family = new Family();
		family.setId("F601");
		family.setHusbandId("I600");
		family.setWifeId("I602");
		family.setMarriageDate("15 OCT 2014");
		testFamilyList.add(family);

		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<ErrorData> recordError = US_CheckUniqueness.findDuplicateSpousedetails(Ftp);
			
		assertTrue(recordError.size()==0);
	}
	
}
