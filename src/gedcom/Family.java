package gedcom;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class Family {
	private String id;
	private String husbandId;
	private String wifeId;
	private ArrayList<String> childId;
	private LocalDate marriageDate;
	private LocalDate divroceDate;
	String[] month= {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};	
	public Family()
	{
		this.childId=new ArrayList<String>();
	}
	public void setId(String id)
	{
		this.id=id;
	}
	public String getId()
	{
		return this.id;
	}
	public void setMarriageDate(String marriageDate)
	{
		String[] marriageDateSplitted = marriageDate.split("\\s");
		LocalDate mdate = LocalDate.of(Integer.parseInt(marriageDateSplitted[2]), Arrays.asList(month).indexOf(marriageDateSplitted[1])+1, Integer.parseInt(marriageDateSplitted[0]));
		this.marriageDate=mdate;
	}
	public LocalDate getMarriageDate()
	{
		return this.marriageDate;
	}
	public void setDivorceDate(String divorcedDate)
	{
		String[] divorcedDateSplitted = divorcedDate.split("\\s");
		LocalDate ddate = LocalDate.of(Integer.parseInt(divorcedDateSplitted[2]), Arrays.asList(month).indexOf(divorcedDateSplitted[1])+1, Integer.parseInt(divorcedDateSplitted[0]));
		this.divroceDate=ddate;
	}
	public LocalDate getDivorcedDate()
	{
		return this.divroceDate;
	}
	public void setHusbandId(String husbandId)
	{
		this.husbandId=husbandId;
	}
	public String getHusbandId() {
		return this.husbandId;
	}
	public void setWifeId(String wifeId)
	{
		this.wifeId=wifeId;
	}
	public String getWifeId() {
		return this.wifeId;
	}
	public void setChildId(String childId) {
		this.childId.add(childId);
	}
	public ArrayList<String> getChildId() {
		return this.childId;
	}

}
