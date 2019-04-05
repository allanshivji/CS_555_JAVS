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
			String wifeName = fam.getWifeId()== null? "NULL" : (Ftp.individualMap.get(fam.getWifeId()) == null? "NULL": Ftp.individualMap.get(fam.getWifeId()).getName());
			String husbandName = fam.getHusbandId() == null? "NULL" : (Ftp.individualMap.get(fam.getHusbandId())== null? "NULL": Ftp.individualMap.get(fam.getHusbandId()).getName());
			System.out.format(familyOutputFormat, fam.getId(),
					fam.getMarriageDate() == null ? "NA" : fam.getMarriageDate(),
					fam.getDivorcedDate() == null ? "NA" : fam.getDivorcedDate(), 
					fam.getHusbandId() == null? "NULL" : fam.getHusbandId(), 
					husbandName,
					fam.getWifeId() == null? "NULL" : fam.getWifeId(), 
					wifeName,
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
	
		allErrors.addAll(US_MultiIndividualFamilyData.US21_check_Gender_Role(Ftp)); //Allan
		
		allErrors.addAll(FamilyTreeParser.duplicateIndividual); //Allan
		
		allErrors.addAll(US_CheckUniqueness.US22_check_Unique_FamilyId(Ftp)); //Allan
		
		allErrors.addAll(US_DatesCheckInFamily.us04MarriageBeforeDivorce(Ftp));
		
		allErrors.addAll(US_Siblings.us15FewerThanFifteenSiblings(Ftp));

		allErrors.addAll(US_Siblings.checkSiblingMarraige(Ftp));
		
		// ---------------------------------Sprint03--------------------------------------------//
		
		allErrors.addAll(US_CheckUniqueness.findDuplicateSpousedetails(Ftp));
		
		allErrors.addAll(US_DatesCheckInFamily.findBirthBeforeDeathOfParents(Ftp));
		
		//-------------------------------------------------------------------------------------//
		
		for (int i = 0; i < allErrors.size(); i++) {
			ErrorData error = allErrors.get(i);
			System.out.println(error.getErrorType() + " : " + error.getRecordType() + " : " + error.getUserStoryNumber()
					+ " : " + error.getIndividualId() + " : " + error.getErrorDetails());
		}
	}
	private static void printListOfDeceasedIndividuals(FamilyTreeParser Ftp) {
		ArrayList<Individual> deceasedPeople = All_Lists.listOfDeceased(Ftp);

		//Sprint 2 - US29 List of Deceased - Shreesh Chavan
		// For individuals
		System.out.println("List of Deceased");
		String individualOutputFormat = "|%1$-6s|%2$-20s|%3$-10s|%4$-12s|%5$-7s|%6$-9s|%7$-12s|%8$-10s|%9$-10s|%n";
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
		System.out.format(
				"|  ID  |       Name         |  Gender  |  Birthday  |  Age  |  Alive  |    Death   |   Child  |  Spouse  |%n");
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
		for (Individual indi:deceasedPeople) {
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
		ArrayList<Individual> orphans = All_Lists.listofOrphans(Ftp);

		//Sprint 3 - US33 List of Orphans - Shreesh Chavan
		// For individuals
		System.out.println("List of Orphans");
		String individualOutputFormat = "|%1$-6s|%2$-20s|%3$-10s|%4$-12s|%5$-7s|%6$-9s|%7$-12s|%8$-10s|%9$-10s|%n";
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
		System.out.format(
				"|  ID  |       Name         |  Gender  |  Birthday  |  Age  |  Alive  |    Death   |   Child  |  Spouse  |%n");
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
		for (Individual indi:orphans) {
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
	private static void printListOfSingleLivingIndividuals(FamilyTreeParser Ftp) {
		ArrayList<Individual> singles = All_Lists.listOflivingSingle(Ftp);

		//Sprint 3 - US31 Living Singles - Shreesh Chavan
		// For individuals
		System.out.println("List of Singles");
		String individualOutputFormat = "|%1$-6s|%2$-20s|%3$-10s|%4$-12s|%5$-7s|%6$-9s|%7$-12s|%8$-10s|%9$-10s|%n";
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
		System.out.format(
				"|  ID  |       Name         |  Gender  |  Birthday  |  Age  |  Alive  |    Death   |   Child  |  Spouse  |%n");
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
		for (Individual indi:singles) {
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
	
	
	//Sprint 3 - US27 Living Married Individuals - Allan
	private static void printListOfLivingMarried(FamilyTreeParser Ftp) {
		System.out.println("List of Living Married");
		ArrayList<Individual> livingMarried = All_Lists.listOfLivingMarried(Ftp);
		
		String individualOutputFormat = "|%1$-6s|%2$-20s|%3$-10s|%4$-12s|%5$-7s|%6$-9s|%7$-12s|%8$-10s|%9$-10s|%n";
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
		System.out.format(
				"|  ID  |       Name         |  Gender  |  Birthday  |  Age  |  Alive  |    Death   |   Child  |  Spouse  |%n");
		System.out.format(
				"+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
		
		
		for(Individual indi:livingMarried) {
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

	
	public static void main(String[] args) throws IOException, ParseException {
		FamilyTreeParser Ftp = new FamilyTreeParser();
		Ftp.setValidTags();
		Ftp.FamilyTreeParserCheck();
		printIndividualList(Ftp);
		printFamilyList(Ftp);
		printErrorDetails(Ftp);
		printListOfDeceasedIndividuals(Ftp);
		printListOfOrphansIndividuals(Ftp);
		printListOfSingleLivingIndividuals(Ftp);
		printListOfLivingMarried(Ftp);
	}
}
