package gedcom;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.*;

public class Test_US15FewerThanFifteenSiblings {

	HashMap<String, Individual> testIndividualMap;
	ArrayList<Family> testFamilyList;
	
	@Before
	public void setUp() {
		testIndividualMap = new HashMap<String, Individual>();
		testFamilyList = new ArrayList<Family>();
	}

	@Test
	public void testMoreThan15Siblings() {
		Individual individual = new Individual();
		individual.setId("I100");
		individual.setFamilySpouseId("F100");
		testIndividualMap.put("I100", individual);
		
		individual = new Individual();
		individual.setId("I101");
		individual.setFamilySpouseId("F100");
		testIndividualMap.put("I101", individual);
		
		individual = new Individual();
		individual.setId("I111");
		individual.setName("Alex Gabriel");
		individual.setFamilyChildId("F100");
		testIndividualMap.put("I111", individual);
		
		individual = new Individual();
		individual.setId("I112");
		individual.setFamilyChildId("F100");
		testIndividualMap.put("I112", individual);
		
		individual = new Individual();
		individual.setId("I113");
		individual.setFamilyChildId("F100");
		testIndividualMap.put("I113", individual);
		
		individual = new Individual();
		individual.setId("I114");
		individual.setFamilyChildId("F100");
		testIndividualMap.put("I114", individual);
		
		individual = new Individual();
		individual.setId("I115");
		individual.setFamilyChildId("F100");
		testIndividualMap.put("I115", individual);
		
		individual = new Individual();
		individual.setId("I116");
		individual.setFamilyChildId("F100");
		testIndividualMap.put("I116", individual);
		
		individual = new Individual();
		individual.setId("I117");
		individual.setFamilyChildId("F100");
		testIndividualMap.put("I117", individual);
		
		individual = new Individual();
		individual.setId("I118");
		individual.setFamilyChildId("F100");
		testIndividualMap.put("I118", individual);
		
		individual = new Individual();
		individual.setId("I119");
		individual.setFamilyChildId("F100");
		testIndividualMap.put("I119", individual);
		
		individual = new Individual();
		individual.setId("I120");
		individual.setFamilyChildId("F100");
		testIndividualMap.put("I120", individual);
		
		individual = new Individual();
		individual.setId("I121");
		individual.setFamilyChildId("F100");
		testIndividualMap.put("I121", individual);
		
		individual = new Individual();
		individual.setId("I122");
		individual.setFamilyChildId("F100");
		testIndividualMap.put("I122", individual);
		
		individual = new Individual();
		individual.setId("I123");
		individual.setFamilyChildId("F100");
		testIndividualMap.put("I123", individual);
		
		individual = new Individual();
		individual.setId("I124");
		individual.setFamilyChildId("F100");
		testIndividualMap.put("I124", individual);
		
		individual = new Individual();
		individual.setId("I125");
		individual.setFamilyChildId("F100");
		testIndividualMap.put("I125", individual);
		
		individual = new Individual();
		individual.setId("I126");
		individual.setFamilyChildId("F100");
		testIndividualMap.put("I126", individual);
		
		Family family = new Family();
		family.setId("F100");
		family.setHusbandId("I100");
		family.setWifeId("I101");
		family.setChildId("I111");
		family.setChildId("I112");
		family.setChildId("I113");
		family.setChildId("I114");
		family.setChildId("I115");
		family.setChildId("I116");
		family.setChildId("I117");
		family.setChildId("I118");
		family.setChildId("I119");
		family.setChildId("I120");
		family.setChildId("I121");
		family.setChildId("I122");
		family.setChildId("I123");
		family.setChildId("I124");
		family.setChildId("I125");
		family.setChildId("I126");
		testFamilyList.add(family);
		
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<ErrorData> recordError = US_Siblings.us15FewerThanFifteenSiblings(Ftp);
		
		ErrorData error = new ErrorData();
		error.setIndividualId("F100");
		error.setErrorDetails("The child I111 (Alex Gabriel) has more than 15 siblings");
		
		assertEquals(error.getErrorDetails(), recordError.get(0).getErrorDetails());
	}
	
	@Test
	public void testLessThan15Siblings() {
		Individual individual = new Individual();
		individual.setId("I200");
		individual.setFamilySpouseId("F200");
		testIndividualMap.put("I200", individual);
		
		individual = new Individual();
		individual.setId("I201");
		individual.setFamilySpouseId("F200");
		testIndividualMap.put("I201", individual);
		
		individual = new Individual();
		individual.setId("I211");
		individual.setFamilyChildId("F200");
		testIndividualMap.put("I211", individual);
		
		individual = new Individual();
		individual.setId("I212");
		individual.setFamilyChildId("F200");
		testIndividualMap.put("I212", individual);
		
		Family family = new Family();
		family.setId("F200");
		family.setHusbandId("I200");
		family.setWifeId("I201");
		family.setChildId("I211");
		family.setChildId("I212");
		testFamilyList.add(family);
		
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<ErrorData> recordError = US_Siblings.us15FewerThanFifteenSiblings(Ftp);
		
		assertTrue(recordError.size()==0);
	}
}
