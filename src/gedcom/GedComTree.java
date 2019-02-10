package gedcom;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class GedComTree
{
	public static void printIndividualList(FamilyTreeParser Ftp) {
		Map<String,Individual> treeMap = new TreeMap<String,Individual>(Ftp.individualMap);
		System.out.println("------------------INDIVIDUAL-------------------------------");
			for(Map.Entry<String, Individual> entry : treeMap.entrySet())
			{
				Individual indi = entry.getValue();
				System.out.println(indi.getId()+"---"+indi.getName()+"---"+indi.getGender()+"---"+indi.getBirthDate()+"---"+indi.getAge()+"---"+indi.isAlive()+"---"+indi.getDeathDate()+"---"+indi.getFamilyChildId()+"---"+indi.getFamilySpouseId());
			}
		}
	public static void printFamilyList(FamilyTreeParser Ftp) {
		System.out.println("-----------------------------FAMILY-------------------");
		
		for(int i=0;i<Ftp.familyList.size();i++)
		{
			Family fam = Ftp.familyList.get(i);
			String wifeName = Ftp.individualMap.get(fam.getWifeId()).getName();
			String husbandName = Ftp.individualMap.get(fam.getHusbandId()).getName();
			System.out.println(fam.getId()+"---"+fam.getMarriageDate()+"---"+fam.getDivorcedDate()+"---"+fam.getHusbandId()+"---"+husbandName+"---"+fam.getWifeId()+"---"+wifeName+"---"+fam.getChildId());
		}
	}
	public static void main(String[] args) throws IOException
	{
		FamilyTreeParser Ftp = new FamilyTreeParser();
		Ftp.setValidTags();
		Ftp.FamilyTreeParserCheck();
		printIndividualList(Ftp);
		printFamilyList(Ftp);
	}
}