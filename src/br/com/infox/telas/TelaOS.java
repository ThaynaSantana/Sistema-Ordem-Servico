/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.infox.telas;

import br.com.infox.dal.ModuloConexao;
import net.proteanit.sql.DbUtils;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author thay
 */
public class TelaOS extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaOS() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    private void pesquisarCliente() throws SQLException {
        String sql = "select * from clientes where nome like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, input_cliente.getText() + "%");
            rs = pst.executeQuery();
            // a linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela
            table_clientes.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void setarClienteSelecionado() {
        int setar = table_clientes.getSelectedRow();
        String nome_cliente = table_clientes.getModel().getValueAt(setar, 1).toString();
        text_cliente_selecionado.setText("Selecionado o cliente: " + nome_cliente + "!");
    }

    private void limpar() {
        input_equipamento.setText(null);
        input_defeito.setText(null);
        input_servico.setText(null);
        input_tecnico.setText(null);
        input_valor.setText(null);
        txt_data.setText("Data: ");
        txt_os.setText("X");
    }

    private void adicionar() {
        String sql = "insert into ordemservicos(equipamento, defeito, servico, tecnico, valor, id) values(?,?,?,?,?,?) ";
        // Pegando o ID do cliente selecionado na tabela
        String ID = table_clientes.getModel().getValueAt(table_clientes.getSelectedRow(), 0).toString();

        if (ID.equals("")) {
            JOptionPane.showMessageDialog(null, "Nenhum cliente foi selecionado!, pesquise e selecione.", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else if (input_equipamento.getText().equals("") || input_defeito.getText().equals("") || input_servico.getText().equals("") || input_tecnico.getText().equals("") || input_valor.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Preencha TODOS os campos para cadastrar uma ordem de serviço!", "ERROR", JOptionPane.ERROR_MESSAGE);

        } else {
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, input_equipamento.getText());
                pst.setString(2, input_defeito.getText());
                pst.setString(3, input_servico.getText());
                pst.setString(4, input_tecnico.getText());
                // .replace substitui a virgula pelo ponto
                pst.setString(5, input_valor.getText().replace(",", "."));
                pst.setString(6, ID);

                // atualizando o bando db com dados novos
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Cadastro de Ordem de Serviço feito com sucesso!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                System.out.println(e);
            }
        }
    }

    private void consultar() {
        String numero_os = JOptionPane.showInputDialog("Insira o número da OS");
        String sql = "select * from ordemservicos where os= " + numero_os;
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                txt_os.setText(rs.getString(1));
                txt_data.setText("Data: " + rs.getString(2));
                input_equipamento.setText(rs.getString(3));
                input_defeito.setText(rs.getString(4));
                input_servico.setText(rs.getString(5));
                input_tecnico.setText(rs.getString(6));
                input_valor.setText(rs.getString(7));

                // desativando os outros botões para evitar bugs
                botao_adicionar.setEnabled(false);
                input_cliente.setEnabled(false);
                table_clientes.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Numero de ordem serviço não existente.");
            }
        } catch (java.sql.SQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null, "Ordem de serviço invalida.");

        } catch (Exception e2) {
            JOptionPane.showMessageDialog(null, e2);
        }
    }

    private void editar() {
        String sql = "update ordemservicos set equipamento=?, defeito=?, servico=?, tecnico=?, valor=? where os=?";
        if (input_equipamento.getText().equals("") || input_defeito.getText().equals("") || input_servico.getText().equals("") || input_tecnico.getText().equals("") || input_valor.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Consulte antes para alterar uma ordem de serviço!", "ERROR", JOptionPane.ERROR_MESSAGE);

        } else {
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, input_equipamento.getText());
                pst.setString(2, input_defeito.getText());
                pst.setString(3, input_servico.getText());
                pst.setString(4, input_tecnico.getText());
                // .replace substitui a virgula pelo ponto
                pst.setString(5, input_valor.getText().replace(",", "."));
                pst.setString(6, txt_os.getText());
                // atualizando o bando db com dados novos
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Cadastro de Ordem de Serviço feito com sucesso!");
                limpar();
                // habilitar os objetos desativados
                botao_adicionar.setEnabled(true);
                input_cliente.setEnabled(true);
                table_clientes.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                System.out.println(e);
            }
        }
    }

    private void excluir() {
        String sql = "delete from ordemservicos where os=?";
        if(txt_os.getText().equals("X")){
            JOptionPane.showMessageDialog(null,"Nenhuma Ordem de serviço foi selecionada, não é possivel fazer a excluzão... consulte uma OS antes de tentar excluir");
        } else {
            int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover esta ordem de serviço?", "ATENÇÃO", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txt_os.getText());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Ordem de serviço deletado com sucesso.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        input_equipamento = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        input_servico = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        input_valor = new javax.swing.JTextField();
        botao_editar = new javax.swing.JToggleButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_clientes = new javax.swing.JTable();
        input_cliente = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        botao_pesquisar = new javax.swing.JButton();
        text_cliente_selecionado = new javax.swing.JLabel();
        txt_data = new javax.swing.JLabel();
        input_defeito = new javax.swing.JTextField();
        input_tecnico = new javax.swing.JTextField();
        botao_excluir = new javax.swing.JToggleButton();
        botao_consultar = new javax.swing.JToggleButton();
        botao_adicionar = new javax.swing.JToggleButton();
        botao_imprimir = new javax.swing.JToggleButton();
        label_os = new javax.swing.JLabel();
        txt_os = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Ordem de Serviço");
        setPreferredSize(new java.awt.Dimension(600, 510));
        setRequestFocusEnabled(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Marca + Modelo:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Diagnostico:");

        input_equipamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_equipamentoActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Serviço a ser feito:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Nome do Tecnico(a):");

        input_servico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_servicoActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Valor (R$):");

        input_valor.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        input_valor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_valorActionPerformed(evt);
            }
        });

        botao_editar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/update.png"))); // NOI18N
        botao_editar.setMinimumSize(new java.awt.Dimension(50, 51));
        botao_editar.setPreferredSize(new java.awt.Dimension(30, 31));
        botao_editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_editarActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));

        table_clientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Nome do Cliente", "Celular", "Email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_clientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_clientesMouseClicked(evt);
            }
        });
        table_clientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                table_clientesKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(table_clientes);

        input_cliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                input_clienteKeyReleased(evt);
            }
        });

        jLabel7.setText("Nome do Cliente");

        botao_pesquisar.setText("Pesquisar");
        botao_pesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_pesquisarActionPerformed(evt);
            }
        });
        botao_pesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                botao_pesquisarKeyReleased(evt);
            }
        });

        text_cliente_selecionado.setText("Selecionado nenhum cliente á ordem de serviço!");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(text_cliente_selecionado))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(input_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botao_pesquisar)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(input_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(text_cliente_selecionado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(botao_pesquisar)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        txt_data.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        txt_data.setForeground(new java.awt.Color(255, 0, 51));
        txt_data.setText("Data: DD/MM/AAAA");

        input_defeito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_defeitoActionPerformed(evt);
            }
        });

        input_tecnico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_tecnicoActionPerformed(evt);
            }
        });

        botao_excluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/delete.png"))); // NOI18N
        botao_excluir.setMinimumSize(new java.awt.Dimension(50, 51));
        botao_excluir.setPreferredSize(new java.awt.Dimension(30, 31));
        botao_excluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_excluirActionPerformed(evt);
            }
        });

        botao_consultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/search_icon_64.png"))); // NOI18N
        botao_consultar.setMinimumSize(new java.awt.Dimension(50, 51));
        botao_consultar.setPreferredSize(new java.awt.Dimension(30, 31));
        botao_consultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_consultarActionPerformed(evt);
            }
        });

        botao_adicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/create.png"))); // NOI18N
        botao_adicionar.setMinimumSize(new java.awt.Dimension(50, 51));
        botao_adicionar.setPreferredSize(new java.awt.Dimension(30, 31));
        botao_adicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_adicionarActionPerformed(evt);
            }
        });

        botao_imprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/print.png"))); // NOI18N
        botao_imprimir.setMinimumSize(new java.awt.Dimension(50, 51));
        botao_imprimir.setPreferredSize(new java.awt.Dimension(30, 31));

        label_os.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        label_os.setForeground(new java.awt.Color(0, 102, 255));
        label_os.setText("N° OS:   ");
        label_os.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txt_os.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        txt_os.setForeground(new java.awt.Color(0, 102, 255));
        txt_os.setText("X");
        txt_os.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(input_tecnico, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(input_equipamento, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(input_servico, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(input_defeito, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22))
                            .addComponent(input_valor, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(label_os)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_os)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_data)
                        .addGap(140, 140, 140))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botao_editar, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(botao_consultar, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(botao_imprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(botao_excluir, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(botao_adicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_data)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_os)
                        .addComponent(label_os)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(input_equipamento, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addGap(10, 10, 10)
                        .addComponent(input_defeito, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(input_servico, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(input_tecnico, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(input_valor, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botao_adicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botao_imprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botao_consultar, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botao_excluir, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botao_editar, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void input_equipamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_equipamentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_input_equipamentoActionPerformed

    private void input_servicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_servicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_input_servicoActionPerformed

    private void input_valorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_valorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_input_valorActionPerformed

    private void input_defeitoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_defeitoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_input_defeitoActionPerformed

    private void input_tecnicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_tecnicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_input_tecnicoActionPerformed

    private void botao_consultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_consultarActionPerformed
        // chamando metodo para pesqusar/consultar a ordem de serviço
        consultar();
    }//GEN-LAST:event_botao_consultarActionPerformed

    private void botao_adicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_adicionarActionPerformed

        try {
            adicionar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Preencha TODOS os campos e SELECIONE um cliente á ordem de serviço", "ERROR", JOptionPane.ERROR_MESSAGE);

        }

    }//GEN-LAST:event_botao_adicionarActionPerformed

    private void table_clientesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_table_clientesKeyReleased

    }//GEN-LAST:event_table_clientesKeyReleased

    private void input_clienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_input_clienteKeyReleased

    }//GEN-LAST:event_input_clienteKeyReleased

    private void botao_pesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_botao_pesquisarKeyReleased

    }//GEN-LAST:event_botao_pesquisarKeyReleased

    private void botao_pesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_pesquisarActionPerformed
        try {
            // pesquisando o cliente e mostrando na tabela
            pesquisarCliente();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_botao_pesquisarActionPerformed

    private void table_clientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_clientesMouseClicked
        try {
            setarClienteSelecionado();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_table_clientesMouseClicked

    private void botao_editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_editarActionPerformed
        try {
            editar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_botao_editarActionPerformed

    private void botao_excluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_excluirActionPerformed
        try {
            excluir();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_botao_excluirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton botao_adicionar;
    private javax.swing.JToggleButton botao_consultar;
    private javax.swing.JToggleButton botao_editar;
    private javax.swing.JToggleButton botao_excluir;
    private javax.swing.JToggleButton botao_imprimir;
    private javax.swing.JButton botao_pesquisar;
    private javax.swing.JTextField input_cliente;
    private javax.swing.JTextField input_defeito;
    private javax.swing.JTextField input_equipamento;
    private javax.swing.JTextField input_servico;
    private javax.swing.JTextField input_tecnico;
    private javax.swing.JTextField input_valor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel label_os;
    private javax.swing.JTable table_clientes;
    private javax.swing.JLabel text_cliente_selecionado;
    private javax.swing.JLabel txt_data;
    private javax.swing.JLabel txt_os;
    // End of variables declaration//GEN-END:variables
}
