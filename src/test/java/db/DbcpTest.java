package db;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;

class DbcpTest {

    private static final String SQL =
            "SELECT account_number, account_type, person_name FROM account";

    private DataSource dataSource;

    @SneakyThrows
    private static DataSource getC3P0DataSource() {
        Class.forName("org.h2.Driver");
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setJdbcUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
//        cpds.setUser("root");
//        cpds.setPassword("password");

        // Optional Settings
        cpds.setInitialPoolSize(5);
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(20);
        cpds.setMaxStatements(100);

        return cpds;
    }

    @SneakyThrows
    @BeforeEach
    void setUp() {
        dataSource = getC3P0DataSource();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate(new String(Files.readAllBytes(Paths.get("./src/test/resources/db-schema.sql"))));
            connection.commit();
//            connection.rollback();
            connection.setAutoCommit(true);
        }
    }

    @Disabled("https://stackoverflow.com/questions/40234989/error-occured-while-trying-to-acquire-a-cached-preparedstatement-in-a-background")
    @Test
    @SneakyThrows
    @DisplayName("Name method works correctly")
    void Name() {
        try (val connection = dataSource.getConnection();
             val preparedStatement = connection.prepareStatement(SQL);
             val resultSet = preparedStatement.executeQuery()) {
            System.out.println("The Connection Object is of Class: " + connection.getClass());
            while (resultSet.next())
                System.out.printf("%s,%s,%s%n",
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3));
        }
    }

}
