import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EmployeeManagementGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Homepage components
    private JPanel homePanel;
    private JButton adminLoginButtonHome;
    private JButton employeeLoginButtonHome;

    // Admin login components
    private JPanel adminLoginPanel;
    private JTextField adminUsernameField;
    private JPasswordField adminPasswordField;
    private JButton adminLoginButton;
    private JLabel adminLoginStatusLabel;

    // Employee login components
    private JPanel employeeLoginPanel;
    private JTextField employeeIdField;
    private JPasswordField employeePasswordField;
    private JButton employeeLoginButton;
    private JLabel employeeLoginStatusLabel;

    // Admin menu components
    private JPanel adminMenuPanel;
    private JButton addEmployeeButton, viewEmployeesButton, updateEmployeeButton, deleteEmployeeButton;
    private JButton calculatePayButton, generateReportButton, adminLogoutButton;

    // Employee menu components
    private JPanel employeeMenuPanel;
    private JButton viewInfoButton, viewReportButton, employeeLogoutButton;
    private JLabel employeeInfoLabel;

    private String loggedInEmployeeId;

    public EmployeeManagementGUI() {
        setTitle("Employee Management System");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new java.awt.Color(245, 245, 220)); // Changed background color to beige

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        initHomePanel();
        initAdminLoginPanel();
        initEmployeeLoginPanel();
        initAdminMenuPanel();
        initEmployeeMenuPanel();

        add(mainPanel);
        cardLayout.show(mainPanel, "home");
    }

    private void initHomePanel() {
        homePanel = new JPanel(new GridBagLayout());
        homePanel.setBackground(new java.awt.Color(245, 245, 220)); // Changed background color to beige
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel headingLabel = new JLabel("Employee Management System");
        headingLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        headingLabel.setForeground(new java.awt.Color(180, 50, 47)); // Changed to dark olive green

        adminLoginButtonHome = new JButton("Admin Login");
        employeeLoginButtonHome = new JButton("Employee Login");

        Font buttonFont = new Font("Segoe UI", Font.BOLD, 20);
        Color buttonBg = new java.awt.Color(107, 142, 35); // Changed to olive drab
        Color buttonFg = Color.WHITE;

        adminLoginButtonHome.setFont(buttonFont);
        adminLoginButtonHome.setBackground(buttonBg);
        adminLoginButtonHome.setForeground(buttonFg);
        adminLoginButtonHome.setFocusPainted(false);
        adminLoginButtonHome.setPreferredSize(new Dimension(180, 50));

        employeeLoginButtonHome.setFont(buttonFont);
        employeeLoginButtonHome.setBackground(buttonBg);
        employeeLoginButtonHome.setForeground(buttonFg);
        employeeLoginButtonHome.setFocusPainted(false);
        employeeLoginButtonHome.setPreferredSize(new Dimension(180, 50));

        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        homePanel.add(headingLabel, gbc);

        gbc.gridwidth = 1;

        // Add small admin logo above admin login button
        gbc.gridy = 1; gbc.gridx = 0;
        ImageIcon adminIconFull = new ImageIcon("admin_logo.png");
        Image adminImg = adminIconFull.getImage().getScaledInstance(125, 125, Image.SCALE_SMOOTH);
        ImageIcon adminIconSmall = new ImageIcon(adminImg);
        JLabel adminLogoLabel = new JLabel(adminIconSmall);
        gbc.insets = new Insets(5, 20, 5, 20);
        homePanel.add(adminLogoLabel, gbc);

        // Add small employee logo above employee login button
        gbc.gridy = 1; gbc.gridx = 1;
        ImageIcon empIconFull = new ImageIcon("emp_logo.png");
        Image empImg = empIconFull.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon empIconSmall = new ImageIcon(empImg);
        JLabel empLogoLabel = new JLabel(empIconSmall);
        gbc.insets = new Insets(5, 20, 5, 20);
        homePanel.add(empLogoLabel, gbc);

        // Add admin login button below admin logo
        gbc.gridy = 2; gbc.gridx = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        homePanel.add(adminLoginButtonHome, gbc);

        // Add employee login button below employee logo
        gbc.gridy = 2; gbc.gridx = 1;
        homePanel.add(employeeLoginButtonHome, gbc);

        adminLoginButtonHome.addActionListener(e -> {
            clearAdminLoginFields();
            cardLayout.show(mainPanel, "adminLogin");
        });

        employeeLoginButtonHome.addActionListener(e -> {
            clearEmployeeLoginFields();
            cardLayout.show(mainPanel, "employeeLogin");
        });

        mainPanel.add(homePanel, "home");
    }

    private void initAdminLoginPanel() {
        adminLoginPanel = new JPanel(new GridBagLayout());
        adminLoginPanel.setBackground(new java.awt.Color(230, 240, 255));
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        adminUsernameField = new JTextField(20);
        adminPasswordField = new JPasswordField(20);
        adminLoginButton = new JButton("Login");
        adminLoginStatusLabel = new JLabel(" ");

        Font labelFont = new Font("Segoe UI", Font.BOLD, 20);
        usernameLabel.setFont(labelFont);
        passwordLabel.setFont(labelFont);

        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 20);
        adminUsernameField.setFont(fieldFont);
        adminPasswordField.setFont(fieldFont);
        adminUsernameField.setPreferredSize(new Dimension(250, 40));
        adminPasswordField.setPreferredSize(new Dimension(250, 40));

        Font buttonFont = new Font("Segoe UI", Font.BOLD, 20);
        Color buttonBg = new java.awt.Color(70, 130, 180);
        Color buttonFg = Color.WHITE;

        adminLoginButton.setFont(buttonFont);
        adminLoginButton.setBackground(buttonBg);
        adminLoginButton.setForeground(buttonFg);
        adminLoginButton.setFocusPainted(false);
        adminLoginButton.setPreferredSize(new Dimension(250, 45));

        gbc.insets = new Insets(15,15,15,15);
        gbc.gridx = 0; gbc.gridy = 0;
        adminLoginPanel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        adminLoginPanel.add(adminUsernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        adminLoginPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        adminLoginPanel.add(adminPasswordField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        adminLoginPanel.add(adminLoginButton, gbc);

        gbc.gridy = 3;
        adminLoginPanel.add(adminLoginStatusLabel, gbc);

        adminLoginButton.addActionListener(e -> handleAdminLogin());

        mainPanel.add(adminLoginPanel, "adminLogin");
    }

    private void initEmployeeLoginPanel() {
        employeeLoginPanel = new JPanel(new GridBagLayout());
        employeeLoginPanel.setBackground(new java.awt.Color(230, 240, 255));
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel idLabel = new JLabel("Employee ID:");
        JLabel passwordLabel = new JLabel("Password:");
        employeeIdField = new JTextField(20);
        employeePasswordField = new JPasswordField(20);
        employeeLoginButton = new JButton("Login");
        employeeLoginStatusLabel = new JLabel(" ");

        Font labelFont = new Font("Segoe UI", Font.BOLD, 20);
        idLabel.setFont(labelFont);
        passwordLabel.setFont(labelFont);

        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 20);
        employeeIdField.setFont(fieldFont);
        employeePasswordField.setFont(fieldFont);
        employeeIdField.setPreferredSize(new Dimension(250, 40));
        employeePasswordField.setPreferredSize(new Dimension(250, 40));

        Font buttonFont = new Font("Segoe UI", Font.BOLD, 20);
        Color buttonBg = new Color(60, 179, 113);
        Color buttonFg = Color.WHITE;

        employeeLoginButton.setFont(buttonFont);
        employeeLoginButton.setBackground(buttonBg);
        employeeLoginButton.setForeground(buttonFg);
        employeeLoginButton.setFocusPainted(false);
        employeeLoginButton.setPreferredSize(new Dimension(250, 45));

        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        employeeLoginPanel.add(idLabel, gbc);
        gbc.gridx = 1;
        employeeLoginPanel.add(employeeIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        employeeLoginPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        employeeLoginPanel.add(employeePasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        employeeLoginPanel.add(employeeLoginButton, gbc);

        gbc.gridy = 3;
        employeeLoginPanel.add(employeeLoginStatusLabel, gbc);

        employeeLoginButton.addActionListener(e -> handleEmployeeLogin());

        mainPanel.add(employeeLoginPanel, "employeeLogin");
    }

    
    private void initAdminMenuPanel() {
        adminMenuPanel = new JPanel(new GridBagLayout());
        adminMenuPanel.setBackground(new Color(230, 240, 255));

        JLabel titleLabel = new JLabel("Employee Management System - Admin Dashboard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(90, 130, 180));

        addEmployeeButton = new JButton("Add Employee");
        viewEmployeesButton = new JButton("View All Employees");
        updateEmployeeButton = new JButton("Update Employee");
        deleteEmployeeButton = new JButton("Delete Employee");
        calculatePayButton = new JButton("Calculate Pay for All Employees");
        generateReportButton = new JButton("Generate Reports for All Employees");
        adminLogoutButton = new JButton("Logout");

        Dimension buttonSize = new Dimension(225, 45); // Changed width to 225

        JButton[] buttons = {addEmployeeButton, viewEmployeesButton, updateEmployeeButton, deleteEmployeeButton,
                calculatePayButton, generateReportButton, adminLogoutButton};

        Color[] colors = {
                new Color(70, 130, 180),
                new Color(60, 179, 113),
                new Color(255, 165, 0),
                new Color(220, 20, 60),
                new Color(123, 104, 238),
                new Color(255, 105, 180),
                new Color(128, 128, 128)
        };

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.weightx = 1;

        // Add title label at the top
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 30, 0);
        adminMenuPanel.add(titleLabel, gbc);

        // Add buttons starting from row 1
        gbc.insets = new Insets(10, 0, 10, 0);
        for (int i = 0; i < buttons.length; i++) {
            JButton btn = buttons[i];
            btn.setPreferredSize(buttonSize);
            btn.setBackground(colors[i]);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);

            gbc.gridy = i + 1;
            adminMenuPanel.add(btn, gbc);
        }

        // Add action listeners for admin menu buttons
        addEmployeeButton.addActionListener(e -> addEmployee());
        viewEmployeesButton.addActionListener(e -> viewEmployees());
        updateEmployeeButton.addActionListener(e -> updateEmployee());
        deleteEmployeeButton.addActionListener(e -> deleteEmployee());
        calculatePayButton.addActionListener(e -> calculatePayForAll());
        generateReportButton.addActionListener(e -> generateReportsForAll());
        // Removed leave request feature action listener
        // viewLeaveRequestsButton.addActionListener(e -> openViewLeaveRequestsDialog());
        adminLogoutButton.addActionListener(e -> {
            loggedInEmployeeId = null;
            cardLayout.show(mainPanel, "home");
        });

        // Wrap adminMenuPanel inside a container panel to center vertically and horizontally
        JPanel containerPanel = new JPanel(new GridBagLayout());
        containerPanel.setBackground(new Color(245, 245, 220));
        GridBagConstraints containerGbc = new GridBagConstraints();
        containerGbc.gridx = 0;
        containerGbc.gridy = 0;
        containerGbc.weightx = 1;
        containerGbc.weighty = 1;
        containerGbc.fill = GridBagConstraints.NONE;
        containerGbc.anchor = GridBagConstraints.CENTER;
        containerPanel.add(adminMenuPanel, containerGbc);

        mainPanel.add(containerPanel, "adminMenu");
    }

    private void initEmployeeMenuPanel() {
        employeeMenuPanel = new JPanel(new GridBagLayout());
        employeeMenuPanel.setBackground(new Color(230, 240, 255));

        JLabel titleLabel = new JLabel("Employee Management System - Employee Dashboard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(60, 179, 113));

        viewInfoButton = new JButton("View My Info");
        viewReportButton = new JButton("View My Report");
        employeeLogoutButton = new JButton("Logout");
        employeeInfoLabel = new JLabel(" ", SwingConstants.CENTER);

        Dimension buttonSize = new Dimension(210, 45);

        JButton[] buttons = {viewInfoButton, viewReportButton, employeeLogoutButton};

        Color[] colors = {
                new Color(220, 20, 60),
                new Color(255, 140, 0),
                new Color(128, 128, 128)
        };

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.weightx = 1;

        // Add title label at the top
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 30, 0);
        employeeMenuPanel.add(titleLabel, gbc);

        // Add buttons starting from row 1
        gbc.insets = new Insets(10, 0, 10, 0);
        for (int i = 0; i < buttons.length; i++) {
            JButton btn = buttons[i];
            btn.setPreferredSize(buttonSize);
            btn.setBackground(colors[i]);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);

            gbc.gridy = i + 1;
            employeeMenuPanel.add(btn, gbc);
        }

        // Add action listeners for employee menu buttons
        viewInfoButton.addActionListener(e -> viewMyInfo());
        viewReportButton.addActionListener(e -> viewMyReport());
        employeeLogoutButton.addActionListener(e -> {
            loggedInEmployeeId = null;
            cardLayout.show(mainPanel, "home");
        });

        // Add employeeInfoLabel at the bottom spanning full width
        gbc.gridy = buttons.length + 1;
        gbc.insets = new Insets(10, 0, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        employeeMenuPanel.add(employeeInfoLabel, gbc);

        // Wrap employeeMenuPanel inside a container panel to center vertically and horizontally
        JPanel containerPanel = new JPanel(new GridBagLayout());
        containerPanel.setBackground(new Color(245, 245, 220));
        GridBagConstraints containerGbc = new GridBagConstraints();
        containerGbc.gridx = 0;
        containerGbc.gridy = 0;
        containerGbc.weightx = 1;
        containerGbc.weighty = 1;
        containerGbc.fill = GridBagConstraints.NONE;
        containerGbc.anchor = GridBagConstraints.CENTER;
        containerPanel.add(employeeMenuPanel, containerGbc);

        mainPanel.add(containerPanel, "employeeMenu");
    }

    private void handleAdminLogin() {
        String username = adminUsernameField.getText().trim();
        String password = new String(adminPasswordField.getPassword());

        if (username.equals("admin") && password.equals("admin")) {
            adminLoginStatusLabel.setText("Login successful as Admin.");
            clearAdminLoginFields();
            cardLayout.show(mainPanel, "adminMenu");
        } else {
            adminLoginStatusLabel.setText("Invalid credentials. Try again.");
        }
    }

    private void handleEmployeeLogin() {
        String empId = employeeIdField.getText().trim();
        String password = new String(employeePasswordField.getPassword());

        if (empId.equals(password) && empId.matches("\\d+")) {
            loggedInEmployeeId = empId;
            employeeLoginStatusLabel.setText("Login successful as Employee ID: " + loggedInEmployeeId);
            clearEmployeeLoginFields();
            cardLayout.show(mainPanel, "employeeMenu");
        } else {
            employeeLoginStatusLabel.setText("Invalid credentials. Try again.");
        }
    }

    private void clearAdminLoginFields() {
        adminUsernameField.setText("");
        adminPasswordField.setText("");
        adminLoginStatusLabel.setText(" ");
    }

    private void clearEmployeeLoginFields() {
        employeeIdField.setText("");
        employeePasswordField.setText("");
        employeeLoginStatusLabel.setText(" ");
    }

    // Admin functionalities
    private void addEmployee() {
        AddEmployeeForm addForm = new AddEmployeeForm(this, null);
        addForm.setVisible(true);
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
                data[i][2] = String.format("%.2f", e.salary);
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

            AddEmployeeForm updateForm = new AddEmployeeForm(this, empToUpdate);
            updateForm.setVisible(true);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid ID format.");
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
            String[] columnNames = {"ID", "Name", "Salary"};
            Object[][] data = new Object[employees.size()][3];
            double totalPayable = 0.0;
            for (int i = 0; i < employees.size(); i++) {
                Employee e = employees.get(i);
                data[i][0] = e.id;
                data[i][1] = e.name;
                data[i][2] = String.format("%.2f", e.salary);
                totalPayable += e.salary;
            }
            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            table.setFillsViewportHeight(true);

            JOptionPane.showMessageDialog(this, scrollPane, "Pay Details - Total: " + String.format("%.2f", totalPayable), JOptionPane.INFORMATION_MESSAGE);
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
            String[] columnNames = {"ID", "Name", "Report Description"};
            Object[][] data = new Object[employees.size()][3];
            for (int i = 0; i < employees.size(); i++) {
                Employee e = employees.get(i);
                data[i][0] = e.id;
                data[i][1] = e.name;
                data[i][2] = "Report for: " + e.name;
            }
            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            table.setFillsViewportHeight(true);

            JOptionPane.showMessageDialog(this, scrollPane, "Employee Reports", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error generating reports: " + e.getMessage());
        }
    }

    private void viewMyInfo() {
        try {
            List<Employee> employees = AdminModule.loadEmployees();
            Employee emp = null;
            for (Employee e : employees) {
                if (String.valueOf(e.id).equals(loggedInEmployeeId)) {
                    emp = e;
                    break;
                }
            }
            if (emp == null) {
                JOptionPane.showMessageDialog(this, "Employee not found.");
                return;
            }
            String info = String.format("ID: %d\nName: %s\nSalary: %.2f\nPosition: %s\nDepartment: %s",
                    emp.id, emp.name, emp.salary, emp.getClass().getSimpleName(), emp.department.getName());
            JOptionPane.showMessageDialog(this, info, "My Info", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error viewing info: " + e.getMessage());
        }
    }

    private void viewMyReport() {
        try {
            List<Employee> employees = AdminModule.loadEmployees();
            Employee emp = null;
            for (Employee e : employees) {
                if (String.valueOf(e.id).equals(loggedInEmployeeId)) {
                    emp = e;
                    break;
                }
            }
            if (emp == null) {
                JOptionPane.showMessageDialog(this, "Employee not found.");
                return;
            }
            // Create a custom JDialog for attractive report display
            JDialog reportDialog = new JDialog(this, "My Report", true);
            reportDialog.setSize(450, 400);
            reportDialog.setLocationRelativeTo(this);
            reportDialog.setLayout(new BorderLayout());

            JPanel headerPanel = new JPanel();
            headerPanel.setBackground(new Color(70, 130, 180));
            JLabel headerLabel = new JLabel("Employee Performance Report");
            headerLabel.setForeground(Color.WHITE);
            headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
            headerPanel.add(headerLabel);

            JTextArea reportArea = new JTextArea();
            reportArea.setEditable(false);
            reportArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            reportArea.setBackground(new Color(245, 245, 220));
            reportArea.setForeground(new Color(50, 50, 50));
            reportArea.setMargin(new Insets(10, 10, 10, 10));

            StringBuilder reportBuilder = new StringBuilder();
            reportBuilder.append("ID: ").append(emp.id).append("\n");
            reportBuilder.append("Name: ").append(emp.name).append("\n");
            reportBuilder.append("Position: ").append(emp.getClass().getSimpleName()).append("\n");
            reportBuilder.append("Department: ").append(emp.department.getName()).append("\n");
            reportBuilder.append(String.format("Salary: %.2f\n", emp.salary));
            reportBuilder.append("\nPerformance Highlights:\n");
            reportBuilder.append("• Consistently meets deadlines\n");
            reportBuilder.append("• Demonstrates strong teamwork\n");
            reportBuilder.append("• Shows initiative in projects\n");
            reportBuilder.append("• Maintains high quality standards\n");
            reportBuilder.append("\nManager's Comments:\n");
            reportBuilder.append("Keep up the excellent work and continue to develop your skills.\n");

            reportArea.setText(reportBuilder.toString());

            JScrollPane scrollPane = new JScrollPane(reportArea);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());

            JButton closeButton = new JButton("Close");
            closeButton.setBackground(new Color(70, 130, 180));
            closeButton.setForeground(Color.WHITE);
            closeButton.setFocusPainted(false);
            closeButton.addActionListener(e -> reportDialog.dispose());

            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(new Color(245, 245, 220));
            buttonPanel.add(closeButton);

            reportDialog.add(headerPanel, BorderLayout.NORTH);
            reportDialog.add(scrollPane, BorderLayout.CENTER);
            reportDialog.add(buttonPanel, BorderLayout.SOUTH);

            reportDialog.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error viewing report: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EmployeeManagementGUI gui = new EmployeeManagementGUI();
            gui.setVisible(true);
        });
    }

    // Inner class for Add/Update Employee Form
    private class AddEmployeeForm extends JDialog {
        private JTextField idField;
        private JTextField nameField;
        private JTextField salaryField;
        private JTextField positionField;
        private JTextField departmentField;
        private JButton saveButton;
        private JButton cancelButton;
        private boolean isUpdate;
        private Employee employeeToUpdate;

        public AddEmployeeForm(Frame owner, Employee employee) {
            super(owner, true);
            this.employeeToUpdate = employee;
            this.isUpdate = (employee != null);

            setTitle(isUpdate ? "Update Employee" : "Add Employee");
            setSize(400, 350);
            setLocationRelativeTo(owner);
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            JLabel idLabel = new JLabel("ID:");
            JLabel nameLabel = new JLabel("Name:");
            JLabel salaryLabel = new JLabel("Salary:");
            JLabel positionLabel = new JLabel("Position:");
            JLabel departmentLabel = new JLabel("Department:");

            idField = new JTextField(20);
            nameField = new JTextField(20);
            salaryField = new JTextField(20);
            positionField = new JTextField(20);
            departmentField = new JTextField(20);

            if (isUpdate) {
                idField.setText(String.valueOf(employeeToUpdate.id));
                idField.setEditable(false);
                nameField.setText(employeeToUpdate.name);
                salaryField.setText(String.valueOf(employeeToUpdate.salary));
                positionField.setText(employeeToUpdate.getClass().getSimpleName());
                departmentField.setText(employeeToUpdate.department.getName());
            }

            saveButton = new JButton(isUpdate ? "Update" : "Add");
            cancelButton = new JButton("Cancel");

            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.anchor = GridBagConstraints.WEST;

            gbc.gridx = 0; gbc.gridy = 0;
            add(idLabel, gbc);
            gbc.gridx = 1;
            add(idField, gbc);

            gbc.gridx = 0; gbc.gridy = 1;
            add(nameLabel, gbc);
            gbc.gridx = 1;
            add(nameField, gbc);

            gbc.gridx = 0; gbc.gridy = 2;
            add(salaryLabel, gbc);
            gbc.gridx = 1;
            add(salaryField, gbc);

            gbc.gridx = 0; gbc.gridy = 3;
            add(positionLabel, gbc);
            gbc.gridx = 1;
            add(positionField, gbc);

            gbc.gridx = 0; gbc.gridy = 4;
            add(departmentLabel, gbc);
            gbc.gridx = 1;
            add(departmentField, gbc);

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(saveButton);
            buttonPanel.add(cancelButton);

            gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
            add(buttonPanel, gbc);

            saveButton.addActionListener(e -> onSave());
            cancelButton.addActionListener(e -> dispose());
        }

        private void onSave() {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                String name = nameField.getText().trim();
                double salary = Double.parseDouble(salaryField.getText().trim());
                String position = positionField.getText().trim();
                String deptName = departmentField.getText().trim();

                if (name.isEmpty() || position.isEmpty() || deptName.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Department dept = new Department(deptName);
                Employee emp;
                if (position.equalsIgnoreCase("Manager")) emp = new Manager(id, name, salary, dept);
                else if (position.equalsIgnoreCase("Intern")) emp = new Intern(id, name, salary, dept);
                else emp = new Developer(id, name, salary, dept);

                List<Employee> employees = AdminModule.loadEmployees();

                if (isUpdate) {
                    boolean found = false;
                    for (int i = 0; i < employees.size(); i++) {
                        if (employees.get(i).id == id) {
                            employees.set(i, emp);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        JOptionPane.showMessageDialog(this, "Employee not found for update.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    for (Employee e : employees) {
                        if (e.id == id) {
                            JOptionPane.showMessageDialog(this, "Employee ID already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    employees.add(emp);
                }

                AdminModule.saveEmployees(employees);
                JOptionPane.showMessageDialog(this, isUpdate ? "Employee updated successfully." : "Employee added successfully.");
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid number format.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    } }

    // Method to open dialog for sending leave request (employee)
    // Removed leave request dialog method

    // Method to open dialog for viewing and managing leave requests (admin)
    // Removed leave request view dialog method
