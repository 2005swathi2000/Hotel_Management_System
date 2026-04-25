package hotel.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddDriversAdvanced extends JFrame implements ActionListener {

    private JTextField nameField, ageField, companyField, brandField, locationField;
    private JComboBox<String> genderBox, availabilityBox;
    private JButton addBtn, backBtn;

    public AddDriversAdvanced() {

        setTitle("Add Driver");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Add Driver Details");
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
        genderBox = new JComboBox<>(new String[]{"Male", "Female"});
        addComponent(panel, gbc, genderBox, 3);

        // Company
        addLabel(panel, gbc, "Car Company", 4);
        companyField = addTextField(panel, gbc, 4);

        // Brand
        addLabel(panel, gbc, "Car Brand", 5);
        brandField = addTextField(panel, gbc, 5);

        // Availability
        addLabel(panel, gbc, "Available", 6);
        availabilityBox = new JComboBox<>(new String[]{"Yes", "No"});
        addComponent(panel, gbc, availabilityBox, 6);

        // Location
        addLabel(panel, gbc, "Location", 7);
        locationField = addTextField(panel, gbc, 7);

        // Buttons
        addBtn = new JButton("Add Driver");
        backBtn = new JButton("Back");

        addBtn.addActionListener(this);
        backBtn.addActionListener(this);

        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(addBtn, gbc);

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

        if (e.getSource() == addBtn) {
            addDriver();
        } else if (e.getSource() == backBtn) {
            dispose();
        }
    }

    private void addDriver() {

        String name = nameField.getText().trim();
        String ageText = ageField.getText().trim();
        String gender = (String) genderBox.getSelectedItem();
        String company = companyField.getText().trim();
        String brand = brandField.getText().trim();
        String available = (String) availabilityBox.getSelectedItem();
        String location = locationField.getText().trim();

        // Validation
        if (name.isEmpty() || ageText.isEmpty() || company.isEmpty() || brand.isEmpty() || location.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageText);
            if (age <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter valid age!");
            return;
        }

        // Database Insert
        try {
            conn c = new conn();

            String query = "INSERT INTO driver (name, age, gender, company, brand, available, location) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = c.c.prepareStatement(query);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            ps.setString(4, company);
            ps.setString(5, brand);
            ps.setString(6, available);
            ps.setString(7, location);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Driver Added Successfully!");
            clearFields();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error!");
        }
    }

    private void clearFields() {
        nameField.setText("");
        ageField.setText("");
        companyField.setText("");
        brandField.setText("");
        locationField.setText("");
    }

    public static void main(String[] args) {
        new AddDriversAdvanced().setVisible(true);
    }
}