package gedcom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.*;

public class Test_US09BirthAfterDeathOfParents {
	HashMap<String, Individual> testIndividualMap;
	ArrayList<Family> testFamilyList;

	@Before
	public void setUp() {
		testIndividualMap = new HashMap<String, Individual>();
		testFamilyList = new ArrayList<Family>();
	}
	
	@Test
	public void testBirthAfterDeathOfMother() {
		Individual individual = new Individual();
		individual.setId("I100");
		individual.setName("Daniel Birt");
		individual.setBirthDate("02 MAY 1976");
		individual.setFamilySpouseId("F100");
		testIndividualMap.put("I100", individual);
		
		individual =  new Individual();
		individual.setId("I101");
		individual.setName("Carol Birt");
		individual.setBirthDate("14 OCT 1980");
		individual.setDeathDate("10 MAR 2016");
		individual.setFamilySpouseId("F100");
		testIndividualMap.put("I101", individual);
		
		individual =  new Individual();
		individual.setId("I102");
		individual.setName("James Birt");
		individual.setBirthDate("24 FEB 2018");
		individual.setFamilyChildId("F100");
		testIndividualMap.put("I102", individual);
		
		Family family = new Family();
		family.setId("F100");
		family.setHusbandId("I100");
		family.setWifeId("I101");
		family.setChildId("I102");
		family.setMarriageDate("12 JAN 2004");
		testFamilyList.add(family);
		
		ErrorData error = new ErrorData();
		error.setErrorType("ERROR");
		error.setIndividualId("I102");
		error.setErrorDetails("The birthdate 2018-02-24 of James Birt (I102) is after the death date 2016-03-10 of mother(I101)");
		
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<ErrorData> erraneousRecord = US_DatesCheckInFamily.US09_findBirthBeforeDeathOfParents(Ftp);
		
		assertEquals(error.getErrorDetails(), erraneousRecord.get(0).getErrorDetails());
		
	}

	@Test
	public void testBirthAfterDeathOfFather() {
		Individual individual = new Individual();
		individual.setId("I200");
		individual.setName("Daniel Birt");
		individual.setBirthDate("02 MAY 1976");
		individual.setDeathDate("01 JAN 2015");
		individual.setFamilySpouseId("F200");
		testIndividualMap.put("I200", individual);
		
		individual =  new Individual();
		individual.setId("I201");
		individual.setName("Carol Birt");
		individual.setBirthDate("14 OCT 1980");
		individual.setFamilySpouseId("F200");
		testIndividualMap.put("I201", individual);
		
		individual =  new Individual();
		individual.setId("I202");
		individual.setName("James Birt");
		individual.setBirthDate("02 OCT 2015");
		individual.setFamilyChildId("F200");
		testIndividualMap.put("I202", individual);
		
		Family family = new Family();
		family.setId("F200");
		family.setHusbandId("I200");
		family.setWifeId("I201");
		family.setChildId("I202");
		family.setMarriageDate("12 JAN 2007");
		testFamilyList.add(family);
		
		ErrorData error = new ErrorData();
		error.setErrorType("ERROR");
		error.setIndividualId("I202");
		error.setErrorDetails("The birthdate 2015-10-02 of James Birt (I202) is nine months after the death date 2015-01-01 of father(I200)");
		
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<ErrorData> erraneousRecord = US_DatesCheckInFamily.US09_findBirthBeforeDeathOfParents(Ftp);
		
		assertEquals(error.getErrorDetails(), erraneousRecord.get(0).getErrorDetails());
		
	}
	
	@Test
	public void testBirthAfterDeathOfMotherFather() {
		Individual individual = new Individual();
		individual.setId("I300");
		individual.setName("Daniel Birt");
		individual.setBirthDate("02 MAY 1976");
		individual.setDeathDate("01 JAN 2015");
		individual.setFamilySpouseId("F300");
		testIndividualMap.put("I300", individual);
		
		individual =  new Individual();
		individual.setId("I301");
		individual.setName("Carol Birt");
		individual.setBirthDate("14 OCT 1980");
		individual.setDeathDate("10 APR 2009");
		individual.setFamilySpouseId("F300");
		testIndividualMap.put("I301", individual);
		
		individual =  new Individual();
		individual.setId("I302");
		individual.setName("James Birt");
		individual.setBirthDate("02 JAN 2016");
		individual.setFamilyChildId("F300");
		testIndividualMap.put("I302", individual);
		
		Family family = new Family();
		family.setId("F300");
		family.setHusbandId("I300");
		family.setWifeId("I301");
		family.setChildId("I302");
		family.setMarriageDate("12 JAN 2001");
		testFamilyList.add(family);
		
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<ErrorData> recordError = US_DatesCheckInFamily.US09_findBirthBeforeDeathOfParents(Ftp);
		
		ErrorData error = new ErrorData();
		error.setErrorType("ERROR");
		error.setIndividualId("I302");
		error.setErrorDetails("The birthdate 2016-01-02 of James Birt (I302) is after the death date 2009-04-10 of mother(I301)");
		assertEquals(error.getErrorDetails(), recordError.get(0).getErrorDetails());
		
		error = new ErrorData();
		error.setErrorType("ERROR");
		error.setIndividualId("I302");
		error.setErrorDetails("The birthdate 2016-01-02 of James Birt (I302) is nine months after the death date 2015-01-01 of father(I300)");
		assertEquals(error.getErrorDetails(), recordError.get(1).getErrorDetails());
	}
	
	@Test
	public void testBirthBeforeDeathOfMotherFather() {
		Individual individual = new Individual();
		individual.setId("I400");
		individual.setName("Daniel Birt");
		individual.setBirthDate("02 MAY 1976");
		individual.setDeathDate("01 JAN 2019");
		individual.setFamilySpouseId("F400");
		testIndividualMap.put("I400", individual);
		
		individual =  new Individual();
		individual.setId("I401");
		individual.setName("Carol Birt");
		individual.setBirthDate("14 OCT 1980");
		individual.setDeathDate("01 APR 2019");
		individual.setFamilySpouseId("F400");
		testIndividualMap.put("I401", individual);
		
		individual =  new Individual();
		individual.setId("I402");
		individual.setName("James Birt");
		individual.setBirthDate("02 JAN 2019");
		individual.setFamilyChildId("F400");
		testIndividualMap.put("I402", individual);
		
		Family family = new Family();
		family.setId("F400");
		family.setHusbandId("I400");
		family.setWifeId("I401");
		family.setChildId("I402");
		family.setMarriageDate("12 JAN 2010");
		testFamilyList.add(family);
		
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<ErrorData> recordError = US_DatesCheckInFamily.US09_findBirthBeforeDeathOfParents(Ftp);

		assertTrue(recordError.size()==0);
		
	}
	
	@Test
	public void testBirthWhenMotherFatherAlive() {
		Individual individual = new Individual();
		individual.setId("I500");
		individual.setName("Daniel Birt");
		individual.setBirthDate("02 MAY 1976");
		individual.setFamilySpouseId("F500");
		testIndividualMap.put("I500", individual);
		
		individual =  new Individual();
		individual.setId("I501");
		individual.setName("Carol Birt");
		individual.setBirthDate("14 OCT 1980");
		individual.setFamilySpouseId("F500");
		testIndividualMap.put("I501", individual);
		
		individual =  new Individual();
		individual.setId("I502");
		individual.setName("James Birt");
		individual.setBirthDate("02 JAN 2019");
		individual.setFamilyChildId("F500");
		testIndividualMap.put("I502", individual);
		
		Family family = new Family();
		family.setId("F500");
		family.setHusbandId("I500");
		family.setWifeId("I501");
		family.setChildId("I502");
		family.setMarriageDate("12 JAN 2010");
		testFamilyList.add(family);
		
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<ErrorData> recordError = US_DatesCheckInFamily.US09_findBirthBeforeDeathOfParents(Ftp);

		assertTrue(recordError.size()==0);
		
	}
}
