package hotel.management.system;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;

public class UpdateRoom extends JFrame {

    private JPanel contentPane;
    private JTextField txt_Ava;
    private JTextField txt_Status;
    private JTextField txt_Room;
    Choice c1;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                new UpdateRoom().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public UpdateRoom() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(530, 200, 1000, 450);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel title = new JLabel("Update Room Status");
        title.setFont(new Font("Tahoma", Font.PLAIN, 20));
        title.setBounds(85, 11, 206, 34);
        contentPane.add(title);

        JLabel lblGuest = new JLabel("Guest ID:");
        lblGuest.setBounds(27, 87, 90, 14);
        contentPane.add(lblGuest);

        c1 = new Choice();

        try {
            conn c = new conn();
            ResultSet rs = c.s.executeQuery("SELECT * FROM customer");
            while (rs.next()) {
                c1.add(rs.getString("number"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        c1.setBounds(160, 84, 140, 20);
        contentPane.add(c1);

        JLabel lblRoom = new JLabel("Room Number:");
        lblRoom.setBounds(27, 133, 100, 14);
        contentPane.add(lblRoom);

        txt_Room = new JTextField();
        txt_Room.setBounds(160, 130, 140, 20);
        contentPane.add(txt_Room);

        JLabel lblAvailability = new JLabel("Availability:");
        lblAvailability.setBounds(27, 187, 90, 14);
        contentPane.add(lblAvailability);

        txt_Ava = new JTextField();
        txt_Ava.setBounds(160, 184, 140, 20);
        contentPane.add(txt_Ava);

        JLabel lblClean = new JLabel("Clean Status:");
        lblClean.setBounds(27, 240, 90, 14);
        contentPane.add(lblClean);

        txt_Status = new JTextField();
        txt_Status.setBounds(160, 237, 140, 20);
        contentPane.add(txt_Status);

        // 🔍 CHECK BUTTON
        JButton btnCheck = new JButton("Check");
        btnCheck.setBounds(120, 315, 100, 25);
        btnCheck.setBackground(Color.BLACK);
        btnCheck.setForeground(Color.WHITE);
        contentPane.add(btnCheck);

        btnCheck.addActionListener(e -> {
            try {
                conn c = new conn();
                String number = c1.getSelectedItem();

                // FIX: add quotes for string
                ResultSet rs1 = c.s.executeQuery(
                    "SELECT * FROM customer WHERE number = '" + number + "'"
                );

                if (rs1.next()) {
                    txt_Room.setText(rs1.getString("room_number"));
                }

                ResultSet rs2 = c.s.executeQuery(
                    "SELECT * FROM room WHERE room_number = '" + txt_Room.getText() + "'"
                );

                if (rs2.next()) {
                    txt_Ava.setText(rs2.getString("availability"));
                    txt_Status.setText(rs2.getString("clean_status"));
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // 🔄 UPDATE BUTTON
        JButton btnUpdate = new JButton("Update");
        btnUpdate.setBounds(60, 355, 100, 25);
        btnUpdate.setBackground(Color.BLACK);
        btnUpdate.setForeground(Color.WHITE);
        contentPane.add(btnUpdate);

        btnUpdate.addActionListener(e -> {
            try {
                conn c = new conn();

                // FIX: use quotes
                String query = "UPDATE room SET clean_status='" + txt_Status.getText() +
                               "' WHERE room_number='" + txt_Room.getText() + "'";

                c.s.executeUpdate(query);

                JOptionPane.showMessageDialog(null, "Update Successful");

                new Reception().setVisible(true);
                setVisible(false);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        JButton btnBack = new JButton("Back");
        btnBack.setBounds(180, 355, 100, 25);
        btnBack.setBackground(Color.BLACK);
        btnBack.setForeground(Color.WHITE);
        contentPane.add(btnBack);

        btnBack.addActionListener(e -> {
            new Reception().setVisible(true);
            setVisible(false);
        });

        getContentPane().setBackground(Color.WHITE);
    }
}