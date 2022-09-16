package br.edu.ifsul.cc.lpoo.cv.gui;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 *
 * @author Camila
 */
public class JFramePrincipal extends JFrame implements WindowListener { // herda JFrame da biblioteca Swing
    // JFramePrincipal é uma customização do modelo de base JFrame
    // implements é uma "herança múltipla", implementando os protótipos contidos na interface
    // WindowsListener é uma interface que escuta eventos de janela (maximizar, minimizar...)

    private Controle controle;

    public CardLayout cardLayout; // gerenciador de cartas de layout

    public JPanel painel; // painel

    public JFramePrincipal(Controle controle) { // construtor recebe uma instância de controle

        this.controle = controle;

        initComponents(); // chama o método abaixo

    }

    private void initComponents() {
        //customização do JFrame

        this.setTitle("Veterinária CosmiCare"); //seta o título do jframe

        this.setMinimumSize(new Dimension(700, 700)); //tamanho minimo quando for reduzido -> ajustado para a imagem

        this.setExtendedState(JFrame.MAXIMIZED_BOTH); // por padrão abre maximizado

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// finaliza o processo quando o frame é fechado 

        this.addWindowListener(this); //adiciona o listener no frame (classe que vai "escutar" os eventos na janela)

        cardLayout = new CardLayout(); //iniciando o gerenciador de layout para esta JFrame
        // iniciar o painel
        painel = new JPanel(); //inicializacao
        // adicionar o gerenciador de layout ao painel
        painel.setLayout(cardLayout); //definindo o cardLayout para o paineldeFundo
        // adicionar o painel ao frame
        this.add(painel); //adiciona no JFrame o paineldeFundo

    }

    public void addTela(JPanel p, String nome) {

        painel.add(p, nome); //adiciona uma "carta no baralho"
    }

    public void showTela(String nome) {

        cardLayout.show(painel, nome); //localiza a "carta no baralho" e mostra.
    }

    @Override
    public void windowOpened(WindowEvent we) { // chamado quando a janela é aberta

        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void windowClosing(WindowEvent we) { // chamado quando a janela é fechada

        System.out.println("Fechando o jframe ..");
        controle.fecharBD();

    }

    @Override
    public void windowClosed(WindowEvent we) { // evento que será chamado quando a janela for fechada (matar o processo)
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void windowIconified(WindowEvent we) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void windowActivated(WindowEvent we) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
