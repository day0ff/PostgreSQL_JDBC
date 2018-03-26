package by.intexsoft.postgresql;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static by.intexsoft.postgresql.FileReader.readFile;

public class JDBCExample {

    public static final String URL = "jdbc:postgresql://127.0.0.1:5432/";
    public static String database = null;//postgres
    public static final String USER = "postgres";
    public static final String PASSWORD = "postgres";
    public static Connection connection = null;
    public static Statement statement = null;

    public static void connectDatabase(String database) {
        try {
            disconnectDatabase();
            connection = DriverManager.getConnection(
                    URL + database, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Соединение не удалось!");
            e.printStackTrace();
            return;
        }
        if (connection != null) {
            System.out.println("Соединение с базой данных \"" + database + "\" произошло успешно.");
        } else {
            System.out.println("Не удалось соединиться с базой данных \"" + database + "\" !");
        }
    }
    public static void disconnectDatabase() {
        try {
            if (connection != null) {
                System.out.println("Соединение с базой данных разорвано.");
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Не удалось разорвать соединение!");
            e.printStackTrace();
        }
    }
    /**
     *  Создание базы данных db_music
     */
    public static void createDatabase(String database){
//        query = "CREATE DATABASE IF NOT EXISTS db_music";
        String query = "CREATE DATABASE " + database + ";";
        try {
            connectDatabase("postgres");    // соединение с базой данных postgres
            statement = connection.createStatement();
            boolean value = statement.execute(query);
            System.out.println("База данных " + database + " создана. ");
        } catch (SQLException sqle) {
            System.out.println("База данных " + database + " не создана.");
        } catch (Exception sqle) {
        }
    }
    /**
     *  Удаление базы данных db_music
     */
    public static void dropDatabase(String database) throws SQLException {
//       query = "DROP DATABASE IF EXISTS db_example";
        String query = "DROP DATABASE " + database +";";
        try {
            connectDatabase("postgres");    // соединение с базой данных postgres
            statement = connection.createStatement();
            boolean value = statement.execute(query);
            System.out.println("База данных \"music\" удалена.");
        } catch (SQLException sqle) {
            System.out.println("Базу данных \"music\" не удалось удалить. ");
        } catch (Exception sqle) {
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
                System.out.println("Соединение с базой данных \"db_music\" завершено");
            }
        }
    }
    public static void query(String query) {
        try {
            statement = connection.createStatement();
            boolean value = statement.execute(query);
            System.out.println("Запрос в \"" + database + "\" произведён успешно.");
        } catch (SQLException sqle) {
            System.out.println("Запрос в \"" + database + "\" неудался.");
        } catch (Exception sqle) {
            System.out.println("Ошибка уровня Exception:");
            System.out.println(sqle);
        }
    }

    public static void query(String query, String message) {
        try {
            statement = connection.createStatement();
            boolean value = statement.execute(query);
            System.out.println("Запрос \"" + message + "\" в \"" + database + "\" произведён успешно.");
        } catch (SQLException sqle) {
            System.out.println("Запрос \"" + message + "\" в \"" + database + "\" неудался.");
        } catch (Exception sqle) {
            System.out.println("Ошибка уровня Exception:");
            System.out.println(sqle);
        }
    }

    public static void main(String[] argv) throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Отсутствует PostgreSQL JDBC драйвер?");
            e.printStackTrace();
            return;
        }
        System.out.println("PostgreSQL JDBC драйвер найден!");

        database = "music";
        dropDatabase(database);         // удаление базы данных
        createDatabase(database);       // создание базы данных
        connectDatabase(database);      // соединение с базой данных db_music

        String query = null;
        query = readFile("/home/day0ff/IdeaProjects/music.sql");
        query(query,"CREATE TABLES");
        query = readFile("/home/day0ff/IdeaProjectsCources/Music/sql/insert.sql");
        query(query,"INSERT TABLES");

        disconnectDatabase();           // разрыв соединениея с базой данных
    }

}
