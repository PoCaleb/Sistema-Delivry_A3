package entregas.sistema.entrega.model;

public class Cliente {
    
    // Propriedades (Herdadas de Usuario)
    private int id;
    private String nome;
    private String endereco;
    private String telefone;
    private String login; 
    private String senha;
    
    // Propriedades específicas
    private String cpf;
    private String email;

    // Construtor vazio (Necessário para DAOs no método autenticar)
    public Cliente() {
    }
    
    // CONSTRUTOR COMPLETO (Necessário para a função cadastrarCliente no SistemaMain)
    public Cliente(String nome, String endereco, String telefone, String login, String senha, String cpf, String email) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.login = login;
        this.senha = senha;
        this.cpf = cpf;
        this.email = email;
    }
    
    // --- GETTERS E SETTERS ---
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}