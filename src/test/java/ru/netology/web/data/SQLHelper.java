package ru.netology.web.data;

import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static QueryRunner runner = new QueryRunner();

    public SQLHelper() {
    }

    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    public static DataHelper.AuthCode getVerificationCode() {
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        try (var conn = getConn()) {
            return runner.query(conn, codeSQL, new BeanHandler<>(DataHelper.AuthCode.class));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static int getCardBalance(DataHelper.CardInfo number) {
        var codeSQL = "SELECT  balance_in_kopecks FROM cards WHERE number='" + number.getCardNumber() + "'";
        try (var conn = getConn()) {
            return runner.query(conn, codeSQL, new ScalarHandler<Integer>());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return 0;
    }


    @SneakyThrows
    public static void cleanDatabase() {
        var connection = getConn();
        runner.execute(connection, "DELETE FROM auth_codes");
        runner.execute(connection, "DELETE FROM card_transactions");
        runner.execute(connection, "DELETE FROM cards");
        runner.execute(connection, "DELETE FROM users");

    }

}
