
package br.edu.ifsul.cc.lpoo.cv.gui.consulta;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 *
 * @author Camila
 */
public class JPanelAConsulta extends JPanel {
    
    private CardLayout cardLayout; // estrutura de cartas para organizar formulario e listagem
    private Controle controle;
    
    // cartas do sub-baralho de Consulta
    private JPanelAConsultaFormulario formulario;
    private JPanelAConsultaListagem listagem;
    
    public JPanelAConsulta(Controle controle){
        
        this.controle = controle;
        initComponents();
    }
    
    private void initComponents(){
        
        // inicializa o baralho
        cardLayout = new CardLayout();
        this.setLayout(cardLayout);
        
        // inicializa as cartas
        formulario = new JPanelAConsultaFormulario(this, controle);
        listagem = new JPanelAConsultaListagem(this, controle);
        
        // coloca as cartas no baralho
        this.add(getFormulario(), "tela_consulta_formulario");
        this.add(listagem, "tela_consulta_listagem");
                
    }
    
    // metodo para mostrar as cartas do baralho de Consulta
    public void showTela(String nomeTela){
        
        if(nomeTela.equals("tela_consulta_listagem")){
            
            listagem.populaTable();
            
        }else if(nomeTela.equals("tela_consulta_formulario")){
            
            getFormulario().populaComboPet();
            getFormulario().populaComboMedico();
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
    public JPanelAConsultaFormulario getFormulario() {
        return formulario;
    }
    
}
