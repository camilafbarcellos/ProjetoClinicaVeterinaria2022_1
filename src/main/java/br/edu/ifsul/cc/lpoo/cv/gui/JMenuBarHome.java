package br.edu.ifsul.cc.lpoo.cv.gui;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author 20202PF.CC0003
 */
public class JMenuBarHome extends JMenuBar implements ActionListener {

    private JMenu menuArquivo;
    private JMenuItem menuItemHome;
    private JMenuItem menuItemLogout;
    private JMenuItem menuItemSair;

    private JMenu menuCadastro;
    private JMenuItem menuItemVenda;
    private JMenuItem menuItemConsulta;

    private Controle controle;

    public JMenuBarHome(Controle controle) {

        this.controle = controle;

        initComponents();
    }

    private void initComponents() {

        menuArquivo = new JMenu("Arquivo");
        menuArquivo.setMnemonic(KeyEvent.VK_A);//ativa o ALT + A para acessar esse menu - acessibilidade
        menuArquivo.setToolTipText("Arquivo"); //acessibilidade
        menuArquivo.setFocusable(true); //acessibilidade

        menuItemHome = new JMenuItem("Home");
        menuItemHome.setToolTipText("Home"); //acessibilidade
        menuItemHome.setFocusable(true);     //acessibilidade
        
        menuItemSair = new JMenuItem("Sair");
        menuItemSair.setToolTipText("Sair"); //acessibilidade
        menuItemSair.setFocusable(true);     //acessibilidade

        menuItemLogout = new JMenuItem("Logout");
        menuItemLogout.setToolTipText("Logout"); //acessibilidade
        menuItemLogout.setFocusable(true);     //acessibilidade

        menuItemHome.addActionListener(this);
        menuItemHome.setActionCommand("menu_home");
        menuArquivo.add(menuItemHome);
        
        menuItemLogout.addActionListener(this);
        menuItemLogout.setActionCommand("menu_logout");
        menuArquivo.add(menuItemLogout);

        menuItemSair.addActionListener(this);
        menuItemSair.setActionCommand("menu_sair");
        menuArquivo.add(menuItemSair);

        menuCadastro = new JMenu("Cadastros");
        menuCadastro.setMnemonic(KeyEvent.VK_C);//ativa o ALT + C para acessar esse menu - acessibilidade
        menuCadastro.setToolTipText("Cadastro"); //acessibilidade
        menuCadastro.setFocusable(true); //acessibilidade

        menuItemVenda = new JMenuItem("Venda");
        menuItemVenda.setToolTipText("Venda"); //acessibilidade
        menuItemVenda.setFocusable(true); //acessibilidade

        menuItemVenda.addActionListener(this);
        menuItemVenda.setActionCommand("menu_venda");
        menuCadastro.add(menuItemVenda);

        menuItemConsulta = new JMenuItem("Consulta");
        menuItemConsulta.setToolTipText("Consulta"); //acessibilidade
        menuItemConsulta.setFocusable(true); //acessibilidade

        menuItemConsulta.addActionListener(this);
        menuItemConsulta.setActionCommand("menu_Consulta");
        menuCadastro.add(menuItemConsulta);

        this.add(menuArquivo);
        this.add(menuCadastro);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals(menuItemSair.getActionCommand())) {

            //se o usuario clicou no menuitem Sair
            int d = JOptionPane.showConfirmDialog(this, "Deseja realmente sair do sistema? ", "Sair", JOptionPane.YES_NO_OPTION);
            // janela de confirmação com 0-sim e 1-não
            if (d == 0) { // usuario clicou 0-sim
                controle.fecharBD();//fecha a conexao com o banco de dados.
                System.exit(0);//finaliza o processo do programa.
            }

        } else if (e.getActionCommand().equals(menuItemVenda.getActionCommand())) {

            //se o usuario clicou no item Venda do menu            
            controle.showTela("tela_venda");          
        } else if (e.getActionCommand().equals(menuItemLogout.getActionCommand())) {

            //se o usuario clicou no item Logout do menu  
            controle.showTela("tela_autenticacao"); // chama o método para mostrar tela de autenticação 
        } else if (e.getActionCommand().equals(menuItemHome.getActionCommand())) {

            //se o usuario clicou no item Home do menu  
            controle.showTela("tela_home");
        } else if (e.getActionCommand().equals(menuItemConsulta.getActionCommand())) {

            //se o usuario clicou no item Consulta do menu 
            controle.showTela("tela_consulta");
        }

    }

}
