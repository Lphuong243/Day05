package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // 1. Cấu hình khi chạy trên Localhost (máy của bạn)
    private static final String LOCAL_URL = "jdbc:postgresql://localhost:5432/murach_db";
    private static final String LOCAL_USER = "postgres";
    private static final String LOCAL_PASSWORD = "7358243Lp**";

    // 2. Cấu hình khi chạy trên Render (Host online của bạn)
    private static final String RENDER_URL = "jdbc:postgresql://dpg-datt14ou01pc73aecb50-a/email_list_db_jut9?sslmode=require";
    private static final String RENDER_USER = "email_list_db_jut9_user";
    private static final String RENDER_PASSWORD = "2hI1wdw0KNvIH77Bilyuv4COJ7mkXDB";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("PostgreSQL JDBC Driver not found!", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        boolean isRender = System.getenv("RENDER") != null 
                        || System.getenv("RENDER_SERVICE_ID") != null
                        || System.getenv("DATABASE_URL") != null;

        if (isRender) {
            return DriverManager.getConnection(RENDER_URL, RENDER_USER, RENDER_PASSWORD);
        } else {
            try {
                // Thử kết nối Localhost trước
                return DriverManager.getConnection(LOCAL_URL, LOCAL_USER, LOCAL_PASSWORD);
            } catch (SQLException localEx) {
                // Nếu không kết nối được localhost (ví dụ đang chạy trên Docker Render mà thiếu biến môi trường)
                // thì tự động chuyển sang kết nối Database Render!
                try {
                    return DriverManager.getConnection(RENDER_URL, RENDER_USER, RENDER_PASSWORD);
                } catch (SQLException renderEx) {
                    throw new SQLException("Lỗi kết nối cả Localhost và Render: " + localEx.getMessage() + " | " + renderEx.getMessage(), renderEx);
                }
            }
        }
    }
}
