import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminMenuGUI extends JFrame {
    private JButton addEmployeeButton, viewEmployeesButton, updateEmployeeButton, deleteEmployeeButton;
    private JButton calculatePayButton, generateReportButton, logoutButton;

    public AdminMenuGUI() {
        setTitle("Admin Menu");
        setSize(450, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set background color
        getContentPane().setBackground(new java.awt.Color(230, 240, 255));

        setLayout(new GridLayout(7, 1, 15, 15));

        Font buttonFont = new Font("Segoe UI", Font.BOLD, 16);

        addEmployeeButton = new JButton("Add Employee");
        viewEmployeesButton = new JButton("View All Employees");
        updateEmployeeButton = new JButton("Update Employee");
        deleteEmployeeButton = new JButton("Delete Employee");
        calculatePayButton = new JButton("Calculate Pay for All Employees");
        generateReportButton = new JButton("Generate Reports for All Employees");
        logoutButton = new JButton("Logout");

        // Set button colors and fonts
        Color buttonBg = new java.awt.Color(70, 130, 180);
        Color buttonFg = Color.WHITE;

        JButton[] buttons = {addEmployeeButton, viewEmployeesButton, updateEmployeeButton, deleteEmployeeButton,
                calculatePayButton, generateReportButton, logoutButton};

        for (JButton btn : buttons) {
            btn.setFont(buttonFont);
            btn.setBackground(buttonBg);
            btn.setForeground(buttonFg);
            btn.setFocusPainted(false);
        }

        add(addEmployeeButton);
        add(viewEmployeesButton);
        add(updateEmployeeButton);
        add(deleteEmployeeButton);
        add(calculatePayButton);
        add(generateReportButton);
        add(logoutButton);

        addEmployeeButton.addActionListener(e -> addEmployee());
        viewEmployeesButton.addActionListener(e -> viewEmployees());
        updateEmployeeButton.addActionListener(e -> updateEmployee());
        deleteEmployeeButton.addActionListener(e -> deleteEmployee());
        calculatePayButton.addActionListener(e -> calculatePayForAll());
        generateReportButton.addActionListener(e -> generateReportsForAll());
        logoutButton.addActionListener(e -> dispose());
    }

    private void addEmployee() {
        AddEmployeeForm form = new AddEmployeeForm(this);
        form.setVisible(true);
        if (form.isSucceeded()) {
            Employee emp = form.getEmployee();
            try {
                List<Employee> employees = AdminModule.loadEmployees();
                employees.add(emp);
                AdminModule.saveEmployees(employees);
                JOptionPane.showMessageDialog(this, "Employee added successfully.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error adding employee: " + e.getMessage());
            }
        }
    }

    // Inner class for Add Employee Form
    class AddEmployeeForm extends JDialog {
        private JTextField idField, nameField, salaryField, deptField;
        private JComboBox<String> positionCombo;
        private boolean succeeded;
        private Employee employee;

        public AddEmployeeForm(Frame parent) {
            super(parent, "Add Employee", true);
            JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

            panel.add(new JLabel("ID:"));
            idField = new JTextField();
            panel.add(idField);

            panel.add(new JLabel("Name:"));
            nameField = new JTextField();
            panel.add(nameField);

            panel.add(new JLabel("Salary:"));
            salaryField = new JTextField();
            panel.add(salaryField);

            panel.add(new JLabel("Position:"));
            positionCombo = new JComboBox<>(new String[]{"Manager", "Developer", "Intern"});
            panel.add(positionCombo);

            panel.add(new JLabel("Department:"));
            deptField = new JTextField();
            panel.add(deptField);

            JButton addButton = new JButton("Add");
            JButton cancelButton = new JButton("Cancel");

            addButton.addActionListener(e -> {
                try {
                    int id = Integer.parseInt(idField.getText().trim());
                    String name = nameField.getText().trim();
                    double salary = Double.parseDouble(salaryField.getText().trim());
                    String position = (String) positionCombo.getSelectedItem();
                    String deptName = deptField.getText().trim();

                    if (name.isEmpty() || deptName.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Name and Department cannot be empty.");
                        return;
                    }

                    Department dept = new Department(deptName);
                    if (position.equals("Manager")) {
                        employee = new Manager(id, name, salary, dept);
                    } else if (position.equals("Intern")) {
                        employee = new Intern(id, name, salary, dept);
                    } else {
                        employee = new Developer(id, name, salary, dept);
                    }
                    succeeded = true;
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid number format.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                }
            });

            cancelButton.addActionListener(e -> {
                succeeded = false;
                dispose();
            });

            JPanel buttonsPanel = new JPanel();
            buttonsPanel.add(addButton);
            buttonsPanel.add(cancelButton);

            getContentPane().add(panel, BorderLayout.CENTER);
            getContentPane().add(buttonsPanel, BorderLayout.PAGE_END);
            pack();
            setLocationRelativeTo(parent);
        }

        public boolean isSucceeded() {
            return succeeded;
        }

        public Employee getEmployee() {
            return employee;
        }
    }

    private void viewEmployees() {
        try {
            List<Employee> employees = AdminModule.loadEmployees();
            if (employees.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No employee data present.");
                return;
            }
            String[] columnNames = {"ID", "Name", "Salary", "Position", "Department"};
            Object[][] data = new Object[employees.size()][5];
            for (int i = 0; i < employees.size(); i++) {
                Employee e = employees.get(i);
                data[i][0] = e.id;
                data[i][1] = e.name;
                data[i][2] = e.salary;
                data[i][3] = e.getClass().getSimpleName();
                data[i][4] = e.department.getName();
            }
            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            table.setFillsViewportHeight(true);
            JOptionPane.showMessageDialog(this, scrollPane, "All Employees", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error viewing employees: " + e.getMessage());
        }
    }

    private void updateEmployee() {
        try {
            String idStr = JOptionPane.showInputDialog(this, "Enter employee ID to update:");
            if (idStr == null) return;
            int updateId = Integer.parseInt(idStr);

            List<Employee> employees = AdminModule.loadEmployees();
            Employee empToUpdate = null;
            for (Employee e : employees) {
                if (e.id == updateId) {
                    empToUpdate = e;
                    break;
                }
            }
            if (empToUpdate == null) {
                JOptionPane.showMessageDialog(this, "Employee not found.");
                return;
            }

            String newName = JOptionPane.showInputDialog(this, "Enter new name (leave blank to keep current):", empToUpdate.name);
            if (newName != null && !newName.trim().isEmpty()) {
                empToUpdate.name = newName.trim();
            }

            String newSalaryStr = JOptionPane.showInputDialog(this, "Enter new salary (leave blank to keep current):", String.valueOf(empToUpdate.salary));
            if (newSalaryStr != null && !newSalaryStr.trim().isEmpty()) {
                double newSalary = Double.parseDouble(newSalaryStr.trim());
                if (newSalary < 0) {
                    JOptionPane.showMessageDialog(this, "Salary cannot be negative!");
                    return;
                }
                empToUpdate.salary = newSalary;
            }

            String newDept = JOptionPane.showInputDialog(this, "Enter new department (leave blank to keep current):", empToUpdate.department.getName());
            if (newDept != null && !newDept.trim().isEmpty()) {
                empToUpdate.department = new Department(newDept.trim());
            }

            AdminModule.saveEmployees(employees);
            JOptionPane.showMessageDialog(this, "Employee updated.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating employee: " + e.getMessage());
        }
    }

    private void deleteEmployee() {
        try {
            String idStr = JOptionPane.showInputDialog(this, "Enter employee ID to delete:");
            if (idStr == null) return;
            int delId = Integer.parseInt(idStr);

            List<Employee> employees = AdminModule.loadEmployees();
            boolean removed = employees.removeIf(e -> e.id == delId);
            if (removed) {
                AdminModule.saveEmployees(employees);
                JOptionPane.showMessageDialog(this, "Employee deleted.");
            } else {
                JOptionPane.showMessageDialog(this, "Employee not found.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting employee: " + e.getMessage());
        }
    }

    private void calculatePayForAll() {
        try {
            List<Employee> employees = AdminModule.loadEmployees();
            if (employees.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No employee data present.");
                return;
            }
            String[] columnNames = {"Employee Name", "Salary"};
            Object[][] data = new Object[employees.size()][2];
            double totalPayable = 0.0;
            for (int i = 0; i < employees.size(); i++) {
                Employee e = employees.get(i);
                data[i][0] = e.name;
                data[i][1] = String.format("%.2f", e.salary);
                totalPayable += e.salary;
            }
            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            table.setFillsViewportHeight(true);

            // Show total payable below the table in a panel
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(scrollPane, BorderLayout.CENTER);
            JLabel totalLabel = new JLabel("Total Payable Amount: " + String.format("%.2f", totalPayable));
            totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            totalLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            panel.add(totalLabel, BorderLayout.SOUTH);

            JOptionPane.showMessageDialog(this, panel, "Pay Details", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error calculating pay: " + e.getMessage());
        }
    }

    private void generateReportsForAll() {
        try {
            List<Employee> employees = AdminModule.loadEmployees();
            if (employees.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No employee data present.");
                return;
            }
            String[] columnNames = {"Employee Name", "Report Summary"};
            Object[][] data = new Object[employees.size()][2];
            for (int i = 0; i < employees.size(); i++) {
                Employee e = employees.get(i);
                data[i][0] = e.name;
                // For demonstration, just a placeholder summary. Replace with actual report details if available.
                data[i][1] = "Report details for " + e.name;
            }
            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            table.setFillsViewportHeight(true);
            JOptionPane.showMessageDialog(this, scrollPane, "Reports", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error generating reports: " + e.getMessage());
        }
    }
}
