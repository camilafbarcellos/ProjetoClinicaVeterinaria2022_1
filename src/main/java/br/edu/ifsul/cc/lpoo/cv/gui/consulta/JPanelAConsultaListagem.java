
package br.edu.ifsul.cc.lpoo.cv.gui.consulta;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import br.edu.ifsul.cc.lpoo.cv.model.Consulta;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author camila
 */
public class JPanelAConsultaListagem extends JPanel implements ActionListener {
    
    private JPanelAConsulta pnlAConsulta;
    private Controle controle;
    
    private BorderLayout borderLayout;
    private JPanel pnlNorte;
    private JLabel lblTitulo; // etiqueta de titulo
    
    private JPanel pnlCentro;
    private JScrollPane scpListagem;
    private JTable tblListagem;
    private DefaultTableModel modeloTabela;
    
    private JPanel pnlSul;
    private JButton btnNovo;
    private JButton btnAlterar;
    private JButton btnRemover;
    
    private SimpleDateFormat format;
    
    
    public JPanelAConsultaListagem(JPanelAConsulta pnlAConsulta, Controle controle){
        
        this.pnlAConsulta = pnlAConsulta;
        this.controle = controle;
        
        initComponents();
    }
    
    
    public void populaTable(){
        
        DefaultTableModel model =  (DefaultTableModel) tblListagem.getModel();//recuperacao do modelo da tabela

        model.setRowCount(0);//elimina as linhas existentes (reset na tabela)

        try {

            List<Consulta> listConsultas = controle.getConexaoJDBC().listConsultas();
            for(Consulta c : listConsultas){
                                
                model.addRow(new Object[]{c, format.format(c.getData().getTime()), c.getObservacao(),
                                        c.getValor(), c.getPet().getNome(), c.getMedico().getNome()});
                // c puxa o metodo toString de Consulta que retorna o id
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this, "Erro ao listar Consultas -:"+ex.getLocalizedMessage(), "Consultas", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }        
        
    }
    
    private void initComponents(){
        
        borderLayout = new BorderLayout();
        this.setLayout(borderLayout);//seta o gerenciado border para este painel
        
        pnlNorte = new JPanel();
        pnlNorte.setLayout(new FlowLayout());
        
        lblTitulo = new JLabel("Bem vindo à listagem de Consultas!");
        pnlNorte.add(lblTitulo);
        
        this.add(pnlNorte, BorderLayout.NORTH);//adiciona o painel na posicao norte.
        
        pnlCentro = new JPanel();
        pnlCentro.setLayout(new BorderLayout());
            
        scpListagem = new JScrollPane();
        tblListagem =  new JTable();
        
        modeloTabela = new DefaultTableModel(
            new String [] {
                "ID", "Data", "Observação", "Valor", "Pet", "Médico"
            }, 0);
        
        tblListagem.setModel(modeloTabela);
        scpListagem.setViewportView(tblListagem);
    
        pnlCentro.add(scpListagem, BorderLayout.CENTER);
    
        this.add(pnlCentro, BorderLayout.CENTER);//adiciona o painel na posicao norte.
        
        pnlSul = new JPanel();
        pnlSul.setLayout(new FlowLayout());
        
        btnNovo = new JButton("Novo");
        btnNovo.addActionListener(this);
        btnNovo.setFocusable(true);    //acessibilidade    
        btnNovo.setToolTipText("btnNovo"); //acessibilidade
        btnNovo.setMnemonic(KeyEvent.VK_N);
        btnNovo.setActionCommand("botao_novo");
        
        pnlSul.add(btnNovo);
        
        btnAlterar = new JButton("Editar");
        btnAlterar.addActionListener(this);
        btnAlterar.setFocusable(true);    //acessibilidade    
        btnAlterar.setToolTipText("btnAlterar"); //acessibilidade
        btnAlterar.setActionCommand("botao_alterar");
        
        pnlSul.add(btnAlterar);
        
        btnRemover = new JButton("Remover");
        btnRemover.addActionListener(this);
        btnRemover.setFocusable(true);    //acessibilidade    
        btnRemover.setToolTipText("btnRemover"); //acessibilidade
        btnRemover.setActionCommand("botao_remover");
        
        pnlSul.add(btnRemover);//adiciona o botao na fila organizada pelo flowlayout
        
        
        this.add(pnlSul, BorderLayout.SOUTH);//adiciona o painel na posicao norte.
      
        format = new SimpleDateFormat("dd/MM/yyyy");
        
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
    
        if(arg0.getActionCommand().equals(btnNovo.getActionCommand())){
            
            pnlAConsulta.showTela("tela_consulta_formulario");            
            
            pnlAConsulta.getFormulario().setConsultaFormulario(null); //limpando o formulário.                        
            
        }else if(arg0.getActionCommand().equals(btnAlterar.getActionCommand())){
            
            
            int indice = tblListagem.getSelectedRow();//recupera a linha selecionada
            if(indice > -1){

                DefaultTableModel model =  (DefaultTableModel) tblListagem.getModel(); //recuperacao do modelo da table

                Vector linha = (Vector) model.getDataVector().get(indice);//recupera o vetor de dados da linha selecionada

                Consulta c = (Consulta) linha.get(0); //model.addRow(new Object[]{u, u.getNome(), ...

                pnlAConsulta.showTela("tela_consulta_formulario");
                pnlAConsulta.getFormulario().setConsultaFormulario(c); 

            }else{
                  JOptionPane.showMessageDialog(this, "Selecione uma linha para editar!", "Edição", JOptionPane.INFORMATION_MESSAGE);
            }
            
            
        }else if(arg0.getActionCommand().equals(btnRemover.getActionCommand())){
            
            
            int indice = tblListagem.getSelectedRow();//recupera a linha selecionada
            if(indice > -1){

                DefaultTableModel model =  (DefaultTableModel) tblListagem.getModel(); //recuperacao do modelo da table

                Vector linha = (Vector) model.getDataVector().get(indice);//recupera o vetor de dados da linha selecionada

                Consulta c = (Consulta) linha.get(0); //model.addRow(new Object[]{u, u.getNome(), ...

                try {
                    pnlAConsulta.getControle().getConexaoJDBC().remover(c);
                    JOptionPane.showMessageDialog(this, "Consulta removida!", "Consulta", JOptionPane.INFORMATION_MESSAGE);
                    populaTable(); //refresh na tabela

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao remover Consulta -:"+ex.getLocalizedMessage(), "Consultas", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }                        

            }else{
                  JOptionPane.showMessageDialog(this, "Selecione uma linha para remover!", "Remoção", JOptionPane.INFORMATION_MESSAGE);
            }     
        }
    }
}
