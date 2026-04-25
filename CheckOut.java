package hotel.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class CheckOutAdvanced extends JFrame implements ActionListener {

    private JComboBox<String> customerBox;
    private JTextField roomField;
    private JButton fetchBtn, checkoutBtn, backBtn;

    public CheckOutAdvanced() {

        setTitle("Check Out");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Customer Check Out");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(title, gbc);

        gbc.gridwidth = 1;

        // Customer Number
        addLabel(panel, gbc, "Customer ID", 1);
        customerBox = new JComboBox<>();
        loadCustomers();
        addComponent(panel, gbc, customerBox, 1);

        // Fetch Button
        fetchBtn = new JButton("Fetch Room");
        fetchBtn.addActionListener(this);
        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(fetchBtn, gbc);

        // Room Number
        addLabel(panel, gbc, "Room Number", 2);
        roomField = new JTextField();
        roomField.setEditable(false);
        addComponent(panel, gbc, roomField, 2);

        // Buttons
        checkoutBtn = new JButton("Check Out");
        backBtn = new JButton("Back");

        checkoutBtn.addActionListener(this);
        backBtn.addActionListener(this);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(checkoutBtn, gbc);

        gbc.gridx = 1;
        panel.add(backBtn, gbc);

        add(panel);
    }

    private void addLabel(JPanel panel, GridBagConstraints gbc, String text, int y) {
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(new JLabel(text), gbc);
    }

    private void addComponent(JPanel panel, GridBagConstraints gbc, JComponent comp, int y) {
        gbc.gridx = 1;
        gbc.gridy = y;
        panel.add(comp, gbc);
    }

    private void loadCustomers() {
        try {
            conn c = new conn();
            String query = "SELECT number FROM customer";
            PreparedStatement ps = c.c.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                customerBox.addItem(rs.getString("number"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == fetchBtn) {
            fetchRoom();
        } else if (e.getSource() == checkoutBtn) {
            checkOutCustomer();
        } else if (e.getSource() == backBtn) {
            dispose();
            new Reception().setVisible(true);
        }
    }

    private void fetchRoom() {
        String customerId = (String) customerBox.getSelectedItem();

        try {
            conn c = new conn();
            String query = "SELECT room_number FROM customer WHERE number = ?";
            PreparedStatement ps = c.c.prepareStatement(query);
            ps.setString(1, customerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                roomField.setText(rs.getString("room_number"));
            } else {
                JOptionPane.showMessageDialog(this, "No record found!");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void checkOutCustomer() {

        String customerId = (String) customerBox.getSelectedItem();
        String roomNo = roomField.getText();

        if (roomNo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fetch room first!");
            return;
        }

        try {
            conn c = new conn();

            String deleteQuery = "DELETE FROM customer WHERE number = ?";
            String updateRoom = "UPDATE room SET availability = 'Available' WHERE room_number = ?";

            PreparedStatement ps1 = c.c.prepareStatement(deleteQuery);
            ps1.setString(1, customerId);

            PreparedStatement ps2 = c.c.prepareStatement(updateRoom);
            ps2.setString(1, roomNo);

            ps1.executeUpdate();
            ps2.executeUpdate();

            JOptionPane.showMessageDialog(this, "Check Out Successful!");

            dispose();
            new Reception().setVisible(true);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error!");
        }
    }

    public static void main(String[] args) {
        new CheckOutAdvanced().setVisible(true);
    }
}