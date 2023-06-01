
package br.com.infox.telas;

import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author thay
 */
public class TelaLogin extends javax.swing.JFrame {
    // variaveis para conexao do banco
    // PS e RS sao frameworks do pacote java.sql
   // server para preparar e executar as instruções SQL 
    Connection conexao = null;
    PreparedStatement preparedstate = null;
    ResultSet resultset = null;
    
    public void logar() throws SQLException{
        String sql="select * from usuarios where login=? and senha=?";
        // as linhas abaixo preparam a consulta ao banco em função do
            //que foi digitado nos inputs. O ? e substituido pelo
            // oncteudo das variaveis
            preparedstate = conexao.prepareStatement(sql);
            preparedstate.setString(1, input_login.getText());
            String captura = new String(input_password.getPassword());
            preparedstate.setString(2, captura);
            // executa a query
            resultset = preparedstate.executeQuery();
        try {
            // autenticacao de loing e senha correspondentes
            if (resultset.next()) {
                //obtem o conteudo do campo perfil da tabela usuario
                String perfil=resultset.getString(6);
               // pegando o nome do usuario no banco de dados
               String nome = resultset.getString(2);
             
                if(perfil.equals("admin")){
                    // exibe o a tela Principal
                    TelaPrincipal principal = new TelaPrincipal();
                    principal.setVisible(true);
                    this.dispose();
                    TelaPrincipal.menu_relatorio.setEnabled(true);
                    TelaPrincipal.menu_usuarios.setEnabled(true);
                    
                    conexao.close();
                } else if(perfil.equals("tecnico")){
                    // exibe o a tela Principal
                    TelaPrincipal principal = new TelaPrincipal();
                    principal.setVisible(true);
                    this.dispose();
                    TelaPrincipal.menu_relatorio.setEnabled(true);
                    conexao.close();
                } else if(perfil.equals("financeiro")){
                    // exibe o a tela Principal
                    TelaPrincipal principal = new TelaPrincipal();
                    principal.setVisible(true);
                    this.dispose();
                    TelaPrincipal.menu_relatorio.setEnabled(true);
                    conexao.close();
                } else {
                    // exibe o a tela Principal
                    TelaPrincipal principal = new TelaPrincipal();
                    principal.setVisible(true);
                    this.dispose();
                    conexao.close();
                }
                // inserindo o nome na tela principal
                    TelaPrincipal.lbl_usuario.setText(nome);
            } else {
                JOptionPane.showMessageDialog(null,"usuario e/ou senha incorretos!");
            }
        } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
        } finally {
            conexao.close();
    }
    }

    /**
     * Creates new form TelaLogin
     */
    public TelaLogin() {
        initComponents();
        conexao =  ModuloConexao.conector();
        // mostrar o status da conexao
        //System.out.println(conexao);
        if (conexao != null) {
            lbl_status.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/database_success_icon_small.png")));
        } else {
            lbl_status.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/database_fail_icon_small.png")));
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
        input_login = new javax.swing.JTextField();
        btn_login = new javax.swing.JButton();
        input_password = new javax.swing.JPasswordField();
        lbl_status = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tela de login");
        setLocation(new java.awt.Point(0, 0));
        setResizable(false);

        jLabel1.setText("Usuario");

        jLabel2.setText("Senha");

        btn_login.setText("Entrar");
        btn_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_loginActionPerformed(evt);
            }
        });
        btn_login.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btn_loginKeyPressed(evt);
            }
        });

        lbl_status.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/database_fail_icon_small.png"))); // NOI18N
        lbl_status.setMaximumSize(new java.awt.Dimension(50, 50));
        lbl_status.setMinimumSize(new java.awt.Dimension(50, 50));
        lbl_status.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_login)
                .addGap(25, 25, 25))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(input_login)
                    .addComponent(input_password, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                .addComponent(lbl_status, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel1)
                        .addGap(5, 5, 5)
                        .addComponent(input_login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbl_status, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)))
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(input_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_login)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_loginActionPerformed
        try {
            // chamando o metodo logar
            logar();
        } catch (SQLException ex) {
            Logger.getLogger(TelaLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_loginActionPerformed

    private void btn_loginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_loginKeyPressed
        try {
            // chamando o metodo logar
            logar();
        } catch (SQLException ex) {
            Logger.getLogger(TelaLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_loginKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_login;
    private javax.swing.JTextField input_login;
    private javax.swing.JPasswordField input_password;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lbl_status;
    // End of variables declaration//GEN-END:variables
}
