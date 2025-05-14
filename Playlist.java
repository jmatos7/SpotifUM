import java.io.Serializable;
import java.util.*;

public class Playlist implements Serializable{
    private String nome;
    private List<Musica> musicas;

    public Playlist(String nome, List<Musica> musicas){
        this.nome = nome;
        this.musicas = musicas;
    }

    public void reproduzir() {
        if (musicas.isEmpty()) {
            System.out.println("A playlist '" + nome + "' está vazia.");
            return;
        }

        System.out.println("Reproduzindo a playlist: " + nome);
        for (Musica m : musicas) {
            m.reproduzir();
        }

        int duracaoTotal = calcularDuracaoTotal();
        System.out.println("Duração total: " + duracaoTotal + " segundos.");
    }

    public int calcularDuracaoTotal() {
        int total = 0;
        for (Musica m : musicas) {
            total += m.getDuracao();
        }
        return total;
    }

    public void listarMusicas() {
        if (musicas.isEmpty()) {
            System.out.println("A playlist '" + nome + "' está vazia.");
            return;
        }
        System.out.println("\n--- Músicas na Playlist: " + nome + " ---");
        for (int i = 0; i < musicas.size(); i++) {
            Musica m = musicas.get(i);
            System.out.println((i + 1) + ". " + m.getNome() + " (Tocada " + m.getContadorReproducao() + " vezes)");
        }
    }
  
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Musica> getMusicas() {
        return musicas;
    }

    public void setMusicas(List<Musica> musicas) {
        this.musicas = musicas;
    }

    
}
