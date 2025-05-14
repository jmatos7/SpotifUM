import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class MainController implements Serializable {
    private HashMap<String, Utilizador> utilizadores;
    private List<Musica> musicas;
    private Utilizador utilizadorAtual;
    private List<Playlist> playlits;

    public MainController() {
        this.utilizadores = new HashMap<>();
        this.musicas = new ArrayList<>();
        this.playlits = new ArrayList<>(); 
    }

    public void gravarEstado() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("info"))) {
            oos.writeObject(utilizadores);
            oos.writeObject(musicas);
            oos.writeObject(playlits);
        } catch (IOException e) {
            System.out.println("Erro ao gravar o estado: " + e.getMessage());
        }
    }

    public void carregarEstado(){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("info"))) {
            utilizadores = (HashMap<String, Utilizador>) ois.readObject();
            musicas = (List<Musica>) ois.readObject();
            playlits = (List<Playlist>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar o estado: " + e.getMessage());
        }
    }

    public String realizarLogin(String email) throws UtilizadorNaoExistenteException {
        if (utilizadores.containsKey(email)) {
            this.utilizadorAtual = utilizadores.get(email);
            return "Login efetuado com sucesso: " + utilizadorAtual.getEmail();
        }
        throw new UtilizadorNaoExistenteException("Utilizador com o email " + email + " não existe.");
    }

    public String adicionarUtilizador(String email, String nome, String morada) throws UtilizadorExistenteException {
        if (utilizadores.containsKey(email)) {
            throw new UtilizadorExistenteException("Utilizador com o email '" + email + "' já foi criado.");
        }
        Utilizador novoUtilizador = new Utilizador(email, nome, morada);
        utilizadores.put(email, novoUtilizador);
        this.utilizadorAtual = novoUtilizador;
        return "Utilizador criado: " + nome + " (" + email + ")";
    }

    public void adicionarMusica(String nome, String interprete, String editora, String genero, int duracao, String letra) throws MusicaExistenteException {
        boolean musicaExistente = musicas.stream()
                .anyMatch(musica -> musica.getNome().equals(nome));
        if (musicaExistente) {
            throw new MusicaExistenteException("A musica com o nome '" + nome + "' já existe.");
        }
        Musica novaMusica = new Musica(nome, interprete, editora, letra, genero, duracao);
        musicas.add(novaMusica);
    }

    public List<Musica> getMusicas(){
        return musicas;
    }

    public String ouvirMusica(List<Musica> music) {
        if (utilizadorAtual == null) {
            return "Nenhum utilizador logado. Faça login primeiro.";
        }
        
        if (musicas.isEmpty()) {
            return "Nenhuma música disponível para ouvir.";
            
        }

        Random random = new Random();
        int length = musicas.size();
        int numero = random.nextInt(length);
        Musica musica = music.get(numero);
        String repro = musica.reproduzir();
        if (utilizadorAtual instanceof UtilizadorPremiumBase){
            utilizadorAtual.setPontos(10);
        }
        else if (utilizadorAtual instanceof UtilizadorPremiumTop){
            utilizadorAtual.setPontos((int) (utilizadorAtual.getPontos()*0.025));
        }
        else{
            utilizadorAtual.setPontos(5);
        }
        return repro;
    }

    public String ouvirMusicaDePlaylist(Playlist playlist) {
        if (utilizadorAtual == null) {
            return "Nenhum utilizador logado. Faça login primeiro.";
        }

        List<Musica> musicasDaPlaylist = playlist.getMusicas();

        if (musicasDaPlaylist.isEmpty()) {
            return "Esta playlist está vazia.";
        }

        Random random = new Random();
        Musica musica = musicasDaPlaylist.get(random.nextInt(musicasDaPlaylist.size()));
        String resultado = musica.reproduzir();

        if (utilizadorAtual instanceof UtilizadorPremiumBase){
            utilizadorAtual.setPontos(10);
        }
        else if (utilizadorAtual instanceof UtilizadorPremiumTop){
            utilizadorAtual.setPontos((int) (utilizadorAtual.getPontos()*0.025));
        }
        else{
            utilizadorAtual.setPontos(5);
        }

        return resultado;
    }


    public String mostrarPerfil() {
        if (utilizadorAtual != null) {
            String perfil = utilizadorAtual.perfil();
            if(utilizadorAtual instanceof UtilizadorPremiumBase){
                return perfil +"/nPlano: Premium Base ";
            }
            else if(utilizadorAtual instanceof UtilizadorPremiumTop){
                return perfil +"\nPlano: Premium Top";
            }
            else{
                return perfil +"\nPlano: Gratis";
            }
        } else {
            return "Nenhum utilizador autenticado.";
        }
    }
    

    public String listarMusicas() {
        if (musicas.isEmpty()) {
            return "Nenhuma música disponível.";
        }
    
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Musica musica : musicas) {
            sb.append(i).append(": ").append(musica.getNome()).append("\n");
            i++;
        }
        return sb.toString();
    }

    public int getNumeroMusicas(){
        return musicas.size();
    }

    public Utilizador getUtilizadorAtual() {
        return utilizadorAtual;
    }    

    public String tornarPremium(String tipo) {
        if (utilizadorAtual == null) {
            return "Nenhum utilizador autenticado.";
        }
    
        switch (tipo.toLowerCase()) {
            case "base":
                if (utilizadorAtual instanceof UtilizadorPremiumBase) {
                    return "Já és Premium Base!";
                }
                utilizadorAtual = new UtilizadorPremiumBase(
                    utilizadorAtual.getNome(),
                    utilizadorAtual.getEmail(),
                    utilizadorAtual.getMorada()
                );
                break;
    
            case "top":
                if (utilizadorAtual instanceof UtilizadorPremiumTop) {
                    return "Já és Premium Top!";
                }
                utilizadorAtual.setPontos(100);
                utilizadorAtual = new UtilizadorPremiumTop(
                    utilizadorAtual.getNome(),
                    utilizadorAtual.getEmail(),
                    utilizadorAtual.getMorada()
                );
                break;
    
            default:
                return "Tipo de premium inválido.";
        }
    
        utilizadorAtual.setPontos(utilizadores.get(utilizadorAtual.getEmail()).getPontos());
        utilizadores.put(utilizadorAtual.getEmail(), utilizadorAtual);
    
        return "Agora és utilizador Premium " + tipo.substring(0, 1).toUpperCase() + tipo.substring(1) + "!";
    }
    

    public String cancelarPremium() {
        Utilizador atual = this.utilizadorAtual;
        if (atual instanceof UtilizadorPremiumBase || atual instanceof UtilizadorPremiumTop) {
            Utilizador novo = new Utilizador(atual); // Construtor que copia os dados
            this.utilizadorAtual = novo;
            this.utilizadores.put(novo.getEmail(), novo);
            return "Subscrição premium cancelada com sucesso!";
        } else {
            return "O utilizador não tem uma subscrição premium ativa.";
        }
    }
    
    
    
    public String musicaMaisOuvida() {
        if (musicas == null || musicas.isEmpty()) {
            return "Nenhuma música disponível.";
            
        }
    
        Musica musicaMaisOuvida = null;
        int maxReproducoes = -1;
    
        for (Musica musica : musicas) {
            if (musica != null && musica.getContadorReproducao() > maxReproducoes) {
                maxReproducoes = musica.getContadorReproducao();
                musicaMaisOuvida = musica;
            }
        }
    
        if (musicaMaisOuvida != null) {
            return "Música mais ouvida: " + musicaMaisOuvida.getNome() +
                               " - " + " (" + maxReproducoes + " reproduções)";
        } else {
            return "Não foi possível determinar a música mais ouvida.";
        }
    }
    

    public String interpreteMaisOuvido() {
        if (musicas == null || musicas.isEmpty()) {
            return "Nenhuma música disponível.";
            
        }
    
        int max = -1;
        String interpreteMaisOuvido = null;
    
        for (Musica musica : musicas) {
            int contador = musica.getContadorReproducao();
            if (contador > max) {
                max = contador;
                interpreteMaisOuvido = musica.getInterprete(); // Obtém o intérprete da música
            }
        }
    
        if (interpreteMaisOuvido != null) {
            return "Intérprete mais ouvido: " + interpreteMaisOuvido;
        } else {
            return "Não foi possível determinar o intérprete mais ouvido.";
        }
    }

    public String utilizadorComMaisPontos() {
        if (utilizadores == null || utilizadores.isEmpty()) {
            return "Nenhum utilizador disponível.";
            
        }
    
        Utilizador utilizadorTop = null;
        int maxPontos = -1;
    
        for (Utilizador u : utilizadores.values()) {
            if (u != null && u.getPontos() > maxPontos) {
                maxPontos = u.getPontos();
                utilizadorTop = u;
            }
        }
    
        if (utilizadorTop != null) {
            return"Utilizador com mais pontos: " + utilizadorTop.getNome() +
                               " (" + maxPontos + " pontos)";
        } else {
            return"Não foi possível determinar o utilizador com mais pontos.";
        }
    }

    public String listarUsers(){
        if (utilizadores.isEmpty()) return "Nenhum utilizador registado.";
    
        StringBuilder sb = new StringBuilder();
        for(Utilizador u : utilizadores.values()){
            sb.append(u.getNome()).append("\n");
        }
        return sb.toString();
    }
    
    
    public String listarPlaylist(){
        if (playlits == null || playlits.isEmpty()) {
            return "Nenhuma playlist disponível.";
        }
    
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for(Playlist p : playlits){
            sb.append(i).append(". ").append(p.getNome()).append("\n");
            i++;
        }
        return sb.toString();
    }

    public int getNumeroDePlaylists() {
        return (playlits == null) ? 0 : playlits.size();
    }

    public void criarPlaylist(String nome, boolean publica, List<Integer> indicesMusicas) {
        List<Musica> musicasSelecionadas = new ArrayList<>();
    
        for (int index : indicesMusicas) {
            if (index >= 0 && index < musicas.size()) {
                musicasSelecionadas.add(musicas.get(index));
            }
        }
    
        Playlist novaPlaylist = new Playlist(nome, musicasSelecionadas);
        
        if(publica){
            playlits.add(novaPlaylist);
        } else{
            if (utilizadorAtual instanceof UtilizadorPremiumBase) {
                ((UtilizadorPremiumBase) utilizadorAtual).setPlaylists(novaPlaylist);
            }
            if(utilizadorAtual instanceof UtilizadorPremiumTop){
                ((UtilizadorPremiumTop) utilizadorAtual).setPlaylists(novaPlaylist);
            }
        }
    }

    public List<String> listarMusicasExcluindo(List<Integer> excluidos) {
        List<String> resultado = new ArrayList<>();
        for (int i = 0; i < musicas.size(); i++) {
            if (!excluidos.contains(i)) {
                resultado.add(musicas.get(i).getNome());
            }
        }
        return resultado;   
    }

    public int getIndiceReal(String tituloMusica) {
        for (int i = 0; i < musicas.size(); i++) {
            if (musicas.get(i).getNome().equals(tituloMusica)) {
                return i;
            }
        }
        return -1;
    }

    public Set<String> getGenerosDisponiveis() {
        Set<String> generos = new HashSet<>();
        for (Musica musica : musicas) {
            generos.add(musica.getGenero());
        }
        return generos;
    }

    public void criarPlaylistPorGenero(String genero, String nome, boolean publica) {
        List<Musica> musicasDoGenero = new ArrayList<>();

        for (Musica musica : musicas) {
            if (musica.getGenero().equalsIgnoreCase(genero)) {
                musicasDoGenero.add(musica);
            }
        }


        Playlist novaPlaylist = new Playlist(nome, musicasDoGenero);

        if (publica) {
            playlits.add(novaPlaylist);
        } else {
            if (utilizadorAtual instanceof UtilizadorPremiumBase) {
                ((UtilizadorPremiumBase) utilizadorAtual).setPlaylists(novaPlaylist);
            }
            if (utilizadorAtual instanceof UtilizadorPremiumTop) {
                ((UtilizadorPremiumTop) utilizadorAtual).setPlaylists(novaPlaylist);
            }
        }
    }

    public Playlist getPlaylistPublica(int index) {
        List<Playlist> publicas = playlits.stream()
            .collect(Collectors.toList());
        if (index >= 0 && index < publicas.size()) {
            return publicas.get(index);
        }
        return null;
    }  


    public List<String> getNomesPlaylistsPublicas() {
        List<String> nomes = new ArrayList<>();
        for (Playlist p : playlits) {
            
            nomes.add(p.getNome());
            
        }
        return nomes;
    }

    public String tipoMaisOuvido() {
        if (musicas == null || musicas.isEmpty()) {
            return "Nenhuma música disponível.";
        }

        Map<String, Integer> contagemGeneros = new HashMap<>();

        for (Musica musica : musicas) {
            String genero = musica.getGenero();
            int reproducoes = musica.getContadorReproducao();

            contagemGeneros.put(genero, contagemGeneros.getOrDefault(genero, 0) + reproducoes);
        }

        String generoMaisOuvido = null;
        int maxReproducoes = -1;

        for (Map.Entry<String, Integer> entry : contagemGeneros.entrySet()) {
            if (entry.getValue() > maxReproducoes) {
                generoMaisOuvido = entry.getKey();
                maxReproducoes = entry.getValue();
            }
        }

        if (generoMaisOuvido == null) {
            return "Nenhum género foi reproduzido ainda.";
        }

        return "Género mais ouvido: " + generoMaisOuvido + " (" + maxReproducoes + " reproduções)";
    }

}
