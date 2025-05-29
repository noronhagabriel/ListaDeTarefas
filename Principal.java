package situacaoDesafiadora;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.awt.Color;

public class Principal {
    public static void main(String[] args) {
        JFrame frmListaDeTarefas = new JFrame("Lista de Tarefas");
        frmListaDeTarefas.setTitle("Lista de Tarefas");
        frmListaDeTarefas.getContentPane().setBackground(new Color(128, 0, 255));
        frmListaDeTarefas.setSize(500, 350);
        frmListaDeTarefas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmListaDeTarefas.getContentPane().setLayout(null);

        JLabel lbl = new JLabel("Tarefa:");
        lbl.setBounds(10, 22, 100, 20);
        frmListaDeTarefas.getContentPane().add(lbl);

        JTextField campo = new JTextField();
        campo.setBounds(120, 20, 240, 25);
        frmListaDeTarefas.getContentPane().add(campo);

        JButton btnAdicionar = new JButton("Adicionar");
        btnAdicionar.setBounds(370, 20, 100, 25);
        btnAdicionar.setForeground(new Color(128, 255, 128));
        frmListaDeTarefas.getContentPane().add(btnAdicionar);

        DefaultListModel<String> modelo = new DefaultListModel<>();
        JList<String> lista = new JList<>(modelo);
        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBounds(20, 60, 450, 180);
        frmListaDeTarefas.getContentPane().add(scroll);

        JButton btnMudarStatus = new JButton("Mudar Status");
        btnMudarStatus.setBounds(20, 260, 110, 30);
        frmListaDeTarefas.getContentPane().add(btnMudarStatus);

        JButton btnRemover = new JButton("Remover");
        btnRemover.setBounds(140, 260, 100, 30);
        frmListaDeTarefas.getContentPane().add(btnRemover);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(250, 260, 100, 30);
        frmListaDeTarefas.getContentPane().add(btnSalvar);

        JButton btnCarregar = new JButton("Carregar");
        btnCarregar.setBounds(360, 260, 100, 30);
        frmListaDeTarefas.getContentPane().add(btnCarregar);

        ArrayList<String> tarefas = new ArrayList<>();
        String status = " Pendente";

        btnAdicionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String texto = campo.getText().trim();
                if (!texto.isEmpty()) {
                    String tarefaCompleta = texto + status;
                    tarefas.add(tarefaCompleta);
                    modelo.addElement(tarefaCompleta);
                    campo.setText("");
                }
            }
        });

        btnRemover.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int remover = lista.getSelectedIndex();
                if (remover != -1) {
                    tarefas.remove(remover);
                    modelo.remove(remover);
                }
            }
        });

        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try (var salvar = new BufferedWriter(new FileWriter("tarefas.csv"))) {
                    for (String t : tarefas) {
                        salvar.write(t);
                        salvar.newLine();
                    }
                    JOptionPane.showMessageDialog(frmListaDeTarefas, "Tarefas salvas!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frmListaDeTarefas, "Erro ao salvar.");
                }
            }
        });

        btnCarregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try (var reader = new BufferedReader(new FileReader("tarefas.csv"))) {
                    String linha;
                    tarefas.clear();
                    modelo.clear();
                    while ((linha = reader.readLine()) != null) {
                        tarefas.add(linha);
                        modelo.addElement(linha);
                    }
                    JOptionPane.showMessageDialog(frmListaDeTarefas, "Tarefas carregadas!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frmListaDeTarefas, "Erro ao carregar.");
                }
            }
        });

        btnMudarStatus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = lista.getSelectedIndex();
                if (index != -1) {
                    String tarefa = tarefas.get(index);
                    if (tarefa.contains("Pendente")) {
                        tarefa = tarefa.replace("Pendente", "Concluída");
                    } else if (tarefa.contains("Concluída")) {
                        tarefa = tarefa.replace("Concluída", "Pendente");
                    }
                    tarefas.set(index, tarefa);
                    modelo.set(index, tarefa);
                }
            }
        });

        frmListaDeTarefas.setVisible(true);
    }
}
