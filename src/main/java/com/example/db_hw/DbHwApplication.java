package com.example.db_hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DbHwApplication {

    public static void main(String[] args) {
        GetConnection.ipAddress = args[0];
        GetConnection.port = args[1];
        GetConnection.pass = args[2];
        SpringApplication.run(DbHwApplication.class, args);
    }

}


