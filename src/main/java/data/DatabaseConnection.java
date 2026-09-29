package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Cấu hình kết nối PostgreSQL (thay đổi tên DB, user, password cho phù hợp với máy của bạn)
    private static final String URL = "jdbc:postgresql://localhost:5432/murach_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "7358243Lp**"; // Thay mật khẩu postgres của bạn ở đây

    static {
        try {
            // Nạp PostgreSQL JDBC Driver
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("PostgreSQL JDBC Driver not found!", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
