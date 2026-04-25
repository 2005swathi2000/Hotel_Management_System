package hotel.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class NewCustomerAdvanced extends JFrame {

    private JTextField numberField, nameField, countryField, checkinField, depositField;
    private JComboBox<String> idTypeBox;
    private JComboBox<String> roomBox;
    private JRadioButton maleBtn, femaleBtn;

    public NewCustomerAdvanced() {

        setTitle("New Customer");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("NEW CUSTOMER FORM", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(title, gbc);

        gbc.gridwidth = 1;

        // ID Type
        gbc.gridy++;
        panel.add(new JLabel("ID Type"), gbc);
        gbc.gridx = 1;
        idTypeBox = new JComboBox<>(new String[]{"Passport", "Aadhar", "Voter ID", "License"});
        panel.add(idTypeBox, gbc);

        // Number
        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("ID Number"), gbc);
        gbc.gridx = 1;
        numberField = new JTextField();
        panel.add(numberField, gbc);

        // Name
        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Name"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField();
        panel.add(nameField, gbc);

        // Gender
        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Gender"), gbc);
        gbc.gridx = 1;

        maleBtn = new JRadioButton("Male");
        femaleBtn = new JRadioButton("Female");

        ButtonGroup bg = new ButtonGroup();
        bg.add(maleBtn);
        bg.add(femaleBtn);

        JPanel genderPanel = new JPanel();
        genderPanel.add(maleBtn);
        genderPanel.add(femaleBtn);

        panel.add(genderPanel, gbc);

        // Country
        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Country"), gbc);
        gbc.gridx = 1;
        countryField = new JTextField();
        panel.add(countryField, gbc);

        // Room
        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Room Number"), gbc);
        gbc.gridx = 1;
        roomBox = new JComboBox<>();
        loadAvailableRooms();
        panel.add(roomBox, gbc);

        // Check-in
        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Check-in Status"), gbc);
        gbc.gridx = 1;
        checkinField = new JTextField("Checked-In");
        panel.add(checkinField, gbc);

        // Deposit
        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Deposit"), gbc);
        gbc.gridx = 1;
        depositField = new JTextField();
        panel.add(depositField, gbc);

        // Buttons
        JButton addBtn = new JButton("Add");
        JButton backBtn = new JButton("Back");

        gbc.gridx = 0; gbc.gridy++;
        panel.add(addBtn, gbc);
        gbc.gridx = 1;
        panel.add(backBtn, gbc);

        add(panel, BorderLayout.CENTER);

        // Actions
        addBtn.addActionListener(e -> addCustomer());
        backBtn.addActionListener(e -> {
            dispose();
            new Reception().setVisible(true);
        });
    }

    private void loadAvailableRooms() {
        try {
            conn c = new conn();
            String query = "SELECT room_number FROM room WHERE availability = 'Available'";
            PreparedStatement ps = c.c.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                roomBox.addItem(rs.getString("room_number"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addCustomer() {

        String idType = (String) idTypeBox.getSelectedItem();
        String number = numberField.getText().trim();
        String name = nameField.getText().trim();
        String country = countryField.getText().trim();
        String checkin = checkinField.getText().trim();
        String deposit = depositField.getText().trim();
        String room = (String) roomBox.getSelectedItem();

        String gender = maleBtn.isSelected() ? "Male" : (femaleBtn.isSelected() ? "Female" : "");

        if (number.isEmpty() || name.isEmpty() || gender.isEmpty() || country.isEmpty() || deposit.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill all fields!");
            return;
        }

        try {
            conn c = new conn();

            String insertQuery = "INSERT INTO customer VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = c.c.prepareStatement(insertQuery);

            ps.setString(1, idType);
            ps.setString(2, number);
            ps.setString(3, name);
            ps.setString(4, gender);
            ps.setString(5, country);
            ps.setString(6, room);
            ps.setString(7, checkin);
            ps.setString(8, deposit);

            ps.executeUpdate();

            String updateRoom = "UPDATE room SET availability = 'Occupied' WHERE room_number = ?";
            PreparedStatement ps2 = c.c.prepareStatement(updateRoom);
            ps2.setString(1, room);
            ps2.executeUpdate();

            JOptionPane.showMessageDialog(this, "Customer Added Successfully!");

            dispose();
            new Reception().setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving data!");
        }
    }

    public static void main(String[] args) {
        new NewCustomerAdvanced().setVisible(true);
    }
}