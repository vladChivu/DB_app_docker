package com.example.db_hw;

import org.springframework.util.SystemPropertyUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MyDB {
    String username = "smyley";
//    String url = "jdbc:mysql://172.17.0.2:3306";
    String url = "jdbc:mysql://";
    String schemaName = "mydb2";
    String tableName = "persons";
    List<String > persons = new ArrayList();

    public MyDB(String ipAddress, String port, String pass){
        connectAndQuery(ipAddress, port, pass);
    }

//    private String askForIpAddress() {
//        String ip = "";
//        System.out.println("Please provide the ip address for the db container: ");
//        Scanner scanner = new Scanner(System.in);
//        ip = scanner.next();
//        ip.concat(":");
//        System.out.println("Please provide the port for the db container: ");
//        scanner = new Scanner(System.in);
//        ip.concat(scanner.next());
//        scanner.close();
//        return ip;
//    }

    private void connectAndQuery(String ipAddress, String port, String pass){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection conn = DriverManager.getConnection(url.concat(ipAddress).concat(":").concat(port), username, pass)) {
            if(!conn.isClosed()){
                System.out.println("DB Conn ok ");
                System.out.println("\n");
                initializeDatabase(conn, schemaName, tableName);
                String sqlSchemaCheck = "USE "+"`"+schemaName+"`";
                PreparedStatement ps = conn.prepareStatement(sqlSchemaCheck);
                ps.executeUpdate();

//                // Get the rows:
                String sql = "SELECT * FROM " + tableName;
                ps = conn.prepareStatement(sql);
                ResultSet resultSet = ps.executeQuery();
                persons.clear();
                while (resultSet.next()){
                    String firstName = resultSet.getString("name");
                    System.out.println("Name: " + firstName);
                    persons.add(firstName);
                }
            }
        } catch (Exception e){
            System.out.println("Error " + e.getMessage());
            System.out.println("\n");
        }
    }

    private void initializeDatabase(Connection conn, String schema, String table) throws Exception {
        PreparedStatement preparedStatement;

        boolean checkDB = false;
        String sqlSchemaCheck = "USE "+"`"+schema+"`";
        try {
            System.out.println("Checking for schema");
            System.out.println("...................");
            preparedStatement = conn.prepareStatement(sqlSchemaCheck);
            preparedStatement.executeUpdate();
            System.out.println("schema already exists.");
            System.out.println("\n");
        } catch (Exception e) {
            System.out.println("Error found: " + e);
            checkDB = true;
        }
        if (checkDB) {
            System.out.println("so, no schema found.");
            System.out.println("creating schema.");
            System.out.println("...................");
            String sqlSchemaCreate = "CREATE SCHEMA " + "`"+schema+"`" + "DEFAULT CHARACTER SET utf8 ;\n";
            preparedStatement = conn.prepareStatement(sqlSchemaCreate);
            preparedStatement.executeUpdate();
            System.out.println("schema created.");
            System.out.println("\n");
        }

        boolean checkTB = false;
        try {
            System.out.println("Checking for table");
            System.out.println("...................");
            String sqlTableCreate = "CREATE TABLE " +'`'+schema+'`'+'.'+'`'+table+'`'+ "(\n" +
                    "`id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "`name` VARCHAR(45) NOT NULL,\n" +
                    "PRIMARY KEY (`id`))";
            preparedStatement = conn.prepareStatement(sqlTableCreate);
            preparedStatement.executeUpdate();
            System.out.println("table was created");
            System.out.println("\n");
        } catch (Exception e) {
            System.out.println("Error found: " + e);
            checkTB = true;
        }
        if (checkTB) {
            System.out.println("\n");
        }

        boolean checkDT = false;
        try {
            System.out.println("Trying to add some data");
            System.out.println("..........................");
            String sqlInsertDataOne = "INSERT INTO " +'`'+schema+'`'+'.'+'`'+table+'`'+ "(name) VALUES ('Ana')";
            preparedStatement = conn.prepareStatement(sqlInsertDataOne);
            preparedStatement.executeUpdate();
            System.out.println("1 row was inserted");
            System.out.println("\n");
        } catch (Exception e) {
            System.out.println(e);
            checkDT = true;
        }
        if (checkDT) {
            System.out.println("error adding data");
            System.out.println("\n");
        }

        checkDT = false;
        try {
            System.out.println("Trying to add some data");
            System.out.println("..........................");
            String sqlInsertDataOne = "INSERT INTO " +'`'+schema+'`'+'.'+'`'+table+'`'+ "(name) VALUES ('Kent')";
            preparedStatement = conn.prepareStatement(sqlInsertDataOne);
            preparedStatement.executeUpdate();
            System.out.println("1 row was inserted");
            System.out.println("\n");
        } catch (Exception e) {
            System.out.println(e);
            checkDT = true;
        }
        if (checkDT) {
            System.out.println("error adding data");
            System.out.println("\n");
        }
    }

        // 1. make sure there exists a schema, named mydb. If not, create one
        // 2. make sure there exists a table, named persons. If not, create one:
        //    Primary key: idpersons INT AUTO_INCREMENT
        //    Column: name VARCHAR(45)
        // 3. If there was no table named persons, then insert two rows into the new table: "Anna" and "Bent"

    public List<String> getPersons() {
        return persons;
    }

}
