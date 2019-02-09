package project_03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.*;

public class Project_03 {

    public static void main(String[] args) throws FileNotFoundException {

        Map<String, String> dictionary = new HashMap<String, String>();
//        dictionary.put("dog", "type of animal");
//        dictionary.put("dog", "type");
////
//        System.out.println(dictionary.size());
        String birthYear = "";
        String birthMonth = "";
        String birthDate = "";
        int set = 0;
        try {

            Scanner sc = new Scanner(new File("/Users/allan/Desktop/Project_03/src/project_03/test.ged"));
            String level0tags[] = {"HEAD", "TRLR", "NOTE"};
            String level1tags[] = {"NAME", "SEX", "BIRT", "DEAT", "FAMC", "FAMS", "MARR", "HUSB", "WIFE", "CHIL", "DIV"};
            String level2tags[] = {"DATE"};

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
//                System.out.println(line);
                if (line.isEmpty()) {
                    continue;
                }
                String[] words;
                words = line.split("\\s");
                int count = 0;

                if (words[0].equals("0")) {
                    for (int i = 0; i < level0tags.length; i++) {

                        if (words[1].equals(level0tags[i])) {
                            break;
                        } else if (words[2].equals("INDI")) {
                            dictionary.put("ID", words[1]);

                            line = sc.nextLine();
                            words = line.split("\\s");
                            for (int j = 0; j < level1tags.length; j++) {
                                if (words[0].equals("1")) {
                                    if (words[1].equals("NAME")) {
                                        dictionary.put("FNAME", words[2]);
                                        dictionary.put("LNAME", words[3]);
                                        line = sc.nextLine();
//                                        System.out.println(line);
                                        words = line.split("\\s");
                                    }
                                    if (words[1].equals("SEX")) {
                                        dictionary.put("SEX", words[2]);
                                        line = sc.nextLine();
//                                        System.out.println(line);
                                        words = line.split("\\s");
                                    }
                                    if (words[1].equals("BIRT")) {
                                        line = sc.nextLine();
//                                        System.out.println(line);
                                        words = line.split("\\s");
                                        if (words[0].equals("2")) {
                                            if (words[1].equals("DATE")) {

                                                //HERE WE HAVE TO MAKE CHANGES
                                                String m = "";
                                                m = giveMonthInt(words[3]);
                                                dictionary.put("BIRTHDATE", words[4] + "-" + m + "-" + words[2]);
                                                //HERE
                                                birthYear = words[4];
                                                birthMonth = m;
                                                birthDate = words[2];
                                                
                                                int year = Integer.parseInt(words[4]);
                                                int month = Integer.parseInt(m);
                                                int date = Integer.parseInt(words[2]);
                                                
                                                //getting age from birthdate
                                                LocalDate bdate = LocalDate.of(year, month, date);
                                                LocalDate now = LocalDate.now();
                                                Period diffage = Period.between(bdate, now);
                                                String age = Integer.toString(diffage.getYears());

                                                dictionary.put("AGE", age);
                                                dictionary.put("ALIVE", "True");
                                                dictionary.put("DEATH", "NA");

                                                line = sc.nextLine();
//                                                System.out.println(line);
                                                words = line.split("\\s");
                                            }
                                        }

                                    }
                                    if (words[1].equals("DEAT")) {
                                        line = sc.nextLine();
//                                        System.out.println(line);
                                        words = line.split("\\s");
                                        if (words[0].equals("2")) {
                                            if (words[1].equals("DATE")) {
                                                //HERE
                                                String m = "";
                                                m = giveMonthInt(words[3]);
                                                

                                                // Getting birthdate
//                                                String bdate = dictionary.get("BIRTHDATE");
                                                
                                                
                                                int byear = Integer.parseInt(birthYear);
                                                int bmonth = Integer.parseInt(birthMonth);
                                                int birdate = Integer.parseInt(birthDate);
                                                
                                                LocalDate bdate = LocalDate.of(byear, bmonth, birdate);
                                                int year = Integer.parseInt(words[4]);
                                                int month = Integer.parseInt(m);
                                                int date = Integer.parseInt(words[2]);
                                                
                                                //getting age from birthdate
                                                LocalDate ddate = LocalDate.of(year, month, date);
                                                
                                                Period diffage = Period.between(bdate, ddate);
                                                
                                                String age = Integer.toString(diffage.getYears());

                                                dictionary.put("AGE", age);
                                                dictionary.put("ALIVE", "False");
                                                
                                                dictionary.put("DEATH", words[4] + "-" + month + "-" + words[2]);

                                                line = sc.nextLine();
//                                                System.out.println(line);
                                                words = line.split("\\s");
                                            }
                                        }

                                    }

                                }

                            }
                        }

                    }
                }
                
//                for(String key: dictionary.values()){
//                    System.out.print(key + "|| ");
//                }
                System.out.print(dictionary.get("ID")+ " | ");
                System.out.print(dictionary.get("FNAME") + " " + dictionary.get("LNAME") + " | ");
                System.out.print(dictionary.get("SEX") + " | ");
                System.out.print(dictionary.get("BIRTHDATE") + " | ");
                System.out.print(dictionary.get("AGE") + " | ");
                System.out.print(dictionary.get("ALIVE") + " | ");
                System.out.println(dictionary.get("DEATH") + " | ");
                
//                
            }
            

        } catch (Error e) {
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
