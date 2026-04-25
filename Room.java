package hotel.management.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import net.proteanit.sql.DbUtils;
import java.awt.*;
import java.sql.*;

public class Room extends JFrame {

    private JTable table;

    public Room() {
        setTitle("Room Details");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);
        setContentPane(panel);

        // 🔹 Title
        JLabel title = new JLabel("ROOM DETAILS", JLabel.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 22));
        panel.add(title, BorderLayout.NORTH);

        // 🔹 Table
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // 🔹 Buttons
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);

        JButton btnLoad = new JButton("Load Data");
        JButton btnBack = new JButton("Back");

        styleButton(btnLoad);
        styleButton(btnBack);

        btnPanel.add(btnLoad);
        btnPanel.add(btnBack);

        panel.add(btnPanel, BorderLayout.SOUTH);

        // 🔹 Actions
        btnLoad.addActionListener(e -> loadData());

        btnBack.addActionListener(e -> {
            new Reception().setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private void loadData() {
        try {
            conn c = new conn();
            String query = "SELECT * FROM room";

            PreparedStatement pst = c.c.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            table.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading data");
            e.printStackTrace();
        }
    }

    private void styleButton(JButton btn) {
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Tahoma", Font.BOLD, 14));
    }

    public static void main(String[] args) {
        new Room();
    }
}