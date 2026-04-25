package hotel.management.system;

import java.sql.*;

public class conn {
    Connection c;
    Statement s;

    public conn() {
        try {
            // ✅ NEW DRIVER (important)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // ✅ Add timezone (fixes errors)
            c = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hms?useSSL=false&serverTimezone=UTC",
                "root",
                ""
            );

            s = c.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}