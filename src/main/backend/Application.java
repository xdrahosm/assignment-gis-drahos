package main.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


@SpringBootApplication
@ComponentScan("main.backend.classes")
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
