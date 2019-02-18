package gedcom;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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
	
	//Shreesh Chavan: Sprint1 US01 valid date
	public static ArrayList<ErrorData> testCheckDatesBeforeCurrentDate(FamilyTreeParser ftp) throws ParseException {
			LocalDate marriageDate;
			String husbandId;
			String wifeId;
			for(Family familyRecord : ftp.familyList) {
				if(familyRecord.getMarriageDate()!=null) {
					marriageDate=familyRecord.getMarriageDate();	
					husbandId=familyRecord.getHusbandId();
					wifeId=familyRecord.getWifeId();
					String[] famIds = {husbandId,wifeId};
					for(String id : famIds) {
						System.out.println(CheckValidity.checkDatesBeforeCurrentDate(marriageDate));
						if (CheckValidity.checkDatesBeforeCurrentDate(marriageDate)) {
							ErrorData error = new ErrorData();
							error.setErrorType("ERROR");
							error.setRecordType("INDIVIDUAL");
							error.setIndividualId(id);
							error.setUserStoryNumber("US01");
							error.setErrorDetails("MarriageDate "+marriageDate+" for "+id+" is after today's date. This is not acceptable. You are not god to predict the future.");
							errorList.add(error);
						}
						if ((familyRecord.getDivorcedDate()!=null) && (CheckValidity.checkDatesBeforeCurrentDate(familyRecord.getDivorcedDate()))) {
							ErrorData error = new ErrorData();
							error.setErrorType("ERROR");
							error.setRecordType("INDIVIDUAL");
							error.setIndividualId(id);
							error.setUserStoryNumber("US01");
							error.setErrorDetails("DivorceDate "+familyRecord.getDivorcedDate()+" for "+id+" is after today's date. This is not acceptable. You are not god to predict the future.");
							errorList.add(error);
						}
					}
				}
			}
			
				HashMap<String,Individual> enter = ftp.individualMap;
				for (Entry<String, Individual> entry : enter.entrySet()) {
				    if(CheckValidity.checkDatesBeforeCurrentDate(entry.getValue().getBirthDate())) {
							ErrorData error = new ErrorData();
							error.setErrorType("ERROR");
							error.setRecordType("INDIVIDUAL");
							error.setIndividualId((entry.getValue().getId()));
							error.setUserStoryNumber("US01");
							error.setErrorDetails("BirthDate "+entry.getValue().getBirthDate()+" for "+entry.getValue().getId()+" is after today's date. This is not acceptable. You are not god to predict the future.");
							errorList.add(error);
					}
				    if((entry.getValue().getDeathDate()!=null) && (CheckValidity.checkDatesBeforeCurrentDate(entry.getValue().getDeathDate()))) {
				    	
						ErrorData error = new ErrorData();
						error.setErrorType("ERROR");
						error.setRecordType("INDIVIDUAL");
						error.setIndividualId(entry.getValue().getId());
						error.setUserStoryNumber("US01");
						error.setErrorDetails("DeathDate "+entry.getValue().getDeathDate()+" for "+entry.getValue().getId()+" is after today's date. This is not acceptable. You are not god to predict the future.");
						errorList.add(error);
				    }
				} 
        return errorList;
    }	
}