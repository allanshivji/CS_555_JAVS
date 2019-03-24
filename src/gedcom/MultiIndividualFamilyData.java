package gedcom;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

public class MultiIndividualFamilyData {
	// Shreesh Chavan: Sprint2 US29 check list of deceased
	public static ArrayList<Individual> listOfDeceased(FamilyTreeParser ftp){
		ArrayList<Individual> deceasedArray = new ArrayList<Individual>();
		HashMap<String, Individual> enter = ftp.individualMap;
		for (Entry<String, Individual> entry : enter.entrySet()) {
			if (entry.getValue().isAlive()=="False") {
			deceasedArray.add(entry.getValue());
			}
		}
		return deceasedArray;
	}
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
//						||familyRecord.getWifeId() == famrec.getWifeId())
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
	// Shreesh Chavan: Sprint1 US11 check bigamy
	public static ArrayList<ErrorData> checkBigamy(FamilyTreeParser ftp) {
		ArrayList<ErrorData> errorList = new ArrayList<ErrorData>();
		ArrayList<Family> fam = new ArrayList<Family>();
		ArrayList<Family> faultyfam = new ArrayList<Family>();
		boolean flag;
		fam.add(ftp.familyList.get(0));
		for (Family familyRecord : ftp.familyList.subList(1, ftp.familyList.size())) {
			int counter = 1;
			fam.add(familyRecord);
			counter++;
			for (Family famrec : fam.subList(0, fam.size() - 1)) {
				flag = false;
				if (familyRecord.getHusbandId().equals(famrec.getHusbandId())) {

					if (famrec.getDivorcedDate() != null) {

						if (famrec.getDivorcedDate().compareTo(familyRecord.getMarriageDate()) > 0) {
							flag = true;
							faultyfam.add(familyRecord);
						}
					} else if (familyRecord.getDivorcedDate() != null) {

						if (familyRecord.getDivorcedDate().compareTo(famrec.getMarriageDate()) > 0) {
							flag = true;
							faultyfam.add(famrec);
							counter++;
						}
					} else if (ftp.individualMap.get(familyRecord.getWifeId()).getDeathDate() != null) {
						if (ftp.individualMap.get(familyRecord.getWifeId()).getDeathDate()
								.compareTo(famrec.getMarriageDate()) > 0) {
							flag = true;
							faultyfam.add(famrec);
							counter++;
//							break;
						}

					} else if (ftp.individualMap.get(famrec.getWifeId()).getDeathDate() != null) {
						if (ftp.individualMap.get(famrec.getWifeId()).getDeathDate()
								.compareTo(familyRecord.getMarriageDate()) > 0) {
							flag = true;
							faultyfam.add(familyRecord);
						}
					} else {
						flag = true;
						faultyfam.add(familyRecord);
					}
					if (flag) {
						ErrorData error = new ErrorData();
						error.setErrorType("ERROR");
						error.setRecordType("INDIVIDUAL");
						error.setIndividualId(familyRecord.getHusbandId());
						error.setUserStoryNumber("US11");
						error.setErrorDetails(
								familyRecord.getHusbandId() + " has married without seperation from previous spouse.");
						errorList.add(error);
					}

				} else if (familyRecord.getWifeId().equals(famrec.getWifeId())) {
					if (famrec.getDivorcedDate() != null) {
						if (famrec.getDivorcedDate().compareTo(familyRecord.getMarriageDate()) > 0) {
							flag = true;
							faultyfam.add(familyRecord);
						}
					} else if (familyRecord.getDivorcedDate() != null) {
						if (familyRecord.getDivorcedDate().compareTo(famrec.getMarriageDate()) > 0) {
							flag = true;
							faultyfam.add(famrec);
							counter++;
							break;

						}
					} else if (ftp.individualMap.get(familyRecord.getHusbandId()).getDeathDate() != null) {
						if (ftp.individualMap.get(familyRecord.getHusbandId()).getDeathDate()
								.compareTo(famrec.getMarriageDate()) > 0) {
							flag = true;
							faultyfam.add(famrec);
							counter++;
							break;
						}

					} else if (ftp.individualMap.get(famrec.getHusbandId()).getDeathDate() != null) {
						if (ftp.individualMap.get(famrec.getHusbandId()).getDeathDate()
								.compareTo(familyRecord.getMarriageDate()) > 0) {
							flag = true;
							faultyfam.add(familyRecord);
						}
					} else {
						flag = true;
						faultyfam.add(familyRecord);
					}
					if (flag) {
						ErrorData error = new ErrorData();
						error.setErrorType("ERROR");
						error.setRecordType("INDIVIDUAL");
						error.setIndividualId(familyRecord.getWifeId());
						error.setUserStoryNumber("US11");
						error.setErrorDetails(
								familyRecord.getWifeId() + " has married without seperation from previous spouse.");
						errorList.add(error);
					}

				} else {
//					if(fam.get(fam.size()-1).getId()!=familyRecord.getId())
//					fam.add(familyRecord);
//					 System.out.println(familyRecord.getId());

//					break;
				}

			}
//			fam.add(familyRecord);
		}
		return errorList;
	}

	// Allan: Sprint 2 US21 - Correct gender of Husband and Wife

	public static ArrayList<ErrorData> US21_check_Gender_Role(FamilyTreeParser Ftp) {
		ArrayList<ErrorData> errorList = new ArrayList<ErrorData>();

		HashMap<String, Individual> individualDetails = Ftp.individualMap;

		for (Family familyRecord : Ftp.familyList) {

			if (familyRecord.getHusbandId() != null && familyRecord.getWifeId() != null) {

				String husbId = familyRecord.getHusbandId();
				String wifeId = familyRecord.getWifeId();
				String husbGender = (Ftp.individualMap.get(husbId) == null? "NULL":Ftp.individualMap.get(husbId).getGender());
				String wifeGender = (Ftp.individualMap.get(wifeId) == null? "NULL": Ftp.individualMap.get(wifeId).getGender());
				String name = "";

				String[] famId = { husbId, wifeId };
				if (husbGender.equals(wifeGender)) {
					int i = 0;
					for (String id : famId) {

						ErrorData error = new ErrorData();
						error.setErrorType("ERROR");
						error.setRecordType("FAMILY");
						error.setIndividualId(id);
						error.setUserStoryNumber("US21");

						if (i == 0) {
							name = "Husband";
						} else {
							name = "Wife";
						}
						error.setErrorDetails(
								"Gender of " + name + " " + Ftp.individualMap.get(id).getName() + " of family "
										+ familyRecord.getId() + " is " + Ftp.individualMap.get(id).getGender());
						errorList.add(error);
						i++;
					}
				}
			}
		}
		return errorList;
	}
	
	
	// Allan: Sprint 2 US22 - Check individual unique ids - Built in function in FamilyTreeParser.java
	
	// Allan: Sprint 2 US22 - Check unique family ids
	
	public static ArrayList<ErrorData> US22_check_Unique_FamilyId(FamilyTreeParser Ftp){
		
		ArrayList<ErrorData> errorList = new ArrayList<ErrorData>();
		
		ArrayList<String> uniqueids = new ArrayList<String>();
		
		for (Family familyRecord : Ftp.familyList) {
			
			if(uniqueids.contains(familyRecord.getId())){
				
				ErrorData error = new ErrorData();
				error.setErrorType("ERROR");
				error.setRecordType("FAMILY");
				error.setIndividualId(familyRecord.getId());
				error.setUserStoryNumber("US22");
				error.setErrorDetails("Family id "+familyRecord.getId()+" is duplicated.");
				errorList.add(error);
			}
			uniqueids.add(familyRecord.getId());
		}
		return errorList;
	}
	
	
	
	
	// Vidya Maiya: Sprint02 : US15: Fewer than 15 siblings
	public static ArrayList<ErrorData> us15FewerThanFifteenSiblings(FamilyTreeParser Ftp) {
		ArrayList<ErrorData> errorList = new ArrayList<ErrorData>();
		for(Family familyRecord : Ftp.familyList) {
			if(familyRecord.getChildId().size()>15) {
				ErrorData error = new ErrorData();
				error.setErrorType("ANOMALY");
				error.setRecordType("FAMILY");
				error.setIndividualId(familyRecord.getId());
				error.setUserStoryNumber("US15");
				error.setErrorDetails("The child "+familyRecord.getChildId().get(0)+" has more than 15 siblings");
				errorList.add(error);
			}
		}
		return errorList;
	}
}
