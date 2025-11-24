package entregas.sistema.entrega.model;

import java.time.LocalDateTime;

public class Pedido {
    private int id;
    private int idCliente;
    private int idRestaurante;
    private String descricaoPedido;
    private double valorTotal;
    private String status; // EM ANDAMENTO, ENTREGUE, CANCELADO
    private LocalDateTime dataHora; 

    // Construtor vazio
    public Pedido() {
        this.dataHora = LocalDateTime.now(); 
    }

    // --- GETTERS E SETTERS ---
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    
    public int getIdRestaurante() { return idRestaurante; }
    public void setIdRestaurante(int idRestaurante) { this.idRestaurante = idRestaurante; }
    
    public String getDescricaoPedido() { return descricaoPedido; }
    public void setDescricaoPedido(String descricaoPedido) { this.descricaoPedido = descricaoPedido; }
    
    public double getValorTotal() { return valorTotal; }
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
}