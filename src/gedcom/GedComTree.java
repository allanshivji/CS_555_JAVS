package gedcom;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class GedComTree {
	private static void printIndividualList(FamilyTreeParser Ftp) {
		Map<String, Individual> treeMap = new TreeMap<String, Individual>(Ftp.individualMap);

		// For individuals
		System.out.println("Individuals");
		String individualOutputFormat = "|%1$-6s|%2$-20s|%3$-10s|%4$-12s|%5$-7s|%6$-9s|%7$-12s|%8$-10s|%9$-10s|%n";
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
		System.out.format(
				"|  ID  |       Name         |  Gender  |  Birthday  |  Age  |  Alive  |    Death   |   Child  |  Spouse  |%n");
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
		for (Map.Entry<String, Individual> entry : treeMap.entrySet()) {
			Individual indi = entry.getValue();
			System.out.format(individualOutputFormat, indi.getId(), indi.getName(), indi.getGender(),
					indi.getBirthDate(), indi.getAge(), indi.isAlive(),
					indi.getDeathDate() == null ? "NA" : indi.getDeathDate(),
					indi.getFamilyChildId() == null ? "NA" : "{'" + indi.getFamilyChildId() + "'}",
					indi.getFamilySpouseId() == null ? "NA" : "{'" + indi.getFamilySpouseId() + "'}");

		}
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
		System.out.println();
	}

	private static void printFamilyList(FamilyTreeParser Ftp) {

		// For Families
		System.out.println("Families");
		String familyOutputFormat = "|%1$-6s|%2$-16s|%3$-16s|%4$-14s|%5$-23s|%6$-14s|%7$-23s|%8$-113s|%n";
		System.out.format(
				"+------+----------------+----------------+--------------+-----------------------+--------------+-----------------------+-----------------------------------------------------------------------------------------------------------------+%n");
		System.out.format(
				"|  ID  |    Married     |    Divorced    |  Husband Id  |      Husband Name     |    Wife Id   |      Wife Name        |      Children                                                                                                   |%n");
		System.out.format(
				"+------+----------------+----------------+--------------+-----------------------+--------------+-----------------------+-----------------------------------------------------------------------------------------------------------------+%n");
		for (int i = 0; i < Ftp.familyList.size(); i++) {
			Family fam = Ftp.familyList.get(i);
			String wifeName = fam.getWifeId() == null ? "NULL"
					: (Ftp.individualMap.get(fam.getWifeId()) == null ? "NULL"
							: Ftp.individualMap.get(fam.getWifeId()).getName());
			String husbandName = fam.getHusbandId() == null ? "NULL"
					: (Ftp.individualMap.get(fam.getHusbandId()) == null ? "NULL"
							: Ftp.individualMap.get(fam.getHusbandId()).getName());
			System.out.format(familyOutputFormat, fam.getId(),
					fam.getMarriageDate() == null ? "NA" : fam.getMarriageDate(),
					fam.getDivorcedDate() == null ? "NA" : fam.getDivorcedDate(),
					fam.getHusbandId() == null ? "NULL" : fam.getHusbandId(), husbandName,
					fam.getWifeId() == null ? "NULL" : fam.getWifeId(), wifeName,
					fam.getChildId().isEmpty() ? "NA" : "{'" + String.join("', '", fam.getChildId()) + "'}");
		}
		System.out.format(
				"+------+----------------+----------------+--------------+-----------------------+--------------+-----------------------+-----------------------------------------------------------------------------------------------------------------+%n");
		System.out.println();
	}

	private static void printErrorDetails(FamilyTreeParser Ftp) throws ParseException {

		// The arralyist allErrors contains list of all ERROR records if present in
		// gedcom test input file.

		ArrayList<ErrorData> allErrors = new ArrayList<ErrorData>();

		// --------------------------------Sprint01-----------------------------------------------//

		allErrors.addAll(US_DatesCheckInFamily.US05_Marriage_Before_Death(Ftp));

		allErrors.addAll(US_DatesCheckInFamily.testCheckDatesBeforeCurrentDate(Ftp));

		allErrors.addAll(US_MultiIndividualFamilyData.checkBigamy(Ftp));

		allErrors.addAll(US_DatesCheckInFamily.US08_Birth_Before_Marriage_Of_Parents(Ftp));

		allErrors.addAll(US_DatesCheckInFamily.US03_check_Birth_Before_Death(Ftp));

		allErrors.addAll(US_DatesCheckInFamily.US02_check_Birth_Before_Marriage(Ftp));

		allErrors.addAll(US_CheckValidity.check(Ftp)); // Jiayuan Liu

		// --------------------------------Sprint02-----------------------------------------------//

		allErrors.addAll(US_MultiIndividualFamilyData.US21_check_Gender_Role(Ftp)); // Allan

		allErrors.addAll(FamilyTreeParser.duplicateIndividual); // Allan

		allErrors.addAll(US_CheckUniqueness.US22_check_Unique_FamilyId(Ftp)); // Allan

		allErrors.addAll(US_DatesCheckInFamily.US04_MarriageBeforeDivorce(Ftp));

		allErrors.addAll(US_Siblings.US15_FewerThanFifteenSiblings(Ftp));

		allErrors.addAll(US_Siblings.checkSiblingMarraige(Ftp));

		// ---------------------------------Sprint03--------------------------------------------//

		allErrors.addAll(US_CheckUniqueness.US24_findDuplicateSpousedetails(Ftp));

		allErrors.addAll(US_DatesCheckInFamily.US09_findBirthBeforeDeathOfParents(Ftp));

		// -----------------------------------Sprint04--------------------------------------------//
		
		allErrors.addAll(US_Unique_Names.US25_Unique_Names_BirthDate(Ftp));

		for (int i = 0; i < allErrors.size(); i++) {
			ErrorData error = allErrors.get(i);
			System.out.println(error.getErrorType() + " : " + error.getRecordType() + " : " + error.getUserStoryNumber()
					+ " : " + error.getIndividualId() + " : " + error.getErrorDetails());
		}
	}

	private static void printListOfDeceasedIndividuals(FamilyTreeParser Ftp) {
		ArrayList<Individual> deceasedPeople = US_All_Lists.US_listOfDeceased(Ftp);

		// Sprint 2 - US29 List of Deceased - Shreesh Chavan
		// For individuals
		System.out.println();
		System.out.println("List of Deceased");
		String individualOutputFormat = "|%1$-6s|%2$-20s|%3$-10s|%4$-12s|%5$-7s|%6$-9s|%7$-12s|%8$-10s|%9$-10s|%n";
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
		System.out.format(
				"|  ID  |       Name         |  Gender  |  Birthday  |  Age  |  Alive  |    Death   |   Child  |  Spouse  |%n");
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
		for (Individual indi : deceasedPeople) {
			System.out.format(individualOutputFormat, indi.getId(), indi.getName(), indi.getGender(),
					indi.getBirthDate(), indi.getAge(), indi.isAlive(),
					indi.getDeathDate() == null ? "NA" : indi.getDeathDate(),
					indi.getFamilyChildId() == null ? "NA" : "{'" + indi.getFamilyChildId() + "'}",
					indi.getFamilySpouseId() == null ? "NA" : "{'" + indi.getFamilySpouseId() + "'}");

		}
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
		System.out.println();
	}

	private static void printListOfOrphansIndividuals(FamilyTreeParser Ftp) {
		ArrayList<Individual> orphans = US_All_Lists.US_listofOrphans(Ftp);

		// Sprint 3 - US33 List of Orphans - Shreesh Chavan
		// For individuals
		System.out.println("List of Orphans");
		String individualOutputFormat = "|%1$-6s|%2$-20s|%3$-10s|%4$-12s|%5$-7s|%6$-9s|%7$-12s|%8$-10s|%9$-10s|%n";
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
		System.out.format(
				"|  ID  |       Name         |  Gender  |  Birthday  |  Age  |  Alive  |    Death   |   Child  |  Spouse  |%n");
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
		for (Individual indi : orphans) {
			System.out.format(individualOutputFormat, indi.getId(), indi.getName(), indi.getGender(),
					indi.getBirthDate(), indi.getAge(), indi.isAlive(),
					indi.getDeathDate() == null ? "NA" : indi.getDeathDate(),
					indi.getFamilyChildId() == null ? "NA" : "{'" + indi.getFamilyChildId() + "'}",
					indi.getFamilySpouseId() == null ? "NA" : "{'" + indi.getFamilySpouseId() + "'}");

		}
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
		System.out.println();
	}

	// Sprint 3 - US31 Living Singles - Shreesh Chavan
	// For individuals
	private static void printListOfSingleLivingIndividuals(FamilyTreeParser Ftp) {
		ArrayList<Individual> singles = US_All_Lists.US_listOflivingSingle(Ftp);

		System.out.println("List of Singles");
		String individualOutputFormat = "|%1$-6s|%2$-20s|%3$-10s|%4$-12s|%5$-7s|%6$-9s|%7$-12s|%8$-10s|%9$-10s|%n";
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
		System.out.format(
				"|  ID  |       Name         |  Gender  |  Birthday  |  Age  |  Alive  |    Death   |   Child  |  Spouse  |%n");
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
		for (Individual indi : singles) {
			System.out.format(individualOutputFormat, indi.getId(), indi.getName(), indi.getGender(),
					indi.getBirthDate(), indi.getAge(), indi.isAlive(),
					indi.getDeathDate() == null ? "NA" : indi.getDeathDate(),
					indi.getFamilyChildId() == null ? "NA" : "{'" + indi.getFamilyChildId() + "'}",
					indi.getFamilySpouseId() == null ? "NA" : "{'" + indi.getFamilySpouseId() + "'}");

		}
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
		System.out.println();
	}

	// Sprint 3 - US27 Living Married Individuals - Allan
	private static void printListOfLivingMarried(FamilyTreeParser Ftp) {
		System.out.println("List of Living Married");
		ArrayList<Individual> livingMarried = US_All_Lists.listOfLivingMarried(Ftp);

		String individualOutputFormat = "|%1$-6s|%2$-20s|%3$-10s|%4$-12s|%5$-7s|%6$-9s|%7$-12s|%8$-10s|%9$-10s|%n";
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
		System.out.format(
				"|  ID  |       Name         |  Gender  |  Birthday  |  Age  |  Alive  |    Death   |   Child  |  Spouse  |%n");
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");

		for (Individual indi : livingMarried) {
			System.out.format(individualOutputFormat, indi.getId(), indi.getName(), indi.getGender(),
					indi.getBirthDate(), indi.getAge(), indi.isAlive(),
					indi.getDeathDate() == null ? "NA" : indi.getDeathDate(),
					indi.getFamilyChildId() == null ? "NA" : "{'" + indi.getFamilyChildId() + "'}",
					indi.getFamilySpouseId() == null ? "NA" : "{'" + indi.getFamilySpouseId() + "'}");
		}
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
		System.out.println();
	}

	public static void printListOfRecentBirths(FamilyTreeParser Ftp) {
		ArrayList<Individual> listOfRecentBirths = US_All_Lists.US35_findListOfRecentBirths(Ftp);
		System.out.println("US35: List of recent births");
		String individualOutputFormat = "|%1$-6s|%2$-20s|%3$-10s|%4$-12s|%5$-7s|%6$-9s|%7$-12s|%8$-10s|%9$-10s|%n";
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
		System.out.format(
				"|  ID  |       Name         |  Gender  |  Birthday  |  Age  |  Alive  |    Death   |   Child  |  Spouse  |%n");
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
		for (int i = 0; i < listOfRecentBirths.size(); i++) {
			Individual indi = listOfRecentBirths.get(i);
			System.out.format(individualOutputFormat, indi.getId(), indi.getName(), indi.getGender(),
					indi.getBirthDate(), indi.getAge(), indi.isAlive(),
					indi.getDeathDate() == null ? "NA" : indi.getDeathDate(),
					indi.getFamilyChildId() == null ? "NA" : "{'" + indi.getFamilyChildId() + "'}",
					indi.getFamilySpouseId() == null ? "NA" : "{'" + indi.getFamilySpouseId() + "'}");
		}
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
		System.out.println();
	}

	public static void printListOfRecentDeaths(FamilyTreeParser Ftp) {
		ArrayList<Individual> listOfRecentDeaths = US_All_Lists.US36_findListOfRecentDeaths(Ftp);
		System.out.println("US36: List of recent deaths");
		String individualOutputFormat = "|%1$-6s|%2$-20s|%3$-10s|%4$-12s|%5$-7s|%6$-9s|%7$-12s|%8$-10s|%9$-10s|%n";
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
		System.out.format(
				"|  ID  |       Name         |  Gender  |  Birthday  |  Age  |  Alive  |    Death   |   Child  |  Spouse  |%n");
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
		for (int i = 0; i < listOfRecentDeaths.size(); i++) {
			Individual indi = listOfRecentDeaths.get(i);
			System.out.format(individualOutputFormat, indi.getId(), indi.getName(), indi.getGender(),
					indi.getBirthDate(), indi.getAge(), indi.isAlive(),
					indi.getDeathDate() == null ? "NA" : indi.getDeathDate(),
					indi.getFamilyChildId() == null ? "NA" : "{'" + indi.getFamilyChildId() + "'}",
					indi.getFamilySpouseId() == null ? "NA" : "{'" + indi.getFamilySpouseId() + "'}");
		}
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
		System.out.println();
	}

	// Sprint 4 - US39 Living Married Individuals - Allan
	private static void printListOfUpcomingAnniversary(FamilyTreeParser Ftp) {
		System.out.println("List of Living Couple Upcoming Anniversaries within 30 days");
		ArrayList<Individual> upCommingAnniversaries = US_All_Lists.listOfUpcoming_Anniversaries(Ftp);

		String individualOutputFormat = "|%1$-6s|%2$-20s|%3$-10s|%4$-12s|%5$-7s|%6$-9s|%7$-12s|%8$-10s|%9$-10s|%n";
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
		System.out.format(
				"|  ID  |       Name         |  Gender  |  Birthday  |  Age  |  Alive  |    Death   |   Child  |  Spouse  |%n");
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");

		for (Individual indi : upCommingAnniversaries) {
			System.out.format(individualOutputFormat, indi.getId(), indi.getName(), indi.getGender(),
					indi.getBirthDate(), indi.getAge(), indi.isAlive(),
					indi.getDeathDate() == null ? "NA" : indi.getDeathDate(),
					indi.getFamilyChildId() == null ? "NA" : "{'" + indi.getFamilyChildId() + "'}",
					indi.getFamilySpouseId() == null ? "NA" : "{'" + indi.getFamilySpouseId() + "'}");
		}
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
		System.out.println();
	}
	private static void printListOfUpcomingBirthdays(FamilyTreeParser Ftp) {
		System.out.println("List of individuals with Upcoming Birthdays within 30 days");
		ArrayList<Individual> upCommingBirthdays = US_All_Lists.US38_listOfUpcomingBirthdays(Ftp);

		String individualOutputFormat = "|%1$-6s|%2$-20s|%3$-10s|%4$-12s|%5$-7s|%6$-9s|%7$-12s|%8$-10s|%9$-10s|%n";
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
		System.out.format(
				"|  ID  |       Name         |  Gender  |  Birthday  |  Age  |  Alive  |    Death   |   Child  |  Spouse  |%n");
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");

		for (Individual indi : upCommingBirthdays) {
			System.out.format(individualOutputFormat, indi.getId(), indi.getName(), indi.getGender(),
					indi.getBirthDate(), indi.getAge(), indi.isAlive(),
					indi.getDeathDate() == null ? "NA" : indi.getDeathDate(),
					indi.getFamilyChildId() == null ? "NA" : "{'" + indi.getFamilyChildId() + "'}",
					indi.getFamilySpouseId() == null ? "NA" : "{'" + indi.getFamilySpouseId() + "'}");
		}
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
		System.out.println();
	}
	
	private static void printListOfCouplesWithHighAgeDifference(FamilyTreeParser Ftp) {

		// For Families
		System.out.println("List of Families with high age difference between spouses at the time of marraige");
		String familyOutputFormat = "|%1$-6s|%2$-16s|%3$-16s|%4$-14s|%5$-23s|%6$-14s|%7$-23s|%8$-113s|%n";
		System.out.format(
				"+------+----------------+----------------+--------------+-----------------------+--------------+-----------------------+-----------------------------------------------------------------------------------------------------------------+%n");
		System.out.format(
				"|  ID  |    Married     |    Divorced    |  Husband Id  |      Husband Name     |    Wife Id   |      Wife Name        |      Children                                                                                                   |%n");
		System.out.format(
				"+------+----------------+----------------+--------------+-----------------------+--------------+-----------------------+-----------------------------------------------------------------------------------------------------------------+%n");
		for (Family fam: US_All_Lists.US34_listOfCouplesWithLargeAgeDifference(Ftp)) {
			String wifeName = fam.getWifeId() == null ? "NULL"
					: (Ftp.individualMap.get(fam.getWifeId()) == null ? "NULL"
							: Ftp.individualMap.get(fam.getWifeId()).getName());
			String husbandName = fam.getHusbandId() == null ? "NULL"
					: (Ftp.individualMap.get(fam.getHusbandId()) == null ? "NULL"
							: Ftp.individualMap.get(fam.getHusbandId()).getName());
			System.out.format(familyOutputFormat, fam.getId(),
					fam.getMarriageDate() == null ? "NA" : fam.getMarriageDate(),
					fam.getDivorcedDate() == null ? "NA" : fam.getDivorcedDate(),
					fam.getHusbandId() == null ? "NULL" : fam.getHusbandId(), husbandName,
					fam.getWifeId() == null ? "NULL" : fam.getWifeId(), wifeName,
					fam.getChildId().isEmpty() ? "NA" : "{'" + String.join("', '", fam.getChildId()) + "'}");
		}
		System.out.format(
				"+------+----------------+----------------+--------------+-----------------------+--------------+-----------------------+-----------------------------------------------------------------------------------------------------------------+%n");
		System.out.println();
	}

	public static void main(String[] args) throws IOException, ParseException {
		FamilyTreeParser Ftp = new FamilyTreeParser();
		Ftp.setValidTags();
		Ftp.FamilyTreeParserCheck();
		printIndividualList(Ftp);
		printFamilyList(Ftp);
		printErrorDetails(Ftp);
		/* Display List User stories */
		// --------------Sprint03-----------------//
		printListOfDeceasedIndividuals(Ftp);
		printListOfOrphansIndividuals(Ftp);
		printListOfSingleLivingIndividuals(Ftp);
		printListOfLivingMarried(Ftp);
		// --------------Sprint04-----------------//
		printListOfRecentBirths(Ftp);
		printListOfRecentDeaths(Ftp);
		printListOfUpcomingAnniversary(Ftp);
		printListOfUpcomingBirthdays(Ftp);
		printListOfCouplesWithHighAgeDifference(Ftp);
	}

}
