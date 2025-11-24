package entregas.sistema.entrega.dao;

import entregas.sistema.entrega.model.Restaurante;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RestauranteDAO {
    
    // 1. Método para autenticar Restaurante
    public Restaurante autenticar(String login, String senha) {
        // SQL ajustado: removido r.tipo_cozinha
        String sql = "SELECT u.id, u.nome, u.endereco, u.telefone, r.cnpj, r.nome_empresa " + 
                     "FROM usuario u JOIN restaurante r ON u.id = r.id " +
                     "WHERE u.login = ? AND u.senha = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, login);
            stmt.setString(2, senha);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Restaurante restaurante = new Restaurante();
                    restaurante.setId(rs.getInt("id"));
                    restaurante.setNomeContato(rs.getString("nome")); 
                    restaurante.setEndereco(rs.getString("endereco"));
                    restaurante.setTelefone(rs.getString("telefone"));
                    restaurante.setCnpj(rs.getString("cnpj"));
                    restaurante.setNomeEmpresa(rs.getString("nome_empresa"));
                    // tipoCozinha removido
                    
                    return restaurante;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro na autenticação do restaurante: " + e.getMessage());
        }
        return null; 
    }
    
    /**
     * 2. Método para cadastrar Restaurante com suporte a Transação (Commit/Rollback).
     */
    public int cadastrarRestaurante(Restaurante restaurante) {
        // SQL ajustado: removido tipo_cozinha
        String sqlUsuario = "INSERT INTO usuario (nome, endereco, telefone, login, senha) VALUES (?, ?, ?, ?, ?)";
        String sqlRestaurante = "INSERT INTO restaurante (id, cnpj, nome_empresa) VALUES (?, ?, ?)";
        int usuarioId = -1;
        Connection conn = null;

        try {
            // 1. OBTÉM CONEXÃO E DESATIVA O AUTO-COMMIT PARA A TRANSAÇÃO
            conn = Conexao.getConexao();
            if (conn == null) {
                System.err.println("ERRO: Conexão com o banco de dados falhou.");
                return -1;
            }
            conn.setAutoCommit(false); 

            // --- 1. INSERÇÃO NA TABELA USUARIO ---
            try (PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
                stmtUsuario.setString(1, restaurante.getNomeContato());
                stmtUsuario.setString(2, restaurante.getEndereco());
                stmtUsuario.setString(3, restaurante.getTelefone());
                stmtUsuario.setString(4, restaurante.getLogin());
                stmtUsuario.setString(5, restaurante.getSenha());

                stmtUsuario.executeUpdate();

                // Pega o ID gerado (chave primária)
                try (ResultSet rs = stmtUsuario.getGeneratedKeys()) {
                    if (rs.next()) {
                        usuarioId = rs.getInt(1);
                    } else {
                        throw new SQLException("Falha ao obter o ID do usuário após a inserção.");
                    }
                }
            }

            // --- 2. INSERÇÃO NA TABELA RESTAURANTE ---
            if (usuarioId > 0) {
                try (PreparedStatement stmtRestaurante = conn.prepareStatement(sqlRestaurante)) {

                    stmtRestaurante.setInt(1, usuarioId); 
                    stmtRestaurante.setString(2, restaurante.getCnpj());
                    stmtRestaurante.setString(3, restaurante.getNomeEmpresa());
                    // tipoCozinha removido

                    stmtRestaurante.executeUpdate();
                    
                    // 3. SE TUDO OK, FAZ O COMMIT DE AMBAS AS OPERAÇÕES
                    conn.commit();
                    restaurante.setId(usuarioId);
                    return usuarioId; 
                    
                } 
            }

        } catch (SQLException e) {
            System.err.println("ERRO SQL CRÍTICO no Cadastro de Restaurante. Tentando Rollback...");
            System.err.println("Detalhe: " + e.getMessage());
            
            // 4. SE FALHAR, EXECUTA ROLLBACK
            if (conn != null) {
                try {
                    conn.rollback();
                    System.err.println("Rollback realizado com sucesso. Nenhuma alteração persistiu.");
                } catch (SQLException ex) {
                    System.err.println("ERRO ao executar rollback: " + ex.getMessage());
                }
            }
            return -1; 
            
        } finally {
            // GARANTE QUE A CONEXÃO SEJA FECHADA, MESMO EM CASO DE ERRO
            if (conn != null) {
                try {
                    // VOLTA O AUTO-COMMIT PARA O PADRÃO E FECHA
                    conn.setAutoCommit(true); 
                    conn.close();
                } catch (SQLException ex) {
                    System.err.println("ERRO ao fechar conexão: " + ex.getMessage());
                }
            }
        }
        return -1; 
    }

    /**
     * MÉTODO DE BUSCA POR ID (Necessário para SistemaMain)
     */
    public Restaurante buscarPorId(int id) {
        // SQL ajustado: removido u.tipo_cozinha
        String sql = "SELECT u.id, u.nome, u.endereco, u.telefone, u.login, u.senha, r.cnpj, r.nome_empresa " +
                     "FROM usuario u JOIN restaurante r ON u.id = r.id " +
                     "WHERE u.id = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Restaurante restaurante = new Restaurante();
                    restaurante.setId(rs.getInt("id"));
                    restaurante.setNomeContato(rs.getString("nome")); 
                    restaurante.setEndereco(rs.getString("endereco"));
                    restaurante.setTelefone(rs.getString("telefone"));
                    restaurante.setCnpj(rs.getString("cnpj"));
                    restaurante.setNomeEmpresa(rs.getString("nome_empresa"));
                    // tipoCozinha removido
                    restaurante.setLogin(rs.getString("login")); 
                    restaurante.setSenha(rs.getString("senha")); 
                    
                    return restaurante;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar restaurante por ID: " + e.getMessage());
        }
        return null;
    }
}