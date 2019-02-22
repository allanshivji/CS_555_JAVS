package gedcom;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class TestUS08BirthBeforeMarriageOfParents {
	HashMap<String,Individual> testIndividualMap;
	ArrayList<Family> testFamilyList;
	
	@Before
	public void setUp() {
		testIndividualMap = new HashMap<String,Individual>();
		testFamilyList = new ArrayList<Family>();
	}

	@Test
	public void testBirthBeforeMarriage() {	
		Individual individual = new Individual();
		individual.setId("I001");
		individual.setBirthDate("05 APR 2011");
		individual.setFamilyChildId("F001");
		testIndividualMap.put("I001", individual);
		
        individual = new Individual();
		individual.setId("I007");
		individual.setBirthDate("05 APR 2014");
		individual.setFamilyChildId("F001");
		testIndividualMap.put("I007", individual);
			
		Family family = new Family();
		family.setId("F001");
		family.setMarriageDate("06 MAY 2012");
		family.setChildId("I001");
		family.setChildId("I007");
		testFamilyList.add(family);
		
		ErrorData error = new ErrorData();
		error.setIndividualId("I001");
		error.setErrorDetails("The child I001 having birth date 2011-04-05 was born before the marriage date 2012-05-06 of parents");
		
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<ErrorData> erraneousRecord = MultiIndividualFamilyData.US08_Birth_Before_Marriage_Of_Parents(Ftp);
		
		assertEquals(error.getErrorDetails(), erraneousRecord.get(0).getErrorDetails());
	}
	
	@Test
	public void testBirthNineMonthsAfterDivorce() {	
		Individual individual = new Individual();
		individual.setId("I002");
		individual.setBirthDate("15 OCT 2009");
		individual.setFamilyChildId("F002");
		testIndividualMap.put("I002", individual);
			
		Family family = new Family();
		family.setId("F002");
		family.setMarriageDate("16 SEP 1999");
		family.setDivorceDate("23 OCT 2008");
		family.setChildId("I002");
		testFamilyList.add(family);
		
		ErrorData error = new ErrorData();
		error.setIndividualId("I002");
		error.setErrorDetails("The child I002 having birth date 2009-10-15 was born 9 months after the divorce date 2008-10-23 of parents");
		
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<ErrorData> erraneousRecord = MultiIndividualFamilyData.US08_Birth_Before_Marriage_Of_Parents(Ftp);
		
		assertEquals(error.getErrorDetails(), erraneousRecord.get(0).getErrorDetails());
	}
	
	@Test
	public void testBirthAfterDivorceBeforeNineMonths() {		
		Individual individual = new Individual();
		individual.setId("I003");
		individual.setBirthDate("25 NOV 2010");
		individual.setFamilyChildId("F003");
		testIndividualMap.put("I003", individual);
			
		Family family = new Family();
		family.setId("F003");
		family.setMarriageDate("16 JUN 2006");
		family.setDivorceDate("23 MAR 2010");
		family.setChildId("I003");
		testFamilyList.add(family);

		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<ErrorData> erraneousRecord = MultiIndividualFamilyData.US08_Birth_Before_Marriage_Of_Parents(Ftp);
		assertTrue(erraneousRecord.size()==0);
	}
	
	@Test
	public void testWhenMarriageDateIsNull() {		
		Individual individual = new Individual();
		individual.setId("I004");
		individual.setBirthDate("13 MAR 2005");
		individual.setFamilyChildId("F004");
		testIndividualMap.put("I004", individual);
			
		Family family = new Family();
		family.setId("F004");
		family.setDivorceDate("25 APR 2010");
		family.setChildId("I004");
		testFamilyList.add(family);

		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<ErrorData> erraneousRecord = MultiIndividualFamilyData.US08_Birth_Before_Marriage_Of_Parents(Ftp);
		assertTrue(erraneousRecord.size()==0);
	}
	
	@Test
	public void testWhenDivroceDateIsNull() {
		Individual individual = new Individual();
		individual.setId("I005");
		individual.setBirthDate("08 FEB 2003");
		individual.setFamilyChildId("F005");
		testIndividualMap.put("I005", individual);
			
		Family family = new Family();
		family.setId("F005");
		family.setMarriageDate("15 APR 2002");
		family.setChildId("I005");
		testFamilyList.add(family);

		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<ErrorData> erraneousRecord = MultiIndividualFamilyData.US08_Birth_Before_Marriage_Of_Parents(Ftp);
		assertTrue(erraneousRecord.size()==0);
	}
	
	@Test
	public void testWhenChildIdIsNull() {		
		Family family = new Family();
		family.setId("F006");
		family.setMarriageDate("15 APR 2002");
		family.setDivorceDate("24 AUG 2012");
		testFamilyList.add(family);

		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<ErrorData> erraneousRecord = MultiIndividualFamilyData.US08_Birth_Before_Marriage_Of_Parents(Ftp);
		assertTrue(erraneousRecord.size()==0);
	}
	
	@Test
	public void testWhenMarriageDateDivorceDateIsNull() {	
		Individual individual = new Individual();
		individual.setId("I006");
		individual.setBirthDate("08 FEB 2002");
		individual.setFamilyChildId("F006");
		testIndividualMap.put("I006", individual);
			
		Family family = new Family();
		family.setId("F006");
		family.setChildId("I006");
		testFamilyList.add(family);

		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<ErrorData> erraneousRecord = MultiIndividualFamilyData.US08_Birth_Before_Marriage_Of_Parents(Ftp);
		assertTrue(erraneousRecord.size()==0);
		
	}

}
