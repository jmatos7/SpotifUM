import java.io.Serializable;

public class Musica implements Serializable {
    private String nome;
    private String interprete;
    private String editora;
    private String letra;
    private String genero;
    private int duracao;
    private int contadorReproducao;

    public Musica(String nome, String interprete,String editora,String letra, String genero,int duracao){
        this.nome = nome;
        this.interprete = interprete;
        this.editora = editora;
        this.letra = letra;
        this.genero = genero;
        this.duracao = duracao;
        this.contadorReproducao = 0;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getInterprete() {
        return interprete;
    }

    public void setInterprete(String interprete) {
        this.interprete = interprete;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public int getContadorReproducao() {return contadorReproducao;}

    public String reproduzir() {
        contadorReproducao++;  
        return "Reproduzindo: " + nome +
        "\n" + letra;
    }
}