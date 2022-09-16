package br.edu.ifsul.cc.lpoo.cv.gui.consulta;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import br.edu.ifsul.cc.lpoo.cv.model.Pet;
import br.edu.ifsul.cc.lpoo.cv.model.Consulta;
import br.edu.ifsul.cc.lpoo.cv.model.Medico;
import br.edu.ifsul.cc.lpoo.cv.model.Consulta;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author camila
 */
public class JPanelAConsultaFormulario extends JPanel implements ActionListener {

    private JPanelAConsulta pnlAConsulta;
    private Controle controle;

    private BorderLayout borderLayout;
    private JPanel pnlNorte;
    private JLabel lblTitulo; // etiqueta de titulo
    private JTabbedPane tbpAbas;

    private JPanel pnlDadosConsulta;
    private JPanel pnlCentroDadosConsulta;

    // dados da consulta
    private GridBagLayout gridBagLayoutDadosConsulta;
    
    // Integer id
    private JLabel lblId;
    private JTextField txfId;
    
    // Calendar data
    private JLabel lblData;
    private JTextField txfData;

    // String observacao
    private JLabel lblObservacao;
    private JTextField txfObservacao;
    
    // Calendar data_retorno
    private JLabel lblRetorno;
    private JTextField txfRetorno;

    // Float valor
    private JLabel lblValor;
    private JTextField txfValor;

    // Pet pet
    private JLabel lblPet;
    private JComboBox cbxPet;

    // Medico medico
    private JLabel lblMedico;
    private JComboBox cbxMedico;    

    private Consulta consulta;
    private SimpleDateFormat format;

    private JPanel pnlSul;
    private JButton btnGravar;
    private JButton btnCancelar;

    public JPanelAConsultaFormulario(JPanelAConsulta pnlAConsulta, Controle controle) {

        this.pnlAConsulta = pnlAConsulta;
        this.controle = controle;

        initComponents();

    }

    public void populaComboPet() {

        cbxPet.removeAllItems();//zera o combo

        DefaultComboBoxModel model = (DefaultComboBoxModel) cbxPet.getModel();

        model.addElement("Selecione"); //primeiro item        
        try {

            List<Pet> listPets = controle.getConexaoJDBC().listPets();
            for (Pet p : listPets) {
                model.addElement(p);
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this, "Erro ao listar Pets -:" + ex.getLocalizedMessage(), "Pets", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

    }

    public void populaComboMedico() {

        cbxMedico.removeAllItems();//zera o combo

        DefaultComboBoxModel model = (DefaultComboBoxModel) cbxMedico.getModel();

        model.addElement("Selecione"); //primeiro item        
        try {

            List<Medico> listMedicos = controle.getConexaoJDBC().listMedicos();
            for (Medico m : listMedicos) {
                model.addElement(m);
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this, "Erro ao listar Medicos -:" + ex.getLocalizedMessage(), "Medicos", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

    }
    
    public Consulta getConsultabyFormulario() {

        //validacao do formulario
        if (new String(txfValor.getText()).trim().length() > 0 // valor > 0
                && cbxPet.getSelectedIndex() > 0
                && cbxMedico.getSelectedIndex() > 0 // Pet e Medico selecionados
                && Integer.parseInt(txfId.getText().trim()) != 0) { // != 0 indica update 

            Consulta c = new Consulta();
            c.setId(Integer.parseInt(txfId.getText().trim()));
            c.setObservacao(new String(txfObservacao.getText()).trim());
            c.setValor(Float.parseFloat(txfValor.getText()));
            c.setPet((Pet) cbxPet.getSelectedItem());
            c.setMedico((Medico) cbxMedico.getSelectedItem());

            if (consulta != null) {
                c.setData(consulta.getData()); // data da consulta
                c.setData_retorno(consulta.getData_retorno()); // data_retorno da consulta
            }

            return c;
            
        } else if (new String(txfValor.getText()).trim().length() > 0 // valor > 0
                && cbxPet.getSelectedIndex() > 0
                && cbxMedico.getSelectedIndex() > 0 // Pet e Medico selecionados
                && Integer.parseInt(txfId.getText().trim()) == 0) { // == 0 indica insert 

            Consulta c = new Consulta();
            //c.setId(Integer.parseInt(txfId.getText().trim())); -> não passa pq é gerado automaticamente
            c.setObservacao(new String(txfObservacao.getText()).trim());
            c.setValor(Float.parseFloat(txfValor.getText()));
            c.setPet((Pet) cbxPet.getSelectedItem());
            c.setMedico((Medico) cbxMedico.getSelectedItem());

            if (consulta != null) {
                c.setData(consulta.getData()); // data da consulta
                c.setData_retorno(consulta.getData_retorno()); // data_retorno da consulta
            }
            
            return c;
        }
        return null;
    }

    public void setConsultaFormulario(Consulta c) {

        if (c == null) { //se o parametro estiver nullo, limpa o formulario
            txfId.setText("0"); // marcação para indicar que é insert -> não necessita id pq é gerado sequencialmente
            txfData.setText(format.format(Calendar.getInstance().getTime())); // data atual
            txfObservacao.setText("");
            txfRetorno.setText(format.format(Calendar.getInstance().getTime())); // data atual
            txfValor.setText("");
            cbxPet.setSelectedIndex(0);
            cbxMedico.setSelectedIndex(0);
            txfObservacao.setText("");
            txfId.setEditable(false); // nao pode alterar id
            txfData.setEditable(false); // nao pode alterar data
            txfRetorno.setEditable(false); // nao pode alterar data_retorno
            consulta = null;

        } else {

            consulta = c;
            txfId.setEditable(false); // nao pode mudar id -> pk
            txfId.setText(consulta.getId().toString());
            txfData.setText(format.format(c.getData().getTime()));
            txfObservacao.setText(consulta.getObservacao());
            txfRetorno.setText(format.format(c.getData_retorno().getTime()));
            txfValor.setText(consulta.getValor().toString());
            cbxPet.getModel().setSelectedItem(consulta.getPet());
            cbxMedico.getModel().setSelectedItem(consulta.getMedico());
            txfData.setEditable(false); // nao pode alterar data
            txfRetorno.setEditable(false); // nao pode alterar data_retorno

        }

    }

    private void initComponents() {

        borderLayout = new BorderLayout();
        this.setLayout(borderLayout);
        
        pnlNorte = new JPanel();
        pnlNorte.setLayout(new FlowLayout());
        
        lblTitulo = new JLabel("Funcionalidades CRUD de Consulta");
        pnlNorte.add(lblTitulo);
        
        this.add(pnlNorte, BorderLayout.NORTH); // adiciona o painel na posicao norte

        tbpAbas = new JTabbedPane();
        this.add(tbpAbas, BorderLayout.CENTER);

        pnlDadosConsulta = new JPanel();
        gridBagLayoutDadosConsulta = new GridBagLayout();
        pnlDadosConsulta.setLayout(gridBagLayoutDadosConsulta);

        lblId = new JLabel("Id:");
        GridBagConstraints posicionador = new GridBagConstraints();
        posicionador.gridy = 0;// posição da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosConsulta.add(lblId, posicionador);//o add adiciona o rotulo no painel  

        txfId = new JTextField(10);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 0;// posição da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosConsulta.add(txfId, posicionador);//o add adiciona o rotulo no painel 
        
        lblData = new JLabel("Data:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1;// posição da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosConsulta.add(lblData, posicionador);//o add adiciona o rotulo no painel 

        txfData = new JTextField(10);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1;// posição da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)      
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosConsulta.add(txfData, posicionador);//o add adiciona o rotulo no painel  

        lblObservacao = new JLabel("Observação:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2;// posição da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosConsulta.add(lblObservacao, posicionador);//o add adiciona o rotulo no painel  

        txfObservacao = new JTextField(10);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2;// posição da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosConsulta.add(txfObservacao, posicionador);//o add adiciona o rotulo no painel  
        
        lblRetorno = new JLabel("Data de retorno:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3;// posição da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosConsulta.add(lblRetorno, posicionador);//o add adiciona o rotulo no painel 

        txfRetorno = new JTextField(10);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3;// posição da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)      
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosConsulta.add(txfRetorno, posicionador);//o add adiciona o rotulo no painel 

        lblValor = new JLabel("Valor:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4;// posição da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosConsulta.add(lblValor, posicionador);//o add adiciona o rotulo no painel  

        txfValor = new JTextField(10);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4;// posição da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosConsulta.add(txfValor, posicionador);//o add adiciona o rotulo no painel  

        lblPet = new JLabel("Pet:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 5;// posição da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosConsulta.add(lblPet, posicionador);//o add adiciona o rotulo no painel  

        cbxPet = new JComboBox();
        posicionador = new GridBagConstraints();
        posicionador.gridy = 5;// posição da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosConsulta.add(cbxPet, posicionador);//o add adiciona o rotulo no painel

        lblMedico = new JLabel("Médico:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 6;// posição da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosConsulta.add(lblMedico, posicionador);//o add adiciona o rotulo no painel  

        cbxMedico = new JComboBox();
        posicionador = new GridBagConstraints();
        posicionador.gridy = 6;// posição da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosConsulta.add(cbxMedico, posicionador);//o add adiciona o rotulo no painel

        tbpAbas.addTab("Dados da Consulta", pnlDadosConsulta);

        pnlSul = new JPanel();
        pnlSul.setLayout(new FlowLayout());

        btnGravar = new JButton("Gravar");
        btnGravar.addActionListener(this);
        btnGravar.setFocusable(true);    //acessibilidade    
        btnGravar.setToolTipText("btnGravarConsulta"); //acessibilidade
        btnGravar.setMnemonic(KeyEvent.VK_G);
        btnGravar.setActionCommand("botao_gravar_formulario_consulta");

        pnlSul.add(btnGravar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);
        btnCancelar.setFocusable(true);    //acessibilidade    
        btnCancelar.setToolTipText("btnCancelarConsulta"); //acessibilidade
        btnCancelar.setActionCommand("botao_cancelar_formulario_consulta");

        pnlSul.add(btnCancelar);

        this.add(pnlSul, BorderLayout.SOUTH);

        format = new SimpleDateFormat("dd/MM/yyyy");

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {

        if (arg0.getActionCommand().equals(btnGravar.getActionCommand())) {

            Consulta c = getConsultabyFormulario();//recupera os dados do formulário

            if (c != null) {

                try {

                    pnlAConsulta.getControle().getConexaoJDBC().persist(c);

                    JOptionPane.showMessageDialog(this, "Consulta armazenada!", "Salvar", JOptionPane.INFORMATION_MESSAGE);

                    pnlAConsulta.showTela("tela_consulta_listagem");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar Consulta! : " + ex.getMessage(), "Salvar", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }

            } else {

                JOptionPane.showMessageDialog(this, "Preencha o formulário!", "Edição", JOptionPane.INFORMATION_MESSAGE);
            }

        } else if (arg0.getActionCommand().equals(btnCancelar.getActionCommand())) {

            pnlAConsulta.showTela("tela_consulta_listagem");
        }
    }
}
