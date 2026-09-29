package data;

import business.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDB {

    /**
     * Thêm mới một User vào bảng "User" trong PostgreSQL sử dụng PreparedStatement.
     * Trả về số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại).
     */
    public static int insert(User user) {
        String query = "INSERT INTO \"User\" (\"Email\", \"FirstName\", \"LastName\") VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());

            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi insert user vào PostgreSQL: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Kiểm tra email đã tồn tại trong PostgreSQL chưa
     */
    public static boolean emailExists(String email) {
        String query = "SELECT \"Email\" FROM \"User\" WHERE \"Email\" = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Lỗi kiểm tra email trong PostgreSQL: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Lấy thông tin User theo Email
     */
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
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Cập nhật thông tin User theo Email
     */
    public static int update(User user) {
        String query = "UPDATE \"User\" SET \"FirstName\" = ?, \"LastName\" = ? WHERE \"Email\" = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());

            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi update user: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Xóa User theo Email
     */
    public static int delete(User user) {
        String query = "DELETE FROM \"User\" WHERE \"Email\" = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, user.getEmail());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi delete user: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }
}
