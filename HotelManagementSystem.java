package hotel.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HotelManagementSystemAdvanced extends JFrame {

    private JLabel titleLabel;

    public HotelManagementSystemAdvanced() {

        setTitle("Hotel Management System");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Background Panel
        JLabel background = new JLabel();
        background.setLayout(new BorderLayout());

        ImageIcon icon = new ImageIcon(
                ClassLoader.getSystemResource("hotel/management/system/icons/first.jpg"));
        Image img = icon.getImage().getScaledInstance(800, 400, Image.SCALE_SMOOTH);
        background.setIcon(new ImageIcon(img));

        // Title
        titleLabel = new JLabel("HOTEL MANAGEMENT SYSTEM", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        titleLabel.setForeground(Color.RED);

        background.add(titleLabel, BorderLayout.SOUTH);

        // Button
        JButton nextBtn = new JButton("Next");
        nextBtn.setFocusPainted(false);

        JPanel btnPanel = new JPanel();
        btnPanel.setOpaque(false);
        btnPanel.add(nextBtn);

        background.add(btnPanel, BorderLayout.NORTH);

        add(background);

        // Button Action
        nextBtn.addActionListener(e -> openLogin());

        // 🔥 Blinking Text using Timer
        Timer blinkTimer = new Timer(500, new ActionListener() {
            boolean visible = true;

            public void actionPerformed(ActionEvent e) {
                titleLabel.setVisible(visible);
                visible = !visible;
            }
        });
        blinkTimer.start();

        // 🚀 Auto navigate after 5 seconds
        Timer redirectTimer = new Timer(5000, e -> openLogin());
        redirectTimer.setRepeats(false);
        redirectTimer.start();
    }

    private void openLogin() {
        new Login().setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HotelManagementSystemAdvanced().setVisible(true);
        });
    }
}