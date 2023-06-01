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
public class TelaUsuario extends javax.swing.JInternalFrame {
    Connection conexao = null;
    PreparedStatement preparedstate = null;
    ResultSet resultset = null;
    /**
     * Creates new form TelaUsuario
     */
    public TelaUsuario() {
        initComponents();
        conexao =  ModuloConexao.conector();
    }
    
    private void consultar(){
        String sql= "select * from usuarios where id=?";
        String id = input_id.getText();
        try {
            preparedstate= conexao.prepareStatement(sql);
            preparedstate.setString(1,id);
            resultset= preparedstate.executeQuery();
            if (resultset.next()) {
                input_nome.setText(resultset.getString(2));
                input_tel.setText(resultset.getString(3));
                input_login.setText(resultset.getString(4));
                input_password.setText(resultset.getString(5));
                // a linha abaixo seta o item do check box
                input_perfil.setSelectedItem(resultset.getString(6));
            } else {
                JOptionPane.showMessageDialog(null, "Usuario não cadastrado.");
                limpar();
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }
        
        
    }
    // Funçao para limpar os campos
    private void limpar(){
        input_id.setText(null);
        input_nome.setText(null);
        input_tel.setText(null);
        input_login.setText(null);
        input_password.setText(null);
        input_perfil.setSelectedItem("Selecione");
    }
   
    
    private void adicionar(){
        String sql = "insert into usuarios(usuario,tel,login,senha,perfil) values (?,?,?,?,?)";
        
            // se campos vazios nao ira cadastrar
            if (input_nome.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Campo NOME não foi preenchido!","ERROR",JOptionPane.ERROR_MESSAGE);
                limpar();
            } else if (input_login.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Campo LOGIN não foi preenchido!","ERROR",JOptionPane.ERROR_MESSAGE);
                limpar();
            } else if (input_password.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Campo SENHA não foi preenchido!","ERROR",JOptionPane.ERROR_MESSAGE);
                limpar();
            } else if (input_perfil.getSelectedItem().equals("Selecione")){
                JOptionPane.showMessageDialog(null, "Campo de PERFIL não foi selecionado!","ERROR",JOptionPane.ERROR_MESSAGE);
                limpar();
            } else {
                try {
                    preparedstate= conexao.prepareStatement(sql);
                    preparedstate.setString(1, input_nome.getText());
                    preparedstate.setString(2, input_tel.getText());
                    preparedstate.setString(3, input_login.getText());
                    preparedstate.setString(4, input_password.getText());
                    preparedstate.setString(5, input_perfil.getSelectedItem().toString());
                    // atualizando o bando db com dados novos
                    preparedstate.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Cadastro de usuario feito com sucesso");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,e);
                }
            }
            
    }
    
    private void atualizar(){
        String sql="update usuarios set usuario=?,tel=?,login=?,senha=?,perfil=? where id=?";
        
            // se campos vazios nao ira cadastrar
            if (input_nome.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Campo NOME não foi preenchido!","ERROR",JOptionPane.ERROR_MESSAGE);
                limpar();
            } else if (input_login.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Campo LOGIN não foi preenchido!","ERROR",JOptionPane.ERROR_MESSAGE);
                limpar();
            } else if (input_password.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Campo SENHA não foi preenchido!","ERROR",JOptionPane.ERROR_MESSAGE);
                limpar();
            } else if (input_perfil.getSelectedItem().equals("Selecione")){
                JOptionPane.showMessageDialog(null, "Campo de PERFIL não foi selecionado!","ERROR",JOptionPane.ERROR_MESSAGE);
                limpar();
            } else if (input_id.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Campo de ID não foi selecionado!","ERROR",JOptionPane.ERROR_MESSAGE);
                limpar();
            } else {
                try {
                    preparedstate= conexao.prepareStatement(sql);
                    preparedstate.setString(1, input_nome.getText());
                    preparedstate.setString(2, input_tel.getText());
                    preparedstate.setString(3, input_login.getText());
                    preparedstate.setString(4, input_password.getText());
                    preparedstate.setString(5, input_perfil.getSelectedItem().toString());
                    preparedstate.setString(6, input_id.getText());
                    // a estrutura aqui serve para alterar os dados de um usuario
                    preparedstate.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Alteraçao de usuario feita com sucesso");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,e);
                }
            }
        
    }
    
    private void deletar(){
       String sql="delete from usuarios where id=?";
       int confirm=JOptionPane.showConfirmDialog(null,"Tem certeza que deseja remover este usuario?","ATENÇÃO",JOptionPane.YES_NO_OPTION);
       if (confirm == JOptionPane.YES_OPTION){
           try {
                preparedstate=conexao.prepareStatement(sql);
                preparedstate.setString(1, input_id.getText());
                preparedstate.executeUpdate();
                JOptionPane.showMessageDialog(null, "Usuario deletado com sucesso");
           } catch (Exception e) {
               JOptionPane.showMessageDialog(null,e);
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
        input_nome = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        input_tel = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        input_login = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        input_perfil = new javax.swing.JComboBox<>();
        btn_create_user = new javax.swing.JButton();
        btn_read_user = new javax.swing.JButton();
        btn_update_user = new javax.swing.JButton();
        btn_delete_user = new javax.swing.JButton();
        lbl_id = new javax.swing.JLabel();
        input_id = new javax.swing.JTextField();
        input_password = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Controle de Usuarios");
        setPreferredSize(new java.awt.Dimension(600, 510));

        jLabel1.setText("Nome *");

        input_nome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_nomeActionPerformed(evt);
            }
        });

        jLabel2.setText("Telefone");

        input_tel.setToolTipText("");
        input_tel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_telActionPerformed(evt);
            }
        });

        jLabel3.setText("Login *");

        input_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_loginActionPerformed(evt);
            }
        });

        jLabel4.setText("Senha  *");

        jLabel5.setText("Tipo de Perfil *");

        input_perfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "auxiliar", "financeiro", "tecnico", "admin" }));
        input_perfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_perfilActionPerformed(evt);
            }
        });

        btn_create_user.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/create.png"))); // NOI18N
        btn_create_user.setToolTipText("Criar");
        btn_create_user.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_create_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_create_userActionPerformed(evt);
            }
        });

        btn_read_user.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/read.png"))); // NOI18N
        btn_read_user.setToolTipText("Consultar");
        btn_read_user.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_read_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_read_userActionPerformed(evt);
            }
        });

        btn_update_user.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/update.png"))); // NOI18N
        btn_update_user.setToolTipText("Atualizar");
        btn_update_user.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_update_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_update_userActionPerformed(evt);
            }
        });

        btn_delete_user.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/delete.png"))); // NOI18N
        btn_delete_user.setToolTipText("Apagar");
        btn_delete_user.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_delete_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delete_userActionPerformed(evt);
            }
        });

        lbl_id.setText("ID *");
        lbl_id.setEnabled(false);

        input_id.setToolTipText("");
        input_id.setEnabled(false);
        input_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_idActionPerformed(evt);
            }
        });

        input_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_passwordActionPerformed(evt);
            }
        });

        jLabel6.setText("Cadastrar");

        jLabel7.setText("Consultar");

        jLabel8.setText("Alterar");

        jLabel9.setText("Deletar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3)
                    .addComponent(input_login)
                    .addComponent(jLabel2)
                    .addComponent(input_tel)
                    .addComponent(jLabel1)
                    .addComponent(input_nome, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 147, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_id)
                    .addComponent(input_id, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5))
                            .addGap(133, 133, 133))
                        .addComponent(input_perfil, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(input_password, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addGap(59, 59, 59))
            .addGroup(layout.createSequentialGroup()
                .addGap(115, 115, 115)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_create_user, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel6)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btn_read_user, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel7)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btn_update_user, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel8)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btn_delete_user))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel9)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btn_create_user, btn_delete_user, btn_read_user, btn_update_user});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jLabel1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(input_perfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(input_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lbl_id)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(input_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(input_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(input_tel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(input_login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(115, 115, 115)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_delete_user, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_create_user, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_read_user, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_update_user, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6))
                .addGap(87, 87, 87))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn_create_user, btn_delete_user, btn_read_user, btn_update_user});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void input_nomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_nomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_input_nomeActionPerformed

    private void input_telActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_telActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_input_telActionPerformed

    private void input_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_loginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_input_loginActionPerformed

    private void input_perfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_perfilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_input_perfilActionPerformed

    private void input_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_input_idActionPerformed

    private void btn_read_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_read_userActionPerformed
        // Ativar o input ID
        if(input_id.isEnabled()){
            consultar();
        }
        input_id.setEnabled(true);
        lbl_id.setEnabled(true);
        
    }//GEN-LAST:event_btn_read_userActionPerformed

    private void input_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_input_passwordActionPerformed

    private void btn_create_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_create_userActionPerformed
        // chamando o metodo adicionar
        if(input_id.isEnabled()){
            input_id.setEnabled(false);
            lbl_id.setEnabled(false);
            limpar();
        } else {
            adicionar();
        }
    }//GEN-LAST:event_btn_create_userActionPerformed

    private void btn_update_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_update_userActionPerformed
        // chamando o metodo atualizar
        if(input_id.isEnabled()){
            atualizar();
        } else {
            input_id.setEnabled(true);
            lbl_id.setEnabled(true);
        }
    }//GEN-LAST:event_btn_update_userActionPerformed

    private void btn_delete_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delete_userActionPerformed
        // TODO add your handling code here:
        if(input_id.isEnabled()){
            deletar();
        } else {
            input_id.setEnabled(true);
            lbl_id.setEnabled(true);
        }
        
    }//GEN-LAST:event_btn_delete_userActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_create_user;
    private javax.swing.JButton btn_delete_user;
    private javax.swing.JButton btn_read_user;
    private javax.swing.JButton btn_update_user;
    private javax.swing.JTextField input_id;
    private javax.swing.JTextField input_login;
    private javax.swing.JTextField input_nome;
    private javax.swing.JTextField input_password;
    private javax.swing.JComboBox<String> input_perfil;
    private javax.swing.JTextField input_tel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lbl_id;
    // End of variables declaration//GEN-END:variables
}
