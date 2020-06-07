/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.negocion;

import modelo.dato.Dinstructor;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author David Torrez
 */
public class Ninstructor {
    
    private Dinstructor DatoInstructor;
    
    public Ninstructor(){
        DatoInstructor = new Dinstructor();
    }
    
    public Ninstructor(String ci, String nombre){
        DatoInstructor = new Dinstructor(ci,nombre);
    }
    
    public void setInstructorR(String ci, String nombre, char sexo, int telefono, String profesion) throws Exception {
        DatoInstructor.setCi(ci);
        DatoInstructor.setNombre(nombre);
        DatoInstructor.setSexo(sexo);
        DatoInstructor.setTelefono(telefono);
        DatoInstructor.setProfesion(profesion);
        DatoInstructor.insertInstructor();
    }

    public void setInstructorM(String ci, String nombre,char sexo, int telefono, String profesion) throws Exception {
        DatoInstructor.setCi(ci);
        DatoInstructor.setNombre(nombre);
        DatoInstructor.setSexo(sexo);
        DatoInstructor.setTelefono(telefono);
        DatoInstructor.setProfesion(profesion);
        DatoInstructor.updateInstructor();
    }

    public void setInstructorE(String ci) throws Exception {
        DatoInstructor.setCi(ci);
        DatoInstructor.deleteInstructor();
    }

    public DefaultTableModel getTablaInstructores() {
        return DatoInstructor.getTabla();
    }
    
    public DefaultComboBoxModel getComboBoxInstructores() {
        return DatoInstructor.getComboBox();
    }

    public Map<String, String> getInstructor(String ci ,int type) {
        
        DatoInstructor.setCi(ci);
        return DatoInstructor.getRow(type);
    }
    
    public String getCi(){
        return this.DatoInstructor.getCi();
    }
    
    public String getNombre(){
        return this.DatoInstructor.getNombre();
    }
    
    @Override
    public String toString() {
        return this.DatoInstructor.toString();
    }

    
}
