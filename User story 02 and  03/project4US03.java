
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.*;


public class project4US03 {
    static LocalDate birthday = null;
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new File("/Users/allan/Desktop/Homework04/src/homework04/testing.ged"));
            String level0tags[] = {"HEAD", "TRLR", "NOTE"};
            String level1tags[] = {"NAME", "SEX", "BIRT", "DEAT", "FAMC", "FAMS", "MARR", "HUSB", "WIFE", "CHIL", "DIV"};
            String level2tags[] = {"DATE"};

            List<String> list = Arrays.asList(level0tags);
            Map<String, String> dictionary = new HashMap<String, String>();

            String birthYear = "";
            String birthMonth = "";
            String birthDate = "";
            int set = 0;

            while (sc.hasNextLine()) {

                String line = sc.nextLine();
                if (line.isEmpty()) {
                    continue;
                }

                String[] words;
                words = line.split("\\s");


                if (!list.contains(words[1])) {

                    if (words.length > 2) {

                        if (words[0].equals("0") && words[2].equals("INDI")) {

                            line = sc.nextLine();
                            words = line.split("\\s");
                            System.out.println("");
                            System.out.println(words[2]+" "+words[3]);
                            while (words[0].equals("1")) {
                                if (words[1].equals("BIRT")) {

                                    line = sc.nextLine();
                                    words = line.split("\\s");

                                    if (words[0].equals("2")) {
                                        if (words[1].equals("DATE")) {
                                            String m = "";
                                            m = giveMonthInt(words[3]);


                                            
                                            

                                            birthYear = words[4];
                                            birthMonth = m;
                                            birthDate = words[2];

                                            int year = Integer.parseInt(words[4]);
                                            int month = Integer.parseInt(m);
                                            int date = Integer.parseInt(words[2]);

                                            LocalDate bdate = LocalDate.of(year, month, date);
                                            System.out.println("Birth Date: "+bdate);
                                            birthday = bdate;
                                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                                            line = sc.nextLine();
                                            words = line.split("\\s");
                                            if(words[0].equals("1") && words[1].equals("DEAT")) {
                                            	line = sc.nextLine();
                                                words = line.split("\\s");
                                                
                                                if(words[0].equals("2")) {


                                                    Map<String, String> dictionary2 = new HashMap<String, String>();
                                                    if (words[1].equals("DATE")) {

                                                    	
                                                        m = "";
                                                        m = giveMonthInt(words[3]);
                                                        

                                                        String dethYear = words[4];
                                                        String deathMonth = m;
                                                        String deathDate = words[2];

                                                        int dyear = Integer.parseInt(words[4]);
                                                        int dmonth = Integer.parseInt(m);
                                                        int dddate = Integer.parseInt(words[2]);

                                                        LocalDate ddate = LocalDate.of(dyear, dmonth, dddate);
                                                        System.out.println("Death Date: "+ddate);
                                                        if(ddate.compareTo(birthday) < 0 || ddate.compareTo(birthday) == 0){
                                                            System.out.println("ERROR: Death date is Incorrect");
                                                        }else {
                                                            System.out.println("SUCCESS: Death date is Correct");
                                                        }
                                                        System.out.println("");

                                                    }

                                                
                                                }
                                            }
                                        }

                                    }                                    
                                    break;
                                }

                                line = sc.nextLine();
                                words = line.split("\\s");
                            }

                        }
                    }

                }

            }

        } catch (FileNotFoundException e) {
            System.err.println("ERROR 404: FILE NOT FOUND");
        }
    }

    public static String giveMonthInt(String month) {
        switch (month) {
            case "JAN":
                return "01";
            case "FEB":
                return "02";
            case "MAR":
                return "03";
            case "APR":
                return "04";
            case "MAY":
                return "05";
            case "JUN":
                return "06";
            case "JUL":
                return "07";
            case "AUG":
                return "08";
            case "SEP":
                return "09";
            case "OCT":
                return "10";
            case "NOV":
                return "11";
            case "DEC":
                return "12";
            default:
                return "00";
        }

    }   
    
}
