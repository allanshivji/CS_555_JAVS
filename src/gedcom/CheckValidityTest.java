package gedcom;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.*;

public class CheckValidityTest {
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
		assertNull(CheckValidity.check_age(indi));
		
		indi.setBirthDate("1 JAN 1800");
		assertNotNull(CheckValidity.check_age(indi));

	}
	
	@Test
	public void test_check_marriage_age() {
		Individual indi = new Individual();
		indi.setId("I01");
		indi.setName("XX");
		indi.setFamilySpouseId("F01");
		indi.setBirthDate("1 JAN 2010");
		assertNotNull(CheckValidity.check_marriage_age(indi));
		
		indi.setBirthDate("1 JAN 2000");
		assertNull(CheckValidity.check_marriage_age(indi));
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
		
		assertNotEquals(CheckValidity.check_parents_not_too_old(indi_husband, indi_wife, indi_child).size(), 0);
		indi_husband.setBirthDate("1 JAN 1950");
		assertNotEquals(CheckValidity.check_parents_not_too_old(indi_husband, indi_wife, indi_child).size(), 0);
		indi_wife.setBirthDate("1 JAN 1970");
		assertEquals(CheckValidity.check_parents_not_too_old(indi_husband, indi_wife, indi_child).size(), 0);
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
		
		assertNotEquals(CheckValidity.check_parents_no_marriages_to_children(indi_husband, indi_wife, indi_husband).size(), 0);
		assertNotEquals(CheckValidity.check_parents_no_marriages_to_children(indi_husband, indi_wife, indi_wife).size(), 0);
		assertEquals(CheckValidity.check_parents_no_marriages_to_children(indi_husband, indi_wife, indi_child).size(), 0);
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
