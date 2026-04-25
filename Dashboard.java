package hotel.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DashboardAdvanced extends JFrame {

    public DashboardAdvanced() {

        setTitle("Hotel Management System");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Background Panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel background = new JLabel();
        background.setLayout(new BorderLayout());

        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("hotel/management/system/icons/third.jpg"));
        Image img = icon.getImage().getScaledInstance(1500, 800, Image.SCALE_SMOOTH);
        background.setIcon(new ImageIcon(img));

        // Title
        JLabel title = new JLabel("THE TAJ GROUP WELCOMES YOU", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

        background.add(title, BorderLayout.NORTH);
        mainPanel.add(background, BorderLayout.CENTER);

        add(mainPanel);

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();

        // HOTEL MENU
        JMenu hotelMenu = new JMenu("Hotel");
        JMenuItem receptionItem = new JMenuItem("Reception");

        receptionItem.addActionListener(e -> new Reception().setVisible(true));

        hotelMenu.add(receptionItem);

        // ADMIN MENU
        JMenu adminMenu = new JMenu("Admin");

        JMenuItem addEmployee = new JMenuItem("Add Employee");
        JMenuItem addRoom = new JMenuItem("Add Room");
        JMenuItem addDriver = new JMenuItem("Add Driver");

        addEmployee.addActionListener(e -> new AddEmployee().setVisible(true));
        addRoom.addActionListener(e -> new AddRoom().setVisible(true));
        addDriver.addActionListener(e -> new AddDrivers().setVisible(true));

        adminMenu.add(addEmployee);
        adminMenu.add(addRoom);
        adminMenu.add(addDriver);

        // Add menus
        menuBar.add(hotelMenu);
        menuBar.add(adminMenu);

        setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DashboardAdvanced().setVisible(true);
        });
    }
}