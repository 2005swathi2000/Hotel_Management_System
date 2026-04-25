package hotel.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class PickUpAdvanced extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JComboBox<String> carTypeBox;

    public PickUpAdvanced() {

        setTitle("Pick Up Service");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // 🔝 Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Pick Up Service");
        title.setFont(new Font("Arial", Font.BOLD, 18));

        carTypeBox = new JComboBox<>();
        loadCarTypes();

        JButton displayBtn = new JButton("Display");

        topPanel.add(title);
        topPanel.add(new JLabel("Car Type:"));
        topPanel.add(carTypeBox);
        topPanel.add(displayBtn);

        add(topPanel, BorderLayout.NORTH);

        // 📊 Table
        model = new DefaultTableModel();
        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // 🔽 Bottom Panel
        JPanel bottomPanel = new JPanel();
        JButton backBtn = new JButton("Back");

        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        // 🎯 Actions
        displayBtn.addActionListener(e -> loadDrivers());
        backBtn.addActionListener(e -> {
            dispose();
            new Reception().setVisible(true);
        });
    }

    private void loadCarTypes() {
        try {
            conn c = new conn();
            String query = "SELECT DISTINCT brand FROM driver WHERE available = 'Yes'";
            PreparedStatement ps = c.c.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                carTypeBox.addItem(rs.getString("brand"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDrivers() {

        String selectedBrand = (String) carTypeBox.getSelectedItem();

        try {
            conn c = new conn();

            String query = "SELECT * FROM driver WHERE brand = ? AND available = 'Yes'";
            PreparedStatement ps = c.c.prepareStatement(query);
            ps.setString(1, selectedBrand);

            ResultSet rs = ps.executeQuery();

            model.setRowCount(0);
            model.setColumnCount(0);

            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();

            // Column names
            for (int i = 1; i <= colCount; i++) {
                model.addColumn(meta.getColumnName(i));
            }

            // Rows
            while (rs.next()) {
                Object[] row = new Object[colCount];
                for (int i = 1; i <= colCount; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                model.addRow(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading drivers");
        }
    }

    public static void main(String[] args) {
        new PickUpAdvanced().setVisible(true);
    }
}