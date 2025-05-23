package situacaoDesafiadora;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.awt.Color;

public class Principal {
    public static void main(String[] args) {
        JFrame frmListaDeTarefas = new JFrame("To-Do List");
        frmListaDeTarefas.setTitle("Lista de Tarefas");
        frmListaDeTarefas.getContentPane().setBackground(new Color(128, 0, 255));
        frmListaDeTarefas.setBackground(new Color(128, 0, 255));
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
        btnAdicionar.setForeground(new Color(128, 255, 128));
        btnAdicionar.setBounds(370, 20, 100, 25);
        frmListaDeTarefas.getContentPane().add(btnAdicionar);

        DefaultListModel<String> modelo = new DefaultListModel<>();
        JList<String> lista = new JList<>(modelo);
        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBounds(20, 60, 450, 180);
        frmListaDeTarefas.getContentPane().add(scroll);

        JButton btnRemover = new JButton("Remover");
        btnRemover.setBounds(20, 260, 100, 30);
        frmListaDeTarefas.getContentPane().add(btnRemover);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(140, 260, 100, 30);
        frmListaDeTarefas.getContentPane().add(btnSalvar);

        JButton btnCarregar = new JButton("Carregar");
        btnCarregar.setBounds(260, 260, 100, 30);
        frmListaDeTarefas.getContentPane().add(btnCarregar);

        JButton btnSair = new JButton("Sair");
        btnSair.setForeground(new Color(255, 0, 0));
        btnSair.setBounds(380, 260, 90, 30);
        frmListaDeTarefas.getContentPane().add(btnSair);

        ArrayList<String> tarefas = new ArrayList<>();

        
        btnAdicionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String texto = campo.getText();
                if (!texto.isEmpty()) {
                    tarefas.add(texto);
                    modelo.addElement(texto);
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
                try (BufferedWriter salvar = new BufferedWriter(new FileWriter("tarefas.csv"))) {
                    for (String t : tarefas) {
                        salvar.write(t + "" + ",");
                        
                    }
                    JOptionPane.showMessageDialog(frmListaDeTarefas, "Tarefas salvas!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frmListaDeTarefas, "Erro ao salvar.");
                }
            }
        });

        
        btnCarregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try (BufferedReader reader = new BufferedReader(new FileReader("tarefas.csv"))) {
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

       
        btnSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frmListaDeTarefas.dispose();
            }
        });

        frmListaDeTarefas.setVisible(true);
    }
}
