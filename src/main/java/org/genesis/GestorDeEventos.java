package org.genesis;


public class GestorDeEventos {
    private Evento[] eventos;
    private int totalEventos;

    public GestorDeEventos() {
        this.eventos = new Evento[10]; // Capacidade máxima de 10 eventos
        this.totalEventos = 0;
    }

    public boolean cadastrarEvento(String nome, String data, String local, int capacidadeMaxima, String categoria) {
        if (totalEventos < 10) {
            Evento novoEvento = new Evento(nome, data, local, capacidadeMaxima, categoria);
            eventos[totalEventos] = novoEvento;
            totalEventos++;
            return true;
        } else {
            return false; // Capacidade de eventos atingida
        }
    }

    public void listarEventos() {
        for (int i = 0; i < totalEventos; i++) {
            Evento evento = eventos[i];
            System.out.println("Nome: " + evento.getNome() +
                    ", Data: " + evento.getData() +
                    ", Local: " + evento.getLocal() +
                    ", Categoria: " + evento.getCategoria() +
                    ", Vagas Disponíveis: " + evento.vagasDisponiveis());
        }
    }

    public Evento buscarEvento(String nome) {
        for (int i = 0; i < totalEventos; i++) {
            if (eventos[i].getNome().equalsIgnoreCase(nome)) {
                return eventos[i];
            }
        }
        return null; // Evento não encontrado
    }

    public boolean inscreverParticipante(String nomeEvento, String nomeParticipante) {
        Evento evento = buscarEvento(nomeEvento);
        if (evento != null) {
            return evento.inscreverParticipante(nomeParticipante);
        } else {
            return false; // Evento não encontrado
        }
    }
}
