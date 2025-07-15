package com.example.locadoraSpring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao  {
    public static Connection getConexao() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/locadora";
        String usuario = "root";
        String senha = "12345";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException();
        }
        return DriverManager.getConnection(url, usuario, senha);
    }
}
