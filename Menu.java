import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.swing.plaf.multi.MultiInternalFrameUI;

public class Menu {
    private enum State {
        INICIAL, ADICIONAR, LOGIN, REGISTAR, PRINCIPAL, SAIR, PREMIUM, PERFIL, ESTATISTICAS, PLAYLIST, FAVORITOS, REPRODUCAO, MUSICAS, CRIACAOPLAYLIST, PLAYLISTGENERO
    }   

    private final Scanner scanner;
    private State state = State.INICIAL;

    private MainController mainController;

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.mainController = new MainController();
        mainController.carregarEstado();
    }

    public void run() {
        while (this.state != State.SAIR) {
            switch (this.state) {
                case INICIAL:
                    displayMenu();
                    break;
                case REGISTAR:
                    displayRegistarSubMenu();
                    break;
                case LOGIN:
                    displayLoginSubMenu();
                    break;
                case ADICIONAR:
                    displayAddMusic();
                    break;
                case PRINCIPAL:
                    displayUtilizadorMenu();
                    break;
                case PREMIUM:
                    displayPremium();
                    break;
                case PERFIL:
                    displayPerfil();
                    break;
                case ESTATISTICAS:
                    displayEstatisticas();
                    break;
                case PLAYLIST:
                    displayPlayLists();
                    break;
                case FAVORITOS:
                    displayFavoritos();
                    break;
                case REPRODUCAO:
                    displayMenuReproducao();
                    break;
                case MUSICAS:
                    displayMenuListaMusica();
                    break;
                case CRIACAOPLAYLIST:
                    displayCriacaoPlaylist();
                    break;
                case PLAYLISTGENERO:
                    displayCriacaoPlaylistPorGenero();
                    break;
                default:
                    break;
            }
        }
    }

    public void displayMenu() {
        System.out.println("\nMENU INICIAL - SpotifUM");
        System.out.println("1. Login");
        System.out.println("2. Registar");
        System.out.println("3. Adicionar Música");
        System.out.println("4. Sair");
        System.out.print("Digite a opção desejada: ");

        // Usando try-catch para evitar InputMismatchException
        try {
            int input = scanner.nextInt();
            scanner.nextLine();  // Limpar o buffer após nextInt()
            
            switch (input) {
                case 1:
                    this.state = State.LOGIN;
                    break;
                case 2:
                    this.state = State.REGISTAR;
                    break;
                case 3:
                    this.state = State.ADICIONAR;
                    break;
                case 4:
                    this.state = State.SAIR;
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Entrada inválida. Por favor, insira um número válido.");
            scanner.nextLine();  // Limpar o buffer de entrada
        }
    }

    public void displayRegistarSubMenu() {
        System.out.println("\n--- Registar ---");
        System.out.print("Introduza o e-mail: ");
        String email = scanner.nextLine();

        try {
            System.out.print("Introduza o nome: ");
            String nome = scanner.nextLine();
            System.out.print("Introduza a morada: ");
            String morada = scanner.nextLine();

            System.out.println(mainController.adicionarUtilizador(email, nome, morada));
        } catch (Exception e) {
            System.out.println("E-mail já existe!");
        }

        this.state = State.PRINCIPAL;
    }

    public void displayLoginSubMenu() {
        try {
            System.out.print("Introduza o email: ");
            String emailLogin = scanner.nextLine();

            System.out.println(mainController.realizarLogin(emailLogin));
            this.state = State.PRINCIPAL;
        } catch (Exception e) {
            System.out.println("Email não encontrado.");
            this.state = State.INICIAL;
        }
    }

    public void displayAddMusic() {
        try {

            System.out.print("Introduza o nome da musica: ");
            String nomeMusica = scanner.nextLine();

            System.out.print("Introduza o interprete(cantor): ");
            String cantorMusica = scanner.nextLine();

            System.out.print("Introduza o nome da editora: ");
            String editoraMusica = scanner.nextLine();

            System.out.print("Introduza um pouco da letra da musica: ");
            String letraMusica = scanner.nextLine();

            System.out.print("Introduza o genero da musica: ");
            String generoMusica = scanner.nextLine();

            System.out.print("Introduza a duração da musica (em segundos): ");
            int duracaoMusica = scanner.nextInt();

            mainController.adicionarMusica(nomeMusica, cantorMusica, editoraMusica, generoMusica, duracaoMusica,letraMusica);
            mainController.gravarEstado();
            this.state = State.INICIAL;
        } catch (Exception e) {
            System.out.println("Erro ao adicionar música: " + e.getMessage());
            this.state = State.INICIAL;
        }
        
    }

    public void displayUtilizadorMenu() {
        System.out.println("\nMENU UTILIZADOR - SpotifUM");
        System.out.println("1. Ouvir musicas");
        System.out.println("2. Ver musicas");
        System.out.println("3. Playlist");
        System.out.println("4. Favoritos");
        System.out.println("5. Premium");
        System.out.println("6. Perfil");
        System.out.println("7. Estatisticas");
        System.out.println("8. Voltar para o menu principal (Logout)");
        System.out.println("9. Sair");
        System.out.print("Digite a opção desejada: ");

        try {
            int input = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer de entrada

            switch (input) {
                case 1:
                    this.state = State.REPRODUCAO;
                    break;
                case 2:
                    this.state = State.MUSICAS;
                    break;
                case 3:
                    this.state = State.PLAYLIST;
                    break;
                case 4:
                    this.state = State.FAVORITOS;
                    break;
                case 5:
                    this.state = State.PREMIUM;
                    break;
                case 6:
                    this.state = State.PERFIL;
                    break;
                case 7:
                    this.state = State.ESTATISTICAS;
                    break;
                case 8:
                    mainController.gravarEstado();
                    this.state = State.INICIAL;
                    break;
                case 9:
                    mainController.gravarEstado();
                    this.state = State.SAIR;
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Entrada inválida. Tente novamente.");
            scanner.nextLine();  // Limpar o buffer de entrada
        }
    }
    
    public void displayPremium() {
        Utilizador atual = mainController.getUtilizadorAtual();
    
        if (atual instanceof UtilizadorPremiumTop) {
            System.out.println("\nBEM-VINDO AO MENU PREMIUM TOP");
            System.out.println("1. Criar playlist");
            System.out.println("2. Playlists tematicas"); //opçao a mudar
            System.out.println("3. Cancelar subscrição");
            System.out.println("4. Voltar");
            System.out.print("Escolha a opção: ");

            try {
                int input = scanner.nextInt();
                scanner.nextLine(); // Limpar o buffer de entrada
        
                switch (input) {
                    case 1:
                        this.state = State.CRIACAOPLAYLIST;
                        break;
                    case 2:
                        this.state = State.PLAYLISTGENERO;
                        break;
                    case 3:
                        mainController.cancelarPremium();
                        break;
                    case 4:
                        this.state = State.PRINCIPAL;
                        break;
                    default:
                        System.out.println("Opção inválida.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida. Tente novamente.");
                scanner.nextLine();  // Limpar o buffer de entrada
            }
        }
    
        else if (atual instanceof UtilizadorPremiumBase) {
            
            System.out.println("\nBEM-VINDO AO MENU PREMIUM BASE");
            System.out.println("1. Criar playlist");
            System.out.println("2. Cancelar subscrição");
            System.out.println("3. Voltar");
            System.out.print("Escolha a opção: ");

            try {
                int input = scanner.nextInt();
                scanner.nextLine(); // Limpar o buffer de entrada
        
                switch (input) {
                    case 1:
                        this.state = State.CRIACAOPLAYLIST;
                        break;
                    case 2:
                        mainController.cancelarPremium();
                        break;
                    case 3:
                        this.state = State.PRINCIPAL;
                        break;
                    default:
                        System.out.println("Opção inválida.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida. Tente novamente.");
                scanner.nextLine();  // Limpar o buffer de entrada
            }
        }
    
        else {  // Caso ainda não seja premium
            System.out.println("\nMENU PREMIUM - Escolhe o tipo de Premium");
            System.out.println("1. Obter Premium Base");
            System.out.println("2. Obter Premium Top");
            System.out.println("3. Voltar");
            System.out.print("Digite a opção desejada: ");
        
            try {
                int input = scanner.nextInt();
                scanner.nextLine(); // Limpar o buffer de entrada
        
                switch (input) {
                    case 1:
                        mainController.tornarPremium("base");
                        break;
                    case 2:
                        mainController.tornarPremium("top");
                        break;
                    case 3:
                        this.state = State.PRINCIPAL;
                        break;
                    default:
                        System.out.println("Opção inválida.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida. Tente novamente.");
                scanner.nextLine();  // Limpar o buffer de entrada
            }
        }
        mainController.gravarEstado();
    }

    public void displayPerfil(){
        System.out.println("\nMENU PERFIL");
        System.out.println(mainController.mostrarPerfil());
        System.out.println("1.Sair");
        System.out.print("Digite a opção desejada: ");

        try {
            int input = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer de entrada
    
            switch (input) {
                case 1:
                    this.state = State.PRINCIPAL;
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Entrada inválida. Tente novamente.");
            scanner.nextLine();  // Limpar o buffer de entrada
        }
    }

    public void displayEstatisticas(){
        while (state == State.ESTATISTICAS) {
            System.out.println("\nMENU ESTATISTICAS");
            System.out.println("1. Musica mais ouvida");
            System.out.println("2. Interprete mais ouvido");
            System.out.println("3. Utilizador com mais pontos");
            System.out.println("4. Tipo de musica mais ouvida");
            System.out.println("5. Todos os utilizadores");
            System.out.println("6. Sair");
            System.out.println("Digite a opção desejada: ");
        
            try {
                int input = scanner.nextInt();
                scanner.nextLine(); // Limpar o buffer de entrada
        
                switch (input) {
                    case 1:
                        System.out.println(mainController.musicaMaisOuvida());
                        System.out.println("\nPressione Enter para continuar...");
                        scanner.nextLine();
                        break;
                    case 2:
                        System.out.println(mainController.interpreteMaisOuvido());
                        System.out.println("\nPressione Enter para continuar...");
                        scanner.nextLine();
                        break;
                    case 3:
                        System.out.println(mainController.utilizadorComMaisPontos());
                        System.out.println("\nPressione Enter para continuar...");
                        scanner.nextLine();
                        break;
                    case 4:
                        System.out.println(mainController.tipoMaisOuvido());
                        System.out.println("\nPressione Enter para continuar...");
                        scanner.nextLine();
                        break;
                    case 5:
                        System.out.println(mainController.listarUsers());
                        System.out.println("\nPressione Enter para continuar...");
                        scanner.nextLine();
                        break;
                    case 6:
                        this.state = State.PRINCIPAL;
                        break;
                    default:
                        System.out.println("Opção inválida.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida. Tente novamente.");
                scanner.nextLine();  // Limpar o buffer de entrada
            }
        }
    }

    public void displayPlayLists() {
        System.out.println("\nMENU PLAYLISTS");
        List<String> nomes = mainController.getNomesPlaylistsPublicas();

        if (nomes.isEmpty()) {
            System.out.println("Não há playlists públicas disponíveis.");
            System.out.println("1. Voltar");
            int input = scanner.nextInt();
            if (input == 1) this.state = State.PRINCIPAL;
            return;
        }

        for (int i = 0; i < nomes.size(); i++) {
            System.out.println((i + 1) + ". " + nomes.get(i));
        }
        System.out.println((nomes.size() + 1) + ". Voltar");

        try {
            int input = scanner.nextInt();
            scanner.nextLine();

            if (input < 1 || input > nomes.size() + 1) {
                System.out.println("Opção inválida.");
            } else if (input == nomes.size() + 1) {
                this.state = State.PRINCIPAL;
            } else {
                Playlist escolhida = mainController.getPlaylistPublica(input - 1);
                if (escolhida != null) {
                    displayPlaylistMusicas(escolhida);
                } else {
                    System.out.println("Playlist inválida.");
                }
            }
        } catch (Exception e) {
            System.out.println("Entrada inválida.");
            scanner.nextLine();
        }
    }


    public void displayPlaylistMusicas(Playlist playlist) {
        System.out.println("\nPlaylist: " + playlist.getNome());
        List<Musica> musicas = playlist.getMusicas();

        if (musicas.isEmpty()) {
            System.out.println("Esta playlist está vazia.");
        } else {
            for (int i = 0; i < musicas.size(); i++) {
                System.out.println((i + 1) + ". " + musicas.get(i).getNome());
            }
        }
        System.out.println((musicas.size() + 1) + ". Reproduzir");
        System.out.println((musicas.size() + 2) + ". Voltar");

        try {
            int input = scanner.nextInt();
            scanner.nextLine();
            if (input == musicas.size() + 2) {
                return;
            } else if (input == musicas.size() + 1) {
                boolean repetir = true;
                while (repetir) {
                    System.out.println(mainController.ouvirMusica(musicas));

                    System.out.print("Deseja ouvir novamente? (s/n): ");
                    String resposta = scanner.nextLine();
                    if (!resposta.equals("s")) {
                        repetir = false;
                    }
                }
            }else {
                System.out.println("Opção inválida.");
            }
        } catch (Exception e) {
            System.out.println("Entrada inválida.");
        }
    }

    public void displayFavoritos(){
        Utilizador atual = mainController.getUtilizadorAtual();
    
        if (atual instanceof UtilizadorPremiumBase || atual instanceof UtilizadorPremiumTop){
            System.out.println("ola");
            System.out.println("\nPressione Enter para voltar...");
            scanner.nextLine();
            // Aqui podes adicionar funcionalidades relacionadas aos favoritos
        } else {
            System.out.println("Tens de ser premium para ter favoritos.");
            System.out.println("\nPressione Enter para voltar...");
            scanner.nextLine(); // Espera o utilizador carregar Enter
        }
    
        this.state = State.PRINCIPAL;
    }
    
    public void displayMenuReproducao() {
        boolean emReproducao = true;
        List<Musica> todMusicas = mainController.getMusicas();
    
        while (emReproducao) {
            System.out.println("\nMENU REPRODUCAO");
            System.out.println(mainController.ouvirMusica(todMusicas)); 
            System.out.println("1. Próxima");
            System.out.println("2. Sair");
            System.out.print("Escolha a opção: ");
    
            try {
                int input = scanner.nextInt();
                scanner.nextLine(); // Limpar o buffer
    
                switch (input) {
                    case 1:
                        mainController.ouvirMusica(todMusicas);
                        break;
                    case 2:
                        this.state = State.PRINCIPAL;
                        emReproducao = false;
                        break;
                    default:
                        System.out.println("Opção inválida.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida. Tente novamente.");
                scanner.nextLine(); // Limpar buffer
            }
        }
    }

    public void displayMenuListaMusica() {
        System.out.println("\nMENU LISTA DE MÚSICAS");
    
        System.out.println(mainController.listarMusicas());
    
        System.out.println("\nPressione Enter para voltar...");
        scanner.nextLine(); // Esperar o utilizador pressionar Enter para voltar
    
        this.state = State.PRINCIPAL;
    }

    public void displayCriacaoPlaylist() {
        System.out.println("\n--- MENU CRIAÇÃO PLAYLIST ---");
        System.out.print("Digite o nome da playlist: ");
        String nome = scanner.nextLine();

        List<Integer> indicesMusicasSelecionadas = new ArrayList<>();
        boolean emAdd = true;

        while (emAdd) {
            System.out.println("\n--- Lista de músicas disponíveis ---");
            List<String> musicasDisponiveis = mainController.listarMusicasExcluindo(indicesMusicasSelecionadas);
            for (int i = 0; i < musicasDisponiveis.size(); i++) {
                System.out.println((i + 1) + " - " + musicasDisponiveis.get(i));
            }

            System.out.print("Digite o número da música para adicionar (0 para terminar): ");

            int input;
            try {
                input = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Tenta novamente.");
                scanner.nextLine(); // limpar buffer
                continue;
            }

            if (input == 0) {
                emAdd = false;
            } else if (input > 0 && input <= musicasDisponiveis.size()) {
                // Mapear posição visível de volta para índice real
                int realIndex = mainController.getIndiceReal(musicasDisponiveis.get(input - 1));
                indicesMusicasSelecionadas.add(realIndex);
                System.out.println("Música adicionada.");
            } else {
                System.out.println("Número inválido. Tenta novamente.");
            }
        }

        System.out.print("A playlist será pública? (s/n): ");
        scanner.nextLine(); // consumir quebra de linha
        String visibilidade = scanner.nextLine();
        boolean publica = visibilidade.equalsIgnoreCase("s");

        mainController.criarPlaylist(nome, publica, indicesMusicasSelecionadas);
        System.out.println("Playlist criada com sucesso!");
        mainController.gravarEstado();
        this.state = State.PREMIUM;
    }

    public void displayCriacaoPlaylistPorGenero() {
        System.out.println("\n--- CRIAÇÃO AUTOMÁTICA DE PLAYLIST POR GÊNERO ---");

        Set<String> generosDisponiveis = mainController.getGenerosDisponiveis();

        if (generosDisponiveis.isEmpty()) {
            System.out.println("Nenhuma música disponível para criar playlists.");
            return;
        }

        System.out.println("Gêneros disponíveis:");
        List<String> generosList = new ArrayList<>(generosDisponiveis);
        for (int i = 0; i < generosList.size(); i++) {
            System.out.println((i + 1) + " - " + generosList.get(i));
        }

        int escolha = -1;
        while (escolha < 1 || escolha > generosList.size()) {
            System.out.print("Escolha o número do gênero desejado: ");
            try {
                escolha = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Tenta novamente.");
                scanner.nextLine(); // limpar buffer
            }
        }
        scanner.nextLine(); // consumir quebra de linha

        String generoEscolhido = generosList.get(escolha - 1);

        System.out.print("Digite o nome da playlist: ");
        String nome = scanner.nextLine();

        System.out.print("A playlist será pública? (s/n): ");
        String visibilidade = scanner.nextLine();
        boolean publica = visibilidade.equalsIgnoreCase("s");

        mainController.criarPlaylistPorGenero(generoEscolhido, nome, publica);
        mainController.gravarEstado();

        this.state = State.PREMIUM;
    }

    
}
