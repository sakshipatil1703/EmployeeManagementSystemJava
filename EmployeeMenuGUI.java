import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EmployeeMenuGUI extends JFrame {
    private JButton viewInfoButton, viewReportButton, logoutButton;
    private JLabel employeeInfoLabel;
    private String loggedInEmployeeId;

    public EmployeeMenuGUI(String employeeId) {
        this.loggedInEmployeeId = employeeId;

        setTitle("Employee Menu");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(new java.awt.Color(230, 240, 255));

        setLayout(new GridLayout(4, 1, 15, 15));

        Font buttonFont = new Font("Segoe UI", Font.BOLD, 16);
        Color buttonBg = new java.awt.Color(70, 130, 180);
        Color buttonFg = Color.WHITE;

        viewInfoButton = new JButton("View My Info");
        viewReportButton = new JButton("View My Report");
        logoutButton = new JButton("Logout");
        employeeInfoLabel = new JLabel(" ", SwingConstants.CENTER);

        JButton[] buttons = {viewInfoButton, viewReportButton, logoutButton};
        for (JButton btn : buttons) {
            btn.setFont(buttonFont);
            btn.setBackground(buttonBg);
            btn.setForeground(buttonFg);
            btn.setFocusPainted(false);
        }

        add(viewInfoButton);
        add(viewReportButton);
        add(logoutButton);
        add(employeeInfoLabel);

        viewInfoButton.addActionListener(e -> viewMyInfo());
        viewReportButton.addActionListener(e -> viewMyReport());
        logoutButton.addActionListener(e -> dispose());
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
            String[] columnNames = {"Report Item", "Details"};
            Object[][] data = {
                {"Name", emp.name},
                {"ID", emp.id},
                {"Position", emp.getClass().getSimpleName()},
                {"Department", emp.department.getName()},
                {"Salary", String.format("%.2f", emp.salary)},
                // Add more detailed report items here if available
            };
            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            table.setFillsViewportHeight(true);
            scrollPane.setPreferredSize(new Dimension(400, 200));
            JOptionPane.showMessageDialog(this, scrollPane, "My Report", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error viewing report: " + e.getMessage());
        }
    }
}
