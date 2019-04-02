package gedcom;

import java.util.ArrayList;

public class US_Siblings {
	
	// Shreesh Chavan: Sprint2 US18 siblings shouldn't marry
	public static ArrayList<ErrorData> checkSiblingMarraige(FamilyTreeParser ftp){
		ArrayList<ErrorData> errorList = new ArrayList<ErrorData>();
		ArrayList <String> temp = new ArrayList<String>();
		ArrayList<ArrayList> checkHalfSiblings = new ArrayList<ArrayList>();
		for(Family familyRecord:ftp.familyList) {
			for (Family famrec:ftp.familyList) {

				if (familyRecord.getChildId().contains(famrec.getHusbandId())&&familyRecord.getChildId().contains(famrec.getWifeId())) {
					ErrorData error = new ErrorData();
					error.setErrorType("ERROR");
					error.setRecordType("FAMILY");
					error.setIndividualId(famrec.getId());
					error.setUserStoryNumber("US18");
					error.setErrorDetails(famrec.getHusbandId() +" and "+ famrec.getWifeId()+" are married siblings.");
					errorList.add(error);
				}
				if((familyRecord.getId()!=famrec.getId())&&((familyRecord.getHusbandId() == famrec.getHusbandId()))) {
//					||familyRecord.getWifeId() == famrec.getWifeId())
					System.out.println("hi");
					temp.addAll(familyRecord.getChildId());
					temp.addAll(famrec.getChildId());
					checkHalfSiblings.add(temp);
					temp.clear();
				}
			}
		}
		for(ArrayList halfsiblings:checkHalfSiblings) {
			for(Family familyRecord:ftp.familyList) {
				if (halfsiblings.contains(familyRecord.getHusbandId())&&halfsiblings.contains(familyRecord.getWifeId())) {
					System.out.println("hi");
					ErrorData error = new ErrorData();
					error.setErrorType("ERROR");
					error.setRecordType("FAMILY");
					error.setIndividualId(familyRecord.getId());
					error.setUserStoryNumber("US18");
					error.setErrorDetails(familyRecord.getHusbandId() +" and "+ familyRecord.getWifeId()+" are married siblings.");
					errorList.add(error);
				}
			}
		}
		return errorList;
	}
	
	// Vidya Maiya: Sprint02 : US15: Fewer than 15 siblings
	public static ArrayList<ErrorData> us15FewerThanFifteenSiblings(FamilyTreeParser Ftp) {
		ArrayList<ErrorData> errorList = new ArrayList<ErrorData>();
		for (Family familyRecord : Ftp.familyList) {
			if (familyRecord.getChildId().size() > 15) {
				ErrorData error = new ErrorData();
				error.setErrorType("ANOMALY");
				error.setRecordType("FAMILY");
				error.setIndividualId(familyRecord.getId());
				error.setUserStoryNumber("US15");
				String childName = Ftp.individualMap.get(familyRecord.getChildId().get(0)).getName();
				error.setErrorDetails("The child " + familyRecord.getChildId().get(0) + " (" + childName
						+ ") has more than 15 siblings");
				errorList.add(error);
			}
		}
		return errorList;
	}

}
