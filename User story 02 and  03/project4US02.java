import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.time.LocalDate;

public class project4US02 {

    static LocalDate birthday = null;

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new File("/Users/allan/Desktop/Homework04/src/homework04/testing2.ged"));
            String level0tags[] = { "HEAD", "TRLR", "NOTE" };
            String level1tags[] = { "NAME", "SEX", "BIRT", "DEAT", "FAMC", "FAMS", "MARR", "HUSB", "WIFE", "CHIL",
                    "DIV" };
            String level2tags[] = { "DATE" };

            List<String> list = Arrays.asList(level0tags);
            Map<String, LocalDate> dictionaryIndi = new HashMap<String, LocalDate>();
            LocalDate demodate = LocalDate.of(2000, 12, 12);

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
                            // System.out.println(words[1]);
                            dictionaryIndi.put(words[1], demodate);
                            String w = words[1];

                            line = sc.nextLine();
                            words = line.split("\\s");
                            System.out.println("");

                            while (words[0].equals("1")) {
                                if (words[1].equals("BIRT")) {
                                    line = sc.nextLine();
                                    words = line.split("\\s");


                                    if (words[0].equals("2")) {
                                        if (words[1].equals("DATE")) {
                                            String m = "";
                                            m = giveMonthInt(words[3]);

                                            int year = Integer.parseInt(words[4]);
                                            int month = Integer.parseInt(m);
                                            int date = Integer.parseInt(words[2]);

                                            LocalDate bdate = LocalDate.of(year, month, date);

                                            dictionaryIndi.put(w, bdate);
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


            for (Map.Entry<String, LocalDate> e : dictionaryIndi.entrySet()) {



                Scanner sc2 = new Scanner(new File("/Users/allan/Desktop/Homework04/src/homework04/testing2.ged"));

                while (sc2.hasNextLine()) {

                    String line = sc2.nextLine();
                    if (line.isEmpty()) {
                        continue;
                    }
                    String[] words;
                    words = line.split("\\s");

                    if (!list.contains(words[1])) {

                        if (words.length > 2) {

                            if (words[0].equals("1") && words[2].equals(e.getKey())
                                    && (words[1].equals("HUSB") || words[1].equals("WIFE"))) {


                                while (!words[1].equals("MARR")) {
                                    line = sc2.nextLine();
                                    if (line.isEmpty()) {
                                        break;
                                    }

                                    words = line.split("\\s");
                                }

                                line = sc2.nextLine();
                                words = line.split("\\s");

                                if (words[0].equals("2")) {
                                    if (words[1].equals("DATE")) {
                                        String m = "";
                                        m = giveMonthInt(words[3]);

                                        int myear = Integer.parseInt(words[4]);
                                        int mmonth = Integer.parseInt(m);
                                        int mdate = Integer.parseInt(words[2]);

                                        LocalDate marrdate = LocalDate.of(myear, mmonth, mdate);

                                        if ((marrdate.compareTo(e.getValue()) < 0)
                                                || (marrdate.compareTo(e.getValue()) == 0)) {
                                            System.out
                                                    .println("ERROR: Marriage date of " + e.getKey() + " is Incorrect");
                                        } else {
                                            System.out
                                                    .println("SUCCESS: Marriage date of " + e.getKey() + " is Correct");
                                        }

                                    }
                                }

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
