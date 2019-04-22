package gedcom;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class Test_US25_UniqueNamesBirthdate {
	
	HashMap<String, Individual> testIndividualMap;
	ArrayList<Family> testFamilyList;

	@Before
	public void setUp() {
		testIndividualMap = new HashMap<String, Individual>();
		testFamilyList = new ArrayList<Family>();
	}

	@Test
	public void testUniqueNamesBirthdate() {
		
		Individual individual = new Individual();
		individual.setId("I1122");
		individual.setName("John Smiths");
		individual.setBirthDate("25 DEC 2010");
		testIndividualMap.put("I1122", individual);
		
		individual = new Individual();
		individual.setId("I1123");
		individual.setName("John Smiths");
		individual.setBirthDate("25 DEC 2010");
		testIndividualMap.put("I1123", individual);
		
		Family family = new Family();
		family.setId("F12345");
		family.setChildId("I1122");
		family.setChildId("I1123");
		testFamilyList.add(family);

		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<ErrorData> errorRecord = US_Unique_Names.US25_Unique_Names_BirthDate(Ftp);
		
		assertTrue(errorRecord.size() == 1);
	}
	
	@Test
	public void testUniqueNamesBirthdate2() {
		
		Individual individual = new Individual();
		individual.setId("I9898");
		individual.setName("Allan Smiths");
		individual.setBirthDate("25 DEC 2010");
		testIndividualMap.put("I9898", individual);
		
		individual = new Individual();
		individual.setId("I9999");
		individual.setName("Khole Smiths");
		individual.setBirthDate("25 DEC 2011");
		testIndividualMap.put("I9999", individual);
		
		Family family = new Family();
		family.setId("F6789");
		family.setChildId("I9898");
		family.setChildId("I9999");
		testFamilyList.add(family);

		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<ErrorData> errorRecord = US_Unique_Names.US25_Unique_Names_BirthDate(Ftp);
		
		assertTrue(errorRecord.size() == 0);
	}
}
