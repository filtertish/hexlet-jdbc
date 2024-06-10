import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {
        var connection = DriverManager.getConnection("jdbc:h2:mem:hexlet_test");

        var sql = "CREATE TABLE USERS (id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                " username VARCHAR(255)," +
                " phone VARCHAR(255))";

        try (var statement = connection.createStatement()) {
            statement.execute(sql);
        }

        var sql2 = "INSERT INTO users (username, phone) VALUES ('Bob', '123456')";

        try (var statement2 = connection.createStatement()) {
            statement2.executeUpdate(sql2);
        }

        var sql3 = "SELECT * FROM users";

        try (var statement3 = connection.createStatement()) {
            var resultSet = statement3.executeQuery(sql3);
            while (resultSet.next()) {
                System.out.println(resultSet.getString("username"));
                System.out.println(resultSet.getString("phone"));
            }
        }

        connection.close();
    }
}
