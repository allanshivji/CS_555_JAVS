package gedcom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class All_Lists {

	// Allan: Sprint 3 US30 check list of Living Married
	public static ArrayList<Individual> listOfLivingMarried(FamilyTreeParser Ftp) {
		ArrayList<Individual> livingMarriedArray = new ArrayList<Individual>();
		for (Family familyRecord : Ftp.familyList) {

			String husbId = familyRecord.getHusbandId();
			String wifeId = familyRecord.getWifeId();

			if (Ftp.individualMap.get(husbId).isAlive() == "True"
					&& Ftp.individualMap.get(wifeId).isAlive() == "True") {
				livingMarriedArray.add(Ftp.individualMap.get(husbId));
				livingMarriedArray.add(Ftp.individualMap.get(wifeId));
			}
		}
		return livingMarriedArray;
	}

	// Shreesh Chavan: Sprint 2 US29 check list of Deceased
	public static ArrayList<Individual> US_listOfDeceased(FamilyTreeParser ftp) {
		ArrayList<Individual> deceasedArray = new ArrayList<Individual>();
		HashMap<String, Individual> enter = ftp.individualMap;
		for (Entry<String, Individual> entry : enter.entrySet()) {
			if (entry.getValue().isAlive() == "False") {
				deceasedArray.add(entry.getValue());
			}
		}
		return deceasedArray;
	}

	// Shreesh Chavan: Sprint 3 US31 check list of Living Singles
	public static ArrayList<Individual> US_listOflivingSingle(FamilyTreeParser ftp) {
		ArrayList<Individual> livingSingleArray = new ArrayList<Individual>();
		HashMap<String, Individual> enter = ftp.individualMap;
		for (Entry<String, Individual> entry : enter.entrySet()) {
			if (entry.getValue().isAlive() == "True") {
				if (entry.getValue().getAge() >= 30 && checkIfMarried(entry.getValue().getId(), ftp) != false) {
					livingSingleArray.add(entry.getValue());
				}
			}
		}
		return livingSingleArray;
	}

	public static boolean checkIfMarried(String id, FamilyTreeParser ftp) {
		ArrayList<Family> family = ftp.familyList;
		for (Family famrec : family) {
			if (id == famrec.getHusbandId() || id == famrec.getWifeId()) {
				return true;
			}
		}
		return false;
	}

	// Shreesh Chavan: Sprint 3 US33 check list of Orphans
	public static ArrayList<Individual> US_listofOrphans(FamilyTreeParser ftp) {
		ArrayList<Individual> orphansArray = new ArrayList<Individual>();
		HashMap<String, Individual> enter = ftp.individualMap;
		ArrayList<Family> family = ftp.familyList;
		for (Family famrec : family) {
			if (famrec.getChildId().size() > 0 && enter.get(famrec.getHusbandId()).isAlive() == "False"
					&& enter.get(famrec.getWifeId()).isAlive() == "False") {
				for (String childid : famrec.getChildId()) {
					if (enter.get(childid).getAge() < 18) {
						orphansArray.add(enter.get(childid));
					}
				}
			}
		}
		return orphansArray;
	}
}
