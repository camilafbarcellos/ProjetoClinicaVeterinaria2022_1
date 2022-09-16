package br.edu.ifsul.cc.lpoo.cv;

import br.edu.ifsul.cc.lpoo.cv.gui.JFramePrincipal;
import br.edu.ifsul.cc.lpoo.cv.gui.JMenuBarHome;
import br.edu.ifsul.cc.lpoo.cv.gui.JPanelHome;
import br.edu.ifsul.cc.lpoo.cv.gui.autenticacao.JPanelAutenticacao;
import br.edu.ifsul.cc.lpoo.cv.gui.consulta.JPanelAConsulta;
import br.edu.ifsul.cc.lpoo.cv.gui.venda.JPanelAVenda;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.Pessoa;
import br.edu.ifsul.cc.lpoo.cv.model.dao.PersistenciaJDBC;
import javax.swing.JOptionPane;

/**
 *
 * @author Camila
 */
public class Controle {

    // instâncias
    private PersistenciaJDBC conexaoJDBC; // objeto referente aos dados

    private JPanelAutenticacao telaAutenticacao; // tela de autenticação

    private JFramePrincipal frame; // frame principal da minha aplicação gráfica
    
    private JMenuBarHome menuBar;
    
    private JPanelHome telaHome; // tela inicial

    private JPanelAVenda telaVenda; // tela de CRUD de venda
    
    private JPanelAConsulta telaConsulta; // tela de CRUD de consulta

    public Controle() {
        // construtor em branco
    }
    
    public boolean conectarBD() throws Exception {
        
        conexaoJDBC = new PersistenciaJDBC(); // inicializa o construtor da conexão JDBC

        if (getConexaoJDBC() != null) { // conexão true

            return getConexaoJDBC().conexaoAberta();
        }
        
        return false;
    }
    
    public void fecharBD() {
        
        System.out.println("Fechando conexão com o Banco de Dados");
        getConexaoJDBC().fecharConexao();
    }
    
    public void showTela(String nomeTela) {
        // tela de venda é subdividida em duas -> listagem e formulário CRUD
        if (nomeTela.equals("tela_venda")) { // caso chamada a tela de Venda
            // "sub-baralho" para alternar entre as duas cartas de telas
            telaVenda.showTela("tela_venda_listagem"); // prevalece mostrar a listagem
            frame.showTela(nomeTela);
        } else if (nomeTela.equals("tela_consulta")) { // caso chamada a tela de Consulta
            // "sub-baralho" para alternar entre as duas cartas de telas
            telaConsulta.showTela("tela_consulta_listagem"); // prevalece mostrar a listagem
            frame.showTela(nomeTela);
        }
        
        // método para mostrar outras telas -> Logout
        frame.showTela(nomeTela); // exibe a tela passada por parâmetro
    }
    
    public void initComponents() {

        //"caminho feliz" : passo 5
        frame = new JFramePrincipal(this); // inicializa a interface gráfica
        // this indica a própria classe Controle
        // FramePrincipal recebe uma instância de Controle

        // telaAutenticacao
        telaAutenticacao = new JPanelAutenticacao(this); // inicializa a tela

        // menuBar
        menuBar = new JMenuBarHome(this);

        // telaHome
        telaHome = new JPanelHome(this);

        // telaVenda
        telaVenda = new JPanelAVenda(this);
        
        // telaConsulta
        telaConsulta = new JPanelAConsulta(this);

        // baralho de cartas de telas
        frame.addTela(telaAutenticacao, "tela_autenticacao"); // adiciona e nomeia a tela ao baralho de cartas        
        frame.addTela(telaHome, "tela_home");
        frame.addTela(telaVenda, "tela_venda");
        frame.addTela(telaConsulta, "tela_consulta");

        // carta de tela padrão
        frame.showTela("tela_autenticacao"); // busca e exibe a tela -> tela padrão
        
        frame.setVisible(true); // torna visível o jframe -> mostra a tela que está nele

    }

    /**
     * @return the conexaoJDBC
     */
    public PersistenciaJDBC getConexaoJDBC() {
        return conexaoJDBC;
    }
    
    public void autenticar(String cpf, String senha) {
        
        try {
            
            Pessoa p = conexaoJDBC.doLogin(cpf, senha); // chama o método doLogin

            if (p != null) { // retorno não nulo

                JOptionPane.showMessageDialog(telaAutenticacao, "Funcionário " + p.getNome()+ " autenticado com sucesso!", "Autenticação", JOptionPane.INFORMATION_MESSAGE);
                
                frame.setJMenuBar(menuBar); // adiciona o menu de barra no frame
                frame.showTela("tela_home"); // muda a tela para o painel de boas vindas (home)
                
            } else { // retorno nulo

                JOptionPane.showMessageDialog(telaAutenticacao, "Dados inválidos!", "Autenticação", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(telaAutenticacao, "Erro ao executar a autenticação no Banco de Dados!", "Autenticação", JOptionPane.ERROR_MESSAGE);
            
        }
    }
    
}
