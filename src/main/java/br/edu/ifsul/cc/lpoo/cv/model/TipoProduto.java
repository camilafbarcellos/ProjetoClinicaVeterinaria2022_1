
package br.edu.ifsul.cc.lpoo.cv.model;

/**
 *
 * @author telmo
 */
public enum TipoProduto {
    
    MEDICAMENTO, SESSAO_FISIOTERAPIA, SESSAO_ADESTRAMENTO, ATENDIMENTO_AMBULATORIAL, CONSULTA, CONSULTA_REVISAO;
    
    public TipoProduto getTipoProduto(String tipo){
        
        if(tipo.equals(MEDICAMENTO.toString())){
            
            return TipoProduto.MEDICAMENTO;
            
        }else if(tipo.equals(SESSAO_FISIOTERAPIA.toString())){
            
            return TipoProduto.SESSAO_FISIOTERAPIA;
            
        }else if(tipo.equals(SESSAO_ADESTRAMENTO.toString())){
         
            return TipoProduto.SESSAO_ADESTRAMENTO;
            
        }else if(tipo.equals(CONSULTA.toString())){
            
            return TipoProduto.CONSULTA;
            
        }else if(tipo.equals(CONSULTA_REVISAO.toString())){
            
            return TipoProduto.CONSULTA_REVISAO;
            
        }else {
        
            return null;
        }
                
    }
    
}
