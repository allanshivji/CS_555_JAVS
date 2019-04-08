package gedcom;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.*;

public class Test_US_CheckValidity {
//
//	int Age1 = 151;
//	int Age2 = 13;
//	int Age3 = 30;
//	int Age4 = 150;
//	int Age5 = 149;
//	int Age6 = 14;
//	String SpouseID1 = null;
//	String SpouseID2 = "F1";
//
//	int fatherAge1 = 100;
//	int fatherAge2 = 90;
//	int motherAge1 = 80;
//	int motherAge2 = 70;
//	int childAge1 = 5;
//	int childAge2 = 10;
//	int childAge3 = 20;
//	int childAge4 = 40;
//
//	String fatherId1 = "bi00";
//	String fatherId2 = "bi01";
//	String motherId1 = "bi10";
//	String motherId2 = "bi11";
//	String childId1 = "bi00";
//	String childId2 = "bi11";
	
	@Test
	public void test_check_age() {
		Individual indi = new Individual();
		indi.setId("I01");
		indi.setName("XX");
		indi.setBirthDate("1 JAN 1900");
		assertNull(US_CheckValidity.check_age(indi));
		
		indi.setBirthDate("1 JAN 1800");
		assertNotNull(US_CheckValidity.check_age(indi));

	}
	
	@Test
	public void test_check_marriage_age() {
		Individual indi = new Individual();
		indi.setId("I01");
		indi.setName("XX");
		indi.setFamilySpouseId("F01");
		indi.setBirthDate("1 JAN 2010");
		assertNotNull(US_CheckValidity.check_marriage_age(indi));
		
		indi.setBirthDate("1 JAN 2000");
		assertNull(US_CheckValidity.check_marriage_age(indi));
	}
	
	@Test
	public void test_check_parents_not_too_old() {
		Individual indi_husband = new Individual();
		indi_husband.setId("I01");
		indi_husband.setName("Husband");
		indi_husband.setFamilySpouseId("F01");
		indi_husband.setBirthDate("1 JAN 1900");
		
		Individual indi_wife = new Individual();
		indi_wife.setId("I02");
		indi_wife.setName("Wife");
		indi_wife.setFamilySpouseId("F01");
		indi_wife.setBirthDate("1 JAN 1900");
		
		Individual indi_child = new Individual();
		indi_child.setId("I03");
		indi_child.setName("Child");
		indi_child.setFamilySpouseId("F01");
		indi_child.setBirthDate("1 JAN 2000");
		
		
		ArrayList<Individual> indi_children = new ArrayList<Individual>();
		indi_children.add(indi_child);
		
		assertNotEquals(US_CheckValidity.check_parents_not_too_old(indi_husband, indi_wife, indi_children, "F1").size(), 0);
		indi_husband.setBirthDate("1 JAN 1950");
		assertNotEquals(US_CheckValidity.check_parents_not_too_old(indi_husband, indi_wife, indi_children, "F1").size(), 0);
		indi_wife.setBirthDate("1 JAN 1970");
		assertEquals(US_CheckValidity.check_parents_not_too_old(indi_husband, indi_wife, indi_children, "F1").size(), 0);
	}
	
	@Test
	public void test_check_parents_no_marriages_to_children() {
		Individual indi_husband = new Individual();
		indi_husband.setId("I01");
		indi_husband.setName("Husband");
		indi_husband.setFamilySpouseId("F01");
		indi_husband.setBirthDate("1 JAN 1900");
		
		Individual indi_wife = new Individual();
		indi_wife.setId("I02");
		indi_wife.setName("Wife");
		indi_wife.setFamilySpouseId("F01");
		indi_wife.setBirthDate("1 JAN 1900");
		
		Individual indi_child = new Individual();
		indi_child.setId("I03");
		indi_child.setName("Child");
		indi_child.setFamilySpouseId("F01");
		indi_child.setBirthDate("1 JAN 2000");
		
		ArrayList<Individual> indi_children1 = new ArrayList<Individual>();
		indi_children1.add(indi_husband);
		ArrayList<Individual> indi_children2 = new ArrayList<Individual>();
		indi_children2.add(indi_wife);
		ArrayList<Individual> indi_children3 = new ArrayList<Individual>();
		indi_children3.add(indi_child);
		
		assertNotEquals(US_CheckValidity.check_parents_no_marriages_to_children(indi_husband, indi_wife, indi_children1, "F01").size(), 0);
		assertNotEquals(US_CheckValidity.check_parents_no_marriages_to_children(indi_husband, indi_wife, indi_children2, "F01").size(), 0);
		assertEquals(US_CheckValidity.check_parents_no_marriages_to_children(indi_husband, indi_wife, indi_children3, "F01").size(), 0);
	}
	
	@Test
	public void test_check_divorce_before_death() {
		Individual indi_husband = new Individual();
		indi_husband.setId("I01");
		indi_husband.setName("Husband");
		indi_husband.setBirthDate("1 JAN 1901");
		indi_husband.setDeathDate("1 JAN 1981");
		
		Individual indi_wife = new Individual();
		indi_wife.setId("I02");
		indi_wife.setName("Wife");
		indi_wife.setDeathDate("1 JAN 1979");
		
		Family indi_family1 = new Family();
		indi_family1.setId("F01");
		indi_family1.setDivorceDate("1 JAN 1980");
		
		Family indi_family2 = new Family();
		indi_family2.setId("F01");
		indi_family2.setDivorceDate("1 JAN 1970");
		
		
		assertNotEquals(US_CheckValidity.check_divorce_before_death(indi_husband, indi_wife, indi_family1).size(), 0);
		assertNotEquals(US_CheckValidity.check_divorce_before_death(indi_husband, indi_wife, indi_family1).size(), 0);
		assertEquals(US_CheckValidity.check_divorce_before_death(indi_husband, indi_wife, indi_family2).size(), 0);
		
	}
	
	@Test
	public void test_check_multiple_births() {
		Individual indi_child1 = new Individual();
		indi_child1.setId("I01");
		indi_child1.setName("child1");
		indi_child1.setBirthDate("1 JAN 1980");
		
		Individual indi_child2 = new Individual();
		indi_child2.setId("I02");
		indi_child2.setName("child2");
		indi_child2.setBirthDate("1 JAN 1980");

		Individual indi_child3 = new Individual();
		indi_child3.setId("I03");
		indi_child3.setName("child3");
		indi_child3.setBirthDate("1 JAN 1980");
		
		Individual indi_child4 = new Individual();
		indi_child4.setId("I04");
		indi_child4.setName("child4");
		indi_child4.setBirthDate("1 JAN 1980");
		
		Individual indi_child5 = new Individual();
		indi_child5.setId("I05");
		indi_child5.setName("child5");
		indi_child5.setBirthDate("1 JAN 1980");
		
		Individual indi_child6 = new Individual();
		indi_child6.setId("I06");
		indi_child6.setName("child6");
		indi_child6.setBirthDate("1 JAN 1980");
		
		ArrayList<Individual> children1 = new ArrayList<Individual>();
		children1.add(indi_child1);
		children1.add(indi_child2);
		children1.add(indi_child3);
		children1.add(indi_child4);
		children1.add(indi_child5);
		children1.add(indi_child6);
		
		ArrayList<Individual> children2 = new ArrayList<Individual>();
		children2.add(indi_child1);
		children2.add(indi_child2);
		children2.add(indi_child3);
		children2.add(indi_child4);
		children2.add(indi_child5);
		
		assertNotEquals(US_CheckValidity.check_multiple_births(children1, "F01"), null);
		assertEquals(US_CheckValidity.check_multiple_births(children2, "F01"), null);
	
	}
	
	
//	@Test
//	public void test_check_age() {
//		// fail("Not yet implemented");
//		assertFalse(CheckValidity.check_age(Age1));
//		assertTrue(CheckValidity.check_age(Age2));
//		assertTrue(CheckValidity.check_age(Age3));
//		assertFalse(CheckValidity.check_age(Age4));
//		assertTrue(CheckValidity.check_age(Age5));
//	}
//
//	@Test
//	public void test_check_marriage_age() {
//		// fail("Not yet implemented");
//		assertTrue(CheckValidity.check_marriage_age(SpouseID1, Age2));
//		assertTrue(CheckValidity.check_marriage_age(SpouseID1, Age3));
//		assertTrue(CheckValidity.check_marriage_age(SpouseID1, Age6));
//		assertFalse(CheckValidity.check_marriage_age(SpouseID2, Age2));
//		assertTrue(CheckValidity.check_marriage_age(SpouseID2, Age3));
//		assertFalse(CheckValidity.check_marriage_age(SpouseID2, Age6));
//	}
//
//	@Test
//	public void test_check_father_not_too_old() {
//		assertFalse(CheckValidity.check_father_not_too_old(fatherAge1, childAge1));
//		assertFalse(CheckValidity.check_father_not_too_old(fatherAge1, childAge2));
//		assertTrue(CheckValidity.check_father_not_too_old(fatherAge1, childAge3));
//		assertTrue(CheckValidity.check_father_not_too_old(fatherAge1, childAge4));
//		assertFalse(CheckValidity.check_father_not_too_old(fatherAge2, childAge1));
//		assertTrue(CheckValidity.check_father_not_too_old(fatherAge2, childAge2));
//		assertTrue(CheckValidity.check_father_not_too_old(fatherAge2, childAge3));
//		assertTrue(CheckValidity.check_father_not_too_old(fatherAge2, childAge4));
//	}
//
//	@Test
//	public void test_check_mother_not_too_old() {
//		assertFalse(CheckValidity.check_mother_not_too_old(motherAge1, childAge1));
//		assertFalse(CheckValidity.check_mother_not_too_old(motherAge1, childAge2));
//		assertTrue(CheckValidity.check_mother_not_too_old(motherAge1, childAge3));
//		assertTrue(CheckValidity.check_mother_not_too_old(motherAge1, childAge4));
//		assertFalse(CheckValidity.check_mother_not_too_old(motherAge2, childAge1));
//		assertTrue(CheckValidity.check_mother_not_too_old(motherAge2, childAge2));
//		assertTrue(CheckValidity.check_mother_not_too_old(motherAge2, childAge3));
//		assertTrue(CheckValidity.check_mother_not_too_old(motherAge2, childAge4));
//	}
//
//	@Test
//	public void test_check_father_no_marriages_to_children() {
//		assertTrue(CheckValidity.check_father_no_marriages_to_children(motherId1, childId1));
//		assertTrue(CheckValidity.check_father_no_marriages_to_children(motherId1, childId2));
//		assertTrue(CheckValidity.check_father_no_marriages_to_children(motherId2, childId1));
//		assertFalse(CheckValidity.check_father_no_marriages_to_children(motherId2, childId2));
//	}
//
//	@Test
//	public void test_check_mother_no_marriages_to_children() {
//		assertFalse(CheckValidity.check_mother_no_marriages_to_children(fatherId1, childId1));
//		assertTrue(CheckValidity.check_mother_no_marriages_to_children(fatherId1, childId2));
//		assertTrue(CheckValidity.check_mother_no_marriages_to_children(fatherId2, childId1));
//		assertTrue(CheckValidity.check_mother_no_marriages_to_children(fatherId1, childId2));
//	}
//	
	
	
}
