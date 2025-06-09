package trading.crypto.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DbConfiguration {

    private final static Logger logger = LoggerFactory.getLogger(DbConfiguration.class);
    private final String dbUrl;
    private final String user;
    private final String pwd;
    private final String driverClassName;

    public DbConfiguration(@Value("${spring.datasource.url}") String dbUrl,
                           @Value("${spring.datasource.username}") String user,
                           @Value("${spring.datasource.password}") String pwd,
                           @Value("${spring.datasource.driver-class-name}") String driverClassName) {
        this.dbUrl = dbUrl;
        this.user = user;
        this.pwd = pwd;
        this.driverClassName = driverClassName;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(user);
        dataSource.setPassword(pwd);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
