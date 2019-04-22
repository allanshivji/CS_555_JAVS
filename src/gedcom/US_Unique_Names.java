package gedcom;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class US_Unique_Names {

	// Allan: Sprint 4 US25 - Unique names and Birthdate

	public static ArrayList<ErrorData> US25_Unique_Names_BirthDate(FamilyTreeParser Ftp) {

		ArrayList<ErrorData> errorList = new ArrayList<ErrorData>();

		HashMap<String, Individual> individualDetails = Ftp.individualMap;

		for (Family familyRecord : Ftp.familyList) {

			List<String> childrens = new ArrayList<>();
			childrens = familyRecord.getChildId();

			for (int i = 0; i < childrens.size(); i++) {

				if (childrens.get(i) != "NA") {

					for (int j = i + 1; j < childrens.size(); j++) {

						if ((Ftp.individualMap.get(childrens.get(i)).getName()
								.equals(Ftp.individualMap.get(childrens.get(j)).getName()))
								&& (Ftp.individualMap.get(childrens.get(i)).getBirthDate()
										.equals(Ftp.individualMap.get(childrens.get(j)).getBirthDate()))) {

							ErrorData error = new ErrorData();
							error.setErrorType("ERROR");
							error.setRecordType("FAMILY");
							error.setIndividualId(familyRecord.getId());
							error.setUserStoryNumber("US25");
							error.setErrorDetails("Name and Birthdate of childrens ("
									+ Ftp.individualMap.get(childrens.get(i)).getId() + ", "
									+ Ftp.individualMap.get(childrens.get(j)).getId() + ") in the family "
									+ familyRecord.getId() + " are same");
							errorList.add(error);
						}
					}
				}
			}
		}
		return errorList;
	}
}
