package hotel.management.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import net.proteanit.sql.DbUtils;
import java.awt.*;
import java.sql.*;

public class SearchRoom extends JFrame {

    private JTable table;
    private JComboBox<String> bedType;
    private JCheckBox availableOnly;

    public SearchRoom() {

        setTitle("Search Room");
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);
        setContentPane(panel);

        // 🔹 Title
        JLabel title = new JLabel("SEARCH ROOM", JLabel.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 22));
        panel.add(title, BorderLayout.NORTH);

        // 🔹 Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);

        topPanel.add(new JLabel("Bed Type:"));

        bedType = new JComboBox<>(new String[]{"Single Bed", "Double Bed"});
        topPanel.add(bedType);

        availableOnly = new JCheckBox("Only Available");
        availableOnly.setBackground(Color.WHITE);
        topPanel.add(availableOnly);

        JButton btnSearch = new JButton("Search");
        styleButton(btnSearch);
        topPanel.add(btnSearch);

        panel.add(topPanel, BorderLayout.NORTH);

        // 🔹 Table
        table = new JTable();
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        // 🔹 Bottom Panel
        JPanel bottom = new JPanel();
        bottom.setBackground(Color.WHITE);

        JButton btnBack = new JButton("Back");
        styleButton(btnBack);

        bottom.add(btnBack);
        panel.add(bottom, BorderLayout.SOUTH);

        // 🔹 Actions
        btnSearch.addActionListener(e -> searchRooms());

        btnBack.addActionListener(e -> {
            new Reception().setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private void searchRooms() {
        String query = "SELECT * FROM room WHERE bed_type = ?";
        if (availableOnly.isSelected()) {
            query += " AND availability = 'Available'";
        }

        try (Connection con = new conn().c;
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, bedType.getSelectedItem().toString());

            ResultSet rs = pst.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error fetching data");
            e.printStackTrace();
        }
    }

    private void styleButton(JButton btn) {
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Tahoma", Font.BOLD, 14));
    }

    public static void main(String[] args) {
        new SearchRoom();
    }
}