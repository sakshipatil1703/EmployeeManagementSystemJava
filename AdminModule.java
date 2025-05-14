import java.io.*;
import java.util.*;

// Custom Exception
class InvalidSalaryException extends Exception {
    public InvalidSalaryException(String message) {
        super(message);
    }
}

// Interface
interface Payable {
    void calculatePay();
}

interface ReportGenerator {
    void generateReport();
}

// Department class
class Department {
    private String name;

    public Department(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

// Base Employee class
class Employee implements Payable, ReportGenerator, Serializable {
    protected int id;
    protected String name;
    protected double salary;
    protected Department department;

    public Employee(int id, String name, double salary, Department department) throws InvalidSalaryException {
        if (salary < 0)
            throw new InvalidSalaryException("Salary cannot be negative!");
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.department = department;
    }

    public void calculatePay() {
        System.out.println("Pay: " + salary);
    }

    public void generateReport() {
        System.out.println("Report for " + name + ":");
        System.out.printf("ID: %d, Salary: %.2f, Position: %s, Department: %s%n",
            id, salary, getClass().getSimpleName(), department.getName());
    }

    public String toFormattedString() {
        return String.format("%-5d %-20s %-10.2f %-15s %-15s", id, name, salary, getClass().getSimpleName(), department.getName());
    }
}

// Subclasses
class Manager extends Employee {
    public Manager(int id, String name, double salary, Department department) throws InvalidSalaryException {
        super(id, name, salary, department);
    }
}

class Developer extends Employee {
    public Developer(int id, String name, double salary, Department department) throws InvalidSalaryException {
        super(id, name, salary, department);
    }
}

class Intern extends Employee {
    public Intern(int id, String name, double salary, Department department) throws InvalidSalaryException {
        super(id, name, salary, department);
    }
}

public class AdminModule {
    static final String FILE_NAME = "employees.txt";

    public static void adminMenu(Scanner sc) {
        if (!adminLogin(sc)) {
            System.out.println("Invalid credentials. Returning to main menu...");
            return;
        }

        int choice;

        do {
            System.out.println("\n--- Employee Management System (Admin) ---");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Calculate Pay for All Employees");
            System.out.println("6. Generate Reports for All Employees");
            System.out.println("7. Logout");
            System.out.print("Choose an option: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addEmployee(sc);
                    break;
                case 2:
                    viewEmployees();
                    break;
                case 3:
                    updateEmployee(sc);
                    break;
                case 4:
                    deleteEmployee(sc);
                    break;
                case 5:
                    calculatePayForAll();
                    break;
                case 6:
                    generateReportsForAll();
                    break;
                case 7:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 7);
    }

    public static boolean adminLogin(Scanner sc) {
        System.out.print("Enter admin username: ");
        String username = sc.nextLine();
        System.out.print("Enter admin password: ");
        String password = sc.nextLine();

        return username.equals("admin") && password.equals("admin");
    }

    // Load from file
    public static List<Employee> loadEmployees() {
        List<Employee> employees = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) return employees;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean headerSkipped = false;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("ID")) {
                    continue;
                }

                String[] parts = line.trim().split("\\s{2,}");
                if (parts.length >= 5) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    double salary = Double.parseDouble(parts[2]);
                    String position = parts[3];
                    String deptName = parts[4];

                    Department dept = new Department(deptName);
                    Employee emp;
                    if (position.equals("Manager")) emp = new Manager(id, name, salary, dept);
                    else if (position.equals("Intern")) emp = new Intern(id, name, salary, dept);
                    else emp = new Developer(id, name, salary, dept);

                    employees.add(emp);
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
        return employees;
    }

    // Save to file (overwrite)
    public static void saveEmployees(List<Employee> employees) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            writer.printf("%-5s %-20s %-10s %-15s %-15s\n", "ID", "Name", "Salary", "Position", "Department");
            writer.println("--------------------------------------------------------");
            for (Employee e : employees) {
                writer.println(e.toFormattedString());
            }
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public static void addEmployee(Scanner sc) {
        try {
            System.out.print("Enter ID: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Salary: ");
            double salary = sc.nextDouble();
            sc.nextLine();
            System.out.print("Enter Position (Manager/Developer/Intern): ");
            String position = sc.nextLine();
            System.out.print("Enter Department: ");
            String deptName = sc.nextLine();

            Department dept = new Department(deptName);
            Employee emp;
            if (position.equalsIgnoreCase("Manager")) emp = new Manager(id, name, salary, dept);
            else if (position.equalsIgnoreCase("Intern")) emp = new Intern(id, name, salary, dept);
            else emp = new Developer(id, name, salary, dept);

            List<Employee> employees = loadEmployees();
            employees.add(emp);
            saveEmployees(employees);
            System.out.println("Employee added successfully.");
        } catch (InvalidSalaryException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void viewEmployees() {
        List<Employee> employees = loadEmployees();
        if (employees.isEmpty()) {
            System.out.println("No employee data present.");
            return;
        }

        System.out.printf("%-5s %-20s %-10s %-15s %-15s\n", "ID", "Name", "Salary", "Position", "Department");
        System.out.println("------------------------------------------------------------------");
        for (Employee e : employees) {
            System.out.println(e.toFormattedString());
        }
    }

    public static void updateEmployee(Scanner sc) {
        List<Employee> employees = loadEmployees();
        System.out.print("Enter employee ID to update: ");
        int updateId = sc.nextInt();
        sc.nextLine();
        boolean found = false;

        for (Employee e : employees) {
            if (e.id == updateId) {
                found = true;
                System.out.print("Update Name (Y/N)? ");
                if (sc.nextLine().equalsIgnoreCase("Y")) {
                    System.out.print("Enter new name: ");
                    e.name = sc.nextLine();
                }

                System.out.print("Update Salary (Y/N)? ");
                if (sc.nextLine().equalsIgnoreCase("Y")) {
                    System.out.print("Enter new salary: ");
                    double newSalary = sc.nextDouble();
                    sc.nextLine();
                    try {
                        if (newSalary < 0)
                            throw new InvalidSalaryException("Salary cannot be negative!");
                        e.salary = newSalary;
                    } catch (InvalidSalaryException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                }

                System.out.print("Update Department (Y/N)? ");
                if (sc.nextLine().equalsIgnoreCase("Y")) {
                    System.out.print("Enter new department: ");
                    e.department = new Department(sc.nextLine());
                }

                System.out.println("Employee updated.");
                break;
            }
        }

        if (!found)
            System.out.println("Employee not found.");
        else
            saveEmployees(employees);
    }

    public static void deleteEmployee(Scanner sc) {
        List<Employee> employees = loadEmployees();
        System.out.print("Enter employee ID to delete: ");
        int delId = sc.nextInt();

        boolean removed = employees.removeIf(e -> e.id == delId);
        if (removed) {
            saveEmployees(employees);
            System.out.println("Employee deleted.");
        } else {
            System.out.println("Employee not found.");
        }
    }

    public static void calculatePayForAll() {
        List<Employee> employees = loadEmployees();
        if (employees.isEmpty()) {
            System.out.println("No employee data present.");
            return;
        }
        System.out.println("\n--- Pay Details for All Employees ---");
        double totalPayable = 0.0;
        for (Employee e : employees) {
            System.out.print(e.name + ": ");
            e.calculatePay();
            totalPayable += e.salary;
        }
        System.out.printf("Total Payable Amount: %.2f%n", totalPayable);
    }

    public static void generateReportsForAll() {
        List<Employee> employees = loadEmployees();
        if (employees.isEmpty()) {
            System.out.println("No employee data present.");
            return;
        }
        System.out.println("\n--- Reports for All Employees ---");
        for (Employee e : employees) {
            e.generateReport();
        }
    }
}
