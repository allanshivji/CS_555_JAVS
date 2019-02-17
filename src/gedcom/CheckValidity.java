package gedcom;

import java.util.Map;
import java.util.TreeMap;

public class CheckValidity {
	public static void check(FamilyTreeParser Ftp) 
	{
		Map<String,Individual> treeMap = new TreeMap<String,Individual>(Ftp.individualMap);
		for(Map.Entry<String, Individual> entry : treeMap.entrySet())
		{
			Individual indi = entry.getValue();
			String ID = indi.getId();
			String Name = indi.getName();
			int Age = indi.getAge();
			String SpouseId = indi.getFamilySpouseId();
			
			if(!checkAge(Age))
				System.out.println("Error US07: Age of "+ Name + "(" + ID + ")" +" is more then 150 years old.");
			if(!checkMarriageAge(SpouseId, Age))
				System.out.println("Error US10: "+ Name + "(" + ID + ")" +" got married before 14 years old.");			

		}
	}
	//Jiayuan Liu: Sprint1 US07 Less then 150 years old
	public static boolean checkAge(int Age)
	{
		if( Age >= 150 )
			return false;
		else
			return true;
	}
	//Jiayuan Liu: Sprint1 US10 Marriage after 14
	public static boolean checkMarriageAge(String SpouseId, int Age)
	{
		if( SpouseId!= null && Age <= 14 )
			return false;
		else
			return true;
	}
}
