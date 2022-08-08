package ai.craftworks.taskmanager;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"ai.craftworks.controller", "ai.craftworks.service"})
@PropertySource("classpath:query.properties")
public class TaskManagerApplication {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${postgre.driver.classname}")
    private String postgreDriverClassName;

    public static void main(String[] args) {
        SpringApplication.run(TaskManagerApplication.class, args);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(postgresDataSource());
    }

    @Bean
    public DataSource postgresDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(postgreDriverClassName);
        dataSource.setUrl(datasourceUrl);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);

        return dataSource;
    }
}
