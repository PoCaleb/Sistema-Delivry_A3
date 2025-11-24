package entregas.sistema.entrega.dao;

import entregas.sistema.entrega.model.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

public class ClienteDAO {
    
    // Método para autenticar Cliente 
    public Cliente autenticar(String login, String senha) {
        String sql = "SELECT u.id, u.nome, u.endereco, u.telefone, c.cpf, c.email " +
                     "FROM usuario u JOIN cliente c ON u.id = c.id " +
                     "WHERE u.login = ? AND u.senha = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, login);
            stmt.setString(2, senha);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getInt("id"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setEndereco(rs.getString("endereco"));
                    cliente.setTelefone(rs.getString("telefone"));
                    cliente.setCpf(rs.getString("cpf"));
                    cliente.setEmail(rs.getString("email"));
                    
                    return cliente;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro na autenticação do cliente: " + e.getMessage());
        }
        return null; 
    }
    
    // Método para cadastrar Cliente
    public int cadastrarCliente(Cliente cliente) {
        String sqlUsuario = "INSERT INTO usuario (nome, endereco, telefone, login, senha) VALUES (?, ?, ?, ?, ?)";
        int usuarioId = -1;

        // 1.  tabela USUARIO
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEndereco());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getLogin());
            stmt.setString(5, cliente.getSenha());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    usuarioId = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("ERRO SQL: Cadastro de Usuário (Cliente). Login já existe ou erro de conexão. " + e.getMessage());
            return -1; 
        }

        // 2. Inserção na tabela CLIENTE
        if (usuarioId > 0) {
            String sqlCliente = "INSERT INTO cliente (id, cpf, email) VALUES (?, ?, ?)";
            try (Connection conn = Conexao.getConexao();
                 PreparedStatement stmt = conn.prepareStatement(sqlCliente)) {

                stmt.setInt(1, usuarioId); 
                stmt.setString(2, cliente.getCpf());
                stmt.setString(3, cliente.getEmail());

                stmt.executeUpdate();
                cliente.setId(usuarioId);
                return usuarioId; 
                
            } catch (SQLException e) {
                System.err.println("ERRO SQL: Cadastro de Cliente. CPF já existe ou erro interno. " + e.getMessage());
            }
        }
        return -1; 
    }

    //Busca apenas o nome do cliente pelo ID
    public String buscarNomeClientePorId(int idCliente) {
        String sql = "SELECT nome FROM usuario WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idCliente);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nome");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar nome do cliente: " + e.getMessage());
        }
        return "Cliente Desconhecido"; 
    }

    /**
     * MÉTODO ADICIONADO: Busca um cliente completo pelo seu ID.
     */
    public Cliente buscarPorId(int id) {
        String sql = "SELECT u.id, u.nome, u.endereco, u.telefone, c.cpf, c.email, u.login, u.senha " +
                     "FROM usuario u JOIN cliente c ON u.id = c.id " +
                     "WHERE u.id = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getInt("id"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setEndereco(rs.getString("endereco"));
                    cliente.setTelefone(rs.getString("telefone"));
                    cliente.setCpf(rs.getString("cpf"));
                    cliente.setEmail(rs.getString("email"));
                    cliente.setLogin(rs.getString("login")); 
                    cliente.setSenha(rs.getString("senha")); 

                    return cliente;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente por ID: " + e.getMessage());
        }
        return null;
    }
}