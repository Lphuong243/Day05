package data;

import business.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDB {

    public static String lastError = "";

    static {
        initTable();
    }

    public static void initTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS \"User\" ("
                + "\"UserID\" SERIAL PRIMARY KEY, "
                + "\"Email\" VARCHAR(100), "
                + "\"FirstName\" VARCHAR(50), "
                + "\"LastName\" VARCHAR(50)"
                + ");";

        try (Connection conn = DatabaseConnection.getConnection();
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

        try (Connection connection = DatabaseConnection.getConnection();
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

        try (Connection connection = DatabaseConnection.getConnection();
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

        try (Connection connection = DatabaseConnection.getConnection();
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
