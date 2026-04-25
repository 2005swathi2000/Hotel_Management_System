package hotel.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddEmployeeAdvanced extends JFrame implements ActionListener {

    private JTextField nameField, ageField, salaryField, phoneField, aadharField, emailField;
    private JComboBox<String> jobBox;
    private JRadioButton maleBtn, femaleBtn;
    private JButton saveBtn, backBtn;

    public AddEmployeeAdvanced() {

        setTitle("Add Employee");
        setSize(500, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Add Employee Details");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(title, gbc);

        gbc.gridwidth = 1;

        // Name
        addLabel(panel, gbc, "Name", 1);
        nameField = addTextField(panel, gbc, 1);

        // Age
        addLabel(panel, gbc, "Age", 2);
        ageField = addTextField(panel, gbc, 2);

        // Gender
        addLabel(panel, gbc, "Gender", 3);
        JPanel genderPanel = new JPanel();
        maleBtn = new JRadioButton("Male");
        femaleBtn = new JRadioButton("Female");
        ButtonGroup bg = new ButtonGroup();
        bg.add(maleBtn);
        bg.add(femaleBtn);
        genderPanel.add(maleBtn);
        genderPanel.add(femaleBtn);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(genderPanel, gbc);

        // Job
        addLabel(panel, gbc, "Job", 4);
        String jobs[] = {"Front Desk", "Porter", "Housekeeping", "Kitchen Staff", "Room Service", "Waiter", "Manager", "Accountant", "Chef"};
        jobBox = new JComboBox<>(jobs);
        addComponent(panel, gbc, jobBox, 4);

        // Salary
        addLabel(panel, gbc, "Salary", 5);
        salaryField = addTextField(panel, gbc, 5);

        // Phone
        addLabel(panel, gbc, "Phone", 6);
        phoneField = addTextField(panel, gbc, 6);

        // Aadhar
        addLabel(panel, gbc, "Aadhar", 7);
        aadharField = addTextField(panel, gbc, 7);

        // Email
        addLabel(panel, gbc, "Email", 8);
        emailField = addTextField(panel, gbc, 8);

        // Buttons
        saveBtn = new JButton("Save");
        backBtn = new JButton("Back");

        saveBtn.addActionListener(this);
        backBtn.addActionListener(this);

        gbc.gridx = 0;
        gbc.gridy = 9;
        panel.add(saveBtn, gbc);

        gbc.gridx = 1;
        panel.add(backBtn, gbc);

        add(panel);
    }

    private void addLabel(JPanel panel, GridBagConstraints gbc, String text, int y) {
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(new JLabel(text), gbc);
    }

    private JTextField addTextField(JPanel panel, GridBagConstraints gbc, int y) {
        JTextField field = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = y;
        panel.add(field, gbc);
        return field;
    }

    private void addComponent(JPanel panel, GridBagConstraints gbc, JComponent comp, int y) {
        gbc.gridx = 1;
        gbc.gridy = y;
        panel.add(comp, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == saveBtn) {
            saveEmployee();
        } else if (e.getSource() == backBtn) {
            dispose();
        }
    }

    private void saveEmployee() {

        String name = nameField.getText().trim();
        String ageText = ageField.getText().trim();
        String salaryText = salaryField.getText().trim();
        String phone = phoneField.getText().trim();
        String aadhar = aadharField.getText().trim();
        String email = emailField.getText().trim();

        String gender = null;
        if (maleBtn.isSelected()) gender = "Male";
        else if (femaleBtn.isSelected()) gender = "Female";

        String job = (String) jobBox.getSelectedItem();

        // Validation
        if (name.isEmpty() || ageText.isEmpty() || salaryText.isEmpty() ||
            phone.isEmpty() || aadhar.isEmpty() || email.isEmpty() || gender == null) {

            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        int age;
        double salary;

        try {
            age = Integer.parseInt(ageText);
            salary = Double.parseDouble(salaryText);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid Age or Salary!");
            return;
        }

        if (!phone.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Phone must be 10 digits!");
            return;
        }

        if (!aadhar.matches("\\d{12}")) {
            JOptionPane.showMessageDialog(this, "Aadhar must be 12 digits!");
            return;
        }

        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(this, "Invalid Email!");
            return;
        }

        // Database Insert
        try {
            conn c = new conn();

            String query = "INSERT INTO employee (name, age, gender, job, salary, phone, aadhar, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = c.c.prepareStatement(query);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            ps.setString(4, job);
            ps.setDouble(5, salary);
            ps.setString(6, phone);
            ps.setString(7, aadhar);
            ps.setString(8, email);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Employee Added Successfully!");
            clearFields();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error!");
        }
    }

    private void clearFields() {
        nameField.setText("");
        ageField.setText("");
        salaryField.setText("");
        phoneField.setText("");
        aadharField.setText("");
        emailField.setText("");
    }

    public static void main(String[] args) {
        new AddEmployeeAdvanced().setVisible(true);
    }
}