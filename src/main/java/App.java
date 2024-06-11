import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {
        var connection = DriverManager.getConnection("jdbc:h2:mem:hexlet_test");
        var dao = new UserDAO(connection);

        var sql = "CREATE TABLE USERS (id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                " username VARCHAR(255)," +
                " phone VARCHAR(255))";

        try (var statement = connection.createStatement()) {
            statement.execute(sql);
        }

        var user1 = new User("Bob", "1234567");
        var user2 = new User("Maria", "9876654");
        var user3 = new User("Mike", "57684753");
        dao.save(user1);
        dao.save(user2);
        dao.save(user3);

        dao.find(1).ifPresent((user -> System.out.println(user.getUsername() + " was called once")));
        dao.find(3).ifPresent((user -> System.out.println(user.getUsername() + " was here")));

        user1.setUsername("Bobby");
        dao.save(user1);
        dao.delete(3);

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
