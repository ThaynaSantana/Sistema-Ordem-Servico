
package br.com.infox.dal;

import java.sql.*;
public class ModuloConexao {
    public static Connection conector(){
        Connection conexao = null;
        // declarando o driver
        String driver = "com.mysql.cj.jdbc.Driver";
        // armazenando as info do banco de dados
        String url = "jdbc:mysql://localhost:3306/dbinfox?characterEncoding=utf-8";
        String user = "dba";
        String password = "@123456@";
        // fazendo conexao com banco de dados
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            // linha de apoio para identificar o error
            System.out.println(e);
            return null;
        }
        
    }
}
