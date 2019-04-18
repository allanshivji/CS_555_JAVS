package gedcom;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;

public class US_All_Lists {

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

	// Allan: Sprint 4 US39 List of Upcoming Anniversaries
	public static ArrayList<Individual> listOfUpcoming_Anniversaries(FamilyTreeParser Ftp) {

		ArrayList<Individual> upcommingAnniversaries = new ArrayList<Individual>();
		LocalDate today = LocalDate.now();

		for (Family familyRecord : Ftp.familyList) {

			String husbId = familyRecord.getHusbandId();
			String wifeId = familyRecord.getWifeId();
			LocalDate marriageDate = familyRecord.getMarriageDate();

			if (Ftp.individualMap.get(husbId).isAlive() == "True"
					&& Ftp.individualMap.get(wifeId).isAlive() == "True") {

			// Checked the LocalDate.of() function from stack overflow 
			//https://stackoverflow.com/questions/55738201/calculate-days-between-two-dates-ignoring-year
				LocalDate thisYearsAnniversary = LocalDate.of(today.getYear(), marriageDate.getMonth(),
						marriageDate.getDayOfMonth());

				long aniDate = ChronoUnit.DAYS.between(today, thisYearsAnniversary);
				if (aniDate >= 1 && aniDate <= 30) {
					upcommingAnniversaries.add(Ftp.individualMap.get(husbId));
					upcommingAnniversaries.add(Ftp.individualMap.get(wifeId));
				}

			}
		}
		return upcommingAnniversaries;
	}

	// Allan: Sprint 4 
	public static ArrayList<Individual> listOfUniqueFirstNames(FamilyTreeParser Ftp){
		ArrayList<Individual> upcommingAnniversaries = new ArrayList<Individual>();
		return upcommingAnniversaries;
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
				if (entry.getValue().getAge() >= 30 && !checkIfMarried(entry.getValue().getId(), ftp) != false) {
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

	// Vidya Maiya: Sprint 04 US_35_List recent births
	public static ArrayList<Individual> US35_findListOfRecentBirths(FamilyTreeParser Ftp) {
		ArrayList<Individual> recentBirths = new ArrayList<Individual>();
		HashMap<String, Individual> individualMap = Ftp.individualMap;
		LocalDate today = LocalDate.now();

		for (Map.Entry<String, Individual> individual : individualMap.entrySet()) {
			Individual individualData = individual.getValue();
			LocalDate birthdate = individualData.getBirthDate();
			if (birthdate != null && (birthdate.isBefore(today) || birthdate.equals(today))) {
				long daysBetween = ChronoUnit.DAYS.between(birthdate, today);
				if (daysBetween >= 0 && daysBetween <= 30) {
					recentBirths.add(individualData);
				}
			}
		}
		return recentBirths;
	}

	// Vidya Maiya: Sprint04 US_36_List_recent_deaths
	public static ArrayList<Individual> US36_findListOfRecentDeaths(FamilyTreeParser Ftp) {
		ArrayList<Individual> recentDeaths = new ArrayList<Individual>();
		HashMap<String, Individual> individualMap = Ftp.individualMap;
		LocalDate today = LocalDate.now();

		for (Map.Entry<String, Individual> individual : individualMap.entrySet()) {
			Individual individualData = individual.getValue();
			LocalDate deathDate = individualData.getDeathDate();
			if (deathDate != null && (deathDate.isBefore(today) || deathDate.equals(today))) {
				long daysBetween = ChronoUnit.DAYS.between(deathDate, today);
				if (daysBetween >= 0 && daysBetween <= 30) {
					recentDeaths.add(individualData);
				}
			}
		}
		return recentDeaths;
	}

}
