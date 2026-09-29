package murach.data;

import murach.business.User;
import data.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDB {

    public static int insert(User user) {
        String query = "INSERT INTO \"User\" (\"Email\", \"FirstName\", \"LastName\") VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());

            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi UserDB.insert: " + e.getMessage());
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
            System.err.println("Lỗi emailExists: " + e.getMessage());
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
            System.err.println("Lỗi selectUser: " + e.getMessage());
        }
        return null;
    }
}
