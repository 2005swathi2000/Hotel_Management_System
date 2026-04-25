package hotel.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class CustomerInfoAdvanced extends JFrame {

    private JTable table;
    private JTextField searchField;
    private DefaultTableModel model;

    public CustomerInfoAdvanced() {

        setTitle("Customer Information");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Customer Details");
        title.setFont(new Font("Arial", Font.BOLD, 18));

        searchField = new JTextField(15);
        JButton searchBtn = new JButton("Search");
        JButton refreshBtn = new JButton("Refresh");

        topPanel.add(title);
        topPanel.add(new JLabel("Search: "));
        topPanel.add(searchField);
        topPanel.add(searchBtn);
        topPanel.add(refreshBtn);

        add(topPanel, BorderLayout.NORTH);

        // Table
        model = new DefaultTableModel();
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        JButton backBtn = new JButton("Back");

        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        // Button Actions
        searchBtn.addActionListener(e -> searchCustomer());
        refreshBtn.addActionListener(e -> loadCustomers());
        backBtn.addActionListener(e -> {
            dispose();
            new Reception().setVisible(true);
        });

        // Load data automatically
        loadCustomers();
    }

    private void loadCustomers() {
        try {
            conn c = new conn();
            String query = "SELECT * FROM customer";
            PreparedStatement ps = c.c.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            model.setRowCount(0);
            model.setColumnCount(0);

            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();

            // Set column names
            for (int i = 1; i <= colCount; i++) {
                model.addColumn(meta.getColumnName(i));
            }

            // Add rows
            while (rs.next()) {
                Object[] row = new Object[colCount];
                for (int i = 1; i <= colCount; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                model.addRow(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data");
        }
    }

    private void searchCustomer() {

        String keyword = searchField.getText().trim();

        if (keyword.isEmpty()) {
            loadCustomers();
            return;
        }

        try {
            conn c = new conn();
            String query = "SELECT * FROM customer WHERE name LIKE ?";
            PreparedStatement ps = c.c.prepareStatement(query);
            ps.setString(1, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();

            model.setRowCount(0);

            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();

            while (rs.next()) {
                Object[] row = new Object[colCount];
                for (int i = 1; i <= colCount; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                model.addRow(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new CustomerInfoAdvanced().setVisible(true);
    }
}