
package br.edu.ifsul.cc.lpoo.cv;

import javax.swing.JOptionPane;

/**
 *
 * @author Prof. Telmo Júnior
 * 
 * Avaliação da Segunda Etapa. 
 */
public class CVMain {
    
    private Controle controle; // inicializa a variável de controle que vai intermediar a manipulaçãp da gui

    // método construtor
    public CVMain() {

        //primeiramente - estabelecer uma conexao com o banco de dados -> sem ela não é necessário exibir a gui
        try {
            controle = new Controle(); //cria a instancia e atribui para o atributo controle.

            //"caminho feliz" : passo 3
            if (controle.conectarBD()) { // caso a conexão retorne verdadeiro

                //"caminho feliz" : passo 4
                controle.initComponents();

            } else { // caso a conexão retorne falso

                JOptionPane.showMessageDialog(null, "Não conectou no Banco de Dados!", "Banco de Dados", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) { // tratamento de exceção (diferente do throws que apenas jogava para cima)
                                       // pai -> indica a centralização na tela
            JOptionPane.showMessageDialog(null, "Erro ao tentar conectar no Banco de Dados: "
                                        + ex.getLocalizedMessage(), "Banco de Dados", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // imprime a pilha de erros
        }

    }
    
    
    public static void main(String[] args){
        
        new CVMain();
    }
    
}
