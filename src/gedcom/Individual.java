package gedcom;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;

public class Individual {
	private String id;
	private String name;
	private String gender;
	private LocalDate birthDate;
	private LocalDate deathDate;
	private String familyChildId;
	private String familySpouseId;
	String[] month= {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};	
	public void setId(String id)
	{
		this.id=id;
	}
	public String getId()
	{
		return this.id;
	}
	public void setName(String name)
	{
		this.name=name;
	}
	public String getName()
	{
		return this.name;
	}
	public void setGender(String gender)
	{
		this.gender=gender;
	}
	public String getGender()
	{
		return this.gender;
	}
	public void setBirthDate(String birthDate)
	{
		String[] birthDateSplitted = birthDate.split("\\s");
		LocalDate bdate = LocalDate.of(Integer.parseInt(birthDateSplitted[2]), Arrays.asList(month).indexOf(birthDateSplitted[1])+1, Integer.parseInt(birthDateSplitted[0]));
		this.birthDate=bdate;
	}
	public LocalDate getBirthDate()
	{
		return this.birthDate;
	}
	public void setDeathDate(String deathDate)
	{
		String[] deathDateSplitted = deathDate.split("\\s");
		LocalDate ddate = LocalDate.of(Integer.parseInt(deathDateSplitted[2]), Arrays.asList(month).indexOf(deathDateSplitted[1])+1, Integer.parseInt(deathDateSplitted[0]));
		this.deathDate=ddate;
	}
	public LocalDate getDeathDate()
	{
		return this.deathDate;
	}
	public void setFamilyChildId(String familyChildId)
	{
		this.familyChildId = familyChildId;
	}
	public String getFamilyChildId()
	{
		return this.familyChildId;
	}
	public void setFamilySpouseId(String familySpouseId)
	{
		this.familySpouseId=familySpouseId;
	}
	public String getFamilySpouseId()
	{
		return this.familySpouseId;
	}
	public int getAge()
	{
		LocalDate today = LocalDate.now();
		return Period.between(this.birthDate, today).getYears();
	}
	public String isAlive()
	{
		return this.deathDate==null?"True":"False";
	}

}
