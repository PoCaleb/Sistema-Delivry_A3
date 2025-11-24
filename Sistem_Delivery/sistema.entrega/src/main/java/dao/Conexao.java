package entregas.sistema.entrega.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_entregas"; 
    private static final String USER = "SEU_USUARIO_AQUI"; 
    
    
    private static final String PASS = "SUA_SENHA_AQUI";     

    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}