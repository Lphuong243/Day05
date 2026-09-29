package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QueryResult implements Serializable {
    private boolean isQuery;            // true nếu là câu lệnh SELECT
    private boolean success;            // true nếu thực thi thành công không lỗi
    private int affectedRows;           // Số dòng bị ảnh hưởng (đối với INSERT/UPDATE/DELETE)
    private List<String> columnNames;   // Danh sách tên cột
    private List<List<String>> rowData; // Danh sách dữ liệu các dòng
    private String message;             // Thông báo kết quả hoặc lỗi

    public QueryResult() {
        this.columnNames = new ArrayList<>();
        this.rowData = new ArrayList<>();
        this.success = true;
    }

    public boolean isQuery() {
        return isQuery;
    }

    public void setQuery(boolean query) {
        isQuery = query;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getAffectedRows() {
        return affectedRows;
    }

    public void setAffectedRows(int affectedRows) {
        this.affectedRows = affectedRows;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public List<List<String>> getRowData() {
        return rowData;
    }

    public void setRowData(List<List<String>> rowData) {
        this.rowData = rowData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Chuyển đổi dữ liệu kết quả thành bảng HTML5 đúng như giao diện mẫu trong sách Murach
     */
    public String toHtml() {
        if (!success) {
            return "<p class=\"error-msg\">" + message + "</p>";
        }

        if (isQuery) {
            StringBuilder sb = new StringBuilder();
            sb.append("<table class=\"result-table\">\n");
            
            // Header cột
            sb.append("  <thead>\n    <tr>\n");
            for (String col : columnNames) {
                sb.append("      <th>").append(escapeHtml(col)).append("</th>\n");
            }
            sb.append("    </tr>\n  </thead>\n");

            // Các dòng dữ liệu
            sb.append("  <tbody>\n");
            for (List<String> row : rowData) {
                sb.append("    <tr>\n");
                for (String cell : row) {
                    sb.append("      <td>").append(cell == null ? "" : escapeHtml(cell)).append("</td>\n");
                }
                sb.append("    </tr>\n");
            }
            sb.append("  </tbody>\n");
            sb.append("</table>");
            return sb.toString();
        } else {
            return "<p class=\"success-msg\">The statement executed successfully.<br>" 
                 + affectedRows + " row(s) affected.</p>";
        }
    }

    private String escapeHtml(String input) {
        if (input == null) return "";
        return input.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    }
}
