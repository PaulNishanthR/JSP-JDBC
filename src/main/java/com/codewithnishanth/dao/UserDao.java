package com.codewithnishanth.dao;

import com.codewithnishanth.db.DatabaseConnection;
import com.codewithnishanth.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private final Connection connection;

    public UserDao() {
        DatabaseConnection.createData();
        connection = DatabaseConnection.getConnection();
    }

    private String selectSQL = "SELECT id, username, password FROM auth WHERE username=? and password=?";
    private static String registerSQL = "insert into auth(username,password) values(?,?);";

    public User loginUser(String username, String password) {
        User user = null;

        try {
            PreparedStatement statement = connection.prepareStatement(selectSQL);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;


    }

    public void register(String username, String password) {
        try {
            PreparedStatement ps = connection.prepareStatement(registerSQL);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
