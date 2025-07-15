package com.example.locadoraSpring;

import java.sql.Connection;

public class TesteConexao {
    public static void main(String[] args) {
        try {
            Connection con = Conexao.getConexao();
            System.out.println("✅ Conectado com sucesso!");
            con.close();
        } catch (Exception e) {
            System.out.println("❌ Erro na conexão:");
            e.printStackTrace();
        }
    }
}
