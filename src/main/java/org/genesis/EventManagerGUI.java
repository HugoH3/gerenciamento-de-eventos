package org.genesis;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventManagerGUI extends JFrame {
    private GestorDeEventos gestorDeEventos;

    public EventManagerGUI(GestorDeEventos gestorDeEventos) {
        this.gestorDeEventos = gestorDeEventos;
        setTitle("Gestor de Eventos");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton btnCadastrar = new JButton("Cadastrar Evento");
        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CadastrarEventoDialog(gestorDeEventos).setVisible(true);
            }
        });
        add(btnCadastrar);

        JButton btnListar = new JButton("Listar Eventos");
        btnListar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ListarEventosDialog(gestorDeEventos).setVisible(true);
            }
        });
        add(btnListar);

        JButton btnBuscar = new JButton("Buscar Evento");
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BuscarEventoDialog(gestorDeEventos).setVisible(true);
            }
        });
        add(btnBuscar);

        JButton btnInscrever = new JButton("Inscrever Participante");
        btnInscrever.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InscreverParticipanteDialog(gestorDeEventos).setVisible(true);
            }
        });
        add(btnInscrever);

        JButton btnExportar = new JButton("Exportar Eventos para CSV");
        btnExportar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gestorDeEventos.exportarEventosCSV();
                JOptionPane.showMessageDialog(null, "Eventos exportados com sucesso!");
            }
        });
        add(btnExportar);

        JButton btnImportar = new JButton("Importar Eventos de CSV");
        btnImportar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gestorDeEventos.importarEventosCSV();
                JOptionPane.showMessageDialog(null, "Eventos importados com sucesso!");
            }
        });
        add(btnImportar);
    }

    public static void main(String[] args) {
        GestorDeEventos gestorDeEventos = new GestorDeEventos();
        EventManagerGUI gui = new EventManagerGUI(gestorDeEventos);
        gui.setVisible(true);
    }
}

// Janela para cadastrar eventos
class CadastrarEventoDialog extends JDialog {
    private GestorDeEventos gestorDeEventos;
    private JTextField txtNomeEvento;
    private JTextField txtData;
    private JTextField txtLocal;
    private JTextField txtCapacidade;
    private JTextField txtCategoria;

    public CadastrarEventoDialog(GestorDeEventos gestorDeEventos) {
        this.gestorDeEventos = gestorDeEventos;
        setTitle("Cadastrar Evento");
        setSize(300, 300);
        setLayout(new GridLayout(6, 2));

        txtNomeEvento = new JTextField();
        txtData = new JTextField();
        txtLocal = new JTextField();
        txtCapacidade = new JTextField();
        txtCategoria = new JTextField();

        add(new JLabel("Nome do Evento:"));
        add(txtNomeEvento);
        add(new JLabel("Data:"));
        add(txtData);
        add(new JLabel("Local:"));
        add(txtLocal);
        add(new JLabel("Capacidade:"));
        add(txtCapacidade);
        add(new JLabel("Categoria:"));
        add(txtCategoria);

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarEvento();
            }
        });
        add(btnCadastrar);
    }

    private void cadastrarEvento() {
        String nome = txtNomeEvento.getText();
        String data = txtData.getText();
        String local = txtLocal.getText();
        int capacidade;

        try {
            capacidade = Integer.parseInt(txtCapacidade.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Capacidade deve ser um número válido.");
            return;
        }

        String categoria = txtCategoria.getText();

        if (gestorDeEventos.cadastrarEvento(nome, data, local, capacidade, categoria)) {
            JOptionPane.showMessageDialog(this, "Evento cadastrado com sucesso!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Não foi possível cadastrar o evento. Limite de eventos atingido.");
        }
    }
}

// Janela para listar eventos
class ListarEventosDialog extends JDialog {
    private GestorDeEventos gestorDeEventos;
    private JTextArea txtAreaEventos;

    public ListarEventosDialog(GestorDeEventos gestorDeEventos) {
        this.gestorDeEventos = gestorDeEventos;
        setTitle("Listar Eventos");
        setSize(400, 300);
        setLayout(new BorderLayout());

        txtAreaEventos = new JTextArea();
        txtAreaEventos.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaEventos);
        add(scrollPane, BorderLayout.CENTER);

        JButton btnListar = new JButton("Listar Eventos");
        btnListar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarEventos();
            }
        });
        add(btnListar, BorderLayout.SOUTH);
    }

    private void listarEventos() {
        String eventos = gestorDeEventos.listarEventos();
        txtAreaEventos.setText(eventos);
    }
}

// Janela para buscar eventos
class BuscarEventoDialog extends JDialog {
    private GestorDeEventos gestorDeEventos;
    private JTextField txtNomeBusca;
    private JTextArea txtAreaResultado;

    public BuscarEventoDialog(GestorDeEventos gestorDeEventos) {
        this.gestorDeEventos = gestorDeEventos;
        setTitle("Buscar Evento");
        setSize(300, 200);
        setLayout(new BorderLayout());

        txtNomeBusca = new JTextField();
        add(txtNomeBusca, BorderLayout.NORTH);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarEvento();
            }
        });
        add(btnBuscar, BorderLayout.CENTER);

        txtAreaResultado = new JTextArea();
        txtAreaResultado.setEditable(false);
        add(new JScrollPane(txtAreaResultado), BorderLayout.SOUTH);
    }

    private void buscarEvento() {
        String nomeBusca = txtNomeBusca.getText();
        Evento eventoEncontrado = gestorDeEventos.buscarEvento(nomeBusca);

        if (eventoEncontrado != null) {
            txtAreaResultado.setText("Evento encontrado: \n" +
                    "Nome: " + eventoEncontrado.getNome() +
                    "\nData: " + eventoEncontrado.getData() +
                    "\nLocal: " + eventoEncontrado.getLocal() +
                    "\nCategoria: " + eventoEncontrado.getCategoria() +
                    "\nVagas disponíveis: " + eventoEncontrado.vagasDisponiveis());
        } else {
            txtAreaResultado.setText("Evento não encontrado.");
        }
    }
}

// Janela para inscrever participantes
class InscreverParticipanteDialog extends JDialog {
    private GestorDeEventos gestorDeEventos;
    private JTextField txtNomeEvento;
    private JTextField txtNomeParticipante;

    public InscreverParticipanteDialog(GestorDeEventos gestorDeEventos) {
        this.gestorDeEventos = gestorDeEventos;
        setTitle("Inscrever Participante");
        setSize(300, 200);
        setLayout(new GridLayout(3, 2));

        txtNomeEvento = new JTextField();
        txtNomeParticipante = new JTextField();

        add(new JLabel("Nome do Evento:"));
        add(txtNomeEvento);
        add(new JLabel("Nome do Participante:"));
        add(txtNomeParticipante);

        JButton btnInscrever = new JButton("Inscrever");
        btnInscrever.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inscreverParticipante();
            }
        });
        add(btnInscrever);
    }

    private void inscreverParticipante() {
        String nomeEvento = txtNomeEvento.getText();
        String nomeParticipante = txtNomeParticipante.getText();

        if (gestorDeEventos.inscreverParticipante(nomeEvento, nomeParticipante)) {
            JOptionPane.showMessageDialog(this, "Participante inscrito com sucesso!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Não foi possível inscrever o participante. Evento esgotado ou não encontrado.");
        }
    }
}



