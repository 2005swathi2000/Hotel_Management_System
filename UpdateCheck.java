package hotel.management.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;

public class UpdateCheck extends JFrame {

    private JComboBox<String> customerId;
    private JTextField roomField, nameField, statusField, paidField, pendingField;

    public UpdateCheck() {

        setTitle("Update Check Status");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(new EmptyBorder(20, 50, 20, 50));
        panel.setBackground(Color.WHITE);
        setContentPane(panel);

        // 🔹 Fields
        customerId = new JComboBox<>();
        loadCustomers();

        roomField = new JTextField();
        nameField = new JTextField();
        statusField = new JTextField();
        paidField = new JTextField();
        pendingField = new JTextField();

        // 🔹 Labels + Inputs
        panel.add(new JLabel("Customer ID:"));
        panel.add(customerId);

        panel.add(new JLabel("Room Number:"));
        panel.add(roomField);

        panel.add(new JLabel("Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Status:"));
        panel.add(statusField);

        panel.add(new JLabel("Amount Paid:"));
        panel.add(paidField);

        panel.add(new JLabel("Pending:"));
        panel.add(pendingField);

        // 🔹 Buttons
        JButton btnCheck = new JButton("Load");
        JButton btnUpdate = new JButton("Update");
        JButton btnBack = new JButton("Back");

        styleButton(btnCheck);
        styleButton(btnUpdate);
        styleButton(btnBack);

        panel.add(btnCheck);
        panel.add(btnUpdate);

        panel.add(new JLabel());
        panel.add(btnBack);

        // 🔹 Actions
        btnCheck.addActionListener(e -> loadCustomerData());

        btnUpdate.addActionListener(e -> updateData());

        btnBack.addActionListener(e -> {
            new Reception().setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private void loadCustomers() {
        try (Connection con = new conn().c;
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT number FROM customer")) {

            while (rs.next()) {
                customerId.addItem(rs.getString("number"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCustomerData() {
        try (Connection con = new conn().c;
             PreparedStatement pst = con.prepareStatement(
                     "SELECT * FROM customer WHERE number=?")) {

            pst.setString(1, customerId.getSelectedItem().toString());
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                roomField.setText(rs.getString("room_number"));
                nameField.setText(rs.getString("name"));
                statusField.setText(rs.getString("status"));
                paidField.setText(rs.getString("deposit"));
            }

            // 🔹 Calculate pending
            PreparedStatement pst2 = con.prepareStatement(
                    "SELECT price FROM room WHERE room_number=?");
            pst2.setString(1, roomField.getText());
            ResultSet rs2 = pst2.executeQuery();

            if (rs2.next()) {
                int total = rs2.getInt("price");
                int paid = Integer.parseInt(paidField.getText());
                pendingField.setText(String.valueOf(total - paid));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateData() {
        try (Connection con = new conn().c;
             PreparedStatement pst = con.prepareStatement(
                     "UPDATE customer SET room_number=?, name=?, status=?, deposit=? WHERE number=?")) {

            pst.setString(1, roomField.getText());
            pst.setString(2, nameField.getText());
            pst.setString(3, statusField.getText());
            pst.setString(4, paidField.getText());
            pst.setString(5, customerId.getSelectedItem().toString());

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Updated Successfully");

        } catch (Exception e) {
            e.printStackMessage();
        }
    }

    private void styleButton(JButton btn) {
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Tahoma", Font.BOLD, 14));
    }

    public static void main(String[] args) {
        new UpdateCheck();
    }
}