package gedcom;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class US_MultiIndividualFamilyData {
	// Shreesh Chavan: Sprint2 US29 check list of deceased
	public static ArrayList<Individual> listOfDeceased(FamilyTreeParser ftp) {
		ArrayList<Individual> deceasedArray = new ArrayList<Individual>();
		HashMap<String, Individual> enter = ftp.individualMap;
		for (Entry<String, Individual> entry : enter.entrySet()) {
			if (entry.getValue().isAlive() == "False") {
				deceasedArray.add(entry.getValue());
			}
		}
		return deceasedArray;
	}
	public static ArrayList<Individual> listOflivingSingle(FamilyTreeParser ftp) {
		ArrayList<Individual> livingSingleArray = new ArrayList<Individual>();
		HashMap<String, Individual> enter = ftp.individualMap;
		for (Entry<String, Individual> entry : enter.entrySet()) {
			if (entry.getValue().isAlive() == "True") {
				if(entry.getValue().getAge() >= 30 && checkIfMarried(entry.getValue().getId(),ftp)!=false)
				{
					livingSingleArray.add(entry.getValue());	
				}
			}
		}
		return livingSingleArray;
	}
	public static boolean checkIfMarried(String id, FamilyTreeParser ftp) {
		ArrayList<Family> family = ftp.familyList;
		for(Family famrec: family) {
//			System.out.println(famrec);
			if(id == famrec.getHusbandId() || id == famrec.getWifeId()) {
				return true;
			}
		}
		return false;
		
	}
	public static ArrayList<Individual> listofOrphans(FamilyTreeParser ftp) {
		ArrayList<Individual> orphansArray = new ArrayList<Individual>();
		HashMap<String, Individual> enter = ftp.individualMap;
		ArrayList<Family> family = ftp.familyList;
		for(Family famrec:family) {
			if(famrec.getChildId().size()>0 && enter.get(famrec.getHusbandId()).isAlive()=="False" && enter.get(famrec.getWifeId()).isAlive()=="False") {
				for(String childid:famrec.getChildId()) {
					if(enter.get(childid).getAge()<18) {
						orphansArray.add(enter.get(childid));
					}
				}
			}
		}
		return orphansArray;
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
				String husbGender = (Ftp.individualMap.get(husbId) == null ? "NULL"
						: Ftp.individualMap.get(husbId).getGender());
				String wifeGender = (Ftp.individualMap.get(wifeId) == null ? "NULL"
						: Ftp.individualMap.get(wifeId).getGender());
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

}
