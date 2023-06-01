/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.infox.telas;

/**
 *
 * @author thay
 */
import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import javax.swing.JOptionPane;
// importar recursos da biblioteca rs2xml.java
import net.proteanit.sql.DbUtils;

public class TelaCliente extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaCliente
     */
    public TelaCliente() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    // Funçao para limpar os campos
    private void limpar() {
        input_nome.setText(null);
        input_tel.setText(null);
        input_email.setText(null);
        input_id.setText(null);
    }

    private void adicionar() {
        String sql = "insert into clientes(nome,tel,email) values(?,?,?)";

        // se campos vazios nao ira cadastrar
        if (input_nome.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Campo NOME não foi preenchido!", "ERROR", JOptionPane.ERROR_MESSAGE);
            limpar();
        } else if (input_tel.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Campo CELULAR não foi preenchido!", "ERROR", JOptionPane.ERROR_MESSAGE);
            limpar();
        } else {
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, input_nome.getText());
                pst.setString(2, input_tel.getText());
                pst.setString(3, input_email.getText());

                // atualizando o bando db com dados novos
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Cadastro de cliente feito com sucesso");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

    }

    // metodo para pesquisar clientes pelo nome com filtro
    public void pesquisarClientes() {
        String sql = "select * from clientes where nome like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, input_pesquisa.getText() + "%");
            rs=pst.executeQuery();
            
            // a linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela
            table_clients.setModel(DbUtils.resultSetToTableModel(rs));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void doubleClickGetDatas() {
        int setar = table_clients.getSelectedRow();
        input_id.setText(table_clients.getModel().getValueAt(setar,0).toString());
        input_nome.setText(table_clients.getModel().getValueAt(setar,1).toString());
        input_tel.setText(table_clients.getModel().getValueAt(setar,2).toString());
        input_email.setText(table_clients.getModel().getValueAt(setar,3).toString());
    }

    private void atualizar() {
        String sql = "update clientes set nome=?,tel=?,email=? where id=?";

        // se campos vazios nao ira cadastrar
        if (input_nome.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Campo NOME não foi preenchido!", "ERROR", JOptionPane.ERROR_MESSAGE);
            limpar();
        } else if (input_tel.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Campo CELULAR não foi preenchido!", "ERROR", JOptionPane.ERROR_MESSAGE);
            limpar();
        }else if(input_id.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Campo ID não foi preenchido!", "ERROR", JOptionPane.ERROR_MESSAGE);
            limpar();
        } else {
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, input_nome.getText());
                pst.setString(2, input_tel.getText());
                pst.setString(3, input_email.getText());
                pst.setString(4, input_id.getText());
                // a estrutura aqui serve para alterar os dados de um usuario
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Alteração do cliente feita com sucesso");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

    }

    private void deletar() {
        String sql = "delete from clientes where nome=?";
        int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este cliente?", "ATENÇÃO", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, input_nome.getText());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Cliente deletado com sucesso.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
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

        input_pesquisa = new javax.swing.JTextField();
        btn_search = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_clients = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        input_nome = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        input_email = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        input_tel = new javax.swing.JTextField();
        btn_delete = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        btn_add = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        input_id = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Clientes");
        setPreferredSize(new java.awt.Dimension(600, 510));

        input_pesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                input_pesquisaKeyReleased(evt);
            }
        });

        btn_search.setText("Pesquisar");

        table_clients = new javax.swing.JTable() {
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        table_clients.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nome", "Celular", "Email", "OS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_clients.setFocusable(false);
        table_clients.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_clientsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_clients);

        jLabel1.setText("Nome:");

        jLabel2.setText("Email:");

        jLabel3.setText("Celular:");

        btn_delete.setText("Excluir");
        btn_delete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        btn_update.setText("Editar");
        btn_update.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });

        btn_add.setText("Adicionar");
        btn_add.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        jLabel4.setText("ID:");

        input_id.setEditable(false);
        input_id.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(input_pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_search))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(input_id, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(94, 94, 94)
                                .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_update, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_add))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(118, 118, 118)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(4, 4, 4)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(input_nome)
                                            .addComponent(input_tel, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(17, 17, 17)
                                        .addComponent(input_email, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(input_pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_search))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(input_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(input_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(input_tel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(input_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_update)
                    .addComponent(btn_add)
                    .addComponent(btn_delete))
                .addGap(31, 31, 31))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        // chamando metodo adicionar
        adicionar();
    }//GEN-LAST:event_btn_addActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        // chamando metodo atualizar
        atualizar();
    }//GEN-LAST:event_btn_updateActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // chamando metodo delete
        deletar();
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void input_pesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_input_pesquisaKeyReleased
        // evento abaixo é do tipo "enquanto for digitando"
        // chamar o meotodo pesquisar cliente
        pesquisarClientes();
    }//GEN-LAST:event_input_pesquisaKeyReleased

    private void table_clientsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_clientsMouseClicked
        // evento que sera usado para setar os campos da tabela
        // chamando o metodo para setar campos
        doubleClickGetDatas();
    }//GEN-LAST:event_table_clientsMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_search;
    private javax.swing.JButton btn_update;
    private javax.swing.JTextField input_email;
    private javax.swing.JTextField input_id;
    private javax.swing.JTextField input_nome;
    private javax.swing.JTextField input_pesquisa;
    private javax.swing.JTextField input_tel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_clients;
    // End of variables declaration//GEN-END:variables
}
