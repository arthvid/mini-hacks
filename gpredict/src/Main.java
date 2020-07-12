import java.util.*;
import java.util.Scanner;

/** Driver class for whatever the f*ck this is meant to do.
 * @author Arth Vidyarthi.
 */
public class Main {
    private static String[] store = new String[28];
    private static HashMap<String, Double> gradePoint = new HashMap<>();
    private static HashMap<String, Double> schema = new HashMap<>();

    public static void main(String... args) {
        initGradepoint();
        int track = 1;
        while (track < args.length) {
            store[track - 1] = args[track];
            track ++;
        }


        if (args[0].equals("calculate")) {
            for (int i = 0; i < args.length - 1; i++) {
                if (i % 2 == 0) {
                    int next = i + 1;
                    String key = store[i];
                    double value = Double.parseDouble(store[next]);
                    schema.put(key, value);
                }
            }
            double GPA = calculateGPA();
            System.out.println("Based on the following grades:");
            System.out.println(schema.entrySet());
            System.out.println("Your GPA is: ");
            System.out.print(GPA);
        }

        if (args[0].equals("predict")) {
            for (int i = 0; i < args.length - 4; i++) {
                if (i % 2 == 0) {
                    int next = i + 1;
                    String key = store[i];
                    double value = Double.parseDouble(store[next]);
                    schema.put(key, value);
                }
            }
            String temp = args[args.length - 3];
            double desiredGPA = Double.parseDouble(temp);
            String temp2 = args[args.length - 2];
            int semesters = Integer.parseInt(temp2);
            double currentGPA = calculateGPA();
            String prefGrade = args[args.length - 1];
            System.out.println("Your current GPA is " + currentGPA);
            predict(desiredGPA, semesters, _totalUnits, _totalGP, prefGrade);
        }


    }
    private static void predict(double desGPA, int numSem, int currUnits,
                                double currGradePoints, String preferredGrade) {
        double gp = gradePoint.get(preferredGrade); //Gives grade points for preferred grade.
        double numerator = currGradePoints - (desGPA * currUnits);
        double denominator = desGPA - gp;
        if ((denominator == 0) || Math.abs(numerator/denominator) > _unitCap*numSem){
            System.out.println("Sorry, with the current parameters, you can't obtain your desired GPA of " + desGPA + ".");
            double max = (currGradePoints + gp*_unitCap*numSem) / (currUnits + _unitCap*numSem);
            System.out.println("The maximum possible GPA you can achieve based on your parameters is " + max);
            System.exit(0);
        }
        double reqUnits = Math.abs(numerator / denominator);
        System.out.println("To achieve a desired GPA of " + desGPA);
        System.out.println("Over a total of " + numSem + " semester(s)");
        System.out.println("Assuming your goal is to get all " + preferredGrade + "'s");
        System.out.println("You must get: ");
        System.out.println(reqUnits + " units worth of " + preferredGrade + "'s");
        System.out.println("At an average of " + reqUnits/numSem + " units of " + preferredGrade + "'s per semester.");
    }



    private static void initGradepoint() {
        gradePoint.put("A+", 4.0);
        gradePoint.put("A", 4.0);
        gradePoint.put("A-", 3.7);
        gradePoint.put("B+", 3.3);
        gradePoint.put("B", 3.0);
        gradePoint.put("B-", 2.7);
        gradePoint.put("C+", 2.3);
        gradePoint.put("C", 2.0);
        gradePoint.put("C-", 1.7);
        gradePoint.put("D+", 1.3);
        gradePoint.put("D", 1.0);
        gradePoint.put("D-", 0.7);
        gradePoint.put("F", 0.0);
    }

    private static double calculateGPA() {
        int totalUnits = 0;
        double totalGP = 0;
        for (String elem : schema.keySet()) {
            double units = schema.get(elem);
            double gp = gradePoint.get(elem);
            double gpa = units * gp;
            totalUnits += units;
            totalGP += gpa;
        }
        set_totalGP(totalGP);
        set_totalUnits(totalUnits);
        return totalGP / totalUnits;
    }


    public static void set_totalUnits(int _totalUnits) {
        Main._totalUnits = _totalUnits;
    }

    public static void set_totalGP(double _totalGP) {
        Main._totalGP = _totalGP;
    }
    /** Integer representing total units. */
    private static int _totalUnits = 0;
    /** Double representing total grade points accumulated. */
    private static double _totalGP = 0;
    /** Double representing the unit cap. */
    private static final double _unitCap = 20.5;

//    private static void reference() {
//        System.out.println("If you want to calculate your current GPA, " +
//                "enter the phrase 'calculate', followed by the grade in capital, followed by a space, followed by the number of" +
//                "units to one decimal space. For example-->  calculate A 4 A- 12 B+ 16");
//        System.out.println("If you want to calculate how many units of a particular grade you " +
//                "need to get on average over a certain number of semesters to get " +
//                "a particular GPA, enter your commands in the following manner: ");
//        System.out.println("predict A 4 A- 12 B+ 16 *desiredGPA* *numberOfSemesters* *PreferredGrade*");
//    }

}

