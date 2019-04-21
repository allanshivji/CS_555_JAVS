package gedcom;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import org.junit.*;

public class Test_CheckList {

	HashMap<String, Individual> testIndividualMap;
	ArrayList<Family> testFamilyList;

	@Before
	public void setUp() {
		testIndividualMap = new HashMap<String, Individual>();
		testFamilyList = new ArrayList<Family>();
	}

	// Allan: Sprint 3 US30 check list of Living Married
	@Test
	public void test_Check_LivingMarried() {
		Individual indi = new Individual();
		indi.setId("J009");
		indi.setName("Allan /Smith/");
		indi.setGender("M");
		indi.setBirthDate("22 NOV 1995");
		testIndividualMap.put("J009", indi);

		indi = new Individual();
		indi.setId("J0099");
		indi.setName("Jess /Smith/");
		indi.setGender("F");
		indi.setBirthDate("22 NOV 1996");
		testIndividualMap.put("J0099", indi);

		Family family = new Family();
		family.setId("F009009");
		family.setHusbandId("J009");
		family.setWifeId("J0099");
		family.setMarriageDate("25 DEC 2018");
		testFamilyList.add(family);

		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<Individual> livingMarriedArray = US_All_Lists.listOfLivingMarried(Ftp);

		assertTrue(livingMarriedArray.size() == 2);
	}

	// Allan: Sprint 3 US30 check list of Living Married
	@Test
	public void test_Check_LivingMarried2() {
		Individual indi = new Individual();
		indi.setId("Z009");
		indi.setName("John /Smith/");
		indi.setGender("M");
		indi.setBirthDate("22 NOV 1995");
		indi.setDeathDate("18 JAN 2015");
		testIndividualMap.put("Z009", indi);

		indi = new Individual();
		indi.setId("Z0099");
		indi.setName("Jane /Smith/");
		indi.setGender("F");
		indi.setBirthDate("22 NOV 1996");
		testIndividualMap.put("Z0099", indi);

		Family family = new Family();
		family.setId("F009009");
		family.setHusbandId("Z009");
		family.setWifeId("Z0099");
		family.setMarriageDate("11 FEB 2018");
		testFamilyList.add(family);

		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<Individual> livingMarriedArray = US_All_Lists.listOfLivingMarried(Ftp);

		assertTrue(livingMarriedArray.size() == 0);
	}

	// Allan: Sprint 3 US27 list of Current Age
	@Test
	public void test_Check_CurrentAge() {
		Individual indi = new Individual();
		indi.setBirthDate("22 NOV 1995");
		assertTrue(indi.getAge() == 23);
	}

	// Allan: Sprint 3 US27 list of Current Age
	@Test
	public void test_Check_CurrentAge2() {
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
		testIndividualMap.put("I1000", individual);

		individual = new Individual();
		individual.setId("I1101");
		individual.setBirthDate("12 APR 2011");
		individual.setDeathDate("25 DEC 2018");
		testIndividualMap.put("I1101", individual);

		individual = new Individual();
		individual.setId("I1102");
		individual.setBirthDate("12 APR 2011");
		testIndividualMap.put("I1002", individual);

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

		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
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
		testIndividualMap.put("I1100", individual);

		individual = new Individual();
		individual.setId("I1101");
		individual.setBirthDate("12 APR 2001");
		individual.setDeathDate("25 DEC 2018");
		testIndividualMap.put("I1101", individual);

		individual = new Individual();
		individual.setId("I1103");
		individual.setBirthDate("12 APR 2011");
		testIndividualMap.put("I1103", individual);

		individual = new Individual();
		individual.setId("I1104");
		individual.setBirthDate("12 APR 2011");
		testIndividualMap.put("I1104", individual);

		Family family = new Family();
		family.setId("F1101");
		family.setHusbandId("I1100");
		family.setWifeId("I1101");
		family.setMarriageDate("25 DEC 2016");
		family.setChildId("I1103");
		family.setChildId("I1104");
		testFamilyList.add(family);

		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
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
		testIndividualMap.put("I1100", individual);

		individual = new Individual();
		individual.setId("I1101");
		individual.setBirthDate("12 APR 2001");
		individual.setDeathDate("25 DEC 2018");
		testIndividualMap.put("I1101", individual);

		individual = new Individual();
		individual.setId("I1103");
		individual.setBirthDate("12 APR 1980");
		testIndividualMap.put("I1103", individual);

		individual = new Individual();
		individual.setId("I1104");
		individual.setBirthDate("12 APR 1980");
		testIndividualMap.put("I1104", individual);

		Family family = new Family();
		family.setId("F1101");
		family.setHusbandId("I1100");
		family.setWifeId("I1101");
		family.setMarriageDate("25 DEC 2016");
		family.setChildId("I1103");
		family.setChildId("I1104");
		testFamilyList.add(family);

		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<Individual> singles = US_All_Lists.US_listOflivingSingle(Ftp);
//					System.out.println(singles.size());
		assertTrue(singles.size() == 2);

	}

	// Allan: Sprint 4 US39 check list of upcoming anniversary
	@Test
	public void test_Check_LivingMarried_Upcomming_Anniversary() {
		Individual indi = new Individual();
		indi.setId("KO009");
		indi.setName("Smith /Allans/");
		indi.setGender("M");
		indi.setBirthDate("22 NOV 1997");
		testIndividualMap.put("KO009", indi);

		indi = new Individual();
		indi.setId("KO0099");
		indi.setName("Jess /Allans/");
		indi.setGender("F");
		indi.setBirthDate("22 NOV 1999");
		testIndividualMap.put("KO0099", indi);

		Family family = new Family();
		family.setId("KO009009");
		family.setHusbandId("KO009");
		family.setWifeId("KO0099");
		family.setMarriageDate("18 MAY 2018");
		testFamilyList.add(family);

		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<Individual> livingMarriedUpcommingAnniversary = US_All_Lists.listOfUpcoming_Anniversaries(Ftp);
		assertTrue(livingMarriedUpcommingAnniversary.size() == 2);
	}

	// Allan: Sprint 4 US39 check list of upcoming anniversary
	@Test
	public void test_Check_LivingMarried_Upcomming_Anniversary2() {
		Individual indi = new Individual();
		indi.setId("KKO009");
		indi.setName("Smith /Allans/");
		indi.setGender("M");
		indi.setBirthDate("22 NOV 1997");
		testIndividualMap.put("KKO009", indi);

		indi = new Individual();
		indi.setId("KKO0099");
		indi.setName("Jess /Allans/");
		indi.setGender("F");
		indi.setBirthDate("22 NOV 1999");
		testIndividualMap.put("KKO0099", indi);

		Family family = new Family();
		family.setId("KKO009009");
		family.setHusbandId("KKO009");
		family.setWifeId("KKO0099");
		family.setMarriageDate("18 DEC 2018");
		testFamilyList.add(family);

		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<Individual> livingMarriedUpcommingAnniversary = US_All_Lists.listOfUpcoming_Anniversaries(Ftp);
		assertTrue(livingMarriedUpcommingAnniversary.size() == 0);
	}

	@Test
	public void test_US35ListOfRecentBirthsSuccess() {
		// DateTimeFormatter dateFormat = new
		// DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("dd MMM
		// yyyy").toFormatter(Locale.ENGLISH);
		// LocalDate birthDate;
		// birthDate = LocalDate.now().minusDays(10);
		// String ob = (birthDate).format(dateFormat);
		// System.out.println(ob);

		Individual indi = new Individual();
		indi.setId("I100");
		indi.setName("Isaac Stark");
		indi.setBirthDate("16 APR 2019");
		testIndividualMap.put("I100", indi);

		indi.setId("I101");
		indi.setName("Liana Stark");
		indi.setBirthDate("18 APR 2019");
		testIndividualMap.put("I101", indi);

		Family family = new Family();
		family.setId("F100");
		family.setChildId("I100");
		testFamilyList.add(family);

		family.setId("F101");
		family.setChildId("I101");
		testFamilyList.add(family);

		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<Individual> recentBirths = US_All_Lists.US35_findListOfRecentBirths(Ftp);
		assertEquals(recentBirths.size(), 2);

	}

	@Test
	public void test_US35ListOfRecentBirthsFailure() {
		Individual indi = new Individual();
		indi.setId("I100");
		indi.setName("Arya Stark");
		indi.setBirthDate("16 APR 2018");
		testIndividualMap.put("I100", indi);

		indi.setId("I101");
		indi.setName("Sophie Stark");
		indi.setBirthDate("18 APR 2025");
		testIndividualMap.put("I101", indi);

		indi.setId("I102");
		indi.setName("Leya Stark");
		testIndividualMap.put("I102", indi);

		Family family = new Family();
		family.setId("F100");
		family.setChildId("I100");
		testFamilyList.add(family);

		family.setId("F101");
		family.setChildId("I101");
		testFamilyList.add(family);

		family.setId("F102");
		family.setChildId("I102");
		testFamilyList.add(family);

		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<Individual> recentBirths = US_All_Lists.US35_findListOfRecentBirths(Ftp);
		assertEquals(recentBirths.size(), 0);

	}

	@Test
	public void test_US36ListOfRecentDeathsSuccess() {
		Individual indi = new Individual();
		indi.setId("I100");
		indi.setName("Sean Stark");
		indi.setDeathDate("16 APR 2019");
		testIndividualMap.put("I100", indi);

		indi.setId("I101");
		indi.setName("Leena Stark");
		indi.setBirthDate("18 APR 2019");
		testIndividualMap.put("I101", indi);

		Family family = new Family();
		family.setId("F100");
		family.setChildId("I100");
		testFamilyList.add(family);

		family.setId("F101");
		family.setChildId("I101");
		testFamilyList.add(family);

		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<Individual> recentDeaths = US_All_Lists.US36_findListOfRecentDeaths(Ftp);
		assertEquals(recentDeaths.size(), 2);

	}

	@Test
	public void test_US35ListOfRecentDeathsFailure() {
		Individual indi = new Individual();
		indi.setId("I100");
		indi.setName("Harry Polis");
		indi.setDeathDate("16 APR 2018");
		testIndividualMap.put("I100", indi);

		indi.setId("I101");
		indi.setName("Masie Polis");
		indi.setDeathDate("18 APR 2025");
		testIndividualMap.put("I101", indi);

		indi.setId("I102");
		indi.setName("Nicole Polis");
		testIndividualMap.put("I102", indi);

		Family family = new Family();
		family.setId("F100");
		family.setChildId("I100");
		testFamilyList.add(family);

		family.setId("F101");
		family.setChildId("I101");
		testFamilyList.add(family);

		family.setId("F102");
		family.setChildId("I102");
		testFamilyList.add(family);

		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<Individual> recentDeaths = US_All_Lists.US36_findListOfRecentDeaths(Ftp);
		assertEquals(recentDeaths.size(), 0);

	}
	@Test
	public void test_US34listCouplesWithLargeAgeDifferrence() {
	
		Individual individual = new Individual();
		individual.setId("I1100");
		individual.setBirthDate("12 APR 2001");
		testIndividualMap.put("I1100", individual);

		individual = new Individual();
		individual.setId("I1101");
		individual.setBirthDate("12 APR 1985");
		testIndividualMap.put("I1101", individual);

		individual = new Individual();
		individual.setId("I1103");
		individual.setBirthDate("12 APR 1980");
		testIndividualMap.put("I1103", individual);

		individual = new Individual();
		individual.setId("I1104");
		individual.setBirthDate("12 APR 1980");
		testIndividualMap.put("I1104", individual);

		Family family = new Family();
		family.setId("F1101");
		family.setHusbandId("I1100");
		family.setWifeId("I1101");
		family.setMarriageDate("25 DEC 2006");
		testFamilyList.add(family);

		Family family1 = new Family();
		family1.setId("F1102");
		family1.setHusbandId("I1103");
		family1.setWifeId("I1104");
		family1.setMarriageDate("25 DEC 2016");
		family1.setChildId("I1103");
		testFamilyList.add(family1);
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<Family> couples = US_All_Lists.US34_listOfCouplesWithLargeAgeDifference(Ftp);
//					System.out.println(couples);
		assertTrue(couples.size() == 1);
	}
	@Test
	public void test_US38listOfUpcomingBirthdays() {
	
		Individual individual = new Individual();
		individual.setId("I1100");
		individual.setBirthDate("12 APR 2001");
		testIndividualMap.put("I1100", individual);

		individual = new Individual();
		individual.setId("I1101");
		individual.setBirthDate("30 APR 1985");
		testIndividualMap.put("I1101", individual);

		individual = new Individual();
		individual.setId("I1103");
		individual.setBirthDate("12 MAY 1980");
		testIndividualMap.put("I1103", individual);

		individual = new Individual();
		individual.setId("I1104");
		individual.setBirthDate("12 APR 1980");
		testIndividualMap.put("I1104", individual);
		FamilyTreeParser Ftp = new FamilyTreeParser(testIndividualMap, testFamilyList);
		ArrayList<Individual> birthdays = US_All_Lists.US38_listOfUpcomingBirthdays(Ftp);
//					System.out.println(couples);
		assertTrue(birthdays.size() == 2);
	}
}
