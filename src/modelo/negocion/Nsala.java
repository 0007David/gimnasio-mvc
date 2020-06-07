/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.negocion;

import modelo.dato.Dsala;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author David Torrez
 */
public class Nsala {
    
    private final Dsala DatoSala;
    
    public Nsala(){
        DatoSala = new Dsala();
    }
    
    public Nsala(int id,String nombre){
        DatoSala = new Dsala(id,nombre);
    }
    
    public void setSalaR(String nombre, int capacidad) throws Exception {
        DatoSala.setNombre(nombre);
        DatoSala.setCapacidad(capacidad);
        DatoSala.insertSala();
    }

    public void setSalaM(int id, String nombre, int capacidad) throws Exception {
        DatoSala.setId(id);
        DatoSala.setNombre(nombre);
        DatoSala.setCapacidad(capacidad);
        DatoSala.updateSala();
    }

    public void setSalaE(int id) throws Exception {
        DatoSala.setId(id);
        DatoSala.deleteSala();
    }

    public DefaultTableModel getTablaSalas() {
        return DatoSala.getTabla();
    }
    
    public DefaultComboBoxModel getComboBoxSalas() {
        return DatoSala.getComboBox();
    }

    public Map<String, String> getSala(int id) {
        DatoSala.setId(id);
        return DatoSala.getRow();
    }
    
    public int getId(){
        return DatoSala.getId();
    }
    
    public String getNombre(){
        return DatoSala.getNombre();
    }

    @Override
    public String toString() {
        return this.DatoSala.toString();
    }
    
}
