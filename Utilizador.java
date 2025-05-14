import java.io.Serializable;

public class Utilizador implements Serializable {
    
    private String nome;
    private String email;
    private String morada;
    private int pontos;

    public Utilizador(String nome, String email,String morada){
        this.nome = nome;
        this.email = email;
        this.morada = morada;
        this.pontos = 0;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos += pontos;
    }

    public String perfil() {
        return "\nPerfil do Utilizador:" +
        "\nNome: " + nome +
        "\nEmail: " + email +
        "\nMorada: " + morada +
        "\nPontos: " + pontos;
    }

    public Utilizador(Utilizador outro) {
        this.nome = outro.getNome();
        this.email = outro.getEmail();
        this.morada = outro.getMorada();
        this.pontos = outro.getPontos();
    }
    

}
