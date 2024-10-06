package org.genesis;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

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

    
    public void exportarEventosCSV() {
        try (FileWriter escritor = new FileWriter("eventos.csv");
             CSVPrinter printer = new CSVPrinter(escritor, CSVFormat.DEFAULT.withHeader("Nome", "Data", "Local", "Capacidade", "Categoria"))) {

            for (int i = 0; i < totalEventos; i++) {
                Evento evento = eventos[i];
                printer.printRecord(evento.getNome(), evento.getData(), evento.getLocal(), evento.getCapacidadeMaxima(), evento.getCategoria());
            }

            System.out.println("Eventos exportados com sucesso para 'eventos.csv'.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public void importarEventosCSV() {
        try (Reader leitor = new FileReader("eventos.csv")) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader("Nome", "Data", "Local", "Capacidade", "Categoria").withSkipHeaderRecord().parse(leitor);

            for (CSVRecord record : records) {
                String nome = record.get("Nome");
                String data = record.get("Data");
                String local = record.get("Local");
                int capacidadeMaxima = Integer.parseInt(record.get("Capacidade"));
                String categoria = record.get("Categoria");

                if (totalEventos < 10) {
                    cadastrarEvento(nome, data, local, capacidadeMaxima, categoria);
                } else {
                    System.out.println("Limite de eventos atingido, alguns eventos não foram importados.");
                    break;
                }
            }

            System.out.println("Eventos importados com sucesso de 'eventos.csv'.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
