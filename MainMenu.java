import java.util.Scanner;

public class MainMenu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("===================================");
            System.out.println("      Employee Management System   ");
            System.out.println("===================================");
            System.out.println("1. Admin Login");
            System.out.println("2. Employee Login");
            System.out.println("3. Exit");
            System.out.println("-----------------------------------");
            System.out.print("Choose an option: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    AdminModule.adminMenu(sc);
                    break;
                case 2:
                    EmployeeModule.employeeLogin(sc);
                    break;
                case 3:
                    System.out.println("Exiting program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 3);
        sc.close();
    }
}
