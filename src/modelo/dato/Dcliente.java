/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dato;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import modelo.negocion.Ncliente;

/**
 *
 * @author David Torrez
 */
public class Dcliente {

    private String ci;
    private String nombre;
    private char sexo;
    private float peso;
    private String telefono;
    private String correo;

    private Dconexion conexion;

    public Dcliente() {
        conexion = new Dconexion();
    }

    public Dcliente(String ci, String nombre) {
        this.ci = ci;
        this.nombre = nombre;
    }

    public void insertCliente() throws Exception {
        String query = "INSERT INTO cliente (ci, nombre, sexo, telefono, correo, peso) VALUES ('"
                + this.ci + "','" + this.nombre + "','"
                + this.sexo + "','" + this.telefono + "','"
                + this.correo + "','" + this.peso + "');";

        Statement consulta;
        try {
            conexion.openConexion();
            consulta = (Statement) conexion.con.createStatement();
            consulta.execute(query);
            consulta.close();
            conexion.closeConexion();
        } catch (SQLException e) {
            System.out.println(e.toString() + " -> error sql");
            System.out.println("Error al guardar");
            throw new Exception();
        }

    }

    public DefaultTableModel getTabla() {
        String[] columnNames = {"CI", "Nombre", "Sexo", "Telefono", "Correo"};
        DefaultTableModel listar = new DefaultTableModel(columnNames, 0);
        String query = "SELECT ci, nombre, sexo,telefono,correo FROM cliente";
        //-----------------------------
        Statement Consulta;
        ResultSet resultado = null;
        String[] datos = new String[columnNames.length];
        try {
            conexion.openConexion();
            Consulta = (Statement) Dconexion.con.createStatement();
            resultado = Consulta.executeQuery(query);
            while (resultado.next()) {
                for (int i = 0; i < columnNames.length; i++) {
                    datos[i] = resultado.getString(i + 1);
                }
                listar.addRow(datos);
            }
            Consulta.close();
            conexion.closeConexion();
        } catch (SQLException e) {
            System.out.println("no se pudo listar los datos" + e.getMessage());
        }
        return listar;
    }

    public void updateCliente() throws Exception {
        String query = "UPDATE cliente set "
                + "nombre= '" + this.nombre + "', correo= '" + this.correo + "', telefono='" + this.telefono
                + "', peso= '" + this.peso + "', sexo= '" + this.sexo
                + "' WHERE ci= '" + this.ci + "';";
        // System.out.println(query);
        Statement consulta;
        try {
            conexion.openConexion();
            consulta = (Statement) conexion.con.createStatement();
            consulta.execute(query);
            consulta.close();
            conexion.closeConexion();
        } catch (SQLException e) {
            System.out.println(e.toString() + " -> error sql");
            System.out.println("Error al update");
            throw new Exception();
        }

    }

    public void deleteCliente() throws Exception {
        String query = "DELETE FROM cliente WHERE ci = '" + this.ci + "';";
        // System.out.println(query);
        Statement consulta;
        try {
            conexion.openConexion();
            consulta = (Statement) conexion.con.createStatement();
            consulta.execute(query);
            consulta.close();
            conexion.closeConexion();
        } catch (SQLException e) {
            System.out.println(e.toString() + " -> error sql");
            System.out.println("Error al guardar");
            throw new Exception();
        }

    }

    public Map<String, String> getRow() {
        Map<String, String> sala = new HashMap<>();
//        map.put("name", "demo");
        String query = "SELECT ci, nombre, sexo,telefono,correo,peso FROM cliente WHERE ci= '" + this.ci + "';";
        // System.out.println(query);
        //----------------------------

        Statement Consulta;
        ResultSet resultado = null;
        try {
            conexion.openConexion();
            Consulta = (Statement) Dconexion.con.createStatement();
            resultado = Consulta.executeQuery(query);
            ResultSetMetaData rsmd = resultado.getMetaData();
            if (!resultado.next()) {
                sala.put("estado", "vacio");
            }
            for (int i = 0; i < 6; i++) {
                sala.put(rsmd.getColumnName(i + 1).trim(), resultado.getString(i + 1).trim());
            }
            Consulta.close();
            conexion.closeConexion();
        } catch (SQLException e) {
            System.out.println("no se pudo listar los datos" + e.getMessage());
        }
        return sala;
    }

    public DefaultComboBoxModel getComboBox() {
        DefaultComboBoxModel comboBox = new DefaultComboBoxModel();
        String query = "SELECT id, nombre FROM cliente;";
        //---------------------------------------
        Statement Consulta;
        ResultSet resultado = null;
        String[] datos = new String[2];
        try {
            this.conexion.openConexion();
            Consulta = (Statement) conexion.con.createStatement();
            resultado = Consulta.executeQuery(query);
            comboBox.addElement(new Ncliente("0", "Seleccione"));
            while (resultado.next()) {
                for (int i = 0; i < 2; i++) {
                    datos[i] = resultado.getString(i + 1).trim();
                }
                comboBox.addElement(new Ncliente(datos[0], datos[1]));
            }
            Consulta.close();
            this.conexion.closeConexion();

        } catch (SQLException e) {
            System.out.println("no se pudo listar los datos" + e.getMessage());
        }
        return comboBox;
    }

    public String getCi() {
        return ci;
    }

    public String getNombre() {
        return nombre;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
