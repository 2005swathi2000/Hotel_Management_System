package hotel.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddRoomAdvanced extends JFrame implements ActionListener {

    private JTextField roomField, priceField;
    private JComboBox<String> availabilityBox, cleaningBox, bedTypeBox;
    private JButton addBtn, backBtn;

    public AddRoomAdvanced() {

        setTitle("Add Room");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Add Room Details");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(title, gbc);

        gbc.gridwidth = 1;

        // Room Number
        addLabel(panel, gbc, "Room Number", 1);
        roomField = addTextField(panel, gbc, 1);

        // Availability
        addLabel(panel, gbc, "Availability", 2);
        availabilityBox = new JComboBox<>(new String[]{"Available", "Occupied"});
        addComponent(panel, gbc, availabilityBox, 2);

        // Cleaning Status
        addLabel(panel, gbc, "Cleaning Status", 3);
        cleaningBox = new JComboBox<>(new String[]{"Cleaned", "Dirty"});
        addComponent(panel, gbc, cleaningBox, 3);

        // Price
        addLabel(panel, gbc, "Price", 4);
        priceField = addTextField(panel, gbc, 4);

        // Bed Type
        addLabel(panel, gbc, "Bed Type", 5);
        bedTypeBox = new JComboBox<>(new String[]{"Single Bed", "Double Bed"});
        addComponent(panel, gbc, bedTypeBox, 5);

        // Buttons
        addBtn = new JButton("Add Room");
        backBtn = new JButton("Back");

        addBtn.addActionListener(this);
        backBtn.addActionListener(this);

        gbc.gridx = 0;
        gbc.gridy = 6;
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
            addRoom();
        } else if (e.getSource() == backBtn) {
            dispose();
        }
    }

    private void addRoom() {

        String roomNo = roomField.getText().trim();
        String availability = (String) availabilityBox.getSelectedItem();
        String cleaning = (String) cleaningBox.getSelectedItem();
        String priceText = priceField.getText().trim();
        String bedType = (String) bedTypeBox.getSelectedItem();

        // Validation
        if (roomNo.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }

        if (!roomNo.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Room number must be numeric!");
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceText);
            if (price <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter valid price!");
            return;
        }

        // Database Insert
        try {
            conn c = new conn();

            String query = "INSERT INTO room (room_no, availability, cleaning_status, price, bed_type) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps = c.c.prepareStatement(query);
            ps.setString(1, roomNo);
            ps.setString(2, availability);
            ps.setString(3, cleaning);
            ps.setDouble(4, price);
            ps.setString(5, bedType);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Room Added Successfully!");
            clearFields();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error!");
        }
    }

    private void clearFields() {
        roomField.setText("");
        priceField.setText("");
    }

    public static void main(String[] args) {
        new AddRoomAdvanced().setVisible(true);
    }
}