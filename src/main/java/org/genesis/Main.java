package org.genesis;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GestorDeEventos gestor = new GestorDeEventos();
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\nGestão de Eventos:");
            System.out.println("1. Cadastrar Evento");
            System.out.println("2. Listar Eventos");
            System.out.println("3. Inscrever Participante");
            System.out.println("4. Buscar Evento por Nome");
            System.out.println("5. Exportar Eventos para CSV");
            System.out.println("6. Importar Eventos de CSV");
            System.out.println("7. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir nova linha

            switch (opcao) {
                case 1:
                    System.out.print("Nome do evento: ");
                    String nome = scanner.nextLine();
                    System.out.print("Data do evento: ");
                    String data = scanner.nextLine();
                    System.out.print("Local do evento: ");
                    String local = scanner.nextLine();
                    System.out.print("Capacidade máxima: ");
                    int capacidade = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Categoria do evento: ");
                    String categoria = scanner.nextLine();
                    if (gestor.cadastrarEvento(nome, data, local, capacidade, categoria)) {
                        System.out.println("Evento cadastrado com sucesso!");
                    } else {
                        System.out.println("Não foi possível cadastrar o evento. Limite de eventos atingido.");
                    }
                    break;

                case 2:
                    gestor.listarEventos();
                    break;

                case 3:
                    System.out.print("Nome do evento para inscrição: ");
                    String nomeEvento = scanner.nextLine();
                    System.out.print("Nome do participante: ");
                    String nomeParticipante = scanner.nextLine();
                    if (gestor.inscreverParticipante(nomeEvento, nomeParticipante)) {
                        System.out.println("Participante inscrito com sucesso!");
                    } else {
                        System.out.println("Não foi possível inscrever o participante. Evento esgotado ou não encontrado.");
                    }
                    break;

                case 4:
                    System.out.print("Nome do evento para busca: ");
                    String nomeBusca = scanner.nextLine();
                    Evento eventoEncontrado = gestor.buscarEvento(nomeBusca);
                    if (eventoEncontrado != null) {
                        System.out.println("Evento encontrado: " +
                                "\nNome: " + eventoEncontrado.getNome() +
                                "\nData: " + eventoEncontrado.getData() +
                                "\nLocal: " + eventoEncontrado.getLocal() +
                                "\nCategoria: " + eventoEncontrado.getCategoria() +
                                "\nVagas disponíveis: " + eventoEncontrado.vagasDisponiveis());
                    } else {
                        System.out.println("Evento não encontrado.");
                    }
                    break;

                case 5:
                    gestor.exportarEventosCSV();
                    break;

                case 6:
                    gestor.importarEventosCSV();
                    break;

                case 7:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 7);

        scanner.close();
    }
}
