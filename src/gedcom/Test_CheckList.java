package gedcom;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.*;

public class Test_CheckList {
	
	HashMap<String,Individual> testIndividualMap;
	ArrayList<Family> testFamilyList;
	@Before
	public void setUp() {
		testIndividualMap = new HashMap<String,Individual>();
		testFamilyList = new ArrayList<Family>();
	}
	
	// Allan: Sprint 3 US30 check list of Living Married
	@Test
	public void test_Check_LivingMarried(){
		Individual indi = new Individual();
		indi.setId("J009");
		indi.setName("Allan /Smith/");
		indi.setGender("M");
		indi.setBirthDate("22 NOV 1995");
		testIndividualMap.put("J009",indi);
		
		indi = new Individual();
		indi.setId("J0099");
		indi.setName("Jess /Smith/");
		indi.setGender("F");
		indi.setBirthDate("22 NOV 1996");
		testIndividualMap.put("J0099",indi);
		
		Family family = new Family();
		family.setId("F009009");
		family.setHusbandId("J009");
		family.setWifeId("J0099");
		family.setMarriageDate("25 DEC 2018");
		testFamilyList.add(family);
		
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap,testFamilyList);
		ArrayList<Individual> livingMarriedArray = US_All_Lists.listOfLivingMarried(Ftp);
		
		assertTrue(livingMarriedArray.size() == 2);
	}
	
	// Allan: Sprint 3 US30 check list of Living Married
	@Test
	public void test_Check_LivingMarried2(){
		Individual indi = new Individual();
		indi.setId("Z009");
		indi.setName("John /Smith/");
		indi.setGender("M");
		indi.setBirthDate("22 NOV 1995");
		indi.setDeathDate("18 JAN 2015");
		testIndividualMap.put("Z009",indi);
		
		indi = new Individual();
		indi.setId("Z0099");
		indi.setName("Jane /Smith/");
		indi.setGender("F");
		indi.setBirthDate("22 NOV 1996");
		testIndividualMap.put("Z0099",indi);
		
		Family family = new Family();
		family.setId("F009009");
		family.setHusbandId("Z009");
		family.setWifeId("Z0099");
		family.setMarriageDate("11 FEB 2018");
		testFamilyList.add(family);
		
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap,testFamilyList);
		ArrayList<Individual> livingMarriedArray = US_All_Lists.listOfLivingMarried(Ftp);
		
		assertTrue(livingMarriedArray.size() == 0);
	}
	
	// Allan: Sprint 3 US27 list of Current Age
	@Test
	public void test_Check_CurrentAge(){
		Individual indi = new Individual();
		indi.setBirthDate("22 NOV 1995");
		assertTrue(indi.getAge() == 23);
	}
	
	// Allan: Sprint 3 US27 list of Current Age
	@Test
	public void test_Check_CurrentAge2(){
		Individual indi = new Individual();
		indi.setBirthDate("11 NOV 1994");
		assertEquals(indi.getAge(), 24);
	}
	
	// Shreesh: Sprint 2 US29 list of Deceased
	@Test
	public void getListOfDeceased() throws Exception {
		Individual individual = new Individual();
		individual.setId("I1100");
		individual.setBirthDate("12 APR 2001");
		testIndividualMap.put("I1000",individual);
		
		individual = new Individual();
		individual.setId("I1101");
		individual.setBirthDate("12 APR 2011");
		individual.setDeathDate("25 DEC 2018");
		testIndividualMap.put("I1101",individual);
		
		individual = new Individual();
		individual.setId("I1102");
		individual.setBirthDate("12 APR 2011");
		testIndividualMap.put("I1002",individual);
		
		Family family = new Family();
		family.setId("F1101");
		family.setHusbandId("I1100");
		family.setWifeId("I1101");
		family.setMarriageDate("25 DEC 2016");
		testFamilyList.add(family);
		
		Family family1 = new Family();
		family1.setId("F1102");
		family1.setHusbandId("I1100");
		family1.setWifeId("I1102");
		family1.setMarriageDate("25 DEC 2017");
		testFamilyList.add(family1);
		
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap,testFamilyList);
		ArrayList<Individual> deceasedPeople = US_All_Lists.US_listOfDeceased(Ftp);
		assertTrue(deceasedPeople.size() == 1);
		
	}
	
	// Shreesh: Sprint 3 US33 list of Orphans
		@Test
		public void testGetListOfOrphans() throws Exception {
			Individual individual = new Individual();
			individual.setId("I1100");
			individual.setBirthDate("12 APR 2001");
			individual.setDeathDate("25 DEC 2018");
			testIndividualMap.put("I1100",individual);
			
			individual = new Individual();
			individual.setId("I1101");
			individual.setBirthDate("12 APR 2001");
			individual.setDeathDate("25 DEC 2018");
			testIndividualMap.put("I1101",individual);
			
			individual = new Individual();
			individual.setId("I1103");
			individual.setBirthDate("12 APR 2011");
			testIndividualMap.put("I1103",individual);
			
			individual = new Individual();
			individual.setId("I1104");
			individual.setBirthDate("12 APR 2011");
			testIndividualMap.put("I1104",individual);
			
			Family family = new Family();
			family.setId("F1101");
			family.setHusbandId("I1100");
			family.setWifeId("I1101");
			family.setMarriageDate("25 DEC 2016");
			family.setChildId("I1103");
			family.setChildId("I1104");
			testFamilyList.add(family);
			
			FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap,testFamilyList);
			ArrayList<Individual> orphans = US_All_Lists.US_listofOrphans(Ftp);
			assertTrue(orphans.size() == 2);
//			check for dead orphan
			
		}
		// Shreesh: Sprint 3 US31 list of Singles
				@Test
				public void testGetListOfLivingSingles() throws Exception {
					Individual individual = new Individual();
					individual.setId("I1100");
					individual.setBirthDate("12 APR 2001");
					individual.setDeathDate("25 DEC 2018");
					testIndividualMap.put("I1100",individual);
					
					individual = new Individual();
					individual.setId("I1101");
					individual.setBirthDate("12 APR 2001");
					individual.setDeathDate("25 DEC 2018");
					testIndividualMap.put("I1101",individual);
					
					individual = new Individual();
					individual.setId("I1103");
					individual.setBirthDate("12 APR 1980");
					testIndividualMap.put("I1103",individual);
					
					individual = new Individual();
					individual.setId("I1104");
					individual.setBirthDate("12 APR 1980");
					testIndividualMap.put("I1104",individual);
					
					Family family = new Family();
					family.setId("F1101");
					family.setHusbandId("I1100");
					family.setWifeId("I1101");
					family.setMarriageDate("25 DEC 2016");
					family.setChildId("I1103");
					family.setChildId("I1104");
					testFamilyList.add(family);
					
					FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap,testFamilyList);
					ArrayList<Individual> singles = US_All_Lists.US_listOflivingSingle(Ftp);
					System.out.println(singles.size());
					assertTrue(singles.size() == 2);
					
				}


}
