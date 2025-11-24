package entregas.sistema.entrega.dao;

import entregas.sistema.entrega.model.Pedido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp; 
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {
    
    //  Cadastra o pedido
    public int cadastrarPedido(Pedido pedido) {
        String sql = "INSERT INTO pedido (id_cliente, id_restaurante, data_hora, descricao_pedido, valor_total, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        int pedidoId = -1;

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, pedido.getIdCliente());
            stmt.setInt(2, pedido.getIdRestaurante());
            stmt.setTimestamp(3, Timestamp.valueOf(pedido.getDataHora())); 
            stmt.setString(4, pedido.getDescricaoPedido());
            stmt.setDouble(5, pedido.getValorTotal());
            stmt.setString(6, pedido.getStatus());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    pedidoId = rs.getInt(1);
                    pedido.setId(pedidoId);
                }
            }
        } catch (SQLException e) {
            System.err.println("ERRO SQL: Cadastro de Pedido. " + e.getMessage());
        }
        return pedidoId;
    }

    // Busca todos os pedidos do CLIENTE
    public List<Pedido> buscarPedidosPorCliente(int idCliente) {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT id, id_cliente, id_restaurante, data_hora, descricao_pedido, valor_total, status " +
                     "FROM pedido WHERE id_cliente = ? ORDER BY data_hora DESC";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pedidos.add(mapResultSetToPedido(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pedidos do cliente: " + e.getMessage());
        }
        return pedidos;
    }

    // Busca pedidos do RESTAURANTE por Status
    public List<Pedido> buscarPedidosPorRestauranteEStatus(int idRestaurante, String status) {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT id, id_cliente, id_restaurante, data_hora, descricao_pedido, valor_total, status " +
                     "FROM pedido WHERE id_restaurante = ? AND status = ? ORDER BY data_hora ASC";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idRestaurante);
            stmt.setString(2, status);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pedidos.add(mapResultSetToPedido(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pedidos do restaurante: " + e.getMessage());
        }
        return pedidos;
    }
    
    // Método para atualizar o status do pedido (ENTREGUE ou CANCELADO)
    public boolean atualizarStatus(int idPedido, String novoStatus) {
        String sql = "UPDATE pedido SET status = ? WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, novoStatus);
            stmt.setInt(2, idPedido);
            
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar status do pedido " + idPedido + ": " + e.getMessage());
            return false;
        }
    }
    
    // Método auxiliar para mapear dados do ResultSet para o objeto Pedido
    private Pedido mapResultSetToPedido(ResultSet rs) throws SQLException {
        Pedido pedido = new Pedido();
        pedido.setId(rs.getInt("id"));
        
        pedido.setIdCliente(rs.getInt("id_cliente")); 
        pedido.setIdRestaurante(rs.getInt("id_restaurante")); 
        
        Timestamp timestamp = rs.getTimestamp("data_hora");
        if (timestamp != null) {
            pedido.setDataHora(timestamp.toLocalDateTime());
        }
        
        pedido.setDescricaoPedido(rs.getString("descricao_pedido"));
        pedido.setValorTotal(rs.getDouble("valor_total"));
        pedido.setStatus(rs.getString("status"));
        return pedido;
    }
}