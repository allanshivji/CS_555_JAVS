package gedcom;
//Shreesh Chavan: Sprint1 US01 valid date
import static org.junit.Assert.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DateCheck {
	HashMap<String,Individual> testIndividualMap;
	ArrayList<Family> testFamilyList;
	@Before
	public void setUp() {
		testIndividualMap = new HashMap<String,Individual>();
		testFamilyList = new ArrayList<Family>();

	}
	
	@Test
	public void testCheckBirthDatesAfterCurrentDate() throws Exception {
//		fail("Not yet implemented");
		
		Individual individual = new Individual();
		individual.setId("I100");
		individual.setBirthDate("12 APR 2020");
		testIndividualMap.put("I100",individual);
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap,testFamilyList);
		
		ArrayList<ErrorData> recordError = MultiIndividualFamilyData.testCheckDatesBeforeCurrentDate(Ftp);
		
		ErrorData error = new ErrorData();
		error.setIndividualId("I100");
		error.setErrorDetails("BirthDate 2020-04-12 for I100 is after today's date.");
//		System.out.println(recordError.get(0).getErrorDetails());
		assertEquals(error.getErrorDetails(),recordError.get(0).getErrorDetails());

	}
	@Test
	public void testCheckDeathDatesAfterCurrentDate() throws Exception {
//		fail("Not yet implemented");
		Individual individual = new Individual();
		individual.setId("I200");
		individual.setBirthDate("12 APR 2010");
		individual.setDeathDate("12 APR 2020");
		testIndividualMap.put("I200",individual);
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap,testFamilyList);
		
		ArrayList<ErrorData> recordError = MultiIndividualFamilyData.testCheckDatesBeforeCurrentDate(Ftp);
		
		
		ErrorData error = new ErrorData();
		error.setIndividualId("I200");
		error.setErrorDetails("DeathDate 2020-04-12 for I200 is after today's date.");
//		System.out.println(recordError.get(1).getErrorDetails());
		assertEquals(error.getErrorDetails(),recordError.get(0).getErrorDetails());
	}
	@Test
	public void testCheckMarriageDateAfterCurrentDate() throws Exception {
		Individual individual = new Individual();
		individual.setId("I300");
		individual.setBirthDate("12 APR 1990");
		testIndividualMap.put("I300",individual);
		
		individual = new Individual();
		individual.setId("I301");
		individual.setBirthDate("12 APR 1990");
		testIndividualMap.put("I301",individual);
		
		Family family = new Family();
		family.setId("F100");
		family.setHusbandId("I300");
		family.setWifeId("I301");
		family.setMarriageDate("25 DEC 2096");
		testFamilyList.add(family);
		
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap,testFamilyList);
		ArrayList<ErrorData> recordError = MultiIndividualFamilyData.testCheckDatesBeforeCurrentDate(Ftp);
		
		ErrorData error = new ErrorData();
		error.setIndividualId("I300");
		error.setErrorDetails("MarriageDate 2096-12-25 for I300 is after today's date.");
//		System.out.println(recordError.get(4).getErrorDetails());
		assertEquals(error.getErrorDetails(),recordError.get(0).getErrorDetails());		
		error = new ErrorData();
		error.setIndividualId("I301");
		error.setErrorDetails("MarriageDate 2096-12-25 for I301 is after today's date.");
//		System.out.println(recordError.get(5).getErrorDetails());
		assertEquals(error.getErrorDetails(),recordError.get(1).getErrorDetails());
	}
	@Test
	public void testCheckDivorceDateAfterCurrentDate() throws Exception {
		Individual individual = new Individual();
		individual.setId("I400");
		individual.setBirthDate("12 APR 2001");
		testIndividualMap.put("I400",individual);
		
		individual = new Individual();
		individual.setId("I401");
		individual.setBirthDate("12 APR 2011");
		testIndividualMap.put("I401",individual);
		
		Family family = new Family();
		family.setId("F401");
		family.setHusbandId("I400");
		family.setWifeId("I401");
		family.setMarriageDate("25 DEC 2018");
		family.setDivorceDate("25 DEC 2019");
		testFamilyList.add(family);
		
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap,testFamilyList);
		ArrayList<ErrorData> recordError = MultiIndividualFamilyData.testCheckDatesBeforeCurrentDate(Ftp);
		
		ErrorData error = new ErrorData();
		error.setIndividualId("I400");
		error.setErrorDetails("DivorceDate 2019-12-25 for I400 is after today's date.");
//		System.out.println(recordError.get(2).getErrorDetails());
		assertEquals(error.getErrorDetails(),recordError.get(0).getErrorDetails());
		error.setIndividualId("I401");
		error.setErrorDetails("DivorceDate 2019-12-25 for I401 is after today's date.");
//		System.out.println(recordError.get(3).getErrorDetails());
		assertEquals(error.getErrorDetails(),recordError.get(1).getErrorDetails());
	}
	@Test
	public void testCheckBirthDatesBeforeCurrentDate() throws Exception {
//		fail("Not yet implemented");
		
		Individual individual = new Individual();
		individual.setId("I500");
		individual.setBirthDate("12 APR 2010");
		testIndividualMap.put("I500",individual);
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap,testFamilyList);
		
		ArrayList<ErrorData> recordError = MultiIndividualFamilyData.testCheckDatesBeforeCurrentDate(Ftp);
		
		ErrorData error = new ErrorData();
		error.setIndividualId("I500");
		error.setErrorDetails("BirthDate 2010-04-12 for I500 is after today's date.");
//		for(int i=0;i<recordError.size();i++) {
//			System.out.println(recordError.get(i).getErrorDetails());
//		}
		for(ErrorData record : recordError) {
			assertNotEquals("I500", record.getIndividualId());
		}
//		System.out.print("hi");
	}
	@Test
	public void testCheckDeathDatesBeforeCurrentDate() throws Exception {
//		fail("Not yet implemented");
		Individual individual = new Individual();
		individual.setId("I600");
		individual.setBirthDate("12 APR 2010");
		individual.setDeathDate("12 APR 2018");
		testIndividualMap.put("I600",individual);
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap,testFamilyList);
		
		ArrayList<ErrorData> recordError = MultiIndividualFamilyData.testCheckDatesBeforeCurrentDate(Ftp);
		
		ErrorData error = new ErrorData();
		error.setIndividualId("I600");
		error.setErrorDetails("DeathDate 2018-04-12 for I600 is after today's date.");
		for(ErrorData record : recordError) {
			assertNotEquals("I600", record.getIndividualId());
		}
	}
	@Test
	public void testCheckMarriageDateBeforeCurrentDate() throws Exception {
		Individual individual = new Individual();
		individual.setId("I700");
		individual.setBirthDate("12 APR 2001");
		testIndividualMap.put("I700",individual);
		
		individual = new Individual();
		individual.setId("I701");
		individual.setBirthDate("12 APR 2011");
		testIndividualMap.put("I701",individual);
		
		Family family = new Family();
		family.setId("F700");
		family.setHusbandId("I700");
		family.setWifeId("I701");
		family.setMarriageDate("25 DEC 2016");
		testFamilyList.add(family);
		
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap,testFamilyList);
		ArrayList<ErrorData> recordError = MultiIndividualFamilyData.testCheckDatesBeforeCurrentDate(Ftp);
		
		ErrorData error = new ErrorData();
		error.setIndividualId("I700");
		error.setErrorDetails("MarriageDate 2016-12-25 for I700 is after today's date.");
		for(ErrorData record : recordError) {
			assertNotEquals("I700", record.getIndividualId());
			assertNotEquals("I701", record.getIndividualId());
		}
	}
	@Test
	public void testCheckDivorceDateBeforeCurrentDate() throws Exception {
		Individual individual = new Individual();
		individual.setId("I800");
		individual.setBirthDate("12 APR 2001");
		testIndividualMap.put("I400",individual);
		
		individual = new Individual();
		individual.setId("I801");
		individual.setBirthDate("12 APR 2011");
		testIndividualMap.put("I401",individual);
		
		Family family = new Family();
		family.setId("F801");
		family.setHusbandId("I800");
		family.setWifeId("I801");
		family.setMarriageDate("25 DEC 2016");
		family.setDivorceDate("25 DEC 2018");
		testFamilyList.add(family);
		
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap,testFamilyList);
		ArrayList<ErrorData> recordError = MultiIndividualFamilyData.testCheckDatesBeforeCurrentDate(Ftp);
		
		ErrorData error = new ErrorData();
		error.setIndividualId("I800");
		error.setErrorDetails("DivorceDate 2018-12-25 for I800 is after today's date.");
		for(ErrorData record : recordError) {
			assertNotEquals("I800", record.getIndividualId());
			assertNotEquals("I801", record.getIndividualId());
		}
		
	}
	@Test
	public void checkbigamybro() throws Exception {
		Individual individual = new Individual();
		individual.setId("I900");
		individual.setBirthDate("12 APR 2001");
		testIndividualMap.put("I900",individual);
		
		individual = new Individual();
		individual.setId("I901");
		individual.setBirthDate("12 APR 2011");
		testIndividualMap.put("I901",individual);
		
		individual = new Individual();
		individual.setId("I902");
		individual.setBirthDate("12 APR 2011");
		testIndividualMap.put("I902",individual);
		
		Family family = new Family();
		family.setId("F901");
		family.setHusbandId("I900");
		family.setWifeId("I901");
		family.setMarriageDate("25 DEC 2016");
		family.setDivorceDate("25 DEC 2018");
		testFamilyList.add(family);
		
		Family family1 = new Family();
		family1.setId("F902");
		family1.setHusbandId("I900");
		family1.setWifeId("I902");
		family1.setMarriageDate("25 DEC 2017");
		testFamilyList.add(family1);
		
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap,testFamilyList);
		ArrayList<ErrorData> recordError = MultiIndividualFamilyData.checkBigamy(Ftp);
		ErrorData error = new ErrorData();
		error.setIndividualId("I900");
		error.setErrorDetails("I900 has married without seperation from previous spouse.");
		assertEquals(error.getErrorDetails(),recordError.get(0).getErrorDetails());
		
	}

	public void checkbigamybeforedeath() throws Exception {
		Individual individual = new Individual();
		individual.setId("I1000");
		individual.setBirthDate("12 APR 2001");
		testIndividualMap.put("I1000",individual);
		
		individual = new Individual();
		individual.setId("I1001");
		individual.setBirthDate("12 APR 2011");
		individual.setDeathDate("25 DEC 2018");
		testIndividualMap.put("I1001",individual);
		
		individual = new Individual();
		individual.setId("I1002");
		individual.setBirthDate("12 APR 2011");
		testIndividualMap.put("I1002",individual);
		
		Family family = new Family();
		family.setId("F1001");
		family.setHusbandId("I1000");
		family.setWifeId("I1001");
		family.setMarriageDate("25 DEC 2016");
		testFamilyList.add(family);
		
		Family family1 = new Family();
		family1.setId("F1002");
		family1.setHusbandId("I1000");
		family1.setWifeId("I1002");
		family1.setMarriageDate("25 DEC 2017");
		testFamilyList.add(family1);
		
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap,testFamilyList);
		ArrayList<ErrorData> recordError = MultiIndividualFamilyData.checkBigamy(Ftp);
		ErrorData error = new ErrorData();
		error.setIndividualId("I1000");
		error.setErrorDetails("I1000 has married without seperation from previous spouse.");
		assertEquals(error.getErrorDetails(),recordError.get(0).getErrorDetails());
		
	}
}
