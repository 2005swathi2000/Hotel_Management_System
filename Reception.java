package hotel.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Reception extends JFrame {

    public Reception() {
        setTitle("Reception Dashboard");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        setContentPane(panel);

        // 🔹 Title
        JLabel title = new JLabel("HOTEL RECEPTION", JLabel.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 26));
        title.setForeground(new Color(25, 25, 112));
        panel.add(title, BorderLayout.NORTH);

        // 🔹 Buttons Panel
        JPanel btnPanel = new JPanel(new GridLayout(6, 2, 15, 15));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        btnPanel.setBackground(Color.WHITE);

        // 🔹 Buttons
        JButton btnNewCustomer = new JButton("New Customer");
        JButton btnRoom = new JButton("Room Details");
        JButton btnDepartment = new JButton("Department");
        JButton btnEmployee = new JButton("Employee Info");
        JButton btnCustomer = new JButton("Customer Info");
        JButton btnManager = new JButton("Manager Info");
        JButton btnCheckout = new JButton("Check Out");
        JButton btnUpdateCheck = new JButton("Update Check Status");
        JButton btnUpdateRoom = new JButton("Update Room");
        JButton btnPickup = new JButton("PickUp Service");
        JButton btnSearch = new JButton("Search Room");
        JButton btnLogout = new JButton("Logout");

        JButton[] buttons = {
                btnNewCustomer, btnRoom, btnDepartment, btnEmployee,
                btnCustomer, btnManager, btnCheckout, btnUpdateCheck,
                btnUpdateRoom, btnPickup, btnSearch, btnLogout
        };

        for (JButton btn : buttons) {
            styleButton(btn);
            btnPanel.add(btn);
        }

        panel.add(btnPanel, BorderLayout.CENTER);

        // 🔹 Actions
        btnNewCustomer.addActionListener(e -> {
            try {
                new NewCustomer().setVisible(true);
                dispose();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        btnRoom.addActionListener(e -> {
            try {
                new Room().setVisible(true);
                dispose();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        btnDepartment.addActionListener(e -> {
            try {
                new Department().setVisible(true);
                dispose();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        btnEmployee.addActionListener(e -> {
            try {
                new Employee().setVisible(true);
                dispose();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        btnCustomer.addActionListener(e -> {
            try {
                new CustomerInfo().setVisible(true);
                dispose();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        btnManager.addActionListener(e -> {
            try {
                new ManagerInfo().setVisible(true);
                dispose();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        btnCheckout.addActionListener(e -> {
            try {
                new CheckOut().setVisible(true);
                dispose();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        btnUpdateCheck.addActionListener(e -> {
            try {
                new UpdateCheck().setVisible(true);
                dispose();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        btnUpdateRoom.addActionListener(e -> {
            try {
                new UpdateRoom().setVisible(true);
                dispose();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        btnPickup.addActionListener(e -> {
            try {
                new PickUp().setVisible(true);
                dispose();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        btnSearch.addActionListener(e -> {
            try {
                new SearchRoom().setVisible(true);
                dispose();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        btnLogout.addActionListener(e -> {
            new Login().setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private void styleButton(JButton btn) {
        btn.setFont(new Font("Tahoma", Font.BOLD, 14));
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
    }

    public static void main(String[] args) {
        new Reception();
    }
}