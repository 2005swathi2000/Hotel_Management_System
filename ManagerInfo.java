package hotel.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ManagerInfoAdvanced extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public ManagerInfoAdvanced() {

        setTitle("Manager Information");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Manager Details", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel();
        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        JButton refreshBtn = new JButton("Refresh");
        JButton backBtn = new JButton("Back");

        bottom.add(refreshBtn);
        bottom.add(backBtn);

        add(bottom, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> loadManagers());
        backBtn.addActionListener(e -> {
            dispose();
            new Reception().setVisible(true);
        });

        loadManagers();
    }

    private void loadManagers() {

        try {
            conn c = new conn();

            String query = "SELECT * FROM employee WHERE job = ?";
            PreparedStatement ps = c.c.prepareStatement(query);
            ps.setString(1, "Manager");

            ResultSet rs = ps.executeQuery();

            model.setRowCount(0);
            model.setColumnCount(0);

            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();

            for (int i = 1; i <= colCount; i++) {
                model.addColumn(meta.getColumnName(i));
            }

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
        new ManagerInfoAdvanced().setVisible(true);
    }
}