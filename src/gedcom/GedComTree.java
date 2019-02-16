package gedcom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class GedComTree
{
	private static void printIndividualList(FamilyTreeParser Ftp) {
		Map<String,Individual> treeMap = new TreeMap<String,Individual>(Ftp.individualMap);

		//For individuals
		System.out.println("Individuals");
		String individualOutputFormat = "|%1$-6s|%2$-20s|%3$-10s|%4$-12s|%5$-7s|%6$-9s|%7$-12s|%8$-10s|%9$-10s|%n";
		System.out.format("+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
		System.out.format("|  ID  |       Name         |  Gender  |  Birthday  |  Age  |  Alive  |    Death   |   Child  |  Spouse  |%n");
		System.out.format("+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
			for(Map.Entry<String, Individual> entry : treeMap.entrySet())
			{
				Individual indi = entry.getValue();
				System.out.format(individualOutputFormat, indi.getId(),indi.getName(),indi.getGender(),indi.getBirthDate(),indi.getAge(),indi.isAlive(),indi.getDeathDate()==null?"NA":indi.getDeathDate(),indi.getFamilyChildId()==null?"NA":"{'"+ indi.getFamilyChildId()+"'}",indi.getFamilySpouseId()==null?"NA":"{'"+indi.getFamilySpouseId()+"'}");
				
			}
			System.out.format("+------+--------------------+----------+------------+-------+---------+------------+----------+----------+%n");
		}
	private static void printFamilyList(FamilyTreeParser Ftp) {

		//For Families
		System.out.println("Families");
		String familyOutputFormat = "|%1$-6s|%2$-16s|%3$-16s|%4$-14s|%5$-23s|%6$-14s|%7$-23s|%8$-23s|%n";
		System.out.format("+------+----------------+----------------+--------------+-----------------------+--------------+-----------------------+-----------------------+%n");
		System.out.format("|  ID  |    Married     |    Divorced    |  Husband Id  |      Husband Name     |    Wife Id   |      Wife Name        |      Children         |%n");
		System.out.format("+------+----------------+----------------+--------------+-----------------------+--------------+-----------------------+-----------------------+%n");
		for(int i=0;i<Ftp.familyList.size();i++)
		{
			Family fam = Ftp.familyList.get(i);
			String wifeName = Ftp.individualMap.get(fam.getWifeId()).getName();
			String husbandName = Ftp.individualMap.get(fam.getHusbandId()).getName();
			System.out.format(familyOutputFormat, fam.getId(),fam.getMarriageDate()==null?"NA":fam.getMarriageDate(),fam.getDivorcedDate()==null?"NA":fam.getDivorcedDate(),fam.getHusbandId(),husbandName,fam.getWifeId(),wifeName,fam.getChildId().isEmpty()?"NA":"{'"+String.join("', '", fam.getChildId())+"'}");
		}
		System.out.format("+------+----------------+----------------+--------------+-----------------------+--------------+-----------------------+-----------------------+%n");
	}
	
	private static void printErrorDetails(FamilyTreeParser Ftp) {
		ArrayList<ErrorData> allErrors = new ArrayList<ErrorData>();
		allErrors.addAll(MultiIndividualFamilyData.US_Marriage_Before_Death(Ftp));
		for(int i=0;i<MultiIndividualFamilyData.errorList.size();i++) {
			ErrorData error = MultiIndividualFamilyData.errorList.get(i);
			System.out.println(error.getErrorType()+":"+error.getRecordType()+":"+error.getUserStroyNumber()+":"+error.getIndividualId()+":"+error.getErrorDetails());
		}
	}
	public static void main(String[] args) throws IOException
	{
		FamilyTreeParser Ftp = new FamilyTreeParser();
		Ftp.setValidTags();
		Ftp.FamilyTreeParserCheck();
		printIndividualList(Ftp);
		printFamilyList(Ftp);
		printErrorDetails(Ftp);
	}
}