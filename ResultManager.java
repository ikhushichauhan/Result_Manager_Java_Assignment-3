import java.util.InputMismatchException;
import java.util.Scanner;

// Custom Exception
class InvalidMarksException extends Exception {
    public InvalidMarksException(String msg) {
        super(msg);
    }
}

// Student Class
class Student {
    int rollNumber;
    String studentName;
    int[] marks = new int[3];

    Student(int rollNumber, String studentName, int[] marks) {
        this.rollNumber = rollNumber;
        this.studentName = studentName;
        this.marks = marks;
    }

    void validateMarks() throws InvalidMarksException {
        for (int i = 0; i < 3; i++) {
            if (marks[i] < 0 || marks[i] > 100) {
                throw new InvalidMarksException("Invalid marks for subject " + (i+1) + ": " + marks[i]);
            }
        }
    }

    double calculateAverage() {
        int sum = 0;
        for (int m : marks) sum += m;
        return sum / 3.0;
    }

    void displayResult() {
        System.out.println("Roll Number: " + rollNumber);
        System.out.println("Student Name: " + studentName);

        System.out.print("Marks: ");
        for (int m : marks) System.out.print(m + " ");
        System.out.println();

        double avg = calculateAverage();
        System.out.println("Average: " + avg);

        if (avg >= 40)
            System.out.println("Result: Pass");
        else
            System.out.println("Result: Fail");
    }
}

// MAIN CLASS
public class ResultManager {

    Student[] students = new Student[50];
    int count = 0;
    Scanner sc = new Scanner(System.in);

    void addStudent() {
        try {
            System.out.print("Enter Roll Number: ");
            int roll = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Student Name: ");
            String name = sc.nextLine();

            int[] m = new int[3];
            for (int i = 0; i < 3; i++) {
                System.out.print("Enter marks for subject " + (i + 1) + ": ");
                m[i] = sc.nextInt();
            }

            Student s = new Student(roll, name, m);
            s.validateMarks();

            students[count++] = s;

            System.out.println("Student added successfully.");

        } catch (InvalidMarksException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Input Error: Please enter valid numeric value.");
            sc.nextLine();
        }
    }

    void showStudentDetails() {
        try {
            System.out.print("Enter Roll Number to search: ");
            int roll = sc.nextInt();

            boolean found = false;
            for (int i = 0; i < count; i++) {
                if (students[i].rollNumber == roll) {
                    students[i].displayResult();
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Student not found!");
            }

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }

    void mainMenu() {
        try {
            while (true) {
                System.out.println("\n===== Student Result Management System =====");
                System.out.println("1. Add Student");
                System.out.println("2. Show Student Details");
                System.out.println("3. Exit");

                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> showStudentDetails();
                    case 3 -> {
                        System.out.println("Exiting program. Thank you!");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            }
        } finally {
            sc.close();
        }
    }

    public static void main(String[] args) {
        ResultManager rm = new ResultManager();
        rm.mainMenu();
    }
}
