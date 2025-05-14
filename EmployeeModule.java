import java.util.List;
import java.util.Scanner;

public class EmployeeModule {

    public static void employeeLogin(Scanner sc) {
        System.out.print("Enter employee ID as username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        if (!username.equals(password)) {
            System.out.println("Invalid credentials. Returning to main menu...");
            return;
        }

        int empId;
        try {
            empId = Integer.parseInt(username);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Returning to main menu...");
            return;
        }

        // Load employees and find matching employee
        List<Employee> employees = AdminModule.loadEmployees();
        Employee loggedInEmployee = null;
        for (Employee e : employees) {
            if (e.id == empId) {
                loggedInEmployee = e;
                break;
            }
        }

        if (loggedInEmployee == null) {
            System.out.println("Employee not found. Returning to main menu...");
            return;
        }

        // Employee menu
        int choice;
        do {
            System.out.println("===================================");
            System.out.println("      Employee Management System   ");
            System.out.println("           Employee Menu            ");
            System.out.println("===================================");
            System.out.println("1. View My Info");
            System.out.println("2. View My Report");
            System.out.println("3. Logout");
            System.out.println("-----------------------------------");
            System.out.print("Choose an option: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("\nMy Information:");
                    System.out.printf("ID: %d\nName: %s\nSalary: %.2f\nPosition: %s\nDepartment: %s\n",
                        loggedInEmployee.id, loggedInEmployee.name, loggedInEmployee.salary,
                        loggedInEmployee.getClass().getSimpleName(), loggedInEmployee.department.getName());
                    break;
                case 2:
                    System.out.println("\nMy Report:");
                    loggedInEmployee.generateReport();
                    break;
                case 3:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 3);
    }
}
