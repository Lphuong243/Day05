package data;

import business.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDB {

    public static String lastError = "";

    static {
        // Load driver thủ công để đảm bảo Render nhận diện được PostgreSQL
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Lỗi load driver PostgreSQL: " + e.getMessage());
        }
        initTable();
    }

    // TẠO HÀM KẾT NỐI TRỰC TIẾP, GHI ĐÈ HOÀN TOÀN CÁC CẤU HÌNH CŨ
    private static Connection getConnection() throws SQLException {
        // Dùng External URL để đảm bảo chạy được trên cả Localhost lẫn Render
        String url = "jdbc:postgresql://dpg-datt14ou01pc73aecb50-a.oregon-postgres.render.com/email_list_db_jut9?sslmode=require";
        String user = "email_list_db_jut9_user";
        String password = "2hI1wdw0KNvIH77Bilyuv4COJ7mkXDB"; // Mật khẩu chuẩn (số 0)

        return DriverManager.getConnection(url, user, password);
    }

    public static void initTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS \"User\" ("
                + "\"UserID\" SERIAL PRIMARY KEY, "
                + "\"Email\" VARCHAR(100), "
                + "\"FirstName\" VARCHAR(50), "
                + "\"LastName\" VARCHAR(50)"
                + ");";

        // Thay DatabaseConnection.getConnection() bằng hàm nội bộ
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(createTableSQL)) {
            ps.execute();
        } catch (Exception e) {
            System.err.println(">> Tự động tạo bảng User: " + e.getMessage());
            lastError = e.getMessage();
        }
    }

    public static int insert(User user) {
        lastError = "";
        String query = "INSERT INTO \"User\" (\"Email\", \"FirstName\", \"LastName\") VALUES (?, ?, ?)";

        // Thay DatabaseConnection.getConnection() bằng hàm nội bộ
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());

            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi insert user vào PostgreSQL: " + e.getMessage());
            lastError = e.getMessage();
            e.printStackTrace();
            return 0;
        }
    }

    public static boolean emailExists(String email) {
        String query = "SELECT \"Email\" FROM \"User\" WHERE \"Email\" = ?";

        // Thay DatabaseConnection.getConnection() bằng hàm nội bộ
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Lỗi kiểm tra email: " + e.getMessage());
            lastError = e.getMessage();
            return false;
        }
    }

    public static User selectUser(String email) {
        String query = "SELECT \"Email\", \"FirstName\", \"LastName\" FROM \"User\" WHERE \"Email\" = ?";

        // Thay DatabaseConnection.getConnection() bằng hàm nội bộ
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setEmail(rs.getString("Email"));
                    user.setFirstName(rs.getString("FirstName"));
                    user.setLastName(rs.getString("LastName"));
                    return user;
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi select user: " + e.getMessage());
            lastError = e.getMessage();
        }
        return null;
    }
}