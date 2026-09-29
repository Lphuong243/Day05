package business;

import data.SqlDao;

public class SqlService {
    private final SqlDao sqlDao;

    public SqlService() {
        this.sqlDao = new SqlDao();
    }

    public QueryResult processSql(String sqlStatement) {
        if (sqlStatement == null || sqlStatement.trim().isEmpty()) {
            QueryResult res = new QueryResult();
            res.setSuccess(false);
            res.setMessage("SQL statement cannot be empty.");
            return res;
        }

        String sql = sqlStatement.trim();

        // 1. Tự động bọc bảng "User" nếu người dùng gõ User chưa có nháy kép:
        sql = sql.replaceAll("(?i)\\bFROM\\s+User\\b(?!\")", "FROM \"User\"")
                 .replaceAll("(?i)\\bINTO\\s+User\\b(?!\")", "INTO \"User\"")
                 .replaceAll("(?i)\\bUPDATE\\s+User\\b(?!\")", "UPDATE \"User\"")
                 .replaceAll("(?i)\\bTABLE\\s+User\\b(?!\")", "TABLE \"User\"");

        // 2. Tự động bọc nháy kép cho các cột trong phần liệt kê cột của INSERT INTO: (Email, FirstName, LastName, UserID)
        int valuesIndex = sql.toUpperCase().indexOf("VALUES");
        if (valuesIndex != -1) {
            String beforeValues = sql.substring(0, valuesIndex);
            String afterValues = sql.substring(valuesIndex);

            beforeValues = beforeValues.replaceAll("(?<!\")(?i)\\bUserID\\b(?!\")", "\"UserID\"")
                                       .replaceAll("(?<!\")(?i)\\bEmail\\b(?!\")", "\"Email\"")
                                       .replaceAll("(?<!\")(?i)\\bFirstName\\b(?!\")", "\"FirstName\"")
                                       .replaceAll("(?<!\")(?i)\\bLastName\\b(?!\")", "\"LastName\"");
            sql = beforeValues + afterValues;
        }

        // 3. Tự động bọc nháy kép cho các cột trong mệnh đề WHERE (ví dụ khi DELETE hoặc UPDATE):
        int whereIndex = sql.toUpperCase().indexOf("WHERE");
        if (whereIndex != -1) {
            String beforeWhere = sql.substring(0, whereIndex);
            String afterWhere = sql.substring(whereIndex);

            afterWhere = afterWhere.replaceAll("(?<!\")(?i)\\bUserID\\b(?!\")", "\"UserID\"")
                                   .replaceAll("(?<!\")(?i)\\bEmail\\b(?!\")", "\"Email\"")
                                   .replaceAll("(?<!\")(?i)\\bFirstName\\b(?!\")", "\"FirstName\"")
                                   .replaceAll("(?<!\")(?i)\\bLastName\\b(?!\")", "\"LastName\"");
            sql = beforeWhere + afterWhere;
        }

        // 4. Chuẩn hóa chống trùng lặp dấu ngoặc kép (nếu người dùng đã gõ sẵn nháy kép ""):
        sql = sql.replaceAll("\"+", "\"");

        return sqlDao.executeSql(sql);
    }
}
