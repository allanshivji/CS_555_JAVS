package gedcom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class CheckValidity {
	public static ArrayList<ErrorData> check(FamilyTreeParser Ftp) {
		Map<String, Individual> treeMap = new TreeMap<String, Individual>(Ftp.individualMap);
		ArrayList<ErrorData> errorList = new ArrayList<ErrorData>();

		// check Individual
		for (Map.Entry<String, Individual> entry : treeMap.entrySet()) {
			Individual indi = entry.getValue();
			String ID = indi.getId();
			String Name = indi.getName();
			int Age = indi.getAge();
			String SpouseId = indi.getFamilySpouseId();

			// US05
			if (!check_age(Age)) {
				ErrorData error = new ErrorData();
				error.setErrorType("ERROR");
				error.setRecordType("INDIVIDUAL");
				error.setUserStoryNumber("US05");
				error.setIndividualId(ID);
				error.setErrorDetails("Age of " + Name + "(" + ID + ")" + " is more then 150 years old.");
				errorList.add(error);
			}
			// US10
			if (!check_marriage_age(SpouseId, Age)) {
				ErrorData error = new ErrorData();
				error.setErrorType("ERROR");
				error.setRecordType("INDIVIDUAL");
				error.setUserStoryNumber("US10");
				error.setIndividualId(ID);
				error.setErrorDetails(Name + "(" + ID + ")" + " got married before 14 years old.");
				errorList.add(error);
			}
		}
		// check Family
		for (Family familyRecord : Ftp.familyList) {
			if (familyRecord.getMarriageDate() != null) {
				String familyId = familyRecord.getId();
				String husbandId = familyRecord.getHusbandId();
				int husbandAge = Ftp.individualMap.get(husbandId).getAge();
				String husbandName = Ftp.individualMap.get(husbandId).getName();

				String wifeId = familyRecord.getWifeId();
				int wifeAge = Ftp.individualMap.get(wifeId).getAge();
				String wifeName = Ftp.individualMap.get(wifeId).getName();

				ArrayList<String> childrenList = familyRecord.getChildId();
				for (String childId : childrenList) {
					int childAge = Ftp.individualMap.get(childId).getAge();
					String childName = Ftp.individualMap.get(childId).getName();
					// US12
					if (!check_father_not_too_old(husbandAge, childAge)) {
						ErrorData error = new ErrorData();
						error.setErrorType("ERROR");
						error.setRecordType("FAMILY");
						error.setIndividualId(familyId);
						error.setUserStoryNumber("US12");
						error.setErrorDetails("father " + husbandId + " " + husbandName
								+ "is more than 80 years older than his children" + childId + " " + childName);
						errorList.add(error);
					}
					if (!check_mother_not_too_old(wifeAge, childAge)) {
						ErrorData error = new ErrorData();
						error.setErrorType("ERROR");
						error.setRecordType("FAMILY");
						error.setIndividualId(familyId);
						error.setUserStoryNumber("US12");
						error.setErrorDetails("mother " + wifeId + " " + wifeName
								+ "is more than 60 years older than his child" + childId + " " + childName);
						errorList.add(error);
					}
					// US17
					if (!check_father_no_marriages_to_children(wifeId, childId)) {
						ErrorData error = new ErrorData();
						error.setErrorType("ERROR");
						error.setRecordType("FAMILY");
						error.setIndividualId(familyId);
						error.setUserStoryNumber("US17");
						error.setErrorDetails("father " + husbandId + " " + husbandName + "married with his child"
								+ childId + " " + childName);
						errorList.add(error);
					}
					if (!check_mother_no_marriages_to_children(husbandId, childId)) {
						ErrorData error = new ErrorData();
						error.setErrorType("ERROR");
						error.setRecordType("FAMILY");
						error.setIndividualId(familyId);
						error.setUserStoryNumber("US17");
						error.setErrorDetails("mother " + wifeId + " " + wifeName + "married with her child" + childId
								+ " " + childName);
						errorList.add(error);
					}
				}

			}
		}
		return errorList;
	}

	// Jiayuan Liu: Sprint1 US07 Less then 150 years old
	public static boolean check_age(int Age) {
		if (Age >= 150)
			return false;
		else
			return true;
	}

	// Jiayuan Liu: Sprint1 US10 Marriage after 14
	public static boolean check_marriage_age(String SpouseId, int Age) {
		if (SpouseId != null && Age <= 14)
			return false;
		else
			return true;
	}

	// Shreesh Chavan: Sprint1 US01 valid date
//	public static boolean checkDatesBeforeCurrentDate(LocalDate p_dateToBeChecked) throws ParseException {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		Date input = new Date();
//		if(LocalDate.now().isBefore(p_dateToBeChecked)) {
//			System.out.print("hi");
//		}
//		String formattedDate = sdf.format(input);
//		Date date1 = sdf.parse(formattedDate);
//		Date date2 = Date.from(p_dateToBeChecked.atStartOfDay(ZoneId.systemDefault()).toInstant());
//		if (date1.compareTo(date2) > 0) {
//			return false;
//		} else {
//			return true;
//		}
//	}

	// Jiayuan Liu: Sprint2 US12 Parents not too old
	// Mother should be less than 60 years older than her children
	// and father should be less than 80 years older than his children
	public static boolean check_father_not_too_old(int husbandAge, int childAge) {
		if ((husbandAge - childAge) > 80) {
			return false;
		}
		return true;
	}

	public static boolean check_mother_not_too_old(int wifeAge, int childAge) {
		if ((wifeAge - childAge) > 60) {
			return false;
		}
		return true;
	}

	// Jiayuan Liu: Sprint2 US17 No marriages to children
	// Parents should not marry any of their children
	public static boolean check_father_no_marriages_to_children(String wifeId, String childId) {
		if (wifeId.equals(childId)) {
			return false;
		}
		return true;
	}

	public static boolean check_mother_no_marriages_to_children(String husbandId, String childId) {
		if (husbandId.equals(childId)) {
			return false;
		}
		return true;
	}
}
