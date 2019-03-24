package gedcom;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class UniqueFamilyBySpouses {
	
	public static ArrayList<ErrorData> US_24_Method_To_Find_Unique_Family_By_Spouse_In_Given_Family_Gedcom_Data(FamilyTreeParser Ftp) {
		ArrayList<ErrorData> errorList = new ArrayList<ErrorData>();
		
		for(Family familyRecord1 :  Ftp.familyList) {
			String familyId1=familyRecord1.getId();
			String husbandName1 = Ftp.individualMap.get(familyRecord1.getHusbandId()).getName();
			String wifeName1 = Ftp.individualMap.get(familyRecord1.getWifeId()).getName();
			LocalDate marriageDate1 = familyRecord1.getMarriageDate();
			
			if(husbandName1!=null && wifeName1!=null && marriageDate1!=null) {
				
				for(Family familyRecord2 : Ftp.familyList) {
					String familyId2=familyRecord2.getId();
					String husbandName2 = Ftp.individualMap.get(familyRecord2.getHusbandId()).getName();
					String wifeName2 = Ftp.individualMap.get(familyRecord2.getWifeId()).getName();
					LocalDate marriageDate2 = familyRecord2.getMarriageDate();
					
					if(husbandName2!=null && wifeName2!=null && marriageDate2!=null) {
						if(familyId2!=familyId1) {
							if(husbandName1.equals(husbandName2) && wifeName1.equals(wifeName2) && marriageDate1.isEqual(marriageDate2)) {
								ErrorData error = new ErrorData();
								error.setErrorType("ERROR");
								error.setRecordType("FAMILY");
								error.setIndividualId(familyId2);
								error.setUserStoryNumber("US24");
								error.setErrorDetails("The husband "+husbandName2+"("+familyRecord2.getHusbandId()+") and wife "+wifeName2+"("+familyRecord2.getWifeId()+") have more than one family record with the same marriage date "+marriageDate2);
								errorList.add(error);
							}
						}
					}
				}
			}
			
		}
		
	return errorList;
}
	
	public static ArrayList<ErrorData> findDuplicateSpousedetails(FamilyTreeParser Ftp) {
		
		ArrayList<ErrorData> errorList = new ArrayList<ErrorData>();
		HashMap<String,LocalDate> familyMap = new HashMap<String,LocalDate>();
		
		for(Family familyRecord : Ftp.familyList ) {
			String husbandName = Ftp.individualMap.get(familyRecord.getHusbandId()).getName();
			String wifeName = Ftp.individualMap.get(familyRecord.getWifeId()).getName();
			LocalDate marriageDate = familyRecord.getMarriageDate();
					
			if(husbandName!=null && wifeName!=null && marriageDate!=null) {
				String husbandWifeName = husbandName+" "+wifeName;
				if(familyMap.containsKey(husbandWifeName) && marriageDate.isEqual(familyMap.get(husbandWifeName))) {
					ErrorData error = new ErrorData();
					error.setErrorType("ERROR");
					error.setRecordType("FAMILY");
					error.setIndividualId(familyRecord.getId());
					error.setUserStoryNumber("US24");
					error.setErrorDetails("The husband "+husbandName+"("+familyRecord.getHusbandId()+") and wife "+wifeName+"("+familyRecord.getWifeId()+") have more than one family record with the same marriage date "+marriageDate);
					errorList.add(error);
			} else {
				familyMap.put(husbandWifeName, marriageDate);
			}
			
		}
	}
	return errorList;
}
		
	
	
}
