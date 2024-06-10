import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {
        var connection = DriverManager.getConnection("jdbc:h2:mem:hexlet_test");

        var sql = "CREATE TABLE USERS (id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                " username VARCHAR(255)," +
                " phone VARCHAR(255))";

        var statement = connection.createStatement();
        statement.execute(sql);
        statement.close();

        var sql2 = "INSERT INTO users (username, phone) VALUES ('Bob', '123456')";

        var statement2 = connection.createStatement();
        statement2.executeUpdate(sql2);
        statement2.close();

        var sql3 = "SELECT * FROM users";

        var statement3 = connection.createStatement();
        var resultSet = statement3.executeQuery(sql3);
        while (resultSet.next()) {
            System.out.println(resultSet.getString("username"));
            System.out.println(resultSet.getString("phone"));
        }
        statement3.close();

        connection.close();
    }
}
