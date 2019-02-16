package gedcom;

import java.time.LocalDate;
import java.util.ArrayList;

public class MultiIndividualFamilyData {
	public static ArrayList<ErrorData> errorList = new ArrayList<ErrorData>();
	
	public static ArrayList<ErrorData> US_Marriage_Before_Death(FamilyTreeParser Ftp) {
		String husbandId=null;
		String wifeId=null;
		LocalDate marriageDate;
		for(Family familyRecord : Ftp.familyList) {
			if(familyRecord.getMarriageDate()!=null) {
				marriageDate=familyRecord.getMarriageDate();
				husbandId=familyRecord.getHusbandId();
				wifeId=familyRecord.getWifeId();
				String[] famIds = {husbandId,wifeId};
				for(String id : famIds) {
					if(id!=null && Ftp.individualMap.get(id).getDeathDate() != null && marriageDate.isAfter(Ftp.individualMap.get(id).getDeathDate())) {
						ErrorData error = new ErrorData();
						error.setErrorType("ERROR");
						error.setRecordType("INDIVIDUAL");
						error.setIndividualId(id);
						error.setUserStoryNumber("US05");
						error.setErrorDetails("MarriageDate "+marriageDate+" for "+id+" is after the death date "+Ftp.individualMap.get(id).getDeathDate());
						errorList.add(error);
					}
				}
		   }
	  }
		return errorList;
   }
}
