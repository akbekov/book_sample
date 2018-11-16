/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookshop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author takbekov
 */
public class Database {

    //  данные для подключения
    private final String url = "jdbc:postgresql://localhost:5432/db_test";
    private final String username = "postgres";
    private final String password = "root";

    //  функция подключения к базе данных
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.out.println("Database:connection error:" + e.getLocalizedMessage());
            return null;
        }
    }

    //  функция для добавления строк в таблицу
    public void insert(String table, String fields, String values, Statement state) {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO ").append(table).append(" (").append(fields).append(") ");
            sql.append("VALUES (").append(values).append(")");
            state.executeUpdate(String.valueOf(sql));
        } catch (Exception e) {
            System.out.println("Database:insert error:" + e.getLocalizedMessage());
            System.out.println("Database:insert error:table:" + table + ":fields:" + fields + ":values:" + values);
        }
    }

    //  функция для удаления строк из таблицы
    public void delete(String table, String condition, Statement state) {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("DELETE FROM ").append(table);
            if (condition != null) {
                sql.append(" WHERE ").append(condition);
            }
            state.executeUpdate(String.valueOf(sql));
        } catch (Exception e) {
            System.out.println("Database:delete error:" + e.getLocalizedMessage());
            System.out.println("Database:delete error:table:" + table + ":condition:" + condition);
        }
    }

    //  функция для обновления строк в таблице
    public void update(String table, String values, String condition, Statement state) {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE ").append(table).append(" SET ").append(values);
            if (condition != null) {
                sql.append(" WHERE ").append(condition);
            }
            state.executeUpdate(String.valueOf(sql));
        } catch (Exception e) {
            System.out.println("Database:update error:" + e.getLocalizedMessage());
            System.out.println("Database:update error:table:" + table + ":condition:" + condition);
        }
    }

    //  функция для выборки строк из таблицы
    public ResultSet select(String table, String field, String condition, String orderBy, Statement state) {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ").append(field);
            sql.append(" FROM ").append(table);
            if (condition != null) {
                sql.append(" WHERE ").append(condition);
            }
            if (orderBy != null) {
                sql.append(" ORDER BY ").append(orderBy);
            }
            return state.executeQuery(String.valueOf(sql));
        } catch (Exception e) {
            System.out.println("Database:select error:" + e.getLocalizedMessage());
            System.out.println("Database:select error:table:" + table + ":condition:" + condition);
            return null;
        }
    }

    //  извлечение идентификатора по определенному полю (для связанных таблиц)
    public String getIdByField(String table, String field, Statement state) {
        try {
            String id = null;
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT id FROM ").append(table);
            sql.append(" WHERE ").append(field);
            ResultSet rs = state.executeQuery(String.valueOf(sql));
            if (rs.next()) {
                id = rs.getString("id");
            }
            return id;
        } catch (Exception e) {
            System.out.println("Database:getIdByField error:" + e.getLocalizedMessage());
            System.out.println("Database:getIdByField error:table:" + table + ":field:" + field);
            return null;
        }
    }

}
