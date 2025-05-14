import java.util.ArrayList;
import java.util.List;

public class UtilizadorPremiumTop extends Utilizador {
    private List<Musica> favoritos;
    private List<Playlist> playlists;

    public UtilizadorPremiumTop(String nome, String email, String morada) {
        super(nome, email, morada);
        this.favoritos = new ArrayList<>();
        this.playlists = new ArrayList<>();
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(Playlist playlists) {
        this.playlists.add(playlists);
    }
    
    public List<Musica> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(List<Musica> favoritos) {
        this.favoritos = favoritos;
    }

}
