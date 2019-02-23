package gedcom;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.*;

public class TestUS05MarriageBeforeDeath {
	HashMap<String,Individual> testIndividualMap;
	ArrayList<Family> testFamilyList;
	
	@Before
	public void setUp() {
		testIndividualMap = new HashMap<String,Individual>();
		testFamilyList = new ArrayList<Family>();
	}

	@Test
	public void testMarrigeAfterDeathforHusband() {
		Individual individual = new Individual();
		individual.setId("I100");
		individual.setDeathDate("12 APR 2019");
		testIndividualMap.put("I100",individual);
		
		individual = new Individual();
		individual.setId("I101");
		individual.setDeathDate("12 APR 2005");
		testIndividualMap.put("I101",individual);
		
		Family family = new Family();
		family.setId("F100");
		family.setHusbandId("I100");
		family.setWifeId("I101");
		family.setMarriageDate("25 DEC 2006");
		testFamilyList.add(family);
		
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap,testFamilyList);
		ArrayList<ErrorData> recordError = MultiIndividualFamilyData.US05_Marriage_Before_Death(Ftp);
		
		ErrorData error = new ErrorData();
		error.setIndividualId("I100");
		error.setErrorDetails("MarriageDate 2006-12-25 for I100 is after his wife's (I101) death date 2005-04-12");
		
		assertEquals(error.getErrorDetails(),recordError.get(0).getErrorDetails());
	}
	
	@Test
	public void testMarrigeAfterDeathforHusbandAndWife() {
		Individual individual = new Individual();
		individual.setId("I400");
		individual.setDeathDate("12 MAR 1998");
		testIndividualMap.put("I400",individual);
		
		individual = new Individual();
		individual.setId("I401");
		individual.setDeathDate("22 NOV 1999");
		testIndividualMap.put("I401",individual);
		
		Family family = new Family();
		family.setId("F400");
		family.setHusbandId("I400");
		family.setWifeId("I401");
		family.setMarriageDate("25 DEC 2001");
		testFamilyList.add(family);
		
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap,testFamilyList);
		ArrayList<ErrorData> recordError = MultiIndividualFamilyData.US05_Marriage_Before_Death(Ftp);
		
		ErrorData error = new ErrorData();
		error.setIndividualId("I400");
		error.setErrorDetails("MarriageDate 2001-12-25 for I400 is after his wife's (I401) death date 1999-11-22");
		assertEquals(error.getErrorDetails(),recordError.get(0).getErrorDetails());
		
		error = new ErrorData();
		error.setIndividualId("I401");
		error.setErrorDetails("MarriageDate 2001-12-25 for I401 is after her husband's (I400) death date 1998-03-12");
		assertEquals(error.getErrorDetails(),recordError.get(1).getErrorDetails());
		
	}
	
	@Test
	public void testMarrigeAfterDeathforWife() {
		Individual individual = new Individual();
		individual.setId("I200");
		individual.setDeathDate("22 MAR 2005");
		testIndividualMap.put("I200",individual);
		
		individual = new Individual();
		individual.setId("I201");
		individual.setDeathDate("12 APR 2018");
		testIndividualMap.put("I201",individual);
		
		Family family = new Family();
		family.setId("F200");
		family.setHusbandId("I200");
		family.setWifeId("I201");
		family.setMarriageDate("25 DEC 2006");
		testFamilyList.add(family);
		
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap,testFamilyList);
		ArrayList<ErrorData> recordError = MultiIndividualFamilyData.US05_Marriage_Before_Death(Ftp);
		
		ErrorData error = new ErrorData();
		error.setIndividualId("I201");
		error.setErrorDetails("MarriageDate 2006-12-25 for I201 is after her husband's (I200) death date 2005-03-22");
		
		assertEquals(error.getErrorDetails(),recordError.get(0).getErrorDetails());
	}

	@Test
	public void testMarrigeBeforeDeath() {
		Individual individual = new Individual();
		individual.setId("I300");
		individual.setDeathDate("12 APR 2011");
		testIndividualMap.put("I300",individual);
		
		individual = new Individual();
		individual.setId("I301");
		individual.setDeathDate("12 APR 2001");
		testIndividualMap.put("I301",individual);
		
		Family family = new Family();
		family.setId("F300");
		family.setHusbandId("I300");
		family.setWifeId("I301");
		family.setMarriageDate("25 DEC 1982");
		testFamilyList.add(family);
		
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap,testFamilyList);
		ArrayList<ErrorData> recordError = MultiIndividualFamilyData.US05_Marriage_Before_Death(Ftp);

		assertTrue(recordError.size()==0);
	}
	
	@Test
	public void testMarrigeBeforeDeathWhenDeathDateIsNull() {
		Individual individual = new Individual();
		individual.setId("I500");
		testIndividualMap.put("I500",individual);
		
		individual = new Individual();
		individual.setId("I501");
		//individual.setDeathDate("18 JAN 2001");
		testIndividualMap.put("I501",individual);
		
		Family family = new Family();
		family.setId("F500");
		family.setHusbandId("I500");
		family.setWifeId("I501");
		family.setMarriageDate("25 FEB 1982");
		testFamilyList.add(family);
		
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap,testFamilyList);
		ArrayList<ErrorData> recordError = MultiIndividualFamilyData.US05_Marriage_Before_Death(Ftp);

		assertTrue(recordError.size()==0);
	}
	
	public void testMarrigeBeforeDeathWhenMarriageDateIsNull() {
		Individual individual = new Individual();
		individual.setId("I600");
		individual.setDeathDate("18 JUN 1980");
		testIndividualMap.put("I600",individual);
		
		individual = new Individual();
		individual.setId("I601");
		individual.setDeathDate("18 JAN 1990");
		testIndividualMap.put("I501",individual);
		
		Family family = new Family();
		family.setId("F600");
		family.setHusbandId("I600");
		family.setWifeId("I601");
		testFamilyList.add(family);
		
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap,testFamilyList);
		ArrayList<ErrorData> recordError = MultiIndividualFamilyData.US05_Marriage_Before_Death(Ftp);

		assertTrue(recordError.size()==0);
	}
}
