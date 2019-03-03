package gedcom;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class DatesCheckInFamily {

	// Vidya Maiya: Sprint01 : US05: Marriage before death
	public static ArrayList<ErrorData> US05_Marriage_Before_Death(FamilyTreeParser Ftp) {

		ArrayList<ErrorData> errorList = new ArrayList<ErrorData>();

		for (Family familyRecord : Ftp.familyList) {
			if (familyRecord.getMarriageDate() != null) {
				LocalDate marriageDate = familyRecord.getMarriageDate();
				String husbandId = familyRecord.getHusbandId();
				String wifeId = familyRecord.getWifeId();
				LocalDate husbandDeathDate = Ftp.individualMap.get(husbandId).getDeathDate();
				LocalDate wifeDeathDate = Ftp.individualMap.get(wifeId).getDeathDate();
				if (husbandId != null && wifeDeathDate != null && marriageDate.isAfter(wifeDeathDate)) {
					ErrorData error = new ErrorData();
					error.setErrorType("ERROR");
					error.setRecordType("FAMILY");
					error.setIndividualId(husbandId);
					error.setUserStoryNumber("US05");
					error.setErrorDetails("MarriageDate " + marriageDate + " for " + husbandId
							+ " is after his wife's (" + wifeId + ") death date " + wifeDeathDate);
					errorList.add(error);
				}
				if (wifeId != null && husbandDeathDate != null && marriageDate.isAfter(husbandDeathDate)) {
					ErrorData error = new ErrorData();
					error.setErrorType("ERROR");
					error.setRecordType("FAMILY");
					error.setIndividualId(wifeId);
					error.setUserStoryNumber("US05");
					error.setErrorDetails("MarriageDate " + marriageDate + " for " + wifeId
							+ " is after her husband's (" + husbandId + ") death date " + husbandDeathDate);
					errorList.add(error);
				}
			}
		}
		return errorList;
	}

	// Vidya Maiya: Sprint01: US08: Birth Before Marriage of Parents
	public static ArrayList<ErrorData> US08_Birth_Before_Marriage_Of_Parents(FamilyTreeParser Ftp) {

		ArrayList<ErrorData> errorList = new ArrayList<ErrorData>();

		for (Family family : Ftp.familyList) {
			LocalDate marriageDate = family.getMarriageDate();
			LocalDate divorcedDate = family.getDivorcedDate();
			ArrayList<String> childrenList = family.getChildId();

			for (String childId : childrenList) {
				LocalDate birthDate = Ftp.individualMap.get(childId).getBirthDate();
				if (birthDate != null && marriageDate != null && birthDate.isBefore(marriageDate)) {
					ErrorData error = new ErrorData();
					error.setErrorType("ERROR");
					error.setRecordType("FAMILY");
					error.setUserStoryNumber("US08");
					error.setIndividualId(childId);
					error.setErrorDetails("The child " + childId + " having birth date " + birthDate
							+ " was born before the marriage date " + marriageDate + " of parents");
					errorList.add(error);
				}
				if (birthDate != null && divorcedDate != null && birthDate.isAfter(divorcedDate.plusMonths(9))) {
					ErrorData error = new ErrorData();
					error.setErrorType("ERROR");
					error.setRecordType("FAMILY");
					error.setUserStoryNumber("US08");
					error.setIndividualId(childId);
					error.setErrorDetails("The child " + childId + " having birth date " + birthDate
							+ " was born 9 months after the divorce date " + divorcedDate + " of parents");
					errorList.add(error);
				}
			}
		}
		return errorList;
	}

	// Shreesh Chavan: Sprint1 US01 valid date
	public static ArrayList<ErrorData> testCheckDatesBeforeCurrentDate(FamilyTreeParser ftp) throws ParseException {
		ArrayList<ErrorData> errorList = new ArrayList<ErrorData>();
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

	// Allan: Sprint 1 US03 - Birth before Death date

	public static ArrayList<ErrorData> US03_check_Birth_Before_Death(FamilyTreeParser Ftp) {

		ArrayList<ErrorData> errorList = new ArrayList<ErrorData>();

		HashMap<String, Individual> individualDetails = Ftp.individualMap;

		for (Entry<String, Individual> entry : individualDetails.entrySet()) {

			if (entry.getValue().getDeathDate() != null) {

				if (entry.getValue().getBirthDate().isAfter(entry.getValue().getDeathDate())) {

					ErrorData error = new ErrorData();

					error.setErrorType("ERROR");
					error.setRecordType("INDIVIDUAL");
					error.setIndividualId(entry.getValue().getId());
					error.setUserStoryNumber("US03");
					error.setErrorDetails("Birthdate " + entry.getValue().getBirthDate() + " of "
							+ entry.getValue().getName() + " (" + entry.getValue().getId() + ") is after Death Date "
							+ entry.getValue().getDeathDate());
					errorList.add(error);
				}
			}
		}
		return errorList;
	}

	// Allan: Sprint 1 US02 - Birth before Marriage date

	public static ArrayList<ErrorData> US02_check_Birth_Before_Marriage(FamilyTreeParser Ftp) {

		ArrayList<ErrorData> errorList = new ArrayList<ErrorData>();

		HashMap<String, Individual> individualDetails = Ftp.individualMap;

		for (Family familyRecord : Ftp.familyList) {

			if (familyRecord.getMarriageDate() != null) {

				LocalDate marriagedate = familyRecord.getMarriageDate();
				String husbId = familyRecord.getHusbandId();
				String wifeId = familyRecord.getWifeId();

				String[] famId = { husbId, wifeId };

				for (String id : famId) {

					if (id != null && marriagedate != null
							&& Ftp.individualMap.get(id).getBirthDate().isAfter(marriagedate)) {

						ErrorData error = new ErrorData();

						error.setErrorType("ERROR");
						error.setRecordType("INDIVIDUAL");
						error.setIndividualId(id);
						error.setUserStoryNumber("US02");
						error.setErrorDetails(
								"MarriageDate " + marriagedate + " of " + Ftp.individualMap.get(id).getName() + " ("
										+ id + ") is after Birth date " + Ftp.individualMap.get(id).getBirthDate());
						errorList.add(error);
					}
				}
			}
		}
		return errorList;
	}

	// Vidya Maiya: Sprint02 : US04: Marriage before Divorce
	public static ArrayList<ErrorData> us04MarriageBeforeDivorce(FamilyTreeParser Ftp) {
		ArrayList<ErrorData> errorList = new ArrayList<ErrorData>();
		for (Family familyRecord : Ftp.familyList) {
			LocalDate marriageDate = familyRecord.getMarriageDate();
			LocalDate divorcedDate = familyRecord.getDivorcedDate();
			if (marriageDate != null) {
				if (divorcedDate != null && marriageDate.isAfter(divorcedDate)) {
					ErrorData error = new ErrorData();
					error.setErrorType("ERROR");
					error.setRecordType("FAMILY");
					error.setIndividualId(familyRecord.getHusbandId());
					error.setUserStoryNumber("US04");
					error.setErrorDetails("Marriage Date " + marriageDate + " in the family " + familyRecord.getId()
							+ " is after the divorce date " + divorcedDate);
					errorList.add(error);
				}
			}

		}
		return errorList;
	}
}