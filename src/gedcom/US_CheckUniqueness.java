package gedcom;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class US_CheckUniqueness {

	//US24 : Unique families by spouses
	public static ArrayList<ErrorData> findDuplicateSpousedetails(FamilyTreeParser Ftp) {

		ArrayList<ErrorData> errorList = new ArrayList<ErrorData>();
		HashMap<String, LocalDate> familyMap = new HashMap<String, LocalDate>();

		for (Family familyRecord : Ftp.familyList) {
			String husbandName = Ftp.individualMap.get(familyRecord.getHusbandId()).getName();
			String wifeName = Ftp.individualMap.get(familyRecord.getWifeId()).getName();
			LocalDate marriageDate = familyRecord.getMarriageDate();

			if (husbandName != null && wifeName != null && marriageDate != null) {
				String husbandWifeName = husbandName + " " + wifeName;
				if (familyMap.containsKey(husbandWifeName) && marriageDate.isEqual(familyMap.get(husbandWifeName))) {
					ErrorData error = new ErrorData();
					error.setErrorType("ERROR");
					error.setRecordType("FAMILY");
					error.setIndividualId(familyRecord.getId());
					error.setUserStoryNumber("US24");
					error.setErrorDetails("The husband " + husbandName + "(" + familyRecord.getHusbandId()
							+ ") and wife " + wifeName + "(" + familyRecord.getWifeId()
							+ ") have more than one family record with the same marriage date " + marriageDate);
					errorList.add(error);
				} else {
					familyMap.put(husbandWifeName, marriageDate);
				}

			}
		}
		return errorList;
	}

	// Allan: Sprint 2 US22 - Check unique family ids

	public static ArrayList<ErrorData> US22_check_Unique_FamilyId(FamilyTreeParser Ftp) {

		ArrayList<ErrorData> errorList = new ArrayList<ErrorData>();

		ArrayList<String> uniqueids = new ArrayList<String>();

		for (Family familyRecord : Ftp.familyList) {

			if (uniqueids.contains(familyRecord.getId())) {

				ErrorData error = new ErrorData();
				error.setErrorType("ERROR");
				error.setRecordType("FAMILY");
				error.setIndividualId(familyRecord.getId());
				error.setUserStoryNumber("US22");
				error.setErrorDetails("Family id " + familyRecord.getId() + " is duplicated.");
				errorList.add(error);
			}
			uniqueids.add(familyRecord.getId());
		}
		return errorList;
	}

}
