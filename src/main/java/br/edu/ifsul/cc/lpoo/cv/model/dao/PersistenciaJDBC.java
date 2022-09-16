package br.edu.ifsul.cc.lpoo.cv.model.dao;

import br.edu.ifsul.cc.lpoo.cv.model.Cliente;
import br.edu.ifsul.cc.lpoo.cv.model.Consulta;
import br.edu.ifsul.cc.lpoo.cv.model.Fornecedor;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.Medico;
import br.edu.ifsul.cc.lpoo.cv.model.Pagamento;
import br.edu.ifsul.cc.lpoo.cv.model.Pessoa;
import br.edu.ifsul.cc.lpoo.cv.model.Pet;
import br.edu.ifsul.cc.lpoo.cv.model.Produto;
import br.edu.ifsul.cc.lpoo.cv.model.Raca;
import br.edu.ifsul.cc.lpoo.cv.model.TipoProduto;
import br.edu.ifsul.cc.lpoo.cv.model.Venda;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Prof. Telmo
 */
public class PersistenciaJDBC implements InterfacePersistencia {

    private final String DRIVER = "org.postgresql.Driver";
    private final String USER = "postgres";
    private final String SENHA = "postgres";
    public static final String URL = "jdbc:postgresql://localhost:5432/db_cv";
    private Connection con = null;

    public PersistenciaJDBC() throws Exception {

        Class.forName(DRIVER); //carregamento do driver postgresql em tempo de execução
        System.out.println("Tentando estabelecer conexao JDBC com : " + URL + " ...");

        this.con = (Connection) DriverManager.getConnection(URL, USER, SENHA);

    }

    public Boolean conexaoAberta() {

        try {
            if (con != null) {
                return !con.isClosed();//verifica se a conexao está aberta
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;

    }

    @Override
    public void fecharConexao() {

        try {
            this.con.close();//fecha a conexao.
            System.out.println("Fechou conexao JDBC");
        } catch (SQLException e) {
            e.printStackTrace();//gera uma pilha de erro na saida.
        }

    }

    @Override
    public Object find(Class c, Object id) throws Exception {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void persist(Object o) throws Exception {

        //questao 2 da primeira etapa.
        if (o instanceof Produto) {

            Produto p = (Produto) o;
            if (p.getId() == null) {

                PreparedStatement ps = this.con.prepareStatement("insert into tb_produto (id, nome, quantidade, tipo, valor, fornecedor_cpf) "
                        + "values (nextval('seq_produto_id'), ?, ?, ?, ?, ?) returning id;");
                ps.setString(1, p.getNome());
                ps.setFloat(2, p.getQuantidade());
                ps.setString(3, p.getTipo().name());
                ps.setFloat(4, p.getValor());
                ps.setString(5, p.getFornecedor().getCpf());

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    p.setId(rs.getInt("id"));
                }

            } else {

                PreparedStatement ps = this.con.prepareStatement("update tb_produto set nome = ?, quantidade = ?, tipo = ?, valor = ?, fornecedor_cpf = ? "
                        + "where id = ?; ");

                ps.setString(1, p.getNome());
                ps.setFloat(2, p.getQuantidade());
                ps.setString(3, p.getTipo().name());
                ps.setFloat(4, p.getValor());
                ps.setString(5, p.getFornecedor().getCpf());
                ps.setInt(6, p.getId());

                ps.execute();

            }

        } else if (o instanceof Venda) {

            Venda v = (Venda) o;
            if (v.getId() == null) {

                PreparedStatement ps = this.con.prepareStatement("insert into tb_venda (id, observacao, valor_total, cliente_cpf, funcionario_cpf, data, formapagamento) "
                        + "values (nextval('seq_venda_id'), ?, ?, ?, ?, now(), ?) returning id");
                ps.setString(1, v.getObservacao());
                ps.setFloat(2, v.getValor_total());
                ps.setString(3, v.getCliente().getCpf());
                ps.setString(4, v.getFuncionario().getCpf());
                ps.setString(5, v.getFormaPagamento().PIX.name());

                ps.execute();

                if (v.getConsultas() != null && !v.getConsultas().isEmpty()) {

                    for (Consulta c : v.getConsultas()) {

                        PreparedStatement ps2 = this.con.prepareStatement("insert into tb_venda_consulta "
                                + "(venda_id, consulta_id) values "
                                + "(?, ?) ");

                        ps2.setInt(1, v.getId());
                        ps2.setInt(2, c.getId());

                        ps2.execute();

                    }
                }

            } else {

                PreparedStatement ps = this.con.prepareStatement("update tb_venda set observacao = ?, valor_total = ?, cliente_cpf = ?, funcionario_cpf = ?, formapagamento = ? "
                        + "where id = ?; ");

                ps.setString(1, v.getObservacao());
                ps.setFloat(2, v.getValor_total());
                ps.setString(3, v.getCliente().getCpf());
                ps.setString(4, v.getFuncionario().getCpf());
                ps.setString(5, v.getFormaPagamento().PIX.name());
                ps.setInt(6, v.getId());

                ps.execute();

                PreparedStatement ps2 = this.con.prepareStatement("delete from tb_venda_consulta where venda_id = ? ");
                ps2.setInt(1, v.getId());
                ps2.execute();

                if (v.getConsultas() != null && !v.getConsultas().isEmpty()) {

                    for (Consulta c : v.getConsultas()) {

                        PreparedStatement ps3 = this.con.prepareStatement("insert into tb_venda_consulta "
                                + "(venda_id, consulta_id) values "
                                + "(?, ? ) ");

                        ps3.setInt(1, v.getId());
                        ps3.setInt(2, c.getId());;

                        ps3.execute();

                    }
                }

            }
        } else if (o instanceof Consulta) {

            Consulta c = (Consulta) o;
            if (c.getId() == null) {

                PreparedStatement ps = this.con.prepareStatement("insert into tb_consulta (id, observacao, valor, pet_id, medico_cpf, data, data_retorno) "
                        + "values (nextval('seq_consulta_id'), ?, ?, ?, ?, now(), now()) returning id");
                ps.setString(1, c.getObservacao());
                ps.setFloat(2, c.getValor());
                ps.setInt(3, c.getPet().getId());
                ps.setString(4, c.getMedico().getCpf());

                ps.execute();

            } else {

                PreparedStatement ps = this.con.prepareStatement("update tb_consulta set observacao = ?, valor = ?, pet_id = ?, medico_cpf = ? "
                        + "where id = ?; ");

                ps.setString(1, c.getObservacao());
                ps.setFloat(2, c.getValor());
                ps.setInt(3, c.getPet().getId());
                ps.setString(4, c.getMedico().getCpf());
                ps.setInt(5, c.getId());

                ps.execute();

            }
        }
    }

    @Override
    public void remover(Object o) throws Exception {

        //questao 3 da primeira etapa.
        if (o instanceof Produto) {
            Produto p = (Produto) o;

            PreparedStatement ps = this.con.prepareStatement("delete from tb_produto where id = ?");
            ps.setInt(1, p.getId());

            ps.execute();

        } else if (o instanceof Venda) {
            Venda p = (Venda) o;

            // deleta o registro em tb_venda_consulta
            PreparedStatement ps = this.con.prepareStatement("delete from tb_venda_consulta where venda_id = ?");
            ps.setInt(1, p.getId());

            ps.execute();

            // deleta o registro em tb_venda_produto
            ps = this.con.prepareStatement("delete from tb_venda_produto where venda_id = ?");
            ps.setInt(1, p.getId());

            ps.execute();

            // deleta o registro da venda sem pendências
            ps = this.con.prepareStatement("delete from tb_venda where id = ?");
            ps.setInt(1, p.getId());

            ps.execute();

        } else if (o instanceof Consulta) {
            Consulta c = (Consulta) o;

            // deleta o registro em tb_venda_consulta
            PreparedStatement ps = this.con.prepareStatement("delete from tb_venda_consulta where consulta_id = ?");
            ps.setInt(1, c.getId());

            ps.execute();

            // deleta o registro da consulta sem pendências
            ps = this.con.prepareStatement("delete from tb_consulta where id = ?");
            ps.setInt(1, c.getId());

            ps.execute();

        }
    }

    // metodo implementado para apoiar a autenticacao
    @Override
    public Pessoa doLogin(String cpf, String senha) throws Exception {

        Pessoa p = null;

        // comando de seleção que verifica se o cpf pertence a um funcionario e autentica senha
        PreparedStatement ps = this.con.prepareStatement("select p.cpf, p.senha, p.nome from tb_pessoa p, tb_funcionario f "
                + "where p.cpf = f.cpf "
                + "and p.cpf = ? and senha = ? ");

        ps.setString(1, cpf);
        ps.setString(2, senha);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            p = new Pessoa();
            p.setCpf(rs.getString("cpf"));
            p.setSenha(rs.getString("senha"));
            p.setNome(rs.getString("nome"));
        }

        ps.close();

        return p;
    }

    @Override
    public List<Produto> listProdutos() throws Exception {

        //questao 4 da primeira etapa.
        List<Produto> lista;

        PreparedStatement ps = this.con.prepareStatement(" select p.id, p.nome, p.quantidade, p.tipo, p.valor, p.fornecedor_cpf, f.ie "
                + " from tb_produto p, tb_fornecedor f where p.fornecedor_cpf=f.cpf order by id asc");

        ResultSet rs = ps.executeQuery();

        lista = new ArrayList();

        while (rs.next()) {

            Produto p = new Produto();
            p.setId(rs.getInt("id"));
            p.setNome(rs.getString("nome"));
            p.setQuantidade(rs.getFloat("quantidade"));
            if (rs.getString("tipo") == TipoProduto.CONSULTA.name()) {
                p.setTipo(TipoProduto.CONSULTA);
            } else if (rs.getString("tipo") == TipoProduto.ATENDIMENTO_AMBULATORIAL.name()) {
                p.setTipo(TipoProduto.ATENDIMENTO_AMBULATORIAL);
            }
            p.setValor(rs.getFloat("valor"));
            Fornecedor fc = new Fornecedor();
            fc.setCpf(rs.getString("fornecedor_cpf"));
            fc.setIe(rs.getString("ie"));
            p.setFornecedor(fc);

            lista.add(p);

        }

        return lista;

    }

    @Override
    public List<Venda> listVendas() throws Exception {

        List<Venda> lista = null;

        PreparedStatement ps = this.con.prepareStatement("select v.id, v.observacao, v.data, v.valor_total, "
                + "v.cliente_cpf, pc.nome as c_nome, v.funcionario_cpf, pf.nome as f_nome, v.formapagamento " // alias para os nomes
                + "from tb_venda v, tb_cliente c, tb_funcionario f, tb_pessoa pc, tb_pessoa pf " // pessoa para c e f
                + "where v.cliente_cpf = c.cpf "
                + "and v.funcionario_cpf = f.cpf "
                + "and c.cpf = pc.cpf " // vincula c com pc
                + "and f.cpf = pf.cpf " // vincula f com pf
                + "order by v.id asc");

        ResultSet rs = ps.executeQuery(); //executa a query        

        lista = new ArrayList();
        while (rs.next()) {

            Venda v = new Venda();
            v.setId(rs.getInt("id"));
            Calendar dtCad = Calendar.getInstance();
            dtCad.setTimeInMillis(rs.getDate("data").getTime());
            v.setData(dtCad);
            v.setObservacao(rs.getString("observacao"));
            v.setValor_total(rs.getFloat("valor_total"));
            Cliente cli = new Cliente();
            cli.setCpf(rs.getString("cliente_cpf"));
            cli.setNome(rs.getString("c_nome"));
            v.setCliente(cli);
            Funcionario fun = new Funcionario();
            fun.setCpf(rs.getString("funcionario_cpf"));
            fun.setNome(rs.getString("f_nome"));
            v.setFuncionario(fun);
            if (rs.getString("formapagamento") == Pagamento.CARTAO_DEBITO.name()) {
                v.setFormaPagamento(Pagamento.CARTAO_DEBITO);
            } else if (rs.getString("formapagamento") == Pagamento.CARTAO_CREDITO.name()) {
                v.setFormaPagamento(Pagamento.CARTAO_CREDITO);
            } else if (rs.getString("formapagamento") == Pagamento.DINHEIRO.name()) {
                v.setFormaPagamento(Pagamento.DINHEIRO);
            } else if (rs.getString("formapagamento") == Pagamento.PIX.name()) {
                v.setFormaPagamento(Pagamento.PIX);
            } else if (rs.getString("formapagamento") == Pagamento.BOLETO.name()) {
                v.setFormaPagamento(Pagamento.BOLETO);
            }

            PreparedStatement ps2 = this.con.prepareStatement("select c.id, c.data, c.observacao, c.valor as c_valor, "
                    + "c.medico_cpf, pss.nome as pss_nome, c.pet_id, p.nome as p_nome " // alias para os nomes
                    + "from tb_venda_consulta vc, tb_consulta c, tb_medico m, tb_pet p, tb_pessoa pss "
                    + "where c.medico_cpf = m.cpf "
                    + "and m.cpf = pss.cpf " // vincula m com pss
                    + "and c.pet_id = p.id " // vincula o pet de c com o nome 
                    + "and c.id = vc.consulta_id " // vincula a c com a v
                    + "and vc.venda_id = ? "); // determina a v

            ps2.setInt(1, v.getId());

            ResultSet rs2 = ps2.executeQuery();

            while (rs2.next()) {

                Consulta c = new Consulta();
                c.setId(rs2.getInt("id"));
                dtCad = Calendar.getInstance();
                dtCad.setTimeInMillis(rs2.getDate("data").getTime());
                c.setData(dtCad);
                c.setObservacao(rs2.getString("observacao"));
                c.setValor(rs2.getFloat("c_valor"));
                Medico med = new Medico();
                med.setCpf(rs2.getString("medico_cpf"));
                med.setNome(rs2.getString("pss_nome"));
                c.setMedico(med);
                Pet pet = new Pet();
                pet.setId(rs2.getInt("pet_id"));
                pet.setNome(rs2.getString("p_nome"));
                c.setPet(pet);

                v.setConsulta(c); // seta a consulta na lista de consultas
            }

            lista.add(v);

        }

        return lista;

    }

    @Override
    public List<Consulta> listConsultas() throws Exception {

        List<Consulta> lista = null;

        PreparedStatement ps = this.con.prepareStatement("select c.id, c.data, c.observacao, c.data_retorno, c.valor, "
                + "c.medico_cpf, pss.nome as pss_nome, c.pet_id, p.nome as p_nome " // alias para os nomes
                + "from tb_consulta c, tb_medico m, tb_pet p, tb_pessoa pss "
                + "where c.medico_cpf = m.cpf "
                + "and m.cpf = pss.cpf " // vincula m com pss
                + "and c.pet_id = p.id " // vincula o pet de c com o nome 
                + "order by id asc");

        ResultSet rs = ps.executeQuery(); //executa a query        

        lista = new ArrayList();
        while (rs.next()) {

            Consulta c = new Consulta();
            c.setId(rs.getInt("id"));
            Calendar dtCad = Calendar.getInstance();
            dtCad.setTimeInMillis(rs.getDate("data").getTime());
            c.setData(dtCad);
            c.setObservacao(rs.getString("observacao"));
            dtCad.setTimeInMillis(rs.getDate("data_retorno").getTime());
            c.setData_retorno(dtCad);
            c.setValor(rs.getFloat("valor"));
            Medico med = new Medico();
            med.setCpf(rs.getString("medico_cpf"));
            med.setNome(rs.getString("pss_nome"));
            c.setMedico(med);
            Pet pet = new Pet();
            pet.setId(rs.getInt("pet_id"));
            pet.setNome(rs.getString("p_nome"));
            c.setPet(pet);

            lista.add(c);

        }

        rs.close();

        return lista;

    }

    @Override
    public List<Cliente> listClientes() throws Exception {

        List<Cliente> lista = null;

        PreparedStatement ps = this.con.prepareStatement("select p.cpf, p.nome, c.data_ultima_visita "
                + "from tb_cliente c, tb_pessoa p "
                + "where c.cpf = p.cpf");

        ResultSet rs = ps.executeQuery(); //executa a query        

        lista = new ArrayList();
        while (rs.next()) {

            Cliente c = new Cliente();
            c.setCpf(rs.getString("cpf"));
            c.setNome(rs.getString("nome"));
            Calendar dtCad = Calendar.getInstance();
            dtCad.setTimeInMillis(rs.getDate("data_ultima_visita").getTime());
            c.setData_ultima_visita(dtCad);

            lista.add(c);

        }

        rs.close();

        return lista;

    }

    @Override
    public List<Funcionario> listFuncionarios() throws Exception {

        List<Funcionario> lista = null;

        PreparedStatement ps = this.con.prepareStatement("select p.cpf, p.nome, f.numero_ctps, f.numero_pis "
                + "from tb_pessoa p, tb_funcionario f "
                + "where p.cpf = f.cpf");

        ResultSet rs = ps.executeQuery(); //executa a query        

        lista = new ArrayList();
        while (rs.next()) {

            Funcionario f = new Funcionario();
            f.setCpf(rs.getString("cpf"));
            f.setNome(rs.getString("nome"));
            f.setNumero_ctps(rs.getString("numero_ctps"));
            f.setNumero_pis(rs.getString("numero_pis"));

            lista.add(f);

        }

        rs.close();

        return lista;

    }

    @Override
    public List<Pet> listPets() throws Exception {
        List<Pet> lista = null;

        PreparedStatement ps = this.con.prepareStatement("select p.id, p.data_nascimento, p.nome, p.observacao, p.cliente_cpf, p.raca_id "
                + "from tb_pet p");

        ResultSet rs = ps.executeQuery(); //executa a query        

        lista = new ArrayList();
        while (rs.next()) {

            Pet p = new Pet();
            p.setId(rs.getInt("id"));
            Calendar dtCad = Calendar.getInstance();
            dtCad.setTimeInMillis(rs.getDate("data_nascimento").getTime());
            p.setData_nascimento(dtCad);
            p.setNome(rs.getString("nome"));
            p.setObservacao(rs.getString("observacao"));
            Cliente cli = new Cliente();
            cli.setCpf(rs.getString("cliente_cpf"));
            p.setCliente(cli);
            Raca rac = new Raca();
            rac.setId(rs.getInt("raca_id"));
            p.setRaca(rac);

            lista.add(p);

        }

        rs.close();

        return lista;
    }

    @Override
    public List<Medico> listMedicos() throws Exception {
        List<Medico> lista = null;

        PreparedStatement ps = this.con.prepareStatement("select p.cpf, p.nome, m.numero_crmv "
                + "from tb_pessoa p, tb_medico m "
                + "where p.cpf = m.cpf");

        ResultSet rs = ps.executeQuery(); //executa a query        

        lista = new ArrayList();
        while (rs.next()) {

            Medico m = new Medico();
            m.setCpf(rs.getString("cpf"));
            m.setNome(rs.getString("nome"));
            m.setNumero_crmv(rs.getString("numero_crmv"));

            lista.add(m);

        }

        rs.close();

        return lista;
    }

}
