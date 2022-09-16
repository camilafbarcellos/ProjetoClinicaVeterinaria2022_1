
package br.edu.ifsul.cc.lpoo.cv.gui.venda;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 *
 * @author Camila
 */
public class JPanelAVenda extends JPanel {
    
    private CardLayout cardLayout; // estrutura de cartas para organizar formulario e listagem
    private Controle controle;
    
    // cartas do sub-baralho de Venda
    private JPanelAVendaFormulario formulario;
    private JPanelAVendaListagem listagem;
    
    public JPanelAVenda(Controle controle){
        
        this.controle = controle;
        initComponents();
    }
    
    private void initComponents(){
        
        // inicializa o baralho
        cardLayout = new CardLayout();
        this.setLayout(cardLayout);
        
        // inicializa as cartas
        formulario = new JPanelAVendaFormulario(this, controle);
        listagem = new JPanelAVendaListagem(this, controle);
        
        // coloca as cartas no baralho
        this.add(getFormulario(), "tela_venda_formulario");
        this.add(listagem, "tela_venda_listagem");
                
    }
    
    // metodo para mostrar as cartas do baralho de Venda
    public void showTela(String nomeTela){
        
        if(nomeTela.equals("tela_venda_listagem")){
            
            listagem.populaTable();
            
        }else if(nomeTela.equals("tela_venda_formulario")){
            
            getFormulario().populaComboCliente();
            getFormulario().populaComboFuncionario();
            getFormulario().populaComboConsulta();
        }
        
        cardLayout.show(this, nomeTela);
        
    }

    /**
     * @return the controle
     */
    public Controle getControle() {
        return controle;
    }

    /**
     * @return the formulario
     */
    public JPanelAVendaFormulario getFormulario() {
        return formulario;
    }
    
    
    
}
