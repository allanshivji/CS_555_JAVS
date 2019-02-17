package gedcom;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Test;

public class DateCheck {
	public static ArrayList<ErrorData> errorList = new ArrayList<ErrorData>();
	@Test
//	public void testCheckDatesBeforeCurrentDate() {
////		fail("Not yet implemented");
//	}
	public static ArrayList<ErrorData> testCheckDatesBeforeCurrentDate(FamilyTreeParser ftp) throws ParseException {
		LocalDate birthdate;
		LocalDate deathdate;
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
					if (CheckValidity.checkDatesBeforeCurrentDate(marriageDate)) {
						ErrorData error = new ErrorData();
						error.setErrorType("ERROR");
						error.setRecordType("INDIVIDUAL");
						error.setIndividualId(id);
						error.setUserStoryNumber("US01");
						error.setErrorDetails("MarriageDate "+marriageDate+" for "+id+" is after today's date. This is not acceptable. You are not god to predict the future.");
						errorList.add(error);
					}
					if ((familyRecord.getMarriageDate()!=null) && !(CheckValidity.checkDatesBeforeCurrentDate(familyRecord.getDivorcedDate()))) {
						ErrorData error = new ErrorData();
						error.setErrorType("ERROR");
						error.setRecordType("INDIVIDUAL");
						error.setIndividualId(id);
						error.setUserStoryNumber("US01");
						error.setErrorDetails("Divorce Date "+familyRecord.getDivorcedDate()+" for "+id+" is after today's date. This is not acceptable. You are not god to predict the future.");
						errorList.add(error);
					}
				}
		   }
	  }
		return errorList;
   }
}
