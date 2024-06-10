import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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

        try (var statement = connection.createStatement()) {
            statement.executeUpdate(sql2);
        }

        var sql3 = "INSERT INTO users (username, phone) VALUES (?, ?)";
        try (var preparedStatement = connection.prepareStatement(sql3)) {
            preparedStatement.setString(1, "Sarah");
            preparedStatement.setString(2, "3456789");
            preparedStatement.executeUpdate();

            preparedStatement.setString(1, "Mike");
            preparedStatement.setString(2, "987654432");
            preparedStatement.executeUpdate();
        }

        var sql4 = "INSERT INTO users (username, phone) VALUES (?, ?)";

        try (var preparedStatement = connection.prepareStatement(sql4, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, "John");
            preparedStatement.setString(2, "6789012345");
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                System.out.println(generatedKeys.getLong(1));
            } else {
                throw new SQLException("DB have not returned an id after saving the entity");
            }
        }

        var sql5 = "SELECT * FROM users";

        try (var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(sql5);
            while (resultSet.next()) {
                System.out.println(resultSet.getString("username"));
                System.out.println(resultSet.getString("phone"));
            }
        }

        connection.close();
    }
}
