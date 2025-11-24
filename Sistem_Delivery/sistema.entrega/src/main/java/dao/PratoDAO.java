package entregas.sistema.entrega.dao;

import entregas.sistema.entrega.model.Prato;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PratoDAO {

    // Método de cadastro de Prato
    public boolean cadastrarPrato(Prato prato) {
        String sql = "INSERT INTO prato (id_restaurante, nome, descricao_prato, preco) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, prato.getIdRestaurante());
            stmt.setString(2, prato.getNome());
            stmt.setString(3, prato.getDescricao());
            stmt.setDouble(4, prato.getValor());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar prato: " + e.getMessage());
            return false;
        }
    }

    // listar TODOS os pratos
    public List<Prato> listarTodos() {
        List<Prato> pratos = new ArrayList<>();
        String sql = "SELECT id, id_restaurante, nome, descricao_prato, preco FROM prato ORDER BY nome"; 

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Prato prato = new Prato();
                prato.setId(rs.getInt("id"));
                prato.setIdRestaurante(rs.getInt("id_restaurante")); 
                prato.setNome(rs.getString("nome"));
                prato.setDescricao(rs.getString("descricao_prato"));
                prato.setValor(rs.getDouble("preco")); 

                pratos.add(prato);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar pratos: " + e.getMessage());
        }
        return pratos;
    }
}