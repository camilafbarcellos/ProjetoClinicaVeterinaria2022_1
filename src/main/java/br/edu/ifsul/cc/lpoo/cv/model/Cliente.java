
package br.edu.ifsul.cc.lpoo.cv.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author telmo
 */


@Entity
@Table(name = "tb_cliente")
@DiscriminatorValue("C")
public class Cliente extends Pessoa{
    
    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar data_ultima_visita;
    
    
    @OneToMany(mappedBy = "cliente")//mappedBy deve apontar para a referencia de cliente dentro de Pet.
    private List<Pet> pets;
    
    public Cliente(){
        
    }

    /**
     * @return the data_ultima_visita
     */
    public Calendar getData_ultima_visita() {
        return data_ultima_visita;
    }

    /**
     * @param data_ultima_visita the data_ultima_visita to set
     */
    public void setData_ultima_visita(Calendar data_ultima_visita) {
        this.data_ultima_visita = data_ultima_visita;
    }

    /**
     * @return the pets
     */
    public List<Pet> getPets() {
        return pets;
    }

    /**
     * @param pets the pets to set
     */
    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
    
    // metodo para setar um pet na lista de pets
    public void setPet(Pet pet) {
        if (this.pets == null) {
            this.pets = new ArrayList();
        }

        this.pets.add(pet);
    }
    
    @Override
    public String toString() {
        return this.getNome();
    }

    @Override
    public boolean equals(Object o){

        if(o == null){
            return false;

        }else if(!(o instanceof Cliente)){
            return false;

        }else{
            Cliente c = (Cliente) o;
            if (c.getCpf()== this.getCpf()){
                return true;
            }else{
                return false;
            }
        }
    }
    
}
