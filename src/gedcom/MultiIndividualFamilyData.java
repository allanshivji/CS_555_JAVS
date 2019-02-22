package gedcom;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class MultiIndividualFamilyData {
	public static ArrayList<ErrorData> errorList = new ArrayList<ErrorData>();
	
	//Vidya Maiya: Sprint01 : US05: Marriage before death
	public static ArrayList<ErrorData> US05_Marriage_Before_Death(FamilyTreeParser Ftp) {

		for (Family familyRecord : Ftp.familyList) {
			if (familyRecord.getMarriageDate() != null) {
				LocalDate marriageDate = familyRecord.getMarriageDate();
				String husbandId = familyRecord.getHusbandId();
				String wifeId = familyRecord.getWifeId();
				String[] famIds = { husbandId, wifeId };
				for (String id : famIds) {
					if (id != null && Ftp.individualMap.get(id).getDeathDate() != null
							&& marriageDate.isAfter(Ftp.individualMap.get(id).getDeathDate())) {
						ErrorData error = new ErrorData();
						error.setErrorType("ERROR");
						error.setRecordType("INDIVIDUAL");
						error.setIndividualId(id);
						error.setUserStoryNumber("US05");
						error.setErrorDetails("MarriageDate " + marriageDate + " for " + id
								+ " is after the death date " + Ftp.individualMap.get(id).getDeathDate());
						errorList.add(error);
					}
				}
			}
		}
		return errorList;
	}

	//Vidya Maiya: Sprint01: US08: Birth Before Marriage of Parents
	public static ArrayList<ErrorData> US08_Birth_Before_Marriage_Of_Parents(FamilyTreeParser Ftp) {
		
		for(Family family : Ftp.familyList) {
			LocalDate marriageDate = family.getMarriageDate();
			LocalDate divorcedDate = family.getDivorcedDate();
			ArrayList<String> childrenList = family.getChildId();
			
			for(String childId: childrenList) {
				LocalDate birthDate = Ftp.individualMap.get(childId).getBirthDate();
				if(birthDate!=null && marriageDate!= null && birthDate.isBefore(marriageDate)) {
					ErrorData error = new ErrorData();
					error.setErrorType("ERROR");
					error.setRecordType("FAMILY");
					error.setUserStoryNumber("US08");
					error.setIndividualId(childId);
					error.setErrorDetails("The child "+childId+" having birth date "+birthDate+" was born before the marriage date "+marriageDate+" of parents");
					errorList.add(error);
				}
				if(birthDate!=null && divorcedDate!=null && birthDate.isAfter(divorcedDate.plusMonths(9))) {
					ErrorData error = new ErrorData();
					error.setErrorType("ERROR");
					error.setRecordType("FAMILY");
					error.setUserStoryNumber("US08");
					error.setIndividualId(childId);
					error.setErrorDetails("The child "+childId+" having birth date "+birthDate+" was born 9 months after the divorce date "+divorcedDate+" of parents");
					errorList.add(error);
				}
			}
		}
		return errorList;
	}
	// Shreesh Chavan: Sprint1 US01 valid date
	public static ArrayList<ErrorData> testCheckDatesBeforeCurrentDate(FamilyTreeParser ftp) throws ParseException {
		LocalDate marriageDate;
		String husbandId;
		String wifeId;
		for (Family familyRecord : ftp.familyList) {
			if (familyRecord.getMarriageDate() != null) {
				marriageDate = familyRecord.getMarriageDate();
				husbandId = familyRecord.getHusbandId();
				wifeId = familyRecord.getWifeId();
				String[] famIds = { husbandId, wifeId };
				for (String id : famIds) {
					if (CheckValidity.checkDatesBeforeCurrentDate(marriageDate)) {
						ErrorData error = new ErrorData();
						error.setErrorType("ERROR");
						error.setRecordType("INDIVIDUAL");
						error.setIndividualId(id);
						error.setUserStoryNumber("US01");
						error.setErrorDetails(
								"MarriageDate " + marriageDate + " for " + id + " is after today's date.");
						errorList.add(error);
					}
					if ((familyRecord.getDivorcedDate() != null)
							&& (CheckValidity.checkDatesBeforeCurrentDate(familyRecord.getDivorcedDate()))) {
						ErrorData error = new ErrorData();
						error.setErrorType("ERROR");
						error.setRecordType("INDIVIDUAL");
						error.setIndividualId(id);
						error.setUserStoryNumber("US01");
						error.setErrorDetails("DivorceDate " + familyRecord.getDivorcedDate() + " for " + id
								+ " is after today's date.");
						errorList.add(error);
					}
				}
			}
		}

		HashMap<String, Individual> enter = ftp.individualMap;
		for (Entry<String, Individual> entry : enter.entrySet()) {
			if (CheckValidity.checkDatesBeforeCurrentDate(entry.getValue().getBirthDate())) {
				ErrorData error = new ErrorData();
				error.setErrorType("ERROR");
				error.setRecordType("INDIVIDUAL");
				error.setIndividualId((entry.getValue().getId()));
				error.setUserStoryNumber("US01");
				error.setErrorDetails("BirthDate " + entry.getValue().getBirthDate() + " for "
						+ entry.getValue().getId() + " is after today's date.");
				errorList.add(error);
			}
			if ((entry.getValue().getDeathDate() != null)
					&& (CheckValidity.checkDatesBeforeCurrentDate(entry.getValue().getDeathDate()))) {

				ErrorData error = new ErrorData();
				error.setErrorType("ERROR");
				error.setRecordType("INDIVIDUAL");
				error.setIndividualId(entry.getValue().getId());
				error.setUserStoryNumber("US01");
				error.setErrorDetails("DeathDate " + entry.getValue().getDeathDate() + " for "
						+ entry.getValue().getId() + " is after today's date.");
				errorList.add(error);
			}
		}
		return errorList;
	}
	// Shreesh Chavan: Sprint1 US11 check bigamy
	public static ArrayList<Family> fam = new ArrayList<Family>();
	public static ArrayList<Family> faultyfam = new ArrayList<Family>();
	public static ArrayList<ErrorData> checkBigamy(FamilyTreeParser ftp) {
		boolean flag;
		fam.add(ftp.familyList.get(0));
		for (Family familyRecord : ftp.familyList.subList(1, ftp.familyList.size())) {
			int counter = 1;
			for (Family famrec : fam) {
				flag = false;
				if (familyRecord.getHusbandId() == famrec.getHusbandId()) {
					if (famrec.getDivorcedDate() != null) {
						if (famrec.getDivorcedDate().compareTo(familyRecord.getMarriageDate()) > 0) {
							flag = true;
							faultyfam.add(familyRecord);
						}
					} else if (familyRecord.getDivorcedDate() != null) {
						if (familyRecord.getDivorcedDate().compareTo(famrec.getMarriageDate()) > 0) {
							flag = true;
							fam.remove(counter);
							fam.add(familyRecord);
							faultyfam.add(famrec);
							counter++;
							break;
						}
					} else if (ftp.individualMap.get(familyRecord.getWifeId()).getDeathDate() != null) {
						if (ftp.individualMap.get(familyRecord.getWifeId()).getDeathDate()
								.compareTo(famrec.getMarriageDate()) > 0) {
							flag = true;
							fam.remove(counter);
							fam.add(familyRecord);
							faultyfam.add(famrec);
							counter++;
							break;
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
					if(flag) {
						ErrorData error = new ErrorData();
						error.setErrorType("ERROR");
						error.setRecordType("INDIVIDUAL");
						error.setIndividualId(familyRecord.getHusbandId());
						error.setUserStoryNumber("US11");
						error.setErrorDetails(familyRecord.getHusbandId() + " has married without seperation from previous spouse");
						errorList.add(error);
					}

				} else if (familyRecord.getWifeId() == famrec.getWifeId()) {
					if (famrec.getDivorcedDate() != null) {
						if (famrec.getDivorcedDate().compareTo(familyRecord.getMarriageDate()) > 0) {
							flag = true;
							faultyfam.add(familyRecord);
						}
					} else if (familyRecord.getDivorcedDate() != null) {
						if (familyRecord.getDivorcedDate().compareTo(famrec.getMarriageDate()) > 0) {
							flag = true;
							fam.remove(counter);
							fam.add(familyRecord);
							faultyfam.add(famrec);
							counter++;
							break;
							
						}
					} else if (ftp.individualMap.get(familyRecord.getHusbandId()).getDeathDate() != null) {
						if (ftp.individualMap.get(familyRecord.getHusbandId()).getDeathDate()
								.compareTo(famrec.getMarriageDate()) > 0) {
							flag = true;
							fam.remove(counter);
							fam.add(familyRecord);
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
					if(flag) {
						ErrorData error = new ErrorData();
						error.setErrorType("ERROR");
						error.setRecordType("INDIVIDUAL");
						error.setIndividualId(familyRecord.getWifeId());
						error.setUserStoryNumber("US11");
						error.setErrorDetails(familyRecord.getWifeId() + " has married without seperation from previous spouse");
						errorList.add(error);
					}

				} else {
					fam.add(familyRecord);
					//System.out.println(fam.size());
					counter++;
					break;
				}
				
			}
//			fam.add(familyRecord);
		}
		return errorList;
	}
}