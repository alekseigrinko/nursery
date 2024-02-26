package service;

import java.sql.*;
import java.util.Arrays;

public class ConnectionService {
    private String localhost;
    private String tableName;
    private String url;
    private String username;
    private String password;

    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    public ConnectionService() {
        this.localhost = "jdbc:mysql://localhost:3306"; //указать свой локалхост
        this.tableName = "friends_person"; //указать желаемое название таблицы
        this.url = localhost + "/" + tableName;
        this.username = "root";
        this.password = "password";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Ошибка загрузки драйвера БД:" + e.getMessage());
        }
    }

    public String getInfo (String command, int maxColumn) {
        StringBuilder sb = new StringBuilder();
        try {
            con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(command);
            while (rs.next()) {
                for (int i = 1; i <= maxColumn; i++) {
                    if (maxColumn == i) {
                        sb.append(rs.getString(i));
                    } else {
                        sb.append(rs.getString(i) + " :: ");
                    }
                }
                if (!rs.isLast()) {
                    sb.append("\n");
                }
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Ошибка команды: " + e.getMessage());
        }
        return sb.toString();
    }

    public void putInfo (String command) {
        try {
            con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
            stmt.executeUpdate(command);
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Arrays.stream(e.getStackTrace()).toList().stream().map(StackTraceElement::toString).forEach(System.out::println);
        }
    }

    public void workInBD (String command) {
        try {
            con = DriverManager.getConnection(localhost, username, password);
            stmt = con.createStatement();
            stmt.executeUpdate(command);
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Arrays.stream(e.getStackTrace()).toList().stream().map(StackTraceElement::toString).forEach(System.out::println);
        }
    }
}
