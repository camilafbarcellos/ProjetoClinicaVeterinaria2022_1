
package br.edu.ifsul.cc.lpoo.cv.model.dao;

import br.edu.ifsul.cc.lpoo.cv.model.Cliente;
import br.edu.ifsul.cc.lpoo.cv.model.Consulta;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.Medico;
import br.edu.ifsul.cc.lpoo.cv.model.Pessoa;
import br.edu.ifsul.cc.lpoo.cv.model.Pet;
import br.edu.ifsul.cc.lpoo.cv.model.Produto;
import br.edu.ifsul.cc.lpoo.cv.model.Venda;
import java.util.List;

/**
 *
 * @author Prof. Telmo (Avaliação da primeira etapa - 30/05/2022)
 */

public interface InterfacePersistencia {
    
    public Boolean conexaoAberta();
    public void fecharConexao();
    public Object find(Class c, Object id) throws Exception;
    public void persist(Object o) throws Exception;
    public void remover(Object o) throws Exception;
    
    public Pessoa doLogin(String CPF, String senha) throws Exception;
    
    public List<Produto> listProdutos() throws Exception;
    
    public List<Consulta> listConsultas() throws Exception;
    
    public List<Venda> listVendas() throws Exception;
    
    public List<Cliente> listClientes() throws Exception;
    
    public List<Funcionario> listFuncionarios() throws Exception;
    
    public List<Pet> listPets() throws Exception;
    
    public List<Medico> listMedicos() throws Exception;
    
}
