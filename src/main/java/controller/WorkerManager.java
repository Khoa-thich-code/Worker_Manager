package controller;

import common.Library;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import model.Worker;
import model.History;

import view.Menu;

public class WorkerManager extends Menu<String>{
    static String[] mc = {"Add a Worker", "Increase salary for worker", "Decrease for worker", "Show adjusted salary worker information", "Exit"};
    
    private Library library;
    
    ArrayList<Worker> workers = new ArrayList<>();
    ArrayList<History> historys = new ArrayList<>();
    
    public WorkerManager() {
        super("===================== USER MANAGEMENT SYSTEM =====================", mc);
        library = new Library();
    }

    @Override
    public void execute(int n) {
        switch (n) {
            case 1:
                addWorker(workers);
                break;
            case 2:
                changeSalary(workers, historys, 1);
                break;
            case 3:
                changeSalary(workers, historys, 2);
                break;
            case 4:
                printListHistory(historys);
                break;
            case 5:
                System.out.println("Exit the program successfully!");
                System.exit(0);
                break;
        }
    }   
    
     public void addWorker(ArrayList<Worker> workers) {
        System.out.print("Enter code: ");
        String id = library.inputString();
//        if (!Validate.checkIdExist(lw, id)) {
//            System.err.println("Code(id) must be existed in DB.");
//            return;
//        }
        System.out.print("Enter name: ");
        String name = library.inputString();
        System.out.print("Enter age: ");
        int age = library.inputIntLimit(18, 50);
        System.out.print("Enter salary: ");
        int salary = library.checkInputSalary();
        System.out.print("Enter work location: ");
        String workLocation = library.inputString();
        if (!library.checkWorkerExist(workers, id, name, age, salary, workLocation)) {
            System.err.println("Duplicate.");
        } else {
            workers.add(new Worker(id, name, age, salary, workLocation));
            System.err.println("Add success.");
        }
    }

    //allow user increase salary for user
    public void changeSalary(ArrayList<Worker> workers, ArrayList<History> historys, int status) {
        if (workers.isEmpty()) {
            System.err.println("List empty.");
            return;
        }
        System.out.print("Enter code: ");
        String id = library.inputString();
        Worker worker = getWorkerByCode(workers, id);
        if (worker == null) {
            System.err.println("Not exist worker.");
        } else {
            int salaryCurrent = worker.getSalary();
            int salaryUpdate;
            //check user want to update salary
            if (status == 1) {
                System.out.print("Enter salary: ");
                //loop until user input salary update > salary current
                while (true) {     
                    salaryUpdate = library.checkInputSalary();
                    //check user input salary update > salary current
                    if (salaryUpdate <= salaryCurrent) {
                        System.err.println("Must be greater than current salary.");
                        System.out.print("Enter again: ");
                    } else {
                        break;
                    }
                }
                historys.add(new History("UP", getCurrentDate(), worker.getId(),
                        worker.getName(), worker.getAge(), salaryUpdate,
                        worker.getWorkLocation()));
            } else {
                System.out.print("Enter salary: ");
                //loop until user input salary update < salary current
                while (true) {
                    salaryUpdate = library.checkInputSalary();
                    //check user input salary update < salary current
                    if (salaryUpdate >= salaryCurrent) {
                        System.err.println("Must be smaller than current salary.");
                        System.out.print("Enter again: ");
                    } else {
                        break;
                    }
                }
                historys.add(new History("DOWN", getCurrentDate(), worker.getId(),
                        worker.getName(), worker.getAge(), salaryUpdate,
                        worker.getWorkLocation()));
            }
            worker.setSalary(salaryUpdate);
            System.err.println("Update success");
        }
    }

    //allow user print history
    public void printListHistory(ArrayList<History> historys) {
        if (historys.isEmpty()) {
            System.err.println("List empty.");
            return;
        }
        System.out.printf("%-5s%-15s%-5s%-10s%-10s%-20s\n", "Code", "Name", "Age",
                "Salary", "Status", "Date");
        Collections.sort(historys);
        //print history from first to last list
        for (History history : historys) {
            printHistory(history);
        }
    }

    //get worker by code
    public Worker getWorkerByCode(ArrayList<Worker> workers, String id) {
        for (Worker worker : workers) {
            if (id.equalsIgnoreCase(worker.getId())) {
                return worker;
            }
        }
        return null;
    }

    //get current date 
    public String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        return dateFormat.format(calendar.getTime());
    }

    //print history
    public void printHistory(History history) {
        System.out.printf("%-5s%-15s%-5d%-10d%-10s%-20s\n", history.getId(),
                history.getName(), history.getAge(), history.getSalary(),
                history.getStatus(), history.getDate());
    }
}