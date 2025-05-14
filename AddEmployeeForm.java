import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class AddEmployeeForm extends JDialog {
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
}
