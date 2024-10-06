package org.genesis;

import java.util.ArrayList;

public class Evento {
    private String nome;
    private String data;
    private String local;
    private int capacidadeMaxima;
    private int inscritosAtuais;
    private String categoria;
    private ArrayList<String> participantes;

    public Evento(String nome, String data, String local, int capacidadeMaxima, String categoria) {
        this.nome = nome;
        this.data = data;
        this.local = local;
        this.capacidadeMaxima = capacidadeMaxima;
        this.categoria = categoria;
        this.participantes = new ArrayList<>();
        this.inscritosAtuais = 0;
    }

    public String getNome() {
        return nome;
    }

    public String getData() {
        return data;
    }

    public String getLocal() {
        return local;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getInscritosAtuais() {
        return inscritosAtuais;
    }

    public int vagasDisponiveis() {
        return capacidadeMaxima - inscritosAtuais;
    }

    public boolean inscreverParticipante(String nomeParticipante) {
        if (inscritosAtuais < capacidadeMaxima) {
            participantes.add(nomeParticipante);
            inscritosAtuais++;
            return true;
        } else {
            return false;
        }
    }
}

