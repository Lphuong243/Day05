package data;

import business.QueryResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlDao {

    /**
     * Thực thi câu lệnh SQL sử dụng PreparedStatement.
     * Hỗ trợ cả câu lệnh truy vấn (SELECT) và câu lệnh cập nhật (INSERT, UPDATE, DELETE, DDL).
     */
    public QueryResult executeSql(String sqlStatement) {
        QueryResult result = new QueryResult();

        if (sqlStatement == null || sqlStatement.trim().isEmpty()) {
            result.setSuccess(false);
            result.setMessage("Please enter an SQL statement.");
            return result;
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlStatement.trim())) {

            // ps.execute() trả về true nếu câu lệnh trả về ResultSet (SELECT)
            // trả về false nếu là số dòng ảnh hưởng (INSERT, UPDATE, DELETE)
            boolean isResultSet = ps.execute();

            if (isResultSet) {
                result.setQuery(true);
                try (ResultSet rs = ps.getResultSet()) {
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    // Lấy danh sách tên cột
                    List<String> columns = new ArrayList<>();
                    for (int i = 1; i <= columnCount; i++) {
                        columns.add(metaData.getColumnName(i));
                    }
                    result.setColumnNames(columns);

                    // Lấy dữ liệu từng dòng
                    List<List<String>> rows = new ArrayList<>();
                    while (rs.next()) {
                        List<String> row = new ArrayList<>();
                        for (int i = 1; i <= columnCount; i++) {
                            row.add(rs.getString(i));
                        }
                        rows.add(row);
                    }
                    result.setRowData(rows);
                }
            } else {
                result.setQuery(false);
                int updateCount = ps.getUpdateCount();
                result.setAffectedRows(updateCount);
            }
            result.setSuccess(true);

        } catch (SQLException e) {
            result.setSuccess(false);
            result.setMessage("Error executing the SQL statement: " + e.getMessage());
        }

        return result;
    }
}
