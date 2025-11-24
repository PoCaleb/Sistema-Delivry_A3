package entregas.sistema.entrega.model;

public class Prato {
    private int id;
    private int idRestaurante; 
    private String nome;
    private String descricao;
    private double valor; 

    // Construtor vazio (para uso nos DAOs)
    public Prato() {
    }

    // Construtor completo (para uso no cadastrarPrato)
    public Prato(String nome, String descricao, double valor, int idRestaurante) {
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.idRestaurante = idRestaurante;
    }
    
    // --- GETTERS E SETTERS ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(int idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}