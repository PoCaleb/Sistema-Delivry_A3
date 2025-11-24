package entregas.sistema.entrega.model;

public abstract class Usuario {
    protected int id;
    protected String nome;
    protected String endereco;  
    protected String telefone;  
    protected String login;     
    protected String senha;     

    public Usuario(String nome, String endereco, String telefone, String login, String senha) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.login = login;
        this.senha = senha;
    }
    
    // --- GETTERS NECESSÁRIOS PARA O DAO ---
    
    public String getNome() { return nome; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    // ESTES ESTAVAM FALTANDO OU INCOMPLETOS:
    public String getEndereco() { return endereco; }
    public String getTelefone() { return telefone; }
    public String getLogin() { return login; }
    public String getSenha() { return senha; }
}