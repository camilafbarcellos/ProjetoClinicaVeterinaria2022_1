package br.edu.ifsul.cc.lpoo.cv.gui.venda;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import br.edu.ifsul.cc.lpoo.cv.model.Cliente;
import br.edu.ifsul.cc.lpoo.cv.model.Consulta;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.Pagamento;
import br.edu.ifsul.cc.lpoo.cv.model.Venda;
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
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author camila
 */
public class JPanelAVendaFormulario extends JPanel implements ActionListener {

    private JPanelAVenda pnlAVenda;
    private Controle controle;

    private BorderLayout borderLayout;
    private JPanel pnlNorte;
    private JLabel lblTitulo; // etiqueta de titulo
    private JTabbedPane tbpAbas;

    private JPanel pnlDadosVenda;
    private JPanel pnlCentroDadosVenda;

    // dados da venda
    private GridBagLayout gridBagLayoutDadosVenda;
    // Integer id
    private JLabel lblId;
    private JTextField txfId;

    // String observacao
    private JLabel lblObservacao;
    private JTextField txfObservacao;

    // FLoat valor_total
    private JLabel lblValor;
    private JTextField txfValor;

    // Cliente cliente
    private JLabel lblCliente;
    private JComboBox cbxCliente;

    // Funcionario funcionario
    private JLabel lblFuncionario;
    private JComboBox cbxFuncionario;

    // Pagamento formaPagamento
    private JLabel lblPagamento;
    private JTextField txfPagamento;

    // Calendar data
    private JLabel lblData;
    private JTextField txfData;

    private Venda venda;
    private SimpleDateFormat format;

    private JPanel pnlSul;
    private JButton btnGravar;
    private JButton btnCancelar;

    // agregação com Consulta
    private JPanel pnlDadosConsultas;
    private JScrollPane scpListagemConsulta;
    private JTable tblListagemConsulta;
    private JComboBox cbxConsulta;
    private JButton btnAdicionarConsulta;
    private JButton btnRemoverConsulta;
    private DefaultTableModel modeloTabelaConsulta;
    private JLabel lblConsultaAdicionar;

    public JPanelAVendaFormulario(JPanelAVenda pnlAVenda, Controle controle) {

        this.pnlAVenda = pnlAVenda;
        this.controle = controle;

        initComponents();

    }

    public void populaComboCliente() {

        cbxCliente.removeAllItems(); // zera o combo

        DefaultComboBoxModel model = (DefaultComboBoxModel) cbxCliente.getModel();

        model.addElement("Selecione"); // primeiro item -> indica instrução para o usuário
        try {

            List<Cliente> listClientes = controle.getConexaoJDBC().listClientes();
            for (Cliente c : listClientes) {
                model.addElement(c);
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this, "Erro ao listar Clientes -:" + ex.getLocalizedMessage(), "Clientes", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

    }

    public void populaComboFuncionario() {

        cbxFuncionario.removeAllItems();//zera o combo

        DefaultComboBoxModel model = (DefaultComboBoxModel) cbxFuncionario.getModel();

        model.addElement("Selecione"); // primeiro item -> indica instrução para o usuário       
        try {

            List<Funcionario> listFuncionarios = controle.getConexaoJDBC().listFuncionarios();
            for (Funcionario f : listFuncionarios) {
                model.addElement(f);
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this, "Erro ao listar Funcionarios -:" + ex.getLocalizedMessage(), "Funcionarios", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

    }

    public void populaComboConsulta() {

        cbxConsulta.removeAllItems();//zera o combo

        DefaultComboBoxModel model = (DefaultComboBoxModel) cbxConsulta.getModel();

        model.addElement("Selecione"); //primeiro item        
        try {

            List<Consulta> listConsultas = controle.getConexaoJDBC().listConsultas();
            for (Consulta c : listConsultas) {
                model.addElement(c);
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this, "Erro ao listar Consultas -:" + ex.getLocalizedMessage(), "Consultas", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public Venda getVendabyFormulario() {

        //validacao do formulario
        if (new String(txfObservacao.getText()).trim().length() > 3 // observacao > 3
                && new String(txfValor.getText()).trim().length() > 0 // valor > 0
                && cbxCliente.getSelectedIndex() > 0
                && cbxFuncionario.getSelectedIndex() > 0 // Cliente e Funcionario selecionados
                && Integer.parseInt(txfId.getText().trim()) != 0) { // != 0 indica update 

            Venda v = new Venda();
            v.setId(Integer.parseInt(txfId.getText().trim()));
            v.setObservacao(new String(txfObservacao.getText()).trim());
            v.setValor_total(Float.parseFloat(txfValor.getText()));
            v.setCliente((Cliente) cbxCliente.getSelectedItem());
            v.setFuncionario((Funcionario) cbxFuncionario.getSelectedItem());
            v.setFormaPagamento(Pagamento.PIX);

            if (venda != null) {
                v.setData(venda.getData()); // data da venda
            }

            DefaultTableModel model = (DefaultTableModel) tblListagemConsulta.getModel(); //recuperacao do modelo da tabela

            for (Vector<Vector> linha : model.getDataVector()) {

                Vector vec = (Vector) linha; //model.addRow(new Object[]{u, u.getNome(), ...
                
                Consulta c = (Consulta) vec.get(0);

                System.out.println("Add consulta na venda ...");

                v.setConsulta(c);

            }
            return v;
            
        } else if (new String(txfObservacao.getText()).trim().length() > 3 // observacao > 3
                && new String(txfValor.getText()).trim().length() > 0 // valor > 0
                && cbxCliente.getSelectedIndex() > 0
                && cbxFuncionario.getSelectedIndex() > 0 // Cliente e Funcionario selecionados
                && Integer.parseInt(txfId.getText().trim()) == 0) { // == 0 indica insert 

            Venda v = new Venda();
            //v.setId(Integer.parseInt(txfId.getText().trim())); -> não passa pq é gerado automaticamente
            v.setObservacao(new String(txfObservacao.getText()).trim());
            v.setValor_total(Float.parseFloat(txfValor.getText()));
            v.setCliente((Cliente) cbxCliente.getSelectedItem());
            v.setFuncionario((Funcionario) cbxFuncionario.getSelectedItem());
            v.setFormaPagamento(Pagamento.PIX);

            if (venda != null) {
                v.setData(venda.getData()); // data da venda
            }

            DefaultTableModel model = (DefaultTableModel) tblListagemConsulta.getModel(); //recuperacao do modelo da tabela

            for (Vector<Vector> linha : model.getDataVector()) {

                Vector vec = (Vector) linha; //model.addRow(new Object[]{u, u.getNome(), ...

                Consulta c = (Consulta) vec.get(0);

                System.out.println("Add consulta na venda ...");

                v.setConsulta(c);

            }
            return v;
        }
        return null;
    }

    public void setVendaFormulario(Venda v) {

        if (v == null) { //se o parametro estiver nullo, limpa o formulario
            txfId.setText("0"); // marcação para indicar que é insert -> não necessita id pq é gerado sequencialmente
            txfObservacao.setText("");
            txfValor.setText("");
            cbxCliente.setSelectedIndex(0);
            cbxFuncionario.setSelectedIndex(0);
            txfObservacao.setText("");
            txfData.setText(format.format(Calendar.getInstance().getTime())); // data atual
            txfPagamento.setText("Pix");
            txfId.setEditable(false); // nao pode alterar id
            txfData.setEditable(false); // nao pode alterar data
            txfPagamento.setEditable(false); // nao pode alterar forma de pagamento
            venda = null;

            //limpa a tabela das consultas do venda.
            DefaultTableModel model = (DefaultTableModel) tblListagemConsulta.getModel(); //recuperacao do modelo da tabela
            model.setRowCount(0);

        } else {

            venda = v;
            txfId.setEditable(false); // nao pode mudar id -> pk
            txfId.setText(venda.getId().toString());
            txfObservacao.setText(venda.getObservacao());
            txfValor.setText(venda.getValor_total().toString());
            cbxCliente.getModel().setSelectedItem(venda.getCliente());
            cbxFuncionario.getModel().setSelectedItem(venda.getFuncionario());
            txfData.setText(format.format(v.getData().getTime()));
            txfData.setEditable(false); // nao pode alterar data
            txfPagamento.setText("Pix");
            txfPagamento.setEditable(false); // nao pode alterar forma de pagamento

            //gera linhas na tabela para listar as consultas de uma determinada venda.
            if (venda.getConsultas() != null) {

                for (Consulta c : venda.getConsultas()) {

                    DefaultTableModel model = (DefaultTableModel) tblListagemConsulta.getModel();//recuperacao do modelo da tabela

                    model.setRowCount(0);//elimina as linhas existentes (reset na tabela)

                    model.addRow(new Object[]{c, format.format(c.getData().getTime()), c.getObservacao(),
                                        c.getValor(), c.getPet().getNome(), c.getMedico().getNome()});
                    // c puxa o metodo toString de Consulta que retorna o id

                }
            }

        }

    }

    private void initComponents() {

        borderLayout = new BorderLayout();
        this.setLayout(borderLayout);
        
        pnlNorte = new JPanel();
        pnlNorte.setLayout(new FlowLayout());
        
        lblTitulo = new JLabel("Funcionalidades CRUD de Venda");
        pnlNorte.add(lblTitulo);
        
        this.add(pnlNorte, BorderLayout.NORTH); // adiciona o painel na posicao norte

        tbpAbas = new JTabbedPane();
        this.add(tbpAbas, BorderLayout.CENTER);

        pnlDadosVenda = new JPanel();
        gridBagLayoutDadosVenda = new GridBagLayout();
        pnlDadosVenda.setLayout(gridBagLayoutDadosVenda);

        lblId = new JLabel("Id:");
        GridBagConstraints posicionador = new GridBagConstraints();
        posicionador.gridy = 0;// posição da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosVenda.add(lblId, posicionador);//o add adiciona o rotulo no painel  

        txfId = new JTextField(10);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 0;// posição da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosVenda.add(txfId, posicionador);//o add adiciona o rotulo no painel  

        lblObservacao = new JLabel("Observação:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1;// posição da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosVenda.add(lblObservacao, posicionador);//o add adiciona o rotulo no painel  

        txfObservacao = new JTextField(10);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1;// posição da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosVenda.add(txfObservacao, posicionador);//o add adiciona o rotulo no painel  

        lblValor = new JLabel("Valor total:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2;// posição da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosVenda.add(lblValor, posicionador);//o add adiciona o rotulo no painel  

        txfValor = new JTextField(10);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2;// posição da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosVenda.add(txfValor, posicionador);//o add adiciona o rotulo no painel  

        lblCliente = new JLabel("Cliente:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3;// posição da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosVenda.add(lblCliente, posicionador);//o add adiciona o rotulo no painel  

        cbxCliente = new JComboBox();
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3;// posição da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosVenda.add(cbxCliente, posicionador);//o add adiciona o rotulo no painel

        lblFuncionario = new JLabel("Funcionário:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4;// posição da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosVenda.add(lblFuncionario, posicionador);//o add adiciona o rotulo no painel  

        cbxFuncionario = new JComboBox();
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4;// posição da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosVenda.add(cbxFuncionario, posicionador);//o add adiciona o rotulo no painel 

        lblData = new JLabel("Data:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 5;// posição da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosVenda.add(lblData, posicionador);//o add adiciona o rotulo no painel 

        txfData = new JTextField(10);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 5;// posição da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)      
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosVenda.add(txfData, posicionador);//o add adiciona o rotulo no painel

        lblPagamento = new JLabel("Pagamento:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 6;// posição da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosVenda.add(lblPagamento, posicionador);//o add adiciona o rotulo no painel  

        txfPagamento = new JTextField(10);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 6;// posição da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosVenda.add(txfPagamento, posicionador);//o add adiciona o rotulo no painel        

        tbpAbas.addTab("Dados da Venda", pnlDadosVenda);

        pnlDadosConsultas = new JPanel();

        pnlDadosConsultas.setLayout(new GridBagLayout());

        scpListagemConsulta = new JScrollPane();
        tblListagemConsulta = new JTable();

        modeloTabelaConsulta = new DefaultTableModel(
                new String[]{
                    "ID", "Data", "Obs.", "Valor", "Pet", "Médico"
                }, 0);

        tblListagemConsulta.setModel(modeloTabelaConsulta);

        scpListagemConsulta.setViewportView(tblListagemConsulta);

        cbxConsulta = new JComboBox();
        lblConsultaAdicionar = new JLabel("Escolha a Consulta para adicionar:");
        btnAdicionarConsulta = new JButton("Adicionar");

        btnRemoverConsulta = new JButton("Remover");

        posicionador = new GridBagConstraints();
        posicionador.gridy = 0;// posição da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosConsultas.add(scpListagemConsulta, posicionador);//o add adiciona o rotulo no painel

        JPanel pnlLinha = new JPanel();
        pnlLinha.setLayout(new FlowLayout());
        pnlLinha.add(lblConsultaAdicionar);
        pnlLinha.add(cbxConsulta);

        posicionador = new GridBagConstraints();
        posicionador.gridy = 1;// posição da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosConsultas.add(pnlLinha, posicionador);//o add adiciona o rotulo no painel

        btnAdicionarConsulta.addActionListener(this);
        btnAdicionarConsulta.setActionCommand("botao_adicionar_consulta_formulario_venda");

        btnRemoverConsulta.addActionListener(this);
        btnRemoverConsulta.setActionCommand("botao_remover_consulta_formulario_venda");

        JPanel pnlLinhaB = new JPanel();
        pnlLinhaB.setLayout(new FlowLayout());
        pnlLinhaB.add(btnAdicionarConsulta);
        pnlLinhaB.add(btnRemoverConsulta);

        posicionador = new GridBagConstraints();
        posicionador.gridy = 2;// posição da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosConsultas.add(pnlLinhaB, posicionador);//o add adiciona o rotulo no painel

        tbpAbas.addTab("Consultas", pnlDadosConsultas);

        pnlSul = new JPanel();
        pnlSul.setLayout(new FlowLayout());

        btnGravar = new JButton("Gravar");
        btnGravar.addActionListener(this);
        btnGravar.setFocusable(true);    //acessibilidade    
        btnGravar.setToolTipText("btnGravarVenda"); //acessibilidade
        btnGravar.setMnemonic(KeyEvent.VK_G);
        btnGravar.setActionCommand("botao_gravar_formulario_venda");

        pnlSul.add(btnGravar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);
        btnCancelar.setFocusable(true);    //acessibilidade    
        btnCancelar.setToolTipText("btnCancelarVenda"); //acessibilidade
        btnCancelar.setActionCommand("botao_cancelar_formulario_venda");

        pnlSul.add(btnCancelar);

        this.add(pnlSul, BorderLayout.SOUTH);

        format = new SimpleDateFormat("dd/MM/yyyy");

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {

        if (arg0.getActionCommand().equals(btnGravar.getActionCommand())) {

            Venda v = getVendabyFormulario();//recupera os dados do formulário

            if (v != null) {

                try {

                    pnlAVenda.getControle().getConexaoJDBC().persist(v);

                    JOptionPane.showMessageDialog(this, "Venda armazenada!", "Salvar", JOptionPane.INFORMATION_MESSAGE);

                    pnlAVenda.showTela("tela_venda_listagem");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar Venda! : " + ex.getMessage(), "Salvar", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }

            } else {

                JOptionPane.showMessageDialog(this, "Preencha o formulário!", "Edição", JOptionPane.INFORMATION_MESSAGE);
            }

        } else if (arg0.getActionCommand().equals(btnCancelar.getActionCommand())) {

            pnlAVenda.showTela("tela_venda_listagem");

        } else if (arg0.getActionCommand().equals(btnAdicionarConsulta.getActionCommand())) {

            //adiciona uma consulta na lista de consultas do venda (jtable)
            if (cbxConsulta.getSelectedIndex() > 0) {

                DefaultTableModel model = (DefaultTableModel) tblListagemConsulta.getModel();//recuperacao do modelo da tabela

                Consulta c = (Consulta) cbxConsulta.getSelectedItem();

                model.addRow(new Object[]{c, format.format(c.getData().getTime()), c.getObservacao(),
                                        c.getValor(), c.getPet().getNome(), c.getMedico().getNome()});
                    // c puxa o metodo toString de Consulta que retorna o id

            } else {

                JOptionPane.showMessageDialog(this, "Selecione uma Consulta para adicionar !!", "Consultas da Venda", JOptionPane.INFORMATION_MESSAGE);
            }

        } else if (arg0.getActionCommand().equals(btnRemoverConsulta.getActionCommand())) {

            int indice = tblListagemConsulta.getSelectedRow();//recupera a linha selecionada
            if (indice > -1) {

                DefaultTableModel model = (DefaultTableModel) tblListagemConsulta.getModel(); //recuperacao do modelo da table

                model.removeRow(indice); // remove a linha selecionada.

            }
        }
    }
}
