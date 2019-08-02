package ca.prsnl.spellbook;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@ComponentScan(value = "ca.prsnl.spellbook")
public class AppConfig {

    @Bean
    public static DataSource createDataSource() throws SQLException {
        String url = "jdbc:postgresql://localhost/spellbook";
        String username = System.getenv("PGUSER");
        String password = System.getenv("PGPASSWORD");
        Driver driver = DriverManager.getDriver(url);
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource(driver, url, username, password);
        return dataSource;
    }
}
