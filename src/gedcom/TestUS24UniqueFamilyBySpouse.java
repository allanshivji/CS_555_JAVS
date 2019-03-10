package gedcom;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class TestUS24UniqueFamilyBySpouse {
	HashMap<String, Individual> testIndividualMap;
	ArrayList<Family> testFamilyList;

	@Before
	public void setUp() {
		testIndividualMap = new HashMap<String, Individual>();
		testFamilyList = new ArrayList<Family>();
	}

	@Test
	public void testUniqueFamilyBySpouseError() {
		Individual individual = new Individual();
		individual.setId("I700");
		individual.setName("Donald Draper");
		testIndividualMap.put("I700", individual);

		individual = new Individual();
		individual.setId("I701");
		individual.setName("Betty Draper");
		testIndividualMap.put("I701", individual);

		Family family = new Family();
		family.setId("F700");
		family.setHusbandId("I700");
		family.setWifeId("I701");
		family.setMarriageDate("25 DEC 2006");
		testFamilyList.add(family);
		
		family = new Family();
		family.setId("F701");
		family.setHusbandId("I700");
		family.setWifeId("I701");
		family.setMarriageDate("25 DEC 2006");
		testFamilyList.add(family);

		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<ErrorData> recordError = UniqueFamilyBySpouses.US_24_Method_To_Find_Unique_Family_By_Spouse_In_Given_Family_Gedcom_Data(Ftp);
		
		ErrorData error = new ErrorData();
		error.setIndividualId("F701");
		error.setErrorDetails("The husband Donald Draper(I700) and wife Betty Draper(I701) have more than one family record with the same marriage date 2006-12-25" );
		System.out.println(recordError.get(0).getErrorDetails());
		System.out.println(error.getErrorDetails());
		assertEquals(error.getErrorDetails(), recordError.get(0).getErrorDetails());
	}
	
	@Test
	public void testUniqueFamilyBySpouseSuccess() {
		Individual individual = new Individual();
		individual.setId("I700");
		individual.setName("Donald Jones");
		testIndividualMap.put("I700", individual);

		individual = new Individual();
		individual.setId("I701");
		individual.setName("Kate Jones");
		testIndividualMap.put("I701", individual);
		
		individual = new Individual();
		individual.setId("I702");
		individual.setName("Rachel Jones");
		testIndividualMap.put("I702", individual);

		Family family = new Family();
		family.setId("F700");
		family.setHusbandId("I700");
		family.setWifeId("I701");
		family.setMarriageDate("25 DEC 2006");
		family.setDivorceDate("12 AUG 2011");
		testFamilyList.add(family);
		
		family = new Family();
		family.setId("F701");
		family.setHusbandId("I700");
		family.setWifeId("I702");
		family.setMarriageDate("15 OCT 2014");
		testFamilyList.add(family);

		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<ErrorData> recordError = UniqueFamilyBySpouses.US_24_Method_To_Find_Unique_Family_By_Spouse_In_Given_Family_Gedcom_Data(Ftp);
			
		assertTrue(recordError.size()==0);
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
		ArrayList<ErrorData> recordError = UniqueFamilyBySpouses.findDuplicateSpousedetails(Ftp);
		
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
		ArrayList<ErrorData> recordError = UniqueFamilyBySpouses.findDuplicateSpousedetails(Ftp);
			
		assertTrue(recordError.size()==0);
	}
	
}
