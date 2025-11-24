package entregas.sistema.entrega.model;

public class Restaurante {

    private int id;
    private String nomeContato;
    private String endereco;
    private String telefone;
    private String login;
    private String senha;
    private String nomeEmpresa;
    private String cnpj;
    // Campo tipoCozinha removido

    // --- Construtor Vazio ---
    public Restaurante() {
    }

    // Construtor COMPLETO (7 Parâmetros)
    public Restaurante(String nomeContato, String endereco, String telefone, String login, String senha, String nomeEmpresa, String cnpj) {
        this.nomeContato = nomeContato;
        this.endereco = endereco;
        this.telefone = telefone;
        this.login = login;
        this.senha = senha;
        this.nomeEmpresa = nomeEmpresa;
        this.cnpj = cnpj;
    }

    // --- Getters e Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeContato() {
        return nomeContato;
    }

    public void setNomeContato(String nomeContato) {
        this.nomeContato = nomeContato;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
    // Getter e Setter de tipoCozinha removidos
}