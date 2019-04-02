package gedcom;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Before;
import org.junit.*;

public class Test_US02_US03 {
	
	HashMap<String, Individual> testIndividualMap;
	ArrayList<Family> testFamilyList;

	@Before
	public void setUp() {
		testIndividualMap = new HashMap<String, Individual>();
		testFamilyList = new ArrayList<Family>();
	}

	// Allan: US03 - Birth Before Death Test case
	@Test
	public void testUS03_check_Birth_Before_Death() {

		Individual individual = new Individual();
		individual.setId("I0022");
		individual.setBirthDate("22 NOV 1995");
		individual.setDeathDate("21 NOV 1994");
		individual.setName("Jony /Doe/");
		testIndividualMap.put("I0022", individual);

		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<ErrorData> recordError = US_DatesCheckInFamily.US03_check_Birth_Before_Death(Ftp);
		
		ErrorData error = new ErrorData();
		error.setIndividualId("I0022");
		error.setErrorDetails("Birthdate 1995-11-22 of Jony /Doe/ (I0022) is after Death Date 1994-11-21");
		
		assertEquals(error.getErrorDetails(),recordError.get(0).getErrorDetails());
	}

	 //Allan: US02 - Birth Before Marriage Test case
	
	@Test
	public void testUS02_check_Birth_Before_Marriage_ofHusb() {
		
		Individual individual = new Individual();
		individual.setId("I1111");
		individual.setBirthDate("13 OCT 1970");
		individual.setName("Jack /Smiths/");
		testIndividualMap.put("I1111", individual);
		
		individual = new Individual();
		individual.setId("I1110");
		individual.setBirthDate("11 MAR 1969");
		individual.setName("Nikki /Smiths/");
		testIndividualMap.put("I1110", individual);
		
		Family family = new Family();
		family.setId("F111");
		family.setHusbandId("I1111");
		family.setWifeId("I1110");
		family.setMarriageDate("12 OCT 1969");
		testFamilyList.add(family);
		
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap,testFamilyList);
		ArrayList<ErrorData> recordError = US_DatesCheckInFamily.US02_check_Birth_Before_Marriage(Ftp);
		
		ErrorData error = new ErrorData();
		error.setIndividualId("I1111");
		error.setErrorDetails("MarriageDate 1969-10-12 of Jack /Smiths/ (I1111) is after Birth date 1970-10-13");

		assertEquals(error.getErrorDetails(),recordError.get(0).getErrorDetails());
	}
	
	@Test
	public void testUS02_check_Birth_Before_Marriage_ofWife() {
		
		Individual individual = new Individual();
		individual.setId("I1111");
		individual.setBirthDate("13 OCT 1968");
		individual.setName("Jack /Smiths/");
		testIndividualMap.put("I1111", individual);
		
		individual = new Individual();
		individual.setId("I1110");
		individual.setBirthDate("11 MAR 1970");
		individual.setName("Nikki /Smiths/");
		testIndividualMap.put("I1110", individual);
		
		Family family = new Family();
		family.setId("F111");
		family.setHusbandId("I1111");
		family.setWifeId("I1110");
		family.setMarriageDate("12 OCT 1969");
		testFamilyList.add(family);
		
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap,testFamilyList);
		ArrayList<ErrorData> recordError = US_DatesCheckInFamily.US02_check_Birth_Before_Marriage(Ftp);
		
		ErrorData error = new ErrorData();
		error.setIndividualId("I1111");
		error.setErrorDetails("MarriageDate 1969-10-12 of Nikki /Smiths/ (I1110) is after Birth date 1970-03-11");

		assertEquals(error.getErrorDetails(),recordError.get(0).getErrorDetails());
	}
	
	@Test
	public void testUS02_check_Birth_Before_Marriage() {
		
		Individual individual = new Individual();
		individual.setId("I1010");
		individual.setBirthDate("13 OCT 1970");
		individual.setName("Jhon /Cena/");
		testIndividualMap.put("I1010", individual);
		
		individual = new Individual();
		individual.setId("I1011");
		individual.setBirthDate("11 MAR 1970");
		individual.setName("Nikki /Cena/");
		testIndividualMap.put("I1011", individual);
		
		Family family = new Family();
		family.setId("F999");
		family.setHusbandId("I1010");
		family.setWifeId("I1011");
		family.setMarriageDate("25 DEC 1950");
		testFamilyList.add(family);
		
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap,testFamilyList);
		ArrayList<ErrorData> recordError = US_DatesCheckInFamily.US02_check_Birth_Before_Marriage(Ftp);
		assertTrue(recordError.size()!=0);	
	}
}