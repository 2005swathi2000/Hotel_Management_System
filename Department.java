package hotel.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class DepartmentAdvanced extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JTextField searchField;

    public DepartmentAdvanced() {

        setTitle("Department Details");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Department Information");
        title.setFont(new Font("Arial", Font.BOLD, 18));

        searchField = new JTextField(15);
        JButton searchBtn = new JButton("Search");
        JButton refreshBtn = new JButton("Refresh");

        topPanel.add(title);
        topPanel.add(new JLabel("Search:"));
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
        searchBtn.addActionListener(e -> searchDepartment());
        refreshBtn.addActionListener(e -> loadDepartments());
        backBtn.addActionListener(e -> {
            dispose();
            new Reception().setVisible(true);
        });

        // Load data initially
        loadDepartments();
    }

    private void loadDepartments() {
        try {
            conn c = new conn();
            String query = "SELECT * FROM department";
            PreparedStatement ps = c.c.prepareStatement(query);
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
            JOptionPane.showMessageDialog(this, "Error loading data");
        }
    }

    private void searchDepartment() {

        String keyword = searchField.getText().trim();

        if (keyword.isEmpty()) {
            loadDepartments();
            return;
        }

        try {
            conn c = new conn();
            String query = "SELECT * FROM department WHERE dept_name LIKE ?";
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
        new DepartmentAdvanced().setVisible(true);
    }
}