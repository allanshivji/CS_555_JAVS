package gedcom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class US_CheckValidity {
	public static ArrayList<ErrorData> check(FamilyTreeParser Ftp) {
		Map<String, Individual> treeMap = new TreeMap<String, Individual>(Ftp.individualMap);
		ArrayList<ErrorData> errorList = new ArrayList<ErrorData>();

		// check Individual
		for (Map.Entry<String, Individual> entry : treeMap.entrySet()) {
			Individual indi = entry.getValue();
			// US05
			if (check_age(indi)!=null)
				errorList.add(check_age(indi));
			// US10
			if (check_marriage_age(indi)!=null)
				errorList.add(check_marriage_age(indi));
		}
		
		// check Family
		for (Family familyRecord : Ftp.familyList) {
			if (familyRecord.getMarriageDate() != null) {
				//Individual husband
				Individual husband = Ftp.individualMap.get(familyRecord.getHusbandId());
				//Individual wife
				Individual wife = Ftp.individualMap.get(familyRecord.getWifeId());
				
				ArrayList<String> childrenList = familyRecord.getChildId();
				for (String childId : childrenList) {
					//Individual child
					Individual child = Ftp.individualMap.get(childId);
					
					ArrayList<ErrorData> tmp_list  = new ArrayList<ErrorData>();
					// US12
					tmp_list.addAll(check_parents_not_too_old(husband, wife, child));
					// US17
					tmp_list.addAll(check_parents_no_marriages_to_children(husband, wife, child));
					
					for (ErrorData error : tmp_list) {
						error.setUserStoryNumber(familyRecord.getId());
					}
					errorList.addAll(tmp_list);
				}
			}
		}
		return errorList;
	}

	// Jiayuan Liu: Sprint1 US07 Less then 150 years old
	public static ErrorData check_age(Individual indi) {
		if (indi.getAge() >= 150) {
			ErrorData error = new ErrorData(
				"ERROR", 
				"INDIVIDUAL",
				"US05",
				indi.getId(),
				"Age of " + indi.getName() + "(" + indi.getId() + ")" + " is more then 150 years old."
				);
			return error;
		}
		else
			return null;
	}

	// Jiayuan Liu: Sprint1 US10 Marriage after 14
	public static ErrorData check_marriage_age(Individual indi) {
		if (indi.getFamilySpouseId() != null && indi.getAge() <= 14) {
			ErrorData error = new ErrorData(
					"ERROR",
					"INDIVIDUAL",
					"US10",
					indi.getId(),
					indi.getName() + "(" + indi.getId() + ")" + " got married before 14 years old."
					);
			return error;
		}
		else
			return null;
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
	
	public static ArrayList<ErrorData> check_parents_not_too_old(Individual husband, Individual wife, Individual child){
		ArrayList<ErrorData> errorList = new ArrayList<ErrorData>();
		//check_father_not_too_old
		if(husband!=null && child!=null&& husband.getAge()-child.getAge() > 80 ) {
			ErrorData error = new ErrorData(
					"ERROR",
					"FAMILY",
					"",
					"US12",
					"father " + husband.getId() + " " + husband.getName() + "is more than 80 years older than his children" + child.getId() + " " + child.getName()
					);
			errorList.add(error);
		}
		//check_mother_not_too_old
		if( wife!=null && child!=null&& wife.getAge()!=0 && child.getAge()!=0 && wife.getAge()-child.getAge() > 60 ) {
			ErrorData error = new ErrorData(
					"ERROR",
					"FAMILY",
					"",
					"US12",
					"mother " + wife.getId() + " " + wife.getName() + "is more than 60 years older than his child" + child.getId() + " " + child.getName()
					);
			errorList.add(error);
		}
		return errorList;
	}
	
//	public static boolean check_father_not_too_old(int husbandAge, int childAge) {
//		if ((husbandAge - childAge) > 80) {
//			return false;
//		}
//		return true;
//	}
//
//	public static boolean check_mother_not_too_old(int wifeAge, int childAge) {
//		if ((wifeAge - childAge) > 60) {
//			return false;
//		}
//		return true;
//	}

	// Jiayuan Liu: Sprint2 US17 No marriages to children
	// Parents should not marry any of their children
	public static ArrayList<ErrorData> check_parents_no_marriages_to_children(Individual husband, Individual wife, Individual child){
		ArrayList<ErrorData> errorList = new ArrayList<ErrorData>();
		//check father no marriages to children
		if (wife!=null && child!=null&& wife.getId().equals(child.getId())) {
			ErrorData error = new ErrorData(
					"ERROR",
					"FAMILY",
					"",
					"US17",
					"father " + husband.getId() + " " + husband.getName() + "married with his child" + child.getId() + " " + child.getName()
					);
			errorList.add(error);
		}
		//check mother no marriages to children
		if (husband!=null && child!=null &&husband.getId().equals(child.getId())) {
			ErrorData error = new ErrorData(
					"ERROR",
					"FAMILY",
					"",
					"US17",
					"mother " + wife.getId() + " " + wife.getName() + "married with her child" + child.getId() + " " + child.getName()
					);
			errorList.add(error);
		}
		return errorList;
	}
	
//	public static boolean check_father_no_marriages_to_children(String wifeId, String childId) {
//		if (wifeId.equals(childId)) {
//			return false;
//		}
//		return true;
//	}
//
//	public static boolean check_mother_no_marriages_to_children(String husbandId, String childId) {
//		if (husbandId.equals(childId)) {
//			return false;
//		}
//		return true;
//	}
}
