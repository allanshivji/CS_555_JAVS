package gedcom;

import static org.junit.Assert.*;
import java.util.*;

import org.junit.Before;
import org.junit.*;

public class Test_US21CheckGenderRole {
	HashMap<String, Individual> testIndividualMap;
	ArrayList<Family> testFamilyList;
	
	@Before
	public void setUp() {
		testIndividualMap = new HashMap<String, Individual>();
		testFamilyList = new ArrayList<Family>();
	}

	@Test
	public void testGenderRoleHusband() {
		
		Individual individual = new Individual();
		individual.setId("I009");
		individual.setName("James /Bond/");
		individual.setGender("M");
		testIndividualMap.put("I009", individual);
		
		individual = new Individual();
		individual.setId("I0010");
		individual.setName("Jess /Bond/");
		individual.setGender("M");
		testIndividualMap.put("I0010", individual);
		
		Family family = new Family();
		family.setId("F0009");
		family.setHusbandId("I009");
		family.setWifeId("I0010");
		family.setMarriageDate("22 DEC 2009");
		testFamilyList.add(family);
		
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<ErrorData> recordError = US_MultiIndividualFamilyData.US21_check_Gender_Role(Ftp);
		
		ErrorData error = new ErrorData();
		error.setIndividualId("I009");
		error.setErrorDetails("Gender of Husband James /Bond/ of family F0009 is M");
		
		assertEquals(error.getErrorDetails(), recordError.get(0).getErrorDetails());

	}
	
	@Test
	public void testGenderRoleWife() {
		
		Individual individual = new Individual();
		individual.setId("I009");
		individual.setName("James /Bond/");
		individual.setGender("M");
		testIndividualMap.put("I009", individual);
		
		individual = new Individual();
		individual.setId("I0010");
		individual.setName("Jess /Bond/");
		individual.setGender("M");
		testIndividualMap.put("I0010", individual);
		
		Family family = new Family();
		family.setId("F0009");
		family.setHusbandId("I009");
		family.setWifeId("I0010");
		family.setMarriageDate("22 DEC 2009");
		testFamilyList.add(family);
		
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<ErrorData> recordError = US_MultiIndividualFamilyData.US21_check_Gender_Role(Ftp);
		
		ErrorData error = new ErrorData();
		error.setIndividualId("I009");
		error.setErrorDetails("Gender of Wife Jess /Bond/ of family F0009 is M");
		
		assertEquals(error.getErrorDetails(), recordError.get(1).getErrorDetails());

	}

}
