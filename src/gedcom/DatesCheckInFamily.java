package gedcom;

import java.time.LocalDate;
import java.util.ArrayList;

public class DatesCheckInFamily {
	public static ArrayList<ErrorData> us04MarriageBeforeDivorce(FamilyTreeParser Ftp) {
		ArrayList<ErrorData> errorList = new ArrayList<ErrorData>();
		for(Family familyRecord : Ftp.familyList) {
			LocalDate marriageDate = familyRecord.getMarriageDate();
			LocalDate divorcedDate = familyRecord.getDivorcedDate();
			if(marriageDate!=null) {
				if(divorcedDate!=null && marriageDate.isAfter(divorcedDate)) {
					ErrorData error = new ErrorData();
					error.setErrorType("ERROR");
					error.setRecordType("FAMILY");
					error.setIndividualId(familyRecord.getHusbandId());
					error.setUserStoryNumber("US04");
					error.setErrorDetails("Marriage Date "+marriageDate+" in the family "+familyRecord.getId()+" is after the divorce date "+divorcedDate);
					errorList.add(error);
				}
			}
			
		}
		return errorList;
	}
}
