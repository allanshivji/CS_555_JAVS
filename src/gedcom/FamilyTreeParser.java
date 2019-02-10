package gedcom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FamilyTreeParser {
	HashMap<String,Integer> validTags = new HashMap<String,Integer>();
	HashMap<String,Individual> individualMap;
	ArrayList<Family> familyList;
	
	public void setValidTags() throws IOException {
		File file = new File("resources//validTags.txt");
		BufferedReader br = null;
		try {
				br = new BufferedReader(new FileReader(file));
				br.readLine();
				String line;
				while((line=br.readLine())!=null){
					validTags.put(line.split(",")[1], Integer.parseInt(line.split(",")[0]));
			}
			br.close();
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public boolean areTagLevelValid(String tag, int level) {
		if(validTags.containsKey(tag))
		{
			if(validTags.get(tag) == level)
			{
				return true;
			}
		}
		return false;
	}
	public void FamilyTreeParserCheck() {
		File file = new File("resources\\GedComTest.ged");
		//File file = new File("C:\\Stevens\\Courses\\CS-555-Agile Methods for SW Eng\\Project\\NewGedCom\\proj02test.ged");
		BufferedReader br = null;
		String line;
		individualMap = new HashMap<String,Individual>();
		familyList = new ArrayList<Family>();


		try {
				br = new BufferedReader(new FileReader(file));
				int level;
				String tag=null;
				String currentIndividualId=null;
				String currentFamilyId=null;
				Individual individual=null;
				Family family=null;
				boolean birth=false, death=false;
				boolean married=false, divorced=false;
				while((line=br.readLine())!=null)
				{
					String arguements="";
					String[] splittedData = line.split("\\s+",3);
					level=Integer.parseInt(splittedData[0]);
					if(splittedData.length==2) {
						tag=splittedData[1];
					}
					else if(splittedData.length>2 &&(splittedData[2].equals("INDI") || splittedData[2].equals("FAM"))){
						tag=splittedData[2];
						arguements=splittedData[1];
					}
					else if(splittedData.length>2 &&(!splittedData[1].equals("INDI") || !splittedData[1].equals("FAM"))) {
						tag=splittedData[1];
						arguements=splittedData[2];
					}
					if(areTagLevelValid(tag,level)) {
						if(tag.equals("INDI")) {
							individual = new Individual();
							currentIndividualId=arguements;
							individual.setId(currentIndividualId);
							individualMap.put(currentIndividualId,individual);
						}
						if(tag.equals("NAME")) {
							individualMap.get(currentIndividualId).setName(arguements);
						}
						if(tag.equals("SEX")) {
							individualMap.get(currentIndividualId).setGender(arguements);
						}
						if(tag.equals("BIRT")) {
							birth=true;
						}
						if(tag.equals("DEAT")) {
							death=true;
						}
						if(tag.equals("DATE") && (birth == true || death==true))
						{
							if(birth==true) {
								individualMap.get(currentIndividualId).setBirthDate(arguements);
								birth=false;
							}
							if(death==true) {
								individualMap.get(currentIndividualId).setDeathDate(arguements);
								death=false;
							}
						}
						if(tag.equals("FAMC")) {
							individualMap.get(currentIndividualId).setFamilyChildId(arguements);
						}
						if(tag.equals("FAMS")) {
							individualMap.get(currentIndividualId).setFamilySpouseId(arguements);
						}
						if(tag.equals("FAM")) {
							family = new Family();
							currentFamilyId=arguements;
							family.setId(currentFamilyId);
							familyList.add(family);
						}
						if(tag.equals("MARR")) {
							married=true;
						}
						if(tag.equals("DIV")) {
							divorced=true;
						}
						if(tag.equals("DATE") && (married==true || divorced == true)) {
							if(married==true) {
								family.setMarriageDate(arguements);
								married=false;
							}
							if(divorced==true) {
								family.setDivorceDate(arguements);
								divorced=false;
							}
						}
						if(tag.equals("WIFE")) {
							family.setWifeId(arguements);
						}
						if(tag.equals("HUSB")) {
							family.setHusbandId(arguements);
						}
						if(tag.equals("CHIL")) {
							family.setChildId(arguements);
						}
					}
				}

			} catch(Exception e) {
				System.out.println(e);
			}
	}
	
	}
	

