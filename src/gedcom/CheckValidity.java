package gedcom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
				System.out.println("ERROR: US07: Age of "+ Name + "(" + ID + ")" +" is more then 150 years old.");
			if(!checkMarriageAge(SpouseId, Age))
				System.out.println("ERROR: US10: "+ Name + "(" + ID + ")" +" got married before 14 years old.");			

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
	//Shreesh Chavan: Sprint1 US01 valid date
	public static boolean checkDatesBeforeCurrentDate(LocalDate p_dateToBeChecked) throws ParseException
	{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date input = new Date();
        String formattedDate= sdf.format(input);
        Date date1 = sdf.parse(formattedDate);
        Date date2 = Date.from(p_dateToBeChecked.atStartOfDay(ZoneId.systemDefault()).toInstant());
        if (date1.compareTo(date2) > 0) {
            return false;
        } else {
            return true;
        }
    }
}
