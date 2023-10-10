package common;

import java.util.ArrayList;
import java.util.Scanner;
import model.Worker;

public class Library {
   Scanner sc = new Scanner(System.in);
   
    public int inputIntLimit(int min, int max) {
        while (true) {
            try {
                int result = Integer.parseInt(sc.nextLine().trim());
                if (result < min || result > max) {
                    throw new NumberFormatException();
                }
                return result;
            } catch (NumberFormatException e) {
                System.err.println("Please input number in rage [" + min + ", " + max + "]");
                System.out.print("Enter again: ");
            }
        }
    }
    
    public String inputString() {
        while (true) {
            String result = sc.nextLine().trim();
            if (result.isEmpty()) {
                System.err.println("Not empty!");
                System.out.print("Enter again: ");
            } else {
                return result;
            }
        }
    }
    
    public int inputInt() {
        while (true) {
            try {
                int result = Integer.parseInt(sc.nextLine());
                if (result > 0) return result;
                else {
                    System.err.println("Must be input number > 0!");
                    System.out.print("Enter again: ");
                }
            } catch (NumberFormatException e) {
                System.err.println("Must be input number!");
                System.out.print("Enter again: ");
            }
        }
    }
    
     public boolean checkIdExist(ArrayList<Worker> workers, String id) {
        //check from first to last list id worker exist or not
        for (Worker worker : workers) {
            if (id.equalsIgnoreCase(worker.getId())) {
                return true;
            }
        }
        return false;
    }

    //check salary must be greater than 0
    public int checkInputSalary() {
        //loop until user input correct
        while (true) {
            try {
                int result = Integer.parseInt(sc.nextLine().trim());
                if (result < 0) {
                    throw new NumberFormatException();

                }
                return result;
            } catch (NumberFormatException e) {
                System.err.println("Salary must be greater than 0");
                System.out.print("Enter again: ");
            }
        }
    }

    //check worker duplicate
    public boolean checkWorkerExist(ArrayList<Worker> workers, String id,
            String name, int age, int salary, String workLocation) {
        //check from first to last list worker  worker exist or not
        for (Worker worker : workers) {
            if (id.equalsIgnoreCase(worker.getId())
                    && name.equalsIgnoreCase(worker.getName())
                    && age == worker.getAge()
                    && salary == worker.getSalary()) {
                return false;
            }
        }
        return true;
    }

}