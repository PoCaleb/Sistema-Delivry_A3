package entregas.sistema.entrega;

// Imports de DAOs
import entregas.sistema.entrega.dao.ClienteDAO;
import entregas.sistema.entrega.dao.PratoDAO;
import entregas.sistema.entrega.dao.RestauranteDAO;
import entregas.sistema.entrega.dao.PedidoDAO;

// Imports de Models
import entregas.sistema.entrega.model.Cliente;
import entregas.sistema.entrega.model.Prato;
import entregas.sistema.entrega.model.Restaurante;
import entregas.sistema.entrega.model.Pedido;

import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class SistemaMain {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ClienteDAO clienteDAO = new ClienteDAO();
    private static final RestauranteDAO restauranteDAO = new RestauranteDAO();
    private static final PratoDAO pratoDAO = new PratoDAO();
    private static final PedidoDAO pedidoDAO = new PedidoDAO();
    private static final long PRAZO_MINUTOS = 30; // Prazo de entrega padrão

    // --- MAIN, LOGIN, CADASTRO (Mantidos) ---
    
    public static void main(String[] args) {
        int opcaoPrincipal = -1;
        while (opcaoPrincipal != 0) {
            System.out.println("\n=== BEM-VINDO AO SISTEMA DE ENTREGAS ===");
            System.out.println("1. Entrar como Cliente");
            System.out.println("2. Entrar como Restaurante");
            System.out.println("3. Cadastrar Novo Cliente");
            System.out.println("4. Cadastrar Novo Restaurante");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            
            try {
                if (scanner.hasNextInt()) {
                    opcaoPrincipal = scanner.nextInt();
                    scanner.nextLine(); 

                    switch (opcaoPrincipal) {
                        case 1:
                            menuLoginCliente();
                            break;
                        case 2:
                            menuLoginRestaurante();
                            break;
                        case 3:
                            cadastrarCliente(); 
                            break;
                        case 4:
                            cadastrarRestaurante(); // Implementação completa
                            break;
                        case 0:
                            System.out.println("Sistema encerrado. Obrigado!");
                            break;
                        default:
                            System.out.println("Opção inválida. Tente novamente.");
                    }
                } else {
                    scanner.nextLine();
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                System.out.println("ERRO: Por favor, insira um número para a opção.");
                opcaoPrincipal = -1;
            }
        }
    }
    
    private static void menuLoginCliente() {
        System.out.println("\n--- LOGIN DO CLIENTE ---");
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Cliente cliente = clienteDAO.autenticar(login, senha);
        if (cliente != null) {
            System.out.println("Login bem-sucedido! Bem-vindo(a), " + cliente.getNome() + ".");
            menuCliente(cliente);
        } else {
            System.out.println("Login ou senha incorretos.");
        }
    }

    private static void menuLoginRestaurante() {
        System.out.println("\n--- LOGIN DO RESTAURANTE ---");
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Restaurante restaurante = restauranteDAO.autenticar(login, senha);
        if (restaurante != null) {
            System.out.println("Login bem-sucedido! Bem-vindo(a), " + restaurante.getNomeEmpresa() + ".");
            menuRestaurante(restaurante);
        } else {
            System.out.println("Login ou senha incorretos.");
        }
    }

    private static void cadastrarCliente() {
        System.out.println("\n=== CADASTRO DE NOVO CLIENTE ===");
        System.out.print("Seu Nome Completo: ");
        String nome = scanner.nextLine();
        System.out.print("Endereço Completo: ");
        String endereco = scanner.nextLine();
        System.out.print("Telefone de Contato: ");
        String telefone = scanner.nextLine();
        System.out.print("Login de Acesso (Único): ");
        String login = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        System.out.print("CPF (ex: 111.111.111-11): ");
        String cpf = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        try {
            Cliente novoCliente = new Cliente(nome, endereco, telefone, login, senha, cpf, email);
            int idGerado = clienteDAO.cadastrarCliente(novoCliente); 

            if (idGerado > 0) {
                System.out.println(">>> Cliente '" + nome + "' cadastrado com sucesso! ID: " + idGerado + " <<<");
            } else {
                System.out.println("ERRO: Não foi possível cadastrar o cliente. Verifique o CPF/Login.");
            }
        } catch (Exception e) {
             System.out.println("ERRO ao processar o cadastro: " + e.getMessage());
        }
    }
    
    private static void cadastrarRestaurante() {
        System.out.println("\n=== CADASTRO DE NOVO RESTAURANTE ===");
        System.out.print("Nome do Contato (Seu Nome): ");
        String nomeContato = scanner.nextLine();
        System.out.print("Nome da Empresa: ");
        String nomeEmpresa = scanner.nextLine();
        System.out.print("CNPJ (ex: 11.111.111/0001-11): ");
        String cnpj = scanner.nextLine();
        System.out.print("Endereço Completo: ");
        String endereco = scanner.nextLine();
        System.out.print("Telefone de Contato: ");
        String telefone = scanner.nextLine();
        System.out.print("Login de Acesso (Único): ");
        String login = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        // Tipo de Cozinha removido
        
        try {
            // Construtor ajustado para 7 parâmetros
            Restaurante novoRestaurante = new Restaurante(nomeContato, endereco, telefone, login, senha, nomeEmpresa, cnpj);
            
            int idGerado = restauranteDAO.cadastrarRestaurante(novoRestaurante); 

            if (idGerado > 0) {
                System.out.println(">>> Restaurante '" + nomeEmpresa + "' cadastrado com sucesso! ID: " + idGerado + " <<<");
            } else {
                System.out.println("ERRO: Não foi possível cadastrar o restaurante. Verifique o CNPJ/Login. (Detalhes no console)");
            }
        } catch (Exception e) {
             System.out.println("ERRO ao processar o cadastro: " + e.getMessage());
        }
    }

    // ------------------------------------
    // --- MENU RESTAURANTE ---
    // ------------------------------------
    private static void menuRestaurante(Restaurante restauranteLogado) {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n=== RESTAURANTE: " + restauranteLogado.getNomeEmpresa() + " ===");
            System.out.println("1. Adicionar Prato");
            System.out.println("2. Verificar Pedidos EM ANDAMENTO (Novos/Em Preparo)");
            System.out.println("3. Ver Histórico de Pedidos ENTREGUES"); 
            System.out.println("0. Logout");
            System.out.print("Escolha uma opção: ");

            try {
                if (scanner.hasNextInt()) {
                    opcao = scanner.nextInt();
                    scanner.nextLine();

                    switch (opcao) {
                        case 1:
                            cadastrarPrato(restauranteLogado);
                            break;
                        case 2:
                            visualizarPedidosRestaurante(restauranteLogado, "EM ANDAMENTO");
                            break;
                        case 3:
                            visualizarPedidosRestaurante(restauranteLogado, "ENTREGUE");
                            break;
                        case 0:
                            System.out.println("Sessão do restaurante encerrada.");
                            break;
                        default:
                            System.out.println("Opção inválida. Tente novamente.");
                    }
                } else {
                    scanner.nextLine();
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                System.out.println("ERRO: Por favor, insira um número para a opção.");
                opcao = -1;
            }
        }
    }
    
    // --- FUNÇÕES AUXILIARES RESTAURANTE ---
    
    private static void cadastrarPrato(Restaurante restauranteLogado) {
        System.out.println("\n--- CADASTRAR NOVO PRATO ---");
        System.out.print("Nome do Prato: ");
        String nomePrato = scanner.nextLine();
        System.out.print("Descrição: ");
        String descPrato = scanner.nextLine();
        System.out.print("Valor: R$ ");
        
        try {
            double valorPrato = scanner.nextDouble();
            scanner.nextLine(); 

            Prato novoPrato = new Prato(nomePrato, descPrato, valorPrato, restauranteLogado.getId());
            pratoDAO.cadastrarPrato(novoPrato);
            System.out.println("Prato cadastrado com sucesso!");
        } catch (InputMismatchException e) {
            System.out.println("ERRO: Valor inválido. O prato não foi cadastrado.");
            scanner.nextLine(); 
        }
    }

    /**
     * USA ClienteDAO.buscarPorId()
     */
    private static void visualizarPedidosRestaurante(Restaurante restauranteLogado, String status) {
        System.out.println("\n=== RESTAURANTE: " + restauranteLogado.getNomeEmpresa() + " | PEDIDOS: " + status.toUpperCase() + " ===");
        
        List<Pedido> pedidos = pedidoDAO.buscarPedidosPorRestauranteEStatus(restauranteLogado.getId(), status); 

        if (pedidos.isEmpty()) {
            System.out.println("Não há pedidos com o status '" + status.toUpperCase() + "' no momento.");
            return;
        }
        
        LocalDateTime agora = LocalDateTime.now();
        
        // --- Exibição da Tabela (Coluna CLIENTE) ---
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-5s | %-16s | %-15s | %-25s | %s\n", "ID", "DATA/HORA", "STATUS/PRAZO", "CLIENTE", "DESCRIÇÃO");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
        
        for (Pedido pedido : pedidos) {
            String dataHoraStr = (pedido.getDataHora() != null) ? pedido.getDataHora().toString().substring(0, 16).replace('T', ' ') : "N/A";
            String statusPrazo = pedido.getStatus().toUpperCase();
            
            Cliente cliente = clienteDAO.buscarPorId(pedido.getIdCliente());
            String nomeCliente = (cliente != null) ? cliente.getNome() : "Cliente Desconhecido"; 

            // Lógica de PRAZO e ATRASO
            if (status.equals("EM ANDAMENTO") && pedido.getDataHora() != null) {
                Duration duracao = Duration.between(pedido.getDataHora(), agora);
                long minutosDecorridos = duracao.toMinutes();
                long minutosRestantes = PRAZO_MINUTOS - minutosDecorridos;

                if (minutosDecorridos > PRAZO_MINUTOS) {
                    long atraso = minutosDecorridos - PRAZO_MINUTOS;
                    statusPrazo = "**EM ATRASO** (" + atraso + " min)";
                } else {
                    statusPrazo = "A caminho (" + minutosRestantes + " min)";
                }
            } else if (status.equals("ENTREGUE")) {
                statusPrazo = "FINALIZADO";
            }

            // FORMATO ATUALIZADO
            System.out.printf("%-5d | %-16s | %-15s | %-25s | %s\n", 
                pedido.getId(), 
                dataHoraStr,
                statusPrazo,
                nomeCliente,
                pedido.getDescricaoPedido().substring(0, Math.min(pedido.getDescricaoPedido().length(), 40)) + "..."
            );
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
    }

    // ------------------------------------
    // --- MENU CLIENTE ---
    // ------------------------------------
    private static void menuCliente(Cliente clienteLogado) {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n=== CLIENTE: " + clienteLogado.getNome() + " ===");
            System.out.println("1. Fazer Pedido");
            System.out.println("2. Ver Meus Pedidos EM ANDAMENTO e Confirmar Entrega");
            System.out.println("3. Ver Histórico de Pedidos ENTREGUES"); 
            System.out.println("0. Logout");
            System.out.print("Escolha uma opção: ");
            
            try {
                if (scanner.hasNextInt()) {
                    opcao = scanner.nextInt();
                    scanner.nextLine();

                    switch (opcao) {
                        case 1:
                            fazerPedido(clienteLogado); 
                            break;
                        case 2:
                            visualizarPedidosEmAndamentoEConfirmar(clienteLogado); 
                            break;
                        case 3:
                            visualizarPedidosEntregues(clienteLogado); 
                            break;
                        case 0:
                            System.out.println("Sessão do cliente encerrada.");
                            break;
                        default:
                            System.out.println("Opção inválida. Tente novamente.");
                    }
                } else {
                    scanner.nextLine();
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                System.out.println("ERRO: Por favor, insira um número para a opção.");
                opcao = -1;
            }
        }
    }
    
    /**
     * USA RestauranteDAO.buscarPorId()
     */
    private static void visualizarPedidosEmAndamentoEConfirmar(Cliente clienteLogado) {
        System.out.println("\n=== PEDIDOS EM ANDAMENTO E CONFIRMAÇÃO ===");
        
        List<Pedido> todosPedidos = pedidoDAO.buscarPedidosPorCliente(clienteLogado.getId()); 
        
        // Filtra APENAS os EM ANDAMENTO
        List<Pedido> pedidosEmAndamento = todosPedidos.stream()
            .filter(p -> p.getStatus().equals("EM ANDAMENTO"))
            .collect(Collectors.toList());

        if (pedidosEmAndamento.isEmpty()) {
            System.out.println("Você não possui pedidos em andamento.");
            return;
        }

        LocalDateTime agora = LocalDateTime.now();
        
        // --- Exibição da Tabela (Coluna RESTAURANTE) ---
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-5s | %-16s | %-20s | %-15s | %s\n", "ID", "DATA/HORA", "RESTAURANTE", "PRAZO", "DESCRIÇÃO"); 
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
        
        for (Pedido pedido : pedidosEmAndamento) {
            String dataHoraStr = (pedido.getDataHora() != null) ? pedido.getDataHora().toString().substring(0, 16).replace('T', ' ') : "N/A";
            String statusPrazo;
            
            Restaurante restaurante = restauranteDAO.buscarPorId(pedido.getIdRestaurante());
            String nomeRestaurante = (restaurante != null) ? restaurante.getNomeEmpresa() : "Restaurante Desconhecido";
            
            Duration duracao = Duration.between(pedido.getDataHora(), agora);
            long minutosDecorridos = duracao.toMinutes();
            long minutosRestantes = PRAZO_MINUTOS - minutosDecorridos;

            if (minutosDecorridos > PRAZO_MINUTOS) {
                long atraso = minutosDecorridos - PRAZO_MINUTOS;
                statusPrazo = "**EM ATRASO** (" + atraso + " min)"; 
            } else {
                statusPrazo = minutosRestantes + " min restantes";
            }
            
            // FORMATO ATUALIZADO
            System.out.printf("%-5d | %-16s | %-20s | %-15s | %s\n", 
                pedido.getId(), 
                dataHoraStr,
                nomeRestaurante,
                statusPrazo,
                pedido.getDescricaoPedido().substring(0, Math.min(pedido.getDescricaoPedido().length(), 40)) + "..."
            );
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
        
        // Ação de Confirmação (Mantida)
        try {
            System.out.print("\nDigite o ID do pedido que você deseja CONFIRMAR ENTREGA ou 0 para voltar: ");
            int idConfirmar = scanner.nextInt();
            scanner.nextLine();
            
            if (idConfirmar == 0) return;
            
            Pedido pedidoParaConfirmar = pedidosEmAndamento.stream()
                .filter(p -> p.getId() == idConfirmar)
                .findFirst()
                .orElse(null);
            
            if (pedidoParaConfirmar != null) {
                if (pedidoDAO.atualizarStatus(idConfirmar, "ENTREGUE")) {
                    System.out.println("✅ Pedido #" + idConfirmar + " CONFIRMADO E MARCADO COMO ENTREGUE. Obrigado!");
                } else {
                    System.out.println("❌ Erro ao atualizar o status do pedido.");
                }
            } else {
                System.out.println("ID inválido ou pedido não está em andamento.");
            }
        } catch (InputMismatchException e) {
            System.out.println("ERRO: Entrada inválida. Voltando ao menu.");
            scanner.nextLine();
        }
    }
    
    /**
     * USA RestauranteDAO.buscarPorId()
     */
    private static void visualizarPedidosEntregues(Cliente clienteLogado) {
        System.out.println("\n=== HISTÓRICO DE PEDIDOS ENTREGUES ===");
        
        List<Pedido> todosPedidos = pedidoDAO.buscarPedidosPorCliente(clienteLogado.getId()); 
        
        // Filtra APENAS os ENTREGUES
        List<Pedido> pedidosEntregues = todosPedidos.stream()
            .filter(p -> p.getStatus().equals("ENTREGUE"))
            .collect(Collectors.toList());

        if (pedidosEntregues.isEmpty()) {
            System.out.println("Você ainda não possui pedidos entregues.");
            return;
        }

        // --- Exibição da Tabela (Coluna RESTAURANTE) ---
        System.out.println("-------------------------------------------------------------------------------------------------------");
        System.out.printf("%-5s | %-16s | %-20s | %-10s | %s\n", "ID", "DATA/HORA", "RESTAURANTE", "VALOR", "DESCRIÇÃO"); 
        System.out.println("-------------------------------------------------------------------------------------------------------");
        
        for (Pedido pedido : pedidosEntregues) {
            String dataHoraStr = (pedido.getDataHora() != null) ? pedido.getDataHora().toString().substring(0, 16).replace('T', ' ') : "N/A";
            
            Restaurante restaurante = restauranteDAO.buscarPorId(pedido.getIdRestaurante());
            String nomeRestaurante = (restaurante != null) ? restaurante.getNomeEmpresa() : "Restaurante Desconhecido";
            
            // FORMATO ATUALIZADO
            System.out.printf("%-5d | %-16s | %-20s | R$ %-7.2f | %s\n", 
                pedido.getId(), 
                dataHoraStr,
                nomeRestaurante,
                pedido.getValorTotal(), 
                pedido.getDescricaoPedido().substring(0, Math.min(pedido.getDescricaoPedido().length(), 40)) + "..."
            );
        }
        System.out.println("-------------------------------------------------------------------------------------------------------");
    }

    // --- FUNÇÃO: FAZER PEDIDO (Mantida) ---
    private static void fazerPedido(Cliente clienteLogado) {
        System.out.println("\n=== FAZER NOVO PEDIDO ===");
        List<Prato> pratos = pratoDAO.listarTodos();

        if (pratos.isEmpty()) {
            System.out.println("Desculpe, não há nenhum prato cadastrado no momento.");
            return;
        }

        System.out.println("--- PRATOS DISPONÍVEIS ---");
        System.out.printf("%-5s | %-25s | %-40s | %s\n", "ID", "NOME", "DESCRIÇÃO", "VALOR");
        System.out.println("-------------------------------------------------------------------------------------------------");
        
        for (int i = 0; i < pratos.size(); i++) {
            Prato prato = pratos.get(i);
            System.out.printf("%-5d | %-25s | %-40s | R$ %.2f\n", 
                i + 1, 
                prato.getNome(), 
                prato.getDescricao(), 
                prato.getValor()
            );
        }
        System.out.println("-------------------------------------------------------------------------------------------------");

        try {
            System.out.print("Digite o ID do prato que deseja pedir ou 0 para cancelar: ");
            int idSelecionado = scanner.nextInt();
            scanner.nextLine();

            if (idSelecionado == 0) {
                System.out.println("Pedido cancelado.");
                return;
            }

            if (idSelecionado > 0 && idSelecionado <= pratos.size()) {
                Prato pratoSelecionado = pratos.get(idSelecionado - 1);
                
                System.out.println("\nVocê selecionou: " + pratoSelecionado.getNome() + " (R$ " + pratoSelecionado.getValor() + ")");
                System.out.print("Confirmar pedido? (S/N): ");
                String confirmacao = scanner.nextLine();

                if (confirmacao.equalsIgnoreCase("S")) {
                    Pedido novoPedido = new Pedido(); 
                    novoPedido.setIdCliente(clienteLogado.getId());
                    novoPedido.setIdRestaurante(pratoSelecionado.getIdRestaurante());
                    novoPedido.setDescricaoPedido("1x " + pratoSelecionado.getNome());
                    novoPedido.setValorTotal(pratoSelecionado.getValor());
                    novoPedido.setStatus("EM ANDAMENTO");
                    
                    int idPedido = pedidoDAO.cadastrarPedido(novoPedido); 
                    
                    if (idPedido > 0) {
                        System.out.println("Pedido de " + pratoSelecionado.getNome() + " enviado com sucesso! ID: " + idPedido);
                    } else {
                        System.out.println("ERRO: Não foi possível registrar o pedido no sistema.");
                    }
                } else {
                    System.out.println("Pedido cancelado.");
                }

            } else {
                System.out.println("ID de prato inválido.");
            }
        } catch (InputMismatchException e) {
            System.out.println("ERRO: Entrada inválida. Pedido cancelado.");
            scanner.nextLine();
        }
    }
}