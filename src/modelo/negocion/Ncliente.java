/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.negocion;

import javax.swing.table.DefaultTableModel;
import modelo.dato.Dcliente;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author David Torrez
 */
public class Ncliente {

    private Dcliente DatoCliente;

    public Ncliente() {
        DatoCliente = new Dcliente();
    }

    public Ncliente(String ci, String nombre) {
        DatoCliente = new Dcliente(ci, nombre);
    }

    public void setClienteR(String ci, String nombre, char sexo, String telefono, String correo, float peso) throws Exception {
        DatoCliente.setCi(ci);
        DatoCliente.setNombre(nombre);
        DatoCliente.setSexo(sexo);
        DatoCliente.setTelefono(telefono);
        DatoCliente.setCorreo(correo);
        DatoCliente.setPeso(peso);
        DatoCliente.insertCliente();
    }

    public void setClienteM(String ci, String nombre, char sexo, String telefono,  String correo, float peso) throws Exception {
        DatoCliente.setCi(ci);
        DatoCliente.setNombre(nombre);
        DatoCliente.setSexo(sexo);
        DatoCliente.setTelefono(telefono);
        DatoCliente.setCorreo(correo);
        DatoCliente.setPeso(peso);
        DatoCliente.updateCliente();
    }

    public void setClienteE(String ci) throws Exception {
        DatoCliente.setCi(ci);
        DatoCliente.deleteCliente();
    }

    public DefaultTableModel getTablaClientes() {
        return DatoCliente.getTabla();
    }

    public Map<String, String> getCliente(String ci) {
        DatoCliente.setCi(ci);
        return DatoCliente.getRow();
    }
    
    public DefaultComboBoxModel getComboBoxClientes() {
        return DatoCliente.getComboBox();
    }
    
    public String getCi(){
        return DatoCliente.getCi();
    }
    
    public String getNombre(){
        return DatoCliente.getNombre();
    }

    @Override
    public String toString() {
        return DatoCliente.toString();
    }

}
