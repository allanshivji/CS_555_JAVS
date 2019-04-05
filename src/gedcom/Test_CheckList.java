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
		ArrayList<Individual> livingMarriedArray = All_Lists.listOfLivingMarried(Ftp);
		
		assertTrue(livingMarriedArray.size() == 2);
	}
	
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
		ArrayList<Individual> livingMarriedArray = All_Lists.listOfLivingMarried(Ftp);
		
		assertTrue(livingMarriedArray.size() == 0);
	}
	
	@Test
	public void test_Check_CurrentAge(){
		Individual indi = new Individual();
		indi.setBirthDate("22 NOV 1995");
		assertTrue(indi.getAge() == 23);
	}
	
	@Test
	public void test_Check_CurrentAge2(){
		Individual indi = new Individual();
		indi.setBirthDate("11 NOV 1994");
		assertEquals(indi.getAge(), 24);
	}

}
